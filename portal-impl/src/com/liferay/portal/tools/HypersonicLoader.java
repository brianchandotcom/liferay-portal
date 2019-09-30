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

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.dao.db.DBManagerImpl;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManager;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.db.DBType;
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

		DBManager dbManager = new DBManagerImpl();

		DB db = dbManager.getDB(DBType.HYPERSONIC, null);

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

		db.runSQLTemplateString(con, sb.toString(), false, true);
	}

	public static void main(String[] args) throws Exception {
		Map<String, String> arguments = ArgumentsUtil.parseArguments(args);

		String databaseName = arguments.get("db.database.name");
		String sqlDir = arguments.get("db.sql.dir");
		String fileNames = arguments.get("db.file.names");
		String username = arguments.get("db.database.username");
		String password = arguments.get("db.database.password");

		try {
			new HypersonicLoader(
				databaseName, sqlDir, fileNames, username, password);
		}
		catch (Exception e) {
			ArgumentsUtil.processMainException(arguments, e);
		}
	}

	/**
	 * @deprecated As of Mueller (7.2.x), replaced by {@link
	 *             #HypersonicLoader(String, String, String, String, String)}
	 */
	@Deprecated
	public HypersonicLoader(
		String databaseName, String sqlDir, String fileNames) {

		throw new UnsupportedOperationException();
	}

	public HypersonicLoader(
			String databaseName, String sqlDir, String fileNames,
			String username, String password)
		throws Exception {

		ToolDependencies.wireBasic();

		DBManagerUtil.setDB(DBType.HYPERSONIC, null);

		// See LEP-2927. Appending ;shutdown=true to the database connection URL
		// guarantees that ${databaseName}.log is purged.

		try (Connection con = DriverManager.getConnection(
				StringBundler.concat(
					"jdbc:hsqldb:", sqlDir, "/", databaseName,
					";hsqldb.write_delay=false;shutdown=true"),
				username, password)) {

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