/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.scheduler;

import com.liferay.portal.kernel.module.service.Snapshot;

import java.util.Date;
import java.util.TimeZone;

/**
 * @author Shuyang Zhou
 */
public class TriggerFactoryUtil {

	public static Trigger createTrigger(
		String jobName, String groupName, Date startDate, Date endDate,
		String cronExpression, TimeZone timeZone) {

		TriggerFactory triggerFactory = _triggerFactorySnapshot.get();

		return triggerFactory.createTrigger(
			jobName, groupName, startDate, endDate, cronExpression, timeZone);
	}

	public static Trigger createTrigger(
		String jobName, String groupName, int interval, TimeUnit timeUnit) {

		TriggerFactory triggerFactory = _triggerFactorySnapshot.get();

		return triggerFactory.createTrigger(
			jobName, groupName, null, null, interval, timeUnit);
	}

	public static Trigger createTrigger(
		String jobName, String groupName, String cronExpression) {

		TriggerFactory triggerFactory = _triggerFactorySnapshot.get();

		return triggerFactory.createTrigger(
			jobName, groupName, null, null, cronExpression);
	}

	private static final Snapshot<TriggerFactory> _triggerFactorySnapshot =
		new Snapshot<>(TriggerFactoryUtil.class, TriggerFactory.class);

}