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

import com.liferay.portal.NoSuchRepositoryException;
import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.cache.CacheRegistryItem;
import com.liferay.portal.kernel.cache.CacheRegistryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.InvalidRepositoryIdException;
import com.liferay.portal.kernel.repository.LocalRepository;
import com.liferay.portal.kernel.repository.Repository;
import com.liferay.portal.kernel.repository.RepositoryFactory;
import com.liferay.portal.kernel.repository.RepositoryProvider;
import com.liferay.portal.model.Group;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.security.permission.PermissionThreadLocal;
import com.liferay.portal.service.GroupLocalService;
import com.liferay.portal.service.RepositoryLocalService;
import com.liferay.portlet.documentlibrary.NoSuchFolderException;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileShortcut;
import com.liferay.portlet.documentlibrary.model.DLFileVersion;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalService;
import com.liferay.portlet.documentlibrary.service.DLFileEntryServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFileShortcutLocalService;
import com.liferay.portlet.documentlibrary.service.DLFileVersionLocalService;
import com.liferay.portlet.documentlibrary.service.DLFolderLocalService;
import com.liferay.portlet.documentlibrary.service.permission.DLFileEntryPermission;
import com.liferay.portlet.documentlibrary.service.permission.DLFolderPermission;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Iván Zaera
 */
public class RepositoryProviderImpl
	implements RepositoryProvider, CacheRegistryItem {

	public void afterPropertiesSet() {
		CacheRegistryUtil.register(this);
	}

	@Override
	public List<LocalRepository> getLocalRepositoriesByGroupId(long groupId)
		throws PortalException {

		List<LocalRepository> localRepositories = new ArrayList<>();

		List<Long> repositoryIds = getRepositoryIdsByGroupId(groupId);

		for (long repositoryId : repositoryIds) {
			localRepositories.add(getLocalRepository(repositoryId));
		}

		return localRepositories;
	}

	@Override
	public LocalRepository getLocalRepository(long repositoryId)
		throws PortalException {

		LocalRepository localRepository = _localRepositoriesByRepositoryId.get(
			repositoryId);

		if (localRepository != null) {
			return localRepository;
		}

		localRepository = _repositoryFactory.createLocalRepository(
			repositoryId);

		checkRepository(repositoryId);
		checkRepositoryAccess(repositoryId);

		_localRepositoriesByRepositoryId.put(repositoryId, localRepository);

		return localRepository;
	}

	@Override
	public LocalRepository getLocalRepositoryByFileEntryId(long fileEntryId)
		throws PortalException {

		return getLocalRepository(getRepositoryIdByFileEntryId(fileEntryId));
	}

	@Override
	public LocalRepository getLocalRepositoryByFileShortcutId(
			long fileShortcutId)
		throws PortalException {

		return getLocalRepository(
			getRepositoryIdByFileShortcutId(fileShortcutId));
	}

	@Override
	public LocalRepository getLocalRepositoryByFileVersionId(long fileVersionId)
		throws PortalException {

		return getLocalRepository(
			getRepositoryIdByFileVersionId(fileVersionId));
	}

	@Override
	public LocalRepository getLocalRepositoryByFolderId(long folderId)
		throws PortalException {

		return getLocalRepository(getRepositoryIdByFolderId(folderId));
	}

	@Override
	public LocalRepository getLocalRepositoryByImageId(long imageId)
		throws PortalException {

		return getLocalRepository(getRepositoryIdByImageId(imageId));
	}

	@Override
	public String getRegistryName() {
		return RepositoryProviderImpl.class.getName();
	}

	@Override
	public List<Repository> getRepositoriesByGroupId(long groupId)
		throws PortalException {

		List<Repository> repositories = new ArrayList<>();

		List<Long> repositoryIds = getRepositoryIdsByGroupId(groupId);

		for (long repositoryId : repositoryIds) {
			repositories.add(getRepository(repositoryId));
		}

		return repositories;
	}

	@Override
	public Repository getRepository(long repositoryId) throws PortalException {
		Repository repository = _repositoriesByRepositoryId.get(repositoryId);

		if (repository != null) {
			return repository;
		}

		repository = _repositoryFactory.createRepository(repositoryId);

		checkRepository(repositoryId);
		checkRepositoryAccess(repositoryId);

		_repositoriesByRepositoryId.put(repositoryId, repository);

		return repository;
	}

	@Override
	public Repository getRepositoryByFileEntryId(long fileEntryId)
		throws PortalException {

		checkFileEntryPermissions(fileEntryId);

		return getRepository(getRepositoryIdByFileEntryId(fileEntryId));
	}

	@Override
	public Repository getRepositoryByFileShortcutId(long fileShortcutId)
		throws PortalException {

		checkFileShortcutPermissions(fileShortcutId);

		return getRepository(getRepositoryIdByFileShortcutId(fileShortcutId));
	}

	@Override
	public Repository getRepositoryByFileVersionId(long fileVersionId)
		throws PortalException {

		checkFileVersionPermissions(fileVersionId);

		return getRepository(getRepositoryIdByFileVersionId(fileVersionId));
	}

	@Override
	public Repository getRepositoryByFolderId(long folderId)
		throws PortalException {

		checkFolderPermissions(folderId);

		return getRepository(getRepositoryIdByFolderId(folderId));
	}

	@Override
	public Repository getRepositoryByImageId(long imageId)
		throws PortalException {

		return getRepository(getRepositoryIdByImageId(imageId));
	}

	@Override
	public void invalidate() {
		_localRepositoriesByRepositoryId.clear();
		_repositoriesByRepositoryId.clear();
	}

	@Override
	public void invalidateRepository(long repositoryId) {
		_localRepositoriesByRepositoryId.remove(repositoryId);

		_repositoriesByRepositoryId.remove(repositoryId);
	}

	protected void checkFileEntryPermissions(long fileEntryId)
		throws PortalException {

		DLFileEntry dlFileEntry = _dlFileEntryLocalService.fetchDLFileEntry(
			fileEntryId);

		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		if ((dlFileEntry != null) && (permissionChecker != null)) {
			DLFileEntryPermission.check(
				permissionChecker, fileEntryId, ActionKeys.VIEW);
		}
	}

	protected void checkFileShortcutPermissions(long fileShortcutId)
		throws PortalException {

		DLFileShortcut dlFileShortcut =
			_dlFileShortcutLocalService.fetchDLFileShortcut(fileShortcutId);

		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		if ((dlFileShortcut != null) && (permissionChecker != null)) {
			DLFileEntryPermission.check(
				permissionChecker, dlFileShortcut.getToFileEntryId(),
				ActionKeys.VIEW);
		}
	}

	protected void checkFileVersionPermissions(long fileVersionId)
		throws PortalException {

		DLFileVersion dlFileVersion =
			_dlFileVersionLocalService.fetchDLFileVersion(fileVersionId);

		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		if ((dlFileVersion != null) && (permissionChecker != null)) {
			DLFileEntryPermission.check(
				permissionChecker, dlFileVersion.getFileEntryId(),
				ActionKeys.VIEW);
		}
	}

	protected void checkFolderPermissions(long folderId)
		throws PortalException {

		DLFolder dlFolder = _dlFolderLocalService.fetchDLFolder(folderId);

		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		if ((dlFolder != null) && (permissionChecker != null)) {
			DLFolderPermission.check(
				permissionChecker, dlFolder, ActionKeys.VIEW);
		}
	}

	protected void checkRepository(long repositoryId) throws PortalException {
		Group group = _groupLocalService.fetchGroup(repositoryId);

		if (group != null) {
			return;
		}

		try {
			_repositoryLocalService.getRepository(repositoryId);
		}
		catch (NoSuchRepositoryException nsre) {
			throw new InvalidRepositoryIdException(nsre.getMessage());
		}
	}

	protected void checkRepositoryAccess(long repositoryId)
		throws PortalException {

		Group group = _groupLocalService.fetchGroup(repositoryId);

		if (group != null) {
			return;
		}

		try {
			com.liferay.portal.model.Repository repository =
				_repositoryLocalService.fetchRepository(repositoryId);

			PermissionChecker permissionChecker =
				PermissionThreadLocal.getPermissionChecker();

			if ((repository != null) && (permissionChecker != null)) {
				try {
					DLFolderPermission.check(
						permissionChecker, repository.getGroupId(),
						repository.getDlFolderId(), ActionKeys.VIEW);
				}
				catch (NoSuchFolderException nsfe) {
				}

				return;
			}
		}
		catch (NoSuchRepositoryException nsre) {
			throw new InvalidRepositoryIdException(nsre.getMessage());
		}
	}

	protected long getRepositoryIdByFileEntryId(long fileEntryId) {
		DLFileEntry dlFileEntry = _dlFileEntryLocalService.fetchDLFileEntry(
			fileEntryId);

		if (dlFileEntry != null) {
			return dlFileEntry.getRepositoryId();
		}

		throw new InvalidRepositoryIdException(
			"Missing a valid ID for file entry");
	}

	protected long getRepositoryIdByFileShortcutId(long fileShortcutId) {
		DLFileShortcut dlFileShortcut =
			_dlFileShortcutLocalService.fetchDLFileShortcut(fileShortcutId);

		if (dlFileShortcut != null) {
			return dlFileShortcut.getRepositoryId();
		}

		throw new InvalidRepositoryIdException(
			"Missing a valid ID for file shortcut");
	}

	protected long getRepositoryIdByFileVersionId(long fileVersionId) {
		DLFileVersion dlFileVersion =
			_dlFileVersionLocalService.fetchDLFileVersion(fileVersionId);

		if (dlFileVersion != null) {
			return dlFileVersion.getRepositoryId();
		}

		throw new InvalidRepositoryIdException(
			"Missing a valid ID for file version");
	}

	protected long getRepositoryIdByFolderId(long folderId) {
		DLFolder dlFolder = _dlFolderLocalService.fetchDLFolder(folderId);

		if (dlFolder != null) {
			if (dlFolder.isMountPoint()) {
				return dlFolder.getGroupId();
			}
			else {
				return dlFolder.getRepositoryId();
			}
		}

		throw new InvalidRepositoryIdException("Missing a valid ID for folder");
	}

	protected long getRepositoryIdByImageId(long imageId)
		throws PortalException {

		DLFileEntry dlFileEntry =
			DLFileEntryServiceUtil.fetchFileEntryByImageId(imageId);

		if (dlFileEntry != null) {
			return dlFileEntry.getRepositoryId();
		}

		throw new InvalidRepositoryIdException("Missing a valid ID for image");
	}

	protected List<Long> getRepositoryIdsByGroupId(long groupId)
		throws PortalException {

		List<com.liferay.portal.model.Repository> repositories =
			_repositoryLocalService.getRepositoriesByGroupId(groupId);

		List<Long> repositoryIds = new ArrayList<>(repositories.size() + 1);

		for (com.liferay.portal.model.Repository repository : repositories) {
			repositoryIds.add(repository.getRepositoryId());
		}

		repositoryIds.add(groupId);

		return repositoryIds;
	}

	@BeanReference(type = DLFileEntryLocalService.class)
	protected DLFileEntryLocalService _dlFileEntryLocalService;

	@BeanReference(type = DLFileShortcutLocalService.class)
	protected DLFileShortcutLocalService _dlFileShortcutLocalService;

	@BeanReference(type = DLFileVersionLocalService.class)
	protected DLFileVersionLocalService _dlFileVersionLocalService;

	@BeanReference(type = DLFolderLocalService.class)
	protected DLFolderLocalService _dlFolderLocalService;

	@BeanReference(type = GroupLocalService.class)
	protected GroupLocalService _groupLocalService;

	@BeanReference(type = RepositoryFactory.class)
	protected RepositoryFactory _repositoryFactory;

	@BeanReference(type = RepositoryLocalService.class)
	protected RepositoryLocalService _repositoryLocalService;

	private final Map<Long, LocalRepository> _localRepositoriesByRepositoryId =
		new ConcurrentHashMap<>();
	private final Map<Long, com.liferay.portal.kernel.repository.Repository>
		_repositoriesByRepositoryId = new ConcurrentHashMap<>();

}