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

package com.liferay.portal.apache.bridges.struts;

import com.liferay.portal.kernel.servlet.ServletContextProvider;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.PortletContextImpl;

import javax.portlet.GenericPortlet;
import javax.portlet.PortletContext;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author James Schopp
 * @author Michael Young
 * @author Deepak Gothe
 */
public class LiferayServletContextProvider implements ServletContextProvider {

	public HttpServletRequest getHttpServletRequest(
		GenericPortlet portlet, PortletRequest portletRequest) {

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			portletRequest);

		return new LiferayStrutsRequestImpl(request);
	}

	public HttpServletResponse getHttpServletResponse(
		GenericPortlet portlet, PortletResponse portletResponse) {

		return PortalUtil.getHttpServletResponse(portletResponse);
	}

	public ServletContext getServletContext(GenericPortlet portlet) {
		PortletContext portletContext = portlet.getPortletContext();

		ServletContext servletContext =
			(ServletContext)portletContext.getAttribute(
				JavaConstants.JAVAX_PORTLET_SERVLET_CONTEXT);

		if (servletContext == null) {
			PortletContextImpl portletContextImpl =
				(PortletContextImpl)portlet.getPortletContext();

			servletContext = portletContextImpl.getServletContext();
		}

		return getServletContext(servletContext);
	}

	public ServletContext getServletContext(ServletContext servletContext) {
		return new LiferayServletContext(servletContext);
	}

}