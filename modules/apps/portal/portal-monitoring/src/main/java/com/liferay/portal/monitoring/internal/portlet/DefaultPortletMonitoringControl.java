/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.monitoring.internal.portlet;

import com.liferay.portal.kernel.monitoring.PortletMonitoringControl;

import org.osgi.service.component.annotations.Component;

/**
 * @author Michael C. Han
 * @author Philip Jones
 */
@Component(enabled = false, service = PortletMonitoringControl.class)
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