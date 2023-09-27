/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.service;

import com.liferay.portal.kernel.model.ClassName;

/**
 * Provides the remote service utility for ClassName. This utility wraps
 * <code>com.liferay.portal.service.impl.ClassNameServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see ClassNameService
 * @generated
 */
public class ClassNameServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.portal.service.impl.ClassNameServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static ClassName fetchByClassNameId(long classNameId) {
		return getService().fetchByClassNameId(classNameId);
	}

	public static ClassName fetchClassName(String value) {
		return getService().fetchClassName(value);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static ClassNameService getService() {
		return _service;
	}

	public static void setService(ClassNameService service) {
		_service = service;
	}

	private static volatile ClassNameService _service;

}