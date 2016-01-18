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

import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.theme.PortletDisplay;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.rss.web.configuration.RSSPortletInstanceConfiguration;
import com.liferay.rss.web.configuration.RSSWebCacheConfiguration;
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
			RSSWebCacheConfiguration rssWebCacheConfiguration)
		throws ConfigurationException {

		_request = request;
		_rssWebCacheConfiguration = rssWebCacheConfiguration;

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		_rssPortletInstanceConfiguration =
			portletDisplay.getPortletInstanceConfiguration(
				RSSPortletInstanceConfiguration.class);
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

			rssFeeds.add(new RSSFeed(_rssWebCacheConfiguration, url, title));
		}

		return rssFeeds;
	}

	public RSSPortletInstanceConfiguration
		getRSSPortletInstanceConfiguration() {

		return _rssPortletInstanceConfiguration;
	}

	private long _displayStyleGroupId;
	private final HttpServletRequest _request;
	private final RSSPortletInstanceConfiguration
		_rssPortletInstanceConfiguration;
	private final RSSWebCacheConfiguration _rssWebCacheConfiguration;

}