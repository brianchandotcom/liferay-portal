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

	public void afterPropertiesSet() {

		if (MonitoringPortlet.isMonitoringPortletActionRequest() ||
			MonitoringPortlet.isMonitoringPortletEventRequest() ||
			MonitoringPortlet.isMonitoringPortletRenderRequest() ||
			MonitoringPortlet.isMonitoringPortletResourceRequest()) {

			setActive(true);
		}
	}

	@Override
	public boolean isActive() {
		return _active;
	}

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
	public void setActive(boolean active) {
		if (active == _active) {
			return;
		}

		PortletInstanceFactoryImpl portletInstanceFactoryImpl =
			new PortletInstanceFactoryImpl();

		if (active) {
			portletInstanceFactoryImpl.setInvokerPortletFactory(
				_monitoringPortletFactoryImpl);
		}
		else {
			portletInstanceFactoryImpl.setInvokerPortletFactory(
				_invokerPortletFactory);
		}

		PortletInstanceFactoryUtil portletInstanceFactoryUtil =
			new PortletInstanceFactoryUtil();

		portletInstanceFactoryUtil.setPortletInstanceFactory(
			portletInstanceFactoryImpl);

		_active = active;
	}

	public void setInvokerPortletFactory(
		InvokerPortletFactory invokerPortletFactory) {

		_invokerPortletFactory = invokerPortletFactory;
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

	public void setMonitoringPortletFactoryImpl(
		InvokerPortletFactory monitoringPortletFactoryImpl) {

		_monitoringPortletFactoryImpl = monitoringPortletFactoryImpl;
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

	private boolean _active;
	private InvokerPortletFactory _invokerPortletFactory;
	private InvokerPortletFactory _monitoringPortletFactoryImpl;

}