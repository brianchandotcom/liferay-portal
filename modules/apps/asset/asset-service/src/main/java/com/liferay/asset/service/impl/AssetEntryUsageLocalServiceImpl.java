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

import com.liferay.asset.constants.AssetEntryUsageConstants;
import com.liferay.asset.model.AssetEntryUsage;
import com.liferay.asset.service.base.AssetEntryUsageLocalServiceBaseImpl;
import com.liferay.layout.page.template.constants.LayoutPageTemplateEntryTypeConstants;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.Date;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

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
		long groupId, long assetEntryId, long containerType,
		String containerKey, long plid, ServiceContext serviceContext) {

		long assetEntryUsageId = counterLocalService.increment();

		AssetEntryUsage assetEntryUsage = assetEntryUsagePersistence.create(
			assetEntryUsageId);

		assetEntryUsage.setUuid(serviceContext.getUuid());
		assetEntryUsage.setGroupId(groupId);
		assetEntryUsage.setCreateDate(new Date());
		assetEntryUsage.setModifiedDate(new Date());
		assetEntryUsage.setAssetEntryId(assetEntryId);
		assetEntryUsage.setContainerType(containerType);
		assetEntryUsage.setContainerKey(containerKey);
		assetEntryUsage.setPlid(plid);
		assetEntryUsage.setType(_getType(plid));

		return assetEntryUsagePersistence.update(assetEntryUsage);
	}

	@Override
	public AssetEntryUsage addDefaultAssetEntryUsage(
		long groupId, long assetEntryId, ServiceContext serviceContext) {

		return addAssetEntryUsage(
			groupId, assetEntryId, 0, StringPool.BLANK, 0, serviceContext);
	}

	@Override
	public void deleteAssetEntryUsages(long assetEntryId) {
		assetEntryUsagePersistence.removeByAssetEntryId(assetEntryId);
	}

	@Override
	public void deleteAssetEntryUsages(
		long containerType, String containerKey, long plid) {

		assetEntryUsagePersistence.removeByC_C_P(
			containerType, containerKey, plid);
	}

	@Override
	public void deleteAssetEntryUsagesByPlid(long plid) {
		assetEntryUsagePersistence.removeByPlid(plid);
	}

	@Override
	public AssetEntryUsage fetchAssetEntryUsage(
		long assetEntryId, long containerType, String containerKey, long plid) {

		return assetEntryUsagePersistence.fetchByA_C_C_P(
			assetEntryId, containerType, containerKey, plid);
	}

	@Override
	public List<AssetEntryUsage> getAssetEntryUsages(long assetEntryId) {
		return assetEntryUsagePersistence.findByAssetEntryId(assetEntryId);
	}

	@Override
	public List<AssetEntryUsage> getAssetEntryUsages(
		long assetEntryId, int type, int start, int end,
		OrderByComparator<AssetEntryUsage> orderByComparator) {

		return assetEntryUsagePersistence.findByA_T(
			assetEntryId, type, start, end, orderByComparator);
	}

	@Override
	public List<AssetEntryUsage> getAssetEntryUsages(
		long assetEntryId, int start, int end,
		OrderByComparator<AssetEntryUsage> orderByComparator) {

		return assetEntryUsagePersistence.findByAssetEntryId(
			assetEntryId, start, end, orderByComparator);
	}

	@Override
	public List<AssetEntryUsage> getAssetEntryUsagesByPlid(long plid) {
		return assetEntryUsagePersistence.findByPlid(plid);
	}

	@Override
	public int getAssetEntryUsagesCount(long assetEntryId) {
		return assetEntryUsagePersistence.countByAssetEntryId(assetEntryId);
	}

	@Override
	public int getAssetEntryUsagesCount(long assetEntryId, int type) {
		return assetEntryUsagePersistence.countByA_T(assetEntryId, type);
	}

	@Override
	public int getUniqueAssetEntryUsagesCount(long assetEntryId) {
		return assetEntryUsageFinder.countByAssetEntryId(assetEntryId);
	}

	@Override
	public boolean hasDefaultAssetEntryUsage(long assetEntryId) {
		AssetEntryUsage assetEntryUsage =
			assetEntryUsageLocalService.fetchAssetEntryUsage(
				assetEntryId, 0, StringPool.BLANK, 0);

		if (assetEntryUsage != null) {
			return true;
		}

		return false;
	}

	private int _getType(long plid) {
		if (plid <= 0) {
			return AssetEntryUsageConstants.TYPE_DEFAULT;
		}

		Layout layout = layoutLocalService.fetchLayout(plid);

		if ((layout.getClassNameId() > 0) && (layout.getClassPK() > 0)) {
			plid = layout.getClassPK();
		}

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			_layoutPageTemplateEntryLocalService.
				fetchLayoutPageTemplateEntryByPlid(plid);

		if (layoutPageTemplateEntry == null) {
			return AssetEntryUsageConstants.TYPE_LAYOUT;
		}

		if (layoutPageTemplateEntry.getType() ==
				LayoutPageTemplateEntryTypeConstants.TYPE_DISPLAY_PAGE) {

			return AssetEntryUsageConstants.TYPE_DISPLAY_PAGE_TEMPLATE;
		}

		return AssetEntryUsageConstants.TYPE_PAGE_TEMPLATE;
	}

	@Reference
	private LayoutPageTemplateEntryLocalService
		_layoutPageTemplateEntryLocalService;

}