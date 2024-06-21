/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.db.schema.definition.internal.test.util;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBInspector;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.db.DBType;
import com.liferay.portal.kernel.dao.jdbc.DataSourceFactoryUtil;
import com.liferay.portal.kernel.instance.PortalInstancePool;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.FileUtil;
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

	public static List<String> getIndexColumns(DataSource dataSource)
		throws Exception {

		DB db = DBManagerUtil.getDB();

		List<String> indexColumns = new ArrayList<>();

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

					indexColumns.addAll(
						_getIndexColumns(connection, db, tableName, false));
				}
			}
		}

		Collections.sort(indexColumns);

		return indexColumns;
	}

	public static String getSchemaURL(String schemaName) {
		if (DBManagerUtil.getDBType() == DBType.MYSQL) {
			return _getMySQLSchemaURL(schemaName);
		}

		return _getPostgreSQLSchemaURL(schemaName);
	}

	public static List<String> getTableColumns(DataSource dataSource)
		throws Exception {

		List<String> tableColumns = new ArrayList<>();

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

					Object[] columnArray = {
						tableName, resultSet.getString("COLUMN_NAME"),
						resultSet.getInt("DATA_TYPE"),
						resultSet.getInt("COLUMN_SIZE"),
						resultSet.getInt("DECIMAL_DIGITS"),
						resultSet.getString("COLUMN_DEF"),
						resultSet.getString("IS_NULLABLE"),
						resultSet.getString("IS_AUTOINCREMENT")
					};

					tableColumns.add(
						ArrayUtil.toString(columnArray, (String)null));
				}
			}
		}

		Collections.sort(tableColumns);

		return tableColumns;
	}

	public static void importFileTo(File file, DataSource targetDataSource)
		throws Exception {

		DB db = DBManagerUtil.getDB(
			DBManagerUtil.getDBType(), targetDataSource);

		try (Connection connection = targetDataSource.getConnection()) {
			db.runSQLTemplate(connection, FileUtil.read(file), true);
		}
	}

	public static DataSource initSchemaDataSource(String schemaName)
		throws Exception {

		return DataSourceFactoryUtil.initDataSource(
			PropsValues.JDBC_DEFAULT_DRIVER_CLASS_NAME,
			getSchemaURL(schemaName), PropsValues.JDBC_DEFAULT_USERNAME,
			PropsValues.JDBC_DEFAULT_PASSWORD, StringPool.BLANK);
	}

	private static List<String> _getIndexColumns(
			Connection connection, DB db, String tableName, boolean unique)
		throws Exception {

		List<String> indexColumns = new ArrayList<>();

		try (ResultSet resultSet = db.getIndexResultSet(
				connection, tableName, unique)) {

			while (resultSet.next()) {
				Object[] tableIndexArray = {
					tableName, resultSet.getString("NON_UNIQUE"),
					resultSet.getString("INDEX_NAME"),
					resultSet.getString("COLUMN_NAME"),
					resultSet.getShort("ORDINAL_POSITION")
				};

				indexColumns.add(
					ArrayUtil.toString(tableIndexArray, (String)null));
			}
		}

		return indexColumns;
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

}