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

import aQute.bnd.annotation.ProviderType;

import com.liferay.asset.kernel.model.AssetEntry;

import com.liferay.document.library.kernel.model.DLFileShortcut;
import com.liferay.document.library.kernel.model.DLFolder;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileShortcut;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.service.BaseLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;

import java.io.Serializable;

import java.util.List;
import java.util.Map;

/**
 * Provides the local service interface for DLAppHelper. Methods of this
 * service will not have security checks based on the propagated JAAS
 * credentials because this service can only be accessed from within the same
 * VM.
 *
 * @author Brian Wing Shun Chan
 * @see DLAppHelperLocalServiceUtil
 * @see com.liferay.portlet.documentlibrary.service.base.DLAppHelperLocalServiceBaseImpl
 * @see com.liferay.portlet.documentlibrary.service.impl.DLAppHelperLocalServiceImpl
 * @generated
 */
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface DLAppHelperLocalService extends BaseLocalService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link DLAppHelperLocalServiceUtil} to access the dl app helper local service. Add custom service methods to {@link com.liferay.portlet.documentlibrary.service.impl.DLAppHelperLocalServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public AssetEntry updateAsset(long userId, FileEntry fileEntry,
		FileVersion fileVersion, long assetClassPk) throws PortalException;

	public AssetEntry updateAsset(long userId, FileEntry fileEntry,
		FileVersion fileVersion, long[] assetCategoryIds,
		java.lang.String[] assetTagNames, long[] assetLinkEntryIds)
		throws PortalException;

	public AssetEntry updateAsset(long userId, Folder folder,
		long[] assetCategoryIds, java.lang.String[] assetTagNames,
		long[] assetLinkEntryIds) throws PortalException;

	public FileEntry moveFileEntryFromTrash(long userId, FileEntry fileEntry,
		long newFolderId, ServiceContext serviceContext)
		throws PortalException;

	/**
	* Moves the file entry to the recycle bin.
	*
	* @param userId the primary key of the user moving the file entry
	* @param fileEntry the file entry to be moved
	* @return the moved file entry
	*/
	public FileEntry moveFileEntryToTrash(long userId, FileEntry fileEntry)
		throws PortalException;

	public FileShortcut moveFileShortcutFromTrash(long userId,
		FileShortcut fileShortcut, long newFolderId,
		ServiceContext serviceContext) throws PortalException;

	/**
	* Moves the file shortcut to the recycle bin.
	*
	* @param userId the primary key of the user moving the file shortcut
	* @param fileShortcut the file shortcut to be moved
	* @return the moved file shortcut
	*/
	public FileShortcut moveFileShortcutToTrash(long userId,
		FileShortcut fileShortcut) throws PortalException;

	public Folder moveFolderFromTrash(long userId, Folder folder,
		long parentFolderId, ServiceContext serviceContext)
		throws PortalException;

	/**
	* Moves the folder to the recycle bin.
	*
	* @param userId the primary key of the user moving the folder
	* @param folder the folder to be moved
	* @return the moved folder
	*/
	public Folder moveFolderToTrash(long userId, Folder folder)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getFileShortcutsCount(long groupId, long folderId,
		boolean active, int status);

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public java.lang.String getOSGiServiceIdentifier();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<DLFileShortcut> getFileShortcuts(long groupId, long folderId,
		boolean active, int status);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<FileEntry> getNoAssetFileEntries();

	public void addFolder(long userId, Folder folder,
		ServiceContext serviceContext) throws PortalException;

	public void cancelCheckOut(long userId, FileEntry fileEntry,
		FileVersion sourceFileVersion, FileVersion destinationFileVersion,
		FileVersion draftFileVersion, ServiceContext serviceContext)
		throws PortalException;

	public void checkAssetEntry(long userId, FileEntry fileEntry,
		FileVersion fileVersion) throws PortalException;

	public void deleteFileEntry(FileEntry fileEntry) throws PortalException;

	public void deleteFolder(Folder folder) throws PortalException;

	public void deleteRepositoryFileEntries(long repositoryId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public void getFileAsStream(long userId, FileEntry fileEntry,
		boolean incrementCounter);

	public void moveDependentsToTrash(DLFolder dlFolder)
		throws PortalException;

	/**
	* @deprecated As of 7.0.0, replaced by {@link
	#moveDependentsToTrash(DLFolder)}
	*/
	@java.lang.Deprecated
	public void moveDependentsToTrash(
		List<java.lang.Object> dlFileEntriesAndDLFolders, long trashEntryId)
		throws PortalException;

	public void restoreDependentsFromTrash(DLFolder dlFolder)
		throws PortalException;

	/**
	* @deprecated As of 7.0.0, replaced by {@link
	#restoreDependentsFromTrash(DLFolder)}
	*/
	@java.lang.Deprecated
	public void restoreDependentsFromTrash(
		List<java.lang.Object> dlFileEntriesAndDLFolders)
		throws PortalException;

	/**
	* @deprecated As of 7.0.0, replaced by {@link
	#restoreDependentsFromTrash(List)}
	*/
	@java.lang.Deprecated
	public void restoreDependentsFromTrash(
		List<java.lang.Object> dlFileEntriesAndDLFolders, long trashEntryId)
		throws PortalException;

	public void restoreFileEntryFromTrash(long userId, FileEntry fileEntry)
		throws PortalException;

	public void restoreFileShortcutFromTrash(long userId,
		FileShortcut fileShortcut) throws PortalException;

	public void restoreFolderFromTrash(long userId, Folder folder)
		throws PortalException;

	public void updateFileEntry(long userId, FileEntry fileEntry,
		FileVersion sourceFileVersion, FileVersion destinationFileVersion,
		ServiceContext serviceContext) throws PortalException;

	public void updateFileEntry(long userId, FileEntry fileEntry,
		FileVersion sourceFileVersion, FileVersion destinationFileVersion,
		long assetClassPk) throws PortalException;

	public void updateFolder(long userId, Folder folder,
		ServiceContext serviceContext) throws PortalException;

	public void updateStatus(long userId, FileEntry fileEntry,
		FileVersion latestFileVersion, int oldStatus, int newStatus,
		ServiceContext serviceContext,
		Map<java.lang.String, Serializable> workflowContext)
		throws PortalException;
}