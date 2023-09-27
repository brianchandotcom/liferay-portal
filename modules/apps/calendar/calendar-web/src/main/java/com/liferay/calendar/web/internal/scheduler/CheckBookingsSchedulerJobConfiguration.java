/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.calendar.web.internal.scheduler;

import com.liferay.calendar.configuration.CalendarServiceConfigurationValues;
import com.liferay.calendar.service.CalendarBookingLocalService;
import com.liferay.petra.function.UnsafeRunnable;
import com.liferay.portal.kernel.scheduler.SchedulerJobConfiguration;
import com.liferay.portal.kernel.scheduler.TimeUnit;
import com.liferay.portal.kernel.scheduler.TriggerConfiguration;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Fabio Pezzutto
 * @author Eduardo Lundgren
 */
@Component(service = SchedulerJobConfiguration.class)
public class CheckBookingsSchedulerJobConfiguration
	implements SchedulerJobConfiguration {

	@Override
	public UnsafeRunnable<Exception> getJobExecutorUnsafeRunnable() {
		return _calendarBookingLocalService::checkCalendarBookings;
	}

	@Override
	public TriggerConfiguration getTriggerConfiguration() {
		return TriggerConfiguration.createTriggerConfiguration(
			CalendarServiceConfigurationValues.
				CALENDAR_NOTIFICATION_CHECK_INTERVAL,
			TimeUnit.MINUTE);
	}

	@Reference
	private CalendarBookingLocalService _calendarBookingLocalService;

}