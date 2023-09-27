/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
		String cronExpression, TimeZone timeZone) {

		return _triggerFactory.createTrigger(
			jobName, groupName, startDate, endDate, cronExpression, timeZone);
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