/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
	public UnsafeRunnable<Exception> getJobExecutorUnsafeRunnable() {
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