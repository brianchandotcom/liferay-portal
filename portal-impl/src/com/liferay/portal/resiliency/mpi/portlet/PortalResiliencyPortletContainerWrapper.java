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

package com.liferay.portal.resiliency.mpi.portlet;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.ActionResult;
import com.liferay.portal.kernel.portlet.PortletContainer;
import com.liferay.portal.kernel.portlet.PortletContainerException;
import com.liferay.portal.kernel.resiliency.PortalResiliencyException;
import com.liferay.portal.kernel.resiliency.spi.SPI;
import com.liferay.portal.kernel.resiliency.spi.SPIRegistryUtil;
import com.liferay.portal.kernel.resiliency.spi.agent.SPIAgent;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.util.PropsValues;

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

	@Override
	public void preparePortlet(HttpServletRequest request, Portlet portlet)
		throws PortletContainerException {

		_portletContainer.preparePortlet(request, portlet);
	}

	@Override
	public ActionResult processAction(
			HttpServletRequest request, HttpServletResponse response,
			Portlet portlet)
		throws PortletContainerException {

		SPIAgent spiAgent = getSPIAgentForPortlet(portlet);

		if (spiAgent == null) {
			return _portletContainer.processAction(request, response, portlet);
		}

		Object[] requestAttributeValues = captureRequestAttibutes(
			request, _ACTION_REQUEST_ATTRIBUTE_NAMES);

		request.setAttribute(
			WebKeys.SPI_AGENT_LIFECYCLE, SPIAgent.Lifecycle.ACTION);
		request.setAttribute(WebKeys.SPI_AGENT_PORTLET, portlet);

		try {
			spiAgent.service(request, response);

			return (ActionResult)request.getAttribute(
				WebKeys.SPI_AGENT_ACTION_RESULT);
		}
		catch (PortalResiliencyException pre) {
			_log.error(pre, pre);

			return ActionResult.EMPTY_ACTION_RESULT;
		}
		finally {
			request.removeAttribute(WebKeys.SPI_AGENT_ACTION_RESULT);

			restoreRequestAttibutes(
				request, _ACTION_REQUEST_ATTRIBUTE_NAMES,
				requestAttributeValues);
		}
	}

	@Override
	public List<Event> processEvent(
			HttpServletRequest request, HttpServletResponse response,
			Portlet portlet, Layout layout, Event event)
		throws PortletContainerException {

		SPIAgent spiAgent = getSPIAgentForPortlet(portlet);

		if (spiAgent == null) {
			return _portletContainer.processEvent(
				request, response, portlet, layout, event);
		}

		Object[] requestAttributeValues = captureRequestAttibutes(
			request, _EVENT_REQUEST_ATTRIBUTE_NAMES);

		request.setAttribute(WebKeys.SPI_AGENT_EVENT, event);
		request.setAttribute(WebKeys.SPI_AGENT_LAYOUT, layout);
		request.setAttribute(
			WebKeys.SPI_AGENT_LIFECYCLE, SPIAgent.Lifecycle.EVENT);
		request.setAttribute(WebKeys.SPI_AGENT_PORTLET, portlet);

		try {
			spiAgent.service(request, response);

			return (List<Event>)request.getAttribute(
				WebKeys.SPI_AGENT_EVENT_RESULT);
		}
		catch (PortalResiliencyException pre) {
			_log.error(pre, pre);

			return Collections.emptyList();
		}
		finally {
			request.removeAttribute(WebKeys.SPI_AGENT_EVENT_RESULT);

			restoreRequestAttibutes(
				request, _EVENT_REQUEST_ATTRIBUTE_NAMES,
				requestAttributeValues);
		}
	}

	@Override
	public void render(
			HttpServletRequest request, HttpServletResponse response,
			Portlet portlet)
		throws PortletContainerException {

		SPIAgent spiAgent = getSPIAgentForPortlet(portlet);

		if (spiAgent == null) {
			_portletContainer.render(request, response, portlet);

			return;
		}

		Object[] requestAttributeValues = captureRequestAttibutes(
			request, _RENDER_REQUEST_ATTRIBUTE_NAMES);

		request.setAttribute(
			WebKeys.SPI_AGENT_LIFECYCLE, SPIAgent.Lifecycle.RENDER);
		request.setAttribute(WebKeys.SPI_AGENT_PORTLET, portlet);

		try {
			spiAgent.service(request, response);
		}
		catch (PortalResiliencyException pre) {
			_log.error(pre, pre);
		}
		finally {
			restoreRequestAttibutes(
				request, _RENDER_REQUEST_ATTRIBUTE_NAMES,
				requestAttributeValues);
		}
	}

	@Override
	public void serveResource(
			HttpServletRequest request, HttpServletResponse response,
			Portlet portlet)
		throws PortletContainerException {

		SPIAgent spiAgent = getSPIAgentForPortlet(portlet);

		if (spiAgent == null) {
			_portletContainer.serveResource(request, response, portlet);

			return;
		}

		Object[] requestAttributeValues = captureRequestAttibutes(
			request, _RESOURCE_REQUEST_ATTRIBUTE_NAMES);

		request.setAttribute(
			WebKeys.SPI_AGENT_LIFECYCLE, SPIAgent.Lifecycle.RESOURCE);
		request.setAttribute(WebKeys.SPI_AGENT_PORTLET, portlet);

		try {
			spiAgent.service(request, response);
		}
		catch (PortalResiliencyException pre) {
			_log.error(pre, pre);
		}
		finally {
			restoreRequestAttibutes(
				request, _RESOURCE_REQUEST_ATTRIBUTE_NAMES,
				requestAttributeValues);
		}
	}

	protected Object[] captureRequestAttibutes(
		HttpServletRequest request, String... names) {

		Object[] values = new Object[names.length];

		for (int i = 0; i < names.length; i++) {
			values[i] = request.getAttribute(names[i]);
		}

		return values;
	}

	protected SPIAgent getSPIAgentForPortlet(Portlet portlet)
		throws PortletContainerException {

		try {
			SPI spi = SPIRegistryUtil.getPortletSPI(portlet.getRootPortletId());

			if (spi == null) {
				return null;
			}

			if (_log.isDebugEnabled()) {
				_log.debug(
					"Portlet " + portlet + " is registered to SPI " + spi);
			}

			return spi.getSPIAgent();
		}
		catch (Exception e) {
			throw new PortletContainerException(e);
		}
	}

	protected void restoreRequestAttibutes(
		HttpServletRequest request, String[] names, Object[] values) {

		for (int i = 0; i < names.length; i++) {
			String name = names[i];
			Object value = values[i];

			if (value == null) {
				request.removeAttribute(name);
			}
			else {
				request.setAttribute(name, value);
			}
		}
	}

	private static final String[] _ACTION_REQUEST_ATTRIBUTE_NAMES =
		{WebKeys.SPI_AGENT_LIFECYCLE, WebKeys.SPI_AGENT_PORTLET};

	private static final String[] _EVENT_REQUEST_ATTRIBUTE_NAMES = {
		WebKeys.SPI_AGENT_EVENT, WebKeys.SPI_AGENT_LAYOUT,
		WebKeys.SPI_AGENT_LIFECYCLE, WebKeys.SPI_AGENT_PORTLET
	};

	private static final String[] _RENDER_REQUEST_ATTRIBUTE_NAMES =
		_ACTION_REQUEST_ATTRIBUTE_NAMES;

	private static final String[] _RESOURCE_REQUEST_ATTRIBUTE_NAMES =
		_ACTION_REQUEST_ATTRIBUTE_NAMES;

	private static final Log _log = LogFactoryUtil.getLog(
		PortalResiliencyPortletContainerWrapper.class);

	private final PortletContainer _portletContainer;

}