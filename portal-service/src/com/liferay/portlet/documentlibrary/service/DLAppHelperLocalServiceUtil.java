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
public class DLAppHelperLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.portlet.documentlibrary.service.impl.DLAppHelperLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public static java.lang.String getBeanIdentifier() {
		return getService().getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public static void setBeanIdentifier(java.lang.String beanIdentifier) {
		getService().setBeanIdentifier(beanIdentifier);
	}

	public static void addFileEntry(long userId,
		com.liferay.portal.kernel.repository.model.FileEntry fileEntry,
		com.liferay.portal.kernel.repository.model.FileVersion fileVersion,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().addFileEntry(userId, fileEntry, fileVersion, serviceContext);
	}

	public static void addFolder(long userId,
		com.liferay.portal.kernel.repository.model.Folder folder,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().addFolder(userId, folder, serviceContext);
	}

	public static void cancelCheckOut(long userId,
		com.liferay.portal.kernel.repository.model.FileEntry fileEntry,
		com.liferay.portal.kernel.repository.model.FileVersion sourceFileVersion,
		com.liferay.portal.kernel.repository.model.FileVersion destinationFileVersion,
		com.liferay.portal.kernel.repository.model.FileVersion draftFileVersion,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService()
			.cancelCheckOut(userId, fileEntry, sourceFileVersion,
			destinationFileVersion, draftFileVersion, serviceContext);
	}

	public static void checkAssetEntry(long userId,
		com.liferay.portal.kernel.repository.model.FileEntry fileEntry,
		com.liferay.portal.kernel.repository.model.FileVersion fileVersion)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().checkAssetEntry(userId, fileEntry, fileVersion);
	}

	public static void deleteFileEntry(
		com.liferay.portal.kernel.repository.model.FileEntry fileEntry)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteFileEntry(fileEntry);
	}

	public static void deleteFolder(
		com.liferay.portal.kernel.repository.model.Folder folder)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteFolder(folder);
	}

	public static void getFileAsStream(long userId,
		com.liferay.portal.kernel.repository.model.FileEntry fileEntry,
		boolean incrementCounter)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().getFileAsStream(userId, fileEntry, incrementCounter);
	}

	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileShortcut> getFileShortcuts(
		long groupId, long folderId, boolean active, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getFileShortcuts(groupId, folderId, active, status);
	}

	/**
	* @deprecated As of 6.2.0, replaced by {@link #getFileShortcuts(long, long,
	boolean, int)}
	*/
	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileShortcut> getFileShortcuts(
		long groupId, long folderId, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getFileShortcuts(groupId, folderId, status);
	}

	public static int getFileShortcutsCount(long groupId, long folderId,
		boolean active, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getFileShortcutsCount(groupId, folderId, active, status);
	}

	/**
	* @deprecated As of 6.2.0, replaced by {@link #getFileShortcutsCount(long,
	long, boolean, int)}
	*/
	public static int getFileShortcutsCount(long groupId, long folderId,
		int status) throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getFileShortcutsCount(groupId, folderId, status);
	}

	public static java.util.List<com.liferay.portal.kernel.repository.model.FileEntry> getNoAssetFileEntries() {
		return getService().getNoAssetFileEntries();
	}

	public static void moveFileEntry(
		com.liferay.portal.kernel.repository.model.FileEntry fileEntry)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().moveFileEntry(fileEntry);
	}

	public static com.liferay.portal.kernel.repository.model.FileEntry moveFileEntryFromTrash(
		long userId,
		com.liferay.portal.kernel.repository.model.FileEntry fileEntry,
		long newFolderId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
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
	* @throws PortalException if a user with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.kernel.repository.model.FileEntry moveFileEntryToTrash(
		long userId,
		com.liferay.portal.kernel.repository.model.FileEntry fileEntry)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().moveFileEntryToTrash(userId, fileEntry);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileShortcut moveFileShortcutFromTrash(
		long userId,
		com.liferay.portlet.documentlibrary.model.DLFileShortcut dlFileShortcut,
		long newFolderId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .moveFileShortcutFromTrash(userId, dlFileShortcut,
			newFolderId, serviceContext);
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
	public static com.liferay.portlet.documentlibrary.model.DLFileShortcut moveFileShortcutToTrash(
		long userId,
		com.liferay.portlet.documentlibrary.model.DLFileShortcut dlFileShortcut)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().moveFileShortcutToTrash(userId, dlFileShortcut);
	}

	public static void moveFolder(
		com.liferay.portal.kernel.repository.model.Folder folder) {
		getService().moveFolder(folder);
	}

	public static com.liferay.portal.kernel.repository.model.Folder moveFolderFromTrash(
		long userId, com.liferay.portal.kernel.repository.model.Folder folder,
		long parentFolderId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
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
	* @throws PortalException if a user with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.kernel.repository.model.Folder moveFolderToTrash(
		long userId, com.liferay.portal.kernel.repository.model.Folder folder)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().moveFolderToTrash(userId, folder);
	}

	public static void restoreFileEntryFromTrash(long userId,
		com.liferay.portal.kernel.repository.model.FileEntry fileEntry)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().restoreFileEntryFromTrash(userId, fileEntry);
	}

	public static void restoreFileShortcutFromTrash(long userId,
		com.liferay.portlet.documentlibrary.model.DLFileShortcut dlFileShortcut)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().restoreFileShortcutFromTrash(userId, dlFileShortcut);
	}

	public static void restoreFolderFromTrash(long userId,
		com.liferay.portal.kernel.repository.model.Folder folder)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().restoreFolderFromTrash(userId, folder);
	}

	public static com.liferay.portlet.asset.model.AssetEntry updateAsset(
		long userId,
		com.liferay.portal.kernel.repository.model.FileEntry fileEntry,
		com.liferay.portal.kernel.repository.model.FileVersion fileVersion,
		long assetClassPk)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .updateAsset(userId, fileEntry, fileVersion, assetClassPk);
	}

	public static com.liferay.portlet.asset.model.AssetEntry updateAsset(
		long userId,
		com.liferay.portal.kernel.repository.model.FileEntry fileEntry,
		com.liferay.portal.kernel.repository.model.FileVersion fileVersion,
		long[] assetCategoryIds, java.lang.String[] assetTagNames,
		long[] assetLinkEntryIds)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .updateAsset(userId, fileEntry, fileVersion,
			assetCategoryIds, assetTagNames, assetLinkEntryIds);
	}

	public static com.liferay.portlet.asset.model.AssetEntry updateAsset(
		long userId, com.liferay.portal.kernel.repository.model.Folder folder,
		long[] assetCategoryIds, java.lang.String[] assetTagNames,
		long[] assetLinkEntryIds)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .updateAsset(userId, folder, assetCategoryIds,
			assetTagNames, assetLinkEntryIds);
	}

	public static void updateDependentStatus(
		com.liferay.portal.model.User user,
		java.util.List<java.lang.Object> dlFileEntriesAndDLFolders, int status)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService()
			.updateDependentStatus(user, dlFileEntriesAndDLFolders, status);
	}

	public static void updateFileEntry(long userId,
		com.liferay.portal.kernel.repository.model.FileEntry fileEntry,
		com.liferay.portal.kernel.repository.model.FileVersion sourceFileVersion,
		com.liferay.portal.kernel.repository.model.FileVersion destinationFileVersion,
		long assetClassPk)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService()
			.updateFileEntry(userId, fileEntry, sourceFileVersion,
			destinationFileVersion, assetClassPk);
	}

	public static void updateFileEntry(long userId,
		com.liferay.portal.kernel.repository.model.FileEntry fileEntry,
		com.liferay.portal.kernel.repository.model.FileVersion sourceFileVersion,
		com.liferay.portal.kernel.repository.model.FileVersion destinationFileVersion,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService()
			.updateFileEntry(userId, fileEntry, sourceFileVersion,
			destinationFileVersion, serviceContext);
	}

	public static void updateFolder(long userId,
		com.liferay.portal.kernel.repository.model.Folder folder,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().updateFolder(userId, folder, serviceContext);
	}

	public static void updateStatus(long userId,
		com.liferay.portal.kernel.repository.model.FileEntry fileEntry,
		com.liferay.portal.kernel.repository.model.FileVersion latestFileVersion,
		int oldStatus, int newStatus,
		java.util.Map<java.lang.String, java.io.Serializable> workflowContext,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService()
			.updateStatus(userId, fileEntry, latestFileVersion, oldStatus,
			newStatus, workflowContext, serviceContext);
	}

	public static DLAppHelperLocalService getService() {
		if (_service == null) {
			_service = (DLAppHelperLocalService)PortalBeanLocatorUtil.locate(DLAppHelperLocalService.class.getName());

			ReferenceRegistry.registerReference(DLAppHelperLocalServiceUtil.class,
				"_service");
		}

		return _service;
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	public void setService(DLAppHelperLocalService service) {
	}

	private static DLAppHelperLocalService _service;
}