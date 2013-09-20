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
public interface MonitoringPortletManagerMBean {

	public boolean isActive();

	public boolean isMonitoringPortletActionRequest();

	public boolean isMonitoringPortletEventRequest();

	public boolean isMonitoringPortletRenderRequest();

	public boolean isMonitoringPortletResourceRequest();

	public void setActive(boolean active);

	public void setMonitoringPortletActionRequest(
		boolean monitoringPortletActionRequest);

	public void setMonitoringPortletEventRequest(
		boolean monitoringPortletEventRequest);

	public void setMonitoringPortletRenderRequest(
		boolean monitoringPortletRenderRequest);

	public void setMonitoringPortletResourceRequest(
		boolean monitoringPortletResourceRequest);

}