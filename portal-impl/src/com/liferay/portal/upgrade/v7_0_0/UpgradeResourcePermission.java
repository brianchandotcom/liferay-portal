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

import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.util.PropsValues;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Sampsa Sohlman
 */
public class UpgradeResourcePermission extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			DatabaseMetaData databaseMetaData = con.getMetaData();

			boolean supportsBatchUpdates =
				databaseMetaData.supportsBatchUpdates();

			ps = con.prepareStatement(
				"select resourcePermissionId, primKey, primKeyId, actionIds, " +
					"viewActionId from ResourcePermission");

			rs = ps.executeQuery();

			while (rs.next()) {
				long resourcePermissionId = rs.getLong("resourcePermissionId");
				long primKeyId = rs.getLong("primKeyId");
				long actionIds = rs.getLong("actionIds");
				boolean viewActionId = rs.getBoolean("viewActionId");

				long newPrimKeyId = GetterUtil.getLong(rs.getString("primKey"));
				boolean newViewActionId = (actionIds % 2 == 1);

				if ((primKeyId == newPrimKeyId) &&
					(newViewActionId == viewActionId)) {

					continue;
				}

				updateResourcePermission(
					con, supportsBatchUpdates, resourcePermissionId,
					newPrimKeyId, newViewActionId);
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected void updateResourcePermission(
			Connection con, boolean supportsBatchUpdates,
			long resourcePermissionId, long newPrimKeyId,
			boolean newViewActionId)
		throws Exception {

		PreparedStatement ps = null;

		try {
			ps = con.prepareStatement(
				"update ResourcePermission set primKeyId = ?," +
					"viewActionId = ?  where resourcePermissionId = ?");

			ps.setLong(1, newPrimKeyId);

			if (newViewActionId) {
				ps.setBoolean(2, true);
			}
			else {
				ps.setBoolean(2, false);
			}

			ps.setLong(3, resourcePermissionId);

			int count = 0;

			if (supportsBatchUpdates) {
				ps.addBatch();

				if (count == PropsValues.HIBERNATE_JDBC_BATCH_SIZE) {
					ps.executeBatch();

					count = 0;
				}
				else {
					count++;
				}
			}
			else {
				ps.executeUpdate();
			}

			if (supportsBatchUpdates && (count > 0)) {
				ps.executeBatch();
			}
		}
		finally {
			DataAccess.cleanUp(ps);
		}
	}

}