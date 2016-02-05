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

import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.upgrade.AutoBatchPreparedStatementUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Sampsa Sohlman
 */
public class UpgradeResourcePermission extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		String selectSQL =
			"select resourcePermissionId, primKey, primKeyId, actionIds, " +
				"viewActionId from ResourcePermission";
		String updateSQL =
			"update ResourcePermission set primKeyId = ?, viewActionId = ? " +
				"where resourcePermissionId = ?";

		try (PreparedStatement ps1 = connection.prepareStatement(selectSQL);
			ResultSet rs = ps1.executeQuery();
			PreparedStatement ps2 = AutoBatchPreparedStatementUtil.autoBatch(
				connection.prepareStatement(updateSQL))) {

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

				ps2.setLong(1, newPrimKeyId);

				if (newViewActionId) {
					ps2.setBoolean(2, true);
				}
				else {
					ps2.setBoolean(2, false);
				}

				ps2.setLong(3, resourcePermissionId);

				ps2.addBatch();
			}

			ps2.executeBatch();
		}
	}

}