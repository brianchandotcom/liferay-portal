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

package com.liferay.portal.tools;

import com.liferay.portal.dao.db.HypersonicDB;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBFactoryUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.FileImpl;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import java.util.List;
import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 */
public class DBLoader {

	public static void loadHypersonic(Connection con, String fileName)
		throws Exception {

		List<String> lines = Files.readAllLines(
			Paths.get(fileName), StandardCharsets.UTF_8);

		StringBundler sb = new StringBundler(lines.size() * 2);

		for (String line : lines) {
			if (line.isEmpty() || line.startsWith(StringPool.DOUBLE_SLASH)) {
				continue;
			}

			sb.append(line);
			sb.append(StringPool.NEW_LINE);
		}

		DB db = HypersonicDB.getInstance();

		db.runSQLTemplateString(con, sb.toString(), false, true);
	}

	public static void main(String[] args) throws Exception {
		Map<String, String> arguments = ArgumentsUtil.parseArguments(args);

		String databaseName = arguments.get("db.database.name");
		String databaseType = arguments.get("db.database.type");
		String sqlDir = arguments.get("db.sql.dir");
		String fileNames = arguments.get("db.file.names");

		try {
			new DBLoader(databaseName, databaseType, sqlDir, fileNames);
		}
		catch (Exception e) {
			ArgumentsUtil.processMainException(arguments, e);
		}
	}

	public DBLoader(
			String databaseName, String databaseType, String sqlDir,
			String fileNames)
		throws Exception {

		_databaseName = databaseName;
		_databaseType = databaseType;
		_sqlDir = sqlDir;
		_fileNames = fileNames;

		if (_databaseType.equals("hypersonic")) {
			ToolDependencies.wireBasic();

			DBFactoryUtil.setDB(_databaseType);

			_loadHypersonic();
		}
	}

	private void _loadHypersonic() throws Exception {

		// See LEP-2927. Appending ;shutdown=true to the database connection URL
		// guarantees that ${_databaseName}.log is purged.

		try (Connection con = DriverManager.getConnection(
				"jdbc:hsqldb:" + _sqlDir + "/" + _databaseName +
					";shutdown=true",
				"sa", "")) {

			if (Validator.isNull(_fileNames)) {
				loadHypersonic(con, _sqlDir + "/portal/portal-hypersonic.sql");
				loadHypersonic(con, _sqlDir + "/indexes.sql");
			}
			else {
				for (String fileName : StringUtil.split(_fileNames)) {
					loadHypersonic(con, _sqlDir + "/" + fileName);
				}
			}

			// Shutdown Hypersonic

			try (Statement statement = con.createStatement()) {
				statement.execute("SHUTDOWN COMPACT");
			}
		}

		// Hypersonic will encode unicode characters twice, this will undo it

		String content = _fileUtil.read(
			_sqlDir + "/" + _databaseName + ".script");

		content = StringUtil.replace(content, "\\u005cu", "\\u");

		_fileUtil.write(_sqlDir + "/" + _databaseName + ".script", content);
	}

	private static final FileImpl _fileUtil = FileImpl.getInstance();

	private final String _databaseName;
	private final String _databaseType;
	private final String _fileNames;
	private final String _sqlDir;

}