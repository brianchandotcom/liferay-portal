/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.verify;

import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBFactoryUtil;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ReleaseInfo;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Ivica Cardic
 * @author Brian Wing Shun Chan
 */
public class VerifyOracle extends VerifyProcess {

	protected void alterVarchar2Columns() throws Exception {
		int buildNumber = getBuildNumber();

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"select table_name, column_name, data_length from " +
					"user_tab_columns where data_type = 'VARCHAR2' and " +
						"char_used = 'B'");

			rs = ps.executeQuery();

			while (rs.next()) {
				String tableName = rs.getString(1);

				if (!isPortalTableName(tableName)) {
					continue;
				}

				String columnName = rs.getString(2);
				int dataLength = rs.getInt(3);

				if (isBetweenBuildNumbers(
						buildNumber, ReleaseInfo.RELEASE_5_2_9_BUILD_NUMBER,
						ReleaseInfo.RELEASE_6_0_0_BUILD_NUMBER) ||
					isBetweenBuildNumbers(
						buildNumber, ReleaseInfo.RELEASE_6_0_5_BUILD_NUMBER,
						ReleaseInfo.RELEASE_6_1_20_BUILD_NUMBER)) {

					// LPS-33903

					if (!ArrayUtil.contains(
							_ORIGINAL_DATA_LENGTH_VALUES, dataLength)) {

						dataLength = dataLength / 4;
					}
				}

				try {
					runSQL(
						"alter table " + tableName + " modify " + columnName +
							" varchar2(" + dataLength + " char)");
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
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected void convertColumnToClob(String tableName, String columnName)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			StringBundler sb = new StringBundler(6);

			sb.append("select data_type from user_tab_columns where ");
			sb.append("table_name = '");
			sb.append(StringUtil.toUpperCase(tableName));
			sb.append("' and column_name = '");
			sb.append(StringUtil.toUpperCase(columnName));
			sb.append(StringPool.APOSTROPHE);

			ps = con.prepareStatement(sb.toString());

			rs = ps.executeQuery();

			if (!rs.next()) {
				if (_log.isWarnEnabled()) {
					sb = new StringBundler(5);

					sb.append("Column ");
					sb.append(columnName);
					sb.append(" in table ");
					sb.append(tableName);
					sb.append(" could not be found.");

					_log.warn(sb.toString());
				}

				return;
			}

			String dataType = rs.getString(1);

			if (dataType.equals("CLOB")) {
				return;
			}

			runSQL("alter table " + tableName + " add temp CLOB");

			sb = new StringBundler(4);

			sb.append("update ");
			sb.append(tableName);
			sb.append(" set temp = ");
			sb.append(columnName);

			runSQL(sb.toString());

			sb = new StringBundler(4);

			sb.append("alter table ");
			sb.append(tableName);
			sb.append(" drop column ");
			sb.append(columnName);

			runSQL(sb.toString());

			sb = new StringBundler(4);

			sb.append("alter table ");
			sb.append(tableName);
			sb.append(" rename column temp to ");
			sb.append(columnName);

			runSQL(sb.toString());
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	@Override
	protected void doVerify() throws Exception {
		DB db = DBFactoryUtil.getDB();

		String dbType = db.getType();

		if (!dbType.equals(DB.TYPE_ORACLE)) {
			return;
		}

		alterVarchar2Columns();

		convertColumnToClob("AssetEntry", "description");
		convertColumnToClob("AssetEntry", "summary");
		convertColumnToClob("ExpandoColumn", "defaultData");
		convertColumnToClob("ExpandoValue", "data_");
		convertColumnToClob("JournalArticle", "description");
		convertColumnToClob("ShoppingCart", "itemIds");
		convertColumnToClob("ShoppingOrder", "comments");
	}

	protected boolean isBetweenBuildNumbers(
		int buildNumber, int startBuildNumber, int endBuildNumber) {

		if ((buildNumber >= startBuildNumber) &&
			(buildNumber < endBuildNumber)) {

			return true;
		}

		return false;
	}

	private static final int[] _ORIGINAL_DATA_LENGTH_VALUES = {
		75, 100, 150, 200, 255, 500, 1000, 1024, 2000, 4000
	};

	private static Log _log = LogFactoryUtil.getLog(VerifyOracle.class);

}