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
 * @author Tina Tian
 */
public class TriggerConfiguration {

	public static TriggerConfiguration createTriggerConfiguration(
		int interval, TimeUnit timeUnit) {

		return new TriggerConfiguration(interval, timeUnit);
	}

	public static TriggerConfiguration createTriggerConfiguration(
		String cronExpression) {

		return new TriggerConfiguration(cronExpression);
	}

	public String getCronExpression() {
		return _cronExpression;
	}

	public int getInterval() {
		return _interval;
	}

	public Date getStartDate() {
		return _startDate;
	}

	public TimeUnit getTimeUnit() {
		return _timeUnit;
	}

	public void setStartDate(Date startDate) {
		_startDate = startDate;
	}

	private TriggerConfiguration(int interval, TimeUnit timeUnit) {
		if (interval <= 0) {
			throw new IllegalArgumentException(
				"Interval is either equal or less than 0");
		}

		if (timeUnit == null) {
			throw new IllegalArgumentException("Time unit is null");
		}

		_interval = interval;
		_timeUnit = timeUnit;
	}

	private TriggerConfiguration(String cronExpression) {
		if (cronExpression == null) {
			throw new IllegalArgumentException("Cron expression is null");
		}

		_cronExpression = cronExpression;
	}

	private String _cronExpression;
	private int _interval;
	private Date _startDate;
	private TimeUnit _timeUnit;

}