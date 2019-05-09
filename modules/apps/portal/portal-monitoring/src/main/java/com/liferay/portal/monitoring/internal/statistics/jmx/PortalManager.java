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

package com.liferay.portal.monitoring.internal.statistics.jmx;

import com.liferay.portal.kernel.monitoring.MonitoringException;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.monitoring.internal.statistics.portal.CompanyStatistics;
import com.liferay.portal.monitoring.internal.statistics.portal.ServerStatistics;
import com.liferay.portal.monitoring.internal.statistics.portal.ServerSummaryStatistics;

import java.util.Set;

import javax.management.DynamicMBean;
import javax.management.NotCompliantMBeanException;
import javax.management.StandardMBean;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 * @author Brian Wing Shun Chan
 */
@Component(
	enabled = false, immediate = true,
	property = {
		"jmx.objectname=com.liferay.portal.monitoring:classification=portal_statistic,name=PortalManager",
		"jmx.objectname.cache.key=PortalManager"
	},
	service = DynamicMBean.class
)
public class PortalManager extends StandardMBean implements PortalManagerMBean {

	public PortalManager() throws NotCompliantMBeanException {
		super(PortalManagerMBean.class);
	}

	@Override
	public long getAverageTime() {
		return _serverSummaryStatistics.getAverageTime();
	}

	@Override
	public long getAverageTimeByCompany(long companyId)
		throws MonitoringException {

		return _serverSummaryStatistics.getAverageTimeByCompany(companyId);
	}

	@Override
	public long getAverageTimeByCompany(String webId)
		throws MonitoringException {

		return _serverSummaryStatistics.getAverageTimeByCompany(webId);
	}

	@Override
	public long[] getCompanyIds() {
		Set<Long> companyIds = _serverStatistics.getCompanyIds();

		return ArrayUtil.toArray(companyIds.toArray(new Long[0]));
	}

	@Override
	public long getErrorCount() {
		return _serverSummaryStatistics.getErrorCount();
	}

	@Override
	public long getErrorCountByCompany(long companyId)
		throws MonitoringException {

		return _serverSummaryStatistics.getErrorCountByCompany(companyId);
	}

	@Override
	public long getErrorCountByCompany(String webId)
		throws MonitoringException {

		return _serverSummaryStatistics.getErrorCountByCompany(webId);
	}

	@Override
	public long getMaxTime() {
		return _serverSummaryStatistics.getMaxTime();
	}

	@Override
	public long getMaxTimeByCompany(long companyId) throws MonitoringException {
		return _serverSummaryStatistics.getMaxTimeByCompany(companyId);
	}

	@Override
	public long getMaxTimeByCompany(String webId) throws MonitoringException {
		return _serverSummaryStatistics.getMaxTimeByCompany(webId);
	}

	@Override
	public long getMinTime() {
		return _serverSummaryStatistics.getMinTime();
	}

	@Override
	public long getMinTimeByCompany(long companyId) throws MonitoringException {
		return _serverSummaryStatistics.getMinTimeByCompany(companyId);
	}

	@Override
	public long getMinTimeByCompany(String webId) throws MonitoringException {
		return _serverSummaryStatistics.getMinTimeByCompany(webId);
	}

	@Override
	public long getRequestCount() {
		return _serverSummaryStatistics.getRequestCount();
	}

	@Override
	public long getRequestCountByCompany(long companyId)
		throws MonitoringException {

		return _serverSummaryStatistics.getRequestCountByCompany(companyId);
	}

	@Override
	public long getRequestCountByCompany(String webId)
		throws MonitoringException {

		return _serverSummaryStatistics.getRequestCountByCompany(webId);
	}

	public long getStartTime(long companyId) throws MonitoringException {
		CompanyStatistics companyStatistics =
			_serverStatistics.getCompanyStatistics(companyId);

		return companyStatistics.getStartTime();
	}

	public long getStartTime(String webId) throws MonitoringException {
		CompanyStatistics companyStatistics =
			_serverStatistics.getCompanyStatistics(webId);

		return companyStatistics.getStartTime();
	}

	@Override
	public long getSuccessCount() {
		return _serverSummaryStatistics.getSuccessCount();
	}

	@Override
	public long getSuccessCountByCompany(long companyId)
		throws MonitoringException {

		return _serverSummaryStatistics.getSuccessCountByCompany(companyId);
	}

	@Override
	public long getSuccessCountByCompany(String webId)
		throws MonitoringException {

		return _serverSummaryStatistics.getSuccessCountByCompany(webId);
	}

	@Override
	public long getTimeoutCount() {
		return _serverSummaryStatistics.getTimeoutCount();
	}

	@Override
	public long getTimeoutCountByCompany(long companyId)
		throws MonitoringException {

		return _serverSummaryStatistics.getTimeoutCountByCompany(companyId);
	}

	@Override
	public long getTimeoutCountByCompany(String webId)
		throws MonitoringException {

		return _serverSummaryStatistics.getTimeoutCountByCompany(webId);
	}

	@Override
	public long getUptime(long companyId) throws MonitoringException {
		CompanyStatistics companyStatistics =
			_serverStatistics.getCompanyStatistics(companyId);

		return companyStatistics.getUptime();
	}

	@Override
	public long getUptime(String webId) throws MonitoringException {
		CompanyStatistics companyStatistics =
			_serverStatistics.getCompanyStatistics(webId);

		return companyStatistics.getUptime();
	}

	@Override
	public String[] getWebIds() {
		Set<String> webIds = _serverStatistics.getWebIds();

		return webIds.toArray(new String[0]);
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

	@Reference(unbind = "-")
	protected void setServerStatistics(ServerStatistics serverStatistics) {
		_serverStatistics = serverStatistics;
	}

	@Reference(unbind = "-")
	protected void setServerSummaryStatistics(
		ServerSummaryStatistics serverSummaryStatistics) {

		_serverSummaryStatistics = serverSummaryStatistics;
	}

	private ServerStatistics _serverStatistics;
	private ServerSummaryStatistics _serverSummaryStatistics;

}