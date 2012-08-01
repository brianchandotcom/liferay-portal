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

package com.liferay.portal.security.ac;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.AccessControlContext;
import com.liferay.portal.security.auth.AuthException;
import com.liferay.portal.security.auth.CompanyThreadLocal;
import com.liferay.portal.security.auth.PrincipalThreadLocal;
import com.liferay.portal.security.auth.verifier.AuthVerifierPipeline;
import com.liferay.portal.security.auth.verifier.AuthVerifierResult;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.security.permission.PermissionThreadLocal;
import com.liferay.portal.service.UserLocalServiceUtil;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Raymond Augé
 */
public class AccessControlImpl implements AccessControl {

	public void initAccessControlContext(
		HttpServletRequest request, HttpServletResponse response) {

		AccessControlContext accessControlContext =
			AccessControlUtil.getAccessControlContext();

		if (accessControlContext != null) {

			// TODO: think of stack of authentication contexts
			// for running subsequent calls under different privileges

			throw new IllegalStateException(
				"Authentication context is already initialized! " +
					"Changing privileges is prohibited");
		}

		accessControlContext = new AccessControlContext();

		accessControlContext.setRequest(request);
		accessControlContext.setResponse(response);

		AccessControlUtil.setAccessControlContext(accessControlContext);
	}

	public void initContextUser(long userId) throws AuthException {
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

			AccessControlThreadLocal.setRemoteAccess(false);

			//TODO: REFACTOR ServiceContext, PermissionThreadLocals,
			// PrincipalThreadLocal and AuthenticationContext into one TL
		}
		catch (Exception e) {
			throw new AuthException(e.getMessage(), e);
		}
	}

	public AuthVerifierResult.State verifyRequest()
		throws SystemException, PortalException {

		AccessControlContext accessControlContext =
			AccessControlUtil.getAccessControlContext();

		AuthVerifierResult authVerifierResult =
			AuthVerifierPipeline.verifyRequest(accessControlContext);

		Map<String, Object> authenticationSettings =
			authVerifierResult.getAuthenticationSettings();

		if (authenticationSettings != null) {
			Map<String, Object> contextSettings =
				accessControlContext.getSettings();

			contextSettings.putAll(authenticationSettings);
		}

		accessControlContext.setVerificationResult(authVerifierResult);

		return authVerifierResult.getState();
	}

}