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

package com.liferay.portal.monitoring.internal.servlet.filter;

import com.liferay.portal.kernel.monitoring.PortalMonitoringControl;

import org.osgi.service.component.annotations.Component;

/**
 * @author Joao Victor Alves
 */
@Component(enabled = false, service = PortalMonitoringControl.class)
public class PortalMonitoringControlImpl implements PortalMonitoringControl {

	@Override
	public boolean isMonitorPortalRequest() {
		return _monitorPortalRequest;
	}

	@Override
	public void setMonitorPortalRequest(boolean monitorPortalRequest) {
		_monitorPortalRequest = monitorPortalRequest;
	}

	private static boolean _monitorPortalRequest;

}