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

package com.liferay.portal.repository.proxy;

import com.liferay.counter.service.CounterLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.repository.BaseRepository;
import com.liferay.portal.kernel.repository.LocalRepository;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.model.Lock;
import com.liferay.portal.service.CompanyLocalService;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.UserLocalService;
import com.liferay.portlet.asset.service.AssetEntryLocalService;
import com.liferay.portlet.documentlibrary.service.DLAppHelperLocalService;

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
			long folderId, String sourceFileName, String mimeType, String title,
			String description, String changeLog, File file,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		FileEntry fileEntry = _baseRepository.addFileEntry(
			folderId, sourceFileName, mimeType, title, description, changeLog,
			file, serviceContext);

		return newFileEntryProxyBean(fileEntry);
	}

	@Override
	public FileEntry addFileEntry(
			long folderId, String sourceFileName, String mimeType, String title,
			String description, String changeLog, InputStream is, long size,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		FileEntry fileEntry = _baseRepository.addFileEntry(
			folderId, sourceFileName, mimeType, title, description, changeLog,
			is, size, serviceContext);

		return newFileEntryProxyBean(fileEntry);
	}

	@Override
	public Folder addFolder(
			long parentFolderId, String title, String description,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		Folder folder = _baseRepository.addFolder(
			parentFolderId, title, description, serviceContext);

		return newFolderProxyBean(folder);
	}

	@Override
	public FileVersion cancelCheckOut(long fileEntryId)
		throws PortalException, SystemException {

		return _baseRepository.cancelCheckOut(fileEntryId);
	}

	@Override
	public void checkInFileEntry(
			long fileEntryId, boolean major, String changeLog,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		_baseRepository.checkInFileEntry(
			fileEntryId, major, changeLog, serviceContext);
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #checkInFileEntry(long,
	 *             String, ServiceContext)}
	 */
	@Override
	public void checkInFileEntry(long fileEntryId, String lockUuid)
		throws PortalException, SystemException {

		_baseRepository.checkInFileEntry(fileEntryId, lockUuid);
	}

	@Override
	public void checkInFileEntry(
			long fileEntryId, String lockUuid, ServiceContext serviceContext)
		throws PortalException, SystemException {

		_baseRepository.checkInFileEntry(fileEntryId, lockUuid, serviceContext);
	}

	@Override
	public FileEntry checkOutFileEntry(
			long fileEntryId, ServiceContext serviceContext)
		throws PortalException, SystemException {

		FileEntry fileEntry = _baseRepository.checkOutFileEntry(
			fileEntryId, serviceContext);

		return newFileEntryProxyBean(fileEntry);
	}

	@Override
	public FileEntry checkOutFileEntry(
			long fileEntryId, String owner, long expirationTime,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		FileEntry fileEntry = _baseRepository.checkOutFileEntry(
			fileEntryId, owner, expirationTime, serviceContext);

		return newFileEntryProxyBean(fileEntry);
	}

	@Override
	public FileEntry copyFileEntry(
			long groupId, long fileEntryId, long destFolderId,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		return _baseRepository.copyFileEntry(
			groupId, fileEntryId, destFolderId, serviceContext);
	}

	@Override
	public void deleteFileEntry(long fileEntryId)
		throws PortalException, SystemException {

		_baseRepository.deleteFileEntry(fileEntryId);
	}

	@Override
	public void deleteFileEntry(long folderId, String title)
		throws PortalException, SystemException {

		_baseRepository.deleteFileEntry(folderId, title);
	}

	@Override
	public void deleteFileVersion(long fileEntryId, String version)
		throws PortalException, SystemException {

		_baseRepository.deleteFileVersion(fileEntryId, version);
	}

	@Override
	public void deleteFolder(long folderId)
		throws PortalException, SystemException {

		_baseRepository.deleteFolder(folderId);
	}

	@Override
	public void deleteFolder(long parentFolderId, String title)
		throws PortalException, SystemException {

		_baseRepository.deleteFolder(parentFolderId, title);
	}

	@Override
	public List<FileEntry> getFileEntries(
			long folderId, int start, int end, OrderByComparator obc)
		throws PortalException, SystemException {

		List<FileEntry> fileEntries = _baseRepository.getFileEntries(
			folderId, start, end, obc);

		return toFileEntryProxyBeans(fileEntries);
	}

	@Override
	public List<FileEntry> getFileEntries(
			long folderId, long documentTypeId, int start, int end,
			OrderByComparator obc)
		throws PortalException, SystemException {

		List<FileEntry> fileEntries = _baseRepository.getFileEntries(
			folderId, documentTypeId, start, end, obc);

		return toFileEntryProxyBeans(fileEntries);
	}

	@Override
	public List<FileEntry> getFileEntries(
			long folderId, String[] mimeTypes, int start, int end,
			OrderByComparator obc)
		throws PortalException, SystemException {

		List<FileEntry> fileEntries = _baseRepository.getFileEntries(
			folderId, mimeTypes, start, end, obc);

		return toFileEntryProxyBeans(fileEntries);
	}

	@Override
	public List<Object> getFileEntriesAndFileShortcuts(
			long folderId, int status, int start, int end)
		throws PortalException, SystemException {

		List<Object> objects = _baseRepository.getFileEntriesAndFileShortcuts(
			folderId, status, start, end);

		return toObjectProxyBeans(objects);
	}

	@Override
	public int getFileEntriesAndFileShortcutsCount(long folderId, int status)
		throws SystemException {

		return _baseRepository.getFileEntriesAndFileShortcutsCount(
			folderId, status);
	}

	@Override
	public int getFileEntriesAndFileShortcutsCount(
			long folderId, int status, String[] mimeTypes)
		throws PortalException, SystemException {

		return _baseRepository.getFileEntriesAndFileShortcutsCount(
			folderId, status, mimeTypes);
	}

	@Override
	public int getFileEntriesCount(long folderId) throws SystemException {
		return _baseRepository.getFileEntriesCount(folderId);
	}

	@Override
	public int getFileEntriesCount(long folderId, long documentTypeId)
		throws SystemException {

		return _baseRepository.getFileEntriesCount(folderId, documentTypeId);
	}

	@Override
	public int getFileEntriesCount(long folderId, String[] mimeTypes)
		throws PortalException, SystemException {

		return _baseRepository.getFileEntriesCount(folderId, mimeTypes);
	}

	@Override
	public FileEntry getFileEntry(long fileEntryId)
		throws PortalException, SystemException {

		FileEntry fileEntry = _baseRepository.getFileEntry(fileEntryId);

		return newFileEntryProxyBean(fileEntry);
	}

	@Override
	public FileEntry getFileEntry(long folderId, String title)
		throws PortalException, SystemException {

		FileEntry fileEntry = _baseRepository.getFileEntry(folderId, title);

		return newFileEntryProxyBean(fileEntry);
	}

	@Override
	public FileEntry getFileEntryByUuid(String uuid)
		throws PortalException, SystemException {

		FileEntry fileEntry = _baseRepository.getFileEntryByUuid(uuid);

		return newFileEntryProxyBean(fileEntry);
	}

	@Override
	public FileVersion getFileVersion(long fileVersionId)
		throws PortalException, SystemException {

		FileVersion fileVersion = _baseRepository.getFileVersion(fileVersionId);

		return newFileVersionProxyBean(fileVersion);
	}

	@Override
	public Folder getFolder(long folderId)
		throws PortalException, SystemException {

		Folder folder = _baseRepository.getFolder(folderId);

		return newFolderProxyBean(folder);
	}

	@Override
	public Folder getFolder(long parentFolderId, String title)
		throws PortalException, SystemException {

		Folder folder = _baseRepository.getFolder(parentFolderId, title);

		return newFolderProxyBean(folder);
	}

	@Override
	public List<Folder> getFolders(
			long parentFolderId, boolean includeMountfolders, int start,
			int end, OrderByComparator obc)
		throws PortalException, SystemException {

		List<Folder> folders = _baseRepository.getFolders(
			parentFolderId, includeMountfolders, start, end, obc);

		return toFolderProxyBeans(folders);
	}

	@Override
	public List<Folder> getFolders(
			long parentFolderId, int status, boolean includeMountfolders,
			int start, int end, OrderByComparator obc)
		throws PortalException, SystemException {

		List<Folder> folders = _baseRepository.getFolders(
			parentFolderId, status, includeMountfolders, start, end, obc);

		return toFolderProxyBeans(folders);
	}

	@Override
	public List<Object> getFoldersAndFileEntriesAndFileShortcuts(
			long folderId, int status, boolean includeMountFolders, int start,
			int end, OrderByComparator obc)
		throws PortalException, SystemException {

		List<Object> objects =
			_baseRepository.getFoldersAndFileEntriesAndFileShortcuts(
				folderId, status, includeMountFolders, start, end, obc);

		return toObjectProxyBeans(objects);
	}

	@Override
	public List<Object> getFoldersAndFileEntriesAndFileShortcuts(
			long folderId, int status, String[] mimeTypes,
			boolean includeMountFolders, int start, int end,
			OrderByComparator obc)
		throws PortalException, SystemException {

		List<Object> objects =
			_baseRepository.getFoldersAndFileEntriesAndFileShortcuts(
				folderId, status, mimeTypes, includeMountFolders, start, end,
				obc);

		return toObjectProxyBeans(objects);
	}

	@Override
	public int getFoldersAndFileEntriesAndFileShortcutsCount(
			long folderId, int status, boolean includeMountFolders)
		throws PortalException, SystemException {

		return _baseRepository.getFoldersAndFileEntriesAndFileShortcutsCount(
			folderId, status, includeMountFolders);
	}

	@Override
	public int getFoldersAndFileEntriesAndFileShortcutsCount(
			long folderId, int status, String[] mimeTypes,
			boolean includeMountFolders)
		throws PortalException, SystemException {

		return _baseRepository.getFoldersAndFileEntriesAndFileShortcutsCount(
			folderId, status, mimeTypes, includeMountFolders);
	}

	@Override
	public int getFoldersCount(long parentFolderId, boolean includeMountfolders)
		throws PortalException, SystemException {

		return _baseRepository.getFoldersCount(
			parentFolderId, includeMountfolders);
	}

	@Override
	public int getFoldersCount(
			long parentFolderId, int status, boolean includeMountfolders)
		throws PortalException, SystemException {

		return _baseRepository.getFoldersCount(
			parentFolderId, status, includeMountfolders);
	}

	@Override
	public int getFoldersFileEntriesCount(List<Long> folderIds, int status)
		throws SystemException {

		return _baseRepository.getFoldersFileEntriesCount(folderIds, status);
	}

	@Override
	public LocalRepository getLocalRepository() {
		LocalRepository localRepository = _baseRepository.getLocalRepository();

		return newLocalRepositoryProxyBean(localRepository);
	}

	@Override
	public List<Folder> getMountFolders(
			long parentFolderId, int start, int end, OrderByComparator obc)
		throws PortalException, SystemException {

		List<Folder> folders = _baseRepository.getMountFolders(
			parentFolderId, start, end, obc);

		return toFolderProxyBeans(folders);
	}

	@Override
	public int getMountFoldersCount(long parentFolderId)
		throws SystemException {

		return _baseRepository.getMountFoldersCount(parentFolderId);
	}

	public BaseRepository getProxyBean() {
		return _baseRepository;
	}

	@Override
	public List<FileEntry> getRepositoryFileEntries(
			long userId, long rootFolderId, int start, int end,
			OrderByComparator obc)
		throws PortalException, SystemException {

		List<FileEntry> fileEntries = _baseRepository.getRepositoryFileEntries(
			userId, rootFolderId, start, end, obc);

		return toFileEntryProxyBeans(fileEntries);
	}

	@Override
	public List<FileEntry> getRepositoryFileEntries(
			long userId, long rootFolderId, String[] mimeTypes, int status,
			int start, int end, OrderByComparator obc)
		throws PortalException, SystemException {

		List<FileEntry> fileEntries = _baseRepository.getRepositoryFileEntries(
			userId, rootFolderId, mimeTypes, status, start, end, obc);

		return toFileEntryProxyBeans(fileEntries);
	}

	@Override
	public int getRepositoryFileEntriesCount(long userId, long rootFolderId)
		throws PortalException, SystemException {

		return _baseRepository.getRepositoryFileEntriesCount(
			userId, rootFolderId);
	}

	@Override
	public int getRepositoryFileEntriesCount(
			long userId, long rootFolderId, String[] mimeTypes, int status)
		throws PortalException, SystemException {

		return _baseRepository.getRepositoryFileEntriesCount(
			userId, rootFolderId, mimeTypes, status);
	}

	@Override
	public long getRepositoryId() {
		return _baseRepository.getRepositoryId();
	}

	@Override
	public void getSubfolderIds(List<Long> folderIds, long folderId)
		throws PortalException, SystemException {

		_baseRepository.getSubfolderIds(folderIds, folderId);
	}

	@Override
	public List<Long> getSubfolderIds(long folderId, boolean recurse)
		throws PortalException, SystemException {

		return _baseRepository.getSubfolderIds(folderId, recurse);
	}

	@Override
	public String[] getSupportedConfigurations() {
		return _baseRepository.getSupportedConfigurations();
	}

	@Override
	public String[][] getSupportedParameters() {
		return _baseRepository.getSupportedParameters();
	}

	@Override
	public void initRepository() throws PortalException, SystemException {
		_baseRepository.initRepository();
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #checkOutFileEntry(long,
	 *             ServiceContext)}
	 */
	@Override
	public Lock lockFileEntry(long fileEntryId)
		throws PortalException, SystemException {

		Lock lock = _baseRepository.lockFileEntry(fileEntryId);

		return (Lock)newProxyInstance(lock, Lock.class);
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #checkOutFileEntry(long,
	 *             String, long, ServiceContext)}
	 */
	@Override
	public Lock lockFileEntry(
			long fileEntryId, String owner, long expirationTime)
		throws PortalException, SystemException {

		Lock lock = _baseRepository.lockFileEntry(
			fileEntryId, owner, expirationTime);

		return (Lock)newProxyInstance(lock, Lock.class);
	}

	@Override
	public Lock lockFolder(long folderId)
		throws PortalException, SystemException {

		Lock lock = _baseRepository.lockFolder(folderId);

		return (Lock)newProxyInstance(lock, Lock.class);
	}

	@Override
	public Lock lockFolder(
			long folderId, String owner, boolean inheritable,
			long expirationTime)
		throws PortalException, SystemException {

		Lock lock = _baseRepository.lockFolder(
			folderId, owner, inheritable, expirationTime);

		return (Lock)newProxyInstance(lock, Lock.class);
	}

	@Override
	public FileEntry moveFileEntry(
			long fileEntryId, long newFolderId, ServiceContext serviceContext)
		throws PortalException, SystemException {

		FileEntry fileEntry = _baseRepository.moveFileEntry(
			fileEntryId, newFolderId, serviceContext);

		return newFileEntryProxyBean(fileEntry);
	}

	@Override
	public Folder moveFolder(
			long folderId, long newParentFolderId,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		Folder folder = _baseRepository.moveFolder(
			folderId, newParentFolderId, serviceContext);

		return newFolderProxyBean(folder);
	}

	@Override
	public Lock refreshFileEntryLock(
			String lockUuid, long companyId, long expirationTime)
		throws PortalException, SystemException {

		Lock lock = _baseRepository.refreshFileEntryLock(
			lockUuid, companyId, expirationTime);

		return (Lock)newProxyInstance(lock, Lock.class);
	}

	@Override
	public Lock refreshFolderLock(
			String lockUuid, long companyId, long expirationTime)
		throws PortalException, SystemException {

		Lock lock = _baseRepository.refreshFolderLock(
			lockUuid, companyId, expirationTime);

		return (Lock)newProxyInstance(lock, Lock.class);
	}

	@Override
	public void revertFileEntry(
			long fileEntryId, String version, ServiceContext serviceContext)
		throws PortalException, SystemException {

		_baseRepository.revertFileEntry(fileEntryId, version, serviceContext);
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
	public void setCounterLocalService(
		CounterLocalService counterLocalService) {

		_baseRepository.setCounterLocalService(counterLocalService);
	}

	@Override
	public void setDLAppHelperLocalService(
		DLAppHelperLocalService dlAppHelperLocalService) {

		_baseRepository.setDLAppHelperLocalService(dlAppHelperLocalService);
	}

	@Override
	public void setGroupId(long groupId) {
		_baseRepository.setGroupId(groupId);
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
		throws PortalException, SystemException {

		_baseRepository.unlockFolder(folderId, lockUuid);
	}

	@Override
	public void unlockFolder(long parentFolderId, String title, String lockUuid)
		throws PortalException, SystemException {

		_baseRepository.unlockFolder(parentFolderId, title, lockUuid);
	}

	@Override
	public FileEntry updateFileEntry(
			long fileEntryId, String sourceFileName, String mimeType,
			String title, String description, String changeLog,
			boolean majorVersion, File file, ServiceContext serviceContext)
		throws PortalException, SystemException {

		FileEntry fileEntry = _baseRepository.updateFileEntry(
			fileEntryId, sourceFileName, mimeType, title, description,
			changeLog, majorVersion, file, serviceContext);

		return newFileEntryProxyBean(fileEntry);
	}

	@Override
	public FileEntry updateFileEntry(
			long fileEntryId, String sourceFileName, String mimeType,
			String title, String description, String changeLog,
			boolean majorVersion, InputStream is, long size,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		FileEntry fileEntry = _baseRepository.updateFileEntry(
			fileEntryId, sourceFileName, mimeType, title, description,
			changeLog, majorVersion, is, size, serviceContext);

		return newFileEntryProxyBean(fileEntry);
	}

	@Override
	public Folder updateFolder(
			long folderId, String title, String description,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		Folder folder = _baseRepository.updateFolder(
			folderId, title, description, serviceContext);

		return newFolderProxyBean(folder);
	}

	@Override
	public boolean verifyFileEntryCheckOut(long fileEntryId, String lockUuid)
		throws PortalException, SystemException {

		return _baseRepository.verifyFileEntryCheckOut(fileEntryId, lockUuid);
	}

	@Override
	public boolean verifyFileEntryLock(long fileEntryId, String lockUuid)
		throws PortalException, SystemException {

		return _baseRepository.verifyFileEntryLock(fileEntryId, lockUuid);
	}

	@Override
	public boolean verifyInheritableLock(long folderId, String lockUuid)
		throws PortalException, SystemException {

		return _baseRepository.verifyInheritableLock(folderId, lockUuid);
	}

	private BaseRepository _baseRepository;

}