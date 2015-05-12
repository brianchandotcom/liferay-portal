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

package com.liferay.rss.web.display.context;

import com.liferay.portal.kernel.settings.SettingsException;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.theme.PortletDisplay;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.WebKeys;
import com.liferay.rss.web.configuration.RSSPortletInstanceConfiguration;
import com.liferay.rss.web.configuration.RSSWebConfiguration;
import com.liferay.rss.web.util.RSSFeed;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class RSSDisplayContext {

	public RSSDisplayContext(
		HttpServletRequest request,
		RSSWebConfiguration rssWebConfiguration) throws SettingsException {

		_request = request;
		_rssWebConfiguration = rssWebConfiguration;

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		_rssPortletInstanceConfiguration =
			portletDisplay.getPortletInstanceConfiguration(
				RSSPortletInstanceConfiguration.class);
	}

	public String getDisplayStyle() {
		if (_displayStyle != null) {
			return _displayStyle;
		}

		_displayStyle = _rssPortletInstanceConfiguration.displayStyle();

		return _displayStyle;
	}

	public long getDisplayStyleGroupId() {
		if (_displayStyleGroupId != 0) {
			return _displayStyleGroupId;
		}

		_displayStyleGroupId =
			_rssPortletInstanceConfiguration.displayStyleGroupId();

		if (_displayStyleGroupId <= 0) {
			ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
				WebKeys.THEME_DISPLAY);

			_displayStyleGroupId = themeDisplay.getScopeGroupId();
		}

		return _displayStyleGroupId;
	}

	public int getEntriesPerFeed() {
		if (_entriesPerFeed != null) {
			return _entriesPerFeed;
		}

		_entriesPerFeed = _rssPortletInstanceConfiguration.entriesPerFeed();

		return _entriesPerFeed;
	}

	public int getExpandedEntriesPerFeed() {
		if (_expandedEntriesPerFeed != null) {
			return _expandedEntriesPerFeed;
		}

		_expandedEntriesPerFeed =
			_rssPortletInstanceConfiguration.expandedEntriesPerFeed();

		return _expandedEntriesPerFeed;
	}

	public String getFeedImageAlignment() {
		if (_feedImageAlignment != null) {
			return _feedImageAlignment;
		}

		_feedImageAlignment =
			_rssPortletInstanceConfiguration.feedImageAlignment();

		return _feedImageAlignment;
	}

	public List<RSSFeed> getRSSFeeds() {
		List<RSSFeed> rssFeeds = new ArrayList<>();

		String[] titles = _rssPortletInstanceConfiguration.titles();

		String[] urls = _rssPortletInstanceConfiguration.urls();

		for (int i = 0; i < urls.length; i++) {
			String url = urls[i];

			String title = StringPool.BLANK;

			if (i < titles.length) {
				title = titles[i];
			}

			rssFeeds.add(new RSSFeed(_rssWebConfiguration, url, title));
		}

		return rssFeeds;
	}

	public String[] getTitles() {
		return _rssPortletInstanceConfiguration.titles();
	}

	public String[] getUrls() {
		return _rssPortletInstanceConfiguration.urls();
	}

	public boolean isShowFeedDescription() {
		if (_showFeedDescription != null) {
			return _showFeedDescription;
		}

		_showFeedDescription =
			_rssPortletInstanceConfiguration.showFeedDescription();

		return _showFeedDescription;
	}

	public boolean isShowFeedImage() {
		if (_showFeedImage != null) {
			return _showFeedImage;
		}

		_showFeedImage = _rssPortletInstanceConfiguration.showFeedImage();

		return _showFeedImage;
	}

	public boolean isShowFeedItemAuthor() {
		if (_showFeedItemAuthor != null) {
			return _showFeedItemAuthor;
		}

		_showFeedItemAuthor =
			_rssPortletInstanceConfiguration.showFeedItemAuthor();

		return _showFeedItemAuthor;
	}

	public boolean isShowFeedPublishedDate() {
		if (_showFeedPublishedDate != null) {
			return _showFeedPublishedDate;
		}

		_showFeedPublishedDate =
			_rssPortletInstanceConfiguration.showFeedPublishedDate();

		return _showFeedPublishedDate;
	}

	public boolean isShowFeedTitle() {
		if (_showFeedTitle != null) {
			return _showFeedTitle;
		}

		_showFeedTitle = _rssPortletInstanceConfiguration.showFeedTitle();

		return _showFeedTitle;
	}

	public String _displayStyle;
	public long _displayStyleGroupId;
	public Integer _entriesPerFeed;
	public Integer _expandedEntriesPerFeed;
	public String _feedImageAlignment;
	public Boolean _showFeedDescription;
	public Boolean _showFeedImage;
	public Boolean _showFeedItemAuthor;
	public Boolean _showFeedPublishedDate;
	public Boolean _showFeedTitle;

	private final HttpServletRequest _request;
	private final RSSPortletInstanceConfiguration
		_rssPortletInstanceConfiguration;
	private final RSSWebConfiguration _rssWebConfiguration;

}