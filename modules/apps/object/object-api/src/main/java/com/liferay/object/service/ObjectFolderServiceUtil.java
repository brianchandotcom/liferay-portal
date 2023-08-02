/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.service;

import com.liferay.object.model.ObjectFolder;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.List;
import java.util.Map;

/**
 * Provides the remote service utility for ObjectFolder. This utility wraps
 * <code>com.liferay.object.service.impl.ObjectFolderServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Marco Leo
 * @see ObjectFolderService
 * @generated
 */
public class ObjectFolderServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.object.service.impl.ObjectFolderServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static ObjectFolder addObjectFolder(
			String externalReferenceCode,
			Map<java.util.Locale, String> labelMap, String name)
		throws PortalException {

		return getService().addObjectFolder(
			externalReferenceCode, labelMap, name);
	}

	public static ObjectFolder deleteObjectFolder(long objectFolderId)
		throws PortalException {

		return getService().deleteObjectFolder(objectFolderId);
	}

	public static ObjectFolder getObjectFolder(long objectFolderId)
		throws PortalException {

		return getService().getObjectFolder(objectFolderId);
	}

	public static ObjectFolder getObjectFolderByExternalReferenceCode(
			String externalReferenceCode, long companyId)
		throws PortalException {

		return getService().getObjectFolderByExternalReferenceCode(
			externalReferenceCode, companyId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static ObjectFolder updateObjectFolder(
			String externalReferenceCode, long objectFolderId,
			Map<java.util.Locale, String> labelMap,
			List<com.liferay.object.model.ObjectFolderItem> objectFolderItems)
		throws PortalException {

		return getService().updateObjectFolder(
			externalReferenceCode, objectFolderId, labelMap, objectFolderItems);
	}

	public static ObjectFolderService getService() {
		return _service;
	}

	public static void setService(ObjectFolderService service) {
		_service = service;
	}

	private static volatile ObjectFolderService _service;

}