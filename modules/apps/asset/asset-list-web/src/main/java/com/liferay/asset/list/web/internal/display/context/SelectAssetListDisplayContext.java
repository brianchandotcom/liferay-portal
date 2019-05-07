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

package com.liferay.asset.list.web.internal.display.context;

import com.liferay.asset.list.model.AssetListEntry;
import com.liferay.asset.list.service.AssetListEntryServiceUtil;
import com.liferay.asset.list.util.AssetListPortletUtil;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Pavel Savinov
 */
public class SelectAssetListDisplayContext {

	public SelectAssetListDisplayContext(
		HttpServletRequest httpServletRequest, RenderResponse renderResponse) {

		_httpServletRequest = httpServletRequest;
		_renderResponse = renderResponse;
	}

	public String getEventName() {
		if (Validator.isNotNull(_eventName)) {
			return _eventName;
		}

		_eventName = ParamUtil.getString(_httpServletRequest, "eventName");

		return _eventName;
	}

	public String getOrderByType() {
		if (_orderByType != null) {
			return _orderByType;
		}

		_orderByType = ParamUtil.getString(
			_httpServletRequest, "orderByType", "asc");

		return _orderByType;
	}

	public SearchContainer getSearchContainer() {
		if (_searchContainer != null) {
			return _searchContainer;
		}

		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		PortletRequest portletRequest =
			(PortletRequest)_httpServletRequest.getAttribute(
				JavaConstants.JAVAX_PORTLET_REQUEST);

		SearchContainer<AssetListEntry> searchContainer = new SearchContainer<>(
			portletRequest, _getPortletURL(), null,
			"there-are-no-content-sets");

		OrderByComparator<AssetListEntry> orderByComparator =
			AssetListPortletUtil.getAssetListEntryOrderByComparator(
				_getOrderByCol(), getOrderByType());

		searchContainer.setOrderByCol(_getOrderByCol());
		searchContainer.setOrderByComparator(orderByComparator);
		searchContainer.setOrderByType(getOrderByType());

		List<AssetListEntry> assetListEntries = new ArrayList<>();
		int assetListEntriesCount = 0;

		if (_isSearch()) {
			assetListEntries = AssetListEntryServiceUtil.getAssetListEntries(
				themeDisplay.getScopeGroupId(), _getKeywords(),
				searchContainer.getStart(), searchContainer.getEnd(),
				searchContainer.getOrderByComparator());
			assetListEntriesCount =
				AssetListEntryServiceUtil.getAssetListEntriesCount(
					themeDisplay.getScopeGroupId(), _getKeywords());
		}
		else {
			assetListEntries = AssetListEntryServiceUtil.getAssetListEntries(
				themeDisplay.getScopeGroupId(), searchContainer.getStart(),
				searchContainer.getEnd(),
				searchContainer.getOrderByComparator());
			assetListEntriesCount =
				AssetListEntryServiceUtil.getAssetListEntriesCount(
					themeDisplay.getScopeGroupId());
		}

		searchContainer.setResults(assetListEntries);
		searchContainer.setTotal(assetListEntriesCount);

		_searchContainer = searchContainer;

		return _searchContainer;
	}

	public long getSelectedAssetListEntryId() {
		if (Validator.isNotNull(_assetListEntryId)) {
			return _assetListEntryId;
		}

		_assetListEntryId = ParamUtil.getLong(
			_httpServletRequest, "assetListEntryId");

		return _assetListEntryId;
	}

	private String _getKeywords() {
		if (_keywords != null) {
			return _keywords;
		}

		_keywords = ParamUtil.getString(_httpServletRequest, "keywords");

		return _keywords;
	}

	private String _getOrderByCol() {
		if (Validator.isNotNull(_orderByCol)) {
			return _orderByCol;
		}

		_orderByCol = ParamUtil.getString(
			_httpServletRequest, "orderByCol", "create-date");

		return _orderByCol;
	}

	private PortletURL _getPortletURL() {
		PortletURL portletURL = _renderResponse.createRenderURL();

		portletURL.setParameter("mvcPath", "/select_asset_list.jsp");
		portletURL.setParameter("keywords", _getKeywords());
		portletURL.setParameter("eventName", getEventName());

		return portletURL;
	}

	private boolean _isSearch() {
		if (Validator.isNotNull(_getKeywords())) {
			return true;
		}

		return false;
	}

	private Long _assetListEntryId;
	private String _eventName;
	private final HttpServletRequest _httpServletRequest;
	private String _keywords;
	private String _orderByCol;
	private String _orderByType;
	private final RenderResponse _renderResponse;
	private SearchContainer _searchContainer;

}