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

package com.liferay.portal.upgrade.v7_0_3;

import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.db.DBType;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.ReleaseConstants;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.ReleaseInfo;
import com.liferay.portal.kernel.util.StringBundler;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Alberto Chaparro
 */
public class UpgradeOracle extends UpgradeProcess {

	protected void alterVarchar2Columns() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer();
			PreparedStatement ps = connection.prepareStatement(
				"select table_name, column_name, data_length from " +
					"user_tab_columns where data_type = 'VARCHAR2' and " +
						"char_used = 'B'");
			ResultSet rs = ps.executeQuery()) {

			int buildNumber = getBuildNumber();

			while (rs.next()) {
				String tableName = rs.getString(1);

				if (!isPortal62TableName(tableName)) {
					continue;
				}

				String columnName = rs.getString(2);
				int dataLength = rs.getInt(3);

				if (buildNumber < ReleaseInfo.RELEASE_6_2_0_BUILD_NUMBER) {

					// LPS-33903

					if (!ArrayUtil.contains(
							_ORIGINAL_DATA_LENGTH_VALUES, dataLength)) {

						dataLength = dataLength / 4;
					}
				}

				try {
					runSQL(
						StringBundler.concat(
							"alter table ", tableName, " modify ", columnName,
							" varchar2(", String.valueOf(dataLength),
							" char)"));
				}
				catch (SQLException sqle) {
					if (sqle.getErrorCode() == 1441) {
						if (_log.isWarnEnabled()) {
							StringBundler sb = new StringBundler(6);

							sb.append("Unable to alter length of column ");
							sb.append(columnName);
							sb.append(" for table ");
							sb.append(tableName);
							sb.append(" because it contains values that are ");
							sb.append("larger than the new column length");

							_log.warn(sb.toString());
						}
					}
					else {
						throw sqle;
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

	protected int getBuildNumber() throws Exception {
		try (PreparedStatement ps = connection.prepareStatement(
				"select buildNumber from Release_ where servletContextName = " +
					"?")) {

			ps.setString(1, ReleaseConstants.DEFAULT_SERVLET_CONTEXT_NAME);

			try (ResultSet rs = ps.executeQuery()) {
				rs.next();

				return rs.getInt(1);
			}
		}
	}

	private static final int[] _ORIGINAL_DATA_LENGTH_VALUES =
		{75, 100, 150, 200, 255, 500, 1000, 1024, 2000, 4000};

	private static final Log _log = LogFactoryUtil.getLog(UpgradeOracle.class);

}