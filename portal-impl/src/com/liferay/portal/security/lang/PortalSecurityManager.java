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
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.security.pacl.PACLClassUtil;
import com.liferay.portal.security.pacl.PACLPolicy;
import com.liferay.portal.spring.util.FilterClassLoader;

import java.awt.AWTPermission;

import java.io.FilePermission;
import java.io.SerializablePermission;

import java.lang.management.ManagementPermission;
import java.lang.reflect.ReflectPermission;

import java.net.NetPermission;
import java.net.SocketPermission;

import java.security.AccessController;
import java.security.AllPermission;
import java.security.Permission;
import java.security.SecurityPermission;
import java.security.UnresolvedPermission;

import java.sql.SQLPermission;

import java.util.PropertyPermission;
import java.util.logging.LoggingPermission;

import javax.management.MBeanPermission;
import javax.management.MBeanServerPermission;
import javax.management.MBeanTrustPermission;
import javax.management.remote.SubjectDelegationPermission;
import javax.net.ssl.SSLPermission;
import javax.security.auth.AuthPermission;
import javax.security.auth.PrivateCredentialPermission;
import javax.security.auth.kerberos.DelegationPermission;
import javax.security.auth.kerberos.ServicePermission;

/**
 * This is the portal's implementation of a security manager. The goal is to
 * protect portal resources from plugins and prevent security issues by
 * forcing plugin developers to openly declare their requirements. Where a
 * SecurityManager exists, we set that as the parent and delegate to it as a
 * fallback. This class will not delegate checks to super when there is no
 * parent so as to avoid forcing the need for a default policy.
 *
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 */
public class PortalSecurityManager extends SecurityManager {

	public PortalSecurityManager() {
		_parentSecurityManager = System.getSecurityManager();

		// Preload dependent classes to prevent ClassCircularityError

		_log.info("Loading " + FilterClassLoader.class.getName());
		_log.info("Loading " + PACLClassUtil.class.getName());
		_log.info(
			"Loading " + PortalSecurityManagerThreadLocal.class.getName());
	}

	@Override
	public void checkPermission(Permission permission) {
		doCheckPermission(permission, AccessController.getContext());
	}

	@Override
	public void checkPermission(Permission permission, Object context) {
		doCheckPermission(permission, context);
	}

	protected void checkAuthPermission(
		PACLPolicy paclPolicy, AuthPermission authPermission, Object context) {
	}

	protected void checkFilePermission(
		PACLPolicy paclPolicy, FilePermission filePermission, Object context) {

		String actions = filePermission.getActions();

		if (actions.equals(_FILE_PERMISSION_ACTION_DELETE)) {
			doCheckDelete(paclPolicy, filePermission.getName());
		}
		else if (actions.equals(_FILE_PERMISSION_ACTION_EXECUTE)) {
			doCheckExec(paclPolicy, filePermission.getName());
		}
		else if (actions.equals(_FILE_PERMISSION_ACTION_READ)) {
			doCheckRead(paclPolicy, filePermission.getName());
		}
		else if (actions.equals(_FILE_PERMISSION_ACTION_WRITE)) {
			doCheckWrite(paclPolicy, filePermission.getName());
		}
	}

	protected void checkLoggingPermission(
		PACLPolicy paclPolicy, LoggingPermission loggingPermission,
		Object context) {

		// TODO

	}

	protected void checkManagementPermission(
		PACLPolicy paclPolicy, ManagementPermission managementPermission,
		Object context) {

		// TODO

	}

	protected void checkMBeanPermission(
		PACLPolicy paclPolicy, MBeanPermission mBeanPermission,
		Object context) {

		// TODO

	}

	protected void checkMBeanServerPermission(
		PACLPolicy paclPolicy, MBeanServerPermission mBeanServerPermission,
		Object context) {

		// TODO

	}

	protected void checkMBeanTrustPermission(
		PACLPolicy paclPolicy, MBeanTrustPermission mBeanTrustPermission,
		Object context) {

		// TODO

	}

	protected void checkNetPermission(
		PACLPolicy paclPolicy, NetPermission netPermission, Object context) {

		// TODO
	}

	protected void checkPropertyPermission(
		PACLPolicy paclPolicy, PropertyPermission propertyPermission,
		Object context) {

		// TODO

	}

	protected void checkReflectPermission(
		PACLPolicy paclPolicy, ReflectPermission reflectPermission,
		Object context) {

		// TODO

	}

	protected void checkRuntimePermission(
		PACLPolicy paclPolicy, RuntimePermission runtimePermission,
		Object context) {

		// TODO

		String name = runtimePermission.getName();

		if (name.startsWith(_RUNTIME_PERMISSION_ACCESS_CLASS_IN_PACKAGE)) {
			int pos = name.indexOf(StringPool.PERIOD);

			String pkg = name.substring(pos);

			doCheckPackageAccess(paclPolicy, pkg);
		}
		else if (name.equals(_RUNTIME_PERMISSION_SET_SECURITY_MANAGER)) {
			throw new SecurityException(
				"Attempted to set another security manager");
		}
		else if (name.equals(_RUNTIME_PERMISSION_EXIT_VM)) {
			Thread.dumpStack();

			throw new SecurityException("Attempted to shutdown the VM!");
		}
	}

	protected void checkSecurityPermission(
		PACLPolicy paclPolicy, SecurityPermission securityPermission,
		Object context) {

		// TODO

	}

	protected void checkSocketPermission(
		PACLPolicy paclPolicy, SocketPermission socketPermission,
		Object context) {

		String actions = socketPermission.getActions();

		String name = socketPermission.getName();

		int pos = name.indexOf(StringPool.COLON);

		String host = "localhost";

		if (pos != -1) {
			host = name.substring(0, pos);
		}

		String portRange = name.substring(pos + 1);

		if (actions.contains("accept")) {

			// TODO

		}
		else if (actions.contains("connect")) {
			doCheckConnect(paclPolicy, host, GetterUtil.getInteger(portRange));
		}
		else if (actions.contains("listen")) {
			doCheckListen(paclPolicy, GetterUtil.getInteger(portRange));
		}
		else if (actions.contains("resolve")) {
			doCheckConnect(paclPolicy, host, -1);
		}
	}

	protected void checkUnresolvedPermission(
		PACLPolicy paclPolicy, UnresolvedPermission unresolvedPermission,
		Object context) {

		// TODO

	}

	protected void doCheckAccept(PACLPolicy paclPolicy, String host, int port) {

		// TODO

	}

	protected void doCheckConnect(PACLPolicy paclPolicy, String host, int port) {
		if (port == -1) {
			if (_logDoCheckConnect.isDebugEnabled()) {
				_logDoCheckConnect.debug(
					"Always allow resolving of host " + host);
			}

			return;
		}

		if (!paclPolicy.hasSocketConnect(host, port)) {
			throw new SecurityException(
				"Attempted to connect to host " + host + " on port " +
					port);
		}
	}

	protected void doCheckDelete(PACLPolicy paclPolicy, String fileName) {
		if (!paclPolicy.hasFileDelete(fileName)) {
			throw new SecurityException(
				"Attempted to delete file " + fileName);
		}
	}

	protected void doCheckExec(PACLPolicy paclPolicy, String fileName) {
		if (!paclPolicy.hasFileExecute(fileName)) {
			throw new SecurityException(
				"Attempted to execute file " + fileName);
		}
	}

	protected void doCheckListen(PACLPolicy paclPolicy, int port) {
		if (!paclPolicy.hasSocketListen(port)) {
			throw new SecurityException(
				"Attempted to listen on port " + port);
		}
	}

	protected void doCheckPackageAccess(PACLPolicy paclPolicy, String pkg) {

		// TODO

		if (pkg.startsWith("sun.reflect")) {
		}
	}

	protected void doCheckPermission(Permission permission, Object context) {
		boolean enabled = PortalSecurityManagerThreadLocal.isEnabled();

		if (!enabled) {
			if (_parentSecurityManager != null) {
				_parentSecurityManager.checkPermission(permission, context);
			}

			return;
		}

		PACLPolicy paclPolicy = null;

		try {
			PortalSecurityManagerThreadLocal.setEnabled(false);

			paclPolicy = getPACLPolicy(_log.isDebugEnabled());
		}
		finally {
			PortalSecurityManagerThreadLocal.setEnabled(enabled);
		}

		if ((paclPolicy != null) && paclPolicy.isActive()) {
			if (_parentSecurityManager != null) {
				_parentSecurityManager.checkPermission(permission, context);
			}

			return;
		}

		if (permission instanceof AllPermission) {
		}
		else if (permission instanceof AuthPermission) {
			checkAuthPermission(
				paclPolicy, (AuthPermission)permission, context);
		}
		else if (permission instanceof AWTPermission) {
		}
		else if (permission instanceof DelegationPermission) {
		}
		else if (permission instanceof FilePermission) {
			checkFilePermission(
				paclPolicy, (FilePermission)permission, context);
		}
		else if (permission instanceof LoggingPermission) {
			checkLoggingPermission(
				paclPolicy, (LoggingPermission)permission, context);
		}
		else if (permission instanceof ManagementPermission) {
			checkManagementPermission(
				paclPolicy, (ManagementPermission)permission, context);
		}
		else if (permission instanceof MBeanPermission) {
			checkMBeanPermission(
				paclPolicy, (MBeanPermission)permission, context);
		}
		else if (permission instanceof MBeanServerPermission) {
			checkMBeanServerPermission(
				paclPolicy, (MBeanServerPermission)permission, context);
		}
		else if (permission instanceof MBeanTrustPermission) {
			checkMBeanTrustPermission(
				paclPolicy, (MBeanTrustPermission)permission, context);
		}
		else if (permission instanceof NetPermission) {
			checkNetPermission(
				paclPolicy, (NetPermission)permission, context);
		}
		else if (permission instanceof PrivateCredentialPermission) {
		}
		else if (permission instanceof PropertyPermission) {
			checkPropertyPermission(
				paclPolicy, (PropertyPermission)permission, context);
		}
		else if (permission instanceof ReflectPermission) {
			checkReflectPermission(
				paclPolicy, (ReflectPermission)permission, context);
		}
		else if (permission instanceof RuntimePermission) {
			checkRuntimePermission(
				paclPolicy, (RuntimePermission)permission, context);
		}
		else if (permission instanceof SecurityPermission) {
			checkSecurityPermission(
				paclPolicy, (SecurityPermission)permission, context);
		}
		else if (permission instanceof SerializablePermission) {
		}
		else if (permission instanceof ServicePermission) {
		}
		else if (permission instanceof SocketPermission) {
			checkSocketPermission(
				paclPolicy, (SocketPermission)permission, context);
		}
		else if (permission instanceof SQLPermission) {
		}
		else if (permission instanceof SSLPermission) {
		}
		else if (permission instanceof SubjectDelegationPermission) {
		}
		else if (permission instanceof UnresolvedPermission) {
			checkUnresolvedPermission(
				paclPolicy, (UnresolvedPermission)permission, context);
		}

		if (_parentSecurityManager != null) {
			_parentSecurityManager.checkPermission(permission, context);
		}
	}

	protected void doCheckRead(PACLPolicy paclPolicy, String fileName) {
		if (!paclPolicy.hasFileRead(fileName)) {
			throw new SecurityException(
				"Attempted to read file " + fileName);
		}
	}

	protected void doCheckWrite(PACLPolicy paclPolicy, String fileName) {
		if (!paclPolicy.hasFileWrite(fileName)) {
			throw new SecurityException(
				"Attempted to write file " + fileName);
		}
	}

	protected PACLPolicy getPACLPolicy(boolean debug) {
		return PACLClassUtil.getPACLPolicyBySecurityManagerClassContext(
			getClassContext(), debug);
	}

	private static final String _FILE_PERMISSION_ACTION_DELETE = "delete";

	private static final String _FILE_PERMISSION_ACTION_EXECUTE = "execute";

	private static final String _FILE_PERMISSION_ACTION_READ = "read";

	private static final String _FILE_PERMISSION_ACTION_WRITE = "write";

	private static final String _RUNTIME_PERMISSION_ACCESS_CLASS_IN_PACKAGE =
		"accessClassInPackage.";

	private static final String _RUNTIME_PERMISSION_EXIT_VM = "exitVM";

	private static final String _RUNTIME_PERMISSION_SET_SECURITY_MANAGER =
		"setSecurityManager";

	private static Log _log = LogFactoryUtil.getLog(
		PortalSecurityManager.class.getName());

	private static Log _logDoCheckConnect = LogFactoryUtil.getLog(
		PortalSecurityManager.class.getName() + "#doCheckConnect");

	private static Log _logDoCheckDelete = LogFactoryUtil.getLog(
		PortalSecurityManager.class.getName() + "#doCheckDelete");

	private static Log _logDoCheckExec = LogFactoryUtil.getLog(
		PortalSecurityManager.class.getName() + "#doCheckExec");

	private static Log _logDoCheckListen = LogFactoryUtil.getLog(
		PortalSecurityManager.class.getName() + "#doCheckListen");

	private static Log _logDoCheckRead = LogFactoryUtil.getLog(
		PortalSecurityManager.class.getName() + "#doCheckRead");

	private static Log _logDoCheckWrite = LogFactoryUtil.getLog(
		PortalSecurityManager.class.getName() + "#doCheckWrite");

	private SecurityManager _parentSecurityManager;

}