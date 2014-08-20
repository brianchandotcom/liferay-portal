/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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
import com.liferay.portal.security.pacl.Reflection;

import java.security.Permission;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Raymond Augé
 * @author Brian Wing Shun Chan
 */
public class RuntimeChecker extends BaseChecker {

	@Override
	public void afterPropertiesSet() {
		initAccessDeclaredMembers();
		initCreateClassLoader();
		initEnvironmentVariables();
		initGetProtectionDomain();
		initModifyThread();
		initSetContextClassLoader();
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

		if (name.startsWith(RUNTIME_PERMISSION_ACCESS_DECLARED_MEMBERS)) {
			key = "security-manager-access-declared-members";

			value = "true";
		}
		else if (name.startsWith(RUNTIME_PERMISSION_CREATE_CLASS_LOADER)) {
			key = "security-manager-create-class-loader";

			value = "true";
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
		else if (name.startsWith(RUNTIME_PERMISSION_GET_PROTECTION_DOMAIN)) {
			key = "security-manager-get-protection-domain";

			value = "true";
		}
		else if (name.equals(RUNTIME_PERMISSION_MODIFY_THREAD)) {
			key = "security-manager-modify-thread";

			value = "true";
		}
		else if (name.equals(RUNTIME_PERMISSION_SET_CONTEXT_CLASS_LOADER)) {
			key = "security-manager-set-context-class-loader";

			value = "true";
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

	@Override
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
			if (!hasAccessDeclaredMembers(permission)) {
				logSecurityException(
					_log, "Attempted to access declared members");

				return false;
			}
		}
		else if (name.equals(RUNTIME_PERMISSION_CREATE_CLASS_LOADER)) {
			if (!hasCreateClassLoader(permission)) {
				logSecurityException(
					_log, "Attempted to create a class loader");

				return false;
			}
		}
		else if (name.equals(RUNTIME_PERMISSION_CREATE_SECURITY_MANAGER)) {
			if (!hasCreateSecurityManager(permission)) {
				logSecurityException(
					_log, "Attempted to create a security manager");

				return false;
			}
		}
		else if (name.startsWith(RUNTIME_PERMISSION_GET_CLASSLOADER)) {
			if (!hasGetClassLoader(permission)) {
				logSecurityException(_log, "Attempted to get class loader");

				return false;
			}
		}
		else if (name.startsWith(RUNTIME_PERMISSION_GET_PROTECTION_DOMAIN)) {
			if (!hasGetProtectionDomain(permission)) {
				logSecurityException(
					_log, "Attempted to get protection domain");

				return false;
			}
		}
		else if (name.startsWith(RUNTIME_PERMISSION_GET_ENV)) {
			int pos = name.indexOf(StringPool.PERIOD);

			String envName = name.substring(pos + 1);

			if (!hasGetEnv(envName, permission)) {
				logSecurityException(
					_log, "Attempted to get environment name " + envName);

				return false;
			}
		}
		else if (name.startsWith(RUNTIME_PERMISSION_LOAD_LIBRARY)) {
			if (!hasLoadLibrary(permission)) {
				logSecurityException(_log, "Attempted to load library");

				return false;
			}
		}
		else if (name.equals(RUNTIME_PERMISSION_MODIFY_THREAD)) {
			if (!hasModifyThread(permission)) {
				logSecurityException(_log, "Attempted to modify a thread");

				return false;
			}
		}
		else if (name.equals(RUNTIME_PERMISSION_READ_FILE_DESCRIPTOR)) {
			if (!hasReadFileDescriptor(permission)) {
				logSecurityException(_log, "Attempted to read file descriptor");

				return false;
			}
		}
		else if (name.equals(RUNTIME_PERMISSION_SET_CONTEXT_CLASS_LOADER)) {
			if (!hasSetContextClassLoader(permission)) {
				logSecurityException(
					_log, "Attempted to set the context class loader");

				return false;
			}
		}
		else if (name.equals(RUNTIME_PERMISSION_SET_SECURITY_MANAGER)) {
			logSecurityException(
				_log, "Attempted to set another security manager");

			return false;
		}
		else if (name.equals(RUNTIME_PERMISSION_WRITE_FILE_DESCRIPTOR)) {
			if (!hasWriteFileDescriptor(permission)) {
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

	protected boolean hasAccessDeclaredMembers(Permission permission) {
		if (_accessDeclaredMembers) {
			return true;
		}

		int stackIndex = Reflection.getStackIndex(13, 12);

		Class<?> callerClass = Reflection.getCallerClass(stackIndex);

		if (isTrustedCaller(callerClass, permission)) {
			return true;
		}

		return false;
	}

	protected boolean hasCreateClassLoader(Permission permission) {
		if (_createClassLoader) {
			return true;
		}

		int stackIndex = Reflection.getStackIndex(15, 11);

		Class<?> callerClass = Reflection.getCallerClass(stackIndex);

		if (isTrustedCaller(callerClass, permission)) {
			return true;
		}

		return false;
	}

	protected boolean hasCreateSecurityManager(Permission permission) {
		int stackIndex = Reflection.getStackIndex(11, 10);

		Class<?> callerClass = Reflection.getCallerClass(stackIndex);

		if (isTrustedCaller(callerClass, permission)) {
			return true;
		}

		return false;
	}

	protected boolean hasGetClassLoader(Permission permission) {
		int stackIndex = Reflection.getStackIndex(
			new int[] {11, 11, 12}, new int[] {10, 10, 10});

		Class<?> callerClass = Reflection.getCallerClass(stackIndex);

		if (isTrustedCaller(callerClass, permission)) {
			return true;
		}

		return false;
	}

	protected boolean hasGetEnv(String name, Permission permission) {
		for (Pattern environmentVariablePattern :
				_environmentVariablePatterns) {

			Matcher matcher = environmentVariablePattern.matcher(name);

			if (matcher.matches()) {
				return true;
			}
		}

		int stackIndex = Reflection.getStackIndex(11, 10);

		Class<?> callerClass = Reflection.getCallerClass(stackIndex);

		if (isTrustedCaller(callerClass, permission)) {
			return true;
		}

		return false;
	}

	protected boolean hasGetProtectionDomain(Permission permission) {
		if (_getProtectionDomain) {
			return true;
		}

		int stackIndex = Reflection.getStackIndex(11, 10);

		Class<?> callerClass = Reflection.getCallerClass(stackIndex);

		if (isTrustedCaller(callerClass, permission)) {
			return true;
		}

		return false;
	}

	protected boolean hasLoadLibrary(Permission permission) {
		int stackIndex = Reflection.getStackIndex(13, 12);

		Class<?> callerClass = Reflection.getCallerClass(stackIndex);

		if (isTrustedCaller(callerClass, permission)) {
			return true;
		}

		return false;
	}

	protected boolean hasModifyThread(Permission permission) {
		if (_modifyThread) {
			return true;
		}

		int stackIndex = Reflection.getStackIndex(13, 12);

		Class<?> callerClass = Reflection.getCallerClass(stackIndex);

		if (isTrustedCaller(callerClass, permission)) {
			return true;
		}

		return false;
	}

	protected boolean hasReadFileDescriptor(Permission permission) {
		int stackIndex = Reflection.getStackIndex(12, 11);

		Class<?> callerClass = Reflection.getCallerClass(stackIndex);

		if (isTrustedCaller(callerClass, permission)) {
			return true;
		}

		return false;
	}

	protected boolean hasSetContextClassLoader(Permission permission) {
		if (_setContextClassLoader) {
			return true;
		}

		int stackIndex = Reflection.getStackIndex(11, 10);

		Class<?> callerClass = Reflection.getCallerClass(stackIndex);

		if (isTrustedCaller(callerClass, permission)) {
			return true;
		}

		return false;
	}

	protected boolean hasWriteFileDescriptor(Permission permission) {
		int stackIndex = Reflection.getStackIndex(12, 11);

		Class<?> callerClass = Reflection.getCallerClass(stackIndex);

		if (isTrustedCaller(callerClass, permission)) {
			return true;
		}

		return false;
	}

	protected void initAccessDeclaredMembers() {
		_accessDeclaredMembers = getPropertyBoolean(
			"security-manager-access-declared-members");
	}

	protected void initCreateClassLoader() {
		_createClassLoader = getPropertyBoolean(
			"security-manager-create-class-loader");
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

	protected void initGetProtectionDomain() {
		_getProtectionDomain = getPropertyBoolean(
			"security-manager-get-protection-domain");
	}

	protected void initModifyThread() {
		_modifyThread = getPropertyBoolean("security-manager-modify-thread");
	}

	protected void initSetContextClassLoader() {
		_setContextClassLoader = getPropertyBoolean(
			"security-manager-set-context-class-loader");
	}

	private static final Log _log = LogFactoryUtil.getLog(RuntimeChecker.class);

	private boolean _accessDeclaredMembers;
	private boolean _createClassLoader;
	private List<Pattern> _environmentVariablePatterns;
	private boolean _getProtectionDomain;
	private boolean _modifyThread;
	private boolean _setContextClassLoader;

}