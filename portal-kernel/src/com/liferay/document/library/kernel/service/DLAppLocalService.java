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

import com.liferay.document.library.kernel.model.DLFileRank;

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

import java.io.File;
import java.io.InputStream;

import java.util.List;

/**
 * Provides the local service interface for DLApp. Methods of this
 * service will not have security checks based on the propagated JAAS
 * credentials because this service can only be accessed from within the same
 * VM.
 *
 * @author Brian Wing Shun Chan
 * @see DLAppLocalServiceUtil
 * @see com.liferay.portlet.documentlibrary.service.base.DLAppLocalServiceBaseImpl
 * @see com.liferay.portlet.documentlibrary.service.impl.DLAppLocalServiceImpl
 * @generated
 */
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface DLAppLocalService extends BaseLocalService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link DLAppLocalServiceUtil} to access the dl app local service. Add custom service methods to {@link com.liferay.portlet.documentlibrary.service.impl.DLAppLocalServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */

	/**
	* Adds the file rank to the existing file entry. This method is only
	* supported by the Liferay repository.
	*
	* @param repositoryId the primary key of the repository
	* @param companyId the primary key of the company
	* @param userId the primary key of the file rank's creator/owner
	* @param fileEntryId the primary key of the file entry
	* @param serviceContext the service context to be applied
	* @return the file rank
	*/
	public DLFileRank addFileRank(long repositoryId, long companyId,
		long userId, long fileEntryId, ServiceContext serviceContext);

	/**
	* Updates a file rank to the existing file entry. This method is only
	* supported by the Liferay repository.
	*
	* @param repositoryId the primary key of the file rank's repository
	* @param companyId the primary key of the file rank's company
	* @param userId the primary key of the file rank's creator/owner
	* @param fileEntryId the primary key of the file rank's file entry
	* @param serviceContext the service context to be applied
	* @return the file rank
	*/
	public DLFileRank updateFileRank(long repositoryId, long companyId,
		long userId, long fileEntryId, ServiceContext serviceContext);

	public FileEntry addFileEntry(long userId, long repositoryId,
		long folderId, java.lang.String sourceFileName,
		java.lang.String mimeType, byte[] bytes, ServiceContext serviceContext)
		throws PortalException;

	/**
	* Adds a file entry and associated metadata based on a byte array.
	*
	* <p>
	* This method takes two file names, the <code>sourceFileName</code> and the
	* <code>title</code>. The <code>sourceFileName</code> corresponds to the
	* name of the actual file being uploaded. The <code>title</code>
	* corresponds to a name the client wishes to assign this file after it has
	* been uploaded to the portal. If it is <code>null</code>, the <code>
	* sourceFileName</code> will be used.
	* </p>
	*
	* @param userId the primary key of the file entry's creator/owner
	* @param repositoryId the primary key of the file entry's repository
	* @param folderId the primary key of the file entry's parent folder
	* @param sourceFileName the original file's name
	* @param mimeType the file's MIME type
	* @param title the name to be assigned to the file (optionally <code>null
	</code>)
	* @param description the file's description
	* @param changeLog the file's version change log
	* @param bytes the file's data (optionally <code>null</code>)
	* @param serviceContext the service context to be applied. Can set the
	asset category IDs, asset tag names, and expando bridge
	attributes for the file entry. In a Liferay repository, it may
	include:  <ul> <li> fileEntryTypeId - ID for a custom file entry
	type </li> <li> fieldsMap - mapping for fields associated with a
	custom file entry type </li> </ul>
	* @return the file entry
	*/
	public FileEntry addFileEntry(long userId, long repositoryId,
		long folderId, java.lang.String sourceFileName,
		java.lang.String mimeType, java.lang.String title,
		java.lang.String description, java.lang.String changeLog, byte[] bytes,
		ServiceContext serviceContext) throws PortalException;

	/**
	* Adds a file entry and associated metadata based on a {@link File} object.
	*
	* <p>
	* This method takes two file names, the <code>sourceFileName</code> and the
	* <code>title</code>. The <code>sourceFileName</code> corresponds to the
	* name of the actual file being uploaded. The <code>title</code>
	* corresponds to a name the client wishes to assign this file after it has
	* been uploaded to the portal. If it is <code>null</code>, the <code>
	* sourceFileName</code> will be used.
	* </p>
	*
	* @param userId the primary key of the file entry's creator/owner
	* @param repositoryId the primary key of the repository
	* @param folderId the primary key of the file entry's parent folder
	* @param sourceFileName the original file's name
	* @param mimeType the file's MIME type
	* @param title the name to be assigned to the file (optionally <code>null
	</code>)
	* @param description the file's description
	* @param changeLog the file's version change log
	* @param file the file's data (optionally <code>null</code>)
	* @param serviceContext the service context to be applied. Can set the
	asset category IDs, asset tag names, and expando bridge
	attributes for the file entry. In a Liferay repository, it may
	include:  <ul> <li> fileEntryTypeId - ID for a custom file entry
	type </li> <li> fieldsMap - mapping for fields associated with a
	custom file entry type </li> </ul>
	* @return the file entry
	*/
	public FileEntry addFileEntry(long userId, long repositoryId,
		long folderId, java.lang.String sourceFileName,
		java.lang.String mimeType, java.lang.String title,
		java.lang.String description, java.lang.String changeLog, File file,
		ServiceContext serviceContext) throws PortalException;

	/**
	* Adds a file entry and associated metadata based on an {@link InputStream}
	* object.
	*
	* <p>
	* This method takes two file names, the <code>sourceFileName</code> and the
	* <code>title</code>. The <code>sourceFileName</code> corresponds to the
	* name of the actual file being uploaded. The <code>title</code>
	* corresponds to a name the client wishes to assign this file after it has
	* been uploaded to the portal. If it is <code>null</code>, the <code>
	* sourceFileName</code> will be used.
	* </p>
	*
	* @param userId the primary key of the file entry's creator/owner
	* @param repositoryId the primary key of the repository
	* @param folderId the primary key of the file entry's parent folder
	* @param sourceFileName the original file's name
	* @param mimeType the file's MIME type
	* @param title the name to be assigned to the file (optionally <code>null
	</code>)
	* @param description the file's description
	* @param changeLog the file's version change log
	* @param is the file's data (optionally <code>null</code>)
	* @param size the file's size (optionally <code>0</code>)
	* @param serviceContext the service context to be applied. Can set the
	asset category IDs, asset tag names, and expando bridge
	attributes for the file entry. In a Liferay repository, it may
	include:  <ul> <li> fileEntryTypeId - ID for a custom file entry
	type </li> <li> fieldsMap - mapping for fields associated with a
	custom file entry type </li> </ul>
	* @return the file entry
	*/
	public FileEntry addFileEntry(long userId, long repositoryId,
		long folderId, java.lang.String sourceFileName,
		java.lang.String mimeType, java.lang.String title,
		java.lang.String description, java.lang.String changeLog,
		InputStream is, long size, ServiceContext serviceContext)
		throws PortalException;

	/**
	* Returns the file entry with the primary key.
	*
	* @param fileEntryId the primary key of the file entry
	* @return the file entry with the primary key
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public FileEntry getFileEntry(long fileEntryId) throws PortalException;

	/**
	* Returns the file entry with the title in the folder.
	*
	* @param groupId the primary key of the file entry's group
	* @param folderId the primary key of the file entry's folder
	* @param title the file entry's title
	* @return the file entry with the title in the folder
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public FileEntry getFileEntry(long groupId, long folderId,
		java.lang.String title) throws PortalException;

	/**
	* Returns the file entry with the UUID and group.
	*
	* @param uuid the file entry's UUID
	* @param groupId the primary key of the file entry's group
	* @return the file entry with the UUID and group
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public FileEntry getFileEntryByUuidAndGroupId(java.lang.String uuid,
		long groupId) throws PortalException;

	/**
	* Moves the file entry to the new folder.
	*
	* @param userId the primary key of the user
	* @param fileEntryId the primary key of the file entry
	* @param newFolderId the primary key of the new folder
	* @param serviceContext the service context to be applied
	* @return the file entry
	*/
	public FileEntry moveFileEntry(long userId, long fileEntryId,
		long newFolderId, ServiceContext serviceContext)
		throws PortalException;

	/**
	* Updates a file entry and associated metadata based on a byte array
	* object. If the file data is <code>null</code>, then only the associated
	* metadata (i.e., <code>title</code>, <code>description</code>, and
	* parameters in the <code>serviceContext</code>) will be updated.
	*
	* <p>
	* This method takes two file names, the <code>sourceFileName</code> and the
	* <code>title</code>. The <code>sourceFileName</code> corresponds to the
	* name of the actual file being uploaded. The <code>title</code>
	* corresponds to a name the client wishes to assign this file after it has
	* been uploaded to the portal.
	* </p>
	*
	* @param userId the primary key of the user
	* @param fileEntryId the primary key of the file entry
	* @param sourceFileName the original file's name (optionally
	<code>null</code>)
	* @param mimeType the file's MIME type (optionally <code>null</code>)
	* @param title the new name to be assigned to the file (optionally <code>
	<code>null</code></code>)
	* @param description the file's new description
	* @param changeLog the file's version change log (optionally
	<code>null</code>)
	* @param majorVersion whether the new file version is a major version
	* @param bytes the file's data (optionally <code>null</code>)
	* @param serviceContext the service context to be applied. Can set the
	asset category IDs, asset tag names, and expando bridge
	attributes for the file entry. In a Liferay repository, it may
	include:  <ul> <li> fileEntryTypeId - ID for a custom file entry
	type </li> <li> fieldsMap - mapping for fields associated with a
	custom file entry type </li> </ul>
	* @return the file entry
	*/
	public FileEntry updateFileEntry(long userId, long fileEntryId,
		java.lang.String sourceFileName, java.lang.String mimeType,
		java.lang.String title, java.lang.String description,
		java.lang.String changeLog, boolean majorVersion, byte[] bytes,
		ServiceContext serviceContext) throws PortalException;

	/**
	* Updates a file entry and associated metadata based on a {@link File}
	* object. If the file data is <code>null</code>, then only the associated
	* metadata (i.e., <code>title</code>, <code>description</code>, and
	* parameters in the <code>serviceContext</code>) will be updated.
	*
	* <p>
	* This method takes two file names, the <code>sourceFileName</code> and the
	* <code>title</code>. The <code>sourceFileName</code> corresponds to the
	* name of the actual file being uploaded. The <code>title</code>
	* corresponds to a name the client wishes to assign this file after it has
	* been uploaded to the portal.
	* </p>
	*
	* @param userId the primary key of the user
	* @param fileEntryId the primary key of the file entry
	* @param sourceFileName the original file's name (optionally
	<code>null</code>)
	* @param mimeType the file's MIME type (optionally <code>null</code>)
	* @param title the new name to be assigned to the file (optionally <code>
	<code>null</code></code>)
	* @param description the file's new description
	* @param changeLog the file's version change log (optionally
	<code>null</code>)
	* @param majorVersion whether the new file version is a major version
	* @param file the file's data (optionally <code>null</code>)
	* @param serviceContext the service context to be applied. Can set the
	asset category IDs, asset tag names, and expando bridge
	attributes for the file entry. In a Liferay repository, it may
	include:  <ul> <li> fileEntryTypeId - ID for a custom file entry
	type </li> <li> fieldsMap - mapping for fields associated with a
	custom file entry type </li> </ul>
	* @return the file entry
	*/
	public FileEntry updateFileEntry(long userId, long fileEntryId,
		java.lang.String sourceFileName, java.lang.String mimeType,
		java.lang.String title, java.lang.String description,
		java.lang.String changeLog, boolean majorVersion, File file,
		ServiceContext serviceContext) throws PortalException;

	/**
	* Updates a file entry and associated metadata based on an {@link
	* InputStream} object. If the file data is <code>null</code>, then only the
	* associated metadata (i.e., <code>title</code>, <code>description</code>,
	* and parameters in the <code>serviceContext</code>) will be updated.
	*
	* <p>
	* This method takes two file names, the <code>sourceFileName</code> and the
	* <code>title</code>. The <code>sourceFileName</code> corresponds to the
	* name of the actual file being uploaded. The <code>title</code>
	* corresponds to a name the client wishes to assign this file after it has
	* been uploaded to the portal.
	* </p>
	*
	* @param userId the primary key of the user
	* @param fileEntryId the primary key of the file entry
	* @param sourceFileName the original file's name (optionally
	<code>null</code>)
	* @param mimeType the file's MIME type (optionally <code>null</code>)
	* @param title the new name to be assigned to the file (optionally <code>
	<code>null</code></code>)
	* @param description the file's new description
	* @param changeLog the file's version change log (optionally
	<code>null</code>)
	* @param majorVersion whether the new file version is a major version
	* @param is the file's data (optionally <code>null</code>)
	* @param size the file's size (optionally <code>0</code>)
	* @param serviceContext the service context to be applied. Can set the
	asset category IDs, asset tag names, and expando bridge
	attributes for the file entry. In a Liferay repository, it may
	include:  <ul> <li> fileEntryTypeId - ID for a custom file entry
	type </li> <li> fieldsMap - mapping for fields associated with a
	custom file entry type </li> </ul>
	* @return the file entry
	*/
	public FileEntry updateFileEntry(long userId, long fileEntryId,
		java.lang.String sourceFileName, java.lang.String mimeType,
		java.lang.String title, java.lang.String description,
		java.lang.String changeLog, boolean majorVersion, InputStream is,
		long size, ServiceContext serviceContext) throws PortalException;

	/**
	* Adds the file shortcut to the existing file entry. This method is only
	* supported by the Liferay repository.
	*
	* @param userId the primary key of the file shortcut's creator/owner
	* @param repositoryId the primary key of the repository
	* @param folderId the primary key of the file shortcut's parent folder
	* @param toFileEntryId the primary key of the file entry to point to
	* @param serviceContext the service context to be applied. Can set the
	asset category IDs, asset tag names, and expando bridge
	attributes for the file entry.
	* @return the file shortcut
	*/
	public FileShortcut addFileShortcut(long userId, long repositoryId,
		long folderId, long toFileEntryId, ServiceContext serviceContext)
		throws PortalException;

	/**
	* Returns the file shortcut with the primary key. This method is only
	* supported by the Liferay repository.
	*
	* @param fileShortcutId the primary key of the file shortcut
	* @return the file shortcut with the primary key
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public FileShortcut getFileShortcut(long fileShortcutId)
		throws PortalException;

	/**
	* Updates a file shortcut to the existing file entry. This method is only
	* supported by the Liferay repository.
	*
	* @param userId the primary key of the file shortcut's creator/owner
	* @param fileShortcutId the primary key of the file shortcut
	* @param folderId the primary key of the file shortcut's parent folder
	* @param toFileEntryId the primary key of the file shortcut's file entry
	* @param serviceContext the service context to be applied. Can set the
	asset category IDs, asset tag names, and expando bridge
	attributes for the file entry.
	* @return the file shortcut
	*/
	public FileShortcut updateFileShortcut(long userId, long fileShortcutId,
		long folderId, long toFileEntryId, ServiceContext serviceContext)
		throws PortalException;

	/**
	* Returns the file version with the primary key.
	*
	* @param fileVersionId the primary key of the file version
	* @return the file version with the primary key
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public FileVersion getFileVersion(long fileVersionId)
		throws PortalException;

	/**
	* Adds a folder.
	*
	* @param userId the primary key of the folder's creator/owner
	* @param repositoryId the primary key of the repository
	* @param parentFolderId the primary key of the folder's parent folder
	* @param name the folder's name
	* @param description the folder's description
	* @param serviceContext the service context to be applied. In a Liferay
	repository, it may include mountPoint which is a boolean
	specifying whether the folder is a facade for mounting a
	third-party repository
	* @return the folder
	*/
	public Folder addFolder(long userId, long repositoryId,
		long parentFolderId, java.lang.String name,
		java.lang.String description, ServiceContext serviceContext)
		throws PortalException;

	/**
	* Returns the folder with the primary key.
	*
	* @param folderId the primary key of the folder
	* @return the folder with the primary key
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Folder getFolder(long folderId) throws PortalException;

	/**
	* Returns the folder with the name in the parent folder.
	*
	* @param repositoryId the primary key of the folder's repository
	* @param parentFolderId the primary key of the folder's parent folder
	* @param name the folder's name
	* @return the folder with the name in the parent folder
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Folder getFolder(long repositoryId, long parentFolderId,
		java.lang.String name) throws PortalException;

	/**
	* Returns the mount folder of the repository with the primary key. This
	* method is only supported by the Liferay repository.
	*
	* @param repositoryId the primary key of the repository
	* @return the folder used for mounting third-party repositories
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Folder getMountFolder(long repositoryId) throws PortalException;

	public Folder moveFolder(long userId, long folderId, long parentFolderId,
		ServiceContext serviceContext) throws PortalException;

	/**
	* Updates the folder.
	*
	* @param folderId the primary key of the folder
	* @param parentFolderId the primary key of the folder's new parent folder
	* @param name the folder's new name
	* @param description the folder's new description
	* @param serviceContext the service context to be applied. In a Liferay
	repository, it may include:  <ul> <li> defaultFileEntryTypeId -
	the file entry type to default all Liferay file entries to </li>
	<li> dlFileEntryTypesSearchContainerPrimaryKeys - a
	comma-delimited list of file entry type primary keys allowed in
	the given folder and all descendants </li> <li> restrictionType -
	specifying restriction type of file entry types allowed </li>
	<li> workflowDefinitionXYZ - the workflow definition name
	specified per file entry type. The parameter name must be the
	string <code>workflowDefinition</code> appended by the
	<code>fileEntryTypeId</code> (optionally <code>0</code>).</li>
	</ul>
	* @return the folder
	*/
	public Folder updateFolder(long folderId, long parentFolderId,
		java.lang.String name, java.lang.String description,
		ServiceContext serviceContext) throws PortalException;

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public java.lang.String getOSGiServiceIdentifier();

	/**
	* Returns the file ranks from the user. This method is only supported by
	* the Liferay repository.
	*
	* @param repositoryId the primary key of the repository
	* @param userId the primary key of the user
	* @return the file ranks from the user
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<DLFileRank> getFileRanks(long repositoryId, long userId);

	/**
	* Delete all data associated to the given repository. This method is only
	* supported by the Liferay repository.
	*
	* @param repositoryId the primary key of the data's repository
	*/
	public void deleteAll(long repositoryId) throws PortalException;

	public void deleteAllRepositories(long groupId) throws PortalException;

	/**
	* Deletes the file entry.
	*
	* @param fileEntryId the primary key of the file entry
	*/
	public void deleteFileEntry(long fileEntryId) throws PortalException;

	/**
	* Deletes the file ranks associated to a given file entry. This method is
	* only supported by the Liferay repository.
	*
	* @param fileEntryId the primary key of the file entry
	*/
	public void deleteFileRanksByFileEntryId(long fileEntryId);

	/**
	* Deletes the file ranks associated to a given user. This method is only
	* supported by the Liferay repository.
	*
	* @param userId the primary key of the user
	*/
	public void deleteFileRanksByUserId(long userId);

	/**
	* Deletes the file shortcut. This method is only supported by the Liferay
	* repository.
	*
	* @param fileShortcut the file shortcut
	*/
	public void deleteFileShortcut(FileShortcut fileShortcut)
		throws PortalException;

	/**
	* Deletes the file shortcut. This method is only supported by the Liferay
	* repository.
	*
	* @param fileShortcutId the primary key of the file shortcut
	*/
	public void deleteFileShortcut(long fileShortcutId)
		throws PortalException;

	/**
	* Deletes all file shortcuts associated to the file entry. This method is
	* only supported by the Liferay repository.
	*
	* @param toFileEntryId the primary key of the associated file entry
	*/
	public void deleteFileShortcuts(long toFileEntryId)
		throws PortalException;

	/**
	* Deletes the folder and all of its subfolders and file entries.
	*
	* @param folderId the primary key of the folder
	*/
	public void deleteFolder(long folderId) throws PortalException;

	/**
	* Subscribe the user to changes in documents of the file entry type. This
	* method is only supported by the Liferay repository.
	*
	* @param userId the primary key of the user
	* @param groupId the primary key of the file entry type's group
	* @param fileEntryTypeId the primary key of the file entry type
	*/
	public void subscribeFileEntryType(long userId, long groupId,
		long fileEntryTypeId) throws PortalException;

	/**
	* Subscribe the user to document changes in the folder. This method is only
	* supported by the Liferay repository.
	*
	* @param userId the primary key of the user
	* @param groupId the primary key of the folder's group
	* @param folderId the primary key of the folder
	*/
	public void subscribeFolder(long userId, long groupId, long folderId)
		throws PortalException;

	/**
	* Unsubscribe the user from changes in documents of the file entry type.
	* This method is only supported by the Liferay repository.
	*
	* @param userId the primary key of the user
	* @param groupId the primary key of the file entry type's group
	* @param fileEntryTypeId the primary key of the file entry type
	*/
	public void unsubscribeFileEntryType(long userId, long groupId,
		long fileEntryTypeId) throws PortalException;

	/**
	* Unsubscribe the user from document changes in the folder. This method is
	* only supported by the Liferay repository.
	*
	* @param userId the primary key of the user
	* @param groupId the primary key of the folder's group
	* @param folderId the primary key of the folder
	*/
	public void unsubscribeFolder(long userId, long groupId, long folderId)
		throws PortalException;

	/**
	* Updates the file entry's asset replacing its asset categories, tags, and
	* links.
	*
	* @param userId the primary key of the user
	* @param fileEntry the file entry to update
	* @param fileVersion the file version to update
	* @param assetCategoryIds the primary keys of the new asset categories
	* @param assetTagNames the new asset tag names
	* @param assetLinkEntryIds the primary keys of the new asset link entries
	*/
	public void updateAsset(long userId, FileEntry fileEntry,
		FileVersion fileVersion, long[] assetCategoryIds,
		java.lang.String[] assetTagNames, long[] assetLinkEntryIds)
		throws PortalException;

	/**
	* Updates all file shortcuts to the existing file entry to the new file
	* entry. This method is only supported by the Liferay repository.
	*
	* @param oldToFileEntryId the primary key of the old file entry pointed to
	* @param newToFileEntryId the primary key of the new file entry to point to
	*/
	public void updateFileShortcuts(long oldToFileEntryId, long newToFileEntryId)
		throws PortalException;

	/**
	* @deprecated As of 7.0.0, replaced by {@link #updateFileShortcuts(long,
	long)}
	*/
	@java.lang.Deprecated
	public void updateFileShortcuts(long toRepositoryId, long oldToFileEntryId,
		long newToFileEntryId) throws PortalException;
}