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
import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;
import com.liferay.portal.kernel.dao.search.RowChecker;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.List;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Pavel Savinov
 */
public class SelectAssetListDisplayContext {

	public SelectAssetListDisplayContext(
		HttpServletRequest request, RenderResponse renderResponse) {

		_request = request;
		_renderResponse = renderResponse;
	}

	public String getEventName() {
		if (Validator.isNotNull(_eventName)) {
			return _eventName;
		}

		_eventName = ParamUtil.getString(_request, "eventName");

		return _eventName;
	}

	public SearchContainer getSearchContainer() throws Exception {
		if (_searchContainer != null) {
			return _searchContainer;
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		PortletRequest portletRequest = (PortletRequest)_request.getAttribute(
			JavaConstants.JAVAX_PORTLET_REQUEST);

		PortletResponse portletResponse =
			(PortletResponse)_request.getAttribute(
				JavaConstants.JAVAX_PORTLET_RESPONSE);

		SearchContainer<AssetListEntry> searchContainer = new SearchContainer<>(
			portletRequest, _getPortletURL(), null, "there-are-no-asset-lists");

		RowChecker rowChecker = new EmptyOnClickRowChecker(portletResponse);

		rowChecker.setCssClass("asset-list-entry-checkbox");

		searchContainer.setRowChecker(rowChecker);

		List<AssetListEntry> assetListEntries =
			AssetListEntryServiceUtil.getAssetListEntries(
				themeDisplay.getScopeGroupId(), searchContainer.getStart(),
				searchContainer.getEnd(),
				searchContainer.getOrderByComparator());

		searchContainer.setResults(assetListEntries);

		int assetListEntriesCount =
			AssetListEntryServiceUtil.getAssetListEntriesCount(
				themeDisplay.getScopeGroupId());

		searchContainer.setTotal(assetListEntriesCount);

		_searchContainer = searchContainer;

		return _searchContainer;
	}

	public long getSelectedAssetListEntryId() {
		if (Validator.isNotNull(_assetListEntryId)) {
			return _assetListEntryId;
		}

		_assetListEntryId = ParamUtil.getLong(_request, "assetListEntryId");

		return _assetListEntryId;
	}

	private PortletURL _getPortletURL() {
		PortletURL portletURL = _renderResponse.createRenderURL();

		return portletURL;
	}

	private Long _assetListEntryId;
	private String _eventName;
	private final RenderResponse _renderResponse;
	private final HttpServletRequest _request;
	private SearchContainer _searchContainer;

}