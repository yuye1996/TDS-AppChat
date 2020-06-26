package umu.tds.persistencia;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import umu.tds.dao.DAOException;
import umu.tds.dao.FactoriaDAO;
import umu.tds.dao.IUsuarioDAO;
import umu.tds.modelo.Usuario;

//TODO
public class CatalogoUsuario {
	
	private static CatalogoUsuario unicaInstancia = null;

	private Map<String,Usuario> usuarioPorTelefono;
	private Map<String,Usuario> usuarioPorNombre;
	
	private FactoriaDAO dao;
	private IUsuarioDAO adaptadorUsuario;
	
	private CatalogoUsuario() {
		try {
			dao = FactoriaDAO.getInstancia(FactoriaDAO.DAO_TDS);
			adaptadorUsuario = dao.getUsuarioDAO();
			usuarioPorTelefono = new HashMap<String, Usuario>();
			usuarioPorNombre = new HashMap<String, Usuario>();
			cargarCatalogo();
			
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static CatalogoUsuario getUnicaInstancia() {
		if (unicaInstancia == null)
			unicaInstancia = new CatalogoUsuario();
		return unicaInstancia;
	}
	
	
	//Devolver usuario del HashMap por telefono
	public Usuario getUsuario(String movil) {
		return usuarioPorTelefono.get(movil);
	}
	
	//Devolver usuario por nombre de usuario
	public Usuario getUsuarioPorNombre(String usuario) {
		return usuarioPorNombre.get(usuario);
	}
	
	
	public void addUsuario(Usuario u) {
		usuarioPorTelefono.put(u.getMovil(), u);
		usuarioPorNombre.put(u.getNombre(), u);
		System.out.println("Añadido usuario a Catalogo Usuario");
	}
	
	
	/*Recupera todos los usuarios para trabajar con ellos en memoria*/
	private void cargarCatalogo() throws DAOException {
		 List<Usuario> usuariosBD = adaptadorUsuario.recuperarTodosoUsuarios();
		 for (Usuario u: usuariosBD) {
			     usuarioPorTelefono.put(u.getMovil(),u);
			     usuarioPorNombre.put(u.getNombre(), u);
		 }
	}

}