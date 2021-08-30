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

package com.liferay.asset.display.page.internal.upgrade.v2_1_0;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalService;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryService;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.LoggingTimer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import java.util.HashMap;
import java.util.Optional;

/**
 * @author Pavel Savinov
 */
public class AssetDisplayLayoutUpgradeProcess extends UpgradeProcess {

	public AssetDisplayLayoutUpgradeProcess(
		AssetEntryLocalService assetEntryLocalService,
		LayoutLocalService layoutLocalService,
		LayoutPageTemplateEntryLocalService layoutPageTemplateEntryLocalService,
		LayoutPageTemplateEntryService layoutPageTemplateEntryService) {

		_assetEntryLocalService = assetEntryLocalService;
		_layoutLocalService = layoutLocalService;
		_layoutPageTemplateEntryLocalService =
			layoutPageTemplateEntryLocalService;
		_layoutPageTemplateEntryService = layoutPageTemplateEntryService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		_upgradeAssetDisplayLayouts();
		_upgradeSchema();
	}

	private long _getPlid(
			AssetEntry assetEntry, long userId, long groupId,
			long layoutPageTemplateEntryId, ServiceContext serviceContext)
		throws PortalException {

		LayoutPageTemplateEntry layoutPageTemplateEntry = Optional.ofNullable(
			_layoutPageTemplateEntryLocalService.fetchLayoutPageTemplateEntry(
				layoutPageTemplateEntryId)
		).orElseGet(
			() ->
				_layoutPageTemplateEntryService.
					fetchDefaultLayoutPageTemplateEntry(
						groupId, assetEntry.getClassNameId(),
						assetEntry.getClassTypeId())
		);

		if (layoutPageTemplateEntry == null) {
			return 0;
		}

		if (layoutPageTemplateEntry.getPlid() > 0) {
			return layoutPageTemplateEntry.getPlid();
		}

		serviceContext.setAttribute(
			"layout.instanceable.allowed", Boolean.TRUE);

		Layout layout = _layoutLocalService.addLayout(
			userId, groupId, false, 0, assetEntry.getTitleMap(),
			assetEntry.getTitleMap(), assetEntry.getDescriptionMap(), null,
			null, LayoutConstants.TYPE_ASSET_DISPLAY, StringPool.BLANK, true,
			true, new HashMap<>(), serviceContext);

		layoutPageTemplateEntry.setPlid(layout.getPlid());

		_layoutPageTemplateEntryLocalService.updateLayoutPageTemplateEntry(
			layoutPageTemplateEntry);

		return layout.getPlid();
	}

	private void _upgradeAssetDisplayLayouts() throws Exception {
		ActionableDynamicQuery actionableDynamicQuery =
			_layoutLocalService.getActionableDynamicQuery();

		actionableDynamicQuery.setAddCriteriaMethod(
			dynamicQuery -> dynamicQuery.add(
				RestrictionsFactoryUtil.eq(
					"type", LayoutConstants.TYPE_ASSET_DISPLAY)));
		actionableDynamicQuery.setPerformActionMethod(
			(Layout layout) -> {
				layout.setSystem(true);

				_layoutLocalService.updateLayout(layout);
			});

		actionableDynamicQuery.performActions();
	}

	private void _upgradeSchema() throws Exception {
		alterTableAddColumn("AssetDisplayPageEntry", "plid", "LONG");

		ServiceContext serviceContext = new ServiceContext();

		try (LoggingTimer loggingTimer = new LoggingTimer();
			Statement s = connection.createStatement();
			ResultSet resultSet = s.executeQuery(
				StringBundler.concat(
					"select assetDisplayPageEntryId, userId, groupId, ",
					"classNameId, classPK, layoutPageTemplateEntryId from ",
					"AssetDisplayPageEntry where plid is null or plid = 0"));
			PreparedStatement preparedStatement =
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

				long userId = resultSet.getLong("userId");
				long groupId = resultSet.getLong("groupId");
				long layoutPageTemplateEntryId = resultSet.getLong(
					"layoutPageTemplateEntryId");

				preparedStatement.setLong(
					1,
					_getPlid(
						assetEntry, userId, groupId, layoutPageTemplateEntryId,
						serviceContext));

				long assetDisplayPageEntryId = resultSet.getLong(
					"assetDisplayPageEntryId");

				preparedStatement.setLong(2, assetDisplayPageEntryId);

				preparedStatement.addBatch();
			}

			preparedStatement.executeBatch();
		}
	}

	private final AssetEntryLocalService _assetEntryLocalService;
	private final LayoutLocalService _layoutLocalService;
	private final LayoutPageTemplateEntryLocalService
		_layoutPageTemplateEntryLocalService;
	private final LayoutPageTemplateEntryService
		_layoutPageTemplateEntryService;

}