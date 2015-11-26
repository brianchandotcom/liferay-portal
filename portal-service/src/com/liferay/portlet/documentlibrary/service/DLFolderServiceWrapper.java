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

import com.liferay.portal.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link DLFolderService}.
 *
 * @author Brian Wing Shun Chan
 * @see DLFolderService
 * @generated
 */
@ProviderType
public class DLFolderServiceWrapper implements DLFolderService,
	ServiceWrapper<DLFolderService> {
	public DLFolderServiceWrapper(DLFolderService dlFolderService) {
		_dlFolderService = dlFolderService;
	}

	@Override
	public com.liferay.portlet.documentlibrary.model.DLFolder addFolder(
		long groupId, long repositoryId, boolean mountPoint,
		long parentFolderId, java.lang.String name,
		java.lang.String description,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _dlFolderService.addFolder(groupId, repositoryId, mountPoint,
			parentFolderId, name, description, serviceContext);
	}

	@Override
	public void deleteFolder(long folderId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_dlFolderService.deleteFolder(folderId);
	}

	@Override
	public void deleteFolder(long folderId, boolean includeTrashedEntries)
		throws com.liferay.portal.kernel.exception.PortalException {
		_dlFolderService.deleteFolder(folderId, includeTrashedEntries);
	}

	@Override
	public void deleteFolder(long groupId, long parentFolderId,
		java.lang.String name)
		throws com.liferay.portal.kernel.exception.PortalException {
		_dlFolderService.deleteFolder(groupId, parentFolderId, name);
	}

	@Override
	public java.util.List<java.lang.Object> getFileEntriesAndFileShortcuts(
		long groupId, long folderId, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _dlFolderService.getFileEntriesAndFileShortcuts(groupId,
			folderId, status, start, end);
	}

	@Override
	public int getFileEntriesAndFileShortcutsCount(long groupId, long folderId,
		int status) throws com.liferay.portal.kernel.exception.PortalException {
		return _dlFolderService.getFileEntriesAndFileShortcutsCount(groupId,
			folderId, status);
	}

	@Override
	public int getFileEntriesAndFileShortcutsCount(long groupId, long folderId,
		int status, java.lang.String[] mimeTypes)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _dlFolderService.getFileEntriesAndFileShortcutsCount(groupId,
			folderId, status, mimeTypes);
	}

	@Override
	public com.liferay.portlet.documentlibrary.model.DLFolder getFolder(
		long folderId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _dlFolderService.getFolder(folderId);
	}

	@Override
	public com.liferay.portlet.documentlibrary.model.DLFolder getFolder(
		long groupId, long parentFolderId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _dlFolderService.getFolder(groupId, parentFolderId, name);
	}

	@Override
	public java.util.List<java.lang.Long> getFolderIds(long groupId,
		long folderId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _dlFolderService.getFolderIds(groupId, folderId);
	}

	@Override
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFolder> getFolders(
		long groupId, long parentFolderId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.documentlibrary.model.DLFolder> obc)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _dlFolderService.getFolders(groupId, parentFolderId, start, end,
			obc);
	}

	@Override
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFolder> getFolders(
		long groupId, long parentFolderId, int status,
		boolean includeMountfolders, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.documentlibrary.model.DLFolder> obc)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _dlFolderService.getFolders(groupId, parentFolderId, status,
			includeMountfolders, start, end, obc);
	}

	@Override
	public java.util.List<java.lang.Object> getFoldersAndFileEntriesAndFileShortcuts(
		long groupId, long folderId, java.lang.String[] mimeTypes,
		boolean includeMountFolders,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<?> queryDefinition)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _dlFolderService.getFoldersAndFileEntriesAndFileShortcuts(groupId,
			folderId, mimeTypes, includeMountFolders, queryDefinition);
	}

	@Override
	public java.util.List<java.lang.Object> getFoldersAndFileEntriesAndFileShortcuts(
		long groupId, long folderId, int status, boolean includeMountFolders,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<?> obc)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _dlFolderService.getFoldersAndFileEntriesAndFileShortcuts(groupId,
			folderId, status, includeMountFolders, start, end, obc);
	}

	@Override
	public java.util.List<java.lang.Object> getFoldersAndFileEntriesAndFileShortcuts(
		long groupId, long folderId, int status, java.lang.String[] mimeTypes,
		boolean includeMountFolders, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<?> obc)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _dlFolderService.getFoldersAndFileEntriesAndFileShortcuts(groupId,
			folderId, status, mimeTypes, includeMountFolders, start, end, obc);
	}

	@Override
	public int getFoldersAndFileEntriesAndFileShortcutsCount(long groupId,
		long folderId, int status, boolean includeMountFolders)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _dlFolderService.getFoldersAndFileEntriesAndFileShortcutsCount(groupId,
			folderId, status, includeMountFolders);
	}

	@Override
	public int getFoldersAndFileEntriesAndFileShortcutsCount(long groupId,
		long folderId, int status, java.lang.String[] mimeTypes,
		boolean includeMountFolders)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _dlFolderService.getFoldersAndFileEntriesAndFileShortcutsCount(groupId,
			folderId, status, mimeTypes, includeMountFolders);
	}

	@Override
	public int getFoldersCount(long groupId, long parentFolderId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _dlFolderService.getFoldersCount(groupId, parentFolderId);
	}

	@Override
	public int getFoldersCount(long groupId, long parentFolderId, int status,
		boolean includeMountfolders)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _dlFolderService.getFoldersCount(groupId, parentFolderId,
			status, includeMountfolders);
	}

	@Override
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFolder> getMountFolders(
		long groupId, long parentFolderId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.documentlibrary.model.DLFolder> obc)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _dlFolderService.getMountFolders(groupId, parentFolderId, start,
			end, obc);
	}

	@Override
	public int getMountFoldersCount(long groupId, long parentFolderId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _dlFolderService.getMountFoldersCount(groupId, parentFolderId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _dlFolderService.getOSGiServiceIdentifier();
	}

	/**
	* @deprecated As of 7.0.0, replaced by {@link #getSubfolderIds(List, long,
	long, boolean)}
	*/
	@Deprecated
	@Override
	public void getSubfolderIds(java.util.List<java.lang.Long> folderIds,
		long groupId, long folderId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_dlFolderService.getSubfolderIds(folderIds, groupId, folderId);
	}

	@Override
	public void getSubfolderIds(java.util.List<java.lang.Long> folderIds,
		long groupId, long folderId, boolean recurse)
		throws com.liferay.portal.kernel.exception.PortalException {
		_dlFolderService.getSubfolderIds(folderIds, groupId, folderId, recurse);
	}

	@Override
	public java.util.List<java.lang.Long> getSubfolderIds(long groupId,
		long folderId, boolean recurse)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _dlFolderService.getSubfolderIds(groupId, folderId, recurse);
	}

	@Override
	public boolean hasFolderLock(long folderId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _dlFolderService.hasFolderLock(folderId);
	}

	@Override
	public boolean isFolderLocked(long folderId) {
		return _dlFolderService.isFolderLocked(folderId);
	}

	@Override
	public com.liferay.portal.kernel.lock.Lock lockFolder(long folderId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _dlFolderService.lockFolder(folderId);
	}

	@Override
	public com.liferay.portal.kernel.lock.Lock lockFolder(long folderId,
		java.lang.String owner, boolean inheritable, long expirationTime)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _dlFolderService.lockFolder(folderId, owner, inheritable,
			expirationTime);
	}

	@Override
	public com.liferay.portlet.documentlibrary.model.DLFolder moveFolder(
		long folderId, long parentFolderId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _dlFolderService.moveFolder(folderId, parentFolderId,
			serviceContext);
	}

	@Override
	public com.liferay.portal.kernel.lock.Lock refreshFolderLock(
		java.lang.String lockUuid, long companyId, long expirationTime)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _dlFolderService.refreshFolderLock(lockUuid, companyId,
			expirationTime);
	}

	@Override
	public void unlockFolder(long folderId, java.lang.String lockUuid)
		throws com.liferay.portal.kernel.exception.PortalException {
		_dlFolderService.unlockFolder(folderId, lockUuid);
	}

	@Override
	public void unlockFolder(long groupId, long parentFolderId,
		java.lang.String name, java.lang.String lockUuid)
		throws com.liferay.portal.kernel.exception.PortalException {
		_dlFolderService.unlockFolder(groupId, parentFolderId, name, lockUuid);
	}

	/**
	* @deprecated As of 7.0.0, replaced by more general {@link
	#updateFolder(long, String, String, long, List, int,
	ServiceContext)}
	*/
	@Deprecated
	@Override
	public com.liferay.portlet.documentlibrary.model.DLFolder updateFolder(
		long folderId, java.lang.String name, java.lang.String description,
		long defaultFileEntryTypeId,
		java.util.List<java.lang.Long> fileEntryTypeIds,
		boolean overrideFileEntryTypes,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _dlFolderService.updateFolder(folderId, name, description,
			defaultFileEntryTypeId, fileEntryTypeIds, overrideFileEntryTypes,
			serviceContext);
	}

	@Override
	public com.liferay.portlet.documentlibrary.model.DLFolder updateFolder(
		long folderId, java.lang.String name, java.lang.String description,
		long defaultFileEntryTypeId,
		java.util.List<java.lang.Long> fileEntryTypeIds, int restrictionType,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _dlFolderService.updateFolder(folderId, name, description,
			defaultFileEntryTypeId, fileEntryTypeIds, restrictionType,
			serviceContext);
	}

	@Override
	public com.liferay.portlet.documentlibrary.model.DLFolder updateFolder(
		long folderId, long parentFolderId, java.lang.String name,
		java.lang.String description, long defaultFileEntryTypeId,
		java.util.List<java.lang.Long> fileEntryTypeIds, int restrictionType,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _dlFolderService.updateFolder(folderId, parentFolderId, name,
			description, defaultFileEntryTypeId, fileEntryTypeIds,
			restrictionType, serviceContext);
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedService}
	 */
	@Deprecated
	public DLFolderService getWrappedDLFolderService() {
		return _dlFolderService;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #setWrappedService}
	 */
	@Deprecated
	public void setWrappedDLFolderService(DLFolderService dlFolderService) {
		_dlFolderService = dlFolderService;
	}

	@Override
	public DLFolderService getWrappedService() {
		return _dlFolderService;
	}

	@Override
	public void setWrappedService(DLFolderService dlFolderService) {
		_dlFolderService = dlFolderService;
	}

	private DLFolderService _dlFolderService;
}