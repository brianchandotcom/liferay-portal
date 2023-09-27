/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.dao.jdbc.postgresql;

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
 * @author István András Dézsi
 */
public class PostgreSQLJDBCUtil {

	public static byte[] getLargeObject(ResultSet resultSet, String name)
		throws SQLException {

		long id = resultSet.getLong(name);

		if (id == 0) {
			return null;
		}

		Statement statement = resultSet.getStatement();

		Connection connection = statement.getConnection();

		boolean autoCommit = connection.getAutoCommit();

		if (autoCommit) {
			connection.setAutoCommit(false);
		}

		try {
			PGConnection pgConnection = connection.unwrap(PGConnection.class);

			LargeObjectManager largeObjectManager =
				pgConnection.getLargeObjectAPI();

			LargeObject largeObject = largeObjectManager.open(
				id, LargeObjectManager.READ);

			byte[] bytes = new byte[largeObject.size()];

			largeObject.read(bytes, 0, largeObject.size());

			largeObject.close();

			return bytes;
		}
		finally {
			if (autoCommit) {
				connection.setAutoCommit(true);
			}
		}
	}

	public static boolean isPGStatement(Statement statement)
		throws SQLException {

		if (statement.isWrapperFor(PGStatement.class)) {
			return true;
		}

		return false;
	}

	public static void setLargeObject(
			PreparedStatement preparedStatement, int index, byte[] bytes)
		throws SQLException {

		Connection connection = preparedStatement.getConnection();

		boolean autoCommit = connection.getAutoCommit();

		if (autoCommit) {
			connection.setAutoCommit(false);
		}

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
			if (autoCommit) {
				connection.setAutoCommit(true);
			}
		}
	}

}