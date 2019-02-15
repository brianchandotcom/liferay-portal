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

package com.liferay.journal.web.internal.display.context;

import com.liferay.journal.model.JournalFeed;
import com.liferay.journal.service.JournalFeedLocalServiceUtil;
import com.liferay.journal.web.internal.search.FeedSearch;
import com.liferay.journal.web.internal.search.FeedSearchTerms;
import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.List;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * @author Eudaldo Alonso
 */
public class JournalFeedsDisplayContext {

	public JournalFeedsDisplayContext(
		RenderRequest renderRequest, RenderResponse renderResponse) {

		_renderRequest = renderRequest;
		_renderResponse = renderResponse;
	}

	public String getDisplayStyle() {
		if (_displayStyle != null) {
			return _displayStyle;
		}

		_displayStyle = ParamUtil.getString(
			_renderRequest, "displayStyle", "list");

		return _displayStyle;
	}

	public SearchContainer getFeedsSearchContainer() {
		if (_feedSearch != null) {
			return _feedSearch;
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)_renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		_feedSearch = new FeedSearch(_renderRequest, getPortletURL());

		_feedSearch.setRowChecker(new EmptyOnClickRowChecker(_renderResponse));

		FeedSearchTerms searchTerms =
			(FeedSearchTerms)_feedSearch.getSearchTerms();

		int feedsCount = JournalFeedLocalServiceUtil.searchCount(
			themeDisplay.getCompanyId(), searchTerms.getGroupId(),
			searchTerms.getKeywords());

		_feedSearch.setTotal(feedsCount);

		List<JournalFeed> feeds = JournalFeedLocalServiceUtil.search(
			themeDisplay.getCompanyId(), searchTerms.getGroupId(),
			searchTerms.getKeywords(), _feedSearch.getStart(),
			_feedSearch.getEnd(), _feedSearch.getOrderByComparator());

		_feedSearch.setResults(feeds);

		return _feedSearch;
	}

	public String getOrderByCol() {
		if (_orderByCol != null) {
			return _orderByCol;
		}

		_orderByCol = ParamUtil.getString(_renderRequest, "orderByCol", "name");

		return _orderByCol;
	}

	public String getOrderByType() {
		if (_orderByType != null) {
			return _orderByType;
		}

		_orderByType = ParamUtil.getString(
			_renderRequest, "orderByType", "asc");

		return _orderByType;
	}

	public PortletURL getPortletURL() {
		PortletURL portletURL = _renderResponse.createRenderURL();

		portletURL.setParameter("mvcPath", "/view_feeds.jsp");
		portletURL.setParameter("redirect", getRedirect());
		portletURL.setParameter("displayStyle", getDisplayStyle());
		portletURL.setParameter("orderByCol", getOrderByCol());
		portletURL.setParameter("orderByType", getOrderByType());

		return portletURL;
	}

	public String getRedirect() {
		if (_redirect != null) {
			return _redirect;
		}

		_redirect = ParamUtil.getString(_renderRequest, "redirect");

		return _redirect;
	}

	private String _displayStyle;
	private FeedSearch _feedSearch;
	private String _orderByCol;
	private String _orderByType;
	private String _redirect;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;

}