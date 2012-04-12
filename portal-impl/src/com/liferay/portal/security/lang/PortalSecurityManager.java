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
import com.liferay.portal.kernel.util.InitialThreadLocal;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.pacl.PACLClassUtil;
import com.liferay.portal.security.pacl.PACLPolicy;

import java.awt.AWTPermission;

import java.io.FilePermission;
import java.io.SerializablePermission;

import java.lang.management.ManagementPermission;
import java.lang.reflect.ReflectPermission;

import java.net.NetPermission;
import java.net.SocketPermission;

import java.security.AccessControlContext;
import java.security.AccessController;
import java.security.AllPermission;
import java.security.Permission;
import java.security.PrivilegedAction;
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
 * SecurityManager exists, we set that as the parent and delegate to it as fall
 * back. This class will not delegate checks to super when there is no parent so
 * as to avoid forcing the need for a default policy.
 *
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 */
public class PortalSecurityManager extends SecurityManager {

	static {
		Class<?> c;
		c = ExecutionState.class;
		c.getName(); // to prevent compiler warnings
	}

	public PortalSecurityManager(
		SecurityManager parent, ClassLoader portalClassLoader) {

		_parent = parent;

		_portalClassLoader = portalClassLoader;
		_commonClassLoader = _portalClassLoader.getParent();
		_systemClassLoader = ClassLoader.getSystemClassLoader();

		_portalContext = AccessController.getContext();
	}

	@Override
	public void checkPermission(Permission permission) {
		ExecutionState state = findExecutionState(permission, null);

		if (state == ExecutionState.PORTAL) {
			if (_parent != null) {
				_parent.checkPermission(permission);
			}

			return;
		}

		if (state == ExecutionState.PASS_THROUGH) {
			_inProcessCheck.set(Boolean.TRUE);
		}

		invokeHandlers(permission, null);

		if (state == ExecutionState.PASS_THROUGH) {
			_inProcessCheck.set(Boolean.FALSE);
		}

		if (_parent != null) {
			_parent.checkPermission(permission);
		}
	}

	@Override
	public void checkPermission(Permission permission, Object context) {
		ExecutionState state = findExecutionState(permission, context);

		if (state == ExecutionState.PORTAL) {
			if (_parent != null) {
				_parent.checkPermission(permission, context);
			}

			return;
		}

		if (state == ExecutionState.PASS_THROUGH) {
			_inProcessCheck.set(Boolean.TRUE);
		}

		invokeHandlers(permission, context);

		if (state == ExecutionState.PASS_THROUGH) {
			_inProcessCheck.set(Boolean.FALSE);
		}

		if (_parent != null) {
			_parent.checkPermission(permission, context);
		}
	}

	protected void invokeHandlers(Permission permission, Object context) {
		if (permission instanceof AllPermission) {
			// Has all permissions
		}
		else if (permission instanceof AuthPermission) {
			checkAuthPermission((AuthPermission)permission, context);
		}
		else if (permission instanceof AWTPermission) {
			// http://docs.oracle.com/javase/1.5.0/docs/api/java/awt/AWTPermission.html
			// I don't think we need this.
		}
		else if (permission instanceof DelegationPermission) {
			// http://docs.oracle.com/javase/1.5.0/docs/api/javax/security/auth/kerberos/DelegationPermission.html
			// I don't think we need this.
		}
		else if (permission instanceof FilePermission) {
			checkFilePermission((FilePermission)permission, context);
		}
		else if (permission instanceof LoggingPermission) {
			checkLoggingPermission((LoggingPermission)permission, context);
		}
		else if (permission instanceof ManagementPermission) {
			checkManagementPermission(
				(ManagementPermission)permission, context);
		}
		else if (permission instanceof MBeanPermission) {
			checkMBeanPermission((MBeanPermission)permission, context);
		}
		else if (permission instanceof MBeanServerPermission) {
			checkMBeanServerPermission(
				(MBeanServerPermission)permission, context);
		}
		else if (permission instanceof MBeanTrustPermission) {
			checkMBeanTrustPermission(
				(MBeanTrustPermission)permission, context);
		}
		else if (permission instanceof NetPermission) {
			checkNetPermission((NetPermission)permission, context);
		}
		else if (permission instanceof PrivateCredentialPermission) {
			// http://docs.oracle.com/javase/1.5.0/docs/api/javax/security/auth/PrivateCredentialPermission.html
			// I don't think we need this
		}
		else if (permission instanceof PropertyPermission) {
			checkPropertyPermission((PropertyPermission)permission, context);
		}
		else if (permission instanceof ReflectPermission) {
			checkReflectPermission((ReflectPermission)permission, context);
		}
		else if (permission instanceof RuntimePermission) {
			checkRuntimePermission((RuntimePermission)permission, context);
		}
		else if (permission instanceof SecurityPermission) {
			checkSecurityPermission((SecurityPermission)permission, context);
		}
		else if (permission instanceof SerializablePermission) {
			// http://docs.oracle.com/javase/1.5.0/docs/api/java/io/SerializablePermission.html
			// I don't think we need this
		}
		else if (permission instanceof ServicePermission) {
			// http://docs.oracle.com/javase/1.5.0/docs/api/javax/security/auth/kerberos/ServicePermission.html
			// I don't think we need this
		}
		else if (permission instanceof SocketPermission) {
			checkSocketPermission((SocketPermission)permission, context);
		}
		else if (permission instanceof SQLPermission) {
			// http://docs.oracle.com/javase/1.5.0/docs/api/java/sql/SQLPermission.html
			// I don't think we need this
		}
		else if (permission instanceof SSLPermission) {
			// http://docs.oracle.com/javase/1.5.0/docs/api/javax/net/ssl/SSLPermission.html
			// TODO Not sure about this one
		}
		else if (permission instanceof SubjectDelegationPermission) {
			// http://docs.oracle.com/javase/1.5.0/docs/api/javax/management/remote/SubjectDelegationPermission.html
			// I don't think we need this
		}
		else if (permission instanceof UnresolvedPermission) {
			checkUnresolvedPermission(
				(UnresolvedPermission)permission, context);
		}
	}

	protected ExecutionState findExecutionState(
		Permission permission, Object context) {

		if (((permission instanceof RuntimePermission) &&
			 permission.getName().equals("getClassLoader")) ||
			((permission instanceof FilePermission) &&
			 permission.getActions().equals("read"))) {

			return ExecutionState.PASS_THROUGH;
		}

		return AccessController.doPrivileged(
			new PrivilegedAction<ExecutionState>() {
				public ExecutionState run() {
					try {
						_inProcessCheck.set(Boolean.TRUE);

						Class<?>[] classContext = getClassContext();

						ClassLoader foreignClassLoader = null;

						for (Class<?> clazz : classContext) {
							ClassLoader classLoader = clazz.getClassLoader();

							if ((classLoader != null) &&
								(classLoader != _portalClassLoader) &&
								(classLoader != _commonClassLoader) &&
								(classLoader != _systemClassLoader)) {

								foreignClassLoader = classLoader;

								break;
							}
						}

						if (foreignClassLoader == null) {
							return ExecutionState.PORTAL;
						}

						return ExecutionState.FOREIGN;
					}
					finally {
						_inProcessCheck.set(Boolean.FALSE);
					}
				}
			}
		, _portalContext);
	}

	protected void checkAuthPermission(
		AuthPermission permission, Object context) {

		// http://docs.oracle.com/javase/1.5.0/docs/api/javax/security/auth/AuthPermission.html

		_log(permission, context);

		// TODO Do we care about this?
	}

	protected void checkFilePermission(
		FilePermission permission, Object context) {

		// http://docs.oracle.com/javase/1.5.0/docs/api/java/io/FilePermission.html

		if (_inProcessCheck.get().booleanValue()) {
			return;
		}

		_log(permission, context);

		String name = permission.getName();
		String actions = permission.getActions();

		if (actions.equals("delete")) {
			_checkDelete(name);
		}
		else if (actions.equals("execute")) {
			_checkExec(name);
		}
		else if (actions.equals("read")) {
			_checkRead(name);
		}
		else if (actions.equals("write")) {
			_checkWrite(name);
		}
	}

	protected void checkLoggingPermission(
		LoggingPermission permission, Object context) {

		// http://docs.oracle.com/javase/1.5.0/docs/api/java/util/logging/LoggingPermission.html

		if (_inProcessCheck.get().booleanValue()) {
			return;
		}

		_log(permission, context);

		// TODO
	}

	protected void checkManagementPermission(
		ManagementPermission permission, Object context) {

		// http://docs.oracle.com/javase/1.5.0/docs/api/java/lang/management/ManagementPermission.html

		_log(permission, context);

		// TODO
	}

	protected void checkMBeanPermission(
		MBeanPermission permission, Object context) {

		// http://docs.oracle.com/javase/1.5.0/docs/api/javax/management/MBeanPermission.html

		_log(permission, context);

		// TODO
	}

	protected void checkMBeanServerPermission(
		MBeanServerPermission permission, Object context) {

		// http://docs.oracle.com/javase/1.5.0/docs/api/javax/management/MBeanServerPermission.html

		_log(permission, context);

		// TODO
	}

	protected void checkMBeanTrustPermission(
		MBeanTrustPermission permission, Object context) {

		// http://docs.oracle.com/javase/1.5.0/docs/api/javax/management/MBeanTrustPermission.html

		_log(permission, context);

		// TODO
	}

	protected void checkNetPermission(
		NetPermission permission, Object context) {

		// http://docs.oracle.com/javase/1.5.0/docs/api/java/net/NetPermission.html

		_log(permission, context);

		// TODO
	}

	protected void checkPropertyPermission(
		PropertyPermission permission, Object context) {

		// http://docs.oracle.com/javase/1.5.0/docs/api/java/util/PropertyPermission.html

		_log(permission, context);

		// TODO
	}

	protected void checkReflectPermission(
		ReflectPermission permission, Object context) {

		// http://docs.oracle.com/javase/1.5.0/docs/api/java/lang/reflect/ReflectPermission.html

		_log(permission, context);

		// TODO
	}

	protected void checkRuntimePermission(
		RuntimePermission permission, Object context) {

		// http://docs.oracle.com/javase/1.5.0/docs/api/java/lang/RuntimePermission.html

		if (_inProcessCheck.get().booleanValue()) {
			return;
		}

		_log(permission, context);

		String name = permission.getName();

		if (name.equals(_PERMISSION_SET_SECURITY_MANAGER)) {
			throw new SecurityException(
				"Attempted to set another security manager");
		}
		else if (name.equals(_PERMISSION_EXIT_VM)) {
			Thread.dumpStack();

			throw new SecurityException("Attempted to shutdown the VM!");
		}
		else if (name.startsWith("accessClassInPackage.")) {
			int pos = name.indexOf(StringPool.PERIOD);

			String pkg = name.substring(pos);

			_checkPackageAccess(pkg);
		}

		// TODO
	}

	protected void checkSecurityPermission(
		SecurityPermission permission, Object context) {

		// http://docs.oracle.com/javase/1.5.0/docs/api/java/security/SecurityPermission.html

		_log(permission, context);

		// TODO
	}

	protected void checkSocketPermission(
		SocketPermission permission, Object context) {

		// http://docs.oracle.com/javase/1.5.0/docs/api/java/net/SocketPermission.html

		_log(permission, context);

		String name = permission.getName();
		String actions = permission.getActions();

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
			_checkConnect(host, Integer.parseInt(portRange));
		}
		else if (actions.contains("listen")) {
			_checkListen(Integer.parseInt(portRange));
		}
		else if (actions.contains("resolve")) {
			// TODO
		}
	}

	protected void checkUnresolvedPermission(
		UnresolvedPermission permission, Object context) {

		// http://docs.oracle.com/javase/1.5.0/docs/api/java/security/UnresolvedPermission.html

		_log(permission, context);

		// TODO
	}

	protected PACLPolicy getPACLPolicy(final boolean debug) {
		return AccessController.doPrivileged(
			new PrivilegedAction<PACLPolicy>() {
				public PACLPolicy run() {
					return PACLClassUtil.getPACLPolicyBySecurityManagerClassContext(
						getClassContext(), debug);
				}
			}
		);
	}

	protected void _checkConnect(String host, int port) {
		if (port == -1) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Always allow resolving of host " + host);
			}

			return;
		}

		PACLPolicy paclPolicy = getPACLPolicy(_log.isDebugEnabled());

		if (paclPolicy != null) {
			if (!paclPolicy.hasSocketConnect(host, port)) {
				throw new SecurityException(
					"Attempted to connect to host " + host + " on port " +
						port);
			}
		}
	}

	protected void _checkDelete(String fileName) {
		PACLPolicy paclPolicy = getPACLPolicy(_log.isDebugEnabled());

		if (paclPolicy != null) {
			if (!paclPolicy.hasFileDelete(fileName)) {
				throw new SecurityException(
					"Attempted to delete file " + fileName);
			}
		}
	}

	protected void _checkExec(String fileName) {
		PACLPolicy paclPolicy = getPACLPolicy(_log.isDebugEnabled());

		if (paclPolicy != null) {
			if (!paclPolicy.hasFileExecute(fileName)) {
				throw new SecurityException(
					"Attempted to execute file " + fileName);
			}
		}
	}

	protected void _checkListen(int port) {
		PACLPolicy paclPolicy = getPACLPolicy(_log.isDebugEnabled());

		if (paclPolicy != null) {
			if (!paclPolicy.hasSocketListen(port)) {
				throw new SecurityException(
					"Attempted to listen on port " + port);
			}
		}
	}

	protected void _checkPackageAccess(String pkg) {
		// TODO

		if (pkg.startsWith("sun.reflect")) {
		}
	}

	protected void _checkRead(String fileName) {
		PACLPolicy paclPolicy = getPACLPolicy(_log.isDebugEnabled());

		if (paclPolicy != null) {
			if (!paclPolicy.hasFileRead(fileName)) {
				throw new SecurityException(
					"Attempted to read file " + fileName);
			}
		}
	}

	protected void _checkWrite(String fileName) {
		PACLPolicy paclPolicy = getPACLPolicy(_log.isDebugEnabled());

		if (paclPolicy != null) {
			if (!paclPolicy.hasFileWrite(fileName)) {
				throw new SecurityException(
					"Attempted to write file " + fileName);
			}
		}
	}

	protected void _log(Permission permission, Object context) {

		try {
			_inProcessCheck.set(Boolean.TRUE);

			if (!_log.isDebugEnabled()) {
				return;
			}

			PACLPolicy paclPolicy = getPACLPolicy(_log.isDebugEnabled());

			String actions = permission.getActions();

			StringBundler sb = new StringBundler(12);

			sb.append(permission.getClass().getSimpleName());
			sb.append(" {");

			if ((paclPolicy != null) &&
				Validator.isNotNull(paclPolicy.getServletContextName())) {

				sb.append("servletContextName=");
				sb.append(paclPolicy.getServletContextName());
				sb.append(", ");
			}

			sb.append("name=");
			sb.append(permission.getName());

			if (Validator.isNotNull(actions)) {
				sb.append(", actions=");
				sb.append(actions);
			}

			if (context != null) {
				sb.append(", context=");
				sb.append(String.valueOf(context));
			}

			sb.append("}");

			_log.debug(sb.toString());
		}
		finally {
			_inProcessCheck.set(Boolean.FALSE);
		}
	}

	private static final String _PERMISSION_EXIT_VM = "exitVM";

	private static final String _PERMISSION_SET_SECURITY_MANAGER =
		"setSecurityManager";

	private static Log _log = LogFactoryUtil.getLog(
		PortalSecurityManager.class.getName());

	private static ThreadLocal<Boolean> _inProcessCheck =
		new InitialThreadLocal<Boolean>(
			PortalSecurityManager.class.getName() + "inProcessCheck",
			Boolean.FALSE);

	private enum ExecutionState {

		/**
		 * The stack has a foreign classloader so do checks
		 */
		FOREIGN,

		/**
		 * Only the portal's or it's parent classloader were found, passively
		 * avoid checks
		 */
		PORTAL,

		/**
		 * The system has to be allowed to proceed without checks, forcibly
		 * avoid checks
		 */
		PASS_THROUGH

	};

	private ClassLoader _commonClassLoader;
	private SecurityManager _parent;
	private ClassLoader _portalClassLoader;
	private AccessControlContext _portalContext;
	private ClassLoader _systemClassLoader;

}