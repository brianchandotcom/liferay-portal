/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.service;

/**
 * Provides the local service utility for FaroEmail. This utility wraps
 * <code>com.liferay.osb.faro.service.impl.FaroEmailLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Matthew Kong
 * @see FaroEmailLocalService
 * @generated
 */
public class FaroEmailLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.osb.faro.service.impl.FaroEmailLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static java.lang.String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static java.util.ResourceBundle getResourceBundle(
		java.util.Locale locale) {

		return getService().getResourceBundle(locale);
	}

	public static java.lang.String getTemplate(java.lang.String name)
		throws java.lang.Exception {

		return getService().getTemplate(name);
	}

	public static FaroEmailLocalService getService() {
		return _service;
	}

	public static void setService(FaroEmailLocalService service) {
		_service = service;
	}

	private static volatile FaroEmailLocalService _service;

}