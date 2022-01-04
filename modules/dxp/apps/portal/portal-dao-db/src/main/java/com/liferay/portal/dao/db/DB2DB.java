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

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.db.DBType;
import com.liferay.portal.kernel.io.unsync.UnsyncBufferedReader;
import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.IOException;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Alexander Chow
 * @author Bruno Farache
 * @author Sandeep Soni
 * @author Ganesh Ram
 */
public class DB2DB extends BaseDB {

	public DB2DB(int majorVersion, int minorVersion) {
		super(DBType.DB2, majorVersion, minorVersion);
	}

	@Override
	public String buildSQL(String template) throws IOException, SQLException {
		template = replaceTemplate(template);

		template = reword(template);
		template = _removeNull(template);
		template = StringUtil.replace(template, "\\'", "''");
		template = StringUtil.replace(template, "\\n", "'||CHR(10)||'");

		return template;
	}

	@Override
	public String getPopulateSQL(String databaseName, String sqlContent) {
		return StringBundler.concat(
			"connect to ", databaseName, ";\n", sqlContent);
	}

	@Override
	public String getRecreateSQL(String databaseName) {
		return StringBundler.concat(
			"drop database ", databaseName, ";\n", "create database ",
			databaseName,
			" pagesize 32768 temporary tablespace managed by automatic ",
			"storage;\n");
	}

	@Override
	public boolean isSupportsInlineDistinct() {
		return _SUPPORTS_INLINE_DISTINCT;
	}

	@Override
	public boolean isSupportsScrollableResults() {
		return _SUPPORTS_SCROLLABLE_RESULTS;
	}

	@Override
	public void runSQL(Connection connection, String[] templates)
		throws IOException, SQLException {

		reorgTables(connection, templates);

		super.runSQL(connection, templates);

		reorgTables(connection, templates);
	}

	@Override
	public void runSQL(String template) throws IOException, SQLException {
		template = StringUtil.trim(template);

		if (template.startsWith(ALTER_COLUMN_NAME)) {
			String sql = buildSQL(template);

			String[] alterSqls = StringUtil.split(sql, CharPool.SEMICOLON);

			for (String alterSql : alterSqls) {
				alterSql = StringUtil.trim(alterSql);

				runSQL(alterSql);
			}
		}
		else {
			super.runSQL(template);
		}
	}

	@Override
	protected int[] getSQLTypes() {
		return _SQL_TYPES;
	}

	@Override
	protected String[] getTemplate() {
		return _DB2;
	}

	protected boolean isRequiresReorgTable(
			Connection connection, String tableName)
		throws SQLException {

		boolean reorgTableRequired = false;

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				StringBundler.concat(
					"select num_reorg_rec_alters from table(",
					"sysproc.admin_get_tab_info(current_schema, '",
					StringUtil.toUpperCase(tableName),
					"')) where reorg_pending = 'Y'"));
			ResultSet resultSet = preparedStatement.executeQuery()) {

			if (resultSet.next()) {
				int numReorgRecAlters = resultSet.getInt(1);

				if (numReorgRecAlters >= 1) {
					reorgTableRequired = true;
				}
			}
		}

		return reorgTableRequired;
	}

	protected void reorgTable(Connection connection, String tableName)
		throws SQLException {

		if (!isRequiresReorgTable(connection, tableName)) {
			return;
		}

		try (CallableStatement callableStatement = connection.prepareCall(
				"call sysproc.admin_cmd(?)")) {

			callableStatement.setString(1, "reorg table " + tableName);

			callableStatement.execute();
		}
	}

	protected void reorgTables(Connection connection, String[] templates)
		throws SQLException {

		Set<String> tableNames = new HashSet<>();

		for (String template : templates) {
			template = StringUtil.trim(template);

			String lowerCaseTemplate = StringUtil.toLowerCase(template);

			if (lowerCaseTemplate.startsWith("alter table") ||
					lowerCaseTemplate.startsWith("delete from")) {
				tableNames.add(template.split(" ")[2]);
			}
			else if (lowerCaseTemplate.startsWith(ALTER_COLUMN_TYPE)) {
				tableNames.add(template.split(" ")[1]);
			}
		}

		if (tableNames.isEmpty()) {
			return;
		}

		for (String tableName : tableNames) {
			reorgTable(connection, tableName);
		}
	}

	@Override
	protected String reword(String data) throws IOException, SQLException {
		try (UnsyncBufferedReader unsyncBufferedReader =
				new UnsyncBufferedReader(new UnsyncStringReader(data))) {

			StringBundler sb = new StringBundler();

			String line = null;

			while ((line = unsyncBufferedReader.readLine()) != null) {
				if (line.startsWith(ALTER_COLUMN_NAME)) {
					String[] template = buildColumnNameTokens(line);

					line = StringUtil.replace(
						"alter table @table@ rename column @old-column@ to " +
							"@new-column@;",
						REWORD_TEMPLATE, template);
				}
				else if (line.startsWith(ALTER_COLUMN_TYPE)) {
					String[] template = buildColumnTypeTokens(line);

					line = StringUtil.replace(
						"alter table @table@ alter column @old-column@ set " +
							"data type @type@;",
						REWORD_TEMPLATE, template);

					String nullable = template[template.length - 1];

					if (!Validator.isBlank(nullable)) {
						String nullableAlter;

						if (nullable.equals("not null")) {
							nullableAlter = StringUtil.replace(
								"alter table @table@ alter column " +
									"@old-column@ set not null;",
								REWORD_TEMPLATE, template);
						}
						else {
							nullableAlter = StringUtil.replace(
								"alter table @table@ alter column " +
									"@old-column@ drop not null;",
								REWORD_TEMPLATE, template);
						}

						runSQL(nullableAlter);
					}
				}
				else if (line.startsWith(ALTER_TABLE_NAME)) {
					String[] template = buildTableNameTokens(line);

					line = StringUtil.replace(
						"alter table @old-table@ to @new-table@;",
						RENAME_TABLE_TEMPLATE, template);
				}
				else if (line.contains(DROP_INDEX)) {
					String[] tokens = StringUtil.split(line, ' ');

					line = StringUtil.replace(
						"drop index @index@;", "@index@", tokens[2]);
				}

				sb.append(line);
				sb.append("\n");
			}

			return sb.toString();
		}
	}

	private String _removeNull(String content) {
		content = StringUtil.replace(content, " = null", " = NULL");
		content = StringUtil.replace(content, " is null", " IS NULL");
		content = StringUtil.replace(content, " not null", " not_null");
		content = StringUtil.removeSubstring(content, " null");
		content = StringUtil.replace(content, " not_null", " not null");

		return content;
	}

	private static final String[] _DB2 = {
		"--", "1", "0", "'1970-01-01-00.00.00.000000'", "current timestamp",
		" blob", " blob", " smallint", " timestamp", " double", " integer",
		" bigint", " varchar(4000)", " clob", " varchar",
		" generated always as identity", "commit"
	};

	private static final int[] _SQL_TYPES = {
		Types.BLOB, Types.BLOB, Types.SMALLINT, Types.TIMESTAMP, Types.DOUBLE,
		Types.INTEGER, Types.BIGINT, Types.VARCHAR, Types.CLOB, Types.VARCHAR
	};

	private static final boolean _SUPPORTS_INLINE_DISTINCT = false;

	private static final boolean _SUPPORTS_SCROLLABLE_RESULTS = false;

}