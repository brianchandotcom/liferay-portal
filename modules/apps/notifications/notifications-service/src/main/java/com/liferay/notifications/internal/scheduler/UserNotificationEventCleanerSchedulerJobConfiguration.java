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

package com.liferay.notifications.internal.scheduler;

import com.liferay.notifications.internal.configuration.UserNotificationConfiguration;
import com.liferay.petra.function.UnsafeRunnable;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.model.UserNotificationEvent;
import com.liferay.portal.kernel.scheduler.SchedulerJobConfiguration;
import com.liferay.portal.kernel.scheduler.TimeUnit;
import com.liferay.portal.kernel.scheduler.TriggerConfiguration;
import com.liferay.portal.kernel.service.UserNotificationEventLocalService;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Reference;

/**
 * @author István András Dézsi
 */
@Component(
	configurationPid = "com.liferay.notifications.internal.configuration.UserNotificationConfiguration",
	configurationPolicy = ConfigurationPolicy.REQUIRE,
	service = SchedulerJobConfiguration.class
)
public class UserNotificationEventCleanerSchedulerJobConfiguration
	implements SchedulerJobConfiguration {

	@Override
	public UnsafeRunnable<Exception> getJobExecutor() {
		return () -> {
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
					_userNotificationEventLocalService.
						deleteUserNotificationEvent(userNotificationEvent));

			actionableDynamicQuery.performActions();
		};
	}

	@Override
	public TriggerConfiguration getTriggerConfiguration() {
		return TriggerConfiguration.createTriggerConfiguration(
			_userNotificationConfiguration.userNotificationEventCheckInterval(),
			TimeUnit.DAY);
	}

	@Activate
	protected void activate(Map<String, Object> properties) {
		_userNotificationConfiguration = ConfigurableUtil.createConfigurable(
			UserNotificationConfiguration.class, properties);

		_userNotificationEventDaysLimit =
			_userNotificationConfiguration.userNotificationEventDaysLimit();
	}

	private UserNotificationConfiguration _userNotificationConfiguration;
	private int _userNotificationEventDaysLimit;

	@Reference
	private UserNotificationEventLocalService
		_userNotificationEventLocalService;

}