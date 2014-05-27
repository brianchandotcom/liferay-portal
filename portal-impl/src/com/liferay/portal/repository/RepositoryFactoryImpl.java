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
import com.liferay.portal.kernel.repository.InvalidRepositoryIdException;
import com.liferay.portal.kernel.repository.Repository;
import com.liferay.portal.kernel.repository.RepositoryFactory;
import com.liferay.portal.repository.liferayrepository.LiferayRepository;
import com.liferay.portlet.documentlibrary.NoSuchFileEntryException;
import com.liferay.portlet.documentlibrary.NoSuchFileVersionException;
import com.liferay.portlet.documentlibrary.NoSuchFolderException;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileVersion;
import com.liferay.portlet.documentlibrary.model.DLFolder;

/**
 * @author Adolfo Pérez
 */
public class RepositoryFactoryImpl extends BaseRepositoryFactory
	implements RepositoryFactory {

	@Override
	public Repository create(long repositoryId)
		throws PortalException, SystemException {

		long classNameId = getRepositoryClassNameId(repositoryId);

		if (classNameId == getDefaultClassNameId()) {
			return createLiferayRepository(repositoryId);
		}
		else {
			return createExternalRepositoryImpl(repositoryId, classNameId);
		}
	}

	@Override
	public Repository create(
			long folderId, long fileEntryId, long fileVersionId)
		throws PortalException, SystemException {

		LiferayRepository liferayRepository = createLiferayRepository(
			folderId, fileEntryId, fileVersionId);

		if (liferayRepository != null) {
			return liferayRepository;
		}

		long repositoryId = getRepositoryId(
			folderId, fileEntryId, fileVersionId);

		return create(repositoryId);
	}

	protected LiferayRepository createLiferayRepository(long repositoryId)
		throws PortalException, SystemException {

		long groupId;
		long actualRepositoryId;
		long dlFolderId;

		try {
			com.liferay.portal.model.Repository repository =
				getRepositoryService().getRepository(repositoryId);

			groupId = repository.getGroupId();
			actualRepositoryId = repository.getRepositoryId();
			dlFolderId = repository.getDlFolderId();
		}
		catch (NoSuchRepositoryException nsre) {
			groupId = repositoryId;
			actualRepositoryId = repositoryId;
			dlFolderId = 0;
		}

		return new LiferayRepository(
			getRepositoryLocalService(), getRepositoryService(),
			getDlAppHelperLocalService(), getDlFileEntryLocalService(),
			getDlFileEntryService(), getDlFileEntryTypeLocalService(),
			getDlFileVersionLocalService(), getDlFileVersionService(),
			getDlFolderLocalService(), getDlFolderService(),
			getResourceLocalService(), groupId, actualRepositoryId, dlFolderId);
	}

	protected LiferayRepository createLiferayRepository(
			long folderId, long fileEntryId, long fileVersionId)
		throws PortalException, SystemException {

		try {
			long repositoryId;

			if (folderId != 0) {
				DLFolder dlFolder = getDlFolderService().getFolder(folderId);

				repositoryId = dlFolder.getRepositoryId();
			}
			else if (fileEntryId != 0) {
				DLFileEntry dlFileEntry = getDlFileEntryService().getFileEntry(
					fileEntryId);

				repositoryId = dlFileEntry.getRepositoryId();
			}
			else if (fileVersionId != 0) {
				DLFileVersion dlFileVersion =
					getDlFileVersionService().getFileVersion(fileVersionId);

				repositoryId = dlFileVersion.getRepositoryId();
			}
			else {
				throw new InvalidRepositoryIdException(
					"Missing a valid ID for folder, file entry or file " +
						"version");
			}

			return createLiferayRepository(repositoryId);
		}
		catch (NoSuchFolderException nsfe) {
			return null;
		}
		catch (NoSuchFileEntryException nsfee) {
			return null;
		}
		catch (NoSuchFileVersionException nsfve) {
			return null;
		}
	}

}