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

package com.liferay.asset.display.page.service.impl;

import com.liferay.asset.display.page.model.AssetDisplayPageEntry;
import com.liferay.asset.display.page.service.base.AssetDisplayPageEntryLocalServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Eudaldo Alonso
 */
public class AssetDisplayPageEntryLocalServiceImpl
	extends AssetDisplayPageEntryLocalServiceBaseImpl {

	@Override
	public AssetDisplayPageEntry addAssetDisplayPageEntry(
		long assetEntryId, long layoutId) {

		long assetDisplayPageEntryId = counterLocalService.increment();

		AssetDisplayPageEntry assetDisplayPageEntry =
			assetDisplayPageEntryPersistence.create(assetDisplayPageEntryId);

		assetDisplayPageEntry.setAssetEntryId(assetEntryId);
		assetDisplayPageEntry.setLayoutId(layoutId);

		assetDisplayPageEntryPersistence.update(assetDisplayPageEntry);

		return assetDisplayPageEntry;
	}

	@Override
	public void deleteAssetDisplayPageEntryByAssetEntryId(long assetEntryId)
		throws PortalException {

		assetDisplayPageEntryPersistence.removeByAssetEntryId(assetEntryId);
	}

	@Override
	public AssetDisplayPageEntry fetchAssetDisplayPageEntryByAssetEntryId(
		long assetEntryId) {

		return assetDisplayPageEntryPersistence.fetchByAssetEntryId(
			assetEntryId);
	}

}