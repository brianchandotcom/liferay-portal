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
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.WebKeys;
import com.liferay.util.RSSUtil;

import com.sun.syndication.feed.synd.SyndContent;
import com.sun.syndication.feed.synd.SyndEnclosure;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class RSSFeedEntryDisplayContext {

	public RSSFeedEntryDisplayContext(
		SyndEntry entry, HttpServletRequest request, RSSFeed rssFeed) {

		String entryLink = entry.getLink();

		if (Validator.isNotNull(entryLink) && !HttpUtil.hasDomain(entryLink)) {
			entryLink = rssFeed.getBaseURL() + entryLink;
		}

		List<SyndEnclosure> enclosures =
			(List<SyndEnclosure>)entry.getEnclosures();

		String enclosureLink = StringPool.BLANK;
		String enclosureLinkTitle = entry.getTitle();

		for (SyndEnclosure enclosure : enclosures) {
			if (Validator.isNotNull(enclosure.getUrl())) {
				enclosureLink = enclosure.getUrl();

				int pos = enclosureLink.lastIndexOf(StringPool.FORWARD_SLASH);

				if (pos > -1) {
					enclosureLinkTitle = enclosureLink.substring(pos + 1);
				}
				else {
					enclosureLinkTitle = enclosureLink;
				}

				break;
			}
		}

		_entry = entry;
		_entryLink = entryLink;
		_enclosureLink = enclosureLink;
		_enclosureLinkTitle = enclosureLinkTitle;
		_request = request;
		_rssFeed = rssFeed;
	}

	public String getEnclosureLink() {
		return _enclosureLink;
	}

	public String getEnclosureLinkTitle() {
		return _enclosureLinkTitle;
	}

	public String getEntryLink() {
		return _entryLink;
	}

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

	protected List<SyndContent> getContents() {
		SyndContent content = _entry.getDescription();

		if (Validator.isNull(content)) {
			return _entry.getContents();
		}

		List<SyndContent> contents = new ArrayList<SyndContent>();

		contents.add(content);

		return contents;
	}

	private final String _enclosureLink;
	private final String _enclosureLinkTitle;
	private final SyndEntry _entry;
	private final String _entryLink;
	private final HttpServletRequest _request;
	private final RSSFeed _rssFeed;

}