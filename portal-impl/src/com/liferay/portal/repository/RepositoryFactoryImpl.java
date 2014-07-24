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
import com.liferay.portal.kernel.repository.Repository;
import com.liferay.portal.kernel.repository.RepositoryFactory;
import com.liferay.portal.kernel.repository.registry.RepositoryCreator;
import com.liferay.portal.repository.capabilities.CapabilityRepository;
import com.liferay.portal.service.RepositoryLocalService;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileVersion;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.service.DLFileEntryService;
import com.liferay.portlet.documentlibrary.service.DLFileVersionService;
import com.liferay.portlet.documentlibrary.service.DLFolderService;

/**
 * @author Adolfo Pérez
 */
public class RepositoryFactoryImpl extends BaseRepositoryFactory<Repository>
	implements RepositoryFactory {

	@Override
	protected Repository createExternalRepository(
			long repositoryId, long classNameId)
		throws PortalException {

		RepositoryCreator externalRepositoryCreator =
			getExternalRepositoryCreator();

		Repository repository = externalRepositoryCreator.createRepository(
			repositoryId);

		return new CapabilityRepository(
			repository, getExternalSupportedCapabilities(),
			getExternalExportedCapabilityClasses());
	}

	@Override
	protected Repository createExternalRepository(
			long folderId, long fileEntryId, long fileVersionId)
		throws PortalException {

		long repositoryId = getRepositoryId(
			folderId, fileEntryId, fileVersionId);

		return create(repositoryId);
	}

	@Override
	protected Repository createInternalRepository(long repositoryId)
		throws PortalException {

		RepositoryCreator liferayRepositoryCreator =
			getLiferayRepositoryCreator();

		Repository repository = liferayRepositoryCreator.createRepository(
			repositoryId);

		return new CapabilityRepository(
			repository, getInternalSupportedCapabilities(),
			getInternalExportedCapabilityClasses());
	}

	@Override
	protected long getFileEntryRepositoryId(long fileEntryId)
		throws PortalException {

		DLFileEntryService dlFileEntryService = getDlFileEntryService();

		DLFileEntry dlFileEntry = dlFileEntryService.getFileEntry(fileEntryId);

		return dlFileEntry.getRepositoryId();
	}

	@Override
	protected long getFileVersionRepositoryId(long fileVersionId)
		throws PortalException {

		DLFileVersionService dlFileVersionService = getDlFileVersionService();

		DLFileVersion dlFileVersion = dlFileVersionService.getFileVersion(
			fileVersionId);

		return dlFileVersion.getRepositoryId();
	}

	@Override
	protected long getFolderRepositoryId(long folderId) throws PortalException {
		DLFolderService dlFolderService = getDlFolderService();

		DLFolder dlFolder = dlFolderService.getFolder(folderId);

		return dlFolder.getRepositoryId();
	}

	@Override
	protected com.liferay.portal.model.Repository getRepository(
		long repositoryId) {

		RepositoryLocalService repositoryLocalService =
			getRepositoryLocalService();

		return repositoryLocalService.fetchRepository(repositoryId);
	}

}