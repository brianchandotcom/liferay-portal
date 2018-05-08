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

package com.liferay.portal.security.auth.verifier.internal.basic.auth.header;

import com.liferay.portal.kernel.security.auth.AccessControlContext;
import com.liferay.portal.kernel.security.auth.AuthException;
import com.liferay.portal.kernel.security.auth.http.HttpAuthManagerUtil;
import com.liferay.portal.kernel.security.auth.http.HttpAuthorizationHeader;
import com.liferay.portal.kernel.security.auth.verifier.AuthVerifier;
import com.liferay.portal.kernel.security.auth.verifier.AuthVerifierResult;
import com.liferay.portal.kernel.security.auto.login.AutoLogin;
import com.liferay.portal.kernel.security.auto.login.AutoLoginException;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MapUtil;

import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Tomas Polesovsky
 */
public class BasicAuthHeaderAuthVerifier implements AuthVerifier {

	public BasicAuthHeaderAuthVerifier(AutoLogin autoLogin) {
		_autoLogin = autoLogin;
	}

	@Override
	public String getAuthType() {
		return HttpServletRequest.BASIC_AUTH;
	}

	@Override
	public AuthVerifierResult verify(
			AccessControlContext accessControlContext, Properties properties)
		throws AuthException {

		try {
			AuthVerifierResult authVerifierResult = new AuthVerifierResult();

			String[] credentials = _autoLogin.login(
				accessControlContext.getRequest(),
				accessControlContext.getResponse());

			if (credentials != null) {
				authVerifierResult.setPassword(credentials[1]);
				authVerifierResult.setPasswordBasedAuthentication(true);
				authVerifierResult.setState(AuthVerifierResult.State.SUCCESS);
				authVerifierResult.setUserId(Long.valueOf(credentials[0]));
			}
			else {
				boolean forcedBasicAuth = MapUtil.getBoolean(
					accessControlContext.getSettings(), "basic_auth");

				if (!forcedBasicAuth) {
					forcedBasicAuth = GetterUtil.getBoolean(
						properties.getProperty("basic_auth"));
				}

				if (forcedBasicAuth) {
					HttpAuthorizationHeader httpAuthorizationHeader =
						new HttpAuthorizationHeader(
							HttpAuthorizationHeader.SCHEME_BASIC);

					HttpAuthManagerUtil.generateChallenge(
						accessControlContext.getRequest(),
						accessControlContext.getResponse(),
						httpAuthorizationHeader);

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

	private final AutoLogin _autoLogin;

}