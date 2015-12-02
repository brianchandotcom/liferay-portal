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

package com.liferay.portal.kernel.repository;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.capabilities.Capability;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileShortcut;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.repository.model.RepositoryEntry;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.service.ServiceContext;

import java.io.File;
import java.io.InputStream;

import java.util.List;

/**
 * This class is designed for third party repository implementations. Since the
 * paradigm of remote and local services exists only within Liferay, the
 * assumption is that all permission checking will be delegated to the specific
 * repository.
 *
 * There are also many calls within this class that pass in a user ID as a
 * parameter. These methods should only be called for administration of Liferay
 * repositories and are hence not supported in all third party repositories.
 * This includes moving between document library hooks and LAR import/export.
 * Calling these methods will throw an
 * <code>UnsupportedOperationException</code>.
 *
 * @author Alexander Chow
 */
public class DefaultLocalRepositoryImpl implements LocalRepository {

	public DefaultLocalRepositoryImpl(Repository repository) {
		_repository = repository;
	}

	@Override
	public FileEntry addFileEntry(
		long userId, long folderId, String sourceFileName, String mimeType,
		String title, String description, String changeLog, File file,
		ServiceContext serviceContext) {

		throw new UnsupportedOperationException();
	}

	@Override
	public FileEntry addFileEntry(
		long userId, long folderId, String sourceFileName, String mimeType,
		String title, String description, String changeLog, InputStream is,
		long size, ServiceContext serviceContext) {

		throw new UnsupportedOperationException();
	}

	@Override
	public FileShortcut addFileShortcut(
		long userId, long folderId, long toFileEntryId,
		ServiceContext serviceContext) {

		throw new UnsupportedOperationException();
	}

	@Override
	public Folder addFolder(
		long userId, long parentFolderId, String name, String description,
		ServiceContext serviceContext) {

		throw new UnsupportedOperationException();
	}

	@Override
	public void checkInFileEntry(
			long userId, long fileEntryId, boolean major, String changeLog,
			ServiceContext serviceContext)
		throws PortalException {

		_repository.checkInFileEntry(
			userId, fileEntryId, major, changeLog, serviceContext);
	}

	@Override
	public void checkInFileEntry(
			long userId, long fileEntryId, String lockUuid,
			ServiceContext serviceContext)
		throws PortalException {

		_repository.checkInFileEntry(
			userId, fileEntryId, lockUuid, serviceContext);
	}

	@Override
	public FileEntry copyFileEntry(
			long userId, long groupId, long fileEntryId, long destFolderId,
			ServiceContext serviceContext)
		throws PortalException {

		return _repository.copyFileEntry(
			userId, groupId, fileEntryId, destFolderId, serviceContext);
	}

	@Override
	public void deleteAll() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void deleteFileEntry(long fileEntryId) throws PortalException {
		_repository.deleteFileEntry(fileEntryId);
	}

	@Override
	public void deleteFileShortcut(long fileShortcutId) throws PortalException {
		_repository.deleteFileShortcut(fileShortcutId);
	}

	@Override
	public void deleteFileShortcuts(long toFileEntryId) throws PortalException {
		_repository.deleteFileShortcuts(toFileEntryId);
	}

	@Override
	public void deleteFolder(long folderId) throws PortalException {
		_repository.deleteFolder(folderId);
	}

	@Override
	public <T extends Capability> T getCapability(Class<T> capabilityClass) {
		return _repository.getCapability(capabilityClass);
	}

	@Override
	public List<FileEntry> getFileEntries(
			long folderId, int status, int start, int end,
			OrderByComparator<FileEntry> obc)
		throws PortalException {

		return _repository.getFileEntries(folderId, status, start, end, obc);
	}

	@Override
	public List<FileEntry> getFileEntries(
			long folderId, int start, int end, OrderByComparator<FileEntry> obc)
		throws PortalException {

		return _repository.getFileEntries(folderId, start, end, obc);
	}

	@Override
	public List<RepositoryEntry> getFileEntriesAndFileShortcuts(
			long folderId, int status, int start, int end)
		throws PortalException {

		return _repository.getFileEntriesAndFileShortcuts(
			folderId, status, start, end);
	}

	@Override
	public int getFileEntriesAndFileShortcutsCount(long folderId, int status)
		throws PortalException {

		return _repository.getFileEntriesAndFileShortcutsCount(
			folderId, status);
	}

	@Override
	public int getFileEntriesCount(long folderId) throws PortalException {
		return _repository.getFileEntriesCount(folderId);
	}

	@Override
	public int getFileEntriesCount(long folderId, int status)
		throws PortalException {

		return _repository.getFileEntriesCount(folderId, status);
	}

	@Override
	public FileEntry getFileEntry(long fileEntryId) throws PortalException {
		return _repository.getFileEntry(fileEntryId);
	}

	@Override
	public FileEntry getFileEntry(long folderId, String title)
		throws PortalException {

		return _repository.getFileEntry(folderId, title);
	}

	@Override
	public FileEntry getFileEntryByUuid(String uuid) throws PortalException {
		return _repository.getFileEntryByUuid(uuid);
	}

	@Override
	public FileShortcut getFileShortcut(long fileShortcutId) {
		throw new UnsupportedOperationException();
	}

	@Override
	public FileVersion getFileVersion(long fileVersionId)
		throws PortalException {

		return _repository.getFileVersion(fileVersionId);
	}

	@Override
	public Folder getFolder(long folderId) throws PortalException {
		return _repository.getFolder(folderId);
	}

	@Override
	public Folder getFolder(long parentFolderId, String name)
		throws PortalException {

		return _repository.getFolder(parentFolderId, name);
	}

	@Override
	public List<Folder> getFolders(
			long parentFolderId, boolean includeMountFolders, int start,
			int end, OrderByComparator<Folder> obc)
		throws PortalException {

		return _repository.getFolders(
			parentFolderId, includeMountFolders, start, end, obc);
	}

	@Override
	public List<Folder> getFolders(
			long parentFolderId, int status, boolean includeMountFolders,
			int start, int end, OrderByComparator<Folder> obc)
		throws PortalException {

		return _repository.getFolders(
			parentFolderId, status, includeMountFolders, start, end, obc);
	}

	@Override
	public List<RepositoryEntry> getFoldersAndFileEntriesAndFileShortcuts(
			long folderId, int status, boolean includeMountFolders, int start,
			int end, OrderByComparator<?> obc)
		throws PortalException {

		return _repository.getFoldersAndFileEntriesAndFileShortcuts(
			folderId, status, includeMountFolders, start, end, obc);
	}

	@Override
	public int getFoldersCount(long parentFolderId, boolean includeMountfolders)
		throws PortalException {

		return _repository.getFoldersCount(parentFolderId, includeMountfolders);
	}

	@Override
	public int getFoldersCount(
			long parentFolderId, int status, boolean includeMountfolders)
		throws PortalException {

		return _repository.getFoldersCount(
			parentFolderId, status, includeMountfolders);
	}

	@Override
	public List<FileEntry> getRepositoryFileEntries(
			long userId, long rootFolderId, int start, int end,
			OrderByComparator<FileEntry> obc)
		throws PortalException {

		return _repository.getRepositoryFileEntries(
			userId, rootFolderId, start, end, obc);
	}

	@Override
	public long getRepositoryId() {
		return _repository.getRepositoryId();
	}

	@Override
	public <T extends Capability> boolean isCapabilityProvided(
		Class<T> capabilityClass) {

		return _repository.isCapabilityProvided(capabilityClass);
	}

	@Override
	public FileEntry moveFileEntry(
		long userId, long fileEntryId, long newFolderId,
		ServiceContext serviceContext) {

		throw new UnsupportedOperationException();
	}

	@Override
	public Folder moveFolder(
		long userId, long folderId, long parentFolderId,
		ServiceContext serviceContext) {

		throw new UnsupportedOperationException();
	}

	@Override
	public void revertFileEntry(
			long userId, long fileEntryId, String version,
			ServiceContext serviceContext)
		throws PortalException {

		_repository.revertFileEntry(
			userId, fileEntryId, version, serviceContext);
	}

	/**
	 * @deprecated As of 7.0.0
	 */
	@Deprecated
	@Override
	public void updateAsset(
		long userId, FileEntry fileEntry, FileVersion fileVersion,
		long[] assetCategoryIds, String[] assetTagNames,
		long[] assetLinkEntryIds) {

		throw new UnsupportedOperationException();
	}

	@Override
	public FileEntry updateFileEntry(
		long userId, long fileEntryId, String sourceFileName, String mimeType,
		String title, String description, String changeLog,
		boolean majorVersion, File file, ServiceContext serviceContext) {

		throw new UnsupportedOperationException();
	}

	@Override
	public FileEntry updateFileEntry(
		long userId, long fileEntryId, String sourceFileName, String mimeType,
		String title, String description, String changeLog,
		boolean majorVersion, InputStream is, long size,
		ServiceContext serviceContext) {

		throw new UnsupportedOperationException();
	}

	@Override
	public FileShortcut updateFileShortcut(
		long userId, long fileShortcutId, long folderId, long toFileEntryId,
		ServiceContext serviceContext) {

		throw new UnsupportedOperationException();
	}

	@Override
	public void updateFileShortcuts(
		long oldToFileEntryId, long newToFileEntryId) {

		throw new UnsupportedOperationException();
	}

	@Override
	public Folder updateFolder(
		long folderId, long parentFolderId, String name, String description,
		ServiceContext serviceContext) {

		throw new UnsupportedOperationException();
	}

	private final Repository _repository;

}