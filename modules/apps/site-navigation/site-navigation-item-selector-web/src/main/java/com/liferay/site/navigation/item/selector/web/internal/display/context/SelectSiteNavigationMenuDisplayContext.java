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
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.site.navigation.model.SiteNavigationMenu;
import com.liferay.site.navigation.service.SiteNavigationMenuLocalServiceUtil;
import com.liferay.site.navigation.service.SiteNavigationMenuServiceUtil;

import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
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

	public SearchContainer<SiteNavigationMenu> getSearchContainer() {
		SearchContainer<SiteNavigationMenu> searchContainer =
			new SearchContainer<>(
				_getPortletRequest(), _portletURL, null, null);

		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		long[] groupIds = {themeDisplay.getScopeGroupId()};

		Group scopeGroup = themeDisplay.getScopeGroup();

		if (!scopeGroup.isCompany()) {
			groupIds = ArrayUtil.append(
				groupIds, themeDisplay.getCompanyGroupId());
		}

		List<SiteNavigationMenu> siteNavigationMenus =
			SiteNavigationMenuServiceUtil.getSiteNavigationMenus(
				groupIds, searchContainer.getStart(), searchContainer.getEnd(),
				null);

		int siteNavigationMenusCount =
			SiteNavigationMenuServiceUtil.getSiteNavigationMenusCount(groupIds);

		if (searchContainer.getStart() == 0) {
			siteNavigationMenus = ListUtil.concat(
				Collections.singletonList(
					_getPublicPagesHierarchySiteNavigationMenu()),
				siteNavigationMenus);

			siteNavigationMenusCount++;
		}

		searchContainer.setResults(siteNavigationMenus);

		searchContainer.setTotal(siteNavigationMenusCount);

		return searchContainer;
	}

	public String getSelectSiteNavigationMenuLevelURL(long siteNavigationMenuId)
		throws PortletException {

		PortletResponse portletResponse =
			(PortletResponse)_httpServletRequest.getAttribute(
				JavaConstants.JAVAX_PORTLET_RESPONSE);

		PortletURL portletURL = PortletURLUtil.clone(
			_portletURL, PortalUtil.getLiferayPortletResponse(portletResponse));

		portletURL.setParameter(
			"backURL", PortalUtil.getCurrentURL(_httpServletRequest));
		portletURL.setParameter(
			"siteNavigationMenuId", String.valueOf(siteNavigationMenuId));

		return portletURL.toString();
	}

	private PortletRequest _getPortletRequest() {
		return (PortletRequest)_httpServletRequest.getAttribute(
			JavaConstants.JAVAX_PORTLET_REQUEST);
	}

	private SiteNavigationMenu _getPublicPagesHierarchySiteNavigationMenu() {
		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			themeDisplay.getLocale(), getClass());

		SiteNavigationMenu siteNavigationMenu =
			SiteNavigationMenuLocalServiceUtil.createSiteNavigationMenu(0);

		siteNavigationMenu.setGroupId(themeDisplay.getScopeGroupId());
		siteNavigationMenu.setName(
			LanguageUtil.get(resourceBundle, "public-pages-hierarchy"));

		return siteNavigationMenu;
	}

	private final HttpServletRequest _httpServletRequest;
	private final PortletURL _portletURL;

}