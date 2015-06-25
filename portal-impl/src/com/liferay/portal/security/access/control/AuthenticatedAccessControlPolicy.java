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

package com.liferay.portal.security.access.control;

import com.liferay.portal.kernel.security.access.control.AccessControlled;
import com.liferay.portal.kernel.security.access.control.BaseAccessControlPolicy;
import com.liferay.portal.kernel.security.access.control.profile.ServiceAccessControlProfile;
import com.liferay.portal.kernel.security.access.control.profile.ServiceAccessControlProfileManagerUtil;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.security.permission.PermissionThreadLocal;

import java.lang.reflect.Method;

/**
 * @author Tomas Polesovsky
 * @author Igor Spasic
 * @author Michael C. Han
 * @author Raymond Augé
 */
public class AuthenticatedAccessControlPolicy extends BaseAccessControlPolicy {

	@Override
	public void onServiceRemoteAccess(
			Method method, Object[] arguments,
			AccessControlled accessControlled)
		throws SecurityException {

		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		ServiceAccessControlProfile defaultServiceAccessControlProfile = null;

		if (ServiceAccessControlProfileManagerUtil.
				getServiceAccessControlProfileManager() != null) {

			defaultServiceAccessControlProfile =
				ServiceAccessControlProfileManagerUtil.
					getDefaultServiceAccessControlProfile(
						permissionChecker.getCompanyId());
		}

		if ((defaultServiceAccessControlProfile == null) &&
			!accessControlled.guestAccessEnabled() &&
			((permissionChecker == null) || !permissionChecker.isSignedIn())) {

			throw new SecurityException("Authenticated access required");
		}
	}

}