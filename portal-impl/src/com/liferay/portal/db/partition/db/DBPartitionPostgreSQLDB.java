/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.db.partition.db;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.dao.db.PostgreSQLDB;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.util.StringUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
	public List<String> getCreateRulesSQL(String partitionName)
		throws SQLException {

		List<String> rules = new ArrayList<>();

		try (Connection connection = DataAccess.getConnection()) {
			String sql = StringBundler.concat(
				"select pg_catalog.pg_rewrite.rulename rulename, ",
				"pg_catalog.pg_get_ruledef(pg_catalog.pg_rewrite.oid, true) ",
				"ruledefinition from pg_catalog.pg_rewrite join pg_catalog.",
				"pg_class on pg_catalog.pg_rewrite.ev_class = ",
				"pg_catalog.pg_class.oid where ",
				"pg_catalog.pg_class.relnamespace ='", connection.getSchema(),
				"'::regnamespace and (pg_catalog.pg_rewrite.rulename like ",
				"'delete_%' or pg_catalog.pg_rewrite.rulename like ",
				"'update_%')");

			try (PreparedStatement preparedStatement =
					connection.prepareStatement(sql);
				ResultSet resultSet = preparedStatement.executeQuery()) {

				while (resultSet.next()) {
					String ruleDefinition = StringUtil.toLowerCase(
						resultSet.getString("ruledefinition"));

					String ruleName = resultSet.getString("rulename");

					String[] ruleTableColumn = _getRuleTableColumn(
						ruleDefinition);

					if (!StringUtil.equals(
							ruleName,
							StringBundler.concat(
								"delete_", ruleTableColumn[0],
								StringPool.UNDERLINE, ruleTableColumn[1])) &&
						!StringUtil.equals(
							ruleName,
							StringBundler.concat(
								"update_", ruleTableColumn[0],
								StringPool.UNDERLINE, ruleTableColumn[1]))) {

						continue;
					}

					rules.add(
						PostgreSQLDB.getCreateRulesSQL(
							StringBundler.concat(
								partitionName, StringPool.PERIOD,
								ruleTableColumn[0]),
							ruleTableColumn[1]));
				}
			}
		}

		return rules;
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

	private String[] _getRuleTableColumn(String ruleSQL) {
		Matcher matcher = _rulePattern.matcher(ruleSQL);

		if (!matcher.find()) {
			return null;
		}

		String ruleName = matcher.group(1);

		String[] parts = ruleName.split(StringPool.UNDERLINE, 3);

		return new String[] {parts[1], parts[2]};
	}

	private static final Pattern _rulePattern = Pattern.compile(
		"create.* rule (.*?) as");

}