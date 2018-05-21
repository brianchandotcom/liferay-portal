/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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

		OAuthUser oAuthUser = OAuthUserLocalServiceUtil.getOAuthUser(
			oAuthUserId);

		return contains(permissionChecker, oAuthUser, actionId);
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