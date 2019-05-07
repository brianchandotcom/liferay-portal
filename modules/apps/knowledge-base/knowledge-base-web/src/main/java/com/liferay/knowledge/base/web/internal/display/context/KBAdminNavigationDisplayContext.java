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

package com.liferay.knowledge.base.web.internal.display.context;

import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItem;
import com.liferay.knowledge.base.constants.KBActionKeys;
import com.liferay.knowledge.base.web.internal.security.permission.resource.AdminPermission;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.service.permission.PortletPermissionUtil;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Sergio González
 */
public class KBAdminNavigationDisplayContext {

	public KBAdminNavigationDisplayContext(
		HttpServletRequest httpServletRequest,
		LiferayPortletResponse liferayPortletResponse) {

		_httpServletRequest = httpServletRequest;
		_liferayPortletResponse = liferayPortletResponse;
	}

	public List<NavigationItem> getInfoPanelNavigationItems() {
		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		List<NavigationItem> navigationItems = new ArrayList<>();

		NavigationItem navigationItem = new NavigationItem();

		navigationItem.setActive(true);
		navigationItem.setHref(themeDisplay.getURLCurrent());
		navigationItem.setLabel(
			LanguageUtil.get(_httpServletRequest, "details"));

		navigationItems.add(navigationItem);

		return navigationItems;
	}

	public List<NavigationItem> getNavigationItems() throws PortalException {
		List<NavigationItem> navigationItems = new ArrayList<>();

		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		String mvcPath = ParamUtil.getString(_httpServletRequest, "mvcPath");

		if (PortletPermissionUtil.contains(
				themeDisplay.getPermissionChecker(), themeDisplay.getPlid(),
				portletDisplay.getId(), KBActionKeys.VIEW)) {

			NavigationItem kbObjectsNavigationItem = new NavigationItem();

			boolean active = false;

			if (!mvcPath.equals("/admin/view_suggestions.jsp") &&
				!mvcPath.equals("/admin/view_templates.jsp")) {

				active = true;
			}

			kbObjectsNavigationItem.setActive(active);

			PortletURL viewKBObjectsURL =
				_liferayPortletResponse.createRenderURL();

			viewKBObjectsURL.setParameter("mvcPath", "/admin/view.jsp");

			kbObjectsNavigationItem.setHref(viewKBObjectsURL.toString());

			kbObjectsNavigationItem.setLabel(
				LanguageUtil.get(_httpServletRequest, "articles"));

			navigationItems.add(kbObjectsNavigationItem);
		}

		if (AdminPermission.contains(
				themeDisplay.getPermissionChecker(),
				themeDisplay.getScopeGroupId(),
				KBActionKeys.VIEW_KB_TEMPLATES)) {

			NavigationItem kbTemplatesNavigationItem = new NavigationItem();

			boolean active = false;

			if (mvcPath.equals("/admin/view_templates.jsp")) {
				active = true;
			}

			kbTemplatesNavigationItem.setActive(active);

			PortletURL viewKBTemplatesURL =
				_liferayPortletResponse.createRenderURL();

			viewKBTemplatesURL.setParameter(
				"mvcPath", "/admin/view_templates.jsp");

			kbTemplatesNavigationItem.setHref(viewKBTemplatesURL.toString());

			kbTemplatesNavigationItem.setLabel(
				LanguageUtil.get(_httpServletRequest, "templates"));

			navigationItems.add(kbTemplatesNavigationItem);
		}

		if (AdminPermission.contains(
				themeDisplay.getPermissionChecker(),
				themeDisplay.getScopeGroupId(),
				KBActionKeys.VIEW_SUGGESTIONS)) {

			NavigationItem kbSuggestionsNavigationItem = new NavigationItem();

			boolean active = false;

			if (mvcPath.equals("/admin/view_suggestions.jsp")) {
				active = true;
			}

			kbSuggestionsNavigationItem.setActive(active);

			PortletURL viewKBTemplatesURL =
				_liferayPortletResponse.createRenderURL();

			viewKBTemplatesURL.setParameter(
				"mvcPath", "/admin/view_suggestions.jsp");

			kbSuggestionsNavigationItem.setHref(viewKBTemplatesURL.toString());

			kbSuggestionsNavigationItem.setLabel(
				LanguageUtil.get(_httpServletRequest, "suggestions"));

			navigationItems.add(kbSuggestionsNavigationItem);
		}

		return navigationItems;
	}

	private final HttpServletRequest _httpServletRequest;
	private final LiferayPortletResponse _liferayPortletResponse;

}