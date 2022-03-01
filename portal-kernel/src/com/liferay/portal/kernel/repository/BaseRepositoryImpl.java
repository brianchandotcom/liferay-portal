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

import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.document.library.kernel.model.DLVersionNumberIncrease;
import com.liferay.document.library.kernel.service.DLAppHelperLocalService;
import com.liferay.document.library.kernel.service.DLFolderLocalService;
import com.liferay.document.library.kernel.util.DL;
import com.liferay.portal.kernel.exception.NoSuchRepositoryEntryException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.RepositoryEntry;
import com.liferay.portal.kernel.repository.capabilities.Capability;
import com.liferay.portal.kernel.repository.capabilities.CapabilityProvider;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.repository.search.RepositorySearchQueryBuilderUtil;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchEngineHelper;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.RepositoryEntryLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.service.persistence.RepositoryEntryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.UnicodeProperties;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import java.util.Date;
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
			String externalReferenceCode, long userId, long folderId,
			String sourceFileName, String mimeType, String title,
			String urlTitle, String description, String changeLog, File file,
			Date expirationDate, Date reviewDate, ServiceContext serviceContext)
		throws PortalException {

		try (InputStream inputStream = new FileInputStream(file)) {
			return addFileEntry(
				externalReferenceCode, userId, folderId, sourceFileName,
				mimeType, title, urlTitle, description, changeLog, inputStream,
				file.length(), expirationDate, reviewDate, serviceContext);
		}
		catch (IOException ioException) {
			throw new SystemException(ioException);
		}
	}

	@Override
	public abstract Folder addFolder(
			long userId, long parentFolderId, String name, String description,
			ServiceContext serviceContext)
		throws PortalException;

	@Override
	public abstract void checkInFileEntry(
			long userId, long fileEntryId,
			DLVersionNumberIncrease dlVersionNumberIncrease, String changeLog,
			ServiceContext serviceContext)
		throws PortalException;

	@Override
	public abstract FileEntry checkOutFileEntry(
			long fileEntryId, ServiceContext serviceContext)
		throws PortalException;

	@Override
	public abstract FileEntry checkOutFileEntry(
			long fileEntryId, String owner, long expirationTime,
			ServiceContext serviceContext)
		throws PortalException;

	@Override
	public void deleteAll() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void deleteFileEntry(long folderId, String title)
		throws PortalException {

		FileEntry fileEntry = getFileEntry(folderId, title);

		deleteFileEntry(fileEntry.getFileEntryId());
	}

	@Override
	public void deleteFileVersion(long fileVersionId) throws PortalException {
		throw new UnsupportedOperationException();
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
	@SuppressWarnings("rawtypes")
	public List<com.liferay.portal.kernel.repository.model.RepositoryEntry>
			getFileEntriesAndFileShortcuts(
				long folderId, int status, int start, int end)
		throws PortalException {

		return (List)getFileEntries(folderId, start, end, null);
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
			int start, int end, OrderByComparator<Folder> orderByComparator)
		throws PortalException {

		return getFolders(
			parentFolderId, includeMountfolders, start, end, orderByComparator);
	}

	public abstract List<Object> getFoldersAndFileEntries(
		long folderId, int start, int end,
		OrderByComparator<?> orderByComparator);

	public abstract List<Object> getFoldersAndFileEntries(
			long folderId, String[] mimeTypes, int start, int end,
			OrderByComparator<?> orderByComparator)
		throws PortalException;

	@Override
	@SuppressWarnings("rawtypes")
	public List<com.liferay.portal.kernel.repository.model.RepositoryEntry>
		getFoldersAndFileEntriesAndFileShortcuts(
			long folderId, int status, boolean includeMountFolders, int start,
			int end, OrderByComparator<?> orderByComparator) {

		return (List)getFoldersAndFileEntries(
			folderId, start, end, orderByComparator);
	}

	@Override
	@SuppressWarnings("rawtypes")
	public List<com.liferay.portal.kernel.repository.model.RepositoryEntry>
			getFoldersAndFileEntriesAndFileShortcuts(
				long folderId, int status, String[] mimeTypes,
				boolean includeMountFolders, int start, int end,
				OrderByComparator<?> orderByComparator)
		throws PortalException {

		return (List)getFoldersAndFileEntries(
			folderId, mimeTypes, start, end, orderByComparator);
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

	@Override
	public List<FileEntry> getRepositoryFileEntries(
			long userId, long rootFolderId, int start, int end,
			OrderByComparator<FileEntry> orderByComparator)
		throws PortalException {

		return getFileEntries(rootFolderId, start, end, orderByComparator);
	}

	@Override
	public List<FileEntry> getRepositoryFileEntries(
			long userId, long rootFolderId, String[] mimeTypes, int status,
			int start, int end, OrderByComparator<FileEntry> orderByComparator)
		throws PortalException {

		return getFileEntries(
			rootFolderId, mimeTypes, start, end, orderByComparator);
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

	/**
	 * @deprecated As of Wilberforce (7.0.x)
	 */
	@Deprecated
	@Override
	public String[][] getSupportedParameters() {
		return _SUPPORTED_PARAMETERS;
	}

	public UnicodeProperties getTypeSettingsProperties() {
		return _typeSettingsUnicodeProperties;
	}

	@Override
	public abstract void initRepository() throws PortalException;

	@Override
	public <T extends Capability> boolean isCapabilityProvided(
		Class<T> capabilityClass) {

		return false;
	}

	@Override
	public Hits search(SearchContext searchContext) throws SearchException {
		searchContext.setSearchEngineId(SearchEngineHelper.GENERIC_ENGINE_ID);

		return search(
			searchContext,
			RepositorySearchQueryBuilderUtil.getFullQuery(searchContext));
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
	public void setDLFolderLocalService(
		DLFolderLocalService dlFolderLocalService) {

		this.dlFolderLocalService = dlFolderLocalService;
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
		UnicodeProperties typeSettingsUnicodeProperties) {

		_typeSettingsUnicodeProperties = typeSettingsUnicodeProperties;
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
			String mimeType, String title, String urlTitle, String description,
			String changeLog, DLVersionNumberIncrease dlVersionNumberIncrease,
			File file, Date expirationDate, Date reviewDate,
			ServiceContext serviceContext)
		throws PortalException {

		try (InputStream inputStream = new FileInputStream(file)) {
			return updateFileEntry(
				userId, fileEntryId, sourceFileName, mimeType, title, urlTitle,
				description, changeLog, dlVersionNumberIncrease, inputStream,
				file.length(), expirationDate, reviewDate, serviceContext);
		}
		catch (IOException ioException) {
			throw new SystemException(ioException);
		}
	}

	@Override
	public abstract FileEntry updateFileEntry(
			long userId, long fileEntryId, String sourceFileName,
			String mimeType, String title, String urlTitle, String description,
			String changeLog, DLVersionNumberIncrease dlVersionNumberIncrease,
			InputStream inputStream, long size, Date expirationDate,
			Date reviewDate, ServiceContext serviceContext)
		throws PortalException;

	@Override
	public Folder updateFolder(
		long folderId, long parentFolderId, String name, String description,
		ServiceContext serviceContext) {

		throw new UnsupportedOperationException();
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
			repositoryEntry.isManualCheckInRequired();

		if (!manualCheckInRequired) {
			return;
		}

		repositoryEntry.setManualCheckInRequired(false);

		RepositoryEntryUtil.update(repositoryEntry);
	}

	protected RepositoryEntry getRepositoryEntry(String objectId)
		throws PortalException {

		return repositoryEntryLocalService.getRepositoryEntry(
			PrincipalThreadLocal.getUserId(), getGroupId(), getRepositoryId(),
			objectId);
	}

	protected void setManualCheckInRequired(
			long fileEntryId, ServiceContext serviceContext)
		throws NoSuchRepositoryEntryException {

		if (!GetterUtil.getBoolean(
				serviceContext.getAttribute(DL.MANUAL_CHECK_IN_REQUIRED))) {

			return;
		}

		RepositoryEntry repositoryEntry = RepositoryEntryUtil.findByPrimaryKey(
			fileEntryId);

		repositoryEntry.setManualCheckInRequired(true);

		RepositoryEntryUtil.update(repositoryEntry);
	}

	protected AssetEntryLocalService assetEntryLocalService;
	protected CompanyLocalService companyLocalService;
	protected DLAppHelperLocalService dlAppHelperLocalService;
	protected DLFolderLocalService dlFolderLocalService;
	protected RepositoryEntryLocalService repositoryEntryLocalService;
	protected UserLocalService userLocalService;

	private static final String[][] _SUPPORTED_PARAMETERS = {};

	private long _companyId;
	private long _groupId;
	private final LocalRepository _localRepository =
		new DefaultLocalRepositoryImpl(this);
	private long _repositoryId;
	private UnicodeProperties _typeSettingsUnicodeProperties;

}