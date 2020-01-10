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

package com.liferay.dispatch.internal.advisor;

import com.liferay.portal.kernel.scheduler.TimeUnit;
import com.liferay.portal.kernel.scheduler.Trigger;
import com.liferay.portal.kernel.scheduler.TriggerFactory;
import com.liferay.portal.kernel.util.Validator;

import java.io.Serializable;

import java.util.Date;
import java.util.TimeZone;

/**
 * @author Igor Beslic
 */
public class MockTriggerFactoryImpl implements TriggerFactory {

	@Override
	public Trigger createTrigger(
		String jobName, String groupName, Date startDate, Date endDate,
		int interval, TimeUnit timeUnit) {

		throw new UnsupportedOperationException();
	}

	@Override
	public Trigger createTrigger(
		String jobName, String groupName, Date startDate, Date endDate,
		String cronExpression) {

		MockTrigger mockTrigger = new MockTrigger();

		mockTrigger._jobName = jobName;
		mockTrigger._groupName = groupName;
		mockTrigger._startDate = startDate;
		mockTrigger._endDate = endDate;

		if (Validator.isNotNull(cronExpression)) {
			mockTrigger._hasFireDateAfter = true;
			mockTrigger._hasFireDateBefore = true;
		}

		return mockTrigger;
	}

	@Override
	public Trigger createTrigger(
		String jobName, String groupName, Date startDate, Date endDate,
		String cronExpression, TimeZone timeZone) {

		throw new UnsupportedOperationException();
	}

	@Override
	public Trigger createTrigger(
		Trigger trigger, Date startDate, Date endDate) {

		throw new UnsupportedOperationException();
	}

	protected class MockTrigger implements Trigger {

		@Override
		public Date getEndDate() {
			return _endDate;
		}

		@Override
		public Date getFireDateAfter(Date date) {
			if (_hasFireDateAfter) {
				return new Date(date.getTime() + 1000);
			}

			return null;
		}

		public Date getFireDateBefore(Date date) {
			if (_hasFireDateBefore) {
				return new Date(date.getTime() - 1000);
			}

			return null;
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
		public Serializable getWrappedTrigger() {
			throw new UnsupportedOperationException();
		}

		private Date _endDate;
		private String _groupName;
		private boolean _hasFireDateAfter;
		private boolean _hasFireDateBefore;
		private String _jobName;
		private Date _startDate;

	}

}