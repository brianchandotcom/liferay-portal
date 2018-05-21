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

package com.liferay.saml.runtime.internal.servlet.filter;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.struts.LastPath;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Props;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.saml.persistence.model.SamlSpSession;
import com.liferay.saml.runtime.configuration.SamlProviderConfigurationHelper;
import com.liferay.saml.runtime.profile.SingleLogoutProfile;
import com.liferay.saml.runtime.profile.WebSsoProfile;
import com.liferay.saml.util.SamlHttpRequestUtil;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Mika Koivisto
 */
@Component(
	immediate = true,
	property = {
		"after-filter=Virtual Host Filter", "dispatcher=FORWARD",
		"dispatcher=REQUEST",
		"init-param.url-regex-ignore-pattern=^/html/.+\\.(css|gif|html|ico|jpg|js|png)(\\?.*)?$",
		"servlet-context-name=", "servlet-filter-name=SSO SAML SP Filter",
		"url-pattern=/*"
	},
	service = Filter.class
)
public class SamlSpSsoFilter extends BaseSamlPortalFilter {

	@Override
	public boolean isFilterEnabled() {
		if (_samlProviderConfigurationHelper.isEnabled() &&
			_samlProviderConfigurationHelper.isRoleSp()) {

			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public boolean isFilterEnabled(
		HttpServletRequest request, HttpServletResponse response) {

		if (!_samlProviderConfigurationHelper.isEnabled() ||
			!_samlProviderConfigurationHelper.isRoleSp()) {

			return false;
		}

		try {
			User user = _portal.getUser(request);

			if (user != null) {
				return true;
			}
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(e, e);
			}
		}

		String requestPath = _samlHttpRequestUtil.getRequestPath(request);

		if (requestPath.equals("/c/portal/login") ||
			requestPath.equals("/c/portal/logout")) {

			return true;
		}

		return false;
	}

	@Override
	protected void doProcessFilter(
			HttpServletRequest request, HttpServletResponse response,
			FilterChain filterChain)
		throws Exception {

		String requestPath = _samlHttpRequestUtil.getRequestPath(request);

		SamlSpSession samlSpSession = _singleLogoutProfile.getSamlSpSession(
			request);

		if ((samlSpSession != null) && samlSpSession.isTerminated()) {
			_singleLogoutProfile.terminateSpSession(request, response);

			_singleLogoutProfile.logout(request, response);

			response.sendRedirect(_portal.getCurrentCompleteURL(request));
		}
		else if (requestPath.equals("/c/portal/login")) {
			login(request, response);
		}
		else if (requestPath.equals("/c/portal/logout") &&
				 _singleLogoutProfile.isSingleLogoutSupported(request)) {

			if (samlSpSession != null) {
				_singleLogoutProfile.processSpLogout(request, response);
			}
			else {
				filterChain.doFilter(request, response);
			}
		}
		else {
			_webSsoProfile.updateSamlSpSession(request, response);

			filterChain.doFilter(request, response);
		}
	}

	@Override
	protected Log getLog() {
		return _log;
	}

	protected void login(
			HttpServletRequest request, HttpServletResponse response)
		throws PortalException {

		String relayState = ParamUtil.getString(request, "redirect");

		if (Validator.isNotNull(relayState)) {
			relayState = _portal.escapeRedirect(relayState);
		}

		HttpSession session = request.getSession();

		LastPath lastPath = (LastPath)session.getAttribute(WebKeys.LAST_PATH);

		if (GetterUtil.getBoolean(
				_props.get(PropsKeys.AUTH_FORWARD_BY_LAST_PATH)) &&
			(lastPath != null) && Validator.isNull(relayState)) {

			StringBundler sb = new StringBundler(4);

			sb.append(_portal.getPortalURL(request));
			sb.append(lastPath.getContextPath());
			sb.append(lastPath.getPath());
			sb.append(lastPath.getParameters());

			relayState = sb.toString();
		}
		else if (Validator.isNull(relayState)) {
			relayState = _portal.getPathMain();
		}

		_webSsoProfile.sendAuthnRequest(request, response, relayState);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SamlSpSsoFilter.class);

	@Reference
	private Portal _portal;

	@Reference
	private Props _props;

	@Reference
	private SamlHttpRequestUtil _samlHttpRequestUtil;

	@Reference
	private SamlProviderConfigurationHelper _samlProviderConfigurationHelper;

	@Reference
	private SingleLogoutProfile _singleLogoutProfile;

	@Reference
	private WebSsoProfile _webSsoProfile;

}