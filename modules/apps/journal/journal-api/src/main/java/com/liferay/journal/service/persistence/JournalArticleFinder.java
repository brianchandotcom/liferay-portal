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

package com.liferay.journal.service.persistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
@ProviderType
public interface JournalArticleFinder {

	public int countByKeywords(
		long companyId, long groupId, java.util.List<Long> folderIds,
		long classNameId, String keywords, Double version,
		String ddmStructureKey, String ddmTemplateKey,
		java.util.Date displayDateGT, java.util.Date displayDateLT, int status,
		java.util.Date reviewDate);

	public int countByG_F(
		long groupId, java.util.List<Long> folderIds,
		com.liferay.portal.kernel.dao.orm.QueryDefinition
			<com.liferay.journal.model.JournalArticle> queryDefinition);

	public int countByG_ST(
		long groupId, int status,
		com.liferay.portal.kernel.dao.orm.QueryDefinition
			<com.liferay.journal.model.JournalArticle> queryDefinition);

	public int countByG_C_S(
		long groupId, long classNameId, String ddmStructureKey,
		com.liferay.portal.kernel.dao.orm.QueryDefinition
			<com.liferay.journal.model.JournalArticle> queryDefinition);

	public int countByG_F_C(
		long groupId, java.util.List<Long> folderIds, long classNameId,
		com.liferay.portal.kernel.dao.orm.QueryDefinition
			<com.liferay.journal.model.JournalArticle> queryDefinition);

	public int countByC_G_F_C_A_V_T_D_C_S_T_D_R(
		long companyId, long groupId, java.util.List<Long> folderIds,
		long classNameId, String articleId, Double version, String title,
		String description, String content, String ddmStructureKey,
		String ddmTemplateKey, java.util.Date displayDateGT,
		java.util.Date displayDateLT, java.util.Date reviewDate,
		boolean andOperator,
		com.liferay.portal.kernel.dao.orm.QueryDefinition
			<com.liferay.journal.model.JournalArticle> queryDefinition);

	public int countByC_G_F_C_A_V_T_D_C_S_T_D_R(
		long companyId, long groupId, java.util.List<Long> folderIds,
		long classNameId, String articleId, Double version, String title,
		String description, String content, String[] ddmStructureKeys,
		String[] ddmTemplateKeys, java.util.Date displayDateGT,
		java.util.Date displayDateLT, java.util.Date reviewDate,
		boolean andOperator,
		com.liferay.portal.kernel.dao.orm.QueryDefinition
			<com.liferay.journal.model.JournalArticle> queryDefinition);

	public int countByC_G_F_C_A_V_T_D_C_S_T_D_R(
		long companyId, long groupId, java.util.List<Long> folderIds,
		long classNameId, String[] articleIds, Double version, String[] titles,
		String[] descriptions, String[] contents, String[] ddmStructureKeys,
		String[] ddmTemplateKeys, java.util.Date displayDateGT,
		java.util.Date displayDateLT, java.util.Date reviewDate,
		boolean andOperator,
		com.liferay.portal.kernel.dao.orm.QueryDefinition
			<com.liferay.journal.model.JournalArticle> queryDefinition);

	public int filterCountByKeywords(
		long companyId, long groupId, java.util.List<Long> folderIds,
		long classNameId, String keywords, Double version,
		String ddmStructureKey, String ddmTemplateKey,
		java.util.Date displayDateGT, java.util.Date displayDateLT, int status,
		java.util.Date reviewDate);

	public int filterCountByG_F(
		long groupId, java.util.List<Long> folderIds,
		com.liferay.portal.kernel.dao.orm.QueryDefinition
			<com.liferay.journal.model.JournalArticle> queryDefinition);

	public int filterCountByG_ST(
		long groupId, int status,
		com.liferay.portal.kernel.dao.orm.QueryDefinition
			<com.liferay.journal.model.JournalArticle> queryDefinition);

	public int filterCountByG_C_S(
		long groupId, long classNameId, String ddmStructureKey,
		com.liferay.portal.kernel.dao.orm.QueryDefinition
			<com.liferay.journal.model.JournalArticle> queryDefinition);

	public int filterCountByG_F_C(
		long groupId, java.util.List<Long> folderIds, long classNameId,
		com.liferay.portal.kernel.dao.orm.QueryDefinition
			<com.liferay.journal.model.JournalArticle> queryDefinition);

	public int filterCountByC_G_F_C_A_V_T_D_C_S_T_D_R(
		long companyId, long groupId, java.util.List<Long> folderIds,
		long classNameId, String articleId, Double version, String title,
		String description, String content, String ddmStructureKey,
		String ddmTemplateKey, java.util.Date displayDateGT,
		java.util.Date displayDateLT, java.util.Date reviewDate,
		boolean andOperator,
		com.liferay.portal.kernel.dao.orm.QueryDefinition
			<com.liferay.journal.model.JournalArticle> queryDefinition);

	public int filterCountByC_G_F_C_A_V_T_D_C_S_T_D_R(
		long companyId, long groupId, java.util.List<Long> folderIds,
		long classNameId, String articleId, Double version, String title,
		String description, String content, String[] ddmStructureKeys,
		String[] ddmTemplateKeys, java.util.Date displayDateGT,
		java.util.Date displayDateLT, java.util.Date reviewDate,
		boolean andOperator,
		com.liferay.portal.kernel.dao.orm.QueryDefinition
			<com.liferay.journal.model.JournalArticle> queryDefinition);

	public int filterCountByC_G_F_C_A_V_T_D_C_S_T_D_R(
		long companyId, long groupId, java.util.List<Long> folderIds,
		long classNameId, String[] articleIds, Double version, String[] titles,
		String[] descriptions, String[] contents, String[] ddmStructureKeys,
		String[] ddmTemplateKeys, java.util.Date displayDateGT,
		java.util.Date displayDateLT, java.util.Date reviewDate,
		boolean andOperator,
		com.liferay.portal.kernel.dao.orm.QueryDefinition
			<com.liferay.journal.model.JournalArticle> queryDefinition);

	public java.util.List<com.liferay.journal.model.JournalArticle>
		filterFindByKeywords(
			long companyId, long groupId, java.util.List<Long> folderIds,
			long classNameId, String keywords, Double version,
			String ddmStructureKey, String ddmTemplateKey,
			java.util.Date displayDateGT, java.util.Date displayDateLT,
			int status, java.util.Date reviewDate, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.journal.model.JournalArticle> orderByComparator);

	public java.util.List<com.liferay.journal.model.JournalArticle>
		filterFindByG_F(
			long groupId, java.util.List<Long> folderIds,
			com.liferay.portal.kernel.dao.orm.QueryDefinition
				<com.liferay.journal.model.JournalArticle> queryDefinition);

	public java.util.List<com.liferay.journal.model.JournalArticle>
		filterFindByG_ST(
			long groupId, int status,
			com.liferay.portal.kernel.dao.orm.QueryDefinition
				<com.liferay.journal.model.JournalArticle> queryDefinition);

	public java.util.List<com.liferay.journal.model.JournalArticle>
		filterFindByG_ST_L(
			long groupId, int status, java.util.Locale locale,
			com.liferay.portal.kernel.dao.orm.QueryDefinition
				<com.liferay.journal.model.JournalArticle> queryDefinition);

	public java.util.List<com.liferay.journal.model.JournalArticle>
		filterFindByG_F_L(
			long groupId, java.util.List<Long> folderIds,
			java.util.Locale locale,
			com.liferay.portal.kernel.dao.orm.QueryDefinition
				<com.liferay.journal.model.JournalArticle> queryDefinition);

	public java.util.List<com.liferay.journal.model.JournalArticle>
		filterFindByG_C_S(
			long groupId, long classNameId, String ddmStructureKey,
			com.liferay.portal.kernel.dao.orm.QueryDefinition
				<com.liferay.journal.model.JournalArticle> queryDefinition);

	public java.util.List<com.liferay.journal.model.JournalArticle>
		filterFindByG_C_S(
			long groupId, long classNameId, String[] ddmStructureKeys,
			com.liferay.portal.kernel.dao.orm.QueryDefinition
				<com.liferay.journal.model.JournalArticle> queryDefinition);

	public java.util.List<com.liferay.journal.model.JournalArticle>
		filterFindByG_F_C(
			long groupId, java.util.List<Long> folderIds, long classNameId,
			com.liferay.portal.kernel.dao.orm.QueryDefinition
				<com.liferay.journal.model.JournalArticle> queryDefinition);

	public java.util.List<com.liferay.journal.model.JournalArticle>
		filterFindByG_C_S_L(
			long groupId, long classNameId, String ddmStructureKey,
			java.util.Locale locale,
			com.liferay.portal.kernel.dao.orm.QueryDefinition
				<com.liferay.journal.model.JournalArticle> queryDefinition);

	public java.util.List<com.liferay.journal.model.JournalArticle>
		filterFindByG_C_S_L(
			long groupId, long classNameId, String[] ddmStructureKeys,
			java.util.Locale locale,
			com.liferay.portal.kernel.dao.orm.QueryDefinition
				<com.liferay.journal.model.JournalArticle> queryDefinition);

	public java.util.List<com.liferay.journal.model.JournalArticle>
		filterFindByG_F_C_L(
			long groupId, java.util.List<Long> folderIds, long classNameId,
			java.util.Locale locale,
			com.liferay.portal.kernel.dao.orm.QueryDefinition
				<com.liferay.journal.model.JournalArticle> queryDefinition);

	public java.util.List<com.liferay.journal.model.JournalArticle>
		filterFindByC_G_F_C_A_V_T_D_C_S_T_D_R(
			long companyId, long groupId, java.util.List<Long> folderIds,
			long classNameId, String articleId, Double version, String title,
			String description, String content, String ddmStructureKey,
			String ddmTemplateKey, java.util.Date displayDateGT,
			java.util.Date displayDateLT, java.util.Date reviewDate,
			boolean andOperator,
			com.liferay.portal.kernel.dao.orm.QueryDefinition
				<com.liferay.journal.model.JournalArticle> queryDefinition);

	public java.util.List<com.liferay.journal.model.JournalArticle>
		filterFindByC_G_F_C_A_V_T_D_C_S_T_D_R(
			long companyId, long groupId, java.util.List<Long> folderIds,
			long classNameId, String articleId, Double version, String title,
			String description, String content, String[] ddmStructureKeys,
			String[] ddmTemplateKeys, java.util.Date displayDateGT,
			java.util.Date displayDateLT, java.util.Date reviewDate,
			boolean andOperator,
			com.liferay.portal.kernel.dao.orm.QueryDefinition
				<com.liferay.journal.model.JournalArticle> queryDefinition);

	public java.util.List<com.liferay.journal.model.JournalArticle>
		filterFindByC_G_F_C_A_V_T_D_C_S_T_D_R(
			long companyId, long groupId, java.util.List<Long> folderIds,
			long classNameId, String[] articleIds, Double version,
			String[] titles, String[] descriptions, String[] contents,
			String[] ddmStructureKeys, String[] ddmTemplateKeys,
			java.util.Date displayDateGT, java.util.Date displayDateLT,
			java.util.Date reviewDate, boolean andOperator,
			com.liferay.portal.kernel.dao.orm.QueryDefinition
				<com.liferay.journal.model.JournalArticle> queryDefinition);

	public java.util.List<com.liferay.journal.model.JournalArticle>
		findByExpirationDate(
			long classNameId, java.util.Date expirationDateLT,
			com.liferay.portal.kernel.dao.orm.QueryDefinition
				<com.liferay.journal.model.JournalArticle> queryDefinition);

	public java.util.List<com.liferay.journal.model.JournalArticle>
		findByKeywords(
			long companyId, long groupId, java.util.List<Long> folderIds,
			long classNameId, String keywords, Double version,
			String ddmStructureKey, String ddmTemplateKey,
			java.util.Date displayDateGT, java.util.Date displayDateLT,
			int status, java.util.Date reviewDate, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.journal.model.JournalArticle> orderByComparator);

	public java.util.List<com.liferay.journal.model.JournalArticle>
		findByNoAssets();

	public java.util.List<com.liferay.journal.model.JournalArticle>
		findByNoPermissions();

	public java.util.List<com.liferay.journal.model.JournalArticle>
		findByReviewDate(
			long classNameId, java.util.Date reviewDateLT,
			java.util.Date reviewDateGT);

	public com.liferay.journal.model.JournalArticle findByR_D(
			long resourcePrimKey, java.util.Date displayDate)
		throws com.liferay.journal.exception.NoSuchArticleException;

	public java.util.List<com.liferay.journal.model.JournalArticle> findByG_F(
		long groupId, java.util.List<Long> folderIds,
		com.liferay.portal.kernel.dao.orm.QueryDefinition
			<com.liferay.journal.model.JournalArticle> queryDefinition);

	public java.util.List<com.liferay.journal.model.JournalArticle> findByG_ST(
		long groupId, int status,
		com.liferay.portal.kernel.dao.orm.QueryDefinition
			<com.liferay.journal.model.JournalArticle> queryDefinition);

	public java.util.List<com.liferay.journal.model.JournalArticle>
		findByG_ST_L(
			long groupId, int status, java.util.Locale locale,
			com.liferay.portal.kernel.dao.orm.QueryDefinition
				<com.liferay.journal.model.JournalArticle> queryDefinition);

	public java.util.List<com.liferay.journal.model.JournalArticle> findByG_F_L(
		long groupId, java.util.List<Long> folderIds, java.util.Locale locale,
		com.liferay.portal.kernel.dao.orm.QueryDefinition
			<com.liferay.journal.model.JournalArticle> queryDefinition);

	public java.util.List<com.liferay.journal.model.JournalArticle> findByG_C_S(
		long groupId, long classNameId, String ddmStructureKey,
		com.liferay.portal.kernel.dao.orm.QueryDefinition
			<com.liferay.journal.model.JournalArticle> queryDefinition);

	public java.util.List<com.liferay.journal.model.JournalArticle> findByG_C_S(
		long groupId, long classNameId, String[] ddmStructureKeys,
		com.liferay.portal.kernel.dao.orm.QueryDefinition
			<com.liferay.journal.model.JournalArticle> queryDefinition);

	public java.util.List<com.liferay.journal.model.JournalArticle> findByG_F_C(
		long groupId, java.util.List<Long> folderIds, long classNameId,
		com.liferay.portal.kernel.dao.orm.QueryDefinition
			<com.liferay.journal.model.JournalArticle> queryDefinition);

	public java.util.List<com.liferay.journal.model.JournalArticle>
		findByG_C_S_L(
			long groupId, long classNameId, String ddmStructureKey,
			java.util.Locale locale,
			com.liferay.portal.kernel.dao.orm.QueryDefinition
				<com.liferay.journal.model.JournalArticle> queryDefinition);

	public java.util.List<com.liferay.journal.model.JournalArticle>
		findByG_C_S_L(
			long groupId, long classNameId, String[] ddmStructureKeys,
			java.util.Locale locale,
			com.liferay.portal.kernel.dao.orm.QueryDefinition
				<com.liferay.journal.model.JournalArticle> queryDefinition);

	public java.util.List<com.liferay.journal.model.JournalArticle>
		findByC_G_F_C_A_V_T_D_C_S_T_D_R(
			long companyId, long groupId, java.util.List<Long> folderIds,
			long classNameId, String articleId, Double version, String title,
			String description, String content, String ddmStructureKey,
			String ddmTemplateKey, java.util.Date displayDateGT,
			java.util.Date displayDateLT, java.util.Date reviewDate,
			boolean andOperator,
			com.liferay.portal.kernel.dao.orm.QueryDefinition
				<com.liferay.journal.model.JournalArticle> queryDefinition);

	public java.util.List<com.liferay.journal.model.JournalArticle>
		findByC_G_F_C_A_V_T_D_C_S_T_D_R(
			long companyId, long groupId, java.util.List<Long> folderIds,
			long classNameId, String articleId, Double version, String title,
			String description, String content, String[] ddmStructureKeys,
			String[] ddmTemplateKeys, java.util.Date displayDateGT,
			java.util.Date displayDateLT, java.util.Date reviewDate,
			boolean andOperator,
			com.liferay.portal.kernel.dao.orm.QueryDefinition
				<com.liferay.journal.model.JournalArticle> queryDefinition);

	public java.util.List<com.liferay.journal.model.JournalArticle>
		findByC_G_F_C_A_V_T_D_C_S_T_D_R(
			long companyId, long groupId, java.util.List<Long> folderIds,
			long classNameId, String[] articleIds, Double version,
			String[] titles, String[] descriptions, String[] contents,
			String[] ddmStructureKeys, String[] ddmTemplateKeys,
			java.util.Date displayDateGT, java.util.Date displayDateLT,
			java.util.Date reviewDate, boolean andOperator,
			com.liferay.portal.kernel.dao.orm.QueryDefinition
				<com.liferay.journal.model.JournalArticle> queryDefinition);

}