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
import com.liferay.portal.kernel.settings.CompanyServiceSettingsLocator;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.security.auto.login.basic.auth.header.module.configuration.BasicAuthHeaderAutoLoginConfiguration;
import com.liferay.portal.security.auto.login.internal.basic.auth.header.constants.BasicAuthHeaderAutoLoginConstants;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * <p>
 * 1. Install Firefox. These instructions assume you have Firefox 2.0.0.1.
 * Previous version of Firefox have been tested and are known to work.
 * </p>
 *
 * <p>
 * 2. Install the Modify Headers 0.5.4 Add-on. Tools > Add Ons. Click the get
 * extensions link at the bottom of the window. Type in "Modify Headers" in the
 * Search box. Find Modify Headers in the results page and click on it. Then
 * click the install now link.
 * </p>
 *
 * <p>
 * 3. Configure Modify Headers to add a basic authentication header. Tools >
 * Modify Headers. In the Modify Headers window select the Add drop down. Type
 * in "Authorization" in the next box. Type in "Basic bGlmZXJheS5jb20uMTp0ZXN0"
 * in the next box. Click the Add button.
 * </p>
 *
 * <p>
 * 4. Make sure your header modification is enabled and point your browser to
 * the Liferay portal.
 * </p>
 *
 * <p>
 * 5. You should now be authenticated as Joe Bloggs.
 * </p>
 *
 * @author Britt Courtney
 * @author Brian Wing Shun Chan
 * @author Tomas Polesovsky
 */
@Component(
	configurationPid = "com.liferay.portal.security.auto.login.basic.auth.header.module.configuration.BasicAuthHeaderAutoLoginConfiguration",
	immediate = true, service = AutoLogin.class
)
public class BasicAuthHeaderAutoLogin extends BaseAutoLogin {

	@Override
	protected String[] doLogin(
			HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		long companyId = PortalUtil.getCompanyId(request);

		if (!isEnabled(companyId)) {
			return null;
		}

		HttpAuthorizationHeader httpAuthorizationHeader =
			HttpAuthManagerUtil.parse(request);

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
			request, httpAuthorizationHeader);

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
		BasicAuthHeaderAutoLoginConfiguration
			basicAuthHeaderAutoLoginConfiguration =
				_getBasicAuthHeaderAutoLoginConfiguration(companyId);

		if (basicAuthHeaderAutoLoginConfiguration == null) {
			return false;
		}

		return basicAuthHeaderAutoLoginConfiguration.enabled();
	}

	@Reference(unbind = "-")
	protected void setConfigurationProvider(
		ConfigurationProvider configurationProvider) {

		_configurationProvider = configurationProvider;
	}

	private BasicAuthHeaderAutoLoginConfiguration
		_getBasicAuthHeaderAutoLoginConfiguration(long companyId) {

		try {
			BasicAuthHeaderAutoLoginConfiguration
				basicAuthHeaderAutoLoginConfiguration =
					_configurationProvider.getConfiguration(
						BasicAuthHeaderAutoLoginConfiguration.class,
						new CompanyServiceSettingsLocator(
							companyId,
							BasicAuthHeaderAutoLoginConstants.SERVICE_NAME));

			return basicAuthHeaderAutoLoginConfiguration;
		}
		catch (ConfigurationException ce) {
			_log.error("Unable to get basic auth header configuration", ce);
		}

		return null;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BasicAuthHeaderAutoLogin.class);

	private ConfigurationProvider _configurationProvider;

}