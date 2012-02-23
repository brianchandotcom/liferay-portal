/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.messaging.sender.SingleDestinationMessageSender;

import javax.portlet.Portlet;
import javax.portlet.PortletConfig;
import javax.portlet.PortletContext;
import javax.portlet.PortletException;

/**
 * @author Shuyang Zhou
 */
public class MonitoringPortletFactoryImpl extends InvokerPortletFactoryImpl {

	public MonitoringPortletFactoryImpl(
		SingleDestinationMessageSender singleDestinationMessageSender) {

		_singleDestinationMessageSender = singleDestinationMessageSender;
	}

	public InvokerPortlet create(
			com.liferay.portal.model.Portlet portletModel, Portlet portlet,
			PortletConfig portletConfig, PortletContext portletContext,
			boolean checkAuthToken, boolean facesPortlet, boolean strutsPortlet,
			boolean strutsBridgePortlet)
		throws PortletException {

		InvokerPortlet invokerPortlet = super.create(
			portletModel, portlet, portletConfig, portletContext,
			checkAuthToken, facesPortlet, strutsPortlet, strutsBridgePortlet);

		return new MonitoringPortlet(
			invokerPortlet, _singleDestinationMessageSender);
	}

	public InvokerPortlet create(
			com.liferay.portal.model.Portlet portletModel, Portlet portlet,
			PortletContext portletContext)
		throws PortletException {

		InvokerPortlet invokerPortlet = super.create(
			portletModel, portlet, portletContext);

		return new MonitoringPortlet(
			invokerPortlet, _singleDestinationMessageSender);
	}

	private SingleDestinationMessageSender _singleDestinationMessageSender;

}