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

package com.liferay.portlet.activities.action;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.Portal;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.social.model.SocialActivity;
import com.liferay.portlet.social.model.SocialActivityFeedEntry;
import com.liferay.portlet.social.service.SocialActivityInterpreterLocalServiceUtil;
import com.liferay.portlet.social.service.SocialActivityLocalServiceUtil;
import com.liferay.util.RSSUtil;

import com.sun.syndication.feed.synd.SyndContent;
import com.sun.syndication.feed.synd.SyndContentImpl;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndEntryImpl;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.feed.synd.SyndFeedImpl;
import com.sun.syndication.feed.synd.SyndLink;
import com.sun.syndication.feed.synd.SyndLinkImpl;
import com.sun.syndication.io.FeedException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.portlet.PortletRequest;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

/**
 * @author Brian Wing Shun Chan
 * @author Vilmos Papp
 */
public class RSSAction extends com.liferay.portal.struts.RSSAction {

	protected List<SocialActivity> getActivities(PortletRequest portletRequest)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		Group group = GroupLocalServiceUtil.getGroup(
			themeDisplay.getScopeGroupId());

		int start = 0;
		int end = 10;

		if (group.isOrganization()) {
			return SocialActivityLocalServiceUtil.getOrganizationActivities(
				group.getOrganizationId(), start, end);
		}
		else if (group.isRegularSite()) {
			return SocialActivityLocalServiceUtil.getGroupActivities(
				group.getGroupId(), start, end);
		}
		else if (group.isUser()) {
			return SocialActivityLocalServiceUtil.getUserActivities(
				group.getClassPK(), start, end);
		}

		return Collections.emptyList();
	}

	@Override
	protected byte[] getRSS(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)resourceRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		SyndFeed syndFeed = new SyndFeedImpl();

		String description = ParamUtil.getString(resourceRequest, "feedTitle");

		syndFeed.setDescription(description);

		List<SyndEntry> syndEntries = new ArrayList<SyndEntry>();

		syndFeed.setEntries(syndEntries);

		List<SocialActivity> activities = getActivities(resourceRequest);

		for (SocialActivity activity : activities) {
			SocialActivityFeedEntry activityFeedEntry =
				SocialActivityInterpreterLocalServiceUtil.interpret(
					activity, themeDisplay);

			if (activityFeedEntry == null) {
				continue;
			}

			SyndEntry syndEntry = new SyndEntryImpl();

			SyndContent syndContent = new SyndContentImpl();

			syndContent.setType(RSSUtil.FEED_TYPE_DEFAULT);
			syndContent.setValue(activityFeedEntry.getBody());

			syndEntry.setDescription(syndContent);

			if (Validator.isNotNull(activityFeedEntry.getLink())) {
				syndEntry.setLink(activityFeedEntry.getLink());
			}

			syndEntry.setPublishedDate(new Date(activity.getCreateDate()));
			syndEntry.setTitle(
				HtmlUtil.extractText(activityFeedEntry.getTitle()));

			syndEntries.add(syndEntry);
		}

		syndFeed.setFeedType(RSSUtil.FEED_TYPE_DEFAULT);

		String link =
			PortalUtil.getLayoutFullURL(themeDisplay) +
				Portal.FRIENDLY_URL_SEPARATOR + "activities/rss";

		List<SyndLink> syndLinks = new ArrayList<SyndLink>();

		syndFeed.setLinks(syndLinks);

		SyndLink syndLinkSelf = new SyndLinkImpl();

		syndLinks.add(syndLinkSelf);

		syndLinkSelf.setHref(link);
		syndLinkSelf.setRel("self");

		SyndLink syndLinkAlternate = new SyndLinkImpl();

		syndLinks.add(syndLinkAlternate);

		syndLinkAlternate.setHref(PortalUtil.getLayoutFullURL(themeDisplay));
		syndLinkAlternate.setRel("alternate");

		syndFeed.setPublishedDate(new Date());
		syndFeed.setTitle(description);
		syndFeed.setUri(link);

		String rss = StringPool.BLANK;

		try {
			rss = RSSUtil.export(syndFeed);
		}
		catch (FeedException fe) {
			throw new SystemException(fe);
		}

		return rss.getBytes(StringPool.UTF8);
	}

}