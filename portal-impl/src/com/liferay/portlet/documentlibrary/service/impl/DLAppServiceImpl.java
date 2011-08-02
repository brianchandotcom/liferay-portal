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
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.TempFileUtil;
import com.liferay.portal.model.Lock;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.documentlibrary.FileSizeException;
import com.liferay.portlet.documentlibrary.NoSuchFileEntryException;
import com.liferay.portlet.documentlibrary.NoSuchFileException;
import com.liferay.portlet.documentlibrary.model.DLFileShortcut;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.service.base.DLAppServiceBaseImpl;
import com.liferay.portlet.documentlibrary.service.permission.DLFolderPermission;
import com.liferay.portlet.documentlibrary.util.DLProcessorRegistryUtil;
import com.liferay.portlet.documentlibrary.util.comparator.RepositoryModelModifiedDateComparator;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import java.util.List;

/**
 * The document library remote service. All portlets should interact with the
 * document library through this class or through {@link DLAppLocalServiceImpl},
 * rather than through the individual document library service classes.
 *
 * <p>
 * This class provides a unified interface to all Liferay and third party
 * repositories. While the method signatures are universal for all repositories,
 * additional parameters may be specified in the serviceContext for added
 * flexibility, especially within Liferay's repository during creation and
 * update. In particular, noteworthy parameters include:
 * </p>
 *
 * <ul>
 * <li>
 * fileEntryTypeId - ID for a custom file entry type
 * </li>
 * <li>
 * fieldsMap - mapping for fields associated with a custom document type
 * </li>
 * <li>
 * sourceFileName - original filename of the file being uploaded
 * </li>
 * </ul>
 *
 * @author Alexander Chow
 * @author Mika Koivisto
 */
public class DLAppServiceImpl extends DLAppServiceBaseImpl {

	public FileEntry addFileEntry(
			long repositoryId, long folderId, String sourceFileName,
			String mimeType, String title, String description, String changeLog,
			byte[] bytes, ServiceContext serviceContext)
		throws PortalException, SystemException {

		if (bytes == null) {
			bytes = new byte[0];
		}

		InputStream is = new UnsyncByteArrayInputStream(bytes);

		return addFileEntry(
			repositoryId, folderId, sourceFileName, mimeType, title,
			description, changeLog, is, bytes.length, serviceContext);
	}

	public FileEntry addFileEntry(
			long repositoryId, long folderId, String sourceFileName,
			String mimeType, String title, String description, String changeLog,
			File file, ServiceContext serviceContext)
		throws PortalException, SystemException {

		try {
			InputStream is = null;
			long size = 0;

			if ((file == null) || !file.exists()) {
				is = new UnsyncByteArrayInputStream(new byte[0]);
			}
			else {
				is = new FileInputStream(file);
				size = file.length();
			}

			return addFileEntry(
				repositoryId, folderId, sourceFileName, mimeType, title,
				description, changeLog, is, size, serviceContext);
		}
		catch (FileNotFoundException fnfe) {
			throw new FileSizeException();
		}
	}

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

		DLProcessorRegistryUtil.trigger(fileEntry);

		return fileEntry;
	}

	public DLFileShortcut addFileShortcut(
			long repositoryId, long folderId, long toFileEntryId,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		return dlFileShortcutService.addFileShortcut(
			repositoryId, folderId, toFileEntryId, serviceContext);
	}

	public Folder addFolder(
			long repositoryId, long parentFolderId, String name,
			String description, ServiceContext serviceContext)
		throws PortalException, SystemException {

		Repository repository = getRepository(repositoryId);

		return repository.addFolder(
			parentFolderId, name, description, serviceContext);
	}

	public String addTempFileEntry(
			long groupId, long folderId, String fileName, String tempFolderName,
			File file)
		throws IOException, PortalException, SystemException {

		DLFolderPermission.check(
			getPermissionChecker(), groupId, folderId, ActionKeys.ADD_DOCUMENT);

		return TempFileUtil.addTempFile(
			getUserId(), fileName, tempFolderName, file);
	}

	public void cancelCheckOut(long fileEntryId)
		throws PortalException, SystemException {

		Repository repository = getRepository(0, fileEntryId, 0);

		repository.cancelCheckOut(fileEntryId);
	}

	public void checkInFileEntry(
			long fileEntryId, boolean major, String changeLog,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		Repository repository = getRepository(0, fileEntryId, 0);

		repository.checkInFileEntry(
			fileEntryId, major, changeLog, serviceContext);
	}

	public void checkInFileEntry(long fileEntryId, String lockUuid)
		throws PortalException, SystemException {

		Repository repository = getRepository(0, fileEntryId, 0);

		repository.checkInFileEntry(fileEntryId, lockUuid);
	}

	public void checkOutFileEntry(long fileEntryId)
		throws PortalException, SystemException {

		Repository repository = getRepository(0, fileEntryId, 0);

		repository.checkOutFileEntry(fileEntryId);
	}

	public FileEntry checkOutFileEntry(
			long fileEntryId, String owner, long expirationTime)
		throws PortalException, SystemException {

		Repository repository = getRepository(0, fileEntryId, 0);

		return repository.checkOutFileEntry(fileEntryId, owner, expirationTime);
	}

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

	public void deleteFileEntry(long fileEntryId)
		throws PortalException, SystemException {

		Repository repository = getRepository(0, fileEntryId, 0);

		repository.deleteFileEntry(fileEntryId);
	}

	public void deleteFileEntryByTitle(
			long repositoryId, long folderId, String title)
		throws PortalException, SystemException {

		Repository repository = getRepository(repositoryId);

		repository.deleteFileEntry(folderId, title);
	}

	public void deleteFileShortcut(long fileShortcutId)
		throws PortalException, SystemException {

		dlFileShortcutService.deleteFileShortcut(fileShortcutId);
	}

	public void deleteFolder(long folderId)
		throws PortalException, SystemException {

		Repository repository = getRepository(folderId, 0, 0);

		repository.deleteFolder(folderId);
	}

	public void deleteFolder(
			long repositoryId, long parentFolderId, String name)
		throws PortalException, SystemException {

		Repository repository = getRepository(repositoryId);

		repository.deleteFolder(parentFolderId, name);
	}

	public void deleteTempFileEntry(
			long groupId, long folderId, String fileName, String tempFolderName)
		throws PortalException, SystemException {

		DLFolderPermission.check(
			getPermissionChecker(), groupId, folderId, ActionKeys.ADD_DOCUMENT);

		TempFileUtil.deleteTempFile(getUserId(), fileName, tempFolderName);
	}

	public List<FileEntry> getFileEntries(long repositoryId, long folderId)
		throws PortalException, SystemException {

		return getFileEntries(
			repositoryId, folderId, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	public List<FileEntry> getFileEntries(
			long repositoryId, long folderId, int start, int end)
		throws PortalException, SystemException {

		return getFileEntries(repositoryId, folderId, start, end, null);
	}

	public List<FileEntry> getFileEntries(
			long repositoryId, long folderId, int start, int end,
			OrderByComparator obc)
		throws PortalException, SystemException {

		Repository repository = getRepository(repositoryId);

		return repository.getFileEntries(folderId, start, end, obc);
	}

	public List<FileEntry> getFileEntries(
			long repositoryId, long folderId, long fileEntryTypeId)
		throws PortalException, SystemException {

		return getFileEntries(
			repositoryId, folderId, fileEntryTypeId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS);
	}

	public List<FileEntry> getFileEntries(
			long repositoryId, long folderId, long fileEntryTypeId, int start,
			int end)
		throws PortalException, SystemException {

		return getFileEntries(
			repositoryId, folderId, fileEntryTypeId, start, end, null);
	}

	public List<FileEntry> getFileEntries(
			long repositoryId, long folderId, long fileEntryTypeId, int start,
			int end, OrderByComparator obc)
		throws PortalException, SystemException {

		Repository repository = getRepository(repositoryId);

		return repository.getFileEntries(
			folderId, fileEntryTypeId, start, end, obc);
	}

	public List<Object> getFileEntriesAndFileShortcuts(
			long repositoryId, long folderId, int status, int start, int end)
		throws PortalException, SystemException {

		Repository repository = getRepository(repositoryId);

		return repository.getFileEntriesAndFileShortcuts(
			folderId, status, start, end);
	}

	public int getFileEntriesAndFileShortcutsCount(
			long repositoryId, long folderId, int status)
		throws PortalException, SystemException {

		Repository repository = getRepository(repositoryId);

		return repository.getFileEntriesAndFileShortcutsCount(
			folderId, status);
	}

	public int getFileEntriesCount(long repositoryId, long folderId)
		throws PortalException, SystemException {

		Repository repository = getRepository(repositoryId);

		return repository.getFileEntriesCount(folderId);
	}

	public int getFileEntriesCount(
			long repositoryId, long folderId, long fileEntryTypeId)
		throws PortalException, SystemException {

		Repository repository = getRepository(repositoryId);

		return repository.getFileEntriesCount(folderId, fileEntryTypeId);
	}

	public FileEntry getFileEntry(long fileEntryId)
		throws PortalException, SystemException {

		Repository repository = getRepository(0, fileEntryId, 0);

		return repository.getFileEntry(fileEntryId);
	}

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

	public DLFileShortcut getFileShortcut(long fileShortcutId)
		throws PortalException, SystemException {

		return dlFileShortcutService.getFileShortcut(fileShortcutId);
	}

	public Folder getFolder(long folderId)
		throws PortalException, SystemException {

		Repository repository = getRepository(folderId, 0, 0);

		return repository.getFolder(folderId);
	}

	public Folder getFolder(long repositoryId, long parentFolderId, String name)
		throws PortalException, SystemException {

		Repository repository = getRepository(repositoryId);

		return repository.getFolder(parentFolderId, name);
	}

	public List<Folder> getFolders(long repositoryId, long parentFolderId)
		throws PortalException, SystemException {

		return getFolders(
			repositoryId, parentFolderId, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	public List<Folder> getFolders(
			long repositoryId, long parentFolderId, boolean includeMountFolders)
		throws PortalException, SystemException {

		return getFolders(
			repositoryId, parentFolderId, includeMountFolders,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	public List<Folder> getFolders(
			long repositoryId, long parentFolderId, boolean includeMountFolders,
			int start, int end)
		throws PortalException, SystemException {

		return getFolders(
			repositoryId, parentFolderId, includeMountFolders, start, end,
			null);
	}

	public List<Folder> getFolders(
			long repositoryId, long parentFolderId, boolean includeMountFolders,
			int start, int end,	OrderByComparator obc)
		throws PortalException, SystemException {

		Repository repository = getRepository(repositoryId);

		return repository.getFolders(
			parentFolderId, includeMountFolders, start, end, obc);
	}

	public List<Folder> getFolders(
			long repositoryId, long parentFolderId, int start, int end)
		throws PortalException, SystemException {

		return getFolders(repositoryId, parentFolderId, start, end, null);
	}

	public List<Folder> getFolders(
			long repositoryId, long parentFolderId, int start, int end,
			OrderByComparator obc)
		throws PortalException, SystemException {

		Repository repository = getRepository(repositoryId);

		return repository.getFolders(parentFolderId, true, start, end, obc);
	}

	public List<Object> getFoldersAndFileEntriesAndFileShortcuts(
			long repositoryId, long folderId, int status,
			boolean includeMountFolders, int start, int end)
		throws PortalException, SystemException {

		return getFoldersAndFileEntriesAndFileShortcuts(
			repositoryId, folderId, status, includeMountFolders, start, end,
			null);
	}

	public List<Object> getFoldersAndFileEntriesAndFileShortcuts(
			long repositoryId, long folderId, int status,
			boolean includeMountFolders, int start, int end,
			OrderByComparator obc)
		throws PortalException, SystemException {

		Repository repository = getRepository(repositoryId);

		return repository.getFoldersAndFileEntriesAndFileShortcuts(
			folderId, status, includeMountFolders, start, end, obc);
	}

	public int getFoldersAndFileEntriesAndFileShortcutsCount(
			long repositoryId, long folderId, int status,
			boolean includeMountFolders)
		throws PortalException, SystemException {

		Repository repository = getRepository(repositoryId);

		return repository.getFoldersAndFileEntriesAndFileShortcutsCount(
			folderId, status, includeMountFolders);
	}

	public int getFoldersCount(long repositoryId, long parentFolderId)
		throws PortalException, SystemException {

		return getFoldersCount(repositoryId, parentFolderId, true);
	}

	public int getFoldersCount(
			long repositoryId, long parentFolderId, boolean includeMountFolders)
		throws PortalException, SystemException {

		Repository repository = getRepository(repositoryId);

		return repository.getFoldersCount(parentFolderId, includeMountFolders);
	}

	public int getFoldersFileEntriesCount(
			long repositoryId, List<Long> folderIds, int status)
		throws PortalException, SystemException {

		Repository repository = getRepository(repositoryId);

		return repository.getFoldersFileEntriesCount(folderIds, status);
	}

	public List<FileEntry> getGroupFileEntries(
			long repositoryId, long userId, int start, int end)
		throws PortalException, SystemException {

		return getGroupFileEntries(
			repositoryId, userId, DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			start, end, new RepositoryModelModifiedDateComparator());
	}

	public List<FileEntry> getGroupFileEntries(
			long repositoryId, long userId, int start, int end,
			OrderByComparator obc)
		throws PortalException, SystemException {

		return getGroupFileEntries(
			repositoryId, userId, DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			start, end, obc);
	}

	public List<FileEntry> getGroupFileEntries(
			long repositoryId, long userId, long rootFolderId, int start,
			int end)
		throws PortalException, SystemException {

		return getGroupFileEntries(
			repositoryId, userId, rootFolderId, start, end,
			new RepositoryModelModifiedDateComparator());
	}

	public List<FileEntry> getGroupFileEntries(
			long repositoryId, long userId, long rootFolderId, int start,
			int end, OrderByComparator obc)
		throws PortalException, SystemException {

		Repository repository = getRepository(repositoryId);

		return repository.getRepositoryFileEntries(
			userId, rootFolderId, start, end, obc);
	}

	public int getGroupFileEntriesCount(long repositoryId, long userId)
		throws PortalException, SystemException {

		return getGroupFileEntriesCount(
			repositoryId, userId, DLFolderConstants.DEFAULT_PARENT_FOLDER_ID);
	}

	public int getGroupFileEntriesCount(
			long repositoryId, long userId, long rootFolderId)
		throws PortalException, SystemException {

		Repository repository = getRepository(repositoryId);

		return repository.getRepositoryFileEntriesCount(userId, rootFolderId);
	}

	public List<Folder> getMountFolders(long repositoryId, long parentFolderId)
		throws PortalException, SystemException {

		return getMountFolders(
			repositoryId, parentFolderId, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	public List<Folder> getMountFolders(
			long repositoryId, long parentFolderId, int start, int end)
		throws PortalException, SystemException {

		return getMountFolders(repositoryId, parentFolderId, start, end, null);
	}

	public List<Folder> getMountFolders(
			long repositoryId, long parentFolderId, int start, int end,
			OrderByComparator obc)
		throws PortalException, SystemException {

		Repository repository = getRepository(repositoryId);

		return repository.getMountFolders(parentFolderId, start, end, obc);
	}

	public List<Long> getSubfolderIds(long repositoryId, long folderId)
		throws PortalException, SystemException {

		return getSubfolderIds(repositoryId, folderId, true);
	}

	public List<Long> getSubfolderIds(
			long repositoryId, long folderId, boolean recurse)
		throws PortalException, SystemException {

		Repository repository = getRepository(repositoryId);

		return repository.getSubfolderIds(folderId, recurse);
	}

	public String[] getTempFileEntryNames(
			long groupId, long folderId, String tempFolderName)
		throws PortalException, SystemException {

		DLFolderPermission.check(
			getPermissionChecker(), groupId, folderId, ActionKeys.ADD_DOCUMENT);

		return TempFileUtil.getTempFileEntryNames(getUserId(), tempFolderName);
	}

	public Lock lockFolder(long repositoryId, long folderId)
		throws PortalException, SystemException {

		Repository repository = getRepository(repositoryId);

		return repository.lockFolder(folderId);
	}

	public Lock lockFolder(
			long repositoryId, long folderId, String owner, boolean inheritable,
			long expirationTime)
		throws PortalException, SystemException {

		Repository repository = getRepository(repositoryId);

		return repository.lockFolder(
			folderId, owner, inheritable, expirationTime);
	}

	public FileEntry moveFileEntry(
			long fileEntryId, long newFolderId, ServiceContext serviceContext)
		throws PortalException, SystemException {

		Repository repository = getRepository(0, fileEntryId, 0);

		return repository.moveFileEntry(
			fileEntryId, newFolderId, serviceContext);
	}

	public Folder moveFolder(
			long folderId, long parentFolderId, ServiceContext serviceContext)
		throws PortalException, SystemException {

		Repository repository = getRepository(folderId, 0, 0);

		return repository.moveFolder(folderId, parentFolderId, serviceContext);
	}

	public Lock refreshFileEntryLock(String lockUuid, long expirationTime)
		throws PortalException, SystemException {

		Lock lock = lockLocalService.getLockByUuid(lockUuid);

		long fileEntryId = GetterUtil.getLong(lock.getKey());

		Repository repository = getRepository(0, fileEntryId, 0);

		return repository.refreshFileEntryLock(lockUuid, expirationTime);
	}

	public Lock refreshFolderLock(String lockUuid, long expirationTime)
		throws PortalException, SystemException {

		Lock lock = lockLocalService.getLockByUuid(lockUuid);

		long folderId = GetterUtil.getLong(lock.getKey());

		Repository repository = getRepository(0, folderId, 0);

		return repository.refreshFolderLock(lockUuid, expirationTime);
	}

	public void revertFileEntry(
			long fileEntryId, String version, ServiceContext serviceContext)
		throws PortalException, SystemException {

		Repository repository = getRepository(0, fileEntryId, 0);

		repository.revertFileEntry(fileEntryId, version, serviceContext);
	}

	public void unlockFolder(long repositoryId, long folderId, String lockUuid)
		throws PortalException, SystemException {

		Repository repository = getRepository(repositoryId);

		repository.unlockFolder(folderId, lockUuid);
	}

	public void unlockFolder(
			long repositoryId, long parentFolderId, String name,
			String lockUuid)
		throws PortalException, SystemException {

		Repository repository = getRepository(repositoryId);

		repository.unlockFolder(parentFolderId, name, lockUuid);
	}

	public FileEntry updateFileEntry(
			long fileEntryId, String sourceFileName, String mimeType,
			String title, String description, String changeLog,
			boolean majorVersion, byte[] bytes, ServiceContext serviceContext)
		throws PortalException, SystemException {

		InputStream is = null;
		long size = 0;

		if (bytes != null) {
			is = new UnsyncByteArrayInputStream(bytes);
			size = bytes.length;
		}

		return updateFileEntry(
			fileEntryId, sourceFileName, mimeType, title, description,
			changeLog, majorVersion, is, size, serviceContext);
	}

	public FileEntry updateFileEntry(
			long fileEntryId, String sourceFileName, String mimeType,
			String title, String description, String changeLog,
			boolean majorVersion, File file, ServiceContext serviceContext)
		throws PortalException, SystemException {

		try {
			InputStream is = null;
			long size = 0;

			if ((file != null) && file.exists()) {
				is = new FileInputStream(file);
				size = file.length();
			}

			return updateFileEntry(
				fileEntryId, sourceFileName, mimeType, title, description,
				changeLog, majorVersion, is, size, serviceContext);
		}
		catch (FileNotFoundException fnfe) {
			throw new NoSuchFileException();
		}
	}

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

		DLProcessorRegistryUtil.trigger(fileEntry);

		return fileEntry;
	}

	public DLFileShortcut updateFileShortcut(
			long fileShortcutId, long folderId, long toFileEntryId,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		return dlFileShortcutService.updateFileShortcut(
			fileShortcutId, folderId, toFileEntryId, serviceContext);
	}

	public Folder updateFolder(
			long folderId, String name, String description,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		Repository repository = getRepository(folderId, 0, 0);

		return repository.updateFolder(
			folderId, name, description, serviceContext);
	}

	public boolean verifyFileEntryCheckOut(
			long repositoryId, long fileEntryId, String lockUuid)
		throws PortalException, SystemException {

		Repository repository = getRepository(repositoryId);

		return repository.verifyFileEntryCheckOut(fileEntryId, lockUuid);
	}

	public boolean verifyInheritableLock(
			long repositoryId, long folderId, String lockUuid)
		throws PortalException, SystemException {

		Repository repository = getRepository(repositoryId);

		return repository.verifyInheritableLock(
			folderId, lockUuid);
	}

	protected void copyFolder(
			Repository repository, Folder srcFolder, Folder destFolder,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		List<FileEntry> srcFileEntries = repository.getFileEntries(
			srcFolder.getFolderId(), QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);

		for (FileEntry srcFileEntry : srcFileEntries) {
			try {
				FileEntry fileEntry = repository.copyFileEntry(
					destFolder.getGroupId(), srcFileEntry.getFileEntryId(),
					destFolder.getFolderId(), serviceContext);

				DLProcessorRegistryUtil.trigger(fileEntry);
			}
			catch (Exception e) {
				_log.error(e, e);

				continue;
			}
		}

		List<Folder> srcSubfolders = repository.getFolders(
			srcFolder.getFolderId(), false, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);

		for (Folder srcSubfolder : srcSubfolders) {
			Folder destSubfolder = repository.addFolder(
				destFolder.getFolderId(), srcSubfolder.getName(),
				srcSubfolder.getDescription(), serviceContext);

			copyFolder(repository, srcSubfolder, destSubfolder, serviceContext);
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

	private static Log _log = LogFactoryUtil.getLog(DLAppServiceImpl.class);

}