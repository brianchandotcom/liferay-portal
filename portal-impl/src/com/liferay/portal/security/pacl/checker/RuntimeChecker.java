/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.util.ServerDetector;
import com.liferay.portal.kernel.util.StringPool;

import java.security.AccessController;
import java.security.Permission;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
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

		if (name.startsWith(RUNTIME_PERMISSION_GET_ENV)) {
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
			if (!hasGetClassLoader()) {
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

		// Temporarily return true

		return true;
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

	protected boolean hasGetClassLoader() {

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

		// Temporarily return true

		return true;
	}

	protected boolean hasWriteFileDescriptor() {

		// Temporarily return true

		return true;
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

	private static Log _log = LogFactoryUtil.getLog(RuntimeChecker.class);

	private List<Pattern> _environmentVariablePatterns;

}