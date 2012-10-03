/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.IOException;

/**
 * @author David Maier
 */
public class IngresDB extends BaseDB {

	public static DB getInstance() {
		return _instance;
	}

	@Override
	public String buildSQL(String template) throws IOException {
		template = convertTimestamp(template);
		template = replaceTemplate(template, getTemplate());

		template = reword(template);
		template = StringUtil.replace(template, "\\n", "'+x'0a'+'");

		return template;
	}

	@Override
	public boolean isSupportsAlterColumnName() {
		return _SUPPORTS_ALTER_COLUMN_NAME;
	}

	protected IngresDB() {
		super(TYPE_INGRES);
	}

	@Override
	protected String buildCreateFileContent(
		String sqlDir, String databaseName, int population) {

		return null;
	}

	@Override
	protected String getServerName() {
		return "ingres";
	}

	@Override
	protected String[] getTemplate() {
		return _INGRES;
	}

	@Override
	protected String replaceTemplate(String template, String[] actual) {
		if ((template == null) || (TEMPLATE == null) || (actual == null)) {
			return null;
		}

		if (TEMPLATE.length != actual.length) {
			return template;
		}

		for (int i = 0; i < TEMPLATE.length; i++) {
			if (TEMPLATE[i].equals("##") ||
				TEMPLATE[i].equals("'01/01/1970'")) {

				template = template.replaceAll(TEMPLATE[i], actual[i]);
			}
			else if (TEMPLATE[i].equals("COMMIT_TRANSACTION")) {
				template = StringUtil.replace(
					template, TEMPLATE[i] + ";", actual[i]);
			}
			else {
				template = template.replaceAll(
					"\\b" + TEMPLATE[i] + "\\b", actual[i]);
			}
		}

		return template;
	}

	@Override
	protected String reword(String data) throws IOException {
		UnsyncBufferedReader unsyncBufferedReader = new UnsyncBufferedReader(
			new UnsyncStringReader(data));

		StringBundler sb = new StringBundler();

		String line = null;

		while ((line = unsyncBufferedReader.readLine()) != null) {
			if (line.startsWith(ALTER_COLUMN_NAME)) {
				line = "-- " + line;

				if (_log.isWarnEnabled()) {
					_log.warn(
						"This statement is not supported by Ingres: " + line);
				}
			}
			else if (line.startsWith(ALTER_COLUMN_TYPE)) {
				String[] template = buildColumnTypeTokens(line);

				line = StringUtil.replace(
					"alter table @table@ alter @old-column@ @type@;",
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
			else if (line.contains(DROP_PRIMARY_KEY)) {
				String[] tokens = StringUtil.split(line, ' ');

				line = StringUtil.replace(
					"alter table @table@ drop constraint @table@_pkey;",
					"@table@", tokens[2]);
			}

			sb.append(line);
			sb.append("\n");
		}

		unsyncBufferedReader.close();

		return sb.toString();
	}

	private static final String[] _INGRES = {
		"--", "1", "0", "'1970-01-01'", "date('now')", " blob", " blob",
		" tinyint", " timestamp", " float", " integer", " bigint",
		" varchar(1000)", " long varchar", " varchar", "", "commit;\\g"
	};

	private static final boolean _SUPPORTS_ALTER_COLUMN_NAME = false;

	private static Log _log = LogFactoryUtil.getLog(IngresDB.class);

	private static IngresDB _instance = new IngresDB();

}