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
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.theme.NavItem;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.site.navigation.model.SiteNavigationMenuItem;
import com.liferay.site.navigation.service.SiteNavigationMenuItemLocalService;
import com.liferay.site.navigation.type.SiteNavigationMenuItemType;
import com.liferay.site.navigation.type.SiteNavigationMenuItemTypeRegistry;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pavel Savinov
 */
@Component(immediate = true, service = {})
public class NavItemUtil {

	public static List<NavItem> getChildNavItems(
		HttpServletRequest request, long siteNavigationMenuId,
		long parentSiteNavigationMenuItemId) {

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		List<SiteNavigationMenuItem> siteNavigationMenuItems =
			_siteNavigationMenuItemLocalService.getSiteNavigationMenuItems(
				siteNavigationMenuId, parentSiteNavigationMenuItemId);

		List<NavItem> navItems = new ArrayList<>(
			siteNavigationMenuItems.size());

		for (SiteNavigationMenuItem siteNavigationMenuItem :
				siteNavigationMenuItems) {

			SiteNavigationMenuItemType siteNavigationMenuItemType =
				_siteNavigationMenuItemTypeRegistry.
					getSiteNavigationMenuItemType(
						siteNavigationMenuItem.getType());

			try {
				if (!siteNavigationMenuItemType.hasPermission(
						themeDisplay.getPermissionChecker(),
						siteNavigationMenuItem)) {

					continue;
				}

				navItems.add(
					new SiteNavigationMenuNavItem(
						request, themeDisplay, siteNavigationMenuItem));
			}
			catch (PortalException pe) {
				if (_log.isDebugEnabled()) {
					_log.debug(pe, pe);
				}
			}
		}

		return navItems;
	}

	public static List<NavItem> getNavItems(
			HttpServletRequest request, String rootLayoutType,
			int rootLayoutLevel, String rootLayoutUuid,
			List<NavItem> branchNavItems)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		NavItem rootNavItem = null;
		List<NavItem> navItems = null;

		if (rootLayoutType.equals("relative")) {
			if ((rootLayoutLevel >= 0) &&
				(rootLayoutLevel < branchNavItems.size())) {

				rootNavItem = branchNavItems.get(rootLayoutLevel);
			}
		}
		else if (rootLayoutType.equals("absolute")) {
			if (rootLayoutLevel == 0) {
				navItems = NavItem.fromLayouts(request, themeDisplay, null);
			}
			else if (branchNavItems.size() >= rootLayoutLevel) {
				rootNavItem = branchNavItems.get(rootLayoutLevel - 1);
			}
		}
		else if (rootLayoutType.equals("select")) {
			Layout layout = themeDisplay.getLayout();

			if (Validator.isNotNull(rootLayoutUuid)) {
				Layout rootLayout =
					_layoutLocalService.getLayoutByUuidAndGroupId(
						rootLayoutUuid, layout.getGroupId(),
						layout.isPrivateLayout());

				rootNavItem = new NavItem(
					request, themeDisplay, rootLayout, null);
			}
			else {
				navItems = NavItem.fromLayouts(request, themeDisplay, null);
			}
		}

		if (rootNavItem == null) {
			if (navItems == null) {
				return new ArrayList<>();
			}

			return navItems;
		}

		return rootNavItem.getChildren();
	}

	@Reference(unbind = "-")
	protected void setLayoutLocalService(
		LayoutLocalService layoutLocalService) {

		_layoutLocalService = layoutLocalService;
	}

	@Reference(unbind = "-")
	protected void setSiteNavigationMenuItemLocalService(
		SiteNavigationMenuItemLocalService siteNavigationMenuItemLocalService) {

		_siteNavigationMenuItemLocalService =
			siteNavigationMenuItemLocalService;
	}

	@Reference(unbind = "-")
	protected void setSiteNavigationMenuItemTypeRegistry(
		SiteNavigationMenuItemTypeRegistry siteNavigationMenuItemTypeRegistry) {

		_siteNavigationMenuItemTypeRegistry =
			siteNavigationMenuItemTypeRegistry;
	}

	private static final Log _log = LogFactoryUtil.getLog(NavItemUtil.class);

	private static LayoutLocalService _layoutLocalService;
	private static SiteNavigationMenuItemLocalService
		_siteNavigationMenuItemLocalService;
	private static SiteNavigationMenuItemTypeRegistry
		_siteNavigationMenuItemTypeRegistry;

}