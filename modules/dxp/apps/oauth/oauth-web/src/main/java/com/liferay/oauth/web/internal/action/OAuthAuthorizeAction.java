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

package com.liferay.oauth.web.internal.action;

import com.liferay.oauth.constants.OAuthConstants;
import com.liferay.oauth.constants.OAuthPortletKeys;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.portlet.WindowStateFactory;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.struts.BaseStrutsAction;
import com.liferay.portal.kernel.struts.StrutsAction;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;

import javax.portlet.PortletMode;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.WindowState;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.oauth.OAuth;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Ivica Cardic
 */
@Component(
	immediate = true, property = "path=" + OAuthConstants.PUBLIC_PATH_AUTHORIZE,
	service = StrutsAction.class
)
public class OAuthAuthorizeAction extends BaseStrutsAction {

	@Override
	public String execute(
			HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		if (!isSignedIn()) {
			return redirectToLogin(request, response);
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		PortletURL portletURL = PortletURLFactoryUtil.create(
			request, OAuthPortletKeys.OAUTH_AUTHORIZE, themeDisplay.getPlid(),
			PortletRequest.RENDER_PHASE);

		portletURL.setParameter("saveLastPath", "0");

		String oauthCallback = request.getParameter(OAuth.OAUTH_CALLBACK);

		if (Validator.isNotNull(oauthCallback)) {
			portletURL.setParameter(OAuth.OAUTH_CALLBACK, oauthCallback);
		}

		portletURL.setParameter(
			OAuth.OAUTH_TOKEN, request.getParameter(OAuth.OAUTH_TOKEN));
		portletURL.setPortletMode(PortletMode.VIEW);
		portletURL.setWindowState(getWindowState(request));

		String redirect = portletURL.toString();

		response.sendRedirect(redirect);

		return null;
	}

	protected WindowState getWindowState(HttpServletRequest request) {
		String windowStateString = ParamUtil.getString(request, "windowState");

		if (Validator.isNotNull(windowStateString)) {
			return WindowStateFactory.getWindowState(windowStateString);
		}

		return LiferayWindowState.POP_UP;
	}

	protected boolean isSignedIn() {
		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		if ((permissionChecker == null) || !permissionChecker.isSignedIn()) {
			return false;
		}

		return true;
	}

	protected String redirectToLogin(
			HttpServletRequest request, HttpServletResponse response)
		throws IOException {

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		StringBundler sb = new StringBundler(4);

		sb.append(themeDisplay.getPathMain());
		sb.append("/portal/login?redirect=");
		sb.append(_http.encodeURL(request.getRequestURI()));

		String queryString = request.getQueryString();

		if (Validator.isNotNull(queryString)) {
			sb.append(_http.encodeURL(StringPool.QUESTION.concat(queryString)));
		}

		response.sendRedirect(sb.toString());

		return null;
	}

	@Reference
	private Http _http;

}