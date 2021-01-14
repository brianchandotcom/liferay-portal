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

import com.liferay.oauth.model.OAuthApplication;
import com.liferay.oauth.service.OAuthApplicationLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;

/**
 * @author Ivica Cardic
 */
public class OAuthApplicationPermission {

	public static void check(
			PermissionChecker permissionChecker, long oAuthApplicationId,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, oAuthApplicationId, actionId)) {
			throw new PrincipalException();
		}
	}

	public static void check(
			PermissionChecker permissionChecker,
			OAuthApplication oAuthApplication, String actionId)
		throws PortalException {

		if (!contains(permissionChecker, oAuthApplication, actionId)) {
			throw new PrincipalException();
		}
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long oAuthApplicationId,
			String actionId)
		throws PortalException {

		return contains(
			permissionChecker,
			OAuthApplicationLocalServiceUtil.getOAuthApplication(
				oAuthApplicationId),
			actionId);
	}

	public static boolean contains(
		PermissionChecker permissionChecker, OAuthApplication oAuthApplication,
		String actionId) {

		if (permissionChecker.hasOwnerPermission(
				oAuthApplication.getCompanyId(),
				OAuthApplication.class.getName(),
				oAuthApplication.getOAuthApplicationId(),
				oAuthApplication.getUserId(), actionId)) {

			return true;
		}

		return permissionChecker.hasPermission(
			null, OAuthApplication.class.getName(),
			oAuthApplication.getOAuthApplicationId(), actionId);
	}

}