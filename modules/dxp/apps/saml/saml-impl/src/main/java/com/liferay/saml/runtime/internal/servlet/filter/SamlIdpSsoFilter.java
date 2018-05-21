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

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.util.CookieKeys;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.saml.constants.SamlWebKeys;
import com.liferay.saml.runtime.configuration.SamlProviderConfigurationHelper;
import com.liferay.saml.runtime.profile.SingleLogoutProfile;
import com.liferay.saml.util.SamlHttpRequestUtil;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Mika Koivisto
 */
@Component(
	immediate = true,
	property = {
		"after-filter=Virtual Host Filter", "dispatcher=FORWARD",
		"dispatcher=REQUEST", "servlet-context-name=",
		"servlet-filter-name=SSO SAML IdP Filter",
		"url-pattern=/c/portal/logout"
	},
	service = Filter.class
)
public class SamlIdpSsoFilter extends BaseSamlPortalFilter {

	@Override
	public boolean isFilterEnabled() {
		if (_samlProviderConfigurationHelper.isEnabled() &&
			_samlProviderConfigurationHelper.isRoleIdp()) {

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
			!_samlProviderConfigurationHelper.isRoleIdp()) {

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

		if (requestPath.equals("/c/portal/logout")) {
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

		if (requestPath.equals("/c/portal/logout")) {
			String samlSsoSessionId = CookieKeys.getCookie(
				request, SamlWebKeys.SAML_SSO_SESSION_ID);

			if (Validator.isNotNull(samlSsoSessionId)) {
				_singleLogoutProfile.processIdpLogout(request, response);
			}
			else {
				filterChain.doFilter(request, response);
			}
		}
		else {
			filterChain.doFilter(request, response);
		}
	}

	@Override
	protected Log getLog() {
		return _log;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SamlIdpSsoFilter.class);

	@Reference
	private Portal _portal;

	@Reference
	private SamlHttpRequestUtil _samlHttpRequestUtil;

	@Reference
	private SamlProviderConfigurationHelper _samlProviderConfigurationHelper;

	@Reference
	private SingleLogoutProfile _singleLogoutProfile;

}