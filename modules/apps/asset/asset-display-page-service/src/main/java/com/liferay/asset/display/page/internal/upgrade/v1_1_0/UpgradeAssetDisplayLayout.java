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

package com.liferay.asset.display.page.internal.upgrade.v1_1_0;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.UnicodeProperties;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import java.util.HashMap;

/**
 * @author Pavel Savinov
 */
public class UpgradeAssetDisplayLayout extends UpgradeProcess {

	public UpgradeAssetDisplayLayout(
		AssetEntryLocalService assetEntryLocalService,
		LayoutLocalService layoutLocalService) {

		_assetEntryLocalService = assetEntryLocalService;
		_layoutLocalService = layoutLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		StringBundler sb = new StringBundler(3);

		sb.append("select assetDisplayPageEntryId, userId, groupId, ");
		sb.append("classNameId, classPK from AssetDisplayPageEntry where ");
		sb.append("plid is null or plid = 0");

		ServiceContext serviceContext = new ServiceContext();

		try (LoggingTimer loggingTimer = new LoggingTimer();
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sb.toString())) {

			try (PreparedStatement ps =
					AutoBatchPreparedStatementUtil.autoBatch(
						connection.prepareStatement(
							"update AssetDisplayPageEntry set plid = ? where " +
								"assetDisplayPageEntryId = ?"))) {

				while (resultSet.next()) {
					long classNameId = resultSet.getLong("classNameId");
					long classPK = resultSet.getLong("classPK");

					AssetEntry assetEntry = _assetEntryLocalService.fetchEntry(
						classNameId, classPK);

					if (assetEntry == null) {
						continue;
					}

					long assetDisplayPageEntryId = resultSet.getLong(
						"assetDisplayPageEntryId");
					long userId = resultSet.getLong("userId");
					long groupId = resultSet.getLong("groupId");

					UnicodeProperties typeSettingsProperties =
						new UnicodeProperties();

					typeSettingsProperties.put(
						"visible", Boolean.FALSE.toString());

					serviceContext.setAttribute(
						"layout.instanceable.allowed", Boolean.TRUE);

					Layout layout = _layoutLocalService.addLayout(
						userId, groupId, false, 0, assetEntry.getTitleMap(),
						assetEntry.getTitleMap(),
						assetEntry.getDescriptionMap(), null, null,
						"asset_display", typeSettingsProperties.toString(),
						true, new HashMap<>(), serviceContext);

					ps.setLong(1, layout.getPlid());

					ps.setLong(2, assetDisplayPageEntryId);

					ps.addBatch();
				}

				ps.executeBatch();
			}
		}
	}

	private final AssetEntryLocalService _assetEntryLocalService;
	private final LayoutLocalService _layoutLocalService;

}