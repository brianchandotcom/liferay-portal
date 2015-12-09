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

package com.liferay.portal.dao.jdbc.postgresql;

import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBFactoryUtil;
import com.liferay.portal.kernel.dao.db.DBType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.postgresql.PGConnection;
import org.postgresql.PGStatement;
import org.postgresql.largeobject.LargeObject;
import org.postgresql.largeobject.LargeObjectManager;

/**
 * @author Istvan Andras Dezsi
 */
public class PostgreSQLJDBCUtil {

	public static byte[] getLargeObject(ResultSet resultSet, String name)
		throws SQLException {

		Statement statement = resultSet.getStatement();

		Connection connection = statement.getConnection();

		connection.setAutoCommit(false);

		try {
			PGConnection pgConnection = connection.unwrap(PGConnection.class);

			LargeObjectManager largeObjectManager =
				pgConnection.getLargeObjectAPI();

			long id = resultSet.getLong(name);

			LargeObject largeObject = largeObjectManager.open(
				id, LargeObjectManager.READ);

			byte[] bytes = new byte[largeObject.size()];

			largeObject.read(bytes, 0, largeObject.size());

			largeObject.close();

			return bytes;
		}
		finally {
			connection.setAutoCommit(true);
		}
	}

	public static boolean isPGStatement(Statement statement)
		throws SQLException {

		DB db = DBFactoryUtil.getDB();

		if (db.getDBType() != DBType.POSTGRESQL) {
			return false;
		}

		if (statement.isWrapperFor(PGStatement.class)) {
			return true;
		}

		return false;
	}

	public static void setLargeObject(
			PreparedStatement preparedStatement, int index, byte[] bytes)
		throws SQLException {

		Connection connection = preparedStatement.getConnection();

		connection.setAutoCommit(false);

		try {
			PGConnection pgConnection = connection.unwrap(PGConnection.class);

			LargeObjectManager largeObjectManager =
				pgConnection.getLargeObjectAPI();

			long id = largeObjectManager.createLO(
				LargeObjectManager.READ | LargeObjectManager.WRITE);

			LargeObject largeObject = largeObjectManager.open(
				id, LargeObjectManager.WRITE);

			largeObject.write(bytes);

			largeObject.close();

			preparedStatement.setLong(index, id);
		}
		finally {
			connection.setAutoCommit(true);
		}
	}

}