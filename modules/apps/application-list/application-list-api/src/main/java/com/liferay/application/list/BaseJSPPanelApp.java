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

package com.liferay.application.list;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Provides a skeletal implementation of the {@link PanelApp} with JSP support
 * to minimize the effort required to implement this interface.
 *
 * <p>
 * To implement a JSP application, this class should be extended and {@link
 * #getJspPath()} should be implemented, which returns a path for the main JSP
 * application view in the current servlet context.
 * </p>
 *
 * <p>
 * JSP applications are included within JSP application categories defined by
 * {@link BaseJSPPanelCategory} implementations.
 * </p>
 *
 * @author Julio Camarero
 * @see    BasePanelApp
 * @see    PanelApp
 */
public abstract class BaseJSPPanelApp extends BasePanelApp {

	public abstract String getJspPath();

	@Override
	public boolean include(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException {

		String jspPath = getJspPath();

		if (Validator.isNull(jspPath)) {
			return false;
		}

		ServletContext servletContext = getServletContext();

		RequestDispatcher requestDispatcher =
			servletContext.getRequestDispatcher(jspPath);

		try {
			requestDispatcher.include(httpServletRequest, httpServletResponse);
		}
		catch (ServletException servletException) {
			_log.error("Unable to include " + jspPath, servletException);

			return false;
		}

		return true;
	}

	protected abstract ServletContext getServletContext();

	private static final Log _log = LogFactoryUtil.getLog(
		BaseJSPPanelApp.class);

}