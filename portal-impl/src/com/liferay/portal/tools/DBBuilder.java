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
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.db.DBType;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.IOException;

import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 * @author Charles May
 * @author Alexander Chow
 * @author Raymond Augé
 */
public class DBBuilder {

	public static void main(String[] args) throws Exception {
		ToolDependencies.wireBasic();

		Map<String, String> arguments = ArgumentsUtil.parseArguments(args);

		String databaseName = arguments.get("db.database.name");

		String databaseTypesString = arguments.get("db.database.types");

		DBType[] dbTypes = DBType.values();

		if (databaseTypesString != null) {
			String[] databaseTypeValues = StringUtil.split(databaseTypesString);

			dbTypes = new DBType[databaseTypeValues.length];

			for (int i = 0; i < dbTypes.length; i++) {
				dbTypes[i] = DBType.valueOf(
					StringUtil.toUpperCase(databaseTypeValues[i]));
			}
		}

		String sqlDir = arguments.get("db.sql.dir");

		try {
			new DBBuilder(databaseName, dbTypes, sqlDir);
		}
		catch (Exception exception) {
			ArgumentsUtil.processMainException(arguments, exception);
		}
	}

	public DBBuilder(String databaseName, DBType[] dbTypes, String sqlDir)
		throws Exception {

		_databaseName = databaseName;
		_dbTypes = dbTypes;

		if (!sqlDir.endsWith("/META-INF/sql") &&
			!sqlDir.endsWith("/WEB-INF/sql")) {

			_buildSQLFile(sqlDir, "portal");
			_buildSQLFile(sqlDir, "portal-tables");
		}
		else {
			_buildSQLFile(sqlDir, "tables");
		}

		_buildSQLFile(sqlDir, "indexes");
		_buildSQLFile(sqlDir, "sequences");
		_buildSQLFile(sqlDir, "update-6.1.0-6.1.1");
		_buildSQLFiles(sqlDir, "update-6.1.1-6.2.0*");
		_buildSQLFiles(sqlDir, "update-6.2.0-7.0.0*");
		_buildSQLFiles(sqlDir, "update-7.0.0-7.0.1*");

		_buildCreateFile(sqlDir);
	}

	private void _buildCreateFile(String sqlDir) throws IOException {
		for (DBType dbType : _dbTypes) {
			if (dbType == DBType.HYPERSONIC) {
				continue;
			}

			DB db = DBManagerUtil.getDB(dbType, null);

			if (db != null) {
				if (!sqlDir.endsWith("/WEB-INF/sql")) {
					FileUtil.write(
						StringBundler.concat(
							sqlDir, "/create-bare/create-bare-", db.getDBType(),
							".sql"),
						db.getRecreateSQL(_databaseName));
				}

				db.buildCreateFile(sqlDir, _databaseName, DB.DEFAULT);
			}
		}
	}

	private void _buildSQLFile(String sqlDir, String fileName)
		throws IOException {

		if (!FileUtil.exists(
				StringBundler.concat(sqlDir, "/", fileName, ".sql"))) {

			return;
		}

		_generateSQLFile(sqlDir, fileName);
	}

	private void _buildSQLFiles(String sqlDir, String regex)
		throws IOException {

		try (DirectoryStream<Path> paths = Files.newDirectoryStream(
				Paths.get(sqlDir), regex)) {

			for (Path path : paths) {
				Path fileNamePath = path.getFileName();

				String fileName = fileNamePath.toString();

				_generateSQLFile(
					sqlDir, StringUtil.removeSubstring(fileName, ".sql"));
			}
		}
	}

	private void _generateSQLFile(String sqlDir, String fileName)
		throws IOException {

		for (DBType dbType : _dbTypes) {
			DB db = DBManagerUtil.getDB(dbType, null);

			if (db != null) {
				db.buildSQLFile(sqlDir, fileName);
			}
		}
	}

	private final String _databaseName;
	private final DBType[] _dbTypes;

}