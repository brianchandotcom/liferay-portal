/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.rss.web.internal.util;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.HttpComponentsUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.webcache.WebCacheItem;
import com.liferay.portal.kernel.webcache.WebCachePoolUtil;
import com.liferay.rss.web.internal.configuration.RSSWebCacheConfiguration;

import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.feed.synd.SyndImage;

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
			_title = title;

			_baseURL = StringPool.BLANK;
			_syndFeedImageLink = StringPool.BLANK;
			_syndFeedImageURL = StringPool.BLANK;
			_syndFeedLink = StringPool.BLANK;

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
			!HttpComponentsUtil.hasDomain(syndFeedLink)) {

			baseURL = StringBundler.concat(
				HttpComponentsUtil.getProtocol(url), Http.PROTOCOL_DELIMITER,
				HttpComponentsUtil.getDomain(url));

			if (Validator.isNotNull(syndFeedLink)) {
				syndFeedLink = baseURL.concat(syndFeedLink);
			}
			else {
				syndFeedLink = baseURL;
			}
		}
		else {
			baseURL = StringBundler.concat(
				HttpComponentsUtil.getProtocol(syndFeedLink),
				Http.PROTOCOL_DELIMITER,
				HttpComponentsUtil.getDomain(syndFeedLink));
		}

		SyndImage syndImage = syndFeed.getImage();

		if (syndImage != null) {
			syndFeedImageLink = syndImage.getLink();

			if (!HttpComponentsUtil.hasDomain(syndFeedImageLink)) {
				syndFeedImageLink = baseURL + syndFeedImageLink;
			}

			syndFeedImageURL = syndImage.getUrl();

			if (!HttpComponentsUtil.hasDomain(syndFeedImageURL)) {
				syndFeedImageURL = baseURL + syndFeedImageURL;
			}
		}

		_title = title;

		_baseURL = baseURL;
		_syndFeedImageLink = syndFeedImageLink;
		_syndFeedImageURL = syndFeedImageURL;
		_syndFeedLink = syndFeedLink;
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

		WebCacheItem webCacheItem = new RSSWebCacheItem(
			_rssWebCacheConfiguration, _url);

		_syndFeed = (SyndFeed)WebCachePoolUtil.get(
			RSSFeed.class.getName() + StringPool.POUND + _url, webCacheItem);

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