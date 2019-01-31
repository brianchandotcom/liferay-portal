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

package com.liferay.asset.list.service.impl;

import com.liferay.asset.list.constants.AssetListActionKeys;
import com.liferay.asset.list.constants.AssetListConstants;
import com.liferay.asset.list.model.AssetListEntry;
import com.liferay.asset.list.service.base.AssetListEntryServiceBaseImpl;
import com.liferay.portal.dao.orm.custom.sql.CustomSQL;
import com.liferay.portal.kernel.dao.orm.WildcardMode;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionFactory;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermissionFactory;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.util.List;

/**
 * @author Jürgen Kappler
 */
public class AssetListEntryServiceImpl extends AssetListEntryServiceBaseImpl {

	@Override
	public void addAssetEntrySelection(
			long assetListEntryId, long assetEntryId,
			ServiceContext serviceContext)
		throws PortalException {

		AssetListEntry assetListEntry =
			assetListEntryLocalService.getAssetListEntry(assetListEntryId);

		_assetListEntryModelResourcePermission.check(
			getPermissionChecker(), assetListEntry, ActionKeys.UPDATE);

		assetListEntryLocalService.addAssetEntrySelection(
			assetListEntryId, assetEntryId, serviceContext);
	}

	@Override
	public AssetListEntry addAssetListEntry(
			long groupId, String title, int type, ServiceContext serviceContext)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), groupId,
			AssetListActionKeys.ADD_ASSET_LIST_ENTRY);

		return assetListEntryLocalService.addAssetListEntry(
			getUserId(), groupId, title, type, serviceContext);
	}

	@Override
	public AssetListEntry addDynamicAssetListEntry(
			long userId, long groupId, String title, String typeSettings,
			ServiceContext serviceContext)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), groupId,
			AssetListActionKeys.ADD_ASSET_LIST_ENTRY);

		return assetListEntryLocalService.addDynamicAssetListEntry(
			getUserId(), groupId, title, typeSettings, serviceContext);
	}

	@Override
	public AssetListEntry addManualAssetListEntry(
			long userId, long groupId, String title, long[] assetEntryIds,
			ServiceContext serviceContext)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), groupId,
			AssetListActionKeys.ADD_ASSET_LIST_ENTRY);

		return assetListEntryLocalService.addManualAssetListEntry(
			getUserId(), groupId, title, assetEntryIds, serviceContext);
	}

	@Override
	public void deleteAssetEntrySelection(long assetListEntryId, int position)
		throws PortalException {

		AssetListEntry assetListEntry =
			assetListEntryLocalService.getAssetListEntry(assetListEntryId);

		_assetListEntryModelResourcePermission.check(
			getPermissionChecker(), assetListEntry, ActionKeys.UPDATE);

		assetListEntryLocalService.deleteAssetEntrySelection(
			assetListEntryId, position);
	}

	@Override
	public void deleteAssetListEntries(long[] assetListEntriesIds)
		throws PortalException {

		for (long assetListEntryId : assetListEntriesIds) {
			AssetListEntry assetListEntry =
				assetListEntryLocalService.getAssetListEntry(assetListEntryId);

			_assetListEntryModelResourcePermission.check(
				getPermissionChecker(), assetListEntry, ActionKeys.DELETE);

			assetListEntryLocalService.deleteAssetListEntry(assetListEntry);
		}
	}

	@Override
	public AssetListEntry deleteAssetListEntry(long assetListEntryId)
		throws PortalException {

		AssetListEntry assetListEntry =
			assetListEntryLocalService.getAssetListEntry(assetListEntryId);

		_assetListEntryModelResourcePermission.check(
			getPermissionChecker(), assetListEntry, ActionKeys.DELETE);

		return assetListEntryLocalService.deleteAssetListEntry(assetListEntry);
	}

	@Override
	public AssetListEntry fetchAssetListEntry(long assetListEntryId)
		throws PortalException {

		AssetListEntry assetListEntry =
			assetListEntryLocalService.fetchAssetListEntry(assetListEntryId);

		if (assetListEntry != null) {
			_assetListEntryModelResourcePermission.check(
				getPermissionChecker(), assetListEntry, ActionKeys.VIEW);
		}

		return assetListEntryLocalService.fetchAssetListEntry(assetListEntryId);
	}

	@Override
	public List<AssetListEntry> getAssetListEntries(
		long groupId, int start, int end,
		OrderByComparator<AssetListEntry> orderByComparator) {

		return assetListEntryPersistence.findByGroupId(
			groupId, start, end, orderByComparator);
	}

	@Override
	public List<AssetListEntry> getAssetListEntries(
		long groupId, String title, int start, int end,
		OrderByComparator<AssetListEntry> orderByComparator) {

		return assetListEntryPersistence.findByG_LikeT(
			groupId,
			_customSQL.keywords(title, false, WildcardMode.SURROUND)[0], start,
			end, orderByComparator);
	}

	@Override
	public int getAssetListEntriesCount(long groupId) {
		return assetListEntryPersistence.countByGroupId(groupId);
	}

	@Override
	public int getAssetListEntriesCount(long groupId, String title) {
		return assetListEntryPersistence.countByG_LikeT(
			groupId,
			_customSQL.keywords(title, false, WildcardMode.SURROUND)[0]);
	}

	@Override
	public void moveAssetEntrySelection(
			long assetListEntryId, int position, int newPosition)
		throws PortalException {

		AssetListEntry assetListEntry =
			assetListEntryLocalService.getAssetListEntry(assetListEntryId);

		_assetListEntryModelResourcePermission.check(
			getPermissionChecker(), assetListEntry, ActionKeys.UPDATE);

		assetListEntryLocalService.moveAssetEntrySelection(
			assetListEntryId, position, newPosition);
	}

	@Override
	public AssetListEntry updateAssetListEntry(
			long assetListEntryId, String title)
		throws PortalException {

		AssetListEntry assetListEntry =
			assetListEntryLocalService.getAssetListEntry(assetListEntryId);

		_assetListEntryModelResourcePermission.check(
			getPermissionChecker(), assetListEntry, ActionKeys.UPDATE);

		return assetListEntryLocalService.updateAssetListEntry(
			assetListEntryId, title);
	}

	@Override
	public AssetListEntry updateAssetListEntryTypeSettings(
			long assetListEntryId, String typeSettings)
		throws PortalException {

		AssetListEntry assetListEntry =
			assetListEntryLocalService.getAssetListEntry(assetListEntryId);

		_assetListEntryModelResourcePermission.check(
			getPermissionChecker(), assetListEntry, ActionKeys.UPDATE);

		return assetListEntryLocalService.updateAssetListEntryTypeSettings(
			assetListEntryId, typeSettings);
	}

	@Override
	public AssetListEntry updateAssetListEntryTypeSettingsProperties(
			long assetListEntryId, String typeSettingsProperties)
		throws PortalException {

		AssetListEntry assetListEntry =
			assetListEntryLocalService.getAssetListEntry(assetListEntryId);

		_assetListEntryModelResourcePermission.check(
			getPermissionChecker(), assetListEntry, ActionKeys.UPDATE);

		return assetListEntryLocalService.
			updateAssetListEntryTypeSettingsProperties(
				assetListEntryId, typeSettingsProperties);
	}

	private static volatile ModelResourcePermission<AssetListEntry>
		_assetListEntryModelResourcePermission =
			ModelResourcePermissionFactory.getInstance(
				AssetListEntryServiceImpl.class,
				"_assetListEntryModelResourcePermission", AssetListEntry.class);
	private static volatile PortletResourcePermission
		_portletResourcePermission =
			PortletResourcePermissionFactory.getInstance(
				AssetListEntryServiceImpl.class, "_portletResourcePermission",
				AssetListConstants.RESOURCE_NAME);

	@ServiceReference(type = CustomSQL.class)
	private CustomSQL _customSQL;

}