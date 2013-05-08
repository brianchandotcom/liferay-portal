/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link DLFolderService}.
 *
 * @author    Brian Wing Shun Chan
 * @see       DLFolderService
 * @generated
 */
public class DLFolderServiceWrapper implements DLFolderService,
	ServiceWrapper<DLFolderService> {
	public DLFolderServiceWrapper(DLFolderService dlFolderService) {
		_dlFolderService = dlFolderService;
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public java.lang.String getBeanIdentifier() {
		return _dlFolderService.getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_dlFolderService.setBeanIdentifier(beanIdentifier);
	}

	public com.liferay.portlet.documentlibrary.model.DLFolder addFolder(
		long groupId, long repositoryId, boolean mountPoint,
		long parentFolderId, java.lang.String name,
		java.lang.String description,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlFolderService.addFolder(groupId, repositoryId, mountPoint,
			parentFolderId, name, description, serviceContext);
	}

	public void deleteFolder(long folderId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_dlFolderService.deleteFolder(folderId);
	}

	public void deleteFolder(long folderId, boolean includeTrashedEntries)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_dlFolderService.deleteFolder(folderId, includeTrashedEntries);
	}

	public void deleteFolder(long groupId, long parentFolderId,
		java.lang.String name)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_dlFolderService.deleteFolder(groupId, parentFolderId, name);
	}

	public java.util.List<java.lang.Object> getFileEntriesAndFileShortcuts(
		long groupId, long folderId, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlFolderService.getFileEntriesAndFileShortcuts(groupId,
			folderId, status, start, end);
	}

	public int getFileEntriesAndFileShortcutsCount(long groupId, long folderId,
		int status) throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFolderService.getFileEntriesAndFileShortcutsCount(groupId,
			folderId, status);
	}

	public int getFileEntriesAndFileShortcutsCount(long groupId, long folderId,
		int status, java.lang.String[] mimeTypes)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFolderService.getFileEntriesAndFileShortcutsCount(groupId,
			folderId, status, mimeTypes);
	}

	public com.liferay.portlet.documentlibrary.model.DLFolder getFolder(
		long folderId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlFolderService.getFolder(folderId);
	}

	public com.liferay.portlet.documentlibrary.model.DLFolder getFolder(
		long groupId, long parentFolderId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlFolderService.getFolder(groupId, parentFolderId, name);
	}

	public java.util.List<java.lang.Long> getFolderIds(long groupId,
		long folderId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlFolderService.getFolderIds(groupId, folderId);
	}

	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFolder> getFolders(
		long groupId, long parentFolderId, int status,
		boolean includeMountfolders, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlFolderService.getFolders(groupId, parentFolderId, status,
			includeMountfolders, start, end, obc);
	}

	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFolder> getFolders(
		long groupId, long parentFolderId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlFolderService.getFolders(groupId, parentFolderId, start, end,
			obc);
	}

	public java.util.List<java.lang.Object> getFoldersAndFileEntriesAndFileShortcuts(
		long groupId, long folderId, int status, boolean includeMountFolders,
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlFolderService.getFoldersAndFileEntriesAndFileShortcuts(groupId,
			folderId, status, includeMountFolders, start, end, obc);
	}

	public int getFoldersAndFileEntriesAndFileShortcuts(long groupId,
		long folderId, int status, java.lang.String[] mimeTypes,
		boolean includeMountFolders)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlFolderService.getFoldersAndFileEntriesAndFileShortcuts(groupId,
			folderId, status, mimeTypes, includeMountFolders);
	}

	public java.util.List<java.lang.Object> getFoldersAndFileEntriesAndFileShortcuts(
		long groupId, long folderId, int status, java.lang.String[] mimeTypes,
		boolean includeMountFolders, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlFolderService.getFoldersAndFileEntriesAndFileShortcuts(groupId,
			folderId, status, mimeTypes, includeMountFolders, start, end, obc);
	}

	public int getFoldersAndFileEntriesAndFileShortcutsCount(long groupId,
		long folderId, int status, boolean includeMountFolders)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFolderService.getFoldersAndFileEntriesAndFileShortcutsCount(groupId,
			folderId, status, includeMountFolders);
	}

	public int getFoldersAndFileEntriesAndFileShortcutsCount(long groupId,
		long folderId, int status, java.lang.String[] mimeTypes,
		boolean includeMountFolders)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFolderService.getFoldersAndFileEntriesAndFileShortcutsCount(groupId,
			folderId, status, mimeTypes, includeMountFolders);
	}

	public int getFoldersCount(long groupId, long parentFolderId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFolderService.getFoldersCount(groupId, parentFolderId);
	}

	public int getFoldersCount(long groupId, long parentFolderId, int status,
		boolean includeMountfolders)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFolderService.getFoldersCount(groupId, parentFolderId,
			status, includeMountfolders);
	}

	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFolder> getMountFolders(
		long groupId, long parentFolderId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlFolderService.getMountFolders(groupId, parentFolderId, start,
			end, obc);
	}

	public int getMountFoldersCount(long groupId, long parentFolderId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFolderService.getMountFoldersCount(groupId, parentFolderId);
	}

	public void getSubfolderIds(java.util.List<java.lang.Long> folderIds,
		long groupId, long folderId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_dlFolderService.getSubfolderIds(folderIds, groupId, folderId);
	}

	public java.util.List<java.lang.Long> getSubfolderIds(long groupId,
		long folderId, boolean recurse)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlFolderService.getSubfolderIds(groupId, folderId, recurse);
	}

	public boolean hasFolderLock(long folderId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlFolderService.hasFolderLock(folderId);
	}

	public boolean hasInheritableLock(long folderId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlFolderService.hasInheritableLock(folderId);
	}

	public boolean isFolderLocked(long folderId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFolderService.isFolderLocked(folderId);
	}

	public com.liferay.portal.model.Lock lockFolder(long folderId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlFolderService.lockFolder(folderId);
	}

	public com.liferay.portal.model.Lock lockFolder(long folderId,
		java.lang.String owner, boolean inheritable, long expirationTime)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlFolderService.lockFolder(folderId, owner, inheritable,
			expirationTime);
	}

	public com.liferay.portlet.documentlibrary.model.DLFolder moveFolder(
		long folderId, long parentFolderId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlFolderService.moveFolder(folderId, parentFolderId,
			serviceContext);
	}

	public com.liferay.portal.model.Lock refreshFolderLock(
		java.lang.String lockUuid, long companyId, long expirationTime)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlFolderService.refreshFolderLock(lockUuid, companyId,
			expirationTime);
	}

	public void unlockFolder(long groupId, long parentFolderId,
		java.lang.String name, java.lang.String lockUuid)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_dlFolderService.unlockFolder(groupId, parentFolderId, name, lockUuid);
	}

	public void unlockFolder(long folderId, java.lang.String lockUuid)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_dlFolderService.unlockFolder(folderId, lockUuid);
	}

	public com.liferay.portlet.documentlibrary.model.DLFolder updateFolder(
		long folderId, java.lang.String name, java.lang.String description,
		long defaultFileEntryTypeId,
		java.util.List<java.lang.Long> fileEntryTypeIds,
		boolean overrideFileEntryTypes,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlFolderService.updateFolder(folderId, name, description,
			defaultFileEntryTypeId, fileEntryTypeIds, overrideFileEntryTypes,
			serviceContext);
	}

	public boolean verifyInheritableLock(long folderId,
		java.lang.String lockUuid)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlFolderService.verifyInheritableLock(folderId, lockUuid);
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedService}
	 */
	public DLFolderService getWrappedDLFolderService() {
		return _dlFolderService;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #setWrappedService}
	 */
	public void setWrappedDLFolderService(DLFolderService dlFolderService) {
		_dlFolderService = dlFolderService;
	}

	public DLFolderService getWrappedService() {
		return _dlFolderService;
	}

	public void setWrappedService(DLFolderService dlFolderService) {
		_dlFolderService = dlFolderService;
	}

	private DLFolderService _dlFolderService;
}