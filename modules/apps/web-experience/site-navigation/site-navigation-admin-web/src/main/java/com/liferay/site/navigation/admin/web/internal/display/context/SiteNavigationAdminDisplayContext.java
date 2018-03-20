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

package com.liferay.site.navigation.admin.web.internal.display.context;

import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItem;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.PortalPreferences;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PrefsParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.site.navigation.admin.constants.SiteNavigationAdminPortletKeys;
import com.liferay.site.navigation.admin.web.internal.constants.SiteNavigationAdminWebKeys;
import com.liferay.site.navigation.admin.web.internal.security.permission.resource.SiteNavigationPermission;
import com.liferay.site.navigation.admin.web.internal.util.SiteNavigationMenuPortletUtil;
import com.liferay.site.navigation.constants.SiteNavigationActionKeys;
import com.liferay.site.navigation.model.SiteNavigationMenu;
import com.liferay.site.navigation.service.SiteNavigationMenuLocalServiceUtil;
import com.liferay.site.navigation.service.SiteNavigationMenuServiceUtil;
import com.liferay.site.navigation.type.SiteNavigationMenuItemType;
import com.liferay.site.navigation.type.SiteNavigationMenuItemTypeRegistry;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.PortletPreferences;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Pavel Savinov
 */
public class SiteNavigationAdminDisplayContext {

	public SiteNavigationAdminDisplayContext(
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse,
			HttpServletRequest request)
		throws PortalException {

		_liferayPortletRequest = liferayPortletRequest;
		_liferayPortletResponse = liferayPortletResponse;
		_request = request;

		_portletPreferences =
			PortletPreferencesFactoryUtil.getPortletPreferences(
				request, SiteNavigationAdminPortletKeys.SITE_NAVIGATION_ADMIN);
		_siteNavigationMenuItemTypeRegistry =
			(SiteNavigationMenuItemTypeRegistry)_request.getAttribute(
				SiteNavigationAdminWebKeys.
					SITE_NAVIGATION_MENU_ITEM_TYPE_REGISTRY);
	}

	public JSONArray getAvailableItemsJSONArray() throws Exception {
		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (SiteNavigationMenuItemType siteNavigationMenuItemType :
				_siteNavigationMenuItemTypeRegistry.
					getSiteNavigationMenuItemTypes()) {

			JSONObject itemTypeJSONObject = JSONFactoryUtil.createJSONObject();

			itemTypeJSONObject.put(
				"icon", siteNavigationMenuItemType.getIcon());
			itemTypeJSONObject.put(
				"label",
				siteNavigationMenuItemType.getLabel(themeDisplay.getLocale()));
			itemTypeJSONObject.put(
				"type", siteNavigationMenuItemType.getType());

			jsonArray.put(itemTypeJSONObject);
		}

		return jsonArray;
	}

	public String getDisplayStyle() {
		if (_displayStyle != null) {
			return _displayStyle;
		}

		String[] displayViews = getDisplayViews();

		PortalPreferences portalPreferences =
			PortletPreferencesFactoryUtil.getPortalPreferences(_request);

		_displayStyle = ParamUtil.getString(_request, "displayStyle");

		if (Validator.isNull(_displayStyle)) {
			_displayStyle = portalPreferences.getValue(
				SiteNavigationAdminPortletKeys.SITE_NAVIGATION_ADMIN,
				"display-style", "list");
		}
		else if (ArrayUtil.contains(displayViews, _displayStyle)) {
			portalPreferences.setValue(
				SiteNavigationAdminPortletKeys.SITE_NAVIGATION_ADMIN,
				"display-style", _displayStyle);
		}

		if (!ArrayUtil.contains(displayViews, _displayStyle)) {
			_displayStyle = displayViews[0];
		}

		return _displayStyle;
	}

	public String[] getDisplayViews() {
		if (_displayViews == null) {
			_displayViews = StringUtil.split(
				PrefsParamUtil.getString(
					_portletPreferences, _request, "displayViews",
					"list,icon,descriptive"));
		}

		return _displayViews;
	}

	public String getKeywords() {
		if (Validator.isNotNull(_keywords)) {
			return _keywords;
		}

		_keywords = ParamUtil.getString(_request, "keywords");

		return _keywords;
	}

	public List<NavigationItem> getNavigationItems() {
		List<NavigationItem> navigationItems = new ArrayList<>();

		NavigationItem entriesNavigationItem = new NavigationItem();

		entriesNavigationItem.setActive(true);

		PortletURL mainURL = _liferayPortletResponse.createRenderURL();

		entriesNavigationItem.setHref(mainURL.toString());

		entriesNavigationItem.setLabel(LanguageUtil.get(_request, "menus"));

		navigationItems.add(entriesNavigationItem);

		return navigationItems;
	}

	public String getOrderByCol() throws Exception {
		if (_orderByCol != null) {
			return _orderByCol;
		}

		_orderByCol = ParamUtil.getString(_request, "orderByCol");

		if (Validator.isNull(_orderByCol)) {
			_orderByCol = _portletPreferences.getValue(
				"order-by-col", "create-date");
		}
		else {
			boolean saveOrderBy = ParamUtil.getBoolean(_request, "saveOrderBy");

			if (saveOrderBy) {
				_portletPreferences.setValue("order-by-col", _orderByCol);
			}
		}

		return _orderByCol;
	}

	public String getOrderByType() throws Exception {
		if (_orderByType != null) {
			return _orderByType;
		}

		_orderByType = ParamUtil.getString(_request, "orderByType");

		if (Validator.isNull(_orderByType)) {
			_orderByType = _portletPreferences.getValue("order-by-type", "asc");
		}
		else {
			boolean saveOrderBy = ParamUtil.getBoolean(_request, "saveOrderBy");

			if (saveOrderBy) {
				_portletPreferences.setValue("order-by-type", _orderByType);
			}
		}

		return _orderByType;
	}

	public String[] getOrderColumns() {
		String[] orderColumns = {"create-date", "name"};

		return orderColumns;
	}

	public PortletURL getPortletURL() throws Exception {
		PortletURL portletURL = _liferayPortletResponse.createRenderURL();

		String displayStyle = ParamUtil.getString(_request, "displayStyle");

		if (Validator.isNotNull(displayStyle)) {
			portletURL.setParameter("displayStyle", getDisplayStyle());
		}

		String orderByCol = getOrderByCol();

		if (Validator.isNotNull(orderByCol)) {
			portletURL.setParameter("orderByCol", orderByCol);
		}

		String orderByType = getOrderByType();

		if (Validator.isNotNull(orderByType)) {
			portletURL.setParameter("orderByType", orderByType);
		}

		return portletURL;
	}

	public SiteNavigationMenu getPrimarySiteNavigationMenu() {
		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		return SiteNavigationMenuLocalServiceUtil.
			fetchPrimarySiteNavigationMenu(themeDisplay.getScopeGroupId());
	}

	public SearchContainer getSearchContainer() throws Exception {
		if (_searchContainer != null) {
			return _searchContainer;
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		SearchContainer searchContainer = new SearchContainer(
			_liferayPortletRequest, getPortletURL(), null,
			"there-are-no-navigation-menus");

		if (Validator.isNotNull(getKeywords())) {
			searchContainer.setSearch(true);
		}

		OrderByComparator<SiteNavigationMenu> orderByComparator =
			SiteNavigationMenuPortletUtil.getOrderByComparator(
				getOrderByCol(), getOrderByType());

		searchContainer.setOrderByCol(getOrderByCol());
		searchContainer.setOrderByComparator(orderByComparator);
		searchContainer.setOrderByType(getOrderByType());

		EmptyOnClickRowChecker emptyOnClickRowChecker =
			new EmptyOnClickRowChecker(_liferayPortletResponse);

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

	public JSONObject getSelectedItemTypeJSONObject() throws Exception {
		if (Validator.isNotNull(_selectedItemTypeJSONObject)) {
			return _selectedItemTypeJSONObject;
		}

		String selectedItemType = ParamUtil.getString(
			_request, "selectedItemType", _getFirstSiteNavigationMenuItem());

		SiteNavigationMenuItemType siteNavigationMenuItemType =
			_siteNavigationMenuItemTypeRegistry.getSiteNavigationMenuItemType(
				selectedItemType);

		if (siteNavigationMenuItemType == null) {
			return null;
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		JSONObject selectedItemTypeJSONObject =
			JSONFactoryUtil.createJSONObject();

		selectedItemTypeJSONObject.put(
			"icon", siteNavigationMenuItemType.getIcon());
		selectedItemTypeJSONObject.put(
			"label",
			siteNavigationMenuItemType.getLabel(themeDisplay.getLocale()));
		selectedItemTypeJSONObject.put(
			"type", siteNavigationMenuItemType.getType());

		_selectedItemTypeJSONObject = selectedItemTypeJSONObject;

		return _selectedItemTypeJSONObject;
	}

	public SiteNavigationMenu getSiteNavigationMenu() throws PortalException {
		if (getSiteNavigationMenuId() == 0) {
			return null;
		}

		return SiteNavigationMenuServiceUtil.fetchSiteNavigationMenu(
			getSiteNavigationMenuId());
	}

	public long getSiteNavigationMenuId() {
		if (_siteNavigationMenuId != null) {
			return _siteNavigationMenuId;
		}

		_siteNavigationMenuId = ParamUtil.getLong(
			_request, "siteNavigationMenuId");

		return _siteNavigationMenuId;
	}

	public SiteNavigationMenuItemTypeRegistry
		getSiteNavigationMenuItemTypeRegistry() {

		return _siteNavigationMenuItemTypeRegistry;
	}

	public String getSiteNavigationMenuName() throws PortalException {
		if (_siteNavigationMenuName != null) {
			return _siteNavigationMenuName;
		}

		SiteNavigationMenu siteNavigationMenu = getSiteNavigationMenu();

		_siteNavigationMenuName = siteNavigationMenu.getName();

		return _siteNavigationMenuName;
	}

	public boolean isShowAddButton() {
		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		if (SiteNavigationPermission.contains(
				themeDisplay.getPermissionChecker(),
				themeDisplay.getSiteGroupId(),
				SiteNavigationActionKeys.ADD_SITE_NAVIGATION_MENU)) {

			return true;
		}

		return false;
	}

	public boolean isShowPrimarySiteNavigationMenuMessage() {
		SiteNavigationMenu primarySiteNavigationMenu =
			getPrimarySiteNavigationMenu();

		if ((primarySiteNavigationMenu == null) ||
			(primarySiteNavigationMenu.getSiteNavigationMenuId() ==
				getSiteNavigationMenuId())) {

			return false;
		}

		return true;
	}

	private String _getFirstSiteNavigationMenuItem() {
		String[] types = _siteNavigationMenuItemTypeRegistry.getTypes();

		String selectedItemType = StringPool.BLANK;

		if (types.length > 0) {
			return types[0];
		}

		return selectedItemType;
	}

	private String _displayStyle;
	private String[] _displayViews;
	private String _keywords;
	private final LiferayPortletRequest _liferayPortletRequest;
	private final LiferayPortletResponse _liferayPortletResponse;
	private String _orderByCol;
	private String _orderByType;
	private final PortletPreferences _portletPreferences;
	private final HttpServletRequest _request;
	private SearchContainer _searchContainer;
	private JSONObject _selectedItemTypeJSONObject;
	private Long _siteNavigationMenuId;
	private final SiteNavigationMenuItemTypeRegistry
		_siteNavigationMenuItemTypeRegistry;
	private String _siteNavigationMenuName;

}