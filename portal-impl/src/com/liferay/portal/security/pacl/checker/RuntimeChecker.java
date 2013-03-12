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
import com.liferay.portal.kernel.util.ServerDetector;
import com.liferay.portal.kernel.util.StringPool;

import java.security.AccessController;
import java.security.Permission;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.context.support.AbstractApplicationContext;

import sun.reflect.Reflection;

/**
 * @author Raymond Augé
 * @author Brian Wing Shun Chan
 */
public class RuntimeChecker extends BaseChecker {

	public void afterPropertiesSet() {
		initClassLoaderReferenceIds();
		initEnvironmentVariables();
	}

	@Override
	public AuthorizationProperty generateAuthorizationProperty(
		Object... arguments) {

		if ((arguments == null) || (arguments.length != 1) ||
			!(arguments[0] instanceof Permission)) {

			return null;
		}

		Permission permission = (Permission)arguments[0];

		String name = permission.getName();

		String key = null;
		String value = null;

		if (name.startsWith(RUNTIME_PERMISSION_GET_CLASSLOADER)) {
			key = "security-manager-class-loader-reference-ids";

			if (name.equals(RUNTIME_PERMISSION_GET_CLASSLOADER)) {
				value = "portal";
			}
			else {
				value = name.substring(
					RUNTIME_PERMISSION_GET_CLASSLOADER.length() + 1);
			}
		}
		else if (name.startsWith(RUNTIME_PERMISSION_GET_ENV)) {
			key = "security-manager-environment-variables";

			value = name.substring(RUNTIME_PERMISSION_GET_ENV.length() + 1);

			// Since we are using a regular expression, we cannot allow a lone *
			// as the rule

			if (value.equals(StringPool.STAR)) {
				value = StringPool.DOUBLE_BACK_SLASH + value;
			}
		}
		else {
			return null;
		}

		AuthorizationProperty authorizationProperty =
			new AuthorizationProperty();

		authorizationProperty.setKey(key);
		authorizationProperty.setValue(value);

		return authorizationProperty;
	}

	public boolean implies(Permission permission) {
		String name = permission.getName();

		if (name.startsWith(RUNTIME_PERMISSION_ACCESS_CLASS_IN_PACKAGE)) {
			int pos = name.indexOf(StringPool.PERIOD);

			String pkg = name.substring(pos + 1);

			if (!hasAccessClassInPackage(pkg)) {
				logSecurityException(
					_log, "Attempted to access package " + pkg);

				return false;
			}
		}
		else if (name.equals(RUNTIME_PERMISSION_ACCESS_DECLARED_MEMBERS)) {

			// Temporarily return true

			return true;
		}
		else if (name.equals(RUNTIME_PERMISSION_CREATE_CLASS_LOADER)) {
			if (!hasCreateClassLoader()) {
				logSecurityException(
					_log, "Attempted to create a class loader");

				return false;
			}
		}
		else if (name.equals(RUNTIME_PERMISSION_CREATE_SECURITY_MANAGER)) {
			if (!hasCreateSecurityManager()) {
				logSecurityException(
					_log, "Attempted to create a security manager");

				return false;
			}
		}
		else if (name.startsWith(RUNTIME_PERMISSION_GET_CLASSLOADER)) {
			if (!hasGetClassLoader(name)) {
				logSecurityException(_log, "Attempted to get class loader");

				return false;
			}
		}
		else if (name.startsWith(RUNTIME_PERMISSION_GET_PROTECTION_DOMAIN)) {
			if (!hasGetProtectionDomain()) {
				logSecurityException(
					_log, "Attempted to get protection domain");

				return false;
			}
		}
		else if (name.startsWith(RUNTIME_PERMISSION_GET_ENV)) {
			int pos = name.indexOf(StringPool.PERIOD);

			String envName = name.substring(pos + 1);

			if (!hasGetEnv(envName)) {
				logSecurityException(
					_log, "Attempted to get environment name " + envName);

				return false;
			}
		}
		else if (name.startsWith(RUNTIME_PERMISSION_LOAD_LIBRARY)) {
			if (!hasLoadLibrary()) {
				logSecurityException(_log, "Attempted to load library");

				return false;
			}
		}
		else if (name.equals(RUNTIME_PERMISSION_READ_FILE_DESCRIPTOR)) {
			if (!hasReadFileDescriptor()) {
				logSecurityException(_log, "Attempted to read file descriptor");

				return false;
			}
		}
		else if (name.equals(RUNTIME_PERMISSION_SET_CONTEXT_CLASS_LOADER)) {
		}
		else if (name.equals(RUNTIME_PERMISSION_SET_SECURITY_MANAGER)) {
			logSecurityException(
				_log, "Attempted to set another security manager");

			return false;
		}
		else if (name.equals(RUNTIME_PERMISSION_WRITE_FILE_DESCRIPTOR)) {
			if (!hasWriteFileDescriptor()) {
				logSecurityException(
					_log, "Attempted to write file descriptor");

				return false;
			}
		}
		else {
			if (_log.isDebugEnabled()) {
				Thread.dumpStack();
			}

			logSecurityException(
				_log, "Attempted to " + permission.getName() + " on " +
					permission.getActions());

			return false;
		}

		return true;
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

			if (callerClassName9.startsWith(_CLASS_NAME_CLASS_DEFINER)) {
				logCreateClassLoader(callerClass9, 9);

				return true;
			}

			Class<?> callerClass10 = Reflection.getCallerClass(10);

			String callerClassName10 = callerClass10.getName();

			if (callerClassName10.startsWith(_CLASS_NAME_CLASS_DEFINER)) {
				logCreateClassLoader(callerClass10, 10);

				return true;
			}
		}
		else if (JavaDetector.isJDK7()) {
			Class<?> callerClass11 = Reflection.getCallerClass(11);

			String callerClassName11 = callerClass11.getName();

			if (callerClassName11.startsWith(_CLASS_NAME_CLASS_DEFINER)) {
				logCreateClassLoader(callerClass11, 11);

				return true;
			}
		}
		else {
			Class<?> callerClass10 = Reflection.getCallerClass(10);

			String callerClassName10 = callerClass10.getName();

			if (callerClassName10.startsWith(_CLASS_NAME_CLASS_DEFINER)) {
				logCreateClassLoader(callerClass10, 10);

				return true;
			}
		}

		return false;
	}

	protected boolean hasCreateSecurityManager() {
		Class<?> callerClass7 = Reflection.getCallerClass(7);

		String callerClassName7 = callerClass7.getName();

		if (callerClassName7.startsWith("javax.crypto")) {
			logCreateSecurityManager(callerClass7, 7);

			return true;
		}

		return false;
	}

	protected boolean hasGetClassLoader(String name) {

		// Temporarily return true

		return true;
	}

	protected boolean hasGetEnv(String name) {
		for (Pattern environmentVariablePattern :
				_environmentVariablePatterns) {

			Matcher matcher = environmentVariablePattern.matcher(name);

			if (matcher.matches()) {
				return true;
			}
		}

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

		if (callerClass8 == AccessController.class) {
			logGetProtectionDomain(callerClass8, 8);

			return true;
		}

		return false;
	}

	protected boolean hasLoadLibrary() {
		Class<?> callerClass10 = Reflection.getCallerClass(10);

		if (callerClass10 == AccessController.class) {
			return true;
		}

		return false;
	}

	protected boolean hasReadFileDescriptor() {
		if (JavaDetector.isJDK7()) {
			Class<?> callerClass9 = Reflection.getCallerClass(9);

			String callerClassName9 = callerClass9.getName();

			if (callerClassName9.startsWith(_CLASS_NAME_PROCESS_IMPL)) {
				logWriteFileDescriptor(callerClass9, 9);

				return true;
			}
		}
		else {
			Class<?> callerClass8 = Reflection.getCallerClass(8);

			String callerClassName8 = callerClass8.getName();

			if (callerClassName8.startsWith(_CLASS_NAME_PROCESS_IMPL)) {
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

			if (callerClassName9.startsWith(_CLASS_NAME_PROCESS_IMPL)) {
				logWriteFileDescriptor(callerClass9, 9);

				return true;
			}
		}
		else {
			Class<?> callerClass8 = Reflection.getCallerClass(8);

			String callerClassName8 = callerClass8.getName();

			if (callerClassName8.startsWith(_CLASS_NAME_PROCESS_IMPL)) {
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

	protected void initEnvironmentVariables() {
		Set<String> environmentVariables = getPropertySet(
			"security-manager-environment-variables");

		_environmentVariablePatterns = new ArrayList<Pattern>(
			environmentVariables.size());

		for (String environmentVariable : environmentVariables) {
			Pattern environmentVariablePattern = Pattern.compile(
				environmentVariable);

			_environmentVariablePatterns.add(environmentVariablePattern);

			if (_log.isDebugEnabled()) {
				_log.debug(
					"Allowing access to environment variables that match " +
						"the regular expression " + environmentVariable);
			}
		}
	}

	protected void logCreateClassLoader(Class<?> callerClass, int frame) {
		if (_log.isInfoEnabled()) {
			_log.info(
				"Allowing frame " + frame + " with caller " + callerClass +
					" to create a class loader");
		}
	}

	protected void logCreateSecurityManager(Class<?> callerClass, int frame) {
		if (_log.isInfoEnabled()) {
			_log.info(
				"Allowing frame " + frame + " with caller " + callerClass +
					"to create a security manager");
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
	private List<Pattern> _environmentVariablePatterns;

}