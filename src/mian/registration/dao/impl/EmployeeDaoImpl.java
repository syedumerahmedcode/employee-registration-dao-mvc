package mian.registration.dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import main.registration.exception.EmployeeRegistrationException;
import mian.registration.dao.EmployeeDao;
import mian.registration.model.Employee;

public class EmployeeDaoImpl implements EmployeeDao{

	private static final String MYSQL_JDBC_DRIVER_CLASSNAME = "com.mysql.jdbc.Driver";
	private static final String USER = "padmin";
	private static final String PASSWORD = "Windo@1234";
	private static final String DATABASE_NAME="employees";
	private static final String JDBC_URL = "jdbc:mysql://localhost:3306/"+DATABASE_NAME+"?useSSL=false&serverTimezone=UTC&useLegacyDatetimeCode=false";
	/**
	 * Insert statement is written in native sql. Have a look at the firstName and
	 * lastName columns ---> They match the column names in the table of the
	 * database.
	 */
	private static final String INSERT_USERS_SQL = "INSERT INTO employee"
			+ " (id, first_name, last_name, username, password, address, contact) " + "VALUES (?,?,?,?,?,?,?);";

	public int registerEmployee(Employee employee) throws ClassNotFoundException, EmployeeRegistrationException {

		int result = 0;

		Class.forName(MYSQL_JDBC_DRIVER_CLASSNAME);
		try (Connection connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);

			// Step 2: Create a statement using connection object
			PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USERS_SQL)) {
			/**
			 * The id should be entered dynamically or auto generated.
			 */
			preparedStatement.setInt(1, 1);
			preparedStatement.setString(2, employee.getFirstName());
			preparedStatement.setString(3, employee.getLastName());
			preparedStatement.setString(4, employee.getUsername());
			preparedStatement.setString(5, employee.getPassword());
			preparedStatement.setString(6, employee.getAddress());
			preparedStatement.setString(7, employee.getContact());

			System.out.println("The prepared statement is: " + preparedStatement);

			// Step 3: Execute the query
			result = preparedStatement.executeUpdate();

		} catch (SQLException ex) {
			ex.printStackTrace();
			throw new EmployeeRegistrationException();
		}
		return result;
	}
}
