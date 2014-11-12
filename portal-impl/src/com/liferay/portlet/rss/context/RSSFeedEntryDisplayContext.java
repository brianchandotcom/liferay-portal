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

import com.liferay.portal.kernel.sanitizer.Sanitizer;
import com.liferay.portal.kernel.sanitizer.SanitizerException;
import com.liferay.portal.kernel.sanitizer.SanitizerUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.WebKeys;
import com.liferay.util.RSSUtil;

import com.sun.syndication.feed.synd.SyndContent;
import com.sun.syndication.feed.synd.SyndFeed;

import java.util.List;

/**
 * @author Eudaldo Alonso
 */
public class RSSFeedEntryDisplayContext {

	public String getSanitizedContent() {
		ThemeDisplay themeDisplay = (ThemeDisplay) _request.getAttribute(
			WebKeys.THEME_DISPLAY);

		String baseURL = _rssFeed.getBaseURL();
		SyndFeed feed = _rssFeed.getFeed();

		List<SyndContent> contents = getContents();

		StringBundler sb = new StringBundler(contents.size());

		for (SyndContent content : contents) {
			if ((content == null) || Validator.isNull(content.getValue())) {
				continue;
			}

			String sanitizedValue = StringPool.BLANK;

			String feedType = feed.getFeedType();
			String type = content.getType();

			if (RSSUtil.getFormatType(feedType).equals(RSSUtil.ATOM) &&
				(type.equals("html") || type.equals("xhtml"))) {

				String value = StringUtil.replace(
					content.getValue(), new String[]{"src=\"/", "href=\"/"},
					new String[] {
						"src=\"" + baseURL + "/", "href=\"" + baseURL + "/"});

				try {
					sanitizedValue = SanitizerUtil.sanitize(
						themeDisplay.getCompanyGroupId(),
						themeDisplay.getScopeGroupId(),
						themeDisplay.getUserId(), null, 0,
						ContentTypes.TEXT_HTML, Sanitizer.MODE_XSS, value,
						null);
				}
				catch (SanitizerException se) {
				}
			}
			else {
				sanitizedValue = HtmlUtil.escape(content.getValue());
			}

			sb.append(sanitizedValue);
		}

		return sb.toString();
	}

}