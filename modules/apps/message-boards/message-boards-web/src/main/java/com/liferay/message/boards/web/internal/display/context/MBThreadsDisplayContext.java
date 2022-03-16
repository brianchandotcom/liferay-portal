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

package com.liferay.message.boards.web.internal.display.context;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.AssetEntryServiceUtil;
import com.liferay.asset.kernel.service.persistence.AssetEntryQuery;
import com.liferay.message.boards.model.MBMessage;
import com.liferay.message.boards.model.MBThread;
import com.liferay.message.boards.service.MBThreadServiceUtil;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.Objects;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Mariano Álvaro Sáiz
 */
public class MBThreadsDisplayContext {

	public MBThreadsDisplayContext(
		HttpServletRequest httpServletRequest, PortletURL portletURL,
		RenderRequest renderRequest) {

		_httpServletRequest = httpServletRequest;
		_portletURL = portletURL;
		_renderRequest = renderRequest;

		_themeDisplay = (ThemeDisplay)_httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public SearchContainer<?> getSearchContainer() throws PortalException {
		if (_searchContainer != null) {
			return _searchContainer;
		}

		if (Objects.equals(
				_getMVCRenderCommandName(),
				"/message_boards/view_my_subscriptions")) {

			_searchContainer = _getMBThreadSearchContainer();
		}
		else {
			_searchContainer = _getAssetEntrySearchContainer();
		}

		return _searchContainer;
	}

	private SearchContainer<AssetEntry> _getAssetEntrySearchContainer()
		throws PortalException {

		SearchContainer<AssetEntry> searchContainer = new SearchContainer(
			_renderRequest, _portletURL,
			ListUtil.fromArray("thread,started-by,posts,views,last-post"),
			"there-are-no-threads-in-this-category");

		AssetEntryQuery assetEntryQuery = new AssetEntryQuery(
			MBMessage.class.getName(), _searchContainer);

		assetEntryQuery.setEnablePermissions(true);
		assetEntryQuery.setExcludeZeroViewCount(false);

		searchContainer.setResultsAndTotal(
			() -> {
				assetEntryQuery.setEnd(searchContainer.getEnd());
				assetEntryQuery.setStart(searchContainer.getStart());

				return AssetEntryServiceUtil.getEntries(assetEntryQuery);
			},
			AssetEntryServiceUtil.getEntriesCount(assetEntryQuery));

		return searchContainer;
	}

	private long _getGroupThreadsUserId() {
		if (_groupThreadsUserId != null) {
			return _groupThreadsUserId;
		}

		_groupThreadsUserId = ParamUtil.getLong(
			_httpServletRequest, "groupThreadsUserId");

		if (_themeDisplay.isSignedIn()) {
			_groupThreadsUserId = _themeDisplay.getUserId();
		}

		return _groupThreadsUserId;
	}

	private SearchContainer<MBThread> _getMBThreadSearchContainer()
		throws PortalException {

		SearchContainer<MBThread> searchContainer = new SearchContainer(
			_renderRequest, _portletURL,
			ListUtil.fromArray("thread,started-by,posts,views,last-post"),
			"you-are-not-subscribed-to-any-threads");

		searchContainer.setResultsAndTotal(
			() -> MBThreadServiceUtil.getGroupThreads(
				_themeDisplay.getScopeGroupId(), _getGroupThreadsUserId(),
				WorkflowConstants.STATUS_APPROVED, true,
				searchContainer.getStart(), searchContainer.getEnd()),
			MBThreadServiceUtil.getGroupThreadsCount(
				_themeDisplay.getScopeGroupId(), _getGroupThreadsUserId(),
				WorkflowConstants.STATUS_APPROVED, true));

		return searchContainer;
	}

	private String _getMVCRenderCommandName() {
		if (_mvcRenderCommandName != null) {
			return _mvcRenderCommandName;
		}

		_mvcRenderCommandName = ParamUtil.getString(
			_httpServletRequest, "mvcRenderCommandName",
			"/message_boards/view");

		return _mvcRenderCommandName;
	}

	private Long _groupThreadsUserId;
	private final HttpServletRequest _httpServletRequest;
	private String _mvcRenderCommandName;
	private final PortletURL _portletURL;
	private final RenderRequest _renderRequest;
	private SearchContainer<?> _searchContainer;
	private final ThemeDisplay _themeDisplay;

}