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

package com.liferay.portal.dao.db;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.db.DBType;
import com.liferay.portal.kernel.dao.db.Index;
import com.liferay.portal.kernel.dao.db.IndexMetadata;
import com.liferay.portal.kernel.io.unsync.UnsyncBufferedReader;
import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PropsValues;

import java.io.IOException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

/**
 * @author Alexander Chow
 * @author Sandeep Soni
 * @author Ganesh Ram
 */
public class MySQLDB extends BaseDB {

	public MySQLDB(int majorVersion, int minorVersion) {
		super(DBType.MYSQL, majorVersion, minorVersion);
	}

	@Override
	public void alterColumnType(
			Connection connection, String tableName, String columnName,
			String newColumnType)
		throws Exception {

		List<IndexMetadata> indexMetadatas = new ArrayList<>();

		Matcher matcher = columnTypePattern.matcher(newColumnType);

		if (matcher.lookingAt() &&
			ArrayUtil.contains(
				SQL_VARCHAR_TYPES, getSQLType(matcher.group(1)))) {

			indexMetadatas = dropIndexes(connection, tableName, columnName);
		}

		super.alterColumnType(connection, tableName, columnName, newColumnType);

		if (!indexMetadatas.isEmpty()) {
			addIndexes(connection, indexMetadatas);
		}
	}

	@Override
	public String buildSQL(String template) throws IOException {
		template = replaceTemplate(template);

		template = reword(template);
		template = StringUtil.replace(template, "\\'", "''");

		return template;
	}

	@Override
	public List<Index> getIndexes(Connection connection) throws SQLException {
		List<Index> indexes = new ArrayList<>();

		String sql = StringBundler.concat(
			"select distinct(index_name), table_name, non_unique from ",
			"information_schema.statistics where index_schema = database() ",
			"and (index_name like 'LIFERAY_%' or index_name like 'IX_%')");

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				sql);
			ResultSet resultSet = preparedStatement.executeQuery()) {

			while (resultSet.next()) {
				String indexName = resultSet.getString("index_name");
				String tableName = resultSet.getString("table_name");
				boolean unique = !resultSet.getBoolean("non_unique");

				indexes.add(new Index(indexName, tableName, unique));
			}
		}

		return indexes;
	}

	@Override
	public String getNewUuidFunctionName() {
		return "UUID()";
	}

	@Override
	public String getPopulateSQL(String databaseName, String sqlContent) {
		return StringBundler.concat("use ", databaseName, ";\n\n", sqlContent);
	}

	@Override
	public String getRecreateSQL(String databaseName) {
		return StringBundler.concat(
			"drop database if exists ", databaseName, ";\n", "create database ",
			databaseName, " character set utf8;\n");
	}

	@Override
	public boolean isSupportsNewUuidFunction() {
		return _SUPPORTS_NEW_UUID_FUNCTION;
	}

	@Override
	public boolean isSupportsUpdateWithInnerJoin() {
		return _SUPPORTS_UPDATE_WITH_INNER_JOIN;
	}

	protected MySQLDB(DBType dbType, int majorVersion, int minorVersion) {
		super(dbType, majorVersion, minorVersion);
	}

	@Override
	protected int[] getSQLTypes() {
		return _SQL_TYPES;
	}

	@Override
	protected String[] getTemplate() {
		return _MYSQL;
	}

	@Override
	protected String reword(String data) throws IOException {
		try (UnsyncBufferedReader unsyncBufferedReader =
				new UnsyncBufferedReader(new UnsyncStringReader(data))) {

			StringBundler sb = new StringBundler();

			boolean createTable = false;

			String line = null;

			while ((line = unsyncBufferedReader.readLine()) != null) {
				if (StringUtil.startsWith(line, "create table")) {
					createTable = true;
				}
				else if (line.startsWith(ALTER_COLUMN_NAME)) {
					String[] template = buildColumnNameTokens(line);

					line = StringUtil.replace(
						"alter table @table@ change column @old-column@ " +
							"@new-column@ @type@;",
						REWORD_TEMPLATE, template);
				}
				else if (line.startsWith(ALTER_COLUMN_TYPE)) {
					String[] template = buildColumnTypeTokens(line);

					String nullable = template[template.length - 1];

					if (Validator.isBlank(nullable)) {
						line = StringUtil.replace(
							"alter table @table@ modify @old-column@ @type@;",
							REWORD_TEMPLATE, template);
					}
					else {
						line = StringUtil.replace(
							"alter table @table@ modify @old-column@ @type@ " +
								"@nullable@;",
							REWORD_TEMPLATE, template);
					}
				}
				else if (line.startsWith(ALTER_TABLE_NAME)) {
					String[] template = buildTableNameTokens(line);

					line = StringUtil.replace(
						"rename table @old-table@ to @new-table@;",
						RENAME_TABLE_TEMPLATE, template);
				}

				int pos = line.indexOf(CharPool.SEMICOLON);

				if (createTable && (pos != -1)) {
					createTable = false;

					line = StringBundler.concat(
						line.substring(0, pos), " engine ",
						PropsValues.DATABASE_MYSQL_ENGINE, line.substring(pos));
				}

				sb.append(line);
				sb.append("\n");
			}

			return sb.toString();
		}
	}

	private static final String[] _MYSQL = {
		"##", "1", "0", "'1970-01-01'", "now()", " longblob", " longblob",
		" tinyint", " datetime(6)", " double", " integer", " bigint",
		" longtext", " longtext", " varchar", "  auto_increment", "commit"
	};

	private static final int[] _SQL_TYPES = {
		Types.LONGVARBINARY, Types.LONGVARBINARY, Types.TINYINT,
		Types.TIMESTAMP, Types.DOUBLE, Types.INTEGER, Types.BIGINT,
		Types.LONGVARCHAR, Types.LONGVARCHAR, Types.VARCHAR
	};

	private static final boolean _SUPPORTS_NEW_UUID_FUNCTION = true;

	private static final boolean _SUPPORTS_UPDATE_WITH_INNER_JOIN = true;

}