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
public class DBPartitionPostgreSQLDB implements DBPartitionDB {

	@Override
	public String getCreatePartitionSQL(
			Connection connection, String partitionName)
		throws SQLException {

		return "create schema if not exists " + partitionName;
	}

	@Override
	public String getCreateTableSQL(
		String fromPartitionName, String toPartitionName, String tableName) {

		return StringBundler.concat(
			"create table if not exists ", toPartitionName, StringPool.PERIOD,
			tableName, " (like ", fromPartitionName, StringPool.PERIOD,
			tableName, " INCLUDING ALL)");
	}

	@Override
	public String getDefaultPartitionName(Connection connection)
		throws SQLException {

		return connection.getSchema();
	}

	@Override
	public String getSchema(Connection connection, String partitionName) {
		return partitionName;
	}

	@Override
	public void setPartition(Connection connection, String partitionName)
		throws SQLException {

		connection.setSchema(partitionName);
	}

}