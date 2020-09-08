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

package com.liferay.change.tracking.web.internal.scheduler;

import com.liferay.change.tracking.model.CTCollection;

import java.util.Date;

/**
 * @author Preston Crary
 */
public class ScheduledPublishInfo {

	public CTCollection getCTCollection() {
		return _ctCollection;
	}

	public String getJobName() {
		return _jobName;
	}

	public Date getStartDate() {
		return _startDate;
	}

	public long getUserId() {
		return _userId;
	}

	protected ScheduledPublishInfo(
		CTCollection ctCollection, long userId, String jobName,
		Date startDate) {

		_ctCollection = ctCollection;
		_userId = userId;
		_jobName = jobName;
		_startDate = startDate;
	}

	private final CTCollection _ctCollection;
	private final String _jobName;
	private final Date _startDate;
	private final long _userId;

}