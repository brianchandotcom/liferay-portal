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

package com.liferay.social.activities.web.portlet.action;

import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.PortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.ActionCommand;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.social.model.SocialActivity;
import com.liferay.portlet.social.model.SocialActivityFeedEntry;
import com.liferay.portlet.social.service.SocialActivityInterpreterLocalServiceUtil;
import com.liferay.portlet.social.service.SocialActivityLocalServiceUtil;
import com.liferay.social.activities.web.constants.SocialActivitiesPortletKeys;
import com.liferay.util.RSSUtil;

import com.sun.syndication.feed.synd.SyndContent;
import com.sun.syndication.feed.synd.SyndContentImpl;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndEntryImpl;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.feed.synd.SyndFeedImpl;
import com.sun.syndication.feed.synd.SyndLink;
import com.sun.syndication.feed.synd.SyndLinkImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.MimeResponse;
import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.ResourceURL;

import org.osgi.service.component.annotations.Component;

/**
 * @author Brian Wing Shun Chan
 * @author Vilmos Papp
 * @author Eduardo Garcia
 * @author Raymond Augé
 */
@Component(
	immediate = true,
	property = {
		"action.command.name=rss",
		"javax.portlet.name=" + SocialActivitiesPortletKeys.SOCIAL_ACTIVITIES
	},
	service = ActionCommand.class
)
public class RSSAction implements ActionCommand {

	@Override
	public boolean processCommand(
			PortletRequest portletRequest, PortletResponse portletResponse)
		throws PortletException {

		if (!(portletResponse instanceof MimeResponse)) {
			return false;
		}

		MimeResponse mimeResponse = (MimeResponse)portletResponse;

		try {
			PortletResponseUtil.sendFile(
				portletRequest, mimeResponse, null,
				getRSS(portletRequest, portletResponse),
				ContentTypes.TEXT_XML_UTF8);
		}
		catch (Exception e) {
			throw new PortletException(e);
		}

		return true;
	}

	protected String exportToRSS(
			PortletRequest portletRequest, PortletResponse portletResponse,
			String title, String description, String format, double version,
			String displayStyle, List<SocialActivity> socialActivities,
			ServiceContext serviceContext)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		SyndFeed syndFeed = new SyndFeedImpl();

		syndFeed.setDescription(GetterUtil.getString(description, title));

		List<SyndEntry> syndEntries = new ArrayList<>();

		syndFeed.setEntries(syndEntries);

		for (SocialActivity socialActivity : socialActivities) {
			SocialActivityFeedEntry socialActivityFeedEntry =
				SocialActivityInterpreterLocalServiceUtil.interpret(
					StringPool.BLANK, socialActivity, serviceContext);

			if (socialActivityFeedEntry == null) {
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
				value = socialActivityFeedEntry.getBody();
			}

			syndContent.setValue(value);

			syndEntry.setDescription(syndContent);

			if (Validator.isNotNull(socialActivityFeedEntry.getLink())) {
				syndEntry.setLink(socialActivityFeedEntry.getLink());
			}

			syndEntry.setPublishedDate(
				new Date(socialActivity.getCreateDate()));
			syndEntry.setTitle(
				HtmlUtil.extractText(socialActivityFeedEntry.getTitle()));
			syndEntry.setUri(syndEntry.getLink());

			syndEntries.add(syndEntry);
		}

		syndFeed.setFeedType(RSSUtil.getFeedType(format, version));

		List<SyndLink> syndLinks = new ArrayList<>();

		syndFeed.setLinks(syndLinks);

		SyndLink selfSyndLink = new SyndLinkImpl();

		syndLinks.add(selfSyndLink);

		LiferayPortletResponse liferayPortletResponse =
			(LiferayPortletResponse)portletResponse;

		ResourceURL rssURL = liferayPortletResponse.createResourceURL();

		rssURL.setParameter(ActionRequest.ACTION_NAME, "rss");
		rssURL.setParameter("feedTitle", title);

		selfSyndLink.setHref(rssURL.toString());

		selfSyndLink.setRel("self");

		SyndLink alternateSyndLink = new SyndLinkImpl();

		syndLinks.add(alternateSyndLink);

		alternateSyndLink.setHref(PortalUtil.getLayoutFullURL(themeDisplay));
		alternateSyndLink.setRel("alternate");

		syndFeed.setPublishedDate(new Date());
		syndFeed.setTitle(title);
		syndFeed.setUri(rssURL.toString());

		return RSSUtil.export(syndFeed);
	}

	protected byte[] getRSS(
			PortletRequest portletRequest, PortletResponse portletResponse)
		throws Exception {

		String feedTitle = ParamUtil.getString(portletRequest, "feedTitle");
		String format = ParamUtil.getString(
			portletRequest, "type", RSSUtil.FORMAT_DEFAULT);
		double version = ParamUtil.getDouble(
			portletRequest, "version", RSSUtil.VERSION_DEFAULT);
		String displayStyle = ParamUtil.getString(
			portletRequest, "displayStyle", RSSUtil.DISPLAY_STYLE_DEFAULT);
		int max = ParamUtil.getInteger(
			portletRequest, "max", SearchContainer.DEFAULT_DELTA);

		List<SocialActivity> socialActivities = getSocialActivities(
			portletRequest, max);

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			portletRequest);

		String rss = exportToRSS(
			portletRequest, portletResponse, feedTitle, null, format, version,
			displayStyle, socialActivities, serviceContext);

		return rss.getBytes(StringPool.UTF8);
	}

	protected List<SocialActivity> getSocialActivities(
			PortletRequest portletRequest, int max)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		Group group = GroupLocalServiceUtil.getGroup(
			themeDisplay.getScopeGroupId());

		int start = 0;

		if (group.isOrganization()) {
			return SocialActivityLocalServiceUtil.getOrganizationActivities(
				group.getOrganizationId(), start, max);
		}
		else if (group.isRegularSite()) {
			return SocialActivityLocalServiceUtil.getGroupActivities(
				group.getGroupId(), start, max);
		}
		else if (group.isUser()) {
			return SocialActivityLocalServiceUtil.getUserActivities(
				group.getClassPK(), start, max);
		}

		return Collections.emptyList();
	}

}