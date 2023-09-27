/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.navigation.util;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
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

		return SiteNavigationMenuItemLocalServiceUtil.addSiteNavigationMenuItem(
			TestPropsValues.getUserId(), siteNavigationMenu.getGroupId(),
			siteNavigationMenu.getSiteNavigationMenuId(), 0,
			SiteNavigationMenuItemTypeConstants.NODE, position,
			StringPool.BLANK,
			ServiceContextTestUtil.getServiceContext(
				siteNavigationMenu.getGroupId()));
	}

	public static SiteNavigationMenuItem addSiteNavigationMenuItem(
			SiteNavigationMenu siteNavigationMenu,
			long parentSiteNavigationMenuItemId)
		throws PortalException {

		return SiteNavigationMenuItemLocalServiceUtil.addSiteNavigationMenuItem(
			TestPropsValues.getUserId(), siteNavigationMenu.getGroupId(),
			siteNavigationMenu.getSiteNavigationMenuId(),
			parentSiteNavigationMenuItemId,
			SiteNavigationMenuItemTypeConstants.LAYOUT, StringPool.BLANK,
			ServiceContextTestUtil.getServiceContext(
				siteNavigationMenu.getGroupId()));
	}

}