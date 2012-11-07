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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.model.JournalArticleConstants;
import com.liferay.portlet.journal.service.base.JournalArticleServiceBaseImpl;
import com.liferay.portlet.journal.service.permission.JournalArticlePermission;
import com.liferay.portlet.journal.service.permission.JournalPermission;

import java.io.File;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 */
public class JournalArticleServiceImpl extends JournalArticleServiceBaseImpl {

	public JournalArticle addArticle(
			long groupId, long folderId, long classNameId, long classPK,
			String articleId, boolean autoArticleId,
			Map<Locale, String> titleMap, Map<Locale, String> descriptionMap,
			String content, String type, String structureId, String templateId,
			String layoutUuid, int displayDateMonth, int displayDateDay,
			int displayDateYear, int displayDateHour, int displayDateMinute,
			int expirationDateMonth, int expirationDateDay,
			int expirationDateYear, int expirationDateHour,
			int expirationDateMinute, boolean neverExpire, int reviewDateMonth,
			int reviewDateDay, int reviewDateYear, int reviewDateHour,
			int reviewDateMinute, boolean neverReview, boolean indexable,
			boolean smallImage, String smallImageURL, File smallFile,
			Map<String, byte[]> images, String articleURL,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		JournalPermission.check(
			getPermissionChecker(), groupId, ActionKeys.ADD_ARTICLE);

		return journalArticleLocalService.addArticle(
			getUserId(), groupId, folderId, classNameId, classPK, articleId,
			autoArticleId, JournalArticleConstants.VERSION_DEFAULT, titleMap,
			descriptionMap, content, type, structureId, templateId, layoutUuid,
			displayDateMonth, displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, expirationDateMonth, expirationDateDay,
			expirationDateYear, expirationDateHour, expirationDateMinute,
			neverExpire, reviewDateMonth, reviewDateDay, reviewDateYear,
			reviewDateHour, reviewDateMinute, neverReview, indexable,
			smallImage, smallImageURL, smallFile, images, articleURL,
			serviceContext);
	}

	public JournalArticle addArticle(
			long groupId, long folderId, long classNameId, long classPK,
			String articleId, boolean autoArticleId,
			Map<Locale, String> titleMap, Map<Locale, String> descriptionMap,
			String content, String type, String structureId, String templateId,
			String layoutUuid, int displayDateMonth, int displayDateDay,
			int displayDateYear, int displayDateHour, int displayDateMinute,
			int expirationDateMonth, int expirationDateDay,
			int expirationDateYear, int expirationDateHour,
			int expirationDateMinute, boolean neverExpire, int reviewDateMonth,
			int reviewDateDay, int reviewDateYear, int reviewDateHour,
			int reviewDateMinute, boolean neverReview, boolean indexable,
			String articleURL, ServiceContext serviceContext)
		throws PortalException, SystemException {

		JournalPermission.check(
			getPermissionChecker(), groupId, ActionKeys.ADD_ARTICLE);

		return journalArticleLocalService.addArticle(
			getUserId(), groupId, folderId, classNameId, classPK, articleId,
			autoArticleId, JournalArticleConstants.VERSION_DEFAULT, titleMap,
			descriptionMap, content, type, structureId, templateId, layoutUuid,
			displayDateMonth, displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, expirationDateMonth, expirationDateDay,
			expirationDateYear, expirationDateHour, expirationDateMinute,
			neverExpire, reviewDateMonth, reviewDateDay, reviewDateYear,
			reviewDateHour, reviewDateMinute, neverReview, indexable, false,
			null, null, null, articleURL, serviceContext);
	}

	public JournalArticle copyArticle(
			long groupId, String oldArticleId, String newArticleId,
			boolean autoArticleId, double version)
		throws PortalException, SystemException {

		JournalPermission.check(
			getPermissionChecker(), groupId, ActionKeys.ADD_ARTICLE);

		return journalArticleLocalService.copyArticle(
			getUserId(), groupId, oldArticleId, newArticleId, autoArticleId,
			version);
	}

	public void deleteArticle(
			long groupId, String articleId, double version, String articleURL,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		JournalArticlePermission.check(
			getPermissionChecker(), groupId, articleId, version,
			ActionKeys.DELETE);

		journalArticleLocalService.deleteArticle(
			groupId, articleId, version, articleURL, serviceContext);
	}

	public void deleteArticle(
			long groupId, String articleId, String articleURL,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		JournalArticlePermission.check(
			getPermissionChecker(), groupId, articleId, ActionKeys.DELETE);

		journalArticleLocalService.deleteArticle(
			groupId, articleId, serviceContext);
	}

	public JournalArticle expireArticle(
			long groupId, String articleId, double version, String articleURL,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		JournalArticlePermission.check(
			getPermissionChecker(), groupId, articleId, version,
			ActionKeys.EXPIRE);

		return journalArticleLocalService.expireArticle(
			getUserId(), groupId, articleId, version, articleURL,
			serviceContext);
	}

	public void expireArticle(
			long groupId, String articleId, String articleURL,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		JournalArticlePermission.check(
			getPermissionChecker(), groupId, articleId, ActionKeys.EXPIRE);

		journalArticleLocalService.expireArticle(
			getUserId(), groupId, articleId, articleURL, serviceContext);
	}

	public JournalArticle getArticle(long id)
		throws PortalException, SystemException {

		JournalArticle article = journalArticleLocalService.getArticle(id);

		JournalArticlePermission.check(
			getPermissionChecker(), article, ActionKeys.VIEW);

		return article;
	}

	public JournalArticle getArticle(long groupId, String articleId)
		throws PortalException, SystemException {

		JournalArticlePermission.check(
			getPermissionChecker(), groupId, articleId, ActionKeys.VIEW);

		return journalArticleLocalService.getArticle(groupId, articleId);
	}

	public JournalArticle getArticle(
			long groupId, String articleId, double version)
		throws PortalException, SystemException {

		JournalArticlePermission.check(
			getPermissionChecker(), groupId, articleId, version,
			ActionKeys.VIEW);

		return journalArticleLocalService.getArticle(
			groupId, articleId, version);
	}

	public JournalArticle getArticle(
			long groupId, String className, long classPK)
		throws PortalException, SystemException {

		JournalArticle article = journalArticleLocalService.getArticle(
			groupId, className, classPK);

		JournalArticlePermission.check(
			getPermissionChecker(), groupId, article.getArticleId(),
			article.getVersion(), ActionKeys.VIEW);

		return article;
	}

	public JournalArticle getArticleByUrlTitle(long groupId, String urlTitle)
		throws PortalException, SystemException {

		JournalArticle article =
			journalArticleLocalService.getArticleByUrlTitle(groupId, urlTitle);

		JournalArticlePermission.check(
			getPermissionChecker(), article, ActionKeys.VIEW);

		return article;
	}

	public String getArticleContent(
			long groupId, String articleId, double version, String languageId,
			ThemeDisplay themeDisplay)
		throws PortalException, SystemException {

		JournalArticlePermission.check(
			getPermissionChecker(), groupId, articleId, version,
			ActionKeys.VIEW);

		return journalArticleLocalService.getArticleContent(
			groupId, articleId, version, null, languageId, themeDisplay);
	}

	public String getArticleContent(
			long groupId, String articleId, String languageId,
			ThemeDisplay themeDisplay)
		throws PortalException, SystemException {

		JournalArticlePermission.check(
			getPermissionChecker(), groupId, articleId, ActionKeys.VIEW);

		return journalArticleLocalService.getArticleContent(
			groupId, articleId, null, languageId, themeDisplay);
	}

	public List<JournalArticle> getArticles(long groupId, long folderId)
		throws SystemException {

		return journalArticlePersistence.filterFindByG_F(groupId, folderId);
	}

	public List<JournalArticle> getArticles(
			long groupId, long folderId, int start, int end,
			OrderByComparator obc)
		throws SystemException {

		return journalArticlePersistence.filterFindByG_F(
			groupId, folderId, start, end, obc);
	}

	public List<JournalArticle> getArticlesByArticleId(
			long groupId, String articleId, int start, int end,
			OrderByComparator obc)
		throws SystemException {

		return journalArticlePersistence.filterFindByG_A(
			groupId, articleId, start, end, obc);
	}

	public List<JournalArticle> getArticlesByLayoutUuid(
			long groupId, String layoutUuid)
		throws SystemException {

		return journalArticlePersistence.filterFindByG_L(groupId, layoutUuid);
	}

	public List<JournalArticle> getArticlesByStructureId(
			long groupId, String structureId, int start, int end,
			OrderByComparator obc)
		throws SystemException {

		return journalArticlePersistence.filterFindByG_S(
			groupId, structureId, start, end, obc);
	}

	public List<JournalArticle> getArticlesByUserId(
			long groupId, long userId, long classNameId, int start, int end,
			OrderByComparator obc)
		throws SystemException {

		return journalArticlePersistence.filterFindByG_U_C(
			groupId, userId, classNameId, start, end, obc);
	}

	public int getArticlesCount(long groupId, long folderId)
		throws SystemException {

		return journalArticlePersistence.filterCountByG_F(groupId, folderId);
	}

	public int getArticlesCountByArticleId(long groupId, String articleId)
		throws SystemException {

		return journalArticlePersistence.filterCountByG_A(groupId, articleId);
	}

	public int getArticlesCountByStructureId(long groupId, String structureId)
		throws SystemException {

		return journalArticlePersistence.filterCountByG_S(groupId, structureId);
	}

	public int getArticlesCountByUserId(
			long groupId, long userId, long classNameId)
		throws SystemException {

		return journalArticlePersistence.filterCountByG_U_C(
			groupId, userId, classNameId);
	}

	public JournalArticle getDisplayArticleByUrlTitle(
			long groupId, String urlTitle)
		throws PortalException, SystemException {

		JournalArticle article =
			journalArticleLocalService.getDisplayArticleByUrlTitle(
				groupId, urlTitle);

		JournalArticlePermission.check(
			getPermissionChecker(), article, ActionKeys.VIEW);

		return article;
	}

	public int getFoldersAndArticlesCount(long groupId, List<Long> folderIds)
		throws SystemException {

		return journalArticlePersistence.filterCountByG_F(
			groupId,
			ArrayUtil.toArray(folderIds.toArray(new Long[folderIds.size()])));
	}

	public JournalArticle getLatestArticle(long resourcePrimKey)
		throws PortalException, SystemException {

		JournalArticlePermission.check(
			getPermissionChecker(), resourcePrimKey, ActionKeys.VIEW);

		return journalArticleLocalService.getLatestArticle(resourcePrimKey);
	}

	public JournalArticle getLatestArticle(
			long groupId, String articleId, int status)
		throws PortalException, SystemException {

		JournalArticlePermission.check(
			getPermissionChecker(), groupId, articleId, status,
			ActionKeys.VIEW);

		return journalArticleLocalService.getLatestArticle(
			groupId, articleId, status);
	}

	public JournalArticle getLatestArticle(
			long groupId, String className, long classPK)
		throws PortalException, SystemException {

		JournalArticle article = journalArticleLocalService.getLatestArticle(
			groupId, className, classPK);

		JournalArticlePermission.check(
			getPermissionChecker(), groupId, article.getArticleId(),
			article.getVersion(), ActionKeys.VIEW);

		return article;
	}

	public void moveArticle(long groupId, String articleId, long newFolderId)
		throws PortalException, SystemException {

		List<JournalArticle> articles = journalArticlePersistence.findByG_A(
			groupId, articleId);

		for (JournalArticle article : articles) {
			JournalArticlePermission.check(
				getPermissionChecker(), article, ActionKeys.UPDATE);

			journalArticleLocalService.moveArticle(
				groupId, articleId, newFolderId);
		}
	}

	public void removeArticleLocale(long companyId, String languageId)
		throws PortalException, SystemException {

		for (JournalArticle article :
				journalArticlePersistence.findByCompanyId(companyId)) {

			removeArticleLocale(
				article.getGroupId(), article.getArticleId(),
				article.getVersion(), languageId);
		}
	}

	public JournalArticle removeArticleLocale(
			long groupId, String articleId, double version, String languageId)
		throws PortalException, SystemException {

		JournalArticlePermission.check(
			getPermissionChecker(), groupId, articleId, version,
			ActionKeys.UPDATE);

		return journalArticleLocalService.removeArticleLocale(
			groupId, articleId, version, languageId);
	}

	public List<JournalArticle> search(
			long companyId, long groupId, List<Long> folderIds,
			long classNameId, String keywords, Double version, String type,
			String structureId, String templateId, Date displayDateGT,
			Date displayDateLT, int status, Date reviewDate, int start, int end,
			OrderByComparator obc)
		throws SystemException {

		return journalArticleFinder.filterFindByKeywords(
			companyId, groupId, folderIds, classNameId, keywords, version, type,
			structureId, templateId, displayDateGT, displayDateLT, status,
			reviewDate, start, end, obc);
	}

	public List<JournalArticle> search(
			long companyId, long groupId, List<Long> folderIds,
			long classNameId, String articleId, Double version, String title,
			String description, String content, String type, String structureId,
			String templateId, Date displayDateGT, Date displayDateLT,
			int status, Date reviewDate, boolean andOperator, int start,
			int end, OrderByComparator obc)
		throws SystemException {

		return journalArticleFinder.filterFindByC_G_F_C_A_V_T_D_C_T_S_T_D_S_R(
			companyId, groupId, folderIds, classNameId, articleId, version,
			title, description, content, type, structureId, templateId,
			displayDateGT, displayDateLT, status, reviewDate, andOperator,
			start, end, obc);
	}

	public List<JournalArticle> search(
			long companyId, long groupId, List<Long> folderIds,
			long classNameId, String articleId, Double version, String title,
			String description, String content, String type,
			String[] structureIds, String[] templateIds, Date displayDateGT,
			Date displayDateLT, int status, Date reviewDate,
			boolean andOperator, int start, int end, OrderByComparator obc)
		throws SystemException {

		return journalArticleFinder.filterFindByC_G_F_C_A_V_T_D_C_T_S_T_D_S_R(
			companyId, groupId, folderIds, classNameId, articleId, version,
			title, description, content, type, structureIds, templateIds,
			displayDateGT, displayDateLT, status, reviewDate, andOperator,
			start, end, obc);
	}

	public int searchCount(
			long companyId, long groupId, List<Long> folderIds,
			long classNameId, String keywords, Double version, String type,
			String structureId, String templateId, Date displayDateGT,
			Date displayDateLT, int status, Date reviewDate)
		throws SystemException {

		return journalArticleFinder.filterCountByKeywords(
			companyId, groupId, folderIds, classNameId, keywords, version, type,
			structureId, templateId, displayDateGT, displayDateLT, status,
			reviewDate);
	}

	public int searchCount(
			long companyId, long groupId, List<Long> folderIds,
			long classNameId, String articleId, Double version, String title,
			String description, String content, String type, String structureId,
			String templateId, Date displayDateGT, Date displayDateLT,
			int status, Date reviewDate, boolean andOperator)
		throws SystemException {

		return journalArticleFinder.filterCountByC_G_F_C_A_V_T_D_C_T_S_T_D_S_R(
			companyId, groupId, folderIds, classNameId, articleId, version,
			title, description, content, type, structureId, templateId,
			displayDateGT, displayDateLT, status, reviewDate, andOperator);
	}

	public int searchCount(
			long companyId, long groupId, List<Long> folderIds,
			long classNameId, String articleId, Double version, String title,
			String description, String content, String type,
			String[] structureIds, String[] templateIds, Date displayDateGT,
			Date displayDateLT, int status, Date reviewDate,
			boolean andOperator)
		throws SystemException {

		return journalArticleFinder.filterCountByC_G_F_C_A_V_T_D_C_T_S_T_D_S_R(
			companyId, groupId, folderIds, classNameId, articleId, version,
			title, description, content, type, structureIds, templateIds,
			displayDateGT, displayDateLT, status, reviewDate, andOperator);
	}

	public void subscribe(long groupId)
		throws PortalException, SystemException {

		JournalPermission.check(
			getPermissionChecker(), groupId, ActionKeys.SUBSCRIBE);

		journalArticleLocalService.subscribe(getUserId(), groupId);
	}

	public void unsubscribe(long groupId)
		throws PortalException, SystemException {

		JournalPermission.check(
			getPermissionChecker(), groupId, ActionKeys.SUBSCRIBE);

		journalArticleLocalService.unsubscribe(getUserId(), groupId);
	}

	public JournalArticle updateArticle(
			long userId, long groupId, long folderId, String articleId,
			double version, Map<Locale, String> titleMap,
			Map<Locale, String> descriptionMap, String content,
			String layoutUuid, ServiceContext serviceContext)
		throws PortalException, SystemException {

		return journalArticleLocalService.updateArticle(
			userId, groupId, folderId, articleId, version, titleMap,
			descriptionMap, content, layoutUuid, serviceContext);
	}

	public JournalArticle updateArticle(
			long groupId, long folderId, String articleId, double version,
			Map<Locale, String> titleMap, Map<Locale, String> descriptionMap,
			String content, String type, String structureId, String templateId,
			String layoutUuid, int displayDateMonth, int displayDateDay,
			int displayDateYear, int displayDateHour, int displayDateMinute,
			int expirationDateMonth, int expirationDateDay,
			int expirationDateYear, int expirationDateHour,
			int expirationDateMinute, boolean neverExpire, int reviewDateMonth,
			int reviewDateDay, int reviewDateYear, int reviewDateHour,
			int reviewDateMinute, boolean neverReview, boolean indexable,
			boolean smallImage, String smallImageURL, File smallFile,
			Map<String, byte[]> images, String articleURL,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		JournalArticlePermission.check(
			getPermissionChecker(), groupId, articleId, version,
			ActionKeys.UPDATE);

		return journalArticleLocalService.updateArticle(
			getUserId(), groupId, folderId, articleId, version, titleMap,
			descriptionMap, content, type, structureId, templateId, layoutUuid,
			displayDateMonth, displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, expirationDateMonth, expirationDateDay,
			expirationDateYear, expirationDateHour, expirationDateMinute,
			neverExpire, reviewDateMonth, reviewDateDay, reviewDateYear,
			reviewDateHour, reviewDateMinute, neverReview, indexable,
			smallImage, smallImageURL, smallFile, images, articleURL,
			serviceContext);
	}

	public JournalArticle updateArticle(
			long groupId, long folderId, String articleId, double version,
			String content, ServiceContext serviceContext)
		throws PortalException, SystemException {

		JournalArticlePermission.check(
			getPermissionChecker(), groupId, articleId, version,
			ActionKeys.UPDATE);

		return journalArticleLocalService.updateArticle(
			getUserId(), groupId, folderId, articleId, version, content,
			serviceContext);
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

		JournalArticlePermission.check(
			getPermissionChecker(), groupId, articleId, version,
			ActionKeys.UPDATE);

		return journalArticleLocalService.updateArticleTranslation(
			groupId, articleId, version, locale, title, description, content,
			images, serviceContext);
	}

	public JournalArticle updateContent(
			long groupId, String articleId, double version, String content)
		throws PortalException, SystemException {

		JournalArticlePermission.check(
			getPermissionChecker(), groupId, articleId, version,
			ActionKeys.UPDATE);

		return journalArticleLocalService.updateContent(
			groupId, articleId, version, content);
	}

	public JournalArticle updateStatus(
			long groupId, String articleId, double version, int status,
			String articleURL, ServiceContext serviceContext)
		throws PortalException, SystemException {

		JournalArticlePermission.check(
			getPermissionChecker(), groupId, articleId, version,
			ActionKeys.UPDATE);

		return journalArticleLocalService.updateStatus(
			getUserId(), groupId, articleId, version, status, articleURL,
			serviceContext);
	}

}