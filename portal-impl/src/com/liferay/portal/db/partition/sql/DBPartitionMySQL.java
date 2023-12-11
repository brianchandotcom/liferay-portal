/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
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
public class DBPartitionMySQL implements DBPartitionSQL {

	@Override
	public String getCopyDataSQL(
		String fromSchemaName, String toSchemaName, String tableName,
		String whereClause) {

		return StringBundler.concat(
			"insert ", toSchemaName, StringPool.PERIOD, tableName,
			" select * from ", fromSchemaName, StringPool.PERIOD, tableName,
			whereClause);
	}

	@Override
	public String getCreateSchemaSQL(Connection connection, String schemaName)
		throws SQLException {

		return StringBundler.concat(
			"create schema if not exists ", schemaName, " character set ",
			_getSessionCharsetEncoding(connection));
	}

	@Override
	public String getCreateTableSQL(
		String fromSchemaName, String toSchemaName, String tableName) {

		return StringBundler.concat(
			"create table if not exists ", toSchemaName, StringPool.PERIOD,
			tableName, " like ", fromSchemaName, StringPool.PERIOD, tableName);
	}

	private String _getSessionCharsetEncoding(Connection connection)
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