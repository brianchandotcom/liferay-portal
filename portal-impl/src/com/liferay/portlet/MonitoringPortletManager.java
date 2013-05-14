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

package com.liferay.portlet;

/**
 * @author Michael C. Han
 */
public class MonitoringPortletManager implements MonitoringPortletManagerMBean {

	@Override
	public boolean isMonitoringPortletActionRequest() {
		return MonitoringPortlet.isMonitoringPortletActionRequest();
	}

	@Override
	public boolean isMonitoringPortletEventRequest() {
		return MonitoringPortlet.isMonitoringPortletEventRequest();
	}

	@Override
	public boolean isMonitoringPortletRenderRequest() {
		return MonitoringPortlet.isMonitoringPortletRenderRequest();
	}

	@Override
	public boolean isMonitoringPortletResourceRequest() {
		return MonitoringPortlet.isMonitoringPortletResourceRequest();
	}

	@Override
	public void setMonitoringPortletActionRequest(
		boolean monitoringPortletActionRequest) {

		MonitoringPortlet.setMonitoringPortletActionRequest(
			monitoringPortletActionRequest);
	}

	@Override
	public void setMonitoringPortletEventRequest(
		boolean monitoringPortletEventRequest) {

		MonitoringPortlet.setMonitoringPortletEventRequest(
			monitoringPortletEventRequest);
	}

	@Override
	public void setMonitoringPortletRenderRequest(
		boolean monitoringPortletRenderRequest) {

		MonitoringPortlet.setMonitoringPortletRenderRequest(
			monitoringPortletRenderRequest);
	}

	@Override
	public void setMonitoringPortletResourceRequest(
		boolean monitoringPortletResourceRequest) {

		MonitoringPortlet.setMonitoringPortletResourceRequest(
			monitoringPortletResourceRequest);
	}

}