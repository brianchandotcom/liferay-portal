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
import com.liferay.notification.internal.configuration.NotificationQueueConfiguration;
import com.liferay.notification.service.NotificationQueueEntryLocalService;
import com.liferay.notification.type.NotificationType;
import com.liferay.notification.type.NotificationTypeServiceTracker;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.function.UnsafeRunnable;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.scheduler.SchedulerJobConfiguration;
import com.liferay.portal.kernel.scheduler.TimeUnit;
import com.liferay.portal.kernel.scheduler.TriggerConfiguration;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Time;

import java.util.Date;
import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Gustavo Lima
 */
@Component(
	factory = "com.liferay.notification.internal.scheduler.CheckNotificationQueueEntrySchedulerJobConfiguration",
	service = SchedulerJobConfiguration.class
)
public class CheckNotificationQueueEntrySchedulerJobConfiguration
	implements SchedulerJobConfiguration {

	@Override
	public UnsafeConsumer<Long, Exception> getCompanyJobExecutor() {
		return companyId -> {
			NotificationType notificationType =
				_notificationTypeServiceTracker.getNotificationType(
					NotificationConstants.TYPE_EMAIL);

			notificationType.sendUnsentNotifications(companyId);

			long deleteInterval =
				_notificationQueueConfiguration.deleteInterval() * Time.MINUTE;

			_notificationQueueEntryLocalService.deleteNotificationQueueEntries(
				companyId,
				new Date(System.currentTimeMillis() - deleteInterval));
		};
	}

	@Override
	public UnsafeRunnable<Exception> getJobExecutor() {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getName() {
		Class<?> clazz = getClass();

		return StringBundler.concat(
			clazz.getName(), StringPool.POUND, _companyId);
	}

	@Override
	public TriggerConfiguration getTriggerConfiguration() {
		return TriggerConfiguration.createTriggerConfiguration(
			_notificationQueueConfiguration.checkInterval(), TimeUnit.MINUTE);
	}

	@Activate
	protected void activate(Map<String, Object> properties) {
		_companyId = GetterUtil.getLong(properties.get("companyId"));

		_notificationQueueConfiguration =
			(NotificationQueueConfiguration)properties.get("configuration");
	}

	private long _companyId;
	private NotificationQueueConfiguration _notificationQueueConfiguration;

	@Reference
	private NotificationQueueEntryLocalService
		_notificationQueueEntryLocalService;

	@Reference
	private NotificationTypeServiceTracker _notificationTypeServiceTracker;

}