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

package com.liferay.portal.security.sso.facebook.connect.servlet.taglib;

import com.liferay.portal.kernel.facebook.FacebookConnect;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.taglib.BaseDynamicInclude;
import com.liferay.portal.kernel.servlet.taglib.DynamicInclude;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.security.sso.facebook.connect.constants.FacebookConnectWebKeys;
import com.liferay.portal.theme.ThemeDisplay;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(immediate = true, service = DynamicInclude.class)
public class FacebookConnectNavigationPreDynamicInclude
	extends BaseDynamicInclude {

	@Override
	public void include(
			HttpServletRequest request, HttpServletResponse response,
			String key)
		throws IOException {

		String strutsAction = ParamUtil.getString(request, "struts_action");

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		if (strutsAction.startsWith("/login/facebook_connect") ||
			!_facebookConnect.isEnabled(themeDisplay.getCompanyId())) {

			return;
		}

		String facebookAuthRedirectURL = _facebookConnect.getRedirectURL(
			themeDisplay.getCompanyId());

		request.setAttribute(
			FacebookConnectWebKeys.FACEBOOK_AUTH_REDIRECT_URL,
			facebookAuthRedirectURL);

		String facebookAuthURL = _facebookConnect.getAuthURL(
			themeDisplay.getCompanyId());

		request.setAttribute(
			FacebookConnectWebKeys.FACEBOOK_AUTH_URL, facebookAuthURL);

		String facebookAppId = _facebookConnect.getAppId(
			themeDisplay.getCompanyId());

		request.setAttribute(
			FacebookConnectWebKeys.FACEBOOK_APP_ID, facebookAppId);

		RequestDispatcher requestDispatcher =
			_servletContext.getRequestDispatcher(_JSP_PATH);

		try {
			requestDispatcher.include(request, response);
		}
		catch (ServletException se) {
			_log.error("Unable to include JSP " + _JSP_PATH, se);

			throw new IOException("Unable to include JSP " + _JSP_PATH, se);
		}
	}

	@Override
	public void register(
		DynamicInclude.DynamicIncludeRegistry dynamicIncludeRegistry) {

		dynamicIncludeRegistry.register(
			"/html/portlet/login/navigation.jsp#pre");
	}

	@Reference(unbind = "-")
	protected void setFacebookConnect(FacebookConnect facebookConnect) {
		_facebookConnect = facebookConnect;
	}

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.portal.security.sso.facebook.connect)",
		unbind = "-"
	)
	protected void setServletContext(ServletContext servletContext) {
		_servletContext = servletContext;
	}

	private static final String _JSP_PATH =
		"/html/portlet/login/navigation/facebook.jsp";

	private static final Log _log = LogFactoryUtil.getLog(
		FacebookConnectNavigationPreDynamicInclude.class);

	private volatile FacebookConnect _facebookConnect;
	private volatile ServletContext _servletContext;

}