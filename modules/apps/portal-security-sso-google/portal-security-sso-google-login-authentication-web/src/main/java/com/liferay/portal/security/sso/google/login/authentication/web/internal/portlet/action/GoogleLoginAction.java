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

package com.liferay.portal.security.sso.google.login.authentication.web.internal.portlet.action;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.struts.StrutsAction;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.security.sso.google.GoogleAuthorization;

import java.util.Arrays;
import java.util.List;

import javax.portlet.PortletMode;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Sergio González
 * @author Federico Budassi
 * @author Stian Sigvartsen
 */
@Component(
	immediate = true, property = "path=/portal/google_login",
	service = StrutsAction.class
)
public class GoogleLoginAction implements StrutsAction {

	@Override
	public String execute(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		if (!_googleAuthorization.isEnabled(themeDisplay.getCompanyId())) {
			throw new PrincipalException.MustBeEnabled(
				themeDisplay.getCompanyId(),
				GoogleAuthorization.class.getName());
		}

		String cmd = ParamUtil.getString(httpServletRequest, Constants.CMD);

		if (cmd.equals("login")) {
			String loginRedirect = _googleAuthorization.getLoginRedirect(
				themeDisplay.getCompanyId(),
				getReturnRequestUri(httpServletRequest), _scopesLogin);

			httpServletResponse.sendRedirect(loginRedirect);
		}
		else if (cmd.equals("token")) {
			HttpSession session = httpServletRequest.getSession();

			String authorizationCode = ParamUtil.getString(
				httpServletRequest, "code");

			if (Validator.isNotNull(authorizationCode)) {
				String returnRequestUri = getReturnRequestUri(
					httpServletRequest);

				try {
					User user = _googleAuthorization.addOrUpdateUser(
						session, themeDisplay.getCompanyId(), authorizationCode,
						returnRequestUri, _scopesLogin);

					if ((user != null) &&
						(user.getStatus() ==
							WorkflowConstants.STATUS_INCOMPLETE)) {

						sendUpdateAccountRedirect(
							httpServletRequest, httpServletResponse, user);

						return null;
					}
				}
				catch (PortalException pe) {
					if (_log.isDebugEnabled()) {
						_log.debug(pe, pe);
					}

					Class<?> clazz = pe.getClass();

					sendError(
						clazz.getSimpleName(), httpServletRequest,
						httpServletResponse);

					return null;
				}

				sendLoginRedirect(httpServletRequest, httpServletResponse);

				return null;
			}

			String error = ParamUtil.getString(httpServletRequest, "error");

			if (error.equals("access_denied")) {
				sendLoginRedirect(httpServletRequest, httpServletResponse);

				return null;
			}
		}

		return null;
	}

	protected String getReturnRequestUri(
		HttpServletRequest httpServletRequest) {

		return _portal.getPortalURL(httpServletRequest) +
			_portal.getPathMain() + _REDIRECT_URI;
	}

	protected void sendError(
			String error, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception {

		PortletURL portletURL = PortletURLFactoryUtil.create(
			httpServletRequest, PortletKeys.LOGIN, PortletRequest.RENDER_PHASE);

		portletURL.setParameter(
			"mvcRenderCommandName", "/login/google_login_error");
		portletURL.setParameter("error", error);
		portletURL.setWindowState(LiferayWindowState.POP_UP);

		httpServletResponse.sendRedirect(portletURL.toString());
	}

	protected void sendLoginRedirect(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception {

		PortletURL portletURL = PortletURLFactoryUtil.create(
			httpServletRequest, PortletKeys.LOGIN, PortletRequest.RENDER_PHASE);

		portletURL.setParameter(
			"mvcRenderCommandName", "/login/login_redirect");
		portletURL.setWindowState(LiferayWindowState.POP_UP);

		httpServletResponse.sendRedirect(portletURL.toString());
	}

	protected void sendUpdateAccountRedirect(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, User user)
		throws Exception {

		PortletURL portletURL = PortletURLFactoryUtil.create(
			httpServletRequest, PortletKeys.LOGIN, PortletRequest.RENDER_PHASE);

		portletURL.setParameter("saveLastPath", Boolean.FALSE.toString());
		portletURL.setParameter(
			"mvcRenderCommandName", "/login/associate_google_user");

		PortletURL redirectURL = PortletURLFactoryUtil.create(
			httpServletRequest, PortletKeys.LOGIN, PortletRequest.RENDER_PHASE);

		redirectURL.setParameter(
			"mvcRenderCommandName", "/login/login_redirect");
		redirectURL.setParameter("emailAddress", user.getEmailAddress());
		redirectURL.setParameter("anonymousUser", Boolean.FALSE.toString());
		redirectURL.setPortletMode(PortletMode.VIEW);
		redirectURL.setWindowState(LiferayWindowState.POP_UP);

		portletURL.setParameter("redirect", redirectURL.toString());

		portletURL.setParameter("userId", String.valueOf(user.getUserId()));
		portletURL.setParameter("emailAddress", user.getEmailAddress());
		portletURL.setParameter("firstName", user.getFirstName());
		portletURL.setParameter("lastName", user.getLastName());
		portletURL.setPortletMode(PortletMode.VIEW);
		portletURL.setWindowState(LiferayWindowState.POP_UP);

		httpServletResponse.sendRedirect(portletURL.toString());
	}

	private static final String _REDIRECT_URI =
		"/portal/google_login?cmd=token";

	private static final Log _log = LogFactoryUtil.getLog(
		GoogleLoginAction.class);

	private static final List<String> _scopesLogin = Arrays.asList(
		"email", "profile");

	@Reference
	private GoogleAuthorization _googleAuthorization;

	@Reference
	private Portal _portal;

}