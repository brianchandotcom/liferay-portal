/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.service;

import com.liferay.object.model.ObjectEntryFolder;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.module.service.Snapshot;

import java.util.Map;

/**
 * Provides the remote service utility for ObjectEntryFolder. This utility wraps
 * <code>com.liferay.object.service.impl.ObjectEntryFolderServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Marco Leo
 * @see ObjectEntryFolderService
 * @generated
 */
public class ObjectEntryFolderServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.object.service.impl.ObjectEntryFolderServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static ObjectEntryFolder addObjectEntryFolder(
			String externalReferenceCode, long groupId,
			long parentObjectEntryFolderId,
			Map<java.util.Locale, String> labelMap, String name,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().addObjectEntryFolder(
			externalReferenceCode, groupId, parentObjectEntryFolderId, labelMap,
			name, serviceContext);
	}

	public static ObjectEntryFolder deleteObjectEntryFolder(
			long objectEntryFolderId)
		throws PortalException {

		return getService().deleteObjectEntryFolder(objectEntryFolderId);
	}

	public static ObjectEntryFolder getObjectEntryFolder(
			long objectEntryFolderId)
		throws PortalException {

		return getService().getObjectEntryFolder(objectEntryFolderId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static ObjectEntryFolder updateObjectEntryFolder(
			long objectEntryFolderId, long parentObjectEntryFolderId,
			Map<java.util.Locale, String> labelMap, String name)
		throws PortalException {

		return getService().updateObjectEntryFolder(
			objectEntryFolderId, parentObjectEntryFolderId, labelMap, name);
	}

	public static ObjectEntryFolderService getService() {
		return _serviceSnapshot.get();
	}

	private static final Snapshot<ObjectEntryFolderService> _serviceSnapshot =
		new Snapshot<>(
			ObjectEntryFolderServiceUtil.class, ObjectEntryFolderService.class);

}