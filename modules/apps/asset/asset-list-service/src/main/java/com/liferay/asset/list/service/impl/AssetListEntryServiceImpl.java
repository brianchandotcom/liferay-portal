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
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionFactory;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.util.Locale;
import java.util.Map;

/**
 * @author Jürgen Kappler
 */
public class AssetListEntryServiceImpl extends AssetListEntryServiceBaseImpl {

	@Override
	public AssetListEntry addAssetListEntry(
			long userId, long groupId, Map<Locale, String> titleMap,
			Map<Locale, String> descriptionMap, int type,
			ServiceContext serviceContext)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), groupId,
			AssetListActionKeys.ADD_ASSET_LIST_ENTRY);

		return assetListEntryLocalService.addAssetListEntry(
			userId, groupId, titleMap, descriptionMap, type, serviceContext);
	}

	@Override
	public AssetListEntry deleteAssetListEntry(long assetListEntryId)
		throws PortalException {

		_assetListEntryModelResourcePermission.check(
			getPermissionChecker(), assetListEntryId, ActionKeys.DELETE);

		return assetListEntryLocalService.deleteAssetListEntry(
			assetListEntryId);
	}

	@Override
	public AssetListEntry fetchAssetListEntry(long assetListEntryId)
		throws PortalException {

		AssetListEntry assetList =
			assetListEntryLocalService.fetchAssetListEntry(assetListEntryId);

		if (assetList != null) {
			_portletResourcePermission.check(
				getPermissionChecker(), assetList.getGroupId(),
				ActionKeys.VIEW);
		}

		return assetList;
	}

	@Override
	public AssetListEntry updateAssetListEntry(
			long assetListEntryId, Map<Locale, String> titleMap,
			Map<Locale, String> descriptionMap)
		throws PortalException {

		_assetListEntryModelResourcePermission.check(
			getPermissionChecker(), assetListEntryId, ActionKeys.UPDATE);

		return assetListEntryLocalService.updateAssetListEntry(
			assetListEntryId, titleMap, descriptionMap);
	}

	private static volatile ModelResourcePermission<AssetListEntry>
		_assetListEntryModelResourcePermission =
			ModelResourcePermissionFactory.getInstance(
				AssetListEntryServiceImpl.class,
				"_assetListEntryModelResourcePermission", AssetListEntry.class);

	@ServiceReference(
		filterString = "(component.name=com.liferay.asset.list.internal.security.permission.resource.AssetListEntryPortletResourcePermission)",
		type = PortletResourcePermission.class
	)
	private static volatile PortletResourcePermission
		_portletResourcePermission;

}