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
import com.liferay.rss.web.configuration.RSSPortletInstanceConfiguration;
import com.liferay.rss.web.configuration.RSSWebConfiguration;
import com.liferay.rss.web.util.RSSFeed;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Eudaldo Alonso
 */
public class RSSDisplayContext {

	public RSSDisplayContext(
		RSSPortletInstanceConfiguration rssPortletInstanceConfiguration,
		RSSWebConfiguration rssWebConfiguration) {

		_rssPortletInstanceConfiguration = rssPortletInstanceConfiguration;
		_rssWebConfiguration = rssWebConfiguration;
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

	private final RSSPortletInstanceConfiguration
		_rssPortletInstanceConfiguration;
	private final RSSWebConfiguration _rssWebConfiguration;

}