/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.upgrade.v6_0_12_to_6_1_0;

import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBFactoryUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.util.UpgradeTable;
import com.liferay.portal.kernel.upgrade.util.UpgradeTableFactoryUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.sql.Types;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Miguel Pastor
 */
public class UpgradeQuartz extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		DB db = DBFactoryUtil.getDB();

		String dbType = db.getType();

		if (!dbType.equals(DB.TYPE_POSTGRESQL)) {
			return;
		}

		Class<?> clazz = getClass();

		ClassLoader classLoader = clazz.getClassLoader();

		String quartzTableSQL = StringUtil.read(
			classLoader, QUARTZ_TABLES_DEPENDENCIES);

		String[] createTableSQLs = StringUtil.split(quartzTableSQL, ";");

		for (String createTableSQL : createTableSQLs) {
			createTableSQL = createTableSQL.trim();

			int x = createTableSQL.indexOf("(");

			String tableName = createTableSQL.substring(13, x - 1);

			String[] columnSQLs = StringUtil.split(
				createTableSQL.substring(x + 1, createTableSQL.length() - 1));

			List<Object[]> columns = new ArrayList<Object[]>();

			for (String columnSQL : columnSQLs) {
				if (columnSQL.contains("primary key")) {
					break;
				}

				columnSQL = StringUtil.replace(columnSQL, "\n", "");
				columnSQL = columnSQL.trim();

				x = columnSQL.indexOf(" ");

				int y = columnSQL.indexOf(" ", x + 1);

				if (y == -1) {
					y = columnSQL.length();
				}

				String columnName = columnSQL.substring(0, x);
				String columnType = columnSQL.substring(x + 1, y);

				Object[] column = {columnName, getTypes(columnType)};

				columns.add(column);
			}

			UpgradeTable upgradeTable = UpgradeTableFactoryUtil.getUpgradeTable(
				tableName, columns.toArray(new Object[columns.size()][]));

			upgradeTable.setCreateSQL(createTableSQL);

			upgradeTable.updateTable();
		}
	}

	protected int getTypes(String columnType) {
		if (columnType.equals("BOOLEAN")) {
			return Types.BOOLEAN;
		}
		else if (columnType.equals("INTEGER")) {
			return Types.INTEGER;
		}
		else if (columnType.equals("LONG")) {
			return Types.BIGINT;
		}
		else if (columnType.equals("SBLOB")) {
			return Types.BLOB;
		}
		else if (columnType.startsWith("VARCHAR")) {
			return Types.VARCHAR;
		}
		else {
			throw new RuntimeException("Invalid column type " + columnType);
		}
	}

	protected static final String QUARTZ_TABLES_DEPENDENCIES =
		"com/liferay/portal/upgrade/v6_0_12_to_6_1_0/dependencies/" +
			"quartz-tables.sql";

}