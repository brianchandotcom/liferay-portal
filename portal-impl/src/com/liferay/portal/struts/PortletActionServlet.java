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

package com.liferay.portal.struts;

import com.liferay.portal.util.WebKeys;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionServlet;
import org.apache.struts.config.ModuleConfig;

/**
 * @author Brian Wing Shun Chan
 */
public class PortletActionServlet extends ActionServlet {

	@Override
	public void init(ServletConfig servletConfig) throws ServletException {
		super.init(servletConfig);

		ServletContext servletContext = getServletContext();

		ModuleConfig moduleConfig = (ModuleConfig)servletContext.getAttribute(
			Globals.MODULE_KEY);

		PortletRequestProcessor portletRequestProcessor =
			PortletRequestProcessor.getInstance(this, moduleConfig);

		servletContext.setAttribute(
			WebKeys.PORTLET_STRUTS_PROCESSOR, portletRequestProcessor);
	}

}