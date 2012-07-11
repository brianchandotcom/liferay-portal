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
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.AutoResetThreadLocal;
import com.liferay.portal.model.User;
import com.liferay.portal.security.RemoteAccessTypeThreadLocal;
import com.liferay.portal.security.auth.verifier.PortalAuthenticationVerifier;
import com.liferay.portal.security.auth.verifier.PortalAuthenticationVerifierImpl;
import com.liferay.portal.security.auth.verifier.VerificationResult;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.security.permission.PermissionThreadLocal;
import com.liferay.portal.service.UserLocalServiceUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Tomas Polesovsky
 * @author Michael C. Han
 */
public class PortalAAManagerImpl implements PortalAAManager {

	/**
	 * Temporary singleton.
	 * //TODO: Rewrite to Spring
	 */
	public static PortalAAManager getInstance() {
		if (_instance == null) {
			_instance = new PortalAAManagerImpl();
			_instance.portalAuthenticationVerifier =
				new PortalAuthenticationVerifierImpl();
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

		AuthenticationContext result = new AuthenticationContext();
		result.setRequest(request);
		result.setResponse(response);

		setAuthenticationContext(result);
	}

	public void initAuthorizationContext(long userId) throws AuthException{
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

	public VerificationResult.State verifyRequest() throws SystemException, PortalException {
		AuthenticationContext authenticationCtx = getAuthenticationContext();

		VerificationResult result = portalAuthenticationVerifier
			.verifyRequest(authenticationCtx);

		if(result.getAuthenticationSettings() != null){
			authenticationCtx.getSettings().putAll(result
				.getAuthenticationSettings());
		}

		authenticationCtx.setVerificationResult(result);

		return result.getState();
	}

	public void setAuthenticationContext(
		AuthenticationContext authenticationContext) {

		_authenticationContextThreadLocal.set(authenticationContext);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PortalAAManagerImpl.class);

	private static PortalAAManagerImpl _instance;

	private static ThreadLocal<AuthenticationContext>
		_authenticationContextThreadLocal =
		new AutoResetThreadLocal<AuthenticationContext>(
			PortalAAManagerImpl.class + "._authenticationContext");

	private PortalAuthenticationVerifier portalAuthenticationVerifier;

}