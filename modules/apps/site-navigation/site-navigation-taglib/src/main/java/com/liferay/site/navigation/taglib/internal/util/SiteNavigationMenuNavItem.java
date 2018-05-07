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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.theme.NavItem;
import com.liferay.portal.kernel.theme.ThemeDisplay;
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
	public boolean equals(Object obj) {
		return _siteNavigationMenuItem.equals(obj);
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
	public Layout getLayout() {
		return _siteNavigationMenuItemType.getLayout(_siteNavigationMenuItem);
	}

	@Override
	public long getLayoutId() {
		return _siteNavigationMenuItem.getSiteNavigationMenuItemId();
	}

	@Override
	public String getRegularURL() throws Exception {
		return _siteNavigationMenuItemType.getRegularURL(
			_request, _siteNavigationMenuItem);
	}

	@Override
	public String getResetLayoutURL() throws Exception {
		return _siteNavigationMenuItemType.getResetLayoutURL(
			_request, _siteNavigationMenuItem);
	}

	@Override
	public String getResetMaxStateURL() throws Exception {
		return _siteNavigationMenuItemType.getResetMaxStateURL(
			_request, _siteNavigationMenuItem);
	}

	@Override
	public String getTarget() {
		return _siteNavigationMenuItemType.getTarget(_siteNavigationMenuItem);
	}

	@Override
	public String getTitle() {
		return _siteNavigationMenuItemType.getTitle(
			_siteNavigationMenuItem, _themeDisplay.getLocale());
	}

	@Override
	public String getUnescapedName() {
		return _siteNavigationMenuItemType.getUnescapedName(
			_siteNavigationMenuItem, _themeDisplay.getLanguageId());
	}

	@Override
	public int hashCode() {
		return _siteNavigationMenuItem.hashCode();
	}

	@Override
	public String iconURL() {
		return _siteNavigationMenuItemType.iconURL(
			_siteNavigationMenuItem, _themeDisplay.getPathImage());
	}

	@Override
	public boolean isBrowsable() {
		return _siteNavigationMenuItemType.isBrowsable(_siteNavigationMenuItem);
	}

	@Override
	public boolean isChildSelected() throws PortalException {
		return _siteNavigationMenuItemType.isChildSelected(
			_themeDisplay.isTilesSelectable(), _siteNavigationMenuItem,
			_themeDisplay.getLayout());
	}

	@Override
	public boolean isSelected() throws Exception {
		return _siteNavigationMenuItemType.isSelected(
			_themeDisplay.isTilesSelectable(), _siteNavigationMenuItem,
			_themeDisplay.getLayout());
	}

	private final HttpServletRequest _request;
	private final SiteNavigationMenuItem _siteNavigationMenuItem;
	private final SiteNavigationMenuItemType _siteNavigationMenuItemType;
	private final ThemeDisplay _themeDisplay;

}