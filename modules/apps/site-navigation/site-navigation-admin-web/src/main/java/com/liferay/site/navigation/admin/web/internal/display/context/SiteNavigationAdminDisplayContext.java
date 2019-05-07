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

import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemList;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.site.navigation.admin.constants.SiteNavigationAdminPortletKeys;
import com.liferay.site.navigation.admin.web.internal.security.permission.resource.SiteNavigationMenuPermission;
import com.liferay.site.navigation.admin.web.internal.util.SiteNavigationMenuPortletUtil;
import com.liferay.site.navigation.model.SiteNavigationMenu;
import com.liferay.site.navigation.service.SiteNavigationMenuLocalService;
import com.liferay.site.navigation.service.SiteNavigationMenuService;
import com.liferay.site.navigation.type.SiteNavigationMenuItemType;
import com.liferay.site.navigation.type.SiteNavigationMenuItemTypeRegistry;
import com.liferay.staging.StagingGroupHelper;
import com.liferay.staging.StagingGroupHelperUtil;

import java.util.Collections;
import java.util.List;

import javax.portlet.PortletURL;
import javax.portlet.WindowStateException;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Pavel Savinov
 */
public class SiteNavigationAdminDisplayContext {

	public SiteNavigationAdminDisplayContext(
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse,
		HttpServletRequest httpServletRequest,
		SiteNavigationMenuItemTypeRegistry siteNavigationMenuItemTypeRegistry,
		SiteNavigationMenuLocalService siteNavigationMenuLocalService,
		SiteNavigationMenuService siteNavigationMenuService) {

		_liferayPortletRequest = liferayPortletRequest;
		_liferayPortletResponse = liferayPortletResponse;
		_httpServletRequest = httpServletRequest;
		_siteNavigationMenuItemTypeRegistry =
			siteNavigationMenuItemTypeRegistry;
		_siteNavigationMenuLocalService = siteNavigationMenuLocalService;
		_siteNavigationMenuService = siteNavigationMenuService;
	}

	public List<DropdownItem> getAddSiteNavigationMenuItemDropdownItems() {
		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		return new DropdownItemList() {
			{
				for (SiteNavigationMenuItemType siteNavigationMenuItemType :
						_siteNavigationMenuItemTypeRegistry.
							getSiteNavigationMenuItemTypes()) {

					add(
						dropdownItem -> {
							dropdownItem.setData(
								Collections.singletonMap("type", "add-button"));
							dropdownItem.setHref(
								_getAddURL(siteNavigationMenuItemType));
							dropdownItem.setLabel(
								siteNavigationMenuItemType.getLabel(
									themeDisplay.getLocale()));
						});
				}
			}
		};
	}

	public String getDisplayStyle() {
		if (_displayStyle != null) {
			return _displayStyle;
		}

		_displayStyle = ParamUtil.getString(
			_httpServletRequest, "displayStyle", "list");

		return _displayStyle;
	}

	public String getKeywords() {
		if (Validator.isNotNull(_keywords)) {
			return _keywords;
		}

		_keywords = ParamUtil.getString(_httpServletRequest, "keywords");

		return _keywords;
	}

	public String getOrderByCol() {
		if (_orderByCol != null) {
			return _orderByCol;
		}

		_orderByCol = ParamUtil.getString(
			_httpServletRequest, "orderByCol", "create-date");

		return _orderByCol;
	}

	public String getOrderByType() {
		if (_orderByType != null) {
			return _orderByType;
		}

		_orderByType = ParamUtil.getString(
			_httpServletRequest, "orderByType", "asc");

		return _orderByType;
	}

	public PortletURL getPortletURL() {
		PortletURL portletURL = _liferayPortletResponse.createRenderURL();

		String displayStyle = ParamUtil.getString(
			_httpServletRequest, "displayStyle");

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
		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		return _siteNavigationMenuLocalService.fetchPrimarySiteNavigationMenu(
			themeDisplay.getScopeGroupId());
	}

	public SearchContainer getSearchContainer() {
		if (_searchContainer != null) {
			return _searchContainer;
		}

		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		SearchContainer searchContainer = new SearchContainer(
			_liferayPortletRequest, getPortletURL(), null,
			"there-are-no-navigation-menus");

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
			menus = _siteNavigationMenuService.getSiteNavigationMenus(
				themeDisplay.getScopeGroupId(), getKeywords(),
				searchContainer.getStart(), searchContainer.getEnd(),
				orderByComparator);

			menusCount = _siteNavigationMenuService.getSiteNavigationMenusCount(
				themeDisplay.getScopeGroupId(), getKeywords());
		}
		else {
			menus = _siteNavigationMenuService.getSiteNavigationMenus(
				themeDisplay.getScopeGroupId(), searchContainer.getStart(),
				searchContainer.getEnd(), orderByComparator);

			menusCount = _siteNavigationMenuService.getSiteNavigationMenusCount(
				themeDisplay.getScopeGroupId());
		}

		searchContainer.setResults(menus);
		searchContainer.setTotal(menusCount);

		_searchContainer = searchContainer;

		return _searchContainer;
	}

	public SiteNavigationMenu getSiteNavigationMenu() throws PortalException {
		if (getSiteNavigationMenuId() == 0) {
			return null;
		}

		return _siteNavigationMenuService.fetchSiteNavigationMenu(
			getSiteNavigationMenuId());
	}

	public long getSiteNavigationMenuId() {
		if (_siteNavigationMenuId != null) {
			return _siteNavigationMenuId;
		}

		_siteNavigationMenuId = ParamUtil.getLong(
			_httpServletRequest, "siteNavigationMenuId");

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

	public boolean hasEditPermission() {
		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		Group group = themeDisplay.getScopeGroup();

		StagingGroupHelper stagingGroupHelper =
			StagingGroupHelperUtil.getStagingGroupHelper();

		if ((stagingGroupHelper.isLocalLiveGroup(group) ||
			 stagingGroupHelper.isRemoteLiveGroup(group)) &&
			group.isStagedPortlet(
				SiteNavigationAdminPortletKeys.SITE_NAVIGATION_ADMIN)) {

			return false;
		}

		return true;
	}

	public boolean hasUpdatePermission() throws PortalException {
		if (_updatePermission != null) {
			return _updatePermission;
		}

		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		_updatePermission = SiteNavigationMenuPermission.contains(
			themeDisplay.getPermissionChecker(), getSiteNavigationMenuId(),
			ActionKeys.UPDATE);

		return _updatePermission;
	}

	private String _getAddURL(
		SiteNavigationMenuItemType siteNavigationMenuItemType) {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		PortletURL addURL = _liferayPortletResponse.createRenderURL();

		addURL.setParameter("mvcPath", "/add_site_navigation_menu_item.jsp");

		PortletURL addSiteNavigationMenuItemRedirectURL =
			_liferayPortletResponse.createRenderURL();

		addSiteNavigationMenuItemRedirectURL.setParameter(
			"mvcPath", "/add_site_navigation_menu_item_redirect.jsp");
		addSiteNavigationMenuItemRedirectURL.setParameter(
			"portletResource", portletDisplay.getId());

		addURL.setParameter(
			"redirect", addSiteNavigationMenuItemRedirectURL.toString());

		addURL.setParameter(
			"siteNavigationMenuId", String.valueOf(getSiteNavigationMenuId()));
		addURL.setParameter("type", siteNavigationMenuItemType.getType());

		try {
			addURL.setWindowState(LiferayWindowState.POP_UP);
		}
		catch (WindowStateException wse) {
			return StringPool.BLANK;
		}

		return addURL.toString();
	}

	private String _displayStyle;
	private final HttpServletRequest _httpServletRequest;
	private String _keywords;
	private final LiferayPortletRequest _liferayPortletRequest;
	private final LiferayPortletResponse _liferayPortletResponse;
	private String _orderByCol;
	private String _orderByType;
	private SearchContainer _searchContainer;
	private Long _siteNavigationMenuId;
	private final SiteNavigationMenuItemTypeRegistry
		_siteNavigationMenuItemTypeRegistry;
	private final SiteNavigationMenuLocalService
		_siteNavigationMenuLocalService;
	private String _siteNavigationMenuName;
	private final SiteNavigationMenuService _siteNavigationMenuService;
	private Boolean _updatePermission;

}