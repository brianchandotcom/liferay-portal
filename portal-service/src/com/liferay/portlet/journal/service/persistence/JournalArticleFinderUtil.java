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

package com.liferay.portlet.journal.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * @author Brian Wing Shun Chan
 */
public class JournalArticleFinderUtil {
	public static int countByKeywords(long companyId, long groupId,
		long classNameId, java.lang.String keywords, java.lang.Double version,
		java.lang.String type, java.lang.String structureId,
		java.lang.String templateId, java.util.Date displayDateGT,
		java.util.Date displayDateLT, int status, java.util.Date reviewDate)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder()
				   .countByKeywords(companyId, groupId, classNameId, keywords,
			version, type, structureId, templateId, displayDateGT,
			displayDateLT, status, reviewDate);
	}

	public static int countByC_G_C_A_V_T_D_C_T_S_T_D_S_R(long companyId,
		long groupId, long classNameId, java.lang.String articleId,
		java.lang.Double version, java.lang.String title,
		java.lang.String description, java.lang.String content,
		java.lang.String type, java.lang.String structureId,
		java.lang.String templateId, java.util.Date displayDateGT,
		java.util.Date displayDateLT, int status, java.util.Date reviewDate,
		boolean andOperator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder()
				   .countByC_G_C_A_V_T_D_C_T_S_T_D_S_R(companyId, groupId,
			classNameId, articleId, version, title, description, content, type,
			structureId, templateId, displayDateGT, displayDateLT, status,
			reviewDate, andOperator);
	}

	public static int countByC_G_C_A_V_T_D_C_T_S_T_D_S_R(long companyId,
		long groupId, long classNameId, java.lang.String articleId,
		java.lang.Double version, java.lang.String title,
		java.lang.String description, java.lang.String content,
		java.lang.String type, java.lang.String[] structureIds,
		java.lang.String[] templateIds, java.util.Date displayDateGT,
		java.util.Date displayDateLT, int status, java.util.Date reviewDate,
		boolean andOperator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder()
				   .countByC_G_C_A_V_T_D_C_T_S_T_D_S_R(companyId, groupId,
			classNameId, articleId, version, title, description, content, type,
			structureIds, templateIds, displayDateGT, displayDateLT, status,
			reviewDate, andOperator);
	}

	public static int countByC_G_C_A_V_T_D_C_T_S_T_D_S_R(long companyId,
		long groupId, long classNameId, java.lang.String[] articleIds,
		java.lang.Double version, java.lang.String[] titles,
		java.lang.String[] descriptions, java.lang.String[] contents,
		java.lang.String type, java.lang.String[] structureIds,
		java.lang.String[] templateIds, java.util.Date displayDateGT,
		java.util.Date displayDateLT, int status, java.util.Date reviewDate,
		boolean andOperator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder()
				   .countByC_G_C_A_V_T_D_C_T_S_T_D_S_R(companyId, groupId,
			classNameId, articleIds, version, titles, descriptions, contents,
			type, structureIds, templateIds, displayDateGT, displayDateLT,
			status, reviewDate, andOperator);
	}

	public static int countByG_F_S(long groupId,
		java.util.List<java.lang.Long> folderIds, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder().countByG_F_S(groupId, folderIds, status);
	}

	public static int filterCountByG_F_S(long groupId,
		java.util.List<java.lang.Long> folderIds, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder().filterCountByG_F_S(groupId, folderIds, status);
	}

	public static int filterCountByKeywords(long companyId, long groupId,
		long classNameId, java.lang.String keywords, java.lang.Double version,
		java.lang.String type, java.lang.String structureId,
		java.lang.String templateId, java.util.Date displayDateGT,
		java.util.Date displayDateLT, int status, java.util.Date reviewDate)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder()
				   .filterCountByKeywords(companyId, groupId, classNameId,
			keywords, version, type, structureId, templateId, displayDateGT,
			displayDateLT, status, reviewDate);
	}

	public static int filterCountByC_G_C_A_V_T_D_C_T_S_T_D_S_R(long companyId,
		long groupId, long classNameId, java.lang.String articleId,
		java.lang.Double version, java.lang.String title,
		java.lang.String description, java.lang.String content,
		java.lang.String type, java.lang.String structureId,
		java.lang.String templateId, java.util.Date displayDateGT,
		java.util.Date displayDateLT, int status, java.util.Date reviewDate,
		boolean andOperator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder()
				   .filterCountByC_G_C_A_V_T_D_C_T_S_T_D_S_R(companyId,
			groupId, classNameId, articleId, version, title, description,
			content, type, structureId, templateId, displayDateGT,
			displayDateLT, status, reviewDate, andOperator);
	}

	public static int filterCountByC_G_C_A_V_T_D_C_T_S_T_D_S_R(long companyId,
		long groupId, long classNameId, java.lang.String articleId,
		java.lang.Double version, java.lang.String title,
		java.lang.String description, java.lang.String content,
		java.lang.String type, java.lang.String[] structureIds,
		java.lang.String[] templateIds, java.util.Date displayDateGT,
		java.util.Date displayDateLT, int status, java.util.Date reviewDate,
		boolean andOperator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder()
				   .filterCountByC_G_C_A_V_T_D_C_T_S_T_D_S_R(companyId,
			groupId, classNameId, articleId, version, title, description,
			content, type, structureIds, templateIds, displayDateGT,
			displayDateLT, status, reviewDate, andOperator);
	}

	public static int filterCountByC_G_C_A_V_T_D_C_T_S_T_D_S_R(long companyId,
		long groupId, long classNameId, java.lang.String[] articleIds,
		java.lang.Double version, java.lang.String[] titles,
		java.lang.String[] descriptions, java.lang.String[] contents,
		java.lang.String type, java.lang.String[] structureIds,
		java.lang.String[] templateIds, java.util.Date displayDateGT,
		java.util.Date displayDateLT, int status, java.util.Date reviewDate,
		boolean andOperator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder()
				   .filterCountByC_G_C_A_V_T_D_C_T_S_T_D_S_R(companyId,
			groupId, classNameId, articleIds, version, titles, descriptions,
			contents, type, structureIds, templateIds, displayDateGT,
			displayDateLT, status, reviewDate, andOperator);
	}

	public static java.util.List<com.liferay.portlet.journal.model.JournalArticle> filterFindByKeywords(
		long companyId, long groupId, long classNameId,
		java.lang.String keywords, java.lang.Double version,
		java.lang.String type, java.lang.String structureId,
		java.lang.String templateId, java.util.Date displayDateGT,
		java.util.Date displayDateLT, int status, java.util.Date reviewDate,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder()
				   .filterFindByKeywords(companyId, groupId, classNameId,
			keywords, version, type, structureId, templateId, displayDateGT,
			displayDateLT, status, reviewDate, start, end, orderByComparator);
	}

	public static java.util.List<com.liferay.portlet.journal.model.JournalArticle> filterFindByC_G_C_A_V_T_D_C_T_S_T_D_S_R(
		long companyId, long groupId, long classNameId,
		java.lang.String articleId, java.lang.Double version,
		java.lang.String title, java.lang.String description,
		java.lang.String content, java.lang.String type,
		java.lang.String structureId, java.lang.String templateId,
		java.util.Date displayDateGT, java.util.Date displayDateLT, int status,
		java.util.Date reviewDate, boolean andOperator, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder()
				   .filterFindByC_G_C_A_V_T_D_C_T_S_T_D_S_R(companyId, groupId,
			classNameId, articleId, version, title, description, content, type,
			structureId, templateId, displayDateGT, displayDateLT, status,
			reviewDate, andOperator, start, end, orderByComparator);
	}

	public static java.util.List<com.liferay.portlet.journal.model.JournalArticle> filterFindByC_G_C_A_V_T_D_C_T_S_T_D_S_R(
		long companyId, long groupId, long classNameId,
		java.lang.String articleId, java.lang.Double version,
		java.lang.String title, java.lang.String description,
		java.lang.String content, java.lang.String type,
		java.lang.String[] structureIds, java.lang.String[] templateIds,
		java.util.Date displayDateGT, java.util.Date displayDateLT, int status,
		java.util.Date reviewDate, boolean andOperator, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder()
				   .filterFindByC_G_C_A_V_T_D_C_T_S_T_D_S_R(companyId, groupId,
			classNameId, articleId, version, title, description, content, type,
			structureIds, templateIds, displayDateGT, displayDateLT, status,
			reviewDate, andOperator, start, end, orderByComparator);
	}

	public static java.util.List<com.liferay.portlet.journal.model.JournalArticle> filterFindByC_G_C_A_V_T_D_C_T_S_T_D_S_R(
		long companyId, long groupId, long classNameId,
		java.lang.String[] articleIds, java.lang.Double version,
		java.lang.String[] titles, java.lang.String[] descriptions,
		java.lang.String[] contents, java.lang.String type,
		java.lang.String[] structureIds, java.lang.String[] templateIds,
		java.util.Date displayDateGT, java.util.Date displayDateLT, int status,
		java.util.Date reviewDate, boolean andOperator, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder()
				   .filterFindByC_G_C_A_V_T_D_C_T_S_T_D_S_R(companyId, groupId,
			classNameId, articleIds, version, titles, descriptions, contents,
			type, structureIds, templateIds, displayDateGT, displayDateLT,
			status, reviewDate, andOperator, start, end, orderByComparator);
	}

	public static java.util.List<com.liferay.portlet.journal.model.JournalArticle> findByExpirationDate(
		long classNameId, int status, java.util.Date expirationDateLT)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder()
				   .findByExpirationDate(classNameId, status, expirationDateLT);
	}

	public static java.util.List<com.liferay.portlet.journal.model.JournalArticle> findByKeywords(
		long companyId, long groupId, long classNameId,
		java.lang.String keywords, java.lang.Double version,
		java.lang.String type, java.lang.String structureId,
		java.lang.String templateId, java.util.Date displayDateGT,
		java.util.Date displayDateLT, int status, java.util.Date reviewDate,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder()
				   .findByKeywords(companyId, groupId, classNameId, keywords,
			version, type, structureId, templateId, displayDateGT,
			displayDateLT, status, reviewDate, start, end, orderByComparator);
	}

	public static java.util.List<com.liferay.portlet.journal.model.JournalArticle> findByReviewDate(
		long classNameId, java.util.Date reviewDateLT,
		java.util.Date reviewDateGT)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder()
				   .findByReviewDate(classNameId, reviewDateLT, reviewDateGT);
	}

	public static com.liferay.portlet.journal.model.JournalArticle findByR_D(
		long resourcePrimKey, java.util.Date displayDate)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchArticleException {
		return getFinder().findByR_D(resourcePrimKey, displayDate);
	}

	public static java.util.List<com.liferay.portlet.journal.model.JournalArticle> findByC_G_C_A_V_T_D_C_T_S_T_D_S_R(
		long companyId, long groupId, long classNameId,
		java.lang.String articleId, java.lang.Double version,
		java.lang.String title, java.lang.String description,
		java.lang.String content, java.lang.String type,
		java.lang.String structureId, java.lang.String templateId,
		java.util.Date displayDateGT, java.util.Date displayDateLT, int status,
		java.util.Date reviewDate, boolean andOperator, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder()
				   .findByC_G_C_A_V_T_D_C_T_S_T_D_S_R(companyId, groupId,
			classNameId, articleId, version, title, description, content, type,
			structureId, templateId, displayDateGT, displayDateLT, status,
			reviewDate, andOperator, start, end, orderByComparator);
	}

	public static java.util.List<com.liferay.portlet.journal.model.JournalArticle> findByC_G_C_A_V_T_D_C_T_S_T_D_S_R(
		long companyId, long groupId, long classNameId,
		java.lang.String articleId, java.lang.Double version,
		java.lang.String title, java.lang.String description,
		java.lang.String content, java.lang.String type,
		java.lang.String[] structureIds, java.lang.String[] templateIds,
		java.util.Date displayDateGT, java.util.Date displayDateLT, int status,
		java.util.Date reviewDate, boolean andOperator, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder()
				   .findByC_G_C_A_V_T_D_C_T_S_T_D_S_R(companyId, groupId,
			classNameId, articleId, version, title, description, content, type,
			structureIds, templateIds, displayDateGT, displayDateLT, status,
			reviewDate, andOperator, start, end, orderByComparator);
	}

	public static java.util.List<com.liferay.portlet.journal.model.JournalArticle> findByC_G_C_A_V_T_D_C_T_S_T_D_S_R(
		long companyId, long groupId, long classNameId,
		java.lang.String[] articleIds, java.lang.Double version,
		java.lang.String[] titles, java.lang.String[] descriptions,
		java.lang.String[] contents, java.lang.String type,
		java.lang.String[] structureIds, java.lang.String[] templateIds,
		java.util.Date displayDateGT, java.util.Date displayDateLT, int status,
		java.util.Date reviewDate, boolean andOperator, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder()
				   .findByC_G_C_A_V_T_D_C_T_S_T_D_S_R(companyId, groupId,
			classNameId, articleIds, version, titles, descriptions, contents,
			type, structureIds, templateIds, displayDateGT, displayDateLT,
			status, reviewDate, andOperator, start, end, orderByComparator);
	}

	public static JournalArticleFinder getFinder() {
		if (_finder == null) {
			_finder = (JournalArticleFinder)PortalBeanLocatorUtil.locate(JournalArticleFinder.class.getName());

			ReferenceRegistry.registerReference(JournalArticleFinderUtil.class,
				"_finder");
		}

		return _finder;
	}

	public void setFinder(JournalArticleFinder finder) {
		_finder = finder;

		ReferenceRegistry.registerReference(JournalArticleFinderUtil.class,
			"_finder");
	}

	private static JournalArticleFinder _finder;
}