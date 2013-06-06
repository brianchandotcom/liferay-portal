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
 * Provides a wrapper for {@link DLAppHelperLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see DLAppHelperLocalService
 * @generated
 */
public class DLAppHelperLocalServiceWrapper implements DLAppHelperLocalService,
	ServiceWrapper<DLAppHelperLocalService> {
	public DLAppHelperLocalServiceWrapper(
		DLAppHelperLocalService dlAppHelperLocalService) {
		_dlAppHelperLocalService = dlAppHelperLocalService;
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	@Override
	public java.lang.String getBeanIdentifier() {
		return _dlAppHelperLocalService.getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	@Override
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_dlAppHelperLocalService.setBeanIdentifier(beanIdentifier);
	}

	@Override
	public void addFileEntry(long userId,
		com.liferay.portal.kernel.repository.model.FileEntry fileEntry,
		com.liferay.portal.kernel.repository.model.FileVersion fileVersion,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_dlAppHelperLocalService.addFileEntry(userId, fileEntry, fileVersion,
			serviceContext);
	}

	@Override
	public void addFolder(long userId,
		com.liferay.portal.kernel.repository.model.Folder folder,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_dlAppHelperLocalService.addFolder(userId, folder, serviceContext);
	}

	@Override
	public void cancelCheckOut(long userId,
		com.liferay.portal.kernel.repository.model.FileEntry fileEntry,
		com.liferay.portal.kernel.repository.model.FileVersion sourceFileVersion,
		com.liferay.portal.kernel.repository.model.FileVersion destinationFileVersion,
		com.liferay.portal.kernel.repository.model.FileVersion draftFileVersion,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_dlAppHelperLocalService.cancelCheckOut(userId, fileEntry,
			sourceFileVersion, destinationFileVersion, draftFileVersion,
			serviceContext);
	}

	@Override
	public void checkAssetEntry(long userId,
		com.liferay.portal.kernel.repository.model.FileEntry fileEntry,
		com.liferay.portal.kernel.repository.model.FileVersion fileVersion)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_dlAppHelperLocalService.checkAssetEntry(userId, fileEntry, fileVersion);
	}

	@Override
	public void deleteFileEntry(
		com.liferay.portal.kernel.repository.model.FileEntry fileEntry)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_dlAppHelperLocalService.deleteFileEntry(fileEntry);
	}

	@Override
	public void deleteFolder(
		com.liferay.portal.kernel.repository.model.Folder folder)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_dlAppHelperLocalService.deleteFolder(folder);
	}

	@Override
	public void getFileAsStream(long userId,
		com.liferay.portal.kernel.repository.model.FileEntry fileEntry,
		boolean incrementCounter)
		throws com.liferay.portal.kernel.exception.SystemException {
		_dlAppHelperLocalService.getFileAsStream(userId, fileEntry,
			incrementCounter);
	}

	@Override
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileShortcut> getFileShortcuts(
		long groupId, long folderId, boolean active, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlAppHelperLocalService.getFileShortcuts(groupId, folderId,
			active, status);
	}

	/**
	* @deprecated As of 6.2.0, replaced by {@link #getFileShortcuts(long, long,
	boolean, int)}
	*/
	@Override
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileShortcut> getFileShortcuts(
		long groupId, long folderId, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlAppHelperLocalService.getFileShortcuts(groupId, folderId,
			status);
	}

	@Override
	public int getFileShortcutsCount(long groupId, long folderId,
		boolean active, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlAppHelperLocalService.getFileShortcutsCount(groupId,
			folderId, active, status);
	}

	/**
	* @deprecated As of 6.2.0, replaced by {@link #getFileShortcutsCount(long,
	long, boolean, int)}
	*/
	@Override
	public int getFileShortcutsCount(long groupId, long folderId, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlAppHelperLocalService.getFileShortcutsCount(groupId,
			folderId, status);
	}

	@Override
	public java.util.List<com.liferay.portal.kernel.repository.model.FileEntry> getNoAssetFileEntries() {
		return _dlAppHelperLocalService.getNoAssetFileEntries();
	}

	@Override
	public void moveFileEntry(
		com.liferay.portal.kernel.repository.model.FileEntry fileEntry)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_dlAppHelperLocalService.moveFileEntry(fileEntry);
	}

	@Override
	public com.liferay.portal.kernel.repository.model.FileEntry moveFileEntryFromTrash(
		long userId,
		com.liferay.portal.kernel.repository.model.FileEntry fileEntry,
		long newFolderId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlAppHelperLocalService.moveFileEntryFromTrash(userId,
			fileEntry, newFolderId, serviceContext);
	}

	/**
	* Moves the file entry to the recycle bin.
	*
	* @param userId the primary key of the user moving the file entry
	* @param fileEntry the file entry to be moved
	* @return the moved file entry
	* @throws PortalException if a user with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public com.liferay.portal.kernel.repository.model.FileEntry moveFileEntryToTrash(
		long userId,
		com.liferay.portal.kernel.repository.model.FileEntry fileEntry)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlAppHelperLocalService.moveFileEntryToTrash(userId, fileEntry);
	}

	@Override
	public com.liferay.portlet.documentlibrary.model.DLFileShortcut moveFileShortcutFromTrash(
		long userId,
		com.liferay.portlet.documentlibrary.model.DLFileShortcut dlFileShortcut,
		long newFolderId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlAppHelperLocalService.moveFileShortcutFromTrash(userId,
			dlFileShortcut, newFolderId, serviceContext);
	}

	/**
	* Moves the file shortcut to the recycle bin.
	*
	* @param userId the primary key of the user moving the file shortcut
	* @param dlFileShortcut the file shortcut to be moved
	* @return the moved file shortcut
	* @throws PortalException if a user with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public com.liferay.portlet.documentlibrary.model.DLFileShortcut moveFileShortcutToTrash(
		long userId,
		com.liferay.portlet.documentlibrary.model.DLFileShortcut dlFileShortcut)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlAppHelperLocalService.moveFileShortcutToTrash(userId,
			dlFileShortcut);
	}

	@Override
	public void moveFolder(
		com.liferay.portal.kernel.repository.model.Folder folder) {
		_dlAppHelperLocalService.moveFolder(folder);
	}

	@Override
	public com.liferay.portal.kernel.repository.model.Folder moveFolderFromTrash(
		long userId, com.liferay.portal.kernel.repository.model.Folder folder,
		long parentFolderId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlAppHelperLocalService.moveFolderFromTrash(userId, folder,
			parentFolderId, serviceContext);
	}

	/**
	* Moves the folder to the recycle bin.
	*
	* @param userId the primary key of the user moving the folder
	* @param folder the folder to be moved
	* @return the moved folder
	* @throws PortalException if a user with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public com.liferay.portal.kernel.repository.model.Folder moveFolderToTrash(
		long userId, com.liferay.portal.kernel.repository.model.Folder folder)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlAppHelperLocalService.moveFolderToTrash(userId, folder);
	}

	@Override
	public void restoreFileEntryFromTrash(long userId,
		com.liferay.portal.kernel.repository.model.FileEntry fileEntry)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_dlAppHelperLocalService.restoreFileEntryFromTrash(userId, fileEntry);
	}

	@Override
	public void restoreFileShortcutFromTrash(long userId,
		com.liferay.portlet.documentlibrary.model.DLFileShortcut dlFileShortcut)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_dlAppHelperLocalService.restoreFileShortcutFromTrash(userId,
			dlFileShortcut);
	}

	@Override
	public void restoreFolderFromTrash(long userId,
		com.liferay.portal.kernel.repository.model.Folder folder)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_dlAppHelperLocalService.restoreFolderFromTrash(userId, folder);
	}

	@Override
	public com.liferay.portlet.asset.model.AssetEntry updateAsset(long userId,
		com.liferay.portal.kernel.repository.model.FileEntry fileEntry,
		com.liferay.portal.kernel.repository.model.FileVersion fileVersion,
		long assetClassPk)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlAppHelperLocalService.updateAsset(userId, fileEntry,
			fileVersion, assetClassPk);
	}

	@Override
	public com.liferay.portlet.asset.model.AssetEntry updateAsset(long userId,
		com.liferay.portal.kernel.repository.model.FileEntry fileEntry,
		com.liferay.portal.kernel.repository.model.FileVersion fileVersion,
		long[] assetCategoryIds, java.lang.String[] assetTagNames,
		long[] assetLinkEntryIds)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlAppHelperLocalService.updateAsset(userId, fileEntry,
			fileVersion, assetCategoryIds, assetTagNames, assetLinkEntryIds);
	}

	@Override
	public com.liferay.portlet.asset.model.AssetEntry updateAsset(long userId,
		com.liferay.portal.kernel.repository.model.Folder folder,
		long[] assetCategoryIds, java.lang.String[] assetTagNames,
		long[] assetLinkEntryIds)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlAppHelperLocalService.updateAsset(userId, folder,
			assetCategoryIds, assetTagNames, assetLinkEntryIds);
	}

	@Override
	public void updateDependentStatus(com.liferay.portal.model.User user,
		java.util.List<java.lang.Object> dlFileEntriesAndDLFolders, int status)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_dlAppHelperLocalService.updateDependentStatus(user,
			dlFileEntriesAndDLFolders, status);
	}

	@Override
	public void updateFileEntry(long userId,
		com.liferay.portal.kernel.repository.model.FileEntry fileEntry,
		com.liferay.portal.kernel.repository.model.FileVersion sourceFileVersion,
		com.liferay.portal.kernel.repository.model.FileVersion destinationFileVersion,
		long assetClassPk)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_dlAppHelperLocalService.updateFileEntry(userId, fileEntry,
			sourceFileVersion, destinationFileVersion, assetClassPk);
	}

	@Override
	public void updateFileEntry(long userId,
		com.liferay.portal.kernel.repository.model.FileEntry fileEntry,
		com.liferay.portal.kernel.repository.model.FileVersion sourceFileVersion,
		com.liferay.portal.kernel.repository.model.FileVersion destinationFileVersion,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_dlAppHelperLocalService.updateFileEntry(userId, fileEntry,
			sourceFileVersion, destinationFileVersion, serviceContext);
	}

	@Override
	public void updateFolder(long userId,
		com.liferay.portal.kernel.repository.model.Folder folder,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_dlAppHelperLocalService.updateFolder(userId, folder, serviceContext);
	}

	@Override
	public void updateStatus(long userId,
		com.liferay.portal.kernel.repository.model.FileEntry fileEntry,
		com.liferay.portal.kernel.repository.model.FileVersion latestFileVersion,
		int oldStatus, int newStatus,
		java.util.Map<java.lang.String, java.io.Serializable> workflowContext,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_dlAppHelperLocalService.updateStatus(userId, fileEntry,
			latestFileVersion, oldStatus, newStatus, workflowContext,
			serviceContext);
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedService}
	 */
	public DLAppHelperLocalService getWrappedDLAppHelperLocalService() {
		return _dlAppHelperLocalService;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #setWrappedService}
	 */
	public void setWrappedDLAppHelperLocalService(
		DLAppHelperLocalService dlAppHelperLocalService) {
		_dlAppHelperLocalService = dlAppHelperLocalService;
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