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

import com.liferay.portal.kernel.monitoring.DataSampleFactory;
import com.liferay.portal.kernel.monitoring.PortletMonitoringControl;
import com.liferay.portal.kernel.portlet.InvokerFilterContainer;
import com.liferay.portal.kernel.portlet.InvokerPortlet;
import com.liferay.portal.kernel.portlet.InvokerPortletFactory;

import javax.portlet.Portlet;
import javax.portlet.PortletConfig;
import javax.portlet.PortletContext;
import javax.portlet.PortletException;

import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Shuyang Zhou
 * @author Philip Jones
 * @author Neil Griffin
 */
@Component(
	enabled = false, immediate = true,
	property = Constants.SERVICE_RANKING + ":Integer=100",
	service = InvokerPortletFactory.class
)
public class MonitoringInvokerPortletFactoryImpl
	implements InvokerPortletFactory {

	@Override
	public InvokerPortlet create(
			com.liferay.portal.kernel.model.Portlet portletModel,
			Portlet portlet, PortletConfig portletConfig,
			PortletContext portletContext,
			InvokerFilterContainer invokerFilterContainer,
			boolean checkAuthToken, boolean facesPortlet, boolean headerPortlet)
		throws PortletException {

		InvokerPortlet invokerPortlet = _invokerPortletFactory.create(
			portletModel, portlet, portletConfig, portletContext,
			invokerFilterContainer, checkAuthToken, facesPortlet,
			headerPortlet);

		return new MonitoringInvokerPortlet(
			invokerPortlet, _dataSampleFactory, _portletMonitoringControl);
	}

	@Override
	public InvokerPortlet create(
			com.liferay.portal.kernel.model.Portlet portletModel,
			Portlet portlet, PortletContext portletContext,
			InvokerFilterContainer invokerFilterContainer)
		throws PortletException {

		InvokerPortlet invokerPortlet = _invokerPortletFactory.create(
			portletModel, portlet, portletContext, invokerFilterContainer);

		return new MonitoringInvokerPortlet(
			invokerPortlet, _dataSampleFactory, _portletMonitoringControl);
	}

	@Reference
	private DataSampleFactory _dataSampleFactory;

	@Reference(target = "(" + Constants.SERVICE_RANKING + "=1)")
	private InvokerPortletFactory _invokerPortletFactory;

	@Reference
	private PortletMonitoringControl _portletMonitoringControl;

}