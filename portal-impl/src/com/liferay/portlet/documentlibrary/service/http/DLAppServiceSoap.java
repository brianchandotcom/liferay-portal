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

package com.liferay.portlet.documentlibrary.service.http;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ListUtil;

import com.liferay.portlet.documentlibrary.service.DLAppServiceUtil;

import java.rmi.RemoteException;

/**
 * Provides the SOAP utility for the
 * {@link com.liferay.portlet.documentlibrary.service.DLAppServiceUtil} service utility. The
 * static methods of this class calls the same methods of the service utility.
 * However, the signatures are different because it is difficult for SOAP to
 * support certain types.
 *
 * <p>
 * The benefits of using the SOAP utility is that it is cross platform
 * compatible. SOAP allows different languages like Java, .NET, C++, PHP, and
 * even Perl, to call the generated services. One drawback of SOAP is that it is
 * slow because it needs to serialize all calls into a text format (XML).
 * </p>
 *
 * <p>
 * You can see a list of services at http://localhost:8080/api/axis. Set the
 * property <b>axis.servlet.hosts.allowed</b> in portal.properties to configure
 * security.
 * </p>
 *
 * <p>
 * The SOAP utility is only generated for remote services.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       DLAppServiceHttp
 * @see       com.liferay.portlet.documentlibrary.service.DLAppServiceUtil
 * @generated
 */
public class DLAppServiceSoap {
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
	public static com.liferay.portal.kernel.repository.model.FileEntrySoap addFileEntry(
		long repositoryId, long folderId, java.lang.String sourceFileName,
		java.lang.String mimeType, java.lang.String title,
		java.lang.String description, java.lang.String changeLog, byte[] bytes,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			com.liferay.portal.kernel.repository.model.FileEntry returnValue = DLAppServiceUtil.addFileEntry(repositoryId,
					folderId, sourceFileName, mimeType, title, description,
					changeLog, bytes, serviceContext);

			return com.liferay.portal.kernel.repository.model.FileEntrySoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

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
	public static com.liferay.portlet.documentlibrary.model.DLFileShortcutSoap addFileShortcut(
		long repositoryId, long folderId, long toFileEntryId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			com.liferay.portlet.documentlibrary.model.DLFileShortcut returnValue =
				DLAppServiceUtil.addFileShortcut(repositoryId, folderId,
					toFileEntryId, serviceContext);

			return com.liferay.portlet.documentlibrary.model.DLFileShortcutSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

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
	public static com.liferay.portal.kernel.repository.model.FolderSoap addFolder(
		long repositoryId, long parentFolderId, java.lang.String name,
		java.lang.String description,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			com.liferay.portal.kernel.repository.model.Folder returnValue = DLAppServiceUtil.addFolder(repositoryId,
					parentFolderId, name, description, serviceContext);

			return com.liferay.portal.kernel.repository.model.FolderSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

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
	public static void cancelCheckOut(long fileEntryId)
		throws RemoteException {
		try {
			DLAppServiceUtil.cancelCheckOut(fileEntryId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

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
	public static void checkInFileEntry(long fileEntryId, boolean majorVersion,
		java.lang.String changeLog,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			DLAppServiceUtil.checkInFileEntry(fileEntryId, majorVersion,
				changeLog, serviceContext);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	* @deprecated As of 6.2.0, replaced by {@link #checkInFileEntry(long,
	String, ServiceContext)}
	*/
	public static void checkInFileEntry(long fileEntryId,
		java.lang.String lockUuid) throws RemoteException {
		try {
			DLAppServiceUtil.checkInFileEntry(fileEntryId, lockUuid);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

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
	public static void checkInFileEntry(long fileEntryId,
		java.lang.String lockUuid,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			DLAppServiceUtil.checkInFileEntry(fileEntryId, lockUuid,
				serviceContext);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

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
	public static void checkOutFileEntry(long fileEntryId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			DLAppServiceUtil.checkOutFileEntry(fileEntryId, serviceContext);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

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
	public static com.liferay.portal.kernel.repository.model.FileEntrySoap checkOutFileEntry(
		long fileEntryId, java.lang.String owner, long expirationTime,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			com.liferay.portal.kernel.repository.model.FileEntry returnValue = DLAppServiceUtil.checkOutFileEntry(fileEntryId,
					owner, expirationTime, serviceContext);

			return com.liferay.portal.kernel.repository.model.FileEntrySoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

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
	public static com.liferay.portal.kernel.repository.model.FolderSoap copyFolder(
		long repositoryId, long sourceFolderId, long parentFolderId,
		java.lang.String name, java.lang.String description,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			com.liferay.portal.kernel.repository.model.Folder returnValue = DLAppServiceUtil.copyFolder(repositoryId,
					sourceFolderId, parentFolderId, name, description,
					serviceContext);

			return com.liferay.portal.kernel.repository.model.FolderSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	* Deletes the file entry with the primary key.
	*
	* @param fileEntryId the primary key of the file entry
	* @throws PortalException if the file entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static void deleteFileEntry(long fileEntryId)
		throws RemoteException {
		try {
			DLAppServiceUtil.deleteFileEntry(fileEntryId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	* Deletes the file entry with the title in the folder.
	*
	* @param repositoryId the primary key of the repository
	* @param folderId the primary key of the file entry's parent folder
	* @param title the file entry's title
	* @throws PortalException if the file entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static void deleteFileEntryByTitle(long repositoryId, long folderId,
		java.lang.String title) throws RemoteException {
		try {
			DLAppServiceUtil.deleteFileEntryByTitle(repositoryId, folderId,
				title);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	* Deletes the file shortcut with the primary key. This method is only
	* supported by the Liferay repository.
	*
	* @param fileShortcutId the primary key of the file shortcut
	* @throws PortalException if the file shortcut could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static void deleteFileShortcut(long fileShortcutId)
		throws RemoteException {
		try {
			DLAppServiceUtil.deleteFileShortcut(fileShortcutId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

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
	public static void deleteFileVersion(long fileEntryId,
		java.lang.String version) throws RemoteException {
		try {
			DLAppServiceUtil.deleteFileVersion(fileEntryId, version);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	* Deletes the folder with the primary key and all of its subfolders and
	* file entries.
	*
	* @param folderId the primary key of the folder
	* @throws PortalException if the folder could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static void deleteFolder(long folderId) throws RemoteException {
		try {
			DLAppServiceUtil.deleteFolder(folderId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

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
	public static void deleteFolder(long repositoryId, long parentFolderId,
		java.lang.String name) throws RemoteException {
		try {
			DLAppServiceUtil.deleteFolder(repositoryId, parentFolderId, name);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

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
	public static void deleteTempFileEntry(long groupId, long folderId,
		java.lang.String fileName, java.lang.String tempFolderName)
		throws RemoteException {
		try {
			DLAppServiceUtil.deleteTempFileEntry(groupId, folderId, fileName,
				tempFolderName);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	* Returns all the file entries in the folder.
	*
	* @param repositoryId the primary key of the file entry's repository
	* @param folderId the primary key of the file entry's folder
	* @return the file entries in the folder
	* @throws PortalException if the folder could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.kernel.repository.model.FileEntrySoap[] getFileEntries(
		long repositoryId, long folderId) throws RemoteException {
		try {
			java.util.List<com.liferay.portal.kernel.repository.model.FileEntry> returnValue =
				DLAppServiceUtil.getFileEntries(repositoryId, folderId);

			return com.liferay.portal.kernel.repository.model.FileEntrySoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

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
	public static com.liferay.portal.kernel.repository.model.FileEntrySoap[] getFileEntries(
		long repositoryId, long folderId, int start, int end)
		throws RemoteException {
		try {
			java.util.List<com.liferay.portal.kernel.repository.model.FileEntry> returnValue =
				DLAppServiceUtil.getFileEntries(repositoryId, folderId, start,
					end);

			return com.liferay.portal.kernel.repository.model.FileEntrySoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

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
	public static com.liferay.portal.kernel.repository.model.FileEntrySoap[] getFileEntries(
		long repositoryId, long folderId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws RemoteException {
		try {
			java.util.List<com.liferay.portal.kernel.repository.model.FileEntry> returnValue =
				DLAppServiceUtil.getFileEntries(repositoryId, folderId, start,
					end, obc);

			return com.liferay.portal.kernel.repository.model.FileEntrySoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

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
	public static com.liferay.portal.kernel.repository.model.FileEntrySoap[] getFileEntries(
		long repositoryId, long folderId, long fileEntryTypeId)
		throws RemoteException {
		try {
			java.util.List<com.liferay.portal.kernel.repository.model.FileEntry> returnValue =
				DLAppServiceUtil.getFileEntries(repositoryId, folderId,
					fileEntryTypeId);

			return com.liferay.portal.kernel.repository.model.FileEntrySoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

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
	public static com.liferay.portal.kernel.repository.model.FileEntrySoap[] getFileEntries(
		long repositoryId, long folderId, long fileEntryTypeId, int start,
		int end) throws RemoteException {
		try {
			java.util.List<com.liferay.portal.kernel.repository.model.FileEntry> returnValue =
				DLAppServiceUtil.getFileEntries(repositoryId, folderId,
					fileEntryTypeId, start, end);

			return com.liferay.portal.kernel.repository.model.FileEntrySoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

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
	public static com.liferay.portal.kernel.repository.model.FileEntrySoap[] getFileEntries(
		long repositoryId, long folderId, long fileEntryTypeId, int start,
		int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws RemoteException {
		try {
			java.util.List<com.liferay.portal.kernel.repository.model.FileEntry> returnValue =
				DLAppServiceUtil.getFileEntries(repositoryId, folderId,
					fileEntryTypeId, start, end, obc);

			return com.liferay.portal.kernel.repository.model.FileEntrySoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

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
	public static int getFileEntriesAndFileShortcutsCount(long repositoryId,
		long folderId, int status) throws RemoteException {
		try {
			int returnValue = DLAppServiceUtil.getFileEntriesAndFileShortcutsCount(repositoryId,
					folderId, status);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

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
	public static int getFileEntriesAndFileShortcutsCount(long repositoryId,
		long folderId, int status, java.lang.String[] mimeTypes)
		throws RemoteException {
		try {
			int returnValue = DLAppServiceUtil.getFileEntriesAndFileShortcutsCount(repositoryId,
					folderId, status, mimeTypes);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	* Returns the number of file entries in the folder.
	*
	* @param repositoryId the primary key of the file entry's repository
	* @param folderId the primary key of the file entry's folder
	* @return the number of file entries in the folder
	* @throws PortalException if the folder could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static int getFileEntriesCount(long repositoryId, long folderId)
		throws RemoteException {
		try {
			int returnValue = DLAppServiceUtil.getFileEntriesCount(repositoryId,
					folderId);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

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
	public static int getFileEntriesCount(long repositoryId, long folderId,
		long fileEntryTypeId) throws RemoteException {
		try {
			int returnValue = DLAppServiceUtil.getFileEntriesCount(repositoryId,
					folderId, fileEntryTypeId);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	* Returns the file entry with the primary key.
	*
	* @param fileEntryId the primary key of the file entry
	* @return the file entry with the primary key
	* @throws PortalException if the file entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.kernel.repository.model.FileEntrySoap getFileEntry(
		long fileEntryId) throws RemoteException {
		try {
			com.liferay.portal.kernel.repository.model.FileEntry returnValue = DLAppServiceUtil.getFileEntry(fileEntryId);

			return com.liferay.portal.kernel.repository.model.FileEntrySoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

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
	public static com.liferay.portal.kernel.repository.model.FileEntrySoap getFileEntry(
		long groupId, long folderId, java.lang.String title)
		throws RemoteException {
		try {
			com.liferay.portal.kernel.repository.model.FileEntry returnValue = DLAppServiceUtil.getFileEntry(groupId,
					folderId, title);

			return com.liferay.portal.kernel.repository.model.FileEntrySoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	* Returns the file entry with the UUID and group.
	*
	* @param uuid the file entry's UUID
	* @param groupId the primary key of the file entry's group
	* @return the file entry with the UUID and group
	* @throws PortalException if the file entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.kernel.repository.model.FileEntrySoap getFileEntryByUuidAndGroupId(
		java.lang.String uuid, long groupId) throws RemoteException {
		try {
			com.liferay.portal.kernel.repository.model.FileEntry returnValue = DLAppServiceUtil.getFileEntryByUuidAndGroupId(uuid,
					groupId);

			return com.liferay.portal.kernel.repository.model.FileEntrySoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	* Returns the file shortcut with the primary key. This method is only
	* supported by the Liferay repository.
	*
	* @param fileShortcutId the primary key of the file shortcut
	* @return the file shortcut with the primary key
	* @throws PortalException if the file shortcut could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.documentlibrary.model.DLFileShortcutSoap getFileShortcut(
		long fileShortcutId) throws RemoteException {
		try {
			com.liferay.portlet.documentlibrary.model.DLFileShortcut returnValue =
				DLAppServiceUtil.getFileShortcut(fileShortcutId);

			return com.liferay.portlet.documentlibrary.model.DLFileShortcutSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	* Returns the folder with the primary key.
	*
	* @param folderId the primary key of the folder
	* @return the folder with the primary key
	* @throws PortalException if the folder could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.kernel.repository.model.FolderSoap getFolder(
		long folderId) throws RemoteException {
		try {
			com.liferay.portal.kernel.repository.model.Folder returnValue = DLAppServiceUtil.getFolder(folderId);

			return com.liferay.portal.kernel.repository.model.FolderSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

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
	public static com.liferay.portal.kernel.repository.model.FolderSoap getFolder(
		long repositoryId, long parentFolderId, java.lang.String name)
		throws RemoteException {
		try {
			com.liferay.portal.kernel.repository.model.Folder returnValue = DLAppServiceUtil.getFolder(repositoryId,
					parentFolderId, name);

			return com.liferay.portal.kernel.repository.model.FolderSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	* Returns all immediate subfolders of the parent folder.
	*
	* @param repositoryId the primary key of the folder's repository
	* @param parentFolderId the primary key of the folder's parent folder
	* @return the immediate subfolders of the parent folder
	* @throws PortalException if the parent folder could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.kernel.repository.model.FolderSoap[] getFolders(
		long repositoryId, long parentFolderId) throws RemoteException {
		try {
			java.util.List<com.liferay.portal.kernel.repository.model.Folder> returnValue =
				DLAppServiceUtil.getFolders(repositoryId, parentFolderId);

			return com.liferay.portal.kernel.repository.model.FolderSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

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
	public static com.liferay.portal.kernel.repository.model.FolderSoap[] getFolders(
		long repositoryId, long parentFolderId, boolean includeMountFolders)
		throws RemoteException {
		try {
			java.util.List<com.liferay.portal.kernel.repository.model.Folder> returnValue =
				DLAppServiceUtil.getFolders(repositoryId, parentFolderId,
					includeMountFolders);

			return com.liferay.portal.kernel.repository.model.FolderSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

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
	public static com.liferay.portal.kernel.repository.model.FolderSoap[] getFolders(
		long repositoryId, long parentFolderId, boolean includeMountFolders,
		int start, int end) throws RemoteException {
		try {
			java.util.List<com.liferay.portal.kernel.repository.model.Folder> returnValue =
				DLAppServiceUtil.getFolders(repositoryId, parentFolderId,
					includeMountFolders, start, end);

			return com.liferay.portal.kernel.repository.model.FolderSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

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
	public static com.liferay.portal.kernel.repository.model.FolderSoap[] getFolders(
		long repositoryId, long parentFolderId, boolean includeMountFolders,
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws RemoteException {
		try {
			java.util.List<com.liferay.portal.kernel.repository.model.Folder> returnValue =
				DLAppServiceUtil.getFolders(repositoryId, parentFolderId,
					includeMountFolders, start, end, obc);

			return com.liferay.portal.kernel.repository.model.FolderSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

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
	public static com.liferay.portal.kernel.repository.model.FolderSoap[] getFolders(
		long repositoryId, long parentFolderId, int status,
		boolean includeMountFolders, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws RemoteException {
		try {
			java.util.List<com.liferay.portal.kernel.repository.model.Folder> returnValue =
				DLAppServiceUtil.getFolders(repositoryId, parentFolderId,
					status, includeMountFolders, start, end, obc);

			return com.liferay.portal.kernel.repository.model.FolderSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

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
	public static com.liferay.portal.kernel.repository.model.FolderSoap[] getFolders(
		long repositoryId, long parentFolderId, int start, int end)
		throws RemoteException {
		try {
			java.util.List<com.liferay.portal.kernel.repository.model.Folder> returnValue =
				DLAppServiceUtil.getFolders(repositoryId, parentFolderId,
					start, end);

			return com.liferay.portal.kernel.repository.model.FolderSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

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
	public static com.liferay.portal.kernel.repository.model.FolderSoap[] getFolders(
		long repositoryId, long parentFolderId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws RemoteException {
		try {
			java.util.List<com.liferay.portal.kernel.repository.model.Folder> returnValue =
				DLAppServiceUtil.getFolders(repositoryId, parentFolderId,
					start, end, obc);

			return com.liferay.portal.kernel.repository.model.FolderSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

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
	public static int getFoldersAndFileEntriesAndFileShortcutsCount(
		long repositoryId, long folderId, int status,
		boolean includeMountFolders) throws RemoteException {
		try {
			int returnValue = DLAppServiceUtil.getFoldersAndFileEntriesAndFileShortcutsCount(repositoryId,
					folderId, status, includeMountFolders);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getFoldersAndFileEntriesAndFileShortcutsCount(
		long repositoryId, long folderId, int status,
		java.lang.String[] mimeTypes, boolean includeMountFolders)
		throws RemoteException {
		try {
			int returnValue = DLAppServiceUtil.getFoldersAndFileEntriesAndFileShortcutsCount(repositoryId,
					folderId, status, mimeTypes, includeMountFolders);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	* Returns the number of immediate subfolders of the parent folder.
	*
	* @param repositoryId the primary key of the folder's repository
	* @param parentFolderId the primary key of the folder's parent folder
	* @return the number of immediate subfolders of the parent folder
	* @throws PortalException if the parent folder could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static int getFoldersCount(long repositoryId, long parentFolderId)
		throws RemoteException {
		try {
			int returnValue = DLAppServiceUtil.getFoldersCount(repositoryId,
					parentFolderId);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

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
	public static int getFoldersCount(long repositoryId, long parentFolderId,
		boolean includeMountFolders) throws RemoteException {
		try {
			int returnValue = DLAppServiceUtil.getFoldersCount(repositoryId,
					parentFolderId, includeMountFolders);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

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
	public static int getFoldersCount(long repositoryId, long parentFolderId,
		int status, boolean includeMountFolders) throws RemoteException {
		try {
			int returnValue = DLAppServiceUtil.getFoldersCount(repositoryId,
					parentFolderId, status, includeMountFolders);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

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
	public static int getFoldersFileEntriesCount(long repositoryId,
		Long[] folderIds, int status) throws RemoteException {
		try {
			int returnValue = DLAppServiceUtil.getFoldersFileEntriesCount(repositoryId,
					ListUtil.toList(folderIds), status);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

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
	public static com.liferay.portal.kernel.repository.model.FileEntrySoap[] getGroupFileEntries(
		long groupId, long userId, int start, int end)
		throws RemoteException {
		try {
			java.util.List<com.liferay.portal.kernel.repository.model.FileEntry> returnValue =
				DLAppServiceUtil.getGroupFileEntries(groupId, userId, start, end);

			return com.liferay.portal.kernel.repository.model.FileEntrySoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

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
	public static com.liferay.portal.kernel.repository.model.FileEntrySoap[] getGroupFileEntries(
		long groupId, long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws RemoteException {
		try {
			java.util.List<com.liferay.portal.kernel.repository.model.FileEntry> returnValue =
				DLAppServiceUtil.getGroupFileEntries(groupId, userId, start,
					end, obc);

			return com.liferay.portal.kernel.repository.model.FileEntrySoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

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
	public static com.liferay.portal.kernel.repository.model.FileEntrySoap[] getGroupFileEntries(
		long groupId, long userId, long rootFolderId, int start, int end)
		throws RemoteException {
		try {
			java.util.List<com.liferay.portal.kernel.repository.model.FileEntry> returnValue =
				DLAppServiceUtil.getGroupFileEntries(groupId, userId,
					rootFolderId, start, end);

			return com.liferay.portal.kernel.repository.model.FileEntrySoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

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
	public static com.liferay.portal.kernel.repository.model.FileEntrySoap[] getGroupFileEntries(
		long groupId, long userId, long rootFolderId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws RemoteException {
		try {
			java.util.List<com.liferay.portal.kernel.repository.model.FileEntry> returnValue =
				DLAppServiceUtil.getGroupFileEntries(groupId, userId,
					rootFolderId, start, end, obc);

			return com.liferay.portal.kernel.repository.model.FileEntrySoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portal.kernel.repository.model.FileEntrySoap[] getGroupFileEntries(
		long groupId, long userId, long rootFolderId,
		java.lang.String[] mimeTypes, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws RemoteException {
		try {
			java.util.List<com.liferay.portal.kernel.repository.model.FileEntry> returnValue =
				DLAppServiceUtil.getGroupFileEntries(groupId, userId,
					rootFolderId, mimeTypes, status, start, end, obc);

			return com.liferay.portal.kernel.repository.model.FileEntrySoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

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
	public static int getGroupFileEntriesCount(long groupId, long userId)
		throws RemoteException {
		try {
			int returnValue = DLAppServiceUtil.getGroupFileEntriesCount(groupId,
					userId);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

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
	public static int getGroupFileEntriesCount(long groupId, long userId,
		long rootFolderId) throws RemoteException {
		try {
			int returnValue = DLAppServiceUtil.getGroupFileEntriesCount(groupId,
					userId, rootFolderId);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getGroupFileEntriesCount(long groupId, long userId,
		long rootFolderId, java.lang.String[] mimeTypes, int status)
		throws RemoteException {
		try {
			int returnValue = DLAppServiceUtil.getGroupFileEntriesCount(groupId,
					userId, rootFolderId, mimeTypes, status);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

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
	public static com.liferay.portal.kernel.repository.model.FolderSoap[] getMountFolders(
		long repositoryId, long parentFolderId) throws RemoteException {
		try {
			java.util.List<com.liferay.portal.kernel.repository.model.Folder> returnValue =
				DLAppServiceUtil.getMountFolders(repositoryId, parentFolderId);

			return com.liferay.portal.kernel.repository.model.FolderSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

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
	public static com.liferay.portal.kernel.repository.model.FolderSoap[] getMountFolders(
		long repositoryId, long parentFolderId, int start, int end)
		throws RemoteException {
		try {
			java.util.List<com.liferay.portal.kernel.repository.model.Folder> returnValue =
				DLAppServiceUtil.getMountFolders(repositoryId, parentFolderId,
					start, end);

			return com.liferay.portal.kernel.repository.model.FolderSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

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
	public static com.liferay.portal.kernel.repository.model.FolderSoap[] getMountFolders(
		long repositoryId, long parentFolderId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws RemoteException {
		try {
			java.util.List<com.liferay.portal.kernel.repository.model.Folder> returnValue =
				DLAppServiceUtil.getMountFolders(repositoryId, parentFolderId,
					start, end, obc);

			return com.liferay.portal.kernel.repository.model.FolderSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

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
	public static int getMountFoldersCount(long repositoryId,
		long parentFolderId) throws RemoteException {
		try {
			int returnValue = DLAppServiceUtil.getMountFoldersCount(repositoryId,
					parentFolderId);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void getSubfolderIds(long repositoryId, Long[] folderIds,
		long folderId) throws RemoteException {
		try {
			DLAppServiceUtil.getSubfolderIds(repositoryId,
				ListUtil.toList(folderIds), folderId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

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
	public static java.lang.Long[] getSubfolderIds(long repositoryId,
		long folderId) throws RemoteException {
		try {
			java.util.List<java.lang.Long> returnValue = DLAppServiceUtil.getSubfolderIds(repositoryId,
					folderId);

			return returnValue.toArray(new java.lang.Long[returnValue.size()]);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

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
	public static java.lang.Long[] getSubfolderIds(long repositoryId,
		long folderId, boolean recurse) throws RemoteException {
		try {
			java.util.List<java.lang.Long> returnValue = DLAppServiceUtil.getSubfolderIds(repositoryId,
					folderId, recurse);

			return returnValue.toArray(new java.lang.Long[returnValue.size()]);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

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
	public static java.lang.String[] getTempFileEntryNames(long groupId,
		long folderId, java.lang.String tempFolderName)
		throws RemoteException {
		try {
			java.lang.String[] returnValue = DLAppServiceUtil.getTempFileEntryNames(groupId,
					folderId, tempFolderName);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

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
	public static com.liferay.portal.kernel.repository.model.FileEntrySoap moveFileEntry(
		long fileEntryId, long newFolderId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			com.liferay.portal.kernel.repository.model.FileEntry returnValue = DLAppServiceUtil.moveFileEntry(fileEntryId,
					newFolderId, serviceContext);

			return com.liferay.portal.kernel.repository.model.FileEntrySoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

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
	public static com.liferay.portal.kernel.repository.model.FileEntrySoap moveFileEntryFromTrash(
		long fileEntryId, long newFolderId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			com.liferay.portal.kernel.repository.model.FileEntry returnValue = DLAppServiceUtil.moveFileEntryFromTrash(fileEntryId,
					newFolderId, serviceContext);

			return com.liferay.portal.kernel.repository.model.FileEntrySoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	* Moves the file entry with the primary key to the trash portlet.
	*
	* @param fileEntryId the primary key of the file entry
	* @throws PortalException if the file entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.kernel.repository.model.FileEntrySoap moveFileEntryToTrash(
		long fileEntryId) throws RemoteException {
		try {
			com.liferay.portal.kernel.repository.model.FileEntry returnValue = DLAppServiceUtil.moveFileEntryToTrash(fileEntryId);

			return com.liferay.portal.kernel.repository.model.FileEntrySoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

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
	public static com.liferay.portlet.documentlibrary.model.DLFileShortcutSoap moveFileShortcutFromTrash(
		long fileShortcutId, long newFolderId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			com.liferay.portlet.documentlibrary.model.DLFileShortcut returnValue =
				DLAppServiceUtil.moveFileShortcutFromTrash(fileShortcutId,
					newFolderId, serviceContext);

			return com.liferay.portlet.documentlibrary.model.DLFileShortcutSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	* Moves the file shortcut with the primary key to the trash portlet.
	*
	* @param fileShortcutId the primary key of the file shortcut
	* @return the file shortcut
	* @throws PortalException if the file shortcut could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.documentlibrary.model.DLFileShortcutSoap moveFileShortcutToTrash(
		long fileShortcutId) throws RemoteException {
		try {
			com.liferay.portlet.documentlibrary.model.DLFileShortcut returnValue =
				DLAppServiceUtil.moveFileShortcutToTrash(fileShortcutId);

			return com.liferay.portlet.documentlibrary.model.DLFileShortcutSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

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
	public static com.liferay.portal.kernel.repository.model.FolderSoap moveFolder(
		long folderId, long parentFolderId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			com.liferay.portal.kernel.repository.model.Folder returnValue = DLAppServiceUtil.moveFolder(folderId,
					parentFolderId, serviceContext);

			return com.liferay.portal.kernel.repository.model.FolderSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

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
	public static com.liferay.portal.kernel.repository.model.FolderSoap moveFolderFromTrash(
		long folderId, long parentFolderId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			com.liferay.portal.kernel.repository.model.Folder returnValue = DLAppServiceUtil.moveFolderFromTrash(folderId,
					parentFolderId, serviceContext);

			return com.liferay.portal.kernel.repository.model.FolderSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	* Moves the folder with the primary key to the trash portlet.
	*
	* @param folderId the primary key of the folder
	* @throws PortalException if the folder could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.kernel.repository.model.FolderSoap moveFolderToTrash(
		long folderId) throws RemoteException {
		try {
			com.liferay.portal.kernel.repository.model.Folder returnValue = DLAppServiceUtil.moveFolderToTrash(folderId);

			return com.liferay.portal.kernel.repository.model.FolderSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	* Restores the file entry with the primary key from the trash portlet.
	*
	* @param fileEntryId the primary key of the file entry
	* @throws PortalException if the file entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static void restoreFileEntryFromTrash(long fileEntryId)
		throws RemoteException {
		try {
			DLAppServiceUtil.restoreFileEntryFromTrash(fileEntryId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	* Restores the file shortcut with the primary key from the trash portlet.
	*
	* @param fileShortcutId the primary key of the file shortcut
	* @throws PortalException if the file shortcut could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static void restoreFileShortcutFromTrash(long fileShortcutId)
		throws RemoteException {
		try {
			DLAppServiceUtil.restoreFileShortcutFromTrash(fileShortcutId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	* Restores the folder with the primary key from the trash portlet.
	*
	* @param folderId the primary key of the folder
	* @throws PortalException if the folder could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static void restoreFolderFromTrash(long folderId)
		throws RemoteException {
		try {
			DLAppServiceUtil.restoreFolderFromTrash(folderId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

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
	public static void revertFileEntry(long fileEntryId,
		java.lang.String version,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			DLAppServiceUtil.revertFileEntry(fileEntryId, version,
				serviceContext);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

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
	public static void subscribeFileEntryType(long groupId, long fileEntryTypeId)
		throws RemoteException {
		try {
			DLAppServiceUtil.subscribeFileEntryType(groupId, fileEntryTypeId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

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
	public static void subscribeFolder(long groupId, long folderId)
		throws RemoteException {
		try {
			DLAppServiceUtil.subscribeFolder(groupId, folderId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	* @deprecated As of 6.2.0, replaced by {@link #checkInFileEntry(long,
	boolean, String, ServiceContext)}.
	*/
	public static void unlockFileEntry(long fileEntryId)
		throws RemoteException {
		try {
			DLAppServiceUtil.unlockFileEntry(fileEntryId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	* @deprecated As of 6.2.0, replaced by {@link #checkInFileEntry(long,
	String)}.
	*/
	public static void unlockFileEntry(long fileEntryId,
		java.lang.String lockUuid) throws RemoteException {
		try {
			DLAppServiceUtil.unlockFileEntry(fileEntryId, lockUuid);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	* Unlocks the folder. This method is primarily used by WebDAV.
	*
	* @param repositoryId the primary key of the repository
	* @param folderId the primary key of the folder
	* @param lockUuid the lock's UUID
	* @throws PortalException if the repository or folder could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static void unlockFolder(long repositoryId, long folderId,
		java.lang.String lockUuid) throws RemoteException {
		try {
			DLAppServiceUtil.unlockFolder(repositoryId, folderId, lockUuid);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

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
	public static void unlockFolder(long repositoryId, long parentFolderId,
		java.lang.String name, java.lang.String lockUuid)
		throws RemoteException {
		try {
			DLAppServiceUtil.unlockFolder(repositoryId, parentFolderId, name,
				lockUuid);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

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
	public static void unsubscribeFileEntryType(long groupId,
		long fileEntryTypeId) throws RemoteException {
		try {
			DLAppServiceUtil.unsubscribeFileEntryType(groupId, fileEntryTypeId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

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
	public static void unsubscribeFolder(long groupId, long folderId)
		throws RemoteException {
		try {
			DLAppServiceUtil.unsubscribeFolder(groupId, folderId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

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
	public static com.liferay.portal.kernel.repository.model.FileEntrySoap updateFileEntry(
		long fileEntryId, java.lang.String sourceFileName,
		java.lang.String mimeType, java.lang.String title,
		java.lang.String description, java.lang.String changeLog,
		boolean majorVersion, byte[] bytes,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			com.liferay.portal.kernel.repository.model.FileEntry returnValue = DLAppServiceUtil.updateFileEntry(fileEntryId,
					sourceFileName, mimeType, title, description, changeLog,
					majorVersion, bytes, serviceContext);

			return com.liferay.portal.kernel.repository.model.FileEntrySoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

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
	public static com.liferay.portlet.documentlibrary.model.DLFileShortcutSoap updateFileShortcut(
		long fileShortcutId, long folderId, long toFileEntryId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			com.liferay.portlet.documentlibrary.model.DLFileShortcut returnValue =
				DLAppServiceUtil.updateFileShortcut(fileShortcutId, folderId,
					toFileEntryId, serviceContext);

			return com.liferay.portlet.documentlibrary.model.DLFileShortcutSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

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
	public static com.liferay.portal.kernel.repository.model.FolderSoap updateFolder(
		long folderId, java.lang.String name, java.lang.String description,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			com.liferay.portal.kernel.repository.model.Folder returnValue = DLAppServiceUtil.updateFolder(folderId,
					name, description, serviceContext);

			return com.liferay.portal.kernel.repository.model.FolderSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

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
	public static boolean verifyFileEntryCheckOut(long repositoryId,
		long fileEntryId, java.lang.String lockUuid) throws RemoteException {
		try {
			boolean returnValue = DLAppServiceUtil.verifyFileEntryCheckOut(repositoryId,
					fileEntryId, lockUuid);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static boolean verifyFileEntryLock(long repositoryId,
		long fileEntryId, java.lang.String lockUuid) throws RemoteException {
		try {
			boolean returnValue = DLAppServiceUtil.verifyFileEntryLock(repositoryId,
					fileEntryId, lockUuid);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

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
	public static boolean verifyInheritableLock(long repositoryId,
		long folderId, java.lang.String lockUuid) throws RemoteException {
		try {
			boolean returnValue = DLAppServiceUtil.verifyInheritableLock(repositoryId,
					folderId, lockUuid);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(DLAppServiceSoap.class);
}