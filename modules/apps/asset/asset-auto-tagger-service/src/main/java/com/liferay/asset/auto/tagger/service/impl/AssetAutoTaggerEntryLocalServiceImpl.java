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
 * The implementation of the asset auto tagger entry local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link com.liferay.asset.auto.tagger.service.AssetAutoTaggerEntryLocalService} interface.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AssetAutoTaggerEntryLocalServiceBaseImpl
 * @see com.liferay.asset.auto.tagger.service.AssetAutoTaggerEntryLocalServiceUtil
 */
public class AssetAutoTaggerEntryLocalServiceImpl
	extends AssetAutoTaggerEntryLocalServiceBaseImpl {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never reference this class directly. Always use {@link com.liferay.asset.auto.tagger.service.AssetAutoTaggerEntryLocalServiceUtil} to access the asset auto tagger entry local service.
	 */
	@Override
	public AssetAutoTaggerEntry addAssetAutoTaggerEntry(
		AssetEntry assetEntry, AssetTag assetTag) {

		AssetAutoTaggerEntry existingEntry =
			assetAutoTaggerEntryPersistence.fetchByA_A(
				assetEntry.getEntryId(), assetTag.getTagId());

		if (existingEntry != null) {
			return existingEntry;
		}

		long entryId = counterLocalService.increment();

		AssetAutoTaggerEntry entry = assetAutoTaggerEntryPersistence.create(
			entryId);

		entry.setGroupId(assetEntry.getGroupId());
		entry.setAssetEntryId(assetEntry.getEntryId());
		entry.setAssetTagId(assetTag.getTagId());

		return assetAutoTaggerEntryPersistence.update(entry);
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