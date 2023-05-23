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

package com.liferay.notification.internal.scheduler;

import com.liferay.notification.constants.NotificationConstants;
import com.liferay.notification.constants.NotificationQueueEntryConstants;
import com.liferay.notification.internal.configuration.NotificationQueueConfiguration;
import com.liferay.notification.service.NotificationQueueEntryLocalService;
import com.liferay.notification.type.NotificationType;
import com.liferay.notification.type.NotificationTypeServiceTracker;
import com.liferay.petra.function.UnsafeRunnable;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.scheduler.SchedulerJobConfiguration;
import com.liferay.portal.kernel.scheduler.TimeUnit;
import com.liferay.portal.kernel.scheduler.TriggerConfiguration;
import com.liferay.portal.kernel.util.Time;

import java.util.Date;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Gustavo Lima
 */
@Component(
	configurationPid = "com.liferay.notification.internal.configuration.NotificationQueueConfiguration",
	service = SchedulerJobConfiguration.class
)
public class CheckNotificationQueueEntrySchedulerJobConfiguration
	implements SchedulerJobConfiguration {

	@Override
	public UnsafeRunnable<Exception> getJobExecutorUnsafeRunnable() {
		return () -> {
			NotificationType notificationType =
				_notificationTypeServiceTracker.getNotificationType(
					NotificationConstants.TYPE_EMAIL);

			notificationType.resendNotifications(
				NotificationQueueEntryConstants.STATUS_FAILED,
				NotificationConstants.TYPE_EMAIL);
			notificationType.resendNotifications(
				NotificationQueueEntryConstants.STATUS_UNSENT,
				NotificationConstants.TYPE_EMAIL);

			NotificationQueueConfiguration notificationQueueConfiguration =
				_configurationProvider.getSystemConfiguration(
					NotificationQueueConfiguration.class);

			long deleteInterval =
				notificationQueueConfiguration.deleteInterval() * Time.MINUTE;

			_notificationQueueEntryLocalService.deleteNotificationQueueEntries(
				new Date(System.currentTimeMillis() - deleteInterval));
		};
	}

	@Override
	public TriggerConfiguration getTriggerConfiguration() {
		return TriggerConfiguration.createTriggerConfiguration(
			15, TimeUnit.MINUTE);
	}

	@Reference
	private ConfigurationProvider _configurationProvider;

	@Reference
	private NotificationQueueEntryLocalService
		_notificationQueueEntryLocalService;

	@Reference
	private NotificationTypeServiceTracker _notificationTypeServiceTracker;

}