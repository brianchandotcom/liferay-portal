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
import com.liferay.portal.kernel.dao.db.DBType;
import com.liferay.portal.kernel.dao.db.Index;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.io.unsync.UnsyncBufferedReader;
import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;

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
	public String buildSQL(String template) throws IOException {
		template = replaceTemplate(template, getTemplate());
		template = reword(template);
		template = StringUtil.replace(
			template, new String[] {"\\\\", "\\'", "\\\""},
			new String[] {"\\", "''", "\""});

		return StringUtil.replace(template, "\\n", "'||CHR(10)||'");
	}

	@Override
	public List<Index> getIndexes(Connection con) throws SQLException {
		List<Index> indexes = new ArrayList<>();

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			StringBundler sb = new StringBundler(3);

			sb.append("select index_name, table_name, uniqueness from ");
			sb.append("user_indexes where index_name like 'LIFERAY_%' or ");
			sb.append("index_name like 'IX_%'");

			String sql = sb.toString();

			ps = con.prepareStatement(sql);

			rs = ps.executeQuery();

			while (rs.next()) {
				String indexName = rs.getString("index_name");
				String tableName = rs.getString("table_name");
				String uniqueness = rs.getString("uniqueness");

				boolean unique = true;

				if (StringUtil.equalsIgnoreCase(uniqueness, "NONUNIQUE")) {
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
	public String getPopulateSQL(String databaseName, String sqlContent) {
		StringBundler sb = new StringBundler(5);

		sb.append("connect &1/&2;\n");
		sb.append("set define off;\n");
		sb.append("\n");
		sb.append(sqlContent);
		sb.append("quit");

		return sb.toString();
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

	@Override
	protected String replaceTemplate(String template, String[] actual) {

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

		return super.replaceTemplate(template, actual);
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
						"alter table @table@ rename column @old-column@ to " +
							"@new-column@;",
						REWORD_TEMPLATE, template);
				}
				else if (line.startsWith(ALTER_COLUMN_TYPE)) {
					String[] template = buildColumnTypeTokens(line);

					line = StringUtil.replace(
						"alter table @table@ modify @old-column@ @type@;",
						REWORD_TEMPLATE, template);
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