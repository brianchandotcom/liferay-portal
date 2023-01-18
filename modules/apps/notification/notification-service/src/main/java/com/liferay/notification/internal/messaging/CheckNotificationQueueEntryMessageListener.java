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
import com.liferay.notification.internal.configuration.NotificationQueueConfiguration;
import com.liferay.notification.service.NotificationQueueEntryLocalService;
import com.liferay.notification.type.NotificationType;
import com.liferay.notification.type.NotificationTypeServiceTracker;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.BaseMessageListener;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.scheduler.SchedulerEngineHelper;
import com.liferay.portal.kernel.scheduler.SchedulerEntryImpl;
import com.liferay.portal.kernel.scheduler.SchedulerException;
import com.liferay.portal.kernel.scheduler.StorageType;
import com.liferay.portal.kernel.scheduler.TimeUnit;
import com.liferay.portal.kernel.scheduler.TriggerFactory;
import com.liferay.portal.kernel.settings.CompanyServiceSettingsLocator;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.Time;

import java.util.Date;

/**
 * @author Gustavo Lima
 */
public class CheckNotificationQueueEntryMessageListener
	extends BaseMessageListener {

	public CheckNotificationQueueEntryMessageListener(
		long companyId, ConfigurationProvider configurationProvider,
		NotificationQueueEntryLocalService notificationQueueEntryLocalService,
		NotificationTypeServiceTracker notificationTypeServiceTracker,
		SchedulerEngineHelper schedulerEngineHelper,
		TriggerFactory triggerFactory) {

		_configurationProvider = configurationProvider;
		_notificationQueueEntryLocalService =
			notificationQueueEntryLocalService;
		_notificationTypeServiceTracker = notificationTypeServiceTracker;
		_schedulerEngineHelper = schedulerEngineHelper;
		_triggerFactory = triggerFactory;

		String className = _getClassName(companyId);

		try {
			_configurationProvider.saveCompanyConfiguration(
				NotificationQueueConfiguration.class, companyId,
				HashMapDictionaryBuilder.<String, Object>put(
					"checkInterval", NotificationConstants.CHECK_INTERVAL
				).put(
					"deleteInterval", NotificationConstants.DELETE_INTERVAL
				).build());
		}
		catch (ConfigurationException configurationException) {
			throw new RuntimeException(configurationException);
		}

		_schedulerEngineHelper.register(
			this,
			new SchedulerEntryImpl(
				className,
				_triggerFactory.createTrigger(
					className, className, null, null,
					NotificationConstants.CHECK_INTERVAL, TimeUnit.MINUTE)),
			DestinationNames.SCHEDULER_DISPATCH);
	}

	@Override
	protected void doReceive(Message message) throws Exception {
		synchronized (this) {
			NotificationType notificationType =
				_notificationTypeServiceTracker.getNotificationType(
					NotificationConstants.TYPE_EMAIL);

			long companyId = message.getLong("companyId");

			notificationType.sendUnsentNotifications(companyId);

			NotificationQueueConfiguration notificationQueueConfiguration =
				_getNotificationQueueConfiguration(companyId);

			long deleteInterval =
				notificationQueueConfiguration.deleteInterval() * Time.MINUTE;

			_notificationQueueEntryLocalService.deleteNotificationQueueEntries(
				companyId,
				new Date(System.currentTimeMillis() - deleteInterval));

			String className = _getClassName(companyId);

			try {
				_schedulerEngineHelper.update(
					_triggerFactory.createTrigger(
						className, className, null, null,
						notificationQueueConfiguration.checkInterval(),
						TimeUnit.MINUTE),
					StorageType.MEMORY_CLUSTERED);
			}
			catch (SchedulerException schedulerException) {
				if (_log.isWarnEnabled()) {
					_log.error(
						"Unable to update trigger for memory clustered job",
						schedulerException);
				}
			}
		}
	}

	private String _getClassName(long companyId) {
		Class<?> clazz = getClass();

		return StringBundler.concat(
			clazz.getName(), StringPool.POUND, companyId);
	}

	private NotificationQueueConfiguration _getNotificationQueueConfiguration(
		long companyId) {

		try {
			return _configurationProvider.getConfiguration(
				NotificationQueueConfiguration.class,
				new CompanyServiceSettingsLocator(
					companyId, NotificationConstants.RESOURCE_NAME,
					NotificationQueueConfiguration.class.getName()));
		}
		catch (ConfigurationException configurationException) {
			if (_log.isDebugEnabled()) {
				_log.debug(configurationException);
			}

			throw new SystemException(configurationException);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CheckNotificationQueueEntryMessageListener.class);

	private final ConfigurationProvider _configurationProvider;
	private volatile NotificationQueueEntryLocalService
		_notificationQueueEntryLocalService;
	private volatile NotificationTypeServiceTracker
		_notificationTypeServiceTracker;
	private volatile SchedulerEngineHelper _schedulerEngineHelper;
	private volatile TriggerFactory _triggerFactory;

}