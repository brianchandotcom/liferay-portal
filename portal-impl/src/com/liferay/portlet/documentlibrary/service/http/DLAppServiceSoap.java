/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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
 * <p>
 * This class provides a SOAP utility for the
 * {@link com.liferay.portlet.documentlibrary.service.DLAppServiceUtil} service utility. The
 * static methods of this class calls the same methods of the service utility.
 * However, the signatures are different because it is difficult for SOAP to
 * support certain types.
 * </p>
 *
 * <p>
 * The benefits of using the SOAP utility is that it is cross platform
 * compatible. SOAP allows different languages like Java, .NET, C++, PHP, and
 * even Perl, to call the generated services. One drawback of SOAP is that it is
 * slow because it needs to serialize all calls into a text format (XML).
 * </p>
 *
 * <p>
 * You can see a list of services at
 * http://localhost:8080/tunnel-web/secure/axis. Set the property
 * <b>tunnel.servlet.hosts.allowed</b> in portal.properties to configure
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
	* Adds a file shortcut to the existing file entry. This method is only
	* supported by the Liferay repository.
	*
	* @param repositoryId the primary key of the repository
	* @param folderId the primary key of the file shortcut's parent folder
	* @param toFileEntryId the primary key of the file shortcut's file entry
	* @param serviceContext the service context to be applied. Can specify the
	file entry's asset category IDs, asset tag names, and expando
	bridge attributes.
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
	* @see #checkOutFileEntry(long)
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
	* @see #checkOutFileEntry(long)
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
	* @param lockUuid the lock's universally unique identifier
	* @throws PortalException if the file entry could not be found
	* @throws SystemException if a system exception occurred
	* @see #cancelCheckOut(long)
	* @see #checkOutFileEntry(long, String, long)
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
	* @throws PortalException if the file entry could not be found
	* @throws SystemException if a system exception occurred
	* @see #cancelCheckOut(long)
	* @see #checkInFileEntry(long, boolean, String, ServiceContext)
	*/
	public static void checkOutFileEntry(long fileEntryId)
		throws RemoteException {
		try {
			DLAppServiceUtil.checkOutFileEntry(fileEntryId);
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
	* @see #addTempFileEntry(long, long, String, String, File)
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

	public static com.liferay.portal.kernel.search.Hits search(
		long repositoryId,
		com.liferay.portal.kernel.search.SearchContext searchContext)
		throws RemoteException {
		try {
			com.liferay.portal.kernel.search.Hits returnValue = DLAppServiceUtil.search(repositoryId,
					searchContext);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portal.kernel.search.Hits search(
		long repositoryId,
		com.liferay.portal.kernel.search.SearchContext searchContext,
		com.liferay.portal.kernel.search.Query query) throws RemoteException {
		try {
			com.liferay.portal.kernel.search.Hits returnValue = DLAppServiceUtil.search(repositoryId,
					searchContext, query);

			return returnValue;
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
	* @param lockUuid the lock's universally unique identifier
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
	* @param lockUuid the lock's universally unique identifier
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
	* Updates a file shortcut to the existing file entry. This method is only
	* supported by the Liferay repository.
	*
	* @param fileShortcutId the primary key of the file shortcut
	* @param folderId the primary key of the file shortcut's parent folder
	* @param toFileEntryId the primary key of the file shortcut's file entry
	* @param serviceContext the service context to be applied. Can specify the
	file entry's asset category IDs, asset tag names, and expando
	bridge attributes.
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
	* Returns <code>true</code> if the file entry is checked out. This method
	* is primarily used by WebDAV.
	*
	* @param repositoryId the primary key for the repository
	* @param fileEntryId the primary key for the file entry
	* @param lockUuid the lock's universally unique identifier
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

	/**
	* Returns <code>true</code> if the inheritable lock exists. This method is
	* primarily used by WebDAV.
	*
	* @param repositoryId the primary key for the repository
	* @param folderId the primary key for the folder
	* @param lockUuid the lock's universally unique identifier
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