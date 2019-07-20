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
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.site.navigation.menu.item.layout.constants.SiteNavigationMenuItemTypeConstants;
import com.liferay.site.navigation.model.SiteNavigationMenu;
import com.liferay.site.navigation.model.SiteNavigationMenuItem;
import com.liferay.site.navigation.service.SiteNavigationMenuItemLocalServiceUtil;

/**
 * @author Kyle Miho
 */
public class SiteNavigationMenuItemTestUtil {

	public static SiteNavigationMenuItem addSiteNavigationMenuItem(
			SiteNavigationMenu siteNavigationMenu)
		throws PortalException {

		return addSiteNavigationMenuItem(siteNavigationMenu, 0);
	}

	public static SiteNavigationMenuItem addSiteNavigationMenuItem(
			SiteNavigationMenu siteNavigationMenu, int position)
		throws PortalException {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				siteNavigationMenu.getGroupId());

		return SiteNavigationMenuItemLocalServiceUtil.addSiteNavigationMenuItem(
			TestPropsValues.getUserId(), siteNavigationMenu.getGroupId(),
			siteNavigationMenu.getSiteNavigationMenuId(), 0,
			SiteNavigationMenuItemTypeConstants.NODE, position,
			StringPool.BLANK, serviceContext);
	}

	public static SiteNavigationMenuItem addSiteNavigationMenuItem(
			SiteNavigationMenu siteNavigationMenu,
			long parentSiteNavigationMenuItemId)
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

}