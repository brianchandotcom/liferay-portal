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

package com.liferay.site.navigation.item.selector.web.internal.display.context;

import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.site.navigation.model.SiteNavigationMenu;
import com.liferay.site.navigation.service.SiteNavigationMenuServiceUtil;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Víctor Galán
 */
public class SelectSiteNavigationMenuDisplayContext {

	public SelectSiteNavigationMenuDisplayContext(
		HttpServletRequest httpServletRequest, PortletURL portletURL) {

		_httpServletRequest = httpServletRequest;
		_portletURL = portletURL;
	}

	public PortletURL gePortletURL() {
		return _portletURL;
	}

	public SearchContainer<SiteNavigationMenu> getSearchContainer() {
		SearchContainer<SiteNavigationMenu> searchContainer =
			new SearchContainer<>(
				_getPortletRequest(), _portletURL, null, "holi");

		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		long[] groupIds = {themeDisplay.getScopeGroupId()};

		Group scopeGroup = themeDisplay.getScopeGroup();

		if (!scopeGroup.isCompany()) {
			groupIds = ArrayUtil.append(
				groupIds, themeDisplay.getCompanyGroupId());
		}

		searchContainer.setResults(
			SiteNavigationMenuServiceUtil.getSiteNavigationMenus(
				groupIds, searchContainer.getStart(), searchContainer.getEnd(),
				null));
		searchContainer.setTotal(
			SiteNavigationMenuServiceUtil.getSiteNavigationMenusCount(
				groupIds));

		return searchContainer;
	}

	private PortletRequest _getPortletRequest() {
		return (PortletRequest)_httpServletRequest.getAttribute(
			JavaConstants.JAVAX_PORTLET_REQUEST);
	}

	private final HttpServletRequest _httpServletRequest;
	private final PortletURL _portletURL;

}