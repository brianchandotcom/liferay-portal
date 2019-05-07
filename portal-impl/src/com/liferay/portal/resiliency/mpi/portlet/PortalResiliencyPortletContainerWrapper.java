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
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.portlet.ActionResult;
import com.liferay.portal.kernel.portlet.PortletContainer;
import com.liferay.portal.kernel.portlet.PortletContainerException;
import com.liferay.portal.kernel.resiliency.PortalResiliencyException;
import com.liferay.portal.kernel.resiliency.spi.SPI;
import com.liferay.portal.kernel.resiliency.spi.SPIRegistryUtil;
import com.liferay.portal.kernel.resiliency.spi.agent.SPIAgent;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.util.PropsValues;

import java.util.Collections;
import java.util.List;

import javax.portlet.Event;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Shuyang Zhou
 * @author Neil Griffin
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
	public void preparePortlet(
			HttpServletRequest httpServletRequest, Portlet portlet)
		throws PortletContainerException {

		_portletContainer.preparePortlet(httpServletRequest, portlet);
	}

	@Override
	public ActionResult processAction(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, Portlet portlet)
		throws PortletContainerException {

		SPIAgent spiAgent = getSPIAgentForPortlet(portlet);

		if (spiAgent == null) {
			return _portletContainer.processAction(
				httpServletRequest, httpServletResponse, portlet);
		}

		Object[] requestAttributeValues = captureRequestAttibutes(
			httpServletRequest, _ACTION_REQUEST_ATTRIBUTE_NAMES);

		httpServletRequest.setAttribute(
			WebKeys.SPI_AGENT_LIFECYCLE, SPIAgent.Lifecycle.ACTION);
		httpServletRequest.setAttribute(WebKeys.SPI_AGENT_PORTLET, portlet);

		try {
			spiAgent.service(httpServletRequest, httpServletResponse);

			return (ActionResult)httpServletRequest.getAttribute(
				WebKeys.SPI_AGENT_ACTION_RESULT);
		}
		catch (PortalResiliencyException pre) {
			_log.error(pre, pre);

			return ActionResult.EMPTY_ACTION_RESULT;
		}
		finally {
			httpServletRequest.removeAttribute(WebKeys.SPI_AGENT_ACTION_RESULT);

			restoreRequestAttibutes(
				httpServletRequest, _ACTION_REQUEST_ATTRIBUTE_NAMES,
				requestAttributeValues);
		}
	}

	@Override
	public List<Event> processEvent(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, Portlet portlet,
			Layout layout, Event event)
		throws PortletContainerException {

		SPIAgent spiAgent = getSPIAgentForPortlet(portlet);

		if (spiAgent == null) {
			return _portletContainer.processEvent(
				httpServletRequest, httpServletResponse, portlet, layout,
				event);
		}

		Object[] requestAttributeValues = captureRequestAttibutes(
			httpServletRequest, _EVENT_REQUEST_ATTRIBUTE_NAMES);

		httpServletRequest.setAttribute(WebKeys.SPI_AGENT_EVENT, event);
		httpServletRequest.setAttribute(WebKeys.SPI_AGENT_LAYOUT, layout);
		httpServletRequest.setAttribute(
			WebKeys.SPI_AGENT_LIFECYCLE, SPIAgent.Lifecycle.EVENT);
		httpServletRequest.setAttribute(WebKeys.SPI_AGENT_PORTLET, portlet);

		try {
			spiAgent.service(httpServletRequest, httpServletResponse);

			return (List<Event>)httpServletRequest.getAttribute(
				WebKeys.SPI_AGENT_EVENT_RESULT);
		}
		catch (PortalResiliencyException pre) {
			_log.error(pre, pre);

			return Collections.emptyList();
		}
		finally {
			httpServletRequest.removeAttribute(WebKeys.SPI_AGENT_EVENT_RESULT);

			restoreRequestAttibutes(
				httpServletRequest, _EVENT_REQUEST_ATTRIBUTE_NAMES,
				requestAttributeValues);
		}
	}

	@Override
	public void processPublicRenderParameters(
		HttpServletRequest httpServletRequest, Layout layout) {

		_portletContainer.processPublicRenderParameters(
			httpServletRequest, layout);
	}

	@Override
	public void processPublicRenderParameters(
		HttpServletRequest httpServletRequest, Layout layout, Portlet portlet) {

		_portletContainer.processPublicRenderParameters(
			httpServletRequest, layout, portlet);
	}

	@Override
	public void render(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, Portlet portlet)
		throws PortletContainerException {

		_render(
			httpServletRequest, httpServletResponse, portlet,
			() -> _portletContainer.render(
				httpServletRequest, httpServletResponse, portlet));
	}

	@Override
	public void renderHeaders(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, Portlet portlet)
		throws PortletContainerException {

		_render(
			httpServletRequest, httpServletResponse, portlet,
			() -> _portletContainer.renderHeaders(
				httpServletRequest, httpServletResponse, portlet));
	}

	@Override
	public void serveResource(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, Portlet portlet)
		throws PortletContainerException {

		SPIAgent spiAgent = getSPIAgentForPortlet(portlet);

		if (spiAgent == null) {
			_portletContainer.serveResource(
				httpServletRequest, httpServletResponse, portlet);

			return;
		}

		Object[] requestAttributeValues = captureRequestAttibutes(
			httpServletRequest, _RESOURCE_REQUEST_ATTRIBUTE_NAMES);

		httpServletRequest.setAttribute(
			WebKeys.SPI_AGENT_LIFECYCLE, SPIAgent.Lifecycle.RESOURCE);
		httpServletRequest.setAttribute(WebKeys.SPI_AGENT_PORTLET, portlet);

		try {
			spiAgent.service(httpServletRequest, httpServletResponse);
		}
		catch (PortalResiliencyException pre) {
			_log.error(pre, pre);
		}
		finally {
			restoreRequestAttibutes(
				httpServletRequest, _RESOURCE_REQUEST_ATTRIBUTE_NAMES,
				requestAttributeValues);
		}
	}

	protected Object[] captureRequestAttibutes(
		HttpServletRequest httpServletRequest, String... names) {

		Object[] values = new Object[names.length];

		for (int i = 0; i < names.length; i++) {
			values[i] = httpServletRequest.getAttribute(names[i]);
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
					StringBundler.concat(
						"Portlet ", String.valueOf(portlet),
						" is registered to SPI ", String.valueOf(spi)));
			}

			return spi.getSPIAgent();
		}
		catch (Exception e) {
			throw new PortletContainerException(e);
		}
	}

	protected void restoreRequestAttibutes(
		HttpServletRequest httpServletRequest, String[] names,
		Object[] values) {

		for (int i = 0; i < names.length; i++) {
			String name = names[i];
			Object value = values[i];

			if (value == null) {
				httpServletRequest.removeAttribute(name);
			}
			else {
				httpServletRequest.setAttribute(name, value);
			}
		}
	}

	private void _render(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, Portlet portlet,
			Renderable renderable)
		throws PortletContainerException {

		SPIAgent spiAgent = getSPIAgentForPortlet(portlet);

		if (spiAgent == null) {
			renderable.render();

			return;
		}

		Object[] requestAttributeValues = captureRequestAttibutes(
			httpServletRequest, _RENDER_REQUEST_ATTRIBUTE_NAMES);

		httpServletRequest.setAttribute(
			WebKeys.SPI_AGENT_LIFECYCLE, SPIAgent.Lifecycle.RENDER);
		httpServletRequest.setAttribute(WebKeys.SPI_AGENT_PORTLET, portlet);

		try {
			spiAgent.service(httpServletRequest, httpServletResponse);
		}
		catch (PortalResiliencyException pre) {
			_log.error(pre, pre);
		}
		finally {
			restoreRequestAttibutes(
				httpServletRequest, _RENDER_REQUEST_ATTRIBUTE_NAMES,
				requestAttributeValues);
		}
	}

	private static final String[] _ACTION_REQUEST_ATTRIBUTE_NAMES = {
		WebKeys.SPI_AGENT_LIFECYCLE, WebKeys.SPI_AGENT_PORTLET
	};

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

	@FunctionalInterface
	private interface Renderable {

		public void render() throws PortletContainerException;

	}

}