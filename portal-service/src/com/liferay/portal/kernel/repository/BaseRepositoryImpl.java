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

import com.liferay.portal.NoSuchRepositoryEntryException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.repository.capabilities.Capability;
import com.liferay.portal.kernel.repository.capabilities.CapabilityProvider;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.repository.search.RepositorySearchQueryBuilderUtil;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchEngineUtil;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.model.Lock;
import com.liferay.portal.model.RepositoryEntry;
import com.liferay.portal.security.auth.PrincipalThreadLocal;
import com.liferay.portal.service.CompanyLocalService;
import com.liferay.portal.service.RepositoryEntryLocalService;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.UserLocalService;
import com.liferay.portal.service.persistence.RepositoryEntryUtil;
import com.liferay.portlet.asset.service.AssetEntryLocalService;
import com.liferay.portlet.documentlibrary.service.DLAppHelperLocalService;
import com.liferay.portlet.documentlibrary.util.DL;

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
public abstract class BaseRepositoryImpl
	implements BaseRepository, CapabilityProvider {

	@Override
	public FileEntry addFileEntry(
			long userId, long folderId, String sourceFileName, String mimeType,
			String title, String description, String changeLog, File file,
			ServiceContext serviceContext)
		throws PortalException {

		InputStream is = null;
		long size = 0;

		try {
			is = new FileInputStream(file);
			size = file.length();

			return addFileEntry(
				userId, folderId, sourceFileName, mimeType, title, description,
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

	/**
	 * @deprecated As of 7.0.0, see {@link #addFileEntry(long, long, String,
	 *             String, String, String, String, File, ServiceContext)}
	 */
	@Deprecated
	@Override
	public FileEntry addFileEntry(
			long folderId, String sourceFileName, String mimeType, String title,
			String description, String changeLog, File file,
			ServiceContext serviceContext)
		throws PortalException {

		return addFileEntry(
			com.liferay.portal.kernel.repository.util.RepositoryUserUtil.
				getUserId(),
			folderId, sourceFileName, mimeType, title, description, changeLog,
			file, serviceContext);
	}

	/**
	 * @deprecated As of 7.0.0, see {@link #addFileEntry(long, long, String,
	 *             String, String, String, String, InputStream, long,
	 *             ServiceContext)}
	 */
	@Deprecated
	@Override
	public FileEntry addFileEntry(
			long folderId, String sourceFileName, String mimeType, String title,
			String description, String changeLog, InputStream is, long size,
			ServiceContext serviceContext)
		throws PortalException {

		return addFileEntry(
			com.liferay.portal.kernel.repository.util.RepositoryUserUtil.
				getUserId(),
			sourceFileName, mimeType, title, description, changeLog, is, size,
			serviceContext);
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
	 * @deprecated As of 7.0.0, replaced by {@link #copyFileEntry(long, long,
	 *             long, long, ServiceContext)}
	 */
	@Deprecated
	@Override
	public FileEntry copyFileEntry(
			long groupId, long fileEntryId, long destFolderId,
			ServiceContext serviceContext)
		throws PortalException {

		return copyFileEntry(
			com.liferay.portal.kernel.repository.util.RepositoryUserUtil.
				getUserId(),
			groupId, fileEntryId, destFolderId, serviceContext);
	}

	@Override
	public void deleteFileEntry(long folderId, String title)
		throws PortalException {

		FileEntry fileEntry = getFileEntry(folderId, title);

		deleteFileEntry(fileEntry.getFileEntryId());
	}

	@Override
	public void deleteFileVersion(long fileEntryId, String version) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void deleteFolder(long parentFolderId, String name)
		throws PortalException {

		Folder folder = getFolder(parentFolderId, name);

		deleteFolder(folder.getFolderId());
	}

	@Override
	public <T extends Capability> T getCapability(Class<T> capabilityClass) {
		throw new IllegalArgumentException(
			String.format(
				"Capability %s is not supported by repository %s",
				capabilityClass.getName(), getRepositoryId()));
	}

	public long getCompanyId() {
		return _companyId;
	}

	@Override
	public List<Object> getFileEntriesAndFileShortcuts(
			long folderId, int status, int start, int end)
		throws PortalException {

		return new ArrayList<Object>(
			getFileEntries(folderId, start, end, null));
	}

	@Override
	public int getFileEntriesAndFileShortcutsCount(long folderId, int status)
		throws PortalException {

		return getFileEntriesCount(folderId);
	}

	@Override
	public int getFileEntriesAndFileShortcutsCount(
			long folderId, int status, String[] mimeTypes)
		throws PortalException {

		return getFileEntriesCount(folderId, mimeTypes);
	}

	@Override
	public List<Folder> getFolders(
			long parentFolderId, int status, boolean includeMountfolders,
			int start, int end, OrderByComparator<Folder> obc)
		throws PortalException {

		return getFolders(parentFolderId, includeMountfolders, start, end, obc);
	}

	public abstract List<Object> getFoldersAndFileEntries(
		long folderId, int start, int end, OrderByComparator<?> obc);

	public abstract List<Object> getFoldersAndFileEntries(
			long folderId, String[] mimeTypes, int start, int end,
			OrderByComparator<?> obc)
		throws PortalException;

	@Override
	public List<Object> getFoldersAndFileEntriesAndFileShortcuts(
		long folderId, int status, boolean includeMountFolders, int start,
		int end, OrderByComparator<?> obc) {

		return getFoldersAndFileEntries(folderId, start, end, obc);
	}

	@Override
	public List<Object> getFoldersAndFileEntriesAndFileShortcuts(
			long folderId, int status, String[] mimeTypes,
			boolean includeMountFolders, int start, int end,
			OrderByComparator<?> obc)
		throws PortalException {

		return getFoldersAndFileEntries(folderId, mimeTypes, start, end, obc);
	}

	@Override
	public int getFoldersAndFileEntriesAndFileShortcutsCount(
		long folderId, int status, boolean includeMountFolders) {

		return getFoldersAndFileEntriesCount(folderId);
	}

	@Override
	public int getFoldersAndFileEntriesAndFileShortcutsCount(
			long folderId, int status, String[] mimeTypes,
			boolean includeMountFolders)
		throws PortalException {

		return getFoldersAndFileEntriesCount(folderId, mimeTypes);
	}

	public abstract int getFoldersAndFileEntriesCount(long folderId);

	public abstract int getFoldersAndFileEntriesCount(
			long folderId, String[] mimeTypes)
		throws PortalException;

	@Override
	public int getFoldersCount(
			long parentFolderId, int status, boolean includeMountfolders)
		throws PortalException {

		return getFoldersCount(parentFolderId, includeMountfolders);
	}

	public long getGroupId() {
		return _groupId;
	}

	@Override
	public LocalRepository getLocalRepository() {
		return _localRepository;
	}

	public Object[] getRepositoryEntryIds(String objectId)
		throws PortalException {

		boolean newRepositoryEntry = false;

		RepositoryEntry repositoryEntry = RepositoryEntryUtil.fetchByR_M(
			getRepositoryId(), objectId);

		if (repositoryEntry == null) {
			repositoryEntry = repositoryEntryLocalService.addRepositoryEntry(
				PrincipalThreadLocal.getUserId(), getGroupId(),
				getRepositoryId(), objectId, new ServiceContext());

			newRepositoryEntry = true;
		}

		return new Object[] {
			repositoryEntry.getRepositoryEntryId(), repositoryEntry.getUuid(),
			newRepositoryEntry
		};
	}

	@Override
	public List<FileEntry> getRepositoryFileEntries(
			long userId, long rootFolderId, int start, int end,
			OrderByComparator<FileEntry> obc)
		throws PortalException {

		return getFileEntries(rootFolderId, start, end, obc);
	}

	@Override
	public List<FileEntry> getRepositoryFileEntries(
			long userId, long rootFolderId, String[] mimeTypes, int status,
			int start, int end, OrderByComparator<FileEntry> obc)
		throws PortalException {

		return getFileEntries(rootFolderId, mimeTypes, start, end, obc);
	}

	@Override
	public int getRepositoryFileEntriesCount(long userId, long rootFolderId)
		throws PortalException {

		return getFileEntriesCount(rootFolderId);
	}

	@Override
	public int getRepositoryFileEntriesCount(
			long userId, long rootFolderId, String[] mimeTypes, int status)
		throws PortalException {

		return getFileEntriesCount(rootFolderId, mimeTypes);
	}

	@Override
	public long getRepositoryId() {
		return _repositoryId;
	}

	public UnicodeProperties getTypeSettingsProperties() {
		return _typeSettingsProperties;
	}

	@Override
	public abstract void initRepository() throws PortalException;

	@Override
	public <T extends Capability> boolean isCapabilityProvided(
		Class<T> capabilityClass) {

		return false;
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

	@Override
	public Hits search(SearchContext searchContext) throws SearchException {
		searchContext.setSearchEngineId(SearchEngineUtil.GENERIC_ENGINE_ID);

		BooleanQuery fullQuery = RepositorySearchQueryBuilderUtil.getFullQuery(
			searchContext);

		return search(searchContext, fullQuery);
	}

	@Override
	public void setAssetEntryLocalService(
		AssetEntryLocalService assetEntryLocalService) {

		this.assetEntryLocalService = assetEntryLocalService;
	}

	@Override
	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	@Override
	public void setCompanyLocalService(
		CompanyLocalService companyLocalService) {

		this.companyLocalService = companyLocalService;
	}

	@Override
	public void setDLAppHelperLocalService(
		DLAppHelperLocalService dlAppHelperLocalService) {

		this.dlAppHelperLocalService = dlAppHelperLocalService;
	}

	@Override
	public void setGroupId(long groupId) {
		_groupId = groupId;
	}

	@Override
	public void setRepositoryEntryLocalService(
		RepositoryEntryLocalService repositoryEntryLocalService) {

		this.repositoryEntryLocalService = repositoryEntryLocalService;
	}

	@Override
	public void setRepositoryId(long repositoryId) {
		_repositoryId = repositoryId;
	}

	@Override
	public void setTypeSettingsProperties(
		UnicodeProperties typeSettingsProperties) {

		_typeSettingsProperties = typeSettingsProperties;
	}

	@Override
	public void setUserLocalService(UserLocalService userLocalService) {
		this.userLocalService = userLocalService;
	}

	@Override
	public void unlockFolder(long parentFolderId, String name, String lockUuid)
		throws PortalException {

		Folder folder = getFolder(parentFolderId, name);

		unlockFolder(folder.getFolderId(), lockUuid);
	}

	@Override
	public FileEntry updateFileEntry(
			long userId, long fileEntryId, String sourceFileName,
			String mimeType, String title, String description, String changeLog,
			boolean majorVersion, File file, ServiceContext serviceContext)
		throws PortalException {

		InputStream is = null;
		long size = 0;

		try {
			is = new FileInputStream(file);
			size = file.length();

			return updateFileEntry(
				userId, fileEntryId, sourceFileName, mimeType, title,
				description, changeLog, majorVersion, is, size, serviceContext);
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

	/**
	 * @deprecated As of 7.0.0, replaced by {@link #updateFileEntry(long, long,
	 *             String, String, String, String, String, boolean, InputStream,
	 *             long, ServiceContext)}
	 */
	@Deprecated
	@Override
	public FileEntry updateFileEntry(
			long fileEntryId, String sourceFileName, String mimeType,
			String title, String description, String changeLog,
			boolean majorVersion, File file, ServiceContext serviceContext)
		throws PortalException {

		return updateFileEntry(
			com.liferay.portal.kernel.repository.util.RepositoryUserUtil.
				getUserId(),
			fileEntryId, sourceFileName, mimeType, title, description,
			changeLog, majorVersion, file, serviceContext);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link #updateFileEntry(long, long,
	 *             String, String, String, String, String, boolean, File,
	 *             ServiceContext)}
	 */
	@Deprecated
	@Override
	public FileEntry updateFileEntry(
			long fileEntryId, String sourceFileName, String mimeType,
			String title, String description, String changeLog,
			boolean majorVersion, InputStream is, long size,
			ServiceContext serviceContext)
		throws PortalException {

		return updateFileEntry(
			com.liferay.portal.kernel.repository.util.RepositoryUserUtil.
				getUserId(),
			fileEntryId, sourceFileName, mimeType, title, description,
			changeLog, majorVersion, is, size, serviceContext);
	}

	@Override
	public boolean verifyFileEntryLock(long fileEntryId, String lockUuid) {
		throw new UnsupportedOperationException();
	}

	protected void clearManualCheckInRequired(
			long fileEntryId, ServiceContext serviceContext)
		throws NoSuchRepositoryEntryException {

		boolean webDAVCheckInMode = GetterUtil.getBoolean(
			serviceContext.getAttribute(DL.WEBDAV_CHECK_IN_MODE));

		if (webDAVCheckInMode) {
			return;
		}

		RepositoryEntry repositoryEntry = RepositoryEntryUtil.findByPrimaryKey(
			fileEntryId);

		boolean manualCheckInRequired =
			repositoryEntry.getManualCheckInRequired();

		if (!manualCheckInRequired) {
			return;
		}

		repositoryEntry.setManualCheckInRequired(false);

		RepositoryEntryUtil.update(repositoryEntry);
	}

	protected void setManualCheckInRequired(
			long fileEntryId, ServiceContext serviceContext)
		throws NoSuchRepositoryEntryException {

		boolean manualCheckInRequired = GetterUtil.getBoolean(
			serviceContext.getAttribute(DL.MANUAL_CHECK_IN_REQUIRED));

		if (!manualCheckInRequired) {
			return;
		}

		RepositoryEntry repositoryEntry = RepositoryEntryUtil.findByPrimaryKey(
			fileEntryId);

		repositoryEntry.setManualCheckInRequired(manualCheckInRequired);

		RepositoryEntryUtil.update(repositoryEntry);
	}

	protected AssetEntryLocalService assetEntryLocalService;
	protected CompanyLocalService companyLocalService;
	protected DLAppHelperLocalService dlAppHelperLocalService;
	protected RepositoryEntryLocalService repositoryEntryLocalService;
	protected UserLocalService userLocalService;

	private long _companyId;
	private long _groupId;
	private final LocalRepository _localRepository =
		new DefaultLocalRepositoryImpl(this);
	private long _repositoryId;
	private UnicodeProperties _typeSettingsProperties;

}