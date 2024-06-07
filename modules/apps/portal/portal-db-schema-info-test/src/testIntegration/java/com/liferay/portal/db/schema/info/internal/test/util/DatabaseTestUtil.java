/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.db.schema.info.internal.test.util;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBInspector;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.db.DBType;
import com.liferay.portal.kernel.dao.jdbc.DataSourceFactoryUtil;
import com.liferay.portal.kernel.instance.PortalInstancePool;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.InfrastructureUtil;
import com.liferay.portal.util.PropsValues;

import java.io.File;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.sql.DataSource;

/**
 * @author Mariano Álvaro Sáiz
 */
public class DatabaseTestUtil {

	public static void createSchema(String schemaName) throws Exception {
		DB db = DBManagerUtil.getDB();

		if (DBManagerUtil.getDBType() == DBType.MYSQL) {
			db.runSQL("create schema " + schemaName + " character set utf8");
		}
		else {
			db.runSQL("create schema " + schemaName);
		}
	}

	public static void destroyDataSource(DataSource dataSource)
		throws Exception {

		DataSourceFactoryUtil.destroyDataSource(dataSource);
	}

	public static void dropSchema(String schemaName) throws Exception {
		DB db = DBManagerUtil.getDB();

		if (DBManagerUtil.getDBType() == DBType.MYSQL) {
			db.runSQL("drop schema " + schemaName);
		}
		else {
			db.runSQL("drop schema " + schemaName + " cascade");
		}
	}

	public static String getSchemaURL(String schemaName) {
		if (DBManagerUtil.getDBType() == DBType.MYSQL) {
			return _getMySQLSchemaURL(schemaName);
		}

		return _getPostgreSQLSchemaURL(schemaName);
	}

	public static List<String> getSourceIndexes() throws Exception {
		return _getIndexes(InfrastructureUtil.getDataSource());
	}

	public static List<String> getSourceTables() throws Exception {
		return _getTables(InfrastructureUtil.getDataSource());
	}

	public static List<String> getTargetIndexes(DataSource targetDataSource)
		throws Exception {

		return _getIndexes(targetDataSource);
	}

	public static List<String> getTargetTables(DataSource targetDataSource)
		throws Exception {

		return _getTables(targetDataSource);
	}

	public static void importFileTo(File file, DataSource targetDataSource)
		throws Exception {

		DB db = DBManagerUtil.getDB(
			DBManagerUtil.getDBType(), targetDataSource);

		try (Connection connection = targetDataSource.getConnection()) {
			db.runSQLTemplateString(connection, FileUtil.read(file), true);
		}
	}

	public static DataSource initSchemaDataSource(String schemaName)
		throws Exception {

		return DataSourceFactoryUtil.initDataSource(
			PropsValues.JDBC_DEFAULT_DRIVER_CLASS_NAME,
			getSchemaURL(schemaName), PropsValues.JDBC_DEFAULT_USERNAME,
			PropsValues.JDBC_DEFAULT_PASSWORD, StringPool.BLANK);
	}

	private static List<String> _getIndexes(DataSource dataSource)
		throws Exception {

		DB db = DBManagerUtil.getDB();

		List<String> indexes = new ArrayList<>();

		try (Connection connection = dataSource.getConnection()) {
			DatabaseMetaData databaseMetaData = connection.getMetaData();

			DBInspector dbInspector = new DBInspector(connection);

			try (ResultSet resultSet = databaseMetaData.getTables(
					connection.getCatalog(), connection.getSchema(), null,
					new String[] {"TABLE"})) {

				while (resultSet.next()) {
					String tableName = resultSet.getString("TABLE_NAME");

					if (dbInspector.isObjectTable(
							Collections.singletonList(
								PortalInstancePool.getDefaultCompanyId()),
							tableName)) {

						continue;
					}

					indexes.addAll(
						_getTableIndexes(connection, db, tableName, false));
					indexes.addAll(
						_getTableIndexes(connection, db, tableName, true));
				}
			}
		}

		Collections.sort(indexes);

		return indexes;
	}

	private static String _getMySQLSchemaURL(String schemaName) {
		String jdbcURL = PropsValues.JDBC_DEFAULT_URL;

		int index = jdbcURL.indexOf("?");

		if (index == -1) {
			return jdbcURL.substring(0, jdbcURL.lastIndexOf("/") + 1) +
				schemaName;
		}

		String baseJDBCURL = jdbcURL.substring(0, index);

		return StringBundler.concat(
			jdbcURL.substring(0, baseJDBCURL.lastIndexOf("/") + 1), schemaName,
			jdbcURL.substring(index));
	}

	private static String _getPostgreSQLSchemaURL(String schemaName) {
		String jdbcURL = PropsValues.JDBC_DEFAULT_URL;

		int index = jdbcURL.indexOf("?");

		if (index == -1) {
			return jdbcURL + "?currentSchema=" + schemaName;
		}

		return jdbcURL + "&currentSchema=" + schemaName;
	}

	private static List<String> _getTableIndexes(
			Connection connection, DB db, String tableName, boolean unique)
		throws Exception {

		List<String> indexes = new ArrayList<>();

		try (ResultSet resultSet = db.getIndexResultSet(
				connection, tableName, unique)) {

			while (resultSet.next()) {
				indexes.add(
					StringBundler.concat(
						"Table Name: ", tableName, "Non Unique: ", unique,
						"Index Name: ", resultSet.getString("INDEX_NAME"),
						"Ordinal Position: ",
						resultSet.getShort("ORDINAL_POSITION"), "Column Name: ",
						resultSet.getString("COLUMN_NAME")));
			}
		}

		return indexes;
	}

	private static List<String> _getTables(DataSource dataSource)
		throws Exception {

		List<String> columns = new ArrayList<>();

		try (Connection connection = dataSource.getConnection()) {
			DatabaseMetaData databaseMetaData = connection.getMetaData();

			DBInspector dbInspector = new DBInspector(connection);

			try (ResultSet resultSet = databaseMetaData.getColumns(
					connection.getCatalog(), connection.getSchema(), null,
					null)) {

				while (resultSet.next()) {
					String tableName = resultSet.getString("TABLE_NAME");

					if (dbInspector.isObjectTable(
							Collections.singletonList(
								PortalInstancePool.getDefaultCompanyId()),
							tableName)) {

						continue;
					}

					columns.add(
						StringBundler.concat(
							"Table Name: ", tableName, "Column Name: ",
							resultSet.getString("COLUMN_NAME"), "Data Type: ",
							resultSet.getInt("DATA_TYPE"), "Column Size: ",
							resultSet.getInt("COLUMN_SIZE"), "Decimal Digits: ",
							resultSet.getInt("DECIMAL_DIGITS"), "Is Nullable: ",
							resultSet.getString("IS_NULLABLE"),
							"Is AutoIncrement: ",
							resultSet.getString("IS_AUTOINCREMENT")));
				}
			}
		}

		Collections.sort(columns);

		return columns;
	}

}