/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.navigation.taglib.internal.util;

import com.liferay.osgi.util.service.Snapshot;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.permission.LayoutPermissionUtil;
import com.liferay.portal.kernel.theme.NavItem;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.site.navigation.model.SiteNavigationMenuItem;
import com.liferay.site.navigation.service.SiteNavigationMenuItemLocalService;
import com.liferay.site.navigation.service.SiteNavigationMenuItemService;
import com.liferay.site.navigation.taglib.servlet.taglib.NavigationMenuMode;
import com.liferay.site.navigation.type.SiteNavigationMenuItemType;
import com.liferay.site.navigation.type.SiteNavigationMenuItemTypeRegistry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Pavel Savinov
 */
public class NavItemUtil {

	public static List<NavItem> getBranchNavItems(
			HttpServletRequest httpServletRequest)
		throws PortalException {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		Layout layout = themeDisplay.getLayout();

		if (layout.isDraftLayout()) {
			LayoutLocalService layoutLocalService =
				_layoutLocalServiceSnapshot.get();

			layout = layoutLocalService.fetchLayout(layout.getClassPK());
		}

		if (layout.isRootLayout()) {
			return Collections.singletonList(
				new NavItem(httpServletRequest, themeDisplay, layout));
		}

		List<Layout> ancestorLayouts = layout.getAncestors();

		List<NavItem> navItems = new ArrayList<>(ancestorLayouts.size() + 1);

		for (int i = ancestorLayouts.size() - 1; i >= 0; i--) {
			Layout ancestorLayout = ancestorLayouts.get(i);

			navItems.add(
				new NavItem(httpServletRequest, themeDisplay, ancestorLayout));
		}

		navItems.add(new NavItem(httpServletRequest, themeDisplay, layout));

		return navItems;
	}

	public static List<NavItem> getChildNavItems(
		HttpServletRequest httpServletRequest, long siteNavigationMenuId,
		long parentSiteNavigationMenuItemId) {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		List<SiteNavigationMenuItem> siteNavigationMenuItems =
			_getSiteNavigationMenuItems(
				httpServletRequest, siteNavigationMenuId,
				parentSiteNavigationMenuItemId);

		List<NavItem> navItems = new ArrayList<>(
			siteNavigationMenuItems.size());

		for (SiteNavigationMenuItem siteNavigationMenuItem :
				siteNavigationMenuItems) {

			SiteNavigationMenuItemTypeRegistry
				siteNavigationMenuItemTypeRegistry =
					_siteNavigationMenuItemTypeRegistrySnapshot.get();

			SiteNavigationMenuItemType siteNavigationMenuItemType =
				siteNavigationMenuItemTypeRegistry.
					getSiteNavigationMenuItemType(
						siteNavigationMenuItem.getType());

			try {
				if ((siteNavigationMenuItemType == null) ||
					!siteNavigationMenuItemType.hasPermission(
						themeDisplay.getPermissionChecker(),
						siteNavigationMenuItem)) {

					continue;
				}

				if (!siteNavigationMenuItemType.isDynamic()) {
					navItems.add(
						new SiteNavigationMenuNavItemImpl(
							httpServletRequest, themeDisplay,
							siteNavigationMenuItem));

					continue;
				}

				for (SiteNavigationMenuItem dynamicSiteNavigationMenuItem :
						siteNavigationMenuItemType.getSiteNavigationMenuItems(
							httpServletRequest, siteNavigationMenuItem)) {

					navItems.add(
						new SiteNavigationMenuNavItemImpl(
							httpServletRequest, themeDisplay,
							dynamicSiteNavigationMenuItem));
				}
			}
			catch (Exception exception) {
				if (_log.isDebugEnabled()) {
					_log.debug(exception);
				}
			}
		}

		return navItems;
	}

	public static List<NavItem> getNavItems(
			NavigationMenuMode navigationMenuMode,
			HttpServletRequest httpServletRequest, String rootLayoutType,
			int rootLayoutLevel, String rootLayoutUuid,
			List<NavItem> branchNavItems)
		throws Exception {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		List<NavItem> navItems = null;
		NavItem rootNavItem = null;

		if (rootLayoutType.equals("absolute")) {
			if (rootLayoutLevel == 0) {
				navItems = _fromLayouts(
					navigationMenuMode, httpServletRequest, themeDisplay);
			}
			else if (branchNavItems.size() >= rootLayoutLevel) {
				rootNavItem = branchNavItems.get(rootLayoutLevel - 1);
			}
		}
		else if (rootLayoutType.equals("relative")) {
			if ((rootLayoutLevel >= 0) &&
				(rootLayoutLevel <= (branchNavItems.size() + 1))) {

				int absoluteLevel = branchNavItems.size() - 1 - rootLayoutLevel;

				if (absoluteLevel == -1) {
					navItems = _fromLayouts(
						navigationMenuMode, httpServletRequest, themeDisplay);
				}
				else if ((absoluteLevel >= 0) &&
						 (absoluteLevel < branchNavItems.size())) {

					rootNavItem = branchNavItems.get(absoluteLevel);
				}
			}
		}
		else if (rootLayoutType.equals("select")) {
			if (Validator.isNotNull(rootLayoutUuid)) {
				Layout layout = themeDisplay.getLayout();

				LayoutLocalService layoutLocalService =
					_layoutLocalServiceSnapshot.get();

				Layout rootLayout =
					layoutLocalService.fetchLayoutByUuidAndGroupId(
						rootLayoutUuid, layout.getGroupId(), false);

				if (rootLayout == null) {
					rootLayout = layoutLocalService.fetchLayoutByUuidAndGroupId(
						rootLayoutUuid, layout.getGroupId(), true);
				}

				if (rootLayout != null) {
					rootNavItem = new NavItem(
						httpServletRequest, themeDisplay, rootLayout);
				}
			}
			else {
				navItems = _fromLayouts(
					navigationMenuMode, httpServletRequest, themeDisplay);
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

	private static List<NavItem> _fromLayouts(
			NavigationMenuMode navigationMenuMode,
			HttpServletRequest httpServletRequest, ThemeDisplay themeDisplay)
		throws Exception {

		if (navigationMenuMode == NavigationMenuMode.DEFAULT) {
			return themeDisplay.getNavItems();
		}

		boolean privateLayout = false;

		if (navigationMenuMode == NavigationMenuMode.PRIVATE_PAGES) {
			privateLayout = true;
		}

		return NavItem.fromLayouts(
			httpServletRequest, _getLayouts(privateLayout, themeDisplay),
			themeDisplay);
	}

	private static List<Layout> _getLayouts(
			boolean privateLayout, ThemeDisplay themeDisplay)
		throws Exception {

		LayoutLocalService layoutLocalService =
			_layoutLocalServiceSnapshot.get();

		List<Layout> layouts = ListUtil.copy(
			layoutLocalService.getLayouts(
				themeDisplay.getScopeGroupId(), privateLayout,
				LayoutConstants.DEFAULT_PARENT_LAYOUT_ID));

		Iterator<Layout> iterator = layouts.iterator();

		while (iterator.hasNext()) {
			Layout layout = iterator.next();

			if (layout.isHidden() ||
				!LayoutPermissionUtil.contains(
					themeDisplay.getPermissionChecker(), layout,
					ActionKeys.VIEW)) {

				iterator.remove();
			}
		}

		return layouts;
	}

	private static List<SiteNavigationMenuItem> _getSiteNavigationMenuItems(
		HttpServletRequest httpServletRequest, long siteNavigationMenuId,
		long parentSiteNavigationMenuItemId) {

		try {
			if (parentSiteNavigationMenuItemId == 0) {
				SiteNavigationMenuItemService siteNavigationMenuItemService =
					_siteNavigationMenuItemServiceSnapshot.get();

				return siteNavigationMenuItemService.getSiteNavigationMenuItems(
					siteNavigationMenuId, parentSiteNavigationMenuItemId);
			}

			SiteNavigationMenuItemLocalService
				siteNavigationMenuItemLocalService =
					_siteNavigationMenuItemLocalServiceSnapshot.get();

			SiteNavigationMenuItem parentSiteNavigationMenuItem =
				siteNavigationMenuItemLocalService.getSiteNavigationMenuItem(
					parentSiteNavigationMenuItemId);

			SiteNavigationMenuItemTypeRegistry
				siteNavigationMenuItemTypeRegistry =
					_siteNavigationMenuItemTypeRegistrySnapshot.get();

			SiteNavigationMenuItemType siteNavigationMenuItemType =
				siteNavigationMenuItemTypeRegistry.
					getSiteNavigationMenuItemType(
						parentSiteNavigationMenuItem.getType());

			if (siteNavigationMenuItemType.isDynamic()) {
				return siteNavigationMenuItemType.
					getChildrenSiteNavigationMenuItems(
						httpServletRequest, parentSiteNavigationMenuItem);
			}

			SiteNavigationMenuItemService siteNavigationMenuItemService =
				_siteNavigationMenuItemServiceSnapshot.get();

			return siteNavigationMenuItemService.getSiteNavigationMenuItems(
				siteNavigationMenuId, parentSiteNavigationMenuItemId);
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to get site navigation menu items", exception);
			}
		}

		return Collections.emptyList();
	}

	private static final Log _log = LogFactoryUtil.getLog(NavItemUtil.class);

	private static final Snapshot<LayoutLocalService>
		_layoutLocalServiceSnapshot = new Snapshot<>(
			NavItemUtil.class, LayoutLocalService.class);
	private static final Snapshot<SiteNavigationMenuItemLocalService>
		_siteNavigationMenuItemLocalServiceSnapshot = new Snapshot<>(
			NavItemUtil.class, SiteNavigationMenuItemLocalService.class);
	private static final Snapshot<SiteNavigationMenuItemService>
		_siteNavigationMenuItemServiceSnapshot = new Snapshot<>(
			NavItemUtil.class, SiteNavigationMenuItemService.class);
	private static final Snapshot<SiteNavigationMenuItemTypeRegistry>
		_siteNavigationMenuItemTypeRegistrySnapshot = new Snapshot<>(
			NavItemUtil.class, SiteNavigationMenuItemTypeRegistry.class);

}