/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.monitoring.internal.statistics;

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

	private String _description;
	private final CountStatistics _errorStatistics;
	private final String _name;
	private final AverageStatistics _successStatistics;
	private final CountStatistics _timeoutStatistics;

}