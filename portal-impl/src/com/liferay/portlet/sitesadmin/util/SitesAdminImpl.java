/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.sitesadmin.util;

import com.liferay.portal.kernel.staging.StagingConstants;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.asset.model.AssetCategory;
import com.liferay.portlet.asset.model.AssetTag;
import com.liferay.portlet.asset.service.AssetCategoryServiceUtil;
import com.liferay.portlet.asset.service.AssetTagServiceUtil;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.PortletRequest;

/**
 * @author Zsolt Szabo
 */
public class SitesAdminImpl implements SitesAdmin {

	public int getStagingType(Group liveGroup, PortletRequest portletRequest) {
		String stagingType = portletRequest.getParameter("stagingType");

		if (stagingType == null) {
			if (liveGroup.isStagedRemotely()) {
				return StagingConstants.TYPE_REMOTE_STAGING;
			}

			if (liveGroup.hasStagingGroup()) {
				return StagingConstants.TYPE_LOCAL_STAGING;
			}

			return StagingConstants.TYPE_NOT_STAGED;
		}

		return Integer.parseInt(stagingType);
	}

	public void updateAssetCategoryIds(
			PortletRequest portletRequest, Group liveGroup,
			ServiceContext serviceContext)
		throws Exception{

		if (serviceContext.getAssetCategoryIds().length > 0) {
			return;
		}

		List<AssetCategory> categories = AssetCategoryServiceUtil.getCategories(
			liveGroup.getClassName(), liveGroup.getClassPK());

		List<Long> assetCategoryIdsList = new ArrayList<Long>();

		for (AssetCategory category : categories) {
			assetCategoryIdsList.add(category.getCategoryId());
		}

		serviceContext.setAssetCategoryIds(
			ArrayUtil.toArray(
				assetCategoryIdsList.toArray(
					new Long[assetCategoryIdsList.size()])));
	}

	public void updateAssetTagNames(
			PortletRequest portletRequest, Group liveGroup,
			ServiceContext serviceContext)
		throws Exception{

		if (serviceContext.getAssetTagNames().length > 0) {
			return;
		}

		List<AssetTag> tags = AssetTagServiceUtil.getTags(
			liveGroup.getClassName(), liveGroup.getClassPK());

		String defaultAssetTagNames = ListUtil.toString(
			tags, AssetTag.NAME_ACCESSOR);

		serviceContext.setAssetTagNames(StringUtil.split(defaultAssetTagNames));
	}

}