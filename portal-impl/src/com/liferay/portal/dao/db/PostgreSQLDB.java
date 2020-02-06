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
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.io.unsync.UnsyncBufferedReader;
import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.util.StringUtil;

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

		StringBundler sb = new StringBundler(45);

		sb.append("create or replace rule delete_");
		sb.append(tableName);
		sb.append(StringPool.UNDERLINE);
		sb.append(columnName);
		sb.append(" as on delete to ");
		sb.append(tableName);
		sb.append(" do also select case when exists(select 1 from ");
		sb.append("pg_catalog.pg_largeobject_metadata where (oid = old.");
		sb.append(columnName);
		sb.append(")) then lo_unlink(old.");
		sb.append(columnName);
		sb.append(") end from ");
		sb.append(tableName);
		sb.append(" where ");
		sb.append(tableName);
		sb.append(StringPool.PERIOD);
		sb.append(columnName);
		sb.append(" = old.");
		sb.append(columnName);

		sb.append(";\ncreate or replace rule update_");
		sb.append(tableName);
		sb.append(StringPool.UNDERLINE);
		sb.append(columnName);
		sb.append(" as on update to ");
		sb.append(tableName);
		sb.append(" where old.");
		sb.append(columnName);
		sb.append(" is distinct from new.");
		sb.append(columnName);
		sb.append(" and old.");
		sb.append(columnName);
		sb.append(" is not null do also select case when exists(select 1 ");
		sb.append("from pg_catalog.pg_largeobject_metadata where (oid = old.");
		sb.append(columnName);
		sb.append(")) then lo_unlink(old.");
		sb.append(columnName);
		sb.append(") end from ");
		sb.append(tableName);
		sb.append(" where ");
		sb.append(tableName);
		sb.append(StringPool.PERIOD);
		sb.append(columnName);
		sb.append(" = old.");
		sb.append(columnName);
		sb.append(StringPool.SEMICOLON);

		return sb.toString();
	}

	public PostgreSQLDB(int majorVersion, int minorVersion) {
		super(DBType.POSTGRESQL, majorVersion, minorVersion);
	}

	@Override
	public String buildSQL(String template) throws IOException {
		template = replaceTemplate(template, getTemplate());

		template = reword(template);

		return template;
	}

	@Override
	public List<Index> getIndexes(Connection con) throws SQLException {
		List<Index> indexes = new ArrayList<>();

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			StringBundler sb = new StringBundler(3);

			sb.append("select indexname, tablename, indexdef from pg_indexes ");
			sb.append("where indexname like 'liferay_%' or indexname like ");
			sb.append("'ix_%'");

			String sql = sb.toString();

			ps = con.prepareStatement(sql);

			rs = ps.executeQuery();

			while (rs.next()) {
				String indexName = rs.getString("indexname");
				String tableName = rs.getString("tablename");
				String indexSQL = StringUtil.toLowerCase(
					StringUtil.trim(rs.getString("indexdef")));

				boolean unique = true;

				if (indexSQL.startsWith("create index ")) {
					unique = false;
				}

				indexes.add(new Index(indexName, tableName, unique));
			}
		}
		finally {
			DataAccess.cleanUp(ps, rs);
		}

		return indexes;
	}

	@Override
	public boolean isSupportsQueryingAfterException() {
		return _SUPPORTS_QUERYING_AFTER_EXCEPTION;
	}

	@Override
	protected String buildCreateFileContent(
			String sqlDir, String databaseName, String createTablesContent)
		throws IOException {

		StringBundler sb = new StringBundler(14);

		sb.append("drop database ");
		sb.append(databaseName);
		sb.append(";\n");
		sb.append("create database ");
		sb.append(databaseName);
		sb.append(" encoding = 'UNICODE';\n");

		if (createTablesContent != null) {
			sb.append("\\c ");
			sb.append(databaseName);
			sb.append(";\n\n");
			sb.append(createTablesContent);
			sb.append("\n\n");
			sb.append(readFile(sqlDir + "/indexes/indexes-postgresql.sql"));
			sb.append("\n\n");
			sb.append(readFile(sqlDir + "/sequences/sequences-postgresql.sql"));
		}

		return sb.toString();
	}

	@Override
	protected String getServerName() {
		return "postgresql";
	}

	@Override
	protected int[] getSQLTypes() {
		return _SQL_TYPES;
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

	private static final int[] _SQL_TYPES = {
		Types.BIGINT, Types.BINARY, Types.BIT, Types.TIMESTAMP, Types.DOUBLE,
		Types.INTEGER, Types.BIGINT, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR
	};

	private static final boolean _SUPPORTS_QUERYING_AFTER_EXCEPTION = false;

}