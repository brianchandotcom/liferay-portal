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

package com.liferay.monitoring.web.portlet;

import com.liferay.portal.kernel.monitoring.PortletMonitoringControl;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.InvokerFilterContainer;
import com.liferay.portlet.InvokerPortlet;
import com.liferay.portlet.InvokerPortletFactory;
import com.liferay.portlet.InvokerPortletFactoryImpl;

import javax.portlet.Portlet;
import javax.portlet.PortletConfig;
import javax.portlet.PortletContext;
import javax.portlet.PortletException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Shuyang Zhou
 * @author Philip Jones
 */
@Component(
	immediate = true,
	property = {"javax.portlet.name=" + PortletKeys.MONITORING_INVOKER},
	service = InvokerPortletFactory.class
)
public class MonitoringInvokerPortletFactoryImpl
	extends InvokerPortletFactoryImpl {

	@Override
	public InvokerPortlet create(
			com.liferay.portal.model.Portlet portletModel, Portlet portlet,
			PortletConfig portletConfig, PortletContext portletContext,
			InvokerFilterContainer invokerFilterContainer,
			boolean checkAuthToken, boolean facesPortlet, boolean strutsPortlet,
			boolean strutsBridgePortlet)
		throws PortletException {

		InvokerPortlet invokerPortlet = super.create(
			portletModel, portlet, portletConfig, portletContext,
			invokerFilterContainer, checkAuthToken, facesPortlet, strutsPortlet,
			strutsBridgePortlet);

		return new MonitoringInvokerPortlet(
			invokerPortlet, _portletMonitoringControl);
	}

	@Override
	public InvokerPortlet create(
			com.liferay.portal.model.Portlet portletModel, Portlet portlet,
			PortletContext portletContext,
			InvokerFilterContainer invokerFilterContainer)
		throws PortletException {

		InvokerPortlet invokerPortlet = super.create(
			portletModel, portlet, portletContext, invokerFilterContainer);

		return new MonitoringInvokerPortlet(
			invokerPortlet, _portletMonitoringControl);
	}

	@Reference(unbind = "-")
	public void setPortletMonitoringControl(
		PortletMonitoringControl portletMonitoringControl) {

		_portletMonitoringControl = portletMonitoringControl;
	}

	private volatile PortletMonitoringControl _portletMonitoringControl;

}