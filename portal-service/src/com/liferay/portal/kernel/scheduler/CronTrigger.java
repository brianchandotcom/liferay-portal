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
public class CronTrigger extends BaseTrigger {

	public CronTrigger(
		String jobName, String groupName, Date startDate, Date endDate,
		String cronText) {

		super(jobName, groupName, TriggerType.CRON, startDate, endDate);

		_cronText = cronText;
	}

	public CronTrigger(
		String jobName, String groupName, Date startDate, String cronText) {

		this(jobName, groupName, startDate, null, cronText);
	}

	public CronTrigger(String jobName, String groupName, String cronText) {
		this(jobName, groupName, null, null, cronText);
	}

	@Override
	public String getTriggerContent() {
		return _cronText;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(5);

		sb.append("{cronText=");
		sb.append(_cronText);
		sb.append(", ");
		sb.append(super.toString());
		sb.append("}");

		return sb.toString();
	}

	private String _cronText;

}