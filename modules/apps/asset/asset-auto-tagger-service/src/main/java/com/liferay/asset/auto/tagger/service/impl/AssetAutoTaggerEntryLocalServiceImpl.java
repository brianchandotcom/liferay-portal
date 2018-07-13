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

package com.liferay.asset.auto.tagger.service.impl;

import com.liferay.asset.auto.tagger.model.AssetAutoTaggerEntry;
import com.liferay.asset.auto.tagger.service.base.AssetAutoTaggerEntryLocalServiceBaseImpl;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetTag;

import java.util.List;

/**
 * @author Alejandro Tardín
 */
public class AssetAutoTaggerEntryLocalServiceImpl
	extends AssetAutoTaggerEntryLocalServiceBaseImpl {

	@Override
	public AssetAutoTaggerEntry addAssetAutoTaggerEntry(
		AssetEntry assetEntry, AssetTag assetTag) {

		AssetAutoTaggerEntry existingAssetAutoTaggerEntry =
			assetAutoTaggerEntryPersistence.fetchByA_A(
				assetEntry.getEntryId(), assetTag.getTagId());

		if (existingAssetAutoTaggerEntry != null) {
			return existingAssetAutoTaggerEntry;
		}

		long assetAutoTaggerEntryId = counterLocalService.increment();

		AssetAutoTaggerEntry assetAutoTaggerEntry =
			assetAutoTaggerEntryPersistence.create(assetAutoTaggerEntryId);

		assetAutoTaggerEntry.setCompanyId(assetEntry.getCompanyId());

		assetAutoTaggerEntry.setGroupId(assetEntry.getGroupId());
		assetAutoTaggerEntry.setAssetEntryId(assetEntry.getEntryId());
		assetAutoTaggerEntry.setAssetTagId(assetTag.getTagId());

		return assetAutoTaggerEntryPersistence.update(assetAutoTaggerEntry);
	}

	@Override
	public AssetAutoTaggerEntry fetchAssetAutoTaggerEntry(
		long assetEntryId, long assetTagId) {

		return assetAutoTaggerEntryPersistence.fetchByA_A(
			assetEntryId, assetTagId);
	}

	@Override
	public List<AssetAutoTaggerEntry> getAssetAutoTaggerEntries(
		AssetEntry assetEntry) {

		return assetAutoTaggerEntryPersistence.findByAssetEntryId(
			assetEntry.getEntryId());
	}

	@Override
	public List<AssetAutoTaggerEntry> getAssetAutoTaggerEntries(
		AssetTag assetTag) {

		return assetAutoTaggerEntryPersistence.findByAssetTagId(
			assetTag.getTagId());
	}

}