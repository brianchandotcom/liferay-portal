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

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.db.DBType;
import com.liferay.portal.kernel.dao.db.Index;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.io.unsync.UnsyncBufferedReader;
import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;

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
	public String buildSQL(String template) throws IOException {
		template = replaceTemplate(template, getTemplate());

		template = reword(template);
		template = StringUtil.replace(template, "\ngo;\n", "\ngo\n");
		template = StringUtil.replace(
			template, new String[] {"\\\\", "\\'", "\\\"", "\\n", "\\r"},
			new String[] {"\\", "''", "\"", "\n", "\r"});

		return template;
	}

	@Override
	public List<Index> getIndexes(Connection con) throws SQLException {
		List<Index> indexes = new ArrayList<>();

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			DatabaseMetaData databaseMetaData = con.getMetaData();

			if (databaseMetaData.getDatabaseMajorVersion() <=
					_SQL_SERVER_2000) {

				return indexes;
			}

			StringBundler sb = new StringBundler(6);

			sb.append("select sys.tables.name as table_name, ");
			sb.append("sys.indexes.name as index_name, is_unique from ");
			sb.append("sys.indexes inner join sys.tables on ");
			sb.append("sys.tables.object_id = sys.indexes.object_id where ");
			sb.append("sys.indexes.name like 'LIFERAY_%' or sys.indexes.name ");
			sb.append("like 'IX_%'");

			String sql = sb.toString();

			ps = con.prepareStatement(sql);

			rs = ps.executeQuery();

			while (rs.next()) {
				String indexName = rs.getString("index_name");
				String tableName = rs.getString("table_name");
				boolean unique = !rs.getBoolean("is_unique");

				indexes.add(new Index(indexName, tableName, unique));
			}
		}
		finally {
			DataAccess.cleanUp(ps, rs);
		}

		return indexes;
	}

	@Override
	public String getNewUuidFunctionName() {
		return "lower(NEWID())";
	}

	@Override
	public String getRecreateSQL(String databaseName) {
		StringBundler sb = new StringBundler(9);

		sb.append("drop database ");
		sb.append(databaseName);
		sb.append(";\n");
		sb.append("create database ");
		sb.append(databaseName);
		sb.append(";\n");
		sb.append("\n");
		sb.append("go\n");
		sb.append("\n");

		return sb.toString();
	}

	@Override
	public boolean isSupportsAlterColumnType() {
		return _SUPPORTS_ALTER_COLUMN_TYPE;
	}

	@Override
	public boolean isSupportsNewUuidFunction() {
		return _SUPPORTS_NEW_UUID_FUNCTION;
	}

	@Override
	protected String buildCreateFileContent(
			String sqlDir, String databaseName, String createContent)
		throws IOException {

		StringBundler sb = new StringBundler(13);

		sb.append("drop database ");
		sb.append(databaseName);
		sb.append(";\n");
		sb.append("create database ");
		sb.append(databaseName);
		sb.append(";\n");
		sb.append("\n");
		sb.append("go\n");
		sb.append("\n");

		if (createContent != null) {
			sb.append("use ");
			sb.append(databaseName);
			sb.append(";\n\n");
			sb.append(createContent);
		}

		return sb.toString();
	}

	@Override
	protected int[] getSQLTypes() {
		return _SQL_TYPES;
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
						"alter table @table@ alter column @old-column@ @type@;",
						REWORD_TEMPLATE, template);
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

	private static final String[] _SQL_SERVER = {
		"--", "1", "0", "'19700101'", "GetDate()", " image", " image", " bit",
		" datetime2(6)", " float", " int", " bigint", " nvarchar(4000)",
		" nvarchar(max)", " nvarchar", "  identity(1,1)", "go"
	};

	private static final int _SQL_SERVER_2000 = 8;

	private static final int[] _SQL_TYPES = {
		Types.LONGVARBINARY, Types.LONGVARBINARY, Types.BIT, Types.TIMESTAMP,
		Types.DOUBLE, Types.INTEGER, Types.BIGINT, Types.LONGVARCHAR,
		Types.LONGVARCHAR, Types.VARCHAR
	};

	private static final boolean _SUPPORTS_ALTER_COLUMN_TYPE = false;

	private static final boolean _SUPPORTS_NEW_UUID_FUNCTION = true;

}