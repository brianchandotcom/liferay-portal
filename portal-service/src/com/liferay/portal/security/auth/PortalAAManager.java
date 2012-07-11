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

package com.liferay.portal.security.auth;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.security.auth.verifier.VerificationResult;
import com.liferay.portal.security.auth.verifier.PortalAuthenticationVerifier;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * PortalAAManager is responsible for creating authentication and authorization
 * contexts. It use {@link PortalAuthenticationVerifier} for fetching user from
 * servlet request. <br />
 * <br/>
 * Should be used in this order:
 * <ol><li>
 * {@link #initAuthenticationContext(HttpServletRequest, HttpServletResponse)}
 * to create AuthenticationContext. AuthenticationContext is then accessible
 * from {@link #getAuthenticationContext()}, internally saved as a ThreadLocal.
 * </li>
 *
 * <li>{@link #verifyRequest()} to obtain user from request and update
 * AuthenticationContext with verifier specific settings.</li>
 *
 * <li>{@link #initAuthorizationContext(long userId)} } to init all
 * authorization related ThreadLocals in the portal. Parameter userId
 * is available in {@link AuthenticationContext#getVerificationResult()}</li>
 * </ol>
 *
 * @author Tomas Polesovsky
 */
public interface PortalAAManager {

	public AuthenticationContext getAuthenticationContext();

	public void initAuthenticationContext(
		HttpServletRequest request, HttpServletResponse response);

	public void initAuthorizationContext(long userId) throws AuthException;

	public VerificationResult.State verifyRequest() throws SystemException, PortalException;
}
