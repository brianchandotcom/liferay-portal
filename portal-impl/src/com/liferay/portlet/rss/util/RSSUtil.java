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

package com.liferay.portlet.rss.util;

import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.webcache.WebCacheItem;
import com.liferay.portal.kernel.webcache.WebCachePoolUtil;

import com.sun.syndication.feed.synd.SyndFeed;

/**
 * @author Brian Wing Shun Chan
 */
public class RSSUtil {

	public static String escapeJavaScriptLink(String link) {
		if (Validator.isNull(link)) {
			return StringPool.BLANK;
		}

		if (link.indexOf(StringPool.COLON) == 10) {
			String protocol = StringUtil.toLowerCase(link.substring(0, 10));

			if (protocol.equals("javascript")) {
				link = StringUtil.replaceFirst(link, StringPool.COLON, "%3a");
			}
		}

		return link;
	}

	public static ObjectValuePair<String, SyndFeed> getFeed(String url) {
		WebCacheItem wci = new RSSWebCacheItem(url);

		return new ObjectValuePair<String, SyndFeed>(
			url,
			(SyndFeed)WebCachePoolUtil.get(
				RSSUtil.class.getName() + StringPool.PERIOD + url, wci));
	}

}