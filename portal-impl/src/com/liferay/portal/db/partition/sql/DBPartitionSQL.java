/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.db.partition.sql;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.db.DBInspector;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author Alberto Chaparro
 */
public interface DBPartitionSQL {

	public default String getCatalog(
			Connection connection, String partitionName)
		throws SQLException {

		DBInspector dbInspector = new DBInspector(connection);

		return dbInspector.getCatalog();
	}

	public default String getCopyDataSQL(
		String fromPartitionName, String toPartitionName, String tableName,
		String whereClause) {

		return StringBundler.concat(
			"insert into ", toPartitionName, StringPool.PERIOD, tableName,
			" select * from ", fromPartitionName, StringPool.PERIOD, tableName,
			whereClause);
	}

	public String getCreatePartitionSQL(
			Connection connection, String partitionName)
		throws SQLException;

	public String getCreateTableSQL(
		String fromPartitionName, String toPartitionName, String tableName);

	public default String getCreateViewSQL(
		String fromPartitionName, String toPartitionName, String viewName) {

		return StringBundler.concat(
			"create or replace view ", toPartitionName, StringPool.PERIOD,
			viewName, " as select * from ", fromPartitionName,
			StringPool.PERIOD, viewName);
	}

	public String getDefaultPartitionName(Connection connection)
		throws SQLException;

	public default String getDropPartitionSQL(String partitionName) {
		return "drop schema " + partitionName;
	}

	public default String getDropTableSQL(
		String partitionName, String tableName) {

		return StringBundler.concat(
			"drop table if exists ", partitionName, StringPool.PERIOD,
			tableName);
	}

	public default String getDropViewSQL(
		String partitionName, String viewName) {

		return StringBundler.concat(
			"drop view if exists ", partitionName, StringPool.PERIOD, viewName);
	}

	public default String getSchema(
		Connection connection, String partitionName) {

		DBInspector dbInspector = new DBInspector(connection);

		return dbInspector.getSchema();
	}

	public void setPartition(Connection connection, String partitionName)
		throws SQLException;

}