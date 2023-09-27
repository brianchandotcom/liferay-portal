/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.dao.jdbc;

import java.sql.Connection;

import javax.sql.DataSource;

/**
 * @author Shuyang Zhou
 */
public class CurrentConnectionUtil {

	public static Connection getConnection(DataSource dataSource) {
		return _currentConnection.getConnection(dataSource);
	}

	public static CurrentConnection getCurrentConnection() {
		return _currentConnection;
	}

	public void setCurrentConnection(CurrentConnection currentConnection) {
		_currentConnection = currentConnection;
	}

	private static CurrentConnection _currentConnection;

}