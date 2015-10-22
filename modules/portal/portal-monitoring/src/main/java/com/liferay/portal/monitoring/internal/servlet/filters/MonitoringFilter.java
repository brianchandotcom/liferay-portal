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

package com.liferay.portal.monitoring.internal.servlet.filters;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.monitoring.DataSample;
import com.liferay.portal.kernel.monitoring.DataSampleFactory;
import com.liferay.portal.kernel.monitoring.DataSampleThreadLocal;
import com.liferay.portal.kernel.monitoring.PortalMonitoringControl;
import com.liferay.portal.kernel.monitoring.PortletMonitoringControl;
import com.liferay.portal.kernel.monitoring.RequestStatus;
import com.liferay.portal.kernel.monitoring.ServiceMonitoringControl;
import com.liferay.portal.kernel.servlet.BaseFilter;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.model.Layout;
import com.liferay.portal.service.LayoutLocalService;
import com.liferay.portal.util.PortalUtil;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Rajesh Thiagarajan
 * @author Michael C. Han
 */
@Component(
	immediate = true,
	property = {
		"dispatcher=FORWARD", "dispatcher=REQUEST", "servlet-context-name=",
		"servlet-filter-name=Monitoring Filter", "url-pattern=/c/*",
		"url-pattern=/group/*", "url-pattern=/user/*", "url-pattern=/web/*"
	},
	service = {Filter.class, PortalMonitoringControl.class}
)
public class MonitoringFilter extends BaseFilter
	implements PortalMonitoringControl {

	@Override
	public boolean isFilterEnabled() {
		if (!super.isFilterEnabled()) {
			return false;
		}

		if (!_monitorPortalRequest &&
			!_portletMonitoringControl.isMonitorPortletActionRequest() &&
			!_portletMonitoringControl.isMonitorPortletEventRequest() &&
			!_portletMonitoringControl.isMonitorPortletRenderRequest() &&
			!_portletMonitoringControl.isMonitorPortletResourceRequest() &&
			!_serviceMonitoringControl.isMonitorServiceRequest()) {

			return false;
		}

		return true;
	}

	@Override
	public boolean isMonitorPortalRequest() {
		return _monitorPortalRequest;
	}

	@Override
	public void setMonitorPortalRequest(boolean monitorPortalRequest) {
		_monitorPortalRequest = monitorPortalRequest;
	}

	protected long getGroupId(HttpServletRequest request) {
		long groupId = ParamUtil.getLong(request, "groupId");

		if (groupId > 0) {
			return groupId;
		}

		long plid = ParamUtil.getLong(request, "p_l_id");

		if ((plid > 0) && (_layoutLocalService != null)) {
			try {
				Layout layout = _layoutLocalService.getLayout(plid);

				groupId = layout.getGroupId();
			}
			catch (PortalException pe) {
				if (_log.isDebugEnabled()) {
					_log.debug("Unable to retrieve layout " + plid, pe);
				}
			}
		}

		return groupId;
	}

	@Override
	protected Log getLog() {
		return _log;
	}

	@Override
	protected void processFilter(
			HttpServletRequest request, HttpServletResponse response,
			FilterChain filterChain)
		throws IOException, ServletException {

		long companyId = PortalUtil.getCompanyId(request);
		long groupId = getGroupId(request);

		DataSample dataSample = null;

		if (_monitorPortalRequest) {
			dataSample = _dataSampleFactory.createPortalRequestDataSample(
				companyId, groupId, request.getRemoteUser(),
				request.getRequestURI(),
				GetterUtil.getString(request.getRequestURL()));

			DataSampleThreadLocal.initialize();
		}

		try {
			if (dataSample != null) {
				dataSample.prepare();
			}

			processFilter(
				MonitoringFilter.class, request, response, filterChain);

			if (dataSample != null) {
				dataSample.capture(RequestStatus.SUCCESS);
			}
		}
		catch (Exception e) {
			if (dataSample != null) {
				dataSample.capture(RequestStatus.ERROR);
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
			if (dataSample != null) {
				DataSampleThreadLocal.addDataSample(dataSample);
			}

			MessageBusUtil.sendMessage(
				DestinationNames.MONITORING,
				DataSampleThreadLocal.getDataSamples());
		}
	}

	@Reference(unbind = "-")
	protected void setDataSampleFactory(DataSampleFactory dataSampleFactory) {
		_dataSampleFactory = dataSampleFactory;
	}

	@Reference(
		cardinality = ReferenceCardinality.OPTIONAL,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY
	)
	protected void setLayoutLocalService(
		LayoutLocalService layoutLocalService) {

		_layoutLocalService = layoutLocalService;
	}

	@Reference(unbind = "-")
	protected final void setPortletMonitoringControl(
		PortletMonitoringControl portletMonitoringControl) {

		_portletMonitoringControl = portletMonitoringControl;
	}

	@Reference(unbind = "-")
	protected void setServiceMonitoringControl(
		ServiceMonitoringControl serviceMonitoringControl) {

		_serviceMonitoringControl = serviceMonitoringControl;
	}

	protected void unsetLayoutLocalService(
		LayoutLocalService layoutLocalService) {

		_layoutLocalService = null;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		MonitoringFilter.class);

	private DataSampleFactory _dataSampleFactory;
	private volatile LayoutLocalService _layoutLocalService;
	private boolean _monitorPortalRequest;
	private PortletMonitoringControl _portletMonitoringControl;
	private ServiceMonitoringControl _serviceMonitoringControl;

}