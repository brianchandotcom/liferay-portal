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
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.repository.BaseRepository;
import com.liferay.portal.kernel.repository.LocalRepository;
import com.liferay.portal.kernel.repository.LocalRepositoryFactory;
import com.liferay.portal.kernel.repository.RepositoryFactoryUtil;
import com.liferay.portal.repository.liferayrepository.LiferayLocalRepository;

/**
 * @author Adolfo Pérez
 */
public class LocalRepositoryFactoryImpl extends BaseRepositoryFactory
	implements LocalRepositoryFactory {

	@Override
	public LocalRepository create(long repositoryId)
		throws PortalException, SystemException {

		long classNameId = getRepositoryClassNameId(repositoryId);

		if (classNameId == getDefaultClassNameId()) {
			return new LiferayLocalRepository(
				getRepositoryLocalService(), getRepositoryService(),
				getDlAppHelperLocalService(), getDlFileEntryLocalService(),
				getDlFileEntryService(), getDlFileEntryTypeLocalService(),
				getDlFileVersionLocalService(), getDlFileVersionService(),
				getDlFolderLocalService(), getDlFolderService(),
				getResourceLocalService(), repositoryId);
		}
		else {
			BaseRepository baseRepository = createExternalRepositoryImpl(
				repositoryId, classNameId);

			return baseRepository.getLocalRepository();
		}
	}

	@Override
	public LocalRepository create(
			long folderId, long fileEntryId, long fileVersionId)
		throws PortalException, SystemException {

		LocalRepository localRepository = new LiferayLocalRepository(
			getRepositoryLocalService(), getRepositoryService(),
			getDlAppHelperLocalService(), getDlFileEntryLocalService(),
			getDlFileEntryService(), getDlFileEntryTypeLocalService(),
			getDlFileVersionLocalService(), getDlFileVersionService(),
			getDlFolderLocalService(), getDlFolderService(),
			getResourceLocalService(), folderId, fileEntryId, fileVersionId);

		if (localRepository.getRepositoryId() != 0) {
			return localRepository;
		}

		long repositoryId = getRepositoryId(
			folderId, fileEntryId, fileVersionId);

		BaseRepository baseRepository =
			(BaseRepository)RepositoryFactoryUtil.create(repositoryId);

		return baseRepository.getLocalRepository();
	}

}