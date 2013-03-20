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

import java.security.Permission;

import sun.reflect.Reflection;

/**
 * @author Brian Wing Shun Chan
 */
public class NetChecker extends BaseChecker {

	public void afterPropertiesSet() {
	}

	public boolean implies(Permission permission) {
		String name = permission.getName();

		if (name.equals(NET_PERMISSION_GET_PROXY_SELECTOR)) {
			if (!hasGetProxySelector(permission)) {
				logSecurityException(_log, "Attempted to get proxy selector");

				return false;
			}
		}
		else if (name.equals(NET_PERMISSION_SPECIFY_STREAM_HANDLER)) {
			if (!hasSpecifyStreamHandler(permission)) {
				logSecurityException(
					_log, "Attempted to specify stream handler");

				return false;
			}
		}
		else {
			logSecurityException(
				_log, "Attempted " + name + " network operation");

			return false;
		}

		return true;
	}

	protected boolean hasGetProxySelector(Permission permission) {
		int stackIndex = getStackIndex(11, 10);

		Class<?> callerClass = Reflection.getCallerClass(stackIndex);

		if (isTrustedCaller(callerClass, permission)) {
			return true;
		}

		return false;
	}

	protected boolean hasSpecifyStreamHandler(Permission permission) {
		int stackIndex = getStackIndex(11, 10);

		Class<?> callerClass = Reflection.getCallerClass(stackIndex);

		if (isTrustedCaller(callerClass, permission)) {
			return true;
		}

		return false;
	}

	private static Log _log = LogFactoryUtil.getLog(NetChecker.class);

}