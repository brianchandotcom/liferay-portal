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

package com.liferay.portal.resiliency.mpi.portlet;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.ActionResult;
import com.liferay.portal.kernel.portlet.PortletContainer;
import com.liferay.portal.kernel.portlet.PortletContainerException;
import com.liferay.portal.kernel.resiliency.PortalResiliencyException;
import com.liferay.portal.kernel.resiliency.spi.SPI;
import com.liferay.portal.kernel.resiliency.spi.SPIRuntimeMappingUtil;
import com.liferay.portal.kernel.resiliency.spi.agent.SPIAgent;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.util.PropsValues;

import java.rmi.RemoteException;

import java.util.Collections;
import java.util.List;

import javax.portlet.Event;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Shuyang Zhou
 */
public class PortalResiliencyPortletContainerWrapper
	implements PortletContainer {

	public static PortletContainer
		createPortalResiliencyPortletContainerWrapper(
			PortletContainer portletContainer) {

		if (PropsValues.PORTAL_RESILIENCY_ENABLED) {
			portletContainer = new PortalResiliencyPortletContainerWrapper(
				portletContainer);
		}

		return portletContainer;
	}

	public PortalResiliencyPortletContainerWrapper(
		PortletContainer portletContainer) {

		_portletContainer = portletContainer;
	}

	public void preparePortlet(HttpServletRequest request, Portlet portlet)
		throws PortletContainerException {

		_portletContainer.preparePortlet(request, portlet);
	}

	public ActionResult processAction(
			HttpServletRequest request, HttpServletResponse response,
			Portlet portlet)
		throws PortletContainerException {

		SPIAgent spiAgent = _getSPIAgentForPortlet(portlet);

		if (spiAgent == null) {
			return _portletContainer.processAction(request, response, portlet);
		}
		else {
			request.setAttribute(SPIAgent.LIFECYCLE, SPIAgent.Lifecycle.Action);
			request.setAttribute(SPIAgent.PORTLET, portlet);

			try {
				spiAgent.service(request, response);

				return (ActionResult)request.getAttribute(
					SPIAgent.ACTION_RESULT);
			}
			catch (PortalResiliencyException pre) {
				_log.error(pre, pre);

				return ActionResult.EMPTY_ACTION_RESULT;
			}
			finally {
				request.removeAttribute(SPIAgent.LIFECYCLE);
				request.removeAttribute(SPIAgent.PORTLET);
				request.removeAttribute(SPIAgent.ACTION_RESULT);
			}
		}
	}

	public List<Event> processEvent(
			HttpServletRequest request, HttpServletResponse response,
			Portlet portlet, Layout layout, Event event)
		throws PortletContainerException {

		SPIAgent spiAgent = _getSPIAgentForPortlet(portlet);

		if (spiAgent == null) {
			return _portletContainer.processEvent(
				request, response, portlet, layout, event);
		}
		else {
			request.setAttribute(SPIAgent.LIFECYCLE, SPIAgent.Lifecycle.Event);
			request.setAttribute(SPIAgent.PORTLET, portlet);
			request.setAttribute(SPIAgent.LAYOUT, layout);
			request.setAttribute(SPIAgent.EVENT, event);

			try {
				spiAgent.service(request, response);

				return (List<Event>)request.getAttribute(SPIAgent.EVENT_RESULT);
			}
			catch (PortalResiliencyException pre) {
				_log.error(pre, pre);

				return Collections.emptyList();
			}
			finally {
				request.removeAttribute(SPIAgent.LIFECYCLE);
				request.removeAttribute(SPIAgent.PORTLET);
				request.removeAttribute(SPIAgent.LAYOUT);
				request.removeAttribute(SPIAgent.EVENT);
				request.removeAttribute(SPIAgent.EVENT_RESULT);
			}
		}
	}

	public void render(
			HttpServletRequest request, HttpServletResponse response,
			Portlet portlet)
		throws PortletContainerException {

		SPIAgent spiAgent = _getSPIAgentForPortlet(portlet);

		if (spiAgent == null) {
			_portletContainer.render(request, response, portlet);
		}
		else {
			request.setAttribute(SPIAgent.LIFECYCLE, SPIAgent.Lifecycle.Render);
			request.setAttribute(SPIAgent.PORTLET, portlet);

			try {
				spiAgent.service(request, response);
			}
			catch (PortalResiliencyException pre) {
				_log.error(pre, pre);
			}
			finally {
				request.removeAttribute(SPIAgent.LIFECYCLE);
				request.removeAttribute(SPIAgent.PORTLET);
			}
		}
	}

	public void serveResource(
			HttpServletRequest request, HttpServletResponse response,
			Portlet portlet)
		throws PortletContainerException {

		SPIAgent spiAgent = _getSPIAgentForPortlet(portlet);

		if (spiAgent == null) {
			_portletContainer.serveResource(request, response, portlet);
		}
		else {
			request.setAttribute(
				SPIAgent.LIFECYCLE, SPIAgent.Lifecycle.Resource);
			request.setAttribute(SPIAgent.PORTLET, portlet);

			try {
				spiAgent.service(request, response);
			}
			catch (PortalResiliencyException pre) {
				_log.error(pre, pre);
			}
			finally {
				request.removeAttribute(SPIAgent.LIFECYCLE);
				request.removeAttribute(SPIAgent.PORTLET);
			}
		}
	}

	private SPIAgent _getSPIAgentForPortlet(Portlet portlet)
		throws PortletContainerException {

		SPI spi = SPIRuntimeMappingUtil.getMappingSPIForPortlet(
			portlet.getRootPortletId());

		if (spi != null) {
			if (_log.isDebugEnabled()) {
				_log.debug("Mapping portlet " + portlet + " to SPI " + spi);
			}

			try {
				return spi.getSPIAgent();
			}
			catch (RemoteException re) {
				throw new PortletContainerException(re);
			}
		}

		return null;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PortalResiliencyPortletContainerWrapper.class);

	private PortletContainer _portletContainer;

}