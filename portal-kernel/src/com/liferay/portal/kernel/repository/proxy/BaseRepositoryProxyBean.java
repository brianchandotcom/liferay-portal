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

package com.liferay.portal.kernel.repository.proxy;

import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.document.library.kernel.model.DLVersionNumberIncrease;
import com.liferay.document.library.kernel.service.DLAppHelperLocalService;
import com.liferay.document.library.kernel.service.DLFolderLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.lock.Lock;
import com.liferay.portal.kernel.repository.BaseRepository;
import com.liferay.portal.kernel.repository.LocalRepository;
import com.liferay.portal.kernel.repository.capabilities.Capability;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileShortcut;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.repository.model.RepositoryEntry;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.RepositoryEntryLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.UnicodeProperties;

import java.io.File;
import java.io.InputStream;

import java.util.List;

/**
 * @author Mika Koivisto
 */
public class BaseRepositoryProxyBean
	extends RepositoryModelProxyBean implements BaseRepository {

	public BaseRepositoryProxyBean(
		BaseRepository baseRepository, ClassLoader classLoader) {

		super(classLoader);

		_baseRepository = baseRepository;
	}

	@Override
	public FileEntry addFileEntry(
			long userId, long folderId, String sourceFileName, String mimeType,
			String title, String description, String changeLog, File file,
			ServiceContext serviceContext)
		throws PortalException {

		FileEntry fileEntry = _baseRepository.addFileEntry(
			userId, folderId, sourceFileName, mimeType, title, description,
			changeLog, file, serviceContext);

		return newFileEntryProxyBean(fileEntry);
	}

	@Override
	public FileEntry addFileEntry(
			long userId, long folderId, String sourceFileName, String mimeType,
			String title, String description, String changeLog, InputStream is,
			long size, ServiceContext serviceContext)
		throws PortalException {

		FileEntry fileEntry = _baseRepository.addFileEntry(
			userId, folderId, sourceFileName, mimeType, title, description,
			changeLog, is, size, serviceContext);

		return newFileEntryProxyBean(fileEntry);
	}

	@Override
	public FileShortcut addFileShortcut(
			long userId, long folderId, long toFileEntryId,
			ServiceContext serviceContext)
		throws PortalException {

		FileShortcut fileShortcut = _baseRepository.addFileShortcut(
			userId, folderId, toFileEntryId, serviceContext);

		return newFileShortcutProxyBean(fileShortcut);
	}

	@Override
	public Folder addFolder(
			long userId, long parentFolderId, String name, String description,
			ServiceContext serviceContext)
		throws PortalException {

		Folder folder = _baseRepository.addFolder(
			userId, parentFolderId, name, description, serviceContext);

		return newFolderProxyBean(folder);
	}

	@Override
	public FileVersion cancelCheckOut(long fileEntryId) throws PortalException {
		return _baseRepository.cancelCheckOut(fileEntryId);
	}

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link
	 *             #checkInFileEntry(long, long, DLVersionNumberIncrease,
	 *             String, ServiceContext)}
	 */
	@Deprecated
	@Override
	public void checkInFileEntry(
			long userId, long fileEntryId, boolean majorVersion,
			String changeLog, ServiceContext serviceContext)
		throws PortalException {

		checkInFileEntry(
			userId, fileEntryId,
			DLVersionNumberIncrease.fromMajorVersion(majorVersion), changeLog,
			serviceContext);
	}

	@Override
	public void checkInFileEntry(
			long userId, long fileEntryId,
			DLVersionNumberIncrease dlVersionNumberIncrease, String changeLog,
			ServiceContext serviceContext)
		throws PortalException {

		_baseRepository.checkInFileEntry(
			userId, fileEntryId, dlVersionNumberIncrease, changeLog,
			serviceContext);
	}

	@Override
	public void checkInFileEntry(
			long userId, long fileEntryId, String lockUuid,
			ServiceContext serviceContext)
		throws PortalException {

		_baseRepository.checkInFileEntry(
			userId, fileEntryId, lockUuid, serviceContext);
	}

	@Override
	public FileEntry checkOutFileEntry(
			long fileEntryId, ServiceContext serviceContext)
		throws PortalException {

		FileEntry fileEntry = _baseRepository.checkOutFileEntry(
			fileEntryId, serviceContext);

		return newFileEntryProxyBean(fileEntry);
	}

	@Override
	public FileEntry checkOutFileEntry(
			long fileEntryId, String owner, long expirationTime,
			ServiceContext serviceContext)
		throws PortalException {

		FileEntry fileEntry = _baseRepository.checkOutFileEntry(
			fileEntryId, owner, expirationTime, serviceContext);

		return newFileEntryProxyBean(fileEntry);
	}

	@Override
	public FileEntry copyFileEntry(
			long userId, long groupId, long fileEntryId, long destFolderId,
			ServiceContext serviceContext)
		throws PortalException {

		return _baseRepository.copyFileEntry(
			userId, groupId, fileEntryId, destFolderId, serviceContext);
	}

	@Override
	public void deleteAll() throws PortalException {
		_baseRepository.deleteAll();
	}

	@Override
	public void deleteFileEntry(long fileEntryId) throws PortalException {
		_baseRepository.deleteFileEntry(fileEntryId);
	}

	@Override
	public void deleteFileEntry(long folderId, String title)
		throws PortalException {

		_baseRepository.deleteFileEntry(folderId, title);
	}

	@Override
	public void deleteFileShortcut(long fileShortcutId) throws PortalException {
		_baseRepository.deleteFileShortcut(fileShortcutId);
	}

	@Override
	public void deleteFileShortcuts(long toFileEntryId) throws PortalException {
		_baseRepository.deleteFileShortcuts(toFileEntryId);
	}

	@Override
	public void deleteFileVersion(long fileVersionId) throws PortalException {
		_baseRepository.deleteFileVersion(fileVersionId);
	}

	@Override
	public void deleteFileVersion(long fileEntryId, String version)
		throws PortalException {

		_baseRepository.deleteFileVersion(fileEntryId, version);
	}

	@Override
	public void deleteFolder(long folderId) throws PortalException {
		_baseRepository.deleteFolder(folderId);
	}

	@Override
	public void deleteFolder(long parentFolderId, String name)
		throws PortalException {

		_baseRepository.deleteFolder(parentFolderId, name);
	}

	@Override
	public <T extends Capability> T getCapability(Class<T> capabilityClass) {
		return _baseRepository.getCapability(capabilityClass);
	}

	@Override
	public List<FileEntry> getFileEntries(
			long folderId, int status, int start, int end,
			OrderByComparator<FileEntry> obc)
		throws PortalException {

		List<FileEntry> fileEntries = _baseRepository.getFileEntries(
			folderId, status, start, end, obc);

		return toFileEntryProxyBeans(fileEntries);
	}

	@Override
	public List<FileEntry> getFileEntries(
			long folderId, int start, int end, OrderByComparator<FileEntry> obc)
		throws PortalException {

		List<FileEntry> fileEntries = _baseRepository.getFileEntries(
			folderId, start, end, obc);

		return toFileEntryProxyBeans(fileEntries);
	}

	@Override
	public List<FileEntry> getFileEntries(
			long folderId, long documentTypeId, int start, int end,
			OrderByComparator<FileEntry> obc)
		throws PortalException {

		List<FileEntry> fileEntries = _baseRepository.getFileEntries(
			folderId, documentTypeId, start, end, obc);

		return toFileEntryProxyBeans(fileEntries);
	}

	@Override
	public List<FileEntry> getFileEntries(
			long folderId, String[] mimeTypes, int status, int start, int end,
			OrderByComparator<FileEntry> obc)
		throws PortalException {

		return _baseRepository.getFileEntries(
			folderId, mimeTypes, status, start, end, obc);
	}

	@Override
	public List<FileEntry> getFileEntries(
			long folderId, String[] mimeTypes, int start, int end,
			OrderByComparator<FileEntry> obc)
		throws PortalException {

		List<FileEntry> fileEntries = _baseRepository.getFileEntries(
			folderId, mimeTypes, start, end, obc);

		return toFileEntryProxyBeans(fileEntries);
	}

	@Override
	public List<RepositoryEntry> getFileEntriesAndFileShortcuts(
			long folderId, int status, int start, int end)
		throws PortalException {

		List<RepositoryEntry> fileEntriesAndFileShortcuts =
			_baseRepository.getFileEntriesAndFileShortcuts(
				folderId, status, start, end);

		return toObjectProxyBeans(fileEntriesAndFileShortcuts);
	}

	@Override
	public int getFileEntriesAndFileShortcutsCount(long folderId, int status)
		throws PortalException {

		return _baseRepository.getFileEntriesAndFileShortcutsCount(
			folderId, status);
	}

	@Override
	public int getFileEntriesAndFileShortcutsCount(
			long folderId, int status, String[] mimeTypes)
		throws PortalException {

		return _baseRepository.getFileEntriesAndFileShortcutsCount(
			folderId, status, mimeTypes);
	}

	@Override
	public int getFileEntriesCount(long folderId) throws PortalException {
		return _baseRepository.getFileEntriesCount(folderId);
	}

	@Override
	public int getFileEntriesCount(long folderId, int status)
		throws PortalException {

		return _baseRepository.getFileEntriesCount(folderId, status);
	}

	@Override
	public int getFileEntriesCount(long folderId, long documentTypeId)
		throws PortalException {

		return _baseRepository.getFileEntriesCount(folderId, documentTypeId);
	}

	@Override
	public int getFileEntriesCount(long folderId, String[] mimeTypes)
		throws PortalException {

		return _baseRepository.getFileEntriesCount(folderId, mimeTypes);
	}

	@Override
	public int getFileEntriesCount(
			long folderId, String[] mimeTypes, int status)
		throws PortalException {

		return _baseRepository.getFileEntriesCount(folderId, mimeTypes, status);
	}

	@Override
	public FileEntry getFileEntry(long fileEntryId) throws PortalException {
		return newFileEntryProxyBean(_baseRepository.getFileEntry(fileEntryId));
	}

	@Override
	public FileEntry getFileEntry(long folderId, String title)
		throws PortalException {

		FileEntry fileEntry = _baseRepository.getFileEntry(folderId, title);

		return newFileEntryProxyBean(fileEntry);
	}

	@Override
	public FileEntry getFileEntryByUuid(String uuid) throws PortalException {
		FileEntry fileEntry = _baseRepository.getFileEntryByUuid(uuid);

		return newFileEntryProxyBean(fileEntry);
	}

	@Override
	public FileShortcut getFileShortcut(long fileShortcutId)
		throws PortalException {

		return newFileShortcutProxyBean(
			_baseRepository.getFileShortcut(fileShortcutId));
	}

	@Override
	public FileVersion getFileVersion(long fileVersionId)
		throws PortalException {

		return newFileVersionProxyBean(
			_baseRepository.getFileVersion(fileVersionId));
	}

	@Override
	public Folder getFolder(long folderId) throws PortalException {
		return newFolderProxyBean(_baseRepository.getFolder(folderId));
	}

	@Override
	public Folder getFolder(long parentFolderId, String name)
		throws PortalException {

		Folder folder = _baseRepository.getFolder(parentFolderId, name);

		return newFolderProxyBean(folder);
	}

	@Override
	public List<Folder> getFolders(
			long parentFolderId, boolean includeMountfolders, int start,
			int end, OrderByComparator<Folder> obc)
		throws PortalException {

		List<Folder> folders = _baseRepository.getFolders(
			parentFolderId, includeMountfolders, start, end, obc);

		return toFolderProxyBeans(folders);
	}

	@Override
	public List<Folder> getFolders(
			long parentFolderId, int status, boolean includeMountfolders,
			int start, int end, OrderByComparator<Folder> obc)
		throws PortalException {

		List<Folder> folders = _baseRepository.getFolders(
			parentFolderId, status, includeMountfolders, start, end, obc);

		return toFolderProxyBeans(folders);
	}

	@Override
	public List<RepositoryEntry> getFoldersAndFileEntriesAndFileShortcuts(
			long folderId, int status, boolean includeMountFolders, int start,
			int end, OrderByComparator<?> obc)
		throws PortalException {

		List<RepositoryEntry> foldersAndFileEntriesAndFileShortcuts =
			_baseRepository.getFoldersAndFileEntriesAndFileShortcuts(
				folderId, status, includeMountFolders, start, end, obc);

		return toObjectProxyBeans(foldersAndFileEntriesAndFileShortcuts);
	}

	@Override
	public List<RepositoryEntry> getFoldersAndFileEntriesAndFileShortcuts(
			long folderId, int status, String[] mimeTypes,
			boolean includeMountFolders, int start, int end,
			OrderByComparator<?> obc)
		throws PortalException {

		List<RepositoryEntry> foldersAndFileEntriesAndFileShortcuts =
			_baseRepository.getFoldersAndFileEntriesAndFileShortcuts(
				folderId, status, mimeTypes, includeMountFolders, start, end,
				obc);

		return toObjectProxyBeans(foldersAndFileEntriesAndFileShortcuts);
	}

	@Override
	public int getFoldersAndFileEntriesAndFileShortcutsCount(
			long folderId, int status, boolean includeMountFolders)
		throws PortalException {

		return _baseRepository.getFoldersAndFileEntriesAndFileShortcutsCount(
			folderId, status, includeMountFolders);
	}

	@Override
	public int getFoldersAndFileEntriesAndFileShortcutsCount(
			long folderId, int status, String[] mimeTypes,
			boolean includeMountFolders)
		throws PortalException {

		return _baseRepository.getFoldersAndFileEntriesAndFileShortcutsCount(
			folderId, status, mimeTypes, includeMountFolders);
	}

	@Override
	public int getFoldersCount(long parentFolderId, boolean includeMountfolders)
		throws PortalException {

		return _baseRepository.getFoldersCount(
			parentFolderId, includeMountfolders);
	}

	@Override
	public int getFoldersCount(
			long parentFolderId, int status, boolean includeMountfolders)
		throws PortalException {

		return _baseRepository.getFoldersCount(
			parentFolderId, status, includeMountfolders);
	}

	@Override
	public int getFoldersFileEntriesCount(List<Long> folderIds, int status)
		throws PortalException {

		return _baseRepository.getFoldersFileEntriesCount(folderIds, status);
	}

	@Override
	public LocalRepository getLocalRepository() {
		return newLocalRepositoryProxyBean(
			_baseRepository.getLocalRepository());
	}

	@Override
	public List<Folder> getMountFolders(
			long parentFolderId, int start, int end,
			OrderByComparator<Folder> obc)
		throws PortalException {

		List<Folder> folders = _baseRepository.getMountFolders(
			parentFolderId, start, end, obc);

		return toFolderProxyBeans(folders);
	}

	@Override
	public int getMountFoldersCount(long parentFolderId)
		throws PortalException {

		return _baseRepository.getMountFoldersCount(parentFolderId);
	}

	public BaseRepository getProxyBean() {
		return _baseRepository;
	}

	@Override
	public List<FileEntry> getRepositoryFileEntries(
			long userId, long rootFolderId, int start, int end,
			OrderByComparator<FileEntry> obc)
		throws PortalException {

		List<FileEntry> fileEntries = _baseRepository.getRepositoryFileEntries(
			userId, rootFolderId, start, end, obc);

		return toFileEntryProxyBeans(fileEntries);
	}

	@Override
	public List<FileEntry> getRepositoryFileEntries(
			long userId, long rootFolderId, String[] mimeTypes, int status,
			int start, int end, OrderByComparator<FileEntry> obc)
		throws PortalException {

		List<FileEntry> fileEntries = _baseRepository.getRepositoryFileEntries(
			userId, rootFolderId, mimeTypes, status, start, end, obc);

		return toFileEntryProxyBeans(fileEntries);
	}

	@Override
	public int getRepositoryFileEntriesCount(long userId, long rootFolderId)
		throws PortalException {

		return _baseRepository.getRepositoryFileEntriesCount(
			userId, rootFolderId);
	}

	@Override
	public int getRepositoryFileEntriesCount(
			long userId, long rootFolderId, String[] mimeTypes, int status)
		throws PortalException {

		return _baseRepository.getRepositoryFileEntriesCount(
			userId, rootFolderId, mimeTypes, status);
	}

	@Override
	public long getRepositoryId() {
		return _baseRepository.getRepositoryId();
	}

	@Override
	public void getSubfolderIds(List<Long> folderIds, long folderId)
		throws PortalException {

		_baseRepository.getSubfolderIds(folderIds, folderId);
	}

	@Override
	public List<Long> getSubfolderIds(long folderId, boolean recurse)
		throws PortalException {

		return _baseRepository.getSubfolderIds(folderId, recurse);
	}

	/**
	 * @deprecated As of Wilberforce (7.0.x)
	 */
	@Deprecated
	@Override
	public String[][] getSupportedParameters() {
		return _baseRepository.getSupportedParameters();
	}

	@Override
	public void initRepository() throws PortalException {
		_baseRepository.initRepository();
	}

	@Override
	public <T extends Capability> boolean isCapabilityProvided(
		Class<T> capabilityClass) {

		return _baseRepository.isCapabilityProvided(capabilityClass);
	}

	@Override
	public Lock lockFolder(long folderId) throws PortalException {
		Lock lock = _baseRepository.lockFolder(folderId);

		return (Lock)newProxyInstance(lock, Lock.class);
	}

	@Override
	public Lock lockFolder(
			long folderId, String owner, boolean inheritable,
			long expirationTime)
		throws PortalException {

		Lock lock = _baseRepository.lockFolder(
			folderId, owner, inheritable, expirationTime);

		return (Lock)newProxyInstance(lock, Lock.class);
	}

	@Override
	public FileEntry moveFileEntry(
			long userId, long fileEntryId, long newFolderId,
			ServiceContext serviceContext)
		throws PortalException {

		FileEntry fileEntry = _baseRepository.moveFileEntry(
			userId, fileEntryId, newFolderId, serviceContext);

		return newFileEntryProxyBean(fileEntry);
	}

	@Override
	public Folder moveFolder(
			long userId, long folderId, long parentFolderId,
			ServiceContext serviceContext)
		throws PortalException {

		Folder folder = _baseRepository.moveFolder(
			userId, folderId, parentFolderId, serviceContext);

		return newFolderProxyBean(folder);
	}

	@Override
	public Lock refreshFileEntryLock(
			String lockUuid, long companyId, long expirationTime)
		throws PortalException {

		Lock lock = _baseRepository.refreshFileEntryLock(
			lockUuid, companyId, expirationTime);

		return (Lock)newProxyInstance(lock, Lock.class);
	}

	@Override
	public Lock refreshFolderLock(
			String lockUuid, long companyId, long expirationTime)
		throws PortalException {

		Lock lock = _baseRepository.refreshFolderLock(
			lockUuid, companyId, expirationTime);

		return (Lock)newProxyInstance(lock, Lock.class);
	}

	@Override
	public void revertFileEntry(
			long userId, long fileEntryId, String version,
			ServiceContext serviceContext)
		throws PortalException {

		_baseRepository.revertFileEntry(
			userId, fileEntryId, version, serviceContext);
	}

	@Override
	public Hits search(long creatorUserId, int status, int start, int end)
		throws PortalException {

		return _baseRepository.search(creatorUserId, status, start, end);
	}

	@Override
	public Hits search(
			long creatorUserId, long folderId, String[] mimeTypes, int status,
			int start, int end)
		throws PortalException {

		return _baseRepository.search(
			creatorUserId, folderId, mimeTypes, status, start, end);
	}

	@Override
	public Hits search(SearchContext searchContext) throws SearchException {
		return _baseRepository.search(searchContext);
	}

	@Override
	public Hits search(SearchContext searchContext, Query query)
		throws SearchException {

		return _baseRepository.search(searchContext, query);
	}

	@Override
	public void setAssetEntryLocalService(
		AssetEntryLocalService assetEntryLocalService) {

		_baseRepository.setAssetEntryLocalService(assetEntryLocalService);
	}

	@Override
	public void setCompanyId(long companyId) {
		_baseRepository.setCompanyId(companyId);
	}

	@Override
	public void setCompanyLocalService(
		CompanyLocalService companyLocalService) {

		_baseRepository.setCompanyLocalService(companyLocalService);
	}

	@Override
	public void setDLAppHelperLocalService(
		DLAppHelperLocalService dlAppHelperLocalService) {

		_baseRepository.setDLAppHelperLocalService(dlAppHelperLocalService);
	}

	@Override
	public void setDLFolderLocalService(
		DLFolderLocalService dlFolderLocalService) {

		_baseRepository.setDLFolderLocalService(dlFolderLocalService);
	}

	@Override
	public void setGroupId(long groupId) {
		_baseRepository.setGroupId(groupId);
	}

	@Override
	public void setRepositoryEntryLocalService(
		RepositoryEntryLocalService repositoryEntryLocalService) {

		_baseRepository.setRepositoryEntryLocalService(
			repositoryEntryLocalService);
	}

	@Override
	public void setRepositoryId(long repositoryId) {
		_baseRepository.setRepositoryId(repositoryId);
	}

	@Override
	public void setTypeSettingsProperties(
		UnicodeProperties typeSettingsProperties) {

		_baseRepository.setTypeSettingsProperties(typeSettingsProperties);
	}

	@Override
	public void setUserLocalService(UserLocalService userLocalService) {
		_baseRepository.setUserLocalService(userLocalService);
	}

	@Override
	public void unlockFolder(long folderId, String lockUuid)
		throws PortalException {

		_baseRepository.unlockFolder(folderId, lockUuid);
	}

	@Override
	public void unlockFolder(long parentFolderId, String name, String lockUuid)
		throws PortalException {

		_baseRepository.unlockFolder(parentFolderId, name, lockUuid);
	}

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link
	 *             #updateFileEntry(long, long, String, String, String, String,
	 *             String, DLVersionNumberIncrease, File, ServiceContext)}
	 */
	@Deprecated
	@Override
	public FileEntry updateFileEntry(
			long userId, long fileEntryId, String sourceFileName,
			String mimeType, String title, String description, String changeLog,
			boolean majorVersion, File file, ServiceContext serviceContext)
		throws PortalException {

		return updateFileEntry(
			userId, fileEntryId, sourceFileName, mimeType, title, description,
			changeLog, DLVersionNumberIncrease.fromMajorVersion(majorVersion),
			file, serviceContext);
	}

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link
	 *             #updateFileEntry(long, long, String, String, String, String,
	 *             String, DLVersionNumberIncrease, InputStream, long,
	 *             ServiceContext)}
	 */
	@Deprecated
	@Override
	public FileEntry updateFileEntry(
			long userId, long fileEntryId, String sourceFileName,
			String mimeType, String title, String description, String changeLog,
			boolean majorVersion, InputStream is, long size,
			ServiceContext serviceContext)
		throws PortalException {

		return updateFileEntry(
			userId, fileEntryId, sourceFileName, mimeType, title, description,
			changeLog, DLVersionNumberIncrease.fromMajorVersion(majorVersion),
			is, size, serviceContext);
	}

	@Override
	public FileEntry updateFileEntry(
			long userId, long fileEntryId, String sourceFileName,
			String mimeType, String title, String description, String changeLog,
			DLVersionNumberIncrease dlVersionNumberIncrease, File file,
			ServiceContext serviceContext)
		throws PortalException {

		FileEntry fileEntry = _baseRepository.updateFileEntry(
			userId, fileEntryId, sourceFileName, mimeType, title, description,
			changeLog, dlVersionNumberIncrease, file, serviceContext);

		return newFileEntryProxyBean(fileEntry);
	}

	@Override
	public FileEntry updateFileEntry(
			long userId, long fileEntryId, String sourceFileName,
			String mimeType, String title, String description, String changeLog,
			DLVersionNumberIncrease dlVersionNumberIncrease, InputStream is,
			long size, ServiceContext serviceContext)
		throws PortalException {

		FileEntry fileEntry = _baseRepository.updateFileEntry(
			userId, fileEntryId, sourceFileName, mimeType, title, description,
			changeLog, dlVersionNumberIncrease, is, size, serviceContext);

		return newFileEntryProxyBean(fileEntry);
	}

	@Override
	public FileShortcut updateFileShortcut(
			long userId, long fileShortcutId, long folderId, long toFileEntryId,
			ServiceContext serviceContext)
		throws PortalException {

		FileShortcut fileShortcut = _baseRepository.updateFileShortcut(
			userId, fileShortcutId, folderId, toFileEntryId, serviceContext);

		return newFileShortcutProxyBean(fileShortcut);
	}

	@Override
	public void updateFileShortcuts(
			long oldToFileEntryId, long newToFileEntryId)
		throws PortalException {

		_baseRepository.updateFileShortcuts(oldToFileEntryId, newToFileEntryId);
	}

	@Override
	public Folder updateFolder(
			long folderId, long parentFolderId, String name, String description,
			ServiceContext serviceContext)
		throws PortalException {

		Folder folder = _baseRepository.updateFolder(
			folderId, parentFolderId, name, description, serviceContext);

		return newFolderProxyBean(folder);
	}

	@Override
	public Folder updateFolder(
			long folderId, String name, String description,
			ServiceContext serviceContext)
		throws PortalException {

		Folder folder = _baseRepository.updateFolder(
			folderId, name, description, serviceContext);

		return newFolderProxyBean(folder);
	}

	@Override
	public boolean verifyFileEntryCheckOut(long fileEntryId, String lockUuid)
		throws PortalException {

		return _baseRepository.verifyFileEntryCheckOut(fileEntryId, lockUuid);
	}

	@Override
	public boolean verifyFileEntryLock(long fileEntryId, String lockUuid)
		throws PortalException {

		return _baseRepository.verifyFileEntryLock(fileEntryId, lockUuid);
	}

	@Override
	public boolean verifyInheritableLock(long folderId, String lockUuid)
		throws PortalException {

		return _baseRepository.verifyInheritableLock(folderId, lockUuid);
	}

	private final BaseRepository _baseRepository;

}