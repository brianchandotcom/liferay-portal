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

package com.liferay.portal.monitoring.internal.jmx;

import com.liferay.portal.kernel.monitoring.PortalMonitoringControl;
import com.liferay.portal.kernel.monitoring.PortletMonitoringControl;
import com.liferay.portal.kernel.monitoring.ServiceMonitoringControl;

/**
 * @author Michael C. Han
 * @author Brian Wing Shun Chan
 */
public interface MonitoringConfigurationManagerMBean
	extends PortalMonitoringControl, PortletMonitoringControl,
			ServiceMonitoringControl {

	public String getLevel(String namespace);

	public String[] getNamespaces();

	@Override
	public boolean isMonitorPortalRequest();

	public void setLevel(String namespace, String levelName);

	@Override
	public void setMonitorPortalRequest(boolean monitorPortalRequest);

	public void setMonitorPortletRequests(boolean monitorPortletRequests);

}