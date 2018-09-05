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

package com.liferay.portal.repository;

import com.liferay.document.library.kernel.exception.NoSuchFileEntryException;
import com.liferay.document.library.kernel.exception.NoSuchFileShortcutException;
import com.liferay.document.library.kernel.exception.NoSuchFileVersionException;
import com.liferay.document.library.kernel.exception.NoSuchFolderException;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFileShortcut;
import com.liferay.document.library.kernel.model.DLFileVersion;
import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.document.library.kernel.service.DLFileEntryLocalService;
import com.liferay.document.library.kernel.service.DLFileEntryServiceUtil;
import com.liferay.document.library.kernel.service.DLFileShortcutLocalService;
import com.liferay.document.library.kernel.service.DLFileVersionLocalService;
import com.liferay.document.library.kernel.service.DLFolderLocalService;
import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.exception.NoSuchRepositoryException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.RepositoryEntry;
import com.liferay.portal.kernel.repository.InvalidRepositoryIdException;
import com.liferay.portal.kernel.repository.LocalRepository;
import com.liferay.portal.kernel.repository.Repository;
import com.liferay.portal.kernel.repository.RepositoryFactory;
import com.liferay.portal.kernel.repository.RepositoryProvider;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionHelper;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.RepositoryEntryLocalService;
import com.liferay.portal.kernel.service.RepositoryLocalService;
import com.liferay.portal.kernel.util.ServiceProxyFactory;
import com.liferay.portal.kernel.util.StringBundler;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Iván Zaera
 */
public class RepositoryProviderImpl implements RepositoryProvider {

	@Override
	public LocalRepository getFileEntryLocalRepository(long fileEntryId)
		throws PortalException {

		try {
			return getLocalRepository(getFileEntryRepositoryId(fileEntryId));
		}
		catch (InvalidRepositoryIdException irie) {
			StringBundler sb = new StringBundler(3);

			sb.append("No FileEntry exists with the key {fileEntryId=");
			sb.append(fileEntryId);
			sb.append("}");

			throw new NoSuchFileEntryException(sb.toString(), irie);
		}
	}

	@Override
	public Repository getFileEntryRepository(long fileEntryId)
		throws PortalException {

		try {
			checkFileEntryPermissions(fileEntryId);

			return getRepository(getFileEntryRepositoryId(fileEntryId));
		}
		catch (InvalidRepositoryIdException irie) {
			StringBundler sb = new StringBundler(3);

			sb.append("No FileEntry exists with the key {fileEntryId=");
			sb.append(fileEntryId);
			sb.append("}");

			throw new NoSuchFileEntryException(sb.toString(), irie);
		}
	}

	@Override
	public LocalRepository getFileShortcutLocalRepository(long fileShortcutId)
		throws PortalException {

		try {
			return getLocalRepository(
				getFileShortcutRepositoryId(fileShortcutId));
		}
		catch (InvalidRepositoryIdException irie) {
			StringBundler sb = new StringBundler(3);

			sb.append("No FileShortcut exists with the key {fileShortcutId=");
			sb.append(fileShortcutId);
			sb.append("}");

			throw new NoSuchFileShortcutException(sb.toString(), irie);
		}
	}

	@Override
	public Repository getFileShortcutRepository(long fileShortcutId)
		throws PortalException {

		try {
			checkFileShortcutPermissions(fileShortcutId);

			return getRepository(getFileShortcutRepositoryId(fileShortcutId));
		}
		catch (InvalidRepositoryIdException irie) {
			StringBundler sb = new StringBundler(3);

			sb.append("No FileShortcut exists with the key {fileShortcutId=");
			sb.append(fileShortcutId);
			sb.append("}");

			throw new NoSuchFileShortcutException(sb.toString(), irie);
		}
	}

	@Override
	public LocalRepository getFileVersionLocalRepository(long fileVersionId)
		throws PortalException {

		try {
			return getLocalRepository(
				getFileVersionRepositoryId(fileVersionId));
		}
		catch (InvalidRepositoryIdException irie) {
			StringBundler sb = new StringBundler(3);

			sb.append("No FileVersion exists with the key {fileVersionId=");
			sb.append(fileVersionId);
			sb.append("}");

			throw new NoSuchFileVersionException(sb.toString(), irie);
		}
	}

	@Override
	public Repository getFileVersionRepository(long fileVersionId)
		throws PortalException {

		try {
			checkFileVersionPermissions(fileVersionId);

			return getRepository(getFileVersionRepositoryId(fileVersionId));
		}
		catch (InvalidRepositoryIdException irie) {
			StringBundler sb = new StringBundler(3);

			sb.append("No FileVersion exists with the key {fileVersionId=");
			sb.append(fileVersionId);
			sb.append("}");

			throw new NoSuchFileVersionException(sb.toString(), irie);
		}
	}

	@Override
	public LocalRepository getFolderLocalRepository(long folderId)
		throws PortalException {

		try {
			return getLocalRepository(getFolderRepositoryId(folderId));
		}
		catch (InvalidRepositoryIdException irie) {
			StringBundler sb = new StringBundler(3);

			sb.append("No Folder exists with the key {folderId=");
			sb.append(folderId);
			sb.append("}");

			throw new NoSuchFolderException(sb.toString(), irie);
		}
	}

	@Override
	public Repository getFolderRepository(long folderId)
		throws PortalException {

		try {
			checkFolderPermissions(folderId);

			return getRepository(getFolderRepositoryId(folderId));
		}
		catch (InvalidRepositoryIdException irie) {
			StringBundler sb = new StringBundler(3);

			sb.append("No Folder exists with the key {folderId=");
			sb.append(folderId);
			sb.append("}");

			throw new NoSuchFolderException(sb.toString(), irie);
		}
	}

	@Override
	public List<LocalRepository> getGroupLocalRepositories(long groupId)
		throws PortalException {

		List<LocalRepository> localRepositories = new ArrayList<>();

		List<Long> repositoryIds = getGroupRepositoryIds(groupId);

		for (long repositoryId : repositoryIds) {
			localRepositories.add(getLocalRepository(repositoryId));
		}

		return localRepositories;
	}

	@Override
	public List<Repository> getGroupRepositories(long groupId)
		throws PortalException {

		List<Repository> repositories = new ArrayList<>();

		List<Long> repositoryIds = getGroupRepositoryIds(groupId);

		for (long repositoryId : repositoryIds) {
			repositories.add(getRepository(repositoryId));
		}

		return repositories;
	}

	@Override
	public LocalRepository getImageLocalRepository(long imageId)
		throws PortalException {

		return getLocalRepository(getImageRepositoryId(imageId));
	}

	@Override
	public Repository getImageRepository(long imageId) throws PortalException {
		return getRepository(getImageRepositoryId(imageId));
	}

	@Override
	public LocalRepository getLocalRepository(long repositoryId)
		throws PortalException {

		LocalRepository localRepository =
			repositoryFactory.createLocalRepository(repositoryId);

		checkRepository(repositoryId);
		checkRepositoryAccess(repositoryId);

		return localRepository;
	}

	@Override
	public Repository getRepository(long repositoryId) throws PortalException {
		Repository repository = repositoryFactory.createRepository(
			repositoryId);

		checkRepository(repositoryId);
		checkRepositoryAccess(repositoryId);

		return repository;
	}

	protected void checkFileEntryPermissions(long fileEntryId)
		throws PortalException {

		DLFileEntry dlFileEntry = dlFileEntryLocalService.fetchDLFileEntry(
			fileEntryId);

		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		if ((dlFileEntry != null) && (permissionChecker != null)) {
			_dlFileEntryModelResourcePermission.check(
				permissionChecker, dlFileEntry, ActionKeys.VIEW);
		}
	}

	protected void checkFileShortcutPermissions(long fileShortcutId)
		throws PortalException {

		DLFileShortcut dlFileShortcut =
			dlFileShortcutLocalService.fetchDLFileShortcut(fileShortcutId);

		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		if ((dlFileShortcut != null) && (permissionChecker != null)) {
			_fileEntryModelResourcePermission.check(
				permissionChecker, dlFileShortcut.getToFileEntryId(),
				ActionKeys.VIEW);
		}
	}

	protected void checkFileVersionPermissions(long fileVersionId)
		throws PortalException {

		DLFileVersion dlFileVersion =
			dlFileVersionLocalService.fetchDLFileVersion(fileVersionId);

		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		if ((dlFileVersion != null) && (permissionChecker != null)) {
			_fileEntryModelResourcePermission.check(
				permissionChecker, dlFileVersion.getFileEntryId(),
				ActionKeys.VIEW);
		}
	}

	protected void checkFolderPermissions(long folderId)
		throws PortalException {

		DLFolder dlFolder = dlFolderLocalService.fetchDLFolder(folderId);

		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		if ((dlFolder != null) && (permissionChecker != null)) {
			_dlFolderModelResourcePermission.check(
				permissionChecker, dlFolder, ActionKeys.VIEW);
		}
	}

	protected void checkRepository(long repositoryId) throws PortalException {
		Group group = groupLocalService.fetchGroup(repositoryId);

		if (group != null) {
			return;
		}

		try {
			repositoryLocalService.getRepository(repositoryId);
		}
		catch (NoSuchRepositoryException nsre) {
			throw new InvalidRepositoryIdException(nsre.getMessage());
		}
	}

	protected void checkRepositoryAccess(long repositoryId)
		throws PortalException {

		Group group = groupLocalService.fetchGroup(repositoryId);

		if (group != null) {
			return;
		}

		try {
			com.liferay.portal.kernel.model.Repository repository =
				repositoryLocalService.fetchRepository(repositoryId);

			PermissionChecker permissionChecker =
				PermissionThreadLocal.getPermissionChecker();

			if ((repository != null) && (permissionChecker != null)) {
				try {
					ModelResourcePermissionHelper.check(
						_folderModelResourcePermission, permissionChecker,
						repository.getGroupId(), repository.getDlFolderId(),
						ActionKeys.VIEW);
				}
				catch (NoSuchFolderException nsfe) {

					// LPS-52675

					if (_log.isDebugEnabled()) {
						_log.debug(nsfe, nsfe);
					}
				}

				return;
			}
		}
		catch (NoSuchRepositoryException nsre) {
			throw new InvalidRepositoryIdException(nsre.getMessage());
		}
	}

	protected long getFileEntryRepositoryId(long fileEntryId) {
		DLFileEntry dlFileEntry = dlFileEntryLocalService.fetchDLFileEntry(
			fileEntryId);

		if (dlFileEntry != null) {
			return dlFileEntry.getRepositoryId();
		}

		RepositoryEntry repositoryEntry =
			repositoryEntryLocalService.fetchRepositoryEntry(fileEntryId);

		if (repositoryEntry != null) {
			return repositoryEntry.getRepositoryId();
		}

		throw new InvalidRepositoryIdException(
			"No repository associated with file entry " + fileEntryId);
	}

	protected long getFileShortcutRepositoryId(long fileShortcutId) {
		DLFileShortcut dlFileShortcut =
			dlFileShortcutLocalService.fetchDLFileShortcut(fileShortcutId);

		if (dlFileShortcut != null) {
			return dlFileShortcut.getRepositoryId();
		}

		throw new InvalidRepositoryIdException(
			"No repository associated with file shortcut " + fileShortcutId);
	}

	protected long getFileVersionRepositoryId(long fileVersionId) {
		DLFileVersion dlFileVersion =
			dlFileVersionLocalService.fetchDLFileVersion(fileVersionId);

		if (dlFileVersion != null) {
			return dlFileVersion.getRepositoryId();
		}

		RepositoryEntry repositoryEntry =
			repositoryEntryLocalService.fetchRepositoryEntry(fileVersionId);

		if (repositoryEntry != null) {
			return repositoryEntry.getRepositoryId();
		}

		throw new InvalidRepositoryIdException(
			"No repository associated with file version " + fileVersionId);
	}

	protected long getFolderRepositoryId(long folderId) {
		DLFolder dlFolder = dlFolderLocalService.fetchDLFolder(folderId);

		if (dlFolder != null) {
			if (dlFolder.isMountPoint()) {
				return dlFolder.getGroupId();
			}

			return dlFolder.getRepositoryId();
		}

		RepositoryEntry repositoryEntry =
			repositoryEntryLocalService.fetchRepositoryEntry(folderId);

		if (repositoryEntry != null) {
			return repositoryEntry.getRepositoryId();
		}

		throw new InvalidRepositoryIdException(
			"No repository associated with folder " + folderId);
	}

	protected List<Long> getGroupRepositoryIds(long groupId) {
		List<com.liferay.portal.kernel.model.Repository> repositories =
			repositoryLocalService.getGroupRepositories(groupId);

		List<Long> repositoryIds = new ArrayList<>(repositories.size() + 1);

		for (com.liferay.portal.kernel.model.Repository repository :
				repositories) {

			repositoryIds.add(repository.getRepositoryId());
		}

		repositoryIds.add(groupId);

		return repositoryIds;
	}

	protected long getImageRepositoryId(long imageId) throws PortalException {
		DLFileEntry dlFileEntry =
			DLFileEntryServiceUtil.fetchFileEntryByImageId(imageId);

		if (dlFileEntry != null) {
			return dlFileEntry.getRepositoryId();
		}

		throw new InvalidRepositoryIdException(
			"No repository associated with image " + imageId);
	}

	@BeanReference(type = DLFileEntryLocalService.class)
	protected DLFileEntryLocalService dlFileEntryLocalService;

	@BeanReference(type = DLFileShortcutLocalService.class)
	protected DLFileShortcutLocalService dlFileShortcutLocalService;

	@BeanReference(type = DLFileVersionLocalService.class)
	protected DLFileVersionLocalService dlFileVersionLocalService;

	@BeanReference(type = DLFolderLocalService.class)
	protected DLFolderLocalService dlFolderLocalService;

	@BeanReference(type = GroupLocalService.class)
	protected GroupLocalService groupLocalService;

	@BeanReference(type = RepositoryEntryLocalService.class)
	protected RepositoryEntryLocalService repositoryEntryLocalService;

	@BeanReference(type = RepositoryFactory.class)
	protected RepositoryFactory repositoryFactory;

	@BeanReference(type = RepositoryLocalService.class)
	protected RepositoryLocalService repositoryLocalService;

	private static final Log _log = LogFactoryUtil.getLog(
		RepositoryProviderImpl.class);

	private static volatile ModelResourcePermission<DLFileEntry>
		_dlFileEntryModelResourcePermission =
			ServiceProxyFactory.newServiceTrackedInstance(
				ModelResourcePermission.class, RepositoryProviderImpl.class,
				"_dlFileEntryModelResourcePermission",
				"(model.class.name=" + DLFileEntry.class.getName() + ")", true);
	private static volatile ModelResourcePermission<DLFolder>
		_dlFolderModelResourcePermission =
			ServiceProxyFactory.newServiceTrackedInstance(
				ModelResourcePermission.class, RepositoryProviderImpl.class,
				"_dlFolderModelResourcePermission",
				"(model.class.name=" + DLFolder.class.getName() + ")", true);
	private static volatile ModelResourcePermission<FileEntry>
		_fileEntryModelResourcePermission =
			ServiceProxyFactory.newServiceTrackedInstance(
				ModelResourcePermission.class, RepositoryProviderImpl.class,
				"_fileEntryModelResourcePermission",
				"(model.class.name=" + FileEntry.class.getName() + ")", true);
	private static volatile ModelResourcePermission<Folder>
		_folderModelResourcePermission =
			ServiceProxyFactory.newServiceTrackedInstance(
				ModelResourcePermission.class, RepositoryProviderImpl.class,
				"_folderModelResourcePermission",
				"(model.class.name=" + Folder.class.getName() + ")", true);

}