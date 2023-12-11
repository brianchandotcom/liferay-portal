/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.db.partition.sql;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author Alberto Chaparro
 */
public interface DBPartitionSQL {

	public String getCopyDataSQL(
		String fromSchemaName, String toSchemaName, String tableName,
		String whereClause);

	public String getCreateSchemaSQL(Connection connection, String schemaName)
		throws SQLException;

	public String getCreateTableSQL(
		String fromSchemaName, String toSchemaName, String tableName);

	public default String getCreateViewSQL(
		String fromSchemaName, String toSchemaName, String viewName) {

		return StringBundler.concat(
			"create or replace view ", toSchemaName, StringPool.PERIOD,
			viewName, " as select * from ", fromSchemaName, StringPool.PERIOD,
			viewName);
	}

	public default String getDropSchemaSQL(String schemaName) {
		return "drop schema " + schemaName;
	}

	public default String getDropTableSQL(String schemaName, String tableName) {
		return StringBundler.concat(
			"drop table if exists ", schemaName, StringPool.PERIOD, tableName);
	}

	public default String getDropViewSQL(String schemaName, String viewName) {
		return StringBundler.concat(
			"drop view if exists ", schemaName, StringPool.PERIOD, viewName);
	}

}