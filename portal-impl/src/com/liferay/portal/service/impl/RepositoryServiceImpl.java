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

import com.liferay.portal.exception.NoSuchRepositoryException;
import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.repository.InvalidRepositoryIdException;
import com.liferay.portal.kernel.repository.RepositoryConfiguration;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.model.ClassName;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Repository;
import com.liferay.portal.repository.registry.RepositoryClassDefinition;
import com.liferay.portal.repository.registry.RepositoryClassDefinitionCatalog;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.base.RepositoryServiceBaseImpl;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileVersion;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.service.permission.DLFileEntryPermission;
import com.liferay.portlet.documentlibrary.service.permission.DLFolderPermission;
import com.liferay.portlet.documentlibrary.service.permission.DLPermission;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Alexander Chow
 * @author Mika Koivisto
 */
public class RepositoryServiceImpl extends RepositoryServiceBaseImpl {

	@Override
	public Repository addRepository(
			long groupId, long classNameId, long parentFolderId, String name,
			String description, String portletId,
			UnicodeProperties typeSettingsProperties,
			ServiceContext serviceContext)
		throws PortalException {

		DLPermission.check(
			getPermissionChecker(), groupId, ActionKeys.ADD_REPOSITORY);

		return repositoryLocalService.addRepository(
			getUserId(), groupId, classNameId, parentFolderId, name,
			description, portletId, typeSettingsProperties, false,
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

		DLFolderPermission.check(
			getPermissionChecker(), repository.getGroupId(),
			repository.getDlFolderId(), ActionKeys.DELETE);

		repositoryLocalService.deleteRepository(repository.getRepositoryId());
	}

	@Override
	public Repository getRepository(long repositoryId) throws PortalException {
		return repositoryPersistence.findByPrimaryKey(repositoryId);
	}

	/**
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
	@Override
	public String[] getSupportedConfigurations(long classNameId) {
		return _SUPPORTED_CONFIGURATIONS;
	}

	/**
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
	@Override
	public String[] getSupportedParameters(
		long classNameId, String configuration) {

		try {
			ClassName className = classNameLocalService.getClassName(
				classNameId);

			String repositoryImplClassName = className.getValue();

			return getSupportedParameters(
				repositoryImplClassName, configuration);
		}
		catch (PortalException pe) {
			throw new SystemException(pe);
		}
	}

	/**
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
	@Override
	public String[] getSupportedParameters(
		String className, String configuration) {

		try {
			if (!configuration.equals(_CONFIGURATION)) {
				throw new IllegalArgumentException(
					"Specified " + configuration + " does not match " +
						"supported configuration " + _CONFIGURATION);
			}

			Collection<String> supportedParameters = new ArrayList<>();

			RepositoryClassDefinition repositoryClassDefinition =
				_repositoryClassDefinitionCatalog.getRepositoryClassDefinition(
					className);

			RepositoryConfiguration repositoryConfiguration =
				repositoryClassDefinition.getRepositoryConfiguration();

			Collection<RepositoryConfiguration.Parameter>
				repositoryConfigurationParameters =
					repositoryConfiguration.getParameters();

			for (RepositoryConfiguration.Parameter
					repositoryConfigurationParameter :
						repositoryConfigurationParameters) {

				supportedParameters.add(
					repositoryConfigurationParameter.getName());
			}

			return supportedParameters.toArray(
				new String[repositoryConfigurationParameters.size()]);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
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

		DLFolderPermission.check(
			getPermissionChecker(), repository.getGroupId(),
			repository.getDlFolderId(), ActionKeys.UPDATE);

		repositoryLocalService.updateRepository(
			repositoryId, name, description);
	}

	protected void checkModelPermissions(
			long folderId, long fileEntryId, long fileVersionId)
		throws PortalException {

		if (folderId != 0) {
			DLFolder dlFolder = dlFolderLocalService.fetchDLFolder(folderId);

			if (dlFolder != null) {
				DLFolderPermission.check(
					getPermissionChecker(), dlFolder, ActionKeys.VIEW);
			}
		}
		else if (fileEntryId != 0) {
			DLFileEntry dlFileEntry = dlFileEntryLocalService.fetchDLFileEntry(
				fileEntryId);

			if (dlFileEntry != null) {
				DLFileEntryPermission.check(
					getPermissionChecker(), fileEntryId, ActionKeys.VIEW);
			}
		}
		else if (fileVersionId != 0) {
			DLFileVersion dlFileVersion =
				dlFileVersionLocalService.fetchDLFileVersion(fileVersionId);

			if (dlFileVersion != null) {
				DLFileEntryPermission.check(
					getPermissionChecker(), dlFileVersion.getFileEntryId(),
					ActionKeys.VIEW);
			}
		}
	}

	protected void checkRepository(
			long repositoryId, long folderId, long fileEntryId,
			long fileVersionId)
		throws PortalException {

		Group group = groupPersistence.fetchByPrimaryKey(repositoryId);

		if (group != null) {
			checkModelPermissions(folderId, fileEntryId, fileVersionId);

			return;
		}

		try {
			Repository repository = repositoryPersistence.fetchByPrimaryKey(
				repositoryId);

			if (repository != null) {
				DLFolderPermission.check(
					getPermissionChecker(), repository.getGroupId(),
					repository.getDlFolderId(), ActionKeys.VIEW);

				return;
			}
		}
		catch (NoSuchRepositoryException nsre) {
			throw new InvalidRepositoryIdException(nsre.getMessage());
		}
	}

	private static final String _CONFIGURATION = "DEFAULT";

	private static final String[] _SUPPORTED_CONFIGURATIONS =
		new String[] {_CONFIGURATION};

	@BeanReference(type = RepositoryClassDefinitionCatalog.class)
	private RepositoryClassDefinitionCatalog _repositoryClassDefinitionCatalog;

}