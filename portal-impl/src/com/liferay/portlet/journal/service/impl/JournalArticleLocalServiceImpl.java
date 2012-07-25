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

package com.liferay.portlet.journal.service.impl;

import com.liferay.portal.LocaleException;
import com.liferay.portal.NoSuchImageException;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.sanitizer.SanitizerUtil;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.QueryConfig;
import com.liferay.portal.kernel.search.SearchContext;
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
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Time;
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
import com.liferay.portlet.dynamicdatamapping.util.DDMXMLUtil;
import com.liferay.portlet.expando.model.ExpandoBridge;
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
import com.liferay.portlet.journal.NoSuchStructureException;
import com.liferay.portlet.journal.NoSuchTemplateException;
import com.liferay.portlet.journal.StructureXsdException;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.model.JournalArticleConstants;
import com.liferay.portlet.journal.model.JournalArticleDisplay;
import com.liferay.portlet.journal.model.JournalArticleResource;
import com.liferay.portlet.journal.model.JournalStructure;
import com.liferay.portlet.journal.model.JournalTemplate;
import com.liferay.portlet.journal.model.impl.JournalArticleDisplayImpl;
import com.liferay.portlet.journal.service.base.JournalArticleLocalServiceBaseImpl;
import com.liferay.portlet.journal.util.JournalUtil;
import com.liferay.portlet.journal.util.comparator.ArticleIDComparator;
import com.liferay.portlet.journal.util.comparator.ArticleVersionComparator;
import com.liferay.portlet.journalcontent.util.JournalContentUtil;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.portlet.PortletPreferences;

/**
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 * @author Bruno Farache
 * @author Juan Fernández
 */
public class JournalArticleLocalServiceImpl
	extends JournalArticleLocalServiceBaseImpl {

	public JournalArticle addArticle(
			long userId, long groupId, long folderId, long classNameId,
			long classPK, String articleId, boolean autoArticleId,
			double version, Map<Locale, String> titleMap,
			Map<Locale, String> descriptionMap, String content, String type,
			String structureId, String templateId, String layoutUuid,
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

		Date displayDate = PortalUtil.getDate(
			displayDateMonth, displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, user.getTimeZone(),
			ArticleDisplayDateException.class);

		Date expirationDate = null;

		if (!neverExpire) {
			expirationDate = PortalUtil.getDate(
				expirationDateMonth, expirationDateDay, expirationDateYear,
				expirationDateHour, expirationDateMinute, user.getTimeZone(),
				ArticleExpirationDateException.class);
		}

		Date reviewDate = null;

		if (!neverReview) {
			reviewDate = PortalUtil.getDate(
				reviewDateMonth, reviewDateDay, reviewDateYear, reviewDateHour,
				reviewDateMinute, user.getTimeZone(),
				ArticleReviewDateException.class);
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
			version, titleMap, content, type, structureId, templateId,
			smallImage, smallImageURL, smallImageFile, smallImageBytes);

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
			user, groupId, articleId, version, false, content, structureId,
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
		article.setStructureId(structureId);
		article.setTemplateId(templateId);
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

		journalArticlePersistence.update(article, false);

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

		// Expando

		ExpandoBridge expandoBridge = article.getExpandoBridge();

		expandoBridge.setAttributes(serviceContext);

		// Small image

		saveImages(
			smallImage, article.getSmallImageId(), smallImageFile,
			smallImageBytes);

		// Asset

		updateAsset(
			userId, article, serviceContext.getAssetCategoryIds(),
			serviceContext.getAssetTagNames(),
			serviceContext.getAssetLinkEntryIds());

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

		if (classNameId == 0) {
			WorkflowHandlerRegistryUtil.startWorkflowInstance(
				user.getCompanyId(), groupId, userId,
				JournalArticle.class.getName(), article.getId(), article,
				serviceContext);

			if (serviceContext.getWorkflowAction() !=
					WorkflowConstants.ACTION_PUBLISH) {

				// Indexer

				Indexer indexer = IndexerRegistryUtil.nullSafeGetIndexer(
					JournalArticle.class);

				indexer.reindex(article);
			}
		}
		else {
			updateStatus(
				userId, article, WorkflowConstants.STATUS_APPROVED, null,
				serviceContext);
		}

		return article;
	}

	public void addArticleResources(
			JournalArticle article, boolean addGroupPermissions,
			boolean addGuestPermissions)
		throws PortalException, SystemException {

		resourceLocalService.addResources(
			article.getCompanyId(), article.getGroupId(), article.getUserId(),
			JournalArticle.class.getName(), article.getResourcePrimKey(), false,
			addGroupPermissions, addGuestPermissions);
	}

	public void addArticleResources(
			JournalArticle article, String[] groupPermissions,
			String[] guestPermissions)
		throws PortalException, SystemException {

		resourceLocalService.addModelResources(
			article.getCompanyId(), article.getGroupId(), article.getUserId(),
			JournalArticle.class.getName(), article.getResourcePrimKey(),
			groupPermissions, guestPermissions);
	}

	public void addArticleResources(
			long groupId, String articleId, boolean addGroupPermissions,
			boolean addGuestPermissions)
		throws PortalException, SystemException {

		JournalArticle article = getLatestArticle(groupId, articleId);

		addArticleResources(article, addGroupPermissions, addGuestPermissions);
	}

	public void addArticleResources(
			long groupId, String articleId, String[] groupPermissions,
			String[] guestPermissions)
		throws PortalException, SystemException {

		JournalArticle article = getLatestArticle(groupId, articleId);

		addArticleResources(article, groupPermissions, guestPermissions);
	}

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

		journalArticlePersistence.update(article, false);

		return article;
	}

	public void checkArticles() throws PortalException, SystemException {
		Date now = new Date();

		List<JournalArticle> articles =
			journalArticleFinder.findByExpirationDate(
				0, WorkflowConstants.STATUS_APPROVED,
				new Date(now.getTime() + _JOURNAL_ARTICLE_CHECK_INTERVAL));

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

					journalArticlePersistence.update(currentArticle, false);
				}
			}
			else {
				article.setStatus(WorkflowConstants.STATUS_EXPIRED);

				journalArticlePersistence.update(article, false);
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

		articles = journalArticleFinder.findByReviewDate(
			0, now, new Date(now.getTime() - _JOURNAL_ARTICLE_CHECK_INTERVAL));

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
	}

	public void checkNewLine(long groupId, String articleId, double version)
		throws PortalException, SystemException {

		JournalArticle article = journalArticlePersistence.findByG_A_V(
			groupId, articleId, version);

		String content = GetterUtil.getString(article.getContent());

		if (content.indexOf("\\n") != -1) {
			content = StringUtil.replace(
				content, new String[] {"\\n", "\\r"},
				new String[] {"\n", "\r"});

			article.setContent(content);

			journalArticlePersistence.update(article, false);
		}
	}

	public void checkStructure(long groupId, String articleId, double version)
		throws PortalException, SystemException {

		JournalArticle article = journalArticlePersistence.findByG_A_V(
			groupId, articleId, version);

		if (Validator.isNull(article.getStructureId())) {
			return;
		}

		try {
			checkStructure(article);
		}
		catch (DocumentException de) {
			_log.error(de, de);
		}
	}

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
			validate(groupId, newArticleId);
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
		newArticle.setStatus(oldArticle.getStatus());

		journalArticlePersistence.update(newArticle, false);

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

	public void deleteArticle(JournalArticle article)
		throws PortalException, SystemException {

		deleteArticle(article, StringPool.BLANK, null);
	}

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

	public void deleteArticle(
			long groupId, String articleId, double version, String articleURL,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		JournalArticle article = journalArticlePersistence.findByG_A_V(
			groupId, articleId, version);

		deleteArticle(article, articleURL, serviceContext);
	}

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

	public void deleteArticles(long groupId)
		throws PortalException, SystemException {

		for (JournalArticle article :
				journalArticlePersistence.findByGroupId(groupId)) {

			deleteArticle(article, null, null);
		}
	}

	public void deleteArticles(long groupId, long folderId)
		throws PortalException, SystemException {

		for (JournalArticle article :
			journalArticlePersistence.findByG_F(groupId, folderId)) {

			deleteArticle(article, null, null);
		}
	}

	public void deleteLayoutArticleReferences(long groupId, String layoutUuid)
		throws SystemException {

		List<JournalArticle> articles = journalArticlePersistence.findByG_L(
			groupId, layoutUuid);

		for (JournalArticle article : articles) {
			article.setLayoutUuid(StringPool.BLANK);

			journalArticlePersistence.update(article, false);
		}
	}

	public JournalArticle expireArticle(
			long userId, long groupId, String articleId, double version,
			String articleURL, ServiceContext serviceContext)
		throws PortalException, SystemException {

		return updateStatus(
			userId, groupId, articleId, version,
			WorkflowConstants.STATUS_EXPIRED, articleURL, serviceContext);
	}

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

	public JournalArticle getArticle(long id)
		throws PortalException, SystemException {

		return journalArticlePersistence.findByPrimaryKey(id);
	}

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

	public JournalArticle getArticle(
			long groupId, String articleId, double version)
		throws PortalException, SystemException {

		return journalArticlePersistence.findByG_A_V(
			groupId, articleId, version);
	}

	public JournalArticle getArticle(
			long groupId, String className, long classPK)
		throws PortalException, SystemException {

		long classNameId = PortalUtil.getClassNameId(className);

		List<JournalArticle> articles = journalArticlePersistence.findByG_C_C(
			groupId, classNameId, classPK);

		if (articles.isEmpty()) {
			throw new NoSuchArticleException(
				"No approved JournalArticle exists with the key {groupId="
					+ groupId + ", className=" + className + ", classPK=" +
						classPK + "}");
		}

		return articles.get(0);
	}

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

	public String getArticleContent(
			JournalArticle article, String templateId, String viewMode,
			String languageId, ThemeDisplay themeDisplay)
		throws PortalException, SystemException {

		JournalArticleDisplay articleDisplay = getArticleDisplay(
			article, templateId, viewMode, languageId, 1, null, themeDisplay);

		if (articleDisplay == null) {
			return StringPool.BLANK;
		}
		else {
			return articleDisplay.getContent();
		}
	}

	public String getArticleContent(
			long groupId, String articleId, double version, String viewMode,
			String templateId, String languageId, ThemeDisplay themeDisplay)
		throws PortalException, SystemException {

		JournalArticleDisplay articleDisplay = getArticleDisplay(
			groupId, articleId, version, templateId, viewMode, languageId,
			themeDisplay);

		if (articleDisplay == null) {
			return StringPool.BLANK;
		}
		else {
			return articleDisplay.getContent();
		}
	}

	public String getArticleContent(
			long groupId, String articleId, double version, String viewMode,
			String languageId, ThemeDisplay themeDisplay)
		throws PortalException, SystemException {

		return getArticleContent(
			groupId, articleId, version, viewMode, null, languageId,
			themeDisplay);
	}

	public String getArticleContent(
			long groupId, String articleId, String viewMode, String templateId,
			String languageId, ThemeDisplay themeDisplay)
		throws PortalException, SystemException {

		JournalArticleDisplay articleDisplay = getArticleDisplay(
			groupId, articleId, templateId, viewMode, languageId, themeDisplay);

		return articleDisplay.getContent();
	}

	public String getArticleContent(
			long groupId, String articleId, String viewMode, String languageId,
			ThemeDisplay themeDisplay)
		throws PortalException, SystemException {

		return getArticleContent(
			groupId, articleId, viewMode, null, languageId, themeDisplay);
	}

	public JournalArticleDisplay getArticleDisplay(
			JournalArticle article, String templateId, String viewMode,
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

		String defaultTemplateId = article.getTemplateId();

		if (article.isTemplateDriven()) {
			if (Validator.isNull(templateId)) {
				templateId = defaultTemplateId;
			}

			tokens.put("structure_id", article.getStructureId());
			tokens.put("template_id", templateId);
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
						XPath xPathSelector = SAXReaderUtil.createXPath(
							"/root/page[@id = '" + targetPage + "']");

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
					rootElement, tokens, article, languageId);

				xml = DDMXMLUtil.formatXML(document);
			}
		}
		catch (DocumentException de) {
			throw new SystemException(de);
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
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
				// default one. If the specified template does not exit, use the
				// default one. If the default one does not exist, throw an
				// exception.

				JournalTemplate template = null;

				try {
					template = journalTemplatePersistence.findByG_T(
						article.getGroupId(), templateId);
				}
				catch (NoSuchTemplateException nste1) {
					try {
						Group companyGroup = groupLocalService.getCompanyGroup(
							article.getCompanyId());

						template = journalTemplatePersistence.findByG_T(
							companyGroup.getGroupId(), templateId);

						tokens.put(
							"company_group_id",
							String.valueOf(companyGroup.getGroupId()));
					}
					catch (NoSuchTemplateException nste2) {
						if (!defaultTemplateId.equals(templateId)) {
							template = journalTemplatePersistence.findByG_T(
								article.getGroupId(), defaultTemplateId);
						}
						else {
							throw nste1;
						}
					}
				}

				script = template.getXsl();
				langType = template.getLangType();
				cacheable = template.isCacheable();
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
			content, article.getType(), article.getStructureId(), templateId,
			article.isSmallImage(), article.getSmallImageId(),
			article.getSmallImageURL(), numberOfPages, page, paginate,
			cacheable);
	}

	public JournalArticleDisplay getArticleDisplay(
			long groupId, String articleId, double version, String templateId,
			String viewMode, String languageId, int page, String xmlRequest,
			ThemeDisplay themeDisplay)
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
			article, templateId, viewMode, languageId, page, xmlRequest,
			themeDisplay);
	}

	public JournalArticleDisplay getArticleDisplay(
			long groupId, String articleId, double version, String templateId,
			String viewMode, String languageId, ThemeDisplay themeDisplay)
		throws PortalException, SystemException {

		return getArticleDisplay(
			groupId, articleId, version, templateId, viewMode, languageId, 1,
			null, themeDisplay);
	}

	public JournalArticleDisplay getArticleDisplay(
			long groupId, String articleId, String viewMode, String languageId,
			int page, String xmlRequest, ThemeDisplay themeDisplay)
		throws PortalException, SystemException {

		return getArticleDisplay(
			groupId, articleId, null, viewMode, languageId, page, xmlRequest,
			themeDisplay);
	}

	public JournalArticleDisplay getArticleDisplay(
			long groupId, String articleId, String templateId, String viewMode,
			String languageId, int page, String xmlRequest,
			ThemeDisplay themeDisplay)
		throws PortalException, SystemException {

		JournalArticle article = getDisplayArticle(groupId, articleId);

		return getArticleDisplay(
			groupId, articleId, article.getVersion(), templateId, viewMode,
			languageId, page, xmlRequest, themeDisplay);
	}

	public JournalArticleDisplay getArticleDisplay(
			long groupId, String articleId, String templateId, String viewMode,
			String languageId, ThemeDisplay themeDisplay)
		throws PortalException, SystemException {

		JournalArticle article = getDisplayArticle(groupId, articleId);

		return getArticleDisplay(
			groupId, articleId, article.getVersion(), templateId, viewMode,
			languageId, themeDisplay);
	}

	public JournalArticleDisplay getArticleDisplay(
			long groupId, String articleId, String viewMode, String languageId,
			ThemeDisplay themeDisplay)
		throws PortalException, SystemException {

		return getArticleDisplay(
			groupId, articleId, null, viewMode, languageId, themeDisplay);
	}

	public List<JournalArticle> getArticles() throws SystemException {
		return journalArticlePersistence.findAll();
	}

	public List<JournalArticle> getArticles(long groupId)
		throws SystemException {

		return journalArticlePersistence.findByGroupId(groupId);
	}

	public List<JournalArticle> getArticles(long groupId, int start, int end)
		throws SystemException {

		return journalArticlePersistence.findByGroupId(groupId, start, end);
	}

	public List<JournalArticle> getArticles(
			long groupId, int start, int end, OrderByComparator obc)
		throws SystemException {

		return journalArticlePersistence.findByGroupId(
			groupId, start, end, obc);
	}

	public List<JournalArticle> getArticles(long groupId, long folderId)
		throws SystemException {

		return journalArticlePersistence.findByG_F(groupId, folderId);
	}

	public List<JournalArticle> getArticles(
			long groupId, long folderId, int start, int end)
		throws SystemException {

		return journalArticlePersistence.findByG_F(
			groupId, folderId, start, end);
	}

	public List<JournalArticle> getArticles(
			long groupId, long folderId, int start, int end,
			OrderByComparator orderByComparator)
		throws SystemException {

		return journalArticlePersistence.findByG_F(
			groupId, folderId, start, end, orderByComparator);
	}

	public List<JournalArticle> getArticles(long groupId, String articleId)
		throws SystemException {

		return journalArticlePersistence.findByG_A(groupId, articleId);
	}

	public List<JournalArticle> getArticlesBySmallImageId(long smallImageId)
		throws SystemException {

		return journalArticlePersistence.findBySmallImageId(smallImageId);
	}

	public int getArticlesCount(long groupId) throws SystemException {
		return journalArticlePersistence.countByGroupId(groupId);
	}

	public int getArticlesCount(long groupId, long folderId)
		throws SystemException {

		return journalArticlePersistence.countByG_F(groupId, folderId);
	}

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

	public int getCompanyArticlesCount(long companyId, int status)
		throws SystemException {

		if (status == WorkflowConstants.STATUS_ANY) {
			return journalArticlePersistence.countByCompanyId(companyId);
		}
		else {
			return journalArticlePersistence.countByC_ST(companyId, status);
		}
	}

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

	public JournalArticle getLatestArticle(long resourcePrimKey)
		throws PortalException, SystemException {

		return getLatestArticle(resourcePrimKey, WorkflowConstants.STATUS_ANY);
	}

	public JournalArticle getLatestArticle(long resourcePrimKey, int status)
		throws PortalException, SystemException {

		return getLatestArticle(resourcePrimKey, status, true);
	}

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

	public JournalArticle getLatestArticle(long groupId, String articleId)
		throws PortalException, SystemException {

		return getLatestArticle(
			groupId, articleId, WorkflowConstants.STATUS_ANY);
	}

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

	public double getLatestVersion(long groupId, String articleId)
		throws PortalException, SystemException {

		JournalArticle article = getLatestArticle(groupId, articleId);

		return article.getVersion();
	}

	public double getLatestVersion(long groupId, String articleId, int status)
		throws PortalException, SystemException {

		JournalArticle article = getLatestArticle(groupId, articleId, status);

		return article.getVersion();
	}

	public List<JournalArticle> getStructureArticles(
			long groupId, String structureId)
		throws SystemException {

		return journalArticlePersistence.findByG_S(groupId, structureId);
	}

	public List<JournalArticle> getStructureArticles(
			long groupId, String structureId, int start, int end,
			OrderByComparator obc)
		throws SystemException {

		return journalArticlePersistence.findByG_S(
			groupId, structureId, start, end, obc);
	}

	public int getStructureArticlesCount(long groupId, String structureId)
		throws SystemException {

		return journalArticlePersistence.countByG_S(groupId, structureId);
	}

	public List<JournalArticle> getTemplateArticles(
			long groupId, String templateId)
		throws SystemException {

		return journalArticlePersistence.findByG_T(groupId, templateId);
	}

	public List<JournalArticle> getTemplateArticles(
			long groupId, String templateId, int start, int end,
			OrderByComparator obc)
		throws SystemException {

		return journalArticlePersistence.findByG_T(
			groupId, templateId, start, end, obc);
	}

	public int getTemplateArticlesCount(long groupId, String templateId)
		throws SystemException {

		return journalArticlePersistence.countByG_T(groupId, templateId);
	}

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

		journalArticlePersistence.update(article, false);

		return article;
	}

	public List<JournalArticle> search(
			long companyId, long groupId, long folderId, long classNameId,
			String keywords, Double version, String type, String structureId,
			String templateId, Date displayDateGT, Date displayDateLT,
			int status, Date reviewDate, int start, int end,
			OrderByComparator obc)
		throws SystemException {

		return journalArticleFinder.findByKeywords(
			companyId, groupId, folderId, classNameId, keywords, version, type,
			structureId, templateId, displayDateGT, displayDateLT, status,
			reviewDate, start, end, obc);
	}

	public List<JournalArticle> search(
			long companyId, long groupId, long folderId, long classNameId,
			String articleId, Double version, String title, String description,
			String content, String type, String structureId, String templateId,
			Date displayDateGT, Date displayDateLT, int status, Date reviewDate,
			boolean andOperator, int start, int end, OrderByComparator obc)
		throws SystemException {

		return journalArticleFinder.findByC_G_F_C_A_V_T_D_C_T_S_T_D_S_R(
			companyId, groupId, folderId, classNameId, articleId, version,
			title, description, content, type, structureId, templateId,
			displayDateGT, displayDateLT, status, reviewDate, andOperator,
			start, end, obc);
	}

	public List<JournalArticle> search(
			long companyId, long groupId, long folderId, long classNameId,
			String articleId, Double version, String title, String description,
			String content, String type, String[] structureIds,
			String[] templateIds, Date displayDateGT, Date displayDateLT,
			int status, Date reviewDate, boolean andOperator, int start,
			int end, OrderByComparator obc)
		throws SystemException {

		return journalArticleFinder.findByC_G_F_C_A_V_T_D_C_T_S_T_D_S_R(
			companyId, groupId, folderId, classNameId, articleId, version,
			title, description, content, type, structureIds, templateIds,
			displayDateGT, displayDateLT, status, reviewDate, andOperator,
			start, end, obc);
	}

	public Hits search(
			long companyId, long groupId, long folderId, long classNameId,
			String structureId, String templateId, String keywords,
			LinkedHashMap<String, Object> params, int start, int end, Sort sort)
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
			companyId, groupId, folderId, classNameId, articleId, title,
			description, content, null, status, structureId, templateId, params,
			andOperator, start, end, sort);
	}

	public Hits search(
			long companyId, long groupId, long folderId, long classNameId,
			String articleId, String title, String description, String content,
			String type, String status, String structureId, String templateId,
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
			attributes.put("folderId", folderId);
			attributes.put("params", params);
			attributes.put("structureId", structureId);
			attributes.put("templateId", templateId);

			searchContext.setAttributes(attributes);

			searchContext.setCompanyId(companyId);
			searchContext.setEnd(end);
			searchContext.setGroupIds(new long[] {groupId});

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

			searchContext.setSorts(new Sort[] {sort});
			searchContext.setStart(start);

			Indexer indexer = IndexerRegistryUtil.nullSafeGetIndexer(
				JournalArticle.class);

			return indexer.search(searchContext);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	public int searchCount(
			long companyId, long groupId, long folderId, long classNameId,
			String keywords, Double version, String type, String structureId,
			String templateId, Date displayDateGT, Date displayDateLT,
			int status, Date reviewDate)
		throws SystemException {

		return journalArticleFinder.countByKeywords(
			companyId, groupId, folderId, classNameId, keywords, version, type,
			structureId, templateId, displayDateGT, displayDateLT, status,
			reviewDate);
	}

	public int searchCount(
			long companyId, long groupId, long folderId, long classNameId,
			String articleId, Double version, String title, String description,
			String content, String type, String structureId, String templateId,
			Date displayDateGT, Date displayDateLT, int status, Date reviewDate,
			boolean andOperator)
		throws SystemException {

		return journalArticleFinder.countByC_G_F_C_A_V_T_D_C_T_S_T_D_S_R(
			companyId, groupId, folderId, classNameId, articleId, version,
			title, description, content, type, structureId, templateId,
			displayDateGT, displayDateLT, status, reviewDate, andOperator);
	}

	public int searchCount(
			long companyId, long groupId, long folderId, long classNameId,
			String articleId, Double version, String title, String description,
			String content, String type, String[] structureIds,
			String[] templateIds, Date displayDateGT, Date displayDateLT,
			int status, Date reviewDate, boolean andOperator)
		throws SystemException {

		return journalArticleFinder.countByC_G_F_C_A_V_T_D_C_T_S_T_D_S_R(
			companyId, groupId, folderId, classNameId, articleId, version,
			title, description, content, type, structureIds, templateIds,
			displayDateGT, displayDateLT, status, reviewDate, andOperator);
	}

	public void subscribe(long userId, long groupId)
		throws PortalException, SystemException {

		subscriptionLocalService.addSubscription(
			userId, groupId, JournalArticle.class.getName(), groupId);
	}

	public void unsubscribe(long userId, long groupId)
		throws PortalException, SystemException {

		subscriptionLocalService.deleteSubscription(
			userId, JournalArticle.class.getName(), groupId);
	}

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

	public JournalArticle updateArticle(
			long userId, long groupId, long folderId, String articleId,
			double version, Map<Locale, String> titleMap,
			Map<Locale, String> descriptionMap, String content, String type,
			String structureId, String templateId, String layoutUuid,
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

		Date displayDate = PortalUtil.getDate(
			displayDateMonth, displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, user.getTimeZone(),
			ArticleDisplayDateException.class);

		Date expirationDate = null;

		if (!neverExpire) {
			expirationDate = PortalUtil.getDate(
				expirationDateMonth, expirationDateDay, expirationDateYear,
				expirationDateHour, expirationDateMinute, user.getTimeZone(),
				ArticleExpirationDateException.class);
		}

		Date now = new Date();

		boolean expired = false;

		if ((expirationDate != null) && expirationDate.before(now)) {
			expired = true;
		}

		Date reviewDate = null;

		if (!neverReview) {
			reviewDate = PortalUtil.getDate(
				reviewDateMonth, reviewDateDay, reviewDateYear, reviewDateHour,
				reviewDateMinute, user.getTimeZone(),
				ArticleReviewDateException.class);
		}

		byte[] smallImageBytes = null;

		try {
			smallImageBytes = FileUtil.getBytes(smallImageFile);
		}
		catch (IOException ioe) {
		}

		JournalArticle oldArticle = null;
		double oldVersion = 0;

		boolean incrementVersion = false;

		boolean imported = ParamUtil.getBoolean(serviceContext, "imported");

		if (imported) {
			oldArticle = getArticle(groupId, articleId, version);
			oldVersion = version;

			if (expired) {
				return expireArticle(
					userId, groupId, articleId, version, articleURL,
					serviceContext);
			}
		}
		else {
			oldArticle = getLatestArticle(
				groupId, articleId, WorkflowConstants.STATUS_ANY);

			oldVersion = oldArticle.getVersion();

			if ((version > 0) && (version != oldVersion)) {
				throw new ArticleVersionException();
			}

			serviceContext.validateModifiedDate(
				oldArticle, ArticleVersionException.class);

			if (oldArticle.isApproved() || oldArticle.isExpired()) {
				incrementVersion = true;
			}
		}

		validate(
			user.getCompanyId(), groupId, oldArticle.getClassNameId(), titleMap,
			content, type, structureId, templateId, smallImage, smallImageURL,
			smallImageFile, smallImageBytes);

		JournalArticle article = null;

		if (incrementVersion) {
			double newVersion = MathUtil.format(oldVersion + 0.1, 1, 1);

			long id = counterLocalService.increment();

			article = journalArticlePersistence.create(id);

			article.setResourcePrimKey(oldArticle.getResourcePrimKey());
			article.setGroupId(oldArticle.getGroupId());
			article.setCompanyId(oldArticle.getCompanyId());
			article.setUserId(user.getUserId());
			article.setUserName(user.getFullName());
			article.setCreateDate(serviceContext.getModifiedDate(now));
			article.setClassNameId(oldArticle.getClassNameId());
			article.setClassPK(oldArticle.getClassPK());
			article.setArticleId(articleId);
			article.setVersion(newVersion);
			article.setSmallImageId(oldArticle.getSmallImageId());
		}
		else {
			article = oldArticle;
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
			user, groupId, articleId, article.getVersion(), incrementVersion,
			content, structureId, images);

		article.setModifiedDate(serviceContext.getModifiedDate(now));
		article.setFolderId(folderId);
		article.setTitleMap(titleMap, locale);
		article.setUrlTitle(
			getUniqueUrlTitle(
				article.getId(), article.getArticleId(), title,
				oldArticle.getUrlTitle(), serviceContext));
		article.setDescriptionMap(descriptionMap, locale);
		article.setContent(content);
		article.setType(type);
		article.setStructureId(structureId);
		article.setTemplateId(templateId);
		article.setLayoutUuid(layoutUuid);
		article.setDisplayDate(displayDate);
		article.setExpirationDate(expirationDate);
		article.setReviewDate(reviewDate);
		article.setIndexable(indexable);
		article.setSmallImage(smallImage);

		if (article.getSmallImageId() == 0) {
			article.setSmallImageId(counterLocalService.increment());
		}

		article.setSmallImageURL(smallImageURL);

		if (oldArticle.isPending()) {
			article.setStatus(oldArticle.getStatus());
		}
		else if (!expired) {
			article.setStatus(WorkflowConstants.STATUS_DRAFT);
		}
		else {
			article.setStatus(WorkflowConstants.STATUS_EXPIRED);
		}

		journalArticlePersistence.update(article, false);

		// Asset

		updateAsset(
			userId, article, serviceContext.getAssetCategoryIds(),
			serviceContext.getAssetTagNames(),
			serviceContext.getAssetLinkEntryIds());

		// Expando

		ExpandoBridge expandoBridge = article.getExpandoBridge();

		expandoBridge.setAttributes(serviceContext);

		// Small image

		saveImages(
			smallImage, article.getSmallImageId(), smallImageFile,
			smallImageBytes);

		// Email

		PortletPreferences preferences =
			ServiceContextUtil.getPortletPreferences(serviceContext);

		// Workflow

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

			Indexer indexer = IndexerRegistryUtil.nullSafeGetIndexer(
				JournalArticle.class);

			indexer.reindex(article);
		}

		return article;
	}

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
	 * @deprecated {@link #updateArticleTranslation(long, String, double,
	 *             Locale, String, String, String, Map, ServiceContext)}
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

		if (serviceContext != null) {
			serviceContext.validateModifiedDate(
				oldArticle, ArticleVersionException.class);
		}

		JournalArticle article = null;

		User user = userPersistence.findByPrimaryKey(oldArticle.getUserId());

		if (!oldArticle.isDraft()) {
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
			user, groupId, articleId, version, !oldArticle.isDraft(), content,
			oldArticle.getStructureId(), images);

		article.setContent(content);

		journalArticlePersistence.update(article, false);

		return article;
	}

	public void updateAsset(
			long userId, JournalArticle article, long[] assetCategoryIds,
			String[] assetTagNames, long[] assetLinkEntryIds)
		throws PortalException, SystemException {

		boolean visible = article.isApproved();

		if (article.getClassNameId() > 0) {
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
				userId, article.getGroupId(), JournalArticle.class.getName(),
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
				userId, article.getGroupId(), JournalArticle.class.getName(),
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

	public JournalArticle updateContent(
			long groupId, String articleId, double version, String content)
		throws PortalException, SystemException {

		JournalArticle article = journalArticlePersistence.findByG_A_V(
			groupId, articleId, version);

		article.setContent(content);

		journalArticlePersistence.update(article, false);

		return article;
	}

	public JournalArticle updateStatus(
			long userId, JournalArticle article, int status, String articleURL,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		// Article

		User user = userPersistence.findByPrimaryKey(userId);
		Date now = new Date();

		int oldStatus = article.getStatus();

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

		journalArticlePersistence.update(article, false);

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

				if (article.getClassNameId() == 0) {

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

				// Expando

				ExpandoBridge expandoBridge = article.getExpandoBridge();

				expandoBridge.setAttributes(serviceContext);

				// Indexer

				Indexer indexer = IndexerRegistryUtil.nullSafeGetIndexer(
					JournalArticle.class);

				indexer.reindex(article);
			}
			else if (oldStatus == WorkflowConstants.STATUS_APPROVED) {
				updatePreviousApprovedArticle(article);
			}
		}

		if (article.getClassNameId() == 0) {

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

	public JournalArticle updateStatus(
			long userId, long classPK, int status,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		JournalArticle article = getArticle(classPK);

		return updateStatus(userId, article, status, null, serviceContext);
	}

	public JournalArticle updateStatus(
			long userId, long groupId, String articleId, double version,
			int status, String articleURL, ServiceContext serviceContext)
		throws PortalException, SystemException {

		JournalArticle article = journalArticlePersistence.findByG_A_V(
			groupId, articleId, version);

		return updateStatus(
			userId, article, status, articleURL, serviceContext);
	}

	public void updateTemplateId(
			long groupId, long classNameId, String oldTemplateId,
			String newTemplateId)
		throws SystemException {

		List<JournalArticle> articles = journalArticlePersistence.findByG_C_T(
			groupId, classNameId, oldTemplateId);

		for (JournalArticle article : articles) {
			article.setTemplateId(newTemplateId);

			journalArticlePersistence.update(article, false);
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
		throws DocumentException, PortalException, SystemException {

		Group companyGroup = groupLocalService.getCompanyGroup(
			article.getCompanyId());

		JournalStructure structure = null;

		try {
			structure = journalStructurePersistence.findByG_S(
				article.getGroupId(), article.getStructureId());
		}
		catch (NoSuchStructureException nsse) {
			structure = journalStructurePersistence.findByG_S(
				companyGroup.getGroupId(), article.getStructureId());
		}

		String content = GetterUtil.getString(article.getContent());

		Document contentDoc = SAXReaderUtil.read(content);
		Document xsdDoc = SAXReaderUtil.read(structure.getXsd());

		try {
			checkStructure(contentDoc, xsdDoc.getRootElement());
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
						dynamicContent = SanitizerUtil.sanitize(
							user.getCompanyId(), groupId, user.getUserId(),
							JournalArticle.class.getName(), 0,
							ContentTypes.TEXT_HTML, dynamicContent);

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
			boolean incrementVersion, String content, String structureId,
			Map<String, byte[]> images)
		throws PortalException, SystemException {

		Document document = null;

		try {
			document = SAXReaderUtil.read(content);

			Element rootElement = document.getRootElement();

			if (Validator.isNotNull(structureId)) {
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
			_log.error(de);
		}
		catch (IOException ioe) {
			_log.error(ioe);
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

			long contentImageId = GetterUtil.getLong(HttpUtil.getParameter(
				dynamicContent.getText(), "img_id"));

			if (contentImageId <= 0) {
				contentImageId = GetterUtil.getLong(HttpUtil.getParameter(
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

	protected long getClassTypeId(JournalArticle article) {
		long classTypeId = 0;

		try {
			JournalStructure structure = journalStructurePersistence.fetchByG_S(
				article.getGroupId(), article.getStructureId());

			if (structure == null) {
				Group companyGroup = groupLocalService.getCompanyGroup(
					article.getCompanyId());

				structure = journalStructurePersistence.fetchByG_S(
					companyGroup.getGroupId(), article.getStructureId());
			}

			if (structure != null) {
				classTypeId = structure.getId();
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

		if (isMatchesServiceContextUrlTitle(serviceContextUrlTitle)) {
			urlTitle = JournalUtil.getUrlTitle(id, serviceContextUrlTitle);

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
		}
		else {
			if (isMatchesServiceContextUrlTitle(oldUrlTitle)) {
				urlTitle = oldUrlTitle;
			}
			else {
				urlTitle = getUniqueUrlTitle(
					id, serviceContext.getScopeGroupId(), articleId, title);
			}
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

	protected boolean isMatchesServiceContextUrlTitle(String urlTitle) {
		if (Validator.isNotNull(urlTitle) &&
			Validator.isNotNull(PropsValues.JOURNAL_ARTICLE_URL_TITLE_REGEXP)) {

			Pattern pattern = Pattern.compile(
				PropsValues.JOURNAL_ARTICLE_URL_TITLE_REGEXP);

			Matcher matcher = pattern.matcher(urlTitle);

			return matcher.matches();
		}

		return false;
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

			assetEntryPersistence.update(assetEntry, false);

			if (article.isIndexable()) {
				Indexer indexer = IndexerRegistryUtil.nullSafeGetIndexer(
					JournalArticle.class);

				indexer.reindex(previousApprovedArticle);
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

				journalArticlePersistence.update(article, false);
			}
		}
	}

	protected void validate(
			long companyId, long groupId, long classNameId,
			Map<Locale, String> titleMap, String content, String type,
			String structureId, String templateId, boolean smallImage,
			String smallImageURL, File smallImageFile, byte[] smallImageBytes)
		throws PortalException, SystemException {

		Locale articleDefaultLocale = LocaleUtil.fromLanguageId(
			LocalizationUtil.getDefaultLocale(content));

		Locale[] availableLocales = LanguageUtil.getAvailableLocales();

		if (!ArrayUtil.contains(availableLocales, articleDefaultLocale)) {
			LocaleException le = new LocaleException();

			Locale[] sourceAvailableLocales = {articleDefaultLocale};

			le.setSourceAvailableLocales(sourceAvailableLocales);
			le.setTargetAvailableLocales(availableLocales);

			throw le;
		}

		if ((classNameId == 0) &&
			(titleMap.isEmpty() ||
			 Validator.isNull(titleMap.get(articleDefaultLocale)))) {

			throw new ArticleTitleException();
		}
		else if (Validator.isNull(type)) {
			throw new ArticleTypeException();
		}

		validateContent(content);

		if (Validator.isNotNull(structureId)) {
			Group companyGroup = groupLocalService.getCompanyGroup(companyId);

			try {
				journalStructurePersistence.findByG_S(groupId, structureId);
			}
			catch (NoSuchStructureException nsse) {
				journalStructurePersistence.findByG_S(
					companyGroup.getGroupId(), structureId);
			}

			JournalTemplate template = null;

			if (Validator.isNotNull(templateId)) {
				try {
					template = journalTemplatePersistence.findByG_T(
						groupId, templateId);
				}
				catch (NoSuchTemplateException nste) {
					template = journalTemplatePersistence.findByG_T(
						companyGroup.getGroupId(), templateId);
				}

				if (!template.getStructureId().equals(structureId)) {
					throw new NoSuchTemplateException();
				}
			}
			else if (classNameId == 0) {
				throw new NoSuchTemplateException();
			}
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
			String content, String type, String structureId, String templateId,
			boolean smallImage, String smallImageURL, File smallImageFile,
			byte[] smallImageBytes)
		throws PortalException, SystemException {

		if (!autoArticleId) {
			validate(groupId, articleId);
		}

		validate(
			companyId, groupId, classNameId, titleMap, content, type,
			structureId, templateId, smallImage, smallImageURL, smallImageFile,
			smallImageBytes);
	}

	protected void validate(long groupId, String articleId)
		throws PortalException, SystemException {

		if (Validator.isNull(articleId) ||
			(articleId.indexOf(CharPool.SPACE) != -1)) {

			throw new ArticleIdException();
		}

		if (journalArticlePersistence.countByG_A(groupId, articleId) > 0) {
			throw new DuplicateArticleIdException();
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

}