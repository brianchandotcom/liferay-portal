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
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.portlet.toolbar.contributor.BasePortletToolbarContributor;
import com.liferay.portal.kernel.portlet.toolbar.contributor.PortletToolbarContributor;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.permission.LayoutPermissionUtil;
import com.liferay.portal.kernel.servlet.taglib.ui.MenuItem;
import com.liferay.portal.kernel.servlet.taglib.ui.URLMenuItem;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.site.navigation.menu.web.internal.constants.SiteNavigationMenuPortletKeys;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eduardo Garcia
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + SiteNavigationMenuPortletKeys.SITE_NAVIGATION_MENU,
		"mvc.path=-", "mvc.path=/view.jsp"
	},
	service = {
		SiteNavigationMenuPortletToolbarContributor.class,
		PortletToolbarContributor.class
	}
)
public class SiteNavigationMenuPortletToolbarContributor
	extends BasePortletToolbarContributor {

	protected void addPortletTitleAddSiteNavigationMenuItems(
			List<MenuItem> menuItems, ThemeDisplay themeDisplay,
			PortletRequest portletRequest)
		throws Exception {

		long scopeGroupId = themeDisplay.getScopeGroupId();

		Layout layout = themeDisplay.getLayout();

		boolean privateLayout = layout.isPrivateLayout();

		URLMenuItem urlMenuItem = new URLMenuItem();

		PortletURL portletURL = PortletProviderUtil.getPortletURL(
			portletRequest, Layout.class.getName(),
			PortletProvider.Action.EDIT);

		portletURL.setParameter("groupId", String.valueOf(scopeGroupId));
		portletURL.setParameter("mvcPath", "/add_layout.jsp");
		portletURL.setParameter("redirect", themeDisplay.getURLCurrent());

		if (privateLayout) {
			portletURL.setParameter(
				"privateLayout", String.valueOf(privateLayout));
		}

		urlMenuItem.setLabel(
			LanguageUtil.get(
				_portal.getHttpServletRequest(portletRequest), "add-page"));

		urlMenuItem.setURL(portletURL.toString());

		menuItems.add(urlMenuItem);
	}

	@Override
	protected List<MenuItem> getPortletTitleMenuItems(
		PortletRequest portletRequest, PortletResponse portletResponse) {

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		List<MenuItem> menuItems = new ArrayList<>();

		try {
			if (!LayoutPermissionUtil.contains(
					themeDisplay.getPermissionChecker(),
					themeDisplay.getLayout(), ActionKeys.UPDATE)) {

				return Collections.emptyList();
			}

			addPortletTitleAddSiteNavigationMenuItems(
				menuItems, themeDisplay, portletRequest);
		}
		catch (Exception e) {
			_log.error("Unable to set add layout to menu item", e);
		}

		return menuItems;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SiteNavigationMenuPortletToolbarContributor.class);

	@Reference
	private Portal _portal;

}