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

package com.liferay.portal.monitoring.internal.statistics;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.monitoring.DataSample;
import com.liferay.portal.kernel.monitoring.RequestStatus;
import com.liferay.portal.monitoring.statistics.Statistics;

/**
 * @author Michael C. Han
 * @author Brian Wing Shun Chan
 */
public class RequestStatistics implements Statistics {

	public RequestStatistics(String name) {
		_name = name;
		_errorStatistics = new CountStatistics(name);
		_successStatistics = new AverageStatistics(name);
		_timeoutStatistics = new CountStatistics(name);
	}

	public long getAverageTime() {
		return _successStatistics.getAverageTime();
	}

	@Override
	public String getDescription() {
		return _description;
	}

	public long getErrorCount() {
		return _errorStatistics.getCount();
	}

	public long getMaxTime() {
		return _successStatistics.getMaxTime();
	}

	public long getMinTime() {
		return _successStatistics.getMinTime();
	}

	@Override
	public String getName() {
		return _name;
	}

	public long getRequestCount() {
		return getErrorCount() + getSuccessCount() + getTimeoutCount();
	}

	public long getSuccessCount() {
		return _successStatistics.getCount();
	}

	public long getTimeoutCount() {
		return _timeoutStatistics.getCount();
	}

	public void incrementError() {
		_errorStatistics.incrementCount();
	}

	public void incrementSuccessDuration(long duration) {
		_successStatistics.addDuration(duration);
	}

	public void incrementTimeout() {
		_timeoutStatistics.incrementCount();
	}

	public void processDataSample(DataSample dataSample) {
		RequestStatus requestStatus = dataSample.getRequestStatus();

		if (requestStatus == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Invalid data sample, no request status: " + dataSample);
			}

			return;
		}

		if (requestStatus.equals(RequestStatus.ERROR)) {
			incrementError();
		}
		else if (requestStatus.equals(RequestStatus.SUCCESS)) {
			incrementSuccessDuration(dataSample.getDuration());
		}
		else if (requestStatus.equals(RequestStatus.TIMEOUT)) {
			incrementTimeout();
		}
	}

	@Override
	public void reset() {
		_errorStatistics.reset();
		_successStatistics.reset();
		_timeoutStatistics.reset();
	}

	@Override
	public void setDescription(String description) {
		_description = description;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		RequestStatistics.class);

	private String _description;
	private final CountStatistics _errorStatistics;
	private final String _name;
	private final AverageStatistics _successStatistics;
	private final CountStatistics _timeoutStatistics;

}