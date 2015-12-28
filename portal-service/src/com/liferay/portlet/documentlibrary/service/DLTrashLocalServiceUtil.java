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

package com.liferay.portlet.documentlibrary.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * Provides the local service utility for DLTrash. This utility wraps
 * {@link com.liferay.portlet.documentlibrary.service.impl.DLTrashLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see DLTrashLocalService
 * @see com.liferay.portlet.documentlibrary.service.base.DLTrashLocalServiceBaseImpl
 * @see com.liferay.portlet.documentlibrary.service.impl.DLTrashLocalServiceImpl
 * @generated
 */
@ProviderType
public class DLTrashLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.portlet.documentlibrary.service.impl.DLTrashLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static java.lang.String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.portal.kernel.repository.model.FileEntry moveFileEntryFromTrash(
		long userId, long repositoryId, long fileEntryId, long newFolderId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .moveFileEntryFromTrash(userId, repositoryId, fileEntryId,
			newFolderId, serviceContext);
	}

	public static com.liferay.portal.kernel.repository.model.FileEntry moveFileEntryToTrash(
		long userId, long repositoryId, long fileEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .moveFileEntryToTrash(userId, repositoryId, fileEntryId);
	}

	public static void restoreFileEntryFromTrash(long userId,
		long repositoryId, long fileEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().restoreFileEntryFromTrash(userId, repositoryId, fileEntryId);
	}

	public static DLTrashLocalService getService() {
		if (_service == null) {
			_service = (DLTrashLocalService)PortalBeanLocatorUtil.locate(DLTrashLocalService.class.getName());

			ReferenceRegistry.registerReference(DLTrashLocalServiceUtil.class,
				"_service");
		}

		return _service;
	}

	private static DLTrashLocalService _service;
}