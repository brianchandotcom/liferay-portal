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

import java.io.IOException;
import java.io.PrintWriter;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.GenericPortlet;
import javax.portlet.PortletContext;
import javax.portlet.PortletException;
import javax.portlet.PortletRequestDispatcher;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

/**
 * @author Tomas Polesovsky
 */
public class UndeployedPortlet extends GenericPortlet {

	public static UndeployedPortlet getInstance() {
		return _undeployedPortlet;
	}

	@Override
	public void processAction(
		ActionRequest actionRequest, ActionResponse actionResponse) {
	}

	@Override
	public void render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		PortletContext portletContext = getPortletContext();

		PortletRequestDispatcher portletRequestDispatcher =
			portletContext.getRequestDispatcher(
				"/html/portal/undeployed_portlet.jsp");

		portletRequestDispatcher.include(renderRequest, renderResponse);
	}

	@Override
	public void serveResource(
			ResourceRequest renderRequest, ResourceResponse renderResponse)
		throws IOException {

		PrintWriter printWriter = renderResponse.getWriter();

		printWriter.write(
			LanguageUtil.get(renderRequest.getLocale(), "undeployed"));
	}

	private static final UndeployedPortlet _undeployedPortlet =
		new UndeployedPortlet();

}