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
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.db.DBType;
import com.liferay.portal.kernel.io.unsync.UnsyncBufferedReader;
import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.IOException;

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
		template = replaceTemplate(template, getTemplate());

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
	public boolean isSupportsInlineDistinct() {
		return _SUPPORTS_INLINE_DISTINCT;
	}

	@Override
	public boolean isSupportsNewUuidFunction() {
		return _SUPPORTS_NEW_UUID_FUNCTION;
	}

	@Override
	protected String applyMaxStringIndexLengthLimitation(String template) {
		if (!template.contains("[$COLUMN_LENGTH:")) {
			return template;
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

		return super.applyMaxStringIndexLengthLimitation(
			StringUtil.merge(strings, StringPool.NEW_LINE));
	}

	@Override
	protected String buildCreateFileContent(
			String sqlDir, String databaseName, int population)
		throws IOException {

		String suffix = getSuffix(population);

		StringBundler sb = new StringBundler(17);

		sb.append("use master\n");
		sb.append("exec sp_dboption '");
		sb.append(databaseName);
		sb.append("', 'allow nulls by default' , true\n");
		sb.append("go\n\n");
		sb.append("exec sp_dboption '");
		sb.append(databaseName);
		sb.append("', 'select into/bulkcopy/pllsort' , true\n");
		sb.append("go\n\n");

		if (population != BARE) {
			sb.append("use ");
			sb.append(databaseName);
			sb.append("\n\n");
			sb.append(getCreateTablesContent(sqlDir, suffix));
			sb.append("\n\n");
			sb.append(readFile(sqlDir + "/indexes/indexes-sybase.sql"));
			sb.append("\n\n");
			sb.append(readFile(sqlDir + "/sequences/sequences-sybase.sql"));
		}

		return sb.toString();
	}

	@Override
	protected String getServerName() {
		return "sybase";
	}

	@Override
	protected int[] getSQLTypes() {
		return _SQL_TYPES;
	}

	@Override
	protected String[] getTemplate() {
		return _SYBASE;
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
						"alter table @table@ modify @old-column@ @type@;",
						REWORD_TEMPLATE, template);
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

	private static final int _SQL_TYPE_TIMESTAMP = 11;

	private static final int[] _SQL_TYPES = {
		Types.LONGVARBINARY, Types.LONGVARBINARY, Types.INTEGER,
		_SQL_TYPE_TIMESTAMP, Types.DOUBLE, Types.INTEGER, Types.DECIMAL,
		Types.VARCHAR, Types.LONGVARCHAR, Types.VARCHAR
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