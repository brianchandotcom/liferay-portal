/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.util.StringBundler;

import java.util.Date;

/**
 * @author Shuyang Zhou
 */
public class IntervalTrigger extends BaseTrigger {

	public IntervalTrigger(
		String jobName, String groupName, Date startDate, Date endDate,
		long interval) {

		super(jobName, groupName, TriggerType.SIMPLE, startDate, endDate);

		_interval = interval;
	}

	public IntervalTrigger(
		String jobName, String groupName, Date startDate, long interval) {

		this(jobName, groupName, startDate, null, interval);
	}

	public IntervalTrigger(String jobName, String groupName, long interval) {
		this(jobName, groupName, null, null, interval);
	}

	@Override
	public Long getTriggerContent() {
		return _interval;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(5);

		sb.append("{interval=");
		sb.append(_interval);
		sb.append(", ");
		sb.append(super.toString());
		sb.append("}");

		return sb.toString();
	}

	private Long _interval;

}