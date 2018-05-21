/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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