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
import com.liferay.site.navigation.model.SiteNavigationMenu;
import com.liferay.site.navigation.model.SiteNavigationMenuItem;
import com.liferay.site.navigation.service.base.SiteNavigationMenuLocalServiceBaseImpl;

import java.util.Date;
import java.util.List;

/**
 * @author Pavel Savinov
 */
public class SiteNavigationMenuLocalServiceImpl
	extends SiteNavigationMenuLocalServiceBaseImpl {

	@Override
	public SiteNavigationMenu addSiteNavigationMenu(
			long groupId, long userId, String name,
			ServiceContext serviceContext)
		throws PortalException {

		long siteNavigationMenuId = counterLocalService.increment();

		SiteNavigationMenu siteNavigationMenu =
			siteNavigationMenuPersistence.create(siteNavigationMenuId);

		User user = userLocalService.getUser(userId);

		siteNavigationMenu.setGroupId(groupId);
		siteNavigationMenu.setCompanyId(user.getCompanyId());
		siteNavigationMenu.setUserId(userId);
		siteNavigationMenu.setUserName(user.getFullName());
		siteNavigationMenu.setCreateDate(
			serviceContext.getCreateDate(new Date()));

		siteNavigationMenu.setName(name);

		siteNavigationMenuPersistence.update(siteNavigationMenu);

		return siteNavigationMenu;
	}

	@Override
	public SiteNavigationMenu deleteSiteNavigationMenu(
			long siteNavigationMenuId)
		throws PortalException {

		SiteNavigationMenu siteNavigationMenu = getSiteNavigationMenu(
			siteNavigationMenuId);

		return deleteSiteNavigationMenu(siteNavigationMenu);
	}

	@Override
	public SiteNavigationMenu deleteSiteNavigationMenu(
			SiteNavigationMenu siteNavigationMenu)
		throws PortalException {

		// Menu

		siteNavigationMenuPersistence.remove(
			siteNavigationMenu.getSiteNavigationMenuId());

		// Menu Items

		List<SiteNavigationMenuItem> siteNavigationMenuItems =
			siteNavigationMenuItemLocalService.getSiteNavigationMenuItems(
				siteNavigationMenu.getSiteNavigationMenuId());

		for (SiteNavigationMenuItem siteNavigationMenuItem :
				siteNavigationMenuItems) {

			siteNavigationMenuItemLocalService.deleteSiteNavigationMenuItem(
				siteNavigationMenuItem.getSiteNavigationMenuItemId());
		}

		return siteNavigationMenu;
	}

	@Override
	public List<SiteNavigationMenu> getSiteNavigationMenus(long groupId) {
		return siteNavigationMenuPersistence.findByGroupId(groupId);
	}

}