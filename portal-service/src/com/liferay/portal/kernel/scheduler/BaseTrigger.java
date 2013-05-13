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

import java.io.Serializable;

import java.util.Date;

/**
 * @author Shuyang Zhou
 */
public abstract class BaseTrigger implements Serializable, Trigger {

	public BaseTrigger(
		String jobName, String groupName, TriggerType triggerType,
		Date startDate, Date endDate) {

		_jobName = jobName;
		_groupName = groupName;
		_triggerType = triggerType;
		_startDate = startDate;
		_endDate = endDate;
	}

	@Override
	public Date getEndDate() {
		return _endDate;
	}

	@Override
	public String getGroupName() {
		return _groupName;
	}

	@Override
	public String getJobName() {
		return _jobName;
	}

	@Override
	public Date getStartDate() {
		return _startDate;
	}

	@Override
	public TriggerType getTriggerType() {
		return _triggerType;
	}

	public void setEndDate(Date endDate) {
		_endDate = endDate;
	}

	public void setGroupName(String groupName) {
		_groupName = groupName;
	}

	public void setJobName(String jobName) {
		_jobName = jobName;
	}

	public void setStartDate(Date startDate) {
		_startDate = startDate;
	}

	public void setTriggerType(TriggerType triggerType) {
		_triggerType = triggerType;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(11);

		sb.append("{endDate=");
		sb.append(_endDate);
		sb.append(", groupName=");
		sb.append(_groupName);
		sb.append(", jobName=");
		sb.append(_jobName);
		sb.append(", startDate=");
		sb.append(_startDate);
		sb.append(", triggerType=");
		sb.append(_triggerType);
		sb.append("}");

		return sb.toString();
	}

	private Date _endDate;
	private String _groupName;
	private String _jobName;
	private Date _startDate;
	private TriggerType _triggerType;

}