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

package com.liferay.portal.security.auth.verifier;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.security.auth.AuthenticationContext;

import java.util.List;

/**
 * PortalAuthenticationVerifier is responsible for applying
 * matching portal.authentication.verifier.pipeline on the current
 * {@link AuthenticationContext} to get user from request.
 *
 * @author Tomas Polesovsky
 */
public interface PortalAuthenticationVerifier {
	List<AuthVerifierConfiguration> getMatchingVerifiers(
		AuthenticationContext authenticationContext);

	public VerificationResult verifyRequest(AuthenticationContext authCtx)
		throws SystemException, PortalException;

	VerificationResult createGuestVerificationResult(
		AuthenticationContext authenticationContext) throws
		SystemException, PortalException;
}
