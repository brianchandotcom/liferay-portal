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
import com.liferay.info.item.provider.InfoItemFormProviderTracker;
import com.liferay.info.list.provider.InfoListProvider;
import com.liferay.info.list.provider.InfoListProviderTracker;
import com.liferay.layout.admin.web.internal.constants.LayoutAdminWebKeys;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.language.LanguageUtil;
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
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portlet.layoutsadmin.display.context.GroupDisplayContextHelper;

import java.util.List;
import java.util.Objects;

import javax.portlet.PortletException;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Jürgen Kappler
 */
public class SelectCollectionDisplayContext {

	public SelectCollectionDisplayContext(
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse) {

		_liferayPortletRequest = liferayPortletRequest;
		_liferayPortletResponse = liferayPortletResponse;

		_httpServletRequest = PortalUtil.getHttpServletRequest(
			_liferayPortletRequest);

		_themeDisplay = (ThemeDisplay)_httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		_groupDisplayContextHelper = new GroupDisplayContextHelper(
			_httpServletRequest);

		_infoItemFormProviderTracker =
			(InfoItemFormProviderTracker)_liferayPortletRequest.getAttribute(
				LayoutAdminWebKeys.INFO_ITEM_FORM_PROVIDER_TRACKER);

		_infoListProviderTracker =
			(InfoListProviderTracker)_liferayPortletRequest.getAttribute(
				LayoutAdminWebKeys.INFO_LIST_PROVIDER_TRACKER);
	}

	public SearchContainer<InfoListProvider<?>>
		getCollectionProvidersSearchContainer() {

		SearchContainer<InfoListProvider<?>> searchContainer =
			new SearchContainer<>(
				_liferayPortletRequest, _getPortletURL(), null,
				LanguageUtil.get(
					_httpServletRequest, "there-are-no-collection-providers"));

		List<InfoListProvider<?>> infoListProviders =
			_infoListProviderTracker.getInfoListProviders();

		searchContainer.setResults(
			ListUtil.subList(
				infoListProviders, searchContainer.getStart(),
				searchContainer.getEnd()));
		searchContainer.setTotal(infoListProviders.size());

		return searchContainer;
	}

	public SearchContainer<AssetListEntry> getCollectionsSearchContainer() {
		SearchContainer<AssetListEntry> searchContainer = new SearchContainer<>(
			_liferayPortletRequest, _getPortletURL(), null,
			LanguageUtil.get(_httpServletRequest, "there-are-no-collections"));

		String orderByCol = ParamUtil.getString(
			_httpServletRequest, "orderByCol", "create-date");

		String orderByType = ParamUtil.getString(
			_httpServletRequest, "orderByType", "asc");

		OrderByComparator<AssetListEntry> orderByComparator =
			AssetListPortletUtil.getAssetListEntryOrderByComparator(
				orderByCol, orderByType);

		searchContainer.setOrderByCol(orderByCol);
		searchContainer.setOrderByComparator(orderByComparator);
		searchContainer.setOrderByType(orderByType);

		long[] groupIds = {_themeDisplay.getScopeGroupId()};

		List<String> types = _getInfoDisplayContributorsClassNames();

		List<AssetListEntry> assetListEntries =
			AssetListEntryServiceUtil.getAssetListEntries(
				groupIds, types.toArray(new String[0]),
				searchContainer.getStart(), searchContainer.getEnd(),
				searchContainer.getOrderByComparator());

		int assetListEntriesCount =
			AssetListEntryServiceUtil.getAssetListEntriesCount(
				groupIds, types.toArray(new String[0]));

		searchContainer.setResults(assetListEntries);
		searchContainer.setTotal(assetListEntriesCount);

		return searchContainer;
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

	private List<String> _getInfoDisplayContributorsClassNames() {
		List<String> infoDisplayContributorsClassNames =
			_infoItemFormProviderTracker.getInfoItemClassNames();

		if (infoDisplayContributorsClassNames.contains(
				FileEntry.class.getName())) {

			infoDisplayContributorsClassNames.add(
				DLFileEntryConstants.getClassName());
			infoDisplayContributorsClassNames.remove(FileEntry.class.getName());
		}

		return infoDisplayContributorsClassNames;
	}

	private PortletURL _getPortletURL() {
		PortletURL currentURLObj = PortletURLUtil.getCurrent(
			_liferayPortletRequest, _liferayPortletResponse);

		try {
			return PortletURLUtil.clone(currentURLObj, _liferayPortletResponse);
		}
		catch (PortletException portletException) {
			PortletURL portletURL = _liferayPortletResponse.createRenderURL();

			portletURL.setParameters(currentURLObj.getParameterMap());

			return portletURL;
		}
	}

	private final GroupDisplayContextHelper _groupDisplayContextHelper;
	private final HttpServletRequest _httpServletRequest;
	private final InfoItemFormProviderTracker _infoItemFormProviderTracker;
	private final InfoListProviderTracker _infoListProviderTracker;
	private final LiferayPortletRequest _liferayPortletRequest;
	private final LiferayPortletResponse _liferayPortletResponse;
	private String _redirect;
	private String _selectedTab;
	private final ThemeDisplay _themeDisplay;

}