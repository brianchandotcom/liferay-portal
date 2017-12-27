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

import com.liferay.asset.entry.rel.model.AssetEntryAssetCategoryRel;
import com.liferay.asset.entry.rel.service.base.AssetEntryAssetCategoryRelLocalServiceBaseImpl;

import java.util.List;

/**
 * @author Eudaldo Alonso
 */
public class AssetEntryAssetCategoryRelLocalServiceImpl
	extends AssetEntryAssetCategoryRelLocalServiceBaseImpl {

	@Override
	public AssetEntryAssetCategoryRel addAssetEntryAssetCategoryRel(
		long entryId, long categoryId) {

		long assetEntryAssetCategoryRelId = counterLocalService.increment();

		AssetEntryAssetCategoryRel assetEntryAssetCategoryRel =
			assetEntryAssetCategoryRelPersistence.create(
				assetEntryAssetCategoryRelId);

		assetEntryAssetCategoryRel.setEntryId(entryId);
		assetEntryAssetCategoryRel.setCategoryId(categoryId);

		assetEntryAssetCategoryRelPersistence.update(
			assetEntryAssetCategoryRel);

		return assetEntryAssetCategoryRel;
	}

	@Override
	public void deleteAssetEntryAssetCategoryRelByCategoryId(long categoryId) {
		assetEntryAssetCategoryRelPersistence.removeByCategoryId(categoryId);
	}

	@Override
	public List<AssetEntryAssetCategoryRel>
		getAssetEntryAssetCategoryRelsByCategoryId(long categoryId) {

		return assetEntryAssetCategoryRelPersistence.findByCategoryId(
			categoryId);
	}

	@Override
	public List<AssetEntryAssetCategoryRel>
		getAssetEntryAssetCategoryRelsByEntryId(long entryId) {

		return assetEntryAssetCategoryRelPersistence.findByEntryId(entryId);
	}

}