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
import com.liferay.portal.kernel.dao.db.DBType;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

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
 * @author Shuyang Zhou
 */
public class HypersonicLoader {

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

		DB db = new HypersonicDB(0, 0);

		db.runSQLTemplateString(con, sb.toString(), false, true);
	}

	public static void main(String[] args) throws Exception {
		Map<String, String> arguments = ArgumentsUtil.parseArguments(args);

		String databaseName = arguments.get("db.database.name");
		String sqlDir = arguments.get("db.sql.dir");
		String fileNames = arguments.get("db.file.names");

		try {
			new HypersonicLoader(databaseName, sqlDir, fileNames);
		}
		catch (Exception e) {
			ArgumentsUtil.processMainException(arguments, e);
		}
	}

	public HypersonicLoader(
			String databaseName, String sqlDir, String fileNames)
		throws Exception {

		ToolDependencies.wireBasic();

		DBFactoryUtil.setDB(DBType.HYPERSONIC, null);

		// See LEP-2927. Appending ;shutdown=true to the database connection URL
		// guarantees that ${databaseName}.log is purged.

		try (Connection con = DriverManager.getConnection(
				"jdbc:hsqldb:" + sqlDir + "/" + databaseName +
					";hsqldb.write_delay=false;shutdown=true",
				"sa", "")) {

			if (Validator.isNull(fileNames)) {
				loadHypersonic(con, sqlDir + "/portal/portal-hypersonic.sql");
				loadHypersonic(con, sqlDir + "/indexes.sql");
			}
			else {
				for (String fileName : StringUtil.split(fileNames)) {
					loadHypersonic(con, sqlDir + "/" + fileName);
				}
			}

			// Shutdown Hypersonic

			try (Statement statement = con.createStatement()) {
				statement.execute("SHUTDOWN COMPACT");
			}
		}
	}

}