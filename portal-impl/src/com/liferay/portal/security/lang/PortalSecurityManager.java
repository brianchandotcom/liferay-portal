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
		if (!PortalSecurityManagerThreadLocal.isEnabled()) {
			return;
		}

		if (port == -1) {
			if (_logCheckConnect.isDebugEnabled()) {
				_logCheckConnect.debug(
					"Always allow resolving of host " + host);
			}

			return;
		}

		PACLPolicy paclPolicy = getPACLPolicy(
			_logCheckConnect.isDebugEnabled());

		if (_logCheckConnect.isDebugEnabled()) {
			_logCheckConnect.debug(
				"Using PACL policy " + paclPolicy +
					" to check permissions to connect to host " + host +
						" on port " + port);
		}

		if (paclPolicy != null) {
			if (!paclPolicy.hasSocketConnect(host, port)) {
				throw new SecurityException(
					"Attempted to connect to host " + host + " on port " +
						port);
			}
		}

		super.checkConnect(host, port);
	}

	@Override
	public void checkDelete(String fileName) {
		if (!PortalSecurityManagerThreadLocal.isEnabled()) {
			return;
		}

		PACLPolicy paclPolicy = getPACLPolicy(_logCheckDelete.isDebugEnabled());

		if (_logCheckDelete.isDebugEnabled()) {
			_logCheckDelete.debug(
				"Using PACL policy " + paclPolicy +
					" to check permissions to delete file " + fileName);
		}

		if (paclPolicy != null) {
			if (!paclPolicy.hasFileDelete(fileName)) {
				throw new SecurityException(
					"Attempted to delete file " + fileName);
			}
		}

		super.checkDelete(fileName);
	}

	@Override
	public void checkExec(String fileName) {
		if (!PortalSecurityManagerThreadLocal.isEnabled()) {
			return;
		}

		PACLPolicy paclPolicy = getPACLPolicy(_logCheckExec.isDebugEnabled());

		if (_logCheckExec.isDebugEnabled()) {
			_logCheckExec.debug(
				"Using PACL policy " + paclPolicy +
					" to check permissions to execute file " + fileName);
		}

		if (paclPolicy != null) {
			if (!paclPolicy.hasFileExecute(fileName)) {
				throw new SecurityException(
					"Attempted to execute file " + fileName);
			}
		}

		super.checkExec(fileName);
	}

	@Override
	public void checkListen(int port) {
		if (!PortalSecurityManagerThreadLocal.isEnabled()) {
			return;
		}

		PACLPolicy paclPolicy = getPACLPolicy(_logCheckListen.isDebugEnabled());

		if (_logCheckListen.isDebugEnabled()) {
			_logCheckListen.debug(
				"Using PACL policy " + paclPolicy +
					" to check permissions to listen on port " + port);
		}

		if (paclPolicy != null) {
			if (!paclPolicy.hasSocketListen(port)) {
				throw new SecurityException(
					"Attempted to listen on port " + port);
			}
		}

		super.checkListen(port);
	}

	@Override
	public void checkPackageAccess(String pkg) {
		if (!PortalSecurityManagerThreadLocal.isEnabled()) {
			return;
		}

		if (pkg.startsWith("sun.reflect")) {
		}

		super.checkPackageAccess(pkg);
	}

	@Override
	public void checkPermission(Permission permission) {
		if (!PortalSecurityManagerThreadLocal.isEnabled()) {
			return;
		}

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
		if (!PortalSecurityManagerThreadLocal.isEnabled()) {
			return;
		}
	}

	@Override
	public void checkRead(String fileName) {
		if (!PortalSecurityManagerThreadLocal.isEnabled()) {
			return;
		}

		PACLPolicy paclPolicy = null;

		try {
			paclPolicy = getPACLPolicy(_logCheckRead.isDebugEnabled());
		}
		catch (ClassCircularityError cce) {
			super.checkRead(fileName);

			return;
		}

		if (_logCheckRead.isDebugEnabled()) {
			_logCheckRead.debug(
				"Using PACL policy " + paclPolicy +
					" to check permissions to read file " + fileName);
		}

		if (paclPolicy != null) {
			if (!paclPolicy.hasFileRead(fileName)) {
				throw new SecurityException(
					"Attempted to read file " + fileName);
			}
		}

		super.checkRead(fileName);
	}

	@Override
	public void checkWrite(String fileName) {
		if (!PortalSecurityManagerThreadLocal.isEnabled()) {
			return;
		}

		PACLPolicy paclPolicy = getPACLPolicy(_logCheckWrite.isDebugEnabled());

		if (_logCheckWrite.isDebugEnabled()) {
			_logCheckWrite.debug(
				"Using PACL policy " + paclPolicy +
					" to check permissions to write file " + fileName);
		}

		if (paclPolicy != null) {
			if (!paclPolicy.hasFileWrite(fileName)) {
				throw new SecurityException(
					"Attempted to write file " + fileName);
			}
		}

		super.checkWrite(fileName);
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

	private static Log _logCheckDelete = LogFactoryUtil.getLog(
		PortalSecurityManager.class.getName() + "#checkDelete");

	private static Log _logCheckExec = LogFactoryUtil.getLog(
		PortalSecurityManager.class.getName() + "#checkExec");

	private static Log _logCheckListen = LogFactoryUtil.getLog(
		PortalSecurityManager.class.getName() + "#checkListen");

	private static Log _logCheckRead = LogFactoryUtil.getLog(
		PortalSecurityManager.class.getName() + "#checkRead");

	private static Log _logCheckWrite = LogFactoryUtil.getLog(
		PortalSecurityManager.class.getName() + "#checkWrite");

}