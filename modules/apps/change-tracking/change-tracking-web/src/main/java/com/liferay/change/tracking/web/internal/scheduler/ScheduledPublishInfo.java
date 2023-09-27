/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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