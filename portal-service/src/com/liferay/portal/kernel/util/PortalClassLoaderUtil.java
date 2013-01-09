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

package com.liferay.portal.kernel.util;

import com.liferay.portal.kernel.security.pacl.PACLConstants;
import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;

import java.net.URL;
import java.net.URLClassLoader;

import java.security.Permission;

/**
 * @author Brian Wing Shun Chan
 */
public class PortalClassLoaderUtil {

	public static ClassLoader getClassLoader() {
		SecurityManager securityManager = System.getSecurityManager();

		if (securityManager != null) {
			Permission permission = new RuntimePermission(
				PACLConstants.RUNTIME_PERMISSION_GET_CLASSLOADER.concat(
					StringPool.PERIOD).concat("portal"));

			securityManager.checkPermission(permission);
		}

		return _classLoader;
	}

	public static void setClassLoader(ClassLoader classLoader) {
		PortalRuntimePermission.checkSetBeanProperty(
			PortalClassLoaderUtil.class);

		if (ServerDetector.isJOnAS() && JavaDetector.isJDK6()) {
			_classLoader = new URLClassLoader(new URL[0], classLoader);
		}
		else {
			_classLoader = classLoader;
		}
	}

	private static ClassLoader _classLoader;

}