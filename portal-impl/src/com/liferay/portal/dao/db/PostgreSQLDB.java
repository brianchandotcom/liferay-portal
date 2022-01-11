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

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.db.DBType;
import com.liferay.portal.kernel.dao.db.Index;
import com.liferay.portal.kernel.io.unsync.UnsyncBufferedReader;
import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.IOException;

import java.sql.Connection;
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
public class PostgreSQLDB extends BaseDB {

	public static String getCreateRulesSQL(
		String tableName, String columnName) {

		return StringBundler.concat(
			"create or replace rule delete_", tableName, StringPool.UNDERLINE,
			columnName, " as on delete to ", tableName,
			" do also select case when exists(select 1 from ",
			"pg_catalog.pg_largeobject_metadata where (oid = old.", columnName,
			")) then lo_unlink(old.", columnName, ") end from ", tableName,
			" where ", tableName, StringPool.PERIOD, columnName, " = old.",
			columnName, ";\ncreate or replace rule update_", tableName,
			StringPool.UNDERLINE, columnName, " as on update to ", tableName,
			" where old.", columnName, " is distinct from new.", columnName,
			" and old.", columnName,
			" is not null do also select case when exists(select 1 from ",
			"pg_catalog.pg_largeobject_metadata where (oid = old.", columnName,
			")) then lo_unlink(old.", columnName, ") end from ", tableName,
			" where ", tableName, StringPool.PERIOD, columnName, " = old.",
			columnName, StringPool.SEMICOLON);
	}

	public PostgreSQLDB(int majorVersion, int minorVersion) {
		super(DBType.POSTGRESQL, majorVersion, minorVersion);
	}

	@Override
	public String buildSQL(String template) throws IOException {
		template = replaceTemplate(template);

		template = reword(template);

		return template;
	}

	@Override
	public List<Index> getIndexes(Connection connection) throws SQLException {
		List<Index> indexes = new ArrayList<>();

		String sql = StringBundler.concat(
			"select indexname, tablename, indexdef from pg_indexes where ",
			"schemaname = current_schema() and (indexname like 'liferay_%' or ",
			"indexname like 'ix_%')");

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				sql);
			ResultSet resultSet = preparedStatement.executeQuery()) {

			while (resultSet.next()) {
				String indexName = resultSet.getString("indexname");
				String tableName = resultSet.getString("tablename");
				String indexSQL = StringUtil.toLowerCase(
					StringUtil.trim(resultSet.getString("indexdef")));

				boolean unique = true;

				if (indexSQL.startsWith("create index ")) {
					unique = false;
				}

				indexes.add(new Index(indexName, tableName, unique));
			}
		}

		return indexes;
	}

	@Override
	public String getPopulateSQL(String databaseName, String sqlContent) {
		return StringBundler.concat("\\c ", databaseName, ";\n\n", sqlContent);
	}

	@Override
	public String getRecreateSQL(String databaseName) {
		return StringBundler.concat(
			"drop database ", databaseName, ";\n", "create database ",
			databaseName, " encoding = 'UNICODE';\n");
	}

	@Override
	public boolean isSupportsQueryingAfterException() {
		return _SUPPORTS_QUERYING_AFTER_EXCEPTION;
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
		return _POSTGRESQL;
	}

	@Override
	protected String reword(String data) throws IOException {
		try (UnsyncBufferedReader unsyncBufferedReader =
				new UnsyncBufferedReader(new UnsyncStringReader(data))) {

			StringBundler sb = new StringBundler();

			StringBundler createRulesSQLSB = new StringBundler();
			String line = null;
			String tableName = null;

			while ((line = unsyncBufferedReader.readLine()) != null) {
				if (line.startsWith(ALTER_COLUMN_NAME)) {
					String[] template = buildColumnNameTokens(line);

					line = StringUtil.replace(
						"alter table @table@ rename @old-column@ to " +
							"@new-column@;",
						REWORD_TEMPLATE, template);
				}
				else if (line.startsWith(ALTER_COLUMN_TYPE)) {
					String[] template = buildColumnTypeTokens(line);

					line = StringUtil.replace(
						"alter table @table@ alter @old-column@ type @type@ " +
							"using @old-column@::@type@;",
						REWORD_TEMPLATE, template);

					String nullable = template[template.length - 1];

					if (!Validator.isBlank(nullable)) {
						if (nullable.equals("not null")) {
							line = line.concat(
								StringUtil.replace(
									"alter table @table@ alter column " +
										"@old-column@ set not null;",
									REWORD_TEMPLATE, template));
						}
						else {
							line = line.concat(
								StringUtil.replace(
									"alter table @table@ alter column " +
										"@old-column@ drop not null;",
									REWORD_TEMPLATE, template));
						}
					}
				}
				else if (line.startsWith(ALTER_TABLE_NAME)) {
					String[] template = buildTableNameTokens(line);

					line = StringUtil.replace(
						"alter table @old-table@ rename to @new-table@;",
						RENAME_TABLE_TEMPLATE, template);
				}
				else if (line.startsWith(CREATE_TABLE)) {
					String[] tokens = StringUtil.split(line, ' ');

					tableName = tokens[2];
				}
				else if (line.contains(DROP_INDEX)) {
					String[] tokens = StringUtil.split(line, ' ');

					line = StringUtil.replace(
						"drop index @index@;", "@index@", tokens[2]);
				}
				else if (line.contains(DROP_PRIMARY_KEY)) {
					String[] tokens = StringUtil.split(line, ' ');

					line = StringUtil.replace(
						"alter table @table@ drop constraint @table@_pkey;",
						"@table@", tokens[2]);
				}
				else if (line.contains(getTemplateBlob())) {
					String[] tokens = StringUtil.split(line, ' ');

					createRulesSQLSB.append(StringPool.NEW_LINE);
					createRulesSQLSB.append(
						getCreateRulesSQL(tableName, tokens[0]));
				}
				else if (line.contains("\\\'")) {
					line = StringUtil.replace(line, "\\\'", "\'\'");
				}

				sb.append(line);
				sb.append("\n");
			}

			sb.append(createRulesSQLSB.toString());

			return sb.toString();
		}
	}

	private static final String[] _POSTGRESQL = {
		"--", "true", "false", "'01/01/1970'", "current_timestamp", " oid",
		" bytea", " bool", " timestamp", " double precision", " integer",
		" bigint", " text", " text", " varchar", "", "commit"
	};

	private static final int _SQL_STRING_SIZE = 2147483647;

	private static final int _SQL_TEXT_SIZE = 2147483647;

	private static final int[] _SQL_TYPES = {
		Types.BIGINT, Types.BINARY, Types.BIT, Types.TIMESTAMP, Types.DOUBLE,
		Types.INTEGER, Types.BIGINT, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR
	};

	private static final int[] _SQL_VARCHAR_SIZES = {
		_SQL_STRING_SIZE, _SQL_TEXT_SIZE
	};

	private static final boolean _SUPPORTS_QUERYING_AFTER_EXCEPTION = false;

}