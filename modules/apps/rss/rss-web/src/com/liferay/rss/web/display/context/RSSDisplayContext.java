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

import com.liferay.portal.kernel.util.StringPool;
import com.liferay.rss.web.settings.RSSPortletInstanceSettings;
import com.liferay.rss.web.util.RSSFeed;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.PortletPreferences;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class RSSDisplayContext {

	public RSSDisplayContext(
		RSSPortletInstanceSettings rssPortletInstanceSettings) {

		_rssPortletInstanceSettings = rssPortletInstanceSettings;
	}

	public List<RSSFeed> getRSSFeeds() {
		List<RSSFeed> rssFeeds = new ArrayList<>();

		String[] titles = _rssPortletInstanceSettings.titles();

		String[] urls = _rssPortletInstanceSettings.urls();

		for (int i = 0; i < urls.length; i++) {
			String url = urls[i];

			String title = StringPool.BLANK;

			if (i < titles.length) {
				title = titles[i];
			}

			rssFeeds.add(new RSSFeed(url, title));
		}

		return rssFeeds;
	}

	private final RSSPortletInstanceSettings _rssPortletInstanceSettings;

}