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

import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;

import javax.sql.DataSource;

/**
 * @author Brian Wing Shun Chan
 */
public class DBManagerUtil {

	public static DB getDB() {
		return getDBManager().getDB();
	}

	public static DB getDB(DBType dbType, DataSource dataSource) {
		return getDBManager().getDB(dbType, dataSource);
	}

	public static DB getDB(Object dialect, DataSource dataSource) {
		DBManager dbManager = getDBManager();

		return dbManager.getDB(dbManager.getDBType(dialect), dataSource);
	}

	public static DBManager getDBManager() {
		PortalRuntimePermission.checkGetBeanProperty(DBManagerUtil.class);

		return _dbManager;
	}

	public static DBType getDBType(Object dialect) {
		return getDBManager().getDBType(dialect);
	}

	public static void reset() {
		setDBManager(null);
	}

	public static void setDB(DBType dbType, DataSource dataSource) {
		DBManager dbManager = getDBManager();

		dbManager.setDB(dbManager.getDB(dbType, dataSource));
	}

	public static void setDB(Object dialect, DataSource dataSource) {
		DBManager dbManager = getDBManager();

		dbManager.setDB(
			dbManager.getDB(dbManager.getDBType(dialect), dataSource));
	}

	public static void setDBManager(DBManager dbManager) {
		PortalRuntimePermission.checkSetBeanProperty(DBManagerUtil.class);

		_dbManager = dbManager;
	}

	private static DBManager _dbManager;

}