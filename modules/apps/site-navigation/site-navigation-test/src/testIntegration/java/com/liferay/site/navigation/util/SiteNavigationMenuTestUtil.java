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

package com.liferay.site.navigation.util;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.site.navigation.menu.item.layout.constants.SiteNavigationMenuItemTypeConstants;
import com.liferay.site.navigation.model.SiteNavigationMenu;
import com.liferay.site.navigation.model.SiteNavigationMenuItem;
import com.liferay.site.navigation.service.SiteNavigationMenuItemLocalServiceUtil;
import com.liferay.site.navigation.service.SiteNavigationMenuLocalServiceUtil;

/**
 * @author Kyle Miho
 */
public class SiteNavigationMenuTestUtil {

	public static SiteNavigationMenu addSiteNavigationMenu(Group group)
		throws PortalException {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		return SiteNavigationMenuLocalServiceUtil.addSiteNavigationMenu(
			TestPropsValues.getUserId(), group.getGroupId(),
			RandomTestUtil.randomString(), serviceContext);
	}

	public static SiteNavigationMenuItem addSiteNavigationMenuItem(
			int position, SiteNavigationMenu siteNavigationMenu)
		throws PortalException {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				siteNavigationMenu.getGroupId());

		return SiteNavigationMenuItemLocalServiceUtil.addSiteNavigationMenuItem(
			TestPropsValues.getUserId(), siteNavigationMenu.getGroupId(),
			siteNavigationMenu.getSiteNavigationMenuId(), 0,
			SiteNavigationMenuItemTypeConstants.LAYOUT, position,
			StringPool.BLANK, serviceContext);
	}

	public static SiteNavigationMenuItem addSiteNavigationMenuItem(
			long parentSiteNavigationMenuItemId,
			SiteNavigationMenu siteNavigationMenu)
		throws PortalException {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				siteNavigationMenu.getGroupId());

		return SiteNavigationMenuItemLocalServiceUtil.addSiteNavigationMenuItem(
			TestPropsValues.getUserId(), siteNavigationMenu.getGroupId(),
			siteNavigationMenu.getSiteNavigationMenuId(),
			parentSiteNavigationMenuItemId,
			SiteNavigationMenuItemTypeConstants.LAYOUT, StringPool.BLANK,
			serviceContext);
	}

	public static SiteNavigationMenuItem addSiteNavigationMenuItem(
			SiteNavigationMenu siteNavigationMenu)
		throws PortalException {

		return addSiteNavigationMenuItem(0, siteNavigationMenu);
	}

}