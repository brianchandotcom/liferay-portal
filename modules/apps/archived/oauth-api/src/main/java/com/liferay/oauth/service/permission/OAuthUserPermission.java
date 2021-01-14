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

package com.liferay.oauth.service.permission;

import com.liferay.oauth.model.OAuthUser;
import com.liferay.oauth.service.OAuthUserLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;

/**
 * @author Ivica Cardic
 */
public class OAuthUserPermission {

	public static void check(
			PermissionChecker permissionChecker, long oAuthUserId,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, oAuthUserId, actionId)) {
			throw new PrincipalException();
		}
	}

	public static void check(
			PermissionChecker permissionChecker, OAuthUser oAuthUser,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, oAuthUser, actionId)) {
			throw new PrincipalException();
		}
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long oAuthUserId,
			String actionId)
		throws PortalException {

		return contains(
			permissionChecker,
			OAuthUserLocalServiceUtil.getOAuthUser(oAuthUserId), actionId);
	}

	public static boolean contains(
		PermissionChecker permissionChecker, OAuthUser oAuthUser,
		String actionId) {

		if (permissionChecker.hasOwnerPermission(
				oAuthUser.getCompanyId(), OAuthUser.class.getName(),
				oAuthUser.getOAuthUserId(), oAuthUser.getUserId(), actionId)) {

			return true;
		}

		return permissionChecker.hasPermission(
			null, OAuthUser.class.getName(), oAuthUser.getOAuthApplicationId(),
			actionId);
	}

}