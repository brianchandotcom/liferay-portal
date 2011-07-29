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
import com.liferay.portal.kernel.repository.LocalRepository;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.repository.liferayrepository.model.LiferayFolder;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.documentlibrary.FileSizeException;
import com.liferay.portlet.documentlibrary.NoSuchFileEntryException;
import com.liferay.portlet.documentlibrary.NoSuchFileException;
import com.liferay.portlet.documentlibrary.model.DLFileRank;
import com.liferay.portlet.documentlibrary.model.DLFileShortcut;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.service.base.DLAppLocalServiceBaseImpl;
import com.liferay.portlet.documentlibrary.util.DLProcessorRegistryUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import java.util.List;

/**
 * @author Alexander Chow
 * @author Mika Koivisto
 * @see    DLAppServiceImpl
 */
public class DLAppLocalServiceImpl extends DLAppLocalServiceBaseImpl {

	public FileEntry addFileEntry(
			long userId, long repositoryId, long folderId,
			String sourceFileName, String mimeType, String title,
			String description, String changeLog, byte[] bytes,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		if (bytes == null) {
			bytes = new byte[0];
		}

		InputStream is = new UnsyncByteArrayInputStream(bytes);

		return addFileEntry(
			userId, repositoryId, folderId, sourceFileName, mimeType, title,
			description, changeLog, is, bytes.length, serviceContext);
	}

	public FileEntry addFileEntry(
			long userId, long repositoryId, long folderId,
			String sourceFileName, String mimeType, String title,
			String description, String changeLog, File file,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		InputStream is = null;

		try {
			long size = 0;

			if (file == null) {
				is = new UnsyncByteArrayInputStream(new byte[0]);
			}
			else {
				is = new FileInputStream(file);
				size = file.length();
			}

			return addFileEntry(
				userId, repositoryId, folderId, sourceFileName, mimeType, title,
				description, changeLog, is, size, serviceContext);
		}
		catch (FileNotFoundException fnfe) {
			throw new FileSizeException();
		}
		finally {
			if (is != null) {
				try {
					is.close();
				}
				catch (IOException ioe) {
				}
			}
		}
	}

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

		LocalRepository localRepository = getLocalRepository(repositoryId);

		FileEntry fileEntry = localRepository.addFileEntry(
			userId, folderId, sourceFileName, mimeType, title, description,
			changeLog, is, size, serviceContext);

		DLProcessorRegistryUtil.trigger(fileEntry);

		return fileEntry;
	}

	public DLFileRank addFileRank(
			long repositoryId, long companyId, long userId, long fileEntryId,
			ServiceContext serviceContext)
		throws SystemException {

		return dlFileRankLocalService.addFileRank(
			repositoryId, companyId, userId, fileEntryId, serviceContext);
	}

	public DLFileShortcut addFileShortcut(
			long userId, long repositoryId, long folderId, long toFileEntryId,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		return dlFileShortcutLocalService.addFileShortcut(
			userId, repositoryId, folderId, toFileEntryId, serviceContext);
	}

	public Folder addFolder(
			long userId, long repositoryId, long parentFolderId, String name,
			String description, ServiceContext serviceContext)
		throws PortalException, SystemException {

		LocalRepository localRepository = getLocalRepository(repositoryId);

		return localRepository.addFolder(
			userId, parentFolderId, name, description, serviceContext);
	}

	public void deleteAll(long repositoryId)
		throws PortalException, SystemException {

		LocalRepository localRepository = getLocalRepository(repositoryId);

		localRepository.deleteAll();
	}

	public void deleteFileEntry(long fileEntryId)
		throws PortalException, SystemException {

		LocalRepository localRepository = getLocalRepository(0, fileEntryId, 0);

		localRepository.deleteFileEntry(fileEntryId);
	}

	public void deleteFileRanksByFileEntryId(long fileEntryId)
		throws SystemException {

		dlFileRankLocalService.deleteFileRanksByFileEntryId(fileEntryId);
	}

	public void deleteFileRanksByUserId(long userId) throws SystemException {
		dlFileRankLocalService.deleteFileRanksByUserId(userId);
	}

	public void deleteFileShortcut(DLFileShortcut dlFileShortcut)
		throws PortalException, SystemException {

		dlFileShortcutLocalService.deleteFileShortcut(dlFileShortcut);
	}

	public void deleteFileShortcut(long fileShortcutId)
		throws PortalException, SystemException {

		dlFileShortcutLocalService.deleteDLFileShortcut(fileShortcutId);
	}

	public void deleteFileShortcuts(long toFileEntryId)
		throws PortalException, SystemException {

		dlFileShortcutLocalService.deleteFileShortcuts(toFileEntryId);
	}

	public void deleteFolder(long folderId)
		throws PortalException, SystemException {

		LocalRepository localRepository = getLocalRepository(folderId, 0, 0);

		localRepository.deleteFolder(folderId);
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

		LocalRepository localRepository = getLocalRepository(repositoryId);

		return localRepository.getFileEntries(folderId, start, end, obc);
	}

	public List<Object> getFileEntriesAndFileShortcuts(
			long repositoryId, long folderId, int status, int start,
			int end)
		throws PortalException, SystemException {

		LocalRepository localRepository = getLocalRepository(repositoryId);

		return localRepository.getFileEntriesAndFileShortcuts(
			folderId, status, start, end);
	}

	public int getFileEntriesAndFileShortcutsCount(
			long repositoryId, long folderId, int status)
		throws PortalException, SystemException {

		LocalRepository localRepository = getLocalRepository(repositoryId);

		return localRepository.getFileEntriesAndFileShortcutsCount(
			folderId, status);
	}

	public int getFileEntriesCount(long repositoryId, long folderId)
		throws PortalException, SystemException {

		LocalRepository localRepository = getLocalRepository(repositoryId);

		return localRepository.getFileEntriesCount(folderId);
	}

	public FileEntry getFileEntry(long fileEntryId)
		throws PortalException, SystemException {

		LocalRepository localRepository = getLocalRepository(0, fileEntryId, 0);

		return localRepository.getFileEntry(fileEntryId);
	}

	public FileEntry getFileEntry(long groupId, long folderId, String title)
		throws PortalException, SystemException {

		try {
			LocalRepository localRepository = getLocalRepository(groupId);

			return localRepository.getFileEntry(folderId, title);
		}
		catch (NoSuchFileEntryException nsfee) {
		}

		LocalRepository localRepository = getLocalRepository(folderId, 0, 0);

		return localRepository.getFileEntry(folderId, title);
	}

	public FileEntry getFileEntryByUuidAndGroupId(
			String uuid, long groupId)
		throws PortalException, SystemException {

		try {
			LocalRepository localRepository = getLocalRepository(groupId);

			return localRepository.getFileEntryByUuid(uuid);
		}
		catch (NoSuchFileEntryException nsfee) {
			List<com.liferay.portal.model.Repository> repositories =
				repositoryPersistence.findByGroupId(groupId);

			for (int i = 0; i < repositories.size(); i++) {
				try {
					long repositoryId = repositories.get(i).getRepositoryId();

					LocalRepository localRepository = getLocalRepository(
						repositoryId);

					return localRepository.getFileEntryByUuid(uuid);
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

	public List<DLFileRank> getFileRanks(long repositoryId, long userId)
		throws SystemException {

		return dlFileRankLocalService.getFileRanks(repositoryId, userId);
	}

	public DLFileShortcut getFileShortcut(long fileShortcutId)
		throws PortalException, SystemException {

		return dlFileShortcutLocalService.getFileShortcut(fileShortcutId);
	}

	public FileVersion getFileVersion(long fileVersionId)
		throws PortalException, SystemException {

		LocalRepository localRepository = getLocalRepository(
			0, 0, fileVersionId);

		return localRepository.getFileVersion(fileVersionId);
	}

	public Folder getFolder(long folderId)
		throws PortalException, SystemException {

		LocalRepository localRepository = getLocalRepository(folderId, 0, 0);

		return localRepository.getFolder(folderId);
	}

	public Folder getFolder(
			long repositoryId, long parentFolderId, String name)
		throws PortalException, SystemException {

		LocalRepository localRepository = getLocalRepository(repositoryId);

		return localRepository.getFolder(parentFolderId, name);
	}

	public List<Folder> getFolders(long repositoryId, long parentFolderId)
		throws PortalException, SystemException {

		return getFolders(repositoryId, parentFolderId, true);
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
			repositoryId, parentFolderId, includeMountFolders,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<Folder> getFolders(
			long repositoryId, long parentFolderId, boolean includeMountFolders,
			int start, int end, OrderByComparator obc)
		throws PortalException, SystemException {

		LocalRepository localRepository = getLocalRepository(repositoryId);

		return localRepository.getFolders(
			parentFolderId, includeMountFolders, start, end, obc);
	}

	public List<Folder> getFolders(
			long repositoryId, long parentFolderId, int start, int end)
		throws PortalException, SystemException {

		return getFolders(repositoryId, parentFolderId, true, start, end);
	}

	public List<Folder> getFolders(
			long repositoryId, long parentFolderId, int start, int end,
			OrderByComparator obc)
		throws PortalException, SystemException {

		return getFolders(repositoryId, parentFolderId, true, start, end, obc);
	}

	public List<Object> getFoldersAndFileEntriesAndFileShortcuts(
			long repositoryId, long folderId, int status,
			boolean includeMountFolders, int start, int end,
			OrderByComparator obc)
		throws PortalException, SystemException {

		LocalRepository localRepository = getLocalRepository(repositoryId);

		return localRepository.getFoldersAndFileEntriesAndFileShortcuts(
			folderId, status, includeMountFolders, start, end, obc);
	}

	public int getFoldersAndFileEntriesAndFileShortcutsCount(
			long repositoryId, long folderId, int status,
			boolean includeMountFolders)
		throws PortalException, SystemException {

		LocalRepository localRepository = getLocalRepository(repositoryId);

		return localRepository.getFoldersAndFileEntriesAndFileShortcutsCount(
			folderId, status, includeMountFolders);
	}

	public int getFoldersCount(long repositoryId, long parentFolderId)
		throws PortalException, SystemException {

		return getFoldersCount(repositoryId, parentFolderId, true);
	}

	public int getFoldersCount(
			long repositoryId, long parentFolderId, boolean includeMountfolders)
		throws PortalException, SystemException {

		LocalRepository localRepository = getLocalRepository(repositoryId);

		return localRepository.getFoldersCount(
			parentFolderId, includeMountfolders);
	}

	public int getFoldersFileEntriesCount(
			long repositoryId, List<Long> folderIds, int status)
		throws PortalException, SystemException {

		LocalRepository localRepository = getLocalRepository(repositoryId);

		return localRepository.getFoldersFileEntriesCount(folderIds, status);
	}

	public Folder getMountFolder(long repositoryId)
		throws PortalException, SystemException {

		DLFolder dlFolder = dlFolderLocalService.getMountFolder(repositoryId);

		return new LiferayFolder(dlFolder);
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

		LocalRepository localRepository = getLocalRepository(repositoryId);

		return localRepository.getMountFolders(parentFolderId, start, end, obc);
	}

	public int getMountFoldersCount(long repositoryId, long parentFolderId)
		throws PortalException, SystemException {

		LocalRepository localRepository = getLocalRepository(repositoryId);

		return localRepository.getMountFoldersCount(parentFolderId);
	}

	public FileEntry moveFileEntry(
			long userId, long fileEntryId, long newFolderId,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		LocalRepository localRepository = getLocalRepository(0, fileEntryId, 0);

		return localRepository.moveFileEntry(
			userId, fileEntryId, newFolderId, serviceContext);
	}

	public void updateAsset(
			long userId, FileEntry fileEntry, FileVersion fileVersion,
			long[] assetCategoryIds, String[] assetTagNames,
			long[] assetLinkEntryIds)
		throws PortalException, SystemException {

		LocalRepository localRepository = getLocalRepository(
			0, fileEntry.getFileEntryId(), 0);

		localRepository.updateAsset(
			userId, fileEntry, fileVersion, assetCategoryIds, assetTagNames,
			assetLinkEntryIds);
	}

	public FileEntry updateFileEntry(
			long userId, long fileEntryId, String sourceFileName,
			String mimeType, String title, String description, String changeLog,
			boolean majorVersion, byte[] bytes, ServiceContext serviceContext)
		throws PortalException, SystemException {

		InputStream is = null;
		long size = 0;

		if (bytes != null) {
			is = new UnsyncByteArrayInputStream(bytes);
			size = bytes.length;
		}

		return updateFileEntry(
			userId, fileEntryId, sourceFileName, mimeType, title, description,
			changeLog, majorVersion, is, size, serviceContext);
	}

	public FileEntry updateFileEntry(
			long userId, long fileEntryId, String sourceFileName,
			String mimeType, String title, String description, String changeLog,
			boolean majorVersion, File file, ServiceContext serviceContext)
		throws PortalException, SystemException {

		InputStream is = null;

		try {
			long size = 0;

			if ((file != null) && file.exists()) {
				is = new FileInputStream(file);
				size = file.length();
			}

			return updateFileEntry(
				userId, fileEntryId, sourceFileName, mimeType, title,
				description, changeLog, majorVersion, is, size, serviceContext);
		}
		catch (FileNotFoundException fnfe) {
			throw new NoSuchFileException();
		}
		finally {
			if (is != null) {
				try {
					is.close();
				}
				catch (IOException ioe) {
				}
			}
		}
	}

	public FileEntry updateFileEntry(
			long userId, long fileEntryId, String sourceFileName,
			String mimeType, String title, String description, String changeLog,
			boolean majorVersion, InputStream is, long size,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		LocalRepository localRepository = getLocalRepository(0, fileEntryId, 0);

		FileEntry fileEntry = localRepository.updateFileEntry(
			userId, fileEntryId, sourceFileName, mimeType, title, description,
			changeLog, majorVersion, is, size, serviceContext);

		DLProcessorRegistryUtil.trigger(fileEntry);

		return fileEntry;
	}

	public DLFileRank updateFileRank(
			long repositoryId, long companyId, long userId, long fileEntryId,
			ServiceContext serviceContext)
		throws SystemException {

		return dlFileRankLocalService.updateFileRank(
			repositoryId, companyId, userId, fileEntryId, serviceContext);
	}

	public DLFileShortcut updateFileShortcut(
			long userId, long fileShortcutId, long folderId, long toFileEntryId,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		return dlFileShortcutLocalService.updateFileShortcut(
			userId, fileShortcutId, folderId, toFileEntryId, serviceContext);
	}

	public void updateFileShortcuts(
			long torepositoryId, long oldToFileEntryId, long newToFileEntryId)
		throws SystemException {

		dlFileShortcutLocalService.updateFileShortcuts(
			oldToFileEntryId, newToFileEntryId);
	}

	public Folder updateFolder(
			long folderId, long parentFolderId, String name, String description,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		LocalRepository localRepository = getLocalRepository(folderId, 0, 0);

		return localRepository.updateFolder(
			folderId, parentFolderId, name, description, serviceContext);
	}

	protected LocalRepository getLocalRepository(long repositoryId)
		throws PortalException, SystemException {

		return repositoryService.getLocalRepositoryImpl(repositoryId);
	}

	protected LocalRepository getLocalRepository(
			long folderId, long fileEntryId, long fileVersionId)
		throws PortalException, SystemException {

		return repositoryService.getLocalRepositoryImpl(
			folderId, fileEntryId, fileVersionId);
	}

}