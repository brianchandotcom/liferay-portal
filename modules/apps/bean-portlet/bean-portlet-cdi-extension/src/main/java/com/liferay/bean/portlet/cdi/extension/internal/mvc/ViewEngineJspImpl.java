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

package com.liferay.bean.portlet.cdi.extension.internal.mvc;

import javax.annotation.Priority;

import javax.mvc.engine.ViewEngine;
import javax.mvc.engine.ViewEngineContext;
import javax.mvc.engine.ViewEngineException;

import javax.portlet.PortletContext;
import javax.portlet.PortletRequest;
import javax.portlet.PortletRequestDispatcher;
import javax.portlet.PortletResponse;

import javax.ws.rs.core.Configuration;

/**
 * @author  Neil Griffin
 */
@Priority(ViewEngine.PRIORITY_BUILTIN)
public class ViewEngineJspImpl implements ViewEngine {

	public ViewEngineJspImpl(
		Configuration configuration, PortletContext portletContext) {

		_configuration = configuration;
		_portletContext = portletContext;
	}

	@Override
	public void processView(ViewEngineContext viewEngineContext)
		throws ViewEngineException {

		String view = viewEngineContext.getView();

		String viewFolder = (String)_configuration.getProperty(
			ViewEngine.VIEW_FOLDER);

		if (viewFolder == null) {
			viewFolder = ViewEngine.DEFAULT_VIEW_FOLDER;
		}

		String viewPath = viewFolder.concat(view);

		PortletRequestDispatcher requestDispatcher =
			_portletContext.getRequestDispatcher(viewPath);

		try {
			requestDispatcher.include(
				viewEngineContext.getRequest(PortletRequest.class),
				viewEngineContext.getResponse(PortletResponse.class));
		}
		catch (Exception exception) {
			throw new ViewEngineException(exception);
		}
	}

	@Override
	public boolean supports(String view) {
		if ((view != null) &&
			(view.endsWith(".jsp") || view.endsWith(".jspx"))) {

			return true;
		}

		return false;
	}

	private final Configuration _configuration;
	private final PortletContext _portletContext;

}