/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.monitoring.internal;

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