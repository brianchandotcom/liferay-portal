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

package com.liferay.portlet.journal.service.impl;

import com.liferay.portal.kernel.dao.orm.QueryDefinition;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.PortletRequestModel;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.model.JournalArticleConstants;
import com.liferay.portlet.journal.model.JournalFolderConstants;
import com.liferay.portlet.journal.service.base.JournalArticleServiceBaseImpl;
import com.liferay.portlet.journal.service.permission.JournalArticlePermission;
import com.liferay.portlet.journal.service.permission.JournalFolderPermission;
import com.liferay.portlet.journal.service.permission.JournalPermission;

import java.io.File;
import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Provides the remote service for accessing, adding, deleting, and updating web
 * content articles. Its methods include permission checks.
 *
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 * @author Levente Hudák
 * @see    com.liferay.portlet.journal.service.impl.JournalArticleLocalServiceImpl
 */
public class JournalArticleServiceImpl extends JournalArticleServiceBaseImpl {

	/**
	 * Adds a web content article with additional parameters.
	 *
	 * @param  groupId the primary key of the web content article's group
	 * @param  folderId the primary key of the web content article folder
	 * @param  classNameId the primary key of the DDMStructure class if the web
	 *         content article is related to a DDM structure, the primary key of
	 *         the class name associated with the article, or {@link
	 *         JournalArticleConstants#CLASSNAME_ID_DEFAULT} otherwise
	 * @param  classPK the primary key of the DDM structure, if the primary key
	 *         of the DDMStructure class is given as the
	 *         <code>classNameId</code> parameter, the primary key of the class
	 *         associated with the web content article, or <code>0</code>
	 *         otherwise
	 * @param  articleId the primary key of the web content article
	 * @param  autoArticleId whether to auto generate the web content article ID
	 * @param  titleMap the web content article's locales and localized titles
	 * @param  descriptionMap the web content article's locales and localized
	 *         descriptions
	 * @param  content the HTML content wrapped in XML. For more information,
	 *         see the content example in the class description for {@link
	 *         JournalArticleLocalServiceImpl}.
	 * @param  type the structure's type, if the web content article is related
	 *         to a DDM structure. For more information, see {@link
	 *         com.liferay.portlet.dynamicdatamapping.model.DDMStructureConstants}.
	 * @param  ddmStructureKey the primary key of the web content article's DDM
	 *         structure, if the article is related to a DDM structure, or
	 *         <code>null</code> otherwise
	 * @param  ddmTemplateKey the primary key of the web content article's DDM
	 *         template
	 * @param  layoutUuid the unique string identifying the web content
	 *         article's display page
	 * @param  displayDateMonth the month the web content article is set to
	 *         display
	 * @param  displayDateDay the calendar day the web content article is set to
	 *         display
	 * @param  displayDateYear the year the web content article is set to
	 *         display
	 * @param  displayDateHour the hour the web content article is set to
	 *         display
	 * @param  displayDateMinute the minute the web content article is set to
	 *         display
	 * @param  expirationDateMonth the month the web content article is set to
	 *         expire
	 * @param  expirationDateDay the calendar day the web content article is set
	 *         to expire
	 * @param  expirationDateYear the year the web content article is set to
	 *         expire
	 * @param  expirationDateHour the hour the web content article is set to
	 *         expire
	 * @param  expirationDateMinute the minute the web content article is set to
	 *         expire
	 * @param  neverExpire whether the web content article is not set to auto
	 *         expire
	 * @param  reviewDateMonth the month the web content article is set for
	 *         review
	 * @param  reviewDateDay the calendar day the web content article is set for
	 *         review
	 * @param  reviewDateYear the year the web content article is set for review
	 * @param  reviewDateHour the hour the web content article is set for review
	 * @param  reviewDateMinute the minute the web content article is set for
	 *         review
	 * @param  neverReview whether the web content article is not set for review
	 * @param  indexable whether the web content article is searchable
	 * @param  smallImage whether the web content article has a small image
	 * @param  smallImageURL the web content article's small image URL
	 * @param  smallFile the web content article's small image file
	 * @param  images the web content's images
	 * @param  articleURL the web content article's accessible URL
	 * @param  serviceContext the service context to be applied. Can set the
	 *         UUID, creation date, modification date, expando bridge
	 *         attributes, guest permissions, group permissions, asset category
	 *         IDs, asset tag names, asset link entry IDs, the "urlTitle"
	 *         attribute, and workflow actions for the web content article. Can
	 *         also set whether to add the default guest and group permissions.
	 * @return the web content article
	 * @throws PortalException if the user did not have permission to add the
	 *         web content article or if a portal exception occurred
	 */
	@Override
	public JournalArticle addArticle(
			long groupId, long folderId, long classNameId, long classPK,
			String articleId, boolean autoArticleId,
			Map<Locale, String> titleMap, Map<Locale, String> descriptionMap,
			String content, String type, String ddmStructureKey,
			String ddmTemplateKey, String layoutUuid, int displayDateMonth,
			int displayDateDay, int displayDateYear, int displayDateHour,
			int displayDateMinute, int expirationDateMonth,
			int expirationDateDay, int expirationDateYear,
			int expirationDateHour, int expirationDateMinute,
			boolean neverExpire, int reviewDateMonth, int reviewDateDay,
			int reviewDateYear, int reviewDateHour, int reviewDateMinute,
			boolean neverReview, boolean indexable, boolean smallImage,
			String smallImageURL, File smallFile, Map<String, byte[]> images,
			String articleURL, ServiceContext serviceContext)
		throws PortalException {

		JournalFolderPermission.check(
			getPermissionChecker(), groupId, folderId, ActionKeys.ADD_ARTICLE);

		return journalArticleLocalService.addArticle(
			getUserId(), groupId, folderId, classNameId, classPK, articleId,
			autoArticleId, JournalArticleConstants.VERSION_DEFAULT, titleMap,
			descriptionMap, content, type, ddmStructureKey, ddmTemplateKey,
			layoutUuid, displayDateMonth, displayDateDay, displayDateYear,
			displayDateHour, displayDateMinute, expirationDateMonth,
			expirationDateDay, expirationDateYear, expirationDateHour,
			expirationDateMinute, neverExpire, reviewDateMonth, reviewDateDay,
			reviewDateYear, reviewDateHour, reviewDateMinute, neverReview,
			indexable, smallImage, smallImageURL, smallFile, images, articleURL,
			serviceContext);
	}

	/**
	 * Adds a web content article without any images.
	 *
	 * @param  groupId the primary key of the web content article's group
	 * @param  folderId the primary key of the web content article folder
	 * @param  classNameId the primary key of the DDMStructure class if the web
	 *         content article is related to a DDM structure, the primary key of
	 *         the class name associated with the article, or {@link
	 *         JournalArticleConstants#CLASSNAME_ID_DEFAULT} otherwise
	 * @param  classPK the primary key of the DDM structure, if the primary key
	 *         of the DDMStructure class is given as the
	 *         <code>classNameId</code> parameter, the primary key of the class
	 *         associated with the web content article, or <code>0</code>
	 *         otherwise
	 * @param  articleId the primary key of the web content article
	 * @param  autoArticleId whether to auto generate the web content article ID
	 * @param  titleMap the web content article's locales and localized titles
	 * @param  descriptionMap the web content article's locales and localized
	 *         descriptions
	 * @param  content the HTML content wrapped in XML. For more information,
	 *         see the content example in the class description for {@link
	 *         JournalArticleLocalServiceImpl}.
	 * @param  type the structure's type, if the web content article is related
	 *         to a DDM structure. For more information, see {@link
	 *         com.liferay.portlet.dynamicdatamapping.model.DDMStructureConstants}.
	 * @param  ddmStructureKey the primary key of the web content article's DDM
	 *         structure, if the article is related to a DDM structure, or
	 *         <code>null</code> otherwise
	 * @param  ddmTemplateKey the primary key of the web content article's DDM
	 *         template
	 * @param  layoutUuid the unique string identifying the web content
	 *         article's display page
	 * @param  displayDateMonth the month the web content article is set to
	 *         display
	 * @param  displayDateDay the calendar day the web content article is set to
	 *         display
	 * @param  displayDateYear the year the web content article is set to
	 *         display
	 * @param  displayDateHour the hour the web content article is set to
	 *         display
	 * @param  displayDateMinute the minute the web content article is set to
	 *         display
	 * @param  expirationDateMonth the month the web content article is set to
	 *         expire
	 * @param  expirationDateDay the calendar day the web content article is set
	 *         to expire
	 * @param  expirationDateYear the year the web content article is set to
	 *         expire
	 * @param  expirationDateHour the hour the web content article is set to
	 *         expire
	 * @param  expirationDateMinute the minute the web content article is set to
	 *         expire
	 * @param  neverExpire whether the web content article is not set to auto
	 *         expire
	 * @param  reviewDateMonth the month the web content article is set for
	 *         review
	 * @param  reviewDateDay the calendar day the web content article is set for
	 *         review
	 * @param  reviewDateYear the year the web content article is set for review
	 * @param  reviewDateHour the hour the web content article is set for review
	 * @param  reviewDateMinute the minute the web content article is set for
	 *         review
	 * @param  neverReview whether the web content article is not set for review
	 * @param  indexable whether the web content article is searchable
	 * @param  articleURL the web content article's accessible URL
	 * @param  serviceContext the service context to be applied. Can set the
	 *         UUID, creation date, modification date, expando bridge
	 *         attributes, guest permissions, group permissions, asset category
	 *         IDs, asset tag names, asset link entry IDs, the "urlTitle"
	 *         attribute, and workflow actions for the web content article. Can
	 *         also set whether to add the default guest and group permissions.
	 * @return the web content article
	 * @throws PortalException if the user did not have permission to add the
	 *         web content article or if a portal exception occurred
	 */
	@Override
	public JournalArticle addArticle(
			long groupId, long folderId, long classNameId, long classPK,
			String articleId, boolean autoArticleId,
			Map<Locale, String> titleMap, Map<Locale, String> descriptionMap,
			String content, String type, String ddmStructureKey,
			String ddmTemplateKey, String layoutUuid, int displayDateMonth,
			int displayDateDay, int displayDateYear, int displayDateHour,
			int displayDateMinute, int expirationDateMonth,
			int expirationDateDay, int expirationDateYear,
			int expirationDateHour, int expirationDateMinute,
			boolean neverExpire, int reviewDateMonth, int reviewDateDay,
			int reviewDateYear, int reviewDateHour, int reviewDateMinute,
			boolean neverReview, boolean indexable, String articleURL,
			ServiceContext serviceContext)
		throws PortalException {

		JournalFolderPermission.check(
			getPermissionChecker(), groupId, folderId, ActionKeys.ADD_ARTICLE);

		return journalArticleLocalService.addArticle(
			getUserId(), groupId, folderId, classNameId, classPK, articleId,
			autoArticleId, JournalArticleConstants.VERSION_DEFAULT, titleMap,
			descriptionMap, content, type, ddmStructureKey, ddmTemplateKey,
			layoutUuid, displayDateMonth, displayDateDay, displayDateYear,
			displayDateHour, displayDateMinute, expirationDateMonth,
			expirationDateDay, expirationDateYear, expirationDateHour,
			expirationDateMinute, neverExpire, reviewDateMonth, reviewDateDay,
			reviewDateYear, reviewDateHour, reviewDateMinute, neverReview,
			indexable, false, null, null, null, articleURL, serviceContext);
	}

	/**
	 * Copies the web content article matching the group, article ID, and
	 * version. This method creates a new article, extracting all the values
	 * from the old one and updating its article ID.
	 *
	 * @param  groupId the primary key of the web content article's group
	 * @param  oldArticleId the primary key of the old web content article
	 * @param  newArticleId the primary key of the new web content article
	 * @param  autoArticleId whether to auto-generate the web content article ID
	 * @param  version the web content article's version
	 * @return the new web content article
	 * @throws PortalException if the user did not have permission to add the
	 *         copy the web content article, if a matching web content article
	 *         could not be found, or if a portal exception occurred
	 */
	@Override
	public JournalArticle copyArticle(
			long groupId, String oldArticleId, String newArticleId,
			boolean autoArticleId, double version)
		throws PortalException {

		JournalArticle article = journalArticleLocalService.getArticle(
			groupId, oldArticleId);

		JournalFolderPermission.check(
			getPermissionChecker(), groupId, article.getFolderId(),
			ActionKeys.ADD_ARTICLE);

		return journalArticleLocalService.copyArticle(
			getUserId(), groupId, oldArticleId, newArticleId, autoArticleId,
			version);
	}

	/**
	 * Deletes the web content article and its resources matching the group,
	 * article ID, and version, optionally sending email notifying denial of the
	 * web content article if it had not yet been approved.
	 *
	 * @param  groupId the primary key of the web content article's group
	 * @param  articleId the primary key of the web content article
	 * @param  version the web content article's version
	 * @param  articleURL the web content article's accessible URL
	 * @param  serviceContext the service context to be applied. Can set the
	 *         portlet preferences that include email information to notify
	 *         recipients of the unapproved web content article's denial.
	 * @throws PortalException if the user did not have permission to delete the
	 *         web content article, if a matching web content article could not
	 *         be found, or if a portal exception occurred
	 */
	@Override
	public void deleteArticle(
			long groupId, String articleId, double version, String articleURL,
			ServiceContext serviceContext)
		throws PortalException {

		JournalArticlePermission.check(
			getPermissionChecker(), groupId, articleId, version,
			ActionKeys.DELETE);

		journalArticleLocalService.deleteArticle(
			groupId, articleId, version, articleURL, serviceContext);
	}

	/**
	 * Deletes all web content articles and their resources matching the group
	 * and article ID, optionally sending email notifying denial of article if
	 * it had not yet been approved.
	 *
	 * @param  groupId the primary key of the web content article's group
	 * @param  articleId the primary key of the web content article
	 * @param  articleURL the web content article's accessible URL
	 * @param  serviceContext the service context to be applied. Can set the
	 *         portlet preferences that include email information to notify
	 *         recipients of the unapproved web content article's denial.
	 * @throws PortalException if the user did not have permission to delete the
	 *         web content article or if a portal exception occurred
	 */
	@Override
	public void deleteArticle(
			long groupId, String articleId, String articleURL,
			ServiceContext serviceContext)
		throws PortalException {

		JournalArticlePermission.check(
			getPermissionChecker(), groupId, articleId, ActionKeys.DELETE);

		journalArticleLocalService.deleteArticle(
			groupId, articleId, serviceContext);
	}

	/**
	 * Expires the web content article matching the group, article ID, and
	 * version.
	 *
	 * @param  groupId the primary key of the web content article's group
	 * @param  articleId the primary key of the web content article
	 * @param  version the web content article's version
	 * @param  articleURL the web content article's accessible URL
	 * @param  serviceContext the service context to be applied. Can set the
	 *         modification date, status date, portlet preferences, and can set
	 *         whether to add the default command update for the web content
	 *         article. With respect to social activities, by setting the
	 *         service context's command to {@link
	 *         com.liferay.portal.kernel.util.Constants#UPDATE}, the invocation
	 *         is considered a web content update activity; otherwise it is
	 *         considered a web content add activity.
	 * @return the web content article
	 * @throws PortalException if the user did not have permission to expire the
	 *         web content article, if a matching web content article could not
	 *         be found, or if a portal exception occurred
	 */
	@Override
	public JournalArticle expireArticle(
			long groupId, String articleId, double version, String articleURL,
			ServiceContext serviceContext)
		throws PortalException {

		JournalArticlePermission.check(
			getPermissionChecker(), groupId, articleId, version,
			ActionKeys.EXPIRE);

		return journalArticleLocalService.expireArticle(
			getUserId(), groupId, articleId, version, articleURL,
			serviceContext);
	}

	/**
	 * Expires the web content article matching the group and article ID,
	 * expiring all of its versions if the
	 * <code>journal.article.expire.all.versions</code> portal property is
	 * <code>true</code>, otherwise expiring only its latest approved version.
	 *
	 * @param  groupId the primary key of the web content article's group
	 * @param  articleId the primary key of the web content article
	 * @param  articleURL the web content article's accessible URL
	 * @param  serviceContext the service context to be applied. Can set the
	 *         modification date, status date, portlet preferences, and can set
	 *         whether to add the default command update for the web content
	 *         article. With respect to social activities, by setting the
	 *         service context's command to {@link
	 *         com.liferay.portal.kernel.util.Constants#UPDATE}, the invocation
	 *         is considered a web content update activity; otherwise it is
	 *         considered a web content add activity.
	 * @throws PortalException if the user did not have permission to expire the
	 *         web content article, if a matching web content article could not
	 *         be found, or if a portal exception occurred
	 */
	@Override
	public void expireArticle(
			long groupId, String articleId, String articleURL,
			ServiceContext serviceContext)
		throws PortalException {

		JournalArticlePermission.check(
			getPermissionChecker(), groupId, articleId, ActionKeys.EXPIRE);

		journalArticleLocalService.expireArticle(
			getUserId(), groupId, articleId, articleURL, serviceContext);
	}

	/**
	 * Returns the web content article with the ID.
	 *
	 * @param  id the primary key of the web content article
	 * @return the web content article with the ID
	 * @throws PortalException if a matching web content article could not be
	 *         found or if the user did not have permission to view the web
	 *         content article
	 */
	@Override
	public JournalArticle getArticle(long id) throws PortalException {
		JournalArticle article = journalArticleLocalService.getArticle(id);

		JournalArticlePermission.check(
			getPermissionChecker(), article, ActionKeys.VIEW);

		return article;
	}

	/**
	 * Returns the latest approved web content article, or the latest unapproved
	 * article if none are approved. Both approved and unapproved articles must
	 * match the group and article ID.
	 *
	 * @param  groupId the primary key of the web content article's group
	 * @param  articleId the primary key of the web content article
	 * @return the matching web content article
	 * @throws PortalException if the user did not have permission to view the
	 *         web content article or if a matching web content article could
	 *         not be found
	 */
	@Override
	public JournalArticle getArticle(long groupId, String articleId)
		throws PortalException {

		JournalArticlePermission.check(
			getPermissionChecker(), groupId, articleId, ActionKeys.VIEW);

		return journalArticleLocalService.getArticle(groupId, articleId);
	}

	/**
	 * Returns the web content article matching the group, article ID, and
	 * version.
	 *
	 * @param  groupId the primary key of the web content article's group
	 * @param  articleId the primary key of the web content article
	 * @param  version the web content article's version
	 * @return the matching web content article
	 * @throws PortalException if the user did not have permission to view the
	 *         web content article or if a matching web content article could
	 *         not be found
	 */
	@Override
	public JournalArticle getArticle(
			long groupId, String articleId, double version)
		throws PortalException {

		JournalArticlePermission.check(
			getPermissionChecker(), groupId, articleId, version,
			ActionKeys.VIEW);

		return journalArticleLocalService.getArticle(
			groupId, articleId, version);
	}

	/**
	 * Returns the web content article matching the group, class name, and class
	 * PK.
	 *
	 * @param  groupId the primary key of the web content article's group
	 * @param  className the DDMStructure class name if the web content article
	 *         is related to a DDM structure, the primary key of the class name
	 *         associated with the article, or {@link
	 *         JournalArticleConstants#CLASSNAME_ID_DEFAULT} otherwise
	 * @param  classPK the primary key of the DDM structure, if the the
	 *         DDMStructure class name is given as the <code>className</code>
	 *         parameter, the primary key of the class associated with the web
	 *         content article, or <code>0</code> otherwise
	 * @return the matching web content article
	 * @throws PortalException if a matching web content article could not be
	 *         found or if the user did not have permission to view the web
	 *         content article
	 */
	@Override
	public JournalArticle getArticle(
			long groupId, String className, long classPK)
		throws PortalException {

		JournalArticle article = journalArticleLocalService.getArticle(
			groupId, className, classPK);

		JournalArticlePermission.check(
			getPermissionChecker(), groupId, article.getArticleId(),
			article.getVersion(), ActionKeys.VIEW);

		return article;
	}

	/**
	 * Returns the latest web content article that is approved, or the latest
	 * unapproved article if none are approved. Both approved and unapproved
	 * articles must match the group and URL title.
	 *
	 * @param  groupId the primary key of the web content article's group
	 * @param  urlTitle the web content article's accessible URL title
	 * @return the matching web content article
	 * @throws PortalException if the user did not have permission to view the
	 *         web content article or if a portal exception occurred
	 */
	@Override
	public JournalArticle getArticleByUrlTitle(long groupId, String urlTitle)
		throws PortalException {

		JournalArticle article =
			journalArticleLocalService.getArticleByUrlTitle(groupId, urlTitle);

		JournalArticlePermission.check(
			getPermissionChecker(), article, ActionKeys.VIEW);

		return article;
	}

	/**
	 * Returns the web content from the web content article matching the group,
	 * article ID, and version.
	 *
	 * @param  groupId the primary key of the web content article's group
	 * @param  articleId the primary key of the web content article
	 * @param  version the web content article's version
	 * @param  languageId the primary key of the language translation to get
	 * @param  portletRequestModel the portlet request model
	 * @param  themeDisplay the theme display
	 * @return the matching web content
	 * @throws PortalException if the user did not have permission to view the
	 *         web content article, if a matching web content article or DDM
	 *         template could not be found, or if a portal exception occurred
	 */
	@Override
	public String getArticleContent(
			long groupId, String articleId, double version, String languageId,
			PortletRequestModel portletRequestModel, ThemeDisplay themeDisplay)
		throws PortalException {

		JournalArticlePermission.check(
			getPermissionChecker(), groupId, articleId, version,
			ActionKeys.VIEW);

		return journalArticleLocalService.getArticleContent(
			groupId, articleId, version, null, null, languageId,
			portletRequestModel, themeDisplay);
	}

	/**
	 * Returns the web content from the web content article matching the group,
	 * article ID, and version.
	 *
	 * @param      groupId the primary key of the web content article's group
	 * @param      articleId the primary key of the web content article
	 * @param      version the web content article's version
	 * @param      languageId the primary key of the language translation to get
	 * @param      themeDisplay the theme display
	 * @return     the matching web content
	 * @throws     PortalException if the user did not have permission to view
	 *             the web content article, if a matching web content article or
	 *             DDM template could not be found, or if a portal exception
	 *             occurred
	 * @deprecated As of 7.0.0, replaced by {@link #getArticleContent(long,
	 *             String, double, String, PortletRequestModel, ThemeDisplay)}
	 */
	@Deprecated
	@Override
	public String getArticleContent(
			long groupId, String articleId, double version, String languageId,
			ThemeDisplay themeDisplay)
		throws PortalException {

		JournalArticlePermission.check(
			getPermissionChecker(), groupId, articleId, version,
			ActionKeys.VIEW);

		return journalArticleLocalService.getArticleContent(
			groupId, articleId, version, null, languageId, themeDisplay);
	}

	/**
	 * Returns the latest web content from the web content article matching the
	 * group and article ID.
	 *
	 * @param  groupId the primary key of the web content article's group
	 * @param  articleId the primary key of the web content article
	 * @param  languageId the primary key of the language translation to get
	 * @param  portletRequestModel the portlet request model
	 * @param  themeDisplay the theme display
	 * @return the matching web content
	 * @throws PortalException if the user did not have permission to view the
	 *         web content article, if a matching web content article or DDM
	 *         template could not be found, or if a portal exception occurred
	 */
	@Override
	public String getArticleContent(
			long groupId, String articleId, String languageId,
			PortletRequestModel portletRequestModel, ThemeDisplay themeDisplay)
		throws PortalException {

		JournalArticlePermission.check(
			getPermissionChecker(), groupId, articleId, ActionKeys.VIEW);

		return journalArticleLocalService.getArticleContent(
			groupId, articleId, null, null, languageId, portletRequestModel,
			themeDisplay);
	}

	/**
	 * Returns the latest web content from the web content article matching the
	 * group and article ID.
	 *
	 * @param      groupId the primary key of the web content article's group
	 * @param      articleId the primary key of the web content article
	 * @param      languageId the primary key of the language translation to get
	 * @param      themeDisplay the theme display
	 * @return     the matching web content
	 * @throws     PortalException if the user did not have permission to view
	 *             the web content article, if a matching web content article or
	 *             DDM template could not be found, or if a portal exception
	 *             occurred
	 * @deprecated As of 7.0.0, replaced by {@link #getArticleContent(long,
	 *             String, String, PortletRequestModel, ThemeDisplay)}
	 */
	@Deprecated
	@Override
	public String getArticleContent(
			long groupId, String articleId, String languageId,
			ThemeDisplay themeDisplay)
		throws PortalException {

		JournalArticlePermission.check(
			getPermissionChecker(), groupId, articleId, ActionKeys.VIEW);

		return journalArticleLocalService.getArticleContent(
			groupId, articleId, null, languageId, themeDisplay);
	}

	/**
	 * Returns all the web content articles matching the group and folder.
	 *
	 * @param  groupId the primary key of the web content article's group
	 * @param  folderId the primary key of the web content article folder
	 * @return the matching web content articles
	 */
	@Override
	public List<JournalArticle> getArticles(long groupId, long folderId) {
		QueryDefinition queryDefinition = new QueryDefinition(
			WorkflowConstants.STATUS_ANY);

		List<Long> folderIds = new ArrayList<Long>();

		folderIds.add(folderId);

		return journalArticleFinder.filterFindByG_F(
			groupId, folderIds, queryDefinition);
	}

	/**
	 * Returns an ordered range of all the web content articles matching the
	 * group and folder.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end -
	 * start</code> instances. <code>start</code> and <code>end</code> are not
	 * primary keys, they are indexes in the result set. Thus, <code>0</code>
	 * refers to the first result in the set. Setting both <code>start</code>
	 * and <code>end</code> to {@link
	 * com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full
	 * result set.
	 * </p>
	 *
	 * @param  groupId the primary key of the web content article's group
	 * @param  folderId the primary key of the web content article folder
	 * @param  start the lower bound of the range of web content articles to
	 *         return
	 * @param  end the upper bound of the range of web content articles to
	 *         return (not inclusive)
	 * @param  obc the comparator to order the web content articles
	 * @return the matching web content articles
	 */
	@Override
	public List<JournalArticle> getArticles(
		long groupId, long folderId, int start, int end,
		OrderByComparator obc) {

		QueryDefinition queryDefinition = new QueryDefinition(
			WorkflowConstants.STATUS_ANY, start, end, obc);

		List<Long> folderIds = new ArrayList<Long>();

		folderIds.add(folderId);

		return journalArticleFinder.filterFindByG_F(
			groupId, folderIds, queryDefinition);
	}

	/**
	 * Returns an ordered range of all the web content articles matching the
	 * group and article ID.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end -
	 * start</code> instances. <code>start</code> and <code>end</code> are not
	 * primary keys, they are indexes in the result set. Thus, <code>0</code>
	 * refers to the first result in the set. Setting both <code>start</code>
	 * and <code>end</code> to {@link
	 * com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full
	 * result set.
	 * </p>
	 *
	 * @param  groupId the primary key of the web content article's group
	 * @param  articleId the primary key of the web content article
	 * @param  start the lower bound of the range of web content articles to
	 *         return
	 * @param  end the upper bound of the range of web content articles to
	 *         return (not inclusive)
	 * @param  obc the comparator to order the web content articles
	 * @return the range of matching web content articles ordered by the
	 *         comparator
	 */
	@Override
	public List<JournalArticle> getArticlesByArticleId(
		long groupId, String articleId, int start, int end,
		OrderByComparator obc) {

		return journalArticlePersistence.filterFindByG_A(
			groupId, articleId, start, end, obc);
	}

	/**
	 * Returns all the web content articles matching the group and layout UUID.
	 *
	 * @param  groupId the primary key of the web content article's group
	 * @param  layoutUuid the unique string identifying the web content
	 *         article's display page
	 * @return the matching web content articles
	 */
	@Override
	public List<JournalArticle> getArticlesByLayoutUuid(
		long groupId, String layoutUuid) {

		return journalArticlePersistence.filterFindByG_L(groupId, layoutUuid);
	}

	/**
	 * Returns an ordered range of all the web content articles matching the
	 * group, class name ID, DDM structure key, and workflow status.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end -
	 * start</code> instances. <code>start</code> and <code>end</code> are not
	 * primary keys, they are indexes in the result set. Thus, <code>0</code>
	 * refers to the first result in the set. Setting both <code>start</code>
	 * and <code>end</code> to {@link
	 * com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full
	 * result set.
	 * </p>
	 *
	 * @param  groupId the primary key of the web content article's group
	 * @param  classNameId the primary key of the DDMStructure class if the web
	 *         content article is related to a DDM structure, the primary key of
	 *         the class name associated with the article, or {@link
	 *         JournalArticleConstants#CLASSNAME_ID_DEFAULT} otherwise
	 * @param  ddmStructureKey the primary key of the web content article's DDM
	 *         structure
	 * @param  status the web content article's workflow status. For more
	 *         information see {@link WorkflowConstants} for constants starting
	 *         with the "STATUS_" prefix.
	 * @param  start the lower bound of the range of web content articles to
	 *         return
	 * @param  end the upper bound of the range of web content articles to
	 *         return (not inclusive)
	 * @param  obc the comparator to order the web content articles
	 * @return the range of matching web content articles ordered by the
	 *         comparator
	 */
	@Override
	public List<JournalArticle> getArticlesByStructureId(
		long groupId, long classNameId, String ddmStructureKey, int status,
		int start, int end, OrderByComparator obc) {

		QueryDefinition queryDefinition = new QueryDefinition(
			status, start, end, obc);

		return journalArticleFinder.filterFindByG_C_S(
			groupId, classNameId, ddmStructureKey, queryDefinition);
	}

	/**
	 * Returns an ordered range of all the web content articles matching the
	 * group, default class name ID, and DDM structure key.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end -
	 * start</code> instances. <code>start</code> and <code>end</code> are not
	 * primary keys, they are indexes in the result set. Thus, <code>0</code>
	 * refers to the first result in the set. Setting both <code>start</code>
	 * and <code>end</code> to {@link
	 * com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full
	 * result set.
	 * </p>
	 *
	 * @param  groupId the primary key of the web content article's group
	 * @param  ddmStructureKey the primary key of the web content article's DDM
	 *         structure
	 * @param  start the lower bound of the range of web content articles to
	 *         return
	 * @param  end the upper bound of the range of web content articles to
	 *         return (not inclusive)
	 * @param  obc the comparator to order the web content articles
	 * @return the range of matching web content articles ordered by the
	 *         comparator
	 */
	@Override
	public List<JournalArticle> getArticlesByStructureId(
		long groupId, String ddmStructureKey, int start, int end,
		OrderByComparator obc) {

		QueryDefinition queryDefinition = new QueryDefinition(
			WorkflowConstants.STATUS_ANY, start, end, obc);

		return journalArticleFinder.filterFindByG_C_S(
			groupId, JournalArticleConstants.CLASSNAME_ID_DEFAULT,
			ddmStructureKey, queryDefinition);
	}

	/**
	 * Returns the number of web content articles matching the group and folder.
	 *
	 * @param  groupId the primary key of the web content article's group
	 * @param  folderId the primary key of the web content article folder
	 * @return the number of matching web content articles
	 */
	@Override
	public int getArticlesCount(long groupId, long folderId) {
		return getArticlesCount(
			groupId, folderId, WorkflowConstants.STATUS_ANY);
	}

	@Override
	public int getArticlesCount(long groupId, long folderId, int status) {
		QueryDefinition queryDefinition = new QueryDefinition(status);

		List<Long> folderIds = new ArrayList<Long>();

		folderIds.add(folderId);

		return journalArticleFinder.filterCountByG_F(
			groupId, folderIds, queryDefinition);
	}

	/**
	 * Returns the number of web content articles matching the group and article
	 * ID.
	 *
	 * @param  groupId the primary key of the web content article's group
	 * @param  articleId the primary key of the web content article
	 * @return the number of matching web content articles
	 */
	@Override
	public int getArticlesCountByArticleId(long groupId, String articleId) {
		return journalArticlePersistence.filterCountByG_A(groupId, articleId);
	}

	/**
	 * Returns the number of web content articles matching the group, class name
	 * ID, DDM structure key, and workflow status.
	 *
	 * @param  groupId the primary key of the web content article's group
	 * @param  classNameId the primary key of the DDMStructure class if the web
	 *         content article is related to a DDM structure, the primary key of
	 *         the class name associated with the article, or {@link
	 *         JournalArticleConstants#CLASSNAME_ID_DEFAULT} otherwise
	 * @param  ddmStructureKey the primary key of the web content article's DDM
	 *         structure
	 * @param  status the web content article's workflow status. For more
	 *         information see {@link WorkflowConstants} for constants starting
	 *         with the "STATUS_" prefix.
	 * @return the number of matching web content articles
	 */
	@Override
	public int getArticlesCountByStructureId(
		long groupId, long classNameId, String ddmStructureKey, int status) {

		return journalArticleFinder.filterCountByG_C_S(
			groupId, classNameId, ddmStructureKey, new QueryDefinition(status));
	}

	/**
	 * Returns the number of web content articles matching the group, default
	 * class name ID, and DDM structure key.
	 *
	 * @param  groupId the primary key of the web content article's group
	 * @param  ddmStructureKey the primary key of the web content article's DDM
	 *         structure
	 * @return the number of matching web content articles
	 */
	@Override
	public int getArticlesCountByStructureId(
		long groupId, String ddmStructureKey) {

		return getArticlesCountByStructureId(
			groupId, JournalArticleConstants.CLASSNAME_ID_DEFAULT,
			ddmStructureKey, WorkflowConstants.STATUS_ANY);
	}

	/**
	 * Returns the web content article matching the URL title that is currently
	 * displayed or next to be displayed if no article is currently displayed.
	 *
	 * @param  groupId the primary key of the web content article's group
	 * @param  urlTitle the web content article's accessible URL title
	 * @return the web content article matching the URL title that is currently
	 *         displayed, or next one to be displayed if no version of the
	 *         article is currently displayed
	 * @throws PortalException if the user did not have permission to view the
	 *         web content article or if no approved matching web content
	 *         articles could be found
	 */
	@Override
	public JournalArticle getDisplayArticleByUrlTitle(
			long groupId, String urlTitle)
		throws PortalException {

		JournalArticle article =
			journalArticleLocalService.getDisplayArticleByUrlTitle(
				groupId, urlTitle);

		JournalArticlePermission.check(
			getPermissionChecker(), article, ActionKeys.VIEW);

		return article;
	}

	/**
	 * Returns the number of folders containing web content articles belonging
	 * to the group.
	 *
	 * @param  groupId the primary key of the web content article's group
	 * @param  folderIds the primary keys of the web content article folders
	 *         (optionally {@link java.util.Collections#EMPTY_LIST})
	 * @return the number of matching folders containing web content articles
	 */
	@Override
	public int getFoldersAndArticlesCount(long groupId, List<Long> folderIds) {
		return journalArticlePersistence.filterCountByG_F(
			groupId,
			ArrayUtil.toArray(folderIds.toArray(new Long[folderIds.size()])));
	}

	@Override
	public List<JournalArticle> getGroupArticles(
			long groupId, long userId, long rootFolderId, int status, int start,
			int end, OrderByComparator orderByComparator)
		throws PortalException {

		List<Long> folderIds = new ArrayList<Long>();

		if (rootFolderId != JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			folderIds = journalFolderService.getFolderIds(
				groupId, rootFolderId);
		}

		QueryDefinition queryDefinition = new QueryDefinition(
			status, start, end, orderByComparator);

		return journalArticleFinder.filterFindByG_U_F_C(
			groupId, userId, folderIds,
			JournalArticleConstants.CLASSNAME_ID_DEFAULT, queryDefinition);
	}

	/**
	 * Returns an ordered range of all the web content articles matching the
	 * group, user, the root folder or any of its subfolders.
	 *
	 * @param  groupId the primary key of the web content article's group
	 * @param  userId the primary key of the user (optionally <code>0</code>)
	 * @param  rootFolderId the primary key of the root folder to begin the
	 *         search
	 * @param  start the lower bound of the range of web content articles to
	 *         return
	 * @param  end the upper bound of the range of web content articles to
	 *         return (not inclusive)
	 * @param  orderByComparator the comparator to order the web content
	 *         articles
	 * @return the range of matching web content articles ordered by the
	 *         comparator
	 * @throws PortalException if the root folder could not be found, if the
	 *         current user did not have permission to view the root folder, or
	 *         if a portal exception occurred
	 */
	@Override
	public List<JournalArticle> getGroupArticles(
			long groupId, long userId, long rootFolderId, int start, int end,
			OrderByComparator orderByComparator)
		throws PortalException {

		return getGroupArticles(
			groupId, userId, rootFolderId, WorkflowConstants.STATUS_ANY, start,
			end, orderByComparator);
	}

	/**
	 * Returns the number of web content articles matching the group, user, and
	 * the root folder or any of its subfolders.
	 *
	 * @param  groupId the primary key of the web content article's group
	 * @param  userId the primary key of the user (optionally <code>0</code>)
	 * @param  rootFolderId the primary key of the root folder to begin the
	 *         search
	 * @return the number of matching web content articles
	 * @throws PortalException if the root folder could not be found, if the
	 *         current user did not have permission to view the root folder, or
	 *         if a portal exception occurred
	 */
	@Override
	public int getGroupArticlesCount(
			long groupId, long userId, long rootFolderId)
		throws PortalException {

		return getGroupArticlesCount(
			groupId, userId, rootFolderId, WorkflowConstants.STATUS_ANY);
	}

	@Override
	public int getGroupArticlesCount(
			long groupId, long userId, long rootFolderId, int status)
		throws PortalException {

		List<Long> folderIds = new ArrayList<Long>();

		if (rootFolderId != JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			folderIds = journalFolderService.getFolderIds(
				groupId, rootFolderId);
		}

		QueryDefinition queryDefinition = new QueryDefinition(status);

		return journalArticleFinder.filterCountByG_U_F_C(
			groupId, userId, folderIds,
			JournalArticleConstants.CLASSNAME_ID_DEFAULT, queryDefinition);
	}

	/**
	 * Returns the latest web content article matching the resource primary key,
	 * preferring articles with approved workflow status.
	 *
	 * @param  resourcePrimKey the primary key of the resource instance
	 * @return the latest web content article matching the resource primary key,
	 *         preferring articles with approved workflow status
	 * @throws PortalException if the user did not have permission to view the
	 *         web content article or if a matching web content article could
	 *         not be found
	 */
	@Override
	public JournalArticle getLatestArticle(long resourcePrimKey)
		throws PortalException {

		JournalArticlePermission.check(
			getPermissionChecker(), resourcePrimKey, ActionKeys.VIEW);

		return journalArticleLocalService.getLatestArticle(resourcePrimKey);
	}

	/**
	 * Returns the latest web content article matching the group, article ID,
	 * and workflow status.
	 *
	 * @param  groupId the primary key of the web content article's group
	 * @param  articleId the primary key of the web content article
	 * @param  status the web content article's workflow status. For more
	 *         information see {@link WorkflowConstants} for constants starting
	 *         with the "STATUS_" prefix.
	 * @return the latest matching web content article
	 * @throws PortalException if the user did not have permission to view the
	 *         web content article or if a matching web content article could
	 *         not be found
	 */
	@Override
	public JournalArticle getLatestArticle(
			long groupId, String articleId, int status)
		throws PortalException {

		JournalArticlePermission.check(
			getPermissionChecker(), groupId, articleId, status,
			ActionKeys.VIEW);

		return journalArticleLocalService.getLatestArticle(
			groupId, articleId, status);
	}

	/**
	 * Returns the latest web content article matching the group, class name ID,
	 * and class PK.
	 *
	 * @param  groupId the primary key of the web content article's group
	 * @param  className the DDMStructure class name if the web content article
	 *         is related to a DDM structure, the class name associated with the
	 *         article, or {@link JournalArticleConstants#CLASSNAME_ID_DEFAULT}
	 *         otherwise
	 * @param  classPK the primary key of the DDM structure, if the DDMStructure
	 *         class name is given as the <code>className</code> parameter, the
	 *         primary key of the class associated with the web content article,
	 *         or <code>0</code> otherwise
	 * @return the latest matching web content article
	 * @throws PortalException if a matching web content article could not be
	 *         found or if the user did not have permission to view the web
	 *         content article
	 */
	@Override
	public JournalArticle getLatestArticle(
			long groupId, String className, long classPK)
		throws PortalException {

		JournalArticle article = journalArticleLocalService.getLatestArticle(
			groupId, className, classPK);

		JournalArticlePermission.check(
			getPermissionChecker(), groupId, article.getArticleId(),
			article.getVersion(), ActionKeys.VIEW);

		return article;
	}

	/**
	 * Moves all versions of the the web content article matching the group and
	 * article ID to the folder.
	 *
	 * @param  groupId the primary key of the web content article's group
	 * @param  articleId the primary key of the web content article
	 * @param  newFolderId the primary key of the web content article's new
	 *         folder
	 * @throws PortalException if the user did not have permission to update any
	 *         one of the versions of the web content article or if any one of
	 *         the versions of the web content article could not be moved to the
	 *         folder
	 */
	@Override
	public void moveArticle(long groupId, String articleId, long newFolderId)
		throws PortalException {

		List<JournalArticle> articles = journalArticlePersistence.findByG_A(
			groupId, articleId);

		for (JournalArticle article : articles) {
			JournalArticlePermission.check(
				getPermissionChecker(), article, ActionKeys.UPDATE);

			journalArticleLocalService.moveArticle(
				groupId, articleId, newFolderId);
		}
	}

	/**
	 * Moves the web content article from the Recycle Bin to the folder.
	 *
	 * @param  groupId the primary key of the web content article's group
	 * @param  resourcePrimKey the primary key of the resource instance
	 * @param  newFolderId the primary key of the web content article's new
	 *         folder
	 * @param  serviceContext the service context to be applied. Can set the
	 *         modification date, portlet preferences, and can set whether to
	 *         add the default command update for the web content article. With
	 *         respect to social activities, by setting the service context's
	 *         command to {@link
	 *         com.liferay.portal.kernel.util.Constants#UPDATE}, the invocation
	 *         is considered a web content update activity; otherwise it is
	 *         considered a web content add activity.
	 * @return the updated web content article, which was moved from the Recycle
	 *         Bin to the folder
	 * @throws PortalException if the user did not have permission to view or
	 *         update the web content article, if a matching trashed web content
	 *         article could not be found, or if a portal exception occurred
	 */
	@Override
	public JournalArticle moveArticleFromTrash(
			long groupId, long resourcePrimKey, long newFolderId,
			ServiceContext serviceContext)
		throws PortalException {

		JournalArticle article = getLatestArticle(resourcePrimKey);

		JournalArticlePermission.check(
			getPermissionChecker(), article, ActionKeys.UPDATE);

		return journalArticleLocalService.moveArticleFromTrash(
			getUserId(), groupId, article, newFolderId, serviceContext);
	}

	/**
	 * Moves the web content article from the Recycle Bin to the folder.
	 *
	 * @param  groupId the primary key of the web content article's group
	 * @param  articleId the primary key of the web content article
	 * @param  newFolderId the primary key of the web content article's new
	 *         folder
	 * @param  serviceContext the service context to be applied. Can set the
	 *         modification date, portlet preferences, and can set whether to
	 *         add the default command update for the web content article. With
	 *         respect to social activities, by setting the service context's
	 *         command to {@link
	 *         com.liferay.portal.kernel.util.Constants#UPDATE}, the invocation
	 *         is considered a web content update activity; otherwise it is
	 *         considered a web content add activity.
	 * @return the updated web content article, which was moved from the Recycle
	 *         Bin to the folder
	 * @throws PortalException if the user did not have permission to view or
	 *         update the web content article, if a trashed web content article
	 *         with the primary key could not be found, or if a portal exception
	 *         occurred
	 */
	@Override
	public JournalArticle moveArticleFromTrash(
			long groupId, String articleId, long newFolderId,
			ServiceContext serviceContext)
		throws PortalException {

		JournalArticle article = getLatestArticle(
			groupId, articleId, WorkflowConstants.STATUS_IN_TRASH);

		JournalArticlePermission.check(
			getPermissionChecker(), groupId, articleId, ActionKeys.UPDATE);

		return journalArticleLocalService.moveArticleFromTrash(
			getUserId(), groupId, article, newFolderId, serviceContext);
	}

	/**
	 * Moves the latest version of the web content article matching the group
	 * and article ID to the recycle bin.
	 *
	 * @param  groupId the primary key of the web content article's group
	 * @param  articleId the primary key of the web content article
	 * @return the moved web content article or <code>null</code> if no matching
	 *         article was found
	 * @throws PortalException if the user did not have permission to move the
	 *         article to the Recycle Bin or if a portal exception occurred
	 */
	@Override
	public JournalArticle moveArticleToTrash(long groupId, String articleId)
		throws PortalException {

		JournalArticlePermission.check(
			getPermissionChecker(), groupId, articleId, ActionKeys.DELETE);

		return journalArticleLocalService.moveArticleToTrash(
			getUserId(), groupId, articleId);
	}

	/**
	 * Removes the web content of all the company's web content articles
	 * matching the language.
	 *
	 * @param  companyId the primary key of the web content article's company
	 * @param  languageId the primary key of the language locale to remove
	 * @throws PortalException if the user did not have permission to update any
	 *         one of the the web content articles or if web content matching
	 *         the language could not be found for any one of the articles
	 */
	@Override
	public void removeArticleLocale(long companyId, String languageId)
		throws PortalException {

		for (JournalArticle article :
				journalArticlePersistence.findByCompanyId(companyId)) {

			removeArticleLocale(
				article.getGroupId(), article.getArticleId(),
				article.getVersion(), languageId);
		}
	}

	/**
	 * Removes the web content of the web content article matching the group,
	 * article ID, and version, and language.
	 *
	 * @param  groupId the primary key of the web content article's group
	 * @param  articleId the primary key of the web content article
	 * @param  version the web content article's version
	 * @param  languageId the primary key of the language locale to remove
	 * @return the updated web content article with the locale removed
	 * @throws PortalException if the user did not have permission to update the
	 *         web content article or if a matching web content article could
	 *         not be found
	 */
	@Override
	public JournalArticle removeArticleLocale(
			long groupId, String articleId, double version, String languageId)
		throws PortalException {

		JournalArticlePermission.check(
			getPermissionChecker(), groupId, articleId, version,
			ActionKeys.UPDATE);

		return journalArticleLocalService.removeArticleLocale(
			groupId, articleId, version, languageId);
	}

	/**
	 * Restores the web content article associated with the resource primary key
	 * from the Recycle Bin.
	 *
	 * @param  resourcePrimKey the primary key of the resource instance
	 * @throws PortalException if a matching web content article could not be
	 *         found in the Recycle Bin, if the user did not have permission to
	 *         view or restore the article, or if a portal exception occurred
	 */
	@Override
	public void restoreArticleFromTrash(long resourcePrimKey)
		throws PortalException {

		JournalArticle article = getLatestArticle(resourcePrimKey);

		JournalArticlePermission.check(
			getPermissionChecker(), article, ActionKeys.DELETE);

		journalArticleLocalService.restoreArticleFromTrash(
			getUserId(), article);
	}

	/**
	 * Restores the web content article from the Recycle Bin.
	 *
	 * @param  groupId the primary key of the web content article's group
	 * @param  articleId the primary key of the web content article
	 * @throws PortalException if the web content article with the primary key
	 *         could not be found in the Recycle Bin, if the user did not have
	 *         permission to restore the article, or if a portal exception
	 *         occurred
	 */
	@Override
	public void restoreArticleFromTrash(long groupId, String articleId)
		throws PortalException {

		JournalArticle article = getLatestArticle(
			groupId, articleId, WorkflowConstants.STATUS_IN_TRASH);

		restoreArticleFromTrash(article.getResourcePrimKey());
	}

	@Override
	public Hits search(
			long groupId, long creatorUserId, int status, int start, int end)
		throws PortalException {

		return journalArticleLocalService.search(
			groupId, getUserId(), creatorUserId, status, start, end);
	}

	/**
	 * Returns an ordered range of all the web content articles matching the
	 * parameters, including a keywords parameter for matching with the
	 * article's ID, title, description, and content, a DDM structure key
	 * parameter, and a DDM template key parameter.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end -
	 * start</code> instances. <code>start</code> and <code>end</code> are not
	 * primary keys, they are indexes in the result set. Thus, <code>0</code>
	 * refers to the first result in the set. Setting both <code>start</code>
	 * and <code>end</code> to {@link
	 * com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full
	 * result set.
	 * </p>
	 *
	 * @param  companyId the primary key of the web content article's company
	 * @param  groupId the primary key of the group (optionally <code>0</code>)
	 * @param  folderIds the primary keys of the web content article folders
	 *         (optionally {@link java.util.Collections#EMPTY_LIST})
	 * @param  classNameId the primary key of the DDMStructure class if the web
	 *         content article is related to a DDM structure, the primary key of
	 *         the class name associated with the article, or {@link
	 *         JournalArticleConstants#CLASSNAME_ID_DEFAULT} otherwise
	 * @param  keywords the keywords (space separated), which may occur in the
	 *         web content article ID, title, description, or content
	 *         (optionally <code>null</code>). If the keywords value is not
	 *         <code>null</code>, the search uses the OR operator in connecting
	 *         query criteria; otherwise it uses the AND operator.
	 * @param  version the web content article's version (optionally
	 *         <code>null</code>)
	 * @param  type the web content article's type (optionally
	 *         <code>null</code>)
	 * @param  ddmStructureKey the primary key of the web content article's DDM
	 *         structure, if the article is related to a DDM structure, or
	 *         <code>null</code> otherwise
	 * @param  ddmTemplateKey the primary key of the web content article's DDM
	 *         template
	 * @param  displayDateGT the date after which a matching web content
	 *         article's display date must be after (optionally
	 *         <code>null</code>)
	 * @param  displayDateLT the date before which a matching web content
	 *         article's display date must be before (optionally
	 *         <code>null</code>)
	 * @param  status the web content article's workflow status. For more
	 *         information see {@link WorkflowConstants} for constants starting
	 *         with the "STATUS_" prefix.
	 * @param  reviewDate the web content article's scheduled review date
	 *         (optionally <code>null</code>)
	 * @param  start the lower bound of the range of web content articles to
	 *         return
	 * @param  end the upper bound of the range of web content articles to
	 *         return (not inclusive)
	 * @param  obc the comparator to order the web content articles
	 * @return the range of matching web content articles ordered by the
	 *         comparator
	 */
	@Override
	public List<JournalArticle> search(
		long companyId, long groupId, List<Long> folderIds, long classNameId,
		String keywords, Double version, String type, String ddmStructureKey,
		String ddmTemplateKey, Date displayDateGT, Date displayDateLT,
		int status, Date reviewDate, int start, int end,
		OrderByComparator obc) {

		return journalArticleFinder.filterFindByKeywords(
			companyId, groupId, folderIds, classNameId, keywords, version, type,
			ddmStructureKey, ddmTemplateKey, displayDateGT, displayDateLT,
			status, reviewDate, start, end, obc);
	}

	/**
	 * Returns an ordered range of all the web content articles matching the
	 * parameters, including keyword parameters for article ID, title,
	 * description, and content, a DDM structure key parameter, a DDM template
	 * key parameter, and an AND operator switch.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end -
	 * start</code> instances. <code>start</code> and <code>end</code> are not
	 * primary keys, they are indexes in the result set. Thus, <code>0</code>
	 * refers to the first result in the set. Setting both <code>start</code>
	 * and <code>end</code> to {@link
	 * com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full
	 * result set.
	 * </p>
	 *
	 * @param  companyId the primary key of the web content article's company
	 * @param  groupId the primary key of the group (optionally <code>0</code>)
	 * @param  folderIds the primary keys of the web content article folders
	 *         (optionally {@link java.util.Collections#EMPTY_LIST})
	 * @param  classNameId the primary key of the DDMStructure class if the web
	 *         content article is related to a DDM structure, the primary key of
	 *         the class name associated with the article, or {@link
	 *         JournalArticleConstants#CLASSNAME_ID_DEFAULT} otherwise
	 * @param  articleId the article ID keywords (space separated, optionally
	 *         <code>null</code>)
	 * @param  version the web content article's version (optionally
	 *         <code>null</code>)
	 * @param  title the title keywords (space separated, optionally
	 *         <code>null</code>)
	 * @param  description the description keywords (space separated, optionally
	 *         <code>null</code>)
	 * @param  content the content keywords (space separated, optionally
	 *         <code>null</code>)
	 * @param  type the web content article's type (optionally
	 *         <code>null</code>)
	 * @param  ddmStructureKey the primary key of the web content article's DDM
	 *         structure, if the article is related to a DDM structure, or
	 *         <code>null</code> otherwise
	 * @param  ddmTemplateKey the primary key of the web content article's DDM
	 *         template
	 * @param  displayDateGT the date after which a matching web content
	 *         article's display date must be after (optionally
	 *         <code>null</code>)
	 * @param  displayDateLT the date before which a matching web content
	 *         article's display date must be before (optionally
	 *         <code>null</code>)
	 * @param  status the web content article's workflow status. For more
	 *         information see {@link WorkflowConstants} for constants starting
	 *         with the "STATUS_" prefix.
	 * @param  reviewDate the web content article's scheduled review date
	 *         (optionally <code>null</code>)
	 * @param  andOperator whether every field must match its value or keywords,
	 *         or just one field must match. Company, group, folder IDs, class
	 *         name ID, and status must all match their values.
	 * @param  start the lower bound of the range of web content articles to
	 *         return
	 * @param  end the upper bound of the range of web content articles to
	 *         return (not inclusive)
	 * @param  obc the comparator to order the web content articles
	 * @return the range of matching web content articles ordered by the
	 *         comparator
	 */
	@Override
	public List<JournalArticle> search(
		long companyId, long groupId, List<Long> folderIds, long classNameId,
		String articleId, Double version, String title, String description,
		String content, String type, String ddmStructureKey,
		String ddmTemplateKey, Date displayDateGT, Date displayDateLT,
		int status, Date reviewDate, boolean andOperator, int start, int end,
		OrderByComparator obc) {

		QueryDefinition queryDefinition = new QueryDefinition(
			status, start, end, obc);

		return journalArticleFinder.filterFindByC_G_F_C_A_V_T_D_C_T_S_T_D_R(
			companyId, groupId, folderIds, classNameId, articleId, version,
			title, description, content, type, ddmStructureKey, ddmTemplateKey,
			displayDateGT, displayDateLT, reviewDate, andOperator,
			queryDefinition);
	}

	/**
	 * Returns an ordered range of all the web content articles matching the
	 * parameters, including keyword parameters for article ID, title,
	 * description, and content, a DDM structure keys (plural) parameter, a DDM
	 * template keys (plural) parameter, and an AND operator switch.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end -
	 * start</code> instances. <code>start</code> and <code>end</code> are not
	 * primary keys, they are indexes in the result set. Thus, <code>0</code>
	 * refers to the first result in the set. Setting both <code>start</code>
	 * and <code>end</code> to {@link
	 * com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full
	 * result set.
	 * </p>
	 *
	 * @param  companyId the primary key of the web content article's company
	 * @param  groupId the primary key of the group (optionally <code>0</code>)
	 * @param  folderIds the primary keys of the web content article folders
	 *         (optionally {@link java.util.Collections#EMPTY_LIST})
	 * @param  classNameId the primary key of the DDMStructure class if the web
	 *         content article is related to a DDM structure, the primary key of
	 *         the class name associated with the article, or {@link
	 *         JournalArticleConstants#CLASSNAME_ID_DEFAULT} otherwise
	 * @param  articleId the article ID keywords (space separated, optionally
	 *         <code>null</code>)
	 * @param  version the web content article's version (optionally
	 *         <code>null</code>)
	 * @param  title the title keywords (space separated, optionally
	 *         <code>null</code>)
	 * @param  description the description keywords (space separated, optionally
	 *         <code>null</code>)
	 * @param  content the content keywords (space separated, optionally
	 *         <code>null</code>)
	 * @param  type the web content article's type (optionally
	 *         <code>null</code>)
	 * @param  ddmStructureKeys the primary keys of the web content article's
	 *         DDM structures, if the article is related to a DDM structure, or
	 *         <code>null</code> otherwise
	 * @param  ddmTemplateKeys the primary keys of the web content article's DDM
	 *         templates (originally <code>null</code>). If the articles are
	 *         related to a DDM structure, the template's structure must match
	 *         it.
	 * @param  displayDateGT the date after which a matching web content
	 *         article's display date must be after (optionally
	 *         <code>null</code>)
	 * @param  displayDateLT the date before which a matching web content
	 *         article's display date must be before (optionally
	 *         <code>null</code>)
	 * @param  status the web content article's workflow status. For more
	 *         information see {@link WorkflowConstants} for constants starting
	 *         with the "STATUS_" prefix.
	 * @param  reviewDate the web content article's scheduled review date
	 *         (optionally <code>null</code>)
	 * @param  andOperator whether every field must match its value or keywords,
	 *         or just one field must match.  Company, group, folder IDs, class
	 *         name ID, and status must all match their values.
	 * @param  start the lower bound of the range of web content articles to
	 *         return
	 * @param  end the upper bound of the range of web content articles to
	 *         return (not inclusive)
	 * @param  obc the comparator to order the web content articles
	 * @return the range of matching web content articles ordered by the
	 *         comparator
	 */
	@Override
	public List<JournalArticle> search(
		long companyId, long groupId, List<Long> folderIds, long classNameId,
		String articleId, Double version, String title, String description,
		String content, String type, String[] ddmStructureKeys,
		String[] ddmTemplateKeys, Date displayDateGT, Date displayDateLT,
		int status, Date reviewDate, boolean andOperator, int start, int end,
		OrderByComparator obc) {

		QueryDefinition queryDefinition = new QueryDefinition(
			status, start, end, obc);

		return journalArticleFinder.filterFindByC_G_F_C_A_V_T_D_C_T_S_T_D_R(
			companyId, groupId, folderIds, classNameId, articleId, version,
			title, description, content, type, ddmStructureKeys,
			ddmTemplateKeys, displayDateGT, displayDateLT, reviewDate,
			andOperator, queryDefinition);
	}

	/**
	 * Returns the number of web content articles matching the parameters,
	 * including a keywords parameter for matching with the article's ID, title,
	 * description, and content, a DDM structure key parameter, and a DDM
	 * template key parameter.
	 *
	 * @param  companyId the primary key of the web content article's company
	 * @param  groupId the primary key of the group (optionally <code>0</code>)
	 * @param  folderIds the primary keys of the web content article folders
	 *         (optionally {@link java.util.Collections#EMPTY_LIST})
	 * @param  classNameId the primary key of the DDMStructure class if the web
	 *         content article is related to a DDM structure, the primary key of
	 *         the class name associated with the article, or {@link
	 *         JournalArticleConstants#CLASSNAME_ID_DEFAULT} otherwise
	 * @param  keywords the keywords (space separated), which may occur in the
	 *         web content article ID, title, description, or content
	 *         (optionally <code>null</code>). If the keywords value is not
	 *         <code>null</code>, the search uses the OR operator in connecting
	 *         query criteria; otherwise it uses the AND operator.
	 * @param  version the web content article's version (optionally
	 *         <code>null</code>)
	 * @param  type the web content article's type (optionally
	 *         <code>null</code>)
	 * @param  ddmStructureKey the primary key of the web content article's DDM
	 *         structure, if the article is related to a DDM structure, or
	 *         <code>null</code> otherwise
	 * @param  ddmTemplateKey the primary key of the web content article's DDM
	 *         template
	 * @param  displayDateGT the date after which a matching web content
	 *         article's display date must be after (optionally
	 *         <code>null</code>)
	 * @param  displayDateLT the date before which a matching web content
	 *         article's display date must be before (optionally
	 *         <code>null</code>)
	 * @param  status the web content article's workflow status. For more
	 *         information see {@link WorkflowConstants} for constants starting
	 *         with the "STATUS_" prefix.
	 * @param  reviewDate the web content article's scheduled review date
	 *         (optionally <code>null</code>)
	 * @return the number of matching web content articles
	 */
	@Override
	public int searchCount(
		long companyId, long groupId, List<Long> folderIds, long classNameId,
		String keywords, Double version, String type, String ddmStructureKey,
		String ddmTemplateKey, Date displayDateGT, Date displayDateLT,
		int status, Date reviewDate) {

		return journalArticleFinder.filterCountByKeywords(
			companyId, groupId, folderIds, classNameId, keywords, version, type,
			ddmStructureKey, ddmTemplateKey, displayDateGT, displayDateLT,
			status, reviewDate);
	}

	/**
	 * Returns the number of web content articles matching the parameters,
	 * including keyword parameters for article ID, title, description, and
	 * content, a DDM structure key parameter, a DDM template key parameter, and
	 * an AND operator switch.
	 *
	 * @param  companyId the primary key of the web content article's company
	 * @param  groupId the primary key of the group (optionally <code>0</code>)
	 * @param  folderIds the primary keys of the web content article folders
	 *         (optionally {@link java.util.Collections#EMPTY_LIST})
	 * @param  classNameId the primary key of the DDMStructure class if the web
	 *         content article is related to a DDM structure, the primary key of
	 *         the class name associated with the article, or {@link
	 *         JournalArticleConstants#CLASSNAME_ID_DEFAULT} otherwise
	 * @param  articleId the article ID keywords (space separated, optionally
	 *         <code>null</code>)
	 * @param  version the web content article's version (optionally
	 *         <code>null</code>)
	 * @param  title the title keywords (space separated, optionally
	 *         <code>null</code>)
	 * @param  description the description keywords (space separated, optionally
	 *         <code>null</code>)
	 * @param  content the content keywords (space separated, optionally
	 *         <code>null</code>)
	 * @param  type the web content article's type (optionally
	 *         <code>null</code>)
	 * @param  ddmStructureKey the primary key of the web content article's DDM
	 *         structure, if the article is related to a DDM structure, or
	 *         <code>null</code> otherwise
	 * @param  ddmTemplateKey the primary key of the web content article's DDM
	 *         template
	 * @param  displayDateGT the date after which a matching web content
	 *         article's display date must be after (optionally
	 *         <code>null</code>)
	 * @param  displayDateLT the date before which a matching web content
	 *         article's display date must be before (optionally
	 *         <code>null</code>)
	 * @param  status the web content article's workflow status. For more
	 *         information see {@link WorkflowConstants} for constants starting
	 *         with the "STATUS_" prefix.
	 * @param  reviewDate the web content article's scheduled review date
	 *         (optionally <code>null</code>)
	 * @param  andOperator whether every field must match its value or keywords,
	 *         or just one field must match. Group, folder IDs, class name ID,
	 *         and status must all match their values.
	 * @return the number of matching web content articles
	 */
	@Override
	public int searchCount(
		long companyId, long groupId, List<Long> folderIds, long classNameId,
		String articleId, Double version, String title, String description,
		String content, String type, String ddmStructureKey,
		String ddmTemplateKey, Date displayDateGT, Date displayDateLT,
		int status, Date reviewDate, boolean andOperator) {

		return journalArticleFinder.filterCountByC_G_F_C_A_V_T_D_C_T_S_T_D_R(
			companyId, groupId, folderIds, classNameId, articleId, version,
			title, description, content, type, ddmStructureKey, ddmTemplateKey,
			displayDateGT, displayDateLT, reviewDate, andOperator,
			new QueryDefinition(status));
	}

	/**
	 * Returns the number of web content articles matching the parameters,
	 * including keyword parameters for article ID, title, description, and
	 * content, a DDM structure keys (plural) parameter, a DDM template keys
	 * (plural) parameter, and an AND operator switch.
	 *
	 * @param  companyId the primary key of the web content article's company
	 * @param  groupId the primary key of the group (optionally <code>0</code>)
	 * @param  folderIds the primary keys of the web content article folders
	 *         (optionally {@link java.util.Collections#EMPTY_LIST})
	 * @param  classNameId the primary key of the DDMStructure class if the web
	 *         content article is related to a DDM structure, the primary key of
	 *         the class name associated with the article, or {@link
	 *         JournalArticleConstants#CLASSNAME_ID_DEFAULT} otherwise
	 * @param  articleId the article ID keywords (space separated, optionally
	 *         <code>null</code>)
	 * @param  version the web content article's version (optionally
	 *         <code>null</code>)
	 * @param  title the title keywords (space separated, optionally
	 *         <code>null</code>)
	 * @param  description the description keywords (space separated, optionally
	 *         <code>null</code>)
	 * @param  content the content keywords (space separated, optionally
	 *         <code>null</code>)
	 * @param  type the web content article's type (optionally
	 *         <code>null</code>)
	 * @param  ddmStructureKeys the primary keys of the web content article's
	 *         DDM structures, if the article is related to a DDM structure, or
	 *         <code>null</code> otherwise
	 * @param  ddmTemplateKeys the primary keys of the web content article's DDM
	 *         templates (originally <code>null</code>). If the articles are
	 *         related to a DDM structure, the template's structure must match
	 *         it.
	 * @param  displayDateGT the date after which a matching web content
	 *         article's display date must be after (optionally
	 *         <code>null</code>)
	 * @param  displayDateLT the date before which a matching web content
	 *         article's display date must be before (optionally
	 *         <code>null</code>)
	 * @param  status the web content article's workflow status. For more
	 *         information see {@link WorkflowConstants} for constants starting
	 *         with the "STATUS_" prefix.
	 * @param  reviewDate the web content article's scheduled review date
	 *         (optionally <code>null</code>)
	 * @param  andOperator whether every field must match its value or keywords,
	 *         or just one field must match.  Group, folder IDs, class name ID,
	 *         and status must all match their values.
	 * @return the number of matching web content articles
	 */
	@Override
	public int searchCount(
		long companyId, long groupId, List<Long> folderIds, long classNameId,
		String articleId, Double version, String title, String description,
		String content, String type, String[] ddmStructureKeys,
		String[] ddmTemplateKeys, Date displayDateGT, Date displayDateLT,
		int status, Date reviewDate, boolean andOperator) {

		return journalArticleFinder.filterCountByC_G_F_C_A_V_T_D_C_T_S_T_D_R(
			companyId, groupId, folderIds, classNameId, articleId, version,
			title, description, content, type, ddmStructureKeys,
			ddmTemplateKeys, displayDateGT, displayDateLT, reviewDate,
			andOperator, new QueryDefinition(status));
	}

	@Override
	public void subscribeStructure(
			long groupId, long userId, long ddmStructureId)
		throws PortalException {

		JournalPermission.check(
			getPermissionChecker(), groupId, ActionKeys.SUBSCRIBE);

		journalArticleLocalService.subscribeStructure(
			groupId, userId, ddmStructureId);
	}

	@Override
	public void unsubscribeStructure(
			long groupId, long userId, long ddmStructureId)
		throws PortalException {

		JournalPermission.check(
			getPermissionChecker(), groupId, ActionKeys.SUBSCRIBE);

		journalArticleLocalService.unsubscribeStructure(
			groupId, userId, ddmStructureId);
	}

	/**
	 * Updates the web content article matching the version, replacing its
	 * folder, title, description, content, and layout UUID.
	 *
	 * @param  userId the primary key of the user updating the web content
	 *         article
	 * @param  groupId the primary key of the web content article's group
	 * @param  folderId the primary key of the web content article folder
	 * @param  articleId the primary key of the web content article
	 * @param  version the web content article's version
	 * @param  titleMap the web content article's locales and localized titles
	 * @param  descriptionMap the web content article's locales and localized
	 *         descriptions
	 * @param  content the HTML content wrapped in XML. For more information,
	 *         see the content example in the class description for {@link
	 *         JournalArticleLocalServiceImpl}.
	 * @param  layoutUuid the unique string identifying the web content
	 *         article's display page
	 * @param  serviceContext the service context to be applied. Can set the
	 *         modification date, expando bridge attributes, asset category IDs,
	 *         asset tag names, asset link entry IDs, workflow actions, the
	 *         "defaultLanguageId" and "urlTitle" attributes, and can set
	 *         whether to add the default command update for the web content
	 *         article. With respect to social activities, by setting the
	 *         service context's command to {@link
	 *         com.liferay.portal.kernel.util.Constants#UPDATE}, the invocation
	 *         is considered a web content update activity; otherwise it is
	 *         considered a web content add activity.
	 * @return the updated web content article
	 * @throws PortalException if a user with the primary key or a matching web
	 *         content article could not be found, or if a portal exception
	 *         occurred
	 */
	@Override
	public JournalArticle updateArticle(
			long userId, long groupId, long folderId, String articleId,
			double version, Map<Locale, String> titleMap,
			Map<Locale, String> descriptionMap, String content,
			String layoutUuid, ServiceContext serviceContext)
		throws PortalException {

		return journalArticleLocalService.updateArticle(
			userId, groupId, folderId, articleId, version, titleMap,
			descriptionMap, content, layoutUuid, serviceContext);
	}

	/**
	 * Updates the web content article with additional parameters.
	 *
	 * @param  groupId the primary key of the web content article's group
	 * @param  folderId the primary key of the web content article folder
	 * @param  articleId the primary key of the web content article
	 * @param  version the web content article's version
	 * @param  titleMap the web content article's locales and localized titles
	 * @param  descriptionMap the web content article's locales and localized
	 *         descriptions
	 * @param  content the HTML content wrapped in XML. For more information,
	 *         see the content example in the class description for {@link
	 *         JournalArticleLocalServiceImpl}.
	 * @param  type the structure's type, if the web content article is related
	 *         to a DDM structure. For more information, see {@link
	 *         com.liferay.portlet.dynamicdatamapping.model.DDMStructureConstants}.
	 * @param  ddmStructureKey the primary key of the web content article's DDM
	 *         structure, if the article is related to a DDM structure, or
	 *         <code>null</code> otherwise
	 * @param  ddmTemplateKey the primary key of the web content article's DDM
	 *         template
	 * @param  layoutUuid the unique string identifying the web content
	 *         article's display page
	 * @param  displayDateMonth the month the web content article is set to
	 *         display
	 * @param  displayDateDay the calendar day the web content article is set to
	 *         display
	 * @param  displayDateYear the year the web content article is set to
	 *         display
	 * @param  displayDateHour the hour the web content article is set to
	 *         display
	 * @param  displayDateMinute the minute the web content article is set to
	 *         display
	 * @param  expirationDateMonth the month the web content article is set to
	 *         expire
	 * @param  expirationDateDay the calendar day the web content article is set
	 *         to expire
	 * @param  expirationDateYear the year the web content article is set to
	 *         expire
	 * @param  expirationDateHour the hour the web content article is set to
	 *         expire
	 * @param  expirationDateMinute the minute the web content article is set to
	 *         expire
	 * @param  neverExpire whether the web content article is not set to auto
	 *         expire
	 * @param  reviewDateMonth the month the web content article is set for
	 *         review
	 * @param  reviewDateDay the calendar day the web content article is set for
	 *         review
	 * @param  reviewDateYear the year the web content article is set for review
	 * @param  reviewDateHour the hour the web content article is set for review
	 * @param  reviewDateMinute the minute the web content article is set for
	 *         review
	 * @param  neverReview whether the web content article is not set for review
	 * @param  indexable whether the web content is searchable
	 * @param  smallImage whether to update web content article's a small image.
	 *         A file must be passed in as <code>smallImageFile</code> value,
	 *         otherwise the current small image is deleted.
	 * @param  smallImageURL the web content article's small image URL
	 *         (optionally <code>null</code>)
	 * @param  smallFile the web content article's new small image file
	 *         (optionally <code>null</code>). Must pass in
	 *         <code>smallImage</code> value of <code>true</code> to replace the
	 *         article's small image file.
	 * @param  images the web content's images (optionally <code>null</code>)
	 * @param  articleURL the web content article's accessible URL (optionally
	 *         <code>null</code>)
	 * @param  serviceContext the service context to be applied. Can set the
	 *         modification date, expando bridge attributes, asset category IDs,
	 *         asset tag names, asset link entry IDs, workflow actions, the
	 *         "defaultLanguageId" and "urlTitle" attributes, and can set
	 *         whether to add the default command update for the web content
	 *         article. With respect to social activities, by setting the
	 *         service context's command to {@link
	 *         com.liferay.portal.kernel.util.Constants#UPDATE}, the invocation
	 *         is considered a web content update activity; otherwise it is
	 *         considered a web content add activity.
	 * @return the updated web content article
	 * @throws PortalException if the user did not have permission to update the
	 *         web content article, if a user with the primary key or a matching
	 *         web content article could not be found, or if a portal exception
	 *         occurred
	 */
	@Override
	public JournalArticle updateArticle(
			long groupId, long folderId, String articleId, double version,
			Map<Locale, String> titleMap, Map<Locale, String> descriptionMap,
			String content, String type, String ddmStructureKey,
			String ddmTemplateKey, String layoutUuid, int displayDateMonth,
			int displayDateDay, int displayDateYear, int displayDateHour,
			int displayDateMinute, int expirationDateMonth,
			int expirationDateDay, int expirationDateYear,
			int expirationDateHour, int expirationDateMinute,
			boolean neverExpire, int reviewDateMonth, int reviewDateDay,
			int reviewDateYear, int reviewDateHour, int reviewDateMinute,
			boolean neverReview, boolean indexable, boolean smallImage,
			String smallImageURL, File smallFile, Map<String, byte[]> images,
			String articleURL, ServiceContext serviceContext)
		throws PortalException {

		JournalArticlePermission.check(
			getPermissionChecker(), groupId, articleId, version,
			ActionKeys.UPDATE);

		return journalArticleLocalService.updateArticle(
			getUserId(), groupId, folderId, articleId, version, titleMap,
			descriptionMap, content, type, ddmStructureKey, ddmTemplateKey,
			layoutUuid, displayDateMonth, displayDateDay, displayDateYear,
			displayDateHour, displayDateMinute, expirationDateMonth,
			expirationDateDay, expirationDateYear, expirationDateHour,
			expirationDateMinute, neverExpire, reviewDateMonth, reviewDateDay,
			reviewDateYear, reviewDateHour, reviewDateMinute, neverReview,
			indexable, smallImage, smallImageURL, smallFile, images, articleURL,
			serviceContext);
	}

	/**
	 * Updates the web content article matching the version, replacing its
	 * folder and content.
	 *
	 * @param  groupId the primary key of the web content article's group
	 * @param  folderId the primary key of the web content article folder
	 * @param  articleId the primary key of the web content article
	 * @param  version the web content article's version
	 * @param  content the HTML content wrapped in XML. For more information,
	 *         see the content example in the class description for {@link
	 *         JournalArticleLocalServiceImpl}.
	 * @param  serviceContext the service context to be applied. Can set the
	 *         modification date, expando bridge attributes, asset category IDs,
	 *         asset tag names, asset link entry IDs, workflow actions, the
	 *         "defaultLanguageId" and "urlTitle" attributes, and can set
	 *         whether to add the default command update for the web content
	 *         article. With respect to social activities, by setting the
	 *         service context's command to {@link
	 *         com.liferay.portal.kernel.util.Constants#UPDATE}, the invocation
	 *         is considered a web content update activity; otherwise it is
	 *         considered a web content add activity.
	 * @return the updated web content article
	 * @throws PortalException if the user did not have permission to update the
	 *         web content article, if a user with the primary key or a matching
	 *         web content article could not be found, or if a portal exception
	 *         occurred
	 */
	@Override
	public JournalArticle updateArticle(
			long groupId, long folderId, String articleId, double version,
			String content, ServiceContext serviceContext)
		throws PortalException {

		JournalArticlePermission.check(
			getPermissionChecker(), groupId, articleId, version,
			ActionKeys.UPDATE);

		return journalArticleLocalService.updateArticle(
			getUserId(), groupId, folderId, articleId, version, content,
			serviceContext);
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link
	 *             #updateArticleTranslation(long, String, double, Locale,
	 *             String, String, String, Map, ServiceContext)}
	 */
	@Deprecated
	@Override
	public JournalArticle updateArticleTranslation(
			long groupId, String articleId, double version, Locale locale,
			String title, String description, String content,
			Map<String, byte[]> images)
		throws PortalException {

		return updateArticleTranslation(
			groupId, articleId, version, locale, title, description, content,
			images, null);
	}

	/**
	 * Updates the translation of the web content article.
	 *
	 * @param  groupId the primary key of the web content article's group
	 * @param  articleId the primary key of the web content article
	 * @param  version the web content article's version
	 * @param  locale the locale of the web content article's display template
	 * @param  title the translated web content article title
	 * @param  description the translated web content article description
	 * @param  content the HTML content wrapped in XML. For more information,
	 *         see the content example in the class description for {@link
	 *         JournalArticleLocalServiceImpl}.
	 * @param  images the web content's images
	 * @param  serviceContext the service context to be applied. Can set the
	 *         modification date and "urlTitle" attribute for the web content
	 *         article.
	 * @return the updated web content article
	 * @throws PortalException if the user did not have permission to update the
	 *         web content article, if a user with the primary key or a matching
	 *         web content article could not be found, or if a portal exception
	 *         occurred
	 */
	@Override
	public JournalArticle updateArticleTranslation(
			long groupId, String articleId, double version, Locale locale,
			String title, String description, String content,
			Map<String, byte[]> images, ServiceContext serviceContext)
		throws PortalException {

		JournalArticlePermission.check(
			getPermissionChecker(), groupId, articleId, version,
			ActionKeys.UPDATE);

		return journalArticleLocalService.updateArticleTranslation(
			groupId, articleId, version, locale, title, description, content,
			images, serviceContext);
	}

	/**
	 * Updates the web content article matching the group, article ID, and
	 * version, replacing its content.
	 *
	 * @param  groupId the primary key of the web content article's group
	 * @param  articleId the primary key of the web content article
	 * @param  version the web content article's version
	 * @param  content the HTML content wrapped in XML. For more information,
	 *         see the content example in the class description for {@link
	 *         JournalArticleLocalServiceImpl}.
	 * @return the updated web content article
	 * @throws PortalException if the user did not have permission to update the
	 *         web content article or if a matching web content article could
	 *         not be found
	 */
	@Override
	public JournalArticle updateContent(
			long groupId, String articleId, double version, String content)
		throws PortalException {

		JournalArticlePermission.check(
			getPermissionChecker(), groupId, articleId, version,
			ActionKeys.UPDATE);

		return journalArticleLocalService.updateContent(
			groupId, articleId, version, content);
	}

	/**
	 * Updates the workflow status of the web content article matching the
	 * group, article ID, and version.
	 *
	 * @param  groupId the primary key of the web content article's group
	 * @param  articleId the primary key of the web content article
	 * @param  version the web content article's version
	 * @param  status the web content article's workflow status. For more
	 *         information see {@link WorkflowConstants} for constants starting
	 *         with the "STATUS_" prefix.
	 * @param  articleURL the web content article's accessible URL
	 * @param  serviceContext the service context to be applied. Can set the
	 *         modification date, portlet preferences, and can set whether to
	 *         add the default command update for the web content article.
	 * @return the updated web content article
	 * @throws PortalException if the user did not have permission to update the
	 *         web content article, if a matching web content article could not
	 *         be found, or if a portal exception occurred
	 */
	@Override
	public JournalArticle updateStatus(
			long groupId, String articleId, double version, int status,
			String articleURL, ServiceContext serviceContext)
		throws PortalException {

		JournalArticlePermission.check(
			getPermissionChecker(), groupId, articleId, version,
			ActionKeys.UPDATE);

		return journalArticleLocalService.updateStatus(
			getUserId(), groupId, articleId, version, status, articleURL,
			new HashMap<String, Serializable>(), serviceContext);
	}

}