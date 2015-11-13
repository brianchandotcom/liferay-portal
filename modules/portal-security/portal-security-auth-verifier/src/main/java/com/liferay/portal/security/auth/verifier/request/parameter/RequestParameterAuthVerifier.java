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

package com.liferay.portal.security.auth.verifier.request.parameter;

import com.liferay.portal.kernel.security.auth.verifier.AuthVerifier;
import com.liferay.portal.kernel.security.auth.verifier.AuthVerifierResult;
import com.liferay.portal.kernel.security.auto.login.AutoLoginException;
import com.liferay.portal.security.auth.AccessControlContext;
import com.liferay.portal.security.auth.AuthException;
import com.liferay.portal.security.auto.login.request.parameter.RequestParameterAutoLogin;

import java.util.Properties;

/**
 * @author Tomas Polesovsky
 */
public class RequestParameterAuthVerifier
	extends RequestParameterAutoLogin implements AuthVerifier {

	@Override
	public String getAuthType() {
		return getClass().getSimpleName();
	}

	@Override
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
				authVerifierResult.setPasswordBasedAuthentication(true);
				authVerifierResult.setState(AuthVerifierResult.State.SUCCESS);
				authVerifierResult.setUserId(Long.valueOf(credentials[0]));
			}

			return authVerifierResult;
		}
		catch (AutoLoginException ale) {
			throw new AuthException(ale);
		}
	}

	@Override
	protected boolean isEnabled(long companyId) {
		return true;
	}

}