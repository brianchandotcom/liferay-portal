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

package com.liferay.saml.internal.servlet.filter;

import com.liferay.petra.string.StringBundler;
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
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.saml.constants.SamlWebKeys;
import com.liferay.saml.persistence.model.SamlSpSession;
import com.liferay.saml.runtime.configuration.SamlProviderConfiguration;
import com.liferay.saml.runtime.configuration.SamlProviderConfigurationHelper;
import com.liferay.saml.runtime.servlet.profile.SingleLogoutProfile;
import com.liferay.saml.runtime.servlet.profile.WebSsoProfile;
import com.liferay.saml.util.SamlHttpRequestUtil;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
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
	public void init(FilterConfig filterConfig) {
		super.init(filterConfig);

		_servletContext = filterConfig.getServletContext();
	}

	@Override
	public boolean isFilterEnabled() {
		if (_samlProviderConfigurationHelper.isEnabled() &&
			_samlProviderConfigurationHelper.isRoleSp()) {

			return true;
		}

		return false;
	}

	@Override
	public boolean isFilterEnabled(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse) {

		if (!_samlProviderConfigurationHelper.isEnabled() ||
			!_samlProviderConfigurationHelper.isRoleSp()) {

			return false;
		}

		try {
			User user = _portal.getUser(httpServletRequest);

			if (user != null) {
				return true;
			}
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception.getMessage(), exception);
			}
			else if (_log.isWarnEnabled()) {
				_log.warn(exception.getMessage());
			}
		}

		String requestPath = _samlHttpRequestUtil.getRequestPath(
			httpServletRequest);

		if (requestPath.equals("/c/portal/login") ||
			requestPath.equals("/c/portal/logout")) {

			return true;
		}

		return false;
	}

	@Override
	protected void doProcessFilter(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, FilterChain filterChain)
		throws Exception {

		String requestPath = _samlHttpRequestUtil.getRequestPath(
			httpServletRequest);

		SamlSpSession samlSpSession = _singleLogoutProfile.getSamlSpSession(
			httpServletRequest);

		if ((samlSpSession != null) && samlSpSession.isTerminated()) {
			_singleLogoutProfile.terminateSpSession(
				httpServletRequest, httpServletResponse);

			_singleLogoutProfile.logout(
				httpServletRequest, httpServletResponse);

			httpServletResponse.sendRedirect(
				_portal.getCurrentCompleteURL(httpServletRequest));
		}
		else if (requestPath.equals("/c/portal/login")) {
			RequestDispatcher requestDispatcher =
				_servletContext.getRequestDispatcher("/c/portal/saml/login");

			httpServletResponse.setContentType("text/html");

			requestDispatcher.include(httpServletRequest, httpServletResponse);

			Object samlSpIdpConnection = httpServletRequest.getAttribute(
				SamlWebKeys.SAML_SP_IDP_CONNECTION);

			if (samlSpIdpConnection != null) {
				try {
					_login(httpServletRequest, httpServletResponse);
				}
				catch (PortalException portalException) {
					if (_log.isInfoEnabled()) {
						_log.info(
							"Failed to send Authn request: " +
								portalException.getMessage());
					}
				}
			}
			else {
				SamlProviderConfiguration samlProviderConfiguration =
					_samlProviderConfigurationHelper.
						getSamlProviderConfiguration();

				Object samlSsologinContext = httpServletRequest.getAttribute(
					SamlWebKeys.SAML_SSO_LOGIN_CONTEXT);

				if ((samlSsologinContext == null) &&
					samlProviderConfiguration.allowShowingTheLoginPortlet()) {

					filterChain.doFilter(
						httpServletRequest, httpServletResponse);
				}
			}
		}
		else if (requestPath.equals("/c/portal/logout")) {
			if (_singleLogoutProfile.isSingleLogoutSupported(
					httpServletRequest)) {

				_singleLogoutProfile.processSpLogout(
					httpServletRequest, httpServletResponse);
			}
			else {
				filterChain.doFilter(httpServletRequest, httpServletResponse);
			}
		}
		else {
			_webSsoProfile.updateSamlSpSession(
				httpServletRequest, httpServletResponse);

			filterChain.doFilter(httpServletRequest, httpServletResponse);
		}
	}

	@Override
	protected Log getLog() {
		return _log;
	}

	private void _login(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws PortalException {

		String relayState = ParamUtil.getString(httpServletRequest, "redirect");

		if (Validator.isNotNull(relayState)) {
			relayState = _portal.escapeRedirect(relayState);
		}

		HttpSession httpSession = httpServletRequest.getSession();

		LastPath lastPath = (LastPath)httpSession.getAttribute(
			WebKeys.LAST_PATH);

		if (GetterUtil.getBoolean(
				_props.get(PropsKeys.AUTH_FORWARD_BY_LAST_PATH)) &&
			(lastPath != null) && Validator.isNull(relayState)) {

			relayState = StringBundler.concat(
				_portal.getPortalURL(httpServletRequest),
				lastPath.getContextPath(), lastPath.getPath(),
				lastPath.getParameters());
		}
		else if (Validator.isNull(relayState)) {
			relayState = _portal.getHomeURL(httpServletRequest);
		}

		_webSsoProfile.sendAuthnRequest(
			httpServletRequest, httpServletResponse, relayState);
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

	private ServletContext _servletContext;

	@Reference
	private SingleLogoutProfile _singleLogoutProfile;

	@Reference
	private WebSsoProfile _webSsoProfile;

}