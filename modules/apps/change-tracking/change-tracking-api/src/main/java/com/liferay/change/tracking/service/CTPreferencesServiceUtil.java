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

	private static volatile CTPreferencesService _service;

}