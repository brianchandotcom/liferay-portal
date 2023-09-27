/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.service;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * Provides the remote service utility for Portal. This utility wraps
 * <code>com.liferay.portal.service.impl.PortalServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see PortalService
 * @generated
 */
public class PortalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.portal.service.impl.PortalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static String getAutoDeployDirectory() {
		return getService().getAutoDeployDirectory();
	}

	public static int getBuildNumber() {
		return getService().getBuildNumber();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static String getVersion() {
		return getService().getVersion();
	}

	public static void testAddClassName_Rollback(String classNameValue) {
		getService().testAddClassName_Rollback(classNameValue);
	}

	public static void testAddClassName_Success(String classNameValue) {
		getService().testAddClassName_Success(classNameValue);
	}

	public static void testAutoSyncHibernateSessionStateOnTxCreation() {
		getService().testAutoSyncHibernateSessionStateOnTxCreation();
	}

	public static void testDeleteClassName() throws PortalException {
		getService().testDeleteClassName();
	}

	public static int testGetBuildNumber() {
		return getService().testGetBuildNumber();
	}

	public static void testGetUserId() {
		getService().testGetUserId();
	}

	public static boolean testHasClassName() {
		return getService().testHasClassName();
	}

	public static PortalService getService() {
		return _service;
	}

	public static void setService(PortalService service) {
		_service = service;
	}

	private static volatile PortalService _service;

}