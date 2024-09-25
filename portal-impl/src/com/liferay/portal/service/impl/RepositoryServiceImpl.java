/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionRegistryUtil;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionUtil;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.persistence.GroupPersistence;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.repository.registry.RepositoryClassDefinitionCatalog;
import com.liferay.portal.service.base.RepositoryServiceBaseImpl;
import com.liferay.portlet.documentlibrary.util.DLPortletResourcePermissionUtil;

/**
 * @author Alexander Chow
 * @author Mika Koivisto
 */
public class RepositoryServiceImpl extends RepositoryServiceBaseImpl {

	@Override
	public Repository addRepository(
			String externalReferenceCode, long groupId, long classNameId,
			long parentFolderId, String name, String description,
			String portletId, UnicodeProperties typeSettingsUnicodeProperties,
			ServiceContext serviceContext)
		throws PortalException {

		PortletResourcePermission portletResourcePermission =
			DLPortletResourcePermissionUtil.getPortletResourcePermission();

		portletResourcePermission.check(
			getPermissionChecker(), groupId, ActionKeys.ADD_REPOSITORY);

		return repositoryLocalService.addRepository(
			externalReferenceCode, getUserId(), groupId, classNameId,
			parentFolderId, name, description, portletId,
			typeSettingsUnicodeProperties, false, serviceContext);
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
			ModelResourcePermissionRegistryUtil.
				<Folder>getModelResourcePermission(Folder.class.getName()),
			getPermissionChecker(), repository.getGroupId(),
			repository.getDlFolderId(), ActionKeys.DELETE);

		repositoryLocalService.deleteRepository(repository.getRepositoryId());
	}

	@Override
	public Repository getRepository(long repositoryId) throws PortalException {
		Repository repository = repositoryPersistence.findByPrimaryKey(
			repositoryId);

		ModelResourcePermissionUtil.check(
			ModelResourcePermissionRegistryUtil.
				<Folder>getModelResourcePermission(Folder.class.getName()),
			getPermissionChecker(), repository.getGroupId(),
			repository.getDlFolderId(), ActionKeys.VIEW);

		return repository;
	}

	@Override
	public Repository getRepository(long groupId, String portletId)
		throws PortalException {

		Repository repository = repositoryPersistence.findByG_N_P(
			groupId, portletId, portletId);

		ModelResourcePermissionUtil.check(
			ModelResourcePermissionRegistryUtil.
				<Folder>getModelResourcePermission(Folder.class.getName()),
			getPermissionChecker(), repository.getGroupId(),
			repository.getDlFolderId(), ActionKeys.VIEW);

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
			ModelResourcePermissionRegistryUtil.
				<Folder>getModelResourcePermission(Folder.class.getName()),
			getPermissionChecker(), repository.getGroupId(),
			repository.getDlFolderId(), ActionKeys.UPDATE);

		repositoryLocalService.updateRepository(
			repositoryId, name, description);
	}

	protected void checkModelPermissions(
			long folderId, long fileEntryId, long fileVersionId)
		throws PortalException {

		if (folderId != 0) {
			DLFolder dlFolder = _dlFolderLocalService.fetchDLFolder(folderId);

			if (dlFolder != null) {
				ModelResourcePermission<Folder> folderModelResourcePermission =
					ModelResourcePermissionRegistryUtil.
						getModelResourcePermission(Folder.class.getName());

				folderModelResourcePermission.check(
					getPermissionChecker(), folderId, ActionKeys.VIEW);
			}
		}
		else if (fileEntryId != 0) {
			DLFileEntry dlFileEntry = _dlFileEntryLocalService.fetchDLFileEntry(
				fileEntryId);

			if (dlFileEntry != null) {
				ModelResourcePermission<FileEntry>
					fileEntryModelResourcePermission =
						ModelResourcePermissionRegistryUtil.
							getModelResourcePermission(
								FileEntry.class.getName());

				fileEntryModelResourcePermission.check(
					getPermissionChecker(), fileEntryId, ActionKeys.VIEW);
			}
		}
		else if (fileVersionId != 0) {
			DLFileVersion dlFileVersion =
				_dlFileVersionLocalService.fetchDLFileVersion(fileVersionId);

			if (dlFileVersion != null) {
				ModelResourcePermission<FileEntry>
					fileEntryModelResourcePermission =
						ModelResourcePermissionRegistryUtil.
							getModelResourcePermission(
								FileEntry.class.getName());

				fileEntryModelResourcePermission.check(
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
					ModelResourcePermissionRegistryUtil.
						<Folder>getModelResourcePermission(
							Folder.class.getName()),
					getPermissionChecker(), repository.getGroupId(),
					repository.getDlFolderId(), ActionKeys.VIEW);
			}
		}
		catch (NoSuchRepositoryException noSuchRepositoryException) {
			throw new InvalidRepositoryIdException(
				noSuchRepositoryException.getMessage());
		}
	}

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