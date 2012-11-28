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
import com.liferay.portal.kernel.util.JavaDetector;
import com.liferay.portal.kernel.util.PathUtil;
import com.liferay.portal.kernel.util.ServerDetector;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.security.lang.PortalSecurityManagerThreadLocal;
import com.liferay.portal.security.pacl.PACLClassLoaderUtil;
import com.liferay.portal.security.pacl.PACLClassUtil;

import java.security.Permission;

import java.util.Set;
import java.util.TreeSet;

import org.apache.xerces.impl.dv.DatatypeException;
import org.apache.xerces.parsers.AbstractDOMParser;

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
				throwSecurityException(
					_log, "Attempted to access package " + pkg);
			}
		}
		else if (name.equals(RUNTIME_PERMISSION_ACCESS_DECLARED_MEMBERS)) {
			if (!hasReflect(permission)) {
				throwSecurityException(
					_log, "Attempted to access declared members");
			}
		}
		else if (name.equals(RUNTIME_PERMISSION_CREATE_CLASS_LOADER)) {
			if (PortalSecurityManagerThreadLocal.isCheckCreateClassLoader() &&
				!isJSPCompiler(permission.getName(), permission.getActions()) &&
				!hasCreateClassLoader()) {

				throwSecurityException(
					_log, "Attempted to create a class loader");
			}
		}
		else if (name.startsWith(RUNTIME_PERMISSION_GET_CLASSLOADER)) {
			if (PortalSecurityManagerThreadLocal.isCheckGetClassLoader() &&
				!isJSPCompiler(permission.getName(), permission.getActions()) &&
				!hasGetClassLoader(name)) {

				throwSecurityException(_log, "Attempted to get class loader");
			}
		}
		else if (name.startsWith(RUNTIME_PERMISSION_GET_PROTECTION_DOMAIN)) {
			if (!hasGetProtectionDomain()) {
				throwSecurityException(
					_log, "Attempted to get protection domain");
			}
		}
		else if (name.startsWith(RUNTIME_PERMISSION_GET_ENV)) {
			int pos = name.indexOf(StringPool.PERIOD);

			String envName = name.substring(pos + 1);

			if (!hasGetEnv(envName)) {
				throwSecurityException(
					_log, "Attempted to get environment name " + envName);
			}
		}
		else if (name.equals(RUNTIME_PERMISSION_READ_FILE_DESCRIPTOR)) {
			if (PortalSecurityManagerThreadLocal.isCheckReadFileDescriptor() &&
				!hasReadFileDescriptor()) {

				throwSecurityException(
					_log, "Attempted to read file descriptor");
			}
		}
		else if (name.equals(RUNTIME_PERMISSION_SET_CONTEXT_CLASS_LOADER)) {
		}
		else if (name.equals(RUNTIME_PERMISSION_SET_SECURITY_MANAGER)) {
			throwSecurityException(
				_log, "Attempted to set another security manager");
		}
		else if (name.equals(RUNTIME_PERMISSION_WRITE_FILE_DESCRIPTOR)) {
			if (PortalSecurityManagerThreadLocal.isCheckWriteFileDescriptor() &&
				!hasWriteFileDescriptor()) {

				throwSecurityException(
					_log, "Attempted to write file descriptor");
			}
		}
		else {
			if (_log.isDebugEnabled()) {
				Thread.dumpStack();
			}

			throwSecurityException(
				_log,
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
		if (JavaDetector.isIBM()) {
			Class<?> callerClass9 = Reflection.getCallerClass(9);

			String callerClassName9 = callerClass9.getName();

			if (callerClassName9.startsWith(_CLASS_NAME_CLASS_DEFINER) &&
				CheckerUtil.isAccessControllerDoPrivileged(10)) {

				logCreateClassLoader(callerClass9, 9);

				return true;
			}

			Class<?> callerClass10 = Reflection.getCallerClass(10);

			String callerClassName10 = callerClass10.getName();

			if (callerClassName10.startsWith(_CLASS_NAME_CLASS_DEFINER) &&
				CheckerUtil.isAccessControllerDoPrivileged(11)) {

				logCreateClassLoader(callerClass10, 10);

				return true;
			}
		}
		else if (JavaDetector.isJDK7()) {
			Class<?> callerClass11 = Reflection.getCallerClass(11);

			String callerClassName11 = callerClass11.getName();

			if (callerClassName11.startsWith(_CLASS_NAME_CLASS_DEFINER) &&
				CheckerUtil.isAccessControllerDoPrivileged(12)) {

				logCreateClassLoader(callerClass11, 11);

				return true;
			}
		}
		else {
			Class<?> callerClass10 = Reflection.getCallerClass(10);

			String callerClassName10 = callerClass10.getName();

			if (callerClassName10.startsWith(_CLASS_NAME_CLASS_DEFINER) &&
				CheckerUtil.isAccessControllerDoPrivileged(11)) {

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

			if (referenceId.equals("portal")) {
				Class<?> callerClass7 = Reflection.getCallerClass(7);

				if (isTrustedCallerClass(callerClass7)) {
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

			logGetClassLoader(callerClass7, 7);

			return true;
		}

		if (callerClass6 == Class.class) {
			if (isJBossMessages(callerClass7) ||
				isJBossServiceControllerImpl(callerClass7) ||
				isJOnASModuleImpl(callerClass7) ||
				isTomcatJdbcLeakPrevention(callerClass7)) {

				logGetClassLoader(callerClass7, 7);

				return true;
			}

			if (isWebSphereProtectionClassLoader(
					callerClass7.getEnclosingClass()) &&
				CheckerUtil.isAccessControllerDoPrivileged(8)) {

				logGetClassLoader(callerClass7, 7);

				return true;
			}
		}
		else if (callerClass6 == ClassLoader.class) {
			Class<?> callerClass8 = Reflection.getCallerClass(8);

			if (isGlassfishAPIClassLoaderServiceImpl(
					callerClass8.getEnclosingClass()) &&
				CheckerUtil.isAccessControllerDoPrivileged(9)) {

				logGetClassLoader(callerClass8, 8);

				return true;
			}

			if (isResinEnvironmentLocal(callerClass7)) {
				logGetClassLoader(callerClass7, 7);

				return true;
			}

			if (isWebLogicGenericClassLoader(
					callerClass7.getEnclosingClass()) &&
				CheckerUtil.isAccessControllerDoPrivileged(8)) {

				logGetClassLoader(callerClass7, 7);

				return true;
			}

			if (isXercesSecuritySupport(callerClass7) &&
				CheckerUtil.isAccessControllerDoPrivileged(8)) {

				logGetClassLoader(callerClass8, 8);

				return true;
			}

			Thread currentThread = Thread.currentThread();

			StackTraceElement[] stackTraceElements =
				currentThread.getStackTrace();

			StackTraceElement stackTraceElement = null;

			if (JavaDetector.isIBM()) {
				stackTraceElement = stackTraceElements[7];
			}
			else {
				stackTraceElement = stackTraceElements[6];
			}

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
			boolean allow = false;

			ClassLoader contextClassLoader =
				PACLClassLoaderUtil.getContextClassLoader();
			ClassLoader portalClassLoader = getPortalClassLoader();

			if (contextClassLoader == portalClassLoader) {
				if (PACLClassLoaderUtil.getClassLoader(callerClass7) !=
						getClassLoader()) {

					allow = true;
				}
			}
			else {
				allow = true;
			}

			if (allow) {
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

		if (ServerDetector.isWebSphere()) {
			if (name.equals("USER_INSTALL_ROOT")) {
				return true;
			}
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
		if (JavaDetector.isJDK7()) {
			Class<?> callerClass9 = Reflection.getCallerClass(9);

			String callerClassName9 = callerClass9.getName();

			if (callerClassName9.startsWith(_CLASS_NAME_PROCESS_IMPL) &&
				CheckerUtil.isAccessControllerDoPrivileged(10)) {

				logWriteFileDescriptor(callerClass9, 9);

				return true;
			}
		}
		else {
			Class<?> callerClass8 = Reflection.getCallerClass(8);

			String callerClassName8 = callerClass8.getName();

			if (callerClassName8.startsWith(_CLASS_NAME_PROCESS_IMPL) &&
				CheckerUtil.isAccessControllerDoPrivileged(9)) {

				logWriteFileDescriptor(callerClass8, 8);

				return true;
			}
		}

		return false;
	}

	protected boolean hasWriteFileDescriptor() {
		if (JavaDetector.isJDK7()) {
			Class<?> callerClass9 = Reflection.getCallerClass(9);

			String callerClassName9 = callerClass9.getName();

			if (callerClassName9.startsWith(_CLASS_NAME_PROCESS_IMPL) &&
				CheckerUtil.isAccessControllerDoPrivileged(10)) {

				logWriteFileDescriptor(callerClass9, 9);

				return true;
			}
		}
		else {
			Class<?> callerClass8 = Reflection.getCallerClass(8);

			String callerClassName8 = callerClass8.getName();

			if (callerClassName8.startsWith(_CLASS_NAME_PROCESS_IMPL) &&
				CheckerUtil.isAccessControllerDoPrivileged(9)) {

				logWriteFileDescriptor(callerClass8, 8);

				return true;
			}
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

	protected boolean isGlassfishAPIClassLoaderServiceImpl(Class<?> clazz) {
		if (!ServerDetector.isGlassfish()) {
			return false;
		}

		if (clazz == null) {
			return false;
		}

		clazz = clazz.getEnclosingClass();

		if (clazz == null) {
			return false;
		}

		String className = clazz.getName();

		if (!className.equals(_CLASS_NAME_API_CLASS_LOADER_SERVICE_IMPL)) {
			return false;
		}

		String classLocation = PACLClassUtil.getClassLocation(clazz);

		return classLocation.startsWith("bundle://");
	}

	protected boolean isJBossMessages(Class<?> clazz) {
		if (!ServerDetector.isJBoss()) {
			return false;
		}

		String className = clazz.getName();

		if (!className.equals(_CLASS_NAME_MESSAGES)) {
			return false;
		}

		String classLocation = PACLClassUtil.getClassLocation(clazz);

		return classLocation.contains(
			"/modules/org/jboss/logging/main/jboss-logging-");
	}

	protected boolean isJBossServiceControllerImpl(Class<?> clazz) {
		if (!ServerDetector.isJBoss()) {
			return false;
		}

		String className = clazz.getName();

		if (!className.equals(_CLASS_NAME_SERVICE_CONTROLLER_IMPL)) {
			return false;
		}

		String classLocation = PACLClassUtil.getClassLocation(clazz);

		return classLocation.contains("/modules/org/jboss/msc/main/jboss-msc-");
	}

	protected boolean isJOnASModuleImpl(Class<?> clazz) {
		if (!ServerDetector.isJOnAS()) {
			return false;
		}

		String className = clazz.getName();

		if (!className.equals(_CLASS_NAME_MODULE_IMPL)) {
			return false;
		}

		String classLocation = PACLClassUtil.getClassLocation(clazz);

		return classLocation.contains("/lib/bootstrap/felix-launcher.jar!/");
	}

	protected boolean isResinEnvironmentLocal(Class<?> clazz) {
		if (!ServerDetector.isResin()) {
			return false;
		}

		String className = clazz.getName();

		if (!className.equals(_CLASS_NAME_ENVIRONMENT_LOCAL)) {
			return false;
		}

		String actualClassLocation = PACLClassUtil.getClassLocation(clazz);
		String expectedClassLocation = PathUtil.toUnixPath(
			System.getProperty("resin.home") + "/lib/resin.jar!/");

		return actualClassLocation.contains(expectedClassLocation);
	}

	protected boolean isTomcatJdbcLeakPrevention(Class<?> clazz) {
		if (!ServerDetector.isTomcat()) {
			return false;
		}

		String className = clazz.getName();

		if (!className.equals(_CLASS_NAME_JDBC_LEAK_PREVENTION)) {
			return false;
		}

		String actualClassLocation = PACLClassUtil.getClassLocation(clazz);

		String expectedClassLocation = PathUtil.toUnixPath(
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

	protected boolean isWebLogicGenericClassLoader(Class<?> clazz) {
		if (!ServerDetector.isWebLogic()) {
			return false;
		}

		if (clazz == null) {
			return false;
		}

		String className = clazz.getName();

		if (!className.equals(_CLASS_NAME_GENERIC_CLASS_LOADER)) {
			return false;
		}

		String classLocation = PACLClassUtil.getClassLocation(clazz);

		if (classLocation.contains(
				"/modules/com.bea.core.utils.classloaders_") ||
			classLocation.contains("/patch_jars/BUG")) {

			return true;
		}

		return false;
	}

	protected boolean isWebSphereProtectionClassLoader(Class<?> clazz) {
		if (!ServerDetector.isWebSphere()) {
			return false;
		}

		if (clazz == null) {
			return false;
		}

		String className = clazz.getName();

		if (!className.equals(_CLASS_NAME_PROTECTION_CLASS_LOADER)) {
			return false;
		}

		String classLocation = PACLClassUtil.getClassLocation(clazz);

		return classLocation.startsWith("bundleresource://");
	}

	protected boolean isXercesSecuritySupport(Class<?> clazz) {
		String className = clazz.getName();

		if (className.contains(".SecuritySupport$") &&
			((clazz.getPackage() == AbstractDOMParser.class.getPackage()) ||
			 (clazz.getPackage() == DatatypeException.class.getPackage()))) {

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

	protected void logGetClassLoader(Class<?> callerClass, int frame) {
		if (_log.isInfoEnabled()) {
			_log.info(
				"Allowing frame " + frame + " with caller " + callerClass +
					" to get the class loader");
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

	private static final String _CLASS_NAME_API_CLASS_LOADER_SERVICE_IMPL =
		"com.sun.enterprise.v3.server.APIClassLoaderServiceImpl";

	private static final String _CLASS_NAME_CLASS_DEFINER =
		"sun.reflect.ClassDefiner$";

	private static final String _CLASS_NAME_DEFAULT_MBEAN_SERVER_INTERCEPTOR =
		"com.sun.jmx.interceptor.DefaultMBeanServerInterceptor";

	private static final String _CLASS_NAME_ENVIRONMENT_LOCAL =
		"com.caucho.loader.EnvironmentLocal";

	private static final String _CLASS_NAME_GENERIC_CLASS_LOADER =
		"weblogic.utils.classloaders.GenericClassLoader";

	private static final String _CLASS_NAME_JDBC_LEAK_PREVENTION =
		"org.apache.catalina.loader.JdbcLeakPrevention";

	private static final String _CLASS_NAME_MESSAGES =
		"org.jboss.logging.Messages";

	private static final String _CLASS_NAME_MODULE_IMPL =
		"org.apache.felix.framework.ModuleImpl";

	private static final String _CLASS_NAME_PROCESS_IMPL =
		"java.lang.ProcessImpl$";

	private static final String _CLASS_NAME_PROTECTION_CLASS_LOADER =
		"com.ibm.ws.classloader.ProtectionClassLoader";

	private static final String _CLASS_NAME_SERVICE_CONTROLLER_IMPL =
		"org.jboss.msc.service.ServiceControllerImpl";

	private static final String _METHOD_NAME_GET_SYSTEM_CLASS_LOADER =
		"getSystemClassLoader";

	private static Log _log = LogFactoryUtil.getLog(RuntimeChecker.class);

	private Set<String> _classLoaderReferenceIds;

}