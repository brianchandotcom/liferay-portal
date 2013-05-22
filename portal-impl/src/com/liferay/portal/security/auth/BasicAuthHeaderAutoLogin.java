/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.security.auth;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.util.Base64;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.util.Portal;
import com.liferay.portlet.login.util.LoginUtil;

import java.util.Properties;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
public class BasicAuthHeaderAutoLogin
	extends BaseAutoLogin implements AuthVerifier {

	public String getAuthType() {
		return HttpServletRequest.BASIC_AUTH;
	}

	public AuthVerifierResult verify(
			AccessControlContext accessControlContext, Properties properties)
		throws AuthException {

		try {
			AuthVerifierResult authVerifierResult = new AuthVerifierResult();

			String[] credentials = login(
				accessControlContext.getRequest(),
				accessControlContext.getResponse());

			if (credentials != null) {
				authVerifierResult.setPassword(credentials[1]);
				authVerifierResult.setState(AuthVerifierResult.State.SUCCESS);
				authVerifierResult.setUserId(Long.valueOf(credentials[0]));
			}
			else {

				// Deprecated

				boolean forcedBasicAuth = MapUtil.getBoolean(
					accessControlContext.getSettings(), "basic_auth");

				if (forcedBasicAuth) {
					HttpServletResponse response =
						accessControlContext.getResponse();

					response.setHeader(
						HttpHeaders.WWW_AUTHENTICATE, _BASIC_REALM);

					response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

					authVerifierResult.setState(
						AuthVerifierResult.State.INVALID_CREDENTIALS);
				}
			}

			return authVerifierResult;
		}
		catch (AutoLoginException ale) {
			throw new AuthException(ale);
		}
	}

	@Override
	protected String[] doLogin(
			HttpServletRequest request, HttpServletResponse response)
		throws Exception {

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

		if (!basic.equalsIgnoreCase(HttpServletRequest.BASIC_AUTH)) {
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

	private static final String _BASIC_REALM =
		"Basic realm=\"" + Portal.PORTAL_REALM + "\"";

	private static Log _log = LogFactoryUtil.getLog(
		BasicAuthHeaderAutoLogin.class);

}