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
import com.liferay.portal.kernel.util.StringBundler;

import java.util.Date;

/**
 * @author Shuyang Zhou
 */
public class IntervalTrigger extends BaseTrigger {

	public IntervalTrigger(
		String jobName, String groupName, Date startDate, Date endDate,
		int interval, TimeUnit timeUnit) {

		super(jobName, groupName, TriggerType.SIMPLE, startDate, endDate);

		_interval = interval;
		_timeUnit = timeUnit;
	}

	public IntervalTrigger(
		String jobName, String groupName, Date startDate, int interval,
		TimeUnit timeUnit) {

		this(jobName, groupName, startDate, null, interval, timeUnit);
	}

	public IntervalTrigger(
		String jobName, String groupName, int interval, TimeUnit timeUnit) {

		this(jobName, groupName, null, null, interval, timeUnit);
	}

	@Override
	public ObjectValuePair<Integer, TimeUnit> getTriggerContent() {
		return new ObjectValuePair<>(_interval, _timeUnit);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(8);

		sb.append("{interval=");
		sb.append(_interval);
		sb.append(", ");
		sb.append("timeUnit=");
		sb.append(_timeUnit);
		sb.append(", ");
		sb.append(super.toString());
		sb.append("}");

		return sb.toString();
	}

	private final int _interval;
	private final TimeUnit _timeUnit;

}