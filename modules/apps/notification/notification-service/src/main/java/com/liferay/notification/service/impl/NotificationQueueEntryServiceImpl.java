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

package com.liferay.notification.service.impl;

import com.liferay.notification.model.NotificationQueueEntry;
import com.liferay.notification.service.NotificationQueueEntryLocalService;
import com.liferay.notification.service.base.NotificationQueueEntryServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Paulo Albuquerque
 */
@Component(
	property = {
		"json.web.service.context.name=notification",
		"json.web.service.context.path=NotificationQueueEntry"
	},
	service = AopService.class
)
public class NotificationQueueEntryServiceImpl
	extends NotificationQueueEntryServiceBaseImpl {

	@Override
	public NotificationQueueEntry deleteNotificationQueueEntry(
			long notificationQueueEntryId)
		throws PortalException {

		_notificationQueueEntryModelResourcePermission.check(
			getPermissionChecker(), notificationQueueEntryId,
			ActionKeys.DELETE);

		return _notificationQueueEntryLocalService.deleteNotificationQueueEntry(
			notificationQueueEntryId);
	}

	@Override
	public NotificationQueueEntry getNotificationQueueEntry(
			long notificationQueueEntryId)
		throws PortalException {

		_notificationQueueEntryModelResourcePermission.check(
			getPermissionChecker(), notificationQueueEntryId, ActionKeys.VIEW);

		return _notificationQueueEntryLocalService.getNotificationQueueEntry(
			notificationQueueEntryId);
	}

	@Override
	public NotificationQueueEntry resendNotificationQueueEntry(
			long notificationQueueEntryId)
		throws PortalException {

		_notificationQueueEntryModelResourcePermission.check(
			getPermissionChecker(), notificationQueueEntryId,
			ActionKeys.UPDATE);

		return notificationQueueEntryLocalService.updateSent(
			notificationQueueEntryId, false);
	}

	@Reference
	private NotificationQueueEntryLocalService
		_notificationQueueEntryLocalService;

	@Reference(
		target = "(model.class.name=com.liferay.notification.model.NotificationQueueEntry)"
	)
	private ModelResourcePermission<NotificationQueueEntry>
		_notificationQueueEntryModelResourcePermission;

}