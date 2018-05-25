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

package com.liferay.asset.tag.stats.internal.service;

import com.liferay.asset.kernel.model.AssetTagStats;
import com.liferay.asset.kernel.service.AssetTagStatsLocalServiceWrapper;
import com.liferay.asset.tag.stats.service.AssetTagStatsLocalService;
import com.liferay.petra.model.adapter.util.ModelAdapterUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceWrapper;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(immediate = true, service = ServiceWrapper.class)
public class ModularAssetTagStatsLocalServiceWrapper
	extends AssetTagStatsLocalServiceWrapper {

	public ModularAssetTagStatsLocalServiceWrapper() {
		super(null);
	}

	public ModularAssetTagStatsLocalServiceWrapper(
		com.liferay.asset.kernel.service.AssetTagStatsLocalService
			assetTagStatsLocalService) {

		super(assetTagStatsLocalService);
	}

	@Override
	public AssetTagStats addTagStats(long tagId, long classNameId) {
		return ModelAdapterUtil.adapt(
			AssetTagStats.class,
			_assetTagStatsLocalService.addTagStats(tagId, classNameId));
	}

	@Override
	public void deleteTagStats(AssetTagStats tagStats) {
		_assetTagStatsLocalService.deleteTagStats(
			ModelAdapterUtil.adapt(
				com.liferay.asset.tag.stats.model.AssetTagStats.class,
				tagStats));
	}

	@Override
	public void deleteTagStats(long tagStatsId) throws PortalException {
		_assetTagStatsLocalService.deleteTagStats(tagStatsId);
	}

	@Override
	public void deleteTagStatsByClassNameId(long classNameId) {
		_assetTagStatsLocalService.deleteTagStatsByClassNameId(classNameId);
	}

	@Override
	public void deleteTagStatsByTagId(long tagId) {
		_assetTagStatsLocalService.deleteTagStatsByTagId(tagId);
	}

	@Override
	public String getOSGiServiceIdentifier() {
		return _assetTagStatsLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public List<AssetTagStats> getTagStats(
		long classNameId, int start, int end) {

		return ModelAdapterUtil.adapt(
			AssetTagStats.class,
			_assetTagStatsLocalService.getTagStats(classNameId, start, end));
	}

	@Override
	public AssetTagStats getTagStats(long tagId, long classNameId) {
		return ModelAdapterUtil.adapt(
			AssetTagStats.class,
			_assetTagStatsLocalService.getTagStats(tagId, classNameId));
	}

	@Override
	public AssetTagStats updateTagStats(long tagId, long classNameId)
		throws PortalException {

		return ModelAdapterUtil.adapt(
			AssetTagStats.class,
			_assetTagStatsLocalService.updateTagStats(tagId, classNameId));
	}

	@Reference
	private AssetTagStatsLocalService _assetTagStatsLocalService;

}