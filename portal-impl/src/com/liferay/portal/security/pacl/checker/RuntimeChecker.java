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
import com.liferay.portal.kernel.messaging.BaseAsyncDestination;
import com.liferay.portal.kernel.util.PathUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.security.lang.PortalSecurityManagerThreadLocal;
import com.liferay.portal.security.pacl.PACLClassLoaderUtil;
import com.liferay.portal.security.pacl.PACLClassUtil;

import java.security.Permission;

import java.util.Set;
import java.util.TreeSet;

import org.springframework.beans.CachedIntrospectionResults;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.util.ClassUtils;

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

			if (!hasAccessClassInPackage(pkg)) {
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
		else if (name.startsWith(RUNTIME_PERMISSION_GET_PROTECTION_DOMAIN)) {
			if (!hasGetProtectionDomain()) {
				throw new SecurityException(
					"Attempted to get protection domain");
			}
		}
		else if (name.startsWith(RUNTIME_PERMISSION_GET_ENV)) {
			int pos = name.indexOf(StringPool.PERIOD);

			String envName = name.substring(pos + 1);

			if (!hasGetEnv(envName)) {
				throw new SecurityException(
					"Attempted to get environment name " + envName);
			}
		}
		else if (name.equals(RUNTIME_PERMISSION_READ_FILE_DESCRIPTOR)) {
			if (PortalSecurityManagerThreadLocal.isCheckReadFileDescriptor() &&
				!hasReadFileDescriptor()) {

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
			if (PortalSecurityManagerThreadLocal.isCheckWriteFileDescriptor() &&
				!hasWriteFileDescriptor()) {

				throw new SecurityException(
					"Attempted to write file descriptor");
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

	protected boolean hasAccessClassInPackage(String pkg) {

		// TODO

		if (pkg.startsWith("sun.reflect")) {
		}

		return true;
	}

	protected boolean hasCreateClassLoader() {
		Class<?> callerClass10 = Reflection.getCallerClass(10);

		String callerClassName10 = callerClass10.getName();

		if (callerClassName10.startsWith(_CLASS_NAME_CLASS_DEFINER) &&
			CheckerUtil.isAccessControllerDoPrivileged(11)) {

			logCreateClassLoader(callerClass10, 10);

			return true;
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

			if (referenceId.equals("portal")) {
				Class<?> callerClass7 = Reflection.getCallerClass(7);

				if (callerClass7 == BaseAsyncDestination.class) {
					return true;
				}
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

		if ((callerClass7 == CachedIntrospectionResults.class) ||
			(callerClass7 == ClassUtils.class) ||
			(callerClass7.getEnclosingClass() ==
				LocalVariableTableParameterNameDiscoverer.class)) {

			if (_log.isInfoEnabled()) {
				_log.info(
					"Allowing " + callerClass7.getName() +
						" to get the class loader");
			}

			return true;
		}

		if (callerClass6 == Class.class) {
			if (isTomcatJdbcLeakPrevention(callerClass7)) {
				return true;
			}
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
							" to get the system class loader");
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

	protected boolean hasGetEnv(String name) {
		Class<?> callerClass7 = Reflection.getCallerClass(7);

		if (callerClass7 == AbstractApplicationContext.class) {
			logGetEnv(callerClass7, 7, name);

			return true;
		}

		return false;
	}

	protected boolean hasGetProtectionDomain() {
		Class<?> callerClass8 = Reflection.getCallerClass(8);

		if (isDefaultMBeanServerInterceptor(
				callerClass8.getEnclosingClass()) &&
			CheckerUtil.isAccessControllerDoPrivileged(9)) {

			logGetProtectionDomain(callerClass8, 8);

			return true;
		}

		return false;
	}

	protected boolean hasReadFileDescriptor() {
		Class<?> callerClass8 = Reflection.getCallerClass(8);

		String callerClassName8 = callerClass8.getName();

		if (callerClassName8.startsWith(_CLASS_NAME_PROCESS_IMPL) &&
			CheckerUtil.isAccessControllerDoPrivileged(9)) {

			logWriteFileDescriptor(callerClass8, 8);

			return true;
		}

		return false;
	}

	protected boolean hasWriteFileDescriptor() {
		Class<?> callerClass8 = Reflection.getCallerClass(8);

		String callerClassName8 = callerClass8.getName();

		if (callerClassName8.startsWith(_CLASS_NAME_PROCESS_IMPL) &&
			CheckerUtil.isAccessControllerDoPrivileged(9)) {

			logWriteFileDescriptor(callerClass8, 8);

			return true;
		}

		return false;
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

	protected boolean isDefaultMBeanServerInterceptor(Class<?> clazz) {
		String className = clazz.getName();

		if (!className.equals(_CLASS_NAME_DEFAULT_MBEAN_SERVER_INTERCEPTOR)) {
			return false;
		}

		String classLocation = PACLClassUtil.getClassLocation(clazz);

		if (classLocation.length() > 0) {
			return false;
		}

		return true;
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

	protected boolean isTomcatJdbcLeakPrevention(Class<?> clazz) {
		String className = clazz.getName();

		if (!className.equals(_JDBC_LEAK_PREVENTION)) {
			return false;
		}

		String actualClassLocation = PACLClassUtil.getClassLocation(clazz);

		String expectedClassLocation =
			PathUtil.toUnixPath(
				System.getProperty("catalina.base") + "/lib/catalina.jar!/");

		expectedClassLocation += StringUtil.replace(
			className, StringPool.PERIOD, StringPool.SLASH);
		expectedClassLocation += ".class";

		if (_log.isDebugEnabled()) {
			_log.debug("Actual class location " + actualClassLocation);
			_log.debug("Expected class location " + expectedClassLocation);
		}

		return actualClassLocation.endsWith(expectedClassLocation);
	}

	protected void logCreateClassLoader(Class<?> callerClass, int frame) {
		if (_log.isInfoEnabled()) {
			_log.info(
				"Allowing frame " + frame + " with caller " + callerClass +
					" to create a class loader");
		}
	}

	protected void logGetEnv(Class<?> callerClass, int frame, String name) {
		if (_log.isInfoEnabled()) {
			_log.info(
				"Allowing frame " + frame + " with caller " + callerClass +
					" to get environment " + name);
		}
	}

	protected void logGetProtectionDomain(Class<?> callerClass, int frame) {
		if (_log.isInfoEnabled()) {
			_log.info(
				"Allowing frame " + frame + " with caller " + callerClass +
					" to get the protection domain");
		}
	}

	protected void logReadFileDescriptor(Class<?> callerClass, int frame) {
		if (_log.isInfoEnabled()) {
			_log.info(
				"Allowing frame " + frame + " with caller " + callerClass +
					" to read a file descriptor");
		}
	}

	protected void logWriteFileDescriptor(Class<?> callerClass, int frame) {
		if (_log.isInfoEnabled()) {
			_log.info(
				"Allowing frame " + frame + " with caller " + callerClass +
					" to write a file descriptor");
		}
	}

	private static final String _CLASS_NAME_CLASS_DEFINER =
		"sun.reflect.ClassDefiner$";

	private static final String _CLASS_NAME_DEFAULT_MBEAN_SERVER_INTERCEPTOR =
		"com.sun.jmx.interceptor.DefaultMBeanServerInterceptor";

	private static final String _CLASS_NAME_PROCESS_IMPL =
		"java.lang.ProcessImpl$";

	private static final String _JDBC_LEAK_PREVENTION =
		"org.apache.catalina.loader.JdbcLeakPrevention";

	private static final String _METHOD_NAME_GET_SYSTEM_CLASS_LOADER =
		"getSystemClassLoader";

	private static Log _log = LogFactoryUtil.getLog(RuntimeChecker.class);

	private Set<String> _classLoaderReferenceIds;

}