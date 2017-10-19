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

package com.liferay.site.navigation.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.site.navigation.model.SiteNavigationMenuItem;
import com.liferay.site.navigation.service.base.SiteNavigationMenuItemLocalServiceBaseImpl;

import java.util.Date;
import java.util.List;

/**
 * @author Pavel Savinov
 */
public class SiteNavigationMenuItemLocalServiceImpl
	extends SiteNavigationMenuItemLocalServiceBaseImpl {

	@Override
	public SiteNavigationMenuItem addSiteNavigationMenuItem(
			long userId, long groupId, long siteNavigationMenuId,
			long parentSiteNavigationMenuItemId, String type,
			String typeSettings, ServiceContext serviceContext)
		throws PortalException {

		long siteNavigationMenuItemId = counterLocalService.increment();

		SiteNavigationMenuItem siteNavigationMenuItem =
			siteNavigationMenuItemPersistence.create(siteNavigationMenuItemId);

		User user = userLocalService.getUser(userId);

		siteNavigationMenuItem.setGroupId(groupId);
		siteNavigationMenuItem.setCompanyId(user.getCompanyId());
		siteNavigationMenuItem.setUserId(userId);
		siteNavigationMenuItem.setUserName(user.getFullName());
		siteNavigationMenuItem.setCreateDate(
			serviceContext.getCreateDate(new Date()));
		siteNavigationMenuItem.setSiteNavigationMenuId(siteNavigationMenuId);
		siteNavigationMenuItem.setParentSiteNavigationMenuItemId(
			parentSiteNavigationMenuItemId);
		siteNavigationMenuItem.setType(type);
		siteNavigationMenuItem.setTypeSettings(typeSettings);

		siteNavigationMenuItemPersistence.update(siteNavigationMenuItem);

		return siteNavigationMenuItem;
	}

	@Override
	public SiteNavigationMenuItem deleteSiteNavigationMenuItem(
			long siteNavigationMenuItemId)
		throws PortalException {

		return siteNavigationMenuItemPersistence.remove(
			siteNavigationMenuItemId);
	}

	@Override
	public void deleteSiteNavigationMenuItems(long siteNavigationMenuId)
		throws PortalException {

		siteNavigationMenuItemPersistence.removeBySiteNavigationMenuId(
			siteNavigationMenuId);
	}

	@Override
	public List<SiteNavigationMenuItem> getSiteNavigationMenuItems(
		long siteNavigationMenuId) {

		return siteNavigationMenuItemPersistence.findBySiteNavigationMenuId(
			siteNavigationMenuId);
	}

	@Override
	public List<SiteNavigationMenuItem> getSiteNavigationMenuItemsByParent(
		long parentSiteNavigationMenuItemId) {

		return siteNavigationMenuItemPersistence.
			findByParentSiteNavigationMenuItemId(
				parentSiteNavigationMenuItemId);
	}

}