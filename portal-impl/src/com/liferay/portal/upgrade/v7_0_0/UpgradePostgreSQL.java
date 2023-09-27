/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

		updatePostgreSQLRules(
			HashMapBuilder.put(
				"DLContent", "data_"
			).build());
	}

	protected void updatePostgreSQLRules(Map<String, String> oidColumnNames)
		throws Exception {

		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			for (Map.Entry<String, String> entry : oidColumnNames.entrySet()) {
				String tableName = entry.getKey();
				String columnName = entry.getValue();

				try (PreparedStatement preparedStatement =
						connection.prepareStatement(
							PostgreSQLDB.getCreateRulesSQL(
								tableName, columnName))) {

					preparedStatement.executeUpdate();
				}
			}
		}
	}

}