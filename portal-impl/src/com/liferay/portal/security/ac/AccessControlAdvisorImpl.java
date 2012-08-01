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

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.security.auth.AccessControlContext;
import com.liferay.portal.security.auth.AuthSettingsUtil;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.security.permission.PermissionThreadLocal;

import java.lang.reflect.Method;

import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Tomas Polesovsky
 * @author Igor Spasic
 * @author Michael C. Han
 * @author Raymond Augé
 */
public class AccessControlAdvisorImpl implements AccessControlAdvisor {

	public void accept(Method method, AccessControlled accessControlled)
		throws SecurityException {

		checkAllowedHosts();

		AccessControlType remoteMethodAccessType =
			accessControlled.accessControlType();

		if (remoteMethodAccessType == AccessControlType.AUTHENTICATED) {
			PermissionChecker permissionChecker =
				PermissionThreadLocal.getPermissionChecker();

			if ((permissionChecker == null) ||
				!permissionChecker.isSignedIn()) {

				throw new SecurityException("Authenticated access required.");
			}
		}
	}

	protected void checkAllowedHosts() {
		AccessControlContext accessControlContext =
			AccessControlUtil.getAccessControlContext();

		if (accessControlContext == null) {

			// TODO: AuthVerificationFilter is not mapped to all URLs!!!!

			return;
		}

		Map<String, Object> settings = accessControlContext.getSettings();

		String[] hostsAllowed = StringUtil.split(
			GetterUtil.getString(settings.get("hosts.allowed")));

		Set<String> hostsAllowedSet = SetUtil.fromArray(hostsAllowed);

		HttpServletRequest httpServletRequest =
			accessControlContext.getHttpServletRequest();

		boolean accessAllowed = AuthSettingsUtil.isAccessAllowed(
			httpServletRequest, hostsAllowedSet);

		if (!accessAllowed) {
			throw new SecurityException(
				"Access denied for " + httpServletRequest.getRemoteAddr());
		}
	}

}