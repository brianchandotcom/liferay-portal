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

package com.liferay.portal.security.pacl.checker;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.security.lang.PortalSecurityManagerThreadLocal;
import com.liferay.portal.security.pacl.PACLClassLoaderUtil;

import java.security.Permission;

import java.util.Set;
import java.util.TreeSet;

import sun.reflect.Reflection;

/**
 * @author Raymond Augé
 * @author Brian Wing Shun Chan
 */
public class RuntimeChecker extends BaseReflectChecker {

	public void afterPropertiesSet() {
		initClassLoaderReferenceIds();
	}

	public void checkPermission(Permission permission) {
		String name = permission.getName();

		if (name.startsWith(RUNTIME_PERMISSION_ACCESS_CLASS_IN_PACKAGE)) {
			int pos = name.indexOf(StringPool.PERIOD);

			String pkg = name.substring(pos + 1);

			if (!hasPackageAccess(pkg)) {
				throw new SecurityException(
					"Attempted to access package " + pkg);
			}
		}
		else if (name.equals(RUNTIME_PERMISSION_ACCESS_DECLARED_MEMBERS)) {
			if (!hasRelect(permission.getName(), permission.getActions())) {
				throw new SecurityException(
					"Attempted to access declared members");
			}
		}
		else if (name.startsWith(RUNTIME_PERMISSION_GET_CLASSLOADER)) {
			if (PortalSecurityManagerThreadLocal.
					isCheckGetClassLoaderEnabled() &&
				!hasGetClassLoader(name)) {

				throw new SecurityException("Attempted to get class loader");
			}
		}
		else if (name.equals(RUNTIME_PERMISSION_SET_SECURITY_MANAGER)) {
			throw new SecurityException(
				"Attempted to set another security manager");
		}
		else if (name.equals(RUNTIME_PERMISSION_EXIT_VM)) {
			Thread.dumpStack();

			throw new SecurityException("Attempted to shutdown the VM");
		}
	}

	protected boolean hasGetClassLoader(String name) {
		int pos = name.indexOf(StringPool.PERIOD);

		if (pos != -1) {
			String referenceId = name.substring(pos + 1);

			if (_classLoaderReferenceIds.contains(referenceId)) {
				return true;
			}

			return false;
		}

		Class<?> callerClass6 = Reflection.getCallerClass(6);
		Class<?> callerClass7 = null;

		if (_log.isDebugEnabled()) {
			callerClass7 = Reflection.getCallerClass(7);

			_log.debug(
				callerClass7.getName() +
					" is attempting to get the class loader via " +
						callerClass6.getName());
		}

		if (callerClass6 == Class.class) {
		}
		else if (callerClass6 == ClassLoader.class) {
			Thread currentThread = Thread.currentThread();

			StackTraceElement[] stackTraceElements =
				currentThread.getStackTrace();

			StackTraceElement stackTraceElement = stackTraceElements[6];

			String methodName = stackTraceElement.getMethodName();

			if (methodName.equals(_METHOD_NAME_GET_SYSTEM_CLASS_LOADER)) {
				if (_log.isDebugEnabled()) {
					_log.debug(
						"Allow " + callerClass7.getName() +
							" to access the system class loader");
				}

				return true;
			}
		}
		else if (callerClass6 == Thread.class) {
			ClassLoader contextClassLoader =
				PACLClassLoaderUtil.getContextClassLoader();

			if (isSharedClassLoader(contextClassLoader) ||
				isLocalClassLoader(contextClassLoader)) {

				if (_log.isDebugEnabled()) {
					callerClass7 = Reflection.getCallerClass(7);

					_log.debug(
						"Allow " + callerClass7.getName() +
							" to access the context class loader");
				}

				return true;
			}
		}

		return false;
	}

	protected boolean hasPackageAccess(String pkg) {

		// TODO

		if (pkg.startsWith("sun.reflect")) {
		}

		return true;
	}

	protected void initClassLoaderReferenceIds() {
		_classLoaderReferenceIds = getPropertySet(
			"security-manager-class-loader-reference-ids");

		if (_log.isDebugEnabled()) {
			Set<String> referenceIds = new TreeSet<String>(
				_classLoaderReferenceIds);

			for (String referenceId : referenceIds) {
				_log.debug(
					"Allowing access to class loader for reference " +
						referenceId);
			}
		}
	}

	protected boolean isLocalClassLoader(ClassLoader classLoader) {
		if (classLoader == getClassLoader()) {
			return true;
		}

		return false;
	}

	protected boolean isSharedClassLoader(ClassLoader classLoader) {
		if ((classLoader == null) || (classLoader == getCommonClassLoader()) ||
			(classLoader == getSystemClassLoader())) {

			return true;
		}

		return false;
	}

	private static final String _METHOD_NAME_GET_SYSTEM_CLASS_LOADER =
		"getSystemClassLoader";

	private static Log _log = LogFactoryUtil.getLog(RuntimeChecker.class);

	private Set<String> _classLoaderReferenceIds;

}