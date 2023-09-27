/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.scheduler.quartz.internal;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.scheduler.TimeUnit;
import com.liferay.portal.kernel.scheduler.Trigger;
import com.liferay.portal.kernel.scheduler.TriggerFactory;

import java.util.Date;
import java.util.TimeZone;

import org.osgi.service.component.annotations.Component;

import org.quartz.CalendarIntervalScheduleBuilder;
import org.quartz.CronScheduleBuilder;
import org.quartz.DateBuilder;
import org.quartz.ScheduleBuilder;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.SimpleTrigger;
import org.quartz.TriggerBuilder;

/**
 * @author Tina Tian
 */
@Component(enabled = false, service = TriggerFactory.class)
public class QuartzTriggerFactory implements TriggerFactory {

	@Override
	public Trigger createTrigger(
		String jobName, String groupName, Date startDate, Date endDate,
		int interval, TimeUnit timeUnit) {

		if (interval < 0) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					StringBundler.concat(
						"Not scheduling ", jobName,
						" because interval is less than 0"));
			}

			return null;
		}

		if (interval <= 0) {
			return createTrigger(
				jobName, groupName, startDate, endDate, (ScheduleBuilder)null);
		}

		if (startDate == null) {
			startDate = new Date(
				System.currentTimeMillis() + timeUnit.toMillis(interval));
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
			interval, DateBuilder.IntervalUnit.valueOf(timeUnit.name()));

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
		String jobName, String groupName, Date startDate, Date endDate,
		String cronExpression, TimeZone timeZone) {

		CronScheduleBuilder cronScheduleBuilder =
			CronScheduleBuilder.cronSchedule(cronExpression);

		return createTrigger(
			jobName, groupName, startDate, endDate,
			cronScheduleBuilder.inTimeZone(timeZone));
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