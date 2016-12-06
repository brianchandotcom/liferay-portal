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

package com.liferay.portal.tools.db.support.util;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Assert;

/**
 * @author Andrea Di Giorgi
 */
public class DBTestUtil {

	public static void assertTableExists(
			DatabaseMetaData databaseMetaData, String name)
		throws SQLException {

		try (ResultSet resultSet = _getTables(databaseMetaData, name)) {
			Assert.assertTrue(
				"Missing table \"" + name + "\"", resultSet.next());
		}
	}

	public static void assertTableNotExists(
			DatabaseMetaData databaseMetaData, String name)
		throws SQLException {

		try (ResultSet resultSet = databaseMetaData.getTables(
				null, null, name.toUpperCase(), new String[] {"TABLE"})) {

			Assert.assertFalse(
				"Unexpected table \"" + name + "\"", resultSet.next());
		}
	}

	public static void assertTableRowCount(
			Connection connection, String name, int expectedCount)
		throws SQLException {

		String sql = "select count(*) from " + name;

		try (Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sql)) {

			Assert.assertTrue(resultSet.next());

			Assert.assertEquals(
				"Unexpected row count in table \"" + name + "\"", expectedCount,
				resultSet.getInt(1));
		}
	}

	private static ResultSet _getTables(
			DatabaseMetaData databaseMetaData, String name)
		throws SQLException {

		// See https://www.h2database.com/javadoc/org/h2/engine/DbSettings.html#DATABASE_TO_UPPER

		return databaseMetaData.getTables(
			null, null, name.toUpperCase(), new String[] {"TABLE"});
	}

}