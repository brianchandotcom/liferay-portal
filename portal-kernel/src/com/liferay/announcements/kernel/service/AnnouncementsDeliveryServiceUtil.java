/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.announcements.kernel.service;

import com.liferay.announcements.kernel.model.AnnouncementsDelivery;
import com.liferay.portal.kernel.exception.PortalException;

/**
 * Provides the remote service utility for AnnouncementsDelivery. This utility wraps
 * <code>com.liferay.portlet.announcements.service.impl.AnnouncementsDeliveryServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see AnnouncementsDeliveryService
 * @generated
 */
public class AnnouncementsDeliveryServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.portlet.announcements.service.impl.AnnouncementsDeliveryServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static AnnouncementsDelivery updateDelivery(
			long userId, String type, boolean email, boolean sms)
		throws PortalException {

		return getService().updateDelivery(userId, type, email, sms);
	}

	public static AnnouncementsDeliveryService getService() {
		return _service;
	}

	public static void setService(AnnouncementsDeliveryService service) {
		_service = service;
	}

	private static volatile AnnouncementsDeliveryService _service;

}