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
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.Date;
import java.util.List;

/**
 * @author Pavel Savinov
 */
public class AssetEntryUsageLocalServiceImpl
	extends AssetEntryUsageLocalServiceBaseImpl {

	@Override
	public AssetEntryUsage addAssetEntryUsage(
			long userId, long groupId, long assetEntryId, long classNameId,
			long classPK, String portletId, ServiceContext serviceContext)
		throws PortalException {

		User user = userLocalService.getUser(userId);

		long assetEntryUsageId = counterLocalService.increment();

		AssetEntryUsage assetEntryUsage = assetEntryUsagePersistence.create(
			assetEntryUsageId);

		assetEntryUsage.setUuid(serviceContext.getUuid());
		assetEntryUsage.setGroupId(groupId);
		assetEntryUsage.setCompanyId(user.getCompanyId());
		assetEntryUsage.setUserId(userId);
		assetEntryUsage.setUserName(user.getFullName());
		assetEntryUsage.setCreateDate(serviceContext.getCreateDate(new Date()));
		assetEntryUsage.setModifiedDate(
			serviceContext.getModifiedDate(new Date()));
		assetEntryUsage.setAssetEntryId(assetEntryId);
		assetEntryUsage.setClassNameId(classNameId);
		assetEntryUsage.setClassPK(classPK);
		assetEntryUsage.setPortletId(portletId);

		return assetEntryUsagePersistence.update(assetEntryUsage);
	}

	@Override
	public void deleteAssetEntryUsages(
		long classNameId, long classPK, String portletId) {

		assetEntryUsagePersistence.removeByC_C_P(
			classNameId, classPK, portletId);
	}

	@Override
	public AssetEntryUsage fetchAssetEntryUsage(
		long assetEntryId, long classNameId, long classPK, String portletId) {

		return assetEntryUsagePersistence.fetchByA_C_C_P(
			assetEntryId, classNameId, classPK, portletId);
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
		long assetEntryId, long classNameId) {

		return assetEntryUsagePersistence.findByA_C(assetEntryId, classNameId);
	}

	@Override
	public List<AssetEntryUsage> getAssetEntryUsages(
		long assetEntryId, long classNameId, int start, int end,
		OrderByComparator<AssetEntryUsage> orderByComparator) {

		return assetEntryUsagePersistence.findByA_C(
			assetEntryId, classNameId, start, end, orderByComparator);
	}

	@Override
	public List<AssetEntryUsage> getAssetEntryUsages(
		long classNameId, long classPK, String portletId) {

		return assetEntryUsagePersistence.findByC_C_P(
			classNameId, classPK, portletId);
	}

	@Override
	public int getAssetEntryUsagesCount(long assetEntryId) {
		return assetEntryUsagePersistence.countByAssetEntryId(assetEntryId);
	}

	@Override
	public int getAssetEntryUsagesCount(long assetEntryId, long classNameId) {
		return assetEntryUsagePersistence.countByA_C(assetEntryId, classNameId);
	}

}