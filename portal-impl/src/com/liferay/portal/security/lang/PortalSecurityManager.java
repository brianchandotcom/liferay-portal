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

package com.liferay.portal.security.lang;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.security.pacl.PACLClassUtil;
import com.liferay.portal.security.pacl.PACLPolicy;

import java.security.Permission;

/**
 * @author Brian Wing Shun Chan
 */
public class PortalSecurityManager extends SecurityManager {

	@Override
	public void checkConnect(String host, int port) {
		if (port == -1) {
			if (_logCheckConnect.isInfoEnabled()) {
				_logCheckConnect.info("Always allow resolving of host " + host);
			}

			return;
		}

		PACLPolicy paclPolicy = getPACLPolicy(
			_logCheckConnect.isDebugEnabled());

		if (_logCheckConnect.isInfoEnabled()) {
			_logCheckConnect.info(
				"Checking permissions to connect to host " + host +
					" on port " + port + " with PACL policy " + paclPolicy);
		}

		if (paclPolicy != null) {
			if (!paclPolicy.isSocketConnect(host, port)) {
				throw new SecurityException(
					"Attempted to connect to host " + host + " on port " +
						port);
			}
		}

		super.checkConnect(host, port);
	}

	@Override
	public void checkListen(int port) {
		PACLPolicy paclPolicy = getPACLPolicy(_logCheckListen.isDebugEnabled());

		if (_logCheckListen.isInfoEnabled()) {
			_logCheckListen.info(
				"Checking permissions to listen on port " + port +
					" with PACL policy " + paclPolicy);
		}

		if (paclPolicy != null) {
			if (!paclPolicy.isSocketListen(port)) {
				throw new SecurityException(
					"Attempted to listen on port " + port);
			}
		}

		super.checkListen(port);
	}

	@Override
	public void checkPackageAccess(String pkg) {
		if (pkg.startsWith("sun.reflect")) {
		}

		super.checkPackageAccess(pkg);
	}

	@Override
	public void checkPermission(Permission permission) {
		String name = permission.getName();

		if (name.equals(_PERMISSION_SET_SECURITY_MANAGER)) {
			throw new SecurityException(
				"Attempted to set another security manager");
		}
		else if (name.equals(_PERMISSION_EXIT_VM)) {
			Thread.dumpStack();
		}
	}

	@Override
	public void checkPermission(Permission permission, Object context) {
	}

	protected PACLPolicy getPACLPolicy(boolean debug) {
		return PACLClassUtil.getPACLPolicyBySecurityManagerClassContext(
			getClassContext(), debug);
	}

	private static final String _PERMISSION_EXIT_VM = "exitVM";

	private static final String _PERMISSION_SET_SECURITY_MANAGER =
		"setSecurityManager";

	private static Log _logCheckConnect = LogFactoryUtil.getLog(
		PortalSecurityManager.class.getName() + "#checkConnect");

	private static Log _logCheckListen = LogFactoryUtil.getLog(
		PortalSecurityManager.class.getName() + "#checkListen");

}