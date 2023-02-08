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

package com.liferay.notification.internal.messaging;

import com.liferay.notification.constants.NotificationConstants;
import com.liferay.notification.constants.NotificationQueueEntryConstants;
import com.liferay.notification.internal.configuration.NotificationQueueConfiguration;
import com.liferay.notification.service.NotificationQueueEntryLocalService;
import com.liferay.notification.type.NotificationType;
import com.liferay.notification.type.NotificationTypeServiceTracker;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.messaging.BaseMessageListener;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.scheduler.SchedulerEngineHelper;
import com.liferay.portal.kernel.scheduler.SchedulerEntryImpl;
import com.liferay.portal.kernel.scheduler.TimeUnit;
import com.liferay.portal.kernel.scheduler.TriggerFactory;
import com.liferay.portal.kernel.util.Time;

import java.util.Date;
import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Gustavo Lima
 */
@Component(service = {})
public class CheckNotificationQueueEntryMessageListener
	extends BaseMessageListener {

	@Activate
	protected void activate(Map<String, Object> properties)
		throws ConfigurationException {

		Class<?> clazz = getClass();

		String className = StringBundler.concat(
			clazz.getName(), StringPool.POUND, properties.get("companyId"));

		NotificationQueueConfiguration notificationQueueConfiguration =
			_getNotificationQueueConfiguration();

		_schedulerEngineHelper.register(
			this,
			new SchedulerEntryImpl(
				className,
				_triggerFactory.createTrigger(
					className, className, null, null,
					notificationQueueConfiguration.checkInterval(),
					TimeUnit.MINUTE)),
			DestinationNames.SCHEDULER_DISPATCH);
	}

	@Deactivate
	protected void deactivate() {
		_schedulerEngineHelper.unregister(this);
	}

	@Override
	protected void doReceive(Message message) throws Exception {
		NotificationType notificationType =
			_notificationTypeServiceTracker.getNotificationType(
				NotificationConstants.TYPE_EMAIL);

		notificationType.resendNotifications(
			NotificationQueueEntryConstants.STATUS_FAILED,
			NotificationConstants.TYPE_EMAIL);

		NotificationQueueConfiguration notificationQueueConfiguration =
			_getNotificationQueueConfiguration();

		long deleteInterval =
			notificationQueueConfiguration.deleteInterval() * Time.MINUTE;

		_notificationQueueEntryLocalService.deleteNotificationQueueEntries(
			new Date(System.currentTimeMillis() - deleteInterval));
	}

	private NotificationQueueConfiguration _getNotificationQueueConfiguration()
		throws ConfigurationException {

		return _configurationProvider.getSystemConfiguration(
			NotificationQueueConfiguration.class);
	}

	@Reference
	private ConfigurationProvider _configurationProvider;

	@Reference
	private NotificationQueueEntryLocalService
		_notificationQueueEntryLocalService;

	@Reference
	private NotificationTypeServiceTracker _notificationTypeServiceTracker;

	@Reference
	private SchedulerEngineHelper _schedulerEngineHelper;

	@Reference
	private TriggerFactory _triggerFactory;

}