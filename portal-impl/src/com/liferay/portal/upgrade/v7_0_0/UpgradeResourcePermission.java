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
import com.liferay.portal.upgrade.v7_0_0.util.ResourcePermissionTable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Sampsa Sohlman
 */
public class UpgradeResourcePermission extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		try {
			runSQL(
				"alter table ResourcePermission add mvccVersion LONG " +
				"default 0");
			runSQL(
				"alter table ResourcePermission add primKeyId LONG default -1");
			runSQL(
				"alter table ResourcePermission add viewPermission BOOLEAN " +
				"default [$TRUE$]");
		}
		catch (SQLException sqle)
		{
			upgradeTable(
				ResourcePermissionTable.TABLE_NAME,
				ResourcePermissionTable.TABLE_COLUMNS,
				ResourcePermissionTable.TABLE_SQL_CREATE,
				ResourcePermissionTable.TABLE_SQL_ADD_INDEXES);
		}

		upgradeData();
	}

	protected void upgradeData() throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"SELECT resourcePermissionId, primKey, primKeyId, actionIds, " +
				"viewPermission FROM ResourcePermission");

			rs = ps.executeQuery();

			while (rs.next()) {
				long actionIds = rs.getLong("actionIds");
				long primKeyId = rs.getLong("primKeyId");
				long resourcePermissionId = rs.getLong("resourcePermissionId");
				boolean viewPermission = rs.getBoolean("viewPermission");

				long newPrimKeyId = GetterUtil.getLong(rs.getString("primKey"));
				boolean newViewPermission = (actionIds % 2 == 1);
				String newViewPermissionStr = "[$FALSE$]";

				if (newViewPermission) {
					newViewPermissionStr = "[$TRUE$]";
				}

				if ((newViewPermission!= viewPermission) ||
					(primKeyId!= newPrimKeyId)) {

					runSQL(
						"update ResourcePermission set primKeyId = " +
						newPrimKeyId + ", viewPermission = " +
						newViewPermissionStr +
						" where resourcePermissionId = " +
						resourcePermissionId);
				}
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

}