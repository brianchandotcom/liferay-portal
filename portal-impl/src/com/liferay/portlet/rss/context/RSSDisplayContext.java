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

package com.liferay.portlet.rss.context;

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.WebKeys;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.PortletPreferences;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class RSSDisplayContext {

	public RSSDisplayContext(
		HttpServletRequest request, PortletPreferences portletPreferences) {

		_request = request;
		_portletPreferences = portletPreferences;
	}

	public String getDisplayStyle() {
		if (_displayStyle != null) {
			return _displayStyle;
		}

		_displayStyle = _portletPreferences.getValue(
			"displayStyle", StringPool.BLANK);

		return _displayStyle;
	}

	public Long getDisplayStyleGroupId() {
		if (_displayStyleGroupId != null) {
			return _displayStyleGroupId;
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		_displayStyleGroupId = GetterUtil.getLong(
			_portletPreferences.getValue("displayStyleGroupId", null),
			themeDisplay.getScopeGroupId());

		return _displayStyleGroupId;
	}

	public int getEntriesPerFeed() {
		if (_entriesPerFeed != null) {
			return _entriesPerFeed;
		}

		_entriesPerFeed = GetterUtil.getInteger(
			_portletPreferences.getValue("entriesPerFeed", "8"));

		return _entriesPerFeed;
	}

	public int getExpandedEntriesPerFeed() {
		if (_expandedEntriesPerFeed != null) {
			return _expandedEntriesPerFeed;
		}

		_expandedEntriesPerFeed = GetterUtil.getInteger(
			_portletPreferences.getValue("expandedEntriesPerFeed", "8"));

		return _expandedEntriesPerFeed;
	}

	public String getFeedImageAlignment() {
		if (_feedImageAlignment != null) {
			return _feedImageAlignment;
		}

		_feedImageAlignment = _portletPreferences.getValue(
			"feedImageAlignment", "right");

		return _feedImageAlignment;
	}

	public List<RSSFeedContext> getRSSFeedContexts() {
		List<RSSFeedContext> rssFeedContexts = new ArrayList<RSSFeedContext>();

		String[] titles = getTitles();
		String[] urls = getURLs();

		for (int i = 0; i < urls.length; i++) {
			String url = urls[i];
			String title = StringPool.BLANK;

			if (i < titles.length) {
				title = titles[i];
			}

			rssFeedContexts.add(new RSSFeedContext(url, title));
		}

		return rssFeedContexts;
	}

	public String[] getTitles() {
		if (_titles != null) {
			return _titles;
		}

		_titles = _portletPreferences.getValues("titles", new String[0]);

		return _titles;
	}

	public String[] getURLs() {
		if (_urls != null) {
			return _urls;
		}

		_urls = _portletPreferences.getValues("urls", new String[0]);

		return _urls;
	}

	public boolean isShowFeedDescription() {
		if (_showFeedDescription != null) {
			return _showFeedDescription;
		}

		_showFeedDescription = GetterUtil.getBoolean(
			_portletPreferences.getValue(
				"showFeedDescription", Boolean.TRUE.toString()));

		return _showFeedDescription;
	}

	public boolean isShowFeedImage() {
		if (_showFeedImage != null) {
			return _showFeedImage;
		}

		_showFeedImage = GetterUtil.getBoolean(
			_portletPreferences.getValue(
				"showFeedImage", Boolean.TRUE.toString()));

		return _showFeedImage;
	}

	public boolean isShowFeedItemAuthor() {
		if (_showFeedItemAuthor != null) {
			return _showFeedItemAuthor;
		}

		_showFeedItemAuthor = GetterUtil.getBoolean(
			_portletPreferences.getValue(
				"showFeedItemAuthor", Boolean.TRUE.toString()));

		return _showFeedItemAuthor;
	}

	public boolean isShowFeedPublishedDate() {
		if (_showFeedPublishedDate != null) {
			return _showFeedPublishedDate;
		}

		_showFeedPublishedDate = GetterUtil.getBoolean(
			_portletPreferences.getValue(
				"showFeedPublishedDate", Boolean.TRUE.toString()));

		return _showFeedPublishedDate;
	}

	public boolean isShowFeedTitle() {
		if (_showFeedTitle != null) {
			return _showFeedTitle;
		}

		_showFeedTitle = GetterUtil.getBoolean(
			_portletPreferences.getValue(
				"showFeedTitle", Boolean.TRUE.toString()));

		return _showFeedTitle;
	}

	private String _displayStyle;
	private Long _displayStyleGroupId;
	private Integer _entriesPerFeed;
	private Integer _expandedEntriesPerFeed;
	private String _feedImageAlignment;
	private final PortletPreferences _portletPreferences;
	private final HttpServletRequest _request;
	private Boolean _showFeedDescription;
	private Boolean _showFeedImage;
	private Boolean _showFeedItemAuthor;
	private Boolean _showFeedPublishedDate;
	private Boolean _showFeedTitle;
	private String[] _titles;
	private String[] _urls;

}