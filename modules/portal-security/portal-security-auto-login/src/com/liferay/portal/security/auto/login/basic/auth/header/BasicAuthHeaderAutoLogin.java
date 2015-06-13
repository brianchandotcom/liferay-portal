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
import com.liferay.portal.kernel.security.auto.login.AutoLogin;
import com.liferay.portal.kernel.security.auto.login.BaseAutoLogin;
import com.liferay.portal.kernel.settings.CompanyServiceSettingsLocator;
import com.liferay.portal.kernel.settings.SettingsException;
import com.liferay.portal.kernel.settings.SettingsFactory;
import com.liferay.portal.kernel.util.Base64;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.security.auto.login.basic.auth.header.configuration.BasicAuthHeaderAutoLoginConfiguration;
import com.liferay.portal.security.auto.login.basic.auth.header.constants.BasicAuthHeaderAutoLoginConstants;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.login.util.LoginUtil;

import java.util.StringTokenizer;

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
	configurationPid = "com.liferay.portal.security.auto.login.basic.auth.header.configuration.BasicAuthHeaderAutoLoginConfiguration",
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

		// Get the Authorization header, if one was supplied

		String authorization = request.getHeader("Authorization");

		if (authorization == null) {
			return null;
		}

		StringTokenizer st = new StringTokenizer(authorization);

		if (!st.hasMoreTokens()) {
			return null;
		}

		String basic = st.nextToken();

		// We only handle HTTP Basic authentication

		if (!StringUtil.equalsIgnoreCase(
				basic, HttpServletRequest.BASIC_AUTH)) {

			return null;
		}

		String encodedCredentials = st.nextToken();

		if (_log.isDebugEnabled()) {
			_log.debug("Encoded credentials are " + encodedCredentials);
		}

		String decodedCredentials = new String(
			Base64.decode(encodedCredentials));

		if (_log.isDebugEnabled()) {
			_log.debug("Decoded credentials are " + decodedCredentials);
		}

		int pos = decodedCredentials.indexOf(CharPool.COLON);

		if (pos == -1) {
			return null;
		}

		String login = GetterUtil.getString(
			decodedCredentials.substring(0, pos));
		String password = decodedCredentials.substring(pos + 1);

		long userId = LoginUtil.getAuthenticatedUserId(
			request, login, password, null);

		String[] credentials = new String[3];

		credentials[0] = String.valueOf(userId);
		credentials[1] = password;
		credentials[2] = Boolean.TRUE.toString();

		return credentials;
	}

	protected boolean isEnabled(long companyId) {
		BasicAuthHeaderAutoLoginConfiguration configuration = _getConfiguration(
			companyId);

		if (configuration == null) {
			return false;
		}

		return configuration.enabled();
	}

	@Reference
	protected void setSettingsFactory(SettingsFactory settingsFactory) {
		_settingsFactory = settingsFactory;
	}

	private BasicAuthHeaderAutoLoginConfiguration _getConfiguration(
		long companyId) {

		try {
			BasicAuthHeaderAutoLoginConfiguration configuration =
				_settingsFactory.getSettings(
					BasicAuthHeaderAutoLoginConfiguration.class,
					new CompanyServiceSettingsLocator(
						companyId,
						BasicAuthHeaderAutoLoginConstants.SERVICE_NAME));

			return configuration;
		}
		catch (SettingsException se) {
			_log.error(
				"Unable to get BasicAuthHeaderAutoLogin configuration", se);
		}

		return null;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BasicAuthHeaderAutoLogin.class);

	private volatile SettingsFactory _settingsFactory;

}