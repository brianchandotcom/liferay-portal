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

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * @generated
 */
@ProviderType
public class DLFileEntryFinderUtil {
	public static int countByExtraSettings() {
		return getFinder().countByExtraSettings();
	}

	public static int countByG_F(long groupId,
		java.util.List<java.lang.Long> folderIds,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<com.liferay.portlet.documentlibrary.model.DLFileEntry> queryDefinition) {
		return getFinder().countByG_F(groupId, folderIds, queryDefinition);
	}

	public static int countByG_M_R(long groupId,
		com.liferay.portal.kernel.util.DateRange dateRange, long repositoryId,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<com.liferay.portlet.documentlibrary.model.DLFileEntry> queryDefinition) {
		return getFinder()
				   .countByG_M_R(groupId, dateRange, repositoryId,
			queryDefinition);
	}

	public static int countByG_R_F(long groupId,
		java.util.List<java.lang.Long> repositoryIds,
		java.util.List<java.lang.Long> folderIds,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<com.liferay.portlet.documentlibrary.model.DLFileEntry> queryDefinition) {
		return getFinder()
				   .countByG_R_F(groupId, repositoryIds, folderIds,
			queryDefinition);
	}

	public static int countByG_U_F_M(long groupId, long userId,
		java.util.List<java.lang.Long> folderIds, java.lang.String[] mimeTypes,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<com.liferay.portlet.documentlibrary.model.DLFileEntry> queryDefinition) {
		return getFinder()
				   .countByG_U_F_M(groupId, userId, folderIds, mimeTypes,
			queryDefinition);
	}

	public static int countByG_U_R_F_M(long groupId, long userId,
		java.util.List<java.lang.Long> repositoryIds,
		java.util.List<java.lang.Long> folderIds, java.lang.String[] mimeTypes,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<com.liferay.portlet.documentlibrary.model.DLFileEntry> queryDefinition) {
		return getFinder()
				   .countByG_U_R_F_M(groupId, userId, repositoryIds, folderIds,
			mimeTypes, queryDefinition);
	}

	public static int filterCountByG_U_F_M(long groupId, long userId,
		java.util.List<java.lang.Long> folderIds, java.lang.String[] mimeTypes,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<com.liferay.portlet.documentlibrary.model.DLFileEntry> queryDefinition) {
		return getFinder()
				   .filterCountByG_U_F_M(groupId, userId, folderIds, mimeTypes,
			queryDefinition);
	}

	public static int filterCountByG_U_R_F_M(long groupId, long userId,
		java.util.List<java.lang.Long> repositoryIds,
		java.util.List<java.lang.Long> folderIds, java.lang.String[] mimeTypes,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<com.liferay.portlet.documentlibrary.model.DLFileEntry> queryDefinition) {
		return getFinder()
				   .filterCountByG_U_R_F_M(groupId, userId, repositoryIds,
			folderIds, mimeTypes, queryDefinition);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileEntry fetchByAnyImageId(
		long imageId) {
		return getFinder().fetchByAnyImageId(imageId);
	}

	public static int filterCountByG_F(long groupId,
		java.util.List<java.lang.Long> folderIds,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<com.liferay.portlet.documentlibrary.model.DLFileEntry> queryDefinition) {
		return getFinder().filterCountByG_F(groupId, folderIds, queryDefinition);
	}

	public static int filterCountByG_R_F(long groupId,
		java.util.List<java.lang.Long> repositoryIds,
		java.util.List<java.lang.Long> folderIds,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<com.liferay.portlet.documentlibrary.model.DLFileEntry> queryDefinition) {
		return getFinder()
				   .filterCountByG_R_F(groupId, repositoryIds, folderIds,
			queryDefinition);
	}

	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> filterFindByG_F(
		long groupId, java.util.List<java.lang.Long> folderIds,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<com.liferay.portlet.documentlibrary.model.DLFileEntry> queryDefinition) {
		return getFinder().filterFindByG_F(groupId, folderIds, queryDefinition);
	}

	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> filterFindByG_R_F(
		long groupId, java.util.List<java.lang.Long> repositoryIds,
		java.util.List<java.lang.Long> folderIds,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<com.liferay.portlet.documentlibrary.model.DLFileEntry> queryDefinition) {
		return getFinder()
				   .filterFindByG_R_F(groupId, repositoryIds, folderIds,
			queryDefinition);
	}

	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> filterFindByG_U_F_M(
		long groupId, long userId, java.util.List<java.lang.Long> folderIds,
		java.lang.String[] mimeTypes,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<com.liferay.portlet.documentlibrary.model.DLFileEntry> queryDefinition) {
		return getFinder()
				   .filterFindByG_U_F_M(groupId, userId, folderIds, mimeTypes,
			queryDefinition);
	}

	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> filterFindByG_U_R_F_M(
		long groupId, long userId,
		java.util.List<java.lang.Long> repositoryIds,
		java.util.List<java.lang.Long> folderIds, java.lang.String[] mimeTypes,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<com.liferay.portlet.documentlibrary.model.DLFileEntry> queryDefinition) {
		return getFinder()
				   .filterFindByG_U_R_F_M(groupId, userId, repositoryIds,
			folderIds, mimeTypes, queryDefinition);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileEntry findByAnyImageId(
		long imageId)
		throws com.liferay.portlet.documentlibrary.NoSuchFileEntryException {
		return getFinder().findByAnyImageId(imageId);
	}

	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> findByCompanyId(
		long companyId,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<com.liferay.portlet.documentlibrary.model.DLFileEntry> queryDefinition) {
		return getFinder().findByCompanyId(companyId, queryDefinition);
	}

	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> findByDDMStructureIds(
		long[] ddmStructureIds, int start, int end) {
		return getFinder().findByDDMStructureIds(ddmStructureIds, start, end);
	}

	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> findByExtraSettings(
		int start, int end) {
		return getFinder().findByExtraSettings(start, end);
	}

	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> findByMisversioned() {
		return getFinder().findByMisversioned();
	}

	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> findByNoAssets() {
		return getFinder().findByNoAssets();
	}

	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> findByOrphanedFileEntries() {
		return getFinder().findByOrphanedFileEntries();
	}

	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> findByG_F(
		long groupId, java.util.List<java.lang.Long> folderIds,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<com.liferay.portlet.documentlibrary.model.DLFileEntry> queryDefinition) {
		return getFinder().findByG_F(groupId, folderIds, queryDefinition);
	}

	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> findByG_R_F(
		long groupId, java.util.List<java.lang.Long> repositoryIds,
		java.util.List<java.lang.Long> folderIds,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<com.liferay.portlet.documentlibrary.model.DLFileEntry> queryDefinition) {
		return getFinder()
				   .findByG_R_F(groupId, repositoryIds, folderIds,
			queryDefinition);
	}

	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> findByG_U_F(
		long groupId, long userId, java.util.List<java.lang.Long> folderIds,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<com.liferay.portlet.documentlibrary.model.DLFileEntry> queryDefinition) {
		return getFinder()
				   .findByG_U_F(groupId, userId, folderIds, queryDefinition);
	}

	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> findByG_U_R_F(
		long groupId, long userId,
		java.util.List<java.lang.Long> repositoryIds,
		java.util.List<java.lang.Long> folderIds,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<com.liferay.portlet.documentlibrary.model.DLFileEntry> queryDefinition) {
		return getFinder()
				   .findByG_U_R_F(groupId, userId, repositoryIds, folderIds,
			queryDefinition);
	}

	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> findByG_U_F_M(
		long groupId, long userId, java.util.List<java.lang.Long> folderIds,
		java.lang.String[] mimeTypes,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<com.liferay.portlet.documentlibrary.model.DLFileEntry> queryDefinition) {
		return getFinder()
				   .findByG_U_F_M(groupId, userId, folderIds, mimeTypes,
			queryDefinition);
	}

	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> findByG_U_R_F_M(
		long groupId, long userId,
		java.util.List<java.lang.Long> repositoryIds,
		java.util.List<java.lang.Long> folderIds, java.lang.String[] mimeTypes,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<com.liferay.portlet.documentlibrary.model.DLFileEntry> queryDefinition) {
		return getFinder()
				   .findByG_U_R_F_M(groupId, userId, repositoryIds, folderIds,
			mimeTypes, queryDefinition);
	}

	public static DLFileEntryFinder getFinder() {
		if (_finder == null) {
			_finder = (DLFileEntryFinder)PortalBeanLocatorUtil.locate(DLFileEntryFinder.class.getName());

			ReferenceRegistry.registerReference(DLFileEntryFinderUtil.class,
				"_finder");
		}

		return _finder;
	}

	public void setFinder(DLFileEntryFinder finder) {
		_finder = finder;

		ReferenceRegistry.registerReference(DLFileEntryFinderUtil.class,
			"_finder");
	}

	private static DLFileEntryFinder _finder;
}