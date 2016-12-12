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

package com.liferay.portal.scheduler.quartz.internal;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.scheduler.TimeUnit;
import com.liferay.portal.kernel.scheduler.Trigger;
import com.liferay.portal.kernel.scheduler.TriggerFactory;
import com.liferay.portal.scheduler.quartz.QuartzTrigger;

import java.util.Date;

import org.osgi.service.component.annotations.Component;

import org.quartz.CalendarIntervalScheduleBuilder;
import org.quartz.CronScheduleBuilder;
import org.quartz.DateBuilder.IntervalUnit;
import org.quartz.ScheduleBuilder;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.SimpleTrigger;
import org.quartz.TriggerBuilder;

/**
 * @author Tina Tian
 */
@Component(
	enabled = false, immediate = true,
	service = {QuartzTriggerFactory.class, TriggerFactory.class}
)
public class QuartzTriggerFactory implements TriggerFactory {

	@Override
	public Trigger createTrigger(
		String jobName, String groupName, Date startDate, Date endDate,
		int interval, TimeUnit timeUnit) {

		if (interval < 0) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Not scheduling " + jobName + " because interval is less " +
						"than 0");
			}

			return null;
		}

		if (interval <= 0) {
			return createTrigger(
				jobName, groupName, startDate, endDate, (ScheduleBuilder)null);
		}

		if (timeUnit == TimeUnit.MILLISECOND) {
			SimpleScheduleBuilder simpleScheduleBuilder =
				SimpleScheduleBuilder.simpleSchedule();

			simpleScheduleBuilder.withIntervalInMilliseconds(interval);
			simpleScheduleBuilder.withRepeatCount(
				SimpleTrigger.REPEAT_INDEFINITELY);

			return createTrigger(
				jobName, groupName, startDate, endDate, simpleScheduleBuilder);
		}

		CalendarIntervalScheduleBuilder calendarIntervalScheduleBuilder =
			CalendarIntervalScheduleBuilder.calendarIntervalSchedule();

		calendarIntervalScheduleBuilder.withInterval(
			interval, IntervalUnit.valueOf(timeUnit.name()));

		return createTrigger(
			jobName, groupName, startDate, endDate,
			calendarIntervalScheduleBuilder);
	}

	@Override
	public Trigger createTrigger(
		String jobName, String groupName, Date startDate, Date endDate,
		String cronExpression) {

		return createTrigger(
			jobName, groupName, startDate, endDate,
			CronScheduleBuilder.cronSchedule(cronExpression));
	}

	@Override
	public Trigger createTrigger(
		Trigger trigger, Date startDate, Date endDate) {

		org.quartz.Trigger wrappedTrigger =
			(org.quartz.Trigger)trigger.getWrappedTrigger();

		return createTrigger(
			trigger.getJobName(), trigger.getGroupName(), startDate, endDate,
			wrappedTrigger.getScheduleBuilder());
	}

	protected Trigger createTrigger(
		String jobName, String groupName, Date startDate, Date endDate,
		ScheduleBuilder<?> scheduleBuilder) {

		if (startDate == null) {
			startDate = new Date(System.currentTimeMillis());
		}

		TriggerBuilder<org.quartz.Trigger> triggerBuilder =
			TriggerBuilder.newTrigger();

		triggerBuilder.endAt(endDate);
		triggerBuilder.forJob(jobName, groupName);
		triggerBuilder.startAt(startDate);
		triggerBuilder.withIdentity(jobName, groupName);

		if (scheduleBuilder != null) {
			triggerBuilder.withSchedule(scheduleBuilder);
		}

		return new QuartzTrigger(triggerBuilder.build());
	}

	private static final Log _log = LogFactoryUtil.getLog(
		QuartzTriggerFactory.class);

}