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

import com.liferay.petra.string.StringPool;
import com.liferay.portal.struts.Definition;
import com.liferay.portal.struts.TilesUtil;

import java.util.HashMap;
import java.util.Map;

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
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, String path, String title)
		throws Exception {

		dispatch(httpServletRequest, httpServletResponse, path, title, false);
	}

	public static void dispatch(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, String path, String title,
			boolean popUp)
		throws Exception {

		Map<String, String> attributes = new HashMap<>();

		attributes.put("content", path);
		attributes.put("pop_up", String.valueOf(popUp));
		attributes.put("title", title);

		httpServletRequest.setAttribute(
			TilesUtil.DEFINITION, new Definition(StringPool.BLANK, attributes));

		RequestDispatcher requestDispatcher =
			httpServletRequest.getRequestDispatcher(
				_PATH_HTML_COMMON_THEMES_PORTAL);

		requestDispatcher.include(httpServletRequest, httpServletResponse);
	}

	private static final String _PATH_HTML_COMMON_THEMES_PORTAL =
		"/html/common/themes/portal.jsp";

}