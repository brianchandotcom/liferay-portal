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

	@Override
	protected byte[] getRSS(HttpServletRequest request) throws Exception {
		RSSRenderer rssRenderer = getRSSRenderer(request);

		String rss = exportToRSS(rssRenderer);

		return rss.getBytes(StringPool.UTF8);
	}

	@Override
	protected byte[] getRSS(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		RSSRenderer rssRenderer = getRSSRenderer(
			resourceRequest, resourceResponse);

		String rss = exportToRSS(rssRenderer);

		return rss.getBytes(StringPool.UTF8);
	}

	protected RSSRenderer getRSSRenderer(HttpServletRequest request)
		throws Exception {
			throw new UnsupportedOperationException();
	}

	protected RSSRenderer getRSSRenderer(
			ResourceRequest portletRequest, ResourceResponse portletResponse)
		throws Exception {

		return getRSSRenderer(PortalUtil.getHttpServletRequest(portletRequest));
	}

	private String exportToRSS(RSSRenderer rssRenderer) throws Exception {
		SyndFeed syndFeed = new SyndFeedImpl();

		syndFeed.setDescription(rssRenderer.getRSSDescription());

		List<SyndEntry> syndEntries = new ArrayList<SyndEntry>();

		syndFeed.setEntries(syndEntries);

		rssRenderer.populateFeedEntries(syndEntries);

		syndFeed.setFeedType(rssRenderer.getRSSFeedType());

		List<SyndLink> syndLinks = new ArrayList<SyndLink>();

		syndFeed.setLinks(syndLinks);

		SyndLink selfSyndLink = new SyndLinkImpl();

		syndLinks.add(selfSyndLink);

		String feedURL = rssRenderer.getFeedURL();

		selfSyndLink.setHref(feedURL);

		selfSyndLink.setRel("self");

		String alternateURL = rssRenderer.getAlternateURL();

		if (alternateURL != null) {
			SyndLink alternateSyndLink = new SyndLinkImpl();

			syndLinks.add(alternateSyndLink);

			alternateSyndLink.setHref(alternateURL);
			alternateSyndLink.setRel("alternate");
		}

		syndFeed.setPublishedDate(rssRenderer.getPublicationDate());
		syndFeed.setTitle(rssRenderer.getRSSName());
		syndFeed.setUri(feedURL);

		try {
			return RSSUtil.export(syndFeed);
		}
		catch (FeedException fe) {
			throw new SystemException(fe);
		}
	}

}