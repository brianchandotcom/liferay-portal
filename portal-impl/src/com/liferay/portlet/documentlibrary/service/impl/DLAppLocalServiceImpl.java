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
import com.liferay.portal.kernel.repository.InvalidRepositoryIdException;
import com.liferay.portal.kernel.repository.LocalRepository;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.systemevent.SystemEventHierarchyEntryThreadLocal;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.Repository;
import com.liferay.portal.repository.liferayrepository.LiferayLocalRepository;
import com.liferay.portal.repository.liferayrepository.model.LiferayFolder;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.documentlibrary.NoSuchFileEntryException;
import com.liferay.portlet.documentlibrary.NoSuchFileVersionException;
import com.liferay.portlet.documentlibrary.NoSuchFolderException;
import com.liferay.portlet.documentlibrary.model.DLFileEntryType;
import com.liferay.portlet.documentlibrary.model.DLFileEntryTypeConstants;
import com.liferay.portlet.documentlibrary.model.DLFileRank;
import com.liferay.portlet.documentlibrary.model.DLFileShortcut;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.service.base.DLAppLocalServiceBaseImpl;
import com.liferay.portlet.documentlibrary.util.DLAppUtil;
import com.liferay.portlet.documentlibrary.util.DLProcessorRegistryUtil;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import java.util.List;

/**
 * Provides the local service for accessing, adding, deleting, moving,
 * subscription handling of, trash handling of, and updating document library
 * file entries, file ranks, and folders. All portlets should interact with the
 * document library through this class or through DLAppService, rather than
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
 * com.liferay.portal.service.RepositoryLocalServiceUtil}.
 * </p>
 *
 * @author Alexander Chow
 * @author Mika Koivisto
 * @see    DLAppServiceImpl
 */
public class DLAppLocalServiceImpl extends DLAppLocalServiceBaseImpl {

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
	 * @param  serviceContext the service context to be applied. Can set the
	 *         asset category IDs, asset tag names, and expando bridge
	 *         attributes for the file entry. In a Liferay repository, it may
	 *         include:  <ul> <li> fileEntryTypeId - ID for a custom file entry
	 *         type </li> <li> fieldsMap - mapping for fields associated with a
	 *         custom file entry type </li> </ul>
	 * @return the file entry
	 * @throws PortalException if the parent folder could not be found or if the
	 *         file entry's information was invalid
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public FileEntry addFileEntry(
			long userId, long repositoryId, long folderId,
			String sourceFileName, String mimeType, String title,
			String description, String changeLog, byte[] bytes,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		File file = null;

		try {
			if (ArrayUtil.isNotEmpty(bytes)) {
				file = FileUtil.createTempFile(bytes);
			}

			return addFileEntry(
				userId, repositoryId, folderId, sourceFileName, mimeType, title,
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
	 * Adds a file entry and associated metadata based on a {@link java.io.File}
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
	 * @param  serviceContext the service context to be applied. Can set the
	 *         asset category IDs, asset tag names, and expando bridge
	 *         attributes for the file entry. In a Liferay repository, it may
	 *         include:  <ul> <li> fileEntryTypeId - ID for a custom file entry
	 *         type </li> <li> fieldsMap - mapping for fields associated with a
	 *         custom file entry type </li> </ul>
	 * @return the file entry
	 * @throws PortalException if the parent folder could not be found or if the
	 *         file entry's information was invalid
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public FileEntry addFileEntry(
			long userId, long repositoryId, long folderId,
			String sourceFileName, String mimeType, String title,
			String description, String changeLog, File file,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		if ((file == null) || !file.exists() || (file.length() == 0)) {
			return addFileEntry(
				userId, repositoryId, folderId, sourceFileName, mimeType, title,
				description, changeLog, null, 0, serviceContext);
		}

		mimeType = DLAppUtil.getMimeType(sourceFileName, mimeType, title, file);

		LocalRepository localRepository = getLocalRepository(repositoryId);

		FileEntry fileEntry = localRepository.addFileEntry(
			userId, folderId, sourceFileName, mimeType, title, description,
			changeLog, file, serviceContext);

		dlAppHelperLocalService.addFileEntry(
			userId, fileEntry, fileEntry.getFileVersion(), serviceContext);

		return fileEntry;
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
	 * @param  userId the primary key of the file entry's creator/owner
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
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public FileEntry addFileEntry(
			long userId, long repositoryId, long folderId,
			String sourceFileName, String mimeType, String title,
			String description, String changeLog, InputStream is, long size,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

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
						userId, repositoryId, folderId, sourceFileName,
						mimeType, title, description, changeLog, file,
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

		LocalRepository localRepository = getLocalRepository(repositoryId);

		FileEntry fileEntry = localRepository.addFileEntry(
			userId, folderId, sourceFileName, mimeType, title, description,
			changeLog, is, size, serviceContext);

		dlAppHelperLocalService.addFileEntry(
			userId, fileEntry, fileEntry.getFileVersion(), serviceContext);

		return fileEntry;
	}

	/**
	 * Adds the file rank to the existing file entry. This method is only
	 * supported by the Liferay repository.
	 *
	 * @param  repositoryId the primary key of the repository
	 * @param  companyId the primary key of the company
	 * @param  userId the primary key of the file rank's creator/owner
	 * @param  fileEntryId the primary key of the file entry
	 * @param  serviceContext the service context to be applied
	 * @return the file rank
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public DLFileRank addFileRank(
			long repositoryId, long companyId, long userId, long fileEntryId,
			ServiceContext serviceContext)
		throws SystemException {

		return dlFileRankLocalService.addFileRank(
			repositoryId, companyId, userId, fileEntryId, serviceContext);
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
	 * @throws PortalException if the parent folder or file entry could not be
	 *         found, or if the file shortcut's information was invalid
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public DLFileShortcut addFileShortcut(
			long userId, long repositoryId, long folderId, long toFileEntryId,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		return dlFileShortcutLocalService.addFileShortcut(
			userId, repositoryId, folderId, toFileEntryId, serviceContext);
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
	 * @throws PortalException if the parent folder could not be found or if the
	 *         new folder's information was invalid
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Folder addFolder(
			long userId, long repositoryId, long parentFolderId, String name,
			String description, ServiceContext serviceContext)
		throws PortalException, SystemException {

		LocalRepository localRepository = getLocalRepository(repositoryId);

		Folder folder = localRepository.addFolder(
			userId, parentFolderId, name, description, serviceContext);

		dlAppHelperLocalService.addFolder(
			serviceContext.getUserId(), folder, serviceContext);

		return folder;
	}

	/**
	 * Delete all data associated to the given repository. This method is only
	 * supported by the Liferay repository.
	 *
	 * @param  repositoryId the primary key of the data's repository
	 * @throws PortalException if the repository could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void deleteAll(long repositoryId)
		throws PortalException, SystemException {

		LocalRepository localRepository = getLocalRepository(repositoryId);

		deleteRepository(localRepository);
	}

	@Override
	public void deleteAllRepositories(long groupId)
		throws PortalException, SystemException {

		List<LocalRepository> localRepositories =
			repositoryLocalService.getGroupLocalRepositoryImpl(groupId);

		for (LocalRepository localRepository : localRepositories) {
			deleteRepository(localRepository);
		}
	}

	/**
	 * Deletes the file entry.
	 *
	 * @param  fileEntryId the primary key of the file entry
	 * @throws PortalException if the file entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void deleteFileEntry(long fileEntryId)
		throws PortalException, SystemException {

		LocalRepository localRepository = getFileEntryLocalRepository(
			fileEntryId);

		FileEntry fileEntry = localRepository.getFileEntry(fileEntryId);

		dlAppHelperLocalService.deleteFileEntry(fileEntry);

		localRepository.deleteFileEntry(fileEntryId);
	}

	/**
	 * Deletes the file ranks associated to a given file entry. This method is
	 * only supported by the Liferay repository.
	 *
	 * @param  fileEntryId the primary key of the file entry
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void deleteFileRanksByFileEntryId(long fileEntryId)
		throws SystemException {

		dlFileRankLocalService.deleteFileRanksByFileEntryId(fileEntryId);
	}

	/**
	 * Deletes the file ranks associated to a given user. This method is only
	 * supported by the Liferay repository.
	 *
	 * @param  userId the primary key of the user
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void deleteFileRanksByUserId(long userId) throws SystemException {
		dlFileRankLocalService.deleteFileRanksByUserId(userId);
	}

	/**
	 * Deletes the file shortcut. This method is only supported by the Liferay
	 * repository.
	 *
	 * @param  dlFileShortcut the file shortcut
	 * @throws PortalException if the file shortcut could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void deleteFileShortcut(DLFileShortcut dlFileShortcut)
		throws PortalException, SystemException {

		dlFileShortcutLocalService.deleteFileShortcut(dlFileShortcut);
	}

	/**
	 * Deletes the file shortcut. This method is only supported by the Liferay
	 * repository.
	 *
	 * @param  fileShortcutId the primary key of the file shortcut
	 * @throws PortalException if the file shortcut could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void deleteFileShortcut(long fileShortcutId)
		throws PortalException, SystemException {

		dlFileShortcutLocalService.deleteFileShortcut(fileShortcutId);
	}

	/**
	 * Deletes all file shortcuts associated to the file entry. This method is
	 * only supported by the Liferay repository.
	 *
	 * @param  toFileEntryId the primary key of the associated file entry
	 * @throws PortalException if the file shortcut for the file entry could not
	 *         be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void deleteFileShortcuts(long toFileEntryId)
		throws PortalException, SystemException {

		dlFileShortcutLocalService.deleteFileShortcuts(toFileEntryId);
	}

	/**
	 * Deletes the folder and all of its subfolders and file entries.
	 *
	 * @param  folderId the primary key of the folder
	 * @throws PortalException if the folder could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void deleteFolder(long folderId)
		throws PortalException, SystemException {

		LocalRepository localRepository = getFolderLocalRepository(folderId);

		List<FileEntry> fileEntries =
			localRepository.getRepositoryFileEntries(
				folderId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

		for (FileEntry fileEntry : fileEntries) {
			dlAppHelperLocalService.deleteFileEntry(fileEntry);
		}

		Folder folder = getFolder(folderId);

		localRepository.deleteFolder(folderId);

		dlAppHelperLocalService.deleteFolder(folder);
	}

	/**
	 * Returns the file entry with the primary key.
	 *
	 * @param  fileEntryId the primary key of the file entry
	 * @return the file entry with the primary key
	 * @throws PortalException if the file entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public FileEntry getFileEntry(long fileEntryId)
		throws PortalException, SystemException {

		LocalRepository localRepository = getFileEntryLocalRepository(
			fileEntryId);

		return localRepository.getFileEntry(fileEntryId);
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
	@Override
	public FileEntry getFileEntry(long groupId, long folderId, String title)
		throws PortalException, SystemException {

		try {
			LocalRepository localRepository = getLocalRepository(groupId);

			return localRepository.getFileEntry(folderId, title);
		}
		catch (NoSuchFileEntryException nsfee) {
		}

		LocalRepository localRepository = getFolderLocalRepository(folderId);

		return localRepository.getFileEntry(folderId, title);
	}

	/**
	 * Returns the file entry with the UUID and group.
	 *
	 * @param  uuid the file entry's UUID
	 * @param  groupId the primary key of the file entry's group
	 * @return the file entry with the UUID and group
	 * @throws PortalException if the file entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public FileEntry getFileEntryByUuidAndGroupId(String uuid, long groupId)
		throws PortalException, SystemException {

		try {
			LocalRepository localRepository = getLocalRepository(groupId);

			return localRepository.getFileEntryByUuid(uuid);
		}
		catch (NoSuchFileEntryException nsfee) {
		}

		List<com.liferay.portal.model.Repository> repositories =
			repositoryPersistence.findByGroupId(groupId);

		for (Repository repository : repositories) {
			try {
				LocalRepository localRepository = getLocalRepository(
					repository.getRepositoryId());

				return localRepository.getFileEntryByUuid(uuid);
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
	 * Returns the file ranks from the user. This method is only supported by
	 * the Liferay repository.
	 *
	 * @param  repositoryId the primary key of the repository
	 * @param  userId the primary key of the user
	 * @return the file ranks from the user
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<DLFileRank> getFileRanks(long repositoryId, long userId)
		throws SystemException {

		return dlFileRankLocalService.getFileRanks(repositoryId, userId);
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
	@Override
	public DLFileShortcut getFileShortcut(long fileShortcutId)
		throws PortalException, SystemException {

		return dlFileShortcutLocalService.getFileShortcut(fileShortcutId);
	}

	/**
	 * Returns the file version with the primary key.
	 *
	 * @param  fileVersionId the primary key of the file version
	 * @return the file version with the primary key
	 * @throws PortalException if the file version could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public FileVersion getFileVersion(long fileVersionId)
		throws PortalException, SystemException {

		LocalRepository localRepository = getFileVersionLocalRepository(
			fileVersionId);

		return localRepository.getFileVersion(fileVersionId);
	}

	/**
	 * Returns the folder with the primary key.
	 *
	 * @param  folderId the primary key of the folder
	 * @return the folder with the primary key
	 * @throws PortalException if the folder could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Folder getFolder(long folderId)
		throws PortalException, SystemException {

		LocalRepository localRepository = getFolderLocalRepository(folderId);

		return localRepository.getFolder(folderId);
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
	@Override
	public Folder getFolder(long repositoryId, long parentFolderId, String name)
		throws PortalException, SystemException {

		LocalRepository localRepository = getLocalRepository(repositoryId);

		return localRepository.getFolder(parentFolderId, name);
	}

	/**
	 * Returns the mount folder of the repository with the primary key. This
	 * method is only supported by the Liferay repository.
	 *
	 * @param  repositoryId the primary key of the repository
	 * @return the folder used for mounting third-party repositories
	 * @throws PortalException if the repository or mount folder could not be
	 *         found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Folder getMountFolder(long repositoryId)
		throws PortalException, SystemException {

		DLFolder dlFolder = dlFolderLocalService.getMountFolder(repositoryId);

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
	 * @throws PortalException if the file entry or the new folder could not be
	 *         found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public FileEntry moveFileEntry(
			long userId, long fileEntryId, long newFolderId,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		SystemEventHierarchyEntryThreadLocal.push(FileEntry.class);

		try {
			LocalRepository fromLocalRepository = getFileEntryLocalRepository(
				fileEntryId);
			LocalRepository toLocalRepository = getFolderLocalRepository(
				newFolderId, serviceContext.getScopeGroupId());

			if (fromLocalRepository.getRepositoryId() ==
					toLocalRepository.getRepositoryId()) {

				// Move file entries within repository

				FileEntry fileEntry = fromLocalRepository.moveFileEntry(
					userId, fileEntryId, newFolderId, serviceContext);

				dlAppHelperLocalService.moveFileEntry(fileEntry);

				return fileEntry;
			}

			// Move file entries between repositories

			return moveFileEntries(
				userId, fileEntryId, newFolderId, fromLocalRepository,
				toLocalRepository, serviceContext);
		}
		finally {
			SystemEventHierarchyEntryThreadLocal.pop(FileEntry.class);
		}
	}

	@Override
	public FileEntry moveFileEntryFromTrash(
			long userId, long fileEntryId, long newFolderId,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		LocalRepository localRepository = getFileEntryLocalRepository(
			fileEntryId);

		if (!(localRepository instanceof LiferayLocalRepository)) {
			throw new InvalidRepositoryException(
				"Repository " + localRepository.getRepositoryId() +
					" does not support trash operations");
		}

		FileEntry fileEntry = localRepository.getFileEntry(fileEntryId);

		return dlAppHelperLocalService.moveFileEntryFromTrash(
			userId, fileEntry, newFolderId, serviceContext);
	}

	/**
	 * Moves the file entry with the primary key to the trash portlet.
	 *
	 * @param  userId the primary key of the user
	 * @param  fileEntryId the primary key of the file entry
	 * @return the file entry
	 * @throws PortalException if the file entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public FileEntry moveFileEntryToTrash(long userId, long fileEntryId)
		throws PortalException, SystemException {

		LocalRepository localRepository = getFileEntryLocalRepository(
			fileEntryId);

		FileEntry fileEntry = localRepository.getFileEntry(fileEntryId);

		return dlAppHelperLocalService.moveFileEntryToTrash(userId, fileEntry);
	}

	@Override
	public Folder moveFolder(
			long userId, long folderId, long parentFolderId,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		SystemEventHierarchyEntryThreadLocal.push(Folder.class);

		try {
			LocalRepository sourceLocalRepository = getFolderLocalRepository(
				folderId);

			LocalRepository destinationLocalRepository =
				getFolderLocalRepository(
					parentFolderId, serviceContext.getScopeGroupId());

			if (parentFolderId != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
				Folder toFolder = destinationLocalRepository.getFolder(
					parentFolderId);

				if (toFolder.isMountPoint()) {
					destinationLocalRepository = getLocalRepository(
						toFolder.getRepositoryId());
				}
			}

			Folder folder = null;

			if (sourceLocalRepository.getRepositoryId() ==
					destinationLocalRepository.getRepositoryId()) {

				// Move file entries within repository

				folder = sourceLocalRepository.moveFolder(
					userId, folderId, parentFolderId, serviceContext);
			}
			else {

				// Move file entries between repositories

				folder = moveFolders(
					userId, folderId, parentFolderId, sourceLocalRepository,
					destinationLocalRepository, serviceContext);
			}

			dlAppHelperLocalService.moveFolder(folder);

			return folder;
		}
		finally {
			SystemEventHierarchyEntryThreadLocal.pop(Folder.class);
		}
	}

	/**
	 * Restores the file entry with the primary key from the trash portlet.
	 *
	 * @param  userId the primary key of the user
	 * @param  fileEntryId the primary key of the file entry
	 * @throws PortalException if the file entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void restoreFileEntryFromTrash(long userId, long fileEntryId)
		throws PortalException, SystemException {

		LocalRepository localRepository = getFileEntryLocalRepository(
			fileEntryId);

		FileEntry fileEntry = localRepository.getFileEntry(fileEntryId);

		dlAppHelperLocalService.restoreFileEntryFromTrash(userId, fileEntry);
	}

	/**
	 * Subscribe the user to changes in documents of the file entry type. This
	 * method is only supported by the Liferay repository.
	 *
	 * @param  userId the primary key of the user
	 * @param  groupId the primary key of the file entry type's group
	 * @param  fileEntryTypeId the primary key of the file entry type
	 * @throws PortalException if the user or group could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void subscribeFileEntryType(
			long userId, long groupId, long fileEntryTypeId)
		throws PortalException, SystemException {

		if (fileEntryTypeId ==
				DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_BASIC_DOCUMENT) {

			fileEntryTypeId = groupId;
		}

		subscriptionLocalService.addSubscription(
			userId, groupId, DLFileEntryType.class.getName(), fileEntryTypeId);
	}

	/**
	 * Subscribe the user to document changes in the folder. This method is only
	 * supported by the Liferay repository.
	 *
	 * @param  userId the primary key of the user
	 * @param  groupId the primary key of the folder's group
	 * @param  folderId the primary key of the folder
	 * @throws PortalException if the user or group could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void subscribeFolder(long userId, long groupId, long folderId)
		throws PortalException, SystemException {

		if (folderId == DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			folderId = groupId;
		}

		subscriptionLocalService.addSubscription(
			userId, groupId, Folder.class.getName(), folderId);
	}

	/**
	 * Unsubscribe the user from changes in documents of the file entry type.
	 * This method is only supported by the Liferay repository.
	 *
	 * @param  userId the primary key of the user
	 * @param  groupId the primary key of the file entry type's group
	 * @param  fileEntryTypeId the primary key of the file entry type
	 * @throws PortalException if the user or group could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void unsubscribeFileEntryType(
			long userId, long groupId, long fileEntryTypeId)
		throws PortalException, SystemException {

		if (fileEntryTypeId ==
				DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_BASIC_DOCUMENT) {

			fileEntryTypeId = groupId;
		}

		subscriptionLocalService.deleteSubscription(
			userId, DLFileEntryType.class.getName(), fileEntryTypeId);
	}

	/**
	 * Unsubscribe the user from document changes in the folder. This method is
	 * only supported by the Liferay repository.
	 *
	 * @param  userId the primary key of the user
	 * @param  groupId the primary key of the folder's group
	 * @param  folderId the primary key of the folder
	 * @throws PortalException if the user or group could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void unsubscribeFolder(long userId, long groupId, long folderId)
		throws PortalException, SystemException {

		if (folderId == DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			folderId = groupId;
		}

		subscriptionLocalService.deleteSubscription(
			userId, Folder.class.getName(), folderId);
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
	 * @throws PortalException if the file entry or version could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void updateAsset(
			long userId, FileEntry fileEntry, FileVersion fileVersion,
			long[] assetCategoryIds, String[] assetTagNames,
			long[] assetLinkEntryIds)
		throws PortalException, SystemException {

		LocalRepository localRepository = getFileEntryLocalRepository(
			fileEntry.getFileEntryId());

		localRepository.updateAsset(
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
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public FileEntry updateFileEntry(
			long userId, long fileEntryId, String sourceFileName,
			String mimeType, String title, String description, String changeLog,
			boolean majorVersion, byte[] bytes, ServiceContext serviceContext)
		throws PortalException, SystemException {

		File file = null;

		try {
			if (ArrayUtil.isNotEmpty(bytes)) {
				file = FileUtil.createTempFile(bytes);
			}

			return updateFileEntry(
				userId, fileEntryId, sourceFileName, mimeType, title,
				description, changeLog, majorVersion, file, serviceContext);
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
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public FileEntry updateFileEntry(
			long userId, long fileEntryId, String sourceFileName,
			String mimeType, String title, String description, String changeLog,
			boolean majorVersion, File file, ServiceContext serviceContext)
		throws PortalException, SystemException {

		if ((file == null) || !file.exists() || (file.length() == 0)) {
			return updateFileEntry(
				userId, fileEntryId, sourceFileName, mimeType, title,
				description, changeLog, majorVersion, null, 0, serviceContext);
		}

		mimeType = DLAppUtil.getMimeType(sourceFileName, mimeType, title, file);

		LocalRepository localRepository = getFileEntryLocalRepository(
			fileEntryId);

		FileEntry fileEntry = localRepository.updateFileEntry(
			userId, fileEntryId, sourceFileName, mimeType, title, description,
			changeLog, majorVersion, file, serviceContext);

		DLProcessorRegistryUtil.cleanUp(fileEntry.getLatestFileVersion(true));

		dlAppHelperLocalService.updateFileEntry(
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
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public FileEntry updateFileEntry(
			long userId, long fileEntryId, String sourceFileName,
			String mimeType, String title, String description, String changeLog,
			boolean majorVersion, InputStream is, long size,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

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
						userId, fileEntryId, sourceFileName, mimeType, title,
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

		LocalRepository localRepository = getFileEntryLocalRepository(
			fileEntryId);

		FileEntry oldFileEntry = localRepository.getFileEntry(fileEntryId);

		FileVersion oldFileVersion = oldFileEntry.getFileVersion();

		FileEntry fileEntry = localRepository.updateFileEntry(
			userId, fileEntryId, sourceFileName, mimeType, title, description,
			changeLog, majorVersion, is, size, serviceContext);

		if (is != null) {
			DLProcessorRegistryUtil.cleanUp(
				fileEntry.getLatestFileVersion(true));

			oldFileVersion = null;
		}

		dlAppHelperLocalService.updateFileEntry(
			userId, fileEntry, oldFileVersion, fileEntry.getFileVersion(),
			serviceContext);

		return fileEntry;
	}

	/**
	 * Updates a file rank to the existing file entry. This method is only
	 * supported by the Liferay repository.
	 *
	 * @param  repositoryId the primary key of the file rank's repository
	 * @param  companyId the primary key of the file rank's company
	 * @param  userId the primary key of the file rank's creator/owner
	 * @param  fileEntryId the primary key of the file rank's file entry
	 * @param  serviceContext the service context to be applied
	 * @return the file rank
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public DLFileRank updateFileRank(
			long repositoryId, long companyId, long userId, long fileEntryId,
			ServiceContext serviceContext)
		throws SystemException {

		return dlFileRankLocalService.updateFileRank(
			repositoryId, companyId, userId, fileEntryId, serviceContext);
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
	 * @throws PortalException if the file shortcut, folder, or file entry could
	 *         not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public DLFileShortcut updateFileShortcut(
			long userId, long fileShortcutId, long folderId, long toFileEntryId,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		return dlFileShortcutLocalService.updateFileShortcut(
			userId, fileShortcutId, folderId, toFileEntryId, serviceContext);
	}

	/**
	 * Updates all file shortcuts to the existing file entry to the new file
	 * entry. This method is only supported by the Liferay repository.
	 *
	 * @param  toRepositoryId the primary key of the repository
	 * @param  oldToFileEntryId the primary key of the old file entry pointed to
	 * @param  newToFileEntryId the primary key of the new file entry to point
	 *         to
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void updateFileShortcuts(
			long toRepositoryId, long oldToFileEntryId, long newToFileEntryId)
		throws SystemException {

		dlFileShortcutLocalService.updateFileShortcuts(
			oldToFileEntryId, newToFileEntryId);
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
	 *         the given folder and all descendants </li> <li>
	 *         overrideFileEntryTypes - boolean specifying whether to override
	 *         ancestral folder's restriction of file entry types allowed </li>
	 *         <li> workflowDefinitionXYZ - the workflow definition name
	 *         specified per file entry type. The parameter name must be the
	 *         string <code>workflowDefinition</code> appended by the <code>
	 *         fileEntryTypeId</code> (optionally <code>0</code>). </li> </ul>
	 * @return the folder
	 * @throws PortalException if the current or new parent folder could not be
	 *         found, or if the new parent folder's information was invalid
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Folder updateFolder(
			long folderId, long parentFolderId, String name, String description,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		LocalRepository localRepository = getFolderLocalRepository(folderId);

		Folder folder = localRepository.updateFolder(
			folderId, parentFolderId, name, description, serviceContext);

		dlAppHelperLocalService.updateFolder(
			serviceContext.getUserId(), folder, serviceContext);

		return folder;
	}

	protected FileEntry copyFileEntry(
			long userId, LocalRepository toLocalRepository, FileEntry fileEntry,
			long newFolderId, ServiceContext serviceContext)
		throws PortalException, SystemException {

		List<FileVersion> fileVersions = fileEntry.getFileVersions(
			WorkflowConstants.STATUS_ANY);

		FileVersion latestFileVersion = fileVersions.get(
			fileVersions.size() - 1);

		FileEntry destinationFileEntry = toLocalRepository.addFileEntry(
			userId, newFolderId, fileEntry.getTitle(),
			latestFileVersion.getMimeType(), latestFileVersion.getTitle(),
			latestFileVersion.getDescription(), StringPool.BLANK,
			latestFileVersion.getContentStream(false),
			latestFileVersion.getSize(), serviceContext);

		for (int i = fileVersions.size() - 2; i >= 0; i--) {
			FileVersion fileVersion = fileVersions.get(i);

			FileVersion previousFileVersion = fileVersions.get(i + 1);

			try {
				destinationFileEntry = toLocalRepository.updateFileEntry(
					userId, destinationFileEntry.getFileEntryId(),
					fileEntry.getTitle(), destinationFileEntry.getMimeType(),
					destinationFileEntry.getTitle(),
					destinationFileEntry.getDescription(), StringPool.BLANK,
					DLAppUtil.isMajorVersion(fileVersion, previousFileVersion),
					fileVersion.getContentStream(false), fileVersion.getSize(),
					serviceContext);
			}
			catch (PortalException pe) {
				toLocalRepository.deleteFileEntry(
					destinationFileEntry.getFileEntryId());

				throw pe;
			}
		}

		dlAppHelperLocalService.addFileEntry(
			userId, destinationFileEntry, destinationFileEntry.getFileVersion(),
			serviceContext);

		return destinationFileEntry;
	}

	protected void deleteFileEntry(
			long oldFileEntryId, long newFileEntryId,
			LocalRepository fromLocalRepository,
			LocalRepository toLocalRepository)
		throws PortalException, SystemException {

		try {
			FileEntry fileEntry = fromLocalRepository.getFileEntry(
				oldFileEntryId);

			fromLocalRepository.deleteFileEntry(oldFileEntryId);

			dlAppHelperLocalService.deleteFileEntry(fileEntry);
		}
		catch (PortalException pe) {
			FileEntry fileEntry = toLocalRepository.getFileEntry(
				newFileEntryId);

			toLocalRepository.deleteFileEntry(newFileEntryId);

			dlAppHelperLocalService.deleteFileEntry(fileEntry);

			throw pe;
		}
	}

	protected void deleteRepository(LocalRepository localRepository)
		throws PortalException, SystemException {

		long repositoryId = localRepository.getRepositoryId();

		dlAppHelperLocalService.deleteRepositoryFileEntries(repositoryId);

		localRepository.deleteAll();
	}

	protected LocalRepository getFileEntryLocalRepository(long fileEntryId)
		throws PortalException, SystemException {

		try {
			return repositoryLocalService.getLocalRepositoryImpl(
				0, fileEntryId, 0);
		}
		catch (InvalidRepositoryIdException irie) {
			StringBundler sb = new StringBundler(3);

			sb.append("No FileEntry exists with the key {fileEntryId=");
			sb.append(fileEntryId);
			sb.append("}");

			throw new NoSuchFileEntryException(sb.toString(), irie);
		}
	}

	protected LocalRepository getFileVersionLocalRepository(long fileVersionId)
		throws PortalException, SystemException {

		try {
			return repositoryLocalService.getLocalRepositoryImpl(
				0, 0, fileVersionId);
		}
		catch (InvalidRepositoryIdException irie) {
			StringBundler sb = new StringBundler(3);

			sb.append("No FileVersion exists with the key {fileVersionId=");
			sb.append(fileVersionId);
			sb.append("}");

			throw new NoSuchFileVersionException(sb.toString(), irie);
		}
	}

	protected LocalRepository getFolderLocalRepository(long folderId)
		throws PortalException, SystemException {

		try {
			return repositoryLocalService.getLocalRepositoryImpl(
				folderId, 0, 0);
		}
		catch (InvalidRepositoryIdException irie) {
			StringBundler sb = new StringBundler(3);

			sb.append("No Folder exists with the key {folderId=");
			sb.append(folderId);
			sb.append("}");

			throw new NoSuchFolderException(sb.toString(), irie);
		}
	}

	protected LocalRepository getFolderLocalRepository(
			long folderId, long groupId)
		throws PortalException, SystemException {

		LocalRepository localRepository = null;

		if (folderId == DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			localRepository = getLocalRepository(groupId);
		}
		else {
			localRepository = getFolderLocalRepository(folderId);
		}

		return localRepository;
	}

	protected LocalRepository getLocalRepository(long repositoryId)
		throws PortalException, SystemException {

		try {
			return repositoryLocalService.getLocalRepositoryImpl(repositoryId);
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
			long userId, long fileEntryId, long newFolderId,
			LocalRepository fromLocalRepository,
			LocalRepository toLocalRepository, ServiceContext serviceContext)
		throws PortalException, SystemException {

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

	protected Folder moveFolders(
			long userId, long folderId, long parentFolderId,
			LocalRepository sourceLocalRepository,
			LocalRepository destinationLocalRepository,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		Folder sourceFolder = sourceLocalRepository.getFolder(folderId);

		Folder destinationFolder = destinationLocalRepository.addFolder(
			userId, parentFolderId, sourceFolder.getName(),
			sourceFolder.getDescription(), serviceContext);

		dlAppHelperLocalService.addFolder(
			userId, destinationFolder, serviceContext);

		List<Object> foldersAndFileEntriesAndFileShortcuts =
			dlAppService.getFoldersAndFileEntriesAndFileShortcuts(
				sourceLocalRepository.getRepositoryId(), folderId,
				WorkflowConstants.STATUS_ANY, true, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS);

		try {
			for (Object folderAndFileEntryAndFileShortcut :
					foldersAndFileEntriesAndFileShortcuts) {

				if (folderAndFileEntryAndFileShortcut instanceof FileEntry) {
					FileEntry fileEntry =
						(FileEntry)folderAndFileEntryAndFileShortcut;

					copyFileEntry(
						userId, destinationLocalRepository, fileEntry,
						destinationFolder.getFolderId(), serviceContext);
				}
				else if (folderAndFileEntryAndFileShortcut instanceof Folder) {
					Folder folder = (Folder)folderAndFileEntryAndFileShortcut;

					moveFolders(
						userId, folder.getFolderId(),
						destinationFolder.getFolderId(), sourceLocalRepository,
						destinationLocalRepository, serviceContext);
				}
				else if (folderAndFileEntryAndFileShortcut
							instanceof DLFileShortcut) {

					if (destinationFolder.isSupportsShortcuts()) {
						DLFileShortcut dlFileShorcut =
							(DLFileShortcut)folderAndFileEntryAndFileShortcut;

						dlFileShortcutLocalService.addFileShortcut(
							userId, dlFileShorcut.getGroupId(),
							destinationFolder.getFolderId(),
							dlFileShorcut.getToFileEntryId(), serviceContext);
					}
				}
			}
		}
		catch (PortalException pe) {
			destinationLocalRepository.deleteFolder(
				destinationFolder.getFolderId());

			throw pe;
		}

		try {
			sourceLocalRepository.deleteFolder(folderId);
		}
		catch (PortalException pe) {
			destinationLocalRepository.deleteFolder(
				destinationFolder.getFolderId());

			throw pe;
		}

		return destinationFolder;
	}

}