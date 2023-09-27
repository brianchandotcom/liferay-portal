/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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