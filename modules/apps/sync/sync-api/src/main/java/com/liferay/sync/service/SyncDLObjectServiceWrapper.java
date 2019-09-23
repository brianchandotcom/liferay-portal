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

package com.liferay.sync.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link SyncDLObjectService}.
 *
 * @author Brian Wing Shun Chan
 * @see SyncDLObjectService
 * @generated
 */
public class SyncDLObjectServiceWrapper
	implements ServiceWrapper<SyncDLObjectService>, SyncDLObjectService {

	public SyncDLObjectServiceWrapper(SyncDLObjectService syncDLObjectService) {
		_syncDLObjectService = syncDLObjectService;
	}

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link SyncDLObjectServiceUtil} to access the sync dl object remote service. Add custom service methods to <code>com.liferay.sync.service.impl.SyncDLObjectServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	@Override
	public com.liferay.sync.model.SyncDLObject addFileEntry(
			long repositoryId, long folderId, String sourceFileName,
			String mimeType, String title, String description, String changeLog,
			java.io.File file, String checksum,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _syncDLObjectService.addFileEntry(
			repositoryId, folderId, sourceFileName, mimeType, title,
			description, changeLog, file, checksum, serviceContext);
	}

	@Override
	public com.liferay.sync.model.SyncDLObject addFolder(
			long repositoryId, long parentFolderId, String name,
			String description,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _syncDLObjectService.addFolder(
			repositoryId, parentFolderId, name, description, serviceContext);
	}

	@Override
	public com.liferay.sync.model.SyncDLObject cancelCheckOut(long fileEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _syncDLObjectService.cancelCheckOut(fileEntryId);
	}

	@Override
	public com.liferay.sync.model.SyncDLObject checkInFileEntry(
			long fileEntryId, boolean majorVersion, String changeLog,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _syncDLObjectService.checkInFileEntry(
			fileEntryId, majorVersion, changeLog, serviceContext);
	}

	@Override
	public com.liferay.sync.model.SyncDLObject checkOutFileEntry(
			long fileEntryId,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _syncDLObjectService.checkOutFileEntry(
			fileEntryId, serviceContext);
	}

	@Override
	public com.liferay.sync.model.SyncDLObject checkOutFileEntry(
			long fileEntryId, String owner, long expirationTime,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _syncDLObjectService.checkOutFileEntry(
			fileEntryId, owner, expirationTime, serviceContext);
	}

	@Override
	public com.liferay.sync.model.SyncDLObject copyFileEntry(
			long sourceFileEntryId, long repositoryId, long folderId,
			String sourceFileName, String title,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _syncDLObjectService.copyFileEntry(
			sourceFileEntryId, repositoryId, folderId, sourceFileName, title,
			serviceContext);
	}

	@Override
	public java.util.List<com.liferay.sync.model.SyncDLObject>
			getAllFolderSyncDLObjects(long repositoryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _syncDLObjectService.getAllFolderSyncDLObjects(repositoryId);
	}

	@Override
	public com.liferay.sync.model.SyncDLObject getFileEntrySyncDLObject(
			long repositoryId, long folderId, String title)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _syncDLObjectService.getFileEntrySyncDLObject(
			repositoryId, folderId, title);
	}

	@Override
	public java.util.List<com.liferay.sync.model.SyncDLObject>
			getFileEntrySyncDLObjects(long repositoryId, long folderId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _syncDLObjectService.getFileEntrySyncDLObjects(
			repositoryId, folderId);
	}

	@Override
	public com.liferay.sync.model.SyncDLObject getFolderSyncDLObject(
			long folderId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _syncDLObjectService.getFolderSyncDLObject(folderId);
	}

	@Override
	public com.liferay.sync.model.SyncDLObject getFolderSyncDLObject(
			long repositoryId, long parentFolderId, String name)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _syncDLObjectService.getFolderSyncDLObject(
			repositoryId, parentFolderId, name);
	}

	@Override
	public java.util.List<com.liferay.sync.model.SyncDLObject>
			getFolderSyncDLObjects(long repositoryId, long parentFolderId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _syncDLObjectService.getFolderSyncDLObjects(
			repositoryId, parentFolderId);
	}

	@Override
	public com.liferay.portal.kernel.model.Group getGroup(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _syncDLObjectService.getGroup(groupId);
	}

	@Override
	public long getLatestModifiedTime()
		throws com.liferay.portal.kernel.exception.PortalException {

		return _syncDLObjectService.getLatestModifiedTime();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _syncDLObjectService.getOSGiServiceIdentifier();
	}

	@Override
	public Object getSyncContext()
		throws com.liferay.portal.kernel.exception.PortalException {

		return _syncDLObjectService.getSyncContext();
	}

	@Override
	public String getSyncDLObjectUpdate(
			long repositoryId, long lastAccessTime, int max)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _syncDLObjectService.getSyncDLObjectUpdate(
			repositoryId, lastAccessTime, max);
	}

	@Override
	public String getSyncDLObjectUpdate(
			long repositoryId, long lastAccessTime, int max,
			boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _syncDLObjectService.getSyncDLObjectUpdate(
			repositoryId, lastAccessTime, max, retrieveFromCache);
	}

	@Override
	public String getSyncDLObjectUpdate(
			long repositoryId, long parentFolderId, long lastAccessTime)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _syncDLObjectService.getSyncDLObjectUpdate(
			repositoryId, parentFolderId, lastAccessTime);
	}

	@Override
	public java.util.List<com.liferay.portal.kernel.model.Group>
			getUserSitesGroups()
		throws com.liferay.portal.kernel.exception.PortalException {

		return _syncDLObjectService.getUserSitesGroups();
	}

	@Override
	public com.liferay.sync.model.SyncDLObject moveFileEntry(
			long fileEntryId, long newFolderId,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _syncDLObjectService.moveFileEntry(
			fileEntryId, newFolderId, serviceContext);
	}

	@Override
	public com.liferay.sync.model.SyncDLObject moveFileEntryToTrash(
			long fileEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _syncDLObjectService.moveFileEntryToTrash(fileEntryId);
	}

	@Override
	public com.liferay.sync.model.SyncDLObject moveFolder(
			long folderId, long parentFolderId,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _syncDLObjectService.moveFolder(
			folderId, parentFolderId, serviceContext);
	}

	@Override
	public com.liferay.sync.model.SyncDLObject moveFolderToTrash(long folderId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _syncDLObjectService.moveFolderToTrash(folderId);
	}

	@Override
	public com.liferay.sync.model.SyncDLObject patchFileEntry(
			long fileEntryId, long sourceVersionId, String sourceFileName,
			String mimeType, String title, String description, String changeLog,
			boolean majorVersion, java.io.File deltaFile, String checksum,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _syncDLObjectService.patchFileEntry(
			fileEntryId, sourceVersionId, sourceFileName, mimeType, title,
			description, changeLog, majorVersion, deltaFile, checksum,
			serviceContext);
	}

	@Override
	public com.liferay.sync.model.SyncDLObject restoreFileEntryFromTrash(
			long fileEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _syncDLObjectService.restoreFileEntryFromTrash(fileEntryId);
	}

	@Override
	public com.liferay.sync.model.SyncDLObject restoreFolderFromTrash(
			long folderId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _syncDLObjectService.restoreFolderFromTrash(folderId);
	}

	@Override
	public java.util.Map<String, Object> updateFileEntries(java.io.File zipFile)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _syncDLObjectService.updateFileEntries(zipFile);
	}

	@Override
	public com.liferay.sync.model.SyncDLObject updateFileEntry(
			long fileEntryId, String sourceFileName, String mimeType,
			String title, String description, String changeLog,
			boolean majorVersion, java.io.File file, String checksum,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _syncDLObjectService.updateFileEntry(
			fileEntryId, sourceFileName, mimeType, title, description,
			changeLog, majorVersion, file, checksum, serviceContext);
	}

	@Override
	public com.liferay.sync.model.SyncDLObject updateFolder(
			long folderId, String name, String description,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _syncDLObjectService.updateFolder(
			folderId, name, description, serviceContext);
	}

	@Override
	public SyncDLObjectService getWrappedService() {
		return _syncDLObjectService;
	}

	@Override
	public void setWrappedService(SyncDLObjectService syncDLObjectService) {
		_syncDLObjectService = syncDLObjectService;
	}

	private SyncDLObjectService _syncDLObjectService;

}