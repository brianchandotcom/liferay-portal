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

package com.liferay.portal.action;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.portlet.WindowStateFactory;
import com.liferay.portal.kernel.security.auth.AuthTokenUtil;
import com.liferay.portal.kernel.security.auth.session.AuthenticatedSessionManagerUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.security.sso.SSOUtil;
import com.liferay.portal.struts.Action;
import com.liferay.portal.struts.model.ActionForward;
import com.liferay.portal.struts.model.ActionMapping;
import com.liferay.portal.util.PropsValues;

import javax.portlet.PortletMode;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.WindowState;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author Brian Wing Shun Chan
 * @author Scott Lee
 */
public class LoginAction implements Action {

	@Override
	public ActionForward execute(
			ActionMapping actionMapping, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		if (PropsValues.AUTH_LOGIN_DISABLED) {
			httpServletResponse.sendRedirect(
				themeDisplay.getPathMain() +
					PropsValues.AUTH_LOGIN_DISABLED_PATH);

			return null;
		}

		if (PropsValues.COMPANY_SECURITY_AUTH_REQUIRES_HTTPS &&
			!httpServletRequest.isSecure()) {

			StringBundler sb = new StringBundler(4);

			sb.append(PortalUtil.getPortalURL(httpServletRequest, true));
			sb.append(httpServletRequest.getRequestURI());
			sb.append(StringPool.QUESTION);
			sb.append(httpServletRequest.getQueryString());

			httpServletResponse.sendRedirect(sb.toString());

			return null;
		}

		String login = ParamUtil.getString(httpServletRequest, "login");
		String password = httpServletRequest.getParameter("password");

		if (Validator.isNotNull(login) && Validator.isNotNull(password)) {
			AuthTokenUtil.checkCSRFToken(
				httpServletRequest, LoginAction.class.getName());

			boolean rememberMe = ParamUtil.getBoolean(
				httpServletRequest, "rememberMe");
			String authType = ParamUtil.getString(
				httpServletRequest, "authType");

			AuthenticatedSessionManagerUtil.login(
				httpServletRequest, httpServletResponse, login, password,
				rememberMe, authType);
		}

		HttpSession session = httpServletRequest.getSession();

		if ((session.getAttribute("j_username") != null) &&
			(session.getAttribute("j_password") != null)) {

			if (PropsValues.PORTAL_JAAS_ENABLE) {
				return actionMapping.getActionForward(
					"/portal/touch_protected.jsp");
			}

			String redirect = ParamUtil.getString(
				httpServletRequest, "redirect");

			redirect = PortalUtil.escapeRedirect(redirect);

			if (Validator.isNull(redirect)) {
				redirect = themeDisplay.getPathMain();
			}

			if (redirect.charAt(0) == CharPool.SLASH) {
				String portalURL = PortalUtil.getPortalURL(
					httpServletRequest, httpServletRequest.isSecure());

				if (Validator.isNotNull(portalURL)) {
					redirect = portalURL.concat(redirect);
				}
			}

			httpServletResponse.sendRedirect(redirect);

			return null;
		}

		String redirect = PortalUtil.getSiteLoginURL(themeDisplay);

		if (Validator.isNull(redirect)) {
			redirect = PropsValues.AUTH_LOGIN_URL;
		}

		if (Validator.isNull(redirect)) {
			PortletURL portletURL = PortletURLFactoryUtil.create(
				httpServletRequest, PortletKeys.LOGIN,
				PortletRequest.RENDER_PHASE);

			portletURL.setParameter("saveLastPath", Boolean.FALSE.toString());
			portletURL.setParameter("mvcRenderCommandName", "/login/login");
			portletURL.setPortletMode(PortletMode.VIEW);
			portletURL.setWindowState(getWindowState(httpServletRequest));

			redirect = portletURL.toString();
		}

		if (PropsValues.COMPANY_SECURITY_AUTH_REQUIRES_HTTPS) {
			String portalURL = PortalUtil.getPortalURL(httpServletRequest);
			String portalURLSecure = PortalUtil.getPortalURL(
				httpServletRequest, true);

			if (!portalURL.equals(portalURLSecure)) {
				redirect = StringUtil.replaceFirst(
					redirect, portalURL, portalURLSecure);
			}
		}

		String loginRedirect = ParamUtil.getString(
			httpServletRequest, "redirect");

		loginRedirect = PortalUtil.escapeRedirect(loginRedirect);

		if (Validator.isNotNull(loginRedirect)) {
			if (SSOUtil.isRedirectRequired(themeDisplay.getCompanyId())) {
				redirect = loginRedirect;
			}
			else {
				String loginPortletNamespace = PortalUtil.getPortletNamespace(
					PropsValues.AUTH_LOGIN_PORTLET_NAME);

				String loginRedirectParameter =
					loginPortletNamespace + "redirect";

				redirect = HttpUtil.setParameter(
					redirect, "p_p_id", PropsValues.AUTH_LOGIN_PORTLET_NAME);
				redirect = HttpUtil.setParameter(
					redirect, "p_p_lifecycle", "0");
				redirect = HttpUtil.setParameter(
					redirect, loginRedirectParameter, loginRedirect);
			}
		}

		httpServletResponse.sendRedirect(redirect);

		return null;
	}

	protected WindowState getWindowState(
		HttpServletRequest httpServletRequest) {

		WindowState windowState = WindowState.MAXIMIZED;

		String windowStateString = ParamUtil.getString(
			httpServletRequest, "windowState");

		if (Validator.isNotNull(windowStateString)) {
			windowState = WindowStateFactory.getWindowState(windowStateString);
		}

		return windowState;
	}

}