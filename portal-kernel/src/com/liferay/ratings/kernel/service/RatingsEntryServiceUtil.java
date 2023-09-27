/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ratings.kernel.service;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.ratings.kernel.model.RatingsEntry;

/**
 * Provides the remote service utility for RatingsEntry. This utility wraps
 * <code>com.liferay.portlet.ratings.service.impl.RatingsEntryServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see RatingsEntryService
 * @generated
 */
public class RatingsEntryServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.portlet.ratings.service.impl.RatingsEntryServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static void deleteEntry(String className, long classPK)
		throws PortalException {

		getService().deleteEntry(className, classPK);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static RatingsEntry updateEntry(
			String className, long classPK, double score)
		throws PortalException {

		return getService().updateEntry(className, classPK, score);
	}

	public static RatingsEntryService getService() {
		return _service;
	}

	public static void setService(RatingsEntryService service) {
		_service = service;
	}

	private static volatile RatingsEntryService _service;

}