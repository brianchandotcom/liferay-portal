/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.service;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * Provides the remote service utility for Permission. This utility wraps
 * <code>com.liferay.portal.service.impl.PermissionServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see PermissionService
 * @generated
 */
public class PermissionServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.portal.service.impl.PermissionServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * Checks to see if the group has permission to the service.
	 *
	 * @param groupId the primary key of the group
	 * @param name the service name
	 * @param primKey the primary key of the service
	 */
	public static void checkPermission(long groupId, String name, long primKey)
		throws PortalException {

		getService().checkPermission(groupId, name, primKey);
	}

	/**
	 * Checks to see if the group has permission to the service.
	 *
	 * @param groupId the primary key of the group
	 * @param name the service name
	 * @param primKey the primary key of the service
	 */
	public static void checkPermission(
			long groupId, String name, String primKey)
		throws PortalException {

		getService().checkPermission(groupId, name, primKey);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static PermissionService getService() {
		return _service;
	}

	public static void setService(PermissionService service) {
		_service = service;
	}

	private static volatile PermissionService _service;

}