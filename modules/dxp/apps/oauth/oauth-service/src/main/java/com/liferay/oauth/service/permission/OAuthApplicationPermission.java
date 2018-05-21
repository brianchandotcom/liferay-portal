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

		OAuthApplication oAuthApplication =
			OAuthApplicationLocalServiceUtil.getOAuthApplication(
				oAuthApplicationId);

		return contains(permissionChecker, oAuthApplication, actionId);
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