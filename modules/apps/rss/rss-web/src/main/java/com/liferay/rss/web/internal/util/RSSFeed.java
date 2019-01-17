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

package com.liferay.rss.web.internal.util;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.webcache.WebCacheItem;
import com.liferay.portal.kernel.webcache.WebCachePoolUtil;
import com.liferay.rss.web.internal.configuration.RSSWebCacheConfiguration;

import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.feed.synd.SyndImage;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Eudaldo Alonso
 */
public class RSSFeed {

	public RSSFeed(
		RSSWebCacheConfiguration rssWebCacheConfiguration, String url,
		String title) {

		_rssWebCacheConfiguration = rssWebCacheConfiguration;
		_url = url;

		SyndFeed syndFeed = getSyndFeed();

		if (syndFeed == null) {
			_baseURL = StringPool.BLANK;
			_syndFeedImageLink = StringPool.BLANK;
			_syndFeedImageURL = StringPool.BLANK;
			_syndFeedLink = StringPool.BLANK;
			_title = title;

			return;
		}

		if (Validator.isNull(title)) {
			title = syndFeed.getTitle();
		}

		String baseURL = StringPool.BLANK;
		String syndFeedImageLink = StringPool.BLANK;
		String syndFeedImageURL = StringPool.BLANK;
		String syndFeedLink = syndFeed.getLink();

		if (Validator.isNull(syndFeedLink) ||
			!HttpUtil.hasDomain(syndFeedLink)) {

			baseURL = HttpUtil.getProtocol(
				_url
			).concat(
				Http.PROTOCOL_DELIMITER
			).concat(
				HttpUtil.getDomain(_url)
			);

			if (Validator.isNotNull(syndFeedLink)) {
				syndFeedLink = baseURL.concat(syndFeedLink);
			}
			else {
				syndFeedLink = baseURL;
			}
		}
		else {
			baseURL = HttpUtil.getProtocol(
				syndFeedLink
			).concat(
				Http.PROTOCOL_DELIMITER
			).concat(
				HttpUtil.getDomain(syndFeedLink)
			);
		}

		SyndImage syndImage = syndFeed.getImage();

		if (syndImage != null) {
			syndFeedImageLink = syndImage.getLink();

			if (!HttpUtil.hasDomain(syndFeedImageLink)) {
				syndFeedImageLink = baseURL + syndFeedImageLink;
			}

			syndFeedImageURL = syndImage.getUrl();

			if (!HttpUtil.hasDomain(syndFeedImageURL)) {
				syndFeedImageURL = baseURL + syndFeedImageURL;
			}
		}

		_baseURL = baseURL;
		_syndFeedImageLink = syndFeedImageLink;
		_syndFeedImageURL = syndFeedImageURL;
		_syndFeedLink = syndFeedLink;
		_title = title;
	}

	public String getBaseURL() {
		return _baseURL;
	}

	public List<RSSFeedEntry> getRSSFeedEntries(ThemeDisplay themeDisplay) {
		if (_rssFeedEntries != null) {
			return _rssFeedEntries;
		}

		_rssFeedEntries = new ArrayList<>();

		SyndFeed syndFeed = getSyndFeed();

		if (syndFeed == null) {
			return _rssFeedEntries;
		}

		for (Object syndEntry : syndFeed.getEntries()) {
			RSSFeedEntry rssFeedEntry = new RSSFeedEntry(
				this, (SyndEntry)syndEntry, themeDisplay);

			_rssFeedEntries.add(rssFeedEntry);
		}

		return _rssFeedEntries;
	}

	public SyndFeed getSyndFeed() {
		if (_syndFeed != null) {
			return _syndFeed;
		}

		WebCacheItem wci = new RSSWebCacheItem(_rssWebCacheConfiguration, _url);

		_syndFeed = (SyndFeed)WebCachePoolUtil.get(
			RSSFeed.class.getName() + StringPool.PERIOD + _url, wci);

		return _syndFeed;
	}

	public String getSyndFeedImageLink() {
		return _syndFeedImageLink;
	}

	public String getSyndFeedImageURL() {
		return _syndFeedImageURL;
	}

	public String getSyndFeedLink() {
		return _syndFeedLink;
	}

	public String getTitle() {
		return _title;
	}

	public String getURL() {
		return _url;
	}

	private final String _baseURL;
	private List<RSSFeedEntry> _rssFeedEntries;
	private final RSSWebCacheConfiguration _rssWebCacheConfiguration;
	private SyndFeed _syndFeed;
	private final String _syndFeedImageLink;
	private final String _syndFeedImageURL;
	private final String _syndFeedLink;
	private final String _title;
	private final String _url;

}