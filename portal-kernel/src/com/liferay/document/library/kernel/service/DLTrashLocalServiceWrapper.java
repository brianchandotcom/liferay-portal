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

package com.liferay.document.library.kernel.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link DLTrashLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see DLTrashLocalService
 * @generated
 */
public class DLTrashLocalServiceWrapper
	implements DLTrashLocalService, ServiceWrapper<DLTrashLocalService> {

	public DLTrashLocalServiceWrapper(DLTrashLocalService dlTrashLocalService) {
		_dlTrashLocalService = dlTrashLocalService;
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _dlTrashLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.repository.model.FileEntry
			moveFileEntryFromTrash(
				long userId, long repositoryId, long fileEntryId,
				long newFolderId,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _dlTrashLocalService.moveFileEntryFromTrash(
			userId, repositoryId, fileEntryId, newFolderId, serviceContext);
	}

	@Override
	public com.liferay.portal.kernel.repository.model.FileEntry
			moveFileEntryToTrash(
				long userId, long repositoryId, long fileEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _dlTrashLocalService.moveFileEntryToTrash(
			userId, repositoryId, fileEntryId);
	}

	@Override
	public void restoreFileEntryFromTrash(
			long userId, long repositoryId, long fileEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_dlTrashLocalService.restoreFileEntryFromTrash(
			userId, repositoryId, fileEntryId);
	}

	@Override
	public DLTrashLocalService getWrappedService() {
		return _dlTrashLocalService;
	}

	@Override
	public void setWrappedService(DLTrashLocalService dlTrashLocalService) {
		_dlTrashLocalService = dlTrashLocalService;
	}

	private DLTrashLocalService _dlTrashLocalService;

}