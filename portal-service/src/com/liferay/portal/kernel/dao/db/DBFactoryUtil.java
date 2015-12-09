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
public class DBFactoryUtil {

	public static DB getDB() {
		return getDBFactory().getDB();
	}

	public static DB getDB(DBType dbType, DataSource dataSource) {
		return getDBFactory().getDB(dbType, dataSource);
	}

	public static DB getDB(Object dialect, DataSource dataSource) {
		return getDBFactory().getDB(dialect, dataSource);
	}

	public static DBFactory getDBFactory() {
		PortalRuntimePermission.checkGetBeanProperty(DBFactoryUtil.class);

		return _dbFactory;
	}

	public static void reset() {
		setDBFactory(null);
	}

	public static void setDB(DBType dbType, DataSource dataSource) {
		getDBFactory().setDB(dbType, dataSource);
	}

	public static void setDB(Object dialect, DataSource dataSource) {
		getDBFactory().setDB(dialect, dataSource);
	}

	public static void setDBFactory(DBFactory dbFactory) {
		PortalRuntimePermission.checkSetBeanProperty(DBFactoryUtil.class);

		_dbFactory = dbFactory;
	}

	private static DBFactory _dbFactory;

}