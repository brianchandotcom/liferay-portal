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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.jsonwebservice.JSONWebService;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.security.ac.AccessControlled;
import com.liferay.portal.service.BaseService;

/**
 * Provides the remote service interface for DLApp. Methods of this
 * service are expected to have security checks based on the propagated JAAS
 * credentials because this service can be accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see DLAppServiceUtil
 * @see com.liferay.portlet.documentlibrary.service.base.DLAppServiceBaseImpl
 * @see com.liferay.portlet.documentlibrary.service.impl.DLAppServiceImpl
 * @generated
 */
@AccessControlled
@JSONWebService
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface DLAppService extends BaseService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link DLAppServiceUtil} to access the d l app remote service. Add custom service methods to {@link com.liferay.portlet.documentlibrary.service.impl.DLAppServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public java.lang.String getBeanIdentifier();

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public void setBeanIdentifier(java.lang.String beanIdentifier);

	/**
	* Adds a file entry and associated metadata. It is created based on a byte
	* array.
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
	* @param repositoryId the primary key of the repository
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
	* @throws PortalException if the parent folder could not be found or if the
	file entry's information was invalid
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.kernel.repository.model.FileEntry addFileEntry(
		long repositoryId, long folderId, java.lang.String sourceFileName,
		java.lang.String mimeType, java.lang.String title,
		java.lang.String description, java.lang.String changeLog, byte[] bytes,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Adds a file entry and associated metadata. It is created based on a
	* {@link java.io.File} object.
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
	* @throws PortalException if the parent folder could not be found or if the
	file entry's information was invalid
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.kernel.repository.model.FileEntry addFileEntry(
		long repositoryId, long folderId, java.lang.String sourceFileName,
		java.lang.String mimeType, java.lang.String title,
		java.lang.String description, java.lang.String changeLog,
		java.io.File file,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Adds a file entry and associated metadata. It is created based on a
	* {@link java.io.InputStream} object.
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
	* @throws PortalException if the parent folder could not be found or if the
	file entry's information was invalid
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.kernel.repository.model.FileEntry addFileEntry(
		long repositoryId, long folderId, java.lang.String sourceFileName,
		java.lang.String mimeType, java.lang.String title,
		java.lang.String description, java.lang.String changeLog,
		java.io.InputStream is, long size,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Adds a file shortcut to the existing file entry. This method is only
	* supported by the Liferay repository.
	*
	* @param repositoryId the primary key of the repository
	* @param folderId the primary key of the file shortcut's parent folder
	* @param toFileEntryId the primary key of the file shortcut's file entry
	* @param serviceContext the service context to be applied. Can set the
	asset category IDs, asset tag names, and expando bridge
	attributes for the file entry.
	* @return the file shortcut
	* @throws PortalException if the parent folder or file entry could not be
	found, or if the file shortcut's information was invalid
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileShortcut addFileShortcut(
		long repositoryId, long folderId, long toFileEntryId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Adds a folder.
	*
	* @param repositoryId the primary key of the repository
	* @param parentFolderId the primary key of the folder's parent folder
	* @param name the folder's name
	* @param description the folder's description
	* @param serviceContext the service context to be applied. In a Liferay
	repository, it may include boolean mountPoint specifying whether
	folder is a facade for mounting a third-party repository
	* @return the folder
	* @throws PortalException if the parent folder could not be found or if the
	new folder's information was invalid
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.kernel.repository.model.Folder addFolder(
		long repositoryId, long parentFolderId, java.lang.String name,
		java.lang.String description,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Adds a temporary file entry.
	*
	* <p>
	* This allows a client to upload a file into a temporary location and
	* manipulate its metadata prior to making it available for public usage.
	* This is different from checking in and checking out a file entry.
	* </p>
	*
	* @param groupId the primary key of the group
	* @param folderId the primary key of the folder where the file entry will
	eventually reside
	* @param fileName the file's original name
	* @param tempFolderName the temporary folder's name
	* @param file Name the file's original name
	* @param mimeType the file's MIME type
	* @return the file's name
	* @throws PortalException if the file name was invalid
	* @throws SystemException if a system exception occurred
	* @see com.liferay.portal.kernel.util.TempFileUtil
	*/
	public com.liferay.portal.kernel.repository.model.FileEntry addTempFileEntry(
		long groupId, long folderId, java.lang.String fileName,
		java.lang.String tempFolderName, java.io.File file,
		java.lang.String mimeType)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.kernel.repository.model.FileEntry addTempFileEntry(
		long groupId, long folderId, java.lang.String fileName,
		java.lang.String tempFolderName, java.io.InputStream inputStream,
		java.lang.String mimeType)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Cancels the check out of the file entry. If a user has not checked out
	* the specified file entry, invoking this method will result in no changes.
	*
	* <p>
	* When a file entry is checked out, a PWC (private working copy) is created
	* and the original file entry is locked. A client can make as many changes
	* to the PWC as he desires without those changes being visible to other
	* users. If the user is satisfied with the changes, he may elect to check
	* in his changes, resulting in a new file version based on the PWC; the PWC
	* will be removed and the file entry will be unlocked. If the user is not
	* satisfied with the changes, he may elect to cancel his check out; this
	* results in the deletion of the PWC and unlocking of the file entry.
	* </p>
	*
	* @param fileEntryId the primary key of the file entry to cancel the
	checkout
	* @throws PortalException if the file entry could not be found
	* @throws SystemException if a system exception occurred
	* @see #checkInFileEntry(long, boolean, String, ServiceContext)
	* @see #checkOutFileEntry(long, ServiceContext)
	*/
	public void cancelCheckOut(long fileEntryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Checks in the file entry. If a user has not checked out the specified
	* file entry, invoking this method will result in no changes.
	*
	* <p>
	* When a file entry is checked out, a PWC (private working copy) is created
	* and the original file entry is locked. A client can make as many changes
	* to the PWC as he desires without those changes being visible to other
	* users. If the user is satisfied with the changes, he may elect to check
	* in his changes, resulting in a new file version based on the PWC; the PWC
	* will be removed and the file entry will be unlocked. If the user is not
	* satisfied with the changes, he may elect to cancel his check out; this
	* results in the deletion of the PWC and unlocking of the file entry.
	* </p>
	*
	* @param fileEntryId the primary key of the file entry to check in
	* @param majorVersion whether the new file version is a major version
	* @param changeLog the file's version change log
	* @param serviceContext the service context to be applied
	* @throws PortalException if the file entry could not be found
	* @throws SystemException if a system exception occurred
	* @see #cancelCheckOut(long)
	* @see #checkOutFileEntry(long, ServiceContext)
	*/
	public void checkInFileEntry(long fileEntryId, boolean majorVersion,
		java.lang.String changeLog,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* @deprecated As of 6.2.0, replaced by {@link #checkInFileEntry(long,
	String, ServiceContext)}
	*/
	public void checkInFileEntry(long fileEntryId, java.lang.String lockUuid)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Checks in the file entry using the lock's UUID. If a user has not checked
	* out the specified file entry, invoking this method will result in no
	* changes. This method is primarily used by WebDAV.
	*
	* <p>
	* When a file entry is checked out, a PWC (private working copy) is created
	* and the original file entry is locked. A client can make as many changes
	* to the PWC as he desires without those changes being visible to other
	* users. If the user is satisfied with the changes, he may elect to check
	* in his changes, resulting in a new file version based on the PWC; the PWC
	* will be removed and the file entry will be unlocked. If the user is not
	* satisfied with the changes, he may elect to cancel his check out; this
	* results in the deletion of the PWC and unlocking of the file entry.
	* </p>
	*
	* @param fileEntryId the primary key of the file entry to check in
	* @param lockUuid the lock's UUID
	* @param serviceContext the service context to be applied
	* @throws PortalException if the file entry could not be found
	* @throws SystemException if a system exception occurred
	* @see #cancelCheckOut(long)
	* @see #checkOutFileEntry(long, String, long, ServiceContext)
	*/
	public void checkInFileEntry(long fileEntryId, java.lang.String lockUuid,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Check out a file entry.
	*
	* <p>
	* When a file entry is checked out, a PWC (private working copy) is created
	* and the original file entry is locked. A client can make as many changes
	* to the PWC as he desires without those changes being visible to other
	* users. If the user is satisfied with the changes, he may elect to check
	* in his changes, resulting in a new file version based on the PWC; the PWC
	* will be removed and the file entry will be unlocked. If the user is not
	* satisfied with the changes, he may elect to cancel his check out; this
	* results in the deletion of the PWC and unlocking of the file entry.
	* </p>
	*
	* @param fileEntryId the file entry to check out
	* @param serviceContext the service context to be applied
	* @throws PortalException if the file entry could not be found
	* @throws SystemException if a system exception occurred
	* @see #cancelCheckOut(long)
	* @see #checkInFileEntry(long, boolean, String, ServiceContext)
	*/
	public void checkOutFileEntry(long fileEntryId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Checks out the file entry. This method is primarily used by WebDAV.
	*
	* <p>
	* When a file entry is checked out, a PWC (private working copy) is created
	* and the original file entry is locked. A client can make as many changes
	* to the PWC as he desires without those changes being visible to other
	* users. If the user is satisfied with the changes, he may elect to check
	* in his changes, resulting in a new file version based on the PWC; the PWC
	* will be removed and the file entry will be unlocked. If the user is not
	* satisfied with the changes, he may elect to cancel his check out; this
	* results in the deletion of the PWC and unlocking of the file entry.
	* </p>
	*
	* @param fileEntryId the file entry to check out
	* @param owner the owner string for the checkout (optionally
	<code>null</code>)
	* @param expirationTime the time in milliseconds before the lock expires.
	If the value is <code>0</code>, the default expiration time will
	be used from <code>portal.properties>.
	* @param serviceContext the service context to be applied
	* @return the file entry
	* @throws PortalException if the file entry could not be found
	* @throws SystemException if a system exception occurred
	* @see #cancelCheckOut(long)
	* @see #checkInFileEntry(long, String)
	*/
	public com.liferay.portal.kernel.repository.model.FileEntry checkOutFileEntry(
		long fileEntryId, java.lang.String owner, long expirationTime,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Performs a deep copy of the folder.
	*
	* @param repositoryId the primary key of the repository
	* @param sourceFolderId the primary key of the folder to copy
	* @param parentFolderId the primary key of the new folder's parent folder
	* @param name the new folder's name
	* @param description the new folder's description
	* @param serviceContext the service context to be applied
	* @return the folder
	* @throws PortalException if the source folder or the new parent folder
	could not be found or if the new folder's information was invalid
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.kernel.repository.model.Folder copyFolder(
		long repositoryId, long sourceFolderId, long parentFolderId,
		java.lang.String name, java.lang.String description,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Deletes the file entry with the primary key.
	*
	* @param fileEntryId the primary key of the file entry
	* @throws PortalException if the file entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public void deleteFileEntry(long fileEntryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Deletes the file entry with the title in the folder.
	*
	* @param repositoryId the primary key of the repository
	* @param folderId the primary key of the file entry's parent folder
	* @param title the file entry's title
	* @throws PortalException if the file entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public void deleteFileEntryByTitle(long repositoryId, long folderId,
		java.lang.String title)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Deletes the file shortcut with the primary key. This method is only
	* supported by the Liferay repository.
	*
	* @param fileShortcutId the primary key of the file shortcut
	* @throws PortalException if the file shortcut could not be found
	* @throws SystemException if a system exception occurred
	*/
	public void deleteFileShortcut(long fileShortcutId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Deletes the file version. File versions can only be deleted if it is
	* approved and there are other approved file versions available. This
	* method is only supported by the Liferay repository.
	*
	* @param fileEntryId the primary key of the file entry
	* @param version the version label of the file version
	* @throws PortalException if the file version could not be found or invalid
	* @throws SystemException if a system exception occurred
	*/
	public void deleteFileVersion(long fileEntryId, java.lang.String version)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Deletes the folder with the primary key and all of its subfolders and
	* file entries.
	*
	* @param folderId the primary key of the folder
	* @throws PortalException if the folder could not be found
	* @throws SystemException if a system exception occurred
	*/
	public void deleteFolder(long folderId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Deletes the folder with the name in the parent folder and all of its
	* subfolders and file entries.
	*
	* @param repositoryId the primary key of the repository
	* @param parentFolderId the primary key of the folder's parent folder
	* @param name the folder's name
	* @throws PortalException if the folder could not be found
	* @throws SystemException if a system exception occurred
	*/
	public void deleteFolder(long repositoryId, long parentFolderId,
		java.lang.String name)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Deletes the temporary file entry.
	*
	* @param groupId the primary key of the group
	* @param folderId the primary key of the folder where the file entry was
	eventually to reside
	* @param fileName the file's original name
	* @param tempFolderName the temporary folder's name
	* @throws PortalException if the file name was invalid
	* @throws SystemException if a system exception occurred
	* @see com.liferay.portal.kernel.util.TempFileUtil
	*/
	public void deleteTempFileEntry(long groupId, long folderId,
		java.lang.String fileName, java.lang.String tempFolderName)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the file entries in the folder.
	*
	* @param repositoryId the primary key of the file entry's repository
	* @param folderId the primary key of the file entry's folder
	* @return the file entries in the folder
	* @throws PortalException if the folder could not be found
	* @throws SystemException if a system exception occurred
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portal.kernel.repository.model.FileEntry> getFileEntries(
		long repositoryId, long folderId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the file entries in the folder.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end -
	* start</code> instances. <code>start</code> and <code>end</code> are not
	* primary keys, they are indexes in the result set. Thus, <code>0</code>
	* refers to the first result in the set. Setting both <code>start</code>
	* and <code>end</code> to {@link
	* com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full
	* result set.
	* </p>
	*
	* @param repositoryId the primary key of the file entry's repository
	* @param folderId the primary key of the file entry's folder
	* @param start the lower bound of the range of results
	* @param end the upper bound of the range of results (not inclusive)
	* @return the range of file entries in the folder
	* @throws PortalException if the folder could not be found
	* @throws SystemException if a system exception occurred
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portal.kernel.repository.model.FileEntry> getFileEntries(
		long repositoryId, long folderId, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the file entries in the folder.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end -
	* start</code> instances. <code>start</code> and <code>end</code> are not
	* primary keys, they are indexes in the result set. Thus, <code>0</code>
	* refers to the first result in the set. Setting both <code>start</code>
	* and <code>end</code> to {@link
	* com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full
	* result set.
	* </p>
	*
	* @param repositoryId the primary key of the file entry's repository
	* @param folderId the primary key of the file entry's folder
	* @param start the lower bound of the range of results
	* @param end the upper bound of the range of results (not inclusive)
	* @param obc the comparator to order the file entries (optionally
	<code>null</code>)
	* @return the range of file entries in the folder ordered by comparator
	<code>obc</code>
	* @throws PortalException if the folder could not be found
	* @throws SystemException if a system exception occurred
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portal.kernel.repository.model.FileEntry> getFileEntries(
		long repositoryId, long folderId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the file entries with the file entry type in the folder.
	*
	* @param repositoryId the primary key of the file entry's repository
	* @param folderId the primary key of the file entry's folder
	* @param fileEntryTypeId the primary key of the file entry type
	* @return the file entries with the file entry type in the folder
	* @throws PortalException if the folder could not be found
	* @throws SystemException if a system exception occurred
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portal.kernel.repository.model.FileEntry> getFileEntries(
		long repositoryId, long folderId, long fileEntryTypeId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the file entries with the file entry type in the
	* folder.
	*
	* @param repositoryId the primary key of the file entry's repository
	* @param folderId the primary key of the file entry's folder
	* @param fileEntryTypeId the primary key of the file entry type
	* @param start the lower bound of the range of results
	* @param end the upper bound of the range of results (not inclusive)
	* @return the file entries in the folder
	* @throws PortalException if the folder could not be found
	* @throws SystemException if a system exception occurred
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portal.kernel.repository.model.FileEntry> getFileEntries(
		long repositoryId, long folderId, long fileEntryTypeId, int start,
		int end)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the file entries with the file entry type
	* in the folder.
	*
	* @param repositoryId the primary key of the repository
	* @param folderId the primary key of the folder
	* @param fileEntryTypeId the primary key of the file entry type
	* @param start the lower bound of the range of results
	* @param end the upper bound of the range of results (not inclusive)
	* @param obc the comparator to order the results by (optionally
	<code>null</code>)
	* @return the range of file entries with the file entry type in the folder
	ordered by <code>null</code>
	* @throws PortalException if the folder could not be found
	* @throws SystemException if a system exception occurred
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portal.kernel.repository.model.FileEntry> getFileEntries(
		long repositoryId, long folderId, long fileEntryTypeId, int start,
		int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the file entries and shortcuts in the folder.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end -
	* start</code> instances. <code>start</code> and <code>end</code> are not
	* primary keys, they are indexes in the result set. Thus, <code>0</code>
	* refers to the first result in the set. Setting both <code>start</code>
	* and <code>end</code> to {@link
	* com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full
	* result set.
	* </p>
	*
	* @param repositoryId the primary key of the repository
	* @param folderId the primary key of the folder
	* @param status the workflow status
	* @param start the lower bound of the range of results
	* @param end the upper bound of the range of results (not inclusive)
	* @return the range of file entries and shortcuts in the folder
	* @throws PortalException if the folder could not be found
	* @throws SystemException if a system exception occurred
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<java.lang.Object> getFileEntriesAndFileShortcuts(
		long repositoryId, long folderId, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of file entries and shortcuts in the folder.
	*
	* @param repositoryId the primary key of the repository
	* @param folderId the primary key of the folder
	* @param status the workflow status
	* @return the number of file entries and shortcuts in the folder
	* @throws PortalException if the folder ould not be found
	* @throws SystemException if a system exception occurred
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getFileEntriesAndFileShortcutsCount(long repositoryId,
		long folderId, int status)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of file entries and shortcuts in the folder.
	*
	* @param repositoryId the primary key of the repository
	* @param folderId the primary key of the folder
	* @param status the workflow status
	* @param mimeTypes allowed media types
	* @return the number of file entries and shortcuts in the folder
	* @throws PortalException if the folder ould not be found
	* @throws SystemException if a system exception occurred
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getFileEntriesAndFileShortcutsCount(long repositoryId,
		long folderId, int status, java.lang.String[] mimeTypes)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of file entries in the folder.
	*
	* @param repositoryId the primary key of the file entry's repository
	* @param folderId the primary key of the file entry's folder
	* @return the number of file entries in the folder
	* @throws PortalException if the folder could not be found
	* @throws SystemException if a system exception occurred
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getFileEntriesCount(long repositoryId, long folderId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of file entries with the file entry type in the
	* folder.
	*
	* @param repositoryId the primary key of the file entry's repository
	* @param folderId the primary key of the file entry's folder
	* @param fileEntryTypeId the primary key of the file entry type
	* @return the number of file entries with the file entry type in the folder
	* @throws PortalException if the folder could not be found
	* @throws SystemException if a system exception occurred
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getFileEntriesCount(long repositoryId, long folderId,
		long fileEntryTypeId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the file entry with the primary key.
	*
	* @param fileEntryId the primary key of the file entry
	* @return the file entry with the primary key
	* @throws PortalException if the file entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portal.kernel.repository.model.FileEntry getFileEntry(
		long fileEntryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the file entry with the title in the folder.
	*
	* @param groupId the primary key of the file entry's group
	* @param folderId the primary key of the file entry's folder
	* @param title the file entry's title
	* @return the file entry with the title in the folder
	* @throws PortalException if the file entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portal.kernel.repository.model.FileEntry getFileEntry(
		long groupId, long folderId, java.lang.String title)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the file entry with the UUID and group.
	*
	* @param uuid the file entry's UUID
	* @param groupId the primary key of the file entry's group
	* @return the file entry with the UUID and group
	* @throws PortalException if the file entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portal.kernel.repository.model.FileEntry getFileEntryByUuidAndGroupId(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the file shortcut with the primary key. This method is only
	* supported by the Liferay repository.
	*
	* @param fileShortcutId the primary key of the file shortcut
	* @return the file shortcut with the primary key
	* @throws PortalException if the file shortcut could not be found
	* @throws SystemException if a system exception occurred
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portlet.documentlibrary.model.DLFileShortcut getFileShortcut(
		long fileShortcutId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the folder with the primary key.
	*
	* @param folderId the primary key of the folder
	* @return the folder with the primary key
	* @throws PortalException if the folder could not be found
	* @throws SystemException if a system exception occurred
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portal.kernel.repository.model.Folder getFolder(
		long folderId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the folder with the name in the parent folder.
	*
	* @param repositoryId the primary key of the folder's repository
	* @param parentFolderId the primary key of the folder's parent folder
	* @param name the folder's name
	* @return the folder with the name in the parent folder
	* @throws PortalException if the folder could not be found
	* @throws SystemException if a system exception occurred
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portal.kernel.repository.model.Folder getFolder(
		long repositoryId, long parentFolderId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all immediate subfolders of the parent folder.
	*
	* @param repositoryId the primary key of the folder's repository
	* @param parentFolderId the primary key of the folder's parent folder
	* @return the immediate subfolders of the parent folder
	* @throws PortalException if the parent folder could not be found
	* @throws SystemException if a system exception occurred
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portal.kernel.repository.model.Folder> getFolders(
		long repositoryId, long parentFolderId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all immediate subfolders of the parent folder, optionally
	* including mount folders for third-party repositories.
	*
	* @param repositoryId the primary key of the folder's repository
	* @param parentFolderId the primary key of the folder's parent folder
	* @param includeMountFolders whether to include mount folders for
	third-party repositories
	* @return the immediate subfolders of the parent folder
	* @throws PortalException if the parent folder could not be found
	* @throws SystemException if a system exception occurred
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portal.kernel.repository.model.Folder> getFolders(
		long repositoryId, long parentFolderId, boolean includeMountFolders)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the immediate subfolders of the parent folder,
	* optionally including mount folders for third-party repositories.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end -
	* start</code> instances. <code>start</code> and <code>end</code> are not
	* primary keys, they are indexes in the result set. Thus, <code>0</code>
	* refers to the first result in the set. Setting both <code>start</code>
	* and <code>end</code> to {@link
	* com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full
	* result set.
	* </p>
	*
	* @param repositoryId the primary key of the folder's repository
	* @param parentFolderId the primary key of the folder's parent folder
	* @param includeMountFolders whether to include mount folders for
	third-party repositories
	* @param start the lower bound of the range of results
	* @param end the upper bound of the range of results (not inclusive)
	* @return the range of immediate subfolders of the parent folder
	* @throws PortalException if the parent folder could not be found
	* @throws SystemException if a system exception occurred
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portal.kernel.repository.model.Folder> getFolders(
		long repositoryId, long parentFolderId, boolean includeMountFolders,
		int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the immediate subfolders of the parent
	* folder.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end -
	* start</code> instances. <code>start</code> and <code>end</code> are not
	* primary keys, they are indexes in the result set. Thus, <code>0</code>
	* refers to the first result in the set. Setting both <code>start</code>
	* and <code>end</code> to {@link
	* com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full
	* result set.
	* </p>
	*
	* @param repositoryId the primary key of the folder's repository
	* @param parentFolderId the primary key of the folder's parent folder
	* @param includeMountFolders whether to include mount folders for
	third-party repositories
	* @param start the lower bound of the range of results
	* @param end the upper bound of the range of results (not inclusive)
	* @param obc the comparator to order the folders (optionally
	<code>null</code>)
	* @return the range of immediate subfolders of the parent folder ordered by
	comparator <code>obc</code>
	* @throws PortalException if the parent folder could not be found
	* @throws SystemException if a system exception occurred
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portal.kernel.repository.model.Folder> getFolders(
		long repositoryId, long parentFolderId, boolean includeMountFolders,
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the immediate subfolders of the parent
	* folder.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end -
	* start</code> instances. <code>start</code> and <code>end</code> are not
	* primary keys, they are indexes in the result set. Thus, <code>0</code>
	* refers to the first result in the set. Setting both <code>start</code>
	* and <code>end</code> to {@link
	* com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full
	* result set.
	* </p>
	*
	* @param repositoryId the primary key of the folder's repository
	* @param parentFolderId the primary key of the folder's parent folder
	* @param status the workflow status
	* @param includeMountFolders whether to include mount folders for
	third-party repositories
	* @param start the lower bound of the range of results
	* @param end the upper bound of the range of results (not inclusive)
	* @param obc the comparator to order the folders (optionally
	<code>null</code>)
	* @return the range of immediate subfolders of the parent folder ordered by
	comparator <code>obc</code>
	* @throws PortalException if the parent folder could not be found
	* @throws SystemException if a system exception occurred
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portal.kernel.repository.model.Folder> getFolders(
		long repositoryId, long parentFolderId, int status,
		boolean includeMountFolders, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the immediate subfolders of the parent folder.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end -
	* start</code> instances. <code>start</code> and <code>end</code> are not
	* primary keys, they are indexes in the result set. Thus, <code>0</code>
	* refers to the first result in the set. Setting both <code>start</code>
	* and <code>end</code> to {@link
	* com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full
	* result set.
	* </p>
	*
	* @param repositoryId the primary key of the folder's repository
	* @param parentFolderId the primary key of the folder's parent folder
	* @param start the lower bound of the range of results
	* @param end the upper bound of the range of results (not inclusive)
	* @return the range of immediate subfolders of the parent folder
	* @throws PortalException if the parent folder could not be found
	* @throws SystemException if a system exception occurred
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portal.kernel.repository.model.Folder> getFolders(
		long repositoryId, long parentFolderId, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the immediate subfolders of the parent
	* folder.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end -
	* start</code> instances. <code>start</code> and <code>end</code> are not
	* primary keys, they are indexes in the result set. Thus, <code>0</code>
	* refers to the first result in the set. Setting both <code>start</code>
	* and <code>end</code> to {@link
	* com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full
	* result set.
	* </p>
	*
	* @param repositoryId the primary key of the folder's repository
	* @param parentFolderId the primary key of the folder's parent folder
	* @param start the lower bound of the range of results
	* @param end the upper bound of the range of results (not inclusive)
	* @param obc the comparator to order the folders (optionally
	<code>null</code>)
	* @return the range of immediate subfolders of the parent folder ordered by
	comparator <code>obc</code>
	* @throws PortalException if the parent folder could not be found
	* @throws SystemException if a system exception occurred
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portal.kernel.repository.model.Folder> getFolders(
		long repositoryId, long parentFolderId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the immediate subfolders, file entries, and file
	* shortcuts in the parent folder.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end -
	* start</code> instances. <code>start</code> and <code>end</code> are not
	* primary keys, they are indexes in the result set. Thus, <code>0</code>
	* refers to the first result in the set. Setting both <code>start</code>
	* and <code>end</code> to {@link
	* com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full
	* result set.
	* </p>
	*
	* @param repositoryId the primary key of the repository
	* @param folderId the primary key of the parent folder
	* @param status the workflow status
	* @param includeMountFolders whether to include mount folders for
	third-party repositories
	* @param start the lower bound of the range of results
	* @param end the upper bound of the range of results (not inclusive)
	* @return the range of immediate subfolders, file entries, and file
	shortcuts in the parent folder ordered by comparator
	<code>obc</code>
	* @throws PortalException if the parent folder could not be found
	* @throws SystemException if a system exception occurred
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<java.lang.Object> getFoldersAndFileEntriesAndFileShortcuts(
		long repositoryId, long folderId, int status,
		boolean includeMountFolders, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the immediate subfolders, file entries,
	* and file shortcuts in the parent folder.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end -
	* start</code> instances. <code>start</code> and <code>end</code> are not
	* primary keys, they are indexes in the result set. Thus, <code>0</code>
	* refers to the first result in the set. Setting both <code>start</code>
	* and <code>end</code> to {@link
	* com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full
	* result set.
	* </p>
	*
	* @param repositoryId the primary key of the repository
	* @param folderId the primary key of the parent folder
	* @param status the workflow status
	* @param includeMountFolders whether to include mount folders for
	third-party repositories
	* @param start the lower bound of the range of results
	* @param end the upper bound of the range of results (not inclusive)
	* @param obc the comparator to order the results (optionally
	<code>null</code>)
	* @return the range of immediate subfolders, file entries, and file
	shortcuts in the parent folder ordered by comparator
	<code>obc</code>
	* @throws PortalException if the parent folder could not be found
	* @throws SystemException if a system exception occurred
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<java.lang.Object> getFoldersAndFileEntriesAndFileShortcuts(
		long repositoryId, long folderId, int status,
		boolean includeMountFolders, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<java.lang.Object> getFoldersAndFileEntriesAndFileShortcuts(
		long repositoryId, long folderId, int status,
		java.lang.String[] mimeTypes, boolean includeMountFolders, int start,
		int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of immediate subfolders, file entries, and file
	* shortcuts in the parent folder.
	*
	* @param repositoryId the primary key of the repository
	* @param folderId the primary key of the parent folder
	* @param status the workflow status
	* @param includeMountFolders whether to include mount folders for
	third-party repositories
	* @return the number of immediate subfolders, file entries, and file
	shortcuts in the parent folder
	* @throws PortalException if the folder could not be found
	* @throws SystemException if a system exception occurred
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getFoldersAndFileEntriesAndFileShortcutsCount(
		long repositoryId, long folderId, int status,
		boolean includeMountFolders)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getFoldersAndFileEntriesAndFileShortcutsCount(
		long repositoryId, long folderId, int status,
		java.lang.String[] mimeTypes, boolean includeMountFolders)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of immediate subfolders of the parent folder.
	*
	* @param repositoryId the primary key of the folder's repository
	* @param parentFolderId the primary key of the folder's parent folder
	* @return the number of immediate subfolders of the parent folder
	* @throws PortalException if the parent folder could not be found
	* @throws SystemException if a system exception occurred
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getFoldersCount(long repositoryId, long parentFolderId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of immediate subfolders of the parent folder,
	* optionally including mount folders for third-party repositories.
	*
	* @param repositoryId the primary key of the folder's repository
	* @param parentFolderId the primary key of the folder's parent folder
	* @param includeMountFolders whether to include mount folders for
	third-party repositories
	* @return the number of immediate subfolders of the parent folder
	* @throws PortalException if the parent folder could not be found
	* @throws SystemException if a system exception occurred
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getFoldersCount(long repositoryId, long parentFolderId,
		boolean includeMountFolders)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of immediate subfolders of the parent folder,
	* optionally including mount folders for third-party repositories.
	*
	* @param repositoryId the primary key of the folder's repository
	* @param parentFolderId the primary key of the folder's parent folder
	* @param status the workflow status
	* @param includeMountFolders whether to include mount folders for
	third-party repositories
	* @return the number of immediate subfolders of the parent folder
	* @throws PortalException if the parent folder could not be found
	* @throws SystemException if a system exception occurred
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getFoldersCount(long repositoryId, long parentFolderId,
		int status, boolean includeMountFolders)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of immediate subfolders and file entries across the
	* folders.
	*
	* @param repositoryId the primary key of the repository
	* @param folderIds the primary keys of folders from which to count
	immediate subfolders and file entries
	* @param status the workflow status
	* @return the number of immediate subfolders and file entries across the
	folders
	* @throws PortalException if the repository could not be found
	* @throws SystemException if a system exception occurred
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getFoldersFileEntriesCount(long repositoryId,
		java.util.List<java.lang.Long> folderIds, int status)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the file entries in the group starting at
	* the repository default parent folder that are stored within the Liferay
	* repository. This method is primarily used to search for recently modified
	* file entries. It can be limited to the file entries modified by a given
	* user.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end -
	* start</code> instances. <code>start</code> and <code>end</code> are not
	* primary keys, they are indexes in the result set. Thus, <code>0</code>
	* refers to the first result in the set. Setting both <code>start</code>
	* and <code>end</code> to {@link
	* com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full
	* result set.
	* </p>
	*
	* @param groupId the primary key of the group
	* @param userId the primary key of the user who created the file
	(optionally <code>0</code>)
	* @param start the lower bound of the range of results
	* @param end the upper bound of the range of results (not inclusive)
	* @return the range of matching file entries ordered by date modified
	* @throws PortalException if the group could not be found
	* @throws SystemException if a system exception occurred
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portal.kernel.repository.model.FileEntry> getGroupFileEntries(
		long groupId, long userId, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the file entries in the group that are
	* stored within the Liferay repository. This method is primarily used to
	* search for recently modified file entries. It can be limited to the file
	* entries modified by a given user.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end -
	* start</code> instances. <code>start</code> and <code>end</code> are not
	* primary keys, they are indexes in the result set. Thus, <code>0</code>
	* refers to the first result in the set. Setting both <code>start</code>
	* and <code>end</code> to {@link
	* com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full
	* result set.
	* </p>
	*
	* @param groupId the primary key of the group
	* @param userId the primary key of the user who created the file
	(optionally <code>0</code>)
	* @param start the lower bound of the range of results
	* @param end the upper bound of the range of results (not inclusive)
	* @param obc the comparator to order the file entries (optionally
	<code>null</code>)
	* @return the range of matching file entries ordered by comparator
	<code>obc</code>
	* @throws PortalException if the group could not be found
	* @throws SystemException if a system exception occurred
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portal.kernel.repository.model.FileEntry> getGroupFileEntries(
		long groupId, long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the file entries in the group starting at
	* the root folder that are stored within the Liferay repository. This
	* method is primarily used to search for recently modified file entries. It
	* can be limited to the file entries modified by a given user.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end -
	* start</code> instances. <code>start</code> and <code>end</code> are not
	* primary keys, they are indexes in the result set. Thus, <code>0</code>
	* refers to the first result in the set. Setting both <code>start</code>
	* and <code>end</code> to {@link
	* com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full
	* result set.
	* </p>
	*
	* @param groupId the primary key of the group
	* @param userId the primary key of the user who created the file
	(optionally <code>0</code>)
	* @param rootFolderId the primary key of the root folder to begin the
	search
	* @param start the lower bound of the range of results
	* @param end the upper bound of the range of results (not inclusive)
	* @return the range of matching file entries ordered by date modified
	* @throws PortalException if the group could not be found
	* @throws SystemException if a system exception occurred
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portal.kernel.repository.model.FileEntry> getGroupFileEntries(
		long groupId, long userId, long rootFolderId, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the file entries in the group starting at
	* the root folder that are stored within the Liferay repository. This
	* method is primarily used to search for recently modified file entries. It
	* can be limited to the file entries modified by a given user.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end -
	* start</code> instances. <code>start</code> and <code>end</code> are not
	* primary keys, they are indexes in the result set. Thus, <code>0</code>
	* refers to the first result in the set. Setting both <code>start</code>
	* and <code>end</code> to {@link
	* com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full
	* result set.
	* </p>
	*
	* @param groupId the primary key of the group
	* @param userId the primary key of the user who created the file
	(optionally <code>0</code>)
	* @param rootFolderId the primary key of the root folder to begin the
	search
	* @param start the lower bound of the range of results
	* @param end the upper bound of the range of results (not inclusive)
	* @param obc the comparator to order the file entries (optionally
	<code>null</code>)
	* @return the range of matching file entries ordered by comparator
	<code>obc</code>
	* @throws PortalException if the group could not be found
	* @throws SystemException if a system exception occurred
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portal.kernel.repository.model.FileEntry> getGroupFileEntries(
		long groupId, long userId, long rootFolderId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portal.kernel.repository.model.FileEntry> getGroupFileEntries(
		long groupId, long userId, long rootFolderId,
		java.lang.String[] mimeTypes, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of file entries in a group starting at the repository
	* default parent folder that are stored within the Liferay repository. This
	* method is primarily used to search for recently modified file entries. It
	* can be limited to the file entries modified by a given user.
	*
	* @param groupId the primary key of the group
	* @param userId the primary key of the user who created the file
	(optionally <code>0</code>)
	* @return the number of matching file entries
	* @throws PortalException if the group could not be found
	* @throws SystemException if a system exception occurred
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getGroupFileEntriesCount(long groupId, long userId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of file entries in a group starting at the root folder
	* that are stored within the Liferay repository. This method is primarily
	* used to search for recently modified file entries. It can be limited to
	* the file entries modified by a given user.
	*
	* @param groupId the primary key of the group
	* @param userId the primary key of the user who created the file
	(optionally <code>0</code>)
	* @param rootFolderId the primary key of the root folder to begin the
	search
	* @return the number of matching file entries
	* @throws PortalException if the group could not be found
	* @throws SystemException if a system exception occurred
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getGroupFileEntriesCount(long groupId, long userId,
		long rootFolderId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getGroupFileEntriesCount(long groupId, long userId,
		long rootFolderId, java.lang.String[] mimeTypes, int status)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all immediate subfolders of the parent folder that are used for
	* mounting third-party repositories. This method is only supported by the
	* Liferay repository.
	*
	* @param repositoryId the primary key of the folder's repository
	* @param parentFolderId the primary key of the folder's parent folder
	* @return the immediate subfolders of the parent folder that are used for
	mounting third-party repositories
	* @throws PortalException if the repository or parent folder could not be
	found
	* @throws SystemException if a system exception occurred
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portal.kernel.repository.model.Folder> getMountFolders(
		long repositoryId, long parentFolderId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the immediate subfolders of the parent folder that
	* are used for mounting third-party repositories. This method is only
	* supported by the Liferay repository.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end -
	* start</code> instances. <code>start</code> and <code>end</code> are not
	* primary keys, they are indexes in the result set. Thus, <code>0</code>
	* refers to the first result in the set. Setting both <code>start</code>
	* and <code>end</code> to {@link
	* com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full
	* result set.
	* </p>
	*
	* @param repositoryId the primary key of the repository
	* @param parentFolderId the primary key of the parent folder
	* @param start the lower bound of the range of results
	* @param end the upper bound of the range of results (not inclusive)
	* @return the range of immediate subfolders of the parent folder that are
	used for mounting third-party repositories
	* @throws PortalException if the repository or parent folder could not be
	found
	* @throws SystemException if a system exception occurred
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portal.kernel.repository.model.Folder> getMountFolders(
		long repositoryId, long parentFolderId, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the immediate subfolders of the parent
	* folder that are used for mounting third-party repositories. This method
	* is only supported by the Liferay repository.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end -
	* start</code> instances. <code>start</code> and <code>end</code> are not
	* primary keys, they are indexes in the result set. Thus, <code>0</code>
	* refers to the first result in the set. Setting both <code>start</code>
	* and <code>end</code> to {@link
	* com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full
	* result set.
	* </p>
	*
	* @param repositoryId the primary key of the folder's repository
	* @param parentFolderId the primary key of the folder's parent folder
	* @param start the lower bound of the range of results
	* @param end the upper bound of the range of results (not inclusive)
	* @param obc the comparator to order the folders (optionally
	<code>null</code>)
	* @return the range of immediate subfolders of the parent folder that are
	used for mounting third-party repositories ordered by comparator
	<code>obc</code>
	* @throws PortalException if the repository or parent folder could not be
	found
	* @throws SystemException if a system exception occurred
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portal.kernel.repository.model.Folder> getMountFolders(
		long repositoryId, long parentFolderId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of immediate subfolders of the parent folder that are
	* used for mounting third-party repositories. This method is only supported
	* by the Liferay repository.
	*
	* @param repositoryId the primary key of the repository
	* @param parentFolderId the primary key of the parent folder
	* @return the number of folders of the parent folder that are used for
	mounting third-party repositories
	* @throws PortalException if the repository or parent folder could not be
	found
	* @throws SystemException if a system exception occurred
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getMountFoldersCount(long repositoryId, long parentFolderId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public void getSubfolderIds(long repositoryId,
		java.util.List<java.lang.Long> folderIds, long folderId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the descendant folders of the folder with the primary key.
	*
	* @param repositoryId the primary key of the repository
	* @param folderId the primary key of the folder
	* @return the descendant folders of the folder with the primary key
	* @throws PortalException if the repository or parent folder could not be
	found
	* @throws SystemException if a system exception occurred
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<java.lang.Long> getSubfolderIds(long repositoryId,
		long folderId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns descendant folders of the folder with the primary key, optionally
	* limiting to one level deep.
	*
	* @param repositoryId the primary key of the repository
	* @param folderId the primary key of the folder
	* @param recurse whether to recurse through each subfolder
	* @return the descendant folders of the folder with the primary key
	* @throws PortalException if the repository or parent folder could not be
	found
	* @throws SystemException if a system exception occurred
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<java.lang.Long> getSubfolderIds(long repositoryId,
		long folderId, boolean recurse)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the temporary file entry names.
	*
	* @param groupId the primary key of the group
	* @param folderId the primary key of the folder where the file entry will
	eventually reside
	* @param tempFolderName the temporary folder's name
	* @return the temporary file entry names
	* @throws PortalException if the folder was invalid
	* @throws SystemException if a system exception occurred
	* @see com.liferay.portlet.documentlibrary.service.impl.DLAppServiceImpl#addTempFileEntry(
	long, long, String, String, File)
	* @see com.liferay.portal.kernel.util.TempFileUtil
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.lang.String[] getTempFileEntryNames(long groupId,
		long folderId, java.lang.String tempFolderName)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* @deprecated As of 6.2.0, replaced by {@link #checkOutFileEntry(long,
	ServiceContext)}
	*/
	public com.liferay.portal.model.Lock lockFileEntry(long fileEntryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* @deprecated As of 6.2.0, replaced by {@link #checkOutFileEntry(long,
	String, long, ServiceContext)}
	*/
	public com.liferay.portal.model.Lock lockFileEntry(long fileEntryId,
		java.lang.String owner, long expirationTime)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Locks the folder. This method is primarily used by WebDAV.
	*
	* @param repositoryId the primary key of the repository
	* @param folderId the primary key of the folder
	* @return the lock object
	* @throws PortalException if the repository or folder could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Lock lockFolder(long repositoryId,
		long folderId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Locks the folder. This method is primarily used by WebDAV.
	*
	* @param repositoryId the primary key of the repository
	* @param folderId the primary key of the folder
	* @param owner the owner string for the checkout (optionally
	<code>null</code>)
	* @param inheritable whether the lock must propagate to descendants
	* @param expirationTime the time in milliseconds before the lock expires.
	If the value is <code>0</code>, the default expiration time will
	be used from <code>portal.properties>.
	* @return the lock object
	* @throws PortalException if the repository or folder could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Lock lockFolder(long repositoryId,
		long folderId, java.lang.String owner, boolean inheritable,
		long expirationTime)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Moves the file entry to the new folder.
	*
	* @param fileEntryId the primary key of the file entry
	* @param newFolderId the primary key of the new folder
	* @param serviceContext the service context to be applied
	* @return the file entry
	* @throws PortalException if the file entry or the new folder could not be
	found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.kernel.repository.model.FileEntry moveFileEntry(
		long fileEntryId, long newFolderId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Moves the file entry from a trashed folder to the new folder.
	*
	* @param fileEntryId the primary key of the file entry
	* @param newFolderId the primary key of the new folder
	* @param serviceContext the service context to be applied
	* @return the file entry
	* @throws PortalException if the file entry or the new folder could not be
	found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.kernel.repository.model.FileEntry moveFileEntryFromTrash(
		long fileEntryId, long newFolderId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Moves the file entry with the primary key to the trash portlet.
	*
	* @param fileEntryId the primary key of the file entry
	* @throws PortalException if the file entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.kernel.repository.model.FileEntry moveFileEntryToTrash(
		long fileEntryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Moves the file shortcut from a trashed folder to the new folder.
	*
	* @param fileShortcutId the primary key of the file shortcut
	* @param newFolderId the primary key of the new folder
	* @param serviceContext the service context to be applied
	* @return the file shortcut
	* @throws PortalException if the file entry or the new folder could not be
	found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileShortcut moveFileShortcutFromTrash(
		long fileShortcutId, long newFolderId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Moves the file shortcut with the primary key to the trash portlet.
	*
	* @param fileShortcutId the primary key of the file shortcut
	* @return the file shortcut
	* @throws PortalException if the file shortcut could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileShortcut moveFileShortcutToTrash(
		long fileShortcutId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Moves the folder to the new parent folder with the primary key.
	*
	* @param folderId the primary key of the folder
	* @param parentFolderId the primary key of the new parent folder
	* @param serviceContext the service context to be applied
	* @return the file entry
	* @throws PortalException if the folder could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.kernel.repository.model.Folder moveFolder(
		long folderId, long parentFolderId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Moves the folder with the primary key from the trash portlet to the new
	* parent folder with the primary key.
	*
	* @param folderId the primary key of the folder
	* @param parentFolderId the primary key of the new parent folder
	* @param serviceContext the service context to be applied
	* @return the file entry
	* @throws PortalException if the folder could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.kernel.repository.model.Folder moveFolderFromTrash(
		long folderId, long parentFolderId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Moves the folder with the primary key to the trash portlet.
	*
	* @param folderId the primary key of the folder
	* @throws PortalException if the folder could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.kernel.repository.model.Folder moveFolderToTrash(
		long folderId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Refreshes the lock for the file entry. This method is primarily used by
	* WebDAV.
	*
	* @param lockUuid the lock's UUID
	* @param companyId the primary key of the file entry's company
	* @param expirationTime the time in milliseconds before the lock expires.
	If the value is <code>0</code>, the default expiration time will
	be used from <code>portal.properties>.
	* @return the lock object
	* @throws PortalException if the file entry or lock could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Lock refreshFileEntryLock(
		java.lang.String lockUuid, long companyId, long expirationTime)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Refreshes the lock for the folder. This method is primarily used by
	* WebDAV.
	*
	* @param lockUuid the lock's UUID
	* @param companyId the primary key of the file entry's company
	* @param expirationTime the time in milliseconds before the lock expires.
	If the value is <code>0</code>, the default expiration time will
	be used from <code>portal.properties>.
	* @return the lock object
	* @throws PortalException if the folder or lock could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Lock refreshFolderLock(
		java.lang.String lockUuid, long companyId, long expirationTime)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Restores the file entry with the primary key from the trash portlet.
	*
	* @param fileEntryId the primary key of the file entry
	* @throws PortalException if the file entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public void restoreFileEntryFromTrash(long fileEntryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Restores the file shortcut with the primary key from the trash portlet.
	*
	* @param fileShortcutId the primary key of the file shortcut
	* @throws PortalException if the file shortcut could not be found
	* @throws SystemException if a system exception occurred
	*/
	public void restoreFileShortcutFromTrash(long fileShortcutId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Restores the folder with the primary key from the trash portlet.
	*
	* @param folderId the primary key of the folder
	* @throws PortalException if the folder could not be found
	* @throws SystemException if a system exception occurred
	*/
	public void restoreFolderFromTrash(long folderId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Reverts the file entry to a previous version. A new version will be
	* created based on the previous version and metadata.
	*
	* @param fileEntryId the primary key of the file entry
	* @param version the version to revert back to
	* @param serviceContext the service context to be applied
	* @throws PortalException if the file entry or version could not be found
	* @throws SystemException if a system exception occurred
	*/
	public void revertFileEntry(long fileEntryId, java.lang.String version,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portal.kernel.search.Hits search(long repositoryId,
		com.liferay.portal.kernel.search.SearchContext searchContext)
		throws com.liferay.portal.kernel.search.SearchException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portal.kernel.search.Hits search(long repositoryId,
		com.liferay.portal.kernel.search.SearchContext searchContext,
		com.liferay.portal.kernel.search.Query query)
		throws com.liferay.portal.kernel.search.SearchException;

	/**
	* Subscribe the user to changes in documents of the file entry type. This
	* method is only supported by the Liferay repository.
	*
	* @param groupId the primary key of the file entry type's group
	* @param fileEntryTypeId the primary key of the file entry type
	* @throws PortalException if the user or group could not be found, or if
	subscribing was not permissible
	* @throws SystemException if a system exception occurred
	*/
	public void subscribeFileEntryType(long groupId, long fileEntryTypeId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Subscribe the user to document changes in the folder. This method is only
	* supported by the Liferay repository.
	*
	* @param groupId the primary key of the folder's group
	* @param folderId the primary key of the folder
	* @throws PortalException if the user or group could not be found, or if
	subscribing was not permissible
	* @throws SystemException if a system exception occurred
	*/
	public void subscribeFolder(long groupId, long folderId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* @deprecated As of 6.2.0, replaced by {@link #checkInFileEntry(long,
	boolean, String, ServiceContext)}.
	*/
	public void unlockFileEntry(long fileEntryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* @deprecated As of 6.2.0, replaced by {@link #checkInFileEntry(long,
	String)}.
	*/
	public void unlockFileEntry(long fileEntryId, java.lang.String lockUuid)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Unlocks the folder. This method is primarily used by WebDAV.
	*
	* @param repositoryId the primary key of the repository
	* @param folderId the primary key of the folder
	* @param lockUuid the lock's UUID
	* @throws PortalException if the repository or folder could not be found
	* @throws SystemException if a system exception occurred
	*/
	public void unlockFolder(long repositoryId, long folderId,
		java.lang.String lockUuid)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Unlocks the folder. This method is primarily used by WebDAV.
	*
	* @param repositoryId the primary key of the repository
	* @param parentFolderId the primary key of the parent folder
	* @param name the folder's name
	* @param lockUuid the lock's UUID
	* @throws PortalException if the repository or folder could not be found
	* @throws SystemException if a system exception occurred
	*/
	public void unlockFolder(long repositoryId, long parentFolderId,
		java.lang.String name, java.lang.String lockUuid)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Unsubscribe the user from changes in documents of the file entry type.
	* This method is only supported by the Liferay repository.
	*
	* @param groupId the primary key of the file entry type's group
	* @param fileEntryTypeId the primary key of the file entry type
	* @throws PortalException if the user or group could not be found, or if
	unsubscribing was not permissible
	* @throws SystemException if a system exception occurred
	*/
	public void unsubscribeFileEntryType(long groupId, long fileEntryTypeId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Unsubscribe the user from document changes in the folder. This method is
	* only supported by the Liferay repository.
	*
	* @param groupId the primary key of the folder's group
	* @param folderId the primary key of the folder
	* @throws PortalException if the user or group could not be found, or if
	unsubscribing was not permissible
	* @throws SystemException if a system exception occurred
	*/
	public void unsubscribeFolder(long groupId, long folderId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

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
	* @throws PortalException if the file entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.kernel.repository.model.FileEntry updateFileEntry(
		long fileEntryId, java.lang.String sourceFileName,
		java.lang.String mimeType, java.lang.String title,
		java.lang.String description, java.lang.String changeLog,
		boolean majorVersion, byte[] bytes,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Updates a file entry and associated metadata based on a {@link
	* java.io.File} object. If the file data is <code>null</code>, then only
	* the associated metadata (i.e., <code>title</code>,
	* <code>description</code>, and parameters in the
	* <code>serviceContext</code>) will be updated.
	*
	* <p>
	* This method takes two file names, the <code>sourceFileName</code> and the
	* <code>title</code>. The <code>sourceFileName</code> corresponds to the
	* name of the actual file being uploaded. The <code>title</code>
	* corresponds to a name the client wishes to assign this file after it has
	* been uploaded to the portal.
	* </p>
	*
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
	* @param file EntryId the primary key of the file entry
	* @param serviceContext the service context to be applied. Can set the
	asset category IDs, asset tag names, and expando bridge
	attributes for the file entry. In a Liferay repository, it may
	include:  <ul> <li> fileEntryTypeId - ID for a custom file entry
	type </li> <li> fieldsMap - mapping for fields associated with a
	custom file entry type </li> </ul>
	* @return the file entry
	* @throws PortalException if the file entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.kernel.repository.model.FileEntry updateFileEntry(
		long fileEntryId, java.lang.String sourceFileName,
		java.lang.String mimeType, java.lang.String title,
		java.lang.String description, java.lang.String changeLog,
		boolean majorVersion, java.io.File file,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Updates a file entry and associated metadata based on an {@link java.io.
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
	* @throws PortalException if the file entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.kernel.repository.model.FileEntry updateFileEntry(
		long fileEntryId, java.lang.String sourceFileName,
		java.lang.String mimeType, java.lang.String title,
		java.lang.String description, java.lang.String changeLog,
		boolean majorVersion, java.io.InputStream is, long size,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.kernel.repository.model.FileEntry updateFileEntryAndCheckIn(
		long fileEntryId, java.lang.String sourceFileName,
		java.lang.String mimeType, java.lang.String title,
		java.lang.String description, java.lang.String changeLog,
		boolean majorVersion, java.io.File file,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.kernel.repository.model.FileEntry updateFileEntryAndCheckIn(
		long fileEntryId, java.lang.String sourceFileName,
		java.lang.String mimeType, java.lang.String title,
		java.lang.String description, java.lang.String changeLog,
		boolean majorVersion, java.io.InputStream is, long size,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Updates a file shortcut to the existing file entry. This method is only
	* supported by the Liferay repository.
	*
	* @param fileShortcutId the primary key of the file shortcut
	* @param folderId the primary key of the file shortcut's parent folder
	* @param toFileEntryId the primary key of the file shortcut's file entry
	* @param serviceContext the service context to be applied. Can set the
	asset category IDs, asset tag names, and expando bridge
	attributes for the file entry.
	* @return the file shortcut
	* @throws PortalException if the file shortcut, folder, or file entry could
	not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileShortcut updateFileShortcut(
		long fileShortcutId, long folderId, long toFileEntryId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Updates the folder.
	*
	* @param folderId the primary key of the folder
	* @param name the folder's new name
	* @param description the folder's new description
	* @param serviceContext the service context to be applied. In a Liferay
	repository, it may include:  <ul> <li> defaultFileEntryTypeId -
	the file entry type to default all Liferay file entries to </li>
	<li> dlFileEntryTypesSearchContainerPrimaryKeys - a
	comma-delimited list of file entry type primary keys allowed in
	the given folder and all descendants </li> <li>
	overrideFileEntryTypes - boolean specifying whether to override
	ancestral folder's restriction of file entry types allowed </li>
	<li> workflowDefinitionXYZ - the workflow definition name
	specified per file entry type. The parameter name must be the
	string <code>workflowDefinition</code> appended by the <code>
	fileEntryTypeId</code> (optionally <code>0</code>). </li> </ul>
	* @return the folder
	* @throws PortalException if the current or new parent folder could not be
	found or if the new parent folder's information was invalid
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.kernel.repository.model.Folder updateFolder(
		long folderId, java.lang.String name, java.lang.String description,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns <code>true</code> if the file entry is checked out. This method
	* is primarily used by WebDAV.
	*
	* @param repositoryId the primary key for the repository
	* @param fileEntryId the primary key for the file entry
	* @param lockUuid the lock's UUID
	* @return <code>true</code> if the file entry is checked out;
	<code>false</code> otherwise
	* @throws PortalException if the file entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public boolean verifyFileEntryCheckOut(long repositoryId, long fileEntryId,
		java.lang.String lockUuid)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	public boolean verifyFileEntryLock(long repositoryId, long fileEntryId,
		java.lang.String lockUuid)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns <code>true</code> if the inheritable lock exists. This method is
	* primarily used by WebDAV.
	*
	* @param repositoryId the primary key for the repository
	* @param folderId the primary key for the folder
	* @param lockUuid the lock's UUID
	* @return <code>true</code> if the inheritable lock exists;
	<code>false</code> otherwise
	* @throws PortalException if the folder could not be found
	* @throws SystemException if a system exception occurred
	*/
	public boolean verifyInheritableLock(long repositoryId, long folderId,
		java.lang.String lockUuid)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;
}