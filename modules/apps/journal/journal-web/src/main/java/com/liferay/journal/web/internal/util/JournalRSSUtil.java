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

package com.liferay.journal.web.internal.util;

import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.document.library.kernel.util.ImageProcessorUtil;
import com.liferay.journal.exception.NoSuchFeedException;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalArticleConstants;
import com.liferay.journal.model.JournalArticleDisplay;
import com.liferay.journal.model.JournalFeed;
import com.liferay.journal.model.JournalFeedConstants;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.journal.service.JournalContentSearchLocalService;
import com.liferay.journal.service.JournalFeedLocalService;
import com.liferay.journal.util.JournalContent;
import com.liferay.journal.util.comparator.ArticleDisplayDateComparator;
import com.liferay.journal.util.comparator.ArticleModifiedDateComparator;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Image;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.portlet.PortletRequestModel;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.ImageLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.Node;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.kernel.xml.XPath;
import com.liferay.rss.export.RSSExporter;
import com.liferay.rss.model.SyndContent;
import com.liferay.rss.model.SyndEnclosure;
import com.liferay.rss.model.SyndEntry;
import com.liferay.rss.model.SyndFeed;
import com.liferay.rss.model.SyndLink;
import com.liferay.rss.model.SyndModelFactory;
import com.liferay.rss.util.RSSUtil;

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

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Raymond Augé
 */
@Component(service = JournalRSSUtil.class)
public class JournalRSSUtil {

	public List<JournalArticle> getArticles(JournalFeed feed) {
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

		return _journalArticleLocalService.search(
			companyId, groupId, folderIds,
			JournalArticleConstants.CLASSNAME_ID_DEFAULT, articleId, version,
			title, description, content, ddmStructureKey, ddmTemplateKey,
			displayDateGT, displayDateLT, status, reviewDate, andOperator,
			start, end, obc);
	}

	public List<SyndEnclosure> getDLEnclosures(String portalURL, String url) {
		List<SyndEnclosure> syndEnclosures = new ArrayList<>();

		FileEntry fileEntry = getFileEntry(url);

		if (fileEntry == null) {
			return syndEnclosures;
		}

		SyndEnclosure syndEnclosure = _syndModelFactory.createSyndEnclosure();

		syndEnclosure.setLength(fileEntry.getSize());
		syndEnclosure.setType(fileEntry.getMimeType());
		syndEnclosure.setUrl(portalURL + url);

		syndEnclosures.add(syndEnclosure);

		return syndEnclosures;
	}

	public List<SyndLink> getDLLinks(String portalURL, String url) {
		List<SyndLink> syndLinks = new ArrayList<>();

		FileEntry fileEntry = getFileEntry(url);

		if (fileEntry == null) {
			return syndLinks;
		}

		SyndLink syndLink = _syndModelFactory.createSyndLink();

		syndLink.setHref(portalURL + url);
		syndLink.setLength(fileEntry.getSize());
		syndLink.setRel("enclosure");
		syndLink.setType(fileEntry.getMimeType());

		syndLinks.add(syndLink);

		return syndLinks;
	}

	public FileEntry getFileEntry(String url) {
		FileEntry fileEntry = null;

		String queryString = _http.getQueryString(url);

		Map<String, String[]> parameters = _http.parameterMapFromString(
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
				title = _http.decodeURL(pathArray[4]);
			}
			else if (pathArray.length > 5) {
				uuid = pathArray[5];
			}

			try {
				if (Validator.isNotNull(uuid)) {
					fileEntry = _dlAppLocalService.getFileEntryByUuidAndGroupId(
						uuid, groupId);
				}
				else {
					fileEntry = _dlAppLocalService.getFileEntry(
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

				fileEntry = _dlAppLocalService.getFileEntry(fileEntryId);
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

				fileEntry = _dlAppLocalService.getFileEntryByUuidAndGroupId(
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

	public List<SyndEnclosure> getIGEnclosures(String portalURL, String url) {
		List<SyndEnclosure> syndEnclosures = new ArrayList<>();

		Object[] imageProperties = getImageProperties(url);

		if (imageProperties == null) {
			return syndEnclosures;
		}

		SyndEnclosure syndEnclosure = _syndModelFactory.createSyndEnclosure();

		syndEnclosure.setLength((Long)imageProperties[1]);
		syndEnclosure.setType(
			MimeTypesUtil.getExtensionContentType(
				imageProperties[0].toString()));
		syndEnclosure.setUrl(portalURL + url);

		syndEnclosures.add(syndEnclosure);

		return syndEnclosures;
	}

	public List<SyndLink> getIGLinks(String portalURL, String url) {
		List<SyndLink> syndLinks = new ArrayList<>();

		Object[] imageProperties = getImageProperties(url);

		if (imageProperties == null) {
			return syndLinks;
		}

		SyndLink syndLink = _syndModelFactory.createSyndLink();

		syndLink.setHref(portalURL + url);
		syndLink.setLength((Long)imageProperties[1]);
		syndLink.setRel("enclosure");
		syndLink.setType(
			MimeTypesUtil.getExtensionContentType(
				imageProperties[0].toString()));

		syndLinks.add(syndLink);

		return syndLinks;
	}

	public Image getImage(String url) {
		Image image = null;

		String queryString = _http.getQueryString(url);

		Map<String, String[]> parameters = _http.parameterMapFromString(
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

				image = _imageLocalService.getImage(imageId);
			}
			catch (Exception e) {
				if (_log.isWarnEnabled()) {
					_log.warn(e, e);
				}
			}
		}

		return image;
	}

	public byte[] getRSS(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)resourceRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		JournalFeed feed = null;

		long id = ParamUtil.getLong(resourceRequest, "id");

		if (id > 0) {
			try {
				feed = _journalFeedLocalService.getFeed(id);
			}
			catch (NoSuchFeedException nsfe) {

				// Backward compatibility with old URLs

				feed = _journalFeedLocalService.getFeed(
					themeDisplay.getScopeGroupId(), String.valueOf(id));
			}
		}
		else {
			long groupId = ParamUtil.getLong(resourceRequest, "groupId");
			String feedId = ParamUtil.getString(resourceRequest, "feedId");

			feed = _journalFeedLocalService.getFeed(groupId, feedId);
		}

		String languageId = LanguageUtil.getLanguageId(resourceRequest);

		long plid = _portal.getPlidFromFriendlyURL(
			themeDisplay.getCompanyId(), feed.getTargetLayoutFriendlyUrl());

		Layout layout = null;

		if (plid > 0) {
			layout = _layoutLocalService.fetchLayout(plid);
		}

		if (layout == null) {
			layout = themeDisplay.getLayout();
		}

		String rss = exportToRSS(
			resourceRequest, resourceResponse, feed, languageId, layout,
			themeDisplay);

		return rss.getBytes(StringPool.UTF8);
	}

	protected String exportToRSS(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse,
			JournalFeed feed, String languageId, Layout layout,
			ThemeDisplay themeDisplay)
		throws Exception {

		SyndFeed syndFeed = _syndModelFactory.createSyndFeed();

		syndFeed.setDescription(feed.getDescription());

		List<SyndEntry> syndEntries = new ArrayList<>();

		syndFeed.setEntries(syndEntries);

		List<JournalArticle> articles = getArticles(feed);

		if (_log.isDebugEnabled()) {
			_log.debug("Syndicating " + articles.size() + " articles");
		}

		for (JournalArticle article : articles) {
			SyndEntry syndEntry = _syndModelFactory.createSyndEntry();

			String author = _portal.getUserName(article);

			syndEntry.setAuthor(author);

			SyndContent syndContent = _syndModelFactory.createSyndContent();

			syndContent.setType(RSSUtil.ENTRY_TYPE_DEFAULT);

			String value = article.getDescription(languageId);

			try {
				value = processContent(
					resourceRequest, resourceResponse, feed, article,
					languageId, themeDisplay, syndEntry, syndContent);
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

		SyndLink selfSyndLink = _syndModelFactory.createSyndLink();

		ResourceURL feedURL = resourceResponse.createResourceURL();

		feedURL.setCacheability(ResourceURL.FULL);
		feedURL.setParameter("groupId", String.valueOf(feed.getGroupId()));
		feedURL.setParameter("feedId", String.valueOf(feed.getFeedId()));
		feedURL.setResourceID("rss");

		selfSyndLink.setHref(feedURL.toString());

		selfSyndLink.setRel("self");

		syndFeed.setLinks(ListUtil.fromArray(selfSyndLink));

		syndFeed.setPublishedDate(new Date());
		syndFeed.setTitle(feed.getName());
		syndFeed.setUri(feedURL.toString());

		return _rssExporter.export(syndFeed);
	}

	protected String getEntryURL(
			ResourceRequest resourceRequest, JournalFeed feed,
			JournalArticle article, Layout layout, ThemeDisplay themeDisplay)
		throws Exception {

		List<Long> hitLayoutIds =
			_journalContentSearchLocalService.getLayoutIds(
				layout.getGroupId(), layout.isPrivateLayout(),
				article.getArticleId());

		if (!hitLayoutIds.isEmpty()) {
			Long hitLayoutId = hitLayoutIds.get(0);

			Layout hitLayout = _layoutLocalService.getLayout(
				layout.getGroupId(), layout.isPrivateLayout(),
				hitLayoutId.longValue());

			return _portal.getLayoutFriendlyURL(hitLayout, themeDisplay);
		}

		String portletId = feed.getTargetPortletId();

		if (Validator.isNull(portletId)) {
			return StringPool.BLANK;
		}

		long plid = _portal.getPlidFromFriendlyURL(
			feed.getCompanyId(), feed.getTargetLayoutFriendlyUrl());

		PortletURL entryURL = PortletURLFactoryUtil.create(
			resourceRequest, portletId, plid, PortletRequest.RENDER_PHASE);

		entryURL.setParameter("groupId", String.valueOf(article.getGroupId()));
		entryURL.setParameter("articleId", article.getArticleId());

		return entryURL.toString();
	}

	protected Object[] getImageProperties(String url) {
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

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link
	 *             #processContent(ResourceRequest, ResourceResponse,
	 *             JournalFeed, JournalArticle, String, ThemeDisplay, SyndEntry,
	 *             SyndContent)}
	 */
	@Deprecated
	protected String processContent(
			JournalFeed feed, JournalArticle article, String languageId,
			ThemeDisplay themeDisplay, SyndEntry syndEntry,
			SyndContent syndContent)
		throws Exception {

		return StringPool.BLANK;
	}

	protected String processContent(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse,
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

			JournalArticleDisplay articleDisplay = _journalContent.getDisplay(
				feed.getGroupId(), article.getArticleId(),
				ddmRendererTemplateKey, null, languageId, 1,
				new PortletRequestModel(resourceRequest, resourceResponse) {

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

				content = StringBundler.concat(
					content, "<br /><br /><img alt='' src='",
					themeDisplay.getURLPortal(), url, "' />");
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
			url, new String[] {"@group_id@", "@image_path@", "@main_path@"},
			new String[] {
				String.valueOf(feed.getGroupId()), themeDisplay.getPathImage(),
				themeDisplay.getPathMain()
			});

		List<SyndEnclosure> syndEnclosures = getDLEnclosures(
			themeDisplay.getURLPortal(), url);

		syndEnclosures.addAll(
			getIGEnclosures(themeDisplay.getURLPortal(), url));

		syndEntry.setEnclosures(syndEnclosures);

		List<SyndLink> syndLinks = getDLLinks(themeDisplay.getURLPortal(), url);

		syndLinks.addAll(getIGLinks(themeDisplay.getURLPortal(), url));

		syndEntry.setLinks(syndLinks);

		return url;
	}

	@Reference(unbind = "-")
	protected void setDLAppLocalService(DLAppLocalService dlAppLocalService) {
		_dlAppLocalService = dlAppLocalService;
	}

	@Reference(unbind = "-")
	protected void setImageLocalService(ImageLocalService imageLocalService) {
		_imageLocalService = imageLocalService;
	}

	@Reference(unbind = "-")
	protected void setJournalArticleLocalService(
		JournalArticleLocalService journalArticleLocalService) {

		_journalArticleLocalService = journalArticleLocalService;
	}

	@Reference(unbind = "-")
	protected void setJournalContent(JournalContent journalContent) {
		_journalContent = journalContent;
	}

	@Reference(unbind = "-")
	protected void setJournalContentSearchLocalService(
		JournalContentSearchLocalService journalContentSearchLocalService) {

		_journalContentSearchLocalService = journalContentSearchLocalService;
	}

	@Reference(unbind = "-")
	protected void setJournalFeedLocalService(
		JournalFeedLocalService journalFeedLocalService) {

		_journalFeedLocalService = journalFeedLocalService;
	}

	@Reference(unbind = "-")
	protected void setLayoutLocalService(
		LayoutLocalService layoutLocalService) {

		_layoutLocalService = layoutLocalService;
	}

	private static final String _XML_REQUUEST =
		"<request><parameters><parameter><name>rss</name><value>true</value>" +
			"</parameter></parameters></request>";

	private static final Log _log = LogFactoryUtil.getLog(JournalRSSUtil.class);

	private DLAppLocalService _dlAppLocalService;

	@Reference
	private Http _http;

	private ImageLocalService _imageLocalService;
	private JournalArticleLocalService _journalArticleLocalService;
	private JournalContent _journalContent;
	private JournalContentSearchLocalService _journalContentSearchLocalService;
	private JournalFeedLocalService _journalFeedLocalService;
	private LayoutLocalService _layoutLocalService;

	@Reference
	private Portal _portal;

	@Reference
	private RSSExporter _rssExporter;

	@Reference
	private SyndModelFactory _syndModelFactory;

}