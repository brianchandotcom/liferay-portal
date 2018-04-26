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

import com.liferay.asset.entry.rel.exception.NoSuchEntryDisplayPageRelException;
import com.liferay.asset.entry.rel.model.AssetEntryDisplayPageRel;
import com.liferay.asset.entry.rel.service.base.AssetEntryDisplayPageRelLocalServiceBaseImpl;

/**
 * @author Jürgen Kappler
 */
public class AssetEntryDisplayPageRelLocalServiceImpl
	extends AssetEntryDisplayPageRelLocalServiceBaseImpl {

	@Override
	public AssetEntryDisplayPageRel addAssetEntryDisplayPageRel(
		long assetEntryId, long displayPageId) {

		long assetEntryDisplayPageRelId = counterLocalService.increment();

		AssetEntryDisplayPageRel assetEntryDisplayPageRel =
			assetEntryDisplayPageRelPersistence.create(
				assetEntryDisplayPageRelId);

		assetEntryDisplayPageRel.setAssetEntryId(assetEntryId);
		assetEntryDisplayPageRel.setDisplayPageId(displayPageId);

		assetEntryDisplayPageRelPersistence.update(assetEntryDisplayPageRel);

		return assetEntryDisplayPageRel;
	}

	@Override
	public void deleteAssetEntryDisplayPageRel(
			long assetEntryId, long displayPageId)
		throws NoSuchEntryDisplayPageRelException {

		assetEntryDisplayPageRelPersistence.removeByA_D(
			assetEntryId, displayPageId);
	}

	@Override
	public void deleteAssetEntryDisplayPageRelByAssetEntryId(
		long assetEntryId) {

		assetEntryDisplayPageRelPersistence.removeByAssetEntryId(assetEntryId);
	}

	@Override
	public AssetEntryDisplayPageRel fetchAssetEntryDisplayPageRel(
		long assetEntryId, long displayPageId) {

		return assetEntryDisplayPageRelPersistence.fetchByA_D(
			assetEntryId, displayPageId);
	}

}