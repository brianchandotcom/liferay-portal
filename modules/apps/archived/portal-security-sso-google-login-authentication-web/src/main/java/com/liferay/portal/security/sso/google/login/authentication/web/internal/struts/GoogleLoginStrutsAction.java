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

package com.liferay.portal.security.sso.google.login.authentication.web.internal.struts;

import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
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
public class GoogleLoginStrutsAction implements StrutsAction {

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
			httpServletResponse.sendRedirect(
				_googleAuthorization.getLoginRedirect(
					themeDisplay.getCompanyId(),
					_getReturnRequestUri(httpServletRequest), _scopesLogin));
		}
		else if (cmd.equals("token")) {
			String authorizationCode = ParamUtil.getString(
				httpServletRequest, "code");

			if (Validator.isNotNull(authorizationCode)) {
				HttpSession httpSession = httpServletRequest.getSession();
				String returnRequestUri = _getReturnRequestUri(
					httpServletRequest);

				try {
					User user = _googleAuthorization.addOrUpdateUser(
						httpSession, themeDisplay.getCompanyId(),
						authorizationCode, returnRequestUri, _scopesLogin);

					if ((user != null) &&
						(user.getStatus() ==
							WorkflowConstants.STATUS_INCOMPLETE)) {

						_sendUpdateAccountRedirect(
							httpServletRequest, httpServletResponse, user);

						return null;
					}
				}
				catch (PortalException portalException) {
					if (_log.isDebugEnabled()) {
						_log.debug(portalException);
					}

					Class<?> clazz = portalException.getClass();

					_sendError(
						clazz.getSimpleName(), httpServletRequest,
						httpServletResponse);

					return null;
				}

				_sendLoginRedirect(httpServletRequest, httpServletResponse);

				return null;
			}

			String error = ParamUtil.getString(httpServletRequest, "error");

			if (error.equals("access_denied")) {
				_sendLoginRedirect(httpServletRequest, httpServletResponse);

				return null;
			}
		}

		return null;
	}

	private String _getReturnRequestUri(HttpServletRequest httpServletRequest) {
		return _portal.getPortalURL(httpServletRequest) +
			_portal.getPathMain() + _REDIRECT_URI;
	}

	private void _sendError(
			String error, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception {

		httpServletResponse.sendRedirect(
			PortletURLBuilder.create(
				PortletURLFactoryUtil.create(
					httpServletRequest, PortletKeys.LOGIN,
					PortletRequest.RENDER_PHASE)
			).setMVCRenderCommandName(
				"/portal_security_sso_google_login_authentication" +
					"/google_login_error"
			).setParameter(
				"error", error
			).setWindowState(
				LiferayWindowState.POP_UP
			).buildString());
	}

	private void _sendLoginRedirect(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception {

		httpServletResponse.sendRedirect(
			PortletURLBuilder.create(
				PortletURLFactoryUtil.create(
					httpServletRequest, PortletKeys.LOGIN,
					PortletRequest.RENDER_PHASE)
			).setMVCRenderCommandName(
				"/login/login_redirect"
			).setWindowState(
				LiferayWindowState.POP_UP
			).buildString());
	}

	private void _sendUpdateAccountRedirect(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, User user)
		throws Exception {

		httpServletResponse.sendRedirect(
			PortletURLBuilder.create(
				PortletURLFactoryUtil.create(
					httpServletRequest, PortletKeys.LOGIN,
					PortletRequest.RENDER_PHASE)
			).setMVCRenderCommandName(
				"/portal_security_sso_google_login_authentication" +
					"/associate_google_user"
			).setRedirect(
				PortletURLBuilder.create(
					PortletURLFactoryUtil.create(
						httpServletRequest, PortletKeys.LOGIN,
						PortletRequest.RENDER_PHASE)
				).setMVCRenderCommandName(
					"/login/login_redirect"
				).setParameter(
					"anonymousUser", false
				).setParameter(
					"emailAddress", user.getEmailAddress()
				).setPortletMode(
					PortletMode.VIEW
				).setWindowState(
					LiferayWindowState.POP_UP
				).buildString()
			).setParameter(
				"emailAddress", user.getEmailAddress()
			).setParameter(
				"firstName", user.getFirstName()
			).setParameter(
				"lastName", user.getLastName()
			).setParameter(
				"saveLastPath", false
			).setParameter(
				"userId", user.getUserId()
			).setPortletMode(
				PortletMode.VIEW
			).setWindowState(
				LiferayWindowState.POP_UP
			).buildString());
	}

	private static final String _REDIRECT_URI =
		"/portal/google_login?cmd=token";

	private static final Log _log = LogFactoryUtil.getLog(
		GoogleLoginStrutsAction.class);

	private static final List<String> _scopesLogin = Arrays.asList(
		"email", "profile");

	@Reference
	private GoogleAuthorization _googleAuthorization;

	@Reference
	private Portal _portal;

}