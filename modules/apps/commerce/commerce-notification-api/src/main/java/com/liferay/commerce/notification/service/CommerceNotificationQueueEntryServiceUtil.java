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

package com.liferay.commerce.notification.service;

import com.liferay.commerce.notification.model.CommerceNotificationQueueEntry;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

/**
 * Provides the remote service utility for CommerceNotificationQueueEntry. This utility wraps
 * <code>com.liferay.commerce.notification.service.impl.CommerceNotificationQueueEntryServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceNotificationQueueEntryService
 * @generated
 */
public class CommerceNotificationQueueEntryServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.commerce.notification.service.impl.CommerceNotificationQueueEntryServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static void deleteCommerceNotificationQueueEntry(
			long commerceNotificationQueueEntryId)
		throws PortalException {

		getService().deleteCommerceNotificationQueueEntry(
			commerceNotificationQueueEntryId);
	}

	public static List<CommerceNotificationQueueEntry>
			getCommerceNotificationQueueEntries(
				long groupId, int start, int end,
				OrderByComparator<CommerceNotificationQueueEntry>
					orderByComparator)
		throws PortalException {

		return getService().getCommerceNotificationQueueEntries(
			groupId, start, end, orderByComparator);
	}

	public static int getCommerceNotificationQueueEntriesCount(long groupId)
		throws PortalException {

		return getService().getCommerceNotificationQueueEntriesCount(groupId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static CommerceNotificationQueueEntry
			resendCommerceNotificationQueueEntry(
				long commerceNotificationQueueEntryId)
		throws PortalException {

		return getService().resendCommerceNotificationQueueEntry(
			commerceNotificationQueueEntryId);
	}

	public static CommerceNotificationQueueEntryService getService() {
		return _service;
	}

	public static void setService(
		CommerceNotificationQueueEntryService service) {

		_service = service;
	}

	private static volatile CommerceNotificationQueueEntryService _service;

}