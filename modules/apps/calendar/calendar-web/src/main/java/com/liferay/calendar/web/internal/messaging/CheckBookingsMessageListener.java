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

package com.liferay.calendar.web.internal.messaging;

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
public class CheckBookingsMessageListener implements SchedulerJobConfiguration {

	@Override
	public UnsafeRunnable<Exception> getJobExecutor() {
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