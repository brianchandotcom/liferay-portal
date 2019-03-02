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
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.SearchException;

import java.util.List;

/**
 * @author Eudaldo Alonso
 */
public class AssetEntryAssetCategoryRelLocalServiceImpl
	extends AssetEntryAssetCategoryRelLocalServiceBaseImpl {

	@Override
	public AssetEntryAssetCategoryRel addAssetEntryAssetCategoryRel(
		long assetEntryId, long assetCategoryId) {

		return assetEntryAssetCategoryRelLocalService.
			addAssetEntryAssetCategoryRel(assetEntryId, assetCategoryId, 0);
	}

	@Override
	public AssetEntryAssetCategoryRel addAssetEntryAssetCategoryRel(
		long assetEntryId, long assetCategoryId, int priority) {

		long assetEntryAssetCategoryRelId = counterLocalService.increment();

		AssetEntryAssetCategoryRel assetEntryAssetCategoryRel =
			assetEntryAssetCategoryRelPersistence.create(
				assetEntryAssetCategoryRelId);

		assetEntryAssetCategoryRel.setAssetEntryId(assetEntryId);
		assetEntryAssetCategoryRel.setAssetCategoryId(assetCategoryId);
		assetEntryAssetCategoryRel.setPriority(priority);

		assetEntryAssetCategoryRelPersistence.update(
			assetEntryAssetCategoryRel);

		return assetEntryAssetCategoryRel;
	}

	@Override
	public void deleteAssetEntryAssetCategoryRel(
		long assetEntryId, long assetCategoryId) {

		AssetEntryAssetCategoryRel assetEntryAssetCategoryRel =
			assetEntryAssetCategoryRelPersistence.fetchByA_A(
				assetEntryId, assetCategoryId);

		if (assetEntryAssetCategoryRel != null) {
			assetEntryAssetCategoryRelPersistence.remove(
				assetEntryAssetCategoryRel);
		}

		_reindex(assetEntryLocalService.fetchEntry(assetEntryId));
	}

	@Override
	public void deleteAssetEntryAssetCategoryRelByAssetCategoryId(
		long assetCategoryId) {

		List<AssetEntryAssetCategoryRel> assetEntryAssetCategoryRels =
			assetEntryAssetCategoryRelPersistence.findByAssetCategoryId(
				assetCategoryId);

		assetEntryAssetCategoryRels.forEach(
			assetEntryAssetCategoryRel -> {
				assetEntryAssetCategoryRelPersistence.remove(
					assetEntryAssetCategoryRel);

				_reindex(
					assetEntryLocalService.fetchEntry(
						assetEntryAssetCategoryRel.getAssetEntryId()));
			});

		assetEntryAssetCategoryRelPersistence.removeByAssetCategoryId(
			assetCategoryId);
	}

	@Override
	public void deleteAssetEntryAssetCategoryRelByAssetEntryId(
		long assetEntryId) {

		assetEntryAssetCategoryRelPersistence.removeByAssetEntryId(
			assetEntryId);

		_reindex(assetEntryLocalService.fetchEntry(assetEntryId));
	}

	@Override
	public AssetEntryAssetCategoryRel fetchAssetEntryAssetCategoryRel(
		long assetEntryId, long assetCategoryId) {

		return assetEntryAssetCategoryRelPersistence.fetchByA_A(
			assetEntryId, assetCategoryId);
	}

	@Override
	public List<AssetEntryAssetCategoryRel>
		getAssetEntryAssetCategoryRelsByAssetCategoryId(long assetCategoryId) {

		return assetEntryAssetCategoryRelPersistence.findByAssetCategoryId(
			assetCategoryId);
	}

	@Override
	public List<AssetEntryAssetCategoryRel>
		getAssetEntryAssetCategoryRelsByAssetEntryId(long assetEntryId) {

		return assetEntryAssetCategoryRelPersistence.findByAssetEntryId(
			assetEntryId);
	}

	@Override
	public int getAssetEntryAssetCategoryRelsCount(long assetEntryId) {
		return assetEntryAssetCategoryRelPersistence.countByAssetEntryId(
			assetEntryId);
	}

	private void _reindex(AssetEntry assetEntry) {
		if (assetEntry == null) {
			return;
		}

		try {
			Indexer indexer = IndexerRegistryUtil.getIndexer(
				assetEntry.getClassName());

			if (indexer == null) {
				return;
			}

			AssetRenderer assetRenderer = assetEntry.getAssetRenderer();

			indexer.reindex(assetRenderer.getAssetObject());
		}
		catch (SearchException se) {
			_log.error("Unable to reindex asset entry", se);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AssetEntryAssetCategoryRelLocalServiceImpl.class);

}