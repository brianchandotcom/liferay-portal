/*
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

import com.liferay.portal.security.MethodSecurity;
import com.liferay.portal.security.RemoteMethodAccessType;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.security.permission.PermissionThreadLocal;

import java.lang.reflect.Method;

/**
 * @author Tomas Polesovsky
 * @author Igor Spasic
 * @author Michael C. Han
 */
public class ServiceAccessControlManagerImpl {
	public void accept(Method method, MethodSecurity methodSecurity)
		throws SecurityException {

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

}