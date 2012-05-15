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

import java.security.Permission;

import sun.reflect.Reflection;

/**
 * @author Brian Wing Shun Chan
 */
public class NetChecker extends BaseChecker {

	public void afterPropertiesSet() {
	}

	public void checkPermission(Permission permission) {
		String name = permission.getName();

		if (name.equals(NET_PERMISSION_GET_PROXY_SELECTOR)) {
			if (!hasGetProxySelector()) {
				throwSecurityException(_log, "Attempted to get proxy selector");
			}
		}
		else if (name.equals(NET_PERMISSION_SPECIFY_STREAM_HANDLER)) {

			// TODO

		}
	}

	protected boolean hasGetProxySelector() {
		if (JavaDetector.isJDK7()) {
			Class<?> callerClass8 = Reflection.getCallerClass(8);

			String className8 = callerClass8.getName();

			if (className8.startsWith(_CLASS_NAME_SOCKS_SOCKET_IMPL) &&
				CheckerUtil.isAccessControllerDoPrivileged(9)) {

				logGetProxySelector(callerClass8, 8);

				return true;
			}
		}
		else {
			Class<?> callerClass7 = Reflection.getCallerClass(7);

			String className7 = callerClass7.getName();

			if (className7.startsWith(_CLASS_NAME_SOCKS_SOCKET_IMPL) &&
				CheckerUtil.isAccessControllerDoPrivileged(8)) {

				logGetProxySelector(callerClass7, 7);

				return true;
			}
		}

		return false;
	}

	protected void logGetProxySelector(Class<?> callerClass, int frame) {
		if (_log.isInfoEnabled()) {
			_log.info(
				"Allowing frame " + frame + " with caller " + callerClass +
					" to get the proxy selector");
		}
	}

	private static final String _CLASS_NAME_SOCKS_SOCKET_IMPL =
		"java.net.SocksSocketImpl$";

	private static Log _log = LogFactoryUtil.getLog(NetChecker.class);

}