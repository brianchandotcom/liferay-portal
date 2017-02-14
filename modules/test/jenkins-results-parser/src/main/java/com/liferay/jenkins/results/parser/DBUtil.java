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

package com.liferay.jenkins.results.parser;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Leslie Wong
 */
public class DBUtil {

	public static List<Map<String, Object>> executeQuery(
			List<Object> arguments, String dbDriverClassName, String url,
			String userName, String password, String query)
		throws ClassNotFoundException, SQLException {

		List<Map<String, Object>> queryResultMap = new ArrayList<>();

		Class.forName(dbDriverClassName);

		try (Connection connection =
				DriverManager.getConnection(url, userName, password)) {

			try (PreparedStatement preparedStatement =
					connection.prepareStatement(query)) {

				for (int i = 0; i < arguments.size(); i++) {
					preparedStatement.setObject(i + 1, arguments.get(i));
				}

				try (ResultSet resultSet = preparedStatement.executeQuery()) {
					ResultSetMetaData resultSetMetaData =
						resultSet.getMetaData();

					while (resultSet.next()) {
						Map<String, Object> row = new HashMap<>();

						for (int i = 1;
							i <= resultSetMetaData.getColumnCount(); i++) {

							row.put(
								resultSetMetaData.getColumnName(i),
								resultSet.getObject(i));
						}

						queryResultMap.add(row);
					}
				}
			}
		}
		catch (SQLException sqle) {
			sqle.printStackTrace();
		}

		return queryResultMap;
	}

}