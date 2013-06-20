/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.login.action;

import com.liferay.portal.CompanyMaxUsersException;
import com.liferay.portal.CookieNotSupportedException;
import com.liferay.portal.NoSuchUserException;
import com.liferay.portal.PasswordExpiredException;
import com.liferay.portal.UserEmailAddressException;
import com.liferay.portal.UserIdException;
import com.liferay.portal.UserLockoutException;
import com.liferay.portal.UserPasswordException;
import com.liferay.portal.UserScreenNameException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.auth.AuthException;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portlet.login.util.LoginUtil;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletPreferences;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Brian Wing Shun Chan
 */
public class LoginAction extends PortletAction {

	@Override
	public void processAction(
			ActionMapping actionMapping, ActionForm actionForm,
			PortletConfig portletConfig, ActionRequest actionRequest,
			ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		if (PropsValues.AUTH_LOGIN_DISABLED) {
			actionResponse.sendRedirect(
				themeDisplay.getPathMain() +
					PropsValues.AUTH_LOGIN_DISABLED_PATH);

			return;
		}

		/*if (actionRequest.getRemoteUser() != null) {
			actionResponse.sendRedirect(themeDisplay.getPathMain());

			return;
		}*/

		try {
			PortletPreferences portletPreferences =
				PortletPreferencesFactoryUtil.getPortletSetup(actionRequest);

			login(
				themeDisplay, actionRequest, actionResponse,
				portletPreferences);

			boolean doActionAfterLogin = ParamUtil.getBoolean(
				actionRequest, "doActionAfterLogin");

			if (doActionAfterLogin) {
				setForward(actionRequest, "portlet.login.login_redirect");
			}
		}
		catch (Exception e) {
			if (e instanceof AuthException) {
				Throwable cause = e.getCause();

				if (cause instanceof PasswordExpiredException ||
					cause instanceof UserLockoutException) {

					SessionErrors.add(actionRequest, cause.getClass());
				}
				else {
					if (_log.isInfoEnabled()) {
						_log.info("Authentication failed");
					}

					SessionErrors.add(actionRequest, e.getClass());
				}
			}
			else if (e instanceof CompanyMaxUsersException ||
					 e instanceof CookieNotSupportedException ||
					 e instanceof NoSuchUserException ||
					 e instanceof PasswordExpiredException ||
					 e instanceof UserEmailAddressException ||
					 e instanceof UserIdException ||
					 e instanceof UserLockoutException ||
					 e instanceof UserPasswordException ||
					 e instanceof UserScreenNameException) {

				SessionErrors.add(actionRequest, e.getClass());
			}
			else {
				_log.error(e, e);

				PortalUtil.sendError(e, actionRequest, actionResponse);
			}
		}
	}

	@Override
	public ActionForward render(
			ActionMapping actionMapping, ActionForm actionForm,
			PortletConfig portletConfig, RenderRequest renderRequest,
			RenderResponse renderResponse)
		throws Exception {

		return actionMapping.findForward(
			getForward(renderRequest, "portlet.login.login"));
	}

	protected String getCompleteRedirectURL(
		HttpServletRequest request, String redirect) {

		HttpSession session = request.getSession();

		Boolean httpsInitial = (Boolean)session.getAttribute(
			WebKeys.HTTPS_INITIAL);

		String portalURL = null;

		if (PropsValues.COMPANY_SECURITY_AUTH_REQUIRES_HTTPS &&
			!PropsValues.SESSION_ENABLE_PHISHING_PROTECTION &&
			(httpsInitial != null) && !httpsInitial.booleanValue()) {

			portalURL = PortalUtil.getPortalURL(request, false);
		}
		else {
			portalURL = PortalUtil.getPortalURL(request);
		}

		return portalURL.concat(redirect);
	}

	@Override
	protected boolean isCheckMethodOnProcessAction() {
		return _CHECK_METHOD_ON_PROCESS_ACTION;
	}

	protected void login(
			ThemeDisplay themeDisplay, ActionRequest actionRequest,
			ActionResponse actionResponse,
			PortletPreferences portletPreferences)
		throws Exception {

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			actionRequest);
		HttpServletResponse response = PortalUtil.getHttpServletResponse(
			actionResponse);

		String login = ParamUtil.getString(actionRequest, "login");
		String password = actionRequest.getParameter("password");
		boolean rememberMe = ParamUtil.getBoolean(actionRequest, "rememberMe");

		String authType = portletPreferences.getValue("authType", null);

		if (!themeDisplay.isSignedIn()) {
			LoginUtil.login(
				request, response, login, password, rememberMe, authType);
		}

		if (PropsValues.PORTAL_JAAS_ENABLE) {
			actionResponse.sendRedirect(
				themeDisplay.getPathMain() + "/portal/protected");
		}
		else {
			String redirect = ParamUtil.getString(actionRequest, "redirect");

			if (Validator.isNotNull(redirect)) {
				redirect = PortalUtil.escapeRedirect(redirect);

				if (!redirect.startsWith(Http.HTTP)) {
					redirect = getCompleteRedirectURL(request, redirect);
				}

				actionResponse.sendRedirect(redirect);
			}
			else {
				boolean doActionAfterLogin = ParamUtil.getBoolean(
					actionRequest, "doActionAfterLogin");

				if (doActionAfterLogin) {
					return;
				}
				else {
					actionResponse.sendRedirect(themeDisplay.getPathMain());
				}
			}
		}
	}

	private static final boolean _CHECK_METHOD_ON_PROCESS_ACTION = false;

	private static Log _log = LogFactoryUtil.getLog(LoginAction.class);

}