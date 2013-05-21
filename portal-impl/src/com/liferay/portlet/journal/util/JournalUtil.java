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

package com.liferay.portlet.journal.util;

import com.liferay.portal.kernel.configuration.Filter;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.templateparser.TransformerListener;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.FriendlyURLNormalizerUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.InstanceFactory;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.Tuple;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Attribute;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.Node;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.kernel.xml.XPath;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutSet;
import com.liferay.portal.model.ModelHintsUtil;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ImageLocalServiceUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.templateparser.Transformer;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.WebKeys;
import com.liferay.portal.webserver.WebServerServletTokenUtil;
import com.liferay.portlet.PortletURLFactoryUtil;
import com.liferay.portlet.asset.service.AssetTagLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.model.DDMTemplate;
import com.liferay.portlet.dynamicdatamapping.service.DDMTemplateLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.util.DDMXMLUtil;
import com.liferay.portlet.journal.NoSuchArticleException;
import com.liferay.portlet.journal.StructureXsdException;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.model.JournalFolder;
import com.liferay.portlet.journal.model.JournalFolderConstants;
import com.liferay.portlet.journal.model.JournalStructureConstants;
import com.liferay.portlet.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.portlet.journal.service.JournalFolderLocalServiceUtil;
import com.liferay.portlet.journal.util.comparator.ArticleCreateDateComparator;
import com.liferay.portlet.journal.util.comparator.ArticleDisplayDateComparator;
import com.liferay.portlet.journal.util.comparator.ArticleIDComparator;
import com.liferay.portlet.journal.util.comparator.ArticleModifiedDateComparator;
import com.liferay.portlet.journal.util.comparator.ArticleReviewDateComparator;
import com.liferay.portlet.journal.util.comparator.ArticleTitleComparator;
import com.liferay.portlet.journal.util.comparator.ArticleVersionComparator;
import com.liferay.util.ContentUtil;
import com.liferay.util.FiniteUniqueStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import javax.portlet.PortletSession;
import javax.portlet.PortletURL;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 * @author Wesley Gong
 * @author Angelo Jefferson
 * @author Hugo Huijser
 */
public class JournalUtil {

	public static final int MAX_STACK_SIZE = 20;

	public static void addAllReservedEls(
		Element rootElement, Map<String, String> tokens, JournalArticle article,
		String languageId, ThemeDisplay themeDisplay) {

		JournalUtil.addReservedEl(
			rootElement, tokens, JournalStructureConstants.RESERVED_ARTICLE_ID,
			article.getArticleId());

		JournalUtil.addReservedEl(
			rootElement, tokens,
			JournalStructureConstants.RESERVED_ARTICLE_VERSION,
			article.getVersion());

		JournalUtil.addReservedEl(
			rootElement, tokens,
			JournalStructureConstants.RESERVED_ARTICLE_TITLE,
			article.getTitle(languageId));

		JournalUtil.addReservedEl(
			rootElement, tokens,
			JournalStructureConstants.RESERVED_ARTICLE_URL_TITLE,
			article.getUrlTitle());

		JournalUtil.addReservedEl(
			rootElement, tokens,
			JournalStructureConstants.RESERVED_ARTICLE_DESCRIPTION,
			article.getDescription(languageId));

		JournalUtil.addReservedEl(
			rootElement, tokens,
			JournalStructureConstants.RESERVED_ARTICLE_TYPE, article.getType());

		JournalUtil.addReservedEl(
			rootElement, tokens,
			JournalStructureConstants.RESERVED_ARTICLE_CREATE_DATE,
			article.getCreateDate());

		JournalUtil.addReservedEl(
			rootElement, tokens,
			JournalStructureConstants.RESERVED_ARTICLE_MODIFIED_DATE,
			article.getModifiedDate());

		if (article.getDisplayDate() != null) {
			JournalUtil.addReservedEl(
				rootElement, tokens,
				JournalStructureConstants.RESERVED_ARTICLE_DISPLAY_DATE,
				article.getDisplayDate());
		}

		String smallImageURL = StringPool.BLANK;

		if (Validator.isNotNull(article.getSmallImageURL())) {
			smallImageURL = article.getSmallImageURL();
		}
		else if ((themeDisplay != null) && article.isSmallImage()) {
			StringBundler sb = new StringBundler(5);

			sb.append(themeDisplay.getPathImage());
			sb.append("/journal/article?img_id=");
			sb.append(article.getSmallImageId());
			sb.append("&t=");
			sb.append(
				WebServerServletTokenUtil.getToken(article.getSmallImageId()));

			smallImageURL = sb.toString();
		}

		JournalUtil.addReservedEl(
			rootElement, tokens,
			JournalStructureConstants.RESERVED_ARTICLE_SMALL_IMAGE_URL,
			smallImageURL);

		String[] assetTagNames = new String[0];

		try {
			assetTagNames = AssetTagLocalServiceUtil.getTagNames(
				JournalArticle.class.getName(), article.getResourcePrimKey());
		}
		catch (SystemException se) {
		}

		JournalUtil.addReservedEl(
			rootElement, tokens,
			JournalStructureConstants.RESERVED_ARTICLE_ASSET_TAG_NAMES,
			StringUtil.merge(assetTagNames));

		JournalUtil.addReservedEl(
			rootElement, tokens,
			JournalStructureConstants.RESERVED_ARTICLE_AUTHOR_ID,
			String.valueOf(article.getUserId()));

		String userName = StringPool.BLANK;
		String userEmailAddress = StringPool.BLANK;
		String userComments = StringPool.BLANK;
		String userJobTitle = StringPool.BLANK;

		User user = null;

		try {
			user = UserLocalServiceUtil.getUserById(article.getUserId());

			userName = user.getFullName();
			userEmailAddress = user.getEmailAddress();
			userComments = user.getComments();
			userJobTitle = user.getJobTitle();
		}
		catch (PortalException pe) {
		}
		catch (SystemException se) {
		}

		JournalUtil.addReservedEl(
			rootElement, tokens,
			JournalStructureConstants.RESERVED_ARTICLE_AUTHOR_NAME, userName);

		JournalUtil.addReservedEl(
			rootElement, tokens,
			JournalStructureConstants.RESERVED_ARTICLE_AUTHOR_EMAIL_ADDRESS,
			userEmailAddress);

		JournalUtil.addReservedEl(
			rootElement, tokens,
			JournalStructureConstants.RESERVED_ARTICLE_AUTHOR_COMMENTS,
			userComments);

		JournalUtil.addReservedEl(
			rootElement, tokens,
			JournalStructureConstants.RESERVED_ARTICLE_AUTHOR_JOB_TITLE,
			userJobTitle);
	}

	public static void addPortletBreadcrumbEntries(
			JournalArticle article, HttpServletRequest request,
			RenderResponse renderResponse)
		throws Exception {

		JournalFolder folder = article.getFolder();

		if (folder.getFolderId() !=
				JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID) {

			addPortletBreadcrumbEntries(folder, request, renderResponse);
		}

		JournalArticle unescapedArticle = article.toUnescapedModel();

		PortletURL portletURL = renderResponse.createRenderURL();

		portletURL.setParameter("struts_action", "/article/view_article");
		portletURL.setParameter(
			"groupId", String.valueOf(article.getGroupId()));
		portletURL.setParameter(
			"articleId", String.valueOf(article.getArticleId()));

		PortalUtil.addPortletBreadcrumbEntry(
			request, unescapedArticle.getTitle(), portletURL.toString());
	}

	public static void addPortletBreadcrumbEntries(
			JournalFolder folder, HttpServletRequest request,
			LiferayPortletResponse liferayPortletResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			com.liferay.portal.kernel.util.WebKeys.THEME_DISPLAY);

		String strutsAction = ParamUtil.getString(request, "struts_action");

		PortletURL portletURL = liferayPortletResponse.createRenderURL();

		if (strutsAction.equals("/journal/select_folder")) {
			portletURL.setParameter("struts_action", "/journal/select_folder");
			portletURL.setWindowState(LiferayWindowState.POP_UP);

			PortalUtil.addPortletBreadcrumbEntry(
				request, themeDisplay.translate("home"), portletURL.toString());
		}
		else {
			portletURL.setParameter("struts_action", "/journal/view");

			Map<String, Object> data = new HashMap<String, Object>();

			data.put("direction-right", Boolean.TRUE.toString());
			data.put(
				"folder-id", JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID);

			PortalUtil.addPortletBreadcrumbEntry(
				request, themeDisplay.translate("home"), portletURL.toString(),
				data);
		}

		if (folder == null) {
			return;
		}

		List<JournalFolder> ancestorFolders = folder.getAncestors();

		Collections.reverse(ancestorFolders);

		for (JournalFolder ancestorFolder : ancestorFolders) {
			portletURL.setParameter(
				"folderId", String.valueOf(ancestorFolder.getFolderId()));

			Map<String, Object> data = new HashMap<String, Object>();

			data.put("direction-right", Boolean.TRUE.toString());
			data.put("folder-id", ancestorFolder.getFolderId());

			PortalUtil.addPortletBreadcrumbEntry(
				request, ancestorFolder.getName(), portletURL.toString(), data);
		}

		portletURL.setParameter(
			"folderId", String.valueOf(folder.getFolderId()));

		if (folder.getFolderId() !=
				JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID) {

			JournalFolder unescapedFolder = folder.toUnescapedModel();

			Map<String, Object> data = new HashMap<String, Object>();

			data.put("direction-right", Boolean.TRUE.toString());
			data.put("folder-id", folder.getFolderId());

			PortalUtil.addPortletBreadcrumbEntry(
				request, unescapedFolder.getName(), portletURL.toString(),
				data);
		}
	}

	public static void addPortletBreadcrumbEntries(
			JournalFolder folder, HttpServletRequest request,
			RenderResponse renderResponse)
		throws Exception {

		LiferayPortletResponse liferayPortletResponse =
			(LiferayPortletResponse)renderResponse;

		addPortletBreadcrumbEntries(folder, request, liferayPortletResponse);
	}

	public static void addPortletBreadcrumbEntries(
			long folderId, HttpServletRequest request,
			RenderResponse renderResponse)
		throws Exception {

		if (folderId == JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			return;
		}

		JournalFolder folder = JournalFolderLocalServiceUtil.getFolder(
			folderId);

		addPortletBreadcrumbEntries(folder, request, renderResponse);
	}

	public static void addRecentArticle(
		PortletRequest portletRequest, JournalArticle article) {

		if (article != null) {
			Stack<JournalArticle> stack = getRecentArticles(portletRequest);

			stack.push(article);
		}
	}

	public static void addRecentDDMStructure(
		PortletRequest portletRequest, DDMStructure ddmStructure) {

		if (ddmStructure != null) {
			Stack<DDMStructure> stack = getRecentDDMStructures(portletRequest);

			stack.push(ddmStructure);
		}
	}

	public static void addRecentDDMTemplate(
		PortletRequest portletRequest, DDMTemplate ddmTemplate) {

		if (ddmTemplate != null) {
			Stack<DDMTemplate> stack = getRecentDDMTemplates(portletRequest);

			stack.push(ddmTemplate);
		}
	}

	public static void addReservedEl(
		Element rootElement, Map<String, String> tokens, String name,
		Date value) {

		addReservedEl(rootElement, tokens, name, Time.getRFC822(value));
	}

	public static void addReservedEl(
		Element rootElement, Map<String, String> tokens, String name,
		double value) {

		addReservedEl(rootElement, tokens, name, String.valueOf(value));
	}

	public static void addReservedEl(
		Element rootElement, Map<String, String> tokens, String name,
		String value) {

		// XML

		if (rootElement != null) {
			Element dynamicElementElement = SAXReaderUtil.createElement(
				"dynamic-element");

			Attribute nameAttribute = SAXReaderUtil.createAttribute(
				dynamicElementElement, "name", name);

			dynamicElementElement.add(nameAttribute);

			Attribute typeAttribute = SAXReaderUtil.createAttribute(
				dynamicElementElement, "type", "text");

			dynamicElementElement.add(typeAttribute);

			Element dynamicContentElement = SAXReaderUtil.createElement(
				"dynamic-content");

			//dynamicContentElement.setText("<![CDATA[" + value + "]]>");
			dynamicContentElement.setText(value);

			dynamicElementElement.add(dynamicContentElement);

			rootElement.add(dynamicElementElement);
		}

		// Tokens

		tokens.put(
			StringUtil.replace(name, CharPool.DASH, CharPool.UNDERLINE), value);
	}

	public static String formatVM(String vm) {
		return vm;
	}

	public static String getAbsolutePath(
			PortletRequest portletRequest, long folderId)
		throws PortalException, SystemException {

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		if (folderId == JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			return themeDisplay.translate("home");
		}

		JournalFolder folder = JournalFolderLocalServiceUtil.getFolder(
			folderId);

		List<JournalFolder> folders = folder.getAncestors();

		Collections.reverse(folders);

		StringBundler sb = new StringBundler((folders.size() * 3) + 5);

		sb.append(themeDisplay.translate("home"));
		sb.append(StringPool.SPACE);

		for (JournalFolder curFolder : folders) {
			sb.append(StringPool.RAQUO);
			sb.append(StringPool.SPACE);
			sb.append(curFolder.getName());
		}

		sb.append(StringPool.RAQUO);
		sb.append(StringPool.SPACE);
		sb.append(folder.getName());

		return sb.toString();
	}

	public static OrderByComparator getArticleOrderByComparator(
		String orderByCol, String orderByType) {

		boolean orderByAsc = false;

		if (orderByType.equals("asc")) {
			orderByAsc = true;
		}

		OrderByComparator orderByComparator = null;

		if (orderByCol.equals("create-date")) {
			orderByComparator = new ArticleCreateDateComparator(orderByAsc);
		}
		else if (orderByCol.equals("display-date")) {
			orderByComparator = new ArticleDisplayDateComparator(orderByAsc);
		}
		else if (orderByCol.equals("id")) {
			orderByComparator = new ArticleIDComparator(orderByAsc);
		}
		else if (orderByCol.equals("modified-date")) {
			orderByComparator = new ArticleModifiedDateComparator(orderByAsc);
		}
		else if (orderByCol.equals("review-date")) {
			orderByComparator = new ArticleReviewDateComparator(orderByAsc);
		}
		else if (orderByCol.equals("title")) {
			orderByComparator = new ArticleTitleComparator(orderByAsc);
		}
		else if (orderByCol.equals("version")) {
			orderByComparator = new ArticleVersionComparator(orderByAsc);
		}

		return orderByComparator;
	}

	public static Tuple getArticles(Hits hits)
		throws PortalException, SystemException {

		List<JournalArticle> articles = new ArrayList<JournalArticle>();
		boolean corruptIndex = false;

		List<com.liferay.portal.kernel.search.Document> documents =
			hits.toList();

		for (com.liferay.portal.kernel.search.Document document : documents) {
			long groupId = GetterUtil.getLong(document.get(Field.GROUP_ID));
			String articleId = document.get("articleId");

			try {
				JournalArticle article =
					JournalArticleLocalServiceUtil.getArticle(
						groupId, articleId);

				articles.add(article);
			}
			catch (NoSuchArticleException nsae) {
				corruptIndex = true;

				Indexer indexer = IndexerRegistryUtil.getIndexer(
					JournalArticle.class);

				long companyId = GetterUtil.getLong(
					document.get(Field.COMPANY_ID));

				indexer.delete(companyId, document.getUID());
			}
		}

		return new Tuple(articles, corruptIndex);
	}

	public static String getEmailArticleAddedBody(
		PortletPreferences preferences) {

		String emailArticleAddedBody = preferences.getValue(
			"emailArticleAddedBody", StringPool.BLANK);

		if (Validator.isNotNull(emailArticleAddedBody)) {
			return emailArticleAddedBody;
		}
		else {
			return ContentUtil.get(
				PropsUtil.get(PropsKeys.JOURNAL_EMAIL_ARTICLE_ADDED_BODY));
		}
	}

	public static boolean getEmailArticleAddedEnabled(
		PortletPreferences preferences) {

		String emailArticleAddedEnabled = preferences.getValue(
			"emailArticleAddedEnabled", StringPool.BLANK);

		if (Validator.isNotNull(emailArticleAddedEnabled)) {
			return GetterUtil.getBoolean(emailArticleAddedEnabled);
		}
		else {
			return GetterUtil.getBoolean(
				PropsUtil.get(PropsKeys.JOURNAL_EMAIL_ARTICLE_ADDED_ENABLED));
		}
	}

	public static String getEmailArticleAddedSubject(
		PortletPreferences preferences) {

		String emailArticleAddedSubject = preferences.getValue(
			"emailArticleAddedSubject", StringPool.BLANK);

		if (Validator.isNotNull(emailArticleAddedSubject)) {
			return emailArticleAddedSubject;
		}
		else {
			return ContentUtil.get(
				PropsUtil.get(PropsKeys.JOURNAL_EMAIL_ARTICLE_ADDED_SUBJECT));
		}
	}

	public static String getEmailArticleApprovalDeniedBody(
		PortletPreferences preferences) {

		String emailArticleApprovalDeniedBody = preferences.getValue(
			"emailArticleApprovalDeniedBody", StringPool.BLANK);

		if (Validator.isNotNull(emailArticleApprovalDeniedBody)) {
			return emailArticleApprovalDeniedBody;
		}
		else {
			return ContentUtil.get(
				PropsUtil.get(
					PropsKeys.JOURNAL_EMAIL_ARTICLE_APPROVAL_DENIED_BODY));
		}
	}

	public static boolean getEmailArticleApprovalDeniedEnabled(
		PortletPreferences preferences) {

		String emailArticleApprovalDeniedEnabled = preferences.getValue(
			"emailArticleApprovalDeniedEnabled", StringPool.BLANK);

		if (Validator.isNotNull(emailArticleApprovalDeniedEnabled)) {
			return GetterUtil.getBoolean(emailArticleApprovalDeniedEnabled);
		}
		else {
			return GetterUtil.getBoolean(
				PropsUtil.get(
					PropsKeys.JOURNAL_EMAIL_ARTICLE_APPROVAL_DENIED_ENABLED));
		}
	}

	public static String getEmailArticleApprovalDeniedSubject(
		PortletPreferences preferences) {

		String emailArticleApprovalDeniedSubject = preferences.getValue(
			"emailArticleApprovalDeniedSubject", StringPool.BLANK);

		if (Validator.isNotNull(emailArticleApprovalDeniedSubject)) {
			return emailArticleApprovalDeniedSubject;
		}
		else {
			return ContentUtil.get(
				PropsUtil.get(
					PropsKeys.JOURNAL_EMAIL_ARTICLE_APPROVAL_DENIED_SUBJECT));
		}
	}

	public static String getEmailArticleApprovalGrantedBody(
		PortletPreferences preferences) {

		String emailArticleApprovalGrantedBody = preferences.getValue(
			"emailArticleApprovalGrantedBody", StringPool.BLANK);

		if (Validator.isNotNull(emailArticleApprovalGrantedBody)) {
			return emailArticleApprovalGrantedBody;
		}
		else {
			return ContentUtil.get(
				PropsUtil.get(
					PropsKeys.JOURNAL_EMAIL_ARTICLE_APPROVAL_GRANTED_BODY));
		}
	}

	public static boolean getEmailArticleApprovalGrantedEnabled(
		PortletPreferences preferences) {

		String emailArticleApprovalGrantedEnabled = preferences.getValue(
			"emailArticleApprovalGrantedEnabled", StringPool.BLANK);

		if (Validator.isNotNull(emailArticleApprovalGrantedEnabled)) {
			return GetterUtil.getBoolean(emailArticleApprovalGrantedEnabled);
		}
		else {
			return GetterUtil.getBoolean(
				PropsUtil.get(
					PropsKeys.JOURNAL_EMAIL_ARTICLE_APPROVAL_GRANTED_ENABLED));
		}
	}

	public static String getEmailArticleApprovalGrantedSubject(
		PortletPreferences preferences) {

		String emailArticleApprovalGrantedSubject = preferences.getValue(
			"emailArticleApprovalGrantedSubject", StringPool.BLANK);

		if (Validator.isNotNull(emailArticleApprovalGrantedSubject)) {
			return emailArticleApprovalGrantedSubject;
		}
		else {
			return ContentUtil.get(
				PropsUtil.get(
					PropsKeys.JOURNAL_EMAIL_ARTICLE_APPROVAL_GRANTED_SUBJECT));
		}
	}

	public static String getEmailArticleApprovalRequestedBody(
		PortletPreferences preferences) {

		String emailArticleApprovalRequestedBody = preferences.getValue(
			"emailArticleApprovalRequestedBody", StringPool.BLANK);

		if (Validator.isNotNull(emailArticleApprovalRequestedBody)) {
			return emailArticleApprovalRequestedBody;
		}
		else {
			return ContentUtil.get(
				PropsUtil.get(
					PropsKeys.JOURNAL_EMAIL_ARTICLE_APPROVAL_REQUESTED_BODY));
		}
	}

	public static boolean getEmailArticleApprovalRequestedEnabled(
		PortletPreferences preferences) {

		String emailArticleApprovalRequestedEnabled = preferences.getValue(
			"emailArticleApprovalRequestedEnabled", StringPool.BLANK);

		if (Validator.isNotNull(emailArticleApprovalRequestedEnabled)) {
			return GetterUtil.getBoolean(emailArticleApprovalRequestedEnabled);
		}
		else {
			return GetterUtil.getBoolean(
				PropsUtil.get(
					PropsKeys.
						JOURNAL_EMAIL_ARTICLE_APPROVAL_REQUESTED_ENABLED));
		}
	}

	public static String getEmailArticleApprovalRequestedSubject(
		PortletPreferences preferences) {

		String emailArticleApprovalRequestedSubject = preferences.getValue(
			"emailArticleApprovalRequestedSubject", StringPool.BLANK);

		if (Validator.isNotNull(emailArticleApprovalRequestedSubject)) {
			return emailArticleApprovalRequestedSubject;
		}
		else {
			return ContentUtil.get(
				PropsUtil.get(
					PropsKeys.
						JOURNAL_EMAIL_ARTICLE_APPROVAL_REQUESTED_SUBJECT));
		}
	}

	public static String getEmailArticleReviewBody(
		PortletPreferences preferences) {

		String emailArticleReviewBody = preferences.getValue(
			"emailArticleReviewBody", StringPool.BLANK);

		if (Validator.isNotNull(emailArticleReviewBody)) {
			return emailArticleReviewBody;
		}
		else {
			return ContentUtil.get(
				PropsUtil.get(PropsKeys.JOURNAL_EMAIL_ARTICLE_REVIEW_BODY));
		}
	}

	public static boolean getEmailArticleReviewEnabled(
		PortletPreferences preferences) {

		String emailArticleReviewEnabled = preferences.getValue(
			"emailArticleReviewEnabled", StringPool.BLANK);

		if (Validator.isNotNull(emailArticleReviewEnabled)) {
			return GetterUtil.getBoolean(emailArticleReviewEnabled);
		}
		else {
			return GetterUtil.getBoolean(
				PropsUtil.get(PropsKeys.JOURNAL_EMAIL_ARTICLE_REVIEW_ENABLED));
		}
	}

	public static String getEmailArticleReviewSubject(
		PortletPreferences preferences) {

		String emailArticleReviewSubject = preferences.getValue(
			"emailArticleReviewSubject", StringPool.BLANK);

		if (Validator.isNotNull(emailArticleReviewSubject)) {
			return emailArticleReviewSubject;
		}
		else {
			return ContentUtil.get(
				PropsUtil.get(PropsKeys.JOURNAL_EMAIL_ARTICLE_REVIEW_SUBJECT));
		}
	}

	public static String getEmailArticleUpdatedBody(
		PortletPreferences preferences) {

		String emailArticleUpdatedBody = preferences.getValue(
			"emailArticleUpdatedBody", StringPool.BLANK);

		if (Validator.isNotNull(emailArticleUpdatedBody)) {
			return emailArticleUpdatedBody;
		}
		else {
			return ContentUtil.get(
				PropsUtil.get(PropsKeys.JOURNAL_EMAIL_ARTICLE_UPDATED_BODY));
		}
	}

	public static boolean getEmailArticleUpdatedEnabled(
		PortletPreferences preferences) {

		String emailArticleUpdatedEnabled = preferences.getValue(
			"emailArticleUpdatedEnabled", StringPool.BLANK);

		if (Validator.isNotNull(emailArticleUpdatedEnabled)) {
			return GetterUtil.getBoolean(emailArticleUpdatedEnabled);
		}
		else {
			return GetterUtil.getBoolean(
				PropsUtil.get(PropsKeys.JOURNAL_EMAIL_ARTICLE_UPDATED_ENABLED));
		}
	}

	public static String getEmailArticleUpdatedSubject(
		PortletPreferences preferences) {

		String emailArticleUpdatedSubject = preferences.getValue(
			"emailArticleUpdatedSubject", StringPool.BLANK);

		if (Validator.isNotNull(emailArticleUpdatedSubject)) {
			return emailArticleUpdatedSubject;
		}
		else {
			return ContentUtil.get(
				PropsUtil.get(PropsKeys.JOURNAL_EMAIL_ARTICLE_UPDATED_SUBJECT));
		}
	}

	public static String getEmailFromAddress(
			PortletPreferences preferences, long companyId)
		throws SystemException {

		return PortalUtil.getEmailFromAddress(
			preferences, companyId, PropsValues.JOURNAL_EMAIL_FROM_ADDRESS);
	}

	public static String getEmailFromName(
			PortletPreferences preferences, long companyId)
		throws SystemException {

		return PortalUtil.getEmailFromName(
			preferences, companyId, PropsValues.JOURNAL_EMAIL_FROM_NAME);
	}

	public static String getJournalControlPanelLink(
			PortletRequest portletRequest, long folderId)
		throws PortalException, SystemException {

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		PortletURL portletURL = PortletURLFactoryUtil.create(
			portletRequest, PortletKeys.JOURNAL,
			PortalUtil.getControlPanelPlid(themeDisplay.getCompanyId()),
			PortletRequest.RENDER_PHASE);

		portletURL.setParameter("struts_action", "/journal/view");
		portletURL.setParameter("folderId", String.valueOf(folderId));

		return portletURL.toString();
	}

	public static Stack<JournalArticle> getRecentArticles(
		PortletRequest portletRequest) {

		PortletSession portletSession = portletRequest.getPortletSession();

		Stack<JournalArticle> recentArticles =
			(Stack<JournalArticle>)portletSession.getAttribute(
				WebKeys.JOURNAL_RECENT_ARTICLES);

		if (recentArticles == null) {
			recentArticles = new FiniteUniqueStack<JournalArticle>(
				MAX_STACK_SIZE);

			portletSession.setAttribute(
				WebKeys.JOURNAL_RECENT_ARTICLES, recentArticles);
		}

		return recentArticles;
	}

	public static Stack<DDMStructure> getRecentDDMStructures(
		PortletRequest portletRequest) {

		PortletSession portletSession = portletRequest.getPortletSession();

		Stack<DDMStructure> recentDDMStructures =
			(Stack<DDMStructure>)portletSession.getAttribute(
				WebKeys.JOURNAL_RECENT_DYNAMIC_DATA_MAPPING_STRUCTURES);

		if (recentDDMStructures == null) {
			recentDDMStructures = new FiniteUniqueStack<DDMStructure>(
				MAX_STACK_SIZE);

			portletSession.setAttribute(
				WebKeys.JOURNAL_RECENT_DYNAMIC_DATA_MAPPING_STRUCTURES,
				recentDDMStructures);
		}

		return recentDDMStructures;
	}

	public static Stack<DDMTemplate> getRecentDDMTemplates(
		PortletRequest portletRequest) {

		PortletSession portletSession = portletRequest.getPortletSession();

		Stack<DDMTemplate> recentDDMTemplates =
			(Stack<DDMTemplate>)portletSession.getAttribute(
				WebKeys.JOURNAL_RECENT_DYNAMIC_DATA_MAPPING_TEMPLATES);

		if (recentDDMTemplates == null) {
			recentDDMTemplates = new FiniteUniqueStack<DDMTemplate>(
				MAX_STACK_SIZE);

			portletSession.setAttribute(
				WebKeys.JOURNAL_RECENT_DYNAMIC_DATA_MAPPING_TEMPLATES,
				recentDDMTemplates);
		}

		return recentDDMTemplates;
	}

	public static String getTemplateScript(
		DDMTemplate ddmTemplate, Map<String, String> tokens, String languageId,
		boolean transform) {

		String script = ddmTemplate.getScript();

		if (!transform) {
			return script;
		}

		String[] transformerListenerClassNames = PropsUtil.getArray(
			PropsKeys.JOURNAL_TRANSFORMER_LISTENER);

		for (String transformerListenerClassName :
				transformerListenerClassNames) {

			TransformerListener transformerListener = null;

			try {
				transformerListener =
					(TransformerListener)InstanceFactory.newInstance(
						transformerListenerClassName);

				continue;
			}
			catch (Exception e) {
				_log.error(e, e);
			}

			script = transformerListener.onScript(
				script, null, languageId, tokens);
		}

		return script;
	}

	public static String getTemplateScript(
			long groupId, String ddmTemplateKey, Map<String, String> tokens,
			String languageId)
		throws PortalException, SystemException {

		return getTemplateScript(
			groupId, ddmTemplateKey, tokens, languageId, true);
	}

	public static String getTemplateScript(
			long groupId, String ddmTemplateKey, Map<String, String> tokens,
			String languageId, boolean transform)
		throws PortalException, SystemException {

		DDMTemplate ddmTemplate = DDMTemplateLocalServiceUtil.getTemplate(
			groupId, PortalUtil.getClassNameId(DDMStructure.class),
			ddmTemplateKey);

		return getTemplateScript(ddmTemplate, tokens, languageId, transform);
	}

	public static Map<String, String> getTokens(
			long articleGroupId, ThemeDisplay themeDisplay)
		throws PortalException, SystemException {

		return getTokens(articleGroupId, themeDisplay, null);
	}

	public static Map<String, String> getTokens(
			long articleGroupId, ThemeDisplay themeDisplay, String xmlRequest)
		throws PortalException, SystemException {

		Map<String, String> tokens = new HashMap<String, String>();

		if (themeDisplay != null) {
			_populateTokens(tokens, articleGroupId, themeDisplay);
		}
		else if (Validator.isNotNull(xmlRequest)) {
			try {
				_populateTokens(tokens, articleGroupId, xmlRequest);
			}
			catch (Exception e) {
				if (_log.isWarnEnabled()) {
					_log.warn(e, e);
				}
			}
		}

		return tokens;
	}

	public static String getUrlTitle(long id, String title) {
		if (title == null) {
			return String.valueOf(id);
		}

		title = title.trim().toLowerCase();

		if (Validator.isNull(title) || Validator.isNumber(title) ||
			title.equals("rss")) {

			title = String.valueOf(id);
		}
		else {
			title = FriendlyURLNormalizerUtil.normalize(
				title, _URL_TITLE_REPLACE_CHARS);
		}

		return ModelHintsUtil.trimString(
			JournalArticle.class.getName(), "urlTitle", title);
	}

	public static String mergeArticleContent(
		String curContent, String newContent, boolean removeNullElements) {

		try {
			Document curDocument = SAXReaderUtil.read(curContent);
			Document newDocument = SAXReaderUtil.read(newContent);

			Element curRootElement = curDocument.getRootElement();
			Element newRootElement = newDocument.getRootElement();

			curRootElement.addAttribute(
				"default-locale",
				newRootElement.attributeValue("default-locale"));
			curRootElement.addAttribute(
				"available-locales",
				newRootElement.attributeValue("available-locales"));

			_mergeArticleContentUpdate(
				curDocument, newRootElement,
				LocaleUtil.toLanguageId(LocaleUtil.getDefault()));

			if (removeNullElements) {
				_mergeArticleContentDelete(curRootElement, newDocument);
			}

			curContent = DDMXMLUtil.formatXML(curDocument);
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return curContent;
	}

	public static void removeArticleLocale(Element element, String languageId)
		throws PortalException, SystemException {

		for (Element dynamicElementElement :
				element.elements("dynamic-element")) {

			for (Element dynamicContentElement :
					dynamicElementElement.elements("dynamic-content")) {

				String curLanguageId = GetterUtil.getString(
					dynamicContentElement.attributeValue("language-id"));

				if (curLanguageId.equals(languageId)) {
					long id = GetterUtil.getLong(
						dynamicContentElement.attributeValue("id"));

					if (id > 0) {
						ImageLocalServiceUtil.deleteImage(id);
					}

					dynamicContentElement.detach();
				}
			}

			removeArticleLocale(dynamicElementElement, languageId);
		}
	}

	public static String removeArticleLocale(
		String content, String languageId) {

		try {
			Document document = SAXReaderUtil.read(content);

			Element rootElement = document.getRootElement();

			String availableLocales = rootElement.attributeValue(
				"available-locales");

			if (availableLocales == null) {
				return content;
			}

			availableLocales = StringUtil.remove(availableLocales, languageId);

			if (availableLocales.endsWith(",")) {
				availableLocales = availableLocales.substring(
					0, availableLocales.length() - 1);
			}

			rootElement.addAttribute("available-locales", availableLocales);

			removeArticleLocale(rootElement, languageId);

			content = DDMXMLUtil.formatXML(document);
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return content;
	}

	public static String removeOldContent(String content, String xsd) {
		try {
			Document contentDoc = SAXReaderUtil.read(content);
			Document xsdDoc = SAXReaderUtil.read(xsd);

			Element contentRoot = contentDoc.getRootElement();

			Stack<String> path = new Stack<String>();

			path.push(contentRoot.getName());

			_removeOldContent(path, contentRoot, xsdDoc);

			content = DDMXMLUtil.formatXML(contentDoc);
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return content;
	}

	public static void removeRecentArticle(
		PortletRequest portletRequest, String articleId) {

		removeRecentArticle(portletRequest, articleId, 0);
	}

	public static void removeRecentArticle(
		PortletRequest portletRequest, String articleId, double version) {

		Stack<JournalArticle> stack = getRecentArticles(portletRequest);

		Iterator<JournalArticle> itr = stack.iterator();

		while (itr.hasNext()) {
			JournalArticle journalArticle = itr.next();

			if (journalArticle.getArticleId().equals(articleId) &&
				((journalArticle.getVersion() == version) ||
				 (version == 0))) {

				itr.remove();
			}
		}
	}

	public static void removeRecentDDMStructure(
		PortletRequest portletRequest, String ddmStructureKey) {

		Stack<DDMStructure> stack = getRecentDDMStructures(portletRequest);

		Iterator<DDMStructure> itr = stack.iterator();

		while (itr.hasNext()) {
			DDMStructure ddmStructure = itr.next();

			if (ddmStructureKey.equals(ddmStructure.getStructureKey())) {
				itr.remove();

				break;
			}
		}
	}

	public static void removeRecentDDMTemplate(
		PortletRequest portletRequest, String ddmTemplateKey) {

		Stack<DDMTemplate> stack = getRecentDDMTemplates(portletRequest);

		Iterator<DDMTemplate> itr = stack.iterator();

		while (itr.hasNext()) {
			DDMTemplate ddmTemplate = itr.next();

			if (ddmTemplateKey.equals(ddmTemplate.getTemplateKey())) {
				itr.remove();

				break;
			}
		}
	}

	public static String transform(
			ThemeDisplay themeDisplay, Map<String, String> tokens,
			String viewMode, String languageId, String xml, String script,
			String langType)
		throws Exception {

		return _transformer.transform(
			themeDisplay, tokens, viewMode, languageId, xml, script, langType);
	}

	public static String validateXSD(String xsd) throws PortalException {
		try {
			Document document = SAXReaderUtil.read(xsd);

			return document.asXML();
		}
		catch (Exception e) {
			throw new StructureXsdException();
		}
	}

	private static void _addElementOptions(
		Element curContentElement, Element newContentElement) {

		List<Element> newElementOptions = newContentElement.elements("option");

		for (Element newElementOption : newElementOptions) {
			Element curElementOption = SAXReaderUtil.createElement("option");

			curElementOption.addCDATA(newElementOption.getText());

			curContentElement.add(curElementOption);
		}
	}

	private static Element _getElementByInstanceId(
		Document document, String instanceId) {

		XPath xPathSelector = SAXReaderUtil.createXPath(
			"//dynamic-element[@instance-id=" +
				HtmlUtil.escapeXPathAttribute(instanceId) + "]");

		List<Node> nodes = xPathSelector.selectNodes(document);

		if (nodes.size() == 1) {
			return (Element)nodes.get(0);
		}
		else {
			return null;
		}
	}

	private static void _mergeArticleContentDelete(
			Element curParentElement, Document newDocument)
		throws Exception {

		List<Element> curElements = curParentElement.elements(
			"dynamic-element");

		for (int i = 0; i < curElements.size(); i++) {
			Element curElement = curElements.get(i);

			_mergeArticleContentDelete(curElement, newDocument);

			String instanceId = curElement.attributeValue("instance-id");

			Element newElement = _getElementByInstanceId(
				newDocument, instanceId);

			if (newElement == null) {
				curElement.detach();
			}
		}
	}

	private static void _mergeArticleContentUpdate(
			Document curDocument, Element newParentElement, Element newElement,
			int pos, String defaultLocale)
		throws Exception {

		_mergeArticleContentUpdate(curDocument, newElement, defaultLocale);

		String instanceId = newElement.attributeValue("instance-id");

		Element curElement = _getElementByInstanceId(curDocument, instanceId);

		if (curElement != null) {
			_mergeArticleContentUpdate(curElement, newElement, defaultLocale);
		}
		else {
			String parentInstanceId = newParentElement.attributeValue(
				"instance-id");

			if (Validator.isNull(parentInstanceId)) {
				Element curRoot = curDocument.getRootElement();

				List<Element> curRootElements = curRoot.elements();

				curRootElements.add(pos, newElement.createCopy());
			}
			else {
				Element curParentElement = _getElementByInstanceId(
					curDocument, parentInstanceId);

				if (curParentElement != null) {
					List<Element> curParentElements =
						curParentElement.elements();

					curParentElements.add(pos, newElement.createCopy());
				}
			}
		}
	}

	private static void _mergeArticleContentUpdate(
			Document curDocument, Element newParentElement,
			String defaultLocale)
		throws Exception {

		List<Element> newElements = newParentElement.elements(
			"dynamic-element");

		for (int i = 0; i < newElements.size(); i++) {
			Element newElement = newElements.get(i);

			_mergeArticleContentUpdate(
				curDocument, newParentElement, newElement, i, defaultLocale);
		}
	}

	private static void _mergeArticleContentUpdate(
		Element curElement, Element newElement, String defaultLocale) {

		Attribute curTypeAttribute = curElement.attribute("type");
		Attribute newTypeAttribute = newElement.attribute("type");

		curTypeAttribute.setValue(newTypeAttribute.getValue());

		Attribute curIndexTypeAttribute = curElement.attribute("index-type");
		Attribute newIndexTypeAttribute = newElement.attribute("index-type");

		if (newIndexTypeAttribute != null) {
			if (curIndexTypeAttribute == null) {
				curElement.addAttribute(
					"index-type", newIndexTypeAttribute.getValue());
			}
			else {
				curIndexTypeAttribute.setValue(
					newIndexTypeAttribute.getValue());
			}
		}

		Element newContentElement = newElement.elements(
			"dynamic-content").get(0);

		String newLanguageId = newContentElement.attributeValue("language-id");
		String newValue = newContentElement.getText();

		String indexType = newElement.attributeValue("index-type");

		if (Validator.isNotNull(indexType)) {
			curElement.addAttribute("index-type", indexType);
		}

		List<Element> curContentElements = curElement.elements(
			"dynamic-content");

		if (Validator.isNull(newLanguageId)) {
			for (Element curContentElement : curContentElements) {
				curContentElement.detach();
			}

			Element curContentElement = SAXReaderUtil.createElement(
				"dynamic-content");

			if (newContentElement.element("option") != null) {
				_addElementOptions(curContentElement, newContentElement);
			}
			else {
				curContentElement.addCDATA(newValue);
			}

			curElement.add(curContentElement);
		}
		else {
			boolean alreadyExists = false;

			for (Element curContentElement : curContentElements) {
				String curLanguageId = curContentElement.attributeValue(
					"language-id");

				if (newLanguageId.equals(curLanguageId)) {
					alreadyExists = true;

					curContentElement.clearContent();

					if (newContentElement.element("option") != null) {
						_addElementOptions(
							curContentElement, newContentElement);
					}
					else {
						curContentElement.addCDATA(newValue);
					}

					break;
				}
			}

			if (!alreadyExists) {
				Element curContentElement = curContentElements.get(0);

				String curLanguageId = curContentElement.attributeValue(
					"language-id");

				if (Validator.isNull(curLanguageId)) {
					if (newLanguageId.equals(defaultLocale)) {
						curContentElement.clearContent();

						if (newContentElement.element("option") != null) {
							_addElementOptions(
								curContentElement, newContentElement);
						}
						else {
							curContentElement.addCDATA(newValue);
						}
					}
					else {
						curElement.add(newContentElement.createCopy());
					}

					curContentElement.addAttribute(
						"language-id", defaultLocale);
				}
				else {
					curElement.add(newContentElement.createCopy());
				}
			}
		}
	}

	private static void _populateCustomTokens(Map<String, String> tokens) {
		if (_customTokens == null) {
			synchronized (JournalUtil.class) {
				_customTokens = new HashMap<String, String>();

				for (String customToken :
						PropsValues.JOURNAL_ARTICLE_CUSTOM_TOKENS) {

					String value = PropsUtil.get(
						PropsKeys.JOURNAL_ARTICLE_CUSTOM_TOKEN_VALUE,
						new Filter(customToken));

					_customTokens.put(customToken, value);
				}
			}
		}

		if (!_customTokens.isEmpty()) {
			tokens.putAll(_customTokens);
		}
	}

	private static void _populateTokens(
			Map<String, String> tokens, long articleGroupId, String xmlRequest)
		throws Exception {

		Document requestDocument = SAXReaderUtil.read(xmlRequest);

		Element rootElement = requestDocument.getRootElement();

		Element themeDisplayElement = rootElement.element("theme-display");

		Layout layout = LayoutLocalServiceUtil.getLayout(
			GetterUtil.getLong(themeDisplayElement.elementText("plid")));

		Group group = layout.getGroup();

		LayoutSet layoutSet = layout.getLayoutSet();

		String friendlyUrlCurrent = null;

		if (layout.isPublicLayout()) {
			friendlyUrlCurrent = themeDisplayElement.elementText(
				"path-friendly-url-public");
		}
		else if (group.isUserGroup()) {
			friendlyUrlCurrent = themeDisplayElement.elementText(
				"path-friendly-url-private-user");
		}
		else {
			friendlyUrlCurrent = themeDisplayElement.elementText(
				"path-friendly-url-private-group");
		}

		String layoutSetFriendlyUrl = themeDisplayElement.elementText(
			"i18n-path");

		String virtualHostname = layoutSet.getVirtualHostname();

		if (Validator.isNull(virtualHostname) ||
			!virtualHostname.equals(
				themeDisplayElement.elementText("server-name"))) {

			layoutSetFriendlyUrl = friendlyUrlCurrent + group.getFriendlyURL();
		}

		tokens.put("article_group_id", String.valueOf(articleGroupId));
		tokens.put("cdn_host", themeDisplayElement.elementText("cdn-host"));
		tokens.put("company_id", themeDisplayElement.elementText("company-id"));
		tokens.put("friendly_url_current", friendlyUrlCurrent);
		tokens.put(
			"friendly_url_private_group",
			themeDisplayElement.elementText("path-friendly-url-private-group"));
		tokens.put(
			"friendly_url_private_user",
			themeDisplayElement.elementText("path-friendly-url-private-user"));
		tokens.put(
			"friendly_url_public",
			themeDisplayElement.elementText("path-friendly-url-public"));
		tokens.put("group_friendly_url", group.getFriendlyURL());
		tokens.put("image_path", themeDisplayElement.elementText("path-image"));
		tokens.put("layout_set_friendly_url", layoutSetFriendlyUrl);
		tokens.put("main_path", themeDisplayElement.elementText("path-main"));
		tokens.put(
			"portal_ctx", themeDisplayElement.elementText("path-context"));
		tokens.put(
			"portal_url",
			HttpUtil.removeProtocol(
				themeDisplayElement.elementText("url-portal")));
		tokens.put(
			"protocol",
			HttpUtil.getProtocol(
				themeDisplayElement.elementText("url-portal")));
		tokens.put(
			"root_path", themeDisplayElement.elementText("path-context"));
		tokens.put(
			"scope_group_id",
			themeDisplayElement.elementText("scope-group-id"));
		tokens.put(
			"site_group_id", themeDisplayElement.elementText("site-group-id"));
		tokens.put(
			"theme_image_path",
			themeDisplayElement.elementText("path-theme-images"));

		_populateCustomTokens(tokens);

		// Deprecated tokens

		tokens.put(
			"friendly_url",
			themeDisplayElement.elementText("path-friendly-url-public"));
		tokens.put(
			"friendly_url_private",
			themeDisplayElement.elementText("path-friendly-url-private-group"));
		tokens.put("group_id", String.valueOf(articleGroupId));
		tokens.put(
			"page_url",
			themeDisplayElement.elementText("path-friendly-url-public"));
	}

	private static void _populateTokens(
			Map<String, String> tokens, long articleGroupId,
			ThemeDisplay themeDisplay)
		throws PortalException, SystemException {

		Layout layout = themeDisplay.getLayout();

		Group group = layout.getGroup();

		LayoutSet layoutSet = layout.getLayoutSet();

		String friendlyUrlCurrent = null;

		if (layout.isPublicLayout()) {
			friendlyUrlCurrent = themeDisplay.getPathFriendlyURLPublic();
		}
		else if (group.isUserGroup()) {
			friendlyUrlCurrent = themeDisplay.getPathFriendlyURLPrivateUser();
		}
		else {
			friendlyUrlCurrent = themeDisplay.getPathFriendlyURLPrivateGroup();
		}

		String layoutSetFriendlyUrl = themeDisplay.getI18nPath();

		String virtualHostname = layoutSet.getVirtualHostname();

		if (Validator.isNull(virtualHostname) ||
			!virtualHostname.equals(themeDisplay.getServerName())) {

			layoutSetFriendlyUrl = friendlyUrlCurrent + group.getFriendlyURL();
		}

		tokens.put("article_group_id", String.valueOf(articleGroupId));
		tokens.put("cdn_host", themeDisplay.getCDNHost());
		tokens.put("company_id", String.valueOf(themeDisplay.getCompanyId()));
		tokens.put("friendly_url_current", friendlyUrlCurrent);
		tokens.put(
			"friendly_url_private_group",
			themeDisplay.getPathFriendlyURLPrivateGroup());
		tokens.put(
			"friendly_url_private_user",
			themeDisplay.getPathFriendlyURLPrivateUser());
		tokens.put(
			"friendly_url_public", themeDisplay.getPathFriendlyURLPublic());
		tokens.put("group_friendly_url", group.getFriendlyURL());
		tokens.put("image_path", themeDisplay.getPathImage());
		tokens.put("layout_set_friendly_url", layoutSetFriendlyUrl);
		tokens.put("main_path", themeDisplay.getPathMain());
		tokens.put("portal_ctx", themeDisplay.getPathContext());
		tokens.put(
			"portal_url", HttpUtil.removeProtocol(themeDisplay.getURLPortal()));
		tokens.put(
			"protocol", HttpUtil.getProtocol(themeDisplay.getURLPortal()));
		tokens.put("root_path", themeDisplay.getPathContext());
		tokens.put(
			"site_group_id", String.valueOf(themeDisplay.getSiteGroupId()));
		tokens.put(
			"scope_group_id", String.valueOf(themeDisplay.getScopeGroupId()));
		tokens.put("theme_image_path", themeDisplay.getPathThemeImages());

		_populateCustomTokens(tokens);

		// Deprecated tokens

		tokens.put("friendly_url", themeDisplay.getPathFriendlyURLPublic());
		tokens.put(
			"friendly_url_private",
			themeDisplay.getPathFriendlyURLPrivateGroup());
		tokens.put("group_id", String.valueOf(articleGroupId));
		tokens.put("page_url", themeDisplay.getPathFriendlyURLPublic());
	}

	private static void _removeOldContent(
			Stack<String> path, Element contentElement, Document xsdDocument)
		throws SystemException {

		String elementPath = "";

		for (int i = 0; i < path.size(); i++) {
			elementPath += "/" + path.elementAt(i);
		}

		for (int i = 0; i < contentElement.nodeCount(); i++) {
			Node contentNode = contentElement.node(i);

			if (contentNode instanceof Element) {
				_removeOldContent(
					path, (Element)contentNode, xsdDocument, elementPath);
			}
		}
	}

	private static void _removeOldContent(
			Stack<String> path, Element contentElement, Document xsdDocument,
			String elementPath)
		throws SystemException {

		String name = contentElement.attributeValue("name");

		if (Validator.isNull(name)) {
			return;
		}

		String localPath =
			"dynamic-element[@name=" + HtmlUtil.escapeXPathAttribute(name) +
				"]";

		String fullPath = elementPath + "/" + localPath;

		XPath xPathSelector = SAXReaderUtil.createXPath(fullPath);

		List<Node> curNodes = xPathSelector.selectNodes(xsdDocument);

		if (curNodes.size() == 0) {
			contentElement.detach();
		}

		path.push(localPath);

		_removeOldContent(path, contentElement, xsdDocument);

		path.pop();
	}

	private static final char[] _URL_TITLE_REPLACE_CHARS = new char[] {
		'.', '/'
	};

	private static Log _log = LogFactoryUtil.getLog(JournalUtil.class);

	private static Map<String, String> _customTokens;
	private static Transformer _transformer = new Transformer(
		PropsKeys.JOURNAL_TRANSFORMER_LISTENER,
		PropsKeys.JOURNAL_ERROR_TEMPLATE, true);

}