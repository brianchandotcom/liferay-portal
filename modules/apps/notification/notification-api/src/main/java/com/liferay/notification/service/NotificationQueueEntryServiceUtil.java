/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.notification.service;

import com.liferay.notification.model.NotificationQueueEntry;
import com.liferay.portal.kernel.exception.PortalException;

/**
 * Provides the remote service utility for NotificationQueueEntry. This utility wraps
 * <code>com.liferay.notification.service.impl.NotificationQueueEntryServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Gabriel Albuquerque
 * @see NotificationQueueEntryService
 * @generated
 */
public class NotificationQueueEntryServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.notification.service.impl.NotificationQueueEntryServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static NotificationQueueEntry addNotificationQueueEntry(
			com.liferay.notification.context.NotificationContext
				notificationContext)
		throws PortalException {

		return getService().addNotificationQueueEntry(notificationContext);
	}

	public static NotificationQueueEntry deleteNotificationQueueEntry(
			long notificationQueueEntryId)
		throws PortalException {

		return getService().deleteNotificationQueueEntry(
			notificationQueueEntryId);
	}

	public static NotificationQueueEntry getNotificationQueueEntry(
			long notificationQueueEntryId)
		throws PortalException {

		return getService().getNotificationQueueEntry(notificationQueueEntryId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static NotificationQueueEntry resendNotificationQueueEntry(
			long notificationQueueEntryId)
		throws PortalException {

		return getService().resendNotificationQueueEntry(
			notificationQueueEntryId);
	}

	public static NotificationQueueEntryService getService() {
		return _service;
	}

	public static void setService(NotificationQueueEntryService service) {
		_service = service;
	}

	private static volatile NotificationQueueEntryService _service;

}