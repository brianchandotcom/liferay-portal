/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.webcache.WebCacheException;
import com.liferay.portal.kernel.webcache.WebCacheItem;
import com.liferay.portal.kernel.webcache.WebCachePoolUtil;

import com.sun.syndication.feed.synd.SyndFeed;

/**
 * @author Brian Wing Shun Chan
 */
public class RSSUtil {

	public static ObjectValuePair<String, SyndFeed> getFeed(String url) {
		WebCacheItem wci = new RSSWebCacheItem(url);

		SyndFeed syndFeed = null;

		try {
			syndFeed = (SyndFeed)WebCachePoolUtil.get(
				RSSUtil.class.getName() + StringPool.PERIOD + url, wci);
		}
		catch (WebCacheException wce) {
		}

		return new ObjectValuePair<String, SyndFeed>(url, syndFeed);
	}

}