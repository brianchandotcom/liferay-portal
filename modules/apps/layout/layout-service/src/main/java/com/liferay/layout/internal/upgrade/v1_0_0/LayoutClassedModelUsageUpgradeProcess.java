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

package com.liferay.layout.internal.upgrade.v1_0_0;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * @author Eudaldo Alonso
 */
public class LayoutClassedModelUsageUpgradeProcess extends UpgradeProcess {

	public LayoutClassedModelUsageUpgradeProcess(
		AssetEntryLocalService assetEntryLocalService) {

		_assetEntryLocalService = assetEntryLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		_upgradeSchema();

		if (hasTable("AssetEntryUsage")) {
			_upgradeLayoutClassedModelUsage();
		}
	}

	private void _upgradeLayoutClassedModelUsage() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer();
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(
				"select groupId, assetEntryId, containerKey, containerType, " +
					"plid, type_ from AssetEntryUsage");
			PreparedStatement preparedStatement =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					StringBundler.concat(
						"insert into LayoutClassedModelUsage (mvccVersion, ",
						"uuid_, layoutClassedModelUsageId, groupId, ",
						"createDate, modifiedDate, classNameId, classPK, ",
						"containerKey, containerType, plid, type_ ) values ",
						"(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"))) {

			while (resultSet.next()) {
				long assetEntryId = resultSet.getLong("assetEntryId");
				long plid = resultSet.getLong("plid");

				AssetEntry assetEntry = _assetEntryLocalService.fetchEntry(
					assetEntryId);

				if ((assetEntry == null) || (plid <= 0)) {
					continue;
				}

				long groupId = resultSet.getLong("groupId");
				String containerKey = resultSet.getString("containerKey");
				long containerType = resultSet.getLong("containerType");
				int type = resultSet.getInt("type_");

				preparedStatement.setLong(1, 0);
				preparedStatement.setString(2, PortalUUIDUtil.generate());
				preparedStatement.setLong(3, increment());
				preparedStatement.setLong(4, groupId);
				preparedStatement.setDate(
					5, new Date(System.currentTimeMillis()));
				preparedStatement.setDate(
					6, new Date(System.currentTimeMillis()));
				preparedStatement.setLong(7, assetEntry.getClassNameId());
				preparedStatement.setLong(8, assetEntry.getClassPK());
				preparedStatement.setString(9, containerKey);
				preparedStatement.setLong(10, containerType);
				preparedStatement.setLong(11, plid);
				preparedStatement.setInt(12, type);

				preparedStatement.addBatch();
			}

			preparedStatement.executeBatch();
		}
	}

	private void _upgradeSchema() throws Exception {
		String template = StringUtil.read(
			LayoutClassedModelUsageUpgradeProcess.class.getResourceAsStream(
				"dependencies/update.sql"));

		runSQLTemplateString(template, false);
	}

	private final AssetEntryLocalService _assetEntryLocalService;

}