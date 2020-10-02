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
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.struts.Definition;
import com.liferay.portal.struts.TilesUtil;

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

		httpServletRequest.setAttribute(
			TilesUtil.DEFINITION,
			new Definition(
				StringPool.BLANK,
				HashMapBuilder.put(
					"content", path
				).put(
					"pop_up", String.valueOf(popUp)
				).put(
					"title", title
				).build()));

		RequestDispatcher requestDispatcher =
			httpServletRequest.getRequestDispatcher(
				_PATH_HTML_COMMON_THEMES_PORTAL);

		if (popUp) {
			requestDispatcher.include(httpServletRequest, httpServletResponse);

			return;
		}

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		boolean stateMaximized = themeDisplay.isStateMaximized();

		themeDisplay.setStateMaximized(true);

		try {
			requestDispatcher.include(httpServletRequest, httpServletResponse);
		}
		finally {
			themeDisplay.setStateMaximized(stateMaximized);
		}
	}

	private static final String _PATH_HTML_COMMON_THEMES_PORTAL =
		"/html/common/themes/portal.jsp";

}