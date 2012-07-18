/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.servlet.filters.authverification;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.ProtectedServletRequest;
import com.liferay.portal.security.auth.AuthenticationContext;
import com.liferay.portal.security.auth.PortalAAManager;
import com.liferay.portal.security.auth.verifier.VerificationResult;
import com.liferay.portal.servlet.filters.BasePortalFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * See <a href="http://issues.liferay.com/browse/LPS-27888">LPS-27888</a>.<br />
 * The servlet filter tries to fetch user from request based on provided token
 * (if available). It uses {@link PortalAAManager} to verify request and
 * to initialize authentication and authorization contexts for underlying
 * service calls.
 *
 * @author Tomas Polesovsky
 */
public class AuthVerificationFilter extends BasePortalFilter {

	@Override
	protected void processFilter(
			HttpServletRequest request, HttpServletResponse response,
			FilterChain filterChain)
		throws Exception {

		PortalAAManager portalAAManager = PortalAAManager.getInstance();

		portalAAManager.initAuthenticationContext(request, response);

		VerificationResult.State verificationState =
			portalAAManager.verifyRequest();

		AuthenticationContext authenticationContext =
			portalAAManager.getAuthenticationContext();

		VerificationResult verificationResult =
			authenticationContext.getVerificationResult();

		if (_log.isDebugEnabled()) {
			_log.debug("Verification result: " + verificationResult);
		}

		switch (verificationState) {
			case INVALID_CREDENTIALS: {
				if (_log.isDebugEnabled()) {
					_log.debug("Result state doesn't allow us to continue.");
				}

				return;
			}
			case NOT_APPLICABLE:
			case SUCCESS: {
				long userId = verificationResult.getUserId();

				portalAAManager.initAuthorizationContext(userId);

				HttpServletRequest protectedServletRequest =
					new ProtectedServletRequest(
						request, String.valueOf(userId));

				authenticationContext.setRequest(protectedServletRequest);

				processFilter(
					getClass(), protectedServletRequest, response, filterChain);

				return;
			}
			default: {
				_log.error("Unimplemented state, returning.");

				return;
			}
		}

	}

	private static Log _log = LogFactoryUtil.getLog(
		AuthVerificationFilter.class.getName());

}