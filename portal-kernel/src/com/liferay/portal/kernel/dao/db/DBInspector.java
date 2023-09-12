/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.dao.db;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Adolfo Pérez
 */
public class DBInspector {

	public static boolean isObjectTable(
		List<Long> companyIds, String tableName) {

		for (long companyId : companyIds) {

			// See ObjectDefinitionImpl#getExtensionDBTableName and
			// ObjectDefinitionLocalServiceImpl#_getDBTableName

			if (tableName.endsWith("_x_" + companyId) ||
				tableName.startsWith("L_" + companyId + "_") ||
				tableName.startsWith("O_" + companyId + "_")) {

				return true;
			}
		}

		return false;
	}

	public DBInspector(Connection connection) {
		_connection = connection;
	}

	public String getCatalog() throws SQLException {
		return _connection.getCatalog();
	}

	public ResultSet getColumnsResultSet(String tableName) throws SQLException {
		return _getColumnsResultSet(tableName, null);
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

	public List<String> getTableNames(String tableNamePattern)
		throws SQLException {

		List<String> tableNames = new ArrayList<>();

		DatabaseMetaData databaseMetaData = _connection.getMetaData();

		try (ResultSet resultSet = databaseMetaData.getTables(
				_connection.getCatalog(), _connection.getSchema(),
				tableNamePattern, new String[] {"TABLE"})) {

			while (resultSet.next()) {
				tableNames.add(resultSet.getString("TABLE_NAME"));
			}
		}

		return tableNames;
	}

	public boolean hasColumn(String tableName, String columnName)
		throws Exception {

		try (ResultSet resultSet = _getColumnsResultSet(
				tableName, columnName)) {

			if (!resultSet.next()) {
				return false;
			}

			return true;
		}
		catch (Exception exception) {
			_log.error(exception);
		}

		return false;
	}

	public boolean hasColumnType(
			String tableName, String columnName, String columnType)
		throws Exception {

		try (ResultSet resultSet = _getColumnsResultSet(
				tableName, columnName)) {

			if (!resultSet.next()) {
				return false;
			}

			int expectedColumnSize = _getColumnSize(columnType);

			int actualColumnSize = resultSet.getInt("COLUMN_SIZE");

			if ((expectedColumnSize != DB.SQL_SIZE_NONE) &&
				(((expectedColumnSize != DB.SQL_VARCHAR_MAX_SIZE) &&
				  (expectedColumnSize != actualColumnSize)) ||
				 ((expectedColumnSize == DB.SQL_VARCHAR_MAX_SIZE) &&
				  (actualColumnSize < DB.SQL_VARCHAR_MAX_SIZE_THRESHOLD)))) {

				return false;
			}

			Integer expectedColumnDataType = _getByColumnType(
				columnType, DB::getSQLType);

			int actualColumnDataType = resultSet.getInt("DATA_TYPE");

			if ((expectedColumnDataType == null) ||
				(expectedColumnDataType != actualColumnDataType)) {

				return false;
			}

			Integer expectedColumnDecimalDigits = _getByColumnType(
				columnType, DB::getSQLTypeDecimalDigits);

			if (expectedColumnDecimalDigits != DB.SQL_SIZE_NONE) {
				int actualColumnDecimalDigits = resultSet.getInt(
					"DECIMAL_DIGITS");

				if (expectedColumnDecimalDigits != actualColumnDecimalDigits) {
					return false;
				}
			}

			boolean expectedColumnNullable = _isColumnNullable(columnType);

			int actualColumnNullable = resultSet.getInt("NULLABLE");

			if ((expectedColumnNullable &&
				 (actualColumnNullable != DatabaseMetaData.columnNullable)) ||
				(!expectedColumnNullable &&
				 (actualColumnNullable != DatabaseMetaData.columnNoNulls))) {

				return false;
			}

			if (!expectedColumnNullable) {
				String expectedColumnDefaultValue = _getColumnDefaultValue(
					columnType);
				String actualColumnDefaultValue = _getColumnDefaultValue(
					resultSet.getString("COLUMN_DEF"), DB::getDefaultValue);

				if (Validator.isNull(expectedColumnDefaultValue) &&
					Validator.isNull(actualColumnDefaultValue)) {

					return true;
				}

				return StringUtil.equals(
					expectedColumnDefaultValue, actualColumnDefaultValue);
			}

			return true;
		}
	}

	public boolean hasIndex(String tableName, String indexName)
		throws Exception {

		DB db = DBManagerUtil.getDB();
		DatabaseMetaData databaseMetaData = _connection.getMetaData();

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
			_log.error(exception);
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
			_log.error(exception);
		}

		return false;
	}

	public boolean hasTable(String tableName) throws Exception {
		return hasTable(tableName, false);
	}

	public boolean hasTable(String tableName, boolean caseSensitive)
		throws Exception {

		DatabaseMetaData databaseMetaData = _connection.getMetaData();

		if (!caseSensitive) {
			tableName = normalizeName(tableName, databaseMetaData);
		}

		try (ResultSet resultSet = databaseMetaData.getTables(
				getCatalog(), getSchema(), tableName, new String[] {"TABLE"})) {

			while (resultSet.next()) {
				return true;
			}
		}

		return false;
	}

	public boolean isControlTable(List<Long> companyIds, String tableName)
		throws Exception {

		if (!isPartitionedControlTable(tableName) &&
			!isObjectTable(companyIds, tableName) &&
			(_controlTableNames.contains(StringUtil.toLowerCase(tableName)) ||
			 !hasColumn(tableName, "companyId"))) {

			return true;
		}

		return false;
	}

	public boolean isNullable(String tableName, String columnName)
		throws SQLException {

		try (ResultSet resultSet = _getColumnsResultSet(
				tableName, columnName)) {

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

	public boolean isPartitionedControlTable(String tableName) {
		if (_partitionedControlTableNames.contains(
				StringUtil.toLowerCase(tableName))) {

			return true;
		}

		return false;
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

	private Integer _getByColumnType(
		String columnType, BiFunction<DB, String, Integer> biFunction) {

		Matcher matcher = _columnTypePattern.matcher(columnType);

		if (!matcher.lookingAt()) {
			return null;
		}

		return biFunction.apply(DBManagerUtil.getDB(), matcher.group(1));
	}

	private String _getColumnDefaultValue(String columnType) {
		Matcher matcher = _columnDefaultClausePattern.matcher(columnType);

		if (matcher.find()) {
			return StringUtil.unquote(matcher.group(1));
		}

		return null;
	}

	private String _getColumnDefaultValue(
		String columnDef, BiFunction<DB, String, String> biFunction) {

		if (Validator.isNull(columnDef)) {
			return columnDef;
		}

		return biFunction.apply(DBManagerUtil.getDB(), columnDef);
	}

	private int _getColumnSize(String columnType) throws Exception {
		Matcher matcher = _columnSizePattern.matcher(columnType);

		if (!matcher.matches()) {
			return DB.SQL_SIZE_NONE;
		}

		String columnSize = matcher.group(1);

		if (Validator.isNotNull(columnSize)) {
			try {
				return Integer.parseInt(columnSize);
			}
			catch (NumberFormatException numberFormatException) {
				throw new Exception(
					StringBundler.concat(
						"Column type ", columnType,
						" has an invalid column size ", columnSize),
					numberFormatException);
			}
		}

		Integer dataTypeSize = _getByColumnType(columnType, DB::getSQLTypeSize);

		if (dataTypeSize != null) {
			return dataTypeSize;
		}

		return DB.SQL_SIZE_NONE;
	}

	private ResultSet _getColumnsResultSet(String tableName, String columnName)
		throws SQLException {

		DatabaseMetaData databaseMetaData = _connection.getMetaData();

		if (columnName != null) {
			columnName = normalizeName(columnName, databaseMetaData);
		}

		return databaseMetaData.getColumns(
			getCatalog(), getSchema(),
			normalizeName(tableName, databaseMetaData), columnName);
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

	private static final Pattern _columnDefaultClausePattern = Pattern.compile(
		".*DEFAULT ((?:'[^']+')|(?:\\S+)) NOT NULL", Pattern.CASE_INSENSITIVE);
	private static final Pattern _columnSizePattern = Pattern.compile(
		"^\\w+(?:\\((\\d+)\\))?.*", Pattern.CASE_INSENSITIVE);
	private static final Pattern _columnTypePattern = Pattern.compile(
		"(^\\w+)", Pattern.CASE_INSENSITIVE);
	private static final Set<String> _controlTableNames = new HashSet<>(
		Arrays.asList("company", "virtualhost"));
	private static final Set<String> _partitionedControlTableNames =
		new HashSet<>(Arrays.asList("classname_"));

	private final Connection _connection;

}