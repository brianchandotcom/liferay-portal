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

	public int countByG_F(
		long groupId, java.util.List<Long> folderIds,
		com.liferay.portal.kernel.dao.orm.QueryDefinition
			<com.liferay.journal.model.JournalArticle> queryDefinition);

	public int countByG_ST(
		long groupId, int status,
		com.liferay.portal.kernel.dao.orm.QueryDefinition
			<com.liferay.journal.model.JournalArticle> queryDefinition);

	public int countByG_F_C(
		long groupId, java.util.List<Long> folderIds, long classNameId,
		com.liferay.portal.kernel.dao.orm.QueryDefinition
			<com.liferay.journal.model.JournalArticle> queryDefinition);

	public int countByG_F_C_S(
		long groupId, java.util.List<Long> folderIds, long classNameId,
		long ddmStructureId,
		com.liferay.portal.kernel.dao.orm.QueryDefinition
			<com.liferay.journal.model.JournalArticle> queryDefinition);

	public int filterCountByG_F(
		long groupId, java.util.List<Long> folderIds,
		com.liferay.portal.kernel.dao.orm.QueryDefinition
			<com.liferay.journal.model.JournalArticle> queryDefinition);

	public int filterCountByG_ST(
		long groupId, int status,
		com.liferay.portal.kernel.dao.orm.QueryDefinition
			<com.liferay.journal.model.JournalArticle> queryDefinition);

	public int filterCountByG_F_C(
		long groupId, java.util.List<Long> folderIds, long classNameId,
		com.liferay.portal.kernel.dao.orm.QueryDefinition
			<com.liferay.journal.model.JournalArticle> queryDefinition);

	public int filterCountByG_F_C_S(
		long groupId, java.util.List<Long> folderIds, long classNameId,
		long ddmStructureId,
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
		filterFindByG_F_C_L(
			long groupId, java.util.List<Long> folderIds, long classNameId,
			java.util.Locale locale,
			com.liferay.portal.kernel.dao.orm.QueryDefinition
				<com.liferay.journal.model.JournalArticle> queryDefinition);

	public java.util.List<com.liferay.journal.model.JournalArticle>
		filterFindByG_F_C_S_L(
			long groupId, java.util.List<Long> folderIds, long classNameId,
			long ddmStructureId, java.util.Locale locale,
			com.liferay.portal.kernel.dao.orm.QueryDefinition
				<com.liferay.journal.model.JournalArticle> queryDefinition);

	public java.util.List<com.liferay.journal.model.JournalArticle>
		findByNoAssets();

	public java.util.List<com.liferay.journal.model.JournalArticle>
		findByNoPermissions();

	public java.util.List<com.liferay.journal.model.JournalArticle>
		findByReviewDate(
			long classNameId, java.util.Date reviewDateLT,
			java.util.Date reviewDateGT);

	public java.util.List<com.liferay.journal.model.JournalArticle> findByG_F_L(
		long groupId, java.util.List<Long> folderIds, java.util.Locale locale,
		com.liferay.portal.kernel.dao.orm.QueryDefinition
			<com.liferay.journal.model.JournalArticle> queryDefinition);

	public java.util.List<com.liferay.journal.model.JournalArticle> findByG_F_C(
		long groupId, java.util.List<Long> folderIds, long classNameId,
		com.liferay.portal.kernel.dao.orm.QueryDefinition
			<com.liferay.journal.model.JournalArticle> queryDefinition);

	public java.util.List<com.liferay.journal.model.JournalArticle>
		findByG_F_C_S_L(
			long groupId, java.util.List<Long> folderIds, long classNameId,
			long ddmStructureId, java.util.Locale locale,
			com.liferay.portal.kernel.dao.orm.QueryDefinition
				<com.liferay.journal.model.JournalArticle> queryDefinition);

}