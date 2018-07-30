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

package com.liferay.portal.kernel.dao.jdbc;

import java.sql.Connection;

import javax.sql.DataSource;

/**
 * @author Shuyang Zhou
 */
public class CurrentConnectionUtil {

	public static Connection getConnection(DataSource dataSource) {
		return getCurrentConnection().getConnection(dataSource);
	}

	public static CurrentConnection getCurrentConnection() {
		return _currentConnection;
	}

	public void setCurrentConnection(CurrentConnection currentConnection) {
		_currentConnection = currentConnection;
	}

	private static CurrentConnection _currentConnection;

}