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
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.monitoring.statistics.portlet.PortletSummaryStatistics;
import com.liferay.portal.monitoring.statistics.portlet.ServerStatistics;

import java.util.Set;

/**
 * @author Michael C. Han
 * @author Brian Wing Shun Chan
 */
public class PortletManager implements PortletManagerMBean {

	@Override
	public long getAverageTime() throws MonitoringException {
		return _portletSummaryStatistics.getAverageTime();
	}

	@Override
	public long getAverageTimeByCompany(long companyId)
		throws MonitoringException {

		return _portletSummaryStatistics.getAverageTimeByCompany(companyId);
	}

	@Override
	public long getAverageTimeByCompany(String webId)
		throws MonitoringException {

		return _portletSummaryStatistics.getAverageTimeByCompany(webId);
	}

	@Override
	public long getAverageTimeByPortlet(String portletId)
		throws MonitoringException {

		return _portletSummaryStatistics.getAverageTimeByPortlet(portletId);
	}

	@Override
	public long getAverageTimeByPortlet(String portletId, long companyId)
		throws MonitoringException {

		return _portletSummaryStatistics.getAverageTimeByPortlet(
			portletId, companyId);
	}

	@Override
	public long getAverageTimeByPortlet(String portletId, String webId)
		throws MonitoringException {

		return _portletSummaryStatistics.getAverageTimeByPortlet(
			portletId, webId);
	}

	@Override
	public long[] getCompanyIds() {
		Set<Long> companyIds = _serverStatistics.getCompanyIds();

		return ArrayUtil.toArray(
			companyIds.toArray(new Long[companyIds.size()]));
	}

	@Override
	public long getErrorCount() throws MonitoringException {
		return _portletSummaryStatistics.getErrorCount();
	}

	@Override
	public long getErrorCountByCompany(long companyId)
		throws MonitoringException {

		return _portletSummaryStatistics.getErrorCountByCompany(companyId);
	}

	@Override
	public long getErrorCountByCompany(String webId)
		throws MonitoringException {

		return _portletSummaryStatistics.getErrorCountByCompany(webId);
	}

	@Override
	public long getErrorCountByPortlet(String portletId)
		throws MonitoringException {

		return _portletSummaryStatistics.getErrorCountByPortlet(portletId);
	}

	@Override
	public long getErrorCountByPortlet(String portletId, long companyId)
		throws MonitoringException {

		return _portletSummaryStatistics.getErrorCountByPortlet(
			portletId, companyId);
	}

	@Override
	public long getErrorCountByPortlet(String portletId, String webId)
		throws MonitoringException {

		return _portletSummaryStatistics.getErrorCountByPortlet(
			portletId, webId);
	}

	@Override
	public long getMaxTime() throws MonitoringException {
		return _portletSummaryStatistics.getMaxTime();
	}

	@Override
	public long getMaxTimeByCompany(long companyId) throws MonitoringException {
		return _portletSummaryStatistics.getMaxTimeByCompany(companyId);
	}

	@Override
	public long getMaxTimeByCompany(String webId) throws MonitoringException {
		return _portletSummaryStatistics.getMaxTimeByCompany(webId);
	}

	@Override
	public long getMaxTimeByPortlet(String portletId)
		throws MonitoringException {

		return _portletSummaryStatistics.getMaxTimeByPortlet(portletId);
	}

	@Override
	public long getMaxTimeByPortlet(String portletId, long companyId)
		throws MonitoringException {

		return _portletSummaryStatistics.getMaxTimeByPortlet(
			portletId, companyId);
	}

	@Override
	public long getMaxTimeByPortlet(String portletId, String webId)
		throws MonitoringException {

		return _portletSummaryStatistics.getMaxTimeByPortlet(portletId, webId);
	}

	@Override
	public long getMinTime() throws MonitoringException {
		return _portletSummaryStatistics.getMinTime();
	}

	@Override
	public long getMinTimeByCompany(long companyId) throws MonitoringException {
		return _portletSummaryStatistics.getMinTimeByCompany(companyId);
	}

	@Override
	public long getMinTimeByCompany(String webId) throws MonitoringException {
		return _portletSummaryStatistics.getMinTimeByCompany(webId);
	}

	@Override
	public long getMinTimeByPortlet(String portletId)
		throws MonitoringException {

		return _portletSummaryStatistics.getMinTimeByPortlet(portletId);
	}

	@Override
	public long getMinTimeByPortlet(String portletId, long companyId)
		throws MonitoringException {

		return _portletSummaryStatistics.getMinTimeByPortlet(
			portletId, companyId);
	}

	@Override
	public long getMinTimeByPortlet(String portletId, String webId)
		throws MonitoringException {

		return _portletSummaryStatistics.getMinTimeByPortlet(portletId, webId);
	}

	@Override
	public String[] getPortletIds() {
		Set<String> portletIds = _serverStatistics.getPortletIds();

		return portletIds.toArray(new String[portletIds.size()]);
	}

	@Override
	public long getRequestCount() throws MonitoringException {
		return _portletSummaryStatistics.getRequestCount();
	}

	@Override
	public long getRequestCountByCompany(long companyId)
		throws MonitoringException {

		return _portletSummaryStatistics.getRequestCountByCompany(companyId);
	}

	@Override
	public long getRequestCountByCompany(String webId)
		throws MonitoringException {

		return _portletSummaryStatistics.getRequestCountByCompany(webId);
	}

	@Override
	public long getRequestCountByPortlet(String portletId)
		throws MonitoringException {

		return _portletSummaryStatistics.getRequestCountByPortlet(portletId);
	}

	@Override
	public long getRequestCountByPortlet(String portletId, long companyId)
		throws MonitoringException {

		return _portletSummaryStatistics.getRequestCountByPortlet(
			portletId, companyId);
	}

	@Override
	public long getRequestCountByPortlet(String portletId, String webId)
		throws MonitoringException {

		return _portletSummaryStatistics.getRequestCountByPortlet(
			portletId, webId);
	}

	@Override
	public long getSuccessCount() throws MonitoringException {
		return _portletSummaryStatistics.getSuccessCount();
	}

	@Override
	public long getSuccessCountByCompany(long companyId)
		throws MonitoringException {

		return _portletSummaryStatistics.getSuccessCountByCompany(companyId);
	}

	@Override
	public long getSuccessCountByCompany(String webId)
		throws MonitoringException {

		return _portletSummaryStatistics.getSuccessCountByCompany(webId);
	}

	@Override
	public long getSuccessCountByPortlet(String portletId)
		throws MonitoringException {

		return _portletSummaryStatistics.getSuccessCountByPortlet(portletId);
	}

	@Override
	public long getSuccessCountByPortlet(String portletId, long companyId)
		throws MonitoringException {

		return _portletSummaryStatistics.getSuccessCountByPortlet(
			portletId, companyId);
	}

	@Override
	public long getSuccessCountByPortlet(String portletId, String webId)
		throws MonitoringException {

		return _portletSummaryStatistics.getSuccessCountByPortlet(
			portletId, webId);
	}

	@Override
	public long getTimeoutCount() throws MonitoringException {
		return _portletSummaryStatistics.getTimeoutCount();
	}

	@Override
	public long getTimeoutCountByCompany(long companyId)
		throws MonitoringException {

		return _portletSummaryStatistics.getTimeoutCountByCompany(companyId);
	}

	@Override
	public long getTimeoutCountByCompany(String webId)
		throws MonitoringException {

		return _portletSummaryStatistics.getTimeoutCountByCompany(webId);
	}

	@Override
	public long getTimeoutCountByPortlet(String portletId)
		throws MonitoringException {

		return _portletSummaryStatistics.getTimeoutCountByPortlet(portletId);
	}

	@Override
	public long getTimeoutCountByPortlet(String portletId, long companyId)
		throws MonitoringException {

		return _portletSummaryStatistics.getTimeoutCountByPortlet(
			portletId, companyId);
	}

	@Override
	public long getTimeoutCountByPortlet(String portletId, String webId)
		throws MonitoringException {

		return _portletSummaryStatistics.getTimeoutCountByPortlet(
			portletId, webId);
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

	public void setPortletSummaryStatistics(
		PortletSummaryStatistics portletSummaryStatistics) {

		_portletSummaryStatistics = portletSummaryStatistics;
	}

	public void setServerStatistics(ServerStatistics serverStatistics) {
		_serverStatistics = serverStatistics;
	}

	private PortletSummaryStatistics _portletSummaryStatistics;
	private ServerStatistics _serverStatistics;

}