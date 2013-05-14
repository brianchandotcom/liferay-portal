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

package com.liferay.portal.kernel.servlet;

import javax.portlet.PortletConfig;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Deepak Gothe
 */
public class PortletServletObjectsFactory implements ServletObjectsFactory {

	@Override
	public ServletConfig getServletConfig(
		PortletConfig portletConfig, PortletRequest portletRequest) {

		Object servletConfig = portletConfig.getPortletContext().getAttribute(
			_PORTLET_CONTAINER_SERVLET_CONFIG);

		if (servletConfig == null) {
			servletConfig = portletRequest.getAttribute(
				PortletServlet.PORTLET_SERVLET_CONFIG);
		}

		return (ServletConfig)servletConfig;
	}

	@Override
	public HttpServletRequest getServletRequest(PortletRequest portletRequest) {
		Object request = portletRequest.getAttribute(
			_PORTLET_CONTAINER_SERVLET_REQUEST);

		if (request == null) {
			request = portletRequest.getAttribute(
				PortletServlet.PORTLET_SERVLET_REQUEST);
		}

		return (HttpServletRequest)request;
	}

	@Override
	public HttpServletResponse getServletResponse(
		PortletRequest portletRequest, PortletResponse portletResponse) {

		Object response = portletRequest.getAttribute(
			_PORTLET_CONTAINER_SERVLET_RESPONSE);

		if (response == null) {
			response = portletRequest.getAttribute(
				PortletServlet.PORTLET_SERVLET_RESPONSE);
		}

		return (HttpServletResponse)response;
	}

	private static final String _PORTLET_CONTAINER_SERVLET_CONFIG =
		"javax.portlet.portletc.servletConfig";

	private static final String _PORTLET_CONTAINER_SERVLET_REQUEST =
		"javax.portlet.portletc.httpServletRequest";

	private static final String _PORTLET_CONTAINER_SERVLET_RESPONSE =
		"javax.portlet.portletc.httpServletResponse";

}