/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.marketplace.service;

import com.liferay.marketplace.model.App;
import com.liferay.portal.kernel.exception.PortalException;

/**
 * Provides the remote service utility for App. This utility wraps
 * <code>com.liferay.marketplace.service.impl.AppServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Ryan Park
 * @see AppService
 * @generated
 */
public class AppServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.marketplace.service.impl.AppServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static App deleteApp(long appId) throws PortalException {
		return getService().deleteApp(appId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static void installApp(long remoteAppId) throws PortalException {
		getService().installApp(remoteAppId);
	}

	public static void uninstallApp(long remoteAppId) throws PortalException {
		getService().uninstallApp(remoteAppId);
	}

	public static App updateApp(java.io.File file) throws PortalException {
		return getService().updateApp(file);
	}

	public static AppService getService() {
		return _service;
	}

	public static void setService(AppService service) {
		_service = service;
	}

	private static volatile AppService _service;

}