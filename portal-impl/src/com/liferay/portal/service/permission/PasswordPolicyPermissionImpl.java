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

package com.liferay.portal.service.permission;

import com.liferay.portal.kernel.model.PasswordPolicy;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.permission.PasswordPolicyPermission;

/**
 * @author Brian Wing Shun Chan
 */
public class PasswordPolicyPermissionImpl implements PasswordPolicyPermission {

	@Override
	public void check(
			PermissionChecker permissionChecker, long passwordPolicyId,
			String actionId)
		throws PrincipalException {

		if (!contains(permissionChecker, passwordPolicyId, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, PasswordPolicy.class.getName(),
				passwordPolicyId, actionId);
		}
	}

	@Override
	public boolean contains(
		PermissionChecker permissionChecker, long passwordPolicyId,
		String actionId) {

		return permissionChecker.hasPermission(
			null, PasswordPolicy.class.getName(), passwordPolicyId, actionId);
	}

}