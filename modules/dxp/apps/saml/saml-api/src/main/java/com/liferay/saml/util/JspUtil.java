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

package com.liferay.saml.util;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Mika Koivisto
 */
public class JspUtil {

	public static final String PATH_PORTAL_SAML_ERROR =
		"/portal/saml/error.jsp";

	public static final String PATH_PORTAL_SAML_SLO = "/portal/saml/slo.jsp";

	public static final String PATH_PORTAL_SAML_SLO_SP_STATUS =
		"/portal/saml/slo_sp_status.jsp";

	public static void dispatch(
			HttpServletRequest request, HttpServletResponse response,
			String path, String title)
		throws Exception {

		dispatch(request, response, path, title, false);
	}

	public static void dispatch(
			HttpServletRequest request, HttpServletResponse response,
			String path, String title, boolean popUp)
		throws Exception {

		request.setAttribute("tilesContent", path);
		request.setAttribute("tilesPopUp", String.valueOf(popUp));
		request.setAttribute("tilesTitle", title);

		RequestDispatcher requestDispatcher = request.getRequestDispatcher(
			_PATH_HTML_PORTAL_SAML_SAML_PORTAL);

		requestDispatcher.include(request, response);
	}

	private static final String _PATH_HTML_PORTAL_SAML_SAML_PORTAL =
		"/html/portal/saml/saml_portal.jsp";

}