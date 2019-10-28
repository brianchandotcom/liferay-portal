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

package com.liferay.oauth.web.internal.struts;

import com.liferay.oauth.constants.OAuthConstants;
import com.liferay.oauth.constants.OAuthPortletKeys;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.portlet.WindowStateFactory;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.struts.StrutsAction;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.URLCodec;
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

/**
 * @author Ivica Cardic
 */
@Component(
	immediate = true, property = "path=" + OAuthConstants.PUBLIC_PATH_AUTHORIZE,
	service = StrutsAction.class
)
public class OAuthAuthorizeAction implements StrutsAction {

	@Override
	public String execute(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception {

		if (!isSignedIn()) {
			return redirectToLogin(httpServletRequest, httpServletResponse);
		}

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		PortletURL portletURL = PortletURLFactoryUtil.create(
			httpServletRequest, OAuthPortletKeys.OAUTH_AUTHORIZE,
			themeDisplay.getPlid(), PortletRequest.RENDER_PHASE);

		portletURL.setParameter("saveLastPath", "0");

		String oauthCallback = httpServletRequest.getParameter(
			OAuth.OAUTH_CALLBACK);

		if (Validator.isNotNull(oauthCallback)) {
			portletURL.setParameter(OAuth.OAUTH_CALLBACK, oauthCallback);
		}

		portletURL.setParameter(
			OAuth.OAUTH_TOKEN,
			httpServletRequest.getParameter(OAuth.OAUTH_TOKEN));
		portletURL.setPortletMode(PortletMode.VIEW);
		portletURL.setWindowState(getWindowState(httpServletRequest));

		String redirect = portletURL.toString();

		httpServletResponse.sendRedirect(redirect);

		return null;
	}

	protected WindowState getWindowState(
		HttpServletRequest httpServletRequest) {

		String windowStateString = ParamUtil.getString(
			httpServletRequest, "windowState");

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
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException {

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