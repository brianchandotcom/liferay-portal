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

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
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
import java.sql.SQLException;

import java.util.Objects;
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
		try {
			return _connection.getSchema();
		}
		catch (Throwable throwable) {
			if (_log.isDebugEnabled()) {
				_log.debug(throwable, throwable);
			}

			return null;
		}
	}

	public boolean hasColumn(String tableName, String columnName)
		throws Exception {

		DatabaseMetaData databaseMetaData = _connection.getMetaData();

		try (ResultSet resultSet = databaseMetaData.getColumns(
				getCatalog(), getSchema(), normalizeName(tableName),
				normalizeName(columnName))) {

			if (!resultSet.next()) {
				return false;
			}

			return true;
		}
		catch (Exception exception) {
			_log.error(exception, exception);
		}

		return false;
	}

	/**
	 * @deprecated As of Mueller (7.2.x), replaced by {@link
	 *             #hasColumnType(String, String, String)}
	 */
	@Deprecated
	public boolean hasColumnType(
			Class<?> tableClass, String columnName, String columnType)
		throws Exception {

		Field tableNameField = tableClass.getField("TABLE_NAME");

		return hasColumnType(
			(String)tableNameField.get(null), columnName, columnType);
	}

	public boolean hasColumnType(
			String tableName, String columnName, String columnType)
		throws Exception {

		DatabaseMetaData databaseMetaData = _connection.getMetaData();

		try (ResultSet resultSet = databaseMetaData.getColumns(
				getCatalog(), getSchema(),
				normalizeName(tableName, databaseMetaData),
				normalizeName(columnName, databaseMetaData))) {

			if (!resultSet.next()) {
				return false;
			}

			int expectedColumnSize = _getColumnSize(columnType);

			int actualColumnSize = resultSet.getInt("COLUMN_SIZE");

			if ((expectedColumnSize != -1) &&
				(expectedColumnSize != actualColumnSize)) {

				return false;
			}

			Integer expectedColumnDataType = _getColumnDataType(columnType);

			int actualColumnDataType = resultSet.getInt("DATA_TYPE");

			if ((expectedColumnDataType == null) ||
				(expectedColumnDataType != actualColumnDataType)) {

				return false;
			}

			boolean expectedColumnNullable = _isColumnNullable(columnType);

			int actualColumnNullable = resultSet.getInt("NULLABLE");

			if ((expectedColumnNullable &&
				 (actualColumnNullable != DatabaseMetaData.columnNullable)) ||
				(!expectedColumnNullable &&
				 (actualColumnNullable != DatabaseMetaData.columnNoNulls))) {

				return false;
			}

			return true;
		}
	}

	public boolean hasIndex(String tableName, String indexName)
		throws Exception {

		DatabaseMetaData databaseMetaData = _connection.getMetaData();

		DB db = DBManagerUtil.getDB();

		try (ResultSet resultSet = db.getIndexResultSet(
				_connection, normalizeName(tableName, databaseMetaData))) {

			while (resultSet.next()) {
				if (Objects.equals(
						normalizeName(indexName, databaseMetaData),
						resultSet.getString("index_name"))) {

					return true;
				}
			}
		}
		catch (Exception exception) {
			_log.error(exception, exception);
		}

		return false;
	}

	public boolean hasRows(String tableName) {
		try (PreparedStatement preparedStatement = _connection.prepareStatement(
				"select count(*) from " + tableName);
			ResultSet resultSet = preparedStatement.executeQuery()) {

			while (resultSet.next()) {
				int count = resultSet.getInt(1);

				if (count > 0) {
					return true;
				}
			}
		}
		catch (Exception exception) {
			_log.error(exception, exception);
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

		DatabaseMetaData databaseMetaData = _connection.getMetaData();

		if (_hasTable(normalizeName(tableName, databaseMetaData))) {
			return true;
		}

		return false;
	}

	public boolean isNullable(String tableName, String columnName)
		throws SQLException {

		DatabaseMetaData databaseMetaData = _connection.getMetaData();

		try (ResultSet resultSet = databaseMetaData.getColumns(
				getCatalog(), getSchema(),
				normalizeName(tableName, databaseMetaData),
				normalizeName(columnName, databaseMetaData))) {

			if (!resultSet.next()) {
				throw new SQLException(
					StringBundler.concat(
						"Column ", tableName, StringPool.PERIOD, columnName,
						" does not exist"));
			}

			if (resultSet.getInt("NULLABLE") ==
					DatabaseMetaData.columnNullable) {

				return true;
			}

			return false;
		}
	}

	public String normalizeName(String name) throws SQLException {
		return normalizeName(name, _connection.getMetaData());
	}

	public String normalizeName(String name, DatabaseMetaData databaseMetaData)
		throws SQLException {

		if (databaseMetaData.storesLowerCaseIdentifiers()) {
			return StringUtil.toLowerCase(name);
		}

		if (databaseMetaData.storesUpperCaseIdentifiers()) {
			return StringUtil.toUpperCase(name);
		}

		return name;
	}

	private Integer _getColumnDataType(String columnType) {
		Matcher matcher = _columnTypePattern.matcher(columnType);

		if (!matcher.lookingAt()) {
			return null;
		}

		DB db = DBManagerUtil.getDB();

		return db.getSQLType(matcher.group(1));
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
			catch (NumberFormatException numberFormatException) {
				throw new UpgradeException(
					StringBundler.concat(
						"Column type ", columnType,
						" has an invalid column size ", columnSize),
					numberFormatException);
			}
		}

		return -1;
	}

	private boolean _hasTable(String tableName) throws Exception {
		DatabaseMetaData metadata = _connection.getMetaData();

		try (ResultSet resultSet = metadata.getTables(
				getCatalog(), getSchema(), tableName, null)) {

			while (resultSet.next()) {
				return true;
			}
		}

		return false;
	}

	private boolean _isColumnNullable(String typeName) {
		typeName = typeName.trim();

		typeName = StringUtil.toLowerCase(typeName);

		if (typeName.endsWith("not null")) {
			return false;
		}

		return true;
	}

	private static final Log _log = LogFactoryUtil.getLog(DBInspector.class);

	private static final Pattern _columnSizePattern = Pattern.compile(
		"^\\w+(?:\\((\\d+)\\))?.*", Pattern.CASE_INSENSITIVE);
	private static final Pattern _columnTypePattern = Pattern.compile(
		"(^\\w+)", Pattern.CASE_INSENSITIVE);

	private final Connection _connection;

}