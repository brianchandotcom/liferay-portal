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

package com.liferay.portal.kernel.dao.db;

import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.UpgradeException;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.lang.reflect.Field;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Adolfo Pérez
 */
public class DBInspector {

	public DBInspector(Connection connection) {
		_connection = connection;
	}

	public String getCatalog() throws SQLException {
		return _connection.getCatalog();
	}

	public String getSchema() {
		String schema = null;

		try {
			schema = _connection.getSchema();
		}
		catch (Throwable t) {
		}

		return schema;
	}

	public boolean hasColumn(String tableName, String columnName)
		throws Exception {

		try (PreparedStatement ps = _connection.prepareStatement(
				"select * from " + tableName);
			ResultSet rs = ps.executeQuery()) {

			ResultSetMetaData rsmd = rs.getMetaData();

			for (int i = 0; i < rsmd.getColumnCount(); i++) {
				String curColumnName = rsmd.getColumnName(i + 1);

				if (StringUtil.equalsIgnoreCase(curColumnName, columnName)) {
					return true;
				}
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return false;
	}

	public boolean hasColumnType(
			Class<?> tableClass, String columnName, String columnType)
		throws Exception {

		Field tableNameField = tableClass.getField("TABLE_NAME");

		String tableName = (String)tableNameField.get(null);

		DatabaseMetaData databaseMetaData = _connection.getMetaData();

		try (ResultSet rs = databaseMetaData.getColumns(
				getCatalog(), getSchema(), tableName, columnName)) {

			if (!rs.next()) {
				return false;
			}

			int expectedColumnDataType = _getColumnDataType(
				tableClass, columnName);
			int expectedColumnSize = _getColumnSize(columnType);
			boolean expectedColumnNullable = _isColumnNullable(columnType);

			int actualColumnDataType = rs.getInt("DATA_TYPE");
			int actualColumnSize = rs.getInt("COLUMN_SIZE");
			int actualColumnNullable = rs.getInt("NULLABLE");

			if ((expectedColumnSize != -1) &&
				(expectedColumnSize != actualColumnSize)) {

				return false;
			}

			if (actualColumnDataType != expectedColumnDataType) {
				return false;
			}

			if ((expectedColumnNullable &&
				 (actualColumnNullable != DatabaseMetaData.columnNullable)) ||
				(!expectedColumnNullable &&
				 (actualColumnNullable != DatabaseMetaData.columnNoNulls))) {

				return false;
			}

			return true;
		}
	}

	public boolean hasRows(String tableName) {
		try (PreparedStatement ps = _connection.prepareStatement(
				"select count(*) from " + tableName);
			ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				int count = rs.getInt(1);

				if (count > 0) {
					return true;
				}
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return false;
	}

	public boolean hasTable(String tableName) throws Exception {
		return hasTable(tableName, false);
	}

	public boolean hasTable(String tableName, boolean caseSensitive)
		throws Exception {

		if (caseSensitive) {
			if (_hasTable(tableName)) {
				return true;
			}

			return false;
		}

		if (_hasTable(StringUtil.toLowerCase(tableName)) ||
			_hasTable(StringUtil.toUpperCase(tableName)) ||
			_hasTable(tableName)) {

			return true;
		}

		return false;
	}

	private int _getColumnDataType(Class<?> tableClass, String columnName)
		throws Exception {

		Field tableColumnsField = tableClass.getField("TABLE_COLUMNS");

		Object[][] tableColumns = (Object[][])tableColumnsField.get(null);

		for (Object[] tableColumn : tableColumns) {
			if (tableColumn[0].equals(columnName)) {
				return (int)tableColumn[1];
			}
		}

		throw new UpgradeException(
			"Table class " + tableClass + " does not have column " +
				columnName);
	}

	private int _getColumnSize(String columnType) throws UpgradeException {
		Matcher matcher = _columnSizePattern.matcher(columnType);

		if (!matcher.matches()) {
			return -1;
		}

		String columnSize = matcher.group(1);

		if (Validator.isNotNull(columnSize)) {
			try {
				return Integer.parseInt(columnSize);
			}
			catch (NumberFormatException nfe) {
				throw new UpgradeException(
					"Column type " + columnType +
						" has an invalid column size " + columnSize,
					nfe);
			}
		}

		return -1;
	}

	private boolean _hasTable(String tableName) throws Exception {
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			DatabaseMetaData metadata = _connection.getMetaData();

			rs = metadata.getTables(getCatalog(), getSchema(), tableName, null);

			while (rs.next()) {
				return true;
			}
		}
		finally {
			DataAccess.cleanUp(ps, rs);
		}

		return false;
	}

	private boolean _isColumnNullable(String typeName) {
		typeName = typeName.trim();

		int i = typeName.indexOf("null");

		if (i == -1) {
			return false;
		}

		if ((i > 0) && !Character.isSpaceChar(typeName.charAt(i - 1))) {
			return false;
		}

		if ((i + 4) < typeName.length()) {
			return false;
		}

		return true;
	}

	private static final Log _log = LogFactoryUtil.getLog(DBInspector.class);

	private static final Pattern _columnSizePattern = Pattern.compile(
		"^\\w+(?:\\((\\d+)\\))?.*", Pattern.CASE_INSENSITIVE);

	private final Connection _connection;

}