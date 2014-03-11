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

package com.liferay.portal.servlet.filters.monitoring;

import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.monitoring.RequestStatus;
import com.liferay.portal.kernel.monitoring.statistics.DataSampleThreadLocal;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.monitoring.statistics.portal.PortalRequestDataSample;
import com.liferay.portal.monitoring.statistics.service.ServiceMonitorAdvice;
import com.liferay.portal.servlet.filters.BasePortalFilter;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.MonitoringPortlet;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Rajesh Thiagarajan
 * @author Michael C. Han
 */
public class MonitoringFilter extends BasePortalFilter {

	public static boolean isMonitoringPortalRequest() {
		return _monitoringPortalRequest;
	}

	public static void setMonitoringPortalRequest(
		boolean monitoringPortalRequest) {

		_monitoringPortalRequest = monitoringPortalRequest;
	}

	@Override
	public boolean isFilterEnabled() {
		if (!super.isFilterEnabled()) {
			return false;
		}

		if (!_monitoringPortalRequest &&
			!MonitoringPortlet.isMonitoringPortletActionRequest() &&
			!MonitoringPortlet.isMonitoringPortletEventRequest() &&
			!MonitoringPortlet.isMonitoringPortletRenderRequest() &&
			!MonitoringPortlet.isMonitoringPortletResourceRequest() &&
			!ServiceMonitorAdvice.isActive()) {

			return false;
		}

		return true;
	}

	@Override
	protected void processFilter(
			HttpServletRequest request, HttpServletResponse response,
			FilterChain filterChain)
		throws IOException, ServletException {

		long companyId = PortalUtil.getCompanyId(request);

		PortalRequestDataSample portalRequestDataSample = null;

		if (_monitoringPortalRequest) {
			portalRequestDataSample = new PortalRequestDataSample(
				companyId, request.getRemoteUser(), request.getRequestURI(),
				GetterUtil.getString(request.getRequestURL()));

			DataSampleThreadLocal.initialize();
		}

		try {
			if (portalRequestDataSample != null) {
				portalRequestDataSample.prepare();
			}

			processFilter(
				MonitoringFilter.class, request, response, filterChain);

			if (portalRequestDataSample != null) {
				portalRequestDataSample.capture(RequestStatus.SUCCESS);
			}
		}
		catch (Exception e) {
			if (portalRequestDataSample != null) {
				portalRequestDataSample.capture(RequestStatus.ERROR);
			}

			if (e instanceof IOException) {
				throw (IOException)e;
			}
			else if (e instanceof ServletException) {
				throw (ServletException)e;
			}
			else {
				throw new ServletException("Unable to execute request", e);
			}
		}
		finally {
			if (portalRequestDataSample != null) {
				DataSampleThreadLocal.addDataSample(portalRequestDataSample);
			}

			MessageBusUtil.sendMessage(
				DestinationNames.MONITORING,
				DataSampleThreadLocal.getDataSamples());
		}
	}

	private static boolean _monitoringPortalRequest =
		PropsValues.MONITORING_PORTAL_REQUEST;

}