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

package com.liferay.portal.struts;

import java.io.IOException;

import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionServlet;
import org.apache.struts.action.RequestProcessor;
import org.apache.struts.config.ForwardConfig;
import org.apache.struts.config.ModuleConfig;
import org.apache.struts.tiles.ComponentContext;
import org.apache.struts.tiles.ComponentDefinition;
import org.apache.struts.tiles.taglib.ComponentConstants;

/**
 * @author Shuyang Zhou
 */
public class TilesRequestProcessor extends RequestProcessor {

	@Override
	public void init(ActionServlet servlet, ModuleConfig moduleConfig)
		throws ServletException {

		super.init(servlet, moduleConfig);

		ServletContext servletContext = getServletContext();

		_componentDefinitions = (Map<String, ComponentDefinition>)
			servletContext.getAttribute(PortalTilesPlugin.DEFINITIONS);
	}

	@Override
	protected void internalModuleRelativeForward(
			String uri, HttpServletRequest request,
			HttpServletResponse response)
		throws IOException, ServletException {

		ComponentDefinition componentDefinition = _componentDefinitions.get(
			uri);

		if (componentDefinition != null) {
			ComponentContext componentContext = new ComponentContext(
				componentDefinition.getAttributes());

			request.setAttribute(
				ComponentConstants.COMPONENT_CONTEXT, componentContext);

			uri = componentDefinition.getPath();
		}

		doForward(uri, request, response);
	}

	@Override
	protected void processForwardConfig(
			HttpServletRequest request, HttpServletResponse response,
			ForwardConfig forward)
		throws IOException, ServletException {

		if (forward == null) {
			return;
		}

		internalModuleRelativeForward(forward.getPath(), request, response);
	}

	private Map<String, ComponentDefinition> _componentDefinitions;

}