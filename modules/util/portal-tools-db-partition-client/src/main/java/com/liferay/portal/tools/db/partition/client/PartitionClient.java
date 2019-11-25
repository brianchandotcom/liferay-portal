/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.tools.db.partition.client;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.time.LocalDateTime;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jline.console.ConsoleReader;

import org.apache.commons.io.output.TeeOutputStream;

/**
 * @author Alberto Chaparro
 */
public class PartitionClient {

	public static void main(String[] args) {
		try {
			_initLog();

			String action = _getAction(args);

			_properties = _readProperties(
				new File("portal-partition.properties"));

			_consoleReader = new ConsoleReader();

			_initConnection();

			_normalizedCompanyId = _normalizeName(
				"companyId", _databaseMetaData);

			_initCompanies();

			_initTableNames();

			_runAction(action);
		}
		catch (Exception e) {
			e.printStackTrace(_printStream);
		}
	}

	private static void _addCompanyIdToPrimaryKey(
			String tableName, String pkName, List<String> columns)
		throws SQLException {

		if (columns.contains(_normalizedCompanyId)) {
			System.out.println(
				"Primary key for " + tableName + " already contains the " +
					"companyId field");

			return;
		}

		String dropPKClause = "alter table ";

		if (_dbType.equals(_DB_MYSQL) || _dbType.equals(_DB_MARIADB) ||
			_dbType.equals(_DB_DB2)) {

			dropPKClause += tableName + " drop primary key";
		}
		else {
			dropPKClause += tableName + " drop constraint " + pkName;
		}

		String createPKClause =
			"alter table " + tableName + " add primary key (";

		for (String pkColumn : columns) {
			createPKClause += pkColumn + ", ";
		}

		createPKClause += "companyId)";

		_dropAndCreatePrimaryKey(dropPKClause, createPKClause);

		System.out.println(
			"Primary key for " + tableName + " now contains the companyId " +
				"field");
	}

	private static void _addCompanyIdToPrimaryKeys(List<String> tableNames)
		throws SQLException {

		for (String tableName : tableNames) {
			try (ResultSet rs = _databaseMetaData.getPrimaryKeys(
					_connection.getCatalog(), _getSchema(), tableName)) {

				String pkName = null;
				List<String> pkColumns = new ArrayList<>();

				if (rs.next()) {
					pkName = rs.getString("PK_NAME");

					pkColumns.add(rs.getString(_JDBC_COLUMN_NAME));

					while (rs.next()) {
						pkColumns.add(rs.getString(_JDBC_COLUMN_NAME));
					}

					_addCompanyIdToPrimaryKey(tableName, pkName, pkColumns);
				}
			}
		}
	}

	private static void _addCompanyIdToUniqueIndex(
			String tableName, String indexName, List<String> columnNames)
		throws SQLException {

		String indexUpperCase = indexName.toUpperCase();

		if (!indexUpperCase.startsWith(_INDEX_PREFIX)) {
			return;
		}

		if (columnNames.indexOf(_normalizedCompanyId) != -1) {
			System.out.println(
				"Unique index " + tableName + "." + indexName +
					" already contains the companyId field");

			return;
		}

		String dropIndexClause = "drop index ";

		if (_dbType.equals(_DB_MYSQL) || _dbType.equals(_DB_MARIADB)) {
			dropIndexClause += indexName + " on " + tableName;
		}
		else if (_dbType.equals(_DB_SQLSERVER) || _dbType.equals(_DB_SYBASE)) {
			dropIndexClause += tableName + "." + tableName;
		}
		else {
			dropIndexClause += indexName;
		}

		String createIndexClause =
			"create unique index " + indexName + " on " + tableName + "(";

		for (String columnName : columnNames) {
			createIndexClause += columnName + ", ";
		}

		createIndexClause += "companyId)";

		_dropAndCreateIndex(indexName, dropIndexClause, createIndexClause);

		System.out.println(
			"Unique index " + tableName + "." + indexName + " now contains " +
				"the companyId field");
	}

	private static void _addCompanyIdToUniqueIndexes(List<String> tableNames)
		throws SQLException {

		for (String tableName : tableNames) {
			try (ResultSet rs = _databaseMetaData.getIndexInfo(
					_connection.getCatalog(), _getSchema(), tableName, true,
					false)) {

				List<String> columnNames = new ArrayList<>();

				String indexName = null;

				while (rs.next()) {
					if (indexName == null) {
						indexName = rs.getString(_JDBC_INDEX_NAME);
					}
					else if (!indexName.equals(
								rs.getString(_JDBC_INDEX_NAME))) {

						_addCompanyIdToUniqueIndex(
							tableName, indexName, columnNames);

						indexName = rs.getString(_JDBC_INDEX_NAME);

						columnNames = new ArrayList();
					}

					columnNames.add(rs.getString(_JDBC_COLUMN_NAME));
				}

				if (indexName != null) {
					_addCompanyIdToUniqueIndex(
						tableName, indexName, columnNames);
				}
			}
		}
	}

	private static void _dropAndCreateIndex(
			String indexName, String dropClause, String createClause)
		throws SQLException {

		if (indexName != null) {
			String tempCreateClause = createClause.replace(
				indexName, indexName + "_t");

			String tempDropClause = dropClause.replace(
				indexName, indexName + "_t");

			try (PreparedStatement ps1 = _connection.prepareStatement(
					tempCreateClause);
				PreparedStatement ps2 = _connection.prepareStatement(
					tempDropClause)) {

				ps1.execute();
				ps2.execute();
			}
			catch (SQLException sqle) {
				System.out.println(
					"Index " + indexName + " can not be regenerated by " +
						"executing " + createClause + ". Modify it manually.");

				throw sqle;
			}
		}

		try (PreparedStatement ps1 = _connection.prepareStatement(dropClause);
			PreparedStatement ps2 = _connection.prepareStatement(
				createClause)) {

			ps1.execute();
			ps2.execute();
		}
	}

	private static void _dropAndCreatePrimaryKey(
			String dropClause, String createClause)
		throws SQLException {

		_dropAndCreateIndex(null, dropClause, createClause);
	}

	private static String _getAction(String[] args)
		throws IllegalArgumentException {

		if ((args.length == 2) && args[0].equals("action")) {
			List<String> actions = Arrays.asList(_ACTIONS);

			if (actions.contains(args[1])) {
				return args[1];
			}
		}

		throw new IllegalArgumentException(
			"Add the argument action and one valid value " +
				_ACTIONS.toString());
	}

	private static String _getSchema() throws SQLException {
		if (_dbType.equals(_DB_DB2)) {
			try (PreparedStatement ps = _connection.prepareStatement(
					"values current schema");
				ResultSet rs = ps.executeQuery()) {

				rs.next();

				return rs.getString(1);
			}
			catch (SQLException sqle) {
				System.out.println("Error getting schema name");

				throw sqle;
			}
		}

		return _connection.getSchema();
	}

	private static boolean _hasColumn(
			Connection con, DatabaseMetaData metadata, String tableName,
			String columnName)
		throws SQLException {

		try (ResultSet rs = metadata.getColumns(
				con.getCatalog(), _getSchema(), tableName,
				_normalizeName(columnName, metadata))) {

			if (!rs.next()) {
				return false;
			}
		}

		return true;
	}

	private static void _initCompanies() throws Exception {
		try (PreparedStatement ps = _connection.prepareStatement(
				"select companyId FROM Company");
			ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				_companyIds.add(rs.getLong(1));
			}
		}

		if (_companyIds.size() == 0) {
			throw new Exception("Company table has not been initialized");
		}
	}

	private static void _initConnection() throws Exception {
		String url = _readProperty(_properties, "jdbc.default.url");

		Matcher matcher = _jdbcUrlPattern.matcher(url);

		if (!matcher.find()) {
			throw new Exception(
				"Invalid jdbc url, follow the pattern " + _JDBC_URL_REGEX);
		}

		_dbType = matcher.group(1);

		String userName = _readProperty(_properties, "jdbc.default.username");

		String password = _readProperty(
			_properties, "jdbc.default.password", '*', null);

		_connection = DriverManager.getConnection(url, userName, password);

		_databaseMetaData = _connection.getMetaData();
	}

	private static void _initLog() throws IOException {
		File logDir = new File("logs");

		if ((logDir != null) && !logDir.exists()) {
			logDir.mkdirs();
		}

		File logFile = new File(logDir, "partition.log");

		if (logFile.exists()) {
			String logFileName = logFile.getName();

			logFile.renameTo(
				new File(logDir, logFileName + "." + logFile.lastModified()));

			logFile = new File(logDir, logFileName);
		}

		TeeOutputStream out = new TeeOutputStream(
			new FileOutputStream(logFile), System.out);

		_printStream = new PrintStream(out, true);

		System.setOut(_printStream);
	}

	private static void _initTableNames() throws SQLException {
		try (ResultSet rs = _databaseMetaData.getTables(
				_connection.getCatalog(), _getSchema(), "%", null)) {

			while (rs.next()) {
				String tableType = rs.getString("TABLE_TYPE");

				if (!Objects.equals("TABLE", tableType)) {
					continue;
				}

				String tableName = rs.getString("TABLE_NAME");

				if (_hasColumn(
						_connection, _databaseMetaData, tableName,
						"companyId")) {

					_companyIdTableNames.add(tableName);
				}
			}
		}
	}

	private static String _normalizeName(
			String name, DatabaseMetaData databaseMetaData)
		throws SQLException {

		if (databaseMetaData.storesLowerCaseIdentifiers()) {
			return name.toLowerCase();
		}

		if (databaseMetaData.storesUpperCaseIdentifiers()) {
			return name.toUpperCase();
		}

		return name;
	}

	private static Properties _readProperties(File file) {
		Properties properties = new Properties();

		if (file.exists()) {
			try {
				properties.load(new FileInputStream(file));
			}
			catch (IOException ioe) {
				System.out.println("Unable to load " + file);
			}
		}

		return properties;
	}

	private static String _readProperty(
			Properties properties, String propertyName)
		throws IOException {

		return _readProperty(properties, propertyName, null, null);
	}

	private static String _readProperty(
			Properties properties, String propertyName, Character mask,
			String message)
		throws IOException {

		String propertyValue = properties.getProperty(propertyName);

		if (propertyValue == null) {
			if (message == null) {
				message = "Enter a value for " + propertyName;
			}

			System.out.println(message);

			propertyValue = _consoleReader.readLine(mask);
		}

		return propertyValue;
	}

	private static String _readProperty(
			Properties properties, String propertyName, String message)
		throws IOException {

		return _readProperty(properties, propertyName, null, message);
	}

	private static void _runAction(String action) throws Exception {
		System.out.println(
			"Execution of " + action + " started at " + LocalDateTime.now());

		long start = System.currentTimeMillis();

		if (action.equals(_ACTIONS[0])) {
			_validateCompanyIds();
		}
		else if (action.equals(_ACTIONS[1])) {
			String input = _readProperty(
				_properties, "update.unique.indexes.tables",
				"Enter a list of tables separated by commas or an empty " +
					"value to analyze all tables");

			List<String> tableNames = _companyIdTableNames;

			if (!input.equals("")) {
				String[] inputArray = input.split(",");

				tableNames = new ArrayList<>();

				for (String tableName : inputArray) {
					tableName = tableName.trim();

					if (!_companyIdTableNames.contains(tableName)) {
						System.out.println(
							"Table " + tableName + " does not exist in the " +
								"database or does not contain the companyId " +
									"field");

						continue;
					}

					tableNames.add(
						_normalizeName(tableName, _databaseMetaData));
				}
			}

			_addCompanyIdToPrimaryKeys(tableNames);
			_addCompanyIdToUniqueIndexes(tableNames);
		}

		System.out.println(
			"Execution of " + action + " finished in " +
				(System.currentTimeMillis() - start) + " ms");
	}

	private static void _validateCompanyIds() throws Exception {
		boolean incorrectCompanyIds = false;

		String companyIdsClause = "(";

		for (long companyId : _companyIds) {
			companyIdsClause += companyId + ",";
		}

		companyIdsClause += "0)";

		for (String tableName : _companyIdTableNames) {
			try (PreparedStatement ps = _connection.prepareStatement(
					"select distinct(companyId) from " + tableName + " where " +
						"companyId not in " + companyIdsClause);
				ResultSet rs = ps.executeQuery()) {

				while (rs.next()) {
					System.out.println(
						"Table " + tableName + " has records with the " +
							"invalid companydId " + rs.getLong(1));

					incorrectCompanyIds = true;
				}
			}

			System.out.println(tableName + " analyzed");
		}

		if (incorrectCompanyIds) {
			throw new Exception(
				"Several tables contain records associated to a non-existent " +
					"companyId. Please fix it before partitioning.");
		}

		System.out.println(
			"All tables point to correct companyId values " + companyIdsClause);
	}

	private static final String[] _ACTIONS = {"validate", "updateIndexes"};

	private static final String _DB_DB2 = "db2";

	private static final String _DB_MARIADB = "mariadb";

	private static final String _DB_MYSQL = "mysql";

	private static final String _DB_SQLSERVER = "sqlserver";

	private static final String _DB_SYBASE = "sybase";

	private static final String _INDEX_PREFIX = "IX_";

	private static final String _JDBC_COLUMN_NAME = "COLUMN_NAME";

	private static final String _JDBC_INDEX_NAME = "INDEX_NAME";

	private static final String _JDBC_URL_REGEX = "jdbc:(.*):.*";

	private static final List<Long> _companyIds = new ArrayList<>();
	private static final List<String> _companyIdTableNames = new ArrayList<>();
	private static Connection _connection;
	private static ConsoleReader _consoleReader;
	private static DatabaseMetaData _databaseMetaData;
	private static String _dbType;
	private static final Pattern _jdbcUrlPattern = Pattern.compile(
		_JDBC_URL_REGEX);
	private static String _normalizedCompanyId;
	private static PrintStream _printStream;
	private static Properties _properties;

}