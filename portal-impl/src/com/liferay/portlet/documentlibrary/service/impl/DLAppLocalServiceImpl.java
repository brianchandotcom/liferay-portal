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

import com.liferay.document.library.kernel.exception.NoSuchFileEntryException;
import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.model.DLVersionNumberIncrease;
import com.liferay.document.library.kernel.service.DLAppHelperLocalService;
import com.liferay.document.library.kernel.service.DLFolderLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.NoSuchGroupException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Repository;
import com.liferay.portal.kernel.model.UserConstants;
import com.liferay.portal.kernel.repository.InvalidRepositoryIdException;
import com.liferay.portal.kernel.repository.LocalRepository;
import com.liferay.portal.kernel.repository.RepositoryProvider;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileShortcut;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.repository.model.RepositoryEntry;
import com.liferay.portal.kernel.service.RepositoryLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.persistence.RepositoryPersistence;
import com.liferay.portal.kernel.systemevent.SystemEventHierarchyEntryThreadLocal;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.repository.liferayrepository.model.LiferayFolder;
import com.liferay.portal.util.RepositoryUtil;
import com.liferay.portlet.documentlibrary.service.base.DLAppLocalServiceBaseImpl;
import com.liferay.portlet.documentlibrary.util.DLAppUtil;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import java.util.Date;
import java.util.List;

/**
 * Provides the local service for accessing, adding, deleting, moving,
 * subscription handling of, and updating document library file entries, file
 * ranks, and folders. All portlets should interact with the document library
 * through this class or through DLAppService, rather than through the
 * individual document library service classes.
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
 * com.liferay.portal.kernel.service.RepositoryLocalServiceUtil}.
 * </p>
 *
 * @author Alexander Chow
 * @author Mika Koivisto
 * @see    DLAppServiceImpl
 */
public class DLAppLocalServiceImpl extends DLAppLocalServiceBaseImpl {

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link
	 *             #addFileEntry(String, long, long, long, String, String,
	 *             byte[], Date, Date, ServiceContext)}
	 */
	@Deprecated
	@Override
	public FileEntry addFileEntry(
			long userId, long repositoryId, long folderId,
			String sourceFileName, String mimeType, byte[] bytes,
			ServiceContext serviceContext)
		throws PortalException {

		return addFileEntry(
			null, userId, repositoryId, folderId, sourceFileName, mimeType,
			sourceFileName, StringPool.BLANK, StringPool.BLANK, bytes, null,
			null, serviceContext);
	}

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
	 * @param      userId the primary key of the file entry's creator/owner
	 * @param      repositoryId the primary key of the file entry's repository
	 * @param      folderId the primary key of the file entry's parent folder
	 * @param      sourceFileName the original file's name
	 * @param      mimeType the file's MIME type
	 * @param      title the name to be assigned to the file (optionally
	 *             <code>null </code>)
	 * @param      description the file's description
	 * @param      changeLog the file's version change log
	 * @param      bytes the file's data (optionally <code>null</code>)
	 * @param      serviceContext the service context to be applied. Can set the
	 *             asset category IDs, asset tag names, and expando bridge
	 *             attributes for the file entry. In a Liferay repository, it
	 *             may include:  <ul> <li> fileEntryTypeId - ID for a custom
	 *             file entry type </li> <li> fieldsMap - mapping for fields
	 *             associated with a custom file entry type </li> </ul>
	 * @return     the file entry
	 * @throws     PortalException if a portal exception occurred
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link
	 *             #addFileEntry(String, long, long, long, String, String,
	 *             String, String, String, byte[], Date, Date, ServiceContext)}
	 */
	@Deprecated
	@Override
	public FileEntry addFileEntry(
			long userId, long repositoryId, long folderId,
			String sourceFileName, String mimeType, String title,
			String description, String changeLog, byte[] bytes,
			ServiceContext serviceContext)
		throws PortalException {

		return addFileEntry(
			null, userId, repositoryId, folderId, sourceFileName, mimeType,
			title, description, changeLog, bytes, null, null, serviceContext);
	}

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
	 * @param      userId the primary key of the file entry's creator/owner
	 * @param      repositoryId the primary key of the repository
	 * @param      folderId the primary key of the file entry's parent folder
	 * @param      sourceFileName the original file's name
	 * @param      mimeType the file's MIME type
	 * @param      title the name to be assigned to the file (optionally
	 *             <code>null </code>)
	 * @param      description the file's description
	 * @param      changeLog the file's version change log
	 * @param      file the file's data (optionally <code>null</code>)
	 * @param      serviceContext the service context to be applied. Can set the
	 *             asset category IDs, asset tag names, and expando bridge
	 *             attributes for the file entry. In a Liferay repository, it
	 *             may include:  <ul> <li> fileEntryTypeId - ID for a custom
	 *             file entry type </li> <li> fieldsMap - mapping for fields
	 *             associated with a custom file entry type </li> </ul>
	 * @return     the file entry
	 * @throws     PortalException if a portal exception occurred
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link
	 * 			   #addFileEntry(String, long, long, long, String, String,
	 * 			   String, String, String, File, Date, Date, ServiceContext)}
	 */
	@Deprecated
	@Override
	public FileEntry addFileEntry(
			long userId, long repositoryId, long folderId,
			String sourceFileName, String mimeType, String title,
			String description, String changeLog, File file,
			ServiceContext serviceContext)
		throws PortalException {

		return addFileEntry(
			null, userId, repositoryId, folderId, sourceFileName, mimeType,
			title, description, changeLog, file, null, null, serviceContext);
	}

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
	 * @param      userId the primary key of the file entry's creator/owner
	 * @param      repositoryId the primary key of the repository
	 * @param      folderId the primary key of the file entry's parent folder
	 * @param      sourceFileName the original file's name
	 * @param      mimeType the file's MIME type
	 * @param      title the name to be assigned to the file (optionally
	 *             <code>null </code>)
	 * @param      description the file's description
	 * @param      changeLog the file's version change log
	 * @param      inputStream the file's data (optionally <code>null</code>)
	 * @param      size the file's size (optionally <code>0</code>)
	 * @param      serviceContext the service context to be applied. Can set the
	 *             asset category IDs, asset tag names, and expando bridge
	 *             attributes for the file entry. In a Liferay repository, it
	 *             may include:  <ul> <li> fileEntryTypeId - ID for a custom
	 *             file entry type </li> <li> fieldsMap - mapping for fields
	 *             associated with a custom file entry type </li> </ul>
	 * @return     the file entry
	 * @throws     PortalException if a portal exception occurred
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link
	 * 			   #addFileEntry(String, long, long, long, String, String,
	 * 			   String, String, String, InputStream, long, Date, Date,
	 * 			   ServiceContext)}
	 */
	@Deprecated
	@Override
	public FileEntry addFileEntry(
			long userId, long repositoryId, long folderId,
			String sourceFileName, String mimeType, String title,
			String description, String changeLog, InputStream inputStream,
			long size, ServiceContext serviceContext)
		throws PortalException {

		return addFileEntry(
			null, userId, repositoryId, folderId, sourceFileName, mimeType,
			title, description, changeLog, inputStream, size, null, null,
			serviceContext);
	}

	@Override
	public FileEntry addFileEntry(
			String externalReferenceCode, long userId, long repositoryId,
			long folderId, String sourceFileName, String mimeType, byte[] bytes,
			Date expirationDate, Date reviewDate, ServiceContext serviceContext)
		throws PortalException {

		return addFileEntry(
			externalReferenceCode, userId, repositoryId, folderId,
			sourceFileName, mimeType, sourceFileName, StringPool.BLANK,
			StringPool.BLANK, bytes, expirationDate, reviewDate,
			serviceContext);
	}

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
	 * @param  externalReferenceCode the file entry's external reference code
	 * @param  userId the primary key of the file entry's creator/owner
	 * @param  repositoryId the primary key of the file entry's repository
	 * @param  folderId the primary key of the file entry's parent folder
	 * @param  sourceFileName the original file's name
	 * @param  mimeType the file's MIME type
	 * @param  title the name to be assigned to the file (optionally <code>null
	 *         </code>)
	 * @param  description the file's description
	 * @param  changeLog the file's version change log
	 * @param  bytes the file's data (optionally <code>null</code>)
	 * @param  expirationDate the file's expiration date (optionally <code>null
	 *                           </code>)
	 * @param  reviewDate the file's review Date (optionally <code>null</code>)
	 * @param  serviceContext the service context to be applied. Can set the
	 *         asset category IDs, asset tag names, and expando bridge
	 *         attributes for the file entry. In a Liferay repository, it may
	 *         include:  <ul> <li> fileEntryTypeId - ID for a custom file entry
	 *         type </li> <li> fieldsMap - mapping for fields associated with a
	 *         custom file entry type </li> </ul>
	 * @return the file entry
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public FileEntry addFileEntry(
			String externalReferenceCode, long userId, long repositoryId,
			long folderId, String sourceFileName, String mimeType, String title,
			String description, String changeLog, byte[] bytes,
			Date expirationDate, Date reviewDate, ServiceContext serviceContext)
		throws PortalException {

		File file = null;

		try {
			if (ArrayUtil.isNotEmpty(bytes)) {
				file = FileUtil.createTempFile(bytes);
			}

			return addFileEntry(
				externalReferenceCode, userId, repositoryId, folderId,
				sourceFileName, mimeType, title, description, changeLog, file,
				expirationDate, reviewDate, serviceContext);
		}
		catch (IOException ioException) {
			throw new SystemException(
				"Unable to write temporary file", ioException);
		}
		finally {
			FileUtil.delete(file);
		}
	}

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
	 * @param  externalReferenceCode the file entry's external reference code
	 * @param  userId the primary key of the file entry's creator/owner
	 * @param  repositoryId the primary key of the repository
	 * @param  folderId the primary key of the file entry's parent folder
	 * @param  sourceFileName the original file's name
	 * @param  mimeType the file's MIME type
	 * @param  title the name to be assigned to the file (optionally <code>null
	 *         </code>)
	 * @param  description the file's description
	 * @param  changeLog the file's version change log
	 * @param  file the file's data (optionally <code>null</code>)
	 * @param  expirationDate the file's expiration date (optionally <code>null</code>)
	 * @param  reviewDate the file's review Date (optionally <code>null</code>)
	 * @param  serviceContext the service context to be applied. Can set the
	 *         asset category IDs, asset tag names, and expando bridge
	 *         attributes for the file entry. In a Liferay repository, it may
	 *         include:  <ul> <li> fileEntryTypeId - ID for a custom file entry
	 *         type </li> <li> fieldsMap - mapping for fields associated with a
	 *         custom file entry type </li> </ul>
	 * @return the file entry
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public FileEntry addFileEntry(
			String externalReferenceCode, long userId, long repositoryId,
			long folderId, String sourceFileName, String mimeType, String title,
			String description, String changeLog, File file,
			Date expirationDate, Date reviewDate, ServiceContext serviceContext)
		throws PortalException {

		if ((file == null) || !file.exists() || (file.length() == 0)) {
			return addFileEntry(
				externalReferenceCode, userId, repositoryId, folderId,
				sourceFileName, mimeType, title, description, changeLog, null,
				0, expirationDate, reviewDate, serviceContext);
		}

		mimeType = DLAppUtil.getMimeType(sourceFileName, mimeType, title, file);

		LocalRepository localRepository = getLocalRepository(repositoryId);

		return localRepository.addFileEntry(
			externalReferenceCode, userId, folderId, sourceFileName, mimeType,
			title, title, description, changeLog, file, expirationDate,
			reviewDate, serviceContext);
	}

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
	 * @param  externalReferenceCode the file entry's external reference code
	 * @param  userId the primary key of the file entry's creator/owner
	 * @param  repositoryId the primary key of the repository
	 * @param  folderId the primary key of the file entry's parent folder
	 * @param  sourceFileName the original file's name
	 * @param  mimeType the file's MIME type
	 * @param  title the name to be assigned to the file (optionally <code>null
	 *         </code>)
	 * @param  description the file's description
	 * @param  changeLog the file's version change log
	 * @param  inputStream the file's data (optionally <code>null</code>)
	 * @param  size the file's size (optionally <code>0</code>)
	 * @param  expirationDate the file's expiration date (optionally <code>null
	 *                           </code>)
	 * @param  reviewDate the file's review Date (optionally <code>null</code>)
	 * @param  serviceContext the service context to be applied. Can set the
	 *         asset category IDs, asset tag names, and expando bridge
	 *         attributes for the file entry. In a Liferay repository, it may
	 *         include:  <ul> <li> fileEntryTypeId - ID for a custom file entry
	 *         type </li> <li> fieldsMap - mapping for fields associated with a
	 *         custom file entry type </li> </ul>
	 * @return the file entry
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public FileEntry addFileEntry(
			String externalReferenceCode, long userId, long repositoryId,
			long folderId, String sourceFileName, String mimeType, String title,
			String description, String changeLog, InputStream inputStream,
			long size, Date expirationDate, Date reviewDate,
			ServiceContext serviceContext)
		throws PortalException {

		if (inputStream == null) {
			inputStream = new UnsyncByteArrayInputStream(new byte[0]);
			size = 0;
		}

		if (Validator.isNull(mimeType) ||
			mimeType.equals(ContentTypes.APPLICATION_OCTET_STREAM)) {

			if (size == 0) {
				String extension = DLAppUtil.getExtension(
					title, sourceFileName);

				mimeType = MimeTypesUtil.getExtensionContentType(extension);
			}
			else {
				File file = null;

				try {
					file = FileUtil.createTempFile(inputStream);

					return addFileEntry(
						externalReferenceCode, userId, repositoryId, folderId,
						sourceFileName, mimeType, title, description, changeLog,
						file, expirationDate, reviewDate, serviceContext);
				}
				catch (IOException ioException) {
					throw new SystemException(
						"Unable to write temporary file", ioException);
				}
				finally {
					FileUtil.delete(file);
				}
			}
		}

		LocalRepository localRepository = getLocalRepository(repositoryId);

		return localRepository.addFileEntry(
			externalReferenceCode, userId, folderId, sourceFileName, mimeType,
			title, title, description, changeLog, inputStream, size,
			expirationDate, reviewDate, serviceContext);
	}

	/**
	 * Adds the file shortcut to the existing file entry. This method is only
	 * supported by the Liferay repository.
	 *
	 * @param  userId the primary key of the file shortcut's creator/owner
	 * @param  repositoryId the primary key of the repository
	 * @param  folderId the primary key of the file shortcut's parent folder
	 * @param  toFileEntryId the primary key of the file entry to point to
	 * @param  serviceContext the service context to be applied. Can set the
	 *         asset category IDs, asset tag names, and expando bridge
	 *         attributes for the file entry.
	 * @return the file shortcut
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public FileShortcut addFileShortcut(
			long userId, long repositoryId, long folderId, long toFileEntryId,
			ServiceContext serviceContext)
		throws PortalException {

		LocalRepository localRepository = getLocalRepository(repositoryId);

		return localRepository.addFileShortcut(
			userId, folderId, toFileEntryId, serviceContext);
	}

	/**
	 * Adds a folder.
	 *
	 * @param  userId the primary key of the folder's creator/owner
	 * @param  repositoryId the primary key of the repository
	 * @param  parentFolderId the primary key of the folder's parent folder
	 * @param  name the folder's name
	 * @param  description the folder's description
	 * @param  serviceContext the service context to be applied. In a Liferay
	 *         repository, it may include mountPoint which is a boolean
	 *         specifying whether the folder is a facade for mounting a
	 *         third-party repository
	 * @return the folder
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public Folder addFolder(
			long userId, long repositoryId, long parentFolderId, String name,
			String description, ServiceContext serviceContext)
		throws PortalException {

		LocalRepository localRepository = getLocalRepository(repositoryId);

		Folder folder = localRepository.addFolder(
			userId, parentFolderId, name, description, serviceContext);

		_dlAppHelperLocalService.addFolder(userId, folder, serviceContext);

		return folder;
	}

	/**
	 * Delete all data associated to the given repository. This method is only
	 * supported by the Liferay repository.
	 *
	 * @param  repositoryId the primary key of the data's repository
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public void deleteAll(long repositoryId) throws PortalException {
		deleteRepository(getLocalRepository(repositoryId));
	}

	@Override
	public void deleteAllRepositories(long groupId) throws PortalException {
		LocalRepository groupLocalRepository =
			repositoryProvider.getLocalRepository(groupId);

		deleteRepository(groupLocalRepository);

		List<LocalRepository> localRepositories =
			repositoryProvider.getGroupLocalRepositories(groupId);

		for (LocalRepository localRepository : localRepositories) {
			if (localRepository.getRepositoryId() !=
					groupLocalRepository.getRepositoryId()) {

				deleteRepository(localRepository);
			}
		}
	}

	/**
	 * Deletes the file entry.
	 *
	 * @param  fileEntryId the primary key of the file entry
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public void deleteFileEntry(long fileEntryId) throws PortalException {
		LocalRepository localRepository =
			repositoryProvider.getFileEntryLocalRepository(fileEntryId);

		_dlAppHelperLocalService.deleteFileEntry(
			localRepository.getFileEntry(fileEntryId));

		localRepository.deleteFileEntry(fileEntryId);
	}

	/**
	 * Deletes the file shortcut. This method is only supported by the Liferay
	 * repository.
	 *
	 * @param  fileShortcut the file shortcut
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public void deleteFileShortcut(FileShortcut fileShortcut)
		throws PortalException {

		deleteFileShortcut(fileShortcut.getFileShortcutId());
	}

	/**
	 * Deletes the file shortcut. This method is only supported by the Liferay
	 * repository.
	 *
	 * @param  fileShortcutId the primary key of the file shortcut
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public void deleteFileShortcut(long fileShortcutId) throws PortalException {
		LocalRepository localRepository =
			repositoryProvider.getFileShortcutLocalRepository(fileShortcutId);

		localRepository.deleteFileShortcut(fileShortcutId);
	}

	/**
	 * Deletes all file shortcuts associated to the file entry. This method is
	 * only supported by the Liferay repository.
	 *
	 * @param  toFileEntryId the primary key of the associated file entry
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public void deleteFileShortcuts(long toFileEntryId) throws PortalException {
		LocalRepository localRepository =
			repositoryProvider.getFileEntryLocalRepository(toFileEntryId);

		localRepository.deleteFileShortcuts(toFileEntryId);
	}

	/**
	 * Deletes the file version. File versions can only be deleted if it is
	 * approved and there are other approved file versions available.
	 *
	 * @param  fileVersionId the primary key of the file version
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public void deleteFileVersion(long fileVersionId) throws PortalException {
		LocalRepository localRepository =
			repositoryProvider.getFileVersionLocalRepository(fileVersionId);

		localRepository.deleteFileVersion(fileVersionId);
	}

	/**
	 * Deletes the folder and all of its subfolders and file entries.
	 *
	 * @param  folderId the primary key of the folder
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public void deleteFolder(long folderId) throws PortalException {
		LocalRepository localRepository =
			repositoryProvider.getFolderLocalRepository(folderId);

		List<FileEntry> fileEntries = localRepository.getRepositoryFileEntries(
			UserConstants.USER_ID_DEFAULT, folderId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);

		for (FileEntry fileEntry : fileEntries) {
			_dlAppHelperLocalService.deleteFileEntry(fileEntry);
		}

		Folder folder = getFolder(folderId);

		localRepository.deleteFolder(folderId);

		_dlAppHelperLocalService.deleteFolder(folder);
	}

	/**
	 * Returns the document library file entry with the matching external
	 * reference code and group.
	 *
	 * @param  groupId the primary key of the file entry's group
	 * @param  externalReferenceCode the file entry's external reference code
	 * @return the matching document library file entry, or <code>null</code> if
	 *         a matching document library file entry could not be found
	 */
	@Override
	public FileEntry fetchFileEntryByExternalReferenceCode(
			long groupId, String externalReferenceCode)
		throws PortalException {

		LocalRepository localRepository = getLocalRepository(groupId);

		return localRepository.fetchFileEntryByExternalReferenceCode(
			externalReferenceCode);
	}

	/**
	 * Returns the file entry with the primary key.
	 *
	 * @param  fileEntryId the primary key of the file entry
	 * @return the file entry with the primary key
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public FileEntry getFileEntry(long fileEntryId) throws PortalException {
		LocalRepository localRepository =
			repositoryProvider.getFileEntryLocalRepository(fileEntryId);

		return localRepository.getFileEntry(fileEntryId);
	}

	/**
	 * Returns the file entry with the title in the folder.
	 *
	 * @param  groupId the primary key of the file entry's group
	 * @param  folderId the primary key of the file entry's folder
	 * @param  title the file entry's title
	 * @return the file entry with the title in the folder
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public FileEntry getFileEntry(long groupId, long folderId, String title)
		throws PortalException {

		try {
			LocalRepository localRepository = getLocalRepository(groupId);

			return localRepository.getFileEntry(folderId, title);
		}
		catch (NoSuchFileEntryException noSuchFileEntryException) {
			if (folderId == DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
				throw noSuchFileEntryException;
			}

			LocalRepository localRepository =
				repositoryProvider.getFolderLocalRepository(folderId);

			return localRepository.getFileEntry(folderId, title);
		}
	}

	/**
	 * Returns the file entry with the external reference code.
	 *
	 * @param  groupId the primary key of the file entry's group
	 * @param  externalReferenceCode the file entry's external reference code
	 * @return the file entry with the external reference code
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public FileEntry getFileEntryByExternalReferenceCode(
			long groupId, String externalReferenceCode)
		throws PortalException {

		LocalRepository localRepository = getLocalRepository(groupId);

		return localRepository.getFileEntryByExternalReferenceCode(
			externalReferenceCode);
	}

	/**
	 * Returns the file entry with the file name in the folder.
	 *
	 * @param  groupId the primary key of the file entry's group
	 * @param  folderId the primary key of the file entry's folder
	 * @param  fileName the file entry's file name
	 * @return the file entry with the file name in the folder
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public FileEntry getFileEntryByFileName(
			long groupId, long folderId, String fileName)
		throws PortalException {

		try {
			LocalRepository localRepository = getLocalRepository(groupId);

			return localRepository.getFileEntryByFileName(folderId, fileName);
		}
		catch (NoSuchFileEntryException noSuchFileEntryException) {
			if (folderId == DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
				throw noSuchFileEntryException;
			}

			LocalRepository localRepository =
				repositoryProvider.getFolderLocalRepository(folderId);

			return localRepository.getFileEntryByFileName(folderId, fileName);
		}
	}

	/**
	 * Returns the file entry with the UUID and group.
	 *
	 * @param  uuid the file entry's UUID
	 * @param  groupId the primary key of the file entry's group
	 * @return the file entry with the UUID and group
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public FileEntry getFileEntryByUuidAndGroupId(String uuid, long groupId)
		throws PortalException {

		try {
			LocalRepository localRepository = getLocalRepository(groupId);

			return localRepository.getFileEntryByUuid(uuid);
		}
		catch (NoSuchFileEntryException noSuchFileEntryException) {
			if (_log.isDebugEnabled()) {
				_log.debug(noSuchFileEntryException);
			}
		}

		List<Repository> repositories = _repositoryPersistence.findByGroupId(
			groupId);

		for (Repository repository : repositories) {
			try {
				LocalRepository localRepository = getLocalRepository(
					repository.getRepositoryId());

				return localRepository.getFileEntryByUuid(uuid);
			}
			catch (NoSuchFileEntryException noSuchFileEntryException) {
				if (_log.isDebugEnabled()) {
					_log.debug(noSuchFileEntryException);
				}
			}
		}

		throw new NoSuchFileEntryException(
			StringBundler.concat(
				"No DLFileEntry exists with the key {uuid=", uuid, ", groupId=",
				groupId, StringPool.CLOSE_CURLY_BRACE));
	}

	/**
	 * Returns the file shortcut with the primary key. This method is only
	 * supported by the Liferay repository.
	 *
	 * @param  fileShortcutId the primary key of the file shortcut
	 * @return the file shortcut with the primary key
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public FileShortcut getFileShortcut(long fileShortcutId)
		throws PortalException {

		LocalRepository localRepository =
			repositoryProvider.getFileShortcutLocalRepository(fileShortcutId);

		return localRepository.getFileShortcut(fileShortcutId);
	}

	/**
	 * Returns the file version with the primary key.
	 *
	 * @param  fileVersionId the primary key of the file version
	 * @return the file version with the primary key
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public FileVersion getFileVersion(long fileVersionId)
		throws PortalException {

		LocalRepository localRepository =
			repositoryProvider.getFileVersionLocalRepository(fileVersionId);

		return localRepository.getFileVersion(fileVersionId);
	}

	/**
	 * Returns the folder with the primary key.
	 *
	 * @param  folderId the primary key of the folder
	 * @return the folder with the primary key
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public Folder getFolder(long folderId) throws PortalException {
		LocalRepository localRepository =
			repositoryProvider.getFolderLocalRepository(folderId);

		return localRepository.getFolder(folderId);
	}

	/**
	 * Returns the folder with the name in the parent folder.
	 *
	 * @param  repositoryId the primary key of the folder's repository
	 * @param  parentFolderId the primary key of the folder's parent folder
	 * @param  name the folder's name
	 * @return the folder with the name in the parent folder
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public Folder getFolder(long repositoryId, long parentFolderId, String name)
		throws PortalException {

		LocalRepository localRepository = getLocalRepository(repositoryId);

		return localRepository.getFolder(parentFolderId, name);
	}

	/**
	 * Returns the mount folder of the repository with the primary key. This
	 * method is only supported by the Liferay repository.
	 *
	 * @param  repositoryId the primary key of the repository
	 * @return the folder used for mounting third-party repositories
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public Folder getMountFolder(long repositoryId) throws PortalException {
		DLFolder dlFolder = _dlFolderLocalService.getMountFolder(repositoryId);

		return new LiferayFolder(dlFolder);
	}

	/**
	 * Moves the file entry to the new folder.
	 *
	 * @param  userId the primary key of the user
	 * @param  fileEntryId the primary key of the file entry
	 * @param  newFolderId the primary key of the new folder
	 * @param  serviceContext the service context to be applied
	 * @return the file entry
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public FileEntry moveFileEntry(
			long userId, long fileEntryId, long newFolderId,
			ServiceContext serviceContext)
		throws PortalException {

		SystemEventHierarchyEntryThreadLocal.push(FileEntry.class);

		try {
			LocalRepository fromLocalRepository =
				repositoryProvider.getFileEntryLocalRepository(fileEntryId);

			LocalRepository toLocalRepository = getFolderLocalRepository(
				newFolderId, serviceContext.getScopeGroupId());

			if (fromLocalRepository.getRepositoryId() ==
					toLocalRepository.getRepositoryId()) {

				// Move file entries within repository

				return fromLocalRepository.moveFileEntry(
					userId, fileEntryId, newFolderId, serviceContext);
			}

			// Move file entries between repositories

			return moveFileEntry(
				userId, fileEntryId, newFolderId, fromLocalRepository,
				toLocalRepository, serviceContext);
		}
		finally {
			SystemEventHierarchyEntryThreadLocal.pop(FileEntry.class);
		}
	}

	@Override
	public Folder moveFolder(
			long userId, long folderId, long parentFolderId,
			ServiceContext serviceContext)
		throws PortalException {

		SystemEventHierarchyEntryThreadLocal.push(Folder.class);

		try {
			LocalRepository fromLocalRepository =
				repositoryProvider.getFolderLocalRepository(folderId);

			LocalRepository toLocalRepository = getFolderLocalRepository(
				parentFolderId, serviceContext.getScopeGroupId());

			if (parentFolderId != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
				Folder toFolder = toLocalRepository.getFolder(parentFolderId);

				if (toFolder.isMountPoint()) {
					toLocalRepository = getLocalRepository(
						toFolder.getRepositoryId());
				}
			}

			if (fromLocalRepository.getRepositoryId() ==
					toLocalRepository.getRepositoryId()) {

				// Move file entries within repository

				return fromLocalRepository.moveFolder(
					userId, folderId, parentFolderId, serviceContext);
			}

			// Move file entries between repositories

			return moveFolder(
				userId, folderId, parentFolderId, fromLocalRepository,
				toLocalRepository, serviceContext);
		}
		finally {
			SystemEventHierarchyEntryThreadLocal.pop(Folder.class);
		}
	}

	/**
	 * Subscribe the user to changes in documents of the file entry type. This
	 * method is only supported by the Liferay repository.
	 *
	 * @param  userId the primary key of the user
	 * @param  groupId the primary key of the file entry type's group
	 * @param  fileEntryTypeId the primary key of the file entry type
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public void subscribeFileEntryType(
			long userId, long groupId, long fileEntryTypeId)
		throws PortalException {
	}

	/**
	 * Subscribe the user to document changes in the folder. This method is only
	 * supported by the Liferay repository.
	 *
	 * @param  userId the primary key of the user
	 * @param  groupId the primary key of the folder's group
	 * @param  folderId the primary key of the folder
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public void subscribeFolder(long userId, long groupId, long folderId)
		throws PortalException {
	}

	/**
	 * Unsubscribe the user from changes in documents of the file entry type.
	 * This method is only supported by the Liferay repository.
	 *
	 * @param  userId the primary key of the user
	 * @param  groupId the primary key of the file entry type's group
	 * @param  fileEntryTypeId the primary key of the file entry type
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public void unsubscribeFileEntryType(
			long userId, long groupId, long fileEntryTypeId)
		throws PortalException {
	}

	/**
	 * Unsubscribe the user from document changes in the folder. This method is
	 * only supported by the Liferay repository.
	 *
	 * @param  userId the primary key of the user
	 * @param  groupId the primary key of the folder's group
	 * @param  folderId the primary key of the folder
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public void unsubscribeFolder(long userId, long groupId, long folderId)
		throws PortalException {
	}

	/**
	 * Updates the file entry's asset replacing its asset categories, tags, and
	 * links.
	 *
	 * @param  userId the primary key of the user
	 * @param  fileEntry the file entry to update
	 * @param  fileVersion the file version to update
	 * @param  assetCategoryIds the primary keys of the new asset categories
	 * @param  assetTagNames the new asset tag names
	 * @param  assetLinkEntryIds the primary keys of the new asset link entries
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public void updateAsset(
			long userId, FileEntry fileEntry, FileVersion fileVersion,
			long[] assetCategoryIds, String[] assetTagNames,
			long[] assetLinkEntryIds)
		throws PortalException {

		_dlAppHelperLocalService.updateAsset(
			userId, fileEntry, fileVersion, assetCategoryIds, assetTagNames,
			assetLinkEntryIds);
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
	 * @param  userId the primary key of the user
	 * @param  fileEntryId the primary key of the file entry
	 * @param  sourceFileName the original file's name (optionally
	 *         <code>null</code>)
	 * @param  mimeType the file's MIME type (optionally <code>null</code>)
	 * @param  title the new name to be assigned to the file (optionally <code>
	 *         <code>null</code></code>)
	 * @param  description the file's new description
	 * @param  changeLog the file's version change log (optionally
	 *         <code>null</code>)
	 * @param  dlVersionNumberIncrease the kind of version number increase to
	 *         apply for these changes.
	 * @param  bytes the file's data (optionally <code>null</code>)
	 * @param  expirationDate the file's expiration date (optionally <code>null
	 *                           </code>)
	 * @param  reviewDate the file's review date (optionally <code>null</code>)
	 * @param  serviceContext the service context to be applied. Can set the
	 *         asset category IDs, asset tag names, and expando bridge
	 *         attributes for the file entry. In a Liferay repository, it may
	 *         include:  <ul> <li> fileEntryTypeId - ID for a custom file entry
	 *         type </li> <li> fieldsMap - mapping for fields associated with a
	 *         custom file entry type </li> </ul>
	 * @return the file entry
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public FileEntry updateFileEntry(
			long userId, long fileEntryId, String sourceFileName,
			String mimeType, String title, String description, String changeLog,
			DLVersionNumberIncrease dlVersionNumberIncrease, byte[] bytes,
			Date expirationDate, Date reviewDate, ServiceContext serviceContext)
		throws PortalException {

		File file = null;

		try {
			if (ArrayUtil.isNotEmpty(bytes)) {
				file = FileUtil.createTempFile(bytes);
			}

			return updateFileEntry(
				userId, fileEntryId, sourceFileName, mimeType, title,
				description, changeLog, dlVersionNumberIncrease, file,
				expirationDate, reviewDate, serviceContext);
		}
		catch (IOException ioException) {
			throw new SystemException(
				"Unable to write temporary file", ioException);
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
	 * @param  userId the primary key of the user
	 * @param  fileEntryId the primary key of the file entry
	 * @param  sourceFileName the original file's name (optionally
	 *         <code>null</code>)
	 * @param  mimeType the file's MIME type (optionally <code>null</code>)
	 * @param  title the new name to be assigned to the file (optionally <code>
	 *         <code>null</code></code>)
	 * @param  description the file's new description
	 * @param  changeLog the file's version change log (optionally
	 *         <code>null</code>)
	 * @param  dlVersionNumberIncrease the kind of version number increase to
	 *         apply for these changes.
	 * @param  file the file's data (optionally <code>null</code>)
	 * @param  expirationDate the file's expiration date (optionally <code>null
	 *                           </code>)
	 * @param  reviewDate the file's review date (optionally <code>null</code>)
	 * @param  serviceContext the service context to be applied. Can set the
	 *         asset category IDs, asset tag names, and expando bridge
	 *         attributes for the file entry. In a Liferay repository, it may
	 *         include:  <ul> <li> fileEntryTypeId - ID for a custom file entry
	 *         type </li> <li> fieldsMap - mapping for fields associated with a
	 *         custom file entry type </li> </ul>
	 * @return the file entry
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public FileEntry updateFileEntry(
			long userId, long fileEntryId, String sourceFileName,
			String mimeType, String title, String description, String changeLog,
			DLVersionNumberIncrease dlVersionNumberIncrease, File file,
			Date expirationDate, Date reviewDate, ServiceContext serviceContext)
		throws PortalException {

		if ((file == null) || !file.exists() || (file.length() == 0)) {
			return updateFileEntry(
				userId, fileEntryId, sourceFileName, mimeType, title,
				description, changeLog, dlVersionNumberIncrease, null, 0,
				expirationDate, reviewDate, serviceContext);
		}

		mimeType = DLAppUtil.getMimeType(sourceFileName, mimeType, title, file);

		LocalRepository localRepository =
			repositoryProvider.getFileEntryLocalRepository(fileEntryId);

		FileEntry fileEntry = localRepository.updateFileEntry(
			userId, fileEntryId, sourceFileName, mimeType, title, title,
			description, changeLog, dlVersionNumberIncrease, file,
			expirationDate, reviewDate, serviceContext);

		_dlAppHelperLocalService.updateFileEntry(
			userId, fileEntry, null, fileEntry.getFileVersion(),
			serviceContext);

		return fileEntry;
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
	 * @param  userId the primary key of the user
	 * @param  fileEntryId the primary key of the file entry
	 * @param  sourceFileName the original file's name (optionally
	 *         <code>null</code>)
	 * @param  mimeType the file's MIME type (optionally <code>null</code>)
	 * @param  title the new name to be assigned to the file (optionally <code>
	 *         <code>null</code></code>)
	 * @param  description the file's new description
	 * @param  changeLog the file's version change log (optionally
	 *         <code>null</code>)
	 * @param  dlVersionNumberIncrease the kind of version number increase to
	 *         apply for these changes.
	 * @param  file the file's data (optionally <code>null</code>)
	 * @param  serviceContext the service context to be applied. Can set the
	 *         asset category IDs, asset tag names, and expando bridge
	 *         attributes for the file entry. In a Liferay repository, it may
	 *         include:  <ul> <li> fileEntryTypeId - ID for a custom file entry
	 *         type </li> <li> fieldsMap - mapping for fields associated with a
	 *         custom file entry type </li> </ul>
	 * @return the file entry
	 * @throws PortalException if a portal exception occurred
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link
	 *             #updateFileEntry(long, long , String, String, String, String,
	 *             String, DLVersionNumberIncrease, File,
	 *             Date, Date, ServiceContext)}
	 */
	@Deprecated
	@Override
	public FileEntry updateFileEntry(
			long userId, long fileEntryId, String sourceFileName,
			String mimeType, String title, String description, String changeLog,
			DLVersionNumberIncrease dlVersionNumberIncrease, File file,
			ServiceContext serviceContext)
		throws PortalException {

		return updateFileEntry(
			userId, fileEntryId, sourceFileName, mimeType, title, description,
			changeLog, dlVersionNumberIncrease, file, null, null,
			serviceContext);
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
	 * @param  userId the primary key of the user
	 * @param  fileEntryId the primary key of the file entry
	 * @param  sourceFileName the original file's name (optionally
	 *         <code>null</code>)
	 * @param  mimeType the file's MIME type (optionally <code>null</code>)
	 * @param  title the new name to be assigned to the file (optionally <code>
	 *         <code>null</code></code>)
	 * @param  description the file's new description
	 * @param  changeLog the file's version change log (optionally
	 *         <code>null</code>)
	 * @param  dlVersionNumberIncrease the kind of version number increase to
	 *         apply for these changes.
	 * @param  inputStream the file's data (optionally <code>null</code>)
	 * @param  size the file's size (optionally <code>0</code>)
	 * @param  expirationDate the file's expiration date (optionally <code>null
	 *                           </code>)
	 * @param  reviewDate the file's review date (optionally <code>null</code>)
	 * @param  serviceContext the service context to be applied. Can set the
	 *         asset category IDs, asset tag names, and expando bridge
	 *         attributes for the file entry. In a Liferay repository, it may
	 *         include:  <ul> <li> fileEntryTypeId - ID for a custom file entry
	 *         type </li> <li> fieldsMap - mapping for fields associated with a
	 *         custom file entry type </li> </ul>
	 * @return the file entry
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public FileEntry updateFileEntry(
			long userId, long fileEntryId, String sourceFileName,
			String mimeType, String title, String description, String changeLog,
			DLVersionNumberIncrease dlVersionNumberIncrease,
			InputStream inputStream, long size, Date expirationDate,
			Date reviewDate, ServiceContext serviceContext)
		throws PortalException {

		if (Validator.isNull(mimeType) ||
			mimeType.equals(ContentTypes.APPLICATION_OCTET_STREAM)) {

			if (size == 0) {
				String extension = DLAppUtil.getExtension(
					title, sourceFileName);

				mimeType = MimeTypesUtil.getExtensionContentType(extension);
			}
			else {
				File file = null;

				try {
					file = FileUtil.createTempFile(inputStream);

					return updateFileEntry(
						userId, fileEntryId, sourceFileName, mimeType, title,
						description, changeLog, dlVersionNumberIncrease, file,
						expirationDate, reviewDate, serviceContext);
				}
				catch (IOException ioException) {
					throw new SystemException(
						"Unable to write temporary file", ioException);
				}
				finally {
					FileUtil.delete(file);
				}
			}
		}

		LocalRepository localRepository =
			repositoryProvider.getFileEntryLocalRepository(fileEntryId);

		FileEntry fileEntry = localRepository.updateFileEntry(
			userId, fileEntryId, sourceFileName, mimeType, title, title,
			description, changeLog, dlVersionNumberIncrease, inputStream, size,
			expirationDate, reviewDate, serviceContext);

		_dlAppHelperLocalService.updateFileEntry(
			userId, fileEntry, null, fileEntry.getFileVersion(),
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
	 * @param  userId the primary key of the user
	 * @param  fileEntryId the primary key of the file entry
	 * @param  sourceFileName the original file's name (optionally
	 *         <code>null</code>)
	 * @param  mimeType the file's MIME type (optionally <code>null</code>)
	 * @param  title the new name to be assigned to the file (optionally <code>
	 *         <code>null</code></code>)
	 * @param  description the file's new description
	 * @param  changeLog the file's version change log (optionally
	 *         <code>null</code>)
	 * @param  dlVersionNumberIncrease the kind of version number increase to
	 *         apply for these changes.
	 * @param  inputStream the file's data (optionally <code>null</code>)
	 * @param  size the file's size (optionally <code>0</code>)
	 * @param  serviceContext the service context to be applied. Can set the
	 *         asset category IDs, asset tag names, and expando bridge
	 *         attributes for the file entry. In a Liferay repository, it may
	 *         include:  <ul> <li> fileEntryTypeId - ID for a custom file entry
	 *         type </li> <li> fieldsMap - mapping for fields associated with a
	 *         custom file entry type </li> </ul>
	 * @return the file entry
	 * @throws PortalException if a portal exception occurred
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link
	 *             #updateFileEntry(long, long , String, String, String, String,
	 *             String, DLVersionNumberIncrease, InputStream, long,
	 *             Date, Date, ServiceContext)}
	 */
	@Deprecated
	@Override
	public FileEntry updateFileEntry(
			long userId, long fileEntryId, String sourceFileName,
			String mimeType, String title, String description, String changeLog,
			DLVersionNumberIncrease dlVersionNumberIncrease,
			InputStream inputStream, long size, ServiceContext serviceContext)
		throws PortalException {

		return updateFileEntry(
			userId, fileEntryId, sourceFileName, mimeType, title, description,
			changeLog, dlVersionNumberIncrease, inputStream, size, null, null,
			serviceContext);
	}

	/**
	 * Updates a file shortcut to the existing file entry. This method is only
	 * supported by the Liferay repository.
	 *
	 * @param  userId the primary key of the file shortcut's creator/owner
	 * @param  fileShortcutId the primary key of the file shortcut
	 * @param  folderId the primary key of the file shortcut's parent folder
	 * @param  toFileEntryId the primary key of the file shortcut's file entry
	 * @param  serviceContext the service context to be applied. Can set the
	 *         asset category IDs, asset tag names, and expando bridge
	 *         attributes for the file entry.
	 * @return the file shortcut
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public FileShortcut updateFileShortcut(
			long userId, long fileShortcutId, long folderId, long toFileEntryId,
			ServiceContext serviceContext)
		throws PortalException {

		LocalRepository localRepository =
			repositoryProvider.getFileShortcutLocalRepository(fileShortcutId);

		return localRepository.updateFileShortcut(
			userId, fileShortcutId, folderId, toFileEntryId, serviceContext);
	}

	/**
	 * Updates all file shortcuts to the existing file entry to the new file
	 * entry. This method is only supported by the Liferay repository.
	 *
	 * @param  oldToFileEntryId the primary key of the old file entry pointed to
	 * @param  newToFileEntryId the primary key of the new file entry to point
	 *         to
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public void updateFileShortcuts(
			long oldToFileEntryId, long newToFileEntryId)
		throws PortalException {

		LocalRepository localRepository =
			repositoryProvider.getFileEntryLocalRepository(newToFileEntryId);

		localRepository.updateFileShortcuts(oldToFileEntryId, newToFileEntryId);
	}

	/**
	 * Updates the folder.
	 *
	 * @param  folderId the primary key of the folder
	 * @param  parentFolderId the primary key of the folder's new parent folder
	 * @param  name the folder's new name
	 * @param  description the folder's new description
	 * @param  serviceContext the service context to be applied. In a Liferay
	 *         repository, it may include:  <ul> <li> defaultFileEntryTypeId -
	 *         the file entry type to default all Liferay file entries to </li>
	 *         <li> dlFileEntryTypesSearchContainerPrimaryKeys - a
	 *         comma-delimited list of file entry type primary keys allowed in
	 *         the given folder and all descendants </li> <li> restrictionType -
	 *         specifying restriction type of file entry types allowed </li>
	 *         <li> workflowDefinitionXYZ - the workflow definition name
	 *         specified per file entry type. The parameter name must be the
	 *         string <code>workflowDefinition</code> appended by the
	 *         <code>fileEntryTypeId</code> (optionally <code>0</code>).</li>
	 *         </ul>
	 * @return the folder
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public Folder updateFolder(
			long folderId, long parentFolderId, String name, String description,
			ServiceContext serviceContext)
		throws PortalException {

		LocalRepository localRepository =
			repositoryProvider.getFolderLocalRepository(folderId);

		Folder folder = localRepository.updateFolder(
			folderId, parentFolderId, name, description, serviceContext);

		if (folderId != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			_dlAppHelperLocalService.updateFolder(
				serviceContext.getUserId(), folder, serviceContext);
		}

		return folder;
	}

	protected FileEntry copyFileEntry(
			long userId, LocalRepository toLocalRepository, FileEntry fileEntry,
			long newFolderId, ServiceContext serviceContext)
		throws PortalException {

		List<FileVersion> fileVersions = fileEntry.getFileVersions(
			WorkflowConstants.STATUS_ANY);

		FileVersion latestFileVersion = fileVersions.get(
			fileVersions.size() - 1);

		String sourceFileName = DLAppUtil.getSourceFileName(latestFileVersion);

		FileEntry destinationFileEntry = toLocalRepository.addFileEntry(
			null, userId, newFolderId, sourceFileName,
			latestFileVersion.getMimeType(), latestFileVersion.getTitle(),
			latestFileVersion.getTitle(), latestFileVersion.getDescription(),
			StringPool.BLANK, latestFileVersion.getContentStream(false),
			latestFileVersion.getSize(), latestFileVersion.getExpirationDate(),
			latestFileVersion.getReviewDate(), serviceContext);

		for (int i = fileVersions.size() - 2; i >= 0; i--) {
			FileVersion fileVersion = fileVersions.get(i);

			sourceFileName = DLAppUtil.getSourceFileName(fileVersion);

			FileVersion previousFileVersion = fileVersions.get(i + 1);

			try {
				destinationFileEntry = toLocalRepository.updateFileEntry(
					userId, destinationFileEntry.getFileEntryId(),
					sourceFileName, destinationFileEntry.getMimeType(),
					destinationFileEntry.getTitle(),
					destinationFileEntry.getTitle(),
					destinationFileEntry.getDescription(), StringPool.BLANK,
					DLVersionNumberIncrease.fromMajorVersion(
						DLAppUtil.isMajorVersion(
							fileVersion, previousFileVersion)),
					fileVersion.getContentStream(false), fileVersion.getSize(),
					fileVersion.getExpirationDate(),
					fileVersion.getReviewDate(), serviceContext);
			}
			catch (PortalException portalException) {
				toLocalRepository.deleteFileEntry(
					destinationFileEntry.getFileEntryId());

				throw portalException;
			}
		}

		return destinationFileEntry;
	}

	protected Folder copyFolder(
			long userId, long folderId, long parentFolderId,
			LocalRepository fromLocalRepository,
			LocalRepository toLocalRepository, ServiceContext serviceContext)
		throws PortalException {

		Folder newFolder = null;

		try {
			Folder folder = fromLocalRepository.getFolder(folderId);

			newFolder = toLocalRepository.addFolder(
				userId, parentFolderId, folder.getName(),
				folder.getDescription(), serviceContext);

			_dlAppHelperLocalService.addFolder(
				userId, newFolder, serviceContext);

			copyFolderDependencies(
				userId, folder, newFolder, fromLocalRepository,
				toLocalRepository, serviceContext);

			return newFolder;
		}
		catch (PortalException portalException) {
			if (newFolder != null) {
				toLocalRepository.deleteFolder(newFolder.getFolderId());
			}

			throw portalException;
		}
	}

	protected void copyFolderDependencies(
			long userId, Folder sourceFolder, Folder destinationFolder,
			LocalRepository fromLocalRepository,
			LocalRepository toLocalRepository, ServiceContext serviceContext)
		throws PortalException {

		List<RepositoryEntry> repositoryEntries =
			fromLocalRepository.getFoldersAndFileEntriesAndFileShortcuts(
				sourceFolder.getFolderId(), WorkflowConstants.STATUS_ANY, true,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

		for (RepositoryEntry repositoryEntry : repositoryEntries) {
			if (repositoryEntry instanceof FileEntry) {
				FileEntry fileEntry = (FileEntry)repositoryEntry;

				copyFileEntry(
					userId, toLocalRepository, fileEntry,
					destinationFolder.getFolderId(), serviceContext);
			}
			else if (repositoryEntry instanceof FileShortcut) {
				if (destinationFolder.isSupportsShortcuts()) {
					FileShortcut fileShortcut = (FileShortcut)repositoryEntry;

					toLocalRepository.addFileShortcut(
						userId, destinationFolder.getFolderId(),
						fileShortcut.getToFileEntryId(), serviceContext);
				}
			}
			else if (repositoryEntry instanceof Folder) {
				Folder currentFolder = (Folder)repositoryEntry;

				Folder newFolder = toLocalRepository.addFolder(
					userId, destinationFolder.getFolderId(),
					currentFolder.getName(), currentFolder.getDescription(),
					serviceContext);

				_dlAppHelperLocalService.addFolder(
					userId, newFolder, serviceContext);

				copyFolderDependencies(
					userId, currentFolder, newFolder, fromLocalRepository,
					toLocalRepository, serviceContext);
			}
		}
	}

	protected void deleteFileEntry(
			long oldFileEntryId, long newFileEntryId,
			LocalRepository fromLocalRepository,
			LocalRepository toLocalRepository)
		throws PortalException {

		try {
			FileEntry fileEntry = fromLocalRepository.getFileEntry(
				oldFileEntryId);

			fromLocalRepository.deleteFileEntry(oldFileEntryId);

			_dlAppHelperLocalService.deleteFileEntry(fileEntry);
		}
		catch (PortalException portalException) {
			FileEntry fileEntry = toLocalRepository.getFileEntry(
				newFileEntryId);

			toLocalRepository.deleteFileEntry(newFileEntryId);

			_dlAppHelperLocalService.deleteFileEntry(fileEntry);

			throw portalException;
		}
	}

	protected void deleteRepository(LocalRepository localRepository)
		throws PortalException {

		long repositoryId = localRepository.getRepositoryId();

		if (!RepositoryUtil.isExternalRepository(
				localRepository.getRepositoryId())) {

			_dlAppHelperLocalService.deleteRepositoryFileEntries(repositoryId);

			localRepository.deleteAll();
		}

		_repositoryLocalService.deleteRepository(repositoryId);
	}

	protected LocalRepository getFolderLocalRepository(
			long folderId, long groupId)
		throws PortalException {

		if (folderId == DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			return getLocalRepository(groupId);
		}

		return repositoryProvider.getFolderLocalRepository(folderId);
	}

	protected LocalRepository getLocalRepository(long repositoryId)
		throws PortalException {

		try {
			return repositoryProvider.getLocalRepository(repositoryId);
		}
		catch (InvalidRepositoryIdException invalidRepositoryIdException) {
			throw new NoSuchGroupException(
				StringBundler.concat(
					"No Group exists with the key {repositoryId=", repositoryId,
					"}"),
				invalidRepositoryIdException);
		}
	}

	protected FileEntry moveFileEntry(
			long userId, long fileEntryId, long newFolderId,
			LocalRepository fromLocalRepository,
			LocalRepository toLocalRepository, ServiceContext serviceContext)
		throws PortalException {

		FileEntry sourceFileEntry = fromLocalRepository.getFileEntry(
			fileEntryId);

		FileEntry destinationFileEntry = copyFileEntry(
			userId, toLocalRepository, sourceFileEntry, newFolderId,
			serviceContext);

		deleteFileEntry(
			fileEntryId, destinationFileEntry.getFileEntryId(),
			fromLocalRepository, toLocalRepository);

		return destinationFileEntry;
	}

	protected Folder moveFolder(
			long userId, long folderId, long parentFolderId,
			LocalRepository fromLocalRepository,
			LocalRepository toLocalRepository, ServiceContext serviceContext)
		throws PortalException {

		Folder newFolder = copyFolder(
			userId, folderId, parentFolderId, fromLocalRepository,
			toLocalRepository, serviceContext);

		fromLocalRepository.deleteFolder(folderId);

		return newFolder;
	}

	@BeanReference(type = RepositoryProvider.class)
	protected RepositoryProvider repositoryProvider;

	private static final Log _log = LogFactoryUtil.getLog(
		DLAppLocalServiceImpl.class);

	@BeanReference(type = DLAppHelperLocalService.class)
	private DLAppHelperLocalService _dlAppHelperLocalService;

	@BeanReference(type = DLFolderLocalService.class)
	private DLFolderLocalService _dlFolderLocalService;

	@BeanReference(type = RepositoryLocalService.class)
	private RepositoryLocalService _repositoryLocalService;

	@BeanReference(type = RepositoryPersistence.class)
	private RepositoryPersistence _repositoryPersistence;

}