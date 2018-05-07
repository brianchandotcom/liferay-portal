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

import com.liferay.asset.display.page.model.AssetDisplayPage;
import com.liferay.asset.display.page.service.base.AssetDisplayPageLocalServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Eudaldo Alonso
 */
public class AssetDisplayPageLocalServiceImpl
	extends AssetDisplayPageLocalServiceBaseImpl {

	@Override
	public AssetDisplayPage addAssetDisplayPage(
		long assetEntryId, long layoutId) {

		long assetDisplayPageId = counterLocalService.increment();

		AssetDisplayPage assetDisplayPage = assetDisplayPagePersistence.create(
			assetDisplayPageId);

		assetDisplayPage.setAssetEntryId(assetEntryId);
		assetDisplayPage.setLayoutId(layoutId);

		assetDisplayPagePersistence.update(assetDisplayPage);

		return assetDisplayPage;
	}

	@Override
	public void deleteAssetDisplayPage(long assetEntryId, long layoutId)
		throws PortalException {

		assetDisplayPagePersistence.removeByA_L(assetEntryId, layoutId);
	}

	@Override
	public void deleteAssetDisplayPageByAssetEntryId(long assetEntryId) {
		assetDisplayPagePersistence.removeByAssetEntryId(assetEntryId);
	}

	@Override
	public AssetDisplayPage fetchAssetDisplayPage(
		long assetEntryId, long layoutId) {

		return assetDisplayPagePersistence.fetchByA_L(assetEntryId, layoutId);
	}

}