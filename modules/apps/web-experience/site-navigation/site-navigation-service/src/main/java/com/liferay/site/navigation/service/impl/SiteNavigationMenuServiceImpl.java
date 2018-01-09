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
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.site.navigation.constants.SiteNavigationActionKeys;
import com.liferay.site.navigation.model.SiteNavigationMenu;
import com.liferay.site.navigation.service.base.SiteNavigationMenuServiceBaseImpl;
import com.liferay.site.navigation.service.permission.SiteNavigationMenuPermission;
import com.liferay.site.navigation.service.permission.SiteNavigationPermission;

import java.util.List;

/**
 * @author Pavel Savinov
 */
public class SiteNavigationMenuServiceImpl
	extends SiteNavigationMenuServiceBaseImpl {

	@Override
	public SiteNavigationMenu addSiteNavigationMenu(
			long groupId, String name, ServiceContext serviceContext)
		throws PortalException {

		SiteNavigationPermission.check(
			getPermissionChecker(), groupId,
			SiteNavigationActionKeys.ADD_SITE_NAVIGATION_MENU);

		return siteNavigationMenuLocalService.addSiteNavigationMenu(
			getUserId(), groupId, name, serviceContext);
	}

	@Override
	public SiteNavigationMenu deleteSiteNavigationMenu(
			long siteNavigationMenuId)
		throws PortalException {

		SiteNavigationMenuPermission.check(
			getPermissionChecker(), siteNavigationMenuId, ActionKeys.DELETE);

		return siteNavigationMenuLocalService.deleteSiteNavigationMenu(
			siteNavigationMenuId);
	}

	@Override
	public SiteNavigationMenu fetchSiteNavigationMenu(long siteNavigationMenuId)
		throws PortalException {

		SiteNavigationMenuPermission.check(
			getPermissionChecker(), siteNavigationMenuId, ActionKeys.VIEW);

		return siteNavigationMenuLocalService.fetchSiteNavigationMenu(
			siteNavigationMenuId);
	}

	@Override
	public List<SiteNavigationMenu> getSiteNavigationMenus(long groupId) {
		return siteNavigationMenuPersistence.filterFindByGroupId(groupId);
	}

	@Override
	public List<SiteNavigationMenu> getSiteNavigationMenus(
		long groupId, int start, int end, OrderByComparator orderByComparator) {

		return siteNavigationMenuPersistence.filterFindByGroupId(
			groupId, start, end, orderByComparator);
	}

	@Override
	public List<SiteNavigationMenu> getSiteNavigationMenus(
		long groupId, String keywords, int start, int end,
		OrderByComparator orderByComparator) {

		return siteNavigationMenuPersistence.filterFindByG_N(
			groupId, keywords, start, end, orderByComparator);
	}

	@Override
	public int getSiteNavigationMenusCount(long groupId) {
		return siteNavigationMenuPersistence.filterCountByGroupId(groupId);
	}

	@Override
	public int getSiteNavigationMenusCount(long groupId, String keywords) {
		return siteNavigationMenuPersistence.filterCountByG_N(
			groupId, keywords);
	}

	@Override
	public SiteNavigationMenu updateSiteNavigationMenu(
			long siteNavigationMenuId, boolean primary,
			ServiceContext serviceContext)
		throws PortalException {

		SiteNavigationMenuPermission.check(
			getPermissionChecker(), siteNavigationMenuId, ActionKeys.UPDATE);

		return siteNavigationMenuLocalService.updateSiteNavigationMenu(
			getUserId(), siteNavigationMenuId, primary, serviceContext);
	}

	@Override
	public SiteNavigationMenu updateSiteNavigationMenu(
			long siteNavigationMenuId, String name,
			ServiceContext serviceContext)
		throws PortalException {

		SiteNavigationMenuPermission.check(
			getPermissionChecker(), siteNavigationMenuId, ActionKeys.UPDATE);

		return siteNavigationMenuLocalService.updateSiteNavigationMenu(
			getUserId(), siteNavigationMenuId, name, serviceContext);
	}

}