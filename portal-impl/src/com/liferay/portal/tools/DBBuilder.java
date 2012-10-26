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

package com.liferay.portal.tools;

import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBFactoryUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.util.InitUtil;

import java.io.IOException;

import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 * @author Charles May
 * @author Alexander Chow
 */
public class DBBuilder {

	public static void main(String[] args) {
		Map<String, String> arguments = ArgumentsUtil.parseArguments(args);

		InitUtil.initWithSpring(true);

		String databaseName = arguments.get("db.database.name");

		String databaseTypesString = arguments.get("db.database.types");

		String[] databaseTypes = null;

		if (databaseTypesString == null) {
			databaseTypes = DB.TYPE_ALL;
		}
		else {
			databaseTypes = StringUtil.split(databaseTypesString);
		}

		String sqlDir = arguments.get("db.sql.dir");

		new DBBuilder(databaseName, databaseTypes, sqlDir);

		System.exit(0);
	}

	public DBBuilder(
		String databaseName, String[] databaseTypes, String sqlDir) {

		try {
			_databaseName = databaseName;
			_databaseTypes = databaseTypes;

			if (!sqlDir.endsWith("/WEB-INF/sql")) {
				_buildSQLFile(sqlDir, "portal");
				_buildSQLFile(sqlDir, "portal-minimal");
				_buildSQLFile(sqlDir, "portal-tables");
			}
			else {
				_buildSQLFile(sqlDir, "tables");
			}

			_buildSQLFile(sqlDir, "indexes");
			_buildSQLFile(sqlDir, "sequences");
			_buildSQLFile(sqlDir, "update-5.0.1-5.1.0");
			_buildSQLFile(sqlDir, "update-5.1.1-5.1.2");
			_buildSQLFile(sqlDir, "update-5.1.2-5.2.0");
			_buildSQLFile(sqlDir, "update-5.2.0-5.2.1");
			_buildSQLFile(sqlDir, "update-5.2.2-5.2.3");
			_buildSQLFile(sqlDir, "update-5.2.3-6.0.0");
			_buildSQLFile(sqlDir, "update-5.2.5-6.0.0");
			_buildSQLFile(sqlDir, "update-5.2.7-6.0.0");
			_buildSQLFile(sqlDir, "update-5.2.8-6.0.5");
			_buildSQLFile(sqlDir, "update-6.0.0-6.0.1");
			_buildSQLFile(sqlDir, "update-6.0.1-6.0.2");
			_buildSQLFile(sqlDir, "update-6.0.2-6.0.3");
			_buildSQLFile(sqlDir, "update-6.0.4-6.0.5");
			_buildSQLFile(sqlDir, "update-6.0.5-6.0.6");
			_buildSQLFile(sqlDir, "update-6.0.6-6.1.0");
			_buildSQLFile(sqlDir, "update-6.0.12-6.1.0");
			_buildSQLFile(sqlDir, "update-6.1.0-6.1.1");
			_buildSQLFile(sqlDir, "update-6.1.1-6.2.0");

			_buildCreateFile(sqlDir);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void _buildCreateFile(String sqlDir) throws IOException {
		for (int i = 0; i < _databaseTypes.length; i++) {
			String databaseType = _databaseTypes[i];

			if (databaseType.equals(DB.TYPE_HYPERSONIC) ||
				databaseType.equals(DB.TYPE_INTERBASE) ||
				databaseType.equals(DB.TYPE_JDATASTORE) ||
				databaseType.equals(DB.TYPE_SAP)) {

				continue;
			}

			DB db = DBFactoryUtil.getDB(_databaseTypes[i]);

			if (db != null) {
				if (!sqlDir.endsWith("/WEB-INF/sql")) {
					db.buildCreateFile(sqlDir, _databaseName);
				}
				else {
					db.buildCreateFile(sqlDir, _databaseName, DB.POPULATED);
				}
			}
		}
	}

	private void _buildSQLFile(String sqlDir, String fileName)
		throws IOException {

		if (!FileUtil.exists(sqlDir + "/" + fileName + ".sql")) {
			return;
		}

		for (int i = 0; i < _databaseTypes.length; i++) {
			DB db = DBFactoryUtil.getDB(_databaseTypes[i]);

			if (db != null) {
				db.buildSQLFile(sqlDir, fileName);
			}
		}
	}

	private String _databaseName;
	private String[] _databaseTypes;

}