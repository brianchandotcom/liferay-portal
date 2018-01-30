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

import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.site.navigation.model.SiteNavigationMenu;
import com.liferay.site.navigation.service.SiteNavigationMenuServiceUtil;
import com.liferay.site.navigation.util.comparator.SiteNavigationMenuCreateDateComparator;
import com.liferay.site.navigation.util.comparator.SiteNavigationMenuNameComparator;

import java.util.List;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Pavel Savinov
 */
public class SiteNavigationMenuItemSelectorViewDisplayContext {

	public SiteNavigationMenuItemSelectorViewDisplayContext(
		HttpServletRequest request, PortletURL portletURL,
		String itemSelectedEventName) {

		_request = request;
		_portletURL = portletURL;
		_itemSelectedEventName = itemSelectedEventName;
	}

	public String getDisplayStyle() {
		if (_displayStyle != null) {
			return _displayStyle;
		}

		String[] displayViews = getDisplayViews();

		_displayStyle = ParamUtil.getString(_request, "displayStyle");

		if (!ArrayUtil.contains(displayViews, _displayStyle)) {
			_displayStyle = displayViews[0];
		}

		return _displayStyle;
	}

	public String[] getDisplayViews() {
		return new String[] {"icon", "descriptive", "list"};
	}

	public String getItemSelectedEventName() {
		return _itemSelectedEventName;
	}

	public String getKeywords() {
		if (Validator.isNotNull(_keywords)) {
			return _keywords;
		}

		_keywords = ParamUtil.getString(_request, "keywords");

		return _keywords;
	}

	public String getOrderByCol() throws Exception {
		if (_orderByCol != null) {
			return _orderByCol;
		}

		_orderByCol = ParamUtil.getString(_request, "orderByCol");

		return _orderByCol;
	}

	public String getOrderByType() throws Exception {
		if (_orderByType != null) {
			return _orderByType;
		}

		_orderByType = ParamUtil.getString(_request, "orderByType");

		return _orderByType;
	}

	public String[] getOrderColumns() {
		return new String[] {"create-date", "name"};
	}

	public PortletURL getPortletURL() throws Exception {
		String displayStyle = ParamUtil.getString(_request, "displayStyle");

		if (Validator.isNotNull(displayStyle)) {
			_portletURL.setParameter("displayStyle", getDisplayStyle());
		}

		String orderByCol = getOrderByCol();

		if (Validator.isNotNull(orderByCol)) {
			_portletURL.setParameter("orderByCol", orderByCol);
		}

		String orderByType = getOrderByType();

		if (Validator.isNotNull(orderByType)) {
			_portletURL.setParameter("orderByType", orderByType);
		}

		return _portletURL;
	}

	public SearchContainer getSearchContainer() throws Exception {
		if (_searchContainer != null) {
			return _searchContainer;
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		SearchContainer searchContainer = new SearchContainer(
			_getPortletRequest(), getPortletURL(), null,
			"there-are-no-navigation-menus");

		if (Validator.isNotNull(getKeywords())) {
			searchContainer.setSearch(true);
		}

		OrderByComparator<SiteNavigationMenu> orderByComparator =
			_getOrderByComparator(getOrderByCol(), getOrderByType());

		searchContainer.setOrderByCol(getOrderByCol());
		searchContainer.setOrderByComparator(orderByComparator);
		searchContainer.setOrderByType(getOrderByType());

		EmptyOnClickRowChecker emptyOnClickRowChecker =
			new EmptyOnClickRowChecker(_getPortletResponse());

		searchContainer.setRowChecker(emptyOnClickRowChecker);

		List<SiteNavigationMenu> menus = null;
		int menusCount = 0;

		if (Validator.isNotNull(getKeywords())) {
			menus = SiteNavigationMenuServiceUtil.getSiteNavigationMenus(
				themeDisplay.getScopeGroupId(), getKeywords(),
				searchContainer.getStart(), searchContainer.getEnd(),
				orderByComparator);

			menusCount =
				SiteNavigationMenuServiceUtil.getSiteNavigationMenusCount(
					themeDisplay.getScopeGroupId(), getKeywords());
		}
		else {
			menus = SiteNavigationMenuServiceUtil.getSiteNavigationMenus(
				themeDisplay.getScopeGroupId(), searchContainer.getStart(),
				searchContainer.getEnd(), orderByComparator);

			menusCount =
				SiteNavigationMenuServiceUtil.getSiteNavigationMenusCount(
					themeDisplay.getScopeGroupId());
		}

		searchContainer.setResults(menus);
		searchContainer.setTotal(menusCount);

		_searchContainer = searchContainer;

		return _searchContainer;
	}

	private OrderByComparator<SiteNavigationMenu> _getOrderByComparator(
		String orderByCol, String orderByType) {

		boolean orderByAsc = false;

		if (orderByType.equals("asc")) {
			orderByAsc = true;
		}

		OrderByComparator<SiteNavigationMenu> orderByComparator = null;

		if (orderByCol.equals("create-date")) {
			orderByComparator = new SiteNavigationMenuCreateDateComparator(
				orderByAsc);
		}
		else if (orderByCol.equals("name")) {
			orderByComparator = new SiteNavigationMenuNameComparator(
				orderByAsc);
		}

		return orderByComparator;
	}

	private PortletRequest _getPortletRequest() {
		return (PortletRequest)_request.getAttribute(
			JavaConstants.JAVAX_PORTLET_REQUEST);
	}

	private PortletResponse _getPortletResponse() {
		return (PortletResponse)_request.getAttribute(
			JavaConstants.JAVAX_PORTLET_RESPONSE);
	}

	private String _displayStyle;
	private final String _itemSelectedEventName;
	private String _keywords;
	private String _orderByCol;
	private String _orderByType;
	private final PortletURL _portletURL;
	private final HttpServletRequest _request;
	private SearchContainer _searchContainer;

}