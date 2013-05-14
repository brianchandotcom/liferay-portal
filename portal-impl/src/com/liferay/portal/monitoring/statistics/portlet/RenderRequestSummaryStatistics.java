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

package com.liferay.portal.monitoring.statistics.portlet;

import com.liferay.portal.kernel.monitoring.MonitoringException;
import com.liferay.portal.kernel.monitoring.statistics.RequestStatistics;

import java.util.Set;

/**
 * @author Michael C. Han
 * @author Brian Wing Shun Chan
 */
public class RenderRequestSummaryStatistics
	implements PortletSummaryStatistics {

	@Override
	public long getAverageTime() {
		long averageTime = 0;

		long count = 0;

		for (CompanyStatistics companyStatistics :
				_serverStatistics.getCompanyStatisticsSet()) {

			for (RequestStatistics requestStatistics :
					companyStatistics.getRenderRequestStatisticsSet()) {

				averageTime += requestStatistics.getAverageTime();

				count++;
			}
		}

		return averageTime / count;
	}

	@Override
	public long getAverageTimeByCompany(long companyId)
		throws MonitoringException {

		CompanyStatistics companyStatistics =
			_serverStatistics.getCompanyStatistics(companyId);

		return getAverageTimeByCompany(companyStatistics);
	}

	@Override
	public long getAverageTimeByCompany(String webId)
		throws MonitoringException {

		CompanyStatistics companyStatistics =
			_serverStatistics.getCompanyStatistics(webId);

		return getAverageTimeByCompany(companyStatistics);
	}

	@Override
	public long getAverageTimeByPortlet(String portletId)
		throws MonitoringException {

		long averageTime = 0;

		Set<CompanyStatistics> companyStatisticsSet =
			_serverStatistics.getCompanyStatisticsSet();

		for (CompanyStatistics companyStatistics : companyStatisticsSet) {
			RequestStatistics requestStatistics =
				companyStatistics.getRenderRequestStatistics(portletId);

			averageTime += requestStatistics.getAverageTime();
		}

		return averageTime / companyStatisticsSet.size();
	}

	@Override
	public long getAverageTimeByPortlet(String portletId, long companyId)
		throws MonitoringException {

		CompanyStatistics companyStatistics =
			_serverStatistics.getCompanyStatistics(companyId);

		RequestStatistics requestStatistics =
			companyStatistics.getRenderRequestStatistics(portletId);

		return requestStatistics.getAverageTime();
	}

	@Override
	public long getAverageTimeByPortlet(String portletId, String webId)
		throws MonitoringException {

		CompanyStatistics companyStatistics =
			_serverStatistics.getCompanyStatistics(webId);

		RequestStatistics requestStatistics =
			companyStatistics.getRenderRequestStatistics(portletId);

		return requestStatistics.getAverageTime();
	}

	@Override
	public long getErrorCount() {
		long errorCount = 0;

		for (CompanyStatistics companyStatistics :
				_serverStatistics.getCompanyStatisticsSet()) {

			errorCount += getErrorCountByCompany(companyStatistics);
		}

		return errorCount;
	}

	@Override
	public long getErrorCountByCompany(long companyId)
		throws MonitoringException {

		CompanyStatistics companyStatistics =
			_serverStatistics.getCompanyStatistics(companyId);

		return getErrorCountByCompany(companyStatistics);
	}

	@Override
	public long getErrorCountByCompany(String webId)
		throws MonitoringException {

		CompanyStatistics companyStatistics =
			_serverStatistics.getCompanyStatistics(webId);

		return getErrorCountByCompany(companyStatistics);
	}

	@Override
	public long getErrorCountByPortlet(String portletId)
		throws MonitoringException {

		long errorCount = 0;

		for (CompanyStatistics companyStatistics :
				_serverStatistics.getCompanyStatisticsSet()) {

			errorCount += getErrorCountByPortlet(portletId, companyStatistics);
		}

		return errorCount;
	}

	@Override
	public long getErrorCountByPortlet(String portletId, long companyId)
		throws MonitoringException {

		CompanyStatistics companyStatistics =
			_serverStatistics.getCompanyStatistics(companyId);

		return getErrorCountByPortlet(portletId, companyStatistics);
	}

	@Override
	public long getErrorCountByPortlet(String portletId, String webId)
		throws MonitoringException {

		CompanyStatistics companyStatistics =
			_serverStatistics.getCompanyStatistics(webId);

		return getErrorCountByPortlet(portletId, companyStatistics);
	}

	@Override
	public long getMaxTime() {
		long maxTime = 0;

		for (CompanyStatistics companyStatistics :
				_serverStatistics.getCompanyStatisticsSet()) {

			for (RequestStatistics requestStatistics :
					companyStatistics.getRenderRequestStatisticsSet()) {

				if (requestStatistics.getMaxTime() > maxTime) {
					maxTime = requestStatistics.getMaxTime();
				}
			}
		}

		return maxTime;
	}

	@Override
	public long getMaxTimeByCompany(long companyId) throws MonitoringException {
		CompanyStatistics companyStatistics =
			_serverStatistics.getCompanyStatistics(companyId);

		return companyStatistics.getMaxTime();
	}

	@Override
	public long getMaxTimeByCompany(String webId) throws MonitoringException {
		CompanyStatistics companyStatistics =
			_serverStatistics.getCompanyStatistics(webId);

		return companyStatistics.getMaxTime();
	}

	@Override
	public long getMaxTimeByPortlet(String portletId)
		throws MonitoringException {

		long maxTime = 0;

		for (CompanyStatistics companyStatistics :
				_serverStatistics.getCompanyStatisticsSet()) {

			long curMaxTime = getMaxTimeByPortlet(portletId, companyStatistics);

			if (curMaxTime > maxTime) {
				maxTime = curMaxTime;
			}
		}

		return maxTime;
	}

	@Override
	public long getMaxTimeByPortlet(String portletId, long companyId)
		throws MonitoringException {

		CompanyStatistics companyStatistics =
			_serverStatistics.getCompanyStatistics(companyId);

		return getMaxTimeByPortlet(portletId, companyStatistics);
	}

	@Override
	public long getMaxTimeByPortlet(String portletId, String webId)
		throws MonitoringException {

		CompanyStatistics companyStatistics =
			_serverStatistics.getCompanyStatistics(webId);

		return getMaxTimeByPortlet(portletId, companyStatistics);
	}

	@Override
	public long getMinTime() {
		long minTime = 0;

		for (CompanyStatistics companyStatistics :
				_serverStatistics.getCompanyStatisticsSet()) {

			for (RequestStatistics requestStatistics :
					companyStatistics.getRenderRequestStatisticsSet()) {

				if (requestStatistics.getMinTime() < minTime) {
					minTime = requestStatistics.getMinTime();
				}
			}
		}

		return minTime;
	}

	@Override
	public long getMinTimeByCompany(long companyId) throws MonitoringException {
		CompanyStatistics companyStatistics =
			_serverStatistics.getCompanyStatistics(companyId);

		return companyStatistics.getMinTime();
	}

	@Override
	public long getMinTimeByCompany(String webId) throws MonitoringException {
		CompanyStatistics companyStatistics =
			_serverStatistics.getCompanyStatistics(webId);

		return companyStatistics.getMinTime();
	}

	@Override
	public long getMinTimeByPortlet(String portletId)
		throws MonitoringException {

		long minTime = 0;

		for (CompanyStatistics companyStatistics :
				_serverStatistics.getCompanyStatisticsSet()) {

			long curMinTime = getMinTimeByPortlet(portletId, companyStatistics);

			if (curMinTime < minTime) {
				minTime = curMinTime;
			}
		}

		return minTime;
	}

	@Override
	public long getMinTimeByPortlet(String portletId, long companyId)
		throws MonitoringException {

		CompanyStatistics companyStatistics =
			_serverStatistics.getCompanyStatistics(companyId);

		return getMinTimeByPortlet(portletId, companyStatistics);
	}

	@Override
	public long getMinTimeByPortlet(String portletId, String webId)
		throws MonitoringException {

		CompanyStatistics companyStatistics =
			_serverStatistics.getCompanyStatistics(webId);

		return getMinTimeByPortlet(portletId, companyStatistics);
	}

	@Override
	public long getRequestCount() {
		long requestCount = 0;

		for (CompanyStatistics companyStatistics :
				_serverStatistics.getCompanyStatisticsSet()) {

			requestCount += getRequestCountByCompany(companyStatistics);
		}

		return requestCount;
	}

	@Override
	public long getRequestCountByCompany(long companyId)
		throws MonitoringException {

		CompanyStatistics companyStatistics =
			_serverStatistics.getCompanyStatistics(companyId);

		return getRequestCountByCompany(companyStatistics);
	}

	@Override
	public long getRequestCountByCompany(String webId)
		throws MonitoringException {

		CompanyStatistics companyStatistics =
			_serverStatistics.getCompanyStatistics(webId);

		return getRequestCountByCompany(companyStatistics);
	}

	@Override
	public long getRequestCountByPortlet(String portletId)
		throws MonitoringException {

		long requestCount = 0;

		for (CompanyStatistics companyStatistics :
				_serverStatistics.getCompanyStatisticsSet()) {

			requestCount += getRequestCountByPortlet(
				portletId, companyStatistics);
		}

		return requestCount;
	}

	@Override
	public long getRequestCountByPortlet(String portletId, long companyId)
		throws MonitoringException {

		CompanyStatistics companyStatistics =
			_serverStatistics.getCompanyStatistics(companyId);

		return getRequestCountByPortlet(portletId, companyStatistics);
	}

	@Override
	public long getRequestCountByPortlet(String portletId, String webId)
		throws MonitoringException {

		CompanyStatistics companyStatistics =
			_serverStatistics.getCompanyStatistics(webId);

		return getRequestCountByPortlet(portletId, companyStatistics);
	}

	@Override
	public long getSuccessCount() {
		long successCount = 0;

		for (CompanyStatistics companyStatistics :
				_serverStatistics.getCompanyStatisticsSet()) {

			successCount += getSuccessCountByCompany(companyStatistics);
		}

		return successCount;
	}

	@Override
	public long getSuccessCountByCompany(long companyId)
		throws MonitoringException {

		CompanyStatistics companyStatistics =
			_serverStatistics.getCompanyStatistics(companyId);

		return getSuccessCountByCompany(companyStatistics);
	}

	@Override
	public long getSuccessCountByCompany(String webId)
		throws MonitoringException {

		CompanyStatistics companyStatistics =
			_serverStatistics.getCompanyStatistics(webId);

		return getSuccessCountByCompany(companyStatistics);
	}

	@Override
	public long getSuccessCountByPortlet(String portletId)
		throws MonitoringException {

		long successCount = 0;

		for (CompanyStatistics companyStatistics :
				_serverStatistics.getCompanyStatisticsSet()) {

			successCount += getSuccessCountByPortlet(
				portletId, companyStatistics);
		}

		return successCount;
	}

	@Override
	public long getSuccessCountByPortlet(String portletId, long companyId)
		throws MonitoringException {

		CompanyStatistics companyStatistics =
			_serverStatistics.getCompanyStatistics(companyId);

		return getSuccessCountByPortlet(portletId, companyStatistics);
	}

	@Override
	public long getSuccessCountByPortlet(String portletId, String webId)
		throws MonitoringException {

		CompanyStatistics companyStatistics =
			_serverStatistics.getCompanyStatistics(webId);

		return getSuccessCountByPortlet(portletId, companyStatistics);
	}

	@Override
	public long getTimeoutCount() {
		long timeoutCount = 0;

		for (CompanyStatistics companyStatistics :
				_serverStatistics.getCompanyStatisticsSet()) {

			timeoutCount += getTimeoutCountByCompany(companyStatistics);
		}

		return timeoutCount;
	}

	@Override
	public long getTimeoutCountByCompany(long companyId)
		throws MonitoringException {

		CompanyStatistics companyStatistics =
			_serverStatistics.getCompanyStatistics(companyId);

		return getTimeoutCountByCompany(companyStatistics);
	}

	@Override
	public long getTimeoutCountByCompany(String webId)
		throws MonitoringException {

		CompanyStatistics companyStatistics =
			_serverStatistics.getCompanyStatistics(webId);

		return getTimeoutCountByCompany(companyStatistics);
	}

	@Override
	public long getTimeoutCountByPortlet(String portletId)
		throws MonitoringException {

		long timeoutCount = 0;

		for (CompanyStatistics companyStatistics :
				_serverStatistics.getCompanyStatisticsSet()) {

			timeoutCount += getTimeoutCountByPortlet(
				portletId, companyStatistics);
		}

		return timeoutCount;
	}

	@Override
	public long getTimeoutCountByPortlet(String portletId, long companyId)
		throws MonitoringException {

		CompanyStatistics companyStatistics =
			_serverStatistics.getCompanyStatistics(companyId);

		return getTimeoutCountByPortlet(portletId, companyStatistics);
	}

	@Override
	public long getTimeoutCountByPortlet(String portletId, String webId)
		throws MonitoringException {

		CompanyStatistics companyStatistics =
			_serverStatistics.getCompanyStatistics(webId);

		return getTimeoutCountByPortlet(portletId, companyStatistics);
	}

	public void setServerStatistics(ServerStatistics serverStatistics) {
		_serverStatistics = serverStatistics;
	}

	protected long getAverageTimeByCompany(
		CompanyStatistics companyStatistics) {

		long averageTime = 0;

		Set<RequestStatistics> requestStatisticsSet =
			companyStatistics.getRenderRequestStatisticsSet();

		for (RequestStatistics requestStatistics : requestStatisticsSet) {
			averageTime += requestStatistics.getAverageTime();
		}

		return averageTime / requestStatisticsSet.size();
	}

	protected long getErrorCountByCompany(CompanyStatistics companyStatistics) {
		long errorCount = 0;

		for (RequestStatistics requestStatistics :
				companyStatistics.getRenderRequestStatisticsSet()) {

			errorCount += requestStatistics.getErrorCount();
		}

		return errorCount;
	}

	protected long getErrorCountByPortlet(
			String portletId, CompanyStatistics companyStatistics)
		throws MonitoringException {

		RequestStatistics requestStatistics =
			companyStatistics.getRenderRequestStatistics(portletId);

		return requestStatistics.getErrorCount();
	}

	protected long getMaxTimeByPortlet(
			String portletId, CompanyStatistics companyStatistics)
		throws MonitoringException {

		long maxTime = 0;

		RequestStatistics requestStatistics =
			companyStatistics.getRenderRequestStatistics(portletId);

		if (requestStatistics.getMaxTime() > maxTime) {
			maxTime = requestStatistics.getMaxTime();
		}

		return maxTime;
	}

	protected long getMinTimeByPortlet(
			String portletId, CompanyStatistics companyStatistics)
		throws MonitoringException {

		long minTime = 0;

		RequestStatistics requestStatistics =
			companyStatistics.getRenderRequestStatistics(portletId);

		if (requestStatistics.getMinTime() < minTime) {
			minTime = requestStatistics.getMinTime();
		}

		return minTime;
	}

	protected long getRequestCountByCompany(
		CompanyStatistics companyStatistics) {

		long requestCount = 0;

		for (RequestStatistics requestStatistics :
				companyStatistics.getRenderRequestStatisticsSet()) {

			requestCount += requestStatistics.getRequestCount();
		}

		return requestCount;
	}

	protected long getRequestCountByPortlet(
			String portletId, CompanyStatistics companyStatistics)
		throws MonitoringException {

		RequestStatistics requestStatistics =
			companyStatistics.getRenderRequestStatistics(portletId);

		return requestStatistics.getRequestCount();
	}

	protected long getSuccessCountByCompany(
		CompanyStatistics companyStatistics) {

		long successCount = 0;

		for (RequestStatistics requestStatistics :
				companyStatistics.getRenderRequestStatisticsSet()) {

			successCount += requestStatistics.getSuccessCount();
		}

		return successCount;
	}

	protected long getSuccessCountByPortlet(
			String portletId, CompanyStatistics companyStatistics)
		throws MonitoringException {

		RequestStatistics requestStatistics =
			companyStatistics.getRenderRequestStatistics(portletId);

		return requestStatistics.getSuccessCount();
	}

	protected long getTimeoutCountByCompany(
		CompanyStatistics companyStatistics) {

		long timeoutCount = 0;

		for (RequestStatistics requestStatistics :
				companyStatistics.getRenderRequestStatisticsSet()) {

			timeoutCount += requestStatistics.getTimeoutCount();
		}

		return timeoutCount;
	}

	protected long getTimeoutCountByPortlet(
			String portletId, CompanyStatistics companyStatistics)
		throws MonitoringException {

		RequestStatistics requestStatistics =
			companyStatistics.getRenderRequestStatistics(portletId);

		return requestStatistics.getTimeoutCount();
	}

	private ServerStatistics _serverStatistics;

}