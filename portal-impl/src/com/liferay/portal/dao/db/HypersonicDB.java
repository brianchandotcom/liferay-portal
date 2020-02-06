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
import com.liferay.portal.kernel.io.unsync.UnsyncBufferedReader;
import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.IOException;

import java.sql.Types;

/**
 * @author Alexander Chow
 * @author Sandeep Soni
 * @author Ganesh Ram
 */
public class HypersonicDB extends BaseDB {

	public HypersonicDB(int majorVersion, int minorVersion) {
		super(DBType.HYPERSONIC, majorVersion, minorVersion);
	}

	@Override
	public String buildSQL(String template) throws IOException {
		template = replaceTemplate(template, getTemplate());

		template = reword(template);
		template = StringUtil.replace(template, "\\'", "''");

		return template;
	}

	@Override
	public String getPopulateSQL(String databaseName, String sqlContent) {
		return StringPool.BLANK;
	}

	@Override
	public String getRecreateSQL(String databaseName) {
		return StringPool.BLANK;
	}

	@Override
	protected int[] getSQLTypes() {
		return _SQL_TYPES;
	}

	@Override
	protected String[] getTemplate() {
		return _HYPERSONIC;
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
						"alter table @table@ alter column @old-column@ " +
							"rename to @new-column@;",
						REWORD_TEMPLATE, template);
				}
				else if (line.startsWith(ALTER_COLUMN_TYPE)) {
					String[] template = buildColumnTypeTokens(line);

					line = StringUtil.replace(
						"alter table @table@ alter column @old-column@ @type@;",
						REWORD_TEMPLATE, template);

					line = line.concat(
						StringUtil.replace(
							"alter table @table@ alter column @old-column@ " +
								"set @nullable@;",
							REWORD_TEMPLATE, template));
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

	private static final String[] _HYPERSONIC = {
		"//", "true", "false", "'1970-01-01 00:00:00'", "now()", " blob",
		" blob", " bit", " timestamp", " double", " int", " bigint",
		" longvarchar", " longvarchar", " varchar", "", "commit"
	};

	private static final int[] _SQL_TYPES = {
		Types.BLOB, Types.BLOB, Types.BIT, Types.TIMESTAMP, Types.DOUBLE,
		Types.INTEGER, Types.BIGINT, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR
	};

}