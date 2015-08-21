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
import org.postgresql.largeobject.LargeObject;
import org.postgresql.largeobject.LargeObjectManager;

/**
 * @author Istvan Andras Dezsi
 */
public class JDBCUtil {

	public static byte[] getPGLargeObject(ResultSet rs, String name)
		throws SQLException {

		Connection con = rs.getStatement().getConnection();

		con.setAutoCommit(false);

		PGConnection pgCon = con.unwrap(org.postgresql.PGConnection.class);

		LargeObjectManager lobj = pgCon.getLargeObjectAPI();

		long oid = rs.getLong(name);

		LargeObject obj = lobj.open(oid, LargeObjectManager.READ);

		byte buff[] = new byte[obj.size()];

		obj.read(buff, 0, obj.size());

		obj.close();

		con.setAutoCommit(true);

		return buff;
	}

	public static boolean isPostgreSQL(ResultSet rs) throws SQLException {
		Statement statement = rs.getStatement();

		return isPostgreSQL(statement);
	}

	public static boolean isPostgreSQL(Statement statement)
		throws SQLException {

		if (statement.isWrapperFor(org.postgresql.PGStatement.class)) {
			return true;
		}

		return false;
	}

	public static void setPGLargeObject(
			PreparedStatement ps, int paramIndex, byte[] value)
		throws SQLException {

		Connection con = ps.getConnection();

		con.setAutoCommit(false);

		PGConnection pgCon = con.unwrap(org.postgresql.PGConnection.class);

		LargeObjectManager lobj = pgCon.getLargeObjectAPI();

		long oid = lobj.createLO(
			LargeObjectManager.READ | LargeObjectManager.WRITE);

		LargeObject obj = lobj.open(oid, LargeObjectManager.WRITE);

		obj.write(value);

		obj.close();

		ps.setLong(paramIndex, oid);

		con.setAutoCommit(true);
	}

}