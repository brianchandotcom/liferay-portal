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
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.lang.PortalSecurityManagerThreadLocal;
import com.liferay.portal.security.pacl.PACLClassLoaderUtil;

import java.security.AccessController;
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
			if (!hasReflect(permission.getName(), permission.getActions())) {
				throw new SecurityException(
					"Attempted to access declared members");
			}
		}
		else if (name.equals(RUNTIME_PERMISSION_CREATE_CLASS_LOADER)) {
			if (PortalSecurityManagerThreadLocal.isCheckCreateClassLoader() &&
				!isJSPCompiler(permission.getName(), permission.getActions()) &&
				!hasCreateClassLoader()) {

				throw new SecurityException(
					"Attempted to create a class loader");
			}
		}
		else if (name.startsWith(RUNTIME_PERMISSION_GET_CLASSLOADER)) {
			if (PortalSecurityManagerThreadLocal.isCheckGetClassLoader() &&
				!hasGetClassLoader(name)) {

				throw new SecurityException("Attempted to get class loader");
			}
		}
		else if (name.equals(RUNTIME_PERMISSION_READ_FILE_DESCRIPTOR)) {
			if (PortalSecurityManagerThreadLocal.isCheckReadFileDescriptor()) {
				throw new SecurityException(
					"Attempted to read file descriptor");
			}
		}
		else if (name.equals(RUNTIME_PERMISSION_SET_CONTEXT_CLASS_LOADER)) {
		}
		else if (name.equals(RUNTIME_PERMISSION_SET_SECURITY_MANAGER)) {
			throw new SecurityException(
				"Attempted to set another security manager");
		}
		else if (name.equals(RUNTIME_PERMISSION_WRITE_FILE_DESCRIPTOR)) {
			if (PortalSecurityManagerThreadLocal.isCheckWriteFileDescriptor()) {
				throw new SecurityException(
					"Attempted to read file descriptor");
			}
		}
		else {
			if (_log.isDebugEnabled()) {
				Thread.dumpStack();
			}

			throw new SecurityException(
				"Attempted to " + permission.getName() + " on " +
					permission.getActions());
		}
	}

	protected boolean hasCreateClassLoader() {
		Class<?> callerClass10 = Reflection.getCallerClass(10);
		Class<?> callerClass11 = Reflection.getCallerClass(11);

		if (Validator.equals(
				callerClass10.getName(), _CLASS_NAME_CLASS_DEFINER_1) &&
			(callerClass11 == AccessController.class)) {

			Thread currentThread = Thread.currentThread();

			StackTraceElement[] stackTraceElements =
				currentThread.getStackTrace();

			StackTraceElement stackTraceElement = stackTraceElements[11];

			String methodName = stackTraceElement.getMethodName();

			if (methodName.equals(_METHOD_NAME_DO_PRIVILEGED)) {
				logCreateClassLoader(callerClass10, 10);

				return true;
			}
		}

		return false;
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
		Class<?> callerClass7 = Reflection.getCallerClass(7);

		if (_log.isDebugEnabled()) {
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
				if (_log.isInfoEnabled()) {
					_log.info(
						"Allowing " + callerClass7.getName() +
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

				if (_log.isInfoEnabled()) {
					_log.info(
						"Allowing " + callerClass7.getName() +
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

	protected void logCreateClassLoader(Class<?> callerClass, int frame) {
		if (_log.isInfoEnabled()) {
			_log.info(
				"Allowing frame " + frame + " with caller " + callerClass +
					" to create a class loader");
		}
	}

	private static final String _CLASS_NAME_CLASS_DEFINER_1 =
		"sun.reflect.ClassDefiner$1";

	private static final String _METHOD_NAME_DO_PRIVILEGED = "doPrivileged";

	private static final String _METHOD_NAME_GET_SYSTEM_CLASS_LOADER =
		"getSystemClassLoader";

	private static Log _log = LogFactoryUtil.getLog(RuntimeChecker.class);

	private Set<String> _classLoaderReferenceIds;

}