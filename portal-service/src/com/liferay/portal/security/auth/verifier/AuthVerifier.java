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

import com.liferay.portal.security.auth.AuthException;
import com.liferay.portal.security.auth.AccessControlContext;

import java.util.Properties;

/**
 * Implementations of AuthVerifier are used to verify authenticated
 * communication.<br />
 * <br />
 * Assuming there is an authentication related token in the
 * servlet request, AuthVerifier checks validity of such token and returns
 * the user which is authenticated using the token. If the token is invalid,
 * AuthVerifier can return {@link AuthVerifierResult.State#INVALID_CREDENTIALS}
 * and optionally redirect servlet response to the authentication service URL.
 * If there is no<br />
 *<br />
 * Warning: Instance of AuthVerifier is a singleton.<br />
 *<br />
 * This interface is a part of authentication verification process, see
 * AuthVerificationFilter for more info.<br />
 *
 * @author Tomas Polesovsky
 */
public interface AuthVerifier {

	/**
	 * Tries to return authenticated user, that means - fetch authentication
	 * token from request, verify the authentication session state and return
	 * state.<br />
	 * Every AuthVerifier is a part of verifier.pipeline
	 * <br />
	 * Returns {@link AuthVerifierResult} with
	 * {@link AuthVerifierResult.State} defining actual state:
	 * <ul>
	 * <li>{@link AuthVerifierResult.State#NOT_APPLICABLE} when
	 * authentication token is missing (different authentication was used)</li>
	 * <li>{@link AuthVerifierResult.State#SUCCESS} when authentication
	 * token is valid and user authenticated</li>
	 * <li>{@link AuthVerifierResult.State#INVALID_CREDENTIALS} when
	 * authentication token is invalid or authenticated session expired.</li>
	 * </ul>
	 * <br />
	 * Successful {@link AuthVerifierResult} will be later accessible from
	 * {@link AccessControlContext} and AuthVerifier can define optional
	 * settings using
	 * {@link AuthVerifierResult#setAuthenticationSettings(java.util.Map)}.
	 * <br />
	 * <br />
	 * Any thrown exception will be logged, verification will continue with next
	 * AuthVerifier.
	 *
	 * @param accessControlContext Authentication context with
	 *                             	request and response.
	 * @param configuration Optional AuthVerifier configuration
	 * @return Not null {@link AuthVerifierResult} object with defined
	 * 	{@link AuthVerifierResult.State}.
	 * @throws AuthException on internal exception
	 */
	public AuthVerifierResult verify(
			AccessControlContext accessControlContext,
			Properties configuration)
		throws AuthException;

}