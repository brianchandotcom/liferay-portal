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

package com.liferay.portal.security.sac;

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.security.MethodSecurity;
import com.liferay.portal.security.RemoteMethodAccessType;
import com.liferay.portal.security.auth.AuthSettingsUtil;
import com.liferay.portal.security.auth.AuthenticationContext;
import com.liferay.portal.security.auth.PortalAAManager;
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
 */
public class ServiceAccessControlManagerImpl {
	public void accept(Method method, MethodSecurity methodSecurity)
		throws SecurityException {

		checkAllowedHosts();

		RemoteMethodAccessType remoteMethodAccessType =
			methodSecurity.remoteMethodAccessType();

		if (remoteMethodAccessType == RemoteMethodAccessType.AUTHENTICATED) {
			PermissionChecker permissionChecker =
				PermissionThreadLocal.getPermissionChecker();

			if ((permissionChecker == null) ||
				!permissionChecker.isSignedIn()) {

				throw new SecurityException("Authenticated access required.");
			}
		}
	}

	protected void checkAllowedHosts() {
		PortalAAManager portalAAManager = PortalAAManager.getInstance();

		AuthenticationContext authenticationContext =
			portalAAManager.getAuthenticationContext();

		if (authenticationContext == null) {
			// TODO: AuthVerificationFilter is not mapped to all URLs!!!!
			return;
		}

		Map<String, Object> properties = authenticationContext.getSettings();

		String[] hostsAllowed = StringUtil.split(
			GetterUtil.getString(properties.get("hosts.allowed")));

		Set<String> hostsAllowedSet = SetUtil.fromArray(hostsAllowed);

		HttpServletRequest httpServletRequest =
			authenticationContext.getHttpServletRequest();

		boolean accessAllowed = AuthSettingsUtil.isAccessAllowed(
			httpServletRequest, hostsAllowedSet);

		if (!accessAllowed) {
			throw new SecurityException(
				"Access denied for " + httpServletRequest.getRemoteAddr());
		}
	}

}