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

package com.liferay.portal.kernel.dao.db;

import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;

/**
 * @author Brian Wing Shun Chan
 */
public class DBFactoryUtil {

	public static DB getDB() {
		return getDBFactory().getDB();
	}

	public static DB getDB(Object dialect) {
		return getDBFactory().getDB(dialect);
	}

	public static DB getDB(String type) {
		return getDBFactory().getDB(type);
	}

	public static DBFactory getDBFactory() {
		PortalRuntimePermission.checkGetBeanProperty(DBFactoryUtil.class);

		return _dbFactory;
	}

	public static void reset() {
		setDBFactory(null);
	}

	public static void setDB(Object dialect) {
		getDBFactory().setDB(dialect);
	}

	public static void setDB(String type) {
		getDBFactory().setDB(type);
	}

	public static void setDBFactory(DBFactory dbFactory) {
		PortalRuntimePermission.checkSetBeanProperty(DBFactoryUtil.class);

		_dbFactory = dbFactory;
	}

	private static DBFactory _dbFactory;

}