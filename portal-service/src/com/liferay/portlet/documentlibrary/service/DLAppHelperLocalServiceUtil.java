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
 * Provides the local service utility for DLAppHelper. This utility wraps
 * {@link com.liferay.portlet.documentlibrary.service.impl.DLAppHelperLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see DLAppHelperLocalService
 * @see com.liferay.portlet.documentlibrary.service.base.DLAppHelperLocalServiceBaseImpl
 * @see com.liferay.portlet.documentlibrary.service.impl.DLAppHelperLocalServiceImpl
 * @generated
 */
@ProviderType
public class DLAppHelperLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.portlet.documentlibrary.service.impl.DLAppHelperLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static void addFolder(long userId,
		com.liferay.portal.kernel.repository.model.Folder folder,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().addFolder(userId, folder, serviceContext);
	}

	public static void cancelCheckOut(long userId,
		com.liferay.portal.kernel.repository.model.FileEntry fileEntry,
		com.liferay.portal.kernel.repository.model.FileVersion sourceFileVersion,
		com.liferay.portal.kernel.repository.model.FileVersion destinationFileVersion,
		com.liferay.portal.kernel.repository.model.FileVersion draftFileVersion,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService()
			.cancelCheckOut(userId, fileEntry, sourceFileVersion,
			destinationFileVersion, draftFileVersion, serviceContext);
	}

	public static void checkAssetEntry(long userId,
		com.liferay.portal.kernel.repository.model.FileEntry fileEntry,
		com.liferay.portal.kernel.repository.model.FileVersion fileVersion)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().checkAssetEntry(userId, fileEntry, fileVersion);
	}

	public static void deleteFileEntry(
		com.liferay.portal.kernel.repository.model.FileEntry fileEntry)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().deleteFileEntry(fileEntry);
	}

	public static void deleteFolder(
		com.liferay.portal.kernel.repository.model.Folder folder)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().deleteFolder(folder);
	}

	public static void deleteRepositoryFileEntries(long repositoryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().deleteRepositoryFileEntries(repositoryId);
	}

	public static void getFileAsStream(long userId,
		com.liferay.portal.kernel.repository.model.FileEntry fileEntry,
		boolean incrementCounter) {
		getService().getFileAsStream(userId, fileEntry, incrementCounter);
	}

	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileShortcut> getFileShortcuts(
		long groupId, long folderId, boolean active, int status) {
		return getService().getFileShortcuts(groupId, folderId, active, status);
	}

	/**
	* @deprecated As of 6.2.0, replaced by {@link #getFileShortcuts(long, long,
	boolean, int)}
	*/
	@Deprecated
	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileShortcut> getFileShortcuts(
		long groupId, long folderId, int status) {
		return getService().getFileShortcuts(groupId, folderId, status);
	}

	public static int getFileShortcutsCount(long groupId, long folderId,
		boolean active, int status) {
		return getService()
				   .getFileShortcutsCount(groupId, folderId, active, status);
	}

	/**
	* @deprecated As of 6.2.0, replaced by {@link #getFileShortcutsCount(long,
	long, boolean, int)}
	*/
	@Deprecated
	public static int getFileShortcutsCount(long groupId, long folderId,
		int status) {
		return getService().getFileShortcutsCount(groupId, folderId, status);
	}

	public static java.util.List<com.liferay.portal.kernel.repository.model.FileEntry> getNoAssetFileEntries() {
		return getService().getNoAssetFileEntries();
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
	* @deprecated As of 7.0.0, replaced by {@link
	#moveDependentsToTrash(DLFolder)}
	*/
	@Deprecated
	public static void moveDependentsToTrash(
		java.util.List<java.lang.Object> dlFileEntriesAndDLFolders,
		long trashEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService()
			.moveDependentsToTrash(dlFileEntriesAndDLFolders, trashEntryId);
	}

	public static void moveDependentsToTrash(
		com.liferay.portlet.documentlibrary.model.DLFolder dlFolder)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().moveDependentsToTrash(dlFolder);
	}

	public static com.liferay.portal.kernel.repository.model.FileEntry moveFileEntryFromTrash(
		long userId,
		com.liferay.portal.kernel.repository.model.FileEntry fileEntry,
		long newFolderId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .moveFileEntryFromTrash(userId, fileEntry, newFolderId,
			serviceContext);
	}

	/**
	* Moves the file entry to the recycle bin.
	*
	* @param userId the primary key of the user moving the file entry
	* @param fileEntry the file entry to be moved
	* @return the moved file entry
	*/
	public static com.liferay.portal.kernel.repository.model.FileEntry moveFileEntryToTrash(
		long userId,
		com.liferay.portal.kernel.repository.model.FileEntry fileEntry)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().moveFileEntryToTrash(userId, fileEntry);
	}

	public static com.liferay.portal.kernel.repository.model.FileShortcut moveFileShortcutFromTrash(
		long userId,
		com.liferay.portal.kernel.repository.model.FileShortcut fileShortcut,
		long newFolderId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .moveFileShortcutFromTrash(userId, fileShortcut,
			newFolderId, serviceContext);
	}

	/**
	* Moves the file shortcut to the recycle bin.
	*
	* @param userId the primary key of the user moving the file shortcut
	* @param fileShortcut the file shortcut to be moved
	* @return the moved file shortcut
	*/
	public static com.liferay.portal.kernel.repository.model.FileShortcut moveFileShortcutToTrash(
		long userId,
		com.liferay.portal.kernel.repository.model.FileShortcut fileShortcut)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().moveFileShortcutToTrash(userId, fileShortcut);
	}

	public static com.liferay.portal.kernel.repository.model.Folder moveFolderFromTrash(
		long userId, com.liferay.portal.kernel.repository.model.Folder folder,
		long parentFolderId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .moveFolderFromTrash(userId, folder, parentFolderId,
			serviceContext);
	}

	/**
	* Moves the folder to the recycle bin.
	*
	* @param userId the primary key of the user moving the folder
	* @param folder the folder to be moved
	* @return the moved folder
	*/
	public static com.liferay.portal.kernel.repository.model.Folder moveFolderToTrash(
		long userId, com.liferay.portal.kernel.repository.model.Folder folder)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().moveFolderToTrash(userId, folder);
	}

	/**
	* @deprecated As of 7.0.0, replaced by {@link
	#restoreDependentsFromTrash(DLFolder)}
	*/
	@Deprecated
	public static void restoreDependentsFromTrash(
		java.util.List<java.lang.Object> dlFileEntriesAndDLFolders)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().restoreDependentsFromTrash(dlFileEntriesAndDLFolders);
	}

	/**
	* @deprecated As of 7.0.0, replaced by {@link
	#restoreDependentsFromTrash(List)}
	*/
	@Deprecated
	public static void restoreDependentsFromTrash(
		java.util.List<java.lang.Object> dlFileEntriesAndDLFolders,
		long trashEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService()
			.restoreDependentsFromTrash(dlFileEntriesAndDLFolders, trashEntryId);
	}

	public static void restoreDependentsFromTrash(
		com.liferay.portlet.documentlibrary.model.DLFolder dlFolder)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().restoreDependentsFromTrash(dlFolder);
	}

	public static void restoreFileEntryFromTrash(long userId,
		com.liferay.portal.kernel.repository.model.FileEntry fileEntry)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().restoreFileEntryFromTrash(userId, fileEntry);
	}

	public static void restoreFileShortcutFromTrash(long userId,
		com.liferay.portal.kernel.repository.model.FileShortcut fileShortcut)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().restoreFileShortcutFromTrash(userId, fileShortcut);
	}

	public static void restoreFolderFromTrash(long userId,
		com.liferay.portal.kernel.repository.model.Folder folder)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().restoreFolderFromTrash(userId, folder);
	}

	public static com.liferay.portlet.asset.model.AssetEntry updateAsset(
		long userId,
		com.liferay.portal.kernel.repository.model.FileEntry fileEntry,
		com.liferay.portal.kernel.repository.model.FileVersion fileVersion,
		long[] assetCategoryIds, java.lang.String[] assetTagNames,
		long[] assetLinkEntryIds)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateAsset(userId, fileEntry, fileVersion,
			assetCategoryIds, assetTagNames, assetLinkEntryIds);
	}

	public static com.liferay.portlet.asset.model.AssetEntry updateAsset(
		long userId,
		com.liferay.portal.kernel.repository.model.FileEntry fileEntry,
		com.liferay.portal.kernel.repository.model.FileVersion fileVersion,
		long assetClassPk)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateAsset(userId, fileEntry, fileVersion, assetClassPk);
	}

	public static com.liferay.portlet.asset.model.AssetEntry updateAsset(
		long userId, com.liferay.portal.kernel.repository.model.Folder folder,
		long[] assetCategoryIds, java.lang.String[] assetTagNames,
		long[] assetLinkEntryIds)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateAsset(userId, folder, assetCategoryIds,
			assetTagNames, assetLinkEntryIds);
	}

	public static void updateFileEntry(long userId,
		com.liferay.portal.kernel.repository.model.FileEntry fileEntry,
		com.liferay.portal.kernel.repository.model.FileVersion sourceFileVersion,
		com.liferay.portal.kernel.repository.model.FileVersion destinationFileVersion,
		long assetClassPk)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService()
			.updateFileEntry(userId, fileEntry, sourceFileVersion,
			destinationFileVersion, assetClassPk);
	}

	public static void updateFileEntry(long userId,
		com.liferay.portal.kernel.repository.model.FileEntry fileEntry,
		com.liferay.portal.kernel.repository.model.FileVersion sourceFileVersion,
		com.liferay.portal.kernel.repository.model.FileVersion destinationFileVersion,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService()
			.updateFileEntry(userId, fileEntry, sourceFileVersion,
			destinationFileVersion, serviceContext);
	}

	public static void updateFolder(long userId,
		com.liferay.portal.kernel.repository.model.Folder folder,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().updateFolder(userId, folder, serviceContext);
	}

	public static void updateStatus(long userId,
		com.liferay.portal.kernel.repository.model.FileEntry fileEntry,
		com.liferay.portal.kernel.repository.model.FileVersion latestFileVersion,
		int oldStatus, int newStatus,
		com.liferay.portal.service.ServiceContext serviceContext,
		java.util.Map<java.lang.String, java.io.Serializable> workflowContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService()
			.updateStatus(userId, fileEntry, latestFileVersion, oldStatus,
			newStatus, serviceContext, workflowContext);
	}

	public static DLAppHelperLocalService getService() {
		if (_service == null) {
			_service = (DLAppHelperLocalService)PortalBeanLocatorUtil.locate(DLAppHelperLocalService.class.getName());

			ReferenceRegistry.registerReference(DLAppHelperLocalServiceUtil.class,
				"_service");
		}

		return _service;
	}

	private static DLAppHelperLocalService _service;
}