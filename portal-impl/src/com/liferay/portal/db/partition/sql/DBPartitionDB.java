/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.db.partition.sql;

import com.liferay.portal.kernel.dao.db.DBInspector;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author Alberto Chaparro
 */
public interface DBPartitionDB {

	public default String getCatalog(
			Connection connection, String partitionName)
		throws SQLException {

		DBInspector dbInspector = new DBInspector(connection);

		return dbInspector.getCatalog();
	}

	public String getCreatePartitionSQL(
			Connection connection, String partitionName)
		throws SQLException;

	public String getCreateTableSQL(
		String fromPartitionName, String toPartitionName, String tableName);

	public String getDefaultPartitionName(Connection connection)
		throws SQLException;

	public default String getSchema(
		Connection connection, String partitionName) {

		DBInspector dbInspector = new DBInspector(connection);

		return dbInspector.getSchema();
	}

	public void setPartition(Connection connection, String partitionName)
		throws SQLException;

}