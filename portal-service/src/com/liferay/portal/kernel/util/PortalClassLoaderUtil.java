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

import sun.reflect.Reflection;

/**
 * @author Brian Wing Shun Chan
 * @author Zsolt Berentey
 */
public class PortalClassLoaderUtil {

	public static ClassLoader getClassLoader() {
		SecurityManager securityManager = System.getSecurityManager();

		if (securityManager != null) {
			Class<?> callerClass = Reflection.getCallerClass(2);

			if (!_codeSourceKernel.equals(getCodeSourceLocation(callerClass)) &&
				!_codeSourceImpl.equals(getCodeSourceLocation(callerClass))) {

				Permission permission = new RuntimePermission(
					PACLConstants.RUNTIME_PERMISSION_GET_CLASSLOADER.concat(
					StringPool.PERIOD).concat("portal"));

				securityManager.checkPermission(permission);
			}
		}

		return _classLoader;
	}

	public static String getCodeSourceLocation(Class<?> clazz) {
		String className = clazz.getName();

		String resourceName =
			StringPool.SLASH + className.replace('.', '/') + ".class";

		URL location = clazz.getResource(resourceName);

		String codeSource = location.toString();

		return codeSource.substring(
			0, codeSource.length() - resourceName.length());
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

		_codeSourceKernel = getCodeSourceLocation(PortalClassLoaderUtil.class);

		Class<?> callerClass = Reflection.getCallerClass(2);

		String callerClassName = callerClass.getName();

		if (callerClassName.equals("com.liferay.portal.util.ClassLoaderUtil")) {
			_codeSourceImpl = getCodeSourceLocation(callerClass);
		}
	}

	private static ClassLoader _classLoader;
	private static String _codeSourceImpl;
	private static String _codeSourceKernel;

}