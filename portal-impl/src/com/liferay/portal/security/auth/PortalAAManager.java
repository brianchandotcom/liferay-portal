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
import com.liferay.portal.kernel.util.AutoResetThreadLocal;
import com.liferay.portal.model.User;
import com.liferay.portal.security.RemoteAccessTypeThreadLocal;
import com.liferay.portal.security.auth.verifier.AuthVerifierPipeline;
import com.liferay.portal.security.auth.verifier.VerificationResult;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.security.permission.PermissionThreadLocal;
import com.liferay.portal.service.UserLocalServiceUtil;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * PortalAAManager is responsible for creating authentication and authorization
 * contexts. It use AuthVerifierPipeline for fetching user from
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
 * @author Tomas Polesovsky
 * @author Michael C. Han
 */
public class PortalAAManager {

	/**
	 * Temporary singleton.
	 */
	public static PortalAAManager getInstance() {
		if (_instance == null) {
			_instance = new PortalAAManager();
		}

		return _instance;
	}

	public AuthenticationContext getAuthenticationContext() {
		return _authenticationContextThreadLocal.get();
	}

	public void initAuthenticationContext(
		HttpServletRequest request, HttpServletResponse response) {

		if (getAuthenticationContext() != null) {
			// TODO: think of stack of authentication contexts
			// for running subsequent calls under different privileges
			throw new IllegalStateException(
				"Authentication context is already initialized! " +
				"Changing privileges is prohibited");
		}

		AuthenticationContext authenticationContext =
			new AuthenticationContext();

		authenticationContext.setRequest(request);
		authenticationContext.setResponse(response);

		setAuthenticationContext(authenticationContext);
	}

	public void initAuthorizationContext(long userId) throws AuthException {
		// TODO: think of stack of authorization contexts (ServiceContext
		// already use stack for this) for running calls under different
		// privileges, the authorization stack should be bound to the
		// authentication one = only one stack with authentication+authorization
		// context? All services could easily have the whole context

		try {
			User user = UserLocalServiceUtil.getUser(userId);

			CompanyThreadLocal.setCompanyId(user.getCompanyId());

			PermissionChecker permissionChecker =
				PermissionCheckerFactoryUtil.create(user);

			PermissionThreadLocal.setPermissionChecker(permissionChecker);
			//TODO: Init ServiceContext?

			//TODO: Verify if we need to init PrincipalThreadLocal.password
			PrincipalThreadLocal.setName(userId);

			// TODO: why we have so many ThreadLocals instead of one with whole
			// context ???
			RemoteAccessTypeThreadLocal.setRemoteAccess(false);

			//TODO: REFACTOR ServiceContext, PermissionThreadLocals,
			// PrincipalThreadLocal and AuthenticationContext into one TL
		}
		catch (Exception e) {
			throw new AuthException(e.getMessage(), e);
		}
	}

	public void setAuthenticationContext(
		AuthenticationContext authenticationContext) {

		_authenticationContextThreadLocal.set(authenticationContext);
	}

	public VerificationResult.State verifyRequest()
		throws SystemException, PortalException {

		AuthenticationContext authenticationContext =
			getAuthenticationContext();

		VerificationResult verificationResult =
			AuthVerifierPipeline.verifyRequest(authenticationContext);

		Map<String, Object> resultSettings =
			verificationResult.getAuthenticationSettings();

		if (resultSettings != null) {
			Map<String, Object> contextSettings =
				authenticationContext.getSettings();

			contextSettings.putAll(resultSettings);
		}

		authenticationContext.setVerificationResult(verificationResult);

		return verificationResult.getState();
	}

	private static PortalAAManager _instance;
	private static ThreadLocal<AuthenticationContext>
		_authenticationContextThreadLocal =
			new AutoResetThreadLocal<AuthenticationContext>(
				PortalAAManager.class + "._authenticationContext");

}