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

package com.liferay.portal.monitoring.jmx;

import com.liferay.portal.kernel.monitoring.MonitoringException;
import com.liferay.portal.kernel.monitoring.statistics.SummaryStatistics;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.monitoring.statistics.portal.ServerStatistics;

import java.util.Set;

/**
 * @author Michael C. Han
 * @author Brian Wing Shun Chan
 */
public class PortalManager implements PortalManagerMBean {

	@Override
	public long getAverageTime() throws MonitoringException {
		return _summaryStatistics.getAverageTime();
	}

	@Override
	public long getAverageTimeByCompany(long companyId)
		throws MonitoringException {

		return _summaryStatistics.getAverageTimeByCompany(companyId);
	}

	@Override
	public long getAverageTimeByCompany(String webId)
		throws MonitoringException {

		return _summaryStatistics.getAverageTimeByCompany(webId);
	}

	@Override
	public long[] getCompanyIds() {
		Set<Long> companyIds = _serverStatistics.getCompanyIds();

		return ArrayUtil.toArray(
			companyIds.toArray(new Long[companyIds.size()]));
	}

	@Override
	public long getErrorCount() throws MonitoringException {
		return _summaryStatistics.getErrorCount();
	}

	@Override
	public long getErrorCountByCompany(long companyId)
		throws MonitoringException {

		return _summaryStatistics.getErrorCountByCompany(companyId);
	}

	@Override
	public long getErrorCountByCompany(String webId)
		throws MonitoringException {

		return _summaryStatistics.getErrorCountByCompany(webId);
	}

	@Override
	public long getMaxTime() throws MonitoringException {
		return _summaryStatistics.getMaxTime();
	}

	@Override
	public long getMaxTimeByCompany(long companyId) throws MonitoringException {
		return _summaryStatistics.getMaxTimeByCompany(companyId);
	}

	@Override
	public long getMaxTimeByCompany(String webId) throws MonitoringException {
		return _summaryStatistics.getMaxTimeByCompany(webId);
	}

	@Override
	public long getMinTime() throws MonitoringException {
		return _summaryStatistics.getMinTime();
	}

	@Override
	public long getMinTimeByCompany(long companyId) throws MonitoringException {
		return _summaryStatistics.getMinTimeByCompany(companyId);
	}

	@Override
	public long getMinTimeByCompany(String webId) throws MonitoringException {
		return _summaryStatistics.getMinTimeByCompany(webId);
	}

	@Override
	public long getRequestCount() throws MonitoringException {
		return _summaryStatistics.getRequestCount();
	}

	@Override
	public long getRequestCountByCompany(long companyId)
		throws MonitoringException {

		return _summaryStatistics.getRequestCountByCompany(companyId);
	}

	@Override
	public long getRequestCountByCompany(String webId)
		throws MonitoringException {

		return _summaryStatistics.getRequestCountByCompany(webId);
	}

	public long getStartTime(long companyId) throws MonitoringException {
		return _serverStatistics.getCompanyStatistics(companyId).getStartTime();
	}

	public long getStartTime(String webId) throws MonitoringException {
		return _serverStatistics.getCompanyStatistics(webId).getStartTime();
	}

	@Override
	public long getSuccessCount() throws MonitoringException {
		return _summaryStatistics.getSuccessCount();
	}

	@Override
	public long getSuccessCountByCompany(long companyId)
		throws MonitoringException {

		return _summaryStatistics.getSuccessCountByCompany(companyId);
	}

	@Override
	public long getSuccessCountByCompany(String webId)
		throws MonitoringException {

		return _summaryStatistics.getSuccessCountByCompany(webId);
	}

	@Override
	public long getTimeoutCount() throws MonitoringException {
		return _summaryStatistics.getTimeoutCount();
	}

	@Override
	public long getTimeoutCountByCompany(long companyId)
		throws MonitoringException {

		return _summaryStatistics.getTimeoutCountByCompany(companyId);
	}

	@Override
	public long getTimeoutCountByCompany(String webId)
		throws MonitoringException {

		return _summaryStatistics.getTimeoutCountByCompany(webId);
	}

	@Override
	public long getUptime(long companyId) throws MonitoringException {
		return _serverStatistics.getCompanyStatistics(companyId).getUptime();
	}

	@Override
	public long getUptime(String webId) throws MonitoringException {
		return _serverStatistics.getCompanyStatistics(webId).getUptime();
	}

	@Override
	public String[] getWebIds() {
		Set<String> webIds = _serverStatistics.getWebIds();

		return webIds.toArray(new String[webIds.size()]);
	}

	@Override
	public void reset() {
		_serverStatistics.reset();
	}

	@Override
	public void reset(long companyId) {
		_serverStatistics.reset(companyId);
	}

	@Override
	public void reset(String webId) {
		_serverStatistics.reset(webId);
	}

	public void setServerStatistics(ServerStatistics serverStatistics) {
		_serverStatistics = serverStatistics;
	}

	public void setSummaryStatistics(SummaryStatistics summaryStatistics) {
		_summaryStatistics = summaryStatistics;
	}

	private ServerStatistics _serverStatistics;
	private SummaryStatistics _summaryStatistics;

}