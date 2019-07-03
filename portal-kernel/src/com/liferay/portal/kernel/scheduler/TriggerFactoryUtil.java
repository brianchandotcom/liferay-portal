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

package com.liferay.portal.kernel.scheduler;

import com.liferay.portal.kernel.util.ServiceProxyFactory;

import java.util.Date;
import java.util.TimeZone;

/**
 * @author Shuyang Zhou
 */
public class TriggerFactoryUtil {

	public static Trigger createTrigger(
		String jobName, String groupName, Date startDate, Date endDate,
		int interval, TimeUnit timeUnit) {

		return _triggerFactory.createTrigger(
			jobName, groupName, startDate, endDate, interval, timeUnit);
	}

	public static Trigger createTrigger(
		String jobName, String groupName, Date startDate, Date endDate,
		String cronExpression) {

		return _triggerFactory.createTrigger(
			jobName, groupName, startDate, endDate, cronExpression);
	}

	public static Trigger createTrigger(
		String jobName, String groupName, Date startDate, Date endDate,
		String cronExpression, TimeZone timeZone) {

		return _triggerFactory.createTrigger(
			jobName, groupName, startDate, endDate, cronExpression, timeZone);
	}

	public static Trigger createTrigger(
		String jobName, String groupName, Date startDate, int interval,
		TimeUnit timeUnit) {

		return _triggerFactory.createTrigger(
			jobName, groupName, startDate, null, interval, timeUnit);
	}

	public static Trigger createTrigger(
		String jobName, String groupName, Date startDate,
		String cronExpression) {

		return _triggerFactory.createTrigger(
			jobName, groupName, startDate, null, cronExpression);
	}

	public static Trigger createTrigger(
		String jobName, String groupName, int interval, TimeUnit timeUnit) {

		return _triggerFactory.createTrigger(
			jobName, groupName, null, null, interval, timeUnit);
	}

	public static Trigger createTrigger(
		String jobName, String groupName, String cronExpression) {

		return _triggerFactory.createTrigger(
			jobName, groupName, null, null, cronExpression);
	}

	private static volatile TriggerFactory _triggerFactory =
		ServiceProxyFactory.newServiceTrackedInstance(
			TriggerFactory.class, TriggerFactoryUtil.class, "_triggerFactory",
			false);

}