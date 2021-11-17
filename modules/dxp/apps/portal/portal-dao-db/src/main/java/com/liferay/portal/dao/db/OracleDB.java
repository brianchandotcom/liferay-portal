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
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.io.unsync.UnsyncBufferedReader;
import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.util.GetterUtil;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Alexander Chow
 * @author Sandeep Soni
 * @author Ganesh Ram
 */
public class OracleDB extends BaseDB {

	public OracleDB(int majorVersion, int minorVersion) {
		super(DBType.ORACLE, majorVersion, minorVersion);
	}

	@Override
	public String buildSQL(String template) throws IOException, SQLException {
		template = replaceTemplate(template);
		template = reword(template);
		template = StringUtil.replace(
			template, new String[] {"\\\\", "\\'", "\\\""},
			new String[] {"\\", "''", "\""});

		return StringUtil.replace(template, "\\n", "'||CHR(10)||'");
	}

	@Override
	public List<Index> getIndexes(Connection connection) throws SQLException {
		List<Index> indexes = new ArrayList<>();

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				StringBundler.concat(
					"select index_name, table_name, uniqueness from ",
					"user_indexes where index_name like 'LIFERAY_%' or ",
					"index_name like 'IX_%'"));
			ResultSet resultSet = preparedStatement.executeQuery()) {

			while (resultSet.next()) {
				String indexName = resultSet.getString("index_name");
				String tableName = resultSet.getString("table_name");
				String uniqueness = resultSet.getString("uniqueness");

				boolean unique = true;

				if (StringUtil.equalsIgnoreCase(uniqueness, "NONUNIQUE")) {
					unique = false;
				}

				indexes.add(new Index(indexName, tableName, unique));
			}
		}

		return indexes;
	}

	@Override
	public String getPopulateSQL(String databaseName, String sqlContent) {
		return StringBundler.concat(
			"connect &1/&2;\n", "set define off;\n\n", sqlContent, "quit");
	}

	@Override
	public String getRecreateSQL(String databaseName) {
		return "drop user &1 cascade;\ncreate user &1 identified by &2;\n" +
			"grant connect,resource to &1;\nquit";
	}

	@Override
	public boolean isSupportsInlineDistinct() {
		return _SUPPORTS_INLINE_DISTINCT;
	}

	@Override
	protected String[] buildColumnTypeTokens(String line) {
		Matcher matcher = _varchar2CharPattern.matcher(line);

		StringBuffer sb = new StringBuffer();

		while (matcher.find()) {
			matcher.appendReplacement(
				sb, "VARCHAR2(" + matcher.group(1) + "%20CHAR)");
		}

		matcher.appendTail(sb);

		String[] template = super.buildColumnTypeTokens(sb.toString());

		template[3] = StringUtil.replace(template[3], "%20", StringPool.SPACE);

		return template;
	}

	@Override
	protected int[] getSQLTypes() {
		return _SQL_TYPES;
	}

	@Override
	protected String[] getTemplate() {
		return _ORACLE;
	}

	protected boolean isNullable(String tableName, String columnName)
		throws SQLException {

		try (Connection connection = DataAccess.getConnection()) {
			DBInspector dbInspector = new DBInspector(connection);

			return dbInspector.isNullable(tableName, columnName);
		}
	}

	@Override
	protected String replaceTemplate(String template) {

		// LPS-12048

		Matcher matcher = _varcharPattern.matcher(template);

		StringBuffer sb = new StringBuffer();

		while (matcher.find()) {
			int size = GetterUtil.getInteger(matcher.group(1));

			if (size > 4000) {
				size = 4000;
			}

			matcher.appendReplacement(sb, "VARCHAR2(" + size + " CHAR)");
		}

		matcher.appendTail(sb);

		template = sb.toString();

		return super.replaceTemplate(template);
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

					String nullable = template[template.length - 1];

					if (!Validator.isBlank(nullable)) {
						boolean currentNullable = isNullable(
							template[0], template[1]);

						if ((nullable.equals("null") && currentNullable) ||
							(nullable.equals("not null") && !currentNullable)) {

							nullable = StringPool.BLANK;
						}
					}

					line = StringUtil.replace(
						"alter table @table@ modify @old-column@ @type@ " +
							nullable + ";",
						REWORD_TEMPLATE, template);

					line = StringUtil.replace(line, " ;", ";");
				}
				else if (line.startsWith(ALTER_TABLE_NAME)) {
					String[] template = buildTableNameTokens(line);

					line = StringUtil.replace(
						"alter table @old-table@ rename to @new-table@;",
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

	private static final String[] _ORACLE = {
		"--", "1", "0",
		"to_date('1970-01-01 00:00:00','YYYY-MM-DD HH24:MI:SS')", "sysdate",
		" blob", " blob", " number(1, 0)", " timestamp", " number(30,20)",
		" number(30,0)", " number(30,0)", " varchar2(4000 char)", " clob",
		" varchar2", "", "commit"
	};

	private static final int[] _SQL_TYPES = {
		Types.BLOB, Types.BLOB, Types.NUMERIC, Types.TIMESTAMP, Types.NUMERIC,
		Types.NUMERIC, Types.NUMERIC, Types.VARCHAR, Types.CLOB, Types.VARCHAR
	};

	private static final boolean _SUPPORTS_INLINE_DISTINCT = false;

	private static final Pattern _varchar2CharPattern = Pattern.compile(
		"VARCHAR2\\((\\d+) CHAR\\)", Pattern.CASE_INSENSITIVE);
	private static final Pattern _varcharPattern = Pattern.compile(
		"VARCHAR\\((\\d+)\\)", Pattern.CASE_INSENSITIVE);

}