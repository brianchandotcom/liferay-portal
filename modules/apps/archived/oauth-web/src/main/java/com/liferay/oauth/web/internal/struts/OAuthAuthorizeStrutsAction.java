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

package com.liferay.oauth.web.internal.struts;

import com.liferay.oauth.constants.OAuthConstants;
import com.liferay.oauth.constants.OAuthPortletKeys;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.portlet.WindowStateFactory;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.struts.StrutsAction;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.URLCodec;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import javax.portlet.PortletMode;
import javax.portlet.PortletRequest;
import javax.portlet.WindowState;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.oauth.OAuth;

import org.osgi.service.component.annotations.Component;

/**
 * @author Ivica Cardic
 */
@Component(
	immediate = true, property = "path=" + OAuthConstants.PUBLIC_PATH_AUTHORIZE,
	service = StrutsAction.class
)
public class OAuthAuthorizeStrutsAction implements StrutsAction {

	@Override
	public String execute(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception {

		if (!_isSignedIn()) {
			return _redirectToLogin(httpServletRequest, httpServletResponse);
		}

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		httpServletResponse.sendRedirect(
			PortletURLBuilder.create(
				PortletURLFactoryUtil.create(
					httpServletRequest, OAuthPortletKeys.OAUTH_AUTHORIZE,
					themeDisplay.getPlid(), PortletRequest.RENDER_PHASE)
			).setParameter(
				OAuth.OAUTH_CALLBACK,
				() -> {
					String oauthCallback = httpServletRequest.getParameter(
						OAuth.OAUTH_CALLBACK);

					if (Validator.isNotNull(oauthCallback)) {
						return oauthCallback;
					}

					return null;
				}
			).setParameter(
				OAuth.OAUTH_TOKEN,
				httpServletRequest.getParameter(OAuth.OAUTH_TOKEN)
			).setParameter(
				"saveLastPath", "0"
			).setPortletMode(
				PortletMode.VIEW
			).setWindowState(
				_getWindowState(httpServletRequest)
			).buildString());

		return null;
	}

	private WindowState _getWindowState(HttpServletRequest httpServletRequest) {
		String windowStateString = ParamUtil.getString(
			httpServletRequest, "windowState");

		if (Validator.isNotNull(windowStateString)) {
			return WindowStateFactory.getWindowState(windowStateString);
		}

		return LiferayWindowState.POP_UP;
	}

	private boolean _isSignedIn() {
		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		if ((permissionChecker == null) || !permissionChecker.isSignedIn()) {
			return false;
		}

		return true;
	}

	private String _redirectToLogin(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		StringBundler sb = new StringBundler(4);

		sb.append(themeDisplay.getPathMain());
		sb.append("/portal/login?redirect=");
		sb.append(URLCodec.encodeURL(httpServletRequest.getRequestURI()));

		String queryString = httpServletRequest.getQueryString();

		if (Validator.isNotNull(queryString)) {
			sb.append(
				URLCodec.encodeURL(StringPool.QUESTION.concat(queryString)));
		}

		httpServletResponse.sendRedirect(sb.toString());

		return null;
	}

}