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

package com.liferay.journal.web.util;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.PortletRequestModel;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.Node;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.kernel.xml.XPath;
import com.liferay.portal.model.Image;
import com.liferay.portal.model.Layout;
import com.liferay.portal.service.ImageLocalServiceUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.PortletURLImpl;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.service.DLAppLocalServiceUtil;
import com.liferay.portlet.documentlibrary.util.ImageProcessorUtil;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.model.JournalArticleConstants;
import com.liferay.portlet.journal.model.JournalArticleDisplay;
import com.liferay.portlet.journal.model.JournalFeed;
import com.liferay.portlet.journal.model.JournalFeedConstants;
import com.liferay.portlet.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.portlet.journal.service.JournalContentSearchLocalServiceUtil;
import com.liferay.portlet.journal.service.JournalFeedLocalServiceUtil;
import com.liferay.portlet.journal.util.JournalContentUtil;
import com.liferay.portlet.journal.util.comparator.ArticleDisplayDateComparator;
import com.liferay.portlet.journal.util.comparator.ArticleModifiedDateComparator;
import com.liferay.util.RSSUtil;

import com.sun.syndication.feed.synd.SyndContent;
import com.sun.syndication.feed.synd.SyndContentImpl;
import com.sun.syndication.feed.synd.SyndEnclosure;
import com.sun.syndication.feed.synd.SyndEnclosureImpl;
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
import java.util.Map;
import java.util.Set;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.portlet.ResourceURL;

/**
 * @author Raymond Augé
 */
public class JournalRSSUtil {

	public static List<JournalArticle> getArticles(JournalFeed feed) {
		long companyId = feed.getCompanyId();
		long groupId = feed.getGroupId();
		List<Long> folderIds = Collections.emptyList();
		String articleId = null;
		Double version = null;
		String title = null;
		String description = null;
		String content = null;

		String ddmStructureKey = feed.getDDMStructureKey();

		if (Validator.isNull(ddmStructureKey)) {
			ddmStructureKey = null;
		}

		String ddmTemplateKey = feed.getDDMTemplateKey();

		if (Validator.isNull(ddmTemplateKey)) {
			ddmTemplateKey = null;
		}

		Date displayDateGT = null;
		Date displayDateLT = new Date();
		int status = WorkflowConstants.STATUS_APPROVED;
		Date reviewDate = null;
		boolean andOperator = true;
		int start = 0;
		int end = feed.getDelta();

		String orderByCol = feed.getOrderByCol();
		String orderByType = feed.getOrderByType();
		boolean orderByAsc = orderByType.equals("asc");

		OrderByComparator<JournalArticle> obc =
			new ArticleModifiedDateComparator(orderByAsc);

		if (orderByCol.equals("display-date")) {
			obc = new ArticleDisplayDateComparator(orderByAsc);
		}

		return JournalArticleLocalServiceUtil.search(
			companyId, groupId, folderIds,
			JournalArticleConstants.CLASSNAME_ID_DEFAULT, articleId, version,
			title, description, content, ddmStructureKey, ddmTemplateKey,
			displayDateGT, displayDateLT, status, reviewDate, andOperator,
			start, end, obc);
	}

	public static List<SyndEnclosure> getDLEnclosures(
		String portalURL, String url) {

		List<SyndEnclosure> syndEnclosures = new ArrayList<>();

		FileEntry fileEntry = getFileEntry(url);

		if (fileEntry == null) {
			return syndEnclosures;
		}

		SyndEnclosure syndEnclosure = new SyndEnclosureImpl();

		syndEnclosure.setLength(fileEntry.getSize());
		syndEnclosure.setType(fileEntry.getMimeType());
		syndEnclosure.setUrl(portalURL + url);

		syndEnclosures.add(syndEnclosure);

		return syndEnclosures;
	}

	public static List<SyndLink> getDLLinks(String portalURL, String url) {
		List<SyndLink> syndLinks = new ArrayList<>();

		FileEntry fileEntry = getFileEntry(url);

		if (fileEntry == null) {
			return syndLinks;
		}

		SyndLink syndLink = new SyndLinkImpl();

		syndLink.setHref(portalURL + url);
		syndLink.setLength(fileEntry.getSize());
		syndLink.setRel("enclosure");
		syndLink.setType(fileEntry.getMimeType());

		syndLinks.add(syndLink);

		return syndLinks;
	}

	public static FileEntry getFileEntry(String url) {
		FileEntry fileEntry = null;

		String queryString = HttpUtil.getQueryString(url);

		Map<String, String[]> parameters = HttpUtil.parameterMapFromString(
			queryString);

		if (url.startsWith("/documents/")) {
			String[] pathArray = StringUtil.split(url, CharPool.SLASH);

			String uuid = null;
			long groupId = GetterUtil.getLong(pathArray[2]);
			long folderId = DLFolderConstants.DEFAULT_PARENT_FOLDER_ID;
			String title = null;

			if (pathArray.length == 4) {
				uuid = pathArray[3];
			}
			else if (pathArray.length == 5) {
				folderId = GetterUtil.getLong(pathArray[3]);
				title = HttpUtil.decodeURL(pathArray[4]);
			}
			else if (pathArray.length > 5) {
				uuid = pathArray[5];
			}

			try {
				if (Validator.isNotNull(uuid)) {
					fileEntry =
						DLAppLocalServiceUtil.getFileEntryByUuidAndGroupId(
							uuid, groupId);
				}
				else {
					fileEntry = DLAppLocalServiceUtil.getFileEntry(
						groupId, folderId, title);
				}
			}
			catch (Exception e) {
				if (_log.isWarnEnabled()) {
					_log.warn(e, e);
				}
			}
		}
		else if (parameters.containsKey("folderId") &&
				 parameters.containsKey("name")) {

			try {
				long fileEntryId = GetterUtil.getLong(
					parameters.get("fileEntryId")[0]);

				fileEntry = DLAppLocalServiceUtil.getFileEntry(fileEntryId);
			}
			catch (Exception e) {
				if (_log.isWarnEnabled()) {
					_log.warn(e, e);
				}
			}
		}
		else if (parameters.containsKey("uuid") &&
				 parameters.containsKey("groupId")) {

			try {
				String uuid = parameters.get("uuid")[0];
				long groupId = GetterUtil.getLong(parameters.get("groupId")[0]);

				fileEntry = DLAppLocalServiceUtil.getFileEntryByUuidAndGroupId(
					uuid, groupId);
			}
			catch (Exception e) {
				if (_log.isWarnEnabled()) {
					_log.warn(e, e);
				}
			}
		}

		return fileEntry;
	}

	public static List<SyndEnclosure> getIGEnclosures(
		String portalURL, String url) {

		List<SyndEnclosure> syndEnclosures = new ArrayList<>();

		Object[] imageProperties = getImageProperties(url);

		if (imageProperties == null) {
			return syndEnclosures;
		}

		SyndEnclosure syndEnclosure = new SyndEnclosureImpl();

		syndEnclosure.setLength((Long)imageProperties[1]);
		syndEnclosure.setType(
			MimeTypesUtil.getExtensionContentType(
				imageProperties[0].toString()));
		syndEnclosure.setUrl(portalURL + url);

		syndEnclosures.add(syndEnclosure);

		return syndEnclosures;
	}

	public static List<SyndLink> getIGLinks(String portalURL, String url) {
		List<SyndLink> syndLinks = new ArrayList<>();

		Object[] imageProperties = getImageProperties(url);

		if (imageProperties == null) {
			return syndLinks;
		}

		SyndLink syndLink = new SyndLinkImpl();

		syndLink.setHref(portalURL + url);
		syndLink.setLength((Long)imageProperties[1]);
		syndLink.setRel("enclosure");
		syndLink.setType(
			MimeTypesUtil.getExtensionContentType(
				imageProperties[0].toString()));

		syndLinks.add(syndLink);

		return syndLinks;
	}

	public static Image getImage(String url) {
		Image image = null;

		String queryString = HttpUtil.getQueryString(url);

		Map<String, String[]> parameters = HttpUtil.parameterMapFromString(
			queryString);

		if (parameters.containsKey("image_id") ||
			parameters.containsKey("img_id") ||
			parameters.containsKey("i_id")) {

			try {
				long imageId = 0;

				if (parameters.containsKey("image_id")) {
					imageId = GetterUtil.getLong(parameters.get("image_id")[0]);
				}
				else if (parameters.containsKey("img_id")) {
					imageId = GetterUtil.getLong(parameters.get("img_id")[0]);
				}
				else if (parameters.containsKey("i_id")) {
					imageId = GetterUtil.getLong(parameters.get("i_id")[0]);
				}

				image = ImageLocalServiceUtil.getImage(imageId);
			}
			catch (Exception e) {
				if (_log.isWarnEnabled()) {
					_log.warn(e, e);
				}
			}
		}

		return image;
	}

	public static byte[] getRSS(
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

		Layout layout = null;

		if (plid > 0) {
			layout = LayoutLocalServiceUtil.fetchLayout(plid);
		}

		if (layout == null) {
			layout = themeDisplay.getLayout();
		}

		String rss = exportToRSS(
			resourceRequest, resourceResponse, feed, languageId, layout,
			themeDisplay);

		return rss.getBytes(StringPool.UTF8);
	}

	protected static String exportToRSS(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse,
			JournalFeed feed, String languageId, Layout layout,
			ThemeDisplay themeDisplay)
		throws Exception {

		SyndFeed syndFeed = new SyndFeedImpl();

		syndFeed.setDescription(feed.getDescription());

		List<SyndEntry> syndEntries = new ArrayList<>();

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

		List<SyndLink> syndLinks = new ArrayList<>();

		syndFeed.setLinks(syndLinks);

		SyndLink selfSyndLink = new SyndLinkImpl();

		syndLinks.add(selfSyndLink);

		ResourceURL feedURL = resourceResponse.createResourceURL();

		feedURL.setCacheability(ResourceURL.FULL);
		feedURL.setParameter("p_p_resource_id", "rss");
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

	protected static String getEntryURL(
			ResourceRequest resourceRequest, JournalFeed feed,
			JournalArticle article, Layout layout, ThemeDisplay themeDisplay)
		throws Exception {

		List<Long> hitLayoutIds =
			JournalContentSearchLocalServiceUtil.getLayoutIds(
				layout.getGroupId(), article.getArticleId());

		if (!hitLayoutIds.isEmpty()) {
			Long hitLayoutId = hitLayoutIds.get(0);

			Layout hitLayout = LayoutLocalServiceUtil.getLayout(
				layout.getGroupId(), hitLayoutId.longValue());

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

	protected static Object[] getImageProperties(String url) {
		String type = null;
		long size = 0;

		Image image = getImage(url);

		if (image != null) {
			type = image.getType();
			size = image.getSize();
		}
		else {
			FileEntry fileEntry = getFileEntry(url);

			Set<String> imageMimeTypes = ImageProcessorUtil.getImageMimeTypes();

			if ((fileEntry != null) &&
				imageMimeTypes.contains(fileEntry.getMimeType())) {

				type = fileEntry.getExtension();
				size = fileEntry.getSize();
			}
		}

		if (Validator.isNotNull(type)) {
			return new Object[] {type, size};
		}

		return null;
	}

	protected static String processContent(
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

	protected static String processURL(
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

	private static final Log _log = LogFactoryUtil.getLog(JournalRSSUtil.class);

}