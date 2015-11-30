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

package com.liferay.portal.tools.upgrade.db;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import javax.sql.DataSource;

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

	public int execute(DBProvider dbProvider, String sql, Object... args)
		throws SQLException {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			DataSource dataSource = dbProvider.getDataSource();

			con = dataSource.getConnection();

			ps = con.prepareStatement(sql);

			for (int i = 0; i < args.length; i++) {
				Object arg = args[i];

				if ((arg instanceof Date)) {
					Date date = (Date)arg;

					ps.setTimestamp(i + 1, new Timestamp(date.getTime()));
				}
				else if ((arg instanceof Timestamp)) {
					Timestamp timestamp = (Timestamp)arg;

					ps.setTimestamp(i + 1, timestamp);
				}
				else {
					ps.setObject(i + 1, arg);
				}
			}

			return ps.executeUpdate();
		}
		finally {
			cleanUp(con, ps, rs);
		}
	}

	public boolean ping(DBProvider dbProvider, String sql) throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			DataSource dataSource = dbProvider.getDataSource();

			con = dataSource.getConnection();

			ps = con.prepareStatement(sql);

			rs = ps.executeQuery();

			return rs.next();
		}
		finally {
			cleanUp(con, ps, rs);
		}
	}

}