/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.notification.internal.scheduler;

import com.liferay.commerce.notification.internal.configuration.CommerceNotificationQueueEntryConfiguration;
import com.liferay.commerce.notification.service.CommerceNotificationQueueEntryLocalService;
import com.liferay.petra.function.UnsafeRunnable;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.scheduler.SchedulerJobConfiguration;
import com.liferay.portal.kernel.scheduler.TimeUnit;
import com.liferay.portal.kernel.scheduler.TriggerConfiguration;
import com.liferay.portal.kernel.util.Time;

import java.util.Date;
import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	configurationPid = "com.liferay.commerce.notification.internal.configuration.CommerceNotificationQueueEntryConfiguration",
	service = SchedulerJobConfiguration.class
)
public class CheckCommerceNotificationQueueEntrySchedulerJobConfiguration
	implements SchedulerJobConfiguration {

	@Override
	public UnsafeRunnable<Exception> getJobExecutorUnsafeRunnable() {
		return () -> {

			// Check unsent commerce notification queue entries

			_commerceNotificationQueueEntryLocalService.
				sendCommerceNotificationQueueEntries();

			// Delete old sent commerce notification queue entries

			int deleteInterval =
				_commerceNotificationQueueEntryConfiguration.deleteInterval();

			Date date = new Date(
				System.currentTimeMillis() - (deleteInterval * Time.MINUTE));

			_commerceNotificationQueueEntryLocalService.
				deleteCommerceNotificationQueueEntries(date);
		};
	}

	@Override
	public TriggerConfiguration getTriggerConfiguration() {
		return TriggerConfiguration.createTriggerConfiguration(
			_commerceNotificationQueueEntryConfiguration.checkInterval(),
			TimeUnit.MINUTE);
	}

	@Activate
	protected void activate(Map<String, Object> properties) {
		_commerceNotificationQueueEntryConfiguration =
			ConfigurableUtil.createConfigurable(
				CommerceNotificationQueueEntryConfiguration.class, properties);
	}

	private volatile CommerceNotificationQueueEntryConfiguration
		_commerceNotificationQueueEntryConfiguration;

	@Reference
	private CommerceNotificationQueueEntryLocalService
		_commerceNotificationQueueEntryLocalService;

}