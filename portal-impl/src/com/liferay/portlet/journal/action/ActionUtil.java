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

package com.liferay.portlet.journal.action;

import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.documentlibrary.NoSuchFileEntryException;
import com.liferay.portlet.journal.NoSuchFolderException;
import com.liferay.portlet.journal.NoSuchStructureException;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.model.JournalFeed;
import com.liferay.portlet.journal.model.JournalFolder;
import com.liferay.portlet.journal.model.JournalFolderConstants;
import com.liferay.portlet.journal.model.JournalStructure;
import com.liferay.portlet.journal.model.JournalTemplate;
import com.liferay.portlet.journal.service.JournalArticleServiceUtil;
import com.liferay.portlet.journal.service.JournalFeedServiceUtil;
import com.liferay.portlet.journal.service.JournalFolderServiceUtil;
import com.liferay.portlet.journal.service.JournalStructureServiceUtil;
import com.liferay.portlet.journal.service.JournalTemplateServiceUtil;
import com.liferay.portlet.journal.service.permission.JournalPermission;
import com.liferay.portlet.journal.util.JournalUtil;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.PortletRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Brian Wing Shun Chan
 */
public class ActionUtil {

	public static final String VERSION_SEPARATOR = "_version_";

	public static void deleteArticle(
			ActionRequest actionRequest, String deleteArticleId)
		throws Exception {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)actionRequest.getAttribute(WebKeys.THEME_DISPLAY);

		long groupId = themeDisplay.getScopeGroupId();

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			JournalArticle.class.getName(), actionRequest);

		int pos = deleteArticleId.lastIndexOf(VERSION_SEPARATOR);

		String articleId = deleteArticleId;

		String articleURL = ParamUtil.getString(actionRequest, "articleURL");

		double version = 0;

		if (pos == -1) {
			JournalArticleServiceUtil.deleteArticle(
				groupId, articleId, articleURL, serviceContext);
		}
		else {
			articleId = articleId.substring(0, pos);
			version = GetterUtil.getDouble(
				deleteArticleId.substring(pos + VERSION_SEPARATOR.length()));

			JournalArticleServiceUtil.deleteArticle(
				groupId, articleId, version, articleURL, serviceContext);
		}

		JournalUtil.removeRecentArticle(actionRequest, articleId, version);
	}

	public static void expireArticle(
			ActionRequest actionRequest, String expireArticleId)
		throws Exception {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)actionRequest.getAttribute(WebKeys.THEME_DISPLAY);

		long groupId = themeDisplay.getScopeGroupId();

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			JournalArticle.class.getName(), actionRequest);

		int pos = expireArticleId.lastIndexOf(VERSION_SEPARATOR);

		String articleId = expireArticleId;

		String articleURL = ParamUtil.getString(actionRequest, "articleURL");

		double version = 0;

		if (pos == -1) {
			JournalArticleServiceUtil.expireArticle(
				groupId, articleId, articleURL, serviceContext);
		}
		else {
			articleId = articleId.substring(0, pos);
			version = GetterUtil.getDouble(
				expireArticleId.substring(pos + VERSION_SEPARATOR.length()));

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

	public static void getArticle(HttpServletRequest request) throws Exception {
		String cmd = ParamUtil.getString(request, Constants.CMD);

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		long groupId = themeDisplay.getScopeGroupId();
		long classNameId = ParamUtil.getLong(request, "classNameId");
		long classPK = ParamUtil.getLong(request, "classPK");
		String articleId = ParamUtil.getString(request, "articleId");
		String structureId = ParamUtil.getString(request, "structureId");

		JournalArticle article = null;

		if (!cmd.equals(Constants.ADD) && Validator.isNotNull(articleId)) {
			article = JournalArticleServiceUtil.getLatestArticle(
				groupId, articleId, WorkflowConstants.STATUS_ANY);
		}
		else if ((classNameId > 0) && (classPK > 0)) {
			String className = PortalUtil.getClassName(classNameId);

			article = JournalArticleServiceUtil.getLatestArticle(
				groupId, className, classPK);
		}
		else if (Validator.isNotNull(structureId)) {
			JournalStructure structure = null;

			try {
				structure = JournalStructureServiceUtil.getStructure(
					groupId, structureId);
			}
			catch (NoSuchStructureException nsse1) {
				if (groupId == themeDisplay.getCompanyGroupId()) {
					return;
				}

				try {
					structure = JournalStructureServiceUtil.getStructure(
						themeDisplay.getCompanyGroupId(), structureId);
				}
				catch (NoSuchStructureException nsse2) {
					return;
				}
			}

			article = JournalArticleServiceUtil.getArticle(
				groupId, JournalStructure.class.getName(), structure.getId());

			article.setNew(true);

			article.setId(0);
			article.setClassNameId(0);
			article.setClassPK(0);
			article.setArticleId(null);
			article.setVersion(0);
		}

		request.setAttribute(WebKeys.JOURNAL_ARTICLE, article);
	}

	public static void getArticle(PortletRequest portletRequest)
		throws Exception {

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			portletRequest);

		getArticle(request);

		JournalArticle article = (JournalArticle)portletRequest.getAttribute(
			WebKeys.JOURNAL_ARTICLE);

		JournalUtil.addRecentArticle(portletRequest, article);
	}

	public static void getArticles(HttpServletRequest request)
		throws Exception {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)request.getAttribute(WebKeys.THEME_DISPLAY);

		long groupId = themeDisplay.getScopeGroupId();

		List<JournalArticle> articles = new ArrayList<JournalArticle>();

		String[] articleIds = StringUtil.split(
			ParamUtil.getString(request, "articleIds"));

		for (String articleId : articleIds) {
			try {
				JournalArticle article = JournalArticleServiceUtil.getArticle(
					groupId, articleId);

				articles.add(article);
			}
			catch (NoSuchFileEntryException nsfee) {
			}
		}

		request.setAttribute(WebKeys.JOURNAL_ARTICLES, articles);
	}

	public static void getArticles(PortletRequest portletRequest)
		throws Exception {

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			portletRequest);

		getArticles(request);
	}

	public static void getFeed(HttpServletRequest request) throws Exception {
		long groupId = ParamUtil.getLong(request, "groupId");
		String feedId = ParamUtil.getString(request, "feedId");

		JournalFeed feed = null;

		if (Validator.isNotNull(feedId)) {
			feed = JournalFeedServiceUtil.getFeed(groupId, feedId);
		}

		request.setAttribute(WebKeys.JOURNAL_FEED, feed);
	}

	public static void getFeed(PortletRequest portletRequest) throws Exception {
		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			portletRequest);

		getFeed(request);
	}

	public static void getFolder(HttpServletRequest request) throws Exception {
		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		long folderId = ParamUtil.getLong(request, "folderId");

		JournalFolder folder = null;

		if ((folderId > 0) &&
			(folderId != JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID)) {

			folder = JournalFolderServiceUtil.getFolder(folderId);
		}
		else {
			JournalPermission.check(
				themeDisplay.getPermissionChecker(),
				themeDisplay.getScopeGroupId(), ActionKeys.VIEW);
		}

		request.setAttribute(WebKeys.JOURNAL_FOLDER, folder);
	}

	public static void getFolder(PortletRequest portletRequest)
		throws Exception {

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			portletRequest);

		getFolder(request);
	}

	public static void getFolders(HttpServletRequest request) throws Exception {
		long[] folderIds = StringUtil.split(
			ParamUtil.getString(request, "folderIds"), 0L);

		List<JournalFolder> folders = new ArrayList<JournalFolder>();

		for (long folderId : folderIds) {
			try {
				JournalFolder folder = JournalFolderServiceUtil.getFolder(
					folderId);

				folders.add(folder);
			}
			catch (NoSuchFolderException nsfee) {
			}
		}

		request.setAttribute(WebKeys.JOURNAL_FOLDERS, folders);
	}

	public static void getFolders(PortletRequest portletRequest)
		throws Exception {

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			portletRequest);

		getFolders(request);
	}

	public static void getStructure(HttpServletRequest request)
		throws Exception {

		long groupId = ParamUtil.getLong(request, "groupId");
		String structureId = ParamUtil.getString(request, "structureId");

		JournalStructure structure = null;

		if (Validator.isNotNull(structureId)) {
			structure = JournalStructureServiceUtil.getStructure(
				groupId, structureId);
		}

		request.setAttribute(WebKeys.JOURNAL_STRUCTURE, structure);
	}

	public static void getStructure(PortletRequest portletRequest)
		throws Exception {

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			portletRequest);

		getStructure(request);

		JournalStructure structure =
			(JournalStructure)portletRequest.getAttribute(
				WebKeys.JOURNAL_STRUCTURE);

		JournalUtil.addRecentStructure(portletRequest, structure);
	}

	public static void getTemplate(HttpServletRequest request)
		throws Exception {

		long groupId = ParamUtil.getLong(request, "groupId");
		String templateId = ParamUtil.getString(request, "templateId");

		JournalTemplate template = null;

		if (Validator.isNotNull(templateId)) {
			template = JournalTemplateServiceUtil.getTemplate(
				groupId, templateId);
		}

		request.setAttribute(WebKeys.JOURNAL_TEMPLATE, template);
	}

	public static void getTemplate(PortletRequest portletRequest)
		throws Exception {

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			portletRequest);

		getTemplate(request);

		JournalTemplate template = (JournalTemplate)portletRequest.getAttribute(
			WebKeys.JOURNAL_TEMPLATE);

		JournalUtil.addRecentTemplate(portletRequest, template);
	}

}