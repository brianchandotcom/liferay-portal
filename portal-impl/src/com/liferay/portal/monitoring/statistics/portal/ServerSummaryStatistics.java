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

package com.liferay.portal.monitoring.statistics.portal;

import com.liferay.portal.kernel.monitoring.MonitoringException;
import com.liferay.portal.kernel.monitoring.statistics.RequestStatistics;
import com.liferay.portal.kernel.monitoring.statistics.SummaryStatistics;

import java.util.Set;

/**
 * @author Michael C. Han
 * @author Brian Wing Shun Chan
 */
public class ServerSummaryStatistics implements SummaryStatistics {

	@Override
	public long getAverageTime() {
		long averageTime = 0;

		Set<CompanyStatistics> companyStatisticsSet =
			_serverStatistics.getCompanyStatisticsSet();

		for (CompanyStatistics companyStatistics : companyStatisticsSet) {
			RequestStatistics requestStatistics =
				companyStatistics.getRequestStatistics();

			averageTime += requestStatistics.getAverageTime();
		}

		return averageTime / companyStatisticsSet.size();
	}

	@Override
	public long getAverageTimeByCompany(long companyId)
		throws MonitoringException {

		return getRequestStatistics(companyId).getAverageTime();
	}

	@Override
	public long getAverageTimeByCompany(String webId)
		throws MonitoringException {

		return getRequestStatistics(webId).getAverageTime();
	}

	@Override
	public long getErrorCount() {
		int errorCount = 0;

		for (CompanyStatistics companyStatistics :
				_serverStatistics.getCompanyStatisticsSet()) {

			errorCount +=
				companyStatistics.getRequestStatistics().getErrorCount();
		}

		return errorCount;
	}

	@Override
	public long getErrorCountByCompany(long companyId)
		throws MonitoringException {

		return getRequestStatistics(companyId).getErrorCount();
	}

	@Override
	public long getErrorCountByCompany(String webId)
		throws MonitoringException {

		return getRequestStatistics(webId).getErrorCount();
	}

	@Override
	public long getMaxTime() {
		long maxTime = 0;

		for (CompanyStatistics companyStatistics :
				_serverStatistics.getCompanyStatisticsSet()) {

			if (companyStatistics.getMaxTime() > maxTime) {
				maxTime = companyStatistics.getMaxTime();
			}
		}

		return maxTime;
	}

	@Override
	public long getMaxTimeByCompany(long companyId) throws MonitoringException {
		return getRequestStatistics(companyId).getMaxTime();
	}

	@Override
	public long getMaxTimeByCompany(String webId) throws MonitoringException {
		return getRequestStatistics(webId).getMaxTime();
	}

	@Override
	public long getMinTime() {
		long minTime = 0;

		for (CompanyStatistics companyStatistics :
				_serverStatistics.getCompanyStatisticsSet()) {

			if (companyStatistics.getMinTime() < minTime) {
				minTime = companyStatistics.getMinTime();
			}
		}

		return minTime;
	}

	@Override
	public long getMinTimeByCompany(long companyId) throws MonitoringException {
		return getRequestStatistics(companyId).getMinTime();
	}

	@Override
	public long getMinTimeByCompany(String webId) throws MonitoringException {
		return getRequestStatistics(webId).getMinTime();
	}

	@Override
	public long getRequestCount() {
		int requestCount = 0;

		for (CompanyStatistics companyStatistics :
				_serverStatistics.getCompanyStatisticsSet()) {

			requestCount +=
				companyStatistics.getRequestStatistics().getRequestCount();
		}

		return requestCount;
	}

	@Override
	public long getRequestCountByCompany(long companyId)
		throws MonitoringException {

		return getRequestStatistics(companyId).getRequestCount();
	}

	@Override
	public long getRequestCountByCompany(String webId)
		throws MonitoringException {

		return getRequestStatistics(webId).getRequestCount();
	}

	@Override
	public long getSuccessCount() {
		int successCount = 0;

		for (CompanyStatistics companyStatistics :
				_serverStatistics.getCompanyStatisticsSet()) {

			successCount +=
				companyStatistics.getRequestStatistics().getSuccessCount();
		}

		return successCount;
	}

	@Override
	public long getSuccessCountByCompany(long companyId)
		throws MonitoringException {

		return getRequestStatistics(companyId).getSuccessCount();
	}

	@Override
	public long getSuccessCountByCompany(String webId)
		throws MonitoringException {

		return getRequestStatistics(webId).getSuccessCount();
	}

	@Override
	public long getTimeoutCount() {
		int timeoutCount = 0;

		for (CompanyStatistics companyStatistics :
				_serverStatistics.getCompanyStatisticsSet()) {

			timeoutCount +=
				companyStatistics.getRequestStatistics().getTimeoutCount();
		}

		return timeoutCount;
	}

	@Override
	public long getTimeoutCountByCompany(long companyId)
		throws MonitoringException {

		return getRequestStatistics(companyId).getTimeoutCount();
	}

	@Override
	public long getTimeoutCountByCompany(String webId)
		throws MonitoringException {

		return getRequestStatistics(webId).getTimeoutCount();
	}

	public void setServerStatistics(ServerStatistics serverStatistics) {
		_serverStatistics = serverStatistics;
	}

	protected RequestStatistics getRequestStatistics(long companyId)
		throws MonitoringException {

		try {
			CompanyStatistics companyStatistics =
				_serverStatistics.getCompanyStatistics(companyId);

			return companyStatistics.getRequestStatistics();
		}
		catch (Exception e) {
			throw new MonitoringException(
				"Unable to get company with company id " + companyId, e);
		}
	}

	protected RequestStatistics getRequestStatistics(String webId)
		throws MonitoringException {

		try {
			CompanyStatistics companyStatistics =
				_serverStatistics.getCompanyStatistics(webId);

			return companyStatistics.getRequestStatistics();
		}
		catch (Exception e) {
			throw new MonitoringException(
				"Unable to get company with web id " + webId, e);
		}
	}

	private ServerStatistics _serverStatistics;

}