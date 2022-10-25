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

package com.liferay.layout.admin.web.internal.display.context;

import com.liferay.asset.list.model.AssetListEntry;
import com.liferay.asset.list.service.AssetListEntryServiceUtil;
import com.liferay.asset.list.util.AssetListPortletUtil;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItem;
import com.liferay.info.collection.provider.InfoCollectionProvider;
import com.liferay.info.item.InfoItemServiceTracker;
import com.liferay.info.item.provider.InfoItemFormProvider;
import com.liferay.layout.admin.constants.LayoutAdminPortletKeys;
import com.liferay.layout.admin.web.internal.info.search.InfoSearchClassMapperTrackerUtil;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.portlet.SearchOrderByUtil;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portlet.layoutsadmin.display.context.GroupDisplayContextHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.portlet.PortletException;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Jürgen Kappler
 */
public class SelectLayoutCollectionDisplayContext {

	public SelectLayoutCollectionDisplayContext(
		InfoItemServiceTracker infoItemServiceTracker,
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse) {

		_infoItemServiceTracker = infoItemServiceTracker;
		_liferayPortletRequest = liferayPortletRequest;
		_liferayPortletResponse = liferayPortletResponse;

		_httpServletRequest = PortalUtil.getHttpServletRequest(
			_liferayPortletRequest);
		_themeDisplay = (ThemeDisplay)_liferayPortletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		_groupDisplayContextHelper = new GroupDisplayContextHelper(
			_httpServletRequest);
	}

	public SearchContainer<InfoCollectionProvider<?>>
		getCollectionProvidersSearchContainer() {

		SearchContainer<InfoCollectionProvider<?>> searchContainer =
			new SearchContainer<>(
				_liferayPortletRequest, getPortletURL(), null,
				LanguageUtil.get(
					_httpServletRequest, "there-are-no-collection-providers"));

		searchContainer.setResultsAndTotal(_getInfoCollectionProviders());

		return searchContainer;
	}

	public SearchContainer<AssetListEntry> getCollectionsSearchContainer() {
		SearchContainer<AssetListEntry> searchContainer = new SearchContainer<>(
			_liferayPortletRequest, getPortletURL(), null,
			LanguageUtil.get(_httpServletRequest, "there-are-no-collections"));

		searchContainer.setOrderByCol(_getOrderByCol());
		searchContainer.setOrderByComparator(
			AssetListPortletUtil.getAssetListEntryOrderByComparator(
				_getOrderByCol(), _getOrderByType()));
		searchContainer.setOrderByType(_getOrderByType());

		if (_isSearch()) {
			searchContainer.setResultsAndTotal(
				() -> AssetListEntryServiceUtil.getAssetListEntries(
					_themeDisplay.getScopeGroupId(), _getKeywords(),
					searchContainer.getStart(), searchContainer.getEnd(),
					searchContainer.getOrderByComparator()),
				AssetListEntryServiceUtil.getAssetListEntriesCount(
					_themeDisplay.getScopeGroupId(), _getKeywords()));
		}
		else {
			long[] groupIds = {_themeDisplay.getScopeGroupId()};

			List<String> types = _getInfoItemFormProviderSearchClassNames();

			searchContainer.setResultsAndTotal(
				() -> AssetListEntryServiceUtil.getAssetListEntries(
					groupIds, types.toArray(new String[0]),
					searchContainer.getStart(), searchContainer.getEnd(),
					searchContainer.getOrderByComparator()),
				AssetListEntryServiceUtil.getAssetListEntriesCount(
					groupIds, types.toArray(new String[0])));
		}

		return searchContainer;
	}

	public List<NavigationItem> getNavigationItems() {
		return ListUtil.fromArray(
			_getNavigationItem("collections", "collections"),
			_getNavigationItem("collection-providers", "collection-providers"));
	}

	public PortletURL getPortletURL() {
		PortletURL currentURLObj = PortletURLUtil.getCurrent(
			_liferayPortletRequest, _liferayPortletResponse);

		try {
			return PortletURLUtil.clone(currentURLObj, _liferayPortletResponse);
		}
		catch (PortletException portletException) {
			if (_log.isDebugEnabled()) {
				_log.debug(portletException);
			}

			return PortletURLBuilder.createRenderURL(
				_liferayPortletResponse
			).setParameters(
				currentURLObj.getParameterMap()
			).buildPortletURL();
		}
	}

	public String getRedirect() {
		if (_redirect != null) {
			return _redirect;
		}

		_redirect = ParamUtil.getString(_httpServletRequest, "redirect");

		return _redirect;
	}

	public String getSelectedTab() {
		if (_selectedTab != null) {
			return _selectedTab;
		}

		_selectedTab = ParamUtil.getString(
			_httpServletRequest, "selectedTab", "collections");

		return _selectedTab;
	}

	public long getSelGroupId() {
		Group selGroup = _groupDisplayContextHelper.getSelGroup();

		if (selGroup != null) {
			return selGroup.getGroupId();
		}

		return 0;
	}

	public boolean isCollectionProviders() {
		if (!Objects.equals(getSelectedTab(), "collection-providers")) {
			return false;
		}

		return true;
	}

	public boolean isCollections() {
		if (!Objects.equals(getSelectedTab(), "collections")) {
			return false;
		}

		return true;
	}

	private List<InfoCollectionProvider<?>> _getInfoCollectionProviders() {
		List<InfoCollectionProvider<?>> infoCollectionProviders =
			(List<InfoCollectionProvider<?>>)
				(List<?>)_infoItemServiceTracker.getAllInfoItemServices(
					InfoCollectionProvider.class);

		return ListUtil.filter(
			infoCollectionProviders,
			infoCollectionProvider -> {
				try {
					if (Validator.isNotNull(
							infoCollectionProvider.getLabel(
								_themeDisplay.getLocale())) &&
						infoCollectionProvider.isAvailable()) {

						return true;
					}

					return false;
				}
				catch (Exception exception) {
					if (_log.isWarnEnabled()) {
						_log.warn(
							"Unable to get info list provider label",
							exception);
					}

					return false;
				}
			});
	}

	private List<String> _getInfoItemFormProviderSearchClassNames() {
		List<String> infoItemClassNames = new ArrayList<>();

		for (String className :
				_infoItemServiceTracker.getInfoItemClassNames(
					InfoItemFormProvider.class)) {

			infoItemClassNames.add(
				InfoSearchClassMapperTrackerUtil.getSearchClassName(className));
		}

		return infoItemClassNames;
	}

	private String _getKeywords() {
		if (_keywords != null) {
			return _keywords;
		}

		_keywords = ParamUtil.getString(_httpServletRequest, "keywords");

		return _keywords;
	}

	private NavigationItem _getNavigationItem(String label, String tabName) {
		NavigationItem navigationItem = new NavigationItem();

		if (Objects.equals(
				tabName,
				ParamUtil.getString(
					_httpServletRequest, "selectedTab", "collections"))) {

			navigationItem.setActive(true);
		}

		navigationItem.setHref(
			PortletURLBuilder.create(
				getPortletURL()
			).setParameter(
				"selectedTab", tabName
			).buildString());

		navigationItem.setLabel(LanguageUtil.get(_httpServletRequest, label));

		return navigationItem;
	}

	private String _getOrderByCol() {
		if (Validator.isNotNull(_orderByCol)) {
			return _orderByCol;
		}

		_orderByCol = SearchOrderByUtil.getOrderByCol(
			_httpServletRequest, LayoutAdminPortletKeys.GROUP_PAGES,
			"select-layout-collection-order-by-col", "create-date");

		return _orderByCol;
	}

	private String _getOrderByType() {
		if (Validator.isNotNull(_orderByType)) {
			return _orderByType;
		}

		_orderByType = SearchOrderByUtil.getOrderByType(
			_httpServletRequest, LayoutAdminPortletKeys.GROUP_PAGES,
			"select-layout-collection-order-by-type", "asc");

		return _orderByType;
	}

	private boolean _isSearch() {
		if (Validator.isNotNull(_getKeywords())) {
			return true;
		}

		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SelectLayoutCollectionDisplayContext.class);

	private final GroupDisplayContextHelper _groupDisplayContextHelper;
	private final HttpServletRequest _httpServletRequest;
	private final InfoItemServiceTracker _infoItemServiceTracker;
	private String _keywords;
	private final LiferayPortletRequest _liferayPortletRequest;
	private final LiferayPortletResponse _liferayPortletResponse;
	private String _orderByCol;
	private String _orderByType;
	private String _redirect;
	private String _selectedTab;
	private final ThemeDisplay _themeDisplay;

}