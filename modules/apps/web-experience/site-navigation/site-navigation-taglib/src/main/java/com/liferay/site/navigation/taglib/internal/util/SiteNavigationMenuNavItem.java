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

package com.liferay.site.navigation.taglib.internal.util;

import com.liferay.portal.kernel.theme.NavItem;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.site.navigation.model.SiteNavigationMenuItem;
import com.liferay.site.navigation.service.SiteNavigationMenuItemLocalServiceUtil;
import com.liferay.site.navigation.taglib.internal.servlet.ServletContextUtil;
import com.liferay.site.navigation.type.SiteNavigationMenuItemType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Pavel Savinov
 */
public class SiteNavigationMenuNavItem extends NavItem {

	public SiteNavigationMenuNavItem(
		HttpServletRequest request, ThemeDisplay themeDisplay,
		SiteNavigationMenuItem siteNavigationMenuItem) {

		super(
			request, themeDisplay, themeDisplay.getLayout(),
			new HashMap<String, Object>());

		SiteNavigationMenuItemType siteNavigationMenuItemType =
			ServletContextUtil.getSiteNavigationMenuItemType(
				siteNavigationMenuItem.getType());

		_request = request;
		_themeDisplay = themeDisplay;
		_siteNavigationMenuItem = siteNavigationMenuItem;
		_siteNavigationMenuItemType = siteNavigationMenuItemType;
	}

	@Override
	public List<NavItem> getChildren() {
		List<NavItem> navItems = new ArrayList<>();

		List<SiteNavigationMenuItem> siteNavigationMenuItems =
			SiteNavigationMenuItemLocalServiceUtil.getSiteNavigationMenuItems(
				_siteNavigationMenuItem.getSiteNavigationMenuId(),
				_siteNavigationMenuItem.getSiteNavigationMenuItemId());

		for (SiteNavigationMenuItem siteNavigationMenuItem :
				siteNavigationMenuItems) {

			navItems.add(
				new SiteNavigationMenuNavItem(
					_request, _themeDisplay, siteNavigationMenuItem));
		}

		return navItems;
	}

	@Override
	public String getRegularURL() throws Exception {
		return _siteNavigationMenuItemType.getRegularURL(
			_request, _siteNavigationMenuItem);
	}

	@Override
	public String getTitle() {
		return _siteNavigationMenuItemType.getTitle(
			_siteNavigationMenuItem, _themeDisplay.getLocale());
	}

	@Override
	public boolean isInNavigation(List<NavItem> navItems) {
		if (ListUtil.isEmpty(navItems)) {
			return true;
		}

		return super.isInNavigation(navItems);
	}

	private final HttpServletRequest _request;
	private final SiteNavigationMenuItem _siteNavigationMenuItem;
	private final SiteNavigationMenuItemType _siteNavigationMenuItemType;
	private final ThemeDisplay _themeDisplay;

}