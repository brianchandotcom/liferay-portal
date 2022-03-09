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

package com.liferay.document.library.kernel.service;

import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

/**
 * Provides the remote service utility for DLFolder. This utility wraps
 * <code>com.liferay.portlet.documentlibrary.service.impl.DLFolderServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see DLFolderService
 * @generated
 */
public class DLFolderServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.portlet.documentlibrary.service.impl.DLFolderServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static DLFolder addFolder(
			long groupId, long repositoryId, boolean mountPoint,
			long parentFolderId, String name, String description,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().addFolder(
			groupId, repositoryId, mountPoint, parentFolderId, name,
			description, serviceContext);
	}

	public static void deleteFolder(long folderId) throws PortalException {
		getService().deleteFolder(folderId);
	}

	public static void deleteFolder(
			long folderId, boolean includeTrashedEntries)
		throws PortalException {

		getService().deleteFolder(folderId, includeTrashedEntries);
	}

	public static void deleteFolder(
			long groupId, long parentFolderId, String name)
		throws PortalException {

		getService().deleteFolder(groupId, parentFolderId, name);
	}

	public static List<Object> getFileEntriesAndFileShortcuts(
			long groupId, long folderId, int status, int start, int end)
		throws PortalException {

		return getService().getFileEntriesAndFileShortcuts(
			groupId, folderId, status, start, end);
	}

	public static int getFileEntriesAndFileShortcutsCount(
			long groupId, long folderId, int status)
		throws PortalException {

		return getService().getFileEntriesAndFileShortcutsCount(
			groupId, folderId, status);
	}

	public static int getFileEntriesAndFileShortcutsCount(
			long groupId, long folderId, String[] mimeTypes, int status)
		throws PortalException {

		return getService().getFileEntriesAndFileShortcutsCount(
			groupId, folderId, mimeTypes, status);
	}

	public static DLFolder getFolder(long folderId) throws PortalException {
		return getService().getFolder(folderId);
	}

	public static DLFolder getFolder(
			long groupId, long parentFolderId, String name)
		throws PortalException {

		return getService().getFolder(groupId, parentFolderId, name);
	}

	public static List<Long> getFolderIds(long groupId, long folderId)
		throws PortalException {

		return getService().getFolderIds(groupId, folderId);
	}

	public static List<DLFolder> getFolders(
			long groupId, long parentFolderId, boolean includeMountfolders,
			int status, int start, int end,
			OrderByComparator<DLFolder> orderByComparator)
		throws PortalException {

		return getService().getFolders(
			groupId, parentFolderId, includeMountfolders, status, start, end,
			orderByComparator);
	}

	public static List<DLFolder> getFolders(
			long groupId, long parentFolderId, int start, int end,
			OrderByComparator<DLFolder> orderByComparator)
		throws PortalException {

		return getService().getFolders(
			groupId, parentFolderId, start, end, orderByComparator);
	}

	public static List<Object> getFoldersAndFileEntriesAndFileShortcuts(
			long groupId, long folderId, boolean includeMountFolders,
			int status, int start, int end,
			OrderByComparator<?> orderByComparator)
		throws PortalException {

		return getService().getFoldersAndFileEntriesAndFileShortcuts(
			groupId, folderId, includeMountFolders, status, start, end,
			orderByComparator);
	}

	public static List<Object> getFoldersAndFileEntriesAndFileShortcuts(
			long groupId, long folderId, String[] mimeTypes,
			boolean includeMountFolders, int status, int start, int end,
			OrderByComparator<?> orderByComparator)
		throws PortalException {

		return getService().getFoldersAndFileEntriesAndFileShortcuts(
			groupId, folderId, mimeTypes, includeMountFolders, status, start,
			end, orderByComparator);
	}

	public static List<Object> getFoldersAndFileEntriesAndFileShortcuts(
			long groupId, long folderId, String[] mimeTypes,
			boolean includeMountFolders,
			com.liferay.portal.kernel.dao.orm.QueryDefinition<?>
				queryDefinition)
		throws PortalException {

		return getService().getFoldersAndFileEntriesAndFileShortcuts(
			groupId, folderId, mimeTypes, includeMountFolders, queryDefinition);
	}

	public static List<Object> getFoldersAndFileEntriesAndFileShortcuts(
			long groupId, long folderId, String[] mimeTypes,
			long fileEntryTypeId, boolean includeMountFolders, int status,
			int start, int end, OrderByComparator<?> orderByComparator)
		throws PortalException {

		return getService().getFoldersAndFileEntriesAndFileShortcuts(
			groupId, folderId, mimeTypes, fileEntryTypeId, includeMountFolders,
			status, start, end, orderByComparator);
	}

	public static int getFoldersAndFileEntriesAndFileShortcutsCount(
			long groupId, long folderId, int status,
			boolean includeMountFolders)
		throws PortalException {

		return getService().getFoldersAndFileEntriesAndFileShortcutsCount(
			groupId, folderId, status, includeMountFolders);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 #getFoldersAndFileEntriesAndFileShortcutsCount(long, long,
	 String[], boolean, int)}
	 */
	@Deprecated
	public static int getFoldersAndFileEntriesAndFileShortcutsCount(
			long groupId, long folderId, int status, String[] mimeTypes,
			boolean includeMountFolders)
		throws PortalException {

		return getService().getFoldersAndFileEntriesAndFileShortcutsCount(
			groupId, folderId, status, mimeTypes, includeMountFolders);
	}

	public static int getFoldersAndFileEntriesAndFileShortcutsCount(
			long groupId, long folderId, String[] mimeTypes,
			boolean includeMountFolders, int status)
		throws PortalException {

		return getService().getFoldersAndFileEntriesAndFileShortcutsCount(
			groupId, folderId, mimeTypes, includeMountFolders, status);
	}

	public static int getFoldersAndFileEntriesAndFileShortcutsCount(
			long groupId, long folderId, String[] mimeTypes,
			boolean includeMountFolders,
			com.liferay.portal.kernel.dao.orm.QueryDefinition<?>
				queryDefinition)
		throws PortalException {

		return getService().getFoldersAndFileEntriesAndFileShortcutsCount(
			groupId, folderId, mimeTypes, includeMountFolders, queryDefinition);
	}

	public static int getFoldersAndFileEntriesAndFileShortcutsCount(
			long groupId, long folderId, String[] mimeTypes,
			long fileEntryTypeId, boolean includeMountFolders, int status)
		throws PortalException {

		return getService().getFoldersAndFileEntriesAndFileShortcutsCount(
			groupId, folderId, mimeTypes, fileEntryTypeId, includeMountFolders,
			status);
	}

	public static int getFoldersCount(long groupId, long parentFolderId)
		throws PortalException {

		return getService().getFoldersCount(groupId, parentFolderId);
	}

	public static int getFoldersCount(
			long groupId, long parentFolderId, boolean includeMountfolders,
			int status)
		throws PortalException {

		return getService().getFoldersCount(
			groupId, parentFolderId, includeMountfolders, status);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 #getFoldersCount(long, long, boolean, int)}
	 */
	@Deprecated
	public static int getFoldersCount(
			long groupId, long parentFolderId, int status,
			boolean includeMountfolders)
		throws PortalException {

		return getService().getFoldersCount(
			groupId, parentFolderId, status, includeMountfolders);
	}

	public static List<DLFolder> getMountFolders(
			long groupId, long parentFolderId, int start, int end,
			OrderByComparator<DLFolder> orderByComparator)
		throws PortalException {

		return getService().getMountFolders(
			groupId, parentFolderId, start, end, orderByComparator);
	}

	public static int getMountFoldersCount(long groupId, long parentFolderId)
		throws PortalException {

		return getService().getMountFoldersCount(groupId, parentFolderId);
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
			List<Long> folderIds, long groupId, long folderId, boolean recurse)
		throws PortalException {

		getService().getSubfolderIds(folderIds, groupId, folderId, recurse);
	}

	public static List<Long> getSubfolderIds(
			long groupId, long folderId, boolean recurse)
		throws PortalException {

		return getService().getSubfolderIds(groupId, folderId, recurse);
	}

	public static boolean hasFolderLock(long folderId) throws PortalException {
		return getService().hasFolderLock(folderId);
	}

	public static boolean hasInheritableLock(long folderId)
		throws PortalException {

		return getService().hasInheritableLock(folderId);
	}

	public static boolean isFolderLocked(long folderId) {
		return getService().isFolderLocked(folderId);
	}

	public static com.liferay.portal.kernel.lock.Lock lockFolder(long folderId)
		throws PortalException {

		return getService().lockFolder(folderId);
	}

	public static com.liferay.portal.kernel.lock.Lock lockFolder(
			long folderId, String owner, boolean inheritable,
			long expirationTime)
		throws PortalException {

		return getService().lockFolder(
			folderId, owner, inheritable, expirationTime);
	}

	public static DLFolder moveFolder(
			long folderId, long parentFolderId,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().moveFolder(
			folderId, parentFolderId, serviceContext);
	}

	public static com.liferay.portal.kernel.lock.Lock refreshFolderLock(
			String lockUuid, long companyId, long expirationTime)
		throws PortalException {

		return getService().refreshFolderLock(
			lockUuid, companyId, expirationTime);
	}

	public static void unlockFolder(
			long groupId, long parentFolderId, String name, String lockUuid)
		throws PortalException {

		getService().unlockFolder(groupId, parentFolderId, name, lockUuid);
	}

	public static void unlockFolder(long folderId, String lockUuid)
		throws PortalException {

		getService().unlockFolder(folderId, lockUuid);
	}

	public static DLFolder updateFolder(
			long folderId, long parentFolderId, String name, String description,
			long defaultFileEntryTypeId, List<Long> fileEntryTypeIds,
			int restrictionType,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().updateFolder(
			folderId, parentFolderId, name, description, defaultFileEntryTypeId,
			fileEntryTypeIds, restrictionType, serviceContext);
	}

	public static DLFolder updateFolder(
			long folderId, String name, String description,
			long defaultFileEntryTypeId, List<Long> fileEntryTypeIds,
			int restrictionType,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().updateFolder(
			folderId, name, description, defaultFileEntryTypeId,
			fileEntryTypeIds, restrictionType, serviceContext);
	}

	public static boolean verifyInheritableLock(long folderId, String lockUuid)
		throws PortalException {

		return getService().verifyInheritableLock(folderId, lockUuid);
	}

	public static DLFolderService getService() {
		return _service;
	}

	private static volatile DLFolderService _service;

}