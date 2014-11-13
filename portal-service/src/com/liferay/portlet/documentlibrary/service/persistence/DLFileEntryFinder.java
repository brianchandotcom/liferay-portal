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

package com.liferay.portlet.documentlibrary.service.persistence;

import aQute.bnd.annotation.ProviderType;

/**
 * @generated
 */
@ProviderType
public interface DLFileEntryFinder {
	public int countByExtraSettings();

	public int countByG_F(long groupId,
		java.util.List<java.lang.Long> folderIds,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<com.liferay.portlet.documentlibrary.model.DLFileEntry> queryDefinition);

	public int countByG_M_R(long groupId,
		com.liferay.portal.kernel.util.DateRange dateRange, long repositoryId,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<com.liferay.portlet.documentlibrary.model.DLFileEntry> queryDefinition);

	public int countByG_R_F(long groupId,
		java.util.List<java.lang.Long> repositoryIds,
		java.util.List<java.lang.Long> folderIds,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<com.liferay.portlet.documentlibrary.model.DLFileEntry> queryDefinition);

	public int countByG_U_F_M(long groupId, long userId,
		java.util.List<java.lang.Long> folderIds, java.lang.String[] mimeTypes,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<com.liferay.portlet.documentlibrary.model.DLFileEntry> queryDefinition);

	public int countByG_U_R_F_M(long groupId, long userId,
		java.util.List<java.lang.Long> repositoryIds,
		java.util.List<java.lang.Long> folderIds, java.lang.String[] mimeTypes,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<com.liferay.portlet.documentlibrary.model.DLFileEntry> queryDefinition);

	public int filterCountByG_U_F_M(long groupId, long userId,
		java.util.List<java.lang.Long> folderIds, java.lang.String[] mimeTypes,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<com.liferay.portlet.documentlibrary.model.DLFileEntry> queryDefinition);

	public int filterCountByG_U_R_F_M(long groupId, long userId,
		java.util.List<java.lang.Long> repositoryIds,
		java.util.List<java.lang.Long> folderIds, java.lang.String[] mimeTypes,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<com.liferay.portlet.documentlibrary.model.DLFileEntry> queryDefinition);

	public com.liferay.portlet.documentlibrary.model.DLFileEntry fetchByAnyImageId(
		long imageId);

	public int filterCountByG_F(long groupId,
		java.util.List<java.lang.Long> folderIds,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<com.liferay.portlet.documentlibrary.model.DLFileEntry> queryDefinition);

	public int filterCountByG_R_F(long groupId,
		java.util.List<java.lang.Long> repositoryIds,
		java.util.List<java.lang.Long> folderIds,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<com.liferay.portlet.documentlibrary.model.DLFileEntry> queryDefinition);

	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> filterFindByG_F(
		long groupId, java.util.List<java.lang.Long> folderIds,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<com.liferay.portlet.documentlibrary.model.DLFileEntry> queryDefinition);

	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> filterFindByG_R_F(
		long groupId, java.util.List<java.lang.Long> repositoryIds,
		java.util.List<java.lang.Long> folderIds,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<com.liferay.portlet.documentlibrary.model.DLFileEntry> queryDefinition);

	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> filterFindByG_U_F_M(
		long groupId, long userId, java.util.List<java.lang.Long> folderIds,
		java.lang.String[] mimeTypes,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<com.liferay.portlet.documentlibrary.model.DLFileEntry> queryDefinition);

	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> filterFindByG_U_R_F_M(
		long groupId, long userId,
		java.util.List<java.lang.Long> repositoryIds,
		java.util.List<java.lang.Long> folderIds, java.lang.String[] mimeTypes,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<com.liferay.portlet.documentlibrary.model.DLFileEntry> queryDefinition);

	public com.liferay.portlet.documentlibrary.model.DLFileEntry findByAnyImageId(
		long imageId)
		throws com.liferay.portlet.documentlibrary.NoSuchFileEntryException;

	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> findByCompanyId(
		long companyId,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<com.liferay.portlet.documentlibrary.model.DLFileEntry> queryDefinition);

	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> findByDDMStructureIds(
		long[] ddmStructureIds, int start, int end);

	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> findByExtraSettings(
		int start, int end);

	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> findByMisversioned();

	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> findByNoAssets();

	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> findByOrphanedFileEntries();

	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> findByG_F(
		long groupId, java.util.List<java.lang.Long> folderIds,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<com.liferay.portlet.documentlibrary.model.DLFileEntry> queryDefinition);

	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> findByG_R_F(
		long groupId, java.util.List<java.lang.Long> repositoryIds,
		java.util.List<java.lang.Long> folderIds,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<com.liferay.portlet.documentlibrary.model.DLFileEntry> queryDefinition);

	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> findByG_U_F(
		long groupId, long userId, java.util.List<java.lang.Long> folderIds,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<com.liferay.portlet.documentlibrary.model.DLFileEntry> queryDefinition);

	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> findByG_U_R_F(
		long groupId, long userId,
		java.util.List<java.lang.Long> repositoryIds,
		java.util.List<java.lang.Long> folderIds,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<com.liferay.portlet.documentlibrary.model.DLFileEntry> queryDefinition);

	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> findByG_U_F_M(
		long groupId, long userId, java.util.List<java.lang.Long> folderIds,
		java.lang.String[] mimeTypes,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<com.liferay.portlet.documentlibrary.model.DLFileEntry> queryDefinition);

	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> findByG_U_R_F_M(
		long groupId, long userId,
		java.util.List<java.lang.Long> repositoryIds,
		java.util.List<java.lang.Long> folderIds, java.lang.String[] mimeTypes,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<com.liferay.portlet.documentlibrary.model.DLFileEntry> queryDefinition);
}