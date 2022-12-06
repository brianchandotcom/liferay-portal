/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.portal.dao.db;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.db.DBInspector;
import com.liferay.portal.kernel.dao.db.DBType;
import com.liferay.portal.kernel.dao.db.Index;
import com.liferay.portal.kernel.dao.db.IndexMetadata;
import com.liferay.portal.kernel.io.unsync.UnsyncBufferedReader;
import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.IOException;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Alexander Chow
 * @author Sandeep Soni
 * @author Ganesh Ram
 */
public class SQLServerDB extends BaseDB {

	public SQLServerDB(int majorVersion, int minorVersion) {
		super(DBType.SQLSERVER, majorVersion, minorVersion);
	}

	@Override
	public void alterColumnType(
			Connection connection, String tableName, String columnName,
			String newColumnType)
		throws Exception {

		List<IndexMetadata> indexMetadatas = dropIndexes(
			connection, tableName, columnName);

		String[] primaryKeyColumnNames = getPrimaryKeyColumnNames(
			connection, tableName);

		DBInspector dbInspector = new DBInspector(connection);

		boolean primaryKey = ArrayUtil.contains(
			primaryKeyColumnNames, dbInspector.normalizeName(columnName));

		if (primaryKey) {
			removePrimaryKey(connection, tableName);
		}

		super.alterColumnType(connection, tableName, columnName, newColumnType);

		if (primaryKey) {
			addPrimaryKey(connection, tableName, primaryKeyColumnNames);
		}

		if (!indexMetadatas.isEmpty()) {
			addIndexes(connection, indexMetadatas);
		}
	}

	@Override
	public void alterTableDropColumn(
			Connection connection, String tableName, String columnName)
		throws Exception {

		dropIndexes(connection, tableName, columnName);

		_dropDefaultConstraint(connection, tableName, columnName);

		super.alterTableDropColumn(connection, tableName, columnName);
	}

	@Override
	public String buildSQL(String template) throws IOException {
		template = replaceTemplate(template);

		template = reword(template);
		template = StringUtil.replace(template, "\ngo;\n", "\ngo\n");
		template = StringUtil.replace(
			template, new String[] {"\\\\", "\\'", "\\\"", "\\n", "\\r"},
			new String[] {"\\", "''", "\"", "\n", "\r"});

		return template;
	}

	@Override
	public List<Index> getIndexes(Connection connection) throws SQLException {
		List<Index> indexes = new ArrayList<>();

		DatabaseMetaData databaseMetaData = connection.getMetaData();

		if (databaseMetaData.getDatabaseMajorVersion() <= _SQL_SERVER_2000) {
			return indexes;
		}

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				StringBundler.concat(
					"select sys.tables.name as table_name, sys.indexes.name ",
					"as index_name, is_unique from sys.indexes inner join ",
					"sys.tables on sys.tables.object_id = ",
					"sys.indexes.object_id where sys.indexes.name like ",
					"'LIFERAY_%' or sys.indexes.name like 'IX_%'"));
			ResultSet resultSet = preparedStatement.executeQuery()) {

			while (resultSet.next()) {
				String indexName = resultSet.getString("index_name");
				String tableName = resultSet.getString("table_name");
				boolean unique = !resultSet.getBoolean("is_unique");

				indexes.add(new Index(indexName, tableName, unique));
			}
		}

		return indexes;
	}

	@Override
	public String getNewUuidFunctionName() {
		return "lower(NEWID())";
	}

	@Override
	public String getPopulateSQL(String databaseName, String sqlContent) {
		return StringBundler.concat("use ", databaseName, ";\n\n", sqlContent);
	}

	@Override
	public String getRecreateSQL(String databaseName) {
		return StringBundler.concat(
			"drop database ", databaseName, ";\n", "create database ",
			databaseName, ";\n\n", "go\n\n");
	}

	@Override
	public boolean isSupportsNewUuidFunction() {
		return _SUPPORTS_NEW_UUID_FUNCTION;
	}

	@Override
	public void removePrimaryKey(Connection connection, String tableName)
		throws Exception {

		DatabaseMetaData databaseMetaData = connection.getMetaData();

		DBInspector dbInspector = new DBInspector(connection);

		String normalizedTableName = dbInspector.normalizeName(
			tableName, databaseMetaData);

		if (!dbInspector.hasTable(normalizedTableName)) {
			throw new SQLException(
				StringBundler.concat(
					"Table ", normalizedTableName, " does not exist"));
		}

		String primaryKeyConstraintName = null;

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"select name from sys.key_constraints where type = 'PK' and " +
					"OBJECT_NAME(parent_object_id) = ?")) {

			preparedStatement.setString(1, normalizedTableName);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if (resultSet.next()) {
					primaryKeyConstraintName = resultSet.getString("name");
				}
			}
		}

		if (primaryKeyConstraintName == null) {
			throw new SQLException(
				"No primary key constraint found for " + normalizedTableName);
		}

		if (dbInspector.hasIndex(
				normalizedTableName, primaryKeyConstraintName)) {

			runSQL(
				StringBundler.concat(
					"alter table ", normalizedTableName, " drop constraint ",
					primaryKeyConstraintName));
		}
		else {
			throw new SQLException(
				StringBundler.concat(
					"Primary key with name ", primaryKeyConstraintName,
					" does not exist"));
		}
	}

	@Override
	protected int[] getSQLTypes() {
		return _SQL_TYPES;
	}

	@Override
	protected int[] getSQLVarcharSizes() {
		return _SQL_VARCHAR_SIZES;
	}

	@Override
	protected String[] getTemplate() {
		return _SQL_SERVER;
	}

	@Override
	protected String reword(String data) throws IOException {
		try (UnsyncBufferedReader unsyncBufferedReader =
				new UnsyncBufferedReader(new UnsyncStringReader(data))) {

			StringBundler sb = new StringBundler();

			String line = null;

			while ((line = unsyncBufferedReader.readLine()) != null) {
				if (line.startsWith(ALTER_COLUMN_NAME)) {
					String[] template = buildColumnNameTokens(line);

					line = StringUtil.replace(
						"exec sp_rename '@table@.@old-column@', " +
							"'@new-column@', 'column';",
						REWORD_TEMPLATE, template);
				}
				else if (line.startsWith(ALTER_COLUMN_TYPE)) {
					String[] template = buildColumnTypeTokens(line);

					line = StringUtil.replace(
						"alter table @table@ alter column @old-column@ " +
							"@type@ @nullable@;",
						REWORD_TEMPLATE, template);

					line = StringUtil.replace(line, " ;", ";");
				}
				else if (line.startsWith(ALTER_TABLE_NAME)) {
					String[] template = buildTableNameTokens(line);

					line = StringUtil.replace(
						"exec sp_rename '@old-table@', '@new-table@';",
						RENAME_TABLE_TEMPLATE, template);
				}
				else if (line.contains(DROP_INDEX)) {
					String[] tokens = StringUtil.split(line, ' ');

					String tableName = tokens[4];

					if (tableName.endsWith(StringPool.SEMICOLON)) {
						tableName = tableName.substring(
							0, tableName.length() - 1);
					}

					line = StringUtil.replace(
						"drop index @table@.@index@;", "@table@", tableName);
					line = StringUtil.replace(line, "@index@", tokens[2]);
				}

				sb.append(line);
				sb.append("\n");
			}

			return sb.toString();
		}
	}

	private void _dropDefaultConstraint(
			Connection connection, String tableName, String columnName)
		throws Exception {

		String defaultConstraintName = null;

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				StringBundler.concat(
					"select default_constraints.name from sys.",
					"default_constraints inner join sys.tables on ",
					"default_constraints.parent_object_id = tables.object_id ",
					"inner join sys.columns on default_constraints.",
					"parent_object_id = columns.object_id and ",
					"default_constraints.parent_column_id = columns.column_id ",
					"where tables.name = ? and columns.name = ?"))) {

			preparedStatement.setString(1, tableName);
			preparedStatement.setString(2, columnName);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if (resultSet.next()) {
					defaultConstraintName = resultSet.getString("name");
				}
			}
		}

		if (Validator.isNull(defaultConstraintName)) {
			return;
		}

		runSQL(
			StringBundler.concat(
				"alter table ", tableName, " drop constraint ",
				defaultConstraintName));
	}

	private static final String[] _SQL_SERVER = {
		"--", "1", "0", "'19700101'", "GetDate()", " image", " image", " bit",
		" datetime2(6)", " float", " int", " bigint", " nvarchar(4000)",
		" nvarchar(max)", " nvarchar", "  identity(1,1)", "go"
	};

	private static final int _SQL_SERVER_2000 = 8;

	private static final int _SQL_STRING_SIZE = 4000;

	private static final int[] _SQL_TYPES = {
		Types.LONGVARBINARY, Types.LONGVARBINARY, Types.BIT, Types.TIMESTAMP,
		Types.DOUBLE, Types.INTEGER, Types.BIGINT, Types.NVARCHAR,
		Types.NVARCHAR, Types.NVARCHAR
	};

	private static final int[] _SQL_VARCHAR_SIZES = {
		_SQL_STRING_SIZE, SQL_VARCHAR_MAX_SIZE
	};

	private static final boolean _SUPPORTS_NEW_UUID_FUNCTION = true;

}