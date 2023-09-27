/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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