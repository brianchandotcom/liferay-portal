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

package com.liferay.portal.kernel.upgrade.util;

import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Cristina González
 */
public class UpgradeCompanyIdUtil {

	public static void updateCompanyColumnOnTable(
			String tableName, String select, String update,
			String... columnNames)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;
		PreparedStatement ps2 = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			DatabaseMetaData databaseMetaData = con.getMetaData();

			boolean supportsBatchUpdates =
				databaseMetaData.supportsBatchUpdates();

			ps = con.prepareStatement(select);

			rs = ps.executeQuery();

			ps2 = con.prepareStatement(update);

			int count = 0;

			while (rs.next()) {
				int i = 1;

				for (String columnName : columnNames) {
					ps2.setLong(i++, rs.getLong(columnName));
				}

				if (supportsBatchUpdates) {
					ps2.addBatch();

					int batchSize = GetterUtil.getInteger(
						PropsUtil.get(PropsKeys.HIBERNATE_JDBC_BATCH_SIZE));

					if (count == batchSize) {
						ps2.executeBatch();

						count = 0;
					}
					else {
						count++;
					}
				}
				else {
					ps2.executeUpdate();
				}

				if (supportsBatchUpdates && (count > 0)) {
					ps.executeBatch();
				}
			}
		}
		catch (SQLException sqle) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"The companyId was not updated in " + tableName, sqle);
			}
		}
		finally {
			DataAccess.cleanUp(con, ps2);
			DataAccess.cleanUp(con, ps, rs);
		}

		checkNotUpdated(tableName, columnNames);
	}

	protected static void checkNotUpdated(
			String tableName, String... columnNames)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			StringBuilder sbSelect = new StringBuilder();

			sbSelect.append("select ");

			for (String columnName : columnNames) {
				sbSelect.append(columnName);
				sbSelect.append(", ");
			}

			sbSelect.setLength(sbSelect.length() - 2);

			sbSelect.append(" from ");
			sbSelect.append(tableName);
			sbSelect.append(" where companyId IS NULL");

			ps = con.prepareStatement(sbSelect.toString());

			rs = ps.executeQuery();

			if (!rs.next()) {
				return;
			}

			StringBuilder sb = new StringBuilder();

			sb.append("Has not be posible to update the companyId of ");
			sb.append("the following rows in the table ");
			sb.append(tableName);

			while (rs.next()) {
				sb.append(" {");

				for (String columnName : columnNames) {
					long columnValue = rs.getLong(columnName);

					sb.append(columnName);
					sb.append(": ");
					sb.append(columnValue);
					sb.append(", ");
				}

				sb.setLength(sb.length() - 2);

				sb.append("}");
			}

			if (_log.isInfoEnabled()) {
				_log.info(sb);
			}
		}
		catch (SQLException sqle) {
			if (_log.isWarnEnabled()) {
				_log.warn(sqle.getMessage(), sqle);
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		UpgradeCompanyIdUtil.class);

}