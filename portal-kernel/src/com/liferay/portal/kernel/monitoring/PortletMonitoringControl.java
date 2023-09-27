/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.monitoring;

/**
 * @author Michael C. Han
 */
public interface PortletMonitoringControl {

	public boolean isMonitorPortletActionRequest();

	public boolean isMonitorPortletEventRequest();

	public boolean isMonitorPortletHeaderRequest();

	public boolean isMonitorPortletRenderRequest();

	public boolean isMonitorPortletResourceRequest();

	public void setMonitorPortletActionRequest(
		boolean monitoringPortletActionRequest);

	public void setMonitorPortletEventRequest(
		boolean monitoringPortletEventRequest);

	public void setMonitorPortletHeaderRequest(
		boolean monitoringPortletHeaderRequest);

	public void setMonitorPortletRenderRequest(
		boolean monitoringPortletRenderRequest);

	public void setMonitorPortletResourceRequest(
		boolean monitoringPortletResourceRequest);

}