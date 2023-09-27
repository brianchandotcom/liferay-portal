/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
	public com.liferay.notification.model.NotificationQueueEntry
			addNotificationQueueEntry(
				com.liferay.notification.context.NotificationContext
					notificationContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _notificationQueueEntryService.addNotificationQueueEntry(
			notificationContext);
	}

	@Override
	public com.liferay.notification.model.NotificationQueueEntry
			deleteNotificationQueueEntry(long notificationQueueEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _notificationQueueEntryService.deleteNotificationQueueEntry(
			notificationQueueEntryId);
	}

	@Override
	public com.liferay.notification.model.NotificationQueueEntry
			getNotificationQueueEntry(long notificationQueueEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _notificationQueueEntryService.getNotificationQueueEntry(
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