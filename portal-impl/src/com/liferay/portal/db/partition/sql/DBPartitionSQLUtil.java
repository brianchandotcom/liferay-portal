/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.db.partition.sql;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Alberto Chaparro
 */
public class DBPartitionSQLUtil {

	public static String getCopyDataSQL(
		String fromSchemaName, String toSchemaName, String tableName,
		String whereClause) {

		return StringBundler.concat(
			"insert ", toSchemaName, StringPool.PERIOD, tableName,
			" select * from ", fromSchemaName, StringPool.PERIOD, tableName,
			whereClause);
	}

	public static String getCreateSchemaSQL(
			Connection connection, String schemaName)
		throws SQLException {

		return StringBundler.concat(
			"create schema if not exists ", schemaName, " character set ",
			_getSessionCharsetEncoding(connection));
	}

	public static String getCreateTableSQL(
		String fromSchemaName, String toSchemaName, String tableName) {

		return StringBundler.concat(
			"create table if not exists ", toSchemaName, StringPool.PERIOD,
			tableName, " like ", fromSchemaName, StringPool.PERIOD, tableName);
	}

	public static String getCreateViewSQL(
		String fromSchemaName, String toSchemaName, String viewName) {

		return StringBundler.concat(
			"create or replace view ", toSchemaName, StringPool.PERIOD,
			viewName, " as select * from ", fromSchemaName, StringPool.PERIOD,
			viewName);
	}

	public static String getDropSchemaSQL(String schemaName) {
		return "drop schema " + schemaName;
	}

	public static String getDropTableSQL(String schemaName, String tableName) {
		return StringBundler.concat(
			"drop table if exists ", schemaName, StringPool.PERIOD, tableName);
	}

	public static String getDropViewSQL(String schemaName, String viewName) {
		return StringBundler.concat(
			"drop view if exists ", schemaName, StringPool.PERIOD, viewName);
	}

	private static String _getSessionCharsetEncoding(Connection connection)
		throws SQLException {

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"select variable_value from " +
					"performance_schema.session_variables where " +
						"variable_name = 'character_set_client'");
			ResultSet resultSet = preparedStatement.executeQuery()) {

			if (resultSet.next()) {
				return resultSet.getString("variable_value");
			}

			return "utf8";
		}
	}

}