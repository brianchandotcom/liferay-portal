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

import com.liferay.portal.kernel.util.ObjectValuePair;

import java.util.Date;

/**
 * @author Shuyang Zhou
 */
public class TriggerFactoryUtil {

	public static Trigger buildTrigger(
		TriggerType triggerType, String jobName, String groupName,
		Date startDate, Date endDate, Object triggerContent) {

		if (triggerType == TriggerType.CRON) {
			return new CronTrigger(
				jobName, groupName, startDate, endDate,
				String.valueOf(triggerContent));
		}

		if (triggerType == TriggerType.SIMPLE) {
			ObjectValuePair<Integer, TimeUnit> objectValuePair =
				(ObjectValuePair<Integer, TimeUnit>)triggerContent;

			return new IntervalTrigger(
				jobName, groupName, startDate, endDate,
				objectValuePair.getKey(), objectValuePair.getValue());
		}

		throw new IllegalArgumentException(
			"Unknown trigger type " + triggerType);
	}

}