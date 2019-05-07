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
 * Provides a skeletal implementation of the {@link PanelCategory} with JSP
 * support to minimize the effort required to implement this interface.
 *
 * <p>
 * To implement a JSP application category, this class should be extended and
 * {@link #getJspPath()} should be implemented, which returns a path for the
 * main JSP application category view in the current servlet context. The
 * servlet context should also be set using {@link
 * #setServletContext(ServletContext)}, which uses the appropriate servlet
 * context for JSP pages. If the servlet context is not set, {@link
 * #include(HttpServletRequest, HttpServletResponse)} will throw a
 * <code>NullPointerException</code>.
 * </p>
 *
 * <p>
 * JSP application categories include JSP applications defined by {@link
 * BaseJSPPanelApp} implementations.
 * </p>
 *
 * @author Eudaldo Alonso
 * @see    BasePanelCategory
 * @see    PanelCategory
 */
public abstract class BaseJSPPanelCategory extends BasePanelCategory {

	public String getHeaderJspPath() {
		return null;
	}

	public abstract String getJspPath();

	@Override
	public boolean include(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException {

		return includeJSP(
			httpServletRequest, httpServletResponse, getJspPath());
	}

	@Override
	public boolean includeHeader(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException {

		return includeJSP(
			httpServletRequest, httpServletResponse, getHeaderJspPath());
	}

	public void setServletContext(ServletContext servletContext) {
		_servletContext = servletContext;
	}

	protected boolean includeJSP(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, String jspPath)
		throws IOException {

		if (Validator.isNull(jspPath)) {
			return false;
		}

		RequestDispatcher requestDispatcher =
			_servletContext.getRequestDispatcher(jspPath);

		try {
			requestDispatcher.include(httpServletRequest, httpServletResponse);
		}
		catch (ServletException se) {
			_log.error("Unable to include " + jspPath, se);

			return false;
		}

		return true;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BaseJSPPanelCategory.class);

	private ServletContext _servletContext;

}