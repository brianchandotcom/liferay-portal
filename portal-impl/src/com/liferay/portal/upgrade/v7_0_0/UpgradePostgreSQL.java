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
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LoggingTimer;

import java.sql.PreparedStatement;

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

		Map<String, String> oidColumnNames = HashMapBuilder.put(
			"DLContent", "data_"
		).build();

		updatePostgreSQLRules(oidColumnNames);
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