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
			Number number = (Number)triggerContent;

			return new IntervalTrigger(
				jobName, groupName, startDate, endDate, number.longValue());
		}

		throw new IllegalArgumentException(
			"Unknown trigger type " + triggerType);
	}

}