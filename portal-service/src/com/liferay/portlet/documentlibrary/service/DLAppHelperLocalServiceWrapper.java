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
 * Provides a wrapper for {@link DLAppHelperLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see DLAppHelperLocalService
 * @generated
 */
@ProviderType
public class DLAppHelperLocalServiceWrapper implements DLAppHelperLocalService,
	ServiceWrapper<DLAppHelperLocalService> {
	public DLAppHelperLocalServiceWrapper(
		DLAppHelperLocalService dlAppHelperLocalService) {
		_dlAppHelperLocalService = dlAppHelperLocalService;
	}

	@Override
	public void addFolder(long userId,
		com.liferay.portal.kernel.repository.model.Folder folder,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		_dlAppHelperLocalService.addFolder(userId, folder, serviceContext);
	}

	@Override
	public void cancelCheckOut(long userId,
		com.liferay.portal.kernel.repository.model.FileEntry fileEntry,
		com.liferay.portal.kernel.repository.model.FileVersion sourceFileVersion,
		com.liferay.portal.kernel.repository.model.FileVersion destinationFileVersion,
		com.liferay.portal.kernel.repository.model.FileVersion draftFileVersion,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		_dlAppHelperLocalService.cancelCheckOut(userId, fileEntry,
			sourceFileVersion, destinationFileVersion, draftFileVersion,
			serviceContext);
	}

	@Override
	public void checkAssetEntry(long userId,
		com.liferay.portal.kernel.repository.model.FileEntry fileEntry,
		com.liferay.portal.kernel.repository.model.FileVersion fileVersion)
		throws com.liferay.portal.kernel.exception.PortalException {
		_dlAppHelperLocalService.checkAssetEntry(userId, fileEntry, fileVersion);
	}

	@Override
	public void deleteFileEntry(
		com.liferay.portal.kernel.repository.model.FileEntry fileEntry)
		throws com.liferay.portal.kernel.exception.PortalException {
		_dlAppHelperLocalService.deleteFileEntry(fileEntry);
	}

	@Override
	public void deleteFolder(
		com.liferay.portal.kernel.repository.model.Folder folder)
		throws com.liferay.portal.kernel.exception.PortalException {
		_dlAppHelperLocalService.deleteFolder(folder);
	}

	@Override
	public void deleteRepositoryFileEntries(long repositoryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_dlAppHelperLocalService.deleteRepositoryFileEntries(repositoryId);
	}

	@Override
	public void getFileAsStream(long userId,
		com.liferay.portal.kernel.repository.model.FileEntry fileEntry,
		boolean incrementCounter) {
		_dlAppHelperLocalService.getFileAsStream(userId, fileEntry,
			incrementCounter);
	}

	@Override
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileShortcut> getFileShortcuts(
		long groupId, long folderId, boolean active, int status) {
		return _dlAppHelperLocalService.getFileShortcuts(groupId, folderId,
			active, status);
	}

	/**
	* @deprecated As of 6.2.0, replaced by {@link #getFileShortcuts(long, long,
	boolean, int)}
	*/
	@Deprecated
	@Override
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileShortcut> getFileShortcuts(
		long groupId, long folderId, int status) {
		return _dlAppHelperLocalService.getFileShortcuts(groupId, folderId,
			status);
	}

	@Override
	public int getFileShortcutsCount(long groupId, long folderId,
		boolean active, int status) {
		return _dlAppHelperLocalService.getFileShortcutsCount(groupId,
			folderId, active, status);
	}

	/**
	* @deprecated As of 6.2.0, replaced by {@link #getFileShortcutsCount(long,
	long, boolean, int)}
	*/
	@Deprecated
	@Override
	public int getFileShortcutsCount(long groupId, long folderId, int status) {
		return _dlAppHelperLocalService.getFileShortcutsCount(groupId,
			folderId, status);
	}

	@Override
	public java.util.List<com.liferay.portal.kernel.repository.model.FileEntry> getNoAssetFileEntries() {
		return _dlAppHelperLocalService.getNoAssetFileEntries();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _dlAppHelperLocalService.getOSGiServiceIdentifier();
	}

	/**
	* @deprecated As of 7.0.0, replaced by {@link
	#moveDependentsToTrash(DLFolder)}
	*/
	@Deprecated
	@Override
	public void moveDependentsToTrash(
		java.util.List<java.lang.Object> dlFileEntriesAndDLFolders,
		long trashEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_dlAppHelperLocalService.moveDependentsToTrash(dlFileEntriesAndDLFolders,
			trashEntryId);
	}

	@Override
	public void moveDependentsToTrash(
		com.liferay.portlet.documentlibrary.model.DLFolder dlFolder)
		throws com.liferay.portal.kernel.exception.PortalException {
		_dlAppHelperLocalService.moveDependentsToTrash(dlFolder);
	}

	@Override
	public com.liferay.portal.kernel.repository.model.FileEntry moveFileEntryFromTrash(
		long userId,
		com.liferay.portal.kernel.repository.model.FileEntry fileEntry,
		long newFolderId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _dlAppHelperLocalService.moveFileEntryFromTrash(userId,
			fileEntry, newFolderId, serviceContext);
	}

	/**
	* Moves the file entry to the recycle bin.
	*
	* @param userId the primary key of the user moving the file entry
	* @param fileEntry the file entry to be moved
	* @return the moved file entry
	*/
	@Override
	public com.liferay.portal.kernel.repository.model.FileEntry moveFileEntryToTrash(
		long userId,
		com.liferay.portal.kernel.repository.model.FileEntry fileEntry)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _dlAppHelperLocalService.moveFileEntryToTrash(userId, fileEntry);
	}

	@Override
	public com.liferay.portal.kernel.repository.model.FileShortcut moveFileShortcutFromTrash(
		long userId,
		com.liferay.portal.kernel.repository.model.FileShortcut fileShortcut,
		long newFolderId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _dlAppHelperLocalService.moveFileShortcutFromTrash(userId,
			fileShortcut, newFolderId, serviceContext);
	}

	/**
	* Moves the file shortcut to the recycle bin.
	*
	* @param userId the primary key of the user moving the file shortcut
	* @param fileShortcut the file shortcut to be moved
	* @return the moved file shortcut
	*/
	@Override
	public com.liferay.portal.kernel.repository.model.FileShortcut moveFileShortcutToTrash(
		long userId,
		com.liferay.portal.kernel.repository.model.FileShortcut fileShortcut)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _dlAppHelperLocalService.moveFileShortcutToTrash(userId,
			fileShortcut);
	}

	@Override
	public com.liferay.portal.kernel.repository.model.Folder moveFolderFromTrash(
		long userId, com.liferay.portal.kernel.repository.model.Folder folder,
		long parentFolderId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _dlAppHelperLocalService.moveFolderFromTrash(userId, folder,
			parentFolderId, serviceContext);
	}

	/**
	* Moves the folder to the recycle bin.
	*
	* @param userId the primary key of the user moving the folder
	* @param folder the folder to be moved
	* @return the moved folder
	*/
	@Override
	public com.liferay.portal.kernel.repository.model.Folder moveFolderToTrash(
		long userId, com.liferay.portal.kernel.repository.model.Folder folder)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _dlAppHelperLocalService.moveFolderToTrash(userId, folder);
	}

	/**
	* @deprecated As of 7.0.0, replaced by {@link
	#restoreDependentsFromTrash(DLFolder)}
	*/
	@Deprecated
	@Override
	public void restoreDependentsFromTrash(
		java.util.List<java.lang.Object> dlFileEntriesAndDLFolders)
		throws com.liferay.portal.kernel.exception.PortalException {
		_dlAppHelperLocalService.restoreDependentsFromTrash(dlFileEntriesAndDLFolders);
	}

	/**
	* @deprecated As of 7.0.0, replaced by {@link
	#restoreDependentsFromTrash(List)}
	*/
	@Deprecated
	@Override
	public void restoreDependentsFromTrash(
		java.util.List<java.lang.Object> dlFileEntriesAndDLFolders,
		long trashEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_dlAppHelperLocalService.restoreDependentsFromTrash(dlFileEntriesAndDLFolders,
			trashEntryId);
	}

	@Override
	public void restoreDependentsFromTrash(
		com.liferay.portlet.documentlibrary.model.DLFolder dlFolder)
		throws com.liferay.portal.kernel.exception.PortalException {
		_dlAppHelperLocalService.restoreDependentsFromTrash(dlFolder);
	}

	@Override
	public void restoreFileEntryFromTrash(long userId,
		com.liferay.portal.kernel.repository.model.FileEntry fileEntry)
		throws com.liferay.portal.kernel.exception.PortalException {
		_dlAppHelperLocalService.restoreFileEntryFromTrash(userId, fileEntry);
	}

	@Override
	public void restoreFileShortcutFromTrash(long userId,
		com.liferay.portal.kernel.repository.model.FileShortcut fileShortcut)
		throws com.liferay.portal.kernel.exception.PortalException {
		_dlAppHelperLocalService.restoreFileShortcutFromTrash(userId,
			fileShortcut);
	}

	@Override
	public void restoreFolderFromTrash(long userId,
		com.liferay.portal.kernel.repository.model.Folder folder)
		throws com.liferay.portal.kernel.exception.PortalException {
		_dlAppHelperLocalService.restoreFolderFromTrash(userId, folder);
	}

	@Override
	public com.liferay.portlet.asset.model.AssetEntry updateAsset(long userId,
		com.liferay.portal.kernel.repository.model.FileEntry fileEntry,
		com.liferay.portal.kernel.repository.model.FileVersion fileVersion,
		long[] assetCategoryIds, java.lang.String[] assetTagNames,
		long[] assetLinkEntryIds)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _dlAppHelperLocalService.updateAsset(userId, fileEntry,
			fileVersion, assetCategoryIds, assetTagNames, assetLinkEntryIds);
	}

	@Override
	public com.liferay.portlet.asset.model.AssetEntry updateAsset(long userId,
		com.liferay.portal.kernel.repository.model.FileEntry fileEntry,
		com.liferay.portal.kernel.repository.model.FileVersion fileVersion,
		long assetClassPk)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _dlAppHelperLocalService.updateAsset(userId, fileEntry,
			fileVersion, assetClassPk);
	}

	@Override
	public com.liferay.portlet.asset.model.AssetEntry updateAsset(long userId,
		com.liferay.portal.kernel.repository.model.Folder folder,
		long[] assetCategoryIds, java.lang.String[] assetTagNames,
		long[] assetLinkEntryIds)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _dlAppHelperLocalService.updateAsset(userId, folder,
			assetCategoryIds, assetTagNames, assetLinkEntryIds);
	}

	@Override
	public void updateFileEntry(long userId,
		com.liferay.portal.kernel.repository.model.FileEntry fileEntry,
		com.liferay.portal.kernel.repository.model.FileVersion sourceFileVersion,
		com.liferay.portal.kernel.repository.model.FileVersion destinationFileVersion,
		long assetClassPk)
		throws com.liferay.portal.kernel.exception.PortalException {
		_dlAppHelperLocalService.updateFileEntry(userId, fileEntry,
			sourceFileVersion, destinationFileVersion, assetClassPk);
	}

	@Override
	public void updateFileEntry(long userId,
		com.liferay.portal.kernel.repository.model.FileEntry fileEntry,
		com.liferay.portal.kernel.repository.model.FileVersion sourceFileVersion,
		com.liferay.portal.kernel.repository.model.FileVersion destinationFileVersion,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		_dlAppHelperLocalService.updateFileEntry(userId, fileEntry,
			sourceFileVersion, destinationFileVersion, serviceContext);
	}

	@Override
	public void updateFolder(long userId,
		com.liferay.portal.kernel.repository.model.Folder folder,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		_dlAppHelperLocalService.updateFolder(userId, folder, serviceContext);
	}

	@Override
	public void updateStatus(long userId,
		com.liferay.portal.kernel.repository.model.FileEntry fileEntry,
		com.liferay.portal.kernel.repository.model.FileVersion latestFileVersion,
		int oldStatus, int newStatus,
		com.liferay.portal.service.ServiceContext serviceContext,
		java.util.Map<java.lang.String, java.io.Serializable> workflowContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		_dlAppHelperLocalService.updateStatus(userId, fileEntry,
			latestFileVersion, oldStatus, newStatus, serviceContext,
			workflowContext);
	}

	@Override
	public DLAppHelperLocalService getWrappedService() {
		return _dlAppHelperLocalService;
	}

	@Override
	public void setWrappedService(
		DLAppHelperLocalService dlAppHelperLocalService) {
		_dlAppHelperLocalService = dlAppHelperLocalService;
	}

	private DLAppHelperLocalService _dlAppHelperLocalService;
}