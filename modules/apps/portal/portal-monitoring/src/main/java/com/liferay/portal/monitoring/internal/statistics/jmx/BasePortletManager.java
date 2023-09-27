/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.monitoring.internal.statistics.jmx;

import com.liferay.portal.kernel.monitoring.MonitoringException;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.monitoring.internal.statistics.portlet.PortletSummaryStatistics;
import com.liferay.portal.monitoring.internal.statistics.portlet.ServerStatisticsHelper;

import java.util.Set;

import javax.management.NotCompliantMBeanException;
import javax.management.StandardMBean;

import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 * @author Brian Wing Shun Chan
 */
public abstract class BasePortletManager
	extends StandardMBean implements PortletManagerMBean {

	public BasePortletManager(Class<?> mBeanInterface)
		throws NotCompliantMBeanException {

		super(mBeanInterface);
	}

	@Override
	public long getAverageTime() throws MonitoringException {
		PortletSummaryStatistics portletSummaryStatistics =
			getPortletSummaryStatistics();

		return portletSummaryStatistics.getAverageTime();
	}

	@Override
	public long getAverageTimeByCompany(long companyId)
		throws MonitoringException {

		PortletSummaryStatistics portletSummaryStatistics =
			getPortletSummaryStatistics();

		return portletSummaryStatistics.getAverageTimeByCompany(companyId);
	}

	@Override
	public long getAverageTimeByCompany(String webId)
		throws MonitoringException {

		PortletSummaryStatistics portletSummaryStatistics =
			getPortletSummaryStatistics();

		return portletSummaryStatistics.getAverageTimeByCompany(webId);
	}

	@Override
	public long getAverageTimeByPortlet(String portletId)
		throws MonitoringException {

		PortletSummaryStatistics portletSummaryStatistics =
			getPortletSummaryStatistics();

		return portletSummaryStatistics.getAverageTimeByPortlet(portletId);
	}

	@Override
	public long getAverageTimeByPortlet(String portletId, long companyId)
		throws MonitoringException {

		PortletSummaryStatistics portletSummaryStatistics =
			getPortletSummaryStatistics();

		return portletSummaryStatistics.getAverageTimeByPortlet(
			portletId, companyId);
	}

	@Override
	public long getAverageTimeByPortlet(String portletId, String webId)
		throws MonitoringException {

		PortletSummaryStatistics portletSummaryStatistics =
			getPortletSummaryStatistics();

		return portletSummaryStatistics.getAverageTimeByPortlet(
			portletId, webId);
	}

	@Override
	public long[] getCompanyIds() {
		Set<Long> companyIds = serverStatisticsHelper.getCompanyIds();

		return ArrayUtil.toArray(companyIds.toArray(new Long[0]));
	}

	@Override
	public long getErrorCount() throws MonitoringException {
		PortletSummaryStatistics portletSummaryStatistics =
			getPortletSummaryStatistics();

		return portletSummaryStatistics.getErrorCount();
	}

	@Override
	public long getErrorCountByCompany(long companyId)
		throws MonitoringException {

		PortletSummaryStatistics portletSummaryStatistics =
			getPortletSummaryStatistics();

		return portletSummaryStatistics.getErrorCountByCompany(companyId);
	}

	@Override
	public long getErrorCountByCompany(String webId)
		throws MonitoringException {

		PortletSummaryStatistics portletSummaryStatistics =
			getPortletSummaryStatistics();

		return portletSummaryStatistics.getErrorCountByCompany(webId);
	}

	@Override
	public long getErrorCountByPortlet(String portletId)
		throws MonitoringException {

		PortletSummaryStatistics portletSummaryStatistics =
			getPortletSummaryStatistics();

		return portletSummaryStatistics.getErrorCountByPortlet(portletId);
	}

	@Override
	public long getErrorCountByPortlet(String portletId, long companyId)
		throws MonitoringException {

		PortletSummaryStatistics portletSummaryStatistics =
			getPortletSummaryStatistics();

		return portletSummaryStatistics.getErrorCountByPortlet(
			portletId, companyId);
	}

	@Override
	public long getErrorCountByPortlet(String portletId, String webId)
		throws MonitoringException {

		PortletSummaryStatistics portletSummaryStatistics =
			getPortletSummaryStatistics();

		return portletSummaryStatistics.getErrorCountByPortlet(
			portletId, webId);
	}

	@Override
	public long getMaxTime() throws MonitoringException {
		PortletSummaryStatistics portletSummaryStatistics =
			getPortletSummaryStatistics();

		return portletSummaryStatistics.getMaxTime();
	}

	@Override
	public long getMaxTimeByCompany(long companyId) throws MonitoringException {
		PortletSummaryStatistics portletSummaryStatistics =
			getPortletSummaryStatistics();

		return portletSummaryStatistics.getMaxTimeByCompany(companyId);
	}

	@Override
	public long getMaxTimeByCompany(String webId) throws MonitoringException {
		PortletSummaryStatistics portletSummaryStatistics =
			getPortletSummaryStatistics();

		return portletSummaryStatistics.getMaxTimeByCompany(webId);
	}

	@Override
	public long getMaxTimeByPortlet(String portletId)
		throws MonitoringException {

		PortletSummaryStatistics portletSummaryStatistics =
			getPortletSummaryStatistics();

		return portletSummaryStatistics.getMaxTimeByPortlet(portletId);
	}

	@Override
	public long getMaxTimeByPortlet(String portletId, long companyId)
		throws MonitoringException {

		PortletSummaryStatistics portletSummaryStatistics =
			getPortletSummaryStatistics();

		return portletSummaryStatistics.getMaxTimeByPortlet(
			portletId, companyId);
	}

	@Override
	public long getMaxTimeByPortlet(String portletId, String webId)
		throws MonitoringException {

		PortletSummaryStatistics portletSummaryStatistics =
			getPortletSummaryStatistics();

		return portletSummaryStatistics.getMaxTimeByPortlet(portletId, webId);
	}

	@Override
	public long getMinTime() throws MonitoringException {
		PortletSummaryStatistics portletSummaryStatistics =
			getPortletSummaryStatistics();

		return portletSummaryStatistics.getMinTime();
	}

	@Override
	public long getMinTimeByCompany(long companyId) throws MonitoringException {
		PortletSummaryStatistics portletSummaryStatistics =
			getPortletSummaryStatistics();

		return portletSummaryStatistics.getMinTimeByCompany(companyId);
	}

	@Override
	public long getMinTimeByCompany(String webId) throws MonitoringException {
		PortletSummaryStatistics portletSummaryStatistics =
			getPortletSummaryStatistics();

		return portletSummaryStatistics.getMinTimeByCompany(webId);
	}

	@Override
	public long getMinTimeByPortlet(String portletId)
		throws MonitoringException {

		PortletSummaryStatistics portletSummaryStatistics =
			getPortletSummaryStatistics();

		return portletSummaryStatistics.getMinTimeByPortlet(portletId);
	}

	@Override
	public long getMinTimeByPortlet(String portletId, long companyId)
		throws MonitoringException {

		PortletSummaryStatistics portletSummaryStatistics =
			getPortletSummaryStatistics();

		return portletSummaryStatistics.getMinTimeByPortlet(
			portletId, companyId);
	}

	@Override
	public long getMinTimeByPortlet(String portletId, String webId)
		throws MonitoringException {

		PortletSummaryStatistics portletSummaryStatistics =
			getPortletSummaryStatistics();

		return portletSummaryStatistics.getMinTimeByPortlet(portletId, webId);
	}

	@Override
	public String[] getPortletIds() {
		Set<String> portletIds = serverStatisticsHelper.getPortletIds();

		return portletIds.toArray(new String[0]);
	}

	@Override
	public long getRequestCount() throws MonitoringException {
		PortletSummaryStatistics portletSummaryStatistics =
			getPortletSummaryStatistics();

		return portletSummaryStatistics.getRequestCount();
	}

	@Override
	public long getRequestCountByCompany(long companyId)
		throws MonitoringException {

		PortletSummaryStatistics portletSummaryStatistics =
			getPortletSummaryStatistics();

		return portletSummaryStatistics.getRequestCountByCompany(companyId);
	}

	@Override
	public long getRequestCountByCompany(String webId)
		throws MonitoringException {

		PortletSummaryStatistics portletSummaryStatistics =
			getPortletSummaryStatistics();

		return portletSummaryStatistics.getRequestCountByCompany(webId);
	}

	@Override
	public long getRequestCountByPortlet(String portletId)
		throws MonitoringException {

		PortletSummaryStatistics portletSummaryStatistics =
			getPortletSummaryStatistics();

		return portletSummaryStatistics.getRequestCountByPortlet(portletId);
	}

	@Override
	public long getRequestCountByPortlet(String portletId, long companyId)
		throws MonitoringException {

		PortletSummaryStatistics portletSummaryStatistics =
			getPortletSummaryStatistics();

		return portletSummaryStatistics.getRequestCountByPortlet(
			portletId, companyId);
	}

	@Override
	public long getRequestCountByPortlet(String portletId, String webId)
		throws MonitoringException {

		PortletSummaryStatistics portletSummaryStatistics =
			getPortletSummaryStatistics();

		return portletSummaryStatistics.getRequestCountByPortlet(
			portletId, webId);
	}

	@Override
	public long getSuccessCount() throws MonitoringException {
		PortletSummaryStatistics portletSummaryStatistics =
			getPortletSummaryStatistics();

		return portletSummaryStatistics.getSuccessCount();
	}

	@Override
	public long getSuccessCountByCompany(long companyId)
		throws MonitoringException {

		PortletSummaryStatistics portletSummaryStatistics =
			getPortletSummaryStatistics();

		return portletSummaryStatistics.getSuccessCountByCompany(companyId);
	}

	@Override
	public long getSuccessCountByCompany(String webId)
		throws MonitoringException {

		PortletSummaryStatistics portletSummaryStatistics =
			getPortletSummaryStatistics();

		return portletSummaryStatistics.getSuccessCountByCompany(webId);
	}

	@Override
	public long getSuccessCountByPortlet(String portletId)
		throws MonitoringException {

		PortletSummaryStatistics portletSummaryStatistics =
			getPortletSummaryStatistics();

		return portletSummaryStatistics.getSuccessCountByPortlet(portletId);
	}

	@Override
	public long getSuccessCountByPortlet(String portletId, long companyId)
		throws MonitoringException {

		PortletSummaryStatistics portletSummaryStatistics =
			getPortletSummaryStatistics();

		return portletSummaryStatistics.getSuccessCountByPortlet(
			portletId, companyId);
	}

	@Override
	public long getSuccessCountByPortlet(String portletId, String webId)
		throws MonitoringException {

		PortletSummaryStatistics portletSummaryStatistics =
			getPortletSummaryStatistics();

		return portletSummaryStatistics.getSuccessCountByPortlet(
			portletId, webId);
	}

	@Override
	public long getTimeoutCount() throws MonitoringException {
		PortletSummaryStatistics portletSummaryStatistics =
			getPortletSummaryStatistics();

		return portletSummaryStatistics.getTimeoutCount();
	}

	@Override
	public long getTimeoutCountByCompany(long companyId)
		throws MonitoringException {

		PortletSummaryStatistics portletSummaryStatistics =
			getPortletSummaryStatistics();

		return portletSummaryStatistics.getTimeoutCountByCompany(companyId);
	}

	@Override
	public long getTimeoutCountByCompany(String webId)
		throws MonitoringException {

		PortletSummaryStatistics portletSummaryStatistics =
			getPortletSummaryStatistics();

		return portletSummaryStatistics.getTimeoutCountByCompany(webId);
	}

	@Override
	public long getTimeoutCountByPortlet(String portletId)
		throws MonitoringException {

		PortletSummaryStatistics portletSummaryStatistics =
			getPortletSummaryStatistics();

		return portletSummaryStatistics.getTimeoutCountByPortlet(portletId);
	}

	@Override
	public long getTimeoutCountByPortlet(String portletId, long companyId)
		throws MonitoringException {

		PortletSummaryStatistics portletSummaryStatistics =
			getPortletSummaryStatistics();

		return portletSummaryStatistics.getTimeoutCountByPortlet(
			portletId, companyId);
	}

	@Override
	public long getTimeoutCountByPortlet(String portletId, String webId)
		throws MonitoringException {

		PortletSummaryStatistics portletSummaryStatistics =
			getPortletSummaryStatistics();

		return portletSummaryStatistics.getTimeoutCountByPortlet(
			portletId, webId);
	}

	@Override
	public String[] getWebIds() {
		Set<String> webIds = serverStatisticsHelper.getWebIds();

		return webIds.toArray(new String[0]);
	}

	@Override
	public void reset() {
		serverStatisticsHelper.reset();
	}

	@Override
	public void reset(long companyId) {
		serverStatisticsHelper.reset(companyId);
	}

	@Override
	public void reset(String webId) {
		serverStatisticsHelper.reset(webId);
	}

	protected abstract PortletSummaryStatistics getPortletSummaryStatistics();

	@Reference
	protected ServerStatisticsHelper serverStatisticsHelper;

}