/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

	public DLTrashLocalServiceWrapper() {
		this(null);
	}

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