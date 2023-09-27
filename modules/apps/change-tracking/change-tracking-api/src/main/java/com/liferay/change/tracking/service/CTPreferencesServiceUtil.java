/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.service;

import com.liferay.change.tracking.model.CTPreferences;
import com.liferay.portal.kernel.exception.PortalException;

/**
 * Provides the remote service utility for CTPreferences. This utility wraps
 * <code>com.liferay.change.tracking.service.impl.CTPreferencesServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see CTPreferencesService
 * @generated
 */
public class CTPreferencesServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.change.tracking.service.impl.CTPreferencesServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static CTPreferences checkoutCTCollection(
			long companyId, long userId, long ctCollectionId)
		throws PortalException {

		return getService().checkoutCTCollection(
			companyId, userId, ctCollectionId);
	}

	public static CTPreferences enablePublications(
			long companyId, boolean enable)
		throws PortalException {

		return getService().enablePublications(companyId, enable);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static CTPreferencesService getService() {
		return _service;
	}

	public static void setService(CTPreferencesService service) {
		_service = service;
	}

	private static volatile CTPreferencesService _service;

}