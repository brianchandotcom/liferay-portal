/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.upgrade.v7_0_3;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.db.DBType;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.LoggingTimer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Alberto Chaparro
 */
public class UpgradeOracle extends UpgradeProcess {

	protected void alterVarchar2Columns() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer();
			PreparedStatement preparedStatement = connection.prepareStatement(
				"select table_name, column_name, data_length from " +
					"user_tab_columns where data_type = 'VARCHAR2' and " +
						"char_used = 'B'");
			ResultSet resultSet = preparedStatement.executeQuery()) {

			while (resultSet.next()) {
				String tableName = resultSet.getString(1);

				if (!isPortal62TableName(tableName)) {
					continue;
				}

				String columnName = resultSet.getString(2);

				try {
					runSQL(
						StringBundler.concat(
							"alter table ", tableName, " modify ", columnName,
							" varchar2(", resultSet.getInt(3), " char)"));
				}
				catch (SQLException sqlException) {
					if (sqlException.getErrorCode() == 1441) {
						if (_log.isWarnEnabled()) {
							_log.warn(
								StringBundler.concat(
									"Unable to alter length of column ",
									columnName, " for table ", tableName,
									" because it contains values that are ",
									"larger than the new column length"));
						}
					}
					else {
						throw sqlException;
					}
				}
			}
		}
	}

	@Override
	protected void doUpgrade() throws Exception {
		DB db = DBManagerUtil.getDB();

		if (db.getDBType() != DBType.ORACLE) {
			return;
		}

		alterVarchar2Columns();
	}

	private static final Log _log = LogFactoryUtil.getLog(UpgradeOracle.class);

}