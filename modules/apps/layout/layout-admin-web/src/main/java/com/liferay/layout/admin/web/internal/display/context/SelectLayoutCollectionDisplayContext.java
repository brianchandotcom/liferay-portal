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
import com.liferay.document.library.kernel.model.DLFileEntryConstants;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItem;
import com.liferay.info.item.InfoItemServiceTracker;
import com.liferay.info.item.provider.InfoItemFormProvider;
import com.liferay.info.list.provider.DefaultInfoListProviderContext;
import com.liferay.info.list.provider.InfoListProvider;
import com.liferay.info.list.provider.InfoListProviderTracker;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
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
		InfoListProviderTracker infoListProviderTracker,
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse) {

		_infoItemServiceTracker = infoItemServiceTracker;
		_infoListProviderTracker = infoListProviderTracker;
		_liferayPortletRequest = liferayPortletRequest;
		_liferayPortletResponse = liferayPortletResponse;

		_httpServletRequest = PortalUtil.getHttpServletRequest(
			_liferayPortletRequest);
		_themeDisplay = (ThemeDisplay)_liferayPortletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		_groupDisplayContextHelper = new GroupDisplayContextHelper(
			_httpServletRequest);
	}

	public SearchContainer<InfoListProvider<?>>
		getCollectionProvidersSearchContainer() {

		SearchContainer<InfoListProvider<?>> searchContainer =
			new SearchContainer<>(
				_liferayPortletRequest, getPortletURL(), null,
				LanguageUtil.get(
					_httpServletRequest, "there-are-no-collection-providers"));

		List<InfoListProvider<?>> infoListProviders = _getInfoListProviders();

		searchContainer.setResults(
			ListUtil.subList(
				infoListProviders, searchContainer.getStart(),
				searchContainer.getEnd()));
		searchContainer.setTotal(infoListProviders.size());

		return searchContainer;
	}

	public SearchContainer<AssetListEntry> getCollectionsSearchContainer() {
		SearchContainer<AssetListEntry> searchContainer = new SearchContainer<>(
			_liferayPortletRequest, getPortletURL(), null,
			LanguageUtil.get(_httpServletRequest, "there-are-no-collections"));

		String orderByCol = ParamUtil.getString(
			_httpServletRequest, "orderByCol", "create-date");

		String orderByType = ParamUtil.getString(
			_httpServletRequest, "orderByType", "asc");

		OrderByComparator<AssetListEntry> orderByComparator =
			AssetListPortletUtil.getAssetListEntryOrderByComparator(
				_getOrderByCol(), _getOrderByType());

		searchContainer.setOrderByCol(orderByCol);
		searchContainer.setOrderByComparator(orderByComparator);
		searchContainer.setOrderByType(orderByType);

		List<AssetListEntry> assetListEntries = null;

		int assetListEntriesCount = 0;

		if (_isSearch()) {
			assetListEntries = AssetListEntryServiceUtil.getAssetListEntries(
				_themeDisplay.getScopeGroupId(), _getKeywords(),
				searchContainer.getStart(), searchContainer.getEnd(),
				orderByComparator);

			assetListEntriesCount =
				AssetListEntryServiceUtil.getAssetListEntriesCount(
					_themeDisplay.getScopeGroupId(), _getKeywords());
		}
		else {
			long[] groupIds = {_themeDisplay.getScopeGroupId()};

			List<String> types = _getInfoItemFormProviderClassNames();

			assetListEntries = AssetListEntryServiceUtil.getAssetListEntries(
				groupIds, types.toArray(new String[0]),
				searchContainer.getStart(), searchContainer.getEnd(),
				searchContainer.getOrderByComparator());

			assetListEntriesCount =
				AssetListEntryServiceUtil.getAssetListEntriesCount(
					groupIds, types.toArray(new String[0]));
		}

		searchContainer.setResults(assetListEntries);

		searchContainer.setTotal(assetListEntriesCount);

		return searchContainer;
	}

	public List<NavigationItem> getNavigationItems() {
		List<NavigationItem> navigationItems = new ArrayList<>();

		navigationItems.add(_getNavigationItem("collections", "collections"));
		navigationItems.add(
			_getNavigationItem("collection-providers", "collection-providers"));

		return navigationItems;
	}

	public PortletURL getPortletURL() {
		PortletURL currentURLObj = PortletURLUtil.getCurrent(
			_liferayPortletRequest, _liferayPortletResponse);

		try {
			return PortletURLUtil.clone(currentURLObj, _liferayPortletResponse);
		}
		catch (PortletException portletException) {
			if (_log.isDebugEnabled()) {
				_log.debug(portletException, portletException);
			}

			return PortletURLBuilder.createRenderURL(
				_liferayPortletResponse
			).setParameters(
				currentURLObj.getParameterMap()
			).build();
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

	private List<String> _getInfoItemFormProviderClassNames() {
		List<String> infoItemClassNames =
			_infoItemServiceTracker.getInfoItemClassNames(
				InfoItemFormProvider.class);

		if (infoItemClassNames.contains(FileEntry.class.getName())) {
			infoItemClassNames.add(DLFileEntryConstants.getClassName());
			infoItemClassNames.remove(FileEntry.class.getName());
		}

		return infoItemClassNames;
	}

	private List<InfoListProvider<?>> _getInfoListProviders() {
		List<InfoListProvider<?>> infoListProviders =
			_infoListProviderTracker.getInfoListProviders();

		DefaultInfoListProviderContext defaultInfoListProviderContext =
			new DefaultInfoListProviderContext(
				_themeDisplay.getScopeGroup(), _themeDisplay.getUser());

		defaultInfoListProviderContext.setLayout(_themeDisplay.getLayout());

		return ListUtil.filter(
			infoListProviders,
			infoListProvider -> {
				try {
					String label = infoListProvider.getLabel(
						_themeDisplay.getLocale());

					if (Validator.isNotNull(label) &&
						infoListProvider.isAvailable(
							defaultInfoListProviderContext)) {

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

	private String _getKeywords() {
		if (_keywords != null) {
			return _keywords;
		}

		_keywords = ParamUtil.getString(_httpServletRequest, "keywords");

		return _keywords;
	}

	private NavigationItem _getNavigationItem(String label, String tabName) {
		NavigationItem navigationItem = new NavigationItem();

		String selectedTabName = ParamUtil.getString(
			_httpServletRequest, "selectedTab", "collections");

		if (Objects.equals(tabName, selectedTabName)) {
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

		_orderByCol = ParamUtil.getString(
			_httpServletRequest, "orderByCol", "create-date");

		return _orderByCol;
	}

	private String _getOrderByType() {
		if (Validator.isNotNull(_orderByType)) {
			return _orderByType;
		}

		_orderByType = ParamUtil.getString(
			_httpServletRequest, "orderByType", "asc");

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
	private final InfoListProviderTracker _infoListProviderTracker;
	private String _keywords;
	private final LiferayPortletRequest _liferayPortletRequest;
	private final LiferayPortletResponse _liferayPortletResponse;
	private String _orderByCol;
	private String _orderByType;
	private String _redirect;
	private String _selectedTab;
	private final ThemeDisplay _themeDisplay;

}