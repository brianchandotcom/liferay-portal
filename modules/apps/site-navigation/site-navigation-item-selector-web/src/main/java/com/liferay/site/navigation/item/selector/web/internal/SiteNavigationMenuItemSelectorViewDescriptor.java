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

package com.liferay.site.navigation.item.selector.web.internal;

import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.item.selector.ItemSelectorViewDescriptor;
import com.liferay.item.selector.criteria.UUIDItemSelectorReturnType;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.site.navigation.model.SiteNavigationMenu;
import com.liferay.site.navigation.service.SiteNavigationMenuServiceUtil;
import com.liferay.site.navigation.util.comparator.SiteNavigationMenuModifiedDateComparator;
import com.liferay.site.navigation.util.comparator.SiteNavigationMenuNameComparator;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class SiteNavigationMenuItemSelectorViewDescriptor
	implements ItemSelectorViewDescriptor<SiteNavigationMenu> {

	public SiteNavigationMenuItemSelectorViewDescriptor(
		HttpServletRequest httpServletRequest, PortletURL portletURL) {

		_httpServletRequest = httpServletRequest;
		_portletURL = portletURL;

		_themeDisplay = (ThemeDisplay)_httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	@Override
	public String getDefaultDisplayStyle() {
		return "list";
	}

	@Override
	public ItemDescriptor getItemDescriptor(
		SiteNavigationMenu siteNavigationMenu) {

		return new SiteNavigationMenuItemDescriptor(
			siteNavigationMenu, _httpServletRequest);
	}

	@Override
	public ItemSelectorReturnType getItemSelectorReturnType() {
		return new UUIDItemSelectorReturnType();
	}

	@Override
	public String[] getOrderByKeys() {
		return new String[] {"modified-date", "name"};
	}

	@Override
	public SearchContainer<SiteNavigationMenu> getSearchContainer()
		throws PortalException {

		SearchContainer<SiteNavigationMenu> searchContainer =
			new SearchContainer<>(
				(PortletRequest)_httpServletRequest.getAttribute(
					JavaConstants.JAVAX_PORTLET_REQUEST),
				_portletURL, null, "there-are-no-navigation-menus");

		String orderByCol = ParamUtil.getString(
			_httpServletRequest, "orderByCol", "modified-date");

		searchContainer.setOrderByCol(orderByCol);

		String orderByType = ParamUtil.getString(
			_httpServletRequest, "orderByType", "asc");

		searchContainer.setOrderByComparator(
			_getOrderByComparator(orderByCol, orderByType));
		searchContainer.setOrderByType(orderByType);

		long[] groupIds = {_themeDisplay.getScopeGroupId()};

		Group scopeGroup = _themeDisplay.getScopeGroup();

		if (!scopeGroup.isCompany()) {
			groupIds = ArrayUtil.append(
				groupIds, _themeDisplay.getCompanyGroupId());
		}

		long[] siteNavigationMenuGroupIds = groupIds;

		String keywords = ParamUtil.getString(_httpServletRequest, "keywords");

		if (Validator.isNotNull(keywords)) {
			searchContainer.setResultsAndTotal(
				() -> SiteNavigationMenuServiceUtil.getSiteNavigationMenus(
					siteNavigationMenuGroupIds, keywords,
					searchContainer.getStart(), searchContainer.getEnd(),
					searchContainer.getOrderByComparator()),
				SiteNavigationMenuServiceUtil.getSiteNavigationMenusCount(
					siteNavigationMenuGroupIds, keywords));
		}
		else {
			searchContainer.setResultsAndTotal(
				() -> SiteNavigationMenuServiceUtil.getSiteNavigationMenus(
					siteNavigationMenuGroupIds, searchContainer.getStart(),
					searchContainer.getEnd(),
					searchContainer.getOrderByComparator()),
				SiteNavigationMenuServiceUtil.getSiteNavigationMenusCount(
					siteNavigationMenuGroupIds));
		}

		return searchContainer;
	}

	@Override
	public boolean isShowBreadcrumb() {
		return false;
	}

	@Override
	public boolean isShowSearch() {
		return true;
	}

	private OrderByComparator<SiteNavigationMenu> _getOrderByComparator(
		String orderByCol, String orderByType) {

		boolean orderByAsc = false;

		if (orderByType.equals("asc")) {
			orderByAsc = true;
		}

		OrderByComparator<SiteNavigationMenu> orderByComparator = null;

		if (orderByCol.equals("modified-date")) {
			orderByComparator = new SiteNavigationMenuModifiedDateComparator(
				orderByAsc);
		}
		else if (orderByCol.equals("name")) {
			orderByComparator = new SiteNavigationMenuNameComparator(
				orderByAsc);
		}

		return orderByComparator;
	}

	private final HttpServletRequest _httpServletRequest;
	private final PortletURL _portletURL;
	private final ThemeDisplay _themeDisplay;

}