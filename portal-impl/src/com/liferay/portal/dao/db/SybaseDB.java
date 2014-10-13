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

import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.io.unsync.UnsyncBufferedReader;
import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.IOException;

/**
 * @author Alexander Chow
 * @author Bruno Farache
 * @author Sandeep Soni
 * @author Ganesh Ram
 */
public class SybaseDB extends BaseDB {

	public static DB getInstance() {
		return _instance;
	}

	@Override
	public String buildSQL(String template) throws IOException {
		template = convertTimestamp(template);
		template = replaceTemplate(template, getTemplate());

		template = reword(template);
		template = StringUtil.replace(template, ");\n", ")\ngo\n");
		template = StringUtil.replace(template, "\ngo;\n", "\ngo\n");
		template = StringUtil.replace(
			template,
			new String[] {"\\\\", "\\'", "\\\"", "\\n", "\\r"},
			new String[] {"\\", "''", "\"", "\n", "\r"});

		return template;
	}

	@Override
	public boolean isSupportsInlineDistinct() {
		return _SUPPORTS_INLINE_DISTINCT;
	}

	protected SybaseDB() {
		super(TYPE_SYBASE);
	}

	@Override
	protected String buildCreateFileContent(
			String sqlDir, String databaseName, int population)
		throws IOException {

		String suffix = getSuffix(population);

		StringBundler sb = new StringBundler(19);

		sb.append("use master\n");
		sb.append("exec sp_dboption '");
		sb.append(databaseName);
		sb.append("', ");
		sb.append("'allow nulls by default' , true\n");
		sb.append("go\n\n");
		sb.append("exec sp_dboption '");
		sb.append(databaseName);
		sb.append("', ");
		sb.append("'select into/bulkcopy/pllsort' , true\n");
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

	private static final boolean _SUPPORTS_INLINE_DISTINCT = false;

	private static final String[] _SYBASE = {
		"--", "1", "0", "'19700101'", "getdate()", " image", " image", " int",
		" datetime", " float", " int", " decimal(20,0)", " varchar(1000)",
		" text", " varchar", "  identity(1,1)", "go"
	};

	private static final SybaseDB _instance = new SybaseDB();

}