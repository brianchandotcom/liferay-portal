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

package com.liferay.portal.kernel.repository;

import com.liferay.counter.service.CounterLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.repository.search.RepositorySearchQueryBuilderUtil;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchEngineUtil;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.model.RepositoryEntry;
import com.liferay.portal.service.CompanyLocalService;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.UserLocalService;
import com.liferay.portal.service.persistence.RepositoryEntryUtil;
import com.liferay.portlet.asset.service.AssetEntryLocalService;
import com.liferay.portlet.documentlibrary.service.DLAppHelperLocalService;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.List;

/**
 * Third-party repository implementations should extend from this class.
 *
 * @author Alexander Chow
 */
public abstract class BaseRepositoryImpl implements BaseRepository {

	public FileEntry addFileEntry(
			long folderId, String sourceFileName, String mimeType, String title,
			String description, String changeLog, File file,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		InputStream is = null;
		long size = 0;

		try {
			is = new FileInputStream(file);
			size = file.length();

			return addFileEntry(
				folderId, sourceFileName, mimeType, title, description,
				changeLog, is, size, serviceContext);
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
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

	public void deleteFileEntry(long folderId, String title)
		throws PortalException, SystemException {

		FileEntry fileEntry = getFileEntry(folderId, title);

		deleteFileEntry(fileEntry.getFileEntryId());
	}

	public void deleteFolder(long parentFolderId, String title)
		throws PortalException, SystemException {

		Folder folder = getFolder(parentFolderId, title);

		deleteFolder(folder.getFolderId());
	}

	public long getCompanyId() {
		return _companyId;
	}

	public List<Object> getFileEntriesAndFileShortcuts(
			long folderId, int status, int start, int end)
		throws SystemException {

		return new ArrayList<Object>(
			getFileEntries(folderId, start, end, null));
	}

	public int getFileEntriesAndFileShortcutsCount(long folderId, int status)
		throws SystemException {

		return getFileEntriesCount(folderId);
	}

	public abstract List<Object> getFoldersAndFileEntries(
			long folderId, int start, int end, OrderByComparator obc)
		throws SystemException;

	public List<Object> getFoldersAndFileEntriesAndFileShortcuts(
			long folderId, int status, boolean includeMountFolders, int start,
			int end, OrderByComparator obc)
		throws SystemException {

		return getFoldersAndFileEntries(folderId, start, end, obc);
	}

	public List<Object> getFoldersAndFileEntriesAndFileShortcuts(
			long folderId, int status, String[] mimeTypes,
			boolean includeMountFolders, int start, int end,
			OrderByComparator obc)
		throws SystemException {

		return getFoldersAndFileEntries(folderId, start, end, obc);
	}

	public int getFoldersAndFileEntriesAndFileShortcutsCount(
			long folderId, int status, boolean includeMountFolders)
		throws SystemException {

		return getFoldersAndFileEntriesCount(folderId);
	}

	public int getFoldersAndFileEntriesAndFileShortcutsCount(
			long folderId, int status, String[] mimeTypes,
			boolean includeMountFolders)
		throws SystemException {

		return getFoldersAndFileEntriesCount(folderId);
	}

	public abstract int getFoldersAndFileEntriesCount(long folderId)
		throws SystemException;

	public long getGroupId() {
		return _groupId;
	}

	public LocalRepository getLocalRepository() {
		return _localRepository;
	}

	public Object[] getRepositoryEntryIds(String objectId)
		throws SystemException {

		RepositoryEntry repositoryEntry = RepositoryEntryUtil.fetchByR_M(
			getRepositoryId(), objectId);

		if (repositoryEntry == null) {
			long repositoryEntryId = counterLocalService.increment();

			repositoryEntry = RepositoryEntryUtil.create(repositoryEntryId);

			repositoryEntry.setGroupId(getGroupId());
			repositoryEntry.setRepositoryId(getRepositoryId());
			repositoryEntry.setMappedId(objectId);

			RepositoryEntryUtil.update(repositoryEntry, false);
		}

		return new Object[] {
			repositoryEntry.getRepositoryEntryId(),
			repositoryEntry.getUuid()
		};
	}

	public List<FileEntry> getRepositoryFileEntries(
			long userId, long rootFolderId, int start, int end,
			OrderByComparator obc)
		throws SystemException {

		return getFileEntries(rootFolderId, start, end, obc);
	}

	public int getRepositoryFileEntriesCount(long userId, long rootFolderId)
		throws SystemException {

		return getFileEntriesCount(rootFolderId);
	}

	public long getRepositoryId() {
		return _repositoryId;
	}

	public UnicodeProperties getTypeSettingsProperties() {
		return _typeSettingsProperties;
	}

	public abstract void initRepository()
		throws PortalException, SystemException;

	public Hits search(SearchContext searchContext) throws SearchException {
		searchContext.setSearchEngineId(SearchEngineUtil.GENERIC_ENGINE_ID);

		BooleanQuery fullQuery = RepositorySearchQueryBuilderUtil.getFullQuery(
			searchContext);

		return search(searchContext, fullQuery);
	}

	public void setAssetEntryLocalService(
		AssetEntryLocalService assetEntryLocalService) {

		this.assetEntryLocalService = assetEntryLocalService;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	public void setCompanyLocalService(
		CompanyLocalService companyLocalService) {

		this.companyLocalService = companyLocalService;
	}

	public void setCounterLocalService(
		CounterLocalService counterLocalService) {

		this.counterLocalService = counterLocalService;
	}

	public void setDLAppHelperLocalService(
		DLAppHelperLocalService dlAppHelperLocalService) {

		this.dlAppHelperLocalService = dlAppHelperLocalService;
	}

	public void setGroupId(long groupId) {
		_groupId = groupId;
	}

	public void setRepositoryId(long repositoryId) {
		_repositoryId = repositoryId;
	}

	public void setTypeSettingsProperties(
		UnicodeProperties typeSettingsProperties) {

		_typeSettingsProperties = typeSettingsProperties;
	}

	public void setUserLocalService(UserLocalService userLocalService) {
		this.userLocalService = userLocalService;
	}

	public void unlockFolder(long parentFolderId, String title, String lockUuid)
		throws PortalException, SystemException {

		Folder folder = getFolder(parentFolderId, title);

		unlockFolder(folder.getFolderId(), lockUuid);
	}

	public FileEntry updateFileEntry(
			long fileEntryId, String sourceFileName, String mimeType,
			String title, String description, String changeLog,
			boolean majorVersion, File file, ServiceContext serviceContext)
		throws PortalException, SystemException {

		InputStream is = null;
		long size = 0;

		try {
			is = new FileInputStream(file);
			size = file.length();

			return updateFileEntry(
				fileEntryId, sourceFileName, mimeType, title, description,
				changeLog, majorVersion, is, size, serviceContext);
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
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

	protected AssetEntryLocalService assetEntryLocalService;
	protected CompanyLocalService companyLocalService;
	protected CounterLocalService counterLocalService;
	protected DLAppHelperLocalService dlAppHelperLocalService;
	protected UserLocalService userLocalService;

	private long _companyId;
	private long _groupId;
	private LocalRepository _localRepository = new DefaultLocalRepositoryImpl(
		this);
	private long _repositoryId;
	private UnicodeProperties _typeSettingsProperties;

}