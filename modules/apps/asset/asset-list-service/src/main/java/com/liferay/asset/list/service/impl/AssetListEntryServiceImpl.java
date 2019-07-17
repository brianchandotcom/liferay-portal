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
import com.liferay.asset.list.model.AssetListEntry;
import com.liferay.asset.list.service.base.AssetListEntryServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.dao.orm.custom.sql.CustomSQL;
import com.liferay.portal.kernel.dao.orm.WildcardMode;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jürgen Kappler
 */
@Component(
	property = {
		"json.web.service.context.name=assetlist",
		"json.web.service.context.path=AssetListEntry"
	},
	service = AopService.class
)
public class AssetListEntryServiceImpl extends AssetListEntryServiceBaseImpl {

	@Override
	public void addAssetEntrySelection(
			long assetListEntryId, long assetEntryId, long segmentsEntryId,
			ServiceContext serviceContext)
		throws PortalException {

		_assetListEntryModelResourcePermission.check(
			getPermissionChecker(),
			assetListEntryLocalService.getAssetListEntry(assetListEntryId),
			ActionKeys.UPDATE);

		assetListEntryLocalService.addAssetEntrySelection(
			assetListEntryId, assetEntryId, segmentsEntryId, serviceContext);
	}

	@Override
	public void addAssetEntrySelections(
			long assetListEntryId, long[] assetEntryIds, long segmentsEntryId,
			ServiceContext serviceContext)
		throws PortalException {

		_assetListEntryModelResourcePermission.check(
			getPermissionChecker(),
			assetListEntryLocalService.getAssetListEntry(assetListEntryId),
			ActionKeys.UPDATE);

		assetListEntryLocalService.addAssetEntrySelections(
			assetListEntryId, assetEntryIds, segmentsEntryId, serviceContext);
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
	public void deleteAssetEntrySelection(
			long assetListEntryId, long segmentsEntryId, int position)
		throws PortalException {

		_assetListEntryModelResourcePermission.check(
			getPermissionChecker(),
			assetListEntryLocalService.getAssetListEntry(assetListEntryId),
			ActionKeys.UPDATE);

		assetListEntryLocalService.deleteAssetEntrySelection(
			assetListEntryId, segmentsEntryId, position);
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
	public void deleteAssetListEntry(
			long assetListEntryId, long segmentsEntryId)
		throws PortalException {

		_assetListEntryModelResourcePermission.check(
			getPermissionChecker(),
			assetListEntryLocalService.getAssetListEntry(assetListEntryId),
			ActionKeys.UPDATE);

		assetListEntryLocalService.deleteAssetListEntry(
			assetListEntryId, segmentsEntryId);
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

		return assetListEntry;
	}

	@Override
	public List<AssetListEntry> getAssetListEntries(
		long groupId, int start, int end,
		OrderByComparator<AssetListEntry> orderByComparator) {

		return assetListEntryPersistence.filterFindByGroupId(
			groupId, start, end, orderByComparator);
	}

	@Override
	public List<AssetListEntry> getAssetListEntries(
		long groupId, String title, int start, int end,
		OrderByComparator<AssetListEntry> orderByComparator) {

		return assetListEntryPersistence.filterFindByG_LikeT(
			groupId,
			_customSQL.keywords(title, false, WildcardMode.SURROUND)[0], start,
			end, orderByComparator);
	}

	@Override
	public int getAssetListEntriesCount(long groupId) {
		return assetListEntryPersistence.filterCountByGroupId(groupId);
	}

	@Override
	public int getAssetListEntriesCount(long groupId, String title) {
		return assetListEntryPersistence.filterCountByG_LikeT(
			groupId,
			_customSQL.keywords(title, false, WildcardMode.SURROUND)[0]);
	}

	@Override
	public AssetListEntry getAssetListEntry(long assetListEntryId)
		throws PortalException {

		AssetListEntry assetListEntry =
			assetListEntryLocalService.getAssetListEntry(assetListEntryId);

		_assetListEntryModelResourcePermission.check(
			getPermissionChecker(), assetListEntry, ActionKeys.VIEW);

		return assetListEntry;
	}

	@Override
	public AssetListEntry getAssetListEntry(
			long groupId, String assetListEntryKey)
		throws PortalException {

		AssetListEntry assetListEntry =
			assetListEntryLocalService.getAssetListEntry(
				groupId, assetListEntryKey);

		_assetListEntryModelResourcePermission.check(
			getPermissionChecker(), assetListEntry, ActionKeys.VIEW);

		return assetListEntry;
	}

	@Override
	public AssetListEntry getAssetListEntryByUuidAndGroupId(
			String uuid, long groupId)
		throws PortalException {

		AssetListEntry assetListEntry =
			assetListEntryLocalService.getAssetListEntryByUuidAndGroupId(
				uuid, groupId);

		_assetListEntryModelResourcePermission.check(
			getPermissionChecker(), assetListEntry, ActionKeys.VIEW);

		return assetListEntry;
	}

	@Override
	public void moveAssetEntrySelection(
			long assetListEntryId, long segmentsEntryId, int position,
			int newPosition)
		throws PortalException {

		_assetListEntryModelResourcePermission.check(
			getPermissionChecker(),
			assetListEntryLocalService.getAssetListEntry(assetListEntryId),
			ActionKeys.UPDATE);

		assetListEntryLocalService.moveAssetEntrySelection(
			assetListEntryId, segmentsEntryId, position, newPosition);
	}

	@Override
	public void updateAssetListEntry(
			long assetListEntryId, long segmentsEntryId, String typeSettings,
			ServiceContext serviceContext)
		throws PortalException {

		_assetListEntryModelResourcePermission.check(
			getPermissionChecker(),
			assetListEntryLocalService.getAssetListEntry(assetListEntryId),
			ActionKeys.UPDATE);

		assetListEntryLocalService.updateAssetListEntry(
			assetListEntryId, segmentsEntryId, typeSettings, serviceContext);
	}

	@Override
	public AssetListEntry updateAssetListEntry(
			long assetListEntryId, String title)
		throws PortalException {

		_assetListEntryModelResourcePermission.check(
			getPermissionChecker(),
			assetListEntryLocalService.getAssetListEntry(assetListEntryId),
			ActionKeys.UPDATE);

		return assetListEntryLocalService.updateAssetListEntry(
			assetListEntryId, title);
	}

	@Override
	public void updateAssetListEntryTypeSettings(
			long assetListEntryId, long segmentsEntryId, String typeSettings)
		throws PortalException {

		_assetListEntryModelResourcePermission.check(
			getPermissionChecker(),
			assetListEntryLocalService.getAssetListEntry(assetListEntryId),
			ActionKeys.UPDATE);

		assetListEntryLocalService.updateAssetListEntryTypeSettings(
			assetListEntryId, segmentsEntryId, typeSettings);
	}

	@Reference(
		target = "(model.class.name=com.liferay.asset.list.model.AssetListEntry)"
	)
	private ModelResourcePermission<AssetListEntry>
		_assetListEntryModelResourcePermission;

	@Reference
	private CustomSQL _customSQL;

	@Reference(target = "(resource.name=com.liferay.asset.list)")
	private PortletResourcePermission _portletResourcePermission;

}