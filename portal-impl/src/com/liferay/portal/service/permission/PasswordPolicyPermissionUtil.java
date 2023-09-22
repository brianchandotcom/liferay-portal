/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.service.permission;

import com.liferay.portal.kernel.model.PasswordPolicy;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;

/**
 * @author Brian Wing Shun Chan
 */
public class PasswordPolicyPermissionUtil {

	public static void check(
			PermissionChecker permissionChecker, long passwordPolicyId,
			String actionId)
		throws PrincipalException {

		if (!contains(permissionChecker, passwordPolicyId, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, PasswordPolicy.class.getName(),
				passwordPolicyId, actionId);
		}
	}

	public static boolean contains(
		PermissionChecker permissionChecker, long passwordPolicyId,
		String actionId) {

		return permissionChecker.hasPermission(
			null, PasswordPolicy.class.getName(), passwordPolicyId, actionId);
	}

}