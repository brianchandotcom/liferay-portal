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

package com.liferay.portal.tools.shard.builder.internal.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author Manuel de la Peña
 */
public class DBManager {

	public void cleanUp(Connection connection) {
		try {
			if (connection != null) {
				connection.close();
			}
		}
		catch (SQLException sqle) {
			sqle.printStackTrace();
		}
	}

	public void cleanUp(
		Connection connection, Statement statement, ResultSet resultSet) {

		cleanUp(resultSet);
		cleanUp(statement);
		cleanUp(connection);
	}

	public void cleanUp(ResultSet resultSet) {
		try {
			if (resultSet != null) {
				resultSet.close();
			}
		}
		catch (SQLException sqle) {
			sqle.printStackTrace();
		}
	}

	public void cleanUp(Statement statement) {
		try {
			if (statement != null) {
				statement.close();
			}
		}
		catch (SQLException sqle) {
			sqle.printStackTrace();
		}
	}

}