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

package com.liferay.site.navigation.menu.web.internal.portlet.contributor;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.portlet.toolbar.contributor.PortletToolbarContributor;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.permission.LayoutPermissionUtil;
import com.liferay.portal.kernel.servlet.taglib.ui.Menu;
import com.liferay.portal.kernel.servlet.taglib.ui.MenuItem;
import com.liferay.portal.kernel.servlet.taglib.ui.URLMenuItem;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.site.navigation.menu.web.internal.constants.SiteNavigationMenuPortletKeys;
import com.liferay.site.navigation.menu.web.internal.display.context.SiteNavigationMenuDisplayContext;
import com.liferay.site.navigation.model.SiteNavigationMenu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jürgen Kappler
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + SiteNavigationMenuPortletKeys.SITE_NAVIGATION_MENU,
		"mvc.path=-", "mvc.path=/view.jsp"
	},
	service = {
		PortletToolbarContributor.class,
		SiteNavigationMenuEditPortletToolbarContributor.class
	}
)
public class SiteNavigationMenuEditPortletToolbarContributor
	implements PortletToolbarContributor {

	@Override
	public List<Menu> getPortletTitleMenus(
		PortletRequest portletRequest, PortletResponse portletResponse) {

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		Group scopeGroup = themeDisplay.getScopeGroup();

		if ((scopeGroup == null) || scopeGroup.isLayoutPrototype()) {
			return Collections.emptyList();
		}

		List<MenuItem> menuItems = _getPortletTitleMenuItems(portletRequest);

		if (menuItems.isEmpty()) {
			return Collections.emptyList();
		}

		List<Menu> menus = new ArrayList<>();

		Menu menu = new Menu();

		menu.setDirection("right");
		menu.setExtended(false);
		menu.setIcon("pencil");
		menu.setMarkupView("lexicon");
		menu.setMenuItems(menuItems);
		menu.setMessage("edit");
		menu.setScroll(false);
		menu.setShowArrow(false);
		menu.setShowWhenSingleIcon(true);

		menus.add(menu);

		return menus;
	}

	private MenuItem _createMenuItem(
			ThemeDisplay themeDisplay, PortletRequest portletRequest)
		throws Exception {

		SiteNavigationMenuDisplayContext siteNavigationMenuDisplayContext =
			new SiteNavigationMenuDisplayContext(
				_portal.getHttpServletRequest(portletRequest));

		long siteNavigationMenuId =
			siteNavigationMenuDisplayContext.getSelectSiteNavigationMenuId();

		if (siteNavigationMenuId <= 0) {
			return null;
		}

		URLMenuItem urlMenuItem = new URLMenuItem();

		urlMenuItem.setLabel(
			LanguageUtil.get(
				_portal.getHttpServletRequest(portletRequest), "edit"));

		PortletURL portletURL = PortletProviderUtil.getPortletURL(
			portletRequest, SiteNavigationMenu.class.getName(),
			PortletProvider.Action.EDIT);

		portletURL.setParameter("mvcPath", "/edit_site_navigation_menu.jsp");
		portletURL.setParameter("redirect", themeDisplay.getURLCurrent());
		portletURL.setParameter(
			"siteNavigationMenuId", String.valueOf(siteNavigationMenuId));

		urlMenuItem.setURL(portletURL.toString());

		return urlMenuItem;
	}

	private List<MenuItem> _getPortletTitleMenuItems(
		PortletRequest portletRequest) {

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		try {
			if (!LayoutPermissionUtil.contains(
					themeDisplay.getPermissionChecker(),
					themeDisplay.getLayout(), ActionKeys.UPDATE)) {

				return Collections.emptyList();
			}

			MenuItem menuItem = _createMenuItem(themeDisplay, portletRequest);

			if (menuItem == null) {
				return Collections.emptyList();
			}

			return Collections.singletonList(menuItem);
		}
		catch (Exception e) {
			_log.error(
				"Unable to set edit site navigation menu to menu item", e);

			return Collections.emptyList();
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SiteNavigationMenuEditPortletToolbarContributor.class);

	@Reference
	private Portal _portal;

}