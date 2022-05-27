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

package com.liferay.notification.rest.internal.resource.v1_0;

import com.liferay.notification.rest.resource.v1_0.NotificationQueueEntryResource;
import com.liferay.notification.service.NotificationQueueEntryService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsUtil;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Gabriel Albuquerque
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/notification-queue-entry.properties",
	scope = ServiceScope.PROTOTYPE,
	service = NotificationQueueEntryResource.class
)
public class NotificationQueueEntryResourceImpl
	extends BaseNotificationQueueEntryResourceImpl {

	@Override
	public void deleteNotificationQueueEntry(Long notificationQueueEntryId)
		throws Exception {

		_checkFeatureFlag();

		_notificationQueueEntryService.deleteNotificationQueueEntry(
			notificationQueueEntryId);
	}

	private void _checkFeatureFlag() throws Exception {
		if (!GetterUtil.getBoolean(PropsUtil.get("feature.flag.LPS-149050"))) {
			throw new UnsupportedOperationException();
		}
	}

	@Reference
	private NotificationQueueEntryService _notificationQueueEntryService;

}