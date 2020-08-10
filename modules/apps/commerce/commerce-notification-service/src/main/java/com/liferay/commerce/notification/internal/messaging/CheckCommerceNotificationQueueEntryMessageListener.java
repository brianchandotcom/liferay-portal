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

package com.liferay.commerce.notification.internal.messaging;

import com.liferay.commerce.notification.internal.configuration.CommerceNotificationQueueEntryConfiguration;
import com.liferay.commerce.notification.internal.constants.CommerceNotificationQueueEntryConstants;
import com.liferay.commerce.notification.service.CommerceNotificationQueueEntryLocalService;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.BaseMessageListener;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;
import com.liferay.portal.kernel.scheduler.SchedulerEngineHelper;
import com.liferay.portal.kernel.scheduler.SchedulerEntry;
import com.liferay.portal.kernel.scheduler.SchedulerEntryImpl;
import com.liferay.portal.kernel.scheduler.TimeUnit;
import com.liferay.portal.kernel.scheduler.Trigger;
import com.liferay.portal.kernel.scheduler.TriggerFactory;
import com.liferay.portal.kernel.settings.SystemSettingsLocator;
import com.liferay.portal.kernel.util.Time;

import java.util.Date;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	configurationPid = "com.liferay.commerce.notification.internal.configuration.CommerceNotificationQueueConfiguration",
	immediate = true,
	service = CheckCommerceNotificationQueueEntryMessageListener.class
)
public class CheckCommerceNotificationQueueEntryMessageListener
	extends BaseMessageListener {

	@Activate
	@Modified
	protected void activate() {
		Class<?> clazz = getClass();

		String className = clazz.getName();

		try {
			_commerceNotificationQueueEntryConfiguration =
				_configurationProvider.getConfiguration(
					CommerceNotificationQueueEntryConfiguration.class,
					new SystemSettingsLocator(
						CommerceNotificationQueueEntryConstants.SERVICE_NAME));
		}
		catch (ConfigurationException ce) {
			_log.error(ce, ce);
		}

		Trigger trigger = _triggerFactory.createTrigger(
			className, className, null, null,
			_commerceNotificationQueueEntryConfiguration.checkInterval(),
			TimeUnit.MINUTE);

		SchedulerEntry schedulerEntry = new SchedulerEntryImpl(
			className, trigger);

		_schedulerEngineHelper.register(
			this, schedulerEntry, DestinationNames.SCHEDULER_DISPATCH);
	}

	@Deactivate
	protected void deactivate() {
		_schedulerEngineHelper.unregister(this);
	}

	@Override
	protected void doReceive(Message message) throws Exception {

		// Check unsent commerce notification queue entries

		_commerceNotificationQueueEntryLocalService.
			sendCommerceNotificationQueueEntries();

		// Delete old sent commerce notification queue entries

		int deleteInterval =
			_commerceNotificationQueueEntryConfiguration.deleteInterval();

		Date now = new Date(
			System.currentTimeMillis() - (deleteInterval * Time.MINUTE));

		_commerceNotificationQueueEntryLocalService.
			deleteCommerceNotificationQueueEntries(now);
	}

	@Reference(target = ModuleServiceLifecycle.PORTAL_INITIALIZED, unbind = "-")
	protected void setModuleServiceLifecycle(
		ModuleServiceLifecycle moduleServiceLifecycle) {
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CheckCommerceNotificationQueueEntryMessageListener.class);

	private CommerceNotificationQueueEntryConfiguration
		_commerceNotificationQueueEntryConfiguration;

	@Reference
	private CommerceNotificationQueueEntryLocalService
		_commerceNotificationQueueEntryLocalService;

	@Reference
	private ConfigurationProvider _configurationProvider;

	@Reference
	private SchedulerEngineHelper _schedulerEngineHelper;

	@Reference
	private TriggerFactory _triggerFactory;

}