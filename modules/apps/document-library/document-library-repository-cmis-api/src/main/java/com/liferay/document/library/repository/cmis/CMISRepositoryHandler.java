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

package com.liferay.document.library.repository.cmis;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.lock.Lock;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.repository.BaseRepository;
import com.liferay.portal.kernel.repository.BaseRepositoryImpl;
import com.liferay.portal.kernel.repository.RepositoryException;
import com.liferay.portal.kernel.repository.capabilities.Capability;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileShortcut;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.InputStream;

import java.util.List;
import java.util.Map;

/**
 * @author Alexander Chow
 */
public abstract class CMISRepositoryHandler
	extends BaseRepositoryImpl implements Capability {

	@Override
	public FileEntry addFileEntry(
			long userId, long folderId, String sourceFileName, String mimeType,
			String title, String description, String changeLog, InputStream is,
			long size, ServiceContext serviceContext)
		throws PortalException {

		return _baseCmisRepository.addFileEntry(
			userId, folderId, sourceFileName, mimeType, title, description,
			changeLog, is, size, serviceContext);
	}

	@Override
	public FileShortcut addFileShortcut(
			long userId, long folderId, long toFileEntryId,
			ServiceContext serviceContext)
		throws PortalException {

		return _baseCmisRepository.addFileShortcut(
			userId, folderId, toFileEntryId, serviceContext);
	}

	@Override
	public Folder addFolder(
			long userId, long parentFolderId, String name, String description,
			ServiceContext serviceContext)
		throws PortalException {

		return _baseCmisRepository.addFolder(
			userId, parentFolderId, name, description, serviceContext);
	}

	@Override
	public FileVersion cancelCheckOut(long fileEntryId) throws PortalException {
		return _baseCmisRepository.cancelCheckOut(fileEntryId);
	}

	@Override
	public void checkInFileEntry(
			long userId, long fileEntryId, boolean major, String changeLog,
			ServiceContext serviceContext)
		throws PortalException {

		_baseCmisRepository.checkInFileEntry(
			userId, fileEntryId, major, changeLog, serviceContext);
	}

	@Override
	public void checkInFileEntry(
			long userId, long fileEntryId, String lockUuid,
			ServiceContext serviceContext)
		throws PortalException {

		_baseCmisRepository.checkInFileEntry(
			userId, fileEntryId, lockUuid, serviceContext);
	}

	@Override
	public FileEntry checkOutFileEntry(
			long fileEntryId, ServiceContext serviceContext)
		throws PortalException {

		return _baseCmisRepository.checkOutFileEntry(
			fileEntryId, serviceContext);
	}

	@Override
	public FileEntry checkOutFileEntry(
			long fileEntryId, String owner, long expirationTime,
			ServiceContext serviceContext)
		throws PortalException {

		return _baseCmisRepository.checkOutFileEntry(
			fileEntryId, owner, expirationTime, serviceContext);
	}

	@Override
	public FileEntry copyFileEntry(
			long userId, long groupId, long fileEntryId, long destFolderId,
			ServiceContext serviceContext)
		throws PortalException {

		return _baseCmisRepository.copyFileEntry(
			userId, groupId, fileEntryId, destFolderId, serviceContext);
	}

	@Override
	public void deleteFileEntry(long fileEntryId) throws PortalException {
		_baseCmisRepository.deleteFileEntry(fileEntryId);
	}

	@Override
	public void deleteFileShortcut(long fileShortcutId) throws PortalException {
		_baseCmisRepository.deleteFileShortcut(fileShortcutId);
	}

	@Override
	public void deleteFileShortcuts(long toFileEntryId) throws PortalException {
		_baseCmisRepository.deleteFileShortcuts(toFileEntryId);
	}

	@Override
	public void deleteFolder(long folderId) throws PortalException {
		_baseCmisRepository.deleteFolder(folderId);
	}

	public BaseRepository getCmisRepository() {
		return _baseCmisRepository;
	}

	@Override
	public List<FileEntry> getFileEntries(
			long folderId, int status, int start, int end,
			OrderByComparator<FileEntry> obc)
		throws PortalException {

		return _baseCmisRepository.getFileEntries(
			folderId, status, start, end, obc);
	}

	@Override
	public List<FileEntry> getFileEntries(
			long folderId, int start, int end, OrderByComparator<FileEntry> obc)
		throws PortalException {

		return _baseCmisRepository.getFileEntries(folderId, start, end, obc);
	}

	@Override
	public List<FileEntry> getFileEntries(
			long folderId, long fileEntryTypeId, int start, int end,
			OrderByComparator<FileEntry> obc)
		throws PortalException {

		return _baseCmisRepository.getFileEntries(
			folderId, fileEntryTypeId, start, end, obc);
	}

	@Override
	public List<FileEntry> getFileEntries(
			long folderId, String[] mimeTypes, int start, int end,
			OrderByComparator<FileEntry> obc)
		throws PortalException {

		return _baseCmisRepository.getFileEntries(
			folderId, mimeTypes, start, end, obc);
	}

	@Override
	public int getFileEntriesCount(long folderId) throws PortalException {
		return _baseCmisRepository.getFileEntriesCount(folderId);
	}

	@Override
	public int getFileEntriesCount(long folderId, int status)
		throws PortalException {

		return _baseCmisRepository.getFileEntriesCount(folderId, status);
	}

	@Override
	public int getFileEntriesCount(long folderId, long fileEntryTypeId)
		throws PortalException {

		return _baseCmisRepository.getFileEntriesCount(
			folderId, fileEntryTypeId);
	}

	@Override
	public int getFileEntriesCount(long folderId, String[] mimeTypes)
		throws PortalException {

		return _baseCmisRepository.getFileEntriesCount(folderId, mimeTypes);
	}

	@Override
	public FileEntry getFileEntry(long fileEntryId) throws PortalException {
		return _baseCmisRepository.getFileEntry(fileEntryId);
	}

	@Override
	public FileEntry getFileEntry(long folderId, String title)
		throws PortalException {

		return _baseCmisRepository.getFileEntry(folderId, title);
	}

	@Override
	public FileEntry getFileEntryByUuid(String uuid) throws PortalException {
		return _baseCmisRepository.getFileEntryByUuid(uuid);
	}

	@Override
	public FileShortcut getFileShortcut(long fileShortcutId)
		throws PortalException {

		return _baseCmisRepository.getFileShortcut(fileShortcutId);
	}

	@Override
	public FileVersion getFileVersion(long fileVersionId)
		throws PortalException {

		return _baseCmisRepository.getFileVersion(fileVersionId);
	}

	@Override
	public Folder getFolder(long folderId) throws PortalException {
		return _baseCmisRepository.getFolder(folderId);
	}

	@Override
	public Folder getFolder(long parentFolderId, String name)
		throws PortalException {

		return _baseCmisRepository.getFolder(parentFolderId, name);
	}

	@Override
	public List<Folder> getFolders(
			long parentFolderId, boolean includeMountfolders, int start,
			int end, OrderByComparator<Folder> obc)
		throws PortalException {

		return _baseCmisRepository.getFolders(
			parentFolderId, includeMountfolders, start, end, obc);
	}

	@Override
	public List<Object> getFoldersAndFileEntries(
		long folderId, int start, int end, OrderByComparator<?> obc) {

		return _baseCmisRepository.getFoldersAndFileEntries(
			folderId, start, end, obc);
	}

	@Override
	public List<Object> getFoldersAndFileEntries(
			long folderId, String[] mimeTypes, int start, int end,
			OrderByComparator<?> obc)
		throws PortalException {

		return _baseCmisRepository.getFoldersAndFileEntries(
			folderId, mimeTypes, start, end, obc);
	}

	@Override
	public int getFoldersAndFileEntriesCount(long folderId) {
		return _baseCmisRepository.getFoldersAndFileEntriesCount(folderId);
	}

	@Override
	public int getFoldersAndFileEntriesCount(long folderId, String[] mimeTypes)
		throws PortalException {

		return _baseCmisRepository.getFoldersAndFileEntriesCount(
			folderId, mimeTypes);
	}

	@Override
	public int getFoldersCount(long parentFolderId, boolean includeMountfolders)
		throws PortalException {

		return _baseCmisRepository.getFoldersCount(
			parentFolderId, includeMountfolders);
	}

	@Override
	public int getFoldersFileEntriesCount(List<Long> folderIds, int status)
		throws PortalException {

		return _baseCmisRepository.getFoldersFileEntriesCount(
			folderIds, status);
	}

	public String getLatestVersionId(String objectId) {
		return _baseCmisRepository.getLatestVersionId(objectId);
	}

	public String getLogin() {
		String login = PrincipalThreadLocal.getName();

		if (Validator.isNull(login)) {
			return login;
		}

		try {
			Company company = companyLocalService.getCompany(getCompanyId());

			String authType = company.getAuthType();

			if (!authType.equals(CompanyConstants.AUTH_TYPE_ID)) {
				User user = userLocalService.getUser(GetterUtil.getLong(login));

				if (authType.equals(CompanyConstants.AUTH_TYPE_EA)) {
					login = user.getEmailAddress();
				}
				else if (authType.equals(CompanyConstants.AUTH_TYPE_SN)) {
					login = user.getScreenName();
				}
			}
		}
		catch (Exception e) {
			throw new RepositoryException(e);
		}

		return login;
	}

	@Override
	public List<Folder> getMountFolders(
			long parentFolderId, int start, int end,
			OrderByComparator<Folder> obc)
		throws PortalException {

		return _baseCmisRepository.getMountFolders(
			parentFolderId, start, end, obc);
	}

	@Override
	public int getMountFoldersCount(long parentFolderId)
		throws PortalException {

		return _baseCmisRepository.getMountFoldersCount(parentFolderId);
	}

	public String getObjectName(String objectId) throws PortalException {
		return _baseCmisRepository.getObjectName(objectId);
	}

	public List<String> getObjectPaths(String objectId) throws PortalException {
		return _baseCmisRepository.getObjectPaths(objectId);
	}

	public abstract Session getSession() throws PortalException;

	@Override
	public void getSubfolderIds(List<Long> folderIds, long folderId)
		throws PortalException {

		_baseCmisRepository.getSubfolderIds(folderIds, folderId);
	}

	@Override
	public List<Long> getSubfolderIds(long folderId, boolean recurse)
		throws PortalException {

		return _baseCmisRepository.getSubfolderIds(folderId, recurse);
	}

	@Override
	public void initRepository() throws PortalException {
		_baseCmisRepository.initRepository();
	}

	public boolean isCancelCheckOutAllowable(String objectId)
		throws PortalException {

		return _baseCmisRepository.isCancelCheckOutAllowable(objectId);
	}

	public boolean isCheckInAllowable(String objectId) throws PortalException {
		return _baseCmisRepository.isCheckInAllowable(objectId);
	}

	public boolean isCheckOutAllowable(String objectId) throws PortalException {
		return _baseCmisRepository.isCheckOutAllowable(objectId);
	}

	public boolean isDocumentRetrievableByVersionSeriesId() {
		return true;
	}

	public boolean isRefreshBeforePermissionCheck() {
		return false;
	}

	public boolean isSupportsMinorVersions(String productName) {

		// LPS-20509

		productName = StringUtil.toLowerCase(productName);

		if (productName.contains("filenet") && productName.contains("p8")) {
			return false;
		}

		return true;
	}

	@Override
	public Lock lockFolder(long folderId) throws PortalException {
		return _baseCmisRepository.lockFolder(folderId);
	}

	@Override
	public Lock lockFolder(
			long folderId, String owner, boolean inheritable,
			long expirationTime)
		throws PortalException {

		return _baseCmisRepository.lockFolder(
			folderId, owner, inheritable, expirationTime);
	}

	@Override
	public FileEntry moveFileEntry(
			long userId, long fileEntryId, long newFolderId,
			ServiceContext serviceContext)
		throws PortalException {

		return _baseCmisRepository.moveFileEntry(
			userId, fileEntryId, newFolderId, serviceContext);
	}

	@Override
	public Folder moveFolder(
			long userId, long folderId, long newParentFolderId,
			ServiceContext serviceContext)
		throws PortalException {

		return _baseCmisRepository.moveFolder(
			userId, folderId, newParentFolderId, serviceContext);
	}

	@Override
	public Lock refreshFileEntryLock(
			String lockUuid, long companyId, long expirationTime)
		throws PortalException {

		return _baseCmisRepository.refreshFileEntryLock(
			lockUuid, companyId, expirationTime);
	}

	@Override
	public Lock refreshFolderLock(
			String lockUuid, long companyId, long expirationTime)
		throws PortalException {

		return _baseCmisRepository.refreshFolderLock(
			lockUuid, companyId, expirationTime);
	}

	@Override
	public void revertFileEntry(
			long userId, long fileEntryId, String version,
			ServiceContext serviceContext)
		throws PortalException {

		_baseCmisRepository.revertFileEntry(
			userId, fileEntryId, version, serviceContext);
	}

	@Override
	public Hits search(long creatorUserId, int status, int start, int end)
		throws PortalException {

		return _baseCmisRepository.search(creatorUserId, status, start, end);
	}

	@Override
	public Hits search(
			long creatorUserId, long folderId, String[] mimeTypes, int status,
			int start, int end)
		throws PortalException {

		return _baseCmisRepository.search(
			creatorUserId, folderId, mimeTypes, status, start, end);
	}

	@Override
	public Hits search(SearchContext searchContext) throws SearchException {
		return _baseCmisRepository.search(searchContext);
	}

	@Override
	public Hits search(SearchContext searchContext, Query query)
		throws SearchException {

		return _baseCmisRepository.search(searchContext, query);
	}

	public void setCmisRepository(BaseCmisRepository baseCmisRepository) {
		_baseCmisRepository = baseCmisRepository;
	}

	public FileEntry toFileEntry(String objectId) throws PortalException {
		return _baseCmisRepository.toFileEntry(objectId);
	}

	public Folder toFolder(String objectId) throws PortalException {
		return _baseCmisRepository.toFolder(objectId);
	}

	@Override
	public void unlockFolder(long folderId, String lockUuid)
		throws PortalException {

		_baseCmisRepository.unlockFolder(folderId, lockUuid);
	}

	@Override
	public FileEntry updateFileEntry(
			long userId, long fileEntryId, String sourceFileName,
			String mimeType, String title, String description, String changeLog,
			boolean majorVersion, InputStream is, long size,
			ServiceContext serviceContext)
		throws PortalException {

		return _baseCmisRepository.updateFileEntry(
			userId, fileEntryId, sourceFileName, mimeType, title, description,
			changeLog, majorVersion, is, size, serviceContext);
	}

	public FileEntry updateFileEntry(
			String objectId, String mimeType, Map<String, Object> properties,
			InputStream is, String sourceFileName, long size,
			ServiceContext serviceContext)
		throws PortalException {

		return _baseCmisRepository.updateFileEntry(
			objectId, mimeType, properties, is, sourceFileName, size,
			serviceContext);
	}

	@Override
	public FileShortcut updateFileShortcut(
			long userId, long fileShortcutId, long folderId, long toFileEntryId,
			ServiceContext serviceContext)
		throws PortalException {

		return _baseCmisRepository.updateFileShortcut(
			userId, fileShortcutId, folderId, toFileEntryId, serviceContext);
	}

	@Override
	public void updateFileShortcuts(
			long oldToFileEntryId, long newToFileEntryId)
		throws PortalException {

		_baseCmisRepository.updateFileShortcuts(
			oldToFileEntryId, newToFileEntryId);
	}

	@Override
	public Folder updateFolder(
			long folderId, String name, String description,
			ServiceContext serviceContext)
		throws PortalException {

		return _baseCmisRepository.updateFolder(
			folderId, name, description, serviceContext);
	}

	@Override
	public boolean verifyFileEntryCheckOut(long fileEntryId, String lockUuid)
		throws PortalException {

		return _baseCmisRepository.verifyFileEntryCheckOut(
			fileEntryId, lockUuid);
	}

	@Override
	public boolean verifyInheritableLock(long folderId, String lockUuid)
		throws PortalException {

		return _baseCmisRepository.verifyInheritableLock(folderId, lockUuid);
	}

	private BaseCmisRepository _baseCmisRepository;

}