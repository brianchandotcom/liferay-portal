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

package com.liferay.journal.web.portlet.action;

import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMStructureServiceUtil;
import com.liferay.dynamic.data.mapping.storage.Field;
import com.liferay.dynamic.data.mapping.storage.FieldConstants;
import com.liferay.dynamic.data.mapping.storage.Fields;
import com.liferay.journal.exception.NoSuchArticleException;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalArticleConstants;
import com.liferay.journal.model.JournalFeed;
import com.liferay.journal.model.JournalFolder;
import com.liferay.journal.model.JournalFolderConstants;
import com.liferay.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.journal.service.JournalArticleServiceUtil;
import com.liferay.journal.service.JournalFeedServiceUtil;
import com.liferay.journal.service.JournalFolderServiceUtil;
import com.liferay.journal.service.permission.JournalPermission;
import com.liferay.journal.util.comparator.ArticleVersionComparator;
import com.liferay.journal.util.impl.JournalUtil;
import com.liferay.journal.web.portlet.JournalPortlet;
import com.liferay.portal.kernel.diff.CompareVersionsException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.PortletRequestModel;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.Node;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.kernel.xml.XPath;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.documentlibrary.service.DLAppLocalServiceUtil;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.portlet.ActionRequest;
import javax.portlet.PortletRequest;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Brian Wing Shun Chan
 */
public class ActionUtil {

	public static void compareVersions(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long groupId = ParamUtil.getLong(renderRequest, "groupId");
		String articleId = ParamUtil.getString(renderRequest, "articleId");

		String sourceArticleId = ParamUtil.getString(
			renderRequest, "sourceVersion");

		int index = sourceArticleId.lastIndexOf(
			JournalPortlet.VERSION_SEPARATOR);

		if (index != -1) {
			sourceArticleId = sourceArticleId.substring(
				index + JournalPortlet.VERSION_SEPARATOR.length(),
				sourceArticleId.length());
		}

		double sourceVersion = GetterUtil.getDouble(sourceArticleId);

		String targetArticleId = ParamUtil.getString(
			renderRequest, "targetVersion");

		index = targetArticleId.lastIndexOf(JournalPortlet.VERSION_SEPARATOR);

		if (index != -1) {
			targetArticleId = targetArticleId.substring(
				index + JournalPortlet.VERSION_SEPARATOR.length(),
				targetArticleId.length());
		}

		double targetVersion = GetterUtil.getDouble(targetArticleId);

		if ((sourceVersion == 0) && (targetVersion == 0)) {
			List<JournalArticle> sourceArticles =
				JournalArticleServiceUtil.getArticlesByArticleId(
					groupId, articleId, 0, 1,
					new ArticleVersionComparator(false));

			JournalArticle sourceArticle = sourceArticles.get(0);

			sourceVersion = sourceArticle.getVersion();

			List<JournalArticle> targetArticles =
				JournalArticleServiceUtil.getArticlesByArticleId(
					groupId, articleId, 0, 1,
					new ArticleVersionComparator(true));

			JournalArticle targetArticle = targetArticles.get(0);

			targetVersion = targetArticle.getVersion();
		}

		if (sourceVersion > targetVersion) {
			double tempVersion = targetVersion;

			targetVersion = sourceVersion;
			sourceVersion = tempVersion;
		}

		String languageId = getLanguageId(
			renderRequest, groupId, articleId, sourceVersion, targetVersion);

		String diffHtmlResults = null;

		try {
			diffHtmlResults = JournalUtil.diffHtml(
				groupId, articleId, sourceVersion, targetVersion, languageId,
				new PortletRequestModel(renderRequest, renderResponse),
				themeDisplay);
		}
		catch (CompareVersionsException cve) {
			renderRequest.setAttribute(WebKeys.DIFF_VERSION, cve.getVersion());
		}

		renderRequest.setAttribute(WebKeys.DIFF_HTML_RESULTS, diffHtmlResults);
		renderRequest.setAttribute(WebKeys.SOURCE_VERSION, sourceVersion);
		renderRequest.setAttribute(WebKeys.TARGET_VERSION, targetVersion);
	}

	public static void deleteArticle(
			ActionRequest actionRequest, String deleteArticleId)
		throws Exception {

		long groupId = ParamUtil.getLong(actionRequest, "groupId");
		String articleId = deleteArticleId;
		String articleURL = ParamUtil.getString(actionRequest, "articleURL");
		double version = 0;

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			JournalArticle.class.getName(), actionRequest);

		int pos = deleteArticleId.lastIndexOf(JournalPortlet.VERSION_SEPARATOR);

		if (pos == -1) {
			JournalArticleServiceUtil.deleteArticle(
				groupId, articleId, articleURL, serviceContext);
		}
		else {
			articleId = articleId.substring(0, pos);
			version = GetterUtil.getDouble(
				deleteArticleId.substring(
					pos + JournalPortlet.VERSION_SEPARATOR.length()));

			JournalArticleServiceUtil.deleteArticle(
				groupId, articleId, version, articleURL, serviceContext);
		}

		JournalUtil.removeRecentArticle(actionRequest, articleId, version);
	}

	public static void expireArticle(
			ActionRequest actionRequest, String expireArticleId)
		throws Exception {

		long groupId = ParamUtil.getLong(actionRequest, "groupId");
		String articleId = expireArticleId;
		String articleURL = ParamUtil.getString(actionRequest, "articleURL");
		double version = 0;

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			JournalArticle.class.getName(), actionRequest);

		int pos = expireArticleId.lastIndexOf(JournalPortlet.VERSION_SEPARATOR);

		if (pos == -1) {
			JournalArticleServiceUtil.expireArticle(
				groupId, articleId, articleURL, serviceContext);
		}
		else {
			articleId = articleId.substring(0, pos);
			version = GetterUtil.getDouble(
				expireArticleId.substring(
					pos + JournalPortlet.VERSION_SEPARATOR.length()));

			JournalArticleServiceUtil.expireArticle(
				groupId, articleId, version, articleURL, serviceContext);
		}

		JournalUtil.removeRecentArticle(actionRequest, articleId, version);
	}

	public static void expireFolder(
			long groupId, long parentFolderId, ServiceContext serviceContext)
		throws Exception {

		List<JournalFolder> folders = JournalFolderServiceUtil.getFolders(
			groupId, parentFolderId);

		for (JournalFolder folder : folders) {
			expireFolder(groupId, folder.getFolderId(), serviceContext);
		}

		List<JournalArticle> articles = JournalArticleServiceUtil.getArticles(
			groupId, parentFolderId);

		for (JournalArticle article : articles) {
			JournalArticleServiceUtil.expireArticle(
				groupId, article.getArticleId(), null, serviceContext);
		}
	}

	public static JournalArticle getArticle(HttpServletRequest request)
		throws PortalException {

		String actionName = ParamUtil.getString(
			request, ActionRequest.ACTION_NAME);

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		long resourcePrimKey = ParamUtil.getLong(request, "resourcePrimKey");
		long groupId = ParamUtil.getLong(
			request, "groupId", themeDisplay.getScopeGroupId());
		long classNameId = ParamUtil.getLong(request, "classNameId");
		long classPK = ParamUtil.getLong(request, "classPK");
		String articleId = ParamUtil.getString(request, "articleId");
		String ddmStructureKey = ParamUtil.getString(
			request, "ddmStructureKey");
		int status = ParamUtil.getInteger(
			request, "status", WorkflowConstants.STATUS_ANY);

		JournalArticle article = null;

		if (actionName.equals("addArticle") && (resourcePrimKey != 0)) {
			article = JournalArticleLocalServiceUtil.getLatestArticle(
				resourcePrimKey, status, false);
		}
		else if (!actionName.equals("addArticle") &&
				 Validator.isNotNull(articleId)) {

			article = JournalArticleServiceUtil.getLatestArticle(
				groupId, articleId, status);
		}
		else if ((classNameId > 0) &&
				 (classPK > JournalArticleConstants.CLASSNAME_ID_DEFAULT)) {

			String className = PortalUtil.getClassName(classNameId);

			try {
				article = JournalArticleServiceUtil.getLatestArticle(
					groupId, className, classPK);
			}
			catch (NoSuchArticleException nsae) {
				return null;
			}
		}
		else {
			DDMStructure ddmStructure = DDMStructureServiceUtil.fetchStructure(
				groupId, PortalUtil.getClassNameId(JournalArticle.class),
				ddmStructureKey, true);

			if (ddmStructure == null) {
				return null;
			}

			try {
				article = JournalArticleServiceUtil.getArticle(
					ddmStructure.getGroupId(), DDMStructure.class.getName(),
					ddmStructure.getStructureId());

				article.setNew(true);

				article.setId(0);
				article.setGroupId(groupId);
				article.setClassNameId(
					JournalArticleConstants.CLASSNAME_ID_DEFAULT);
				article.setClassPK(0);
				article.setArticleId(null);
				article.setVersion(0);
			}
			catch (NoSuchArticleException nsae) {
				return null;
			}
		}

		return article;
	}

	public static JournalArticle getArticle(PortletRequest portletRequest)
		throws Exception {

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			portletRequest);

		JournalArticle article = getArticle(request);

		JournalUtil.addRecentArticle(portletRequest, article);

		return article;
	}

	public static List<JournalArticle> getArticles(ResourceRequest request)
		throws Exception {

		long groupId = ParamUtil.getLong(request, "groupId");

		String[] articleIds = ParamUtil.getStringValues(
			request, "rowIdsJournalArticle");

		List<JournalArticle> articles = new ArrayList<>();

		for (String articleId : articleIds) {
			JournalArticle article = JournalArticleServiceUtil.getArticle(
				groupId, articleId);

			articles.add(article);
		}

		return articles;
	}

	public static JournalFeed getFeed(HttpServletRequest request)
		throws Exception {

		long groupId = ParamUtil.getLong(request, "groupId");
		String feedId = ParamUtil.getString(request, "feedId");

		JournalFeed feed = null;

		if (Validator.isNotNull(feedId)) {
			feed = JournalFeedServiceUtil.getFeed(groupId, feedId);
		}

		return feed;
	}

	public static JournalFeed getFeed(PortletRequest portletRequest)
		throws Exception {

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			portletRequest);

		return getFeed(request);
	}

	public static JournalFolder getFolder(HttpServletRequest request)
		throws PortalException {

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		long folderId = ParamUtil.getLong(request, "folderId");

		JournalFolder folder = null;

		if ((folderId > 0) &&
			(folderId != JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID)) {

			folder = JournalFolderServiceUtil.fetchFolder(folderId);
		}
		else {
			JournalPermission.check(
				themeDisplay.getPermissionChecker(),
				themeDisplay.getScopeGroupId(), ActionKeys.VIEW);
		}

		return folder;
	}

	public static JournalFolder getFolder(PortletRequest portletRequest)
		throws PortalException {

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			portletRequest);

		return getFolder(request);
	}

	public static List<JournalFolder> getFolders(ResourceRequest request)
		throws Exception {

		long[] folderIds = ParamUtil.getLongValues(
			request, "rowIdsJournalFolder");

		List<JournalFolder> folders = new ArrayList<>();

		for (long folderId : folderIds) {
			JournalFolder folder = JournalFolderServiceUtil.getFolder(folderId);

			folders.add(folder);
		}

		return folders;
	}

	public static Map<String, byte[]> getImages(String content, Fields fields)
		throws Exception {

		Map<String, byte[]> images = new HashMap<>();

		for (Field field : fields) {
			String dataType = field.getDataType();

			if (!dataType.equals(FieldConstants.IMAGE)) {
				continue;
			}

			Map<Locale, List<Serializable>> valuesMap = field.getValuesMap();

			for (Locale locale : valuesMap.keySet()) {
				List<Serializable> values = valuesMap.get(locale);

				for (int i = 0; i < values.size(); i++) {
					JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
						(String)values.get(i));

					String type = jsonObject.getString("type");

					if (type.equals("document")) {
						continue;
					}

					String uuid = jsonObject.getString("uuid");
					long groupId = jsonObject.getLong("groupId");

					if (Validator.isNotNull(uuid) && (groupId > 0)) {
						StringBundler sb = new StringBundler(7);

						sb.append(
							getElementInstanceId(content, field.getName(), i));
						sb.append(StringPool.UNDERLINE);
						sb.append(field.getName());
						sb.append(StringPool.UNDERLINE);
						sb.append(i);
						sb.append(StringPool.UNDERLINE);
						sb.append(LanguageUtil.getLanguageId(locale));

						FileEntry fileEntry =
							DLAppLocalServiceUtil.getFileEntryByUuidAndGroupId(
								uuid, groupId);

						byte[] bytes = FileUtil.getBytes(
							fileEntry.getContentStream());

						images.put(sb.toString(), bytes);
					}
				}
			}
		}

		return images;
	}

	public static JournalArticle getPreviewArticle(
			PortletRequest portletRequest)
		throws Exception {

		long groupId = ParamUtil.getLong(portletRequest, "groupId");
		String articleId = ParamUtil.getString(portletRequest, "articleId");
		double version = ParamUtil.getDouble(
			portletRequest, "version", JournalArticleConstants.VERSION_DEFAULT);

		JournalArticle article = JournalArticleServiceUtil.getArticle(
			groupId, articleId, version);

		JournalUtil.addRecentArticle(portletRequest, article);

		return article;
	}

	public static boolean hasArticle(ActionRequest actionRequest)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String articleId = ParamUtil.getString(actionRequest, "articleId");

		if (Validator.isNull(articleId)) {
			String[] articleIds = StringUtil.split(
				ParamUtil.getString(actionRequest, "rowIds"));

			if (articleIds.length <= 0) {
				return false;
			}

			articleId = articleIds[0];
		}

		int pos = articleId.lastIndexOf(JournalPortlet.VERSION_SEPARATOR);

		if (pos != -1) {
			articleId = articleId.substring(0, pos);
		}

		JournalArticle article = JournalArticleLocalServiceUtil.fetchArticle(
			themeDisplay.getScopeGroupId(), articleId);

		if (article == null) {
			return false;
		}

		return true;
	}

	protected static String getElementInstanceId(
			String content, String fieldName, int index)
		throws Exception {

		Document document = SAXReaderUtil.read(content);

		String xPathExpression =
			"//dynamic-element[@name = " +
				HtmlUtil.escapeXPathAttribute(fieldName) + "]";

		XPath xPath = SAXReaderUtil.createXPath(xPathExpression);

		List<Node> nodes = xPath.selectNodes(document);

		if (index > nodes.size()) {
			return StringPool.BLANK;
		}

		Element dynamicElementElement = (Element)nodes.get(index);

		return dynamicElementElement.attributeValue("instance-id");
	}

	protected static String getLanguageId(
			RenderRequest renderRequest, long groupId, String articleId,
			double sourceVersion, double targetVersion)
		throws Exception {

		JournalArticle sourceArticle =
			JournalArticleLocalServiceUtil.fetchArticle(
				groupId, articleId, sourceVersion);

		JournalArticle targetArticle =
			JournalArticleLocalServiceUtil.fetchArticle(
				groupId, articleId, targetVersion);

		Set<Locale> locales = new HashSet<>();

		for (String locale : sourceArticle.getAvailableLanguageIds()) {
			locales.add(LocaleUtil.fromLanguageId(locale));
		}

		for (String locale : targetArticle.getAvailableLanguageIds()) {
			locales.add(LocaleUtil.fromLanguageId(locale));
		}

		String languageId = ParamUtil.get(
			renderRequest, "languageId", targetArticle.getDefaultLanguageId());

		Locale locale = LocaleUtil.fromLanguageId(languageId);

		if (!locales.contains(locale)) {
			languageId = targetArticle.getDefaultLanguageId();
		}

		renderRequest.setAttribute(WebKeys.AVAILABLE_LOCALES, locales);
		renderRequest.setAttribute(WebKeys.LANGUAGE_ID, languageId);

		return languageId;
	}

}