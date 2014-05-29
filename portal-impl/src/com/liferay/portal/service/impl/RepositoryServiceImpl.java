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

import com.liferay.portal.NoSuchRepositoryException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.repository.BaseRepository;
import com.liferay.portal.kernel.repository.LocalRepository;
import com.liferay.portal.kernel.repository.RepositoryException;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.model.ClassName;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Repository;
import com.liferay.portal.repository.util.ExternalRepositoryFactoryUtil;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.base.RepositoryServiceBaseImpl;
import com.liferay.portlet.documentlibrary.service.permission.DLFolderPermission;
import com.liferay.portlet.documentlibrary.service.permission.DLPermission;

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
		throws PortalException, SystemException {

		DLPermission.check(
			getPermissionChecker(), groupId, ActionKeys.ADD_REPOSITORY);

		return repositoryLocalService.addRepository(
			getUserId(), groupId, classNameId, parentFolderId, name,
			description, portletId, typeSettingsProperties, false,
			serviceContext);
	}

	@Override
	public void checkRepository(long repositoryId)
		throws PortalException, SystemException {

		Group group = groupPersistence.fetchByPrimaryKey(repositoryId);

		if (group != null) {
			return;
		}

		try {
			Repository repository = repositoryPersistence.findByPrimaryKey(
				repositoryId);

			DLFolderPermission.check(
				getPermissionChecker(), repository.getGroupId(),
				repository.getDlFolderId(), ActionKeys.VIEW);
		}
		catch (NoSuchRepositoryException nsre) {
			throw new RepositoryException(nsre.getMessage());
		}
	}

	@Override
	public void deleteRepository(long repositoryId)
		throws PortalException, SystemException {

		Repository repository = repositoryPersistence.findByPrimaryKey(
			repositoryId);

		DLFolderPermission.check(
			getPermissionChecker(), repository.getGroupId(),
			repository.getDlFolderId(), ActionKeys.DELETE);

		repositoryLocalService.deleteRepository(repository.getRepositoryId());
	}

	@Override
	public LocalRepository getLocalRepositoryImpl(long repositoryId)
		throws PortalException, SystemException {

		checkRepository(repositoryId);

		return repositoryLocalService.getLocalRepositoryImpl(repositoryId);
	}

	@Override
	public LocalRepository getLocalRepositoryImpl(
			long folderId, long fileEntryId, long fileVersionId)
		throws PortalException, SystemException {

		LocalRepository localRepositoryImpl =
			repositoryLocalService.getLocalRepositoryImpl(
				folderId, fileEntryId, fileVersionId);

		checkRepository(localRepositoryImpl.getRepositoryId());

		return localRepositoryImpl;
	}

	@Override
	public Repository getRepository(long repositoryId)
		throws PortalException, SystemException {

		return repositoryPersistence.findByPrimaryKey(repositoryId);
	}

	@Override
	public com.liferay.portal.kernel.repository.Repository getRepositoryImpl(
			long repositoryId)
		throws PortalException, SystemException {

		checkRepository(repositoryId);

		return repositoryLocalService.getRepositoryImpl(repositoryId);
	}

	@Override
	public com.liferay.portal.kernel.repository.Repository getRepositoryImpl(
			long folderId, long fileEntryId, long fileVersionId)
		throws PortalException, SystemException {

		com.liferay.portal.kernel.repository.Repository repositoryImpl =
			repositoryLocalService.getRepositoryImpl(
				folderId, fileEntryId, fileVersionId);

		checkRepository(repositoryImpl.getRepositoryId());

		return repositoryImpl;
	}

	@Override
	public String[] getSupportedConfigurations(long classNameId)
		throws SystemException {

		try {
			ClassName className = classNameLocalService.getClassName(
				classNameId);

			String repositoryImplClassName = className.getValue();

			BaseRepository baseRepository =
				ExternalRepositoryFactoryUtil.getInstance(
					repositoryImplClassName);

			return baseRepository.getSupportedConfigurations();
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	@Override
	public String[] getSupportedParameters(
			long classNameId, String configuration)
		throws SystemException {

		try {
			ClassName className = classNameLocalService.getClassName(
				classNameId);

			String repositoryImplClassName = className.getValue();

			BaseRepository baseRepository =
				ExternalRepositoryFactoryUtil.getInstance(
					repositoryImplClassName);

			String[] supportedConfigurations =
				baseRepository.getSupportedConfigurations();

			String[][] supportedParameters =
				baseRepository.getSupportedParameters();

			for (int i = 0; i < supportedConfigurations.length; i++) {
				if (supportedConfigurations[i].equals(configuration)) {
					return supportedParameters[i];
				}
			}

			throw new RepositoryException(
				"Configuration not found for repository with class name id " +
					classNameId);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	@Override
	public UnicodeProperties getTypeSettingsProperties(long repositoryId)
		throws PortalException, SystemException {

		checkRepository(repositoryId);

		return repositoryLocalService.getTypeSettingsProperties(repositoryId);
	}

	@Override
	public void updateRepository(
			long repositoryId, String name, String description)
		throws PortalException, SystemException {

		Repository repository = repositoryPersistence.findByPrimaryKey(
			repositoryId);

		DLFolderPermission.check(
			getPermissionChecker(), repository.getGroupId(),
			repository.getDlFolderId(), ActionKeys.UPDATE);

		repositoryLocalService.updateRepository(
			repositoryId, name, description);
	}

}