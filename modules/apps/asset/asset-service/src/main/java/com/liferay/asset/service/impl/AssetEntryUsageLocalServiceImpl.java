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

package com.liferay.asset.service.impl;

import com.liferay.asset.model.AssetEntryUsage;
import com.liferay.asset.service.base.AssetEntryUsageLocalServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.Date;
import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * @author Pavel Savinov
 */
@Component(
	property = "model.class.name=com.liferay.asset.model.AssetEntryUsage",
	service = AopService.class
)
public class AssetEntryUsageLocalServiceImpl
	extends AssetEntryUsageLocalServiceBaseImpl {

	@Override
	public AssetEntryUsage addAssetEntryUsage(
		long groupId, long assetEntryId, long plid, String portletId,
		ServiceContext serviceContext) {

		long assetEntryUsageId = counterLocalService.increment();

		AssetEntryUsage assetEntryUsage = assetEntryUsagePersistence.create(
			assetEntryUsageId);

		assetEntryUsage.setUuid(serviceContext.getUuid());
		assetEntryUsage.setGroupId(groupId);
		assetEntryUsage.setCreateDate(new Date());
		assetEntryUsage.setModifiedDate(new Date());
		assetEntryUsage.setAssetEntryId(assetEntryId);
		assetEntryUsage.setPlid(plid);
		assetEntryUsage.setPortletId(portletId);

		return assetEntryUsagePersistence.update(assetEntryUsage);
	}

	@Override
	public void deleteAssetEntryUsages(long plid, String portletId) {
		assetEntryUsagePersistence.removeByP_P(plid, portletId);
	}

	@Override
	public AssetEntryUsage fetchAssetEntryUsage(
		long assetEntryId, long plid, String portletId) {

		return assetEntryUsagePersistence.fetchByA_P_P(
			assetEntryId, plid, portletId);
	}

	@Override
	public List<AssetEntryUsage> getAssetEntryUsages(long assetEntryId) {
		return assetEntryUsagePersistence.findByAssetEntryId(assetEntryId);
	}

	@Override
	public List<AssetEntryUsage> getAssetEntryUsages(
		long assetEntryId, int start, int end,
		OrderByComparator<AssetEntryUsage> orderByComparator) {

		return assetEntryUsagePersistence.findByAssetEntryId(
			assetEntryId, start, end, orderByComparator);
	}

	@Override
	public List<AssetEntryUsage> getAssetEntryUsages(
		long assetEntryId, long plid) {

		return assetEntryUsagePersistence.findByA_P(assetEntryId, plid);
	}

	@Override
	public List<AssetEntryUsage> getAssetEntryUsages(
		long assetEntryId, long plid, int start, int end,
		OrderByComparator<AssetEntryUsage> orderByComparator) {

		return assetEntryUsagePersistence.findByA_P(
			assetEntryId, plid, start, end, orderByComparator);
	}

	@Override
	public List<AssetEntryUsage> getAssetEntryUsages(
		long plid, String portletId) {

		return assetEntryUsagePersistence.findByP_P(plid, portletId);
	}

	@Override
	public int getAssetEntryUsagesCount(long assetEntryId) {
		return assetEntryUsagePersistence.countByAssetEntryId(assetEntryId);
	}

	@Override
	public int getAssetEntryUsagesCount(long assetEntryId, long plid) {
		return assetEntryUsagePersistence.countByA_P(assetEntryId, plid);
	}

}