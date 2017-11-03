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

import com.liferay.portal.kernel.scheduler.Trigger;

import java.util.Date;

import org.quartz.JobKey;

/**
 * @author Tina Tian
 */
public class QuartzTrigger implements Trigger {

	public QuartzTrigger(org.quartz.Trigger trigger) {
		_trigger = trigger;
	}

	@Override
	public Date getEndDate() {
		return _trigger.getEndTime();
	}

	@Override
	public Date getFireDateAfter(Date date) {
		return _trigger.getFireTimeAfter(date);
	}

	@Override
	public String getGroupName() {
		JobKey jobKey = _trigger.getJobKey();

		return jobKey.getGroup();
	}

	@Override
	public String getJobName() {
		JobKey jobKey = _trigger.getJobKey();

		return jobKey.getName();
	}

	@Override
	public Date getStartDate() {
		return _trigger.getStartTime();
	}

	@Override
	public org.quartz.Trigger getWrappedTrigger() {
		return _trigger;
	}

	private static final long serialVersionUID = 1L;

	private final org.quartz.Trigger _trigger;

}