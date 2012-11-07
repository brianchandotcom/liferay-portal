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

package com.liferay.portlet.journal.service;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * The utility for the journal article remote service. This utility wraps {@link com.liferay.portlet.journal.service.impl.JournalArticleServiceImpl} and is the primary access point for service operations in application layer code running on a remote server.
 *
 * <p>
 * This is a remote service. Methods of this service are expected to have security checks based on the propagated JAAS credentials because this service can be accessed remotely.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see JournalArticleService
 * @see com.liferay.portlet.journal.service.base.JournalArticleServiceBaseImpl
 * @see com.liferay.portlet.journal.service.impl.JournalArticleServiceImpl
 * @generated
 */
public class JournalArticleServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.portlet.journal.service.impl.JournalArticleServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public static java.lang.String getBeanIdentifier() {
		return getService().getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public static void setBeanIdentifier(java.lang.String beanIdentifier) {
		getService().setBeanIdentifier(beanIdentifier);
	}

	public static com.liferay.portlet.journal.model.JournalArticle addArticle(
		long groupId, long folderId, long classNameId, long classPK,
		java.lang.String articleId, boolean autoArticleId,
		java.util.Map<java.util.Locale, java.lang.String> titleMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.lang.String content, java.lang.String type,
		java.lang.String structureId, java.lang.String templateId,
		java.lang.String layoutUuid, int displayDateMonth, int displayDateDay,
		int displayDateYear, int displayDateHour, int displayDateMinute,
		int expirationDateMonth, int expirationDateDay, int expirationDateYear,
		int expirationDateHour, int expirationDateMinute, boolean neverExpire,
		int reviewDateMonth, int reviewDateDay, int reviewDateYear,
		int reviewDateHour, int reviewDateMinute, boolean neverReview,
		boolean indexable, boolean smallImage, java.lang.String smallImageURL,
		java.io.File smallFile, java.util.Map<java.lang.String, byte[]> images,
		java.lang.String articleURL,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .addArticle(groupId, folderId, classNameId, classPK,
			articleId, autoArticleId, titleMap, descriptionMap, content, type,
			structureId, templateId, layoutUuid, displayDateMonth,
			displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, expirationDateMonth, expirationDateDay,
			expirationDateYear, expirationDateHour, expirationDateMinute,
			neverExpire, reviewDateMonth, reviewDateDay, reviewDateYear,
			reviewDateHour, reviewDateMinute, neverReview, indexable,
			smallImage, smallImageURL, smallFile, images, articleURL,
			serviceContext);
	}

	public static com.liferay.portlet.journal.model.JournalArticle addArticle(
		long groupId, long folderId, long classNameId, long classPK,
		java.lang.String articleId, boolean autoArticleId,
		java.util.Map<java.util.Locale, java.lang.String> titleMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.lang.String content, java.lang.String type,
		java.lang.String structureId, java.lang.String templateId,
		java.lang.String layoutUuid, int displayDateMonth, int displayDateDay,
		int displayDateYear, int displayDateHour, int displayDateMinute,
		int expirationDateMonth, int expirationDateDay, int expirationDateYear,
		int expirationDateHour, int expirationDateMinute, boolean neverExpire,
		int reviewDateMonth, int reviewDateDay, int reviewDateYear,
		int reviewDateHour, int reviewDateMinute, boolean neverReview,
		boolean indexable, java.lang.String articleURL,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .addArticle(groupId, folderId, classNameId, classPK,
			articleId, autoArticleId, titleMap, descriptionMap, content, type,
			structureId, templateId, layoutUuid, displayDateMonth,
			displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, expirationDateMonth, expirationDateDay,
			expirationDateYear, expirationDateHour, expirationDateMinute,
			neverExpire, reviewDateMonth, reviewDateDay, reviewDateYear,
			reviewDateHour, reviewDateMinute, neverReview, indexable,
			articleURL, serviceContext);
	}

	public static com.liferay.portlet.journal.model.JournalArticle copyArticle(
		long groupId, java.lang.String oldArticleId,
		java.lang.String newArticleId, boolean autoArticleId, double version)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .copyArticle(groupId, oldArticleId, newArticleId,
			autoArticleId, version);
	}

	public static void deleteArticle(long groupId, java.lang.String articleId,
		double version, java.lang.String articleURL,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService()
			.deleteArticle(groupId, articleId, version, articleURL,
			serviceContext);
	}

	public static void deleteArticle(long groupId, java.lang.String articleId,
		java.lang.String articleURL,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService()
			.deleteArticle(groupId, articleId, articleURL, serviceContext);
	}

	public static com.liferay.portlet.journal.model.JournalArticle expireArticle(
		long groupId, java.lang.String articleId, double version,
		java.lang.String articleURL,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .expireArticle(groupId, articleId, version, articleURL,
			serviceContext);
	}

	public static void expireArticle(long groupId, java.lang.String articleId,
		java.lang.String articleURL,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService()
			.expireArticle(groupId, articleId, articleURL, serviceContext);
	}

	public static com.liferay.portlet.journal.model.JournalArticle getArticle(
		long id)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getArticle(id);
	}

	public static com.liferay.portlet.journal.model.JournalArticle getArticle(
		long groupId, java.lang.String articleId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getArticle(groupId, articleId);
	}

	public static com.liferay.portlet.journal.model.JournalArticle getArticle(
		long groupId, java.lang.String articleId, double version)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getArticle(groupId, articleId, version);
	}

	public static com.liferay.portlet.journal.model.JournalArticle getArticle(
		long groupId, java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getArticle(groupId, className, classPK);
	}

	public static com.liferay.portlet.journal.model.JournalArticle getArticleByUrlTitle(
		long groupId, java.lang.String urlTitle)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getArticleByUrlTitle(groupId, urlTitle);
	}

	public static java.lang.String getArticleContent(long groupId,
		java.lang.String articleId, double version,
		java.lang.String languageId,
		com.liferay.portal.theme.ThemeDisplay themeDisplay)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getArticleContent(groupId, articleId, version, languageId,
			themeDisplay);
	}

	public static java.lang.String getArticleContent(long groupId,
		java.lang.String articleId, java.lang.String languageId,
		com.liferay.portal.theme.ThemeDisplay themeDisplay)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getArticleContent(groupId, articleId, languageId,
			themeDisplay);
	}

	public static java.util.List<com.liferay.portlet.journal.model.JournalArticle> getArticles(
		long groupId, long folderId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getArticles(groupId, folderId);
	}

	public static java.util.List<com.liferay.portlet.journal.model.JournalArticle> getArticles(
		long groupId, long folderId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getArticles(groupId, folderId, start, end, obc);
	}

	public static java.util.List<com.liferay.portlet.journal.model.JournalArticle> getArticlesByArticleId(
		long groupId, java.lang.String articleId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getArticlesByArticleId(groupId, articleId, start, end, obc);
	}

	public static java.util.List<com.liferay.portlet.journal.model.JournalArticle> getArticlesByLayoutUuid(
		long groupId, java.lang.String layoutUuid)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getArticlesByLayoutUuid(groupId, layoutUuid);
	}

	public static java.util.List<com.liferay.portlet.journal.model.JournalArticle> getArticlesByStructureId(
		long groupId, java.lang.String structureId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getArticlesByStructureId(groupId, structureId, start, end,
			obc);
	}

	public static java.util.List<com.liferay.portlet.journal.model.JournalArticle> getArticlesByUserId(
		long groupId, long userId, long classNameId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getArticlesByUserId(groupId, userId, classNameId, start,
			end, obc);
	}

	public static int getArticlesCount(long groupId, long folderId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getArticlesCount(groupId, folderId);
	}

	public static int getArticlesCountByArticleId(long groupId,
		java.lang.String articleId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getArticlesCountByArticleId(groupId, articleId);
	}

	public static int getArticlesCountByStructureId(long groupId,
		java.lang.String structureId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getArticlesCountByStructureId(groupId, structureId);
	}

	public static int getArticlesCountByUserId(long groupId, long userId,
		long classNameId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getArticlesCountByUserId(groupId, userId, classNameId);
	}

	public static com.liferay.portlet.journal.model.JournalArticle getDisplayArticleByUrlTitle(
		long groupId, java.lang.String urlTitle)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getDisplayArticleByUrlTitle(groupId, urlTitle);
	}

	public static int getFoldersAndArticlesCount(long groupId,
		java.util.List<java.lang.Long> folderIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getFoldersAndArticlesCount(groupId, folderIds);
	}

	public static com.liferay.portlet.journal.model.JournalArticle getLatestArticle(
		long resourcePrimKey)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getLatestArticle(resourcePrimKey);
	}

	public static com.liferay.portlet.journal.model.JournalArticle getLatestArticle(
		long groupId, java.lang.String articleId, int status)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getLatestArticle(groupId, articleId, status);
	}

	public static com.liferay.portlet.journal.model.JournalArticle getLatestArticle(
		long groupId, java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getLatestArticle(groupId, className, classPK);
	}

	public static void moveArticle(long groupId, java.lang.String articleId,
		long newFolderId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().moveArticle(groupId, articleId, newFolderId);
	}

	public static void removeArticleLocale(long companyId,
		java.lang.String languageId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().removeArticleLocale(companyId, languageId);
	}

	public static com.liferay.portlet.journal.model.JournalArticle removeArticleLocale(
		long groupId, java.lang.String articleId, double version,
		java.lang.String languageId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .removeArticleLocale(groupId, articleId, version, languageId);
	}

	public static java.util.List<com.liferay.portlet.journal.model.JournalArticle> search(
		long companyId, long groupId, java.util.List<java.lang.Long> folderIds,
		long classNameId, java.lang.String keywords, java.lang.Double version,
		java.lang.String type, java.lang.String structureId,
		java.lang.String templateId, java.util.Date displayDateGT,
		java.util.Date displayDateLT, int status, java.util.Date reviewDate,
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .search(companyId, groupId, folderIds, classNameId,
			keywords, version, type, structureId, templateId, displayDateGT,
			displayDateLT, status, reviewDate, start, end, obc);
	}

	public static java.util.List<com.liferay.portlet.journal.model.JournalArticle> search(
		long companyId, long groupId, java.util.List<java.lang.Long> folderIds,
		long classNameId, java.lang.String articleId, java.lang.Double version,
		java.lang.String title, java.lang.String description,
		java.lang.String content, java.lang.String type,
		java.lang.String structureId, java.lang.String templateId,
		java.util.Date displayDateGT, java.util.Date displayDateLT, int status,
		java.util.Date reviewDate, boolean andOperator, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .search(companyId, groupId, folderIds, classNameId,
			articleId, version, title, description, content, type, structureId,
			templateId, displayDateGT, displayDateLT, status, reviewDate,
			andOperator, start, end, obc);
	}

	public static java.util.List<com.liferay.portlet.journal.model.JournalArticle> search(
		long companyId, long groupId, java.util.List<java.lang.Long> folderIds,
		long classNameId, java.lang.String articleId, java.lang.Double version,
		java.lang.String title, java.lang.String description,
		java.lang.String content, java.lang.String type,
		java.lang.String[] structureIds, java.lang.String[] templateIds,
		java.util.Date displayDateGT, java.util.Date displayDateLT, int status,
		java.util.Date reviewDate, boolean andOperator, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .search(companyId, groupId, folderIds, classNameId,
			articleId, version, title, description, content, type,
			structureIds, templateIds, displayDateGT, displayDateLT, status,
			reviewDate, andOperator, start, end, obc);
	}

	public static int searchCount(long companyId, long groupId,
		java.util.List<java.lang.Long> folderIds, long classNameId,
		java.lang.String keywords, java.lang.Double version,
		java.lang.String type, java.lang.String structureId,
		java.lang.String templateId, java.util.Date displayDateGT,
		java.util.Date displayDateLT, int status, java.util.Date reviewDate)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .searchCount(companyId, groupId, folderIds, classNameId,
			keywords, version, type, structureId, templateId, displayDateGT,
			displayDateLT, status, reviewDate);
	}

	public static int searchCount(long companyId, long groupId,
		java.util.List<java.lang.Long> folderIds, long classNameId,
		java.lang.String articleId, java.lang.Double version,
		java.lang.String title, java.lang.String description,
		java.lang.String content, java.lang.String type,
		java.lang.String structureId, java.lang.String templateId,
		java.util.Date displayDateGT, java.util.Date displayDateLT, int status,
		java.util.Date reviewDate, boolean andOperator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .searchCount(companyId, groupId, folderIds, classNameId,
			articleId, version, title, description, content, type, structureId,
			templateId, displayDateGT, displayDateLT, status, reviewDate,
			andOperator);
	}

	public static int searchCount(long companyId, long groupId,
		java.util.List<java.lang.Long> folderIds, long classNameId,
		java.lang.String articleId, java.lang.Double version,
		java.lang.String title, java.lang.String description,
		java.lang.String content, java.lang.String type,
		java.lang.String[] structureIds, java.lang.String[] templateIds,
		java.util.Date displayDateGT, java.util.Date displayDateLT, int status,
		java.util.Date reviewDate, boolean andOperator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .searchCount(companyId, groupId, folderIds, classNameId,
			articleId, version, title, description, content, type,
			structureIds, templateIds, displayDateGT, displayDateLT, status,
			reviewDate, andOperator);
	}

	public static void subscribe(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().subscribe(groupId);
	}

	public static void unsubscribe(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().unsubscribe(groupId);
	}

	public static com.liferay.portlet.journal.model.JournalArticle updateArticle(
		long userId, long groupId, long folderId, java.lang.String articleId,
		double version,
		java.util.Map<java.util.Locale, java.lang.String> titleMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.lang.String content, java.lang.String layoutUuid,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .updateArticle(userId, groupId, folderId, articleId,
			version, titleMap, descriptionMap, content, layoutUuid,
			serviceContext);
	}

	public static com.liferay.portlet.journal.model.JournalArticle updateArticle(
		long groupId, long folderId, java.lang.String articleId,
		double version,
		java.util.Map<java.util.Locale, java.lang.String> titleMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.lang.String content, java.lang.String type,
		java.lang.String structureId, java.lang.String templateId,
		java.lang.String layoutUuid, int displayDateMonth, int displayDateDay,
		int displayDateYear, int displayDateHour, int displayDateMinute,
		int expirationDateMonth, int expirationDateDay, int expirationDateYear,
		int expirationDateHour, int expirationDateMinute, boolean neverExpire,
		int reviewDateMonth, int reviewDateDay, int reviewDateYear,
		int reviewDateHour, int reviewDateMinute, boolean neverReview,
		boolean indexable, boolean smallImage, java.lang.String smallImageURL,
		java.io.File smallFile, java.util.Map<java.lang.String, byte[]> images,
		java.lang.String articleURL,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .updateArticle(groupId, folderId, articleId, version,
			titleMap, descriptionMap, content, type, structureId, templateId,
			layoutUuid, displayDateMonth, displayDateDay, displayDateYear,
			displayDateHour, displayDateMinute, expirationDateMonth,
			expirationDateDay, expirationDateYear, expirationDateHour,
			expirationDateMinute, neverExpire, reviewDateMonth, reviewDateDay,
			reviewDateYear, reviewDateHour, reviewDateMinute, neverReview,
			indexable, smallImage, smallImageURL, smallFile, images,
			articleURL, serviceContext);
	}

	public static com.liferay.portlet.journal.model.JournalArticle updateArticle(
		long groupId, long folderId, java.lang.String articleId,
		double version, java.lang.String content,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .updateArticle(groupId, folderId, articleId, version,
			content, serviceContext);
	}

	/**
	* @deprecated {@link #updateArticleTranslation(long, String, double,
	Locale, String, String, String, Map, ServiceContext)}
	*/
	public static com.liferay.portlet.journal.model.JournalArticle updateArticleTranslation(
		long groupId, java.lang.String articleId, double version,
		java.util.Locale locale, java.lang.String title,
		java.lang.String description, java.lang.String content,
		java.util.Map<java.lang.String, byte[]> images)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .updateArticleTranslation(groupId, articleId, version,
			locale, title, description, content, images);
	}

	public static com.liferay.portlet.journal.model.JournalArticle updateArticleTranslation(
		long groupId, java.lang.String articleId, double version,
		java.util.Locale locale, java.lang.String title,
		java.lang.String description, java.lang.String content,
		java.util.Map<java.lang.String, byte[]> images,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .updateArticleTranslation(groupId, articleId, version,
			locale, title, description, content, images, serviceContext);
	}

	public static com.liferay.portlet.journal.model.JournalArticle updateContent(
		long groupId, java.lang.String articleId, double version,
		java.lang.String content)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().updateContent(groupId, articleId, version, content);
	}

	public static com.liferay.portlet.journal.model.JournalArticle updateStatus(
		long groupId, java.lang.String articleId, double version, int status,
		java.lang.String articleURL,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .updateStatus(groupId, articleId, version, status,
			articleURL, serviceContext);
	}

	public static JournalArticleService getService() {
		if (_service == null) {
			_service = (JournalArticleService)PortalBeanLocatorUtil.locate(JournalArticleService.class.getName());

			ReferenceRegistry.registerReference(JournalArticleServiceUtil.class,
				"_service");
		}

		return _service;
	}

	/**
	 * @deprecated
	 */
	public void setService(JournalArticleService service) {
	}

	private static JournalArticleService _service;
}