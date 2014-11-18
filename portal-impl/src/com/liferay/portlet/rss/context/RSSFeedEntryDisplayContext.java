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
import com.liferay.util.RSSUtil;

import com.sun.syndication.feed.synd.SyndContent;
import com.sun.syndication.feed.synd.SyndEnclosure;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Eudaldo Alonso
 */
public class RSSFeedEntryDisplayContext {

	public RSSFeedEntryDisplayContext(
		SyndEntry syndEntry, ThemeDisplay themeDisplay,
		RSSFeedContext rssFeedContext) {

		_syndEntry = syndEntry;
		_themeDisplay = themeDisplay;
		_rssFeedContext = rssFeedContext;

		List<SyndEnclosure> syndEnclosures = syndEntry.getEnclosures();

		String syndEnclosureLink = StringPool.BLANK;
		String syndEnclosureLinkTitle = syndEntry.getTitle();

		for (SyndEnclosure syndEnclosure : syndEnclosures) {
			if (Validator.isNotNull(syndEnclosure.getUrl())) {
				syndEnclosureLink = syndEnclosure.getUrl();

				int pos = syndEnclosureLink.lastIndexOf(
					StringPool.FORWARD_SLASH);

				if (pos > -1) {
					syndEnclosureLinkTitle = syndEnclosureLink.substring(
						pos + 1);
				}
				else {
					syndEnclosureLinkTitle = syndEnclosureLink;
				}

				break;
			}
		}

		_syndEnclosureLink = syndEnclosureLink;
		_syndEnclosureLinkTitle = syndEnclosureLinkTitle;

		String syndEntryLink = syndEntry.getLink();

		if (Validator.isNotNull(syndEntryLink) &&
			!HttpUtil.hasDomain(syndEntryLink)) {

			syndEntryLink = rssFeedContext.getBaseURL() + syndEntryLink;
		}

		_syndEntryLink = syndEntryLink;
	}

	public String getSanitizedContent() {
		String baseURL = _rssFeedContext.getBaseURL();
		SyndFeed syndFeed = _rssFeedContext.getSyndFeed();

		List<SyndContent> syndContents = getSyndContents();

		StringBundler sb = new StringBundler(syndContents.size());

		for (SyndContent syndContent : syndContents) {
			if ((syndContent == null) ||
				Validator.isNull(syndContent.getValue())) {

				continue;
			}

			String sanitizedValue = StringPool.BLANK;

			String feedType = syndFeed.getFeedType();
			String type = syndContent.getType();

			if (RSSUtil.getFormatType(feedType).equals(RSSUtil.ATOM) &&
				(type.equals("html") || type.equals("xhtml"))) {

				String value = StringUtil.replace(
					syndContent.getValue(), new String[]{"src=\"/", "href=\"/"},
					new String[] {
						"src=\"" + baseURL + "/", "href=\"" + baseURL + "/"});

				try {
					sanitizedValue = SanitizerUtil.sanitize(
						_themeDisplay.getCompanyGroupId(),
						_themeDisplay.getScopeGroupId(),
						_themeDisplay.getUserId(), null, 0,
						ContentTypes.TEXT_HTML, Sanitizer.MODE_XSS, value,
						null);
				}
				catch (SanitizerException se) {
				}
			}
			else {
				sanitizedValue = HtmlUtil.escape(syndContent.getValue());
			}

			sb.append(sanitizedValue);
		}

		return sb.toString();
	}

	public String getSyndEnclosureLink() {
		return _syndEnclosureLink;
	}

	public String getSyndEnclosureLinkTitle() {
		return _syndEnclosureLinkTitle;
	}

	public SyndEntry getSyndEntry() {
		return _syndEntry;
	}

	public String getSyndEntryLink() {
		return _syndEntryLink;
	}

	protected List<SyndContent> getSyndContents() {
		SyndContent syndContent = _syndEntry.getDescription();

		if (Validator.isNull(syndContent)) {
			return _syndEntry.getContents();
		}

		List<SyndContent> syndContents = new ArrayList<SyndContent>();

		syndContents.add(syndContent);

		return syndContents;
	}

	private final RSSFeedContext _rssFeedContext;
	private final String _syndEnclosureLink;
	private final String _syndEnclosureLinkTitle;
	private final SyndEntry _syndEntry;
	private final String _syndEntryLink;
	private final ThemeDisplay _themeDisplay;

}