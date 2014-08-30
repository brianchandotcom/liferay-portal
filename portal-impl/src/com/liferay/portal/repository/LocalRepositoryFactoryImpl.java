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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.LocalRepository;
import com.liferay.portal.kernel.repository.LocalRepositoryFactory;
import com.liferay.portal.kernel.repository.registry.RepositoryCreator;
import com.liferay.portal.model.Repository;
import com.liferay.portal.repository.capabilities.CapabilityLocalRepository;
import com.liferay.portal.repository.registry.RepositoryDefinition;
import com.liferay.portal.service.RepositoryLocalService;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileVersion;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalService;
import com.liferay.portlet.documentlibrary.service.DLFileVersionLocalService;
import com.liferay.portlet.documentlibrary.service.DLFolderLocalService;

/**
 * @author Adolfo Pérez
 */
public class LocalRepositoryFactoryImpl
	extends BaseRepositoryFactory<LocalRepository>
	implements LocalRepositoryFactory {

	@Override
	protected LocalRepository createExternalRepository(
			long repositoryId, long classNameId)
		throws PortalException {

		RepositoryDefinition repositoryDefinition = getRepositoryDefinition(
			classNameId);

		RepositoryCreator repositoryCreator =
			repositoryDefinition.getRepositoryCreator();

		LocalRepository localRepository =
			repositoryCreator.createLocalRepository(repositoryId);

		return new CapabilityLocalRepository(
			localRepository, repositoryDefinition.getSupportedCapabilities(),
			repositoryDefinition.getExportedCapabilities(),
			repositoryDefinition.getRepositoryEventTrigger());
	}

	@Override
	protected LocalRepository createExternalRepository(
			long folderId, long fileEntryId, long fileVersionId)
		throws PortalException {

		long repositoryId = getRepositoryId(
			folderId, fileEntryId, fileVersionId);

		return create(repositoryId);
	}

	@Override
	protected LocalRepository createInternalRepository(long repositoryId)
		throws PortalException {

		RepositoryDefinition repositoryDefinition = getRepositoryDefinition(
			getDefaultClassNameId());

		RepositoryCreator repositoryCreator =
			repositoryDefinition.getRepositoryCreator();

		LocalRepository localRepository =
			repositoryCreator.createLocalRepository(repositoryId);

		return new CapabilityLocalRepository(
			localRepository, repositoryDefinition.getSupportedCapabilities(),
			repositoryDefinition.getExportedCapabilities(),
			repositoryDefinition.getRepositoryEventTrigger());
	}

	@Override
	protected long getFileEntryRepositoryId(long fileEntryId)
		throws PortalException {

		DLFileEntryLocalService dlFileEntryLocalService =
			getDlFileEntryLocalService();

		DLFileEntry dlFileEntry = dlFileEntryLocalService.getFileEntry(
			fileEntryId);

		return dlFileEntry.getRepositoryId();
	}

	@Override
	protected long getFileVersionRepositoryId(long fileVersionId)
		throws PortalException {

		DLFileVersionLocalService dlFileVersionLocalService =
			getDlFileVersionLocalService();

		DLFileVersion dlFileVersion = dlFileVersionLocalService.getFileVersion(
			fileVersionId);

		return dlFileVersion.getRepositoryId();
	}

	@Override
	protected long getFolderRepositoryId(long folderId) throws PortalException {
		DLFolderLocalService dlFolderLocalService = getDlFolderLocalService();

		DLFolder dlFolder = dlFolderLocalService.getFolder(folderId);

		return dlFolder.getRepositoryId();
	}

	@Override
	protected Repository getRepository(long repositoryId) {
		RepositoryLocalService repositoryLocalService =
			getRepositoryLocalService();

		return repositoryLocalService.fetchRepository(repositoryId);
	}

}