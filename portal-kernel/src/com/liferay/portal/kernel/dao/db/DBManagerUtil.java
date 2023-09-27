/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.dao.db;

import java.util.Set;

import javax.sql.DataSource;

/**
 * @author Brian Wing Shun Chan
 */
public class DBManagerUtil {

	public static DB getDB() {
		DBManager dbManager = _dbManager;

		return dbManager.getDB();
	}

	public static DB getDB(DBType dbType, DataSource dataSource) {
		DBManager dbManager = _dbManager;

		return dbManager.getDB(dbType, dataSource);
	}

	public static DB getDB(Object dialect, DataSource dataSource) {
		DBManager dbManager = _dbManager;

		return dbManager.getDB(dbManager.getDBType(dialect), dataSource);
	}

	public static DBType getDBType(Object dialect) {
		DBManager dbManager = _dbManager;

		return dbManager.getDBType(dialect);
	}

	public static Set<DBType> getDBTypes() {
		DBManager dbManager = _dbManager;

		return dbManager.getDBTypes();
	}

	public static void reset() {
		setDBManager(null);
	}

	public static void setDB(DBType dbType, DataSource dataSource) {
		DBManager dbManager = _dbManager;

		dbManager.setDB(dbManager.getDB(dbType, dataSource));
	}

	public static void setDB(Object dialect, DataSource dataSource) {
		DBManager dbManager = _dbManager;

		dbManager.setDB(
			dbManager.getDB(dbManager.getDBType(dialect), dataSource));
	}

	public static void setDBManager(DBManager dbManager) {
		_dbManager = dbManager;
	}

	private static DBManager _dbManager;

}