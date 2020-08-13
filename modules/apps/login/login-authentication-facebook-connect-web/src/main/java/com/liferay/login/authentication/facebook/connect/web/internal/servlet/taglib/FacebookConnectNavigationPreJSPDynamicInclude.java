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

package com.liferay.login.authentication.facebook.connect.web.internal.servlet.taglib;

import com.liferay.portal.kernel.facebook.FacebookConnect;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.taglib.BaseJSPDynamicInclude;
import com.liferay.portal.kernel.servlet.taglib.DynamicInclude;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.security.sso.facebook.connect.constants.FacebookConnectWebKeys;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * When Liferay's Sign In portlet is requested, this class adds a Facebook link
 * to the portlet if Facebook Connect authentication has been enabled for the
 * portal instance being accessed.
 *
 * @author Michael C. Han
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 */
@Component(immediate = true, service = DynamicInclude.class)
@Deprecated
public class FacebookConnectNavigationPreJSPDynamicInclude
	extends BaseJSPDynamicInclude {

	@Override
	public void include(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, String key)
		throws IOException {

		String strutsAction = ParamUtil.getString(
			httpServletRequest, "struts_action");

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		if (strutsAction.startsWith("/login/facebook_connect") ||
			!_facebookConnect.isEnabled(themeDisplay.getCompanyId())) {

			return;
		}

		String facebookAuthRedirectURL = _facebookConnect.getRedirectURL(
			themeDisplay.getCompanyId());

		httpServletRequest.setAttribute(
			FacebookConnectWebKeys.FACEBOOK_AUTH_REDIRECT_URL,
			facebookAuthRedirectURL);

		String facebookAuthURL = _facebookConnect.getAuthURL(
			themeDisplay.getCompanyId());

		httpServletRequest.setAttribute(
			FacebookConnectWebKeys.FACEBOOK_AUTH_URL, facebookAuthURL);

		String facebookAppId = _facebookConnect.getAppId(
			themeDisplay.getCompanyId());

		httpServletRequest.setAttribute(
			FacebookConnectWebKeys.FACEBOOK_APP_ID, facebookAppId);

		super.include(httpServletRequest, httpServletResponse, key);
	}

	@Override
	public void register(
		DynamicInclude.DynamicIncludeRegistry dynamicIncludeRegistry) {

		dynamicIncludeRegistry.register(
			"com.liferay.login.web#/navigation.jsp#pre");
	}

	@Override
	protected String getJspPath() {
		return "/dynamic_include/com.liferay.login.web/navigation/facebook.jsp";
	}

	@Override
	protected Log getLog() {
		return _log;
	}

	@Reference(unbind = "-")
	protected void setFacebookConnect(FacebookConnect facebookConnect) {
		_facebookConnect = facebookConnect;
	}

	@Override
	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.login.authentication.facebook.connect.web)",
		unbind = "-"
	)
	protected void setServletContext(ServletContext servletContext) {
		super.setServletContext(servletContext);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FacebookConnectNavigationPreJSPDynamicInclude.class);

	private FacebookConnect _facebookConnect;

}