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

import com.liferay.asset.list.model.AssetListEntry;
import com.liferay.asset.list.service.base.AssetListEntryServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;

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

		return assetListEntryLocalService.addAssetListEntry(
			userId, groupId, titleMap, descriptionMap, type, serviceContext);
	}

	@Override
	public AssetListEntry deleteAssetListEntry(long assetListEntryId)
		throws PortalException {

		return assetListEntryLocalService.deleteAssetListEntry(
			assetListEntryId);
	}

	@Override
	public AssetListEntry fetchAssetListEntry(long assetListEntryId) {
		return assetListEntryLocalService.fetchAssetListEntry(assetListEntryId);
	}

	@Override
	public AssetListEntry updateAssetListEntry(
			long assetListEntryId, Map<Locale, String> titleMap,
			Map<Locale, String> descriptionMap)
		throws PortalException {

		return assetListEntryLocalService.updateAssetListEntry(
			assetListEntryId, titleMap, descriptionMap);
	}

}