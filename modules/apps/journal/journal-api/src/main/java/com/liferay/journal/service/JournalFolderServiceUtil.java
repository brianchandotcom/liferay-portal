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

package com.liferay.journal.service;

import com.liferay.journal.model.JournalFolder;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

/**
 * Provides the remote service utility for JournalFolder. This utility wraps
 * <code>com.liferay.journal.service.impl.JournalFolderServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see JournalFolderService
 * @generated
 */
public class JournalFolderServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.journal.service.impl.JournalFolderServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static JournalFolder addFolder(
			String externalReferenceCode, long groupId, long parentFolderId,
			String name, String description,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().addFolder(
			externalReferenceCode, groupId, parentFolderId, name, description,
			serviceContext);
	}

	public static void deleteFolder(long folderId) throws PortalException {
		getService().deleteFolder(folderId);
	}

	public static void deleteFolder(
			long folderId, boolean includeTrashedEntries)
		throws PortalException {

		getService().deleteFolder(folderId, includeTrashedEntries);
	}

	public static JournalFolder fetchFolder(long folderId)
		throws PortalException {

		return getService().fetchFolder(folderId);
	}

	public static List<com.liferay.dynamic.data.mapping.model.DDMStructure>
			getDDMStructures(
				long[] groupIds, long folderId, int restrictionType)
		throws PortalException {

		return getService().getDDMStructures(
			groupIds, folderId, restrictionType);
	}

	public static List<com.liferay.dynamic.data.mapping.model.DDMStructure>
			getDDMStructures(
				long[] groupIds, long folderId, int restrictionType,
				OrderByComparator
					<com.liferay.dynamic.data.mapping.model.DDMStructure>
						orderByComparator)
		throws PortalException {

		return getService().getDDMStructures(
			groupIds, folderId, restrictionType, orderByComparator);
	}

	public static JournalFolder getFolder(long folderId)
		throws PortalException {

		return getService().getFolder(folderId);
	}

	public static JournalFolder getFolderByExternalReferenceCode(
			long groupId, String externalReferenceCode)
		throws PortalException {

		return getService().getFolderByExternalReferenceCode(
			groupId, externalReferenceCode);
	}

	public static List<Long> getFolderIds(long groupId, long folderId)
		throws PortalException {

		return getService().getFolderIds(groupId, folderId);
	}

	public static List<JournalFolder> getFolders(long groupId) {
		return getService().getFolders(groupId);
	}

	public static List<JournalFolder> getFolders(
		long groupId, long parentFolderId) {

		return getService().getFolders(groupId, parentFolderId);
	}

	public static List<JournalFolder> getFolders(
		long groupId, long parentFolderId, int status) {

		return getService().getFolders(groupId, parentFolderId, status);
	}

	public static List<JournalFolder> getFolders(
		long groupId, long parentFolderId, int start, int end) {

		return getService().getFolders(groupId, parentFolderId, start, end);
	}

	public static List<JournalFolder> getFolders(
		long groupId, long parentFolderId, int status, int start, int end) {

		return getService().getFolders(
			groupId, parentFolderId, status, start, end);
	}

	public static List<Object> getFoldersAndArticles(
		long groupId, long folderId, int status, int start, int end,
		OrderByComparator<?> orderByComparator) {

		return getService().getFoldersAndArticles(
			groupId, folderId, status, start, end, orderByComparator);
	}

	public static List<Object> getFoldersAndArticles(
		long groupId, long folderId, int start, int end,
		OrderByComparator<?> orderByComparator) {

		return getService().getFoldersAndArticles(
			groupId, folderId, start, end, orderByComparator);
	}

	public static List<Object> getFoldersAndArticles(
		long groupId, long userId, long folderId, int status, int start,
		int end, OrderByComparator<?> orderByComparator) {

		return getService().getFoldersAndArticles(
			groupId, userId, folderId, status, start, end, orderByComparator);
	}

	public static List<Object> getFoldersAndArticles(
		long groupId, long userId, long folderId, int status,
		java.util.Locale locale, int start, int end,
		OrderByComparator<?> orderByComparator) {

		return getService().getFoldersAndArticles(
			groupId, userId, folderId, status, locale, start, end,
			orderByComparator);
	}

	public static List<Object> getFoldersAndArticles(
		long groupId, long userId, long folderId, long ddmStructureId,
		int status, java.util.Locale locale, int start, int end,
		OrderByComparator<?> orderByComparator) {

		return getService().getFoldersAndArticles(
			groupId, userId, folderId, ddmStructureId, status, locale, start,
			end, orderByComparator);
	}

	public static int getFoldersAndArticlesCount(
		long groupId, List<Long> folderIds, int status) {

		return getService().getFoldersAndArticlesCount(
			groupId, folderIds, status);
	}

	public static int getFoldersAndArticlesCount(long groupId, long folderId) {
		return getService().getFoldersAndArticlesCount(groupId, folderId);
	}

	public static int getFoldersAndArticlesCount(
		long groupId, long folderId, int status) {

		return getService().getFoldersAndArticlesCount(
			groupId, folderId, status);
	}

	public static int getFoldersAndArticlesCount(
		long groupId, long userId, long folderId, int status) {

		return getService().getFoldersAndArticlesCount(
			groupId, userId, folderId, status);
	}

	public static int getFoldersAndArticlesCount(
		long groupId, long userId, long folderId, long ddmStructureId,
		int status) {

		return getService().getFoldersAndArticlesCount(
			groupId, userId, folderId, ddmStructureId, status);
	}

	public static int getFoldersCount(long groupId, long parentFolderId) {
		return getService().getFoldersCount(groupId, parentFolderId);
	}

	public static int getFoldersCount(
		long groupId, long parentFolderId, int status) {

		return getService().getFoldersCount(groupId, parentFolderId, status);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static void getSubfolderIds(
		List<Long> folderIds, long groupId, long folderId, boolean recurse) {

		getService().getSubfolderIds(folderIds, groupId, folderId, recurse);
	}

	public static List<Long> getSubfolderIds(
		long groupId, long folderId, boolean recurse) {

		return getService().getSubfolderIds(groupId, folderId, recurse);
	}

	public static JournalFolder moveFolder(
			long folderId, long parentFolderId,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().moveFolder(
			folderId, parentFolderId, serviceContext);
	}

	public static JournalFolder moveFolderFromTrash(
			long folderId, long parentFolderId,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().moveFolderFromTrash(
			folderId, parentFolderId, serviceContext);
	}

	public static JournalFolder moveFolderToTrash(long folderId)
		throws PortalException {

		return getService().moveFolderToTrash(folderId);
	}

	public static void restoreFolderFromTrash(long folderId)
		throws PortalException {

		getService().restoreFolderFromTrash(folderId);
	}

	public static List<com.liferay.dynamic.data.mapping.model.DDMStructure>
			searchDDMStructures(
				long companyId, long[] groupIds, long folderId,
				int restrictionType, String keywords, int start, int end,
				OrderByComparator
					<com.liferay.dynamic.data.mapping.model.DDMStructure>
						orderByComparator)
		throws PortalException {

		return getService().searchDDMStructures(
			companyId, groupIds, folderId, restrictionType, keywords, start,
			end, orderByComparator);
	}

	public static int searchDDMStructuresCount(
			long companyId, long[] groupIds, long folderId, int restrictionType,
			String keywords)
		throws PortalException {

		return getService().searchDDMStructuresCount(
			companyId, groupIds, folderId, restrictionType, keywords);
	}

	public static void subscribe(long groupId, long folderId)
		throws PortalException {

		getService().subscribe(groupId, folderId);
	}

	public static void unsubscribe(long groupId, long folderId)
		throws PortalException {

		getService().unsubscribe(groupId, folderId);
	}

	public static JournalFolder updateFolder(
			long groupId, long folderId, long parentFolderId, String name,
			String description, boolean mergeWithParentFolder,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().updateFolder(
			groupId, folderId, parentFolderId, name, description,
			mergeWithParentFolder, serviceContext);
	}

	public static JournalFolder updateFolder(
			long groupId, long folderId, long parentFolderId, String name,
			String description, long[] ddmStructureIds, int restrictionType,
			boolean mergeWithParentFolder,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().updateFolder(
			groupId, folderId, parentFolderId, name, description,
			ddmStructureIds, restrictionType, mergeWithParentFolder,
			serviceContext);
	}

	public static JournalFolderService getService() {
		return _service;
	}

	private static volatile JournalFolderService _service;

}