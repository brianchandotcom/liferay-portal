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

package com.liferay.notification.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link NotificationQueueEntryService}.
 *
 * @author Gabriel Albuquerque
 * @see NotificationQueueEntryService
 * @generated
 */
public class NotificationQueueEntryServiceWrapper
	implements NotificationQueueEntryService,
			   ServiceWrapper<NotificationQueueEntryService> {

	public NotificationQueueEntryServiceWrapper() {
		this(null);
	}

	public NotificationQueueEntryServiceWrapper(
		NotificationQueueEntryService notificationQueueEntryService) {

		_notificationQueueEntryService = notificationQueueEntryService;
	}

	@Override
	public void deleteNotificationQueueEntry(long notificationQueueEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_notificationQueueEntryService.deleteNotificationQueueEntry(
			notificationQueueEntryId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _notificationQueueEntryService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.notification.model.NotificationQueueEntry
			resendNotificationQueueEntry(long notificationQueueEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _notificationQueueEntryService.resendNotificationQueueEntry(
			notificationQueueEntryId);
	}

	@Override
	public NotificationQueueEntryService getWrappedService() {
		return _notificationQueueEntryService;
	}

	@Override
	public void setWrappedService(
		NotificationQueueEntryService notificationQueueEntryService) {

		_notificationQueueEntryService = notificationQueueEntryService;
	}

	private NotificationQueueEntryService _notificationQueueEntryService;

}