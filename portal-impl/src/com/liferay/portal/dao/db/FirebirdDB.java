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
import com.liferay.portal.kernel.util.StringUtil;

import java.io.IOException;

/**
 * @author Alexander Chow
 * @author Sandeep Soni
 * @author Ganesh Ram
 */
public class FirebirdDB extends BaseDB {

	public static DB getInstance() {
		return _instance;
	}

	@Override
	public String buildSQL(String template) throws IOException {
		template = convertTimestamp(template);
		template = replaceTemplate(template, getTemplate());

		template = reword(template);
		template = removeInserts(template);
		template = removeNull(template);

		return template;
	}

	protected FirebirdDB() {
		super(TYPE_FIREBIRD);
	}

	protected FirebirdDB(String type) {
		super(type);
	}

	@Override
	protected String buildCreateFileContent(
			String sqlDir, String databaseName, int population)
		throws IOException {

		String suffix = getSuffix(population);

		StringBundler sb = new StringBundler(7);

		sb.append("create database '");
		sb.append(databaseName);
		sb.append(".gdb' page_size 8192 user 'sysdba' password 'masterkey';\n");
		sb.append("connect '");
		sb.append(databaseName);
		sb.append(".gdb' user 'sysdba' password 'masterkey';\n");

		if (population != BARE) {
			if (!sqlDir.endsWith("/WEB-INF/sql")) {
				sb.append(
					readSQL(
						sqlDir + "/portal" + suffix + "/portal" + suffix +
							"-firebird.sql",
						_FIREBIRD[0], ";\n"));
			}
			else {
				sb.append(
					readSQL(
						sqlDir + "/tables" + suffix + "/tables" + suffix +
							"-firebird.sql",
						_FIREBIRD[0], ";\n"));
			}
		}

		return sb.toString();
	}

	@Override
	protected String getServerName() {
		return "firebird";
	}

	@Override
	protected String[] getTemplate() {
		return _FIREBIRD;
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
						"alter table @table@ alter column \"@old-column@\" " +
							"to \"@new-column@\";",
						REWORD_TEMPLATE, template);
				}
				else if (line.startsWith(ALTER_COLUMN_TYPE)) {
					String[] template = buildColumnTypeTokens(line);

					line = StringUtil.replace(
						"alter table @table@ alter column \"@old-column@\" " +
							"type @type@;",
						REWORD_TEMPLATE, template);
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

	private static final String[] _FIREBIRD = {
		"--", "1", "0", "'01/01/1970'", "current_timestamp", " blob", " blob",
		" smallint", " timestamp", " double precision", " integer", " int64",
		" varchar(4000)", " blob", " varchar", "", "commit"
	};

	private static final FirebirdDB _instance = new FirebirdDB();

}