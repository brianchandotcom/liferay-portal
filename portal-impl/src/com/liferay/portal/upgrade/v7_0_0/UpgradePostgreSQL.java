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

package com.liferay.portal.upgrade.v7_0_0;

import com.liferay.portal.dao.db.PostgreSQLDB;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.db.DBType;
import com.liferay.portal.kernel.upgrade.UpgradeException;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.StringBundler;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Michael Bowerman
 * @author Amadea Fejes
 */
public class UpgradePostgreSQL extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		DB db = DBManagerUtil.getDB();

		if (db.getDBType() != DBType.POSTGRESQL) {
			return;
		}

		Map<String, String> oidColumnNames = new HashMap<>();

		oidColumnNames.put("DLContent", "data_");

		updatePostgreSQLRules(oidColumnNames);
	}

	/**
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
	protected String getCurrentSchema() throws Exception {
		try (PreparedStatement ps = connection.prepareStatement(
				"select current_schema();");
			ResultSet rs = ps.executeQuery()) {

			if (rs.next()) {
				return (String)rs.getObject("current_schema");
			}

			return null;
		}
	}

	/**
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
	protected Map<String, String> getOidColumnNames() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			Map<String, String> columnsWithOids = new HashMap<>();

			StringBundler sb = new StringBundler(4);

			sb.append("select table_name, column_name from ");
			sb.append("information_schema.columns where table_schema='");

			String schema = getCurrentSchema();

			if (schema == null) {
				throw new UpgradeException("Unable to get current schema");
			}

			sb.append(schema);

			sb.append("' and data_type='oid';");

			try (PreparedStatement ps = connection.prepareStatement(
					sb.toString());
				ResultSet rs = ps.executeQuery()) {

				while (rs.next()) {
					String tableName = (String)rs.getObject("table_name");
					String columnName = (String)rs.getObject("column_name");

					columnsWithOids.put(tableName, columnName);
				}

				return columnsWithOids;
			}
		}
	}

	/**
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
	protected void updateOrphanedLargeObjects(
			Map<String, String> oidColumnNames)
		throws Exception {

		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			StringBundler sb = new StringBundler();

			sb.append("select lo_unlink(l.oid) from pg_largeobject_metadata ");
			sb.append("l where ");

			int i = 1;

			for (Map.Entry<String, String> column : oidColumnNames.entrySet()) {
				String tableName = column.getKey();
				String columnName = column.getValue();

				sb.append("(not exists (select 1 from ");
				sb.append(tableName);
				sb.append(" t where t.");
				sb.append(columnName);
				sb.append(" = l.oid))");

				if (i < oidColumnNames.size()) {
					sb.append(" and ");
				}

				i++;
			}

			try (PreparedStatement ps = connection.prepareStatement(
					sb.toString())) {

				ps.execute();
			}
		}
	}

	protected void updatePostgreSQLRules(Map<String, String> oidColumnNames)
		throws Exception {

		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			for (Map.Entry<String, String> entry : oidColumnNames.entrySet()) {
				String tableName = entry.getKey();
				String columnName = entry.getValue();

				try (PreparedStatement ps = connection.prepareStatement(
						PostgreSQLDB.getCreateRulesSQL(
							tableName, columnName))) {

					ps.executeUpdate();
				}
			}
		}
	}

}