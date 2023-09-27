/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.bean.portlet.spring.extension.internal.mvc;

import javax.annotation.ManagedBean;
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
 * @author Neil Griffin
 */
@ManagedBean
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