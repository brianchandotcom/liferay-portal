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
import com.liferay.portal.security.pacl.Reflection;

import java.security.Permission;

/**
 * @author Brian Wing Shun Chan
 */
public class ReflectChecker extends BaseChecker {

	@Override
	public void afterPropertiesSet() {
		initSuppressAccessChecks();
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

		if (name.startsWith(RUNTIME_PERMISSION_SUPPRESS_ACCESS_CHECKS)) {
			key = "security-manager-suppress-access-checks";
			value = Boolean.TRUE.toString();
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

		if (name.startsWith(RUNTIME_PERMISSION_SUPPRESS_ACCESS_CHECKS)) {
			if (!hasSuppressAccessChecks(permission)) {
				logSecurityException(
					_log, "Attempted to suppess access checks");

				return false;
			}
		}
		else {
			int stackIndex = Reflection.getStackIndex(10, 9);

			Class<?> callerClass = Reflection.getCallerClass(stackIndex);

			if (isTrustedCaller(callerClass, permission)) {
				return true;
			}

			logSecurityException(_log, "Attempted to reflect");

			return false;
		}

		return true;
	}

	protected boolean hasSuppressAccessChecks(Permission permission) {
		if (_suppressAccessChecks) {
			return true;
		}

		int stackIndex = Reflection.getStackIndex(11, 10);

		Class<?> callerClass = Reflection.getCallerClass(stackIndex);

		if (isTrustedCaller(callerClass, permission)) {
			return true;
		}

		logSecurityException(_log, "Attempted to reflect");

		return false;
	}

	protected void initSuppressAccessChecks() {
		_suppressAccessChecks = getPropertyBoolean(
			"security-manager-suppress-access-checks");
	}

	private static final Log _log = LogFactoryUtil.getLog(ReflectChecker.class);

	private boolean _suppressAccessChecks;

}