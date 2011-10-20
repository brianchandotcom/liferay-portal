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

package com.liferay.portlet.documentlibrary.service.impl;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.Repository;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.TempFileUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.Lock;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.spring.transaction.TransactionCommitCallbackUtil;
import com.liferay.portlet.documentlibrary.NoSuchFileEntryException;
import com.liferay.portlet.documentlibrary.model.DLFileShortcut;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.service.base.DLAppServiceBaseImpl;
import com.liferay.portlet.documentlibrary.service.permission.DLFolderPermission;
import com.liferay.portlet.documentlibrary.util.DLProcessorRegistryUtil;
import com.liferay.portlet.documentlibrary.util.comparator.RepositoryModelModifiedDateComparator;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.Callable;

/**
 * The document library remote service. All portlets should interact with the
 * document library through this class or through {@link DLAppLocalServiceImpl},
 * rather than through the individual document library service classes.
 *
 * <p>
 * This class provides a unified interface to all Liferay and third party
 * repositories. While the method signatures are universal for all repositories.
 * Additional implementation-specific parameters may be specified in the
 * serviceContext.
 * </p>
 *
 * <p>
 * The <code>repositoryId</code> parameter used by most of the methods is the
 * primary key of the specific repository. If the repository is a default
 * Liferay repository, the <code>repositoryId</code> is the <code>groupId</code>
 * or <code>scopeGroupId</code>. Otherwise, the <code>repositoryId</code> will
 * correspond to values obtained from {@link RepositoryServiceUtil}.
 * </p>
 *
 * @author Alexander Chow
 * @author Mika Koivisto
 * @author Shuyang Zhou
 * @see    DLAppLocalServiceImpl
 */
public class DLAppServiceImpl extends DLAppServiceBaseImpl {

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
	 * @param  repositoryId the primary key of the repository
	 * @param  folderId the primary key of the file entry's parent folder
	 * @param  sourceFileName the original file's name
	 * @param  mimeType the file's MIME type
	 * @param  title the name to be assigned to the file (optionally <code>null
	 *         </code>)
	 * @param  description the file's description
	 * @param  changeLog the file's version change log
	 * @param  bytes the file's data (optionally <code>null</code>)
	 * @param  serviceContext the service context to be applied. Can specify the
	 *         file entry's asset category IDs, asset tag names, and expando
	 *         bridge attributes. In a Liferay repository, it may include:
	 *
	 *         <ul>
	 *         <li>
	 *         fileEntryTypeId - ID for a custom file entry type
	 *         </li>
	 *         <li>
	 *         fieldsMap - mapping for fields associated with a custom file
	 *         entry type
	 *         </li>
	 *         </ul>
	 *
	 * @return the file entry
	 * @throws PortalException if the parent folder could not be found or if the
	 *         file entry's information was invalid
	 * @throws SystemException if a system exception occurred
	 */
	public FileEntry addFileEntry(
			long repositoryId, long folderId, String sourceFileName,
			String mimeType, String title, String description, String changeLog,
			byte[] bytes, ServiceContext serviceContext)
		throws PortalException, SystemException {

		File file = null;

		try {
			if ((bytes != null) && (bytes.length > 0)) {
				file = FileUtil.createTempFile(bytes);
			}

			return addFileEntry(
				repositoryId, folderId, sourceFileName, mimeType, title,
				description, changeLog, file, serviceContext);
		}
		catch (IOException ioe) {
			throw new SystemException("Unable to write temporary file", ioe);
		}
		finally {
			FileUtil.delete(file);
		}
	}

	/**
	 * Adds a file entry and associated metadata. It is created based on a
	 * {@link File} object.
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
	 * @param  repositoryId the primary key of the repository
	 * @param  folderId the primary key of the file entry's parent folder
	 * @param  sourceFileName the original file's name
	 * @param  mimeType the file's MIME type
	 * @param  title the name to be assigned to the file (optionally <code>null
	 *         </code>)
	 * @param  description the file's description
	 * @param  changeLog the file's version change log
	 * @param  file the file's data (optionally <code>null</code>)
	 * @param  serviceContext the service context to be applied. Can specify the
	 *         file entry's asset category IDs, asset tag names, and expando
	 *         bridge attributes. In a Liferay repository, it may include:
	 *
	 *         <ul>
	 *         <li>
	 *         fileEntryTypeId - ID for a custom file entry type
	 *         </li>
	 *         <li>
	 *         fieldsMap - mapping for fields associated with a custom file
	 *         entry type
	 *         </li>
	 *         </ul>
	 *
	 * @return the file entry
	 * @throws PortalException if the parent folder could not be found or if the
	 *         file entry's information was invalid
	 * @throws SystemException if a system exception occurred
	 */
	public FileEntry addFileEntry(
			long repositoryId, long folderId, String sourceFileName,
			String mimeType, String title, String description, String changeLog,
			File file, ServiceContext serviceContext)
		throws PortalException, SystemException {

		if (file == null || !file.exists() || (file.length() == 0)) {
			return addFileEntry(
				repositoryId, folderId, sourceFileName, mimeType, title,
				description, changeLog, null, 0, serviceContext);
		}

		Repository repository = getRepository(repositoryId);

		FileEntry fileEntry = repository.addFileEntry(
			folderId, sourceFileName, mimeType, title, description, changeLog,
			file, serviceContext);

		dlAppHelperLocalService.addFileEntry(
			getUserId(), fileEntry, fileEntry.getFileVersion(), serviceContext);

		return fileEntry;
	}

	/**
	 * Adds a file entry and associated metadata. It is created based on a
	 * {@link InputStream} object.
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
	 * @param  repositoryId the primary key of the repository
	 * @param  folderId the primary key of the file entry's parent folder
	 * @param  sourceFileName the original file's name
	 * @param  mimeType the file's MIME type
	 * @param  title the name to be assigned to the file (optionally <code>null
	 *         </code>)
	 * @param  description the file's description
	 * @param  changeLog the file's version change log
	 * @param  is the file's data (optionally <code>null</code>)
	 * @param  size the file's size (optionally <code>0</code>)
	 * @param  serviceContext the service context to be applied. Can specify the
	 *         file entry's asset category IDs, asset tag names, and expando
	 *         bridge attributes. In a Liferay repository, it may include:
	 *
	 *         <ul>
	 *         <li>
	 *         fileEntryTypeId - ID for a custom file entry type
	 *         </li>
	 *         <li>
	 *         fieldsMap - mapping for fields associated with a custom file
	 *         entry type
	 *         </li>
	 *         </ul>
	 *
	 * @return the file entry
	 * @throws PortalException if the parent folder could not be found or if the
	 *         file entry's information was invalid
	 * @throws SystemException if a system exception occurred
	 */
	public FileEntry addFileEntry(
			long repositoryId, long folderId, String sourceFileName,
			String mimeType, String title, String description, String changeLog,
			InputStream is, long size, ServiceContext serviceContext)
		throws PortalException, SystemException {

		if (is == null) {
			is = new UnsyncByteArrayInputStream(new byte[0]);
			size = 0;
		}

		Repository repository = getRepository(repositoryId);

		FileEntry fileEntry = repository.addFileEntry(
			folderId, sourceFileName, mimeType, title, description, changeLog,
			is, size, serviceContext);

		dlAppHelperLocalService.addFileEntry(
			getUserId(), fileEntry, fileEntry.getFileVersion(), serviceContext);

		return fileEntry;
	}

	/**
	 * Adds a file shortcut to the existing file entry. This method is only
	 * supported by the Liferay repository.
	 *
	 * @param  repositoryId the primary key of the repository
	 * @param  folderId the primary key of the file shortcut's parent folder
	 * @param  toFileEntryId the primary key of the file shortcut's file entry
	 * @param  serviceContext the service context to be applied. Can specify the
	 *         file entry's asset category IDs, asset tag names, and expando
	 *         bridge attributes.
	 * @return the file shortcut
	 * @throws PortalException if the parent folder or file entry could not be
	 *         found, or if the file shortcut's information was invalid
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileShortcut addFileShortcut(
			long repositoryId, long folderId, long toFileEntryId,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		return dlFileShortcutService.addFileShortcut(
			repositoryId, folderId, toFileEntryId, serviceContext);
	}

	/**
	 * Adds a folder.
	 *
	 * @param  repositoryId the primary key of the repository
	 * @param  parentFolderId the primary key of the folder's parent folder
	 * @param  name the folder's name
	 * @param  description the folder's description
	 * @param  serviceContext the service context to be applied. In a Liferay
	 *         repository, it may include boolean mountPoint specifying whether
	 *         folder is a facade for mounting a third-party repository
	 * @return the folder
	 * @throws PortalException if the parent folder could not be found or if the
	 *         new folder's information was invalid
	 * @throws SystemException if a system exception occurred
	 */
	public Folder addFolder(
			long repositoryId, long parentFolderId, String name,
			String description, ServiceContext serviceContext)
		throws PortalException, SystemException {

		Repository repository = getRepository(repositoryId);

		return repository.addFolder(
			parentFolderId, name, description, serviceContext);
	}

	/**
	 * Adds a temporary file entry.
	 *
	 * <p>
	 * This allows a client to upload a file into a temporary location and
	 * manipulate its metadata prior to making it available for public usage.
	 * This is different from checking in and checking out a file entry.
	 * </p>
	 *
	 * @param  groupId the primary key of the group
	 * @param  folderId the primary key of the folder where the file entry will
	 *         eventually reside
	 * @param  fileName the file's original name
	 * @param  tempFolderName the temporary folder's name
	 * @param  file the file's data
	 * @return the file's name
	 * @throws IOException if a problem occurred in the access or storage of the
	 *         file
	 * @throws PortalException if the file name was invalid
	 * @throws SystemException if a system exception occurred
	 * @see    com.liferay.portal.kernel.util.TempFileUtil
	 */
	public String addTempFileEntry(
			long groupId, long folderId, String fileName, String tempFolderName,
			File file)
		throws IOException, PortalException, SystemException {

		DLFolderPermission.check(
			getPermissionChecker(), groupId, folderId, ActionKeys.ADD_DOCUMENT);

		return TempFileUtil.addTempFile(
			getUserId(), fileName, tempFolderName, file);
	}

	public String addTempFileEntry(
			long groupId, long folderId, String fileName, String tempFolderName,
			InputStream inputStream)
		throws IOException, PortalException, SystemException {

		DLFolderPermission.check(
			getPermissionChecker(), groupId, folderId, ActionKeys.ADD_DOCUMENT);

		return TempFileUtil.addTempFile(
			getUserId(), fileName, tempFolderName, inputStream);
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
	 * @param  fileEntryId the primary key of the file entry to cancel the
	 *         checkout
	 * @throws PortalException if the file entry could not be found
	 * @throws SystemException if a system exception occurred
	 * @see    #checkInFileEntry(long, boolean, String, ServiceContext)
	 * @see    #checkOutFileEntry(long)
	 */
	public void cancelCheckOut(long fileEntryId)
		throws PortalException, SystemException {

		Repository repository = getRepository(0, fileEntryId, 0);

		repository.cancelCheckOut(fileEntryId);
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
	 * @param  fileEntryId the primary key of the file entry to check in
	 * @param  majorVersion whether the new file version is a major version
	 * @param  changeLog the file's version change log
	 * @param  serviceContext the service context to be applied
	 * @throws PortalException if the file entry could not be found
	 * @throws SystemException if a system exception occurred
	 * @see    #cancelCheckOut(long)
	 * @see    #checkOutFileEntry(long)
	 */
	public void checkInFileEntry(
			long fileEntryId, boolean majorVersion, String changeLog,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		Repository repository = getRepository(0, fileEntryId, 0);

		repository.checkInFileEntry(
			fileEntryId, majorVersion, changeLog, serviceContext);

		FileEntry fileEntry = getFileEntry(fileEntryId);

		dlAppHelperLocalService.updateFileEntry(
			getUserId(), fileEntry, fileEntry.getFileVersion(), serviceContext);
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
	 * @param  fileEntryId the primary key of the file entry to check in
	 * @param  lockUuid the lock's universally unique identifier
	 * @throws PortalException if the file entry could not be found
	 * @throws SystemException if a system exception occurred
	 * @see    #cancelCheckOut(long)
	 * @see    #checkOutFileEntry(long, String, long)
	 */
	public void checkInFileEntry(long fileEntryId, String lockUuid)
		throws PortalException, SystemException {

		Repository repository = getRepository(0, fileEntryId, 0);

		repository.checkInFileEntry(fileEntryId, lockUuid);

		FileEntry fileEntry = getFileEntry(fileEntryId);

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setWorkflowAction(WorkflowConstants.ACTION_PUBLISH);

		dlAppHelperLocalService.updateFileEntry(
			getUserId(), fileEntry, fileEntry.getFileVersion(), serviceContext);
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
	 * @param  fileEntryId the file entry to check out
	 * @throws PortalException if the file entry could not be found
	 * @throws SystemException if a system exception occurred
	 * @see    #cancelCheckOut(long)
	 * @see    #checkInFileEntry(long, boolean, String, ServiceContext)
	 */
	public void checkOutFileEntry(long fileEntryId)
		throws PortalException, SystemException {

		Repository repository = getRepository(0, fileEntryId, 0);

		FileEntry fileEntry = repository.checkOutFileEntry(fileEntryId);

		dlAppHelperLocalService.updateAsset(
			getUserId(), fileEntry, fileEntry.getLatestFileVersion(),
			fileEntryId);
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
	 * @param  fileEntryId the file entry to check out
	 * @param  owner the owner string for the checkout (optionally
	 *         <code>null</code>)
	 * @param  expirationTime the time in milliseconds before the lock expires.
	 *         If the value is <code>0</code>, the default expiration time will
	 *         be used from <code>portal.properties>.
	 * @return the file entry
	 * @throws PortalException if the file entry could not be found
	 * @throws SystemException if a system exception occurred
	 * @see    #cancelCheckOut(long)
	 * @see    #checkInFileEntry(long, String)
	 */
	public FileEntry checkOutFileEntry(
			long fileEntryId, String owner, long expirationTime)
		throws PortalException, SystemException {

		Repository repository = getRepository(0, fileEntryId, 0);

		FileEntry fileEntry = repository.checkOutFileEntry(
			fileEntryId, owner, expirationTime);

		dlAppHelperLocalService.updateAsset(
			getUserId(), fileEntry, fileEntry.getLatestFileVersion(),
			fileEntryId);

		return fileEntry;
	}

	/**
	 * Performs a deep copy of the folder.
	 *
	 * @param  repositoryId the primary key of the repository
	 * @param  sourceFolderId the primary key of the folder to copy
	 * @param  parentFolderId the primary key of the new folder's parent folder
	 * @param  name the new folder's name
	 * @param  description the new folder's description
	 * @param  serviceContext the service context to be applied
	 * @return the folder
	 * @throws PortalException if the source folder or the new parent folder
	 *         could not be found or if the new folder's information was invalid
	 * @throws SystemException if a system exception occurred
	 */
	public Folder copyFolder(
			long repositoryId, long sourceFolderId, long parentFolderId,
			String name, String description, ServiceContext serviceContext)
		throws PortalException, SystemException {

		Repository repository = getRepository(repositoryId);

		Folder srcFolder = repository.getFolder(sourceFolderId);

		Folder destFolder = repository.addFolder(
			parentFolderId, name,description, serviceContext);

		copyFolder(repository, srcFolder, destFolder, serviceContext);

		return destFolder;
	}

	/**
	 * Deletes the file entry with the primary key.
	 *
	 * @param  fileEntryId the primary key of the file entry
	 * @throws PortalException if the file entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public void deleteFileEntry(long fileEntryId)
		throws PortalException, SystemException {

		Repository repository = getRepository(0, fileEntryId, 0);

		FileEntry fileEntry = repository.getFileEntry(fileEntryId);

		dlAppHelperLocalService.deleteFileEntry(fileEntry);

		repository.deleteFileEntry(fileEntryId);
	}

	/**
	 * Deletes the file entry with the title in the folder.
	 *
	 * @param  repositoryId the primary key of the repository
	 * @param  folderId the primary key of the file entry's parent folder
	 * @param  title the file entry's title
	 * @throws PortalException if the file entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public void deleteFileEntryByTitle(
			long repositoryId, long folderId, String title)
		throws PortalException, SystemException {

		Repository repository = getRepository(repositoryId);

		FileEntry fileEntry = repository.getFileEntry(folderId, title);

		dlAppHelperLocalService.deleteFileEntry(fileEntry);

		repository.deleteFileEntry(folderId, title);
	}

	/**
	 * Deletes the file shortcut with the primary key. This method is only
	 * supported by the Liferay repository.
	 *
	 * @param  fileShortcutId the primary key of the file shortcut
	 * @throws PortalException if the file shortcut could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public void deleteFileShortcut(long fileShortcutId)
		throws PortalException, SystemException {

		dlFileShortcutService.deleteFileShortcut(fileShortcutId);
	}

	/**
	 * Deletes the folder with the primary key and all of its subfolders and
	 * file entries.
	 *
	 * @param  folderId the primary key of the folder
	 * @throws PortalException if the folder could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public void deleteFolder(long folderId)
		throws PortalException, SystemException {

		Repository repository = getRepository(folderId, 0, 0);

		repository.deleteFolder(folderId);
	}

	/**
	 * Deletes the folder with the name in the parent folder and all of its
	 * subfolders and file entries.
	 *
	 * @param  repositoryId the primary key of the repository
	 * @param  parentFolderId the primary key of the folder's parent folder
	 * @param  name the folder's name
	 * @throws PortalException if the folder could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public void deleteFolder(
			long repositoryId, long parentFolderId, String name)
		throws PortalException, SystemException {

		Repository repository = getRepository(repositoryId);

		repository.deleteFolder(parentFolderId, name);
	}

	/**
	 * Deletes the temporary file entry.
	 *
	 * @param  groupId the primary key of the group
	 * @param  folderId the primary key of the folder where the file entry was
	 *         eventually to reside
	 * @param  fileName the file's original name
	 * @param  tempFolderName the temporary folder's name
	 * @throws PortalException if the file name was invalid
	 * @throws SystemException if a system exception occurred
	 * @see    com.liferay.portal.kernel.util.TempFileUtil
	 */
	public void deleteTempFileEntry(
			long groupId, long folderId, String fileName, String tempFolderName)
		throws PortalException, SystemException {

		DLFolderPermission.check(
			getPermissionChecker(), groupId, folderId, ActionKeys.ADD_DOCUMENT);

		TempFileUtil.deleteTempFile(getUserId(), fileName, tempFolderName);
	}

	/**
	 * Returns all the file entries in the folder.
	 *
	 * @param  repositoryId the primary key of the file entry's repository
	 * @param  folderId the primary key of the file entry's folder
	 * @return the file entries in the folder
	 * @throws PortalException if the folder could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public List<FileEntry> getFileEntries(long repositoryId, long folderId)
		throws PortalException, SystemException {

		return getFileEntries(
			repositoryId, folderId, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
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
	 * @param  repositoryId the primary key of the file entry's repository
	 * @param  folderId the primary key of the file entry's folder
	 * @param  start the lower bound of the range of results
	 * @param  end the upper bound of the range of results (not inclusive)
	 * @return the range of file entries in the folder
	 * @throws PortalException if the folder could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public List<FileEntry> getFileEntries(
			long repositoryId, long folderId, int start, int end)
		throws PortalException, SystemException {

		return getFileEntries(repositoryId, folderId, start, end, null);
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
	 * @param  repositoryId the primary key of the file entry's repository
	 * @param  folderId the primary key of the file entry's folder
	 * @param  start the lower bound of the range of results
	 * @param  end the upper bound of the range of results (not inclusive)
	 * @param  obc the comparator to order the file entries (optionally
	 *         <code>null</code>)
	 * @return the range of file entries in the folder ordered by comparator
	 *         <code>obc</code>
	 * @throws PortalException if the folder could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public List<FileEntry> getFileEntries(
			long repositoryId, long folderId, int start, int end,
			OrderByComparator obc)
		throws PortalException, SystemException {

		Repository repository = getRepository(repositoryId);

		return repository.getFileEntries(folderId, start, end, obc);
	}

	/**
	 * Returns the file entries with the file entry type in the folder.
	 *
	 * @param  repositoryId the primary key of the file entry's repository
	 * @param  folderId the primary key of the file entry's folder
	 * @param  fileEntryTypeId the primary key of the file entry type
	 * @return the file entries with the file entry type in the folder
	 * @throws PortalException if the folder could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public List<FileEntry> getFileEntries(
			long repositoryId, long folderId, long fileEntryTypeId)
		throws PortalException, SystemException {

		return getFileEntries(
			repositoryId, folderId, fileEntryTypeId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS);
	}

	/**
	 * Returns a range of all the file entries with the file entry type in the
	 * folder.
	 *
	 * @param  repositoryId the primary key of the file entry's repository
	 * @param  folderId the primary key of the file entry's folder
	 * @param  fileEntryTypeId the primary key of the file entry type
	 * @param  start the lower bound of the range of results
	 * @param  end the upper bound of the range of results (not inclusive)
	 * @return the file entries in the folder
	 * @throws PortalException if the folder could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public List<FileEntry> getFileEntries(
			long repositoryId, long folderId, long fileEntryTypeId, int start,
			int end)
		throws PortalException, SystemException {

		return getFileEntries(
			repositoryId, folderId, fileEntryTypeId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the file entries with the file entry type
	 * in the folder.
	 *
	 * @param  repositoryId the primary key of the repository
	 * @param  folderId the primary key of the folder
	 * @param  fileEntryTypeId the primary key of the file entry type
	 * @param  start the lower bound of the range of results
	 * @param  end the upper bound of the range of results (not inclusive)
	 * @param  obc the comparator to order the results by (optionally
	 *         <code>null</code>)
	 * @return the range of file entries with the file entry type in the folder
	 *         ordered by <code>null</code>
	 * @throws PortalException if the folder could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public List<FileEntry> getFileEntries(
			long repositoryId, long folderId, long fileEntryTypeId, int start,
			int end, OrderByComparator obc)
		throws PortalException, SystemException {

		Repository repository = getRepository(repositoryId);

		return repository.getFileEntries(
			folderId, fileEntryTypeId, start, end, obc);
	}

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
	 * @param  repositoryId the primary key of the repository
	 * @param  folderId the primary key of the folder
	 * @param  status the workflow status
	 * @param  start the lower bound of the range of results
	 * @param  end the upper bound of the range of results (not inclusive)
	 * @return the range of file entries and shortcuts in the folder
	 * @throws PortalException if the folder could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public List<Object> getFileEntriesAndFileShortcuts(
			long repositoryId, long folderId, int status, int start, int end)
		throws PortalException, SystemException {

		Repository repository = getRepository(repositoryId);

		return repository.getFileEntriesAndFileShortcuts(
			folderId, status, start, end);
	}

	/**
	 * Returns the number of file entries and shortcuts in the folder.
	 *
	 * @param  repositoryId the primary key of the repository
	 * @param  folderId the primary key of the folder
	 * @param  status the workflow status
	 * @return the number of file entries and shortcuts in the folder
	 * @throws PortalException if the folder ould not be found
	 * @throws SystemException if a system exception occurred
	 */
	public int getFileEntriesAndFileShortcutsCount(
			long repositoryId, long folderId, int status)
		throws PortalException, SystemException {

		Repository repository = getRepository(repositoryId);

		return repository.getFileEntriesAndFileShortcutsCount(
			folderId, status);
	}

	/**
	 * Returns the number of file entries in the folder.
	 *
	 * @param  repositoryId the primary key of the file entry's repository
	 * @param  folderId the primary key of the file entry's folder
	 * @return the number of file entries in the folder
	 * @throws PortalException if the folder could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public int getFileEntriesCount(long repositoryId, long folderId)
		throws PortalException, SystemException {

		Repository repository = getRepository(repositoryId);

		return repository.getFileEntriesCount(folderId);
	}

	/**
	 * Returns the number of file entries with the file entry type in the
	 * folder.
	 *
	 * @param  repositoryId the primary key of the file entry's repository
	 * @param  folderId the primary key of the file entry's folder
	 * @param  fileEntryTypeId the primary key of the file entry type
	 * @return the number of file entries with the file entry type in the folder
	 * @throws PortalException if the folder could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public int getFileEntriesCount(
			long repositoryId, long folderId, long fileEntryTypeId)
		throws PortalException, SystemException {

		Repository repository = getRepository(repositoryId);

		return repository.getFileEntriesCount(folderId, fileEntryTypeId);
	}

	/**
	 * Returns the file entry with the primary key.
	 *
	 * @param  fileEntryId the primary key of the file entry
	 * @return the file entry with the primary key
	 * @throws PortalException if the file entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public FileEntry getFileEntry(long fileEntryId)
		throws PortalException, SystemException {

		Repository repository = getRepository(0, fileEntryId, 0);

		return repository.getFileEntry(fileEntryId);
	}

	/**
	 * Returns the file entry with the title in the folder.
	 *
	 * @param  groupId the primary key of the file entry's group
	 * @param  folderId the primary key of the file entry's folder
	 * @param  title the file entry's title
	 * @return the file entry with the title in the folder
	 * @throws PortalException if the file entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public FileEntry getFileEntry(long groupId, long folderId, String title)
		throws PortalException, SystemException {

		try {
			Repository repository = getRepository(groupId);

			return repository.getFileEntry(folderId, title);
		}
		catch (NoSuchFileEntryException nsfee) {
			if (folderId != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
				Repository repository = getRepository(folderId, 0, 0);

				return repository.getFileEntry(folderId, title);
			}
			else {
				throw nsfee;
			}
		}
	}

	/**
	 * Returns the file entry with the UUID and group.
	 *
	 * @param  uuid the file entry's universally unique identifier
	 * @param  groupId the primary key of the file entry's group
	 * @return the file entry with the UUID and group
	 * @throws PortalException if the file entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public FileEntry getFileEntryByUuidAndGroupId(String uuid, long groupId)
		throws PortalException, SystemException {

		try {
			Repository repository = getRepository(groupId);

			return repository.getFileEntryByUuid(uuid);
		}
		catch (NoSuchFileEntryException nsfee) {
			List<com.liferay.portal.model.Repository> repositories =
				repositoryPersistence.findByGroupId(groupId);

			for (int i = 0; i < repositories.size(); i++) {
				try {
					long repositoryId = repositories.get(i).getRepositoryId();

					Repository repository = getRepository(repositoryId);

					return repository.getFileEntryByUuid(uuid);
				}
				catch (NoSuchFileEntryException nsfee2) {
				}
			}
		}

		StringBundler msg = new StringBundler(6);

		msg.append("No DLFileEntry exists with the key {");
		msg.append("uuid=");
		msg.append(uuid);
		msg.append(", groupId=");
		msg.append(groupId);
		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFileEntryException(msg.toString());
	}

	/**
	 * Returns the file shortcut with the primary key. This method is only
	 * supported by the Liferay repository.
	 *
	 * @param  fileShortcutId the primary key of the file shortcut
	 * @return the file shortcut with the primary key
	 * @throws PortalException if the file shortcut could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileShortcut getFileShortcut(long fileShortcutId)
		throws PortalException, SystemException {

		return dlFileShortcutService.getFileShortcut(fileShortcutId);
	}

	/**
	 * Returns the folder with the primary key.
	 *
	 * @param  folderId the primary key of the folder
	 * @return the folder with the primary key
	 * @throws PortalException if the folder could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Folder getFolder(long folderId)
		throws PortalException, SystemException {

		Repository repository = getRepository(folderId, 0, 0);

		return repository.getFolder(folderId);
	}

	/**
	 * Returns the folder with the name in the parent folder.
	 *
	 * @param  repositoryId the primary key of the folder's repository
	 * @param  parentFolderId the primary key of the folder's parent folder
	 * @param  name the folder's name
	 * @return the folder with the name in the parent folder
	 * @throws PortalException if the folder could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Folder getFolder(long repositoryId, long parentFolderId, String name)
		throws PortalException, SystemException {

		Repository repository = getRepository(repositoryId);

		return repository.getFolder(parentFolderId, name);
	}

	/**
	 * Returns all immediate subfolders of the parent folder.
	 *
	 * @param  repositoryId the primary key of the folder's repository
	 * @param  parentFolderId the primary key of the folder's parent folder
	 * @return the immediate subfolders of the parent folder
	 * @throws PortalException if the parent folder could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public List<Folder> getFolders(long repositoryId, long parentFolderId)
		throws PortalException, SystemException {

		return getFolders(
			repositoryId, parentFolderId, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	/**
	 * Returns all immediate subfolders of the parent folder, optionally
	 * including mount folders for third-party repositories.
	 *
	 * @param  repositoryId the primary key of the folder's repository
	 * @param  parentFolderId the primary key of the folder's parent folder
	 * @param  includeMountFolders whether to include mount folders for
	 *         third-party repositories
	 * @return the immediate subfolders of the parent folder
	 * @throws PortalException if the parent folder could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public List<Folder> getFolders(
			long repositoryId, long parentFolderId, boolean includeMountFolders)
		throws PortalException, SystemException {

		return getFolders(
			repositoryId, parentFolderId, includeMountFolders,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS);
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
	 * @param  repositoryId the primary key of the folder's repository
	 * @param  parentFolderId the primary key of the folder's parent folder
	 * @param  includeMountFolders whether to include mount folders for
	 *         third-party repositories
	 * @param  start the lower bound of the range of results
	 * @param  end the upper bound of the range of results (not inclusive)
	 * @return the range of immediate subfolders of the parent folder
	 * @throws PortalException if the parent folder could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public List<Folder> getFolders(
			long repositoryId, long parentFolderId, boolean includeMountFolders,
			int start, int end)
		throws PortalException, SystemException {

		return getFolders(
			repositoryId, parentFolderId, includeMountFolders, start, end,
			null);
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
	 * @param  repositoryId the primary key of the folder's repository
	 * @param  parentFolderId the primary key of the folder's parent folder
	 * @param  includeMountFolders whether to include mount folders for
	 *         third-party repositories
	 * @param  start the lower bound of the range of results
	 * @param  end the upper bound of the range of results (not inclusive)
	 * @param  obc the comparator to order the folders (optionally
	 *         <code>null</code>)
	 * @return the range of immediate subfolders of the parent folder ordered by
	 *         comparator <code>obc</code>
	 * @throws PortalException if the parent folder could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public List<Folder> getFolders(
			long repositoryId, long parentFolderId, boolean includeMountFolders,
			int start, int end, OrderByComparator obc)
		throws PortalException, SystemException {

		Repository repository = getRepository(repositoryId);

		return repository.getFolders(
			parentFolderId, includeMountFolders, start, end, obc);
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
	 * @param  repositoryId the primary key of the folder's repository
	 * @param  parentFolderId the primary key of the folder's parent folder
	 * @param  start the lower bound of the range of results
	 * @param  end the upper bound of the range of results (not inclusive)
	 * @return the range of immediate subfolders of the parent folder
	 * @throws PortalException if the parent folder could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public List<Folder> getFolders(
			long repositoryId, long parentFolderId, int start, int end)
		throws PortalException, SystemException {

		return getFolders(repositoryId, parentFolderId, start, end, null);
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
	 * @param  repositoryId the primary key of the folder's repository
	 * @param  parentFolderId the primary key of the folder's parent folder
	 * @param  start the lower bound of the range of results
	 * @param  end the upper bound of the range of results (not inclusive)
	 * @param  obc the comparator to order the folders (optionally
	 *         <code>null</code>)
	 * @return the range of immediate subfolders of the parent folder ordered by
	 *         comparator <code>obc</code>
	 * @throws PortalException if the parent folder could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public List<Folder> getFolders(
			long repositoryId, long parentFolderId, int start, int end,
			OrderByComparator obc)
		throws PortalException, SystemException {

		Repository repository = getRepository(repositoryId);

		return repository.getFolders(parentFolderId, true, start, end, obc);
	}

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
	 * @param  repositoryId the primary key of the repository
	 * @param  folderId the primary key of the parent folder
	 * @param  status the workflow status
	 * @param  includeMountFolders whether to include mount folders for
	 *         third-party repositories
	 * @param  start the lower bound of the range of results
	 * @param  end the upper bound of the range of results (not inclusive)
	 * @return the range of immediate subfolders, file entries, and file
	 *         shortcuts in the parent folder ordered by comparator
	 *         <code>obc</code>
	 * @throws PortalException if the parent folder could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public List<Object> getFoldersAndFileEntriesAndFileShortcuts(
			long repositoryId, long folderId, int status,
			boolean includeMountFolders, int start, int end)
		throws PortalException, SystemException {

		return getFoldersAndFileEntriesAndFileShortcuts(
			repositoryId, folderId, status, includeMountFolders, start, end,
			null);
	}

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
	 * @param  repositoryId the primary key of the repository
	 * @param  folderId the primary key of the parent folder
	 * @param  status the workflow status
	 * @param  includeMountFolders whether to include mount folders for
	 *         third-party repositories
	 * @param  start the lower bound of the range of results
	 * @param  end the upper bound of the range of results (not inclusive)
	 * @param  obc the comparator to order the results (optionally
	 *         <code>null</code>)
	 * @return the range of immediate subfolders, file entries, and file
	 *         shortcuts in the parent folder ordered by comparator
	 *         <code>obc</code>
	 * @throws PortalException if the parent folder could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public List<Object> getFoldersAndFileEntriesAndFileShortcuts(
			long repositoryId, long folderId, int status,
			boolean includeMountFolders, int start, int end,
			OrderByComparator obc)
		throws PortalException, SystemException {

		return getFoldersAndFileEntriesAndFileShortcuts(
			repositoryId, folderId, status, null, includeMountFolders, start,
			end, obc);
	}

	public List<Object> getFoldersAndFileEntriesAndFileShortcuts(
			long repositoryId, long folderId, int status, String[] mimeTypes,
			boolean includeMountFolders, int start, int end,
			OrderByComparator obc)
		throws PortalException, SystemException {

		Repository repository = getRepository(repositoryId);

		return repository.getFoldersAndFileEntriesAndFileShortcuts(
			folderId, status, mimeTypes, includeMountFolders, start, end, obc);
	}

	/**
	 * Returns the number of immediate subfolders, file entries, and file
	 * shortcuts in the parent folder.
	 *
	 * @param  repositoryId the primary key of the repository
	 * @param  folderId the primary key of the parent folder
	 * @param  status the workflow status
	 * @param  includeMountFolders whether to include mount folders for
	 *         third-party repositories
	 * @return the number of immediate subfolders, file entries, and file
	 *         shortcuts in the parent folder
	 * @throws PortalException if the folder could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public int getFoldersAndFileEntriesAndFileShortcutsCount(
			long repositoryId, long folderId, int status,
			boolean includeMountFolders)
		throws PortalException, SystemException {

		return getFoldersAndFileEntriesAndFileShortcutsCount(
			repositoryId, folderId, status, null, includeMountFolders);
	}

	public int getFoldersAndFileEntriesAndFileShortcutsCount(
			long repositoryId, long folderId, int status, String[] mimeTypes,
			boolean includeMountFolders)
		throws PortalException, SystemException {

		Repository repository = getRepository(repositoryId);

		return repository.getFoldersAndFileEntriesAndFileShortcutsCount(
			folderId, status, mimeTypes, includeMountFolders);
	}

	/**
	 * Returns the number of immediate subfolders of the parent folder.
	 *
	 * @param  repositoryId the primary key of the folder's repository
	 * @param  parentFolderId the primary key of the folder's parent folder
	 * @return the number of immediate subfolders of the parent folder
	 * @throws PortalException if the parent folder could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public int getFoldersCount(long repositoryId, long parentFolderId)
		throws PortalException, SystemException {

		return getFoldersCount(repositoryId, parentFolderId, true);
	}

	/**
	 * Returns the number of immediate subfolders of the parent folder,
	 * optionally including mount folders for third-party repositories.
	 *
	 * @param  repositoryId the primary key of the folder's repository
	 * @param  parentFolderId the primary key of the folder's parent folder
	 * @param  includeMountFolders whether to include mount folders for
	 *         third-party repositories
	 * @return the number of immediate subfolders of the parent folder
	 * @throws PortalException if the parent folder could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public int getFoldersCount(
			long repositoryId, long parentFolderId, boolean includeMountFolders)
		throws PortalException, SystemException {

		Repository repository = getRepository(repositoryId);

		return repository.getFoldersCount(parentFolderId, includeMountFolders);
	}

	/**
	 * Returns the number of immediate subfolders and file entries across the
	 * folders.
	 *
	 * @param  repositoryId the primary key of the repository
	 * @param  folderIds the primary keys of folders from which to count
	 *         immediate subfolders and file entries
	 * @param  status the workflow status
	 * @return the number of immediate subfolders and file entries across the
	 *         folders
	 * @throws PortalException if the repository could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public int getFoldersFileEntriesCount(
			long repositoryId, List<Long> folderIds, int status)
		throws PortalException, SystemException {

		Repository repository = getRepository(repositoryId);

		return repository.getFoldersFileEntriesCount(folderIds, status);
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
	 * @param  groupId the primary key of the group
	 * @param  userId the primary key of the user who created the file
	 *         (optionally <code>0</code>)
	 * @param  start the lower bound of the range of results
	 * @param  end the upper bound of the range of results (not inclusive)
	 * @return the range of matching file entries ordered by date modified
	 * @throws PortalException if the group could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public List<FileEntry> getGroupFileEntries(
			long groupId, long userId, int start, int end)
		throws PortalException, SystemException {

		return getGroupFileEntries(
			groupId, userId, DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			start, end, new RepositoryModelModifiedDateComparator());
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
	 * @param  groupId the primary key of the group
	 * @param  userId the primary key of the user who created the file
	 *         (optionally <code>0</code>)
	 * @param  start the lower bound of the range of results
	 * @param  end the upper bound of the range of results (not inclusive)
	 * @param  obc the comparator to order the file entries (optionally
	 *         <code>null</code>)
	 * @return the range of matching file entries ordered by comparator
	 *         <code>obc</code>
	 * @throws PortalException if the group could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public List<FileEntry> getGroupFileEntries(
			long groupId, long userId, int start, int end,
			OrderByComparator obc)
		throws PortalException, SystemException {

		return getGroupFileEntries(
			groupId, userId, DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			start, end, obc);
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
	 * @param  groupId the primary key of the group
	 * @param  userId the primary key of the user who created the file
	 *         (optionally <code>0</code>)
	 * @param  rootFolderId the primary key of the root folder to begin the
	 *         search
	 * @param  start the lower bound of the range of results
	 * @param  end the upper bound of the range of results (not inclusive)
	 * @return the range of matching file entries ordered by date modified
	 * @throws PortalException if the group could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public List<FileEntry> getGroupFileEntries(
			long groupId, long userId, long rootFolderId, int start, int end)
		throws PortalException, SystemException {

		return getGroupFileEntries(
			groupId, userId, rootFolderId, start, end,
			new RepositoryModelModifiedDateComparator());
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
	 * @param  groupId the primary key of the group
	 * @param  userId the primary key of the user who created the file
	 *         (optionally <code>0</code>)
	 * @param  rootFolderId the primary key of the root folder to begin the
	 *         search
	 * @param  start the lower bound of the range of results
	 * @param  end the upper bound of the range of results (not inclusive)
	 * @param  obc the comparator to order the file entries (optionally
	 *         <code>null</code>)
	 * @return the range of matching file entries ordered by comparator
	 *         <code>obc</code>
	 * @throws PortalException if the group could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public List<FileEntry> getGroupFileEntries(
			long groupId, long userId, long rootFolderId, int start, int end,
			OrderByComparator obc)
		throws PortalException, SystemException {

		Repository repository = getRepository(groupId);

		return repository.getRepositoryFileEntries(
			userId, rootFolderId, start, end, obc);
	}

	/**
	 * Returns the number of file entries in a group starting at the repository
	 * default parent folder that are stored within the Liferay repository. This
	 * method is primarily used to search for recently modified file entries. It
	 * can be limited to the file entries modified by a given user.
	 *
	 * @param  groupId the primary key of the group
	 * @param  userId the primary key of the user who created the file
	 *         (optionally <code>0</code>)
	 * @return the number of matching file entries
	 * @throws PortalException if the group could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public int getGroupFileEntriesCount(long groupId, long userId)
		throws PortalException, SystemException {

		return getGroupFileEntriesCount(
			groupId, userId, DLFolderConstants.DEFAULT_PARENT_FOLDER_ID);
	}

	/**
	 * Returns the number of file entries in a group starting at the root folder
	 * that are stored within the Liferay repository. This method is primarily
	 * used to search for recently modified file entries. It can be limited to
	 * the file entries modified by a given user.
	 *
	 * @param  groupId the primary key of the group
	 * @param  userId the primary key of the user who created the file
	 *         (optionally <code>0</code>)
	 * @param  rootFolderId the primary key of the root folder to begin the
	 *         search
	 * @return the number of matching file entries
	 * @throws PortalException if the group could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public int getGroupFileEntriesCount(
			long groupId, long userId, long rootFolderId)
		throws PortalException, SystemException {

		Repository repository = getRepository(groupId);

		return repository.getRepositoryFileEntriesCount(userId, rootFolderId);
	}

	/**
	 * Returns all immediate subfolders of the parent folder that are used for
	 * mounting third-party repositories. This method is only supported by the
	 * Liferay repository.
	 *
	 * @param  repositoryId the primary key of the folder's repository
	 * @param  parentFolderId the primary key of the folder's parent folder
	 * @return the immediate subfolders of the parent folder that are used for
	 *         mounting third-party repositories
	 * @throws PortalException if the repository or parent folder could not be
	 *         found
	 * @throws SystemException if a system exception occurred
	 */
	public List<Folder> getMountFolders(long repositoryId, long parentFolderId)
		throws PortalException, SystemException {

		return getMountFolders(
			repositoryId, parentFolderId, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
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
	 * @param  repositoryId the primary key of the repository
	 * @param  parentFolderId the primary key of the parent folder
	 * @param  start the lower bound of the range of results
	 * @param  end the upper bound of the range of results (not inclusive)
	 * @return the range of immediate subfolders of the parent folder that are
	 *         used for mounting third-party repositories
	 * @throws PortalException if the repository or parent folder could not be
	 *         found
	 * @throws SystemException if a system exception occurred
	 */
	public List<Folder> getMountFolders(
			long repositoryId, long parentFolderId, int start, int end)
		throws PortalException, SystemException {

		return getMountFolders(repositoryId, parentFolderId, start, end, null);
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
	 * @param  repositoryId the primary key of the folder's repository
	 * @param  parentFolderId the primary key of the folder's parent folder
	 * @param  start the lower bound of the range of results
	 * @param  end the upper bound of the range of results (not inclusive)
	 * @param  obc the comparator to order the folders (optionally
	 *         <code>null</code>)
	 * @return the range of immediate subfolders of the parent folder that are
	 *         used for mounting third-party repositories ordered by comparator
	 *         <code>obc</code>
	 * @throws PortalException if the repository or parent folder could not be
	 *         found
	 * @throws SystemException if a system exception occurred
	 */
	public List<Folder> getMountFolders(
			long repositoryId, long parentFolderId, int start, int end,
			OrderByComparator obc)
		throws PortalException, SystemException {

		Repository repository = getRepository(repositoryId);

		return repository.getMountFolders(parentFolderId, start, end, obc);
	}

	public void getSubfolderIds(
			long repositoryId, List<Long> folderIds, long folderId)
		throws PortalException, SystemException {

		Repository repository = getRepository(repositoryId);

		repository.getSubfolderIds(folderIds, folderId);
	}

	/**
	 * Returns all the descendant folders of the folder with the primary key.
	 *
	 * @param  repositoryId the primary key of the repository
	 * @param  folderId the primary key of the folder
	 * @return the descendant folders of the folder with the primary key
	 * @throws PortalException if the repository or parent folder could not be
	 *         found
	 * @throws SystemException if a system exception occurred
	 */
	public List<Long> getSubfolderIds(long repositoryId, long folderId)
		throws PortalException, SystemException {

		return getSubfolderIds(repositoryId, folderId, true);
	}

	/**
	 * Returns descendant folders of the folder with the primary key, optionally
	 * limiting to one level deep.
	 *
	 * @param  repositoryId the primary key of the repository
	 * @param  folderId the primary key of the folder
	 * @param  recurse whether to recurse through each subfolder
	 * @return the descendant folders of the folder with the primary key
	 * @throws PortalException if the repository or parent folder could not be
	 *         found
	 * @throws SystemException if a system exception occurred
	 */
	public List<Long> getSubfolderIds(
			long repositoryId, long folderId, boolean recurse)
		throws PortalException, SystemException {

		Repository repository = getRepository(repositoryId);

		return repository.getSubfolderIds(folderId, recurse);
	}

	/**
	 * Returns all the temporary file entry names.
	 *
	 * @param  groupId the primary key of the group
	 * @param  folderId the primary key of the folder where the file entry will
	 *         eventually reside
	 * @param  tempFolderName the temporary folder's name
	 * @return the temporary file entry names
	 * @throws PortalException if the folder was invalid
	 * @throws SystemException if a system exception occurred
	 * @see    #addTempFileEntry(long, long, String, String, File)
	 * @see    com.liferay.portal.kernel.util.TempFileUtil
	 */
	public String[] getTempFileEntryNames(
			long groupId, long folderId, String tempFolderName)
		throws PortalException, SystemException {

		DLFolderPermission.check(
			getPermissionChecker(), groupId, folderId, ActionKeys.ADD_DOCUMENT);

		return TempFileUtil.getTempFileEntryNames(getUserId(), tempFolderName);
	}

	/**
	 * Locks the folder. This method is primarily used by WebDAV.
	 *
	 * @param  repositoryId the primary key of the repository
	 * @param  folderId the primary key of the folder
	 * @return the lock object
	 * @throws PortalException if the repository or folder could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Lock lockFolder(long repositoryId, long folderId)
		throws PortalException, SystemException {

		Repository repository = getRepository(repositoryId);

		return repository.lockFolder(folderId);
	}

	/**
	 * Locks the folder. This method is primarily used by WebDAV.
	 *
	 * @param  repositoryId the primary key of the repository
	 * @param  folderId the primary key of the folder
	 * @param  owner the owner string for the checkout (optionally
	 *         <code>null</code>)
	 * @param  inheritable whether the lock must propagate to descendants
	 * @param  expirationTime the time in milliseconds before the lock expires.
	 *         If the value is <code>0</code>, the default expiration time will
	 *         be used from <code>portal.properties>.
	 * @return the lock object
	 * @throws PortalException if the repository or folder could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Lock lockFolder(
			long repositoryId, long folderId, String owner, boolean inheritable,
			long expirationTime)
		throws PortalException, SystemException {

		Repository repository = getRepository(repositoryId);

		return repository.lockFolder(
			folderId, owner, inheritable, expirationTime);
	}

	/**
	 * Moves the file entry to the new folder.
	 *
	 * @param  fileEntryId the primary key of the file entry
	 * @param  newFolderId the primary key of the new folder
	 * @param  serviceContext the service context to be applied
	 * @return the file entry
	 * @throws PortalException if the file entry or the new folder could not be
	 *         found
	 * @throws SystemException if a system exception occurred
	 */
	public FileEntry moveFileEntry(
			long fileEntryId, long newFolderId, ServiceContext serviceContext)
		throws PortalException, SystemException {

		Repository fromRepository = getRepository(0, fileEntryId, 0);
		Repository toRepository = getRepository(newFolderId, serviceContext);

		if (fromRepository.getRepositoryId() ==
				toRepository.getRepositoryId()) {

			// Move file entries within repository

			return fromRepository.moveFileEntry(
				fileEntryId, newFolderId, serviceContext);
		}

		// Move file entries between repositories

		return moveFileEntries(
			fileEntryId, newFolderId, fromRepository, toRepository,
			serviceContext);
	}

	/**
	 * Moves the folder to the new parent folder with the primary key.
	 *
	 * @param  folderId the primary key of the folder
	 * @param  parentFolderId the primary key of the new parent folder
	 * @param  serviceContext the service context to be applied
	 * @return the file entry
	 * @throws PortalException if the folder could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Folder moveFolder(
			long folderId, long parentFolderId, ServiceContext serviceContext)
		throws PortalException, SystemException {

		Repository fromRepository = getRepository(folderId, 0, 0);
		Repository toRepository = getRepository(parentFolderId, serviceContext);

		if (fromRepository.getRepositoryId() ==
				toRepository.getRepositoryId()) {

			// Move file entries within repository

			return fromRepository.moveFolder(
				folderId, parentFolderId, serviceContext);
		}

		// Move file entries between repositories

		return moveFolders(
			folderId, parentFolderId, fromRepository, toRepository,
			serviceContext);
	}

	/**
	 * Refreshes the lock for the file entry. This method is primarily used by
	 * WebDAV.
	 *
	 * @param  lockUuid the lock's universally unique identifier
	 * @param  expirationTime the time in milliseconds before the lock expires.
	 *         If the value is <code>0</code>, the default expiration time will
	 *         be used from <code>portal.properties>.
	 * @return the lock object
	 * @throws PortalException if the file entry or lock could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Lock refreshFileEntryLock(String lockUuid, long expirationTime)
		throws PortalException, SystemException {

		Lock lock = lockLocalService.getLockByUuid(lockUuid);

		long fileEntryId = GetterUtil.getLong(lock.getKey());

		Repository repository = getRepository(0, fileEntryId, 0);

		return repository.refreshFileEntryLock(lockUuid, expirationTime);
	}

	/**
	 * Refreshes the lock for the folder. This method is primarily used by
	 * WebDAV.
	 *
	 * @param  lockUuid the lock's universally unique identifier
	 * @param  expirationTime the time in milliseconds before the lock expires.
	 *         If the value is <code>0</code>, the default expiration time will
	 *         be used from <code>portal.properties>.
	 * @return the lock object
	 * @throws PortalException if the folder or lock could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Lock refreshFolderLock(String lockUuid, long expirationTime)
		throws PortalException, SystemException {

		Lock lock = lockLocalService.getLockByUuid(lockUuid);

		long folderId = GetterUtil.getLong(lock.getKey());

		Repository repository = getRepository(0, folderId, 0);

		return repository.refreshFolderLock(lockUuid, expirationTime);
	}

	/**
	 * Reverts the file entry to a previous version. A new version will be
	 * created based on the previous version and metadata.
	 *
	 * @param  fileEntryId the primary key of the file entry
	 * @param  version the version to revert back to
	 * @param  serviceContext the service context to be applied
	 * @throws PortalException if the file entry or version could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public void revertFileEntry(
			long fileEntryId, String version, ServiceContext serviceContext)
		throws PortalException, SystemException {

		Repository repository = getRepository(0, fileEntryId, 0);

		repository.revertFileEntry(fileEntryId, version, serviceContext);

		FileEntry fileEntry = getFileEntry(fileEntryId);

		dlAppHelperLocalService.updateFileEntry(
			getUserId(), fileEntry, fileEntry.getFileVersion(), serviceContext);
	}

	public Hits search(
			long repositoryId, SearchContext searchContext)
		throws SearchException {

		try {
			Repository repository = getRepository(repositoryId);

			return repository.search(searchContext);
		}
		catch (Exception e) {
			throw new SearchException(e);
		}
	}

	public Hits search(
			long repositoryId, SearchContext searchContext, Query query)
		throws SearchException {

		try {
			Repository repository = getRepository(repositoryId);

			return repository.search(searchContext, query);
		}
		catch (Exception e) {
			throw new SearchException(e);
		}
	}

	/**
	 * Unlocks the folder. This method is primarily used by WebDAV.
	 *
	 * @param  repositoryId the primary key of the repository
	 * @param  folderId the primary key of the folder
	 * @param  lockUuid the lock's universally unique identifier
	 * @throws PortalException if the repository or folder could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public void unlockFolder(long repositoryId, long folderId, String lockUuid)
		throws PortalException, SystemException {

		Repository repository = getRepository(repositoryId);

		repository.unlockFolder(folderId, lockUuid);
	}

	/**
	 * Unlocks the folder. This method is primarily used by WebDAV.
	 *
	 * @param  repositoryId the primary key of the repository
	 * @param  parentFolderId the primary key of the parent folder
	 * @param  name the folder's name
	 * @param  lockUuid the lock's universally unique identifier
	 * @throws PortalException if the repository or folder could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public void unlockFolder(
			long repositoryId, long parentFolderId, String name,
			String lockUuid)
		throws PortalException, SystemException {

		Repository repository = getRepository(repositoryId);

		repository.unlockFolder(parentFolderId, name, lockUuid);
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
	 * @param  fileEntryId the primary key of the file entry
	 * @param  sourceFileName the original file's name (optionally
	 *         <code>null</code>)
	 * @param  mimeType the file's MIME type (optionally <code>null</code>)
	 * @param  title the new name to be assigned to the file (optionally <code>
	 *         null</code>)
	 * @param  description the file's new description
	 * @param  changeLog the file's version change log (optionally
	 *         <code>null</code>)
	 * @param  majorVersion whether the new file version is a major version
	 * @param  bytes the file's data (optionally <code>null</code>)
	 * @param  serviceContext the service context to be applied. Can specify the
	 *         file entry's asset category IDs, asset tag names, and expando
	 *         bridge attributes. In a Liferay repository, it may include:
	 *
	 *         <ul>
	 *         <li>
	 *         fileEntryTypeId - ID for a custom file entry type
	 *         </li>
	 *         <li>
	 *         fieldsMap - mapping for fields associated with a custom file
	 *         entry type
	 *         </li>
	 *         </ul>
	 *
	 * @return the file entry
	 * @throws PortalException if the file entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public FileEntry updateFileEntry(
			long fileEntryId, String sourceFileName, String mimeType,
			String title, String description, String changeLog,
			boolean majorVersion, byte[] bytes, ServiceContext serviceContext)
		throws PortalException, SystemException {

		File file = null;

		try {
			if ((bytes != null) && (bytes.length > 0)) {
				file = FileUtil.createTempFile(bytes);
			}

			return updateFileEntry(
				fileEntryId, sourceFileName, mimeType, title, description,
				changeLog, majorVersion, file, serviceContext);
		}
		catch (IOException ioe) {
			throw new SystemException("Unable to write temporary file", ioe);
		}
		finally {
			FileUtil.delete(file);
		}
	}

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
	 * @param  fileEntryId the primary key of the file entry
	 * @param  sourceFileName the original file's name (optionally
	 *         <code>null</code>)
	 * @param  mimeType the file's MIME type (optionally <code>null</code>)
	 * @param  title the new name to be assigned to the file (optionally <code>
	 *         null</code>)
	 * @param  description the file's new description
	 * @param  changeLog the file's version change log (optionally
	 *         <code>null</code>)
	 * @param  majorVersion whether the new file version is a major version
	 * @param  file EntryId the primary key of the file entry
	 * @param  serviceContext the service context to be applied. Can specify the
	 *         file entry's asset category IDs, asset tag names, and expando
	 *         bridge attributes. In a Liferay repository, it may include:
	 *
	 *         <ul>
	 *         <li>
	 *         fileEntryTypeId - ID for a custom file entry type
	 *         </li>
	 *         <li>
	 *         fieldsMap - mapping for fields associated with a custom file
	 *         entry type
	 *         </li>
	 *         </ul>
	 *
	 * @return the file entry
	 * @throws PortalException if the file entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public FileEntry updateFileEntry(
			long fileEntryId, String sourceFileName, String mimeType,
			String title, String description, String changeLog,
			boolean majorVersion, File file, ServiceContext serviceContext)
		throws PortalException, SystemException {

		if (file == null || !file.exists() || (file.length() == 0)) {
			return updateFileEntry(
				fileEntryId, sourceFileName, mimeType, title, description,
				changeLog, majorVersion, null, 0, serviceContext);
		}

		Repository repository = getRepository(0, fileEntryId, 0);

		FileEntry fileEntry = repository.updateFileEntry(
			fileEntryId, sourceFileName, mimeType, title, description,
			changeLog, majorVersion, file, serviceContext);

		dlAppHelperLocalService.updateFileEntry(
			getUserId(), fileEntry, fileEntry.getFileVersion(), serviceContext);

		return fileEntry;
	}

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
	 * @param  fileEntryId the primary key of the file entry
	 * @param  sourceFileName the original file's name (optionally
	 *         <code>null</code>)
	 * @param  mimeType the file's MIME type (optionally <code>null</code>)
	 * @param  title the new name to be assigned to the file (optionally <code>
	 *         null</code>)
	 * @param  description the file's new description
	 * @param  changeLog the file's version change log (optionally
	 *         <code>null</code>)
	 * @param  majorVersion whether the new file version is a major version
	 * @param  is the file's data (optionally <code>null</code>)
	 * @param  size the file's size (optionally <code>0</code>)
	 * @param  serviceContext the service context to be applied. Can specify the
	 *         file entry's asset category IDs, asset tag names, and expando
	 *         bridge attributes. In a Liferay repository, it may include:
	 *
	 *         <ul>
	 *         <li>
	 *         fileEntryTypeId - ID for a custom file entry type
	 *         </li>
	 *         <li>
	 *         fieldsMap - mapping for fields associated with a custom file
	 *         entry type
	 *         </li>
	 *         </ul>
	 *
	 * @return the file entry
	 * @throws PortalException if the file entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public FileEntry updateFileEntry(
			long fileEntryId, String sourceFileName, String mimeType,
			String title, String description, String changeLog,
			boolean majorVersion, InputStream is, long size,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		Repository repository = getRepository(0, fileEntryId, 0);

		FileEntry fileEntry = repository.updateFileEntry(
			fileEntryId, sourceFileName, mimeType, title, description,
			changeLog, majorVersion, is, size, serviceContext);

		dlAppHelperLocalService.updateFileEntry(
			getUserId(), fileEntry, fileEntry.getFileVersion(), serviceContext);

		return fileEntry;
	}

	public FileEntry updateFileEntryAndCheckIn(
			long fileEntryId, String sourceFileName, String mimeType,
			String title, String description, String changeLog,
			boolean majorVersion, File file, ServiceContext serviceContext)
		throws PortalException, SystemException {

		if ((file == null) || !file.exists() || (file.length() == 0)) {
			return updateFileEntryAndCheckIn(
				fileEntryId, sourceFileName, mimeType, title, description,
				changeLog, majorVersion, null, 0, serviceContext);
		}

		Repository repository = getRepository(0, fileEntryId, 0);

		FileEntry fileEntry = repository.updateFileEntry(
			fileEntryId, sourceFileName, mimeType, title, description,
			changeLog, majorVersion, file, serviceContext);

		repository.checkInFileEntry(
			fileEntryId, majorVersion, changeLog, serviceContext);

		dlAppHelperLocalService.updateFileEntry(
			getUserId(), fileEntry, fileEntry.getFileVersion(), serviceContext);

		return fileEntry;
	}

	public FileEntry updateFileEntryAndCheckIn(
			long fileEntryId, String sourceFileName, String mimeType,
			String title, String description, String changeLog,
			boolean majorVersion, InputStream is, long size,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		Repository repository = getRepository(0, fileEntryId, 0);

		FileEntry fileEntry = repository.updateFileEntry(
			fileEntryId, sourceFileName, mimeType, title, description,
			changeLog, majorVersion, is, size, serviceContext);

		repository.checkInFileEntry(
			fileEntryId, majorVersion, changeLog, serviceContext);

		dlAppHelperLocalService.updateFileEntry(
			getUserId(), fileEntry, fileEntry.getFileVersion(), serviceContext);

		return fileEntry;
	}

	/**
	 * Updates a file shortcut to the existing file entry. This method is only
	 * supported by the Liferay repository.
	 *
	 * @param  fileShortcutId the primary key of the file shortcut
	 * @param  folderId the primary key of the file shortcut's parent folder
	 * @param  toFileEntryId the primary key of the file shortcut's file entry
	 * @param  serviceContext the service context to be applied. Can specify the
	 *         file entry's asset category IDs, asset tag names, and expando
	 *         bridge attributes.
	 * @return the file shortcut
	 * @throws PortalException if the file shortcut, folder, or file entry could
	 *         not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileShortcut updateFileShortcut(
			long fileShortcutId, long folderId, long toFileEntryId,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		return dlFileShortcutService.updateFileShortcut(
			fileShortcutId, folderId, toFileEntryId, serviceContext);
	}

	/**
	 * Updates the folder.
	 *
	 * @param  folderId the primary key of the folder
	 * @param  name the folder's new name
	 * @param  description the folder's new description
	 * @param  serviceContext the service context to be applied. In a Liferay
	 *         repository, it may include:
	 *
	 *         <ul>
	 *         <li>
	 *         defaultFileEntryTypeId - the file entry type to default all
	 *         Liferay file entries to
	 *         </li>
	 *         <li>
	 *         fileEntryTypeSearchContainerPrimaryKeys - a comma-delimited list
	 *         of file entry type primary keys allowed in the given folder and
	 *         all descendants
	 *         </li>
	 *         <li>
	 *         overrideFileEntryTypes - boolean specifying whether to override
	 *         ancestral folder's restriction of file entry types allowed
	 *         </li>
	 *         <li>
	 *         workflowDefinitionXYZ - the workflow definition name specified
	 *         per file entry type. The parameter name must be the string
	 *         <code>workflowDefinition</code> appended by the
	 *         <code>fileEntryTypeId</code> (optionally <code>0</code>).
	 *         </li>
	 *         </ul>
	 *
	 * @return the folder
	 * @throws PortalException if the current or new parent folder could not be
	 *         found or if the new parent folder's information was invalid
	 * @throws SystemException if a system exception occurred
	 */
	public Folder updateFolder(
			long folderId, String name, String description,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		Repository repository = null;

		if (folderId == DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			repository = getRepository(serviceContext.getScopeGroupId());
		}
		else {
			repository = getRepository(folderId, 0, 0);
		}

		return repository.updateFolder(
			folderId, name, description, serviceContext);
	}

	/**
	 * Returns <code>true</code> if the file entry is checked out. This method
	 * is primarily used by WebDAV.
	 *
	 * @param  repositoryId the primary key for the repository
	 * @param  fileEntryId the primary key for the file entry
	 * @param  lockUuid the lock's universally unique identifier
	 * @return <code>true</code> if the file entry is checked out;
	 *         <code>false</code> otherwise
	 * @throws PortalException if the file entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public boolean verifyFileEntryCheckOut(
			long repositoryId, long fileEntryId, String lockUuid)
		throws PortalException, SystemException {

		Repository repository = getRepository(repositoryId);

		return repository.verifyFileEntryCheckOut(fileEntryId, lockUuid);
	}

	/**
	 * Returns <code>true</code> if the inheritable lock exists. This method is
	 * primarily used by WebDAV.
	 *
	 * @param  repositoryId the primary key for the repository
	 * @param  folderId the primary key for the folder
	 * @param  lockUuid the lock's universally unique identifier
	 * @return <code>true</code> if the inheritable lock exists;
	 *         <code>false</code> otherwise
	 * @throws PortalException if the folder could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public boolean verifyInheritableLock(
			long repositoryId, long folderId, String lockUuid)
		throws PortalException, SystemException {

		Repository repository = getRepository(repositoryId);

		return repository.verifyInheritableLock(
			folderId, lockUuid);
	}

	protected FileEntry copyFileEntry(
			Repository toRepository, FileEntry fileEntry, long newFolderId,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		List<FileVersion> fileVersions = fileEntry.getFileVersions(
			WorkflowConstants.STATUS_ANY);

		FileVersion latestFileVersion = fileVersions.get(
			fileVersions.size() - 1);

		FileEntry destinationFileEntry = toRepository.addFileEntry(
			newFolderId, fileEntry.getTitle(), latestFileVersion.getMimeType(),
			latestFileVersion.getTitle(), latestFileVersion.getDescription(),
			StringPool.BLANK, latestFileVersion.getContentStream(false),
			latestFileVersion.getSize(), serviceContext);

		for (int i = fileVersions.size() - 2; i >= 0 ; i--) {
			FileVersion fileVersion = fileVersions.get(i);

			FileVersion previousFileVersion = fileVersions.get(i + 1);

			try {
				destinationFileEntry = toRepository.updateFileEntry(
					destinationFileEntry.getFileEntryId(), fileEntry.getTitle(),
					destinationFileEntry.getMimeType(),
					destinationFileEntry.getTitle(),
					destinationFileEntry.getDescription(), StringPool.BLANK,
					isMajorVersion(previousFileVersion, fileVersion),
					fileVersion.getContentStream(false), fileVersion.getSize(),
					serviceContext);
			}
			catch (PortalException pe) {
				toRepository.deleteFileEntry(
					destinationFileEntry.getFileEntryId());

				throw  pe;
			}
		}

		dlAppHelperLocalService.addFileEntry(
			getUserId(), destinationFileEntry,
			destinationFileEntry.getFileVersion(), serviceContext);

		return destinationFileEntry;
	}

	protected void copyFolder(
			Repository repository, Folder srcFolder, Folder destFolder,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		Queue<Folder[]> folders = new LinkedList<Folder[]>();
		final List<FileEntry> fileEntries = new ArrayList<FileEntry>();

		Folder curSrcFolder = srcFolder;
		Folder curDestFolder = destFolder;

		while (true) {
			List<FileEntry> srcFileEntries = repository.getFileEntries(
				curSrcFolder.getFolderId(), QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null);

			for (FileEntry srcFileEntry : srcFileEntries) {
				try {
					FileEntry fileEntry = repository.copyFileEntry(
						curDestFolder.getGroupId(),
						srcFileEntry.getFileEntryId(),
						curDestFolder.getFolderId(), serviceContext);

					dlAppHelperLocalService.addFileEntry(
						getUserId(), fileEntry, fileEntry.getFileVersion(),
						serviceContext);

					fileEntries.add(fileEntry);
				}
				catch (Exception e) {
					_log.error(e, e);

					continue;
				}
			}

			List<Folder> srcSubfolders = repository.getFolders(
				curSrcFolder.getFolderId(), false, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null);

			for (Folder srcSubfolder : srcSubfolders) {
				Folder destSubfolder = repository.addFolder(
					curDestFolder.getFolderId(), srcSubfolder.getName(),
					srcSubfolder.getDescription(), serviceContext);

				folders.offer(new Folder[] {srcSubfolder, destSubfolder});
			}

			Folder[] next = folders.poll();

			if (next == null) {
				break;
			}
			else {
				curSrcFolder = next[0];
				curDestFolder = next[1];
			}
		}

		TransactionCommitCallbackUtil.registerCallback(
			new Callable<Void>() {

				public Void call() throws Exception {
					for (FileEntry fileEntry : fileEntries) {
						DLProcessorRegistryUtil.trigger(fileEntry);
					}

					return null;
				}

			});
	}

	protected void deleteFileEntry(
			long oldFileEntryId, long newFileEntryId,
			Repository fromRepository, Repository toRepository)
		throws PortalException, SystemException {

		try {
			FileEntry fileEntry = fromRepository.getFileEntry(oldFileEntryId);

			dlAppHelperLocalService.deleteFileEntry(fileEntry);

			fromRepository.deleteFileEntry(oldFileEntryId);
		}
		catch (PortalException pe) {
			FileEntry fileEntry = toRepository.getFileEntry(newFileEntryId);

			toRepository.deleteFileEntry(newFileEntryId);

			dlAppHelperLocalService.deleteFileEntry(fileEntry);

			throw pe;
		}
	}

	protected Repository getRepository(long repositoryId)
		throws PortalException, SystemException {

		return repositoryService.getRepositoryImpl(repositoryId);
	}

	protected Repository getRepository(
			long folderId, long fileEntryId, long fileVersionId)
		throws PortalException, SystemException {

		return repositoryService.getRepositoryImpl(
			folderId, fileEntryId, fileVersionId);
	}

	protected Repository getRepository(
			long folderId, ServiceContext serviceContext)
		throws PortalException, SystemException{

		Repository repository = null;

		if (folderId == DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			repository = getRepository(serviceContext.getScopeGroupId());
		}
		else {
			repository = getRepository(folderId, 0, 0);
		}

		return repository;
	}

	protected boolean isMajorVersion(
		FileVersion previousFileVersion, FileVersion currentFileVersion) {

		long currentVersion = GetterUtil.getLong(
			currentFileVersion.getVersion());
		long previousVersion = GetterUtil.getLong(
			previousFileVersion.getVersion());

		return (currentVersion - previousVersion) >= 1;
	}

	protected FileEntry moveFileEntries(
			long fileEntryId, long newFolderId, Repository fromRepository,
			Repository toRepository, ServiceContext serviceContext)
		throws SystemException, PortalException {

		FileEntry sourceFileEntry = fromRepository.getFileEntry(fileEntryId);

		FileEntry destinationFileEntry = copyFileEntry(
			toRepository, sourceFileEntry, newFolderId, serviceContext);

		deleteFileEntry(
			fileEntryId, destinationFileEntry.getFileEntryId(), fromRepository,
			toRepository);

		return destinationFileEntry;
	}

	protected Folder moveFolders(
			long folderId, long parentFolderId, Repository fromRepository,
			Repository toRepository, ServiceContext serviceContext)
		throws PortalException, SystemException{

		Folder folder = fromRepository.getFolder(folderId);

		Folder newFolder = toRepository.addFolder(
			parentFolderId, folder.getName(), folder.getDescription(),
			serviceContext);

		List<Object> foldersAndFileEntriesAndFileShortcuts =
			getFoldersAndFileEntriesAndFileShortcuts(
				fromRepository.getRepositoryId(), folderId,
				WorkflowConstants.STATUS_ANY, true, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS);

		try {
			for (Object folderAndFileEntryAndFileShortcut :
					foldersAndFileEntriesAndFileShortcuts) {

				if (folderAndFileEntryAndFileShortcut instanceof FileEntry) {
					FileEntry fileEntry =
						(FileEntry)folderAndFileEntryAndFileShortcut;

					copyFileEntry(
						toRepository, fileEntry, newFolder.getFolderId(),
						serviceContext);
				}
				else if (folderAndFileEntryAndFileShortcut instanceof Folder) {
					Folder currentFolder =
						(Folder)folderAndFileEntryAndFileShortcut;

					moveFolders(
						currentFolder.getFolderId(), newFolder.getFolderId(),
						fromRepository, toRepository, serviceContext);

				}
				else if (folderAndFileEntryAndFileShortcut
							instanceof DLFileShortcut) {

					if (newFolder.isSupportsShortcuts()) {
						DLFileShortcut dlFileShorcut =
							(DLFileShortcut)folderAndFileEntryAndFileShortcut;

						dlFileShortcutService.addFileShortcut(
							dlFileShorcut.getGroupId(), newFolder.getFolderId(),
							dlFileShorcut.getToFileEntryId(), serviceContext);
					}
				}
			}
		}
		catch (PortalException pe) {
			toRepository.deleteFolder(newFolder.getFolderId());

			throw  pe;
		}

		try {
			fromRepository.deleteFolder(folderId);
		}
		catch (PortalException pe) {
			toRepository.deleteFolder(newFolder.getFolderId());

			throw pe;
		}

		return newFolder;
	}

	private static Log _log = LogFactoryUtil.getLog(DLAppServiceImpl.class);

}