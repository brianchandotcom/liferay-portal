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

import com.liferay.portal.kernel.dao.jdbc.DataAccess;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author Carlos Sierra Andrés
 */
public class DBContext {

	public Connection getDataAccess() {
		try {
			return DataAccess.getUpgradeOptimizedConnection();
		}
		catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public DBManager getDBManager() {
		return DBManagerUtil.getDBManager();
	}

	public Connection getUpgradeOptimizedConnection() {
		try {
			return DataAccess.getUpgradeOptimizedConnection();
		}
		catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}