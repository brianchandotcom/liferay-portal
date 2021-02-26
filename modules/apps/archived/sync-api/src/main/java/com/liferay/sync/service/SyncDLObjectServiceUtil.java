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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.sync.model.SyncDLObject;

import java.util.List;
import java.util.Map;

/**
 * Provides the remote service utility for SyncDLObject. This utility wraps
 * <code>com.liferay.sync.service.impl.SyncDLObjectServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see SyncDLObjectService
 * @generated
 */
public class SyncDLObjectServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.sync.service.impl.SyncDLObjectServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static SyncDLObject addFileEntry(
			long repositoryId, long folderId, String sourceFileName,
			String mimeType, String title, String description, String changeLog,
			java.io.File file, String checksum,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().addFileEntry(
			repositoryId, folderId, sourceFileName, mimeType, title,
			description, changeLog, file, checksum, serviceContext);
	}

	public static SyncDLObject addFolder(
			long repositoryId, long parentFolderId, String name,
			String description,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().addFolder(
			repositoryId, parentFolderId, name, description, serviceContext);
	}

	public static SyncDLObject cancelCheckOut(long fileEntryId)
		throws PortalException {

		return getService().cancelCheckOut(fileEntryId);
	}

	public static SyncDLObject checkInFileEntry(
			long fileEntryId, boolean majorVersion, String changeLog,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().checkInFileEntry(
			fileEntryId, majorVersion, changeLog, serviceContext);
	}

	public static SyncDLObject checkOutFileEntry(
			long fileEntryId,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().checkOutFileEntry(fileEntryId, serviceContext);
	}

	public static SyncDLObject checkOutFileEntry(
			long fileEntryId, String owner, long expirationTime,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().checkOutFileEntry(
			fileEntryId, owner, expirationTime, serviceContext);
	}

	public static SyncDLObject copyFileEntry(
			long sourceFileEntryId, long repositoryId, long folderId,
			String sourceFileName, String title,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().copyFileEntry(
			sourceFileEntryId, repositoryId, folderId, sourceFileName, title,
			serviceContext);
	}

	public static List<SyncDLObject> getAllFolderSyncDLObjects(
			long repositoryId)
		throws PortalException {

		return getService().getAllFolderSyncDLObjects(repositoryId);
	}

	public static SyncDLObject getFileEntrySyncDLObject(
			long repositoryId, long folderId, String title)
		throws PortalException {

		return getService().getFileEntrySyncDLObject(
			repositoryId, folderId, title);
	}

	public static List<SyncDLObject> getFileEntrySyncDLObjects(
			long repositoryId, long folderId)
		throws PortalException {

		return getService().getFileEntrySyncDLObjects(repositoryId, folderId);
	}

	public static SyncDLObject getFolderSyncDLObject(long folderId)
		throws PortalException {

		return getService().getFolderSyncDLObject(folderId);
	}

	public static SyncDLObject getFolderSyncDLObject(
			long repositoryId, long parentFolderId, String name)
		throws PortalException {

		return getService().getFolderSyncDLObject(
			repositoryId, parentFolderId, name);
	}

	public static List<SyncDLObject> getFolderSyncDLObjects(
			long repositoryId, long parentFolderId)
		throws PortalException {

		return getService().getFolderSyncDLObjects(
			repositoryId, parentFolderId);
	}

	public static com.liferay.portal.kernel.model.Group getGroup(long groupId)
		throws PortalException {

		return getService().getGroup(groupId);
	}

	public static long getLatestModifiedTime() throws PortalException {
		return getService().getLatestModifiedTime();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static Object getSyncContext() throws PortalException {
		return getService().getSyncContext();
	}

	public static String getSyncDLObjectUpdate(
			long repositoryId, long lastAccessTime, int max)
		throws PortalException {

		return getService().getSyncDLObjectUpdate(
			repositoryId, lastAccessTime, max);
	}

	public static String getSyncDLObjectUpdate(
			long repositoryId, long lastAccessTime, int max,
			boolean retrieveFromCache)
		throws PortalException {

		return getService().getSyncDLObjectUpdate(
			repositoryId, lastAccessTime, max, retrieveFromCache);
	}

	public static String getSyncDLObjectUpdate(
			long repositoryId, long parentFolderId, long lastAccessTime)
		throws PortalException {

		return getService().getSyncDLObjectUpdate(
			repositoryId, parentFolderId, lastAccessTime);
	}

	public static List<com.liferay.portal.kernel.model.Group>
			getUserSitesGroups()
		throws PortalException {

		return getService().getUserSitesGroups();
	}

	public static SyncDLObject moveFileEntry(
			long fileEntryId, long newFolderId,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().moveFileEntry(
			fileEntryId, newFolderId, serviceContext);
	}

	public static SyncDLObject moveFileEntryToTrash(long fileEntryId)
		throws PortalException {

		return getService().moveFileEntryToTrash(fileEntryId);
	}

	public static SyncDLObject moveFolder(
			long folderId, long parentFolderId,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().moveFolder(
			folderId, parentFolderId, serviceContext);
	}

	public static SyncDLObject moveFolderToTrash(long folderId)
		throws PortalException {

		return getService().moveFolderToTrash(folderId);
	}

	public static SyncDLObject patchFileEntry(
			long fileEntryId, long sourceVersionId, String sourceFileName,
			String mimeType, String title, String description, String changeLog,
			boolean majorVersion, java.io.File deltaFile, String checksum,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().patchFileEntry(
			fileEntryId, sourceVersionId, sourceFileName, mimeType, title,
			description, changeLog, majorVersion, deltaFile, checksum,
			serviceContext);
	}

	public static SyncDLObject restoreFileEntryFromTrash(long fileEntryId)
		throws PortalException {

		return getService().restoreFileEntryFromTrash(fileEntryId);
	}

	public static SyncDLObject restoreFolderFromTrash(long folderId)
		throws PortalException {

		return getService().restoreFolderFromTrash(folderId);
	}

	public static Map<String, Object> updateFileEntries(java.io.File zipFile)
		throws PortalException {

		return getService().updateFileEntries(zipFile);
	}

	public static SyncDLObject updateFileEntry(
			long fileEntryId, String sourceFileName, String mimeType,
			String title, String description, String changeLog,
			boolean majorVersion, java.io.File file, String checksum,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().updateFileEntry(
			fileEntryId, sourceFileName, mimeType, title, description,
			changeLog, majorVersion, file, checksum, serviceContext);
	}

	public static SyncDLObject updateFolder(
			long folderId, String name, String description,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().updateFolder(
			folderId, name, description, serviceContext);
	}

	public static SyncDLObjectService getService() {
		return _service;
	}

	private static volatile SyncDLObjectService _service;

}