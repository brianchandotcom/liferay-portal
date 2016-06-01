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

import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LoggingTimer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Sampsa Sohlman
 */
public class UpgradeResourcePermission extends UpgradeProcess {

	protected void createIndex() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			runSQLTemplateString(
				"create index IX_D5F1E2A2 on ResourcePermission " +
					"(name[$COLUMN_LENGTH:255$])",
				false, false);
		}
	}

	@Override
	protected void doUpgrade() throws Exception {
		createIndex();

		upgradeResourcePermissions();
	}

	protected void upgradeResourcePermissions() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			String selectSQL =
				"select resourcePermissionId, primKey, actionIds from " +
					"ResourcePermission";
			String updateSQL =
				"update ResourcePermission set primKeyId = ?, viewActionId = " +
					"? where resourcePermissionId = ?";

			try (PreparedStatement ps1 = connection.prepareStatement(selectSQL);
				ResultSet rs = ps1.executeQuery();
				PreparedStatement ps2 =
					AutoBatchPreparedStatementUtil.concurrentAutoBatch(
						connection, updateSQL)) {

				while (rs.next()) {
					long resourcePermissionId = rs.getLong(
						"resourcePermissionId");
					long actionIds = rs.getLong("actionIds");

					long newPrimKeyId = GetterUtil.getLong(
						rs.getString("primKey"));

					boolean newViewActionId = false;

					if ((actionIds % 2) == 1) {
						newViewActionId = true;
					}

					if ((newPrimKeyId == 0) && !newViewActionId) {
						continue;
					}

					ps2.setLong(1, newPrimKeyId);
					ps2.setBoolean(2, newViewActionId);
					ps2.setLong(3, resourcePermissionId);

					ps2.addBatch();
				}

				ps2.executeBatch();
			}
		}
	}

}