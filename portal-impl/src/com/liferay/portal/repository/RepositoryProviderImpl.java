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
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.InvalidRepositoryIdException;
import com.liferay.portal.kernel.repository.LocalRepository;
import com.liferay.portal.kernel.repository.Repository;
import com.liferay.portal.kernel.repository.RepositoryFactory;
import com.liferay.portal.kernel.repository.RepositoryProvider;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.RepositoryEntry;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.security.permission.PermissionThreadLocal;
import com.liferay.portal.service.GroupLocalService;
import com.liferay.portal.service.RepositoryEntryLocalService;
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
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceRegistration;

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
		Registry registry = RegistryUtil.getRegistry();

		_serviceRegistration = registry.registerService(
			CacheRegistryItem.class, this);
	}

	public void destroy() {
		if (_serviceRegistration != null) {
			_serviceRegistration.unregister();
		}
	}

	@Override
	public LocalRepository getFileEntryLocalRepository(long fileEntryId)
		throws PortalException {

		return getLocalRepository(getFileEntryRepositoryId(fileEntryId));
	}

	@Override
	public Repository getFileEntryRepository(long fileEntryId)
		throws PortalException {

		checkFileEntryPermissions(fileEntryId);

		return getRepository(getFileEntryRepositoryId(fileEntryId));
	}

	@Override
	public LocalRepository getFileShortcutLocalRepository(long fileShortcutId)
		throws PortalException {

		return getLocalRepository(getFileShortcutRepositoryId(fileShortcutId));
	}

	@Override
	public Repository getFileShortcutRepository(long fileShortcutId)
		throws PortalException {

		checkFileShortcutPermissions(fileShortcutId);

		return getRepository(getFileShortcutRepositoryId(fileShortcutId));
	}

	@Override
	public LocalRepository getFileVersionLocalRepository(long fileVersionId)
		throws PortalException {

		return getLocalRepository(getFileVersionRepositoryId(fileVersionId));
	}

	@Override
	public Repository getFileVersionRepository(long fileVersionId)
		throws PortalException {

		checkFileVersionPermissions(fileVersionId);

		return getRepository(getFileVersionRepositoryId(fileVersionId));
	}

	@Override
	public LocalRepository getFolderLocalRepository(long folderId)
		throws PortalException {

		return getLocalRepository(getFolderRepositoryId(folderId));
	}

	@Override
	public Repository getFolderRepository(long folderId)
		throws PortalException {

		checkFolderPermissions(folderId);

		return getRepository(getFolderRepositoryId(folderId));
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

		LocalRepository localRepository = _localRepositories.get(repositoryId);

		if (localRepository != null) {
			return localRepository;
		}

		localRepository = repositoryFactory.createLocalRepository(repositoryId);

		checkRepository(repositoryId);
		checkRepositoryAccess(repositoryId);

		_localRepositories.put(repositoryId, localRepository);

		return localRepository;
	}

	@Override
	public String getRegistryName() {
		return RepositoryProviderImpl.class.getName();
	}

	@Override
	public Repository getRepository(long repositoryId) throws PortalException {
		Repository repository = _repositories.get(repositoryId);

		if (repository != null) {
			return repository;
		}

		repository = repositoryFactory.createRepository(repositoryId);

		checkRepository(repositoryId);
		checkRepositoryAccess(repositoryId);

		_repositories.put(repositoryId, repository);

		return repository;
	}

	@Override
	public void invalidate() {
		_localRepositories.clear();
		_repositories.clear();
	}

	@Override
	public void invalidateRepository(long repositoryId) {
		_localRepositories.remove(repositoryId);

		_repositories.remove(repositoryId);
	}

	protected void checkFileEntryPermissions(long fileEntryId)
		throws PortalException {

		DLFileEntry dlFileEntry = dlFileEntryLocalService.fetchDLFileEntry(
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
			dlFileShortcutLocalService.fetchDLFileShortcut(fileShortcutId);

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
			dlFileVersionLocalService.fetchDLFileVersion(fileVersionId);

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

		DLFolder dlFolder = dlFolderLocalService.fetchDLFolder(folderId);

		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		if ((dlFolder != null) && (permissionChecker != null)) {
			DLFolderPermission.check(
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
			com.liferay.portal.model.Repository repository =
				repositoryLocalService.fetchRepository(repositoryId);

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
			else {
				return dlFolder.getRepositoryId();
			}
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
		List<com.liferay.portal.model.Repository> repositories =
			repositoryLocalService.getGroupRepositories(groupId);

		List<Long> repositoryIds = new ArrayList<>(repositories.size() + 1);

		for (com.liferay.portal.model.Repository repository : repositories) {
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

	private final Map<Long, LocalRepository> _localRepositories =
		new ConcurrentHashMap<>();
	private final Map<Long, Repository> _repositories =
		new ConcurrentHashMap<>();
	private ServiceRegistration<CacheRegistryItem> _serviceRegistration;

}