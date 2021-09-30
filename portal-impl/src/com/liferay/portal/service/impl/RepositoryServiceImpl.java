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

package com.liferay.portal.service.impl;

import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFileVersion;
import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.document.library.kernel.service.DLFileEntryLocalService;
import com.liferay.document.library.kernel.service.DLFileVersionLocalService;
import com.liferay.document.library.kernel.service.DLFolderLocalService;
import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.exception.NoSuchRepositoryException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Repository;
import com.liferay.portal.kernel.repository.InvalidRepositoryIdException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionFactory;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionUtil;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermissionFactory;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.persistence.GroupPersistence;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.repository.registry.RepositoryClassDefinitionCatalog;
import com.liferay.portal.service.base.RepositoryServiceBaseImpl;
import com.liferay.portlet.documentlibrary.constants.DLConstants;

/**
 * @author Alexander Chow
 * @author Mika Koivisto
 */
public class RepositoryServiceImpl extends RepositoryServiceBaseImpl {

	@Override
	public Repository addRepository(
			long groupId, long classNameId, long parentFolderId, String name,
			String description, String portletId,
			UnicodeProperties typeSettingsUnicodeProperties,
			ServiceContext serviceContext)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), groupId, ActionKeys.ADD_REPOSITORY);

		return repositoryLocalService.addRepository(
			getUserId(), groupId, classNameId, parentFolderId, name,
			description, portletId, typeSettingsUnicodeProperties, false,
			serviceContext);
	}

	@Override
	public void checkRepository(long repositoryId) throws PortalException {
		checkRepository(repositoryId, 0, 0, 0);
	}

	@Override
	public void deleteRepository(long repositoryId) throws PortalException {
		Repository repository = repositoryPersistence.findByPrimaryKey(
			repositoryId);

		ModelResourcePermissionUtil.check(
			_folderModelResourcePermission, getPermissionChecker(),
			repository.getGroupId(), repository.getDlFolderId(),
			ActionKeys.DELETE);

		repositoryLocalService.deleteRepository(repository.getRepositoryId());
	}

	@Override
	public Repository getRepository(long repositoryId) throws PortalException {
		Repository repository = repositoryPersistence.findByPrimaryKey(
			repositoryId);

		ModelResourcePermissionUtil.check(
			_folderModelResourcePermission, getPermissionChecker(),
			repository.getGroupId(), repository.getDlFolderId(),
			ActionKeys.VIEW);

		return repository;
	}

	@Override
	public Repository getRepository(long groupId, String portletId)
		throws PortalException {

		Repository repository = repositoryPersistence.findByG_N_P(
			groupId, portletId, portletId);

		ModelResourcePermissionUtil.check(
			_folderModelResourcePermission, getPermissionChecker(),
			repository.getGroupId(), repository.getDlFolderId(),
			ActionKeys.VIEW);

		return repository;
	}

	@Override
	public UnicodeProperties getTypeSettingsProperties(long repositoryId)
		throws PortalException {

		checkRepository(repositoryId);

		return repositoryLocalService.getTypeSettingsProperties(repositoryId);
	}

	@Override
	public void updateRepository(
			long repositoryId, String name, String description)
		throws PortalException {

		Repository repository = repositoryPersistence.findByPrimaryKey(
			repositoryId);

		ModelResourcePermissionUtil.check(
			_folderModelResourcePermission, getPermissionChecker(),
			repository.getGroupId(), repository.getDlFolderId(),
			ActionKeys.UPDATE);

		repositoryLocalService.updateRepository(
			repositoryId, name, description);
	}

	protected void checkModelPermissions(
			long folderId, long fileEntryId, long fileVersionId)
		throws PortalException {

		if (folderId != 0) {
			DLFolder dlFolder = _dlFolderLocalService.fetchDLFolder(folderId);

			if (dlFolder != null) {
				_folderModelResourcePermission.check(
					getPermissionChecker(), folderId, ActionKeys.VIEW);
			}
		}
		else if (fileEntryId != 0) {
			DLFileEntry dlFileEntry = _dlFileEntryLocalService.fetchDLFileEntry(
				fileEntryId);

			if (dlFileEntry != null) {
				_fileEntryModelResourcePermission.check(
					getPermissionChecker(), fileEntryId, ActionKeys.VIEW);
			}
		}
		else if (fileVersionId != 0) {
			DLFileVersion dlFileVersion =
				_dlFileVersionLocalService.fetchDLFileVersion(fileVersionId);

			if (dlFileVersion != null) {
				_fileEntryModelResourcePermission.check(
					getPermissionChecker(), dlFileVersion.getFileEntryId(),
					ActionKeys.VIEW);
			}
		}
	}

	protected void checkRepository(
			long repositoryId, long folderId, long fileEntryId,
			long fileVersionId)
		throws PortalException {

		Group group = _groupPersistence.fetchByPrimaryKey(repositoryId);

		if (group != null) {
			checkModelPermissions(folderId, fileEntryId, fileVersionId);

			return;
		}

		try {
			Repository repository = repositoryPersistence.fetchByPrimaryKey(
				repositoryId);

			if (repository != null) {
				ModelResourcePermissionUtil.check(
					_folderModelResourcePermission, getPermissionChecker(),
					repository.getGroupId(), repository.getDlFolderId(),
					ActionKeys.VIEW);
			}
		}
		catch (NoSuchRepositoryException noSuchRepositoryException) {
			throw new InvalidRepositoryIdException(
				noSuchRepositoryException.getMessage());
		}
	}

	private static volatile ModelResourcePermission<FileEntry>
		_fileEntryModelResourcePermission =
			ModelResourcePermissionFactory.getInstance(
				RepositoryServiceImpl.class,
				"_fileEntryModelResourcePermission", FileEntry.class);
	private static volatile ModelResourcePermission<Folder>
		_folderModelResourcePermission =
			ModelResourcePermissionFactory.getInstance(
				RepositoryServiceImpl.class, "_folderModelResourcePermission",
				Folder.class);
	private static volatile PortletResourcePermission
		_portletResourcePermission =
			PortletResourcePermissionFactory.getInstance(
				RepositoryServiceImpl.class, "_portletResourcePermission",
				DLConstants.RESOURCE_NAME);

	@BeanReference(type = DLFileEntryLocalService.class)
	private DLFileEntryLocalService _dlFileEntryLocalService;

	@BeanReference(type = DLFileVersionLocalService.class)
	private DLFileVersionLocalService _dlFileVersionLocalService;

	@BeanReference(type = DLFolderLocalService.class)
	private DLFolderLocalService _dlFolderLocalService;

	@BeanReference(type = GroupPersistence.class)
	private GroupPersistence _groupPersistence;

	@BeanReference(type = RepositoryClassDefinitionCatalog.class)
	private RepositoryClassDefinitionCatalog _repositoryClassDefinitionCatalog;

}