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

package com.liferay.portal.security.auto.login.basic.auth.header;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.security.auth.AuthException;
import com.liferay.portal.kernel.security.auth.http.HttpAuthManagerUtil;
import com.liferay.portal.kernel.security.auth.http.HttpAuthorizationHeader;
import com.liferay.portal.kernel.security.auto.login.AutoLogin;
import com.liferay.portal.kernel.security.auto.login.BaseAutoLogin;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.security.configuration.BasicAuthHeaderSupportConfiguration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Shuyang Zhou
 */
@Component(
	property = {"private.auto.login=true", "type=basic.auth.header"},
	service = AutoLogin.class
)
public class BasicAuthHeaderAutoLoginSupport extends BaseAutoLogin {

	@Override
	protected String[] doLogin(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception {

		if (!isEnabled(_portal.getCompanyId(httpServletRequest))) {
			return null;
		}

		HttpAuthorizationHeader httpAuthorizationHeader =
			HttpAuthManagerUtil.parse(httpServletRequest);

		if (httpAuthorizationHeader == null) {
			return null;
		}

		String scheme = httpAuthorizationHeader.getScheme();

		// We only handle HTTP Basic authentication

		if (!StringUtil.equalsIgnoreCase(
				scheme, HttpAuthorizationHeader.SCHEME_BASIC)) {

			return null;
		}

		long userId = HttpAuthManagerUtil.getUserId(
			httpServletRequest, httpAuthorizationHeader);

		if (userId <= 0) {
			throw new AuthException();
		}

		String[] credentials = new String[3];

		credentials[0] = String.valueOf(userId);
		credentials[1] = httpAuthorizationHeader.getAuthParameter(
			HttpAuthorizationHeader.AUTH_PARAMETER_NAME_PASSWORD);

		credentials[2] = Boolean.TRUE.toString();

		return credentials;
	}

	protected boolean isEnabled(long companyId) {
		try {
			BasicAuthHeaderSupportConfiguration
				basicAuthHeaderSupportConfiguration =
					_configurationProvider.getCompanyConfiguration(
						BasicAuthHeaderSupportConfiguration.class, companyId);

			return basicAuthHeaderSupportConfiguration.enabled();
		}
		catch (ConfigurationException configurationException) {
			_log.error(
				"Unable to get basic auth protocol support configuration",
				configurationException);
		}

		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BasicAuthHeaderAutoLoginSupport.class);

	@Reference
	private ConfigurationProvider _configurationProvider;

	@Reference
	private Portal _portal;

}