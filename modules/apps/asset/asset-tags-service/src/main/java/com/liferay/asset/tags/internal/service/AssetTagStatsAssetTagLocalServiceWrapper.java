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

package com.liferay.asset.tags.internal.service;

import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.asset.kernel.service.AssetTagLocalService;
import com.liferay.asset.kernel.service.AssetTagLocalServiceWrapper;
import com.liferay.asset.tag.stats.service.AssetTagStatsLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceWrapper;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(immediate = true, service = ServiceWrapper.class)
public class AssetTagStatsAssetTagLocalServiceWrapper
	extends AssetTagLocalServiceWrapper {

	public AssetTagStatsAssetTagLocalServiceWrapper() {
		super(null);
	}

	public AssetTagStatsAssetTagLocalServiceWrapper(
		AssetTagLocalService assetTagLocalService) {

		super(assetTagLocalService);
	}

	@Override
	public AssetTag decrementAssetCount(long tagId, long classNameId)
		throws PortalException {

		AssetTag tag = super.decrementAssetCount(tagId, classNameId);

		_assetTagStatsLocalService.updateTagStats(tagId, classNameId);

		return tag;
	}

	@Override
	public void deleteTag(AssetTag tag) throws PortalException {
		super.deleteTag(tag);

		_assetTagStatsLocalService.deleteTagStatsByTagId(tag.getTagId());
	}

	@Override
	public AssetTag incrementAssetCount(long tagId, long classNameId)
		throws PortalException {

		AssetTag tag = super.incrementAssetCount(tagId, classNameId);

		_assetTagStatsLocalService.updateTagStats(tagId, classNameId);

		return tag;
	}

	@Reference
	private AssetTagStatsLocalService _assetTagStatsLocalService;

}