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

import com.liferay.asset.constants.AssetEntryUsagesTypeConstants;
import com.liferay.asset.model.AssetEntryUsage;
import com.liferay.asset.service.base.AssetEntryUsageLocalServiceBaseImpl;
import com.liferay.petra.string.StringPool;
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
		assetEntryUsage.setType(AssetEntryUsagesTypeConstants.TYPE_LAYOUT);
		assetEntryUsage.setPortletId(portletId);

		return assetEntryUsagePersistence.update(assetEntryUsage);
	}

	@Override
	public AssetEntryUsage addDefaultAssetEntryUsage(
		long groupId, long assetEntryId, ServiceContext serviceContext) {

		return addAssetEntryUsage(
			groupId, assetEntryId, 0, StringPool.BLANK, serviceContext);
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
	public int getAssetEntryUsagesCount(long assetEntryId) {
		return assetEntryUsagePersistence.countByAssetEntryId(assetEntryId);
	}

	@Override
	public boolean hasDefaultAssetEntryUsage(long assetEntryId) {
		AssetEntryUsage assetEntryUsage =
			assetEntryUsageLocalService.fetchAssetEntryUsage(
				assetEntryId, 0, StringPool.BLANK);

		if (assetEntryUsage != null) {
			return true;
		}

		return false;
	}

}