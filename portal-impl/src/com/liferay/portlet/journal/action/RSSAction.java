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

package com.liferay.portlet.journal.action;

import com.liferay.portal.NoSuchLayoutException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.PortletRequestModel;
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
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.PortletURLImpl;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.model.JournalArticleDisplay;
import com.liferay.portlet.journal.model.JournalFeed;
import com.liferay.portlet.journal.model.JournalFeedConstants;
import com.liferay.portlet.journal.service.JournalContentSearchLocalServiceUtil;
import com.liferay.portlet.journal.service.JournalFeedLocalServiceUtil;
import com.liferay.portlet.journal.util.JournalContentUtil;
import com.liferay.portlet.journal.util.JournalRSSUtil;
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

		if (_log.isDebugEnabled()) {
			_log.debug("Syndicating " + articles.size() + " articles");
		}

		for (JournalArticle article : articles) {
			SyndEntry syndEntry = new SyndEntryImpl();

			String author = PortalUtil.getUserName(article);

			syndEntry.setAuthor(author);

			SyndContent syndContent = new SyndContentImpl();

			syndContent.setType(RSSUtil.ENTRY_TYPE_DEFAULT);

			String value = article.getDescription(languageId);

			try {
				value = processContent(
					feed, article, languageId, themeDisplay, syndEntry,
					syndContent);
			}
			catch (Exception e) {
				if (_log.isWarnEnabled()) {
					_log.warn(e, e);
				}
			}

			syndContent.setValue(value);

			syndEntry.setDescription(syndContent);

			String link = getEntryURL(
				resourceRequest, feed, article, layout, themeDisplay);

			syndEntry.setLink(link);

			syndEntry.setPublishedDate(article.getDisplayDate());
			syndEntry.setTitle(article.getTitle(languageId));
			syndEntry.setUpdatedDate(article.getModifiedDate());
			syndEntry.setUri(link);

			syndEntries.add(syndEntry);
		}

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

	protected String getEntryURL(
			ResourceRequest resourceRequest, JournalFeed feed,
			JournalArticle article, Layout layout, ThemeDisplay themeDisplay)
		throws Exception {

		List<Long> hitLayoutIds =
			JournalContentSearchLocalServiceUtil.getLayoutIds(
				layout.getGroupId(), layout.isPrivateLayout(),
				article.getArticleId());

		if (!hitLayoutIds.isEmpty()) {
			Long hitLayoutId = hitLayoutIds.get(0);

			Layout hitLayout = LayoutLocalServiceUtil.getLayout(
				layout.getGroupId(), layout.isPrivateLayout(),
				hitLayoutId.longValue());

			return PortalUtil.getLayoutFriendlyURL(hitLayout, themeDisplay);
		}

		String portletId = feed.getTargetPortletId();

		if (Validator.isNull(portletId)) {
			return StringPool.BLANK;
		}

		long plid = PortalUtil.getPlidFromFriendlyURL(
			feed.getCompanyId(), feed.getTargetLayoutFriendlyUrl());

		PortletURL entryURL = new PortletURLImpl(
			resourceRequest, portletId, plid, PortletRequest.RENDER_PHASE);

		entryURL.setParameter("groupId", String.valueOf(article.getGroupId()));
		entryURL.setParameter("articleId", article.getArticleId());

		return entryURL.toString();
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

	protected String processContent(
			JournalFeed feed, JournalArticle article, String languageId,
			ThemeDisplay themeDisplay, SyndEntry syndEntry,
			SyndContent syndContent)
		throws Exception {

		String content = article.getDescription(languageId);

		String contentField = feed.getContentField();

		if (contentField.equals(JournalFeedConstants.RENDERED_WEB_CONTENT)) {
			String ddmRendererTemplateKey = article.getDDMTemplateKey();

			if (Validator.isNotNull(feed.getDDMRendererTemplateKey())) {
				ddmRendererTemplateKey = feed.getDDMRendererTemplateKey();
			}

			JournalArticleDisplay articleDisplay =
				JournalContentUtil.getDisplay(
					feed.getGroupId(), article.getArticleId(),
					ddmRendererTemplateKey, null, languageId, 1,
					new PortletRequestModel() {

						@Override
						public String toXML() {
							return _XML_REQUUEST;
						}

					},
					themeDisplay);

			if (articleDisplay != null) {
				content = articleDisplay.getContent();
			}
		}
		else if (!contentField.equals(
					JournalFeedConstants.WEB_CONTENT_DESCRIPTION)) {

			Document document = SAXReaderUtil.read(
				article.getContentByLocale(languageId));

			contentField = HtmlUtil.escapeXPathAttribute(contentField);

			XPath xPathSelector = SAXReaderUtil.createXPath(
				"//dynamic-element[@name=" + contentField + "]");

			List<Node> results = xPathSelector.selectNodes(document);

			if (results.isEmpty()) {
				return content;
			}

			Element element = (Element)results.get(0);

			String elType = element.attributeValue("type");

			if (elType.equals("document_library")) {
				String url = element.elementText("dynamic-content");

				url = processURL(feed, url, themeDisplay, syndEntry);
			}
			else if (elType.equals("image") || elType.equals("image_gallery")) {
				String url = element.elementText("dynamic-content");

				url = processURL(feed, url, themeDisplay, syndEntry);

				content =
					content + "<br /><br /><img alt='' src='" +
						themeDisplay.getURLPortal() + url + "' />";
			}
			else if (elType.equals("text_box")) {
				syndContent.setType("text");

				content = element.elementText("dynamic-content");
			}
			else {
				content = element.elementText("dynamic-content");
			}
		}

		return content;
	}

	protected String processURL(
		JournalFeed feed, String url, ThemeDisplay themeDisplay,
		SyndEntry syndEntry) {

		url = StringUtil.replace(
			url,
			new String[] {
				"@group_id@", "@image_path@", "@main_path@"
			},
			new String[] {
				String.valueOf(feed.getGroupId()), themeDisplay.getPathImage(),
				themeDisplay.getPathMain()
			}
		);

		List<SyndEnclosure> syndEnclosures = JournalRSSUtil.getDLEnclosures(
			themeDisplay.getURLPortal(), url);

		syndEnclosures.addAll(
			JournalRSSUtil.getIGEnclosures(themeDisplay.getURLPortal(), url));

		syndEntry.setEnclosures(syndEnclosures);

		List<SyndLink> syndLinks = JournalRSSUtil.getDLLinks(
			themeDisplay.getURLPortal(), url);

		syndLinks.addAll(
			JournalRSSUtil.getIGLinks(themeDisplay.getURLPortal(), url));

		syndEntry.setLinks(syndLinks);

		return url;
	}

	private static final String _XML_REQUUEST =
		"<request><parameters><parameter><name>rss</name><value>true</value>" +
			"</parameter></parameters></request>";

	private static final Log _log = LogFactoryUtil.getLog(RSSAction.class);

}