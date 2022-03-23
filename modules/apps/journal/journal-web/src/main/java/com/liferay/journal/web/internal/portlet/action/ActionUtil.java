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

package com.liferay.journal.web.internal.portlet.action;

import com.liferay.diff.exception.CompareVersionsException;
import com.liferay.dynamic.data.mapping.exception.TemplateScriptException;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMStructureServiceUtil;
import com.liferay.journal.constants.JournalArticleConstants;
import com.liferay.journal.constants.JournalFolderConstants;
import com.liferay.journal.exception.NoSuchArticleException;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalFeed;
import com.liferay.journal.model.JournalFolder;
import com.liferay.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.journal.service.JournalArticleServiceUtil;
import com.liferay.journal.service.JournalFeedServiceUtil;
import com.liferay.journal.service.JournalFolderServiceUtil;
import com.liferay.journal.util.comparator.ArticleVersionComparator;
import com.liferay.journal.web.internal.portlet.JournalPortlet;
import com.liferay.journal.web.internal.security.permission.resource.JournalPermission;
import com.liferay.journal.web.internal.util.JournalHelperUtil;
import com.liferay.journal.web.internal.util.JournalUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.PortletRequestModel;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.io.File;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
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
				index + JournalPortlet.VERSION_SEPARATOR.length());
		}

		double sourceVersion = GetterUtil.getDouble(sourceArticleId);

		String targetArticleId = ParamUtil.getString(
			renderRequest, "targetVersion");

		index = targetArticleId.lastIndexOf(JournalPortlet.VERSION_SEPARATOR);

		if (index != -1) {
			targetArticleId = targetArticleId.substring(
				index + JournalPortlet.VERSION_SEPARATOR.length());
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

		String languageId = _getLanguageId(
			renderRequest, groupId, articleId, sourceVersion, targetVersion);

		String diffHtmlResults = null;

		try {
			diffHtmlResults = JournalHelperUtil.diffHtml(
				groupId, articleId, sourceVersion, targetVersion, languageId,
				new PortletRequestModel(renderRequest, renderResponse),
				themeDisplay);
		}
		catch (CompareVersionsException compareVersionsException) {
			renderRequest.setAttribute(
				WebKeys.DIFF_VERSION, compareVersionsException.getVersion());
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
			groupId, parentFolderId, LocaleUtil.getMostRelevantLocale());

		for (JournalArticle article : articles) {
			JournalArticleServiceUtil.expireArticle(
				groupId, article.getArticleId(), null, serviceContext);
		}
	}

	public static JournalArticle getArticle(
			HttpServletRequest httpServletRequest)
		throws PortalException {

		String actionName = ParamUtil.getString(
			httpServletRequest, ActionRequest.ACTION_NAME);

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		long resourcePrimKey = ParamUtil.getLong(
			httpServletRequest, "resourcePrimKey");
		long groupId = ParamUtil.getLong(
			httpServletRequest, "groupId", themeDisplay.getScopeGroupId());
		long classNameId = ParamUtil.getLong(httpServletRequest, "classNameId");
		long classPK = ParamUtil.getLong(httpServletRequest, "classPK");
		String articleId = ParamUtil.getString(httpServletRequest, "articleId");
		int status = ParamUtil.getInteger(
			httpServletRequest, "status", WorkflowConstants.STATUS_ANY);

		JournalArticle article = null;

		if (actionName.equals("/journal/add_article") &&
			(resourcePrimKey != 0)) {

			article = JournalArticleLocalServiceUtil.getLatestArticle(
				resourcePrimKey, status, false);
		}
		else if (!actionName.equals("/journal/add_article") &&
				 Validator.isNotNull(articleId)) {

			article = JournalArticleServiceUtil.getLatestArticle(
				groupId, articleId, status);
		}
		else if ((classNameId > 0) &&
				 (classPK > JournalArticleConstants.CLASS_NAME_ID_DEFAULT)) {

			String className = PortalUtil.getClassName(classNameId);

			try {
				article = JournalArticleServiceUtil.getLatestArticle(
					groupId, className, classPK);
			}
			catch (NoSuchArticleException noSuchArticleException) {
				if (_log.isDebugEnabled()) {
					_log.debug(noSuchArticleException);
				}

				return null;
			}
		}
		else {
			long ddmStructureId = ParamUtil.getLong(
				httpServletRequest, "ddmStructureId");
			String ddmStructureKey = ParamUtil.getString(
				httpServletRequest, "ddmStructureKey");

			DDMStructure ddmStructure = null;

			if (Validator.isNotNull(ddmStructureKey)) {
				ddmStructure = DDMStructureServiceUtil.fetchStructure(
					groupId, PortalUtil.getClassNameId(JournalArticle.class),
					ddmStructureKey, true);
			}
			else if (ddmStructureId > 0) {
				try {
					ddmStructure = DDMStructureServiceUtil.getStructure(
						ddmStructureId);
				}
				catch (Exception exception) {
					if (_log.isDebugEnabled()) {
						_log.debug(exception);
					}
				}
			}

			if (ddmStructure == null) {
				return null;
			}

			try {
				article = JournalArticleServiceUtil.getArticle(
					ddmStructure.getGroupId(), DDMStructure.class.getName(),
					ddmStructure.getStructureId());

				article.getDescriptionMap();
				article.getTitleMap();

				article.setNew(true);
				article.setId(0);
				article.setGroupId(groupId);
				article.setClassNameId(
					JournalArticleConstants.CLASS_NAME_ID_DEFAULT);
				article.setClassPK(0);
				article.setArticleId(null);
				article.setVersion(0);
			}
			catch (NoSuchArticleException noSuchArticleException) {
				if (_log.isDebugEnabled()) {
					_log.debug(noSuchArticleException);
				}

				return null;
			}
		}

		return article;
	}

	public static JournalArticle getArticle(PortletRequest portletRequest)
		throws Exception {

		JournalArticle article = getArticle(
			PortalUtil.getHttpServletRequest(portletRequest));

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

	public static JournalFeed getFeed(HttpServletRequest httpServletRequest)
		throws Exception {

		String feedId = ParamUtil.getString(httpServletRequest, "feedId");

		JournalFeed feed = null;

		if (Validator.isNotNull(feedId)) {
			long groupId = ParamUtil.getLong(httpServletRequest, "groupId");

			feed = JournalFeedServiceUtil.getFeed(groupId, feedId);
		}

		return feed;
	}

	public static JournalFolder getFolder(HttpServletRequest httpServletRequest)
		throws PortalException {

		long folderId = ParamUtil.getLong(httpServletRequest, "folderId");

		JournalFolder folder = null;

		if ((folderId > 0) &&
			(folderId != JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID)) {

			folder = JournalFolderServiceUtil.fetchFolder(folderId);
		}
		else {
			ThemeDisplay themeDisplay =
				(ThemeDisplay)httpServletRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			JournalPermission.check(
				themeDisplay.getPermissionChecker(),
				themeDisplay.getScopeGroup(), ActionKeys.VIEW);
		}

		return folder;
	}

	public static JournalFolder getFolder(PortletRequest portletRequest)
		throws PortalException {

		return getFolder(PortalUtil.getHttpServletRequest(portletRequest));
	}

	public static List<JournalFolder> getFolders(ResourceRequest request)
		throws Exception {

		long[] folderIds = ParamUtil.getLongValues(
			request, "rowIdsJournalFolder");

		List<JournalFolder> folders = new ArrayList<>();

		for (long folderId : folderIds) {
			folders.add(JournalFolderServiceUtil.getFolder(folderId));
		}

		return folders;
	}

	public static String getScript(UploadPortletRequest uploadPortletRequest)
		throws Exception {

		String fileScriptContent = _getFileScriptContent(uploadPortletRequest);

		if (Validator.isNotNull(fileScriptContent)) {
			return fileScriptContent;
		}

		return ParamUtil.getString(uploadPortletRequest, "scriptContent");
	}

	public static boolean hasArticle(ActionRequest actionRequest)
		throws Exception {

		String articleId = ParamUtil.getString(actionRequest, "articleId");

		if (Validator.isNull(articleId)) {
			String[] articleIds = StringUtil.split(
				ParamUtil.getString(actionRequest, "rowIds"));

			if (articleIds.length <= 0) {
				return false;
			}

			articleId = articleIds[0];
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

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

	private static String _getFileScriptContent(
			UploadPortletRequest uploadPortletRequest)
		throws Exception {

		File file = uploadPortletRequest.getFile("script");

		if (file == null) {
			return null;
		}

		String fileScriptContent = FileUtil.read(file);

		String contentType = MimeTypesUtil.getContentType(file);

		if (Validator.isNotNull(fileScriptContent) &&
			!_isValidContentType(contentType)) {

			throw new TemplateScriptException(
				"Invalid contentType " + contentType);
		}

		return fileScriptContent;
	}

	private static String _getLanguageId(
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

	private static boolean _isValidContentType(String contentType) {
		if (contentType.equals(ContentTypes.APPLICATION_XSLT_XML) ||
			contentType.startsWith(ContentTypes.TEXT)) {

			return true;
		}

		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(ActionUtil.class);

}