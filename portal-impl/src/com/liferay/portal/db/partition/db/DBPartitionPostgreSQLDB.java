/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.db.partition.db;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.dao.db.PostgreSQLDB;
import com.liferay.portal.kernel.util.StringUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.List;

/**
 * @author Alberto Chaparro
 */
public class DBPartitionPostgreSQLDB implements DBPartitionDB {

	@Override
	public void copyRules(Connection connection, String toPartitionName)
		throws SQLException {

		List<String> createRulesSQL = PostgreSQLDB.getCreateRulesSQL(
			toPartitionName);

		try (Statement statement = connection.createStatement()) {
			for (String createRuleSQL : createRulesSQL) {
				statement.executeUpdate(createRuleSQL);
			}
		}
	}

	@Override
	public String getCreatePartitionSQL(
			Connection connection, String partitionName)
		throws SQLException {

		return "create schema if not exists " + partitionName;
	}

	@Override
	public String getCreateTableSQL(
		String fromPartitionName, String toPartitionName, String fromTableName,
		String toTableName) {

		return StringBundler.concat(
			"create table if not exists ", toPartitionName, StringPool.PERIOD,
			toTableName, " (like ", fromPartitionName, StringPool.PERIOD,
			fromTableName, " including all)");
	}

	@Override
	public String getDefaultPartitionName(Connection connection)
		throws SQLException {

		return connection.getSchema();
	}

	@Override
	public String getDropPartitionSQL(String partitionName) {
		return "drop schema if exists " + partitionName + " cascade";
	}

	@Override
	public String getSafeAlterTable(String alterTableSQL) {
		String lowerCaseAlterTableSQL = StringUtil.toLowerCase(alterTableSQL);

		if ((StringUtil.count(lowerCaseAlterTableSQL, " cascade") == 0) &&
			lowerCaseAlterTableSQL.matches("alter table \\S* drop.*$")) {

			return alterTableSQL + " cascade";
		}

		return alterTableSQL;
	}

	@Override
	public String getSchema(Connection connection, String partitionName) {
		return partitionName;
	}

	@Override
	public boolean isDDLTransactional() {
		return true;
	}

	@Override
	public void setPartition(Connection connection, String partitionName)
		throws SQLException {

		connection.setSchema(partitionName);
	}

}