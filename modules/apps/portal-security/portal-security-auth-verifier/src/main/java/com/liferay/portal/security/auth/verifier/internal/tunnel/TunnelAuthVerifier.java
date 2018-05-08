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

package com.liferay.portal.security.auth.verifier.internal.tunnel;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.AccessControlContext;
import com.liferay.portal.kernel.security.auth.AuthException;
import com.liferay.portal.kernel.security.auth.tunnel.TunnelAuthenticationManagerUtil;
import com.liferay.portal.kernel.security.auth.verifier.AuthVerifier;
import com.liferay.portal.kernel.security.auth.verifier.AuthVerifierResult;
import com.liferay.portal.kernel.security.service.access.policy.ServiceAccessPolicyThreadLocal;
import com.liferay.portal.kernel.servlet.HttpHeaders;

import java.io.IOException;
import java.io.ObjectOutputStream;

import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Zsolt Berentey
 */
public class TunnelAuthVerifier implements AuthVerifier {

	@Override
	public String getAuthType() {
		return HttpServletRequest.BASIC_AUTH;
	}

	@Override
	public AuthVerifierResult verify(
			AccessControlContext accessControlContext, Properties properties)
		throws AuthException {

		AuthVerifierResult authVerifierResult = new AuthVerifierResult();

		try {
			String[] credentials = verify(accessControlContext.getRequest());

			if (credentials != null) {
				authVerifierResult.setPassword(credentials[1]);
				authVerifierResult.setState(AuthVerifierResult.State.SUCCESS);
				authVerifierResult.setUserId(Long.valueOf(credentials[0]));

				String serviceAccessPolicyName = (String)properties.get(
					"service.access.policy.name");

				ServiceAccessPolicyThreadLocal.addActiveServiceAccessPolicyName(
					serviceAccessPolicyName);
			}
		}
		catch (AuthException ae) {
			if (_log.isDebugEnabled()) {
				_log.debug(ae);
			}

			HttpServletResponse response = accessControlContext.getResponse();

			try (ObjectOutputStream objectOutputStream =
					new ObjectOutputStream(response.getOutputStream())) {

				objectOutputStream.writeObject(ae);

				authVerifierResult.setState(
					AuthVerifierResult.State.INVALID_CREDENTIALS);
			}
			catch (IOException ioe) {
				_log.error(ioe, ioe);

				throw ae;
			}
		}

		return authVerifierResult;
	}

	protected String[] verify(HttpServletRequest request) throws AuthException {
		String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);

		if (authorization == null) {
			return null;
		}

		String[] credentials = new String[2];

		long userId = TunnelAuthenticationManagerUtil.getUserId(request);

		credentials[0] = String.valueOf(userId);

		credentials[1] = StringPool.BLANK;

		return credentials;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		TunnelAuthVerifier.class);

}