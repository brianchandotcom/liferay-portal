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

package com.liferay.portal.dao.jdbc.util;

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
public class PostgresUtil {

	public static byte[] getLargeObject(ResultSet rs, String name)
		throws SQLException {

		Statement statement = rs.getStatement();

		Connection connection = statement.getConnection();

		connection.setAutoCommit(false);

		PGConnection pgConnection = connection.unwrap(PGConnection.class);

		LargeObjectManager largeObjectManager =
			pgConnection.getLargeObjectAPI();

		long oid = rs.getLong(name);

		LargeObject largeObject = largeObjectManager.open(
			oid, LargeObjectManager.READ);

		byte buffer[] = new byte[largeObject.size()];

		largeObject.read(buffer, 0, largeObject.size());

		largeObject.close();

		connection.setAutoCommit(true);

		return buffer;
	}

	public static boolean isPostgreSQL(ResultSet rs) throws SQLException {
		Statement statement = rs.getStatement();

		return isPostgreSQL(statement);
	}

	public static boolean isPostgreSQL(Statement statement)
		throws SQLException {

		if (statement.isWrapperFor(PGStatement.class)) {
			return true;
		}

		return false;
	}

	public static void setLargeObject(
			PreparedStatement ps, int paramIndex, byte[] value)
		throws SQLException {

		Connection connection = ps.getConnection();

		connection.setAutoCommit(false);

		PGConnection pgConnection = connection.unwrap(PGConnection.class);

		LargeObjectManager largeObjectManager =
			pgConnection.getLargeObjectAPI();

		long oid = largeObjectManager.createLO(
			LargeObjectManager.READ | LargeObjectManager.WRITE);

		LargeObject largeObject = largeObjectManager.open(
			oid, LargeObjectManager.WRITE);

		largeObject.write(value);

		largeObject.close();

		ps.setLong(paramIndex, oid);

		connection.setAutoCommit(true);
	}

}