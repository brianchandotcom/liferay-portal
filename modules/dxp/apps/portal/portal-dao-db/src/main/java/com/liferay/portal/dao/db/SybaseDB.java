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
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.db.DBInspector;
import com.liferay.portal.kernel.dao.db.DBType;
import com.liferay.portal.kernel.io.unsync.UnsyncBufferedReader;
import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.IOException;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Alexander Chow
 * @author Bruno Farache
 * @author Sandeep Soni
 * @author Ganesh Ram
 */
public class SybaseDB extends BaseDB {

	public SybaseDB(int majorVersion, int minorVersion) {
		super(DBType.SYBASE, majorVersion, minorVersion);
	}

	@Override
	public String buildSQL(String template) throws IOException {
		template = replaceTemplate(template);

		if (Validator.isNull(template)) {
			return null;
		}

		template = reword(template);
		template = StringUtil.replace(template, ");\n", ")\ngo\n");
		template = StringUtil.replace(template, "\ngo;\n", "\ngo\n");
		template = StringUtil.replace(
			template, new String[] {"\\\\", "\\'", "\\\"", "\\n", "\\r"},
			new String[] {"\\", "''", "\"", "\n", "\r"});

		return template;
	}

	@Override
	public String getNewUuidFunctionName() {
		return "newid(1)";
	}

	@Override
	public String getPopulateSQL(String databaseName, String sqlContent) {
		return StringBundler.concat("use ", databaseName, "\n\n", sqlContent);
	}

	@Override
	public String getRecreateSQL(String databaseName) {
		return StringBundler.concat(
			"use master\n", "exec sp_dboption '", databaseName,
			"', 'allow nulls by default' , true\n", "go\n\n",
			"exec sp_dboption '", databaseName,
			"', 'select into/bulkcopy/pllsort' , true\n", "go\n\n");
	}

	@Override
	public boolean isSupportsInlineDistinct() {
		return _SUPPORTS_INLINE_DISTINCT;
	}

	@Override
	public boolean isSupportsNewUuidFunction() {
		return _SUPPORTS_NEW_UUID_FUNCTION;
	}

	@Override
	public void removePrimaryKey(Connection connection, String tableName)
		throws SQLException {

		DatabaseMetaData databaseMetaData = connection.getMetaData();

		DBInspector dbInspector = new DBInspector(connection);

		String normalizedTableName = dbInspector.normalizeName(
			tableName, databaseMetaData);

		String primaryKeyConstraintName = null;

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"sp_helpconstraint ?")) {

			preparedStatement.setString(1, normalizedTableName);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {
					String definition = resultSet.getString("definition");

					if (definition.startsWith("PRIMARY KEY INDEX")) {
						primaryKeyConstraintName = resultSet.getString("name");

						break;
					}
				}
			}
		}

		if (primaryKeyConstraintName == null) {
			throw new SQLException(
				"No primary key constraint found for " + normalizedTableName);
		}

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"alter table ? drop constraint ?")) {

			preparedStatement.setString(1, normalizedTableName);
			preparedStatement.setString(2, primaryKeyConstraintName);

			preparedStatement.executeUpdate();
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
		return _SYBASE;
	}

	@Override
	protected String replaceTemplate(String template) {
		if (template == null) {
			return null;
		}

		if (!template.contains("[$COLUMN_LENGTH:")) {
			return super.replaceTemplate(template);
		}

		String[] strings = StringUtil.split(template, CharPool.NEW_LINE);

		Matcher matcher = _columnLengthPattern.matcher(StringPool.BLANK);

		for (int i = 0; i < strings.length; i++) {
			matcher.reset(strings[i]);

			while (matcher.find()) {
				int length = Integer.valueOf(matcher.group(1));

				if (length > 1250) {
					strings[i] = StringPool.BLANK;

					break;
				}
			}
		}

		return super.replaceTemplate(
			StringUtil.merge(strings, StringPool.NEW_LINE));
	}

	@Override
	protected String reword(String data) throws IOException {
		try (UnsyncBufferedReader unsyncBufferedReader =
				new UnsyncBufferedReader(new UnsyncStringReader(data))) {

			StringBundler sb = new StringBundler();

			String line = null;

			while ((line = unsyncBufferedReader.readLine()) != null) {
				if (line.contains(DROP_COLUMN)) {
					line = StringUtil.replace(line, " drop column ", " drop ");
				}

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
						"alter table @table@ modify @old-column@ @type@ " +
							"@nullable@;",
						REWORD_TEMPLATE, template);

					line = StringUtil.replace(line, " ;", ";");
				}
				else if (line.startsWith(ALTER_TABLE_NAME)) {
					String[] template = buildTableNameTokens(line);

					line = StringUtil.replace(
						"exec sp_rename @old-table@, @new-table@;",
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

	protected static final String DROP_COLUMN = "drop column";

	private static final int _SQL_STRING_SIZE = 4000;

	private static final int _SQL_TYPE_TIMESTAMP = 11;

	private static final int[] _SQL_TYPES = {
		Types.LONGVARBINARY, Types.LONGVARBINARY, Types.INTEGER,
		_SQL_TYPE_TIMESTAMP, Types.DOUBLE, Types.INTEGER, Types.DECIMAL,
		Types.VARCHAR, Types.LONGVARCHAR, Types.VARCHAR
	};

	private static final int[] _SQL_VARCHAR_SIZES = {
		_SQL_STRING_SIZE, SQL_SIZE_NONE
	};

	private static final boolean _SUPPORTS_INLINE_DISTINCT = false;

	private static final boolean _SUPPORTS_NEW_UUID_FUNCTION = true;

	private static final String[] _SYBASE = {
		"--", "1", "0", "'19700101'", "getdate()", " image", " image", " int",
		" bigdatetime", " float", " int", " decimal(20,0)", " varchar(4000)",
		" text", " varchar", "  identity(1,1)", "go"
	};

	private static final Pattern _columnLengthPattern = Pattern.compile(
		"\\[\\$COLUMN_LENGTH:(\\d+)\\$\\]");

}