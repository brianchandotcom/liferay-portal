/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.rss.action;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.struts.RSSAction;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.rss.RSSRenderer;
import com.liferay.util.RSSUtil;

import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.feed.synd.SyndFeedImpl;
import com.sun.syndication.feed.synd.SyndLink;
import com.sun.syndication.feed.synd.SyndLinkImpl;
import com.sun.syndication.io.FeedException;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Carlos Sierra Andrés
 *
 */
public abstract class DefaultRSSAction extends RSSAction {

	protected RSSRenderer createRSSRenderer(HttpServletRequest request)
		throws Exception {
			throw new UnsupportedOperationException();
	}

	protected RSSRenderer createRSSRenderer(
			ResourceRequest portletRequest, ResourceResponse portletResponse)
		throws Exception {

		return createRSSRenderer(
			PortalUtil.getHttpServletRequest(portletRequest));
	}

	@Override
	protected byte[] getRSS(HttpServletRequest request) throws Exception {
		return exportToRSS(
			createRSSRenderer(request)).getBytes(StringPool.UTF8);
	}

	@Override
	protected byte[] getRSS(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		return exportToRSS(
			createRSSRenderer(
				resourceRequest, resourceResponse)).getBytes(StringPool.UTF8);
	}

	private String exportToRSS(RSSRenderer rssRenderer) throws Exception {
		SyndFeed syndFeed = new SyndFeedImpl();

		syndFeed.setDescription(GetterUtil.getString(description, title));

		List<SyndEntry> syndEntries = new ArrayList<SyndEntry>();

		syndFeed.setEntries(syndEntries);

		for (SocialActivity activity : activities) {
			SocialActivityFeedEntry activityFeedEntry =
				SocialActivityInterpreterLocalServiceUtil.interpret(
					StringPool.BLANK, activity, serviceContext);

			if (activityFeedEntry == null) {
				continue;
			}

			SyndEntry syndEntry = new SyndEntryImpl();

			SyndContent syndContent = new SyndContentImpl();

			syndContent.setType(RSSUtil.ENTRY_TYPE_DEFAULT);

			String value = null;

			if (displayStyle.equals(RSSUtil.DISPLAY_STYLE_TITLE)) {
				value = StringPool.BLANK;
			}
			else {
				value = activityFeedEntry.getBody();
			}

			syndContent.setValue(value);

			syndEntry.setDescription(syndContent);

			if (Validator.isNotNull(activityFeedEntry.getLink())) {
				syndEntry.setLink(activityFeedEntry.getLink());
			}

			syndEntry.setPublishedDate(new Date(activity.getCreateDate()));
			syndEntry.setTitle(
				HtmlUtil.extractText(activityFeedEntry.getTitle()));
			syndEntry.setUri(syndEntry.getLink());

			syndEntries.add(syndEntry);
		}

		syndFeed.setFeedType(RSSUtil.getFeedType(format, version));

		List<SyndLink> syndLinks = new ArrayList<SyndLink>();

		syndFeed.setLinks(syndLinks);

		SyndLink selfSyndLink = new SyndLinkImpl();

		syndLinks.add(selfSyndLink);

		String link =
			PortalUtil.getLayoutFullURL(themeDisplay) +
				Portal.FRIENDLY_URL_SEPARATOR + "activities/rss";

		selfSyndLink.setHref(link);

		selfSyndLink.setRel("self");

		SyndLink alternateSyndLink = new SyndLinkImpl();

		syndLinks.add(alternateSyndLink);

		alternateSyndLink.setHref(PortalUtil.getLayoutFullURL(themeDisplay));
		alternateSyndLink.setRel("alternate");

		syndFeed.setPublishedDate(new Date());
		syndFeed.setTitle(title);
		syndFeed.setUri(link);

		return RSSUtil.export(syndFeed);
	}

}