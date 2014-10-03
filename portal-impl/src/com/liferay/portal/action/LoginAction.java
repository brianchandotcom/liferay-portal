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

import com.liferay.portal.kernel.portlet.WindowStateFactory;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.sso.SSOUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.PortletURLFactoryUtil;
import com.liferay.portlet.login.util.LoginUtil;

import javax.portlet.PortletMode;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.WindowState;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Brian Wing Shun Chan
 * @author Scott Lee
 */
public class LoginAction extends Action {

	@Override
	public ActionForward execute(
			ActionMapping actionMapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		if (PropsValues.AUTH_LOGIN_DISABLED) {
			response.sendRedirect(
				themeDisplay.getPathMain() +
					PropsValues.AUTH_LOGIN_DISABLED_PATH);

			return null;
		}

		if (PropsValues.COMPANY_SECURITY_AUTH_REQUIRES_HTTPS &&
			!request.isSecure()) {

			StringBundler sb = new StringBundler(4);

			sb.append(PortalUtil.getPortalURL(request, true));
			sb.append(request.getRequestURI());
			sb.append(StringPool.QUESTION);
			sb.append(request.getQueryString());

			response.sendRedirect(sb.toString());

			return null;
		}

		String login = ParamUtil.getString(request, "login");
		String password = request.getParameter("password");
		boolean rememberMe = ParamUtil.getBoolean(request, "rememberMe");
		String authType = ParamUtil.getString(request, "authType");

		if (Validator.isNotNull(login) && Validator.isNotNull(password)) {
			LoginUtil.login(
				request, response, login, password, rememberMe, authType);
		}

		HttpSession session = request.getSession();

		if ((session.getAttribute("j_username") != null) &&
			(session.getAttribute("j_password") != null)) {

			if (PropsValues.PORTAL_JAAS_ENABLE) {
				return actionMapping.findForward("/portal/touch_protected.jsp");
			}

			String redirect = ParamUtil.getString(request, "redirect");

			redirect = PortalUtil.escapeRedirect(redirect);

			if (Validator.isNull(redirect)) {
				redirect = themeDisplay.getPathMain();
			}

			if (redirect.charAt(0) == CharPool.SLASH) {
				String portalURL = PortalUtil.getPortalURL(
					request, request.isSecure());

				if (Validator.isNotNull(portalURL)) {
					redirect = portalURL.concat(redirect);
				}
			}

			response.sendRedirect(redirect);

			return null;
		}

		String redirect = PortalUtil.getSiteLoginURL(themeDisplay);

		if (Validator.isNull(redirect)) {
			redirect = PropsValues.AUTH_LOGIN_URL;
		}

		if (Validator.isNull(redirect)) {
			PortletURL portletURL = PortletURLFactoryUtil.create(
				request, PortletKeys.LOGIN, themeDisplay.getPlid(),
				PortletRequest.RENDER_PHASE);

			portletURL.setParameter("saveLastPath", Boolean.FALSE.toString());
			portletURL.setParameter("struts_action", "/login/login");
			portletURL.setPortletMode(PortletMode.VIEW);
			portletURL.setWindowState(getWindowState(request));

			redirect = portletURL.toString();
		}

		if (PropsValues.COMPANY_SECURITY_AUTH_REQUIRES_HTTPS) {
			String portalURL = PortalUtil.getPortalURL(request);
			String portalURLSecure = PortalUtil.getPortalURL(request, true);

			if (!portalURL.equals(portalURLSecure)) {
				redirect = StringUtil.replaceFirst(
					redirect, portalURL, portalURLSecure);
			}
		}

		String loginRedirect = ParamUtil.getString(request, "redirect");

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

		response.sendRedirect(redirect);

		return null;
	}

	protected WindowState getWindowState(HttpServletRequest request) {
		WindowState windowState = WindowState.MAXIMIZED;

		String windowStateString = ParamUtil.getString(request, "windowState");

		if (Validator.isNotNull(windowStateString)) {
			windowState = WindowStateFactory.getWindowState(windowStateString);
		}

		return windowState;
	}

}