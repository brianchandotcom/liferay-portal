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

package com.liferay.portlet.journal.service.impl;

import com.liferay.portal.LocaleException;
import com.liferay.portal.NoSuchImageException;
import com.liferay.portal.kernel.dao.orm.QueryDefinition;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.lar.ExportImportThreadLocal;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.sanitizer.SanitizerUtil;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.QueryConfig;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.MathUtil;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.workflow.WorkflowHandlerRegistryUtil;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.Node;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.kernel.xml.XPath;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Image;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextUtil;
import com.liferay.portal.servlet.filters.cache.CacheUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.SubscriptionSender;
import com.liferay.portal.webserver.WebServerServletTokenUtil;
import com.liferay.portlet.asset.NoSuchEntryException;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.model.AssetLink;
import com.liferay.portlet.asset.model.AssetLinkConstants;
import com.liferay.portlet.dynamicdatamapping.NoSuchStructureException;
import com.liferay.portlet.dynamicdatamapping.NoSuchTemplateException;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.model.DDMTemplate;
import com.liferay.portlet.dynamicdatamapping.storage.FieldConstants;
import com.liferay.portlet.dynamicdatamapping.util.DDMXMLUtil;
import com.liferay.portlet.journal.ArticleContentException;
import com.liferay.portlet.journal.ArticleDisplayDateException;
import com.liferay.portlet.journal.ArticleExpirationDateException;
import com.liferay.portlet.journal.ArticleIdException;
import com.liferay.portlet.journal.ArticleReviewDateException;
import com.liferay.portlet.journal.ArticleSmallImageNameException;
import com.liferay.portlet.journal.ArticleSmallImageSizeException;
import com.liferay.portlet.journal.ArticleTitleException;
import com.liferay.portlet.journal.ArticleTypeException;
import com.liferay.portlet.journal.ArticleVersionException;
import com.liferay.portlet.journal.DuplicateArticleIdException;
import com.liferay.portlet.journal.NoSuchArticleException;
import com.liferay.portlet.journal.NoSuchArticleResourceException;
import com.liferay.portlet.journal.StructureXsdException;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.model.JournalArticleConstants;
import com.liferay.portlet.journal.model.JournalArticleDisplay;
import com.liferay.portlet.journal.model.JournalArticleResource;
import com.liferay.portlet.journal.model.JournalFolder;
import com.liferay.portlet.journal.model.impl.JournalArticleDisplayImpl;
import com.liferay.portlet.journal.service.base.JournalArticleLocalServiceBaseImpl;
import com.liferay.portlet.journal.social.JournalActivityKeys;
import com.liferay.portlet.journal.util.JournalUtil;
import com.liferay.portlet.journal.util.comparator.ArticleIDComparator;
import com.liferay.portlet.journal.util.comparator.ArticleVersionComparator;
import com.liferay.portlet.journalcontent.util.JournalContentUtil;
import com.liferay.portlet.social.model.SocialActivityConstants;
import com.liferay.portlet.trash.model.TrashEntry;
import com.liferay.portlet.trash.model.TrashVersion;
import com.liferay.portlet.trash.util.TrashUtil;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.portlet.PortletPreferences;

/**
 * The web content local service is responsible for accessing, creating,
 * modifying, searching, and deleting web content articles.
 *
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 * @author Bruno Farache
 * @author Juan Fernández
 * @author Sergio González
 */
public class JournalArticleLocalServiceImpl
	extends JournalArticleLocalServiceBaseImpl {

	/**
	 * Adds a web content article with additional parameters.
	 *
	 * @param  userId the primary key of the web content's creator/owner
	 * @param  groupId the primary key of the group
	 * @param  folderId the primary key of the web content folder
	 * @param  classNameId the primary key of the entity's instance the web
	 *         content is related to
	 * @param  classPK the primary key of the web content's related entity
	 * @param  articleId the primary key of the web content
	 * @param  autoArticleId whether to auto generate the web content ID
	 * @param  version the web content's version
	 * @param  titleMap the web content's locales and localized titles
	 * @param  descriptionMap the web content's locales and localized
	 *         descriptions
	 * @param  content the web content's content
	 * @param  type the web content's type
	 * @param  ddmStructureKey the primary key of the web content's DDM
	 *         structure
	 * @param  ddmTemplateKey the primary key of the web content's DDM template
	 * @param  layoutUuid the unique string identifying the web content's layout
	 * @param  displayDateMonth the month the web content is set to display
	 * @param  displayDateDay the calendar day the web content is set to display
	 * @param  displayDateYear the year the web content is set to display
	 * @param  displayDateHour the hour the web content is set to display
	 * @param  displayDateMinute the minute the web content is set to display
	 * @param  expirationDateMonth the month the web content is set to expire
	 * @param  expirationDateDay the calendar day the web content is set to
	 *         expire
	 * @param  expirationDateYear the year the web content is set to expire
	 * @param  expirationDateHour the hour the web content is set to expire
	 * @param  expirationDateMinute the minute the web content is set to expire
	 * @param  neverExpire whether the web content is not set to auto expire
	 * @param  reviewDateMonth the month the web content is set for review
	 * @param  reviewDateDay the calendar day the web content is set for review
	 * @param  reviewDateYear the year the web content is set for review
	 * @param  reviewDateHour the hour the web content is set for review
	 * @param  reviewDateMinute the minute the web content is set for review
	 * @param  neverReview whether the web content is not set for review
	 * @param  indexable whether the web content is searchable
	 * @param  smallImage whether the web content has a small image
	 * @param  smallImageURL the web content's small image URL
	 * @param  smallImageFile the web content's small image file
	 * @param  images the web content's images
	 * @param  articleURL the web content's accessible URL
	 * @param  serviceContext the service context to be applied. Can set the
	 *         UUID, creation date, modification date, expando bridge
	 *         attributes, guest permissions, group permissions, asset category
	 *         IDs, asset tag names, asset link entry IDs, and workflow actions
	 *         for the web content. Can also set whether to add the default
	 *         guest and group permissions.
	 * @return the web content
	 * @throws PortalException if a portal exception occurred
	 * @throws SystemException if a system exception occurred
	 */
	public JournalArticle addArticle(
			long userId, long groupId, long folderId, long classNameId,
			long classPK, String articleId, boolean autoArticleId,
			double version, Map<Locale, String> titleMap,
			Map<Locale, String> descriptionMap, String content, String type,
			String ddmStructureKey, String ddmTemplateKey, String layoutUuid,
			int displayDateMonth, int displayDateDay, int displayDateYear,
			int displayDateHour, int displayDateMinute, int expirationDateMonth,
			int expirationDateDay, int expirationDateYear,
			int expirationDateHour, int expirationDateMinute,
			boolean neverExpire, int reviewDateMonth, int reviewDateDay,
			int reviewDateYear, int reviewDateHour, int reviewDateMinute,
			boolean neverReview, boolean indexable, boolean smallImage,
			String smallImageURL, File smallImageFile,
			Map<String, byte[]> images, String articleURL,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		// Article

		User user = userPersistence.findByPrimaryKey(userId);
		articleId = articleId.trim().toUpperCase();

		Date displayDate = null;
		Date expirationDate = null;
		Date reviewDate = null;

		if (classNameId == JournalArticleConstants.CLASSNAME_ID_DEFAULT) {
			displayDate = PortalUtil.getDate(
				displayDateMonth, displayDateDay, displayDateYear,
				displayDateHour, displayDateMinute, user.getTimeZone(),
				ArticleDisplayDateException.class);

			if (!neverExpire) {
				expirationDate = PortalUtil.getDate(
					expirationDateMonth, expirationDateDay, expirationDateYear,
					expirationDateHour, expirationDateMinute,
					user.getTimeZone(), ArticleExpirationDateException.class);
			}

			if (!neverReview) {
				reviewDate = PortalUtil.getDate(
					reviewDateMonth, reviewDateDay, reviewDateYear,
					reviewDateHour, reviewDateMinute, user.getTimeZone(),
					ArticleReviewDateException.class);
			}
		}

		byte[] smallImageBytes = null;

		try {
			smallImageBytes = FileUtil.getBytes(smallImageFile);
		}
		catch (IOException ioe) {
		}

		Date now = new Date();

		validate(
			user.getCompanyId(), groupId, classNameId, articleId, autoArticleId,
			version, titleMap, content, type, ddmStructureKey, ddmTemplateKey,
			expirationDate, smallImage, smallImageURL, smallImageFile,
			smallImageBytes);

		if (autoArticleId) {
			articleId = String.valueOf(counterLocalService.increment());
		}

		long id = counterLocalService.increment();

		long resourcePrimKey =
			journalArticleResourceLocalService.getArticleResourcePrimKey(
				serviceContext.getUuid(), groupId, articleId);

		JournalArticle article = journalArticlePersistence.create(id);

		Locale locale = LocaleUtil.getDefault();

		String defaultLanguageId = ParamUtil.getString(
			serviceContext, "defaultLanguageId");

		if (Validator.isNull(defaultLanguageId)) {
			defaultLanguageId = LocalizationUtil.getDefaultLocale(content);
		}

		if (Validator.isNotNull(defaultLanguageId)) {
			locale = LocaleUtil.fromLanguageId(defaultLanguageId);
		}

		String title = titleMap.get(locale);

		content = format(
			user, groupId, articleId, version, false, content, ddmStructureKey,
			images);

		article.setResourcePrimKey(resourcePrimKey);
		article.setGroupId(groupId);
		article.setCompanyId(user.getCompanyId());
		article.setUserId(user.getUserId());
		article.setUserName(user.getFullName());
		article.setCreateDate(serviceContext.getCreateDate(now));
		article.setModifiedDate(serviceContext.getModifiedDate(now));
		article.setFolderId(folderId);
		article.setClassNameId(classNameId);
		article.setClassPK(classPK);
		article.setArticleId(articleId);
		article.setVersion(version);
		article.setTitleMap(titleMap, locale);
		article.setUrlTitle(
			getUniqueUrlTitle(id, articleId, title, null, serviceContext));
		article.setDescriptionMap(descriptionMap, locale);
		article.setContent(content);
		article.setType(type);
		article.setStructureId(ddmStructureKey);
		article.setTemplateId(ddmTemplateKey);
		article.setLayoutUuid(layoutUuid);
		article.setDisplayDate(displayDate);
		article.setExpirationDate(expirationDate);
		article.setReviewDate(reviewDate);
		article.setIndexable(indexable);
		article.setSmallImage(smallImage);
		article.setSmallImageId(counterLocalService.increment());
		article.setSmallImageURL(smallImageURL);

		if ((expirationDate == null) || expirationDate.after(now)) {
			article.setStatus(WorkflowConstants.STATUS_DRAFT);
		}
		else {
			article.setStatus(WorkflowConstants.STATUS_EXPIRED);
		}

		article.setExpandoBridgeAttributes(serviceContext);

		journalArticlePersistence.update(article);

		// Resources

		if (serviceContext.isAddGroupPermissions() ||
			serviceContext.isAddGuestPermissions()) {

			addArticleResources(
				article, serviceContext.isAddGroupPermissions(),
				serviceContext.isAddGuestPermissions());
		}
		else {
			addArticleResources(
				article, serviceContext.getGroupPermissions(),
				serviceContext.getGuestPermissions());
		}

		// Small image

		saveImages(
			smallImage, article.getSmallImageId(), smallImageFile,
			smallImageBytes);

		// Asset

		updateAsset(
			userId, article, serviceContext.getAssetCategoryIds(),
			serviceContext.getAssetTagNames(),
			serviceContext.getAssetLinkEntryIds());

		// Dynamic data mapping

		if (PortalUtil.getClassNameId(DDMStructure.class) == classNameId) {
			updateDDMStructureXSD(classPK, content, serviceContext);
		}

		// Message boards

		if (PropsValues.JOURNAL_ARTICLE_COMMENTS_ENABLED) {
			mbMessageLocalService.addDiscussionMessage(
				userId, article.getUserName(), groupId,
				JournalArticle.class.getName(), resourcePrimKey,
				WorkflowConstants.ACTION_PUBLISH);
		}

		// Email

		PortletPreferences preferences =
			ServiceContextUtil.getPortletPreferences(serviceContext);

		sendEmail(
			article, articleURL, preferences, "requested", serviceContext);

		// Workflow

		if (classNameId == JournalArticleConstants.CLASSNAME_ID_DEFAULT) {
			WorkflowHandlerRegistryUtil.startWorkflowInstance(
				user.getCompanyId(), groupId, userId,
				JournalArticle.class.getName(), article.getId(), article,
				serviceContext);

			if (serviceContext.getWorkflowAction() !=
					WorkflowConstants.ACTION_PUBLISH) {

				// Indexer

				reindex(article);
			}
		}
		else {
			updateStatus(
				userId, article, WorkflowConstants.STATUS_APPROVED, null,
				new HashMap<String, Serializable>(), serviceContext);
		}

		return getArticle(article.getPrimaryKey());
	}

	/**
	 * Adds a web content article.
	 *
	 * @param  userId the primary key of the web content's creator/owner
	 * @param  groupId the primary key of the group
	 * @param  folderId the primary key of the web content folder
	 * @param  titleMap the web content's locales and localized titles
	 * @param  descriptionMap the web content's locales and localized
	 *         descriptions
	 * @param  content the web content's content
	 * @param  ddmStructureKey the primary key of the web content's DDM
	 *         structure
	 * @param  ddmTemplateKey the primary key of the web content's DDM template
	 * @param  serviceContext the service context to be applied. Can set the
	 *         UUID, creation date, modification date, expando bridge
	 *         attributes, guest permissions, group permissions, asset category
	 *         IDs, asset tag names, asset link entry IDs, and workflow actions
	 *         for the web content. Can also set whether to add the default
	 *         guest and group permissions.
	 * @return the web content
	 * @throws PortalException if a portal exception occurred
	 * @throws SystemException if a system exception occurred
	 */
	public JournalArticle addArticle(
			long userId, long groupId, long folderId,
			Map<Locale, String> titleMap, Map<Locale, String> descriptionMap,
			String content, String ddmStructureKey, String ddmTemplateKey,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		Calendar calendar = CalendarFactoryUtil.getCalendar();

		int displayDateMonth = calendar.get(Calendar.MONTH);
		int displayDateDay = calendar.get(Calendar.DAY_OF_MONTH);
		int displayDateYear = calendar.get(Calendar.YEAR);
		int displayDateHour = calendar.get(Calendar.HOUR_OF_DAY);
		int displayDateMinute = calendar.get(Calendar.MINUTE);

		return addArticle(
			userId, groupId, folderId,
			JournalArticleConstants.CLASSNAME_ID_DEFAULT, 0, StringPool.BLANK,
			true, 1, titleMap, descriptionMap, content, "general",
			ddmStructureKey, ddmTemplateKey, null, displayDateMonth,
			displayDateDay, displayDateYear, displayDateHour, displayDateMinute,
			0, 0, 0, 0, 0, true, 0, 0, 0, 0, 0, true, true, false, null, null,
			null, null, serviceContext);
	}

	/**
	 * Adds the resources to the web content.
	 *
	 * @param  article the web content to add resources to
	 * @param  addGroupPermissions whether to add group permissions
	 * @param  addGuestPermissions whether to add guest permissions
	 * @throws PortalException if a portal exception occurred
	 * @throws SystemException if a system exception occurred
	 */
	public void addArticleResources(
			JournalArticle article, boolean addGroupPermissions,
			boolean addGuestPermissions)
		throws PortalException, SystemException {

		resourceLocalService.addResources(
			article.getCompanyId(), article.getGroupId(), article.getUserId(),
			JournalArticle.class.getName(), article.getResourcePrimKey(), false,
			addGroupPermissions, addGuestPermissions);
	}

	/**
	 * Adds the model resources with the permissions to the web content.
	 *
	 * @param  article the web content to add resources to
	 * @param  groupPermissions the group permissions to be added
	 * @param  guestPermissions the guest permissions to be added
	 * @throws PortalException if a portal exception occurred
	 * @throws SystemException if a system exception occurred
	 */
	public void addArticleResources(
			JournalArticle article, String[] groupPermissions,
			String[] guestPermissions)
		throws PortalException, SystemException {

		resourceLocalService.addModelResources(
			article.getCompanyId(), article.getGroupId(), article.getUserId(),
			JournalArticle.class.getName(), article.getResourcePrimKey(),
			groupPermissions, guestPermissions);
	}

	/**
	 * Adds the resources to the most recently created web content.
	 *
	 * @param  groupId the primary key of the group
	 * @param  articleId the primary key of the web content
	 * @param  addGroupPermissions whether to add group permissions
	 * @param  addGuestPermissions whether to add guest permissions
	 * @throws PortalException if a portal exception occurred
	 * @throws SystemException if a system exception occurred
	 */
	public void addArticleResources(
			long groupId, String articleId, boolean addGroupPermissions,
			boolean addGuestPermissions)
		throws PortalException, SystemException {

		JournalArticle article = getLatestArticle(groupId, articleId);

		addArticleResources(article, addGroupPermissions, addGuestPermissions);
	}

	/**
	 * Adds the resources with the permissions to the most recently created web
	 * content.
	 *
	 * @param  groupId the primary key of the group
	 * @param  articleId the primary key of the web content
	 * @param  groupPermissions the group permissions to be added
	 * @param  guestPermissions the guest permissions to be added
	 * @throws PortalException if a portal exception occurred
	 * @throws SystemException if a system exception occurred
	 */
	public void addArticleResources(
			long groupId, String articleId, String[] groupPermissions,
			String[] guestPermissions)
		throws PortalException, SystemException {

		JournalArticle article = getLatestArticle(groupId, articleId);

		addArticleResources(article, groupPermissions, guestPermissions);
	}

	/**
	 * Returns the web content with the group ID, article ID, and version. This
	 * method checks for the web content's resource primary key and, if not
	 * found, the method creates a new one.
	 *
	 * @param  groupId the primary key of the group
	 * @param  articleId the primary key of the web content
	 * @param  version the web content's version
	 * @return the matching web content
	 * @throws PortalException if a portal exception occurred
	 * @throws SystemException if a system exception occurred
	 */
	public JournalArticle checkArticleResourcePrimKey(
			long groupId, String articleId, double version)
		throws PortalException, SystemException {

		JournalArticle article = journalArticlePersistence.findByG_A_V(
			groupId, articleId, version);

		if (article.getResourcePrimKey() > 0) {
			return article;
		}

		long resourcePrimKey =
			journalArticleResourceLocalService.getArticleResourcePrimKey(
				groupId, articleId);

		article.setResourcePrimKey(resourcePrimKey);

		journalArticlePersistence.update(article);

		return article;
	}

	/**
	 * Checks all web content based on their current workflow.
	 *
	 * @throws PortalException if a portal exception occurred
	 * @throws SystemException if a system exception occurred
	 */
	public void checkArticles() throws PortalException, SystemException {
		Date now = new Date();

		List<JournalArticle> articles =
			journalArticleFinder.findByExpirationDate(
				JournalArticleConstants.CLASSNAME_ID_DEFAULT,
				new Date(now.getTime() + _JOURNAL_ARTICLE_CHECK_INTERVAL),
				new QueryDefinition(WorkflowConstants.STATUS_APPROVED));

		if (_log.isDebugEnabled()) {
			_log.debug("Expiring " + articles.size() + " articles");
		}

		Set<Long> companyIds = new HashSet<Long>();

		for (JournalArticle article : articles) {
			if (PropsValues.JOURNAL_ARTICLE_EXPIRE_ALL_VERSIONS) {
				List<JournalArticle> currentArticles =
					journalArticlePersistence.findByG_A(
						article.getGroupId(), article.getArticleId(),
						QueryUtil.ALL_POS, QueryUtil.ALL_POS,
						new ArticleVersionComparator(true));

				for (JournalArticle currentArticle : currentArticles) {
					currentArticle.setExpirationDate(
						article.getExpirationDate());
					currentArticle.setStatus(WorkflowConstants.STATUS_EXPIRED);

					journalArticlePersistence.update(currentArticle);
				}
			}
			else {
				article.setStatus(WorkflowConstants.STATUS_EXPIRED);

				journalArticlePersistence.update(article);
			}

			updatePreviousApprovedArticle(article);

			JournalContentUtil.clearCache(
				article.getGroupId(), article.getArticleId(),
				article.getTemplateId());

			companyIds.add(article.getCompanyId());
		}

		for (long companyId : companyIds) {
			CacheUtil.clearCache(companyId);
		}

		if (_previousCheckDate == null) {
			_previousCheckDate = new Date(
				now.getTime() - _JOURNAL_ARTICLE_CHECK_INTERVAL);
		}

		articles = journalArticleFinder.findByReviewDate(
			JournalArticleConstants.CLASSNAME_ID_DEFAULT, now,
			_previousCheckDate);

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Sending review notifications for " + articles.size() +
					" articles");
		}

		for (JournalArticle article : articles) {
			String articleURL = StringPool.BLANK;

			long ownerId = article.getGroupId();
			int ownerType = PortletKeys.PREFS_OWNER_TYPE_GROUP;
			long plid = PortletKeys.PREFS_PLID_SHARED;
			String portletId = PortletKeys.JOURNAL;

			PortletPreferences preferences =
				portletPreferencesLocalService.getPreferences(
					article.getCompanyId(), ownerId, ownerType, plid,
					portletId);

			sendEmail(
				article, articleURL, preferences, "review",
				new ServiceContext());
		}

		_previousCheckDate = now;
	}

	/**
	 * Checks if the web content matching the group, article ID, and version
	 * contains new line strings.
	 *
	 * @param  groupId the primary key of the group
	 * @param  articleId the primary key of the web content
	 * @param  version the web content's version
	 * @throws PortalException if a portal exception occurred
	 * @throws SystemException if a system exception occurred
	 */
	public void checkNewLine(long groupId, String articleId, double version)
		throws PortalException, SystemException {

		JournalArticle article = journalArticlePersistence.findByG_A_V(
			groupId, articleId, version);

		String content = GetterUtil.getString(article.getContent());

		if (content.contains("\\n")) {
			content = StringUtil.replace(
				content, new String[] {"\\n", "\\r"},
				new String[] {"\n", "\r"});

			article.setContent(content);

			journalArticlePersistence.update(article);
		}
	}

	/**
	 * Checks if the web content matching the group, article ID, and version has
	 * a structure.
	 *
	 * @param  groupId the primary key of the group
	 * @param  articleId the primary key of the web content
	 * @param  version the web content's version
	 * @throws PortalException if a portal exception occurred
	 * @throws SystemException if a system exception occurred
	 */
	public void checkStructure(long groupId, String articleId, double version)
		throws PortalException, SystemException {

		JournalArticle article = journalArticlePersistence.findByG_A_V(
			groupId, articleId, version);

		if (Validator.isNull(article.getStructureId())) {
			return;
		}

		checkStructure(article);
	}

	/**
	 * Copies the web content matching the group ID, article ID, and version.
	 * This method creates a new web content article, extracting all the values
	 * from the old one and updating its article ID.
	 *
	 * @param  userId the primary key of the web content's creator/owner
	 * @param  groupId the primary key of the group
	 * @param  oldArticleId the primary key of the old web content
	 * @param  newArticleId the primary key of the new web content
	 * @param  autoArticleId whether to auto generate the web content ID
	 * @param  version the web content's version
	 * @return the new template
	 * @throws PortalException if a portal exception occurred
	 * @throws SystemException if a system exception occurred
	 */
	public JournalArticle copyArticle(
			long userId, long groupId, String oldArticleId, String newArticleId,
			boolean autoArticleId, double version)
		throws PortalException, SystemException {

		// Article

		User user = userPersistence.findByPrimaryKey(userId);
		oldArticleId = oldArticleId.trim().toUpperCase();
		newArticleId = newArticleId.trim().toUpperCase();
		Date now = new Date();

		JournalArticle oldArticle = journalArticlePersistence.findByG_A_V(
			groupId, oldArticleId, version);

		if (autoArticleId) {
			newArticleId = String.valueOf(counterLocalService.increment());
		}
		else {
			validate(newArticleId);

			if (journalArticlePersistence.countByG_A(
					groupId, newArticleId) > 0) {

				throw new DuplicateArticleIdException();
			}
		}

		long id = counterLocalService.increment();

		long resourcePrimKey =
			journalArticleResourceLocalService.getArticleResourcePrimKey(
				groupId, newArticleId);

		JournalArticle newArticle = journalArticlePersistence.create(id);

		newArticle.setResourcePrimKey(resourcePrimKey);
		newArticle.setGroupId(groupId);
		newArticle.setCompanyId(user.getCompanyId());
		newArticle.setUserId(user.getUserId());
		newArticle.setUserName(user.getFullName());
		newArticle.setCreateDate(now);
		newArticle.setModifiedDate(now);
		newArticle.setArticleId(newArticleId);
		newArticle.setVersion(JournalArticleConstants.VERSION_DEFAULT);
		newArticle.setTitle(oldArticle.getTitle());
		newArticle.setUrlTitle(
			getUniqueUrlTitle(
				id, groupId, newArticleId, oldArticle.getTitleCurrentValue()));
		newArticle.setDescription(oldArticle.getDescription());

		try {
			copyArticleImages(oldArticle, newArticle);
		}
		catch (Exception e) {
			newArticle.setContent(oldArticle.getContent());
		}

		newArticle.setType(oldArticle.getType());
		newArticle.setStructureId(oldArticle.getStructureId());
		newArticle.setTemplateId(oldArticle.getTemplateId());
		newArticle.setLayoutUuid(oldArticle.getLayoutUuid());
		newArticle.setDisplayDate(oldArticle.getDisplayDate());
		newArticle.setExpirationDate(oldArticle.getExpirationDate());
		newArticle.setReviewDate(oldArticle.getReviewDate());
		newArticle.setIndexable(oldArticle.isIndexable());
		newArticle.setSmallImage(oldArticle.isSmallImage());
		newArticle.setSmallImageId(counterLocalService.increment());
		newArticle.setSmallImageURL(oldArticle.getSmallImageURL());

		if (oldArticle.isPending()) {
			newArticle.setStatus(WorkflowConstants.STATUS_DRAFT);
		}
		else {
			newArticle.setStatus(oldArticle.getStatus());
		}

		newArticle.setExpandoBridgeAttributes(oldArticle);

		journalArticlePersistence.update(newArticle);

		// Resources

		addArticleResources(newArticle, true, true);

		// Small image

		if (oldArticle.getSmallImage()) {
			Image image = imageLocalService.getImage(
				oldArticle.getSmallImageId());

			byte[] smallImageBytes = image.getTextObj();

			imageLocalService.updateImage(
				newArticle.getSmallImageId(), smallImageBytes);
		}

		// Asset

		long[] assetCategoryIds = assetCategoryLocalService.getCategoryIds(
			JournalArticle.class.getName(), oldArticle.getResourcePrimKey());
		String[] assetTagNames = assetTagLocalService.getTagNames(
			JournalArticle.class.getName(), oldArticle.getResourcePrimKey());

		updateAsset(userId, newArticle, assetCategoryIds, assetTagNames, null);

		return newArticle;
	}

	/**
	 * Deletes the web content and its resources.
	 *
	 * @param  article the web content
	 * @throws PortalException if a portal exception occurred
	 * @throws SystemException if a system exception occurred
	 */
	public void deleteArticle(JournalArticle article)
		throws PortalException, SystemException {

		deleteArticle(article, StringPool.BLANK, null);
	}

	/**
	 * Deletes the matching web content and its resources.
	 *
	 * @param  article the web content
	 * @param  articleURL the web content's accessible URL
	 * @param  serviceContext the service context to be applied. Can set the
	 *         portlet preferences for the web content.
	 * @throws PortalException if a portal exception occurred
	 * @throws SystemException if a system exception occurred
	 */
	public void deleteArticle(
			JournalArticle article, String articleURL,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		if (article.isApproved() &&
			isLatestVersion(
				article.getGroupId(), article.getArticleId(),
				article.getVersion(), WorkflowConstants.STATUS_APPROVED)) {

			updatePreviousApprovedArticle(article);
		}
		else if (article.isInTrash()) {
			Indexer indexer = IndexerRegistryUtil.nullSafeGetIndexer(
				JournalArticle.class);

			indexer.delete(article);
		}

		// Email

		if ((serviceContext != null) && Validator.isNotNull(articleURL)) {
			PortletPreferences preferences =
				ServiceContextUtil.getPortletPreferences(serviceContext);

			if ((preferences != null) && !article.isApproved() &&
				isLatestVersion(
					article.getGroupId(), article.getArticleId(),
					article.getVersion())) {

				sendEmail(
					article, articleURL, preferences, "denied", serviceContext);
			}
		}

		// Images

		journalArticleImageLocalService.deleteImages(
			article.getGroupId(), article.getArticleId(), article.getVersion());

		// Workflow

		if (!article.isDraft()) {
			workflowInstanceLinkLocalService.deleteWorkflowInstanceLink(
				article.getCompanyId(), article.getGroupId(),
				JournalArticle.class.getName(), article.getId());
		}

		int articlesCount = journalArticlePersistence.countByG_A(
			article.getGroupId(), article.getArticleId());

		if (articlesCount == 1) {

			// Subscriptions

			subscriptionLocalService.deleteSubscriptions(
				article.getCompanyId(), JournalArticle.class.getName(),
				article.getResourcePrimKey());

			// Ratings

			ratingsStatsLocalService.deleteStats(
				JournalArticle.class.getName(), article.getResourcePrimKey());

			// Message boards

			mbMessageLocalService.deleteDiscussionMessages(
				JournalArticle.class.getName(), article.getResourcePrimKey());

			// Asset

			assetEntryLocalService.deleteEntry(
				JournalArticle.class.getName(), article.getResourcePrimKey());

			// Content searches

			journalContentSearchLocalService.deleteArticleContentSearches(
				article.getGroupId(), article.getArticleId());

			// Small image

			imageLocalService.deleteImage(article.getSmallImageId());

			// Expando

			expandoValueLocalService.deleteValues(
				JournalArticle.class.getName(), article.getId());

			// Trash

			trashEntryLocalService.deleteEntry(
				JournalArticle.class.getName(), article.getResourcePrimKey());

			// Resources

			resourceLocalService.deleteResource(
				article.getCompanyId(), JournalArticle.class.getName(),
				ResourceConstants.SCOPE_INDIVIDUAL,
				article.getResourcePrimKey());

			// Resource

			try {
				journalArticleResourceLocalService.deleteArticleResource(
					article.getGroupId(), article.getArticleId());
			}
			catch (NoSuchArticleResourceException nsare) {
			}
		}

		// Article

		journalArticlePersistence.remove(article);
	}

	/**
	 * Deletes the matching web content and its resources.
	 *
	 * @param  groupId the primary key of the group
	 * @param  articleId the primary key of the web content
	 * @param  version the web content's version
	 * @param  articleURL the web content's accessible URL
	 * @param  serviceContext the service context to be applied. Can set the
	 *         portlet preferences for the web content.
	 * @throws PortalException if a portal exception occurred
	 * @throws SystemException if a system exception occurred
	 */
	public void deleteArticle(
			long groupId, String articleId, double version, String articleURL,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		JournalArticle article = journalArticlePersistence.findByG_A_V(
			groupId, articleId, version);

		deleteArticle(article, articleURL, serviceContext);
	}

	/**
	 * Deletes the matching web content and its resources.
	 *
	 * @param  groupId the primary key of the group
	 * @param  articleId the primary key of the web content
	 * @param  serviceContext the service context to be applied. Can set the
	 *         portlet preferences for the web content.
	 * @throws PortalException if a portal exception occurred
	 * @throws SystemException if a system exception occurred
	 */
	public void deleteArticle(
			long groupId, String articleId, ServiceContext serviceContext)
		throws PortalException, SystemException {

		List<JournalArticle> articles = journalArticlePersistence.findByG_A(
			groupId, articleId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			new ArticleVersionComparator(true));

		for (JournalArticle article : articles) {
			deleteArticle(article, null, serviceContext);
		}
	}

	/**
	 * Deletes all the web content of the group.
	 *
	 * @param  groupId the primary key of the group
	 * @throws PortalException if a portal exception occurred
	 * @throws SystemException if a system exception occurred
	 */
	public void deleteArticles(long groupId)
		throws PortalException, SystemException {

		for (JournalArticle article :
				journalArticlePersistence.findByGroupId(groupId)) {

			deleteArticle(article, null, null);
		}
	}

	/**
	 * Deletes the matching web content and its resources.
	 *
	 * @param  groupId the primary key of the group
	 * @param  folderId the primary key of the web content folder
	 * @throws PortalException if a portal exception occurred
	 * @throws SystemException if a system exception occurred
	 */
	public void deleteArticles(long groupId, long folderId)
		throws PortalException, SystemException {

		deleteArticles(groupId, folderId, true);
	}

	/**
	 * Deletes the matching recycled web content and its resources.
	 *
	 * @param  groupId the primary key of the group
	 * @param  folderId the primary key of the web content folder
	 * @param  includeTrashedEntries whether to include recycled web content
	 *         articles
	 * @throws PortalException if a portal exception occurred
	 * @throws SystemException if a system exception occurred
	 */
	public void deleteArticles(
			long groupId, long folderId, boolean includeTrashedEntries)
		throws PortalException, SystemException {

		for (JournalArticle article :
				journalArticlePersistence.findByG_F(groupId, folderId)) {

			if (includeTrashedEntries || !article.isInTrash()) {
				deleteArticle(article, null, null);
			}
		}
	}

	/**
	 * Sets the UUID of the matching web content.
	 *
	 * @param  groupId the primary key of the group
	 * @param  layoutUuid the unique string identifying the web content's layout
	 * @throws SystemException if a system exception occurred
	 */
	public void deleteLayoutArticleReferences(long groupId, String layoutUuid)
		throws SystemException {

		List<JournalArticle> articles = journalArticlePersistence.findByG_L(
			groupId, layoutUuid);

		for (JournalArticle article : articles) {
			article.setLayoutUuid(StringPool.BLANK);

			journalArticlePersistence.update(article);
		}
	}

	/**
	 * Updates the web content's status to <code>Expired</code>.
	 *
	 * @param  userId the primary key of the web content's creator/owner
	 * @param  groupId the primary key of the group
	 * @param  articleId the primary key of the web content
	 * @param  version the web content's version
	 * @param  articleURL the web content's accessible URL
	 * @param  serviceContext the service context to be applied. Can set the
	 *         modification date, portlet preferences, and can set whether to
	 *         add the default command update for the web content.
	 * @return the web content's updated status
	 * @throws PortalException if a portal exception occurred
	 * @throws SystemException if a system exception occurred
	 */
	public JournalArticle expireArticle(
			long userId, long groupId, String articleId, double version,
			String articleURL, ServiceContext serviceContext)
		throws PortalException, SystemException {

		return updateStatus(
			userId, groupId, articleId, version,
			WorkflowConstants.STATUS_EXPIRED, articleURL,
			new HashMap<String, Serializable>(), serviceContext);
	}

	/**
	 * Expires the matching web content and its resources.
	 *
	 * @param  userId the primary key of the web content's creator/owner
	 * @param  groupId the primary key of the group
	 * @param  articleId the primary key of the web content
	 * @param  articleURL the web content's accessible URL
	 * @param  serviceContext the service context to be applied. Can set the
	 *         modification date, portlet preferences, and can set whether to
	 *         add the default command update for the web content.
	 * @throws PortalException if a portal exception occurred
	 * @throws SystemException if a system exception occurred
	 */
	public void expireArticle(
			long userId, long groupId, String articleId, String articleURL,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		if (PropsValues.JOURNAL_ARTICLE_EXPIRE_ALL_VERSIONS) {
			List<JournalArticle> articles = journalArticlePersistence.findByG_A(
				groupId, articleId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				new ArticleVersionComparator(true));

			for (JournalArticle article : articles) {
				expireArticle(
					userId, groupId, article.getArticleId(),
					article.getVersion(), articleURL, serviceContext);
			}
		}
		else {
			JournalArticle article = getLatestArticle(
				groupId, articleId, WorkflowConstants.STATUS_APPROVED);

			expireArticle(
				userId, groupId, article.getArticleId(), article.getVersion(),
				articleURL, serviceContext);
		}
	}

	/**
	 * Returns the web content matching the UUID and group.
	 *
	 * @param  uuid the unique string identifying the web content
	 * @param  groupId the primary key of the group
	 * @return the matching web content
	 * @throws SystemException if a system exception occurred
	 */
	public JournalArticle fetchArticle(String uuid, long groupId)
		throws SystemException {

		return journalArticlePersistence.fetchByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the web content with the ID.
	 *
	 * @param  id the primary key of the web content
	 * @return the web content with the ID
	 * @throws PortalException if a matching web content article could not be
	 *         found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalArticle getArticle(long id)
		throws PortalException, SystemException {

		return journalArticlePersistence.findByPrimaryKey(id);
	}

	/**
	 * Returns the latest web content that is approved, or the latest unapproved
	 * web content if none are approved. Both approved and unapproved web
	 * content must match the group and article ID.
	 *
	 * @param  groupId the primary key of the group
	 * @param  articleId the primary key of the web content
	 * @return the matching web content
	 * @throws PortalException if a matching web content article could not be
	 *         found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalArticle getArticle(long groupId, String articleId)
		throws PortalException, SystemException {

		// Get the latest article that is approved, if none are approved, get
		// the latest unapproved article

		try {
			return getLatestArticle(
				groupId, articleId, WorkflowConstants.STATUS_APPROVED);
		}
		catch (NoSuchArticleException nsae) {
			return getLatestArticle(
				groupId, articleId, WorkflowConstants.STATUS_ANY);
		}
	}

	/**
	 * Returns the web content matching the group, article ID, and version.
	 *
	 * @param  groupId the primary key of the group
	 * @param  articleId the primary key of the web content
	 * @param  version the web content's version
	 * @return the matching web content
	 * @throws PortalException if a matching web content article could not be
	 *         found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalArticle getArticle(
			long groupId, String articleId, double version)
		throws PortalException, SystemException {

		return journalArticlePersistence.findByG_A_V(
			groupId, articleId, version);
	}

	/**
	 * Returns the web content matching the group, class name ID, and class PK.
	 *
	 * @param  groupId the primary key of the group
	 * @param  className the class name for the web content's related model
	 * @param  classPK the primary key of the web content's related entity
	 * @return the matching web content
	 * @throws PortalException if a matching web content article could not be
	 *         found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalArticle getArticle(
			long groupId, String className, long classPK)
		throws PortalException, SystemException {

		long classNameId = PortalUtil.getClassNameId(className);

		List<JournalArticle> articles = journalArticlePersistence.findByG_C_C(
			groupId, classNameId, classPK);

		if (articles.isEmpty()) {
			throw new NoSuchArticleException(
				"No approved JournalArticle exists with the key {groupId=" +
					groupId + ", className=" + className + ", classPK=" +
						classPK + "}");
		}

		return articles.get(0);
	}

	/**
	 * Returns the latest web content that is approved, or the latest unapproved
	 * web content if none are approved. Both approved and unapproved web
	 * content must match the group and URL title.
	 *
	 * @param  groupId the primary key of the group
	 * @param  urlTitle the web content's accessible URL title
	 * @return the matching web content
	 * @throws PortalException if a portal exception occurred
	 * @throws SystemException if a system exception occurred
	 */
	public JournalArticle getArticleByUrlTitle(long groupId, String urlTitle)
		throws PortalException, SystemException {

		// Get the latest article that is approved, if none are approved, get
		// the latest unapproved article

		try {
			return getLatestArticleByUrlTitle(
				groupId, urlTitle, WorkflowConstants.STATUS_APPROVED);
		}
		catch (NoSuchArticleException nsae) {
			return getLatestArticleByUrlTitle(
				groupId, urlTitle, WorkflowConstants.STATUS_PENDING);
		}
	}

	/**
	 * Returns the extracted content used for the web content's display.
	 *
	 * @param  article the web content
	 * @param  ddmTemplateKey the primary key of the web content's DDM template
	 * @param  viewMode the mode in which the web content is being viewed
	 * @param  languageId the primary key of the web content's default user's
	 *         language
	 * @param  themeDisplay the web content's current theme display
	 * @return the extracted content used for the web content's display
	 * @throws PortalException if a portal exception occurred
	 * @throws SystemException if a system exception occurred
	 */
	public String getArticleContent(
			JournalArticle article, String ddmTemplateKey, String viewMode,
			String languageId, ThemeDisplay themeDisplay)
		throws PortalException, SystemException {

		JournalArticleDisplay articleDisplay = getArticleDisplay(
			article, ddmTemplateKey, viewMode, languageId, 1, null,
			themeDisplay);

		if (articleDisplay == null) {
			return StringPool.BLANK;
		}
		else {
			return articleDisplay.getContent();
		}
	}

	/**
	 * Returns the extracted content used for the web content's display.
	 *
	 * @param  groupId the primary key of the group
	 * @param  articleId the primary key of the web content
	 * @param  version the web content's version
	 * @param  viewMode the mode in which the web content is being viewed
	 * @param  ddmTemplateKey the primary key of the web content's DDM template
	 * @param  languageId the primary key of the web content's default user's
	 *         language
	 * @param  themeDisplay the web content's current theme display
	 * @return the extracted content used for the web content's display
	 * @throws PortalException if a portal exception occurred
	 * @throws SystemException if a system exception occurred
	 */
	public String getArticleContent(
			long groupId, String articleId, double version, String viewMode,
			String ddmTemplateKey, String languageId, ThemeDisplay themeDisplay)
		throws PortalException, SystemException {

		JournalArticleDisplay articleDisplay = getArticleDisplay(
			groupId, articleId, version, ddmTemplateKey, viewMode, languageId,
			themeDisplay);

		if (articleDisplay == null) {
			return StringPool.BLANK;
		}
		else {
			return articleDisplay.getContent();
		}
	}

	/**
	 * Returns the extracted content from the web content.
	 *
	 * @param  groupId the primary key of the group
	 * @param  articleId the primary key of the web content
	 * @param  version the web content's version
	 * @param  viewMode the mode in which the web content is being viewed
	 * @param  languageId the primary key of the web content's default user's
	 *         language
	 * @param  themeDisplay the web content's current theme display
	 * @return the extracted content from the web content
	 * @throws PortalException if a portal exception occurred
	 * @throws SystemException if a system exception occurred
	 */
	public String getArticleContent(
			long groupId, String articleId, double version, String viewMode,
			String languageId, ThemeDisplay themeDisplay)
		throws PortalException, SystemException {

		return getArticleContent(
			groupId, articleId, version, viewMode, null, languageId,
			themeDisplay);
	}

	/**
	 * Returns the extracted content used for the web content's display.
	 *
	 * @param  groupId the primary key of the group
	 * @param  articleId the primary key of the web content
	 * @param  viewMode the mode in which the web content is being viewed
	 * @param  ddmTemplateKey the primary key of the web content's DDM template
	 * @param  languageId the primary key of the web content's default user's
	 *         language
	 * @param  themeDisplay the web content's current theme display
	 * @return the extracted content used for the web content's display
	 * @throws PortalException if a portal exception occurred
	 * @throws SystemException if a system exception occurred
	 */
	public String getArticleContent(
			long groupId, String articleId, String viewMode,
			String ddmTemplateKey, String languageId, ThemeDisplay themeDisplay)
		throws PortalException, SystemException {

		JournalArticleDisplay articleDisplay = getArticleDisplay(
			groupId, articleId, ddmTemplateKey, viewMode, languageId,
			themeDisplay);

		return articleDisplay.getContent();
	}

	/**
	 * Returns the extracted content from the web content.
	 *
	 * @param  groupId the primary key of the group
	 * @param  articleId the primary key of the web content
	 * @param  viewMode the mode in which the web content is being viewed
	 * @param  languageId the primary key of the web content's default user's
	 *         language
	 * @param  themeDisplay the web content's current theme display
	 * @return the extracted content from the web content
	 * @throws PortalException if a portal exception occurred
	 * @throws SystemException if a system exception occurred
	 */
	public String getArticleContent(
			long groupId, String articleId, String viewMode, String languageId,
			ThemeDisplay themeDisplay)
		throws PortalException, SystemException {

		return getArticleContent(
			groupId, articleId, viewMode, null, languageId, themeDisplay);
	}

	/**
	 * Returns the web content display implementation by searching for the DDM
	 * template matching the group, class name ID, and DDM template key.
	 *
	 * @param  article the web content
	 * @param  ddmTemplateKey the primary key of the web content's DDM template
	 * @param  viewMode the mode in which the web content is being viewed
	 * @param  languageId the primary key of the web content's default user's
	 *         language
	 * @param  page the web content's page number
	 * @param  xmlRequest the request that serializes the web content into a
	 *         hierarchical hash map
	 * @param  themeDisplay the web content's current theme display
	 * @return the web content display implementation
	 * @throws PortalException if a portal exception occurred
	 * @throws SystemException if a system exception occurred
	 */
	public JournalArticleDisplay getArticleDisplay(
			JournalArticle article, String ddmTemplateKey, String viewMode,
			String languageId, int page, String xmlRequest,
			ThemeDisplay themeDisplay)
		throws PortalException, SystemException {

		String content = null;

		if (page < 1) {
			page = 1;
		}

		int numberOfPages = 1;
		boolean paginate = false;
		boolean pageFlow = false;

		boolean cacheable = true;

		if (Validator.isNull(xmlRequest)) {
			xmlRequest = "<request />";
		}

		Map<String, String> tokens = JournalUtil.getTokens(
			article.getGroupId(), themeDisplay, xmlRequest);

		tokens.put(
			"article_resource_pk",
			String.valueOf(article.getResourcePrimKey()));

		String defaultDDMTemplateKey = article.getTemplateId();

		if (article.isTemplateDriven()) {
			if (Validator.isNull(ddmTemplateKey)) {
				ddmTemplateKey = defaultDDMTemplateKey;
			}

			tokens.put("structure_id", article.getStructureId());
			tokens.put("template_id", ddmTemplateKey);
		}

		String xml = article.getContent();

		try {
			Document document = null;

			Element rootElement = null;

			if (article.isTemplateDriven()) {
				document = SAXReaderUtil.read(xml);

				rootElement = document.getRootElement();

				Document requestDocument = SAXReaderUtil.read(xmlRequest);

				List<Element> pages = rootElement.elements("page");

				if (!pages.isEmpty()) {
					pageFlow = true;

					String targetPage = requestDocument.valueOf(
						"/request/parameters/parameter[name='targetPage']/" +
							"value");

					Element pageElement = null;

					if (Validator.isNotNull(targetPage)) {
						targetPage = HtmlUtil.escapeXPathAttribute(targetPage);

						XPath xPathSelector = SAXReaderUtil.createXPath(
							"/root/page[@id = " + targetPage + "]");

						pageElement = (Element)xPathSelector.selectSingleNode(
							document);
					}

					if (pageElement != null) {
						document = SAXReaderUtil.createDocument(pageElement);

						rootElement = document.getRootElement();

						numberOfPages = pages.size();
					}
					else {
						if (page > pages.size()) {
							page = 1;
						}

						pageElement = pages.get(page - 1);

						document = SAXReaderUtil.createDocument(pageElement);

						rootElement = document.getRootElement();

						numberOfPages = pages.size();
						paginate = true;
					}
				}

				rootElement.add(requestDocument.getRootElement().createCopy());

				JournalUtil.addAllReservedEls(
					rootElement, tokens, article, languageId, themeDisplay);

				xml = DDMXMLUtil.formatXML(document);
			}
		}
		catch (DocumentException de) {
			throw new SystemException(de);
		}

		try {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Transforming " + article.getArticleId() + " " +
						article.getVersion() + " " + languageId);
			}

			String script = null;
			String langType = null;

			if (article.isTemplateDriven()) {

				// Try with specified template first (in the current group and
				// the global group). If a template is not specified, use the
				// default one. If the specified template does not exist, use the
				// default one. If the default one does not exist, throw an
				// exception.

				DDMTemplate ddmTemplate = null;

				try {
					ddmTemplate = ddmTemplatePersistence.findByG_C_T(
						article.getGroupId(),
						PortalUtil.getClassNameId(DDMStructure.class),
						ddmTemplateKey);
				}
				catch (NoSuchTemplateException nste1) {
					try {
						Group companyGroup = groupLocalService.getCompanyGroup(
							article.getCompanyId());

						ddmTemplate = ddmTemplatePersistence.findByG_C_T(
							companyGroup.getGroupId(),
							PortalUtil.getClassNameId(DDMStructure.class),
							ddmTemplateKey);

						tokens.put(
							"company_group_id",
							String.valueOf(companyGroup.getGroupId()));
					}
					catch (NoSuchTemplateException nste2) {
						if (!defaultDDMTemplateKey.equals(ddmTemplateKey)) {
							ddmTemplate = ddmTemplatePersistence.findByG_C_T(
								article.getGroupId(),
								PortalUtil.getClassNameId(DDMStructure.class),
								defaultDDMTemplateKey);
						}
						else {
							throw nste1;
						}
					}
				}

				script = ddmTemplate.getScript();
				langType = ddmTemplate.getLanguage();
				cacheable = ddmTemplate.isCacheable();
			}

			content = JournalUtil.transform(
				themeDisplay, tokens, viewMode, languageId, xml, script,
				langType);

			if (!pageFlow) {
				String[] pieces = StringUtil.split(
					content, PropsValues.JOURNAL_ARTICLE_TOKEN_PAGE_BREAK);

				if (pieces.length > 1) {
					if (page > pieces.length) {
						page = 1;
					}

					content = pieces[page - 1];
					numberOfPages = pieces.length;
					paginate = true;
				}
			}
		}
		catch (Exception e) {
			throw new SystemException(e);
		}

		return new JournalArticleDisplayImpl(
			article.getCompanyId(), article.getId(),
			article.getResourcePrimKey(), article.getGroupId(),
			article.getUserId(), article.getArticleId(), article.getVersion(),
			article.getTitle(languageId), article.getUrlTitle(),
			article.getDescription(languageId), article.getAvailableLocales(),
			content, article.getType(), article.getStructureId(),
			ddmTemplateKey, article.isSmallImage(), article.getSmallImageId(),
			article.getSmallImageURL(), numberOfPages, page, paginate,
			cacheable);
	}

	/**
	 * Returns the web content display matching the group, article ID, and
	 * version. This method checks the expiration date of the web content and if
	 * expired, the web content is not returned.
	 *
	 * @param  groupId the primary key of the group
	 * @param  articleId the primary key of the web content
	 * @param  version the web content's version
	 * @param  ddmTemplateKey the primary key of the web content's DDM template
	 * @param  viewMode the mode in which the web content is being viewed
	 * @param  languageId the primary key of the web content's default user's
	 *         language
	 * @param  page the web content's page number
	 * @param  xmlRequest the request that serializes the web content into a
	 *         hierarchical hash map
	 * @param  themeDisplay the web content's current theme display
	 * @return the matching web content display
	 * @throws PortalException if a portal exception occurred
	 * @throws SystemException if a system exception occurred
	 */
	public JournalArticleDisplay getArticleDisplay(
			long groupId, String articleId, double version,
			String ddmTemplateKey, String viewMode, String languageId, int page,
			String xmlRequest, ThemeDisplay themeDisplay)
		throws PortalException, SystemException {

		Date now = new Date();

		JournalArticle article = journalArticlePersistence.findByG_A_V(
			groupId, articleId, version);

		if (article.isExpired()) {
			Date expirationDate = article.getExpirationDate();

			if ((expirationDate != null) && expirationDate.before(now)) {
				return null;
			}
		}

		if (article.getDisplayDate().after(now)) {
			return null;
		}

		return getArticleDisplay(
			article, ddmTemplateKey, viewMode, languageId, page, xmlRequest,
			themeDisplay);
	}

	/**
	 * Returns the web content display with the xml request set to
	 * <code>null</code>.
	 *
	 * @param  groupId the primary key of the group
	 * @param  articleId the primary key of the web content
	 * @param  version the web content's version
	 * @param  ddmTemplateKey the primary key of the web content's DDM template
	 * @param  viewMode the mode in which the web content is being viewed
	 * @param  languageId the primary key of the web content's default user's
	 *         language
	 * @param  themeDisplay the web content's current theme display
	 * @return the matching web content display
	 * @throws PortalException if a portal exception occurred
	 * @throws SystemException if a system exception occurred
	 */
	public JournalArticleDisplay getArticleDisplay(
			long groupId, String articleId, double version,
			String ddmTemplateKey, String viewMode, String languageId,
			ThemeDisplay themeDisplay)
		throws PortalException, SystemException {

		return getArticleDisplay(
			groupId, articleId, version, ddmTemplateKey, viewMode, languageId,
			1, null, themeDisplay);
	}

	/**
	 * Returns the web content article display with the version set to
	 * <code>null</code>.
	 *
	 * @param  groupId the primary key of the group
	 * @param  articleId the primary key of the web content
	 * @param  viewMode the mode in which the web content is being viewed
	 * @param  languageId the primary key of the web content's default user's
	 *         language
	 * @param  page the web content's page number
	 * @param  xmlRequest the request that serializes the web content into a
	 *         hierarchical hash map
	 * @param  themeDisplay the web content's current theme display
	 * @return the matching web content display
	 * @throws PortalException if a portal exception occurred
	 * @throws SystemException if a system exception occurred
	 */
	public JournalArticleDisplay getArticleDisplay(
			long groupId, String articleId, String viewMode, String languageId,
			int page, String xmlRequest, ThemeDisplay themeDisplay)
		throws PortalException, SystemException {

		return getArticleDisplay(
			groupId, articleId, null, viewMode, languageId, page, xmlRequest,
			themeDisplay);
	}

	/**
	 * Returns the web content display matching the group and article ID.
	 *
	 * @param  groupId the primary key of the group
	 * @param  articleId the primary key of the web content
	 * @param  ddmTemplateKey the primary key of the web content's DDM template
	 * @param  viewMode the mode in which the web content is being viewed
	 * @param  languageId the primary key of the web content's default user's
	 *         language
	 * @param  page the web content's page number
	 * @param  xmlRequest the request that serializes the web content into a
	 *         hierarchical hash map
	 * @param  themeDisplay the web content's current theme display
	 * @return the matching web content display
	 * @throws PortalException if a portal exception occurred
	 * @throws SystemException if a system exception occurred
	 */
	public JournalArticleDisplay getArticleDisplay(
			long groupId, String articleId, String ddmTemplateKey,
			String viewMode, String languageId, int page, String xmlRequest,
			ThemeDisplay themeDisplay)
		throws PortalException, SystemException {

		JournalArticle article = getDisplayArticle(groupId, articleId);

		return getArticleDisplay(
			groupId, articleId, article.getVersion(), ddmTemplateKey, viewMode,
			languageId, page, xmlRequest, themeDisplay);
	}

	/**
	 * Returns the web content display matching the group and article ID.
	 *
	 * @param  groupId the primary key of the group
	 * @param  articleId the primary key of the web content
	 * @param  ddmTemplateKey the primary key of the web content's DDM template
	 * @param  viewMode the mode in which the web content is being viewed
	 * @param  languageId the primary key of the web content's default user's
	 *         language
	 * @param  themeDisplay the web content's current theme display
	 * @return the matching web content display
	 * @throws PortalException if a portal exception occurred
	 * @throws SystemException if a system exception occurred
	 */
	public JournalArticleDisplay getArticleDisplay(
			long groupId, String articleId, String ddmTemplateKey,
			String viewMode, String languageId, ThemeDisplay themeDisplay)
		throws PortalException, SystemException {

		JournalArticle article = getDisplayArticle(groupId, articleId);

		return getArticleDisplay(
			groupId, articleId, article.getVersion(), ddmTemplateKey, viewMode,
			languageId, themeDisplay);
	}

	/**
	 * Returns the web content display with the DDM template key set to
	 * <code>null</code>.
	 *
	 * @param  groupId the primary key of the group
	 * @param  articleId the primary key of the web content
	 * @param  viewMode the mode in which the web content is being viewed
	 * @param  languageId the primary key of the web content's default user's
	 *         language
	 * @param  themeDisplay the web content's current theme display
	 * @return the matching web content display
	 * @throws PortalException if a portal exception occurred
	 * @throws SystemException if a system exception occurred
	 */
	public JournalArticleDisplay getArticleDisplay(
			long groupId, String articleId, String viewMode, String languageId,
			ThemeDisplay themeDisplay)
		throws PortalException, SystemException {

		return getArticleDisplay(
			groupId, articleId, null, viewMode, languageId, themeDisplay);
	}

	/**
	 * Returns all the web content present in the system.
	 *
	 * @return the web content present in the system
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalArticle> getArticles() throws SystemException {
		return journalArticlePersistence.findAll();
	}

	/**
	 * Returns all the web content present in the group.
	 *
	 * @param  groupId the primary key of the group
	 * @return the structures present in the group
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalArticle> getArticles(long groupId)
		throws SystemException {

		return journalArticlePersistence.findByGroupId(groupId);
	}

	/**
	 * Returns a range of all the web content belonging to the group.
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
	 * @param  groupId the primary key of the group
	 * @param  start the lower bound of the range of web content to return
	 * @param  end the upper bound of the range of web content to return (not
	 *         inclusive)
	 * @return the range of matching web content
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalArticle> getArticles(long groupId, int start, int end)
		throws SystemException {

		return journalArticlePersistence.findByGroupId(groupId, start, end);
	}

	/**
	 * Returns an ordered range of all the web content belonging to the group.
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
	 * @param  groupId the primary key of the group
	 * @param  start the lower bound of the range of web content to return
	 * @param  end the upper bound of the range of web content to return (not
	 *         inclusive)
	 * @param  obc the comparator to order the web content
	 * @return the range of matching web content ordered by the comparator
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalArticle> getArticles(
			long groupId, int start, int end, OrderByComparator obc)
		throws SystemException {

		return journalArticlePersistence.findByGroupId(
			groupId, start, end, obc);
	}

	/**
	 * Returns all the web content matching the group and folder.
	 *
	 * @param  groupId the primary key of the group
	 * @param  folderId the primary key of the web content folder
	 * @return the matching web content
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalArticle> getArticles(long groupId, long folderId)
		throws SystemException {

		return journalArticlePersistence.findByG_F(groupId, folderId);
	}

	/**
	 * Returns a range of all the web content matching the group and folder.
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
	 * @param  groupId the primary key of the group
	 * @param  folderId the primary key of the web content folder
	 * @param  start the lower bound of the range of web content to return
	 * @param  end the upper bound of the range of web content to return (not
	 *         inclusive)
	 * @return the range of matching web content
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalArticle> getArticles(
			long groupId, long folderId, int start, int end)
		throws SystemException {

		return journalArticlePersistence.findByG_F(
			groupId, folderId, start, end);
	}

	/**
	 * Returns an ordered range of all the web content matching the group and
	 * folder.
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
	 * @param  groupId the primary key of the group
	 * @param  folderId the primary key of the web content folder
	 * @param  start the lower bound of the range of web content to return
	 * @param  end the upper bound of the range of web content to return (not
	 *         inclusive)
	 * @param  orderByComparator the comparator to order the web content
	 * @return the range of matching web content ordered by the comparator
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalArticle> getArticles(
			long groupId, long folderId, int start, int end,
			OrderByComparator orderByComparator)
		throws SystemException {

		return journalArticlePersistence.findByG_F(
			groupId, folderId, start, end, orderByComparator);
	}

	/**
	 * Returns all the web content matching the group and article ID.
	 *
	 * @param  groupId the primary key of the group
	 * @param  articleId the primary key of the web content
	 * @return the web content belonging to the group
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalArticle> getArticles(long groupId, String articleId)
		throws SystemException {

		return journalArticlePersistence.findByG_A(groupId, articleId);
	}

	/**
	 * Returns all the web content matching the small image ID.
	 *
	 * @param  smallImageId the primary key of the web content's small image
	 * @return the web content matching the small image ID
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalArticle> getArticlesBySmallImageId(long smallImageId)
		throws SystemException {

		return journalArticlePersistence.findBySmallImageId(smallImageId);
	}

	/**
	 * Returns the number of web content belonging to the group.
	 *
	 * @param  groupId the primary key of the group
	 * @return the number of web content belonging to the group
	 * @throws SystemException if a system exception occurred
	 */
	public int getArticlesCount(long groupId) throws SystemException {
		return journalArticlePersistence.countByGroupId(groupId);
	}

	/**
	 * Returns the number of web content matching the group and folder.
	 *
	 * @param  groupId the primary key of the group
	 * @param  folderId the primary key of the web content folder
	 * @return the number of matching web content
	 * @throws SystemException if a system exception occurred
	 */
	public int getArticlesCount(long groupId, long folderId)
		throws SystemException {

		return journalArticlePersistence.countByG_F(groupId, folderId);
	}

	/**
	 * Returns an ordered range of all the web content matching the company,
	 * version, and status.
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
	 * @param  companyId the primary key of the web content's company
	 * @param  version the web content's version
	 * @param  status the web content's status
	 * @param  start the lower bound of the range of web content to return
	 * @param  end the upper bound of the range of web content to return (not
	 *         inclusive)
	 * @return the range of matching web content ordered by article ID
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalArticle> getCompanyArticles(
			long companyId, double version, int status, int start, int end)
		throws SystemException {

		if (status == WorkflowConstants.STATUS_ANY) {
			return journalArticlePersistence.findByC_V(
				companyId, version, start, end, new ArticleIDComparator(true));
		}
		else {
			return journalArticlePersistence.findByC_V_ST(
				companyId, version, status, start, end,
				new ArticleIDComparator(true));
		}
	}

	/**
	 * Returns an ordered range of all the web content matching the company and
	 * status.
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
	 * @param  companyId the primary key of the web content's company
	 * @param  status the web content's status
	 * @param  start the lower bound of the range of web content to return
	 * @param  end the upper bound of the range of web content to return (not
	 *         inclusive)
	 * @return the range of matching web content ordered by article ID
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalArticle> getCompanyArticles(
			long companyId, int status, int start, int end)
		throws SystemException {

		if (status == WorkflowConstants.STATUS_ANY) {
			return journalArticlePersistence.findByCompanyId(
				companyId, start, end, new ArticleIDComparator(true));
		}
		else {
			return journalArticlePersistence.findByC_ST(
				companyId, status, start, end, new ArticleIDComparator(true));
		}
	}

	/**
	 * Returns the number of web content matching the company, version, and
	 * status.
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
	 * @param  companyId the primary key of the web content's company
	 * @param  version the web content's version
	 * @param  status the web content's status
	 * @param  start the lower bound of the range of web content to return
	 * @param  end the upper bound of the range of web content to return (not
	 *         inclusive)
	 * @return the number of matching web content
	 * @throws SystemException if a system exception occurred
	 */
	public int getCompanyArticlesCount(
			long companyId, double version, int status, int start, int end)
		throws SystemException {

		if (status == WorkflowConstants.STATUS_ANY) {
			return journalArticlePersistence.countByC_V(companyId, version);
		}
		else {
			return journalArticlePersistence.countByC_V_ST(
				companyId, version, status);
		}
	}

	/**
	 * Returns the number of web content matching the company and status.
	 *
	 * @param  companyId the primary key of the web content's company
	 * @param  status the web content's status
	 * @return the number of matching web content
	 * @throws SystemException if a system exception occurred
	 */
	public int getCompanyArticlesCount(long companyId, int status)
		throws SystemException {

		if (status == WorkflowConstants.STATUS_ANY) {
			return journalArticlePersistence.countByCompanyId(companyId);
		}
		else {
			return journalArticlePersistence.countByC_ST(companyId, status);
		}
	}

	/**
	 * Returns the web content matching the group, article ID, and approved
	 * workflow status. This method checks the expiration date of the web
	 * content and if expired, the web content is not returned.
	 *
	 * @param  groupId the primary key of the group
	 * @param  articleId the primary key of the web content
	 * @return the matching web content
	 * @throws PortalException if a portal exception occurred
	 * @throws SystemException if a system exception occurred
	 */
	public JournalArticle getDisplayArticle(long groupId, String articleId)
		throws PortalException, SystemException {

		List<JournalArticle> articles = journalArticlePersistence.findByG_A_ST(
			groupId, articleId, WorkflowConstants.STATUS_APPROVED);

		if (articles.isEmpty()) {
			throw new NoSuchArticleException(
				"No approved JournalArticle exists with the key {groupId=" +
					groupId + ", " + "articleId=" + articleId + "}");

		}

		Date now = new Date();

		for (int i = 0; i < articles.size(); i++) {
			JournalArticle article = articles.get(i);

			Date expirationDate = article.getExpirationDate();

			if (article.getDisplayDate().before(now) &&
				((expirationDate == null) || expirationDate.after(now))) {

				return article;
			}
		}

		return articles.get(0);
	}

	/**
	 * Returns an ordered range of all the web content matching the group, URL
	 * title, and approved workflow status. This method checks the expiration
	 * date of the web content and if expired, the web content is not returned.
	 *
	 * @param  groupId the primary key of the group
	 * @param  urlTitle the web content's accessible URL title
	 * @return the range of matching web content ordered by article ID
	 * @throws PortalException if a portal exception occurred
	 * @throws SystemException if a system exception occurred
	 */
	public JournalArticle getDisplayArticleByUrlTitle(
			long groupId, String urlTitle)
		throws PortalException, SystemException {

		List<JournalArticle> articles = null;

		OrderByComparator orderByComparator = new ArticleVersionComparator();

		articles = journalArticlePersistence.findByG_UT_ST(
			groupId, urlTitle, WorkflowConstants.STATUS_APPROVED,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, orderByComparator);

		if (articles.isEmpty()) {
			throw new NoSuchArticleException(
				"No JournalArticle exists with the key {groupId=" + groupId +
					", urlTitle=" + urlTitle + "}");
		}

		Date now = new Date();

		for (JournalArticle article : articles) {
			Date expirationDate = article.getExpirationDate();

			if (article.getDisplayDate().before(now) &&
				((expirationDate == null) || expirationDate.after(now))) {

				return article;
			}
		}

		return articles.get(0);
	}

	/**
	 * Returns the latest web content with a workflow status.
	 *
	 * @param  resourcePrimKey the primary key of the model instance
	 * @return the latest web content
	 * @throws PortalException if a portal exception occurred
	 * @throws SystemException if a system exception occurred
	 */
	public JournalArticle getLatestArticle(long resourcePrimKey)
		throws PortalException, SystemException {

		return getLatestArticle(resourcePrimKey, WorkflowConstants.STATUS_ANY);
	}

	/**
	 * Returns the latest web content with a status.
	 *
	 * @param  resourcePrimKey the primary key of the model instance
	 * @param  status the web content's status
	 * @return the latest web content with a status
	 * @throws PortalException if a portal exception occurred
	 * @throws SystemException if a system exception occurred
	 */
	public JournalArticle getLatestArticle(long resourcePrimKey, int status)
		throws PortalException, SystemException {

		return getLatestArticle(resourcePrimKey, status, true);
	}

	/**
	 * Returns an ordered range of all the latest web content matching the
	 * resource primary key and status.
	 *
	 * @param  resourcePrimKey the primary key of the model instance
	 * @param  status the web content's status
	 * @param  preferApproved whether the workflow status is set to
	 *         <code>Approved</code>
	 * @return the range of matching web content ordered by article ID
	 * @throws PortalException if a portal exception occurred
	 * @throws SystemException if a system exception occurred
	 */
	public JournalArticle getLatestArticle(
			long resourcePrimKey, int status, boolean preferApproved)
		throws PortalException, SystemException {

		List<JournalArticle> articles = null;

		OrderByComparator orderByComparator = new ArticleVersionComparator();

		if (status == WorkflowConstants.STATUS_ANY) {
			if (preferApproved) {
				articles = journalArticlePersistence.findByR_ST(
					resourcePrimKey, WorkflowConstants.STATUS_APPROVED, 0, 1,
					orderByComparator);
			}

			if ((articles == null) || (articles.size() == 0)) {
				articles = journalArticlePersistence.findByResourcePrimKey(
					resourcePrimKey, 0, 1, orderByComparator);
			}
		}
		else {
			articles = journalArticlePersistence.findByR_ST(
				resourcePrimKey, status, 0, 1, orderByComparator);
		}

		if (articles.isEmpty()) {
			throw new NoSuchArticleException(
				"No JournalArticle exists with the key {resourcePrimKey=" +
					resourcePrimKey + "}");
		}

		return articles.get(0);
	}

	/**
	 * Returns the latest web content with the group and article ID.
	 *
	 * @param  groupId the primary key of the group
	 * @param  articleId the primary key of the web content
	 * @return the latest web content
	 * @throws PortalException if a portal exception occurred
	 * @throws SystemException if a system exception occurred
	 */
	public JournalArticle getLatestArticle(long groupId, String articleId)
		throws PortalException, SystemException {

		return getLatestArticle(
			groupId, articleId, WorkflowConstants.STATUS_ANY);
	}

	/**
	 * Returns an ordered range of all the latest web content matching the
	 * group, article ID, and status.
	 *
	 * @param  groupId the primary key of the group
	 * @param  articleId the primary key of the web content
	 * @param  status the web content's status
	 * @return the range of matching web content ordered by article ID
	 * @throws PortalException if a portal exception occurred
	 * @throws SystemException if a system exception occurred
	 */
	public JournalArticle getLatestArticle(
			long groupId, String articleId, int status)
		throws PortalException, SystemException {

		List<JournalArticle> articles = null;

		OrderByComparator orderByComparator = new ArticleVersionComparator();

		if (status == WorkflowConstants.STATUS_ANY) {
			articles = journalArticlePersistence.findByG_A(
				groupId, articleId, 0, 1, orderByComparator);
		}
		else {
			articles = journalArticlePersistence.findByG_A_ST(
				groupId, articleId, status, 0, 1, orderByComparator);
		}

		if (articles.isEmpty()) {
			throw new NoSuchArticleException(
				"No JournalArticle exists with the key {groupId=" + groupId +
					", articleId=" + articleId + ", status=" + status + "}");
		}

		return articles.get(0);
	}

	/**
	 * Returns an ordered range of all the latest web content matching the
	 * group, class name ID, and class PK.
	 *
	 * @param  groupId the primary key of the group
	 * @param  className the primary key of the class name for the web content's
	 *         related model
	 * @param  classPK the primary key of the web content's related entity
	 * @return the range of matching web content ordered by article ID
	 * @throws PortalException if a portal exception occurred
	 * @throws SystemException if a system exception occurred
	 */
	public JournalArticle getLatestArticle(
			long groupId, String className, long classPK)
		throws PortalException, SystemException {

		long classNameId = PortalUtil.getClassNameId(className);

		List<JournalArticle> articles = journalArticlePersistence.findByG_C_C(
			groupId, classNameId, classPK, 0, 1,
			new ArticleVersionComparator());

		if (articles.isEmpty()) {
			throw new NoSuchArticleException(
				"No JournalArticle exists with the key {groupId=" + groupId +
					", className=" + className + ", classPK =" + classPK + "}");
		}

		return articles.get(0);
	}

	/**
	 * Returns an ordered range of all the latest web content matching the
	 * group, URL title, and status.
	 *
	 * @param  groupId the primary key of the group
	 * @param  urlTitle the web content's accessible URL title
	 * @param  status the web content's status
	 * @return the range of matching web content ordered by article ID
	 * @throws PortalException if a portal exception occurred
	 * @throws SystemException if a system exception occurred
	 */
	public JournalArticle getLatestArticleByUrlTitle(
			long groupId, String urlTitle, int status)
		throws PortalException, SystemException {

		List<JournalArticle> articles = null;

		OrderByComparator orderByComparator = new ArticleVersionComparator();

		if (status == WorkflowConstants.STATUS_ANY) {
			articles = journalArticlePersistence.findByG_UT(
				groupId, urlTitle, 0, 1, orderByComparator);
		}
		else {
			articles = journalArticlePersistence.findByG_UT_ST(
				groupId, urlTitle, status, 0, 1, orderByComparator);
		}

		if (articles.isEmpty()) {
			throw new NoSuchArticleException(
				"No JournalArticle exists with the key {groupId=" + groupId +
					", urlTitle=" + urlTitle + ", status=" + status + "}");
		}

		return articles.get(0);
	}

	/**
	 * Returns the latest version of the web content with the group and article
	 * ID.
	 *
	 * @param  groupId the primary key of the group
	 * @param  articleId the primary key of the web content
	 * @return the latest web content version
	 * @throws PortalException if a portal exception occurred
	 * @throws SystemException if a system exception occurred
	 */
	public double getLatestVersion(long groupId, String articleId)
		throws PortalException, SystemException {

		JournalArticle article = getLatestArticle(groupId, articleId);

		return article.getVersion();
	}

	/**
	 * Returns the latest version of the web content with the group, article ID,
	 * and status.
	 *
	 * @param  groupId the primary key of the group
	 * @param  articleId the primary key of the web content
	 * @param  status the web content's status
	 * @return the latest web content version
	 * @throws PortalException if a portal exception occurred
	 * @throws SystemException if a system exception occurred
	 */
	public double getLatestVersion(long groupId, String articleId, int status)
		throws PortalException, SystemException {

		JournalArticle article = getLatestArticle(groupId, articleId, status);

		return article.getVersion();
	}

	/**
	 * Returns the number of web content that are not recycled.
	 *
	 * @param  groupId the primary key of the group
	 * @param  folderId the primary key of the web content folder
	 * @return the number of matching web content
	 * @throws SystemException if a system exception occurred
	 */
	public int getNotInTrashArticlesCount(long groupId, long folderId)
		throws SystemException {

		QueryDefinition queryDefinition = new QueryDefinition(
			WorkflowConstants.STATUS_ANY);

		List<Long> folderIds = new ArrayList<Long>();

		folderIds.add(folderId);

		return journalArticleFinder.countByG_F(
			groupId, folderIds, queryDefinition);
	}

	/**
	 * Returns the web content matching the group and DDM structure key.
	 *
	 * @param  groupId the primary key of the group
	 * @param  ddmStructureKey the primary key of the web content's DDM
	 *         structure
	 * @return the matching web content
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalArticle> getStructureArticles(
			long groupId, String ddmStructureKey)
		throws SystemException {

		return journalArticlePersistence.findByG_S(groupId, ddmStructureKey);
	}

	/**
	 * Returns an ordered range of all the web content matching the group and
	 * DDM structure key.
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
	 * @param  groupId the primary key of the group
	 * @param  ddmStructureKey the primary key of the web content's DDM
	 *         structure
	 * @param  start the lower bound of the range of web content to return
	 * @param  end the upper bound of the range of web content to return (not
	 *         inclusive)
	 * @param  obc the comparator to order the web content
	 * @return the range of matching web content ordered by the comparator
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalArticle> getStructureArticles(
			long groupId, String ddmStructureKey, int start, int end,
			OrderByComparator obc)
		throws SystemException {

		return journalArticlePersistence.findByG_S(
			groupId, ddmStructureKey, start, end, obc);
	}

	public List<JournalArticle> getStructureArticles(String[] ddmStructureKeys)
		throws SystemException {

		return journalArticlePersistence.findByStructureId(ddmStructureKeys);
	}

	/**
	 * Returns the number of web content matching the group and DDM structure
	 * key.
	 *
	 * @param  groupId the primary key of the group
	 * @param  ddmStructureKey the primary key of the web content's DDM
	 *         structure
	 * @return the number of matching web content
	 * @throws SystemException if a system exception occurred
	 */
	public int getStructureArticlesCount(long groupId, String ddmStructureKey)
		throws SystemException {

		return journalArticlePersistence.countByG_S(groupId, ddmStructureKey);
	}

	/**
	 * Returns the web content matching the group and DDM template key.
	 *
	 * @param  groupId the primary key of the group
	 * @param  ddmTemplateKey the primary key of the web content's DDM template
	 * @return the matching web content
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalArticle> getTemplateArticles(
			long groupId, String ddmTemplateKey)
		throws SystemException {

		return journalArticlePersistence.findByG_T(groupId, ddmTemplateKey);
	}

	/**
	 * Returns an ordered range of all the web content matching the group and
	 * DDM template key.
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
	 * @param  groupId the primary key of the group
	 * @param  ddmTemplateKey the primary key of the web content's DDM template
	 * @param  start the lower bound of the range of web content to return
	 * @param  end the upper bound of the range of web content to return (not
	 *         inclusive)
	 * @param  obc the comparator to order the web content
	 * @return the range of matching web content ordered by the comparator
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalArticle> getTemplateArticles(
			long groupId, String ddmTemplateKey, int start, int end,
			OrderByComparator obc)
		throws SystemException {

		return journalArticlePersistence.findByG_T(
			groupId, ddmTemplateKey, start, end, obc);
	}

	/**
	 * Returns the number of web content matching the group and DDM template
	 * key.
	 *
	 * @param  groupId the primary key of the group
	 * @param  ddmTemplateKey the primary key of the web content's DDM template
	 * @return the number of matching web content
	 * @throws SystemException if a system exception occurred
	 */
	public int getTemplateArticlesCount(long groupId, String ddmTemplateKey)
		throws SystemException {

		return journalArticlePersistence.countByG_T(groupId, ddmTemplateKey);
	}

	/**
	 * Returns <code>true</code> if the specified web content exists.
	 *
	 * @param  groupId the primary key of the group
	 * @param  articleId the primary key of the web content
	 * @return <code>true</code> if the specified web content
	 *         exists;<code>false</code> otherwise
	 * @throws SystemException if a system exception occurred
	 */
	public boolean hasArticle(long groupId, String articleId)
		throws SystemException {

		try {
			getArticle(groupId, articleId);

			return true;
		}
		catch (PortalException pe) {
			return false;
		}
	}

	/**
	 * Returns <code>true</code> if the web content, specified by group and
	 * article ID, is the latest version.
	 *
	 * @param  groupId the primary key of the group
	 * @param  articleId the primary key of the web content
	 * @param  version the web content's version
	 * @return <code>true</code> if the specified web content is the latest
	 *         version;<code>false</code> otherwise
	 * @throws PortalException if a portal exception occurred
	 * @throws SystemException if a system exception occurred
	 */
	public boolean isLatestVersion(
			long groupId, String articleId, double version)
		throws PortalException, SystemException {

		if (getLatestVersion(groupId, articleId) == version) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Returns <code>true</code> if the web content, specified by group, article
	 * ID, and status, is the latest version.
	 *
	 * @param  groupId the primary key of the group
	 * @param  articleId the primary key of the web content
	 * @param  version the web content's version
	 * @param  status the web content's status
	 * @return <code>true</code> if the specified web content is the latest
	 *         version;<code>false</code> otherwise
	 * @throws PortalException if a portal exception occurred
	 * @throws SystemException if a system exception occurred
	 */
	public boolean isLatestVersion(
			long groupId, String articleId, double version, int status)
		throws PortalException, SystemException {

		if (getLatestVersion(groupId, articleId, status) == version) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Moves the web content matching the group and article ID to a new folder.
	 *
	 * @param  groupId the primary key of the group
	 * @param  articleId the primary key of the web content
	 * @param  newFolderId the primary key of the web content's new folder
	 * @return the updated web content, which was moved to a new folder
	 * @throws PortalException
	 * @throws SystemException if a system exception occurred
	 */
	@Indexable(type = IndexableType.REINDEX)
	public JournalArticle moveArticle(
			long groupId, String articleId, long newFolderId)
		throws PortalException, SystemException {

		List<JournalArticle> articles = journalArticlePersistence.findByG_A(
			groupId, articleId);

		for (JournalArticle article : articles) {
			article.setFolderId(newFolderId);

			journalArticlePersistence.update(article);
		}

		return getArticle(groupId, articleId);
	}

	/**
	 * Moves the web content from the recycle bin to a new folder.
	 *
	 * @param  userId the primary key of the web content's creator/owner
	 * @param  groupId the primary key of the group
	 * @param  article the web content
	 * @param  newFolderId the primary key of the web content's new folder
	 * @param  serviceContext the service context to be applied. Can set the
	 *         modification date, portlet preferences and can set whether to add
	 *         the default command update for the web content.
	 * @return the updated web content, which was moved from the recycle bin to
	 *         a new folder
	 * @throws PortalException if a portal exception occurred
	 * @throws SystemException if a system exception occurred
	 */
	public JournalArticle moveArticleFromTrash(
			long userId, long groupId, JournalArticle article, long newFolderId,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		if (article.isInTrash()) {
			restoreArticleFromTrash(userId, article);
		}
		else {
			updateStatus(
				userId, article, article.getStatus(), null,
				new HashMap<String, Serializable>(), serviceContext);
		}

		return journalArticleLocalService.moveArticle(
			groupId, article.getArticleId(), newFolderId);
	}


	/**
	 * Moves the web content matching the group and article ID to the recycle
	 * bin.
	 *
	 * @param  userId the primary key of the web content's creator/owner
	 * @param  article the web content
	 * @return the updated article, which was moved to the recycle bin
	 * @throws PortalException if a portal exception occurred
	 * @throws SystemException if a system exception occurred
	 */
	public JournalArticle moveArticleToTrash(
			long userId, JournalArticle article)
		throws PortalException, SystemException {

		List<JournalArticle> articleVersions =
			journalArticlePersistence.findByG_A(
				article.getGroupId(), article.getArticleId());

		articleVersions = ListUtil.sort(
			articleVersions, new ArticleVersionComparator());

		Map<String, Serializable> workflowContext =
			new HashMap<String, Serializable>();

		workflowContext.put("articleVersions", (Serializable)articleVersions);

		article = updateStatus(
			userId, article.getId(), WorkflowConstants.STATUS_IN_TRASH,
			workflowContext, new ServiceContext());

		// Remove the existing index because we are changing article ID

		Indexer indexer = IndexerRegistryUtil.nullSafeGetIndexer(
			JournalArticle.class);

		indexer.delete(article);

		TrashEntry trashEntry = trashEntryLocalService.getEntry(
			JournalArticle.class.getName(), article.getResourcePrimKey());

		String trashArticleId = TrashUtil.getTrashTitle(
			trashEntry.getEntryId());

		if (!articleVersions.isEmpty()) {
			for (JournalArticle curArticleVersion : articleVersions) {
				curArticleVersion.setArticleId(trashArticleId);

				journalArticlePersistence.update(curArticleVersion);
			}
		}

		JournalArticleResource articleResource =
			journalArticleResourcePersistence.fetchByPrimaryKey(
				article.getResourcePrimKey());

		articleResource.setArticleId(trashArticleId);

		journalArticleResourcePersistence.update(articleResource);

		article.setArticleId(trashArticleId);

		reindex(article);

		article = journalArticlePersistence.update(article);

		socialActivityCounterLocalService.disableActivityCounters(
			JournalFolder.class.getName(), article.getFolderId());

		socialActivityLocalService.addActivity(
			userId, article.getGroupId(), JournalArticle.class.getName(),
			article.getResourcePrimKey(),
			SocialActivityConstants.TYPE_MOVE_TO_TRASH, StringPool.BLANK, 0);

		return article;
	}

	/**
	 * Moves the web content matching the group and article ID to the recycle
	 * bin.
	 *
	 * @param  userId the primary key of the web content's creator/owner
	 * @param  groupId the primary key of the group
	 * @param  articleId the primary key of the web content
	 * @return the updated article, which was moved to the recycle bin
	 * @throws PortalException if a portal exception occurred
	 * @throws SystemException if a system exception occurred
	 */
	public JournalArticle moveArticleToTrash(
			long userId, long groupId, String articleId)
		throws PortalException, SystemException {

		List<JournalArticle> articles = journalArticlePersistence.findByG_A(
			groupId, articleId, 0, 1, new ArticleVersionComparator());

		if (!articles.isEmpty()) {
			return moveArticleToTrash(userId, articles.get(0));
		}

		return null;
	}

	/**
	 * Removes the web content's locale matching the group, article ID, and
	 * version.
	 *
	 * @param  groupId the primary key of the group
	 * @param  articleId the primary key of the web content
	 * @param  version the web content's version
	 * @param  languageId the primary key of the web content's default user's
	 *         language
	 * @return the updated web content with a removed locale
	 * @throws PortalException if a portal exception occurred
	 * @throws SystemException if a system exception occurred
	 */
	public JournalArticle removeArticleLocale(
			long groupId, String articleId, double version, String languageId)
		throws PortalException, SystemException {

		JournalArticle article = journalArticlePersistence.findByG_A_V(
			groupId, articleId, version);

		String title = article.getTitle();

		title = LocalizationUtil.removeLocalization(
			title, "static-content", languageId, true);

		article.setTitle(title);

		String description = article.getDescription();

		description = LocalizationUtil.removeLocalization(
			description, "static-content", languageId, true);

		article.setDescription(description);

		String content = article.getContent();

		if (article.isTemplateDriven()) {
			content = JournalUtil.removeArticleLocale(content, languageId);
		}
		else {
			content = LocalizationUtil.removeLocalization(
				content, "static-content", languageId, true);
		}

		article.setContent(content);

		journalArticlePersistence.update(article);

		return article;
	}

	/**
	 * Restores the web content from the Recycle Bin.
	 *
	 * @param  userId the primary key of the web content's creator/owner
	 * @param  article the web content
	 * @throws PortalException if a portal exception occurred
	 * @throws SystemException if a system exception occurred
	 */
	public void restoreArticleFromTrash(long userId, JournalArticle article)
		throws PortalException, SystemException {

		// Remove the existing index because we are changing article ID

		Indexer indexer = IndexerRegistryUtil.nullSafeGetIndexer(
			JournalArticle.class);

		indexer.delete(article);

		String trashArticleId = TrashUtil.getOriginalTitle(
			article.getArticleId());

		List<JournalArticle> articleVersions =
			journalArticlePersistence.findByG_A(
				article.getGroupId(), article.getArticleId());

		if (!articleVersions.isEmpty()) {
			for (JournalArticle curArticleVersion : articleVersions) {
				curArticleVersion.setArticleId(trashArticleId);

				journalArticlePersistence.update(curArticleVersion);
			}
		}

		article.setArticleId(trashArticleId);

		journalArticlePersistence.update(article);

		JournalArticleResource articleResource =
			journalArticleResourcePersistence.fetchByPrimaryKey(
				article.getResourcePrimKey());

		articleResource.setArticleId(trashArticleId);

		journalArticleResourcePersistence.update(articleResource);

		TrashEntry trashEntry = trashEntryLocalService.getEntry(
			JournalArticle.class.getName(), article.getResourcePrimKey());

		Map<String, Serializable> workflowContext =
			new HashMap<String, Serializable>();

		List<TrashVersion> trashVersions = trashEntryLocalService.getVersions(
			trashEntry.getEntryId());

		workflowContext.put("trashVersions", (Serializable)trashVersions);

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setScopeGroupId(article.getGroupId());

		updateStatus(
			userId, article, trashEntry.getStatus(), null, workflowContext,
			serviceContext);

		socialActivityCounterLocalService.enableActivityCounters(
			JournalFolder.class.getName(), article.getFolderId());

		socialActivityLocalService.addActivity(
			userId, article.getGroupId(), JournalArticle.class.getName(),
			article.getResourcePrimKey(),
			SocialActivityConstants.TYPE_RESTORE_FROM_TRASH, StringPool.BLANK,
			0);
	}

	/**
	 * Returns an ordered range of all the web content matching the group,
	 * folders, class name ID, version, type, DDM structure key, DDM template
	 * key, display date, status, and review date, and matching the keywords in
	 * the web content's article ID, title, description, and content.
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
	 * @param  companyId the primary key of the web content's company
	 * @param  groupId the primary key of the group
	 * @param  folderIds the primary keys of the web content folders
	 * @param  classNameId the primary key of the entity's instance the web
	 *         content is related to
	 * @param  keywords the keywords (space separated), which may occur in the
	 *         web content's article ID, title, description, or content
	 *         (optionally <code>null</code>)
	 * @param  version the web content's version
	 * @param  type the web content's type
	 * @param  ddmStructureKey the primary key of the web content's DDM
	 *         structure
	 * @param  ddmTemplateKey the primary key of the web content's DDM template
	 * @param  displayDateGT the latest date in the web content's display date
	 *         search
	 * @param  displayDateLT the earliest date in the web content's display date
	 *         search
	 * @param  status the web content's status
	 * @param  reviewDate the web content's scheduled review date
	 * @param  start the lower bound of the range of web content to return
	 * @param  end the upper bound of the range of web content to return (not
	 *         inclusive)
	 * @param  obc the comparator to order the web content
	 * @return the range of matching web content ordered by the comparator
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalArticle> search(
			long companyId, long groupId, List<Long> folderIds,
			long classNameId, String keywords, Double version, String type,
			String ddmStructureKey, String ddmTemplateKey, Date displayDateGT,
			Date displayDateLT, int status, Date reviewDate, int start, int end,
			OrderByComparator obc)
		throws SystemException {

		return journalArticleFinder.findByKeywords(
			companyId, groupId, folderIds, classNameId, keywords, version, type,
			ddmStructureKey, ddmTemplateKey, displayDateGT, displayDateLT,
			status, reviewDate, start, end, obc);
	}

	/**
	 * Returns an ordered range of all the web content matching the group,
	 * folders, class name ID, article ID, version, title keyword, description
	 * keyword, content keyword, type, DDM structure key, DDM template key,
	 * display date, status, and review date.
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
	 * @param  companyId the primary key of the web content's company
	 * @param  groupId the primary key of the group
	 * @param  folderIds the primary keys of the web content folders
	 * @param  classNameId the primary key of the entity's instance the web
	 *         content is related to
	 * @param  articleId the primary key of the web content
	 * @param  version the web content's version
	 * @param  title the title keywords
	 * @param  description the description keywords
	 * @param  content the content keywords
	 * @param  type the web content's type
	 * @param  ddmStructureKey the primary key of the web content's DDM
	 *         structure
	 * @param  ddmTemplateKey the primary key of the web content's DDM template
	 * @param  displayDateGT the latest date in the web content's display date
	 *         search
	 * @param  displayDateLT the earliest date in the web content's display date
	 *         search
	 * @param  status the web content's status
	 * @param  reviewDate the web content's scheduled review date
	 * @param  andOperator whether every field must match its keywords, or just
	 *         one field
	 * @param  start the lower bound of the range of web content to return
	 * @param  end the upper bound of the range of web content to return (not
	 *         inclusive)
	 * @param  obc the comparator to order the web content
	 * @return the range of matching web content ordered by the comparator
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalArticle> search(
			long companyId, long groupId, List<Long> folderIds,
			long classNameId, String articleId, Double version, String title,
			String description, String content, String type,
			String ddmStructureKey, String ddmTemplateKey, Date displayDateGT,
			Date displayDateLT, int status, Date reviewDate,
			boolean andOperator, int start, int end, OrderByComparator obc)
		throws SystemException {

		QueryDefinition queryDefinition = new QueryDefinition(
			status, start, end, obc);

		return journalArticleFinder.findByC_G_F_C_A_V_T_D_C_T_S_T_D_R(
			companyId, groupId, folderIds, classNameId, articleId, version,
			title, description, content, type, ddmStructureKey, ddmTemplateKey,
			displayDateGT, displayDateLT, reviewDate, andOperator,
			queryDefinition);
	}

	/**
	 * Returns an ordered range of all the web content matching the group,
	 * folders, class name ID, article ID, version, title keyword, description
	 * keyword, content keyword, type, DDM structure keys, DDM template keys,
	 * display date, status, and review date.
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
	 * @param  companyId the primary key of the web content's company
	 * @param  groupId the primary key of the group
	 * @param  folderIds the primary keys of the web content folders
	 * @param  classNameId the primary key of the entity's instance the web
	 *         content is related to
	 * @param  articleId the primary key of the web content
	 * @param  version the web content's version
	 * @param  title the title keywords
	 * @param  description the description keywords
	 * @param  content the content keywords
	 * @param  type the web content's type
	 * @param  ddmStructureKeys the primary keys of the web content's DDM
	 *         structures
	 * @param  ddmTemplateKeys the primary keys of the web content's DDM
	 *         templates
	 * @param  displayDateGT the latest date in the web content's display date
	 *         search
	 * @param  displayDateLT the earliest date in the web content's display date
	 *         search
	 * @param  status the web content's status
	 * @param  reviewDate the web content's scheduled review date
	 * @param  andOperator whether every field must match its keywords, or just
	 *         one field
	 * @param  start the lower bound of the range of web content to return
	 * @param  end the upper bound of the range of web content to return (not
	 *         inclusive)
	 * @param  obc the comparator to order the web content
	 * @return the range of matching web content ordered by the comparator
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalArticle> search(
			long companyId, long groupId, List<Long> folderIds,
			long classNameId, String articleId, Double version, String title,
			String description, String content, String type,
			String[] ddmStructureKeys, String[] ddmTemplateKeys,
			Date displayDateGT, Date displayDateLT, int status, Date reviewDate,
			boolean andOperator, int start, int end, OrderByComparator obc)
		throws SystemException {

		QueryDefinition queryDefinition = new QueryDefinition(
			status, start, end, obc);

		return journalArticleFinder.findByC_G_F_C_A_V_T_D_C_T_S_T_D_R(
			companyId, groupId, folderIds, classNameId, articleId, version,
			title, description, content, type, ddmStructureKeys,
			ddmTemplateKeys, displayDateGT, displayDateLT, reviewDate,
			andOperator, queryDefinition);
	}

	/**
	 * Returns an ordered range of all the web content matching the group,
	 * folders, class name ID, DDM structure key, and DDM template key, and
	 * matching the keywords in the web content's article ID, title,
	 * description, and content.
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
	 * @param  companyId the primary key of the web content's company
	 * @param  groupId the primary key of the group
	 * @param  folderIds the primary keys of the web content folders
	 * @param  classNameId the primary key of the entity's instance the web
	 *         content is related to
	 * @param  ddmStructureKey the primary key of the web content's DDM
	 *         structure
	 * @param  ddmTemplateKey the primary key of the web content's DDM template
	 * @param  keywords the keywords (space separated), which may occur in the
	 *         web content's article ID, title, description, or content
	 *         (optionally <code>null</code>)
	 * @param  params the finder parameters (optionally <code>null</code>)
	 * @param  start the lower bound of the range of web content to return
	 * @param  end the upper bound of the range of web content to return (not
	 *         inclusive)
	 * @param  sort the field and direction by which to sort (optionally
	 *         <code>null</code>)
	 * @return the matching web content ordered by <code>sort</code>
	 * @throws SystemException if a system exception occurred
	 */
	public Hits search(
			long companyId, long groupId, List<Long> folderIds,
			long classNameId, String ddmStructureKey, String ddmTemplateKey,
			String keywords, LinkedHashMap<String, Object> params, int start,
			int end, Sort sort)
		throws SystemException {

		String articleId = null;
		String title = null;
		String description = null;
		String content = null;
		boolean andOperator = false;

		if (Validator.isNotNull(keywords)) {
			articleId = keywords;
			title = keywords;
			description = keywords;
			content = keywords;
		}
		else {
			andOperator = true;
		}

		String status = String.valueOf(WorkflowConstants.STATUS_ANY);

		if (params != null) {
			params.put("keywords", keywords);
		}

		return search(
			companyId, groupId, folderIds, classNameId, articleId, title,
			description, content, null, status, ddmStructureKey, ddmTemplateKey,
			params, andOperator, start, end, sort);
	}

	/**
	 * Returns an ordered range of all the web content matching the group,
	 * folders, class name ID, article ID, title keyword, description keyword,
	 * content keyword, type, status, DDM structure key, and DDM template key.
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
	 * @param  companyId the primary key of the web content's company
	 * @param  groupId the primary key of the group
	 * @param  folderIds the primary keys of the web content folders
	 * @param  classNameId the primary key of the entity's instance the web
	 *         content is related to
	 * @param  articleId the primary key of the web content
	 * @param  title the title keywords
	 * @param  description the description keywords
	 * @param  content the content keywords
	 * @param  type the web content's type
	 * @param  status the web content's status
	 * @param  ddmStructureKey the primary key of the web content's DDM
	 *         structure
	 * @param  ddmTemplateKey the primary key of the web content's DDM template
	 * @param  params the finder parameters (optionally <code>null</code>)
	 * @param  andSearch whether every field must match its keywords, or just
	 *         one field
	 * @param  start the lower bound of the range of web content to return
	 * @param  end the upper bound of the range of web content to return (not
	 *         inclusive)
	 * @param  sort the field and direction by which to sort (optionally
	 *         <code>null</code>)
	 * @return the matching web content ordered by <code>sort</code>
	 * @throws SystemException if a system exception occurred
	 */
	public Hits search(
			long companyId, long groupId, List<Long> folderIds,
			long classNameId, String articleId, String title,
			String description, String content, String type, String status,
			String ddmStructureKey, String ddmTemplateKey,
			LinkedHashMap<String, Object> params, boolean andSearch, int start,
			int end, Sort sort)
		throws SystemException {

		try {
			SearchContext searchContext = new SearchContext();

			searchContext.setAndSearch(andSearch);

			Map<String, Serializable> attributes =
				new HashMap<String, Serializable>();

			attributes.put(Field.CLASS_NAME_ID, classNameId);
			attributes.put(Field.CONTENT, content);
			attributes.put(Field.DESCRIPTION, description);
			attributes.put(Field.STATUS, status);
			attributes.put(Field.TITLE, title);
			attributes.put(Field.TYPE, type);
			attributes.put("articleId", articleId);
			attributes.put("ddmStructureKey", ddmStructureKey);
			attributes.put("ddmTemplateKey", ddmTemplateKey);
			attributes.put("params", params);

			searchContext.setAttributes(attributes);

			searchContext.setCompanyId(companyId);
			searchContext.setEnd(end);
			searchContext.setFolderIds(folderIds);
			searchContext.setGroupIds(new long[] {groupId});
			searchContext.setIncludeDiscussions(
				GetterUtil.getBoolean(params.get("includeDiscussions")));

			if (params != null) {
				String keywords = (String)params.remove("keywords");

				if (Validator.isNotNull(keywords)) {
					searchContext.setKeywords(keywords);
				}
			}

			QueryConfig queryConfig = new QueryConfig();

			queryConfig.setHighlightEnabled(false);
			queryConfig.setScoreEnabled(false);

			searchContext.setQueryConfig(queryConfig);

			if (sort != null) {
				searchContext.setSorts(new Sort[] {sort});
			}

			searchContext.setStart(start);

			Indexer indexer = IndexerRegistryUtil.nullSafeGetIndexer(
				JournalArticle.class);

			return indexer.search(searchContext);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	/**
	 * Returns the number of web content matching the group, folders, class name
	 * ID, keywords, version, type, DDM structure key, DDM template key, display
	 * date, status, and review date.
	 *
	 * @param  companyId the primary key of the web content's company
	 * @param  groupId the primary key of the group
	 * @param  folderIds the primary keys of the web content folders
	 * @param  classNameId the primary key of the entity's instance the web
	 *         content is related to
	 * @param  keywords the keywords (space separated), which may occur in the
	 *         web content's article ID, title, description, or content
	 *         (optionally <code>null</code>)
	 * @param  version the web content's version
	 * @param  type the web content's type
	 * @param  ddmStructureKey the primary key of the web content's DDM
	 *         structure
	 * @param  ddmTemplateKey the primary key of the web content's DDM template
	 * @param  displayDateGT the latest date in the web content's display date
	 *         search
	 * @param  displayDateLT the earliest date in the web content's display date
	 *         search
	 * @param  status the web content's status
	 * @param  reviewDate the web content's scheduled review date
	 * @return the number of matching web content
	 * @throws SystemException if a system exception occurred
	 */
	public int searchCount(
			long companyId, long groupId, List<Long> folderIds,
			long classNameId, String keywords, Double version, String type,
			String ddmStructureKey, String ddmTemplateKey, Date displayDateGT,
			Date displayDateLT, int status, Date reviewDate)
		throws SystemException {

		return journalArticleFinder.countByKeywords(
			companyId, groupId, folderIds, classNameId, keywords, version, type,
			ddmStructureKey, ddmTemplateKey, displayDateGT, displayDateLT,
			status, reviewDate);
	}

	/**
	 * Returns the number of web content matching the group, folders, class name
	 * ID, article ID, version, title keyword, description keyword, content
	 * keyword, type, DDM structure key, DDM template key, display date, status,
	 * and review date.
	 *
	 * @param  companyId the primary key of the web content's company
	 * @param  groupId the primary key of the group
	 * @param  folderIds the primary keys of the web content folders
	 * @param  classNameId the primary key of the entity's instance the web
	 *         content is related to
	 * @param  articleId the primary key of the web content
	 * @param  version the web content's version
	 * @param  title the title keywords
	 * @param  description the description keywords
	 * @param  content the content keywords
	 * @param  type the web content's type
	 * @param  ddmStructureKey the primary key of the web content's DDM
	 *         structure
	 * @param  ddmTemplateKey the primary key of the web content's DDM template
	 * @param  displayDateGT the latest date in the web content's display date
	 *         search
	 * @param  displayDateLT the earliest date in the web content's display date
	 *         search
	 * @param  status the web content's status
	 * @param  reviewDate the web content's scheduled review date
	 * @param  andOperator whether every field must match its keywords, or just
	 *         one field
	 * @return the number of matching web content
	 * @throws SystemException if a system exception occurred
	 */
	public int searchCount(
			long companyId, long groupId, List<Long> folderIds,
			long classNameId, String articleId, Double version, String title,
			String description, String content, String type,
			String ddmStructureKey, String ddmTemplateKey, Date displayDateGT,
			Date displayDateLT, int status, Date reviewDate,
			boolean andOperator)
		throws SystemException {

		return journalArticleFinder.countByC_G_F_C_A_V_T_D_C_T_S_T_D_R(
			companyId, groupId, folderIds, classNameId, articleId, version,
			title, description, content, type, ddmStructureKey, ddmTemplateKey,
			displayDateGT, displayDateLT, reviewDate, andOperator,
			new QueryDefinition(status));
	}

	/**
	 * Returns the number of web content matching the group, folders, class name
	 * ID, article ID, version, title keyword, description keyword, content
	 * keyword, type, DDM structure keys, DDM template keys, display date,
	 * status, and review date.
	 *
	 * @param  companyId the primary key of the web content's company
	 * @param  groupId the primary key of the group
	 * @param  folderIds the primary keys of the web content folders
	 * @param  classNameId the primary key of the entity's instance the web
	 *         content is related to
	 * @param  articleId the primary key of the web content
	 * @param  version the web content's version
	 * @param  title the title keywords
	 * @param  description the description keywords
	 * @param  content the content keywords
	 * @param  type the web content's type
	 * @param  ddmStructureKeys the primary keys of the web content's DDM
	 *         structures
	 * @param  ddmTemplateKeys the primary keys of the web content's DDM
	 *         templates
	 * @param  displayDateGT the latest date in the web content's display date
	 *         search
	 * @param  displayDateLT the earliest date in the web content's display date
	 *         search
	 * @param  status the web content's status
	 * @param  reviewDate the web content's scheduled review date
	 * @param  andOperator whether every field must match its keywords, or just
	 *         one field
	 * @return the number of matching web content
	 * @throws SystemException if a system exception occurred
	 */
	public int searchCount(
			long companyId, long groupId, List<Long> folderIds,
			long classNameId, String articleId, Double version, String title,
			String description, String content, String type,
			String[] ddmStructureKeys, String[] ddmTemplateKeys,
			Date displayDateGT, Date displayDateLT, int status, Date reviewDate,
			boolean andOperator)
		throws SystemException {

		return journalArticleFinder.countByC_G_F_C_A_V_T_D_C_T_S_T_D_R(
			companyId, groupId, folderIds, classNameId, articleId, version,
			title, description, content, type, ddmStructureKeys,
			ddmTemplateKeys, displayDateGT, displayDateLT, reviewDate,
			andOperator, new QueryDefinition(status));
	}

	/**
	 * Subscribes the user or group to the web content.
	 *
	 * @param  userId the primary key of the web content's creator/owner
	 * @param  groupId the primary key of the group
	 * @throws PortalException if a portal exception occurred
	 * @throws SystemException if a system exception occurred
	 */
	public void subscribe(long userId, long groupId)
		throws PortalException, SystemException {

		subscriptionLocalService.addSubscription(
			userId, groupId, JournalArticle.class.getName(), groupId);
	}

	/**
	 * Unsubscribes the user or group from the web content.
	 *
	 * @param  userId the primary key of the web content's creator/owner
	 * @param  groupId the primary key of the group
	 * @throws PortalException if a portal exception occurred
	 * @throws SystemException if a system exception occurred
	 */
	public void unsubscribe(long userId, long groupId)
		throws PortalException, SystemException {

		subscriptionLocalService.deleteSubscription(
			userId, JournalArticle.class.getName(), groupId);
	}

	/**
	 * Updates the web content matching the group, article ID, and version.
	 *
	 * @param  userId the primary key of the web content's creator/owner
	 * @param  groupId the primary key of the group
	 * @param  folderId the primary key of the web content folder
	 * @param  articleId the primary key of the web content
	 * @param  version the web content's version
	 * @param  titleMap the web content's locales and localized titles
	 * @param  descriptionMap the web content's locales and localized
	 *         descriptions
	 * @param  content the web content's content
	 * @param  layoutUuid the unique string identifying the web content's layout
	 * @param  serviceContext the service context to be applied. Can set the
	 *         modification date, expando bridge attributes, asset category IDs,
	 *         asset tag names, asset link entry IDs, portlet preferences,
	 *         workflow actions, and can set whether to add the default command
	 *         update for the web content.
	 * @return the updated web content
	 * @throws PortalException if a portal exception occurred
	 * @throws SystemException if a system exception occurred
	 */
	public JournalArticle updateArticle(
			long userId, long groupId, long folderId, String articleId,
			double version, Map<Locale, String> titleMap,
			Map<Locale, String> descriptionMap, String content,
			String layoutUuid, ServiceContext serviceContext)
		throws PortalException, SystemException {

		User user = userPersistence.findByPrimaryKey(userId);

		JournalArticle article = journalArticlePersistence.findByG_A_V(
			groupId, articleId, version);

		Date displayDate = article.getDisplayDate();

		int displayDateMonth = 0;
		int displayDateDay = 0;
		int displayDateYear = 0;
		int displayDateHour = 0;
		int displayDateMinute = 0;

		if (displayDate != null) {
			Calendar displayCal = CalendarFactoryUtil.getCalendar(
				user.getTimeZone());

			displayCal.setTime(displayDate);

			displayDateMonth = displayCal.get(Calendar.MONTH);
			displayDateDay = displayCal.get(Calendar.DATE);
			displayDateYear = displayCal.get(Calendar.YEAR);
			displayDateHour = displayCal.get(Calendar.HOUR);
			displayDateMinute = displayCal.get(Calendar.MINUTE);

			if (displayCal.get(Calendar.AM_PM) == Calendar.PM) {
				displayDateHour += 12;
			}
		}

		Date expirationDate = article.getExpirationDate();

		int expirationDateMonth = 0;
		int expirationDateDay = 0;
		int expirationDateYear = 0;
		int expirationDateHour = 0;
		int expirationDateMinute = 0;
		boolean neverExpire = true;

		if (expirationDate != null) {
			Calendar expirationCal = CalendarFactoryUtil.getCalendar(
				user.getTimeZone());

			expirationCal.setTime(expirationDate);

			expirationDateMonth = expirationCal.get(Calendar.MONTH);
			expirationDateDay = expirationCal.get(Calendar.DATE);
			expirationDateYear = expirationCal.get(Calendar.YEAR);
			expirationDateHour = expirationCal.get(Calendar.HOUR);
			expirationDateMinute = expirationCal.get(Calendar.MINUTE);
			neverExpire = false;

			if (expirationCal.get(Calendar.AM_PM) == Calendar.PM) {
				expirationDateHour += 12;
			}
		}

		Date reviewDate = article.getReviewDate();

		int reviewDateMonth = 0;
		int reviewDateDay = 0;
		int reviewDateYear = 0;
		int reviewDateHour = 0;
		int reviewDateMinute = 0;
		boolean neverReview = true;

		if (reviewDate != null) {
			Calendar reviewCal = CalendarFactoryUtil.getCalendar(
				user.getTimeZone());

			reviewCal.setTime(reviewDate);

			reviewDateMonth = reviewCal.get(Calendar.MONTH);
			reviewDateDay = reviewCal.get(Calendar.DATE);
			reviewDateYear = reviewCal.get(Calendar.YEAR);
			reviewDateHour = reviewCal.get(Calendar.HOUR);
			reviewDateMinute = reviewCal.get(Calendar.MINUTE);
			neverReview = false;

			if (reviewCal.get(Calendar.AM_PM) == Calendar.PM) {
				reviewDateHour += 12;
			}
		}

		return updateArticle(
			userId, groupId, folderId, articleId, version, titleMap,
			descriptionMap, content, article.getType(),
			article.getStructureId(), article.getTemplateId(), layoutUuid,
			displayDateMonth, displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, expirationDateMonth, expirationDateDay,
			expirationDateYear, expirationDateHour, expirationDateMinute,
			neverExpire, reviewDateMonth, reviewDateDay, reviewDateYear,
			reviewDateHour, reviewDateMinute, neverReview,
			article.getIndexable(), article.isSmallImage(),
			article.getSmallImageURL(), null, null, null, serviceContext);
	}

	/**
	 * Updates the web content.
	 *
	 * @param  userId the primary key of the web content's creator/owner
	 * @param  groupId the primary key of the group
	 * @param  folderId the primary key of the web content folder
	 * @param  articleId the primary key of the web content
	 * @param  version the web content's version
	 * @param  titleMap the web content's locales and localized titles
	 * @param  descriptionMap the web content's locales and localized
	 *         descriptions
	 * @param  content the web content's content
	 * @param  type the web content's type
	 * @param  ddmStructureKey the primary key of the web content's DDM
	 *         structure
	 * @param  ddmTemplateKey the primary key of the web content's DDM template
	 * @param  layoutUuid the unique string identifying the web content's layout
	 * @param  displayDateMonth the month the web content is set to display
	 * @param  displayDateDay the calendar day the web content is set to display
	 * @param  displayDateYear the year the web content is set to display
	 * @param  displayDateHour the hour the web content is set to display
	 * @param  displayDateMinute the minute the web content is set to display
	 * @param  expirationDateMonth the month the web content is set to expire
	 * @param  expirationDateDay the calendar day the web content is set to
	 *         expire
	 * @param  expirationDateYear the year the web content is set to expire
	 * @param  expirationDateHour the hour the web content is set to expire
	 * @param  expirationDateMinute the minute the web content is set to expire
	 * @param  neverExpire whether the web content is not set to auto expire
	 * @param  reviewDateMonth the month the web content is set for review
	 * @param  reviewDateDay the calendar day the web content is set for review
	 * @param  reviewDateYear the year the web content is set for review
	 * @param  reviewDateHour the hour the web content is set for review
	 * @param  reviewDateMinute the minute the web content is set for review
	 * @param  neverReview whether the web content is not set for review
	 * @param  indexable whether the web content is searchable
	 * @param  smallImage whether the web content has a small image
	 * @param  smallImageURL the web content's small image URL
	 * @param  smallImageFile the web content's small image file
	 * @param  images the web content's images
	 * @param  articleURL the web content's accessible URL
	 * @param  serviceContext the service context to be applied. Can set the
	 *         modification date, expando bridge attributes,  asset category
	 *         IDs, asset tag names, asset link entry IDs, portlet preferences,
	 *         workflow actions, and can set whether to add the default command
	 *         update for the web content.
	 * @return the updated web content
	 * @throws PortalException if a portal exception occurred
	 * @throws SystemException if a system exception occurred
	 */
	public JournalArticle updateArticle(
			long userId, long groupId, long folderId, String articleId,
			double version, Map<Locale, String> titleMap,
			Map<Locale, String> descriptionMap, String content, String type,
			String ddmStructureKey, String ddmTemplateKey, String layoutUuid,
			int displayDateMonth, int displayDateDay, int displayDateYear,
			int displayDateHour, int displayDateMinute, int expirationDateMonth,
			int expirationDateDay, int expirationDateYear,
			int expirationDateHour, int expirationDateMinute,
			boolean neverExpire, int reviewDateMonth, int reviewDateDay,
			int reviewDateYear, int reviewDateHour, int reviewDateMinute,
			boolean neverReview, boolean indexable, boolean smallImage,
			String smallImageURL, File smallImageFile,
			Map<String, byte[]> images, String articleURL,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		// Article

		User user = userPersistence.findByPrimaryKey(userId);
		articleId = articleId.trim().toUpperCase();

		byte[] smallImageBytes = null;

		try {
			smallImageBytes = FileUtil.getBytes(smallImageFile);
		}
		catch (IOException ioe) {
		}

		JournalArticle latestArticle = getLatestArticle(
			groupId, articleId, WorkflowConstants.STATUS_ANY);

		JournalArticle article = latestArticle;

		boolean imported = ExportImportThreadLocal.isImportInProcess();

		double latestVersion = latestArticle.getVersion();

		boolean addNewVersion = false;

		if (imported) {
			if (latestVersion > version) {
				JournalArticle existingArticle =
					journalArticlePersistence.fetchByG_A_V(
						groupId, articleId, version);

				if (existingArticle != null) {
					article = existingArticle;
				}
				else {
					addNewVersion = true;
				}
			}
			else if (latestVersion < version) {
				addNewVersion = true;
			}
		}
		else {
			if ((version > 0) && (version != latestVersion)) {
				throw new ArticleVersionException();
			}

			serviceContext.validateModifiedDate(
				latestArticle, ArticleVersionException.class);

			if (latestArticle.isApproved() || latestArticle.isExpired()) {
				addNewVersion = true;

				version = MathUtil.format(latestVersion + 0.1, 1, 1);
			}
		}

		Date displayDate = null;
		Date expirationDate = null;
		Date reviewDate = null;

		if (article.getClassNameId() ==
				JournalArticleConstants.CLASSNAME_ID_DEFAULT) {

			displayDate = PortalUtil.getDate(
				displayDateMonth, displayDateDay, displayDateYear,
				displayDateHour, displayDateMinute, user.getTimeZone(),
				ArticleDisplayDateException.class);

			if (!neverExpire) {
				expirationDate = PortalUtil.getDate(
					expirationDateMonth, expirationDateDay, expirationDateYear,
					expirationDateHour, expirationDateMinute,
					user.getTimeZone(), ArticleExpirationDateException.class);
			}

			if (!neverReview) {
				reviewDate = PortalUtil.getDate(
					reviewDateMonth, reviewDateDay, reviewDateYear,
					reviewDateHour, reviewDateMinute, user.getTimeZone(),
					ArticleReviewDateException.class);
			}
		}

		Date now = new Date();

		boolean expired = false;

		if ((expirationDate != null) && expirationDate.before(now)) {
			expired = true;
		}

		validate(
			user.getCompanyId(), groupId, latestArticle.getClassNameId(),
			titleMap, content, type, ddmStructureKey, ddmTemplateKey,
			expirationDate, smallImage, smallImageURL, smallImageFile,
			smallImageBytes);

		if (addNewVersion) {
			long id = counterLocalService.increment();

			article = journalArticlePersistence.create(id);

			article.setResourcePrimKey(latestArticle.getResourcePrimKey());
			article.setGroupId(latestArticle.getGroupId());
			article.setCompanyId(latestArticle.getCompanyId());
			article.setUserId(user.getUserId());
			article.setUserName(user.getFullName());
			article.setCreateDate(serviceContext.getModifiedDate(now));
			article.setClassNameId(latestArticle.getClassNameId());
			article.setClassPK(latestArticle.getClassPK());
			article.setArticleId(articleId);
			article.setVersion(version);
			article.setSmallImageId(latestArticle.getSmallImageId());
		}

		Locale locale = LocaleUtil.getDefault();

		String defaultLanguageId = ParamUtil.getString(
			serviceContext, "defaultLanguageId");

		if (Validator.isNull(defaultLanguageId)) {
			defaultLanguageId = LocalizationUtil.getDefaultLocale(content);
		}

		if (Validator.isNotNull(defaultLanguageId)) {
			locale = LocaleUtil.fromLanguageId(defaultLanguageId);
		}

		String title = titleMap.get(locale);

		content = format(
			user, groupId, articleId, article.getVersion(), addNewVersion,
			content, ddmStructureKey, images);

		article.setModifiedDate(serviceContext.getModifiedDate(now));
		article.setFolderId(folderId);
		article.setTitleMap(titleMap, locale);
		article.setUrlTitle(
			getUniqueUrlTitle(
				article.getId(), article.getArticleId(), title,
				latestArticle.getUrlTitle(), serviceContext));
		article.setDescriptionMap(descriptionMap, locale);
		article.setContent(content);
		article.setType(type);
		article.setStructureId(ddmStructureKey);
		article.setTemplateId(ddmTemplateKey);
		article.setLayoutUuid(layoutUuid);
		article.setDisplayDate(displayDate);
		article.setExpirationDate(expirationDate);
		article.setReviewDate(reviewDate);
		article.setIndexable(indexable);
		article.setSmallImage(smallImage);

		if (smallImage) {
			if ((smallImageFile != null) && (smallImageBytes != null)) {
				article.setSmallImageId(counterLocalService.increment());
			}
		}
		else {
			article.setSmallImageId(0);
		}

		article.setSmallImageURL(smallImageURL);

		if (latestArticle.isPending()) {
			article.setStatus(latestArticle.getStatus());
		}
		else if (!expired) {
			article.setStatus(WorkflowConstants.STATUS_DRAFT);
		}
		else {
			article.setStatus(WorkflowConstants.STATUS_EXPIRED);
		}

		article.setExpandoBridgeAttributes(serviceContext);

		journalArticlePersistence.update(article);

		// Asset

		updateAsset(
			userId, article, serviceContext.getAssetCategoryIds(),
			serviceContext.getAssetTagNames(),
			serviceContext.getAssetLinkEntryIds());

		// Dynamic data mapping

		if (PortalUtil.getClassNameId(DDMStructure.class) ==
				article.getClassNameId()) {

			updateDDMStructureXSD(
				article.getClassPK(), content, serviceContext);
		}

		// Small image

		saveImages(
			smallImage, article.getSmallImageId(), smallImageFile,
			smallImageBytes);

		// Email

		PortletPreferences preferences =
			ServiceContextUtil.getPortletPreferences(serviceContext);

		// Workflow

		if (expired && imported) {
			updateStatus(
				userId, article, article.getStatus(), articleURL,
				new HashMap<String, Serializable>(), serviceContext);
		}

		if (serviceContext.getWorkflowAction() ==
				WorkflowConstants.ACTION_PUBLISH) {

			sendEmail(
				article, articleURL, preferences, "requested", serviceContext);

			WorkflowHandlerRegistryUtil.startWorkflowInstance(
				user.getCompanyId(), groupId, userId,
				JournalArticle.class.getName(), article.getId(), article,
				serviceContext);
		}
		else if (article.getVersion() ==
					JournalArticleConstants.VERSION_DEFAULT) {

			// Indexer

			reindex(article);
		}

		return article;
	}

	/**
	 * Updates the web content matching the group, article ID, and version,
	 * replacing its user, group, folder, article ID, version, and content.
	 *
	 * @param  userId the primary key of the web content's creator/owner
	 * @param  groupId the primary key of the group
	 * @param  folderId the primary key of the web content folder
	 * @param  articleId the primary key of the web content
	 * @param  version the web content's version
	 * @param  content the web content's content
	 * @param  serviceContext the service context to be applied. Can set the
	 *         modification date, expando bridge attributes, asset category IDs,
	 *         asset tag names, asset link entry IDs, portlet preferences,
	 *         workflow actions, and can set whether to add the default command
	 *         update for the web content.
	 * @return the updated web content
	 * @throws PortalException if a portal exception occurred
	 * @throws SystemException if a system exception occurred
	 */
	public JournalArticle updateArticle(
			long userId, long groupId, long folderId, String articleId,
			double version, String content, ServiceContext serviceContext)
		throws PortalException, SystemException {

		JournalArticle article = journalArticlePersistence.findByG_A_V(
			groupId, articleId, version);

		return updateArticle(
			userId, groupId, folderId, articleId, version,
			article.getTitleMap(), article.getDescriptionMap(), content,
			article.getLayoutUuid(), serviceContext);
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link
	 *             #updateArticleTranslation(long, String, double, Locale,
	 *             String, String, String, Map, ServiceContext)}
	 */
	public JournalArticle updateArticleTranslation(
			long groupId, String articleId, double version, Locale locale,
			String title, String description, String content,
			Map<String, byte[]> images)
		throws PortalException, SystemException {

		return updateArticleTranslation(
			groupId, articleId, version, locale, title, description, content,
			images, null);
	}

	/**
	 * Updates the translation of the web content.
	 *
	 * @param  groupId the primary key of the group
	 * @param  articleId the primary key of the web content
	 * @param  version the web content's version
	 * @param  locale the locale of the web content's display template
	 * @param  title the translated web content title
	 * @param  description the translated web content description
	 * @param  content the web content's content
	 * @param  images the web content's images
	 * @param  serviceContext the service context to be applied. Can set the
	 *         modification date for the web content.
	 * @return the updated web content
	 * @throws PortalException if a portal exception occurred
	 * @throws SystemException if a system exception occurred
	 */
	public JournalArticle updateArticleTranslation(
			long groupId, String articleId, double version, Locale locale,
			String title, String description, String content,
			Map<String, byte[]> images, ServiceContext serviceContext)
		throws PortalException, SystemException {

		validateContent(content);

		JournalArticle oldArticle = getLatestArticle(
			groupId, articleId, WorkflowConstants.STATUS_ANY);

		double oldVersion = oldArticle.getVersion();

		if ((version > 0) && (version != oldVersion)) {
			throw new ArticleVersionException();
		}

		boolean incrementVersion = false;

		if (oldArticle.isApproved() || oldArticle.isExpired()) {
			incrementVersion = true;
		}

		if (serviceContext != null) {
			serviceContext.validateModifiedDate(
				oldArticle, ArticleVersionException.class);
		}

		JournalArticle article = null;

		User user = userPersistence.findByPrimaryKey(oldArticle.getUserId());

		if (incrementVersion) {
			double newVersion = MathUtil.format(oldVersion + 0.1, 1, 1);

			long id = counterLocalService.increment();

			article = journalArticlePersistence.create(id);

			article.setResourcePrimKey(oldArticle.getResourcePrimKey());
			article.setGroupId(oldArticle.getGroupId());
			article.setCompanyId(oldArticle.getCompanyId());
			article.setUserId(oldArticle.getUserId());
			article.setUserName(user.getFullName());
			article.setCreateDate(new Date());
			article.setModifiedDate(new Date());
			article.setClassNameId(oldArticle.getClassNameId());
			article.setClassPK(oldArticle.getClassPK());
			article.setArticleId(articleId);
			article.setVersion(newVersion);
			article.setTitleMap(oldArticle.getTitleMap());
			article.setUrlTitle(
				getUniqueUrlTitle(
					id, articleId, title, oldArticle.getUrlTitle(),
					serviceContext));
			article.setDescriptionMap(oldArticle.getDescriptionMap());
			article.setType(oldArticle.getType());
			article.setStructureId(oldArticle.getStructureId());
			article.setTemplateId(oldArticle.getTemplateId());
			article.setLayoutUuid(oldArticle.getLayoutUuid());
			article.setDisplayDate(oldArticle.getDisplayDate());
			article.setExpirationDate(oldArticle.getExpirationDate());
			article.setReviewDate(oldArticle.getReviewDate());
			article.setIndexable(oldArticle.getIndexable());
			article.setSmallImage(oldArticle.getSmallImage());
			article.setSmallImageId(oldArticle.getSmallImageId());

			if (article.getSmallImageId() == 0) {
				article.setSmallImageId(counterLocalService.increment());
			}

			article.setSmallImageURL(oldArticle.getSmallImageURL());

			article.setStatus(WorkflowConstants.STATUS_DRAFT);
			article.setStatusDate(new Date());
		}
		else {
			article = oldArticle;
		}

		Map<Locale, String> titleMap = article.getTitleMap();

		titleMap.put(locale, title);

		article.setTitleMap(titleMap);

		Map<Locale, String> descriptionMap = article.getDescriptionMap();

		descriptionMap.put(locale, description);

		article.setDescriptionMap(descriptionMap);

		content = format(
			user, groupId, articleId, article.getVersion(),
			!oldArticle.isDraft(), content, oldArticle.getStructureId(),
			images);

		article.setContent(content);

		journalArticlePersistence.update(article);

		return article;
	}

	/**
	 * Updates the web content's asset with the new asset categories, tag names,
	 * and link entries, removing and adding them as necessary.
	 *
	 * @param  userId the primary key of the web content's creator/owner
	 * @param  article the web content
	 * @param  assetCategoryIds the primary keys of the new asset categories
	 * @param  assetTagNames the new asset tag names
	 * @param  assetLinkEntryIds the primary keys of the new asset link entries
	 * @throws PortalException if a portal exception occurred
	 * @throws SystemException if a system exception occurred
	 */
	public void updateAsset(
			long userId, JournalArticle article, long[] assetCategoryIds,
			String[] assetTagNames, long[] assetLinkEntryIds)
		throws PortalException, SystemException {

		boolean visible = article.isApproved();

		if (article.getClassNameId() !=
				JournalArticleConstants.CLASSNAME_ID_DEFAULT) {

			visible = false;
		}

		boolean addDraftAssetEntry = false;

		if (!article.isApproved() &&
			(article.getVersion() != JournalArticleConstants.VERSION_DEFAULT)) {

			int approvedArticlesCount = journalArticlePersistence.countByG_A_ST(
				article.getGroupId(), article.getArticleId(),
				JournalArticleConstants.ASSET_ENTRY_CREATION_STATUSES);

			if (approvedArticlesCount > 0) {
				addDraftAssetEntry = true;
			}
		}

		AssetEntry assetEntry = null;

		if (addDraftAssetEntry) {
			assetEntry = assetEntryLocalService.updateEntry(
				userId, article.getGroupId(), article.getCreateDate(),
				article.getModifiedDate(), JournalArticle.class.getName(),
				article.getPrimaryKey(), article.getUuid(),
				getClassTypeId(article), assetCategoryIds, assetTagNames, false,
				null, null, null, ContentTypes.TEXT_HTML, article.getTitle(),
				article.getDescription(), article.getDescription(), null,
				article.getLayoutUuid(), 0, 0, null, false);
		}
		else {
			JournalArticleResource journalArticleResource =
				journalArticleResourceLocalService.getArticleResource(
					article.getResourcePrimKey());

			assetEntry = assetEntryLocalService.updateEntry(
				userId, article.getGroupId(), article.getCreateDate(),
				article.getModifiedDate(), JournalArticle.class.getName(),
				journalArticleResource.getResourcePrimKey(),
				journalArticleResource.getUuid(), getClassTypeId(article),
				assetCategoryIds, assetTagNames, visible, null, null, null,
				ContentTypes.TEXT_HTML, article.getTitle(),
				article.getDescription(), article.getDescription(), null,
				article.getLayoutUuid(), 0, 0, null, false);
		}

		assetLinkLocalService.updateLinks(
			userId, assetEntry.getEntryId(), assetLinkEntryIds,
			AssetLinkConstants.TYPE_RELATED);
	}

	/**
	 * Updates the web content matching the group, article ID, and version,
	 * replacing the web content's content.
	 *
	 * @param  groupId the primary key of the group
	 * @param  articleId the primary key of the web content
	 * @param  version the web content's version
	 * @param  content the web content's content
	 * @return the updated web content
	 * @throws PortalException if a portal exception occurred
	 * @throws SystemException if a system exception occurred
	 */
	public JournalArticle updateContent(
			long groupId, String articleId, double version, String content)
		throws PortalException, SystemException {

		JournalArticle article = journalArticlePersistence.findByG_A_V(
			groupId, articleId, version);

		article.setContent(content);

		journalArticlePersistence.update(article);

		return article;
	}

	/**
	 * Updates the web content's status.
	 *
	 * @param  userId the primary key of the web content's creator/owner
	 * @param  article the web content
	 * @param  status the web content's status
	 * @param  articleURL the web content's accessible URL
	 * @param  workflowContext the web content's configured workflow
	 * @param  serviceContext the service context to be applied. Can set the
	 *         modification date, portlet preferences, and can set whether to
	 *         add the default command update for the web content.
	 * @return the updated web content
	 * @throws PortalException if a portal exception occurred
	 * @throws SystemException if a system exception occurred
	 */
	public JournalArticle updateStatus(
			long userId, JournalArticle article, int status, String articleURL,
			Map<String, Serializable> workflowContext,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		// Article

		User user = userPersistence.findByPrimaryKey(userId);
		Date now = new Date();

		int oldStatus = article.getStatus();

		int oldArticleVersionStatus = WorkflowConstants.STATUS_ANY;

		List<ObjectValuePair<Long, Integer>> articleVersionStatusOVPs =
			new ArrayList<ObjectValuePair<Long, Integer>>();

		List<JournalArticle> articleVersions =
			(List<JournalArticle>)workflowContext.get("articleVersions");

		if ((articleVersions != null) && !articleVersions.isEmpty()) {
			JournalArticle oldArticleVersion = articleVersions.get(0);

			oldArticleVersionStatus = oldArticleVersion.getStatus();

			articleVersionStatusOVPs = getArticleVersionStatuses(
				articleVersions);
		}

		article.setModifiedDate(serviceContext.getModifiedDate(now));

		boolean neverExpire = false;

		if (status == WorkflowConstants.STATUS_APPROVED) {
			Date expirationDate = article.getExpirationDate();

			if ((expirationDate != null) && expirationDate.before(now)) {
				neverExpire = true;

				article.setExpirationDate(null);
			}
		}

		if (status == WorkflowConstants.STATUS_EXPIRED) {
			article.setExpirationDate(now);
		}

		article.setStatus(status);
		article.setStatusByUserId(user.getUserId());
		article.setStatusByUserName(user.getFullName());
		article.setStatusDate(serviceContext.getModifiedDate(now));

		journalArticlePersistence.update(article);

		if (hasModifiedLatestApprovedVersion(
				article.getGroupId(), article.getArticleId(),
				article.getVersion())) {

			if (status == WorkflowConstants.STATUS_APPROVED) {
				updateUrlTitles(
					article.getGroupId(), article.getArticleId(),
					article.getUrlTitle());

				// Asset

				if ((oldStatus != WorkflowConstants.STATUS_APPROVED) &&
					(article.getVersion() !=
						JournalArticleConstants.VERSION_DEFAULT)) {

					AssetEntry draftAssetEntry = null;

					try {
						draftAssetEntry = assetEntryLocalService.getEntry(
							JournalArticle.class.getName(),
							article.getPrimaryKey());

						long[] assetCategoryIds =
							draftAssetEntry.getCategoryIds();
						String[] assetTagNames = draftAssetEntry.getTagNames();

						List<AssetLink> assetLinks =
							assetLinkLocalService.getDirectLinks(
								draftAssetEntry.getEntryId(),
								AssetLinkConstants.TYPE_RELATED);

						long[] assetLinkEntryIds = StringUtil.split(
							ListUtil.toString(
								assetLinks, AssetLink.ENTRY_ID2_ACCESSOR), 0L);

						AssetEntry assetEntry =
							assetEntryLocalService.updateEntry(
								userId, article.getGroupId(),
								article.getCreateDate(),
								article.getModifiedDate(),
								JournalArticle.class.getName(),
								article.getResourcePrimKey(), article.getUuid(),
								getClassTypeId(article), assetCategoryIds,
								assetTagNames, false, null, null, null,
								ContentTypes.TEXT_HTML, article.getTitle(),
								article.getDescription(),
								article.getDescription(), null,
								article.getLayoutUuid(), 0, 0, null, false);

						assetLinkLocalService.updateLinks(
							userId, assetEntry.getEntryId(), assetLinkEntryIds,
							AssetLinkConstants.TYPE_RELATED);

						assetEntryLocalService.deleteEntry(
							JournalArticle.class.getName(),
							article.getPrimaryKey());
					}
					catch (NoSuchEntryException nsee) {
					}
				}

				if (article.getClassNameId() ==
						JournalArticleConstants.CLASSNAME_ID_DEFAULT) {

					// Get the earliest display date and latest expiration date
					// among all article versions

					Date[] dateInterval = getDateInterval(
						article.getGroupId(), article.getArticleId(),
						article.getDisplayDate(), article.getExpirationDate());

					Date displayDate = dateInterval[0];
					Date expirationDate = dateInterval[1];

					if (neverExpire) {
						expirationDate = null;
					}

					assetEntryLocalService.updateEntry(
						JournalArticle.class.getName(),
						article.getResourcePrimKey(), displayDate,
						expirationDate, true);
				}

				// Social

				if (serviceContext.isCommandUpdate()) {
					socialActivityLocalService.addActivity(
						user.getUserId(), article.getGroupId(),
						JournalArticle.class.getName(),
						article.getResourcePrimKey(),
						JournalActivityKeys.UPDATE_ARTICLE,
						getExtraDataJSON(article, serviceContext), 0);
				}
				else {
					socialActivityLocalService.addUniqueActivity(
						user.getUserId(), article.getGroupId(),
						JournalArticle.class.getName(),
						article.getResourcePrimKey(),
						JournalActivityKeys.ADD_ARTICLE,
						getExtraDataJSON(article, serviceContext), 0);
				}

				// Indexer

				reindex(article);
			}
			else if (oldStatus == WorkflowConstants.STATUS_APPROVED) {
				updatePreviousApprovedArticle(article);
			}
		}

		if (oldStatus == WorkflowConstants.STATUS_IN_TRASH) {

			// Trash

			List<TrashVersion> trashVersions =
				(List<TrashVersion>)workflowContext.get("trashVersions");

			for (TrashVersion trashVersion : trashVersions) {
				JournalArticle trashArticleVersion =
					journalArticlePersistence.findByPrimaryKey(
						trashVersion.getClassPK());

				trashArticleVersion.setStatus(trashVersion.getStatus());

				journalArticlePersistence.update(trashArticleVersion);
			}

			trashEntryLocalService.deleteEntry(
				JournalArticle.class.getName(), article.getResourcePrimKey());

			// Indexer

			if (status == WorkflowConstants.STATUS_APPROVED) {
				reindex(article);
			}
			else {
				Indexer indexer = IndexerRegistryUtil.nullSafeGetIndexer(
					JournalArticle.class);

				indexer.delete(article);
			}

		}
		else if (status == WorkflowConstants.STATUS_IN_TRASH) {
			assetEntryLocalService.updateVisible(
				JournalArticle.class.getName(), article.getResourcePrimKey(),
				false);

			// Trash

			for (JournalArticle curArticleVersion : articleVersions) {
				curArticleVersion.setStatus(WorkflowConstants.STATUS_IN_TRASH);

				journalArticlePersistence.update(curArticleVersion);
			}

			UnicodeProperties typeSettingsProperties = new UnicodeProperties();

			typeSettingsProperties.put("title", article.getArticleId());

			trashEntryLocalService.addTrashEntry(
				userId, article.getGroupId(), JournalArticle.class.getName(),
				article.getResourcePrimKey(), oldArticleVersionStatus,
				articleVersionStatusOVPs, typeSettingsProperties);

			// Indexer

			reindex(article);
		}

		if ((article.getClassNameId() ==
				JournalArticleConstants.CLASSNAME_ID_DEFAULT) &&
			(oldStatus != WorkflowConstants.STATUS_IN_TRASH) &&
			(status != WorkflowConstants.STATUS_IN_TRASH)) {

			// Email

			if ((oldStatus == WorkflowConstants.STATUS_PENDING) &&
				((status == WorkflowConstants.STATUS_APPROVED) ||
				 (status == WorkflowConstants.STATUS_DENIED))) {

				String msg = "granted";

				if (status == WorkflowConstants.STATUS_DENIED) {
					msg = "denied";
				}

				try {
					PortletPreferences preferences =
						ServiceContextUtil.getPortletPreferences(
							serviceContext);

					sendEmail(
						article, articleURL, preferences, msg, serviceContext);
				}
				catch (Exception e) {
					_log.error(
						"Unable to send email to notify the change of status " +
							" to " + msg + " for article " + article.getId() +
								": " + e.getMessage());
				}
			}

			// Subscriptions

			notifySubscribers(article, serviceContext);
		}

		return article;
	}

	/**
	 * Updates the web content's status by matching the class PK.
	 *
	 * @param  userId the primary key of the web content's creator/owner
	 * @param  classPK the primary key of the web content's related entity
	 * @param  status the web content's status
	 * @param  workflowContext the web content's configured workflow
	 * @param  serviceContext the service context to be applied. Can set the
	 *         modification date, portlet preferences, and can set whether to
	 *         add the default command update for the web content.
	 * @return the updated web content
	 * @throws PortalException if a portal exception occurred
	 * @throws SystemException if a system exception occurred
	 */
	public JournalArticle updateStatus(
			long userId, long classPK, int status,
			Map<String, Serializable> workflowContext,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		JournalArticle article = getArticle(classPK);

		return updateStatus(
			userId, article, status, null, workflowContext, serviceContext);
	}

	/**
	 * Updates the web content's status by matching the group, article ID, and
	 * version.
	 *
	 * @param  userId the primary key of the web content's creator/owner
	 * @param  groupId the primary key of the group
	 * @param  articleId the primary key of the web content
	 * @param  version the web content's version
	 * @param  status the web content's status
	 * @param  articleURL the web content's accessible URL
	 * @param  workflowContext the web content's configured workflow
	 * @param  serviceContext the service context to be applied. Can set the
	 *         modification date, portlet preferences, and can set whether to
	 *         add the default command update for the web content.
	 * @return the updated web content
	 * @throws PortalException if a portal exception occurred
	 * @throws SystemException if a system exception occurred
	 */
	public JournalArticle updateStatus(
			long userId, long groupId, String articleId, double version,
			int status, String articleURL,
			Map<String, Serializable> workflowContext,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		JournalArticle article = journalArticlePersistence.findByG_A_V(
			groupId, articleId, version);

		return updateStatus(
			userId, article, status, articleURL, workflowContext,
			serviceContext);
	}

	/**
	 * Updates the web content matching the group, class name ID, and DDM
	 * template key, replacing the DDM template key with a new one.
	 *
	 * @param  groupId the primary key of the group
	 * @param  classNameId the primary key of the entity's instance the web
	 *         content is related to
	 * @param  oldDDMTemplateKey the primary key of the web content's old DDM
	 *         template
	 * @param  newDDMTemplateKey the primary key of the web content's new DDM
	 *         template
	 * @throws SystemException if a system exception occurred
	 */
	public void updateTemplateId(
			long groupId, long classNameId, String oldDDMTemplateKey,
			String newDDMTemplateKey)
		throws SystemException {

		List<JournalArticle> articles = journalArticlePersistence.findByG_C_T(
			groupId, classNameId, oldDDMTemplateKey);

		for (JournalArticle article : articles) {
			article.setTemplateId(newDDMTemplateKey);

			journalArticlePersistence.update(article);
		}
	}

	protected void checkStructure(Document contentDoc, Element root)
		throws PortalException {

		for (Element el : root.elements()) {
			checkStructureField(el, contentDoc);

			checkStructure(contentDoc, el);
		}
	}

	protected void checkStructure(JournalArticle article)
		throws PortalException, SystemException {

		Group companyGroup = groupLocalService.getCompanyGroup(
			article.getCompanyId());

		DDMStructure structure = null;

		try {
			structure = ddmStructurePersistence.findByG_C_S(
				article.getGroupId(),
				PortalUtil.getClassNameId(JournalArticle.class),
				article.getStructureId());
		}
		catch (NoSuchStructureException nsse) {
			structure = ddmStructurePersistence.findByG_C_S(
				companyGroup.getGroupId(),
				PortalUtil.getClassNameId(JournalArticle.class),
				article.getStructureId());
		}

		String content = GetterUtil.getString(article.getContent());

		try {
			Document contentDocument = SAXReaderUtil.read(content);
			Document xsdDocument = SAXReaderUtil.read(structure.getXsd());

			checkStructure(contentDocument, xsdDocument.getRootElement());
		}
		catch (DocumentException de) {
			throw new SystemException(de);
		}
		catch (StructureXsdException sxsde) {
			long groupId = article.getGroupId();
			String articleId = article.getArticleId();
			double version = article.getVersion();

			if (_log.isWarnEnabled()) {
				_log.warn(
					"Article {groupId=" + groupId + ", articleId=" +
						articleId + ", version=" + version +
							"} has content that does not match its " +
								"structure: " + sxsde.getMessage());
			}
		}
	}

	protected void checkStructureField(Element el, Document contentDoc)
		throws PortalException {

		StringBuilder elPath = new StringBuilder();

		elPath.append(el.attributeValue("name"));

		Element elParent = el.getParent();

		for (;;) {
			if ((elParent == null) || elParent.getName().equals("root")) {
				break;
			}

			elPath.insert(
				0, elParent.attributeValue("name") + StringPool.COMMA);

			elParent = elParent.getParent();
		}

		String[] elPathNames = StringUtil.split(elPath.toString());

		Element contentEl = contentDoc.getRootElement();

		for (String _elPathName : elPathNames) {
			boolean foundEl = false;

			for (Element tempEl : contentEl.elements()) {
				if (_elPathName.equals(
						tempEl.attributeValue("name", StringPool.BLANK))) {

					contentEl = tempEl;
					foundEl = true;

					break;
				}
			}

			if (!foundEl) {
				String elType = contentEl.attributeValue(
					"type", StringPool.BLANK);

				if (!elType.equals("list") && !elType.equals("multi-list")) {
					throw new StructureXsdException(elPath.toString());
				}

				break;
			}
		}
	}

	protected void copyArticleImages(
			JournalArticle oldArticle, JournalArticle newArticle)
		throws Exception {

		Document contentDoc = SAXReaderUtil.read(oldArticle.getContent());

		XPath xPathSelector = SAXReaderUtil.createXPath(
			"//dynamic-element[@type='image']");

		List<Node> imageNodes = xPathSelector.selectNodes(contentDoc);

		for (Node imageNode : imageNodes) {
			Element imageEl = (Element)imageNode;

			String instanceId = imageEl.attributeValue("instance-id");
			String name = imageEl.attributeValue("name");

			List<Element> dynamicContentEls = imageEl.elements(
				"dynamic-content");

			for (Element dynamicContentEl : dynamicContentEls) {
				long imageId = GetterUtil.getLong(
					dynamicContentEl.attributeValue("id"));
				String languageId = dynamicContentEl.attributeValue(
					"language-id");

				Image oldImage = null;

				try {
					oldImage = imageLocalService.getImage(imageId);
				}
				catch (NoSuchImageException nsie) {
					continue;
				}

				imageId = journalArticleImageLocalService.getArticleImageId(
					newArticle.getGroupId(), newArticle.getArticleId(),
					newArticle.getVersion(), instanceId, name, languageId);

				imageLocalService.updateImage(imageId, oldImage.getTextObj());

				String elContent =
					"/image/journal/article?img_id=" + imageId + "&t=" +
						WebServerServletTokenUtil.getToken(imageId);

				dynamicContentEl.setText(elContent);
				dynamicContentEl.addAttribute("id", String.valueOf(imageId));
			}
		}

		newArticle.setContent(contentDoc.formattedString());
	}

	protected void format(
			User user, long groupId, String articleId, double version,
			boolean incrementVersion, Element root, Map<String, byte[]> images)
		throws PortalException, SystemException {

		for (Element element : root.elements()) {
			String elInstanceId = element.attributeValue(
				"instance-id", StringPool.BLANK);
			String elName = element.attributeValue("name", StringPool.BLANK);
			String elType = element.attributeValue("type", StringPool.BLANK);

			if (elType.equals("image")) {
				formatImage(
					groupId, articleId, version, incrementVersion, element,
					elInstanceId, elName, images);
			}
			else if (elType.equals("text_area") || elType.equals("text") ||
					 elType.equals("text_box")) {

				List<Element> dynamicContentElements = element.elements(
					"dynamic-content");

				for (Element dynamicContentElement : dynamicContentElements) {
					String dynamicContent = dynamicContentElement.getText();

					if (Validator.isNotNull(dynamicContent)) {
						String contentType = ContentTypes.TEXT_PLAIN;

						if (elType.equals("text_area")) {
							contentType = ContentTypes.TEXT_HTML;
						}

						dynamicContent = SanitizerUtil.sanitize(
							user.getCompanyId(), groupId, user.getUserId(),
							JournalArticle.class.getName(), 0, contentType,
							dynamicContent);

						dynamicContentElement.clearContent();

						dynamicContentElement.addCDATA(dynamicContent);
					}
				}
			}

			format(
				user, groupId, articleId, version, incrementVersion, element,
				images);
		}
	}

	protected String format(
			User user, long groupId, String articleId, double version,
			boolean incrementVersion, String content, String ddmStructureKey,
			Map<String, byte[]> images)
		throws PortalException, SystemException {

		Document document = null;

		try {
			document = SAXReaderUtil.read(content);

			Element rootElement = document.getRootElement();

			if (Validator.isNotNull(ddmStructureKey)) {
				format(
					user, groupId, articleId, version, incrementVersion,
					rootElement, images);
			}
			else {
				List<Element> staticContentElements = rootElement.elements(
					"static-content");

				for (Element staticContentElement : staticContentElements) {
					String staticContent = staticContentElement.getText();

					staticContent = SanitizerUtil.sanitize(
						user.getCompanyId(), groupId, user.getUserId(),
						JournalArticle.class.getName(), 0,
						ContentTypes.TEXT_HTML, staticContent);

					staticContentElement.clearContent();

					staticContentElement.addCDATA(staticContent);
				}
			}

			content = DDMXMLUtil.formatXML(document);
		}
		catch (DocumentException de) {
			_log.error(de, de);
		}

		content = HtmlUtil.replaceMsWordCharacters(content);

		return content;
	}

	protected void formatImage(
			long groupId, String articleId, double version,
			boolean incrementVersion, Element el, String elInstanceId,
			String elName, Map<String, byte[]> images)
		throws PortalException, SystemException {

		List<Element> imageContents = el.elements("dynamic-content");

		for (Element dynamicContent : imageContents) {
			String elLanguage = dynamicContent.attributeValue(
				"language-id", StringPool.BLANK);

			if (!elLanguage.equals(StringPool.BLANK)) {
				elLanguage = "_" + elLanguage;
			}

			long imageId = journalArticleImageLocalService.getArticleImageId(
				groupId, articleId, version, elInstanceId, elName, elLanguage);

			double oldVersion = MathUtil.format(version - 0.1, 1, 1);

			long oldImageId = 0;

			if ((oldVersion >= 1) && incrementVersion) {
				oldImageId = journalArticleImageLocalService.getArticleImageId(
					groupId, articleId, oldVersion, elInstanceId, elName,
					elLanguage);
			}

			String elContent =
				"/image/journal/article?img_id=" + imageId + "&t=" +
					WebServerServletTokenUtil.getToken(imageId);

			if (dynamicContent.getText().equals("delete")) {
				dynamicContent.setText(StringPool.BLANK);

				imageLocalService.deleteImage(imageId);

				String defaultElLanguage = "";

				if (!Validator.isNotNull(elLanguage)) {
					defaultElLanguage =
						"_" + LocaleUtil.toLanguageId(LocaleUtil.getDefault());
				}

				long defaultImageId =
					journalArticleImageLocalService.getArticleImageId(
						groupId, articleId, version, elInstanceId, elName,
						defaultElLanguage);

				imageLocalService.deleteImage(defaultImageId);

				continue;
			}

			byte[] bytes = images.get(elInstanceId + "_" + elName + elLanguage);

			if ((bytes != null) && (bytes.length > 0)) {
				dynamicContent.setText(elContent);
				dynamicContent.addAttribute("id", String.valueOf(imageId));

				imageLocalService.updateImage(imageId, bytes);

				continue;
			}

			if ((version > JournalArticleConstants.VERSION_DEFAULT) &&
				incrementVersion) {

				Image oldImage = null;

				if (oldImageId > 0) {
					oldImage = imageLocalService.getImage(oldImageId);
				}

				if (oldImage != null) {
					dynamicContent.setText(elContent);
					dynamicContent.addAttribute("id", String.valueOf(imageId));

					bytes = oldImage.getTextObj();

					imageLocalService.updateImage(imageId, bytes);
				}

				continue;
			}

			Image image = imageLocalService.getImage(imageId);

			if (image != null) {
				dynamicContent.setText(elContent);
				dynamicContent.addAttribute("id", String.valueOf(imageId));

				continue;
			}

			long contentImageId = GetterUtil.getLong(
				HttpUtil.getParameter(dynamicContent.getText(), "img_id"));

			if (contentImageId <= 0) {
				contentImageId = GetterUtil.getLong(
					HttpUtil.getParameter(
						dynamicContent.getText(), "img_id", false));
			}

			if (contentImageId > 0) {
				image = imageLocalService.getImage(contentImageId);

				if (image != null) {
					dynamicContent.addAttribute(
						"id", String.valueOf(contentImageId));

					continue;
				}
			}

			String defaultElLanguage = "";

			if (!Validator.isNotNull(elLanguage)) {
				defaultElLanguage =
					"_" + LocaleUtil.toLanguageId(LocaleUtil.getDefault());
			}

			long defaultImageId =
				journalArticleImageLocalService.getArticleImageId(
					groupId, articleId, version, elInstanceId, elName,
					defaultElLanguage);

			Image defaultImage = imageLocalService.getImage(defaultImageId);

			if (defaultImage != null) {
				dynamicContent.setText(elContent);
				dynamicContent.addAttribute(
					"id", String.valueOf(defaultImageId));

				bytes = defaultImage.getTextObj();

				imageLocalService.updateImage(defaultImageId, bytes);

				continue;
			}

			if (Validator.isNotNull(elLanguage)) {
				dynamicContent.setText(StringPool.BLANK);
			}
		}
	}

	protected List<ObjectValuePair<Long, Integer>> getArticleVersionStatuses(
		List<JournalArticle> articles) {

		List<ObjectValuePair<Long, Integer>> dlArticleVersionStatusOVPs =
			new ArrayList<ObjectValuePair<Long, Integer>>(articles.size());

		for (JournalArticle article : articles) {
			int status = article.getStatus();

			if (status == WorkflowConstants.STATUS_PENDING) {
				status = WorkflowConstants.STATUS_DRAFT;
			}

			ObjectValuePair<Long, Integer> dlFileVersionStatusOVP =
				new ObjectValuePair<Long, Integer>(article.getId(), status);

			dlArticleVersionStatusOVPs.add(dlFileVersionStatusOVP);
		}

		return dlArticleVersionStatusOVPs;
	}

	protected long getClassTypeId(JournalArticle article) {
		long classTypeId = 0;

		try {
			long classNameId = PortalUtil.getClassNameId(JournalArticle.class);

			DDMStructure ddmStructure = ddmStructurePersistence.fetchByG_C_S(
				article.getGroupId(), classNameId, article.getStructureId());

			if (ddmStructure == null) {
				Group companyGroup = groupLocalService.getCompanyGroup(
					article.getCompanyId());

				ddmStructure = ddmStructurePersistence.fetchByG_C_S(
					companyGroup.getGroupId(), classNameId,
					article.getStructureId());
			}

			if (ddmStructure != null) {
				classTypeId = ddmStructure.getStructureId();
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return classTypeId;
	}

	protected Date[] getDateInterval(
			long groupId, String articleId, Date earliestDisplayDate,
			Date latestExpirationDate)
		throws SystemException {

		Date[] dateInterval = new Date[2];

		List<JournalArticle> articles = journalArticlePersistence.findByG_A_ST(
			groupId, articleId, WorkflowConstants.STATUS_APPROVED);

		boolean expiringArticle = true;

		if (latestExpirationDate == null) {
			expiringArticle = false;
		}

		for (JournalArticle article : articles) {
			if ((earliestDisplayDate == null) ||
				((article.getDisplayDate() != null) &&
				 earliestDisplayDate.after(article.getDisplayDate()))) {

				earliestDisplayDate = article.getDisplayDate();
			}

			if (expiringArticle &&
				((latestExpirationDate == null) ||
				 ((article.getExpirationDate() != null) &&
				  latestExpirationDate.before(article.getExpirationDate())))) {

				latestExpirationDate = article.getExpirationDate();
			}

			if (expiringArticle && (article.getExpirationDate() == null) &&
				(latestExpirationDate != null)) {

				expiringArticle = false;
			}
		}

		dateInterval[0] = earliestDisplayDate;
		dateInterval[1] = latestExpirationDate;

		return dateInterval;
	}

	protected String getExtraDataJSON(
		JournalArticle article, ServiceContext serviceContext) {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		jsonObject.put("title", article.getTitle(serviceContext.getLocale()));

		return jsonObject.toString();
	}

	protected String getUniqueUrlTitle(
			long id, long groupId, String articleId, String title)
		throws PortalException, SystemException {

		String urlTitle = JournalUtil.getUrlTitle(id, title);

		for (int i = 1;; i++) {
			JournalArticle article = null;

			try {
				article = getArticleByUrlTitle(groupId, urlTitle);
			}
			catch (NoSuchArticleException nsae) {
			}

			if ((article == null) || articleId.equals(article.getArticleId())) {
				break;
			}
			else {
				String suffix = StringPool.DASH + i;

				String prefix = urlTitle;

				if (urlTitle.length() > suffix.length()) {
					prefix = urlTitle.substring(
						0, urlTitle.length() - suffix.length());
				}

				urlTitle = prefix + suffix;
			}
		}

		return urlTitle;
	}

	protected String getUniqueUrlTitle(
			long id, String articleId, String title, String oldUrlTitle,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		String serviceContextUrlTitle = ParamUtil.getString(
			serviceContext, "urlTitle");

		String urlTitle = null;

		if (Validator.isNotNull(serviceContextUrlTitle)) {
			urlTitle = JournalUtil.getUrlTitle(id, serviceContextUrlTitle);
		}
		else if (Validator.isNotNull(oldUrlTitle)) {
			return oldUrlTitle;
		}
		else {
			urlTitle = getUniqueUrlTitle(
				id, serviceContext.getScopeGroupId(), articleId, title);
		}

		JournalArticle urlTitleArticle = null;

		try {
			urlTitleArticle = getArticleByUrlTitle(
				serviceContext.getScopeGroupId(), urlTitle);
		}
		catch (NoSuchArticleException nsae) {
		}

		if ((urlTitleArticle != null) &&
			!Validator.equals(
				urlTitleArticle.getArticleId(), articleId)) {

			urlTitle = getUniqueUrlTitle(
				id, serviceContext.getScopeGroupId(), articleId, urlTitle);
		}

		return urlTitle;
	}

	protected boolean hasModifiedLatestApprovedVersion(
			long groupId, String articleId, double version)
		throws PortalException, SystemException {

		double latestApprovedVersion;

		try {
			latestApprovedVersion = getLatestVersion(
				groupId, articleId, WorkflowConstants.STATUS_APPROVED);

			if (version >= latestApprovedVersion) {
				return true;
			}
			else {
				return false;
			}
		}
		catch (NoSuchArticleException nsae) {
			return true;
		}
	}

	protected void notifySubscribers(
			JournalArticle article, ServiceContext serviceContext)
		throws PortalException, SystemException {

		if (!article.isApproved()) {
			return;
		}

		String articleURL = PortalUtil.getControlPanelFullURL(
			serviceContext.getScopeGroupId(), PortletKeys.JOURNAL, null);

		if (Validator.isNull(articleURL)) {
			return;
		}

		PortletPreferences preferences =
			ServiceContextUtil.getPortletPreferences(serviceContext);

		if (preferences == null) {
			long ownerId = article.getGroupId();
			int ownerType = PortletKeys.PREFS_OWNER_TYPE_GROUP;
			long plid = PortletKeys.PREFS_PLID_SHARED;
			String portletId = PortletKeys.JOURNAL;
			String defaultPreferences = null;

			preferences = portletPreferencesLocalService.getPreferences(
				article.getCompanyId(), ownerId, ownerType, plid, portletId,
				defaultPreferences);
		}

		if ((article.getVersion() == 1.0) &&
			JournalUtil.getEmailArticleAddedEnabled(preferences)) {
		}
		else if ((article.getVersion() != 1.0) &&
				 JournalUtil.getEmailArticleUpdatedEnabled(preferences)) {
		}
		else {
			return;
		}

		String fromName = JournalUtil.getEmailFromName(
			preferences, article.getCompanyId());
		String fromAddress = JournalUtil.getEmailFromAddress(
			preferences, article.getCompanyId());

		String subject = null;
		String body = null;

		if (article.getVersion() == 1.0) {
			subject = JournalUtil.getEmailArticleAddedSubject(preferences);
			body = JournalUtil.getEmailArticleAddedBody(preferences);
		}
		else {
			subject = JournalUtil.getEmailArticleUpdatedSubject(preferences);
			body = JournalUtil.getEmailArticleUpdatedBody(preferences);
		}

		SubscriptionSender subscriptionSender = new SubscriptionSender();

		subscriptionSender.setBody(body);
		subscriptionSender.setCompanyId(article.getCompanyId());
		subscriptionSender.setContextAttributes(
			"[$ARTICLE_ID$]", article.getArticleId(), "[$ARTICLE_TITLE$]",
			article.getTitle(serviceContext.getLanguageId()), "[$ARTICLE_URL$]",
			articleURL, "[$ARTICLE_VERSION$]", article.getVersion());
		subscriptionSender.setContextUserPrefix("ARTICLE");
		subscriptionSender.setFrom(fromAddress, fromName);
		subscriptionSender.setHtmlFormat(true);
		subscriptionSender.setMailId("journal_article", article.getId());
		subscriptionSender.setPortletId(PortletKeys.JOURNAL);
		subscriptionSender.setReplyToAddress(fromAddress);
		subscriptionSender.setScopeGroupId(article.getGroupId());
		subscriptionSender.setServiceContext(serviceContext);
		subscriptionSender.setSubject(subject);
		subscriptionSender.setUserId(article.getUserId());

		subscriptionSender.addPersistedSubscribers(
			JournalArticle.class.getName(), article.getGroupId());

		subscriptionSender.flushNotificationsAsync();
	}

	protected void reindex(JournalArticle article) throws SearchException {
		Indexer indexer = IndexerRegistryUtil.nullSafeGetIndexer(
			JournalArticle.class);

		indexer.reindex(article);
	}

	protected void saveImages(
			boolean smallImage, long smallImageId, File smallImageFile,
			byte[] smallImageBytes)
		throws PortalException, SystemException {

		if (smallImage) {
			if ((smallImageFile != null) && (smallImageBytes != null)) {
				imageLocalService.updateImage(smallImageId, smallImageBytes);
			}
		}
		else {
			imageLocalService.deleteImage(smallImageId);
		}
	}

	protected void sendEmail(
			JournalArticle article, String articleURL,
			PortletPreferences preferences, String emailType,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		if (preferences == null) {
			return;
		}
		else if (emailType.equals("denied") &&
				 JournalUtil.getEmailArticleApprovalDeniedEnabled(
					 preferences)) {
		}
		else if (emailType.equals("granted") &&
				 JournalUtil.getEmailArticleApprovalGrantedEnabled(
					 preferences)) {
		}
		else if (emailType.equals("requested") &&
				 JournalUtil.getEmailArticleApprovalRequestedEnabled(
					 preferences)) {
		}
		else if (emailType.equals("review") &&
				 JournalUtil.getEmailArticleReviewEnabled(preferences)) {
		}
		else {
			return;
		}

		Company company = companyPersistence.findByPrimaryKey(
			article.getCompanyId());

		User user = userPersistence.findByPrimaryKey(article.getUserId());

		articleURL +=
			"&groupId=" + article.getGroupId() + "&articleId=" +
				article.getArticleId() + "&version=" + article.getVersion();

		String fromName = JournalUtil.getEmailFromName(
			preferences, article.getCompanyId());
		String fromAddress = JournalUtil.getEmailFromAddress(
			preferences, article.getCompanyId());

		String toName = user.getFullName();
		String toAddress = user.getEmailAddress();

		if (emailType.equals("requested") || emailType.equals("review")) {
			String tempToName = fromName;
			String tempToAddress = fromAddress;

			fromName = toName;
			fromAddress = toAddress;

			toName = tempToName;
			toAddress = tempToAddress;
		}

		String subject = null;
		String body = null;

		if (emailType.equals("denied")) {
			subject = JournalUtil.getEmailArticleApprovalDeniedSubject(
				preferences);
			body = JournalUtil.getEmailArticleApprovalDeniedBody(preferences);
		}
		else if (emailType.equals("granted")) {
			subject = JournalUtil.getEmailArticleApprovalGrantedSubject(
				preferences);
			body = JournalUtil.getEmailArticleApprovalGrantedBody(preferences);
		}
		else if (emailType.equals("requested")) {
			subject = JournalUtil.getEmailArticleApprovalRequestedSubject(
				preferences);
			body = JournalUtil.getEmailArticleApprovalRequestedBody(
				preferences);
		}
		else if (emailType.equals("review")) {
			subject = JournalUtil.getEmailArticleReviewSubject(preferences);
			body = JournalUtil.getEmailArticleReviewBody(preferences);
		}

		SubscriptionSender subscriptionSender = new SubscriptionSender();

		subscriptionSender.setBody(body);
		subscriptionSender.setCompanyId(company.getCompanyId());
		subscriptionSender.setContextAttributes(
			"[$ARTICLE_ID$]", article.getArticleId(), "[$ARTICLE_TITLE$]",
			article.getTitle(serviceContext.getLanguageId()), "[$ARTICLE_URL$]",
			articleURL, "[$ARTICLE_USER_NAME$]", article.getUserName(),
			"[$ARTICLE_VERSION$]", article.getVersion());
		subscriptionSender.setContextUserPrefix("ARTICLE");
		subscriptionSender.setFrom(fromAddress, fromName);
		subscriptionSender.setHtmlFormat(true);
		subscriptionSender.setMailId("journal_article", article.getId());
		subscriptionSender.setPortletId(PortletKeys.JOURNAL);
		subscriptionSender.setScopeGroupId(article.getGroupId());
		subscriptionSender.setServiceContext(serviceContext);
		subscriptionSender.setSubject(subject);
		subscriptionSender.setUserId(article.getUserId());

		subscriptionSender.addRuntimeSubscribers(toAddress, toName);

		subscriptionSender.flushNotificationsAsync();
	}

	protected void updateDDMStructureXSD(
			long ddmStructureId, String content, ServiceContext serviceContext)
		throws PortalException, SystemException {

		try {
			Document document = SAXReaderUtil.read(content);

			Element rootElement = document.getRootElement();

			List<Element> elements = rootElement.elements();

			for (Element element : elements) {
				String fieldName = element.attributeValue(
					"name", StringPool.BLANK);

				List<Element> dynamicContentElements = element.elements(
					"dynamic-content");

				for (Element dynamicContentElement : dynamicContentElements) {
					String value = dynamicContentElement.getText();

					ddmStructureLocalService.updateXSDFieldMetadata(
						ddmStructureId, fieldName,
						FieldConstants.PREDEFINED_VALUE, value, serviceContext);
				}
			}
		}
		catch (DocumentException de) {
			throw new SystemException(de);
		}
	}

	protected void updatePreviousApprovedArticle(JournalArticle article)
		throws PortalException, SystemException {

		List<JournalArticle> approvedArticles =
			journalArticlePersistence.findByG_A_ST(
				article.getGroupId(), article.getArticleId(),
				WorkflowConstants.STATUS_APPROVED, 0, 2);

		if (approvedArticles.isEmpty() ||
			((approvedArticles.size() == 1) &&
			 (article.getStatus() == WorkflowConstants.STATUS_APPROVED))) {

			if (article.isIndexable()) {
				Indexer indexer = IndexerRegistryUtil.nullSafeGetIndexer(
					JournalArticle.class);

				indexer.delete(article);
			}

			assetEntryLocalService.updateVisible(
				JournalArticle.class.getName(), article.getResourcePrimKey(),
				false);
		}
		else {
			JournalArticle previousApprovedArticle = approvedArticles.get(0);

			if (article.getStatus() == WorkflowConstants.STATUS_APPROVED) {
				previousApprovedArticle = approvedArticles.get(1);
			}

			Date[] dateInterval = getDateInterval(
				previousApprovedArticle.getGroupId(),
				previousApprovedArticle.getArticleId(),
				previousApprovedArticle.getDisplayDate(),
				previousApprovedArticle.getExpirationDate());

			Date displayDate = dateInterval[0];
			Date expirationDate = dateInterval[1];

			AssetEntry assetEntry = assetEntryLocalService.updateEntry(
				JournalArticle.class.getName(), article.getResourcePrimKey(),
				displayDate, expirationDate, true);

			assetEntry.setModifiedDate(
				previousApprovedArticle.getModifiedDate());

			assetEntryPersistence.update(assetEntry);

			if (article.isIndexable()) {
				reindex(previousApprovedArticle);
			}
		}
	}

	protected void updateUrlTitles(
			long groupId, String articleId, String urlTitle)
		throws SystemException {

		List<JournalArticle> articles = journalArticlePersistence.findByG_A(
			groupId, articleId);

		for (JournalArticle article : articles) {
			if (!article.getUrlTitle().equals(urlTitle)) {
				article.setUrlTitle(urlTitle);

				journalArticlePersistence.update(article);
			}
		}
	}

	protected void validate(
			long companyId, long groupId, long classNameId,
			Map<Locale, String> titleMap, String content, String type,
			String ddmStructureKey, String ddmTemplateKey, Date expirationDate,
			boolean smallImage, String smallImageURL, File smallImageFile,
			byte[] smallImageBytes)
		throws PortalException, SystemException {

		Locale articleDefaultLocale = LocaleUtil.fromLanguageId(
			LocalizationUtil.getDefaultLocale(content));

		Locale[] availableLocales = LanguageUtil.getAvailableLocales();

		if (!ArrayUtil.contains(availableLocales, articleDefaultLocale)) {
			LocaleException le = new LocaleException(
				"The locale " + articleDefaultLocale +
					" is not available in company " + companyId);

			Locale[] sourceAvailableLocales = {articleDefaultLocale};

			le.setSourceAvailableLocales(sourceAvailableLocales);
			le.setTargetAvailableLocales(availableLocales);

			throw le;
		}

		if ((classNameId == JournalArticleConstants.CLASSNAME_ID_DEFAULT) &&
			(titleMap.isEmpty() ||
			 Validator.isNull(titleMap.get(articleDefaultLocale)))) {

			throw new ArticleTitleException();
		}
		else if (Validator.isNull(type)) {
			throw new ArticleTypeException();
		}

		validateContent(content);

		if (Validator.isNotNull(ddmStructureKey)) {
			Group companyGroup = groupLocalService.getCompanyGroup(companyId);

			DDMStructure ddmStructure = null;

			try {
				ddmStructure = ddmStructurePersistence.findByG_C_S(
					groupId, PortalUtil.getClassNameId(JournalArticle.class),
					ddmStructureKey);
			}
			catch (NoSuchStructureException nsse) {
				ddmStructure = ddmStructurePersistence.findByG_C_S(
					companyGroup.getGroupId(),
					PortalUtil.getClassNameId(JournalArticle.class),
					ddmStructureKey);
			}

			DDMTemplate ddmTemplate = null;

			if (Validator.isNotNull(ddmTemplateKey)) {
				try {
					ddmTemplate = ddmTemplatePersistence.findByG_C_T(
						groupId, PortalUtil.getClassNameId(DDMStructure.class),
						ddmTemplateKey);
				}
				catch (NoSuchTemplateException nste) {
					ddmTemplate = ddmTemplatePersistence.findByG_C_T(
						companyGroup.getGroupId(),
						PortalUtil.getClassNameId(DDMStructure.class),
						ddmTemplateKey);
				}

				if (ddmTemplate.getClassPK() != ddmStructure.getStructureId()) {
					throw new NoSuchTemplateException();
				}
			}
			else if (classNameId ==
						JournalArticleConstants.CLASSNAME_ID_DEFAULT) {

				throw new NoSuchTemplateException();
			}
		}

		if ((expirationDate != null) && expirationDate.before(new Date()) &&
			!ExportImportThreadLocal.isImportInProcess()) {

			throw new ArticleExpirationDateException();
		}

		String[] imageExtensions = PrefsPropsUtil.getStringArray(
			PropsKeys.JOURNAL_IMAGE_EXTENSIONS, StringPool.COMMA);

		if (smallImage && Validator.isNull(smallImageURL) &&
			(smallImageFile != null) && (smallImageBytes != null)) {

			String smallImageName = smallImageFile.getName();

			if (smallImageName != null) {
				boolean validSmallImageExtension = false;

				for (String _imageExtension : imageExtensions) {
					if (StringPool.STAR.equals(_imageExtension) ||
						StringUtil.endsWith(smallImageName, _imageExtension)) {

						validSmallImageExtension = true;

						break;
					}
				}

				if (!validSmallImageExtension) {
					throw new ArticleSmallImageNameException(smallImageName);
				}
			}

			long smallImageMaxSize = PrefsPropsUtil.getLong(
				PropsKeys.JOURNAL_IMAGE_SMALL_MAX_SIZE);

			if ((smallImageMaxSize > 0) &&
				((smallImageBytes == null) ||
				 (smallImageBytes.length > smallImageMaxSize))) {

				throw new ArticleSmallImageSizeException();
			}
		}
	}

	protected void validate(
			long companyId, long groupId, long classNameId, String articleId,
			boolean autoArticleId, double version, Map<Locale, String> titleMap,
			String content, String type, String ddmStructureKey,
			String ddmTemplateKey, Date expirationDate, boolean smallImage,
			String smallImageURL, File smallImageFile, byte[] smallImageBytes)
		throws PortalException, SystemException {

		if (!autoArticleId) {
			validate(articleId);
		}

		JournalArticle article = journalArticlePersistence.fetchByG_A_V(
			groupId, articleId, version);

		if (article != null) {
			throw new DuplicateArticleIdException();
		}

		validate(
			companyId, groupId, classNameId, titleMap, content, type,
			ddmStructureKey, ddmTemplateKey, expirationDate, smallImage,
			smallImageURL, smallImageFile, smallImageBytes);
	}

	protected void validate(String articleId) throws PortalException {
		if (Validator.isNull(articleId) ||
			(articleId.indexOf(CharPool.SPACE) != -1)) {

			throw new ArticleIdException();
		}
	}

	protected void validateContent(String content) throws PortalException {
		if (Validator.isNull(content)) {
			throw new ArticleContentException("Content is null");
		}

		try {
			SAXReaderUtil.read(content);
		}
		catch (DocumentException de) {
			if (_log.isDebugEnabled()) {
				_log.debug("Invalid content:\n" + content);
			}

			throw new ArticleContentException(
				"Unable to read content with an XML parser", de);
		}
	}

	private static final long _JOURNAL_ARTICLE_CHECK_INTERVAL =
		PropsValues.JOURNAL_ARTICLE_CHECK_INTERVAL * Time.MINUTE;

	private static Log _log = LogFactoryUtil.getLog(
		JournalArticleLocalServiceImpl.class);

	private Date _previousCheckDate;

}