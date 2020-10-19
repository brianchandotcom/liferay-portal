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
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.servlet.taglib.ui.BreadcrumbEntry;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.site.navigation.model.SiteNavigationMenu;
import com.liferay.site.navigation.model.SiteNavigationMenuItem;
import com.liferay.site.navigation.service.SiteNavigationMenuItemLocalServiceUtil;
import com.liferay.site.navigation.service.SiteNavigationMenuItemServiceUtil;
import com.liferay.site.navigation.service.SiteNavigationMenuLocalServiceUtil;
import com.liferay.site.navigation.service.SiteNavigationMenuServiceUtil;
import com.liferay.site.navigation.type.SiteNavigationMenuItemType;
import com.liferay.site.navigation.type.SiteNavigationMenuItemTypeRegistry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
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
		HttpServletRequest httpServletRequest, String itemSelectedEventName,
		SiteNavigationMenuItemTypeRegistry siteNavigationMenuItemTypeRegistry,
		PortletURL portletURL) {

		_httpServletRequest = httpServletRequest;
		_itemSelectedEventName = itemSelectedEventName;
		_siteNavigationMenuItemTypeRegistry =
			siteNavigationMenuItemTypeRegistry;
		_portletURL = portletURL;

		_themeDisplay = (ThemeDisplay)_httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public List<BreadcrumbEntry> getBreadcrumbEntries() throws Exception {
		long siteNavigationMenuId = ParamUtil.getLong(
			_httpServletRequest, "siteNavigationMenuId");

		long parentSiteNavigationMenuItemId = ParamUtil.getLong(
			_httpServletRequest, "parentSiteNavigationMenuItemId");

		List<BreadcrumbEntry> breadcrumbEntries = new ArrayList<>();

		breadcrumbEntries.add(_getAllBreadcrumbEntry());

		if (siteNavigationMenuId == 0) {
			breadcrumbEntries.addAll(
				_getLayoutBreadcrumbEntries(
					siteNavigationMenuId, parentSiteNavigationMenuItemId));
		}
		else {
			breadcrumbEntries.addAll(
				_getSiteNavigationMenuBreadcrumbEntries(
					siteNavigationMenuId, parentSiteNavigationMenuItemId));
		}

		return breadcrumbEntries;
	}

	public String getItemSelectedEventName() {
		return _itemSelectedEventName;
	}

	public String getSelectSiteNavigationMenuLevelURL(long siteNavigationMenuId)
		throws PortletException {

		return _getSelectSiteNavigationMenuLevelURL(siteNavigationMenuId, -1);
	}

	public SearchContainer<Map<String, String>>
			getSiteNavigationMenuItemSearchContainer()
		throws PortalException, PortletException {

		SearchContainer<Map<String, String>> searchContainer =
			new SearchContainer<>(
				_getPortletRequest(), _portletURL, null,
				"there-are-no-navigation-menus");

		long siteNavigationMenuId = ParamUtil.getLong(
			_httpServletRequest, "siteNavigationMenuId");
		long parentSiteNavigationMenuItemId = ParamUtil.getLong(
			_httpServletRequest, "parentSiteNavigationMenuItemId");

		List<Map<String, String>> siteNavigationMenuItems =
			_getSiteNavigationMenuItems(
				siteNavigationMenuId, parentSiteNavigationMenuItemId);

		searchContainer.setResults(siteNavigationMenuItems);
		searchContainer.setTotal(siteNavigationMenuItems.size());

		return searchContainer;
	}

	public SearchContainer<SiteNavigationMenu>
		getSiteNavigationMenuSearchContainer() {

		SearchContainer<SiteNavigationMenu> searchContainer =
			new SearchContainer<>(
				_getPortletRequest(), _portletURL, null, null);

		long[] groupIds = {_themeDisplay.getScopeGroupId()};

		Group scopeGroup = _themeDisplay.getScopeGroup();

		if (!scopeGroup.isCompany()) {
			groupIds = ArrayUtil.append(
				groupIds, _themeDisplay.getCompanyGroupId());
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

	private BreadcrumbEntry _createBreadcrumbEntry(String title, String url) {
		return new BreadcrumbEntry() {
			{
				setBrowsable(url != null);
				setTitle(title);
				setURL(url);
			}
		};
	}

	private BreadcrumbEntry _getAllBreadcrumbEntry() {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			_themeDisplay.getLocale(), getClass());

		String backURL = ParamUtil.getString(
			_httpServletRequest, "backURL",
			PortalUtil.getCurrentURL(_httpServletRequest));

		return _createBreadcrumbEntry(
			LanguageUtil.get(resourceBundle, "all"), backURL);
	}

	private List<BreadcrumbEntry> _getAncestorsBreadcrumbEntries(
			long siteNavigationMenuId, long siteNavigationMenuItemId)
		throws Exception {

		List<BreadcrumbEntry> breadcrumbEntries = new ArrayList<>();

		SiteNavigationMenuItem siteNavigationMenuItem =
			SiteNavigationMenuItemLocalServiceUtil.fetchSiteNavigationMenuItem(
				siteNavigationMenuItemId);

		breadcrumbEntries.add(
			_createBreadcrumbEntry(
				_getSiteNavigationMenuItemName(siteNavigationMenuItem),
				getSelectSiteNavigationMenuLevelURL(siteNavigationMenuId)));

		while (siteNavigationMenuItem.getParentSiteNavigationMenuItemId() !=
					0) {

			siteNavigationMenuItem =
				SiteNavigationMenuItemLocalServiceUtil.
					fetchSiteNavigationMenuItem(
						siteNavigationMenuItem.
							getParentSiteNavigationMenuItemId());

			breadcrumbEntries.add(
				0,
				_createBreadcrumbEntry(
					_getSiteNavigationMenuItemName(siteNavigationMenuItem),
					_getSelectSiteNavigationMenuLevelURL(
						siteNavigationMenuId,
						siteNavigationMenuItem.getSiteNavigationMenuItemId())));
		}

		return breadcrumbEntries;
	}

	private List<BreadcrumbEntry> _getLayoutBreadcrumbEntries(
			long siteNavigationMenuId, long parentSiteNavigationMenuItemId)
		throws Exception {

		List<BreadcrumbEntry> breadcrumbEntries = new ArrayList<>();

		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			_themeDisplay.getLocale(), getClass());

		breadcrumbEntries.add(
			_createBreadcrumbEntry(
				LanguageUtil.get(resourceBundle, "public-pages-hierarchy"),
				getSelectSiteNavigationMenuLevelURL(siteNavigationMenuId)));

		if (parentSiteNavigationMenuItemId != 0) {
			Layout layout = LayoutLocalServiceUtil.fetchLayout(
				_themeDisplay.getScopeGroupId(), false,
				parentSiteNavigationMenuItemId);

			List<Layout> ancestors = layout.getAncestors();

			Collections.reverse(ancestors);

			for (Layout ancestor : ancestors) {
				breadcrumbEntries.add(
					_createBreadcrumbEntry(
						ancestor.getName(_themeDisplay.getLocale()),
						_getSelectSiteNavigationMenuLevelURL(
							siteNavigationMenuId, ancestor.getLayoutId())));
			}

			breadcrumbEntries.add(
				_createBreadcrumbEntry(
					layout.getName(_themeDisplay.getLocale()),
					_getSelectSiteNavigationMenuLevelURL(
						siteNavigationMenuId, layout.getLayoutId())));
		}

		return breadcrumbEntries;
	}

	private PortletRequest _getPortletRequest() {
		return (PortletRequest)_httpServletRequest.getAttribute(
			JavaConstants.JAVAX_PORTLET_REQUEST);
	}

	private SiteNavigationMenu _getPublicPagesHierarchySiteNavigationMenu() {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			_themeDisplay.getLocale(), getClass());

		SiteNavigationMenu siteNavigationMenu =
			SiteNavigationMenuLocalServiceUtil.createSiteNavigationMenu(0);

		siteNavigationMenu.setGroupId(_themeDisplay.getScopeGroupId());
		siteNavigationMenu.setName(
			LanguageUtil.get(resourceBundle, "public-pages-hierarchy"));

		return siteNavigationMenu;
	}

	private String _getSelectSiteNavigationMenuLevelURL(
			long siteNavigationMenuId, long parentSiteNavigationMenuItemId)
		throws PortletException {

		PortletResponse portletResponse =
			(PortletResponse)_httpServletRequest.getAttribute(
				JavaConstants.JAVAX_PORTLET_RESPONSE);

		PortletURL portletURL = PortletURLUtil.clone(
			_portletURL, PortalUtil.getLiferayPortletResponse(portletResponse));

		portletURL.setParameter(
			"backURL",
			ParamUtil.getString(
				_httpServletRequest, "backURL",
				PortalUtil.getCurrentURL(_httpServletRequest)));

		portletURL.setParameter(
			"siteNavigationMenuId", String.valueOf(siteNavigationMenuId));

		if (parentSiteNavigationMenuItemId >= 0) {
			portletURL.setParameter(
				"parentSiteNavigationMenuItemId",
				String.valueOf(parentSiteNavigationMenuItemId));
		}

		return portletURL.toString();
	}

	private List<BreadcrumbEntry> _getSiteNavigationMenuBreadcrumbEntries(
			long siteNavigationMenuId, long parentSiteNavigationMenuItemId)
		throws Exception {

		List<BreadcrumbEntry> breadcrumbEntries = new ArrayList<>();

		SiteNavigationMenu siteNavigationMenu =
			SiteNavigationMenuServiceUtil.fetchSiteNavigationMenu(
				siteNavigationMenuId);

		breadcrumbEntries.add(
			_createBreadcrumbEntry(
				siteNavigationMenu.getName(),
				getSelectSiteNavigationMenuLevelURL(siteNavigationMenuId)));

		if (parentSiteNavigationMenuItemId != 0) {
			breadcrumbEntries.addAll(
				_getAncestorsBreadcrumbEntries(
					siteNavigationMenuId, parentSiteNavigationMenuItemId));
		}

		return breadcrumbEntries;
	}

	private String _getSiteNavigationMenuItemName(
		SiteNavigationMenuItem siteNavigationMenuItem) {

		SiteNavigationMenuItemType siteNavigationMenuItemType =
			_siteNavigationMenuItemTypeRegistry.getSiteNavigationMenuItemType(
				siteNavigationMenuItem);

		return siteNavigationMenuItemType.getTitle(
			siteNavigationMenuItem, _themeDisplay.getLocale());
	}

	private List<Map<String, String>> _getSiteNavigationMenuItems(
			long siteNavigationMenuId, long parentSiteNavigationMenuItemId)
		throws PortalException, PortletException {

		List<Map<String, String>> siteNavigationItems = new ArrayList<>();

		if (siteNavigationMenuId > 0) {
			List<SiteNavigationMenuItem> siteNavigationMenuItems =
				SiteNavigationMenuItemServiceUtil.getSiteNavigationMenuItems(
					siteNavigationMenuId, parentSiteNavigationMenuItemId);

			for (SiteNavigationMenuItem siteNavigationMenuItem :
					siteNavigationMenuItems) {

				siteNavigationItems.add(
					HashMapBuilder.put(
						"name",
						_getSiteNavigationMenuItemName(siteNavigationMenuItem)
					).put(
						"selectSiteNavigationMenuLevelURL",
						_getSelectSiteNavigationMenuLevelURL(
							siteNavigationMenuId,
							siteNavigationMenuItem.
								getSiteNavigationMenuItemId())
					).build());
			}
		}
		else {
			List<Layout> layouts = LayoutLocalServiceUtil.getLayouts(
				_themeDisplay.getScopeGroupId(), false,
				parentSiteNavigationMenuItemId);

			for (Layout layout : layouts) {
				siteNavigationItems.add(
					HashMapBuilder.put(
						"name", layout.getName(_themeDisplay.getLocale())
					).put(
						"selectSiteNavigationMenuLevelURL",
						_getSelectSiteNavigationMenuLevelURL(
							siteNavigationMenuId, layout.getLayoutId())
					).build());
			}
		}

		return siteNavigationItems;
	}

	private final HttpServletRequest _httpServletRequest;
	private final String _itemSelectedEventName;
	private final PortletURL _portletURL;
	private final SiteNavigationMenuItemTypeRegistry
		_siteNavigationMenuItemTypeRegistry;
	private final ThemeDisplay _themeDisplay;

}