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

package com.liferay.portlet.documentlibrary.service.impl;

import com.liferay.portal.InvalidRepositoryException;
import com.liferay.portal.NoSuchGroupException;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.InvalidRepositoryIdException;
import com.liferay.portal.kernel.repository.Repository;
import com.liferay.portal.kernel.repository.RepositoryException;
import com.liferay.portal.kernel.repository.capabilities.TrashCapability;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.transaction.TransactionCommitCallbackRegistryUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.TempFileEntryUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.Lock;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.documentlibrary.NoSuchFileEntryException;
import com.liferay.portlet.documentlibrary.NoSuchFileVersionException;
import com.liferay.portlet.documentlibrary.NoSuchFolderException;
import com.liferay.portlet.documentlibrary.model.DLFileShortcut;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.service.base.DLAppServiceBaseImpl;
import com.liferay.portlet.documentlibrary.service.permission.DLFileEntryPermission;
import com.liferay.portlet.documentlibrary.service.permission.DLFileShortcutPermission;
import com.liferay.portlet.documentlibrary.service.permission.DLFolderPermission;
import com.liferay.portlet.documentlibrary.service.permission.DLPermission;
import com.liferay.portlet.documentlibrary.util.DLAppUtil;
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
 * Provides the remote service for accessing, adding, checking in/out, deleting,
 * locking/unlocking, moving, subscription handling of, trash handling of,
 * updating, and verifying document library file entries and folders. Its
 * methods include permission checks. All portlets should interact with the
 * document library through this class or through DLAppLocalService, rather than
 * through the individual document library service classes.
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
 * correspond to values obtained from {@link
 * com.liferay.portal.service.RepositoryServiceUtil}.
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
	 * @param  serviceContext the service context to be applied. Can set the
	 *         asset category IDs, asset tag names, and expando bridge
	 *         attributes for the file entry. In a Liferay repository, it may
	 *         include:  <ul> <li> fileEntryTypeId - ID for a custom file entry
	 *         type </li> <li> fieldsMap - mapping for fields associated with a
	 *         custom file entry type </li> </ul>
	 * @return the file entry
	 * @throws PortalException if the parent folder could not be found or if the
	 *         file entry's information was invalid
	 */
	@Override
	public FileEntry addFileEntry(
			long repositoryId, long folderId, String sourceFileName,
			String mimeType, String title, String description, String changeLog,
			byte[] bytes, ServiceContext serviceContext)
		throws PortalException {

		File file = null;

		try {
			if (ArrayUtil.isNotEmpty(bytes)) {
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
	 * @param  repositoryId the primary key of the repository
	 * @param  folderId the primary key of the file entry's parent folder
	 * @param  sourceFileName the original file's name
	 * @param  mimeType the file's MIME type
	 * @param  title the name to be assigned to the file (optionally <code>null
	 *         </code>)
	 * @param  description the file's description
	 * @param  changeLog the file's version change log
	 * @param  file the file's data (optionally <code>null</code>)
	 * @param  serviceContext the service context to be applied. Can set the
	 *         asset category IDs, asset tag names, and expando bridge
	 *         attributes for the file entry. In a Liferay repository, it may
	 *         include:  <ul> <li> fileEntryTypeId - ID for a custom file entry
	 *         type </li> <li> fieldsMap - mapping for fields associated with a
	 *         custom file entry type </li> </ul>
	 * @return the file entry
	 * @throws PortalException if the parent folder could not be found or if the
	 *         file entry's information was invalid
	 */
	@Override
	public FileEntry addFileEntry(
			long repositoryId, long folderId, String sourceFileName,
			String mimeType, String title, String description, String changeLog,
			File file, ServiceContext serviceContext)
		throws PortalException {

		if ((file == null) || !file.exists() || (file.length() == 0)) {
			return addFileEntry(
				repositoryId, folderId, sourceFileName, mimeType, title,
				description, changeLog, null, 0, serviceContext);
		}

		mimeType = DLAppUtil.getMimeType(sourceFileName, mimeType, title, file);

		Repository repository = getRepository(repositoryId);

		FileEntry fileEntry = repository.addFileEntry(
			getUserId(), folderId, sourceFileName, mimeType, title, description,
			changeLog, file, serviceContext);

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
	 * @param  serviceContext the service context to be applied. Can set the
	 *         asset category IDs, asset tag names, and expando bridge
	 *         attributes for the file entry. In a Liferay repository, it may
	 *         include:  <ul> <li> fileEntryTypeId - ID for a custom file entry
	 *         type </li> <li> fieldsMap - mapping for fields associated with a
	 *         custom file entry type </li> </ul>
	 * @return the file entry
	 * @throws PortalException if the parent folder could not be found or if the
	 *         file entry's information was invalid
	 */
	@Override
	public FileEntry addFileEntry(
			long repositoryId, long folderId, String sourceFileName,
			String mimeType, String title, String description, String changeLog,
			InputStream is, long size, ServiceContext serviceContext)
		throws PortalException {

		if (is == null) {
			is = new UnsyncByteArrayInputStream(new byte[0]);
			size = 0;
		}

		if (Validator.isNull(mimeType) ||
			mimeType.equals(ContentTypes.APPLICATION_OCTET_STREAM)) {

			String extension = DLAppUtil.getExtension(title, sourceFileName);

			if (size == 0) {
				mimeType = MimeTypesUtil.getExtensionContentType(extension);
			}
			else {
				File file = null;

				try {
					file = FileUtil.createTempFile(is);

					return addFileEntry(
						repositoryId, folderId, sourceFileName, mimeType, title,
						description, changeLog, file, serviceContext);
				}
				catch (IOException ioe) {
					throw new SystemException(
						"Unable to write temporary file", ioe);
				}
				finally {
					FileUtil.delete(file);
				}
			}
		}

		Repository repository = getRepository(repositoryId);

		FileEntry fileEntry = repository.addFileEntry(
			getUserId(), folderId, sourceFileName, mimeType, title, description,
			changeLog, is, size, serviceContext);

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
	 * @param  serviceContext the service context to be applied. Can set the
	 *         asset category IDs, asset tag names, and expando bridge
	 *         attributes for the file entry.
	 * @return the file shortcut
	 * @throws PortalException if the parent folder or file entry could not be
	 *         found, or if the file shortcut's information was invalid
	 */
	@Override
	public DLFileShortcut addFileShortcut(
			long repositoryId, long folderId, long toFileEntryId,
			ServiceContext serviceContext)
		throws PortalException {

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
	 */
	@Override
	public Folder addFolder(
			long repositoryId, long parentFolderId, String name,
			String description, ServiceContext serviceContext)
		throws PortalException {

		Repository repository = getRepository(repositoryId);

		Folder folder = repository.addFolder(
			parentFolderId, name, description, serviceContext);

		dlAppHelperLocalService.addFolder(getUserId(), folder, serviceContext);

		return folder;
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
	 * @param  folderName the temporary folder's name
	 * @param  fileName the file's original name
	 * @param  file the file's data (optionally <code>null</code>)
	 * @param  mimeType the file's MIME type
	 * @return the temporary file entry
	 * @throws PortalException if the file name was invalid
	 * @see    com.liferay.portal.kernel.util.TempFileEntryUtil
	 */
	@Override
	public FileEntry addTempFileEntry(
			long groupId, long folderId, String folderName, String fileName,
			File file, String mimeType)
		throws PortalException {

		DLFolderPermission.check(
			getPermissionChecker(), groupId, folderId, ActionKeys.ADD_DOCUMENT);

		return TempFileEntryUtil.addTempFileEntry(
			groupId, getUserId(), folderName, fileName, file, mimeType);
	}

	/**
	 * Adds a temporary file entry. It is created based on the {@link
	 * InputStream} object.
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
	 * @param  folderName the temporary folder's name
	 * @param  fileName the file's original name
	 * @param  inputStream the file's data
	 * @param  mimeType the file's MIME type
	 * @return the temporary file entry
	 * @throws PortalException if the file name was invalid or if a portal
	 *         exception occurred
	 * @see    com.liferay.portal.kernel.util.TempFileEntryUtil
	 */
	@Override
	public FileEntry addTempFileEntry(
			long groupId, long folderId, String folderName, String fileName,
			InputStream inputStream, String mimeType)
		throws PortalException {

		DLFolderPermission.check(
			getPermissionChecker(), groupId, folderId, ActionKeys.ADD_DOCUMENT);

		return TempFileEntryUtil.addTempFileEntry(
			groupId, getUserId(), folderName, fileName, inputStream, mimeType);
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
	 * @see    #checkInFileEntry(long, boolean, String, ServiceContext)
	 * @see    #checkOutFileEntry(long, ServiceContext)
	 */
	@Override
	public void cancelCheckOut(long fileEntryId) throws PortalException {
		Repository repository = getFileEntryRepository(fileEntryId);

		FileEntry fileEntry = repository.getFileEntry(fileEntryId);

		DLProcessorRegistryUtil.cleanUp(fileEntry.getLatestFileVersion());

		FileVersion draftFileVersion = repository.cancelCheckOut(fileEntryId);

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setWorkflowAction(WorkflowConstants.ACTION_PUBLISH);

		dlAppHelperLocalService.cancelCheckOut(
			getUserId(), fileEntry, null, fileEntry.getFileVersion(),
			draftFileVersion, serviceContext);
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
	 * @see    #cancelCheckOut(long)
	 * @see    #checkOutFileEntry(long, ServiceContext)
	 */
	@Override
	public void checkInFileEntry(
			long fileEntryId, boolean majorVersion, String changeLog,
			ServiceContext serviceContext)
		throws PortalException {

		Repository repository = getFileEntryRepository(fileEntryId);

		FileEntry oldFileEntry = repository.getFileEntry(fileEntryId);

		FileVersion oldFileVersion = oldFileEntry.getFileVersion();

		repository.checkInFileEntry(
			fileEntryId, majorVersion, changeLog, serviceContext);

		FileEntry fileEntry = getFileEntry(fileEntryId);

		FileVersion fileVersion = fileEntry.getLatestFileVersion();

		dlAppHelperLocalService.updateFileEntry(
			getUserId(), fileEntry, oldFileVersion, fileVersion,
			fileVersion.getFileVersionId());
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #checkInFileEntry(long,
	 *             String, ServiceContext)}
	 */
	@Deprecated
	@Override
	public void checkInFileEntry(long fileEntryId, String lockUuid)
		throws PortalException {

		checkInFileEntry(fileEntryId, lockUuid, new ServiceContext());
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
	 * @param  lockUuid the lock's UUID
	 * @param  serviceContext the service context to be applied
	 * @throws PortalException if the file entry could not be found
	 * @see    #cancelCheckOut(long)
	 * @see    #checkOutFileEntry(long, String, long, ServiceContext)
	 */
	@Override
	public void checkInFileEntry(
			long fileEntryId, String lockUuid, ServiceContext serviceContext)
		throws PortalException {

		Repository repository = getFileEntryRepository(fileEntryId);

		FileEntry oldFileEntry = repository.getFileEntry(fileEntryId);

		FileVersion oldFileVersion = oldFileEntry.getFileVersion();

		repository.checkInFileEntry(fileEntryId, lockUuid, serviceContext);

		FileEntry fileEntry = getFileEntry(fileEntryId);

		FileVersion fileVersion = fileEntry.getLatestFileVersion();

		dlAppHelperLocalService.updateFileEntry(
			getUserId(), fileEntry, oldFileVersion, fileVersion,
			fileVersion.getFileVersionId());
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
	 * @param  serviceContext the service context to be applied
	 * @throws PortalException if the file entry could not be found
	 * @see    #cancelCheckOut(long)
	 * @see    #checkInFileEntry(long, boolean, String, ServiceContext)
	 */
	@Override
	public void checkOutFileEntry(
			long fileEntryId, ServiceContext serviceContext)
		throws PortalException {

		Repository repository = getFileEntryRepository(fileEntryId);

		FileEntry oldFileEntry = repository.getFileEntry(fileEntryId);

		FileVersion oldFileVersion = oldFileEntry.getFileVersion();

		FileEntry fileEntry = repository.checkOutFileEntry(
			fileEntryId, serviceContext);

		FileVersion fileVersion = fileEntry.getLatestFileVersion();

		dlAppHelperLocalService.updateFileEntry(
			getUserId(), fileEntry, oldFileVersion, fileVersion, fileEntryId);
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
	 * @param  serviceContext the service context to be applied
	 * @return the file entry
	 * @throws PortalException if the file entry could not be found
	 * @see    #cancelCheckOut(long)
	 * @see    #checkInFileEntry(long, String)
	 */
	@Override
	public FileEntry checkOutFileEntry(
			long fileEntryId, String owner, long expirationTime,
			ServiceContext serviceContext)
		throws PortalException {

		Repository repository = getFileEntryRepository(fileEntryId);

		FileEntry oldFileEntry = repository.getFileEntry(fileEntryId);

		FileVersion oldFileVersion = oldFileEntry.getFileVersion();

		FileEntry fileEntry = repository.checkOutFileEntry(
			fileEntryId, owner, expirationTime, serviceContext);

		FileVersion fileVersion = fileEntry.getLatestFileVersion();

		dlAppHelperLocalService.updateFileEntry(
			getUserId(), fileEntry, oldFileVersion, fileVersion, fileEntryId);

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
	 */
	@Override
	public Folder copyFolder(
			long repositoryId, long sourceFolderId, long parentFolderId,
			String name, String description, ServiceContext serviceContext)
		throws PortalException {

		Repository repository = getRepository(repositoryId);

		Folder srcFolder = repository.getFolder(sourceFolderId);

		Folder destFolder = repository.addFolder(
			parentFolderId, name, description, serviceContext);

		dlAppHelperLocalService.addFolder(
			getUserId(), destFolder, serviceContext);

		copyFolder(repository, srcFolder, destFolder, serviceContext);

		return destFolder;
	}

	/**
	 * Deletes the file entry with the primary key.
	 *
	 * @param  fileEntryId the primary key of the file entry
	 * @throws PortalException if the file entry could not be found
	 */
	@Override
	public void deleteFileEntry(long fileEntryId) throws PortalException {
		Repository repository = getFileEntryRepository(fileEntryId);

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
	 */
	@Override
	public void deleteFileEntryByTitle(
			long repositoryId, long folderId, String title)
		throws PortalException {

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
	 */
	@Override
	public void deleteFileShortcut(long fileShortcutId) throws PortalException {
		dlFileShortcutService.deleteFileShortcut(fileShortcutId);
	}

	/**
	 * Deletes the file version. File versions can only be deleted if it is
	 * approved and there are other approved file versions available. This
	 * method is only supported by the Liferay repository.
	 *
	 * @param  fileEntryId the primary key of the file entry
	 * @param  version the version label of the file version
	 * @throws PortalException if the file version could not be found or invalid
	 */
	@Override
	public void deleteFileVersion(long fileEntryId, String version)
		throws PortalException {

		Repository repository = getFileEntryRepository(fileEntryId);

		repository.deleteFileVersion(fileEntryId, version);
	}

	/**
	 * Deletes the folder with the primary key and all of its subfolders and
	 * file entries.
	 *
	 * @param  folderId the primary key of the folder
	 * @throws PortalException if the folder could not be found
	 */
	@Override
	public void deleteFolder(long folderId) throws PortalException {
		Repository repository = getFolderRepository(folderId);

		Folder folder = repository.getFolder(folderId);

		if (repository.isCapabilityProvided(TrashCapability.class)) {
			TrashCapability trashCapability = repository.getCapability(
				TrashCapability.class);

			if (trashCapability.isInTrash(folder)) {
				trashCapability.deleteFolder(folder);

				return;
			}
		}

		List<FileEntry> fileEntries = repository.getRepositoryFileEntries(
			0, folderId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

		for (FileEntry fileEntry : fileEntries) {
			dlAppHelperLocalService.deleteFileEntry(fileEntry);
		}

		repository.deleteFolder(folderId);

		dlAppHelperLocalService.deleteFolder(folder);
	}

	/**
	 * Deletes the folder with the name in the parent folder and all of its
	 * subfolders and file entries.
	 *
	 * @param  repositoryId the primary key of the repository
	 * @param  parentFolderId the primary key of the folder's parent folder
	 * @param  name the folder's name
	 * @throws PortalException if the folder could not be found
	 */
	@Override
	public void deleteFolder(
			long repositoryId, long parentFolderId, String name)
		throws PortalException {

		Repository repository = getRepository(repositoryId);

		Folder folder = repository.getFolder(parentFolderId, name);

		if (repository.isCapabilityProvided(TrashCapability.class)) {
			TrashCapability trashCapability = repository.getCapability(
				TrashCapability.class);

			if (trashCapability.isInTrash(folder)) {
				trashCapability.deleteFolder(folder);

				return;
			}
		}

		repository.deleteFolder(parentFolderId, name);
	}

	/**
	 * Deletes the temporary file entry.
	 *
	 * @param  groupId the primary key of the group
	 * @param  folderId the primary key of the folder where the file entry was
	 *         eventually to reside
	 * @param  folderName the temporary folder's name
	 * @param  fileName the file's original name
	 * @throws PortalException if the file name was invalid
	 * @see    com.liferay.portal.kernel.util.TempFileEntryUtil
	 */
	@Override
	public void deleteTempFileEntry(
			long groupId, long folderId, String folderName, String fileName)
		throws PortalException {

		DLFolderPermission.check(
			getPermissionChecker(), groupId, folderId, ActionKeys.ADD_DOCUMENT);

		TempFileEntryUtil.deleteTempFileEntry(
			groupId, getUserId(), folderName, fileName);
	}

	/**
	 * Returns all the file entries in the folder.
	 *
	 * @param  repositoryId the primary key of the file entry's repository
	 * @param  folderId the primary key of the file entry's folder
	 * @return the file entries in the folder
	 * @throws PortalException if the folder could not be found
	 */
	@Override
	public List<FileEntry> getFileEntries(long repositoryId, long folderId)
		throws PortalException {

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
	 */
	@Override
	public List<FileEntry> getFileEntries(
			long repositoryId, long folderId, int start, int end)
		throws PortalException {

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
	 */
	@Override
	public List<FileEntry> getFileEntries(
			long repositoryId, long folderId, int start, int end,
			OrderByComparator<FileEntry> obc)
		throws PortalException {

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
	 */
	@Override
	public List<FileEntry> getFileEntries(
			long repositoryId, long folderId, long fileEntryTypeId)
		throws PortalException {

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
	 */
	@Override
	public List<FileEntry> getFileEntries(
			long repositoryId, long folderId, long fileEntryTypeId, int start,
			int end)
		throws PortalException {

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
	 */
	@Override
	public List<FileEntry> getFileEntries(
			long repositoryId, long folderId, long fileEntryTypeId, int start,
			int end, OrderByComparator<FileEntry> obc)
		throws PortalException {

		Repository repository = getRepository(repositoryId);

		return repository.getFileEntries(
			folderId, fileEntryTypeId, start, end, obc);
	}

	@Override
	public List<FileEntry> getFileEntries(
			long repositoryId, long folderId, String[] mimeTypes)
		throws PortalException {

		return getFileEntries(
			repositoryId, folderId, mimeTypes, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	@Override
	public List<FileEntry> getFileEntries(
			long repositoryId, long folderId, String[] mimeTypes, int start,
			int end, OrderByComparator<FileEntry> obc)
		throws PortalException {

		Repository repository = getRepository(repositoryId);

		return repository.getFileEntries(folderId, mimeTypes, start, end, obc);
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
	 */
	@Override
	public List<Object> getFileEntriesAndFileShortcuts(
			long repositoryId, long folderId, int status, int start, int end)
		throws PortalException {

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
	 */
	@Override
	public int getFileEntriesAndFileShortcutsCount(
			long repositoryId, long folderId, int status)
		throws PortalException {

		Repository repository = getRepository(repositoryId);

		return repository.getFileEntriesAndFileShortcutsCount(folderId, status);
	}

	/**
	 * Returns the number of file entries and shortcuts in the folder.
	 *
	 * @param  repositoryId the primary key of the repository
	 * @param  folderId the primary key of the folder
	 * @param  status the workflow status
	 * @param  mimeTypes allowed media types
	 * @return the number of file entries and shortcuts in the folder
	 * @throws PortalException if the folder ould not be found
	 */
	@Override
	public int getFileEntriesAndFileShortcutsCount(
			long repositoryId, long folderId, int status, String[] mimeTypes)
		throws PortalException {

		Repository repository = getRepository(repositoryId);

		return repository.getFileEntriesAndFileShortcutsCount(
			folderId, status, mimeTypes);
	}

	/**
	 * Returns the number of file entries in the folder.
	 *
	 * @param  repositoryId the primary key of the file entry's repository
	 * @param  folderId the primary key of the file entry's folder
	 * @return the number of file entries in the folder
	 * @throws PortalException if the folder could not be found
	 */
	@Override
	public int getFileEntriesCount(long repositoryId, long folderId)
		throws PortalException {

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
	 */
	@Override
	public int getFileEntriesCount(
			long repositoryId, long folderId, long fileEntryTypeId)
		throws PortalException {

		Repository repository = getRepository(repositoryId);

		return repository.getFileEntriesCount(folderId, fileEntryTypeId);
	}

	@Override
	public int getFileEntriesCount(
			long repositoryId, long folderId, String[] mimeTypes)
		throws PortalException {

		Repository repository = getRepository(repositoryId);

		return repository.getFileEntriesCount(folderId, mimeTypes);
	}

	/**
	 * Returns the file entry with the primary key.
	 *
	 * @param  fileEntryId the primary key of the file entry
	 * @return the file entry with the primary key
	 * @throws PortalException if the file entry could not be found
	 */
	@Override
	public FileEntry getFileEntry(long fileEntryId) throws PortalException {
		Repository repository = getFileEntryRepository(fileEntryId);

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
	 */
	@Override
	public FileEntry getFileEntry(long groupId, long folderId, String title)
		throws PortalException {

		try {
			Repository repository = getRepository(groupId);

			return repository.getFileEntry(folderId, title);
		}
		catch (NoSuchFileEntryException nsfee) {
			if (folderId != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
				Repository repository = getFolderRepository(folderId);

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
	 * @param  uuid the file entry's UUID
	 * @param  groupId the primary key of the file entry's group
	 * @return the file entry with the UUID and group
	 * @throws PortalException if the file entry could not be found
	 */
	@Override
	public FileEntry getFileEntryByUuidAndGroupId(String uuid, long groupId)
		throws PortalException {

		try {
			Repository repository = getRepository(groupId);

			return repository.getFileEntryByUuid(uuid);
		}
		catch (NoSuchFileEntryException nsfee) {
		}
		catch (RepositoryException re) {
			throw new NoSuchFileEntryException(re);
		}

		List<com.liferay.portal.model.Repository> repositories =
			repositoryPersistence.findByGroupId(groupId);

		for (int i = 0; i < repositories.size(); i++) {
			try {
				long repositoryId = repositories.get(i).getRepositoryId();

				Repository repository = getRepository(repositoryId);

				return repository.getFileEntryByUuid(uuid);
			}
			catch (NoSuchFileEntryException nsfee) {
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
	 */
	@Override
	public DLFileShortcut getFileShortcut(long fileShortcutId)
		throws PortalException {

		return dlFileShortcutService.getFileShortcut(fileShortcutId);
	}

	/**
	 * Returns the file version with the primary key.
	 *
	 * @param  fileVersionId the primary key of the file version
	 * @return the file version with the primary key
	 * @throws PortalException if the file version could not be found
	 */
	@Override
	public FileVersion getFileVersion(long fileVersionId)
		throws PortalException {

		Repository repository = getFileVersionRepository(fileVersionId);

		return repository.getFileVersion(fileVersionId);
	}

	/**
	 * Returns the folder with the primary key.
	 *
	 * @param  folderId the primary key of the folder
	 * @return the folder with the primary key
	 * @throws PortalException if the folder could not be found
	 */
	@Override
	public Folder getFolder(long folderId) throws PortalException {
		Repository repository = getFolderRepository(folderId);

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
	 */
	@Override
	public Folder getFolder(long repositoryId, long parentFolderId, String name)
		throws PortalException {

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
	 */
	@Override
	public List<Folder> getFolders(long repositoryId, long parentFolderId)
		throws PortalException {

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
	 */
	@Override
	public List<Folder> getFolders(
			long repositoryId, long parentFolderId, boolean includeMountFolders)
		throws PortalException {

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
	 */
	@Override
	public List<Folder> getFolders(
			long repositoryId, long parentFolderId, boolean includeMountFolders,
			int start, int end)
		throws PortalException {

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
	 */
	@Override
	public List<Folder> getFolders(
			long repositoryId, long parentFolderId, boolean includeMountFolders,
			int start, int end, OrderByComparator<Folder> obc)
		throws PortalException {

		Repository repository = getRepository(repositoryId);

		return repository.getFolders(
			parentFolderId, includeMountFolders, start, end, obc);
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
	 * @param  status the workflow status
	 * @param  includeMountFolders whether to include mount folders for
	 *         third-party repositories
	 * @param  start the lower bound of the range of results
	 * @param  end the upper bound of the range of results (not inclusive)
	 * @param  obc the comparator to order the folders (optionally
	 *         <code>null</code>)
	 * @return the range of immediate subfolders of the parent folder ordered by
	 *         comparator <code>obc</code>
	 * @throws PortalException if the parent folder could not be found
	 */
	@Override
	public List<Folder> getFolders(
			long repositoryId, long parentFolderId, int status,
			boolean includeMountFolders, int start, int end,
			OrderByComparator<Folder> obc)
		throws PortalException {

		Repository repository = getRepository(repositoryId);

		return repository.getFolders(
			parentFolderId, status, includeMountFolders, start, end, obc);
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
	 */
	@Override
	public List<Folder> getFolders(
			long repositoryId, long parentFolderId, int start, int end)
		throws PortalException {

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
	 */
	@Override
	public List<Folder> getFolders(
			long repositoryId, long parentFolderId, int start, int end,
			OrderByComparator<Folder> obc)
		throws PortalException {

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
	 */
	@Override
	public List<Object> getFoldersAndFileEntriesAndFileShortcuts(
			long repositoryId, long folderId, int status,
			boolean includeMountFolders, int start, int end)
		throws PortalException {

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
	 */
	@Override
	public List<Object> getFoldersAndFileEntriesAndFileShortcuts(
			long repositoryId, long folderId, int status,
			boolean includeMountFolders, int start, int end,
			OrderByComparator<?> obc)
		throws PortalException {

		return getFoldersAndFileEntriesAndFileShortcuts(
			repositoryId, folderId, status, null, includeMountFolders, start,
			end, obc);
	}

	@Override
	public List<Object> getFoldersAndFileEntriesAndFileShortcuts(
			long repositoryId, long folderId, int status, String[] mimeTypes,
			boolean includeMountFolders, int start, int end,
			OrderByComparator<?> obc)
		throws PortalException {

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
	 */
	@Override
	public int getFoldersAndFileEntriesAndFileShortcutsCount(
			long repositoryId, long folderId, int status,
			boolean includeMountFolders)
		throws PortalException {

		return getFoldersAndFileEntriesAndFileShortcutsCount(
			repositoryId, folderId, status, null, includeMountFolders);
	}

	@Override
	public int getFoldersAndFileEntriesAndFileShortcutsCount(
			long repositoryId, long folderId, int status, String[] mimeTypes,
			boolean includeMountFolders)
		throws PortalException {

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
	 */
	@Override
	public int getFoldersCount(long repositoryId, long parentFolderId)
		throws PortalException {

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
	 */
	@Override
	public int getFoldersCount(
			long repositoryId, long parentFolderId, boolean includeMountFolders)
		throws PortalException {

		Repository repository = getRepository(repositoryId);

		return repository.getFoldersCount(parentFolderId, includeMountFolders);
	}

	/**
	 * Returns the number of immediate subfolders of the parent folder,
	 * optionally including mount folders for third-party repositories.
	 *
	 * @param  repositoryId the primary key of the folder's repository
	 * @param  parentFolderId the primary key of the folder's parent folder
	 * @param  status the workflow status
	 * @param  includeMountFolders whether to include mount folders for
	 *         third-party repositories
	 * @return the number of immediate subfolders of the parent folder
	 * @throws PortalException if the parent folder could not be found
	 */
	@Override
	public int getFoldersCount(
			long repositoryId, long parentFolderId, int status,
			boolean includeMountFolders)
		throws PortalException {

		Repository repository = getRepository(repositoryId);

		return repository.getFoldersCount(
			parentFolderId, status, includeMountFolders);
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
	 */
	@Override
	public int getFoldersFileEntriesCount(
			long repositoryId, List<Long> folderIds, int status)
		throws PortalException {

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
	 */
	@Override
	public List<FileEntry> getGroupFileEntries(
			long groupId, long userId, int start, int end)
		throws PortalException {

		return getGroupFileEntries(
			groupId, userId, DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, start,
			end, new RepositoryModelModifiedDateComparator<FileEntry>());
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
	 */
	@Override
	public List<FileEntry> getGroupFileEntries(
			long groupId, long userId, int start, int end,
			OrderByComparator<FileEntry> obc)
		throws PortalException {

		return getGroupFileEntries(
			groupId, userId, DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, start,
			end, obc);
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
	 */
	@Override
	public List<FileEntry> getGroupFileEntries(
			long groupId, long userId, long rootFolderId, int start, int end)
		throws PortalException {

		return getGroupFileEntries(
			groupId, userId, rootFolderId, start, end,
			new RepositoryModelModifiedDateComparator<FileEntry>());
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
	 */
	@Override
	public List<FileEntry> getGroupFileEntries(
			long groupId, long userId, long rootFolderId, int start, int end,
			OrderByComparator<FileEntry> obc)
		throws PortalException {

		Repository repository = getRepository(groupId);

		return repository.getRepositoryFileEntries(
			userId, rootFolderId, start, end, obc);
	}

	@Override
	public List<FileEntry> getGroupFileEntries(
			long groupId, long userId, long rootFolderId, String[] mimeTypes,
			int status, int start, int end, OrderByComparator<FileEntry> obc)
		throws PortalException {

		Repository repository = getRepository(groupId);

		return repository.getRepositoryFileEntries(
			userId, rootFolderId, mimeTypes, status, start, end, obc);
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
	 */
	@Override
	public int getGroupFileEntriesCount(long groupId, long userId)
		throws PortalException {

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
	 */
	@Override
	public int getGroupFileEntriesCount(
			long groupId, long userId, long rootFolderId)
		throws PortalException {

		Repository repository = getRepository(groupId);

		return repository.getRepositoryFileEntriesCount(userId, rootFolderId);
	}

	@Override
	public int getGroupFileEntriesCount(
			long groupId, long userId, long rootFolderId, String[] mimeTypes,
			int status)
		throws PortalException {

		Repository repository = getRepository(groupId);

		return repository.getRepositoryFileEntriesCount(
			userId, rootFolderId, mimeTypes, status);
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
	 */
	@Override
	public List<Folder> getMountFolders(long repositoryId, long parentFolderId)
		throws PortalException {

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
	 */
	@Override
	public List<Folder> getMountFolders(
			long repositoryId, long parentFolderId, int start, int end)
		throws PortalException {

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
	 */
	@Override
	public List<Folder> getMountFolders(
			long repositoryId, long parentFolderId, int start, int end,
			OrderByComparator<Folder> obc)
		throws PortalException {

		Repository repository = getRepository(repositoryId);

		return repository.getMountFolders(parentFolderId, start, end, obc);
	}

	/**
	 * Returns the number of immediate subfolders of the parent folder that are
	 * used for mounting third-party repositories. This method is only supported
	 * by the Liferay repository.
	 *
	 * @param  repositoryId the primary key of the repository
	 * @param  parentFolderId the primary key of the parent folder
	 * @return the number of folders of the parent folder that are used for
	 *         mounting third-party repositories
	 * @throws PortalException if the repository or parent folder could not be
	 *         found
	 */
	@Override
	public int getMountFoldersCount(long repositoryId, long parentFolderId)
		throws PortalException {

		Repository repository = getRepository(repositoryId);

		return repository.getMountFoldersCount(parentFolderId);
	}

	@Override
	public void getSubfolderIds(
			long repositoryId, List<Long> folderIds, long folderId)
		throws PortalException {

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
	 */
	@Override
	public List<Long> getSubfolderIds(long repositoryId, long folderId)
		throws PortalException {

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
	 */
	@Override
	public List<Long> getSubfolderIds(
			long repositoryId, long folderId, boolean recurse)
		throws PortalException {

		Repository repository = getRepository(repositoryId);

		return repository.getSubfolderIds(folderId, recurse);
	}

	/**
	 * Returns all the temporary file entry names.
	 *
	 * @param  groupId the primary key of the group
	 * @param  folderId the primary key of the folder where the file entry will
	 *         eventually reside
	 * @param  folderName the temporary folder's name
	 * @return the temporary file entry names
	 * @throws PortalException if the folder was invalid
	 * @see    #addTempFileEntry(long, long, String, String, File, String)
	 * @see    com.liferay.portal.kernel.util.TempFileEntryUtil
	 */
	@Override
	public String[] getTempFileNames(
			long groupId, long folderId, String folderName)
		throws PortalException {

		DLFolderPermission.check(
			getPermissionChecker(), groupId, folderId, ActionKeys.ADD_DOCUMENT);

		return TempFileEntryUtil.getTempFileNames(
			groupId, getUserId(), folderName);
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #checkOutFileEntry(long,
	 *             ServiceContext)}
	 */
	@Deprecated
	@Override
	public Lock lockFileEntry(long fileEntryId) throws PortalException {
		checkOutFileEntry(fileEntryId, new ServiceContext());

		FileEntry fileEntry = getFileEntry(fileEntryId);

		return fileEntry.getLock();
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #checkOutFileEntry(long,
	 *             String, long, ServiceContext)}
	 */
	@Deprecated
	@Override
	public Lock lockFileEntry(
			long fileEntryId, String owner, long expirationTime)
		throws PortalException {

		FileEntry fileEntry = checkOutFileEntry(
			fileEntryId, owner, expirationTime, new ServiceContext());

		return fileEntry.getLock();
	}

	/**
	 * Locks the folder. This method is primarily used by WebDAV.
	 *
	 * @param  repositoryId the primary key of the repository
	 * @param  folderId the primary key of the folder
	 * @return the lock object
	 * @throws PortalException if the repository or folder could not be found
	 */
	@Override
	public Lock lockFolder(long repositoryId, long folderId)
		throws PortalException {

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
	 */
	@Override
	public Lock lockFolder(
			long repositoryId, long folderId, String owner, boolean inheritable,
			long expirationTime)
		throws PortalException {

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
	 */
	@Override
	public FileEntry moveFileEntry(
			long fileEntryId, long newFolderId, ServiceContext serviceContext)
		throws PortalException {

		Repository fromRepository = getFileEntryRepository(fileEntryId);
		Repository toRepository = getFolderRepository(
			newFolderId, serviceContext.getScopeGroupId());

		if (newFolderId != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			Folder toFolder = toRepository.getFolder(newFolderId);

			if (toFolder.isMountPoint()) {
				toRepository = getRepository(toFolder.getRepositoryId());
			}
		}

		if (fromRepository.getRepositoryId() ==
				toRepository.getRepositoryId()) {

			// Move file entries within repository

			FileEntry fileEntry = fromRepository.moveFileEntry(
				fileEntryId, newFolderId, serviceContext);

			return fileEntry;
		}

		// Move file entries between repositories

		return moveFileEntries(
			fileEntryId, newFolderId, fromRepository, toRepository,
			serviceContext);
	}

	/**
	 * Moves the file entry from a trashed folder to the new folder.
	 *
	 * @param  fileEntryId the primary key of the file entry
	 * @param  newFolderId the primary key of the new folder
	 * @param  serviceContext the service context to be applied
	 * @return the file entry
	 * @throws PortalException if the file entry or the new folder could not be
	 *         found
	 */
	@Override
	public FileEntry moveFileEntryFromTrash(
			long fileEntryId, long newFolderId, ServiceContext serviceContext)
		throws PortalException {

		Repository repository = getFileEntryRepository(fileEntryId);

		if (!repository.isCapabilityProvided(TrashCapability.class)) {
			throw new InvalidRepositoryException(
				"Repository " + repository.getRepositoryId() +
					" does not support trash operations");
		}

		FileEntry fileEntry = repository.getFileEntry(fileEntryId);

		DLFileEntryPermission.check(
			getPermissionChecker(), fileEntry, ActionKeys.UPDATE);

		TrashCapability trashCapability = repository.getCapability(
			TrashCapability.class);

		Folder destinationFolder = repository.getFolder(newFolderId);

		return trashCapability.moveFileEntryFromTrash(
			getUserId(), fileEntry, destinationFolder, serviceContext);
	}

	/**
	 * Moves the file entry with the primary key to the trash portlet.
	 *
	 * @param  fileEntryId the primary key of the file entry
	 * @return the file entry
	 * @throws PortalException if the file entry could not be found
	 */
	@Override
	public FileEntry moveFileEntryToTrash(long fileEntryId)
		throws PortalException {

		Repository repository = getFileEntryRepository(fileEntryId);

		if (!repository.isCapabilityProvided(TrashCapability.class)) {
			throw new InvalidRepositoryException(
				"Repository " + repository.getRepositoryId() +
					" does not support trash operations");
		}

		FileEntry fileEntry = repository.getFileEntry(fileEntryId);

		DLFileEntryPermission.check(
			getPermissionChecker(), fileEntry, ActionKeys.DELETE);

		TrashCapability trashCapability = repository.getCapability(
			TrashCapability.class);

		return trashCapability.moveFileEntryToTrash(getUserId(), fileEntry);
	}

	/**
	 * Moves the file shortcut from a trashed folder to the new folder.
	 *
	 * @param  fileShortcutId the primary key of the file shortcut
	 * @param  newFolderId the primary key of the new folder
	 * @param  serviceContext the service context to be applied
	 * @return the file shortcut
	 * @throws PortalException if the file entry or the new folder could not be
	 *         found
	 */
	@Override
	public DLFileShortcut moveFileShortcutFromTrash(
			long fileShortcutId, long newFolderId,
			ServiceContext serviceContext)
		throws PortalException {

		DLFileShortcut fileShortcut = getFileShortcut(fileShortcutId);

		DLFileShortcutPermission.check(
			getPermissionChecker(), fileShortcut, ActionKeys.UPDATE);

		return dlAppHelperLocalService.moveFileShortcutFromTrash(
			getUserId(), fileShortcut, newFolderId, serviceContext);
	}

	/**
	 * Moves the file shortcut with the primary key to the trash portlet.
	 *
	 * @param  fileShortcutId the primary key of the file shortcut
	 * @return the file shortcut
	 * @throws PortalException if the file shortcut could not be found
	 */
	@Override
	public DLFileShortcut moveFileShortcutToTrash(long fileShortcutId)
		throws PortalException {

		DLFileShortcut fileShortcut = getFileShortcut(fileShortcutId);

		DLFileShortcutPermission.check(
			getPermissionChecker(), fileShortcut, ActionKeys.DELETE);

		return dlAppHelperLocalService.moveFileShortcutToTrash(
			getUserId(), fileShortcut);
	}

	/**
	 * Moves the folder to the new parent folder with the primary key.
	 *
	 * @param  folderId the primary key of the folder
	 * @param  parentFolderId the primary key of the new parent folder
	 * @param  serviceContext the service context to be applied
	 * @return the file entry
	 * @throws PortalException if the folder could not be found
	 */
	@Override
	public Folder moveFolder(
			long folderId, long parentFolderId, ServiceContext serviceContext)
		throws PortalException {

		Repository fromRepository = getFolderRepository(folderId);
		Repository toRepository = getFolderRepository(
			parentFolderId, serviceContext.getScopeGroupId());

		if (parentFolderId != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			Folder toFolder = toRepository.getFolder(parentFolderId);

			if (toFolder.isMountPoint()) {
				toRepository = getRepository(toFolder.getRepositoryId());
			}
		}

		Folder folder = null;

		if (fromRepository.getRepositoryId() ==
				toRepository.getRepositoryId()) {

			// Move file entries within repository

			folder = fromRepository.moveFolder(
				folderId, parentFolderId, serviceContext);
		}
		else {

			// Move file entries between repositories

			folder = moveFolders(
				folderId, parentFolderId, fromRepository, toRepository,
				serviceContext);
		}

		return folder;
	}

	/**
	 * Moves the folder with the primary key from the trash portlet to the new
	 * parent folder with the primary key.
	 *
	 * @param  folderId the primary key of the folder
	 * @param  parentFolderId the primary key of the new parent folder
	 * @param  serviceContext the service context to be applied
	 * @return the file entry
	 * @throws PortalException if the folder could not be found
	 */
	@Override
	public Folder moveFolderFromTrash(
			long folderId, long parentFolderId, ServiceContext serviceContext)
		throws PortalException {

		Repository repository = getFolderRepository(folderId);

		if (!repository.isCapabilityProvided(TrashCapability.class)) {
			throw new InvalidRepositoryException(
				"Repository " + repository.getRepositoryId() +
					" does not support trash operations");
		}

		Folder folder = repository.getFolder(folderId);

		DLFolderPermission.check(
			getPermissionChecker(), folder, ActionKeys.UPDATE);

		TrashCapability trashCapability = repository.getCapability(
			TrashCapability.class);

		Folder destinationFolder = repository.getFolder(parentFolderId);

		return trashCapability.moveFolderFromTrash(
			getUserId(), folder, destinationFolder, serviceContext);
	}

	/**
	 * Moves the folder with the primary key to the trash portlet.
	 *
	 * @param  folderId the primary key of the folder
	 * @return the file entry
	 * @throws PortalException if the folder could not be found
	 */
	@Override
	public Folder moveFolderToTrash(long folderId) throws PortalException {
		Repository repository = getFolderRepository(folderId);

		if (!repository.isCapabilityProvided(TrashCapability.class)) {
			throw new InvalidRepositoryException(
				"Repository " + repository.getRepositoryId() +
					" does not support trash operations");
		}

		Folder folder = repository.getFolder(folderId);

		DLFolderPermission.check(
			getPermissionChecker(), folder, ActionKeys.DELETE);

		TrashCapability trashCapability = repository.getCapability(
			TrashCapability.class);

		return trashCapability.moveFolderToTrash(getUserId(), folder);
	}

	/**
	 * Refreshes the lock for the file entry. This method is primarily used by
	 * WebDAV.
	 *
	 * @param  lockUuid the lock's UUID
	 * @param  companyId the primary key of the file entry's company
	 * @param  expirationTime the time in milliseconds before the lock expires.
	 *         If the value is <code>0</code>, the default expiration time will
	 *         be used from <code>portal.properties>.
	 * @return the lock object
	 * @throws PortalException if the file entry or lock could not be found
	 */
	@Override
	public Lock refreshFileEntryLock(
			String lockUuid, long companyId, long expirationTime)
		throws PortalException {

		Lock lock = lockLocalService.getLockByUuidAndCompanyId(
			lockUuid, companyId);

		long fileEntryId = GetterUtil.getLong(lock.getKey());

		Repository repository = getFileEntryRepository(fileEntryId);

		return repository.refreshFileEntryLock(
			lockUuid, companyId, expirationTime);
	}

	/**
	 * Refreshes the lock for the folder. This method is primarily used by
	 * WebDAV.
	 *
	 * @param  lockUuid the lock's UUID
	 * @param  companyId the primary key of the file entry's company
	 * @param  expirationTime the time in milliseconds before the lock expires.
	 *         If the value is <code>0</code>, the default expiration time will
	 *         be used from <code>portal.properties>.
	 * @return the lock object
	 * @throws PortalException if the folder or lock could not be found
	 */
	@Override
	public Lock refreshFolderLock(
			String lockUuid, long companyId, long expirationTime)
		throws PortalException {

		Lock lock = lockLocalService.getLockByUuidAndCompanyId(
			lockUuid, companyId);

		long folderId = GetterUtil.getLong(lock.getKey());

		Repository repository = getFolderRepository(folderId);

		return repository.refreshFolderLock(
			lockUuid, companyId, expirationTime);
	}

	/**
	 * Restores the file entry with the primary key from the trash portlet.
	 *
	 * @param  fileEntryId the primary key of the file entry
	 * @throws PortalException if the file entry could not be found
	 */
	@Override
	public void restoreFileEntryFromTrash(long fileEntryId)
		throws PortalException {

		Repository repository = getFileEntryRepository(fileEntryId);

		if (!repository.isCapabilityProvided(TrashCapability.class)) {
			throw new InvalidRepositoryException(
				"Repository " + repository.getRepositoryId() +
					" does not support trash operations");
		}

		FileEntry fileEntry = repository.getFileEntry(fileEntryId);

		DLFileEntryPermission.check(
			getPermissionChecker(), fileEntry, ActionKeys.DELETE);

		TrashCapability trashCapability = repository.getCapability(
			TrashCapability.class);

		trashCapability.restoreFileEntryFromTrash(getUserId(), fileEntry);
	}

	/**
	 * Restores the file shortcut with the primary key from the trash portlet.
	 *
	 * @param  fileShortcutId the primary key of the file shortcut
	 * @throws PortalException if the file shortcut could not be found
	 */
	@Override
	public void restoreFileShortcutFromTrash(long fileShortcutId)
		throws PortalException {

		DLFileShortcut fileShortcut = getFileShortcut(fileShortcutId);

		DLFileShortcutPermission.check(
			getPermissionChecker(), fileShortcut, ActionKeys.DELETE);

		dlAppHelperLocalService.restoreFileShortcutFromTrash(
			getUserId(), fileShortcut);
	}

	/**
	 * Restores the folder with the primary key from the trash portlet.
	 *
	 * @param  folderId the primary key of the folder
	 * @throws PortalException if the folder could not be found
	 */
	@Override
	public void restoreFolderFromTrash(long folderId) throws PortalException {
		Repository repository = getFolderRepository(folderId);

		if (!repository.isCapabilityProvided(TrashCapability.class)) {
			throw new InvalidRepositoryException(
				"Repository " + repository.getRepositoryId() +
					" does not support trash operations");
		}

		Folder folder = repository.getFolder(folderId);

		DLFolderPermission.check(
			getPermissionChecker(), folder, ActionKeys.DELETE);

		TrashCapability trashCapability = repository.getCapability(
			TrashCapability.class);

		trashCapability.restoreFolderFromTrash(getUserId(), folder);
	}

	/**
	 * Reverts the file entry to a previous version. A new version will be
	 * created based on the previous version and metadata.
	 *
	 * @param  fileEntryId the primary key of the file entry
	 * @param  version the version to revert back to
	 * @param  serviceContext the service context to be applied
	 * @throws PortalException if the file entry or version could not be found
	 */
	@Override
	public void revertFileEntry(
			long fileEntryId, String version, ServiceContext serviceContext)
		throws PortalException {

		Repository repository = getFileEntryRepository(fileEntryId);

		repository.revertFileEntry(fileEntryId, version, serviceContext);

		FileEntry fileEntry = getFileEntry(fileEntryId);

		dlAppHelperLocalService.updateFileEntry(
			getUserId(), fileEntry, fileEntry.getFileVersion(version),
			fileEntry.getFileVersion(), serviceContext);
	}

	@Override
	public Hits search(
			long repositoryId, long creatorUserId, int status, int start,
			int end)
		throws PortalException {

		Repository repository = getRepository(repositoryId);

		return repository.search(creatorUserId, status, start, end);
	}

	@Override
	public Hits search(
			long repositoryId, long creatorUserId, long folderId,
			String[] mimeTypes, int status, int start, int end)
		throws PortalException {

		Repository repository = getRepository(repositoryId);

		return repository.search(
			creatorUserId, folderId, mimeTypes, status, start, end);
	}

	@Override
	public Hits search(long repositoryId, SearchContext searchContext)
		throws SearchException {

		try {
			Repository repository = getRepository(repositoryId);

			return repository.search(searchContext);
		}
		catch (Exception e) {
			throw new SearchException(e);
		}
	}

	@Override
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
	 * Subscribe the user to changes in documents of the file entry type. This
	 * method is only supported by the Liferay repository.
	 *
	 * @param  groupId the primary key of the file entry type's group
	 * @param  fileEntryTypeId the primary key of the file entry type
	 * @throws PortalException if the user or group could not be found, or if
	 *         subscribing was not permissible
	 */
	@Override
	public void subscribeFileEntryType(long groupId, long fileEntryTypeId)
		throws PortalException {

		DLPermission.check(
			getPermissionChecker(), groupId, ActionKeys.SUBSCRIBE);

		dlAppLocalService.subscribeFileEntryType(
			getUserId(), groupId, fileEntryTypeId);
	}

	/**
	 * Subscribe the user to document changes in the folder. This method is only
	 * supported by the Liferay repository.
	 *
	 * @param  groupId the primary key of the folder's group
	 * @param  folderId the primary key of the folder
	 * @throws PortalException if the user or group could not be found, or if
	 *         subscribing was not permissible
	 */
	@Override
	public void subscribeFolder(long groupId, long folderId)
		throws PortalException {

		DLFolderPermission.check(
			getPermissionChecker(), groupId, folderId, ActionKeys.SUBSCRIBE);

		dlAppLocalService.subscribeFolder(getUserId(), groupId, folderId);
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #checkInFileEntry(long,
	 *             boolean, String, ServiceContext)}.
	 */
	@Deprecated
	@Override
	public void unlockFileEntry(long fileEntryId) throws PortalException {
		checkInFileEntry(
			fileEntryId, false, StringPool.BLANK, new ServiceContext());
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #checkInFileEntry(long,
	 *             String)}.
	 */
	@Deprecated
	@Override
	public void unlockFileEntry(long fileEntryId, String lockUuid)
		throws PortalException {

		checkInFileEntry(fileEntryId, lockUuid);
	}

	/**
	 * Unlocks the folder. This method is primarily used by WebDAV.
	 *
	 * @param  repositoryId the primary key of the repository
	 * @param  folderId the primary key of the folder
	 * @param  lockUuid the lock's UUID
	 * @throws PortalException if the repository or folder could not be found
	 */
	@Override
	public void unlockFolder(long repositoryId, long folderId, String lockUuid)
		throws PortalException {

		Repository repository = getRepository(repositoryId);

		repository.unlockFolder(folderId, lockUuid);
	}

	/**
	 * Unlocks the folder. This method is primarily used by WebDAV.
	 *
	 * @param  repositoryId the primary key of the repository
	 * @param  parentFolderId the primary key of the parent folder
	 * @param  name the folder's name
	 * @param  lockUuid the lock's UUID
	 * @throws PortalException if the repository or folder could not be found
	 */
	@Override
	public void unlockFolder(
			long repositoryId, long parentFolderId, String name,
			String lockUuid)
		throws PortalException {

		Repository repository = getRepository(repositoryId);

		repository.unlockFolder(parentFolderId, name, lockUuid);
	}

	/**
	 * Unsubscribe the user from changes in documents of the file entry type.
	 * This method is only supported by the Liferay repository.
	 *
	 * @param  groupId the primary key of the file entry type's group
	 * @param  fileEntryTypeId the primary key of the file entry type
	 * @throws PortalException if the user or group could not be found, or if
	 *         unsubscribing was not permissible
	 */
	@Override
	public void unsubscribeFileEntryType(long groupId, long fileEntryTypeId)
		throws PortalException {

		DLPermission.check(
			getPermissionChecker(), groupId, ActionKeys.SUBSCRIBE);

		dlAppLocalService.unsubscribeFileEntryType(
			getUserId(), groupId, fileEntryTypeId);
	}

	/**
	 * Unsubscribe the user from document changes in the folder. This method is
	 * only supported by the Liferay repository.
	 *
	 * @param  groupId the primary key of the folder's group
	 * @param  folderId the primary key of the folder
	 * @throws PortalException if the user or group could not be found, or if
	 *         unsubscribing was not permissible
	 */
	@Override
	public void unsubscribeFolder(long groupId, long folderId)
		throws PortalException {

		DLFolderPermission.check(
			getPermissionChecker(), groupId, folderId, ActionKeys.SUBSCRIBE);

		dlAppLocalService.unsubscribeFolder(getUserId(), groupId, folderId);
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
	 *         <code>null</code></code>)
	 * @param  description the file's new description
	 * @param  changeLog the file's version change log (optionally
	 *         <code>null</code>)
	 * @param  majorVersion whether the new file version is a major version
	 * @param  bytes the file's data (optionally <code>null</code>)
	 * @param  serviceContext the service context to be applied. Can set the
	 *         asset category IDs, asset tag names, and expando bridge
	 *         attributes for the file entry. In a Liferay repository, it may
	 *         include:  <ul> <li> fileEntryTypeId - ID for a custom file entry
	 *         type </li> <li> fieldsMap - mapping for fields associated with a
	 *         custom file entry type </li> </ul>
	 * @return the file entry
	 * @throws PortalException if the file entry could not be found
	 */
	@Override
	public FileEntry updateFileEntry(
			long fileEntryId, String sourceFileName, String mimeType,
			String title, String description, String changeLog,
			boolean majorVersion, byte[] bytes, ServiceContext serviceContext)
		throws PortalException {

		File file = null;

		try {
			if (ArrayUtil.isNotEmpty(bytes)) {
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
	 * @param  fileEntryId the primary key of the file entry
	 * @param  sourceFileName the original file's name (optionally
	 *         <code>null</code>)
	 * @param  mimeType the file's MIME type (optionally <code>null</code>)
	 * @param  title the new name to be assigned to the file (optionally <code>
	 *         <code>null</code></code>)
	 * @param  description the file's new description
	 * @param  changeLog the file's version change log (optionally
	 *         <code>null</code>)
	 * @param  majorVersion whether the new file version is a major version
	 * @param  file the file's data (optionally <code>null</code>)
	 * @param  serviceContext the service context to be applied. Can set the
	 *         asset category IDs, asset tag names, and expando bridge
	 *         attributes for the file entry. In a Liferay repository, it may
	 *         include:  <ul> <li> fileEntryTypeId - ID for a custom file entry
	 *         type </li> <li> fieldsMap - mapping for fields associated with a
	 *         custom file entry type </li> </ul>
	 * @return the file entry
	 * @throws PortalException if the file entry could not be found
	 */
	@Override
	public FileEntry updateFileEntry(
			long fileEntryId, String sourceFileName, String mimeType,
			String title, String description, String changeLog,
			boolean majorVersion, File file, ServiceContext serviceContext)
		throws PortalException {

		if ((file == null) || !file.exists() || (file.length() == 0)) {
			return updateFileEntry(
				fileEntryId, sourceFileName, mimeType, title, description,
				changeLog, majorVersion, null, 0, serviceContext);
		}

		mimeType = DLAppUtil.getMimeType(sourceFileName, mimeType, title, file);

		Repository repository = getFileEntryRepository(fileEntryId);

		FileEntry fileEntry = repository.updateFileEntry(
			getUserId(), fileEntryId, sourceFileName, mimeType, title,
			description, changeLog, majorVersion, file, serviceContext);

		DLProcessorRegistryUtil.cleanUp(fileEntry.getLatestFileVersion());

		dlAppHelperLocalService.updateFileEntry(
			getUserId(), fileEntry, null, fileEntry.getFileVersion(),
			serviceContext);

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
	 *         <code>null</code></code>)
	 * @param  description the file's new description
	 * @param  changeLog the file's version change log (optionally
	 *         <code>null</code>)
	 * @param  majorVersion whether the new file version is a major version
	 * @param  is the file's data (optionally <code>null</code>)
	 * @param  size the file's size (optionally <code>0</code>)
	 * @param  serviceContext the service context to be applied. Can set the
	 *         asset category IDs, asset tag names, and expando bridge
	 *         attributes for the file entry. In a Liferay repository, it may
	 *         include:  <ul> <li> fileEntryTypeId - ID for a custom file entry
	 *         type </li> <li> fieldsMap - mapping for fields associated with a
	 *         custom file entry type </li> </ul>
	 * @return the file entry
	 * @throws PortalException if the file entry could not be found
	 */
	@Override
	public FileEntry updateFileEntry(
			long fileEntryId, String sourceFileName, String mimeType,
			String title, String description, String changeLog,
			boolean majorVersion, InputStream is, long size,
			ServiceContext serviceContext)
		throws PortalException {

		if (Validator.isNull(mimeType) ||
			mimeType.equals(ContentTypes.APPLICATION_OCTET_STREAM)) {

			String extension = DLAppUtil.getExtension(title, sourceFileName);

			if (size == 0) {
				mimeType = MimeTypesUtil.getExtensionContentType(extension);
			}
			else {
				File file = null;

				try {
					file = FileUtil.createTempFile(is);

					return updateFileEntry(
						fileEntryId, sourceFileName, mimeType, title,
						description, changeLog, majorVersion, file,
						serviceContext);
				}
				catch (IOException ioe) {
					throw new SystemException(
						"Unable to write temporary file", ioe);
				}
				finally {
					FileUtil.delete(file);
				}
			}
		}

		Repository repository = getFileEntryRepository(fileEntryId);

		FileEntry oldFileEntry = repository.getFileEntry(fileEntryId);

		FileVersion oldFileVersion = oldFileEntry.getFileVersion();

		FileEntry fileEntry = repository.updateFileEntry(
			getUserId(), fileEntryId, sourceFileName, mimeType, title,
			description, changeLog, majorVersion, is, size, serviceContext);

		if (is != null) {
			DLProcessorRegistryUtil.cleanUp(fileEntry.getLatestFileVersion());

			oldFileVersion = null;
		}

		dlAppHelperLocalService.updateFileEntry(
			getUserId(), fileEntry, oldFileVersion, fileEntry.getFileVersion(),
			serviceContext);

		return fileEntry;
	}

	@Override
	public FileEntry updateFileEntryAndCheckIn(
			long fileEntryId, String sourceFileName, String mimeType,
			String title, String description, String changeLog,
			boolean majorVersion, File file, ServiceContext serviceContext)
		throws PortalException {

		if ((file == null) || !file.exists() || (file.length() == 0)) {
			return updateFileEntryAndCheckIn(
				fileEntryId, sourceFileName, mimeType, title, description,
				changeLog, majorVersion, null, 0, serviceContext);
		}

		Repository repository = getFileEntryRepository(fileEntryId);

		FileEntry fileEntry = repository.updateFileEntry(
			getUserId(), fileEntryId, sourceFileName, mimeType, title,
			description, changeLog, majorVersion, file, serviceContext);

		DLProcessorRegistryUtil.cleanUp(fileEntry.getLatestFileVersion());

		repository.checkInFileEntry(
			fileEntryId, majorVersion, changeLog, serviceContext);

		dlAppHelperLocalService.updateFileEntry(
			getUserId(), fileEntry, null, fileEntry.getFileVersion(),
			serviceContext);

		return fileEntry;
	}

	@Override
	public FileEntry updateFileEntryAndCheckIn(
			long fileEntryId, String sourceFileName, String mimeType,
			String title, String description, String changeLog,
			boolean majorVersion, InputStream is, long size,
			ServiceContext serviceContext)
		throws PortalException {

		Repository repository = getFileEntryRepository(fileEntryId);

		FileEntry oldFileEntry = repository.getFileEntry(fileEntryId);

		FileVersion oldFileVersion = oldFileEntry.getFileVersion();

		FileEntry fileEntry = repository.updateFileEntry(
			getUserId(), fileEntryId, sourceFileName, mimeType, title,
			description, changeLog, majorVersion, is, size, serviceContext);

		if (is != null) {
			DLProcessorRegistryUtil.cleanUp(fileEntry.getLatestFileVersion());

			oldFileVersion = null;
		}

		repository.checkInFileEntry(
			fileEntryId, majorVersion, changeLog, serviceContext);

		dlAppHelperLocalService.updateFileEntry(
			getUserId(), fileEntry, oldFileVersion, fileEntry.getFileVersion(),
			serviceContext);

		return fileEntry;
	}

	/**
	 * Updates a file shortcut to the existing file entry. This method is only
	 * supported by the Liferay repository.
	 *
	 * @param  fileShortcutId the primary key of the file shortcut
	 * @param  folderId the primary key of the file shortcut's parent folder
	 * @param  toFileEntryId the primary key of the file shortcut's file entry
	 * @param  serviceContext the service context to be applied. Can set the
	 *         asset category IDs, asset tag names, and expando bridge
	 *         attributes for the file entry.
	 * @return the file shortcut
	 * @throws PortalException if the file shortcut, folder, or file entry could
	 *         not be found
	 */
	@Override
	public DLFileShortcut updateFileShortcut(
			long fileShortcutId, long folderId, long toFileEntryId,
			ServiceContext serviceContext)
		throws PortalException {

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
	 *         repository, it may include:  <ul> <li> defaultFileEntryTypeId -
	 *         the file entry type to default all Liferay file entries to </li>
	 *         <li> dlFileEntryTypesSearchContainerPrimaryKeys - a
	 *         comma-delimited list of file entry type primary keys allowed in
	 *         the given folder and all descendants </li> <li>
	 *         overrideFileEntryTypes - boolean specifying whether to override
	 *         ancestral folder's restriction of file entry types allowed </li>
	 *         <li> workflowDefinitionXYZ - the workflow definition name
	 *         specified per file entry type. The parameter name must be the
	 *         string <code>workflowDefinition</code> appended by the <code>
	 *         fileEntryTypeId</code> (optionally <code>0</code>). </li> </ul>
	 * @return the folder
	 * @throws PortalException if the current or new parent folder could not be
	 *         found or if the new parent folder's information was invalid
	 */
	@Override
	public Folder updateFolder(
			long folderId, String name, String description,
			ServiceContext serviceContext)
		throws PortalException {

		Repository repository = null;

		if (folderId == DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			repository = getRepository(serviceContext.getScopeGroupId());
		}
		else {
			repository = getFolderRepository(folderId);
		}

		Folder folder = repository.updateFolder(
			folderId, name, description, serviceContext);

		if (folderId != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			dlAppHelperLocalService.updateFolder(
				getUserId(), folder, serviceContext);
		}

		return folder;
	}

	/**
	 * Returns <code>true</code> if the file entry is checked out. This method
	 * is primarily used by WebDAV.
	 *
	 * @param  repositoryId the primary key for the repository
	 * @param  fileEntryId the primary key for the file entry
	 * @param  lockUuid the lock's UUID
	 * @return <code>true</code> if the file entry is checked out;
	 *         <code>false</code> otherwise
	 * @throws PortalException if the file entry could not be found
	 */
	@Override
	public boolean verifyFileEntryCheckOut(
			long repositoryId, long fileEntryId, String lockUuid)
		throws PortalException {

		Repository repository = getRepository(repositoryId);

		return repository.verifyFileEntryCheckOut(fileEntryId, lockUuid);
	}

	@Override
	public boolean verifyFileEntryLock(
			long repositoryId, long fileEntryId, String lockUuid)
		throws PortalException {

		Repository repository = getRepository(repositoryId);

		return repository.verifyFileEntryLock(fileEntryId, lockUuid);
	}

	/**
	 * Returns <code>true</code> if the inheritable lock exists. This method is
	 * primarily used by WebDAV.
	 *
	 * @param  repositoryId the primary key for the repository
	 * @param  folderId the primary key for the folder
	 * @param  lockUuid the lock's UUID
	 * @return <code>true</code> if the inheritable lock exists;
	 *         <code>false</code> otherwise
	 * @throws PortalException if the folder could not be found
	 */
	@Override
	public boolean verifyInheritableLock(
			long repositoryId, long folderId, String lockUuid)
		throws PortalException {

		Repository repository = getRepository(repositoryId);

		return repository.verifyInheritableLock(folderId, lockUuid);
	}

	protected FileEntry copyFileEntry(
			Repository toRepository, FileEntry fileEntry, long newFolderId,
			ServiceContext serviceContext)
		throws PortalException {

		List<FileVersion> fileVersions = fileEntry.getFileVersions(
			WorkflowConstants.STATUS_ANY);

		FileVersion latestFileVersion = fileVersions.get(
			fileVersions.size() - 1);

		FileEntry destinationFileEntry = toRepository.addFileEntry(
			getUserId(), newFolderId, fileEntry.getTitle(),
			latestFileVersion.getMimeType(), latestFileVersion.getTitle(),
			latestFileVersion.getDescription(), StringPool.BLANK,
			latestFileVersion.getContentStream(false),
			latestFileVersion.getSize(), serviceContext);

		FileVersion oldDestinationFileVersion =
			destinationFileEntry.getFileVersion();

		dlAppHelperLocalService.addFileEntry(
			getUserId(), destinationFileEntry, oldDestinationFileVersion,
			serviceContext);

		for (int i = fileVersions.size() - 2; i >= 0; i--) {
			FileVersion fileVersion = fileVersions.get(i);

			FileVersion previousFileVersion = fileVersions.get(i + 1);

			try {
				destinationFileEntry = toRepository.updateFileEntry(
					getUserId(), destinationFileEntry.getFileEntryId(),
					fileVersion.getTitle(), fileVersion.getMimeType(),
					fileVersion.getTitle(), fileVersion.getDescription(),
					StringPool.BLANK,
					DLAppUtil.isMajorVersion(previousFileVersion, fileVersion),
					fileVersion.getContentStream(false), fileVersion.getSize(),
					serviceContext);

				FileVersion destinationFileVersion =
					destinationFileEntry.getFileVersion();

				dlAppHelperLocalService.updateFileEntry(
					getUserId(), destinationFileEntry,
					oldDestinationFileVersion, destinationFileVersion,
					serviceContext);

				oldDestinationFileVersion = destinationFileVersion;
			}
			catch (PortalException pe) {
				toRepository.deleteFileEntry(
					destinationFileEntry.getFileEntryId());

				throw pe;
			}
		}

		return destinationFileEntry;
	}

	protected void copyFolder(
			Repository repository, Folder srcFolder, Folder destFolder,
			ServiceContext serviceContext)
		throws PortalException {

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
						getUserId(), curDestFolder.getGroupId(),
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

				dlAppHelperLocalService.addFolder(
					getUserId(), destSubfolder, serviceContext);

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

		TransactionCommitCallbackRegistryUtil.registerCallback(
			new Callable<Void>() {

				@Override
				public Void call() throws Exception {
					for (FileEntry fileEntry : fileEntries) {
						DLProcessorRegistryUtil.trigger(fileEntry, null);
					}

					return null;
				}

			});
	}

	protected void deleteFileEntry(
			long oldFileEntryId, long newFileEntryId, Repository fromRepository,
			Repository toRepository)
		throws PortalException {

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

	protected Repository getFileEntryRepository(long fileEntryId)
		throws PortalException {

		try {
			return repositoryService.getRepositoryImpl(0, fileEntryId, 0);
		}
		catch (InvalidRepositoryIdException irie) {
			StringBundler sb = new StringBundler(3);

			sb.append("No FileEntry exists with the key {fileEntryId=");
			sb.append(fileEntryId);
			sb.append("}");

			throw new NoSuchFileEntryException(sb.toString(), irie);
		}
	}

	protected Repository getFileVersionRepository(long fileVersionId)
		throws PortalException {

		try {
			return repositoryService.getRepositoryImpl(0, 0, fileVersionId);
		}
		catch (InvalidRepositoryIdException irie) {
			StringBundler sb = new StringBundler(3);

			sb.append("No FileVersion exists with the key {fileVersionId=");
			sb.append(fileVersionId);
			sb.append("}");

			throw new NoSuchFileVersionException(sb.toString(), irie);
		}
	}

	protected Repository getFolderRepository(long folderId)
		throws PortalException {

		try {
			return repositoryService.getRepositoryImpl(folderId, 0, 0);
		}
		catch (InvalidRepositoryIdException irie) {
			StringBundler sb = new StringBundler(3);

			sb.append("No Folder exists with the key {folderId=");
			sb.append(folderId);
			sb.append("}");

			throw new NoSuchFolderException(sb.toString(), irie);
		}
	}

	protected Repository getFolderRepository(long folderId, long groupId)
		throws PortalException {

		Repository repository = null;

		if (folderId == DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			repository = getRepository(groupId);
		}
		else {
			repository = getFolderRepository(folderId);
		}

		return repository;
	}

	protected Repository getRepository(long repositoryId)
		throws PortalException {

		try {
			return repositoryService.getRepositoryImpl(repositoryId);
		}
		catch (InvalidRepositoryIdException irie) {
			StringBundler sb = new StringBundler(3);

			sb.append("No Group exists with the key {repositoryId=");
			sb.append(repositoryId);
			sb.append("}");

			throw new NoSuchGroupException(sb.toString(), irie);
		}
	}

	protected FileEntry moveFileEntries(
			long fileEntryId, long newFolderId, Repository fromRepository,
			Repository toRepository, ServiceContext serviceContext)
		throws PortalException {

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
		throws PortalException {

		Folder folder = fromRepository.getFolder(folderId);

		Folder newFolder = toRepository.addFolder(
			parentFolderId, folder.getName(), folder.getDescription(),
			serviceContext);

		dlAppHelperLocalService.addFolder(
			getUserId(), newFolder, serviceContext);

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

			throw pe;
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