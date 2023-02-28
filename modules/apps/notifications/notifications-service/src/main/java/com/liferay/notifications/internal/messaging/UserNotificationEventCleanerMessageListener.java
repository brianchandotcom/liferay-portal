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

package com.liferay.notifications.internal.messaging;

import com.liferay.notifications.internal.configuration.UserNotificationConfiguration;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.messaging.BaseMessageListener;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.model.UserNotificationEvent;
import com.liferay.portal.kernel.scheduler.SchedulerEngineHelper;
import com.liferay.portal.kernel.scheduler.SchedulerEntryImpl;
import com.liferay.portal.kernel.scheduler.TimeUnit;
import com.liferay.portal.kernel.scheduler.Trigger;
import com.liferay.portal.kernel.scheduler.TriggerFactory;
import com.liferay.portal.kernel.service.UserNotificationEventLocalService;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author István András Dézsi
 */
@Component(
	configurationPid = "com.liferay.notifications.internal.configuration.UserNotificationConfiguration",
	configurationPolicy = ConfigurationPolicy.REQUIRE, service = {}
)
public class UserNotificationEventCleanerMessageListener
	extends BaseMessageListener {

	@Activate
	protected void activate(Map<String, Object> properties) {
		UserNotificationConfiguration userNotificationConfiguration =
			ConfigurableUtil.createConfigurable(
				UserNotificationConfiguration.class, properties);

		_userNotificationEventDaysLimit =
			userNotificationConfiguration.userNotificationEventDaysLimit();

		String className =
			UserNotificationEventCleanerMessageListener.class.getName();

		int userNotificationEventCheckInterval =
			userNotificationConfiguration.userNotificationEventCheckInterval();

		Trigger trigger = _triggerFactory.createTrigger(
			className, className, null, null,
			userNotificationEventCheckInterval, TimeUnit.DAY);

		_schedulerEngineHelper.register(
			this, new SchedulerEntryImpl(className, trigger),
			DestinationNames.SCHEDULER_DISPATCH);
	}

	@Deactivate
	protected void deactivate() {
		_schedulerEngineHelper.unregister(this);
	}

	@Override
	protected void doReceive(Message message) throws Exception {
		if (_userNotificationEventDaysLimit <= 0) {
			return;
		}

		long timestamp =
			System.currentTimeMillis() -
				TimeUnit.DAY.toMillis(_userNotificationEventDaysLimit);

		ActionableDynamicQuery actionableDynamicQuery =
			_userNotificationEventLocalService.getActionableDynamicQuery();

		actionableDynamicQuery.setAddCriteriaMethod(
			dynamicQuery -> {
				Property archivedProperty = PropertyFactoryUtil.forName(
					"archived");

				dynamicQuery.add(archivedProperty.eq(true));

				Property timestampProperty = PropertyFactoryUtil.forName(
					"timestamp");

				dynamicQuery.add(timestampProperty.lt(timestamp));
			});
		actionableDynamicQuery.setPerformActionMethod(
			(UserNotificationEvent userNotificationEvent) ->
				_userNotificationEventLocalService.deleteUserNotificationEvent(
					userNotificationEvent));

		actionableDynamicQuery.performActions();
	}

	@Reference
	private SchedulerEngineHelper _schedulerEngineHelper;

	@Reference
	private TriggerFactory _triggerFactory;

	private int _userNotificationEventDaysLimit;

	@Reference
	private UserNotificationEventLocalService
		_userNotificationEventLocalService;

}