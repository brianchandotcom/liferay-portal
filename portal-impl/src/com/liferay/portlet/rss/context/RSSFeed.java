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

import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;

import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.feed.synd.SyndImage;

/**
 * @author Eudaldo Alonso
 */
public class RSSFeed {

	public RSSFeed(String url, String title) {
		_url = url;

		SyndFeed feed = getFeed();

		if (feed == null) {
			_baseURL = StringPool.BLANK;
			_feedImageLink = StringPool.BLANK;
			_feedImageURL = StringPool.BLANK;
			_feedLink = StringPool.BLANK;
			_title = title;

			return;
		}

		if (Validator.isNull(title)) {
			title = feed.getTitle();
		}

		String baseURL = StringPool.BLANK;
		String feedImageLink = StringPool.BLANK;
		String feedImageURL = StringPool.BLANK;
		String feedLink = feed.getLink();

		if (Validator.isNull(feedLink) || !HttpUtil.hasDomain(feedLink)) {
			baseURL = HttpUtil.getProtocol(_url).concat(
				Http.PROTOCOL_DELIMITER).concat(HttpUtil.getDomain(_url));

			if (Validator.isNotNull(feedLink)) {
				feedLink = baseURL.concat(feedLink);
			}
			else {
				feedLink = baseURL;
			}
		}
		else {
			baseURL = HttpUtil.getProtocol(feedLink).concat(
				Http.PROTOCOL_DELIMITER).concat(HttpUtil.getDomain(feedLink));
		}

		SyndImage feedImage = feed.getImage();

		if (feedImage != null) {
			feedImageLink = feedImage.getLink();

			if (!HttpUtil.hasDomain(feedImageLink)) {
				feedImageLink = baseURL + feedImageLink;
			}

			feedImageURL = feedImage.getUrl();

			if (!HttpUtil.hasDomain(feedImageURL)) {
				feedImageURL = baseURL + feedImageURL;
			}
		}

		_baseURL = baseURL;
		_feedImageLink = feedImageLink;
		_feedImageURL = feedImageURL;
		_feedLink = feedLink;
		_title = title;
	}

	public String getBaseURL() {
		return _baseURL;
	}

	public SyndFeed getFeed() {
		if (_feed != null) {
			return _feed;
		}

		try {
			ObjectValuePair ovp = com.liferay.portlet.rss.util.RSSUtil.getFeed(
				_url);

			_feed = (SyndFeed)ovp.getValue();
		}
		catch (Exception e) {
		}

		return _feed;
	}

	public String getFeedImageLink() {
		return _feedImageLink;
	}

	public String getFeedImageURL() {
		return _feedImageURL;
	}

	public String getFeedLink() {
		return _feedLink;
	}

	public String getTitle() {
		return _title;
	}

	public String getUrl() {
		return _url;
	}

	private final String _baseURL;
	private SyndFeed _feed;
	private final String _feedImageLink;
	private final String _feedImageURL;
	private final String _feedLink;
	private final String _title;
	private final String _url;

}