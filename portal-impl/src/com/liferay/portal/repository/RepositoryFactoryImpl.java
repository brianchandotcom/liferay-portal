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
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.repository.BaseRepository;
import com.liferay.portal.kernel.repository.Repository;
import com.liferay.portal.kernel.repository.RepositoryFactory;
import com.liferay.portal.repository.liferayrepository.LiferayRepository;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileVersion;
import com.liferay.portlet.documentlibrary.model.DLFolder;

/**
 * @author Adolfo Pérez
 */
public class RepositoryFactoryImpl extends BaseRepositoryFactory<Repository>
	implements RepositoryFactory {

	@Override
	public Repository create(long repositoryId)
		throws PortalException, SystemException {

		long classNameId = getRepositoryClassNameId(repositoryId);

		if (classNameId == getDefaultClassNameId()) {
			return createLiferayRepository(repositoryId);
		}
		else {
			return createExternalRepository(repositoryId, classNameId);
		}
	}

	@Override
	public Repository create(
			long folderId, long fileEntryId, long fileVersionId)
		throws PortalException, SystemException {

		Repository liferayRepository = createLiferayRepository(
			folderId, fileEntryId, fileVersionId);

		if (liferayRepository != null) {
			return liferayRepository;
		}

		return createExternalRepository(folderId, fileEntryId, fileVersionId);
	}

	protected BaseRepository createExternalRepository(
			long repositoryId, long classNameId)
		throws PortalException, SystemException {

		return createExternalRepositoryImpl(repositoryId, classNameId);
	}

	protected Repository createExternalRepository(
			long folderId, long fileEntryId, long fileVersionId)
		throws PortalException, SystemException {

		long repositoryId = getRepositoryId(
			folderId, fileEntryId, fileVersionId);

		return create(repositoryId);
	}

	@Override
	protected LiferayRepository createLiferayRepositoryInstance(
		long groupId, long repositoryId, long dlFolderId) {

		return new LiferayRepository(
			getRepositoryLocalService(), getRepositoryService(),
			getDlAppHelperLocalService(), getDlFileEntryLocalService(),
			getDlFileEntryService(), getDlFileEntryTypeLocalService(),
			getDlFileVersionLocalService(), getDlFileVersionService(),
			getDlFolderLocalService(), getDlFolderService(),
			getResourceLocalService(), groupId, repositoryId, dlFolderId);
	}

	@Override
	protected long getFileEntryRepositoryId(long fileEntryId)
		throws PortalException, SystemException {

		DLFileEntry dlFileEntry = getDlFileEntryService().getFileEntry(
			fileEntryId);

		return dlFileEntry.getRepositoryId();
	}

	@Override
	protected long getFileVersionRepositoryId(long fileVersionId)
		throws PortalException, SystemException {

		DLFileVersion dlFileVersion =
			getDlFileVersionService().getFileVersion(fileVersionId);

		return dlFileVersion.getRepositoryId();
	}

	@Override
	protected long getFolderRepositoryId(long folderId)
		throws PortalException, SystemException {

		DLFolder dlFolder = getDlFolderService().getFolder(folderId);

		return dlFolder.getRepositoryId();
	}

	@Override
	protected com.liferay.portal.model.Repository getRepository(
			long repositoryId)
		throws PortalException, SystemException {

		try {
			return getRepositoryService().getRepository(repositoryId);
		}
		catch (NoSuchRepositoryException nsre) {
			return null;
		}
	}

}