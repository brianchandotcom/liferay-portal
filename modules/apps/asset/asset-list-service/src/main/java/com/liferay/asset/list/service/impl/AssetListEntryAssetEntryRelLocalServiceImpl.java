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

import com.liferay.asset.list.model.AssetListEntryAssetEntryRel;
import com.liferay.asset.list.service.base.AssetListEntryAssetEntryRelLocalServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.List;

/**
 * @author Pavel savinov
 */
public class AssetListEntryAssetEntryRelLocalServiceImpl
	extends AssetListEntryAssetEntryRelLocalServiceBaseImpl {

	@Override
	public AssetListEntryAssetEntryRel addAssetListEntryAssetEntryRel(
		long assetListEntryId, long assetEntryId) {

		long assetListEntryAssetEntryRelId = counterLocalService.increment();

		AssetListEntryAssetEntryRel assetListEntryAssetEntryRel =
			assetListEntryAssetEntryRelPersistence.create(
				assetListEntryAssetEntryRelId);

		assetListEntryAssetEntryRel.setAssetListEntryId(assetListEntryId);
		assetListEntryAssetEntryRel.setAssetEntryId(assetEntryId);
		assetListEntryAssetEntryRel.setPosition(
			assetListEntryAssetEntryRelPersistence.countByAssetListEntryId(
				assetListEntryId));

		return assetListEntryAssetEntryRelPersistence.update(
			assetListEntryAssetEntryRel);
	}

	@Override
	public AssetListEntryAssetEntryRel deleteAssetListEntryAssetEntryRel(
			long assetListEntryId, int position)
		throws PortalException {

		return assetListEntryAssetEntryRelPersistence.removeByA_P(
			assetListEntryId, position);
	}

	@Override
	public List<AssetListEntryAssetEntryRel> getAssetListEntryAssetEntryRels(
		long assetListEntryId, int start, int end) {

		return assetListEntryAssetEntryRelPersistence.findByAssetListEntryId(
			assetListEntryId, start, end);
	}

	@Override
	public int getAssetListEntryAssetEntryRelsCount(long assetListEntryId) {
		return assetListEntryAssetEntryRelPersistence.countByAssetListEntryId(
			assetListEntryId);
	}

	@Override
	public AssetListEntryAssetEntryRel moveAssetListEntryAssetEntryRel(
			long assetListEntryId, int position, int newPosition)
		throws PortalException {

		AssetListEntryAssetEntryRel assetListEntryAssetEntryRel =
			assetListEntryAssetEntryRelPersistence.findByA_P(
				assetListEntryId, position);

		int assetListEntryAssetEntryRelCount =
			assetListEntryAssetEntryRelPersistence.countByAssetListEntryId(
				assetListEntryId);

		if ((newPosition < 0) ||
			(newPosition >= assetListEntryAssetEntryRelCount)) {

			return assetListEntryAssetEntryRel;
		}

		assetListEntryAssetEntryRel.setPosition(newPosition);

		assetListEntryAssetEntryRelPersistence.update(
			assetListEntryAssetEntryRel);

		return assetListEntryAssetEntryRel;
	}

}