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

package com.liferay.portlet.journal.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * @generated
 */
@ProviderType
public class JournalArticleFinderUtil {
	public static int countByKeywords(long companyId, long groupId,
		java.util.List<java.lang.Long> folderIds, long classNameId,
		java.lang.String keywords, java.lang.Double version,
		java.lang.String ddmStructureKey, java.lang.String ddmTemplateKey,
		java.util.Date displayDateGT, java.util.Date displayDateLT, int status,
		java.util.Date reviewDate) {
		return getFinder()
				   .countByKeywords(companyId, groupId, folderIds, classNameId,
			keywords, version, ddmStructureKey, ddmTemplateKey, displayDateGT,
			displayDateLT, status, reviewDate);
	}

	public static int countByG_F(long groupId,
		java.util.List<java.lang.Long> folderIds,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<com.liferay.portlet.journal.model.JournalArticle> queryDefinition) {
		return getFinder().countByG_F(groupId, folderIds, queryDefinition);
	}

	public static int countByG_C_S(long groupId, long classNameId,
		java.lang.String ddmStructureKey,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<com.liferay.portlet.journal.model.JournalArticle> queryDefinition) {
		return getFinder()
				   .countByG_C_S(groupId, classNameId, ddmStructureKey,
			queryDefinition);
	}

	public static int countByG_U_F_C(long groupId, long userId,
		java.util.List<java.lang.Long> folderIds, long classNameId,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<com.liferay.portlet.journal.model.JournalArticle> queryDefinition) {
		return getFinder()
				   .countByG_U_F_C(groupId, userId, folderIds, classNameId,
			queryDefinition);
	}

	public static int countByC_G_F_C_A_V_T_D_C_S_T_D_R(long companyId,
		long groupId, java.util.List<java.lang.Long> folderIds,
		long classNameId, java.lang.String articleId, java.lang.Double version,
		java.lang.String title, java.lang.String description,
		java.lang.String content, java.lang.String ddmStructureKey,
		java.lang.String ddmTemplateKey, java.util.Date displayDateGT,
		java.util.Date displayDateLT, java.util.Date reviewDate,
		boolean andOperator,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<com.liferay.portlet.journal.model.JournalArticle> queryDefinition) {
		return getFinder()
				   .countByC_G_F_C_A_V_T_D_C_S_T_D_R(companyId, groupId,
			folderIds, classNameId, articleId, version, title, description,
			content, ddmStructureKey, ddmTemplateKey, displayDateGT,
			displayDateLT, reviewDate, andOperator, queryDefinition);
	}

	public static int countByC_G_F_C_A_V_T_D_C_S_T_D_R(long companyId,
		long groupId, java.util.List<java.lang.Long> folderIds,
		long classNameId, java.lang.String articleId, java.lang.Double version,
		java.lang.String title, java.lang.String description,
		java.lang.String content, java.lang.String[] ddmStructureKeys,
		java.lang.String[] ddmTemplateKeys, java.util.Date displayDateGT,
		java.util.Date displayDateLT, java.util.Date reviewDate,
		boolean andOperator,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<com.liferay.portlet.journal.model.JournalArticle> queryDefinition) {
		return getFinder()
				   .countByC_G_F_C_A_V_T_D_C_S_T_D_R(companyId, groupId,
			folderIds, classNameId, articleId, version, title, description,
			content, ddmStructureKeys, ddmTemplateKeys, displayDateGT,
			displayDateLT, reviewDate, andOperator, queryDefinition);
	}

	public static int countByC_G_F_C_A_V_T_D_C_S_T_D_R(long companyId,
		long groupId, java.util.List<java.lang.Long> folderIds,
		long classNameId, java.lang.String[] articleIds,
		java.lang.Double version, java.lang.String[] titles,
		java.lang.String[] descriptions, java.lang.String[] contents,
		java.lang.String[] ddmStructureKeys,
		java.lang.String[] ddmTemplateKeys, java.util.Date displayDateGT,
		java.util.Date displayDateLT, java.util.Date reviewDate,
		boolean andOperator,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<com.liferay.portlet.journal.model.JournalArticle> queryDefinition) {
		return getFinder()
				   .countByC_G_F_C_A_V_T_D_C_S_T_D_R(companyId, groupId,
			folderIds, classNameId, articleIds, version, titles, descriptions,
			contents, ddmStructureKeys, ddmTemplateKeys, displayDateGT,
			displayDateLT, reviewDate, andOperator, queryDefinition);
	}

	public static int filterCountByKeywords(long companyId, long groupId,
		java.util.List<java.lang.Long> folderIds, long classNameId,
		java.lang.String keywords, java.lang.Double version,
		java.lang.String ddmStructureKey, java.lang.String ddmTemplateKey,
		java.util.Date displayDateGT, java.util.Date displayDateLT, int status,
		java.util.Date reviewDate) {
		return getFinder()
				   .filterCountByKeywords(companyId, groupId, folderIds,
			classNameId, keywords, version, ddmStructureKey, ddmTemplateKey,
			displayDateGT, displayDateLT, status, reviewDate);
	}

	public static int filterCountByG_F(long groupId,
		java.util.List<java.lang.Long> folderIds,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<com.liferay.portlet.journal.model.JournalArticle> queryDefinition) {
		return getFinder().filterCountByG_F(groupId, folderIds, queryDefinition);
	}

	public static int filterCountByG_C_S(long groupId, long classNameId,
		java.lang.String ddmStructureKey,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<com.liferay.portlet.journal.model.JournalArticle> queryDefinition) {
		return getFinder()
				   .filterCountByG_C_S(groupId, classNameId, ddmStructureKey,
			queryDefinition);
	}

	public static int filterCountByG_U_F_C(long groupId, long userId,
		java.util.List<java.lang.Long> folderIds, long classNameId,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<com.liferay.portlet.journal.model.JournalArticle> queryDefinition) {
		return getFinder()
				   .filterCountByG_U_F_C(groupId, userId, folderIds,
			classNameId, queryDefinition);
	}

	public static int filterCountByC_G_F_C_A_V_T_D_C_S_T_D_R(long companyId,
		long groupId, java.util.List<java.lang.Long> folderIds,
		long classNameId, java.lang.String articleId, java.lang.Double version,
		java.lang.String title, java.lang.String description,
		java.lang.String content, java.lang.String ddmStructureKey,
		java.lang.String ddmTemplateKey, java.util.Date displayDateGT,
		java.util.Date displayDateLT, java.util.Date reviewDate,
		boolean andOperator,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<com.liferay.portlet.journal.model.JournalArticle> queryDefinition) {
		return getFinder()
				   .filterCountByC_G_F_C_A_V_T_D_C_S_T_D_R(companyId, groupId,
			folderIds, classNameId, articleId, version, title, description,
			content, ddmStructureKey, ddmTemplateKey, displayDateGT,
			displayDateLT, reviewDate, andOperator, queryDefinition);
	}

	public static int filterCountByC_G_F_C_A_V_T_D_C_S_T_D_R(long companyId,
		long groupId, java.util.List<java.lang.Long> folderIds,
		long classNameId, java.lang.String articleId, java.lang.Double version,
		java.lang.String title, java.lang.String description,
		java.lang.String content, java.lang.String[] ddmStructureKeys,
		java.lang.String[] ddmTemplateKeys, java.util.Date displayDateGT,
		java.util.Date displayDateLT, java.util.Date reviewDate,
		boolean andOperator,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<com.liferay.portlet.journal.model.JournalArticle> queryDefinition) {
		return getFinder()
				   .filterCountByC_G_F_C_A_V_T_D_C_S_T_D_R(companyId, groupId,
			folderIds, classNameId, articleId, version, title, description,
			content, ddmStructureKeys, ddmTemplateKeys, displayDateGT,
			displayDateLT, reviewDate, andOperator, queryDefinition);
	}

	public static int filterCountByC_G_F_C_A_V_T_D_C_S_T_D_R(long companyId,
		long groupId, java.util.List<java.lang.Long> folderIds,
		long classNameId, java.lang.String[] articleIds,
		java.lang.Double version, java.lang.String[] titles,
		java.lang.String[] descriptions, java.lang.String[] contents,
		java.lang.String[] ddmStructureKeys,
		java.lang.String[] ddmTemplateKeys, java.util.Date displayDateGT,
		java.util.Date displayDateLT, java.util.Date reviewDate,
		boolean andOperator,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<com.liferay.portlet.journal.model.JournalArticle> queryDefinition) {
		return getFinder()
				   .filterCountByC_G_F_C_A_V_T_D_C_S_T_D_R(companyId, groupId,
			folderIds, classNameId, articleIds, version, titles, descriptions,
			contents, ddmStructureKeys, ddmTemplateKeys, displayDateGT,
			displayDateLT, reviewDate, andOperator, queryDefinition);
	}

	public static java.util.List<com.liferay.portlet.journal.model.JournalArticle> filterFindByKeywords(
		long companyId, long groupId, java.util.List<java.lang.Long> folderIds,
		long classNameId, java.lang.String keywords, java.lang.Double version,
		java.lang.String ddmStructureKey, java.lang.String ddmTemplateKey,
		java.util.Date displayDateGT, java.util.Date displayDateLT, int status,
		java.util.Date reviewDate, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.journal.model.JournalArticle> orderByComparator) {
		return getFinder()
				   .filterFindByKeywords(companyId, groupId, folderIds,
			classNameId, keywords, version, ddmStructureKey, ddmTemplateKey,
			displayDateGT, displayDateLT, status, reviewDate, start, end,
			orderByComparator);
	}

	public static java.util.List<com.liferay.portlet.journal.model.JournalArticle> filterFindByG_F(
		long groupId, java.util.List<java.lang.Long> folderIds,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<com.liferay.portlet.journal.model.JournalArticle> queryDefinition) {
		return getFinder().filterFindByG_F(groupId, folderIds, queryDefinition);
	}

	public static java.util.List<com.liferay.portlet.journal.model.JournalArticle> filterFindByG_C_S(
		long groupId, long classNameId, java.lang.String ddmStructureKey,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<com.liferay.portlet.journal.model.JournalArticle> queryDefinition) {
		return getFinder()
				   .filterFindByG_C_S(groupId, classNameId, ddmStructureKey,
			queryDefinition);
	}

	public static java.util.List<com.liferay.portlet.journal.model.JournalArticle> filterFindByG_U_F_C(
		long groupId, long userId, java.util.List<java.lang.Long> folderIds,
		long classNameId,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<com.liferay.portlet.journal.model.JournalArticle> queryDefinition) {
		return getFinder()
				   .filterFindByG_U_F_C(groupId, userId, folderIds,
			classNameId, queryDefinition);
	}

	public static java.util.List<com.liferay.portlet.journal.model.JournalArticle> filterFindByC_G_F_C_A_V_T_D_C_S_T_D_R(
		long companyId, long groupId, java.util.List<java.lang.Long> folderIds,
		long classNameId, java.lang.String articleId, java.lang.Double version,
		java.lang.String title, java.lang.String description,
		java.lang.String content, java.lang.String ddmStructureKey,
		java.lang.String ddmTemplateKey, java.util.Date displayDateGT,
		java.util.Date displayDateLT, java.util.Date reviewDate,
		boolean andOperator,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<com.liferay.portlet.journal.model.JournalArticle> queryDefinition) {
		return getFinder()
				   .filterFindByC_G_F_C_A_V_T_D_C_S_T_D_R(companyId, groupId,
			folderIds, classNameId, articleId, version, title, description,
			content, ddmStructureKey, ddmTemplateKey, displayDateGT,
			displayDateLT, reviewDate, andOperator, queryDefinition);
	}

	public static java.util.List<com.liferay.portlet.journal.model.JournalArticle> filterFindByC_G_F_C_A_V_T_D_C_S_T_D_R(
		long companyId, long groupId, java.util.List<java.lang.Long> folderIds,
		long classNameId, java.lang.String articleId, java.lang.Double version,
		java.lang.String title, java.lang.String description,
		java.lang.String content, java.lang.String[] ddmStructureKeys,
		java.lang.String[] ddmTemplateKeys, java.util.Date displayDateGT,
		java.util.Date displayDateLT, java.util.Date reviewDate,
		boolean andOperator,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<com.liferay.portlet.journal.model.JournalArticle> queryDefinition) {
		return getFinder()
				   .filterFindByC_G_F_C_A_V_T_D_C_S_T_D_R(companyId, groupId,
			folderIds, classNameId, articleId, version, title, description,
			content, ddmStructureKeys, ddmTemplateKeys, displayDateGT,
			displayDateLT, reviewDate, andOperator, queryDefinition);
	}

	public static java.util.List<com.liferay.portlet.journal.model.JournalArticle> filterFindByC_G_F_C_A_V_T_D_C_S_T_D_R(
		long companyId, long groupId, java.util.List<java.lang.Long> folderIds,
		long classNameId, java.lang.String[] articleIds,
		java.lang.Double version, java.lang.String[] titles,
		java.lang.String[] descriptions, java.lang.String[] contents,
		java.lang.String[] ddmStructureKeys,
		java.lang.String[] ddmTemplateKeys, java.util.Date displayDateGT,
		java.util.Date displayDateLT, java.util.Date reviewDate,
		boolean andOperator,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<com.liferay.portlet.journal.model.JournalArticle> queryDefinition) {
		return getFinder()
				   .filterFindByC_G_F_C_A_V_T_D_C_S_T_D_R(companyId, groupId,
			folderIds, classNameId, articleIds, version, titles, descriptions,
			contents, ddmStructureKeys, ddmTemplateKeys, displayDateGT,
			displayDateLT, reviewDate, andOperator, queryDefinition);
	}

	public static java.util.List<com.liferay.portlet.journal.model.JournalArticle> findByExpirationDate(
		long classNameId, java.util.Date expirationDateLT,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<com.liferay.portlet.journal.model.JournalArticle> queryDefinition) {
		return getFinder()
				   .findByExpirationDate(classNameId, expirationDateLT,
			queryDefinition);
	}

	public static java.util.List<com.liferay.portlet.journal.model.JournalArticle> findByKeywords(
		long companyId, long groupId, java.util.List<java.lang.Long> folderIds,
		long classNameId, java.lang.String keywords, java.lang.Double version,
		java.lang.String ddmStructureKey, java.lang.String ddmTemplateKey,
		java.util.Date displayDateGT, java.util.Date displayDateLT, int status,
		java.util.Date reviewDate, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.journal.model.JournalArticle> orderByComparator) {
		return getFinder()
				   .findByKeywords(companyId, groupId, folderIds, classNameId,
			keywords, version, ddmStructureKey, ddmTemplateKey, displayDateGT,
			displayDateLT, status, reviewDate, start, end, orderByComparator);
	}

	public static java.util.List<com.liferay.portlet.journal.model.JournalArticle> findByReviewDate(
		long classNameId, java.util.Date reviewDateLT,
		java.util.Date reviewDateGT) {
		return getFinder()
				   .findByReviewDate(classNameId, reviewDateLT, reviewDateGT);
	}

	public static com.liferay.portlet.journal.model.JournalArticle findByR_D(
		long resourcePrimKey, java.util.Date displayDate)
		throws com.liferay.portlet.journal.NoSuchArticleException {
		return getFinder().findByR_D(resourcePrimKey, displayDate);
	}

	public static java.util.List<com.liferay.portlet.journal.model.JournalArticle> findByG_F(
		long groupId, java.util.List<java.lang.Long> folderIds,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<com.liferay.portlet.journal.model.JournalArticle> queryDefinition) {
		return getFinder().findByG_F(groupId, folderIds, queryDefinition);
	}

	public static java.util.List<com.liferay.portlet.journal.model.JournalArticle> findByG_C_S(
		long groupId, long classNameId, java.lang.String ddmStructureKey,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<com.liferay.portlet.journal.model.JournalArticle> queryDefinition) {
		return getFinder()
				   .findByG_C_S(groupId, classNameId, ddmStructureKey,
			queryDefinition);
	}

	public static java.util.List<com.liferay.portlet.journal.model.JournalArticle> findByG_U_F_C(
		long groupId, long userId, java.util.List<java.lang.Long> folderIds,
		long classNameId,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<com.liferay.portlet.journal.model.JournalArticle> queryDefinition) {
		return getFinder()
				   .findByG_U_F_C(groupId, userId, folderIds, classNameId,
			queryDefinition);
	}

	public static java.util.List<com.liferay.portlet.journal.model.JournalArticle> findByC_G_F_C_A_V_T_D_C_S_T_D_R(
		long companyId, long groupId, java.util.List<java.lang.Long> folderIds,
		long classNameId, java.lang.String articleId, java.lang.Double version,
		java.lang.String title, java.lang.String description,
		java.lang.String content, java.lang.String ddmStructureKey,
		java.lang.String ddmTemplateKey, java.util.Date displayDateGT,
		java.util.Date displayDateLT, java.util.Date reviewDate,
		boolean andOperator,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<com.liferay.portlet.journal.model.JournalArticle> queryDefinition) {
		return getFinder()
				   .findByC_G_F_C_A_V_T_D_C_S_T_D_R(companyId, groupId,
			folderIds, classNameId, articleId, version, title, description,
			content, ddmStructureKey, ddmTemplateKey, displayDateGT,
			displayDateLT, reviewDate, andOperator, queryDefinition);
	}

	public static java.util.List<com.liferay.portlet.journal.model.JournalArticle> findByC_G_F_C_A_V_T_D_C_S_T_D_R(
		long companyId, long groupId, java.util.List<java.lang.Long> folderIds,
		long classNameId, java.lang.String articleId, java.lang.Double version,
		java.lang.String title, java.lang.String description,
		java.lang.String content, java.lang.String[] ddmStructureKeys,
		java.lang.String[] ddmTemplateKeys, java.util.Date displayDateGT,
		java.util.Date displayDateLT, java.util.Date reviewDate,
		boolean andOperator,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<com.liferay.portlet.journal.model.JournalArticle> queryDefinition) {
		return getFinder()
				   .findByC_G_F_C_A_V_T_D_C_S_T_D_R(companyId, groupId,
			folderIds, classNameId, articleId, version, title, description,
			content, ddmStructureKeys, ddmTemplateKeys, displayDateGT,
			displayDateLT, reviewDate, andOperator, queryDefinition);
	}

	public static java.util.List<com.liferay.portlet.journal.model.JournalArticle> findByC_G_F_C_A_V_T_D_C_S_T_D_R(
		long companyId, long groupId, java.util.List<java.lang.Long> folderIds,
		long classNameId, java.lang.String[] articleIds,
		java.lang.Double version, java.lang.String[] titles,
		java.lang.String[] descriptions, java.lang.String[] contents,
		java.lang.String[] ddmStructureKeys,
		java.lang.String[] ddmTemplateKeys, java.util.Date displayDateGT,
		java.util.Date displayDateLT, java.util.Date reviewDate,
		boolean andOperator,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<com.liferay.portlet.journal.model.JournalArticle> queryDefinition) {
		return getFinder()
				   .findByC_G_F_C_A_V_T_D_C_S_T_D_R(companyId, groupId,
			folderIds, classNameId, articleIds, version, titles, descriptions,
			contents, ddmStructureKeys, ddmTemplateKeys, displayDateGT,
			displayDateLT, reviewDate, andOperator, queryDefinition);
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