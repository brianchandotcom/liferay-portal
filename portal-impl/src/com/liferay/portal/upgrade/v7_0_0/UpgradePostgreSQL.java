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
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.StringBundler;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Michael Bowerman
 */
public class UpgradePostgreSQL extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		DB db = DBManagerUtil.getDB();

		if (db.getDBType() != DBType.POSTGRESQL) {
			return;
		}

		Map<String, String> columnsWithOids = getColumnsWithOids();

		updatePostgreSQLRules(columnsWithOids);

		updateOrphanedLargeObjects(columnsWithOids);
	}

	protected HashMap<String, String> getColumnsWithOids() throws Exception {
		HashMap<String, String> columnsWithOids = new HashMap<>();

		PreparedStatement ps = null;
		ResultSet rs = null;

		StringBundler sb = new StringBundler(3);

		sb.append("select table_name, column_name from ");
		sb.append("information_schema.columns where table_schema='public' ");
		sb.append("and data_type='oid';");

		try {
			ps = connection.prepareStatement(sb.toString());

			rs = ps.executeQuery();

			while (rs.next()) {
				String table = (String)rs.getObject("table_name");
				String column = (String)rs.getObject("column_name");

				columnsWithOids.put(table, column);
			}

			return columnsWithOids;
		}

		finally {
			DataAccess.cleanUp(ps, rs);
		}
	}

	protected void updateOrphanedLargeObjects(
			Map<String, String> columnsWithOids)
		throws Exception {

		PreparedStatement ps = null;

		StringBundler sb = new StringBundler();

		sb.append("select lo_unlink(l.oid) from pg_largeobject_metadata l ");
		sb.append("where ");

		int count = 1;
		int size = columnsWithOids.size();

		for (Map.Entry<String, String> column : columnsWithOids.entrySet()) {
			String tableName = column.getKey();
			String columnName = column.getValue();

			sb.append("(not exists (select 1 from ");
			sb.append(tableName);
			sb.append(" t where t.");
			sb.append(columnName);
			sb.append(" = l.oid))");

			if (count < size) {
				sb.append(" and ");
			}

			count++;
		}

		try {
			ps = connection.prepareStatement(sb.toString());

			ps.execute();
		}
		finally {
			DataAccess.cleanUp(ps);
		}
	}

	protected void updatePostgreSQLLargeObjects() throws Exception {
		PreparedStatement ps = null;

		StringBundler sb = new StringBundler();

		sb.append("select lo_unlink (l.oid) from pg_largeobject_metadata l ");
		sb.append("where (not exists (select 1 from dlcontent t where ");
		sb.append("t.data_ = l.oid))");

		try {
			ps = connection.prepareStatement(sb.toString());

			ps.execute();
		}
		finally {
			DataAccess.cleanUp(ps);
		}
	}

	protected void updatePostgreSQLRules(Map<String, String> columnsWithOids)
		throws Exception {

		for (Map.Entry<String, String> entry : columnsWithOids.entrySet()) {
			PreparedStatement ps = null;

			String table = entry.getKey();
			String column = entry.getValue();

			try {
				ps = connection.prepareStatement(
					PostgreSQLDB.getCreateRulesSql(table, column));

				ps.executeUpdate();
			}
			finally {
				DataAccess.cleanUp(ps);
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		UpgradePostgreSQL.class);

}