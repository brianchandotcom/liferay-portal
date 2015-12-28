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

package com.liferay.portlet.documentlibrary.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * Provides the remote service utility for DLFolder. This utility wraps
 * {@link com.liferay.portlet.documentlibrary.service.impl.DLFolderServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on a remote server. Methods of this service are expected to have security
 * checks based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see DLFolderService
 * @see com.liferay.portlet.documentlibrary.service.base.DLFolderServiceBaseImpl
 * @see com.liferay.portlet.documentlibrary.service.impl.DLFolderServiceImpl
 * @generated
 */
@ProviderType
public class DLFolderServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.portlet.documentlibrary.service.impl.DLFolderServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.portlet.documentlibrary.model.DLFolder addFolder(
		long groupId, long repositoryId, boolean mountPoint,
		long parentFolderId, java.lang.String name,
		java.lang.String description,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addFolder(groupId, repositoryId, mountPoint,
			parentFolderId, name, description, serviceContext);
	}

	public static void deleteFolder(long folderId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().deleteFolder(folderId);
	}

	public static void deleteFolder(long folderId, boolean includeTrashedEntries)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().deleteFolder(folderId, includeTrashedEntries);
	}

	public static void deleteFolder(long groupId, long parentFolderId,
		java.lang.String name)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().deleteFolder(groupId, parentFolderId, name);
	}

	public static java.util.List<java.lang.Object> getFileEntriesAndFileShortcuts(
		long groupId, long folderId, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getFileEntriesAndFileShortcuts(groupId, folderId, status,
			start, end);
	}

	public static int getFileEntriesAndFileShortcutsCount(long groupId,
		long folderId, int status)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getFileEntriesAndFileShortcutsCount(groupId, folderId,
			status);
	}

	public static int getFileEntriesAndFileShortcutsCount(long groupId,
		long folderId, int status, java.lang.String[] mimeTypes)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getFileEntriesAndFileShortcutsCount(groupId, folderId,
			status, mimeTypes);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFolder getFolder(
		long folderId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getFolder(folderId);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFolder getFolder(
		long groupId, long parentFolderId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getFolder(groupId, parentFolderId, name);
	}

	public static java.util.List<java.lang.Long> getFolderIds(long groupId,
		long folderId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getFolderIds(groupId, folderId);
	}

	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFolder> getFolders(
		long groupId, long parentFolderId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.documentlibrary.model.DLFolder> obc)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getFolders(groupId, parentFolderId, start, end, obc);
	}

	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFolder> getFolders(
		long groupId, long parentFolderId, int status,
		boolean includeMountfolders, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.documentlibrary.model.DLFolder> obc)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getFolders(groupId, parentFolderId, status,
			includeMountfolders, start, end, obc);
	}

	public static java.util.List<java.lang.Object> getFoldersAndFileEntriesAndFileShortcuts(
		long groupId, long folderId, java.lang.String[] mimeTypes,
		boolean includeMountFolders,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<?> queryDefinition)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getFoldersAndFileEntriesAndFileShortcuts(groupId, folderId,
			mimeTypes, includeMountFolders, queryDefinition);
	}

	public static java.util.List<java.lang.Object> getFoldersAndFileEntriesAndFileShortcuts(
		long groupId, long folderId, int status, boolean includeMountFolders,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<?> obc)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getFoldersAndFileEntriesAndFileShortcuts(groupId, folderId,
			status, includeMountFolders, start, end, obc);
	}

	public static java.util.List<java.lang.Object> getFoldersAndFileEntriesAndFileShortcuts(
		long groupId, long folderId, int status, java.lang.String[] mimeTypes,
		boolean includeMountFolders, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<?> obc)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getFoldersAndFileEntriesAndFileShortcuts(groupId, folderId,
			status, mimeTypes, includeMountFolders, start, end, obc);
	}

	public static int getFoldersAndFileEntriesAndFileShortcutsCount(
		long groupId, long folderId, int status, boolean includeMountFolders)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getFoldersAndFileEntriesAndFileShortcutsCount(groupId,
			folderId, status, includeMountFolders);
	}

	public static int getFoldersAndFileEntriesAndFileShortcutsCount(
		long groupId, long folderId, int status, java.lang.String[] mimeTypes,
		boolean includeMountFolders)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getFoldersAndFileEntriesAndFileShortcutsCount(groupId,
			folderId, status, mimeTypes, includeMountFolders);
	}

	public static int getFoldersCount(long groupId, long parentFolderId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getFoldersCount(groupId, parentFolderId);
	}

	public static int getFoldersCount(long groupId, long parentFolderId,
		int status, boolean includeMountfolders)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getFoldersCount(groupId, parentFolderId, status,
			includeMountfolders);
	}

	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFolder> getMountFolders(
		long groupId, long parentFolderId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.documentlibrary.model.DLFolder> obc)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getMountFolders(groupId, parentFolderId, start, end, obc);
	}

	public static int getMountFoldersCount(long groupId, long parentFolderId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getMountFoldersCount(groupId, parentFolderId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static java.lang.String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	/**
	* @deprecated As of 7.0.0, replaced by {@link #getSubfolderIds(List, long,
	long, boolean)}
	*/
	@Deprecated
	public static void getSubfolderIds(
		java.util.List<java.lang.Long> folderIds, long groupId, long folderId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().getSubfolderIds(folderIds, groupId, folderId);
	}

	public static void getSubfolderIds(
		java.util.List<java.lang.Long> folderIds, long groupId, long folderId,
		boolean recurse)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().getSubfolderIds(folderIds, groupId, folderId, recurse);
	}

	public static java.util.List<java.lang.Long> getSubfolderIds(long groupId,
		long folderId, boolean recurse)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getSubfolderIds(groupId, folderId, recurse);
	}

	public static boolean hasFolderLock(long folderId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().hasFolderLock(folderId);
	}

	public static boolean hasInheritableLock(long folderId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().hasInheritableLock(folderId);
	}

	public static boolean isFolderLocked(long folderId) {
		return getService().isFolderLocked(folderId);
	}

	public static com.liferay.portal.kernel.lock.Lock lockFolder(long folderId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().lockFolder(folderId);
	}

	public static com.liferay.portal.kernel.lock.Lock lockFolder(
		long folderId, java.lang.String owner, boolean inheritable,
		long expirationTime)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .lockFolder(folderId, owner, inheritable, expirationTime);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFolder moveFolder(
		long folderId, long parentFolderId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().moveFolder(folderId, parentFolderId, serviceContext);
	}

	public static com.liferay.portal.kernel.lock.Lock refreshFolderLock(
		java.lang.String lockUuid, long companyId, long expirationTime)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .refreshFolderLock(lockUuid, companyId, expirationTime);
	}

	public static void unlockFolder(long folderId, java.lang.String lockUuid)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().unlockFolder(folderId, lockUuid);
	}

	public static void unlockFolder(long groupId, long parentFolderId,
		java.lang.String name, java.lang.String lockUuid)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().unlockFolder(groupId, parentFolderId, name, lockUuid);
	}

	/**
	* @deprecated As of 7.0.0, replaced by more general {@link
	#updateFolder(long, String, String, long, List, int,
	ServiceContext)}
	*/
	@Deprecated
	public static com.liferay.portlet.documentlibrary.model.DLFolder updateFolder(
		long folderId, java.lang.String name, java.lang.String description,
		long defaultFileEntryTypeId,
		java.util.List<java.lang.Long> fileEntryTypeIds,
		boolean overrideFileEntryTypes,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateFolder(folderId, name, description,
			defaultFileEntryTypeId, fileEntryTypeIds, overrideFileEntryTypes,
			serviceContext);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFolder updateFolder(
		long folderId, java.lang.String name, java.lang.String description,
		long defaultFileEntryTypeId,
		java.util.List<java.lang.Long> fileEntryTypeIds, int restrictionType,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateFolder(folderId, name, description,
			defaultFileEntryTypeId, fileEntryTypeIds, restrictionType,
			serviceContext);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFolder updateFolder(
		long folderId, long parentFolderId, java.lang.String name,
		java.lang.String description, long defaultFileEntryTypeId,
		java.util.List<java.lang.Long> fileEntryTypeIds, int restrictionType,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateFolder(folderId, parentFolderId, name, description,
			defaultFileEntryTypeId, fileEntryTypeIds, restrictionType,
			serviceContext);
	}

	public static boolean verifyInheritableLock(long folderId,
		java.lang.String lockUuid)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().verifyInheritableLock(folderId, lockUuid);
	}

	public static DLFolderService getService() {
		if (_service == null) {
			_service = (DLFolderService)PortalBeanLocatorUtil.locate(DLFolderService.class.getName());

			ReferenceRegistry.registerReference(DLFolderServiceUtil.class,
				"_service");
		}

		return _service;
	}

	private static DLFolderService _service;
}