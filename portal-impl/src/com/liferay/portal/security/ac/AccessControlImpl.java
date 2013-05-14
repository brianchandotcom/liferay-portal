/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.security.auth.AuthVerifierPipeline;
import com.liferay.portal.security.auth.AuthVerifierResult;
import com.liferay.portal.security.auth.CompanyThreadLocal;
import com.liferay.portal.security.auth.PrincipalThreadLocal;
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

	@Override
	public void initAccessControlContext(
		HttpServletRequest request, HttpServletResponse response,
		Map<String, Object> settings) {

		AccessControlContext accessControlContext =
			AccessControlUtil.getAccessControlContext();

		if (accessControlContext != null) {
			throw new IllegalStateException(
				"Authentication context is already initialized");
		}

		accessControlContext = new AccessControlContext();

		accessControlContext.setRequest(request);
		accessControlContext.setResponse(response);

		Map<String, Object> accessControlContextSettings =
			accessControlContext.getSettings();

		accessControlContextSettings.putAll(settings);

		AccessControlUtil.setAccessControlContext(accessControlContext);
	}

	@Override
	public void initContextUser(long userId) throws AuthException {
		try {
			User user = UserLocalServiceUtil.getUser(userId);

			CompanyThreadLocal.setCompanyId(user.getCompanyId());

			PrincipalThreadLocal.setName(userId);

			PermissionChecker permissionChecker =
				PermissionCheckerFactoryUtil.create(user);

			PermissionThreadLocal.setPermissionChecker(permissionChecker);

			AccessControlThreadLocal.setRemoteAccess(false);
		}
		catch (Exception e) {
			throw new AuthException(e.getMessage(), e);
		}
	}

	@Override
	public AuthVerifierResult.State verifyRequest()
		throws PortalException, SystemException {

		AccessControlContext accessControlContext =
			AccessControlUtil.getAccessControlContext();

		AuthVerifierResult authVerifierResult =
			AuthVerifierPipeline.verifyRequest(accessControlContext);

		Map<String, Object> authVerifierResultSettings =
			authVerifierResult.getSettings();

		if (authVerifierResultSettings != null) {
			Map<String, Object> settings = accessControlContext.getSettings();

			settings.putAll(authVerifierResultSettings);
		}

		accessControlContext.setAuthVerifierResult(authVerifierResult);

		return authVerifierResult.getState();
	}

}