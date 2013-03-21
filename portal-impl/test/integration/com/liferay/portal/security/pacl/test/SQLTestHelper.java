/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.security.pacl.test;

import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBFactoryUtil;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author Raymond Augé
 */
public class SQLTestHelper {

	protected void executeDB(String sql) throws Exception {
		DB db = DBFactoryUtil.getDB();

		db.runSQL(sql);
	}

	protected void executePreparedStatement(String sql) throws Exception {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			connection = DataAccess.getConnection();

			preparedStatement = connection.prepareStatement(sql);

			preparedStatement.execute();
		}
		catch (SQLException se) {
		}
		finally {
			DataAccess.cleanUp(connection, preparedStatement);
		}
	}

	protected void executeStatement(String sql) throws Exception {
		Connection connection = null;
		Statement statement = null;

		try {
			connection = DataAccess.getConnection();

			statement = connection.createStatement();

			statement.execute(sql);
		}
		catch (SQLException se) {
		}
		finally {
			DataAccess.cleanUp(connection, statement);
		}

	}

	protected boolean isMySQL() {
		DB db = DBFactoryUtil.getDB();

		String dbType = db.getType();

		if (dbType.equals(DB.TYPE_MYSQL)) {
			return true;
		}

		return false;
	}

}