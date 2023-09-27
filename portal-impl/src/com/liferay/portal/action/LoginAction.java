/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.action;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.portlet.WindowStateFactory;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.security.auth.AuthTokenUtil;
import com.liferay.portal.kernel.security.auth.session.AuthenticatedSessionManagerUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HttpComponentsUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.PortletKeys;
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

			httpServletResponse.sendRedirect(
				StringBundler.concat(
					PortalUtil.getPortalURL(httpServletRequest, true),
					httpServletRequest.getRequestURI(), StringPool.QUESTION,
					httpServletRequest.getQueryString()));

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

		HttpSession httpSession = httpServletRequest.getSession();

		if ((httpSession.getAttribute("j_username") != null) &&
			(httpSession.getAttribute("j_password") != null)) {

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
			redirect = PortletURLBuilder.create(
				PortletURLFactoryUtil.create(
					httpServletRequest, PortletKeys.LOGIN,
					PortletRequest.RENDER_PHASE)
			).setMVCRenderCommandName(
				"/login/login"
			).setParameter(
				"saveLastPath", false
			).setPortletMode(
				PortletMode.VIEW
			).setWindowState(
				getWindowState(httpServletRequest)
			).buildString();
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

				redirect = HttpComponentsUtil.setParameter(
					redirect, "p_p_id", PropsValues.AUTH_LOGIN_PORTLET_NAME);
				redirect = HttpComponentsUtil.setParameter(
					redirect, "p_p_lifecycle", "0");
				redirect = HttpComponentsUtil.setParameter(
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