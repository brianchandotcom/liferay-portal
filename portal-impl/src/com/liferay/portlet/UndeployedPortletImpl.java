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

package com.liferay.portlet;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.util.PortalUtil;

import java.io.IOException;
import java.io.PrintWriter;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.GenericPortlet;
import javax.portlet.PortletException;
import javax.portlet.PortletRequestDispatcher;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Tomas Polesovsky
 */
public class UndeployedPortletImpl extends GenericPortlet {

	@Override
	public void processAction(ActionRequest request, ActionResponse response) {
	}

	@Override
	public void render(RenderRequest request, RenderResponse response)
		throws IOException, PortletException {

		PortletRequestDispatcher portletRequestDispatcher =
			getPortletContext().getRequestDispatcher(_JSP);

		portletRequestDispatcher.include(request, response);
	}

	@Override
	public void serveResource(
			ResourceRequest request, ResourceResponse response)
		throws IOException, PortletException {

		HttpServletRequest httpServletRequest =
			PortalUtil.getHttpServletRequest(request);

		PrintWriter printWriter = response.getWriter();

		printWriter.write(LanguageUtil.get(httpServletRequest, "undeployed"));
	}

	private static final String _JSP = "/html/portal/undeployed_portlet.jsp";

}