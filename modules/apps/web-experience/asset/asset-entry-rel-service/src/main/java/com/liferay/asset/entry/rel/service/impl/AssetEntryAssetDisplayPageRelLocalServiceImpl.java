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

package com.liferay.asset.entry.rel.service.impl;

import com.liferay.asset.entry.rel.model.AssetEntryAssetDisplayPageRel;
import com.liferay.asset.entry.rel.service.base.AssetEntryAssetDisplayPageRelLocalServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Jürgen Kappler
 */
public class AssetEntryAssetDisplayPageRelLocalServiceImpl
	extends AssetEntryAssetDisplayPageRelLocalServiceBaseImpl {

	@Override
	public AssetEntryAssetDisplayPageRel addAssetEntryAssetDisplayPageRel(
		long assetEntryId, long assetDisplayPageId) {

		long assetEntryAssetDisplayPageRelId = counterLocalService.increment();

		AssetEntryAssetDisplayPageRel assetEntryAssetDisplayPageRel =
			assetEntryAssetDisplayPageRelPersistence.create(
				assetEntryAssetDisplayPageRelId);

		assetEntryAssetDisplayPageRel.setAssetEntryId(assetEntryId);
		assetEntryAssetDisplayPageRel.setAssetDisplayPageId(assetDisplayPageId);

		assetEntryAssetDisplayPageRelPersistence.update(
			assetEntryAssetDisplayPageRel);

		return assetEntryAssetDisplayPageRel;
	}

	@Override
	public void deleteAssetEntryAssetDisplayPageRel(
			long assetEntryId, long assetDisplayPageId)
		throws PortalException {

		assetEntryAssetDisplayPageRelPersistence.removeByA_A(
			assetEntryId, assetDisplayPageId);
	}

	@Override
	public void deleteAssetEntryAssetDisplayPageRelByAssetEntryId(
		long assetEntryId) {

		assetEntryAssetDisplayPageRelPersistence.removeByAssetEntryId(
			assetEntryId);
	}

	@Override
	public AssetEntryAssetDisplayPageRel fetchAssetEntryAssetDisplayPageRel(
		long assetEntryId, long assetDisplayPageId) {

		return assetEntryAssetDisplayPageRelPersistence.fetchByA_A(
			assetEntryId, assetDisplayPageId);
	}

}