/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.monitoring.internal.statistics.jmx;

import com.liferay.portal.kernel.monitoring.MonitoringException;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.monitoring.internal.statistics.SummaryStatistics;
import com.liferay.portal.monitoring.internal.statistics.portal.CompanyStatistics;
import com.liferay.portal.monitoring.internal.statistics.portal.ServerStatisticsHelper;

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
	enabled = false,
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
	public long getAverageTime() throws MonitoringException {
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
		Set<Long> companyIds = _serverStatisticsHelper.getCompanyIds();

		return ArrayUtil.toArray(companyIds.toArray(new Long[0]));
	}

	@Override
	public long getErrorCount() throws MonitoringException {
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
	public long getMaxTime() throws MonitoringException {
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
	public long getMinTime() throws MonitoringException {
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
	public long getRequestCount() throws MonitoringException {
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
			_serverStatisticsHelper.getCompanyStatistics(companyId);

		return companyStatistics.getStartTime();
	}

	public long getStartTime(String webId) throws MonitoringException {
		CompanyStatistics companyStatistics =
			_serverStatisticsHelper.getCompanyStatistics(webId);

		return companyStatistics.getStartTime();
	}

	@Override
	public long getSuccessCount() throws MonitoringException {
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
	public long getTimeoutCount() throws MonitoringException {
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
			_serverStatisticsHelper.getCompanyStatistics(companyId);

		return companyStatistics.getUptime();
	}

	@Override
	public long getUptime(String webId) throws MonitoringException {
		CompanyStatistics companyStatistics =
			_serverStatisticsHelper.getCompanyStatistics(webId);

		return companyStatistics.getUptime();
	}

	@Override
	public String[] getWebIds() {
		Set<String> webIds = _serverStatisticsHelper.getWebIds();

		return webIds.toArray(new String[0]);
	}

	@Override
	public void reset() {
		_serverStatisticsHelper.reset();
	}

	@Override
	public void reset(long companyId) {
		_serverStatisticsHelper.reset(companyId);
	}

	@Override
	public void reset(String webId) {
		_serverStatisticsHelper.reset(webId);
	}

	@Reference
	private ServerStatisticsHelper _serverStatisticsHelper;

	@Reference(
		target = "(component.name=com.liferay.portal.monitoring.internal.statistics.portal.ServerSummaryStatistics)"
	)
	private SummaryStatistics _serverSummaryStatistics;

}