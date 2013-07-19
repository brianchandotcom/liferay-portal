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

package com.liferay.portlet.journal.action;

import com.liferay.portal.NoSuchLayoutException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.Node;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.kernel.xml.XPath;
import com.liferay.portal.model.Layout;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.PortletURLImpl;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.model.JournalArticleDisplay;
import com.liferay.portlet.journal.model.JournalFeed;
import com.liferay.portlet.journal.model.JournalFeedConstants;
import com.liferay.portlet.journal.service.JournalContentSearchLocalServiceUtil;
import com.liferay.portlet.journal.service.JournalFeedLocalServiceUtil;
import com.liferay.portlet.journal.util.JournalRSSUtil;
import com.liferay.portlet.journalcontent.util.JournalContentUtil;
import com.liferay.util.RSSUtil;

import com.sun.syndication.feed.synd.SyndContent;
import com.sun.syndication.feed.synd.SyndContentImpl;
import com.sun.syndication.feed.synd.SyndEnclosure;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndEntryImpl;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.feed.synd.SyndFeedImpl;
import com.sun.syndication.feed.synd.SyndLink;
import com.sun.syndication.feed.synd.SyndLinkImpl;
import com.sun.syndication.io.FeedException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.portlet.ResourceURL;

/**
 * @author Raymond Augé
 */
public class RSSAction extends com.liferay.portal.struts.RSSAction {

	protected String exportToRSS(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse,
			JournalFeed feed, String languageId, Layout layout,
			ThemeDisplay themeDisplay)
		throws Exception {

		SyndFeed syndFeed = new SyndFeedImpl();

		syndFeed.setDescription(feed.getDescription());

		List<SyndEntry> syndEntries = new ArrayList<SyndEntry>();

		syndFeed.setEntries(syndEntries);

		List<JournalArticle> articles = JournalRSSUtil.getArticles(feed);

		syndFeed.setFeedType(
			feed.getFeedFormat() + "_" + feed.getFeedVersion());

		List<SyndLink> syndLinks = new ArrayList<SyndLink>();

		syndFeed.setLinks(syndLinks);

		SyndLink selfSyndLink = new SyndLinkImpl();

		syndLinks.add(selfSyndLink);

		ResourceURL feedURL = resourceResponse.createResourceURL();

		feedURL.setCacheability(ResourceURL.FULL);
		feedURL.setParameter("struts_action", "/journal/rss");
		feedURL.setParameter("groupId", String.valueOf(feed.getGroupId()));
		feedURL.setParameter("feedId", String.valueOf(feed.getFeedId()));

		String link = feedURL.toString();

		selfSyndLink.setHref(link);

		selfSyndLink.setRel("self");

		syndFeed.setTitle(feed.getName());
		syndFeed.setPublishedDate(new Date());
		syndFeed.setUri(feedURL.toString());

		try {
			return RSSUtil.export(syndFeed);
		}
		catch (FeedException fe) {
			throw new SystemException(fe);
		}
	}

	@Override
	protected byte[] getRSS(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)resourceRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		JournalFeed feed = null;

		long id = ParamUtil.getLong(resourceRequest, "id");

		long groupId = ParamUtil.getLong(resourceRequest, "groupId");
		String feedId = ParamUtil.getString(resourceRequest, "feedId");

		if (id > 0) {
			feed = JournalFeedLocalServiceUtil.getFeed(id);
		}
		else {
			feed = JournalFeedLocalServiceUtil.getFeed(groupId, feedId);
		}

		String languageId = LanguageUtil.getLanguageId(resourceRequest);

		long plid = PortalUtil.getPlidFromFriendlyURL(
			themeDisplay.getCompanyId(), feed.getTargetLayoutFriendlyUrl());

		Layout layout = themeDisplay.getLayout();

		if (plid > 0) {
			try {
				layout = LayoutLocalServiceUtil.getLayout(plid);
			}
			catch (NoSuchLayoutException nsle) {
			}
		}

		String rss = exportToRSS(
			resourceRequest, resourceResponse, feed, languageId, layout,
			themeDisplay);

		return rss.getBytes(StringPool.UTF8);
	}

	private static Log _log = LogFactoryUtil.getLog(RSSAction.class);

}