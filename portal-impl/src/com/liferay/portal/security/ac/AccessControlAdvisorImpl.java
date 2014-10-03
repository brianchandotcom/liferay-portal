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

package com.liferay.portal.security.ac;

import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.security.auth.AccessControlContext;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.security.permission.PermissionThreadLocal;
import com.liferay.portal.security.sso.SSOUtil;

import java.lang.reflect.Method;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Tomas Polesovsky
 * @author Igor Spasic
 * @author Michael C. Han
 * @author Raymond Augé
 */
public class AccessControlAdvisorImpl implements AccessControlAdvisor {

	@Override
	public void accept(Method method, AccessControlled accessControlled)
		throws SecurityException {

		if (accessControlled.hostAllowedValidationEnabled()) {
			checkAllowedHosts();
		}

		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		if (!accessControlled.guestAccessEnabled() &&
			((permissionChecker == null) || !permissionChecker.isSignedIn())) {

			throw new SecurityException("Authenticated access required");
		}
	}

	protected void checkAllowedHosts() {
		AccessControlContext accessControlContext =
			AccessControlUtil.getAccessControlContext();

		if (accessControlContext == null) {
			return;
		}

		HttpServletRequest request = accessControlContext.getRequest();

		String hostsAllowedString = MapUtil.getString(
			accessControlContext.getSettings(), "hosts.allowed");

		String[] hostsAllowed = StringUtil.split(hostsAllowedString);

		Set<String> hostsAllowedSet = SetUtil.fromArray(hostsAllowed);

		if (!SSOUtil.isAccessAllowed(request, hostsAllowedSet)) {
			throw new SecurityException(
				"Access denied for " + request.getRemoteAddr());
		}
	}

}