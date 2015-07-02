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

package com.liferay.calendar.web.messaging;

import com.liferay.calendar.constants.CalendarPortletKeys;
import com.liferay.calendar.service.CalendarBookingLocalService;
import com.liferay.calendar.service.CalendarBookingLocalServiceUtil;
import com.liferay.calendar.service.configuration.CalendarServiceConfigurationValues;
import com.liferay.calendar.service.configuration.configurator.CalendarServiceConfigurator;
import com.liferay.portal.kernel.messaging.BaseSchedulerEntryMessageListener;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.scheduler.SchedulerEntry;
import com.liferay.portal.kernel.scheduler.TimeUnit;
import com.liferay.portal.kernel.scheduler.TriggerType;
import com.liferay.portal.model.Portlet;

import javax.servlet.ServletContext;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Fabio Pezzutto
 * @author Eduardo Lundgren
 */
@Component(
	property = {"javax.portlet.name=" + CalendarPortletKeys.CALENDAR},
	service = SchedulerEntry.class
)
public class CheckBookingsMessageListener
	extends BaseSchedulerEntryMessageListener {

	@Activate
	protected void activate() {
		schedulerEntry.setTimeUnit(TimeUnit.MINUTE);
		schedulerEntry.setTriggerType(TriggerType.SIMPLE);
		schedulerEntry.setTriggerValue(
			CalendarServiceConfigurationValues.
				CALENDAR_NOTIFICATION_CHECK_INTERVAL);
	}

	@Override
	protected void doReceive(Message message) throws Exception {
		CalendarBookingLocalServiceUtil.checkCalendarBookings();
	}

	@Reference
	protected void setCalendarBookingLocalService(
		CalendarBookingLocalService calendarBookingLocalService) {
	}

	@Reference
	protected void setCalendarServiceConfigurator(
		CalendarServiceConfigurator calendarServiceConfigurator) {
	}

	@Reference(
		target = "(javax.portlet.name=" + CalendarPortletKeys.CALENDAR + ")"
	)
	protected void setPortlet(Portlet portlet) {
	}

	@Reference(target = "(original.bean=*)", unbind = "-")
	protected void setServletContext(ServletContext servletContext) {
	}

}