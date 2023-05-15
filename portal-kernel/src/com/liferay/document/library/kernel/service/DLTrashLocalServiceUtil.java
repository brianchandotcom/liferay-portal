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

import com.liferay.portal.kernel.exception.PortalException;

/**
 * Provides the local service utility for DLTrash. This utility wraps
 * <code>com.liferay.portlet.documentlibrary.service.impl.DLTrashLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see DLTrashLocalService
 * @generated
 */
public class DLTrashLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.portlet.documentlibrary.service.impl.DLTrashLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.portal.kernel.repository.model.FileEntry
			moveFileEntryFromTrash(
				long userId, long repositoryId, long fileEntryId,
				long newFolderId,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().moveFileEntryFromTrash(
			userId, repositoryId, fileEntryId, newFolderId, serviceContext);
	}

	public static com.liferay.portal.kernel.repository.model.FileEntry
			moveFileEntryToTrash(
				long userId, long repositoryId, long fileEntryId)
		throws PortalException {

		return getService().moveFileEntryToTrash(
			userId, repositoryId, fileEntryId);
	}

	public static void restoreFileEntryFromTrash(
			long userId, long repositoryId, long fileEntryId)
		throws PortalException {

		getService().restoreFileEntryFromTrash(
			userId, repositoryId, fileEntryId);
	}

	public static DLTrashLocalService getService() {
		return _service;
	}

	public static void setService(DLTrashLocalService service) {
		_service = service;
	}

	private static volatile DLTrashLocalService _service;

}