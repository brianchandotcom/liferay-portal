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

package com.liferay.portal.monitoring.internal.portlet;

import com.liferay.portal.kernel.monitoring.PortletMonitoringControl;

import org.osgi.service.component.annotations.Component;

/**
 * @author Michael C. Han
 * @author Philip Jones
 */
@Component(
	enabled = false, immediate = true, service = PortletMonitoringControl.class
)
public class DefaultPortletMonitoringControl
	implements PortletMonitoringControl {

	@Override
	public boolean isMonitorPortletActionRequest() {
		return _monitorPortletActionRequest;
	}

	@Override
	public boolean isMonitorPortletEventRequest() {
		return _monitorPortletEventRequest;
	}

	@Override
	public boolean isMonitorPortletHeaderRequest() {
		return _monitorPortletHeaderRequest;
	}

	@Override
	public boolean isMonitorPortletRenderRequest() {
		return _monitorPortletRenderRequest;
	}

	@Override
	public boolean isMonitorPortletResourceRequest() {
		return _monitorPortletResourceRequest;
	}

	@Override
	public void setMonitorPortletActionRequest(
		boolean monitorPortletActionRequest) {

		_monitorPortletActionRequest = monitorPortletActionRequest;
	}

	@Override
	public void setMonitorPortletEventRequest(
		boolean monitorPortletEventRequest) {

		_monitorPortletEventRequest = monitorPortletEventRequest;
	}

	@Override
	public void setMonitorPortletHeaderRequest(
		boolean monitoringPortletHeaderRequest) {

		_monitorPortletHeaderRequest = monitoringPortletHeaderRequest;
	}

	@Override
	public void setMonitorPortletRenderRequest(
		boolean monitorPortletRenderRequest) {

		_monitorPortletRenderRequest = monitorPortletRenderRequest;
	}

	@Override
	public void setMonitorPortletResourceRequest(
		boolean monitorPortletResourceRequest) {

		_monitorPortletResourceRequest = monitorPortletResourceRequest;
	}

	private boolean _monitorPortletActionRequest;
	private boolean _monitorPortletEventRequest;
	private boolean _monitorPortletHeaderRequest;
	private boolean _monitorPortletRenderRequest;
	private boolean _monitorPortletResourceRequest;

}