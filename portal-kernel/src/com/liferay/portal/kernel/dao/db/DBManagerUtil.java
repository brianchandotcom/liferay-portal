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