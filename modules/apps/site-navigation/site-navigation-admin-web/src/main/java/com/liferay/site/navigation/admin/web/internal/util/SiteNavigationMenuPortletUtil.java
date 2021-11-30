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

package com.liferay.site.navigation.admin.web.internal.util;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.site.navigation.model.SiteNavigationMenu;
import com.liferay.site.navigation.model.SiteNavigationMenuItem;
import com.liferay.site.navigation.service.SiteNavigationMenuItemLocalServiceUtil;
import com.liferay.site.navigation.type.SiteNavigationMenuItemType;
import com.liferay.site.navigation.type.SiteNavigationMenuItemTypeRegistry;
import com.liferay.site.navigation.util.comparator.SiteNavigationMenuCreateDateComparator;
import com.liferay.site.navigation.util.comparator.SiteNavigationMenuNameComparator;

import java.util.List;

/**
 * @author Pavel Savinov
 */
public class SiteNavigationMenuPortletUtil {

	public static OrderByComparator<SiteNavigationMenu> getOrderByComparator(
		String orderByCol, String orderByType) {

		boolean orderByAsc = false;

		if (orderByType.equals("asc")) {
			orderByAsc = true;
		}

		OrderByComparator<SiteNavigationMenu> orderByComparator = null;

		if (orderByCol.equals("create-date")) {
			orderByComparator = new SiteNavigationMenuCreateDateComparator(
				orderByAsc);
		}
		else if (orderByCol.equals("name")) {
			orderByComparator = new SiteNavigationMenuNameComparator(
				orderByAsc);
		}

		return orderByComparator;
	}

	public static JSONArray getSiteNavigationMenuItemsJSONArray(
		long parentSiteNavigationMenuItemId, long siteNavigationMenuId,
		SiteNavigationMenuItemTypeRegistry siteNavigationMenuItemTypeRegistry,
		ThemeDisplay themeDisplay) {

		List<SiteNavigationMenuItem> siteNavigationMenuItems =
			SiteNavigationMenuItemLocalServiceUtil.getSiteNavigationMenuItems(
				siteNavigationMenuId, parentSiteNavigationMenuItemId);

		JSONArray siteNavigationMenuItemsJSONArray =
			JSONFactoryUtil.createJSONArray();

		for (SiteNavigationMenuItem siteNavigationMenuItem :
				siteNavigationMenuItems) {

			long siteNavigationMenuItemId =
				siteNavigationMenuItem.getSiteNavigationMenuItemId();
			SiteNavigationMenuItemType siteNavigationMenuItemType =
				siteNavigationMenuItemTypeRegistry.
					getSiteNavigationMenuItemType(
						siteNavigationMenuItem.getType());

			siteNavigationMenuItemsJSONArray.put(
				JSONUtil.put(
					"children",
					getSiteNavigationMenuItemsJSONArray(
						siteNavigationMenuItemId, siteNavigationMenuId,
						siteNavigationMenuItemTypeRegistry, themeDisplay)
				).put(
					"parentSiteNavigationMenuItemId",
					parentSiteNavigationMenuItemId
				).put(
					"siteNavigationMenuItemId", siteNavigationMenuItemId
				).put(
					"title",
					siteNavigationMenuItemType.getTitle(
						siteNavigationMenuItem, themeDisplay.getLocale())
				).put(
					"type",
					siteNavigationMenuItemType.getSubtitle(
						siteNavigationMenuItem, themeDisplay.getLocale())
				));
		}

		return siteNavigationMenuItemsJSONArray;
	}

}