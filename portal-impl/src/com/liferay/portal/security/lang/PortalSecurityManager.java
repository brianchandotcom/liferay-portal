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

import com.liferay.portal.jndi.pacl.PACLInitialContextFactoryBuilder;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.pacl.PACLConstants;
import com.liferay.portal.kernel.security.pacl.permission.CheckMemberAccessPermission;
import com.liferay.portal.kernel.security.pacl.permission.PortalHookPermission;
import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;
import com.liferay.portal.kernel.servlet.taglib.FileAvailabilityUtil;
import com.liferay.portal.kernel.util.JavaDetector;
import com.liferay.portal.security.pacl.PACLClassLoaderUtil;
import com.liferay.portal.security.pacl.PACLClassUtil;
import com.liferay.portal.security.pacl.PACLPolicy;
import com.liferay.portal.security.pacl.PACLPolicyManager;
import com.liferay.portal.security.pacl.checker.CheckerUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Member;

import java.security.Permission;

import javax.naming.spi.InitialContextFactoryBuilder;
import javax.naming.spi.NamingManager;

import sun.reflect.Reflection;

/**
 * This is the portal's implementation of a security manager. The goal is to
 * protect portal resources from plugins and prevent security issues by forcing
 * plugin developers to openly declare their requirements. Where a
 * SecurityManager exists, we set that as the parent and delegate to it as a
 * fallback. This class will not delegate checks to super when there is no
 * parent so as to avoid forcing the need for a default policy.
 *
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 * @author Zsolt Berentey
 */
public class PortalSecurityManager extends SecurityManager {

	public PortalSecurityManager() {
		_parentSecurityManager = System.getSecurityManager();

		initClasses();

		try {
			initInitialContextFactoryBuilder();
		}
		catch (Exception e) {
			if (_log.isInfoEnabled()) {
				_log.info(
					"Unable to override the initial context factory builder " +
						"because one already exists. JNDI security is not " +
							"enabled.");
			}

			if (_log.isWarnEnabled()) {
				_log.warn(e, e);
			}
		}
	}

	@Override
	public void checkMemberAccess(Class<?> clazz, int which) {
		if (clazz == null) {
			throw new NullPointerException();
		}

		if (which == Member.PUBLIC) {
			return;
		}

		ClassLoader classClassLoader = PACLClassLoaderUtil.getClassLoader(
			clazz);

		if (classClassLoader == null) {
			return;
		}

		Class<?> callerClass = Reflection.getCallerClass(4);

		ClassLoader callerClassLoader = PACLClassLoaderUtil.getClassLoader(
			callerClass);

		if (callerClassLoader == null) {
			for (int i = 5;; i++) {
				callerClass = Reflection.getCallerClass(i);

				if (callerClass == null) {
					break;
				}

				String className = callerClass.getName();

				if (!className.startsWith("java.lang") &&
					!className.startsWith("java.security") &&
					!className.startsWith("sun.reflect")) {

					callerClassLoader = PACLClassLoaderUtil.getClassLoader(
						callerClass);

					break;
				}
			}
		}

		if (classClassLoader == callerClassLoader) {
			return;
		}

		Permission permission = new CheckMemberAccessPermission(
			PACLConstants.RUNTIME_PERMISSION_ACCESS_DECLARED_MEMBERS,
			callerClass, callerClassLoader, clazz, classClassLoader);

		checkPermission(permission);
	}

	@Override
	public void checkPermission(Permission permission) {
		checkPermission(permission, null);
	}

	@Override
	public void checkPermission(Permission permission, Object context) {
		if (!PACLPolicyManager.isActive() ||
			!PortalSecurityManagerThreadLocal.isEnabled()) {

			parentCheckPermission(permission, context);

			return;
		}

		PACLPolicy paclPolicy = PACLPolicyManager.getDefaultPACLPolicy();

		if (!paclPolicy.isCheckablePermission(permission)) {
			parentCheckPermission(permission, context);

			return;
		}

		paclPolicy = getPACLPolicy(permission);

		if ((paclPolicy == null) || !paclPolicy.isActive()) {
			parentCheckPermission(permission, context);

			return;
		}

		paclPolicy.checkPermission(permission);

		parentCheckPermission(permission, context);
	}

	protected PACLPolicy getPACLPolicy(Permission permission) {
		PACLPolicy paclPolicy =
			PortalSecurityManagerThreadLocal.getPACLPolicy();

		if (paclPolicy != null) {
			return paclPolicy;
		}

		if (permission instanceof PortalHookPermission) {
			PortalHookPermission portalHookPermission =
				(PortalHookPermission)permission;

			ClassLoader classLoader = portalHookPermission.getClassLoader();

			paclPolicy = PACLPolicyManager.getPACLPolicy(classLoader);

			if (paclPolicy == null) {
				paclPolicy = PACLPolicyManager.getDefaultPACLPolicy();
			}

			return paclPolicy;
		}
		else if (permission instanceof PortalRuntimePermission) {
			PortalRuntimePermission portalRuntimePermission =
				(PortalRuntimePermission)permission;

			String name = portalRuntimePermission.getName();

			if (name.equals(
					PACLConstants.PORTAL_RUNTIME_PERMISSION_EXPANDO_BRIDGE)) {

				return PACLClassUtil.getPACLPolicyByReflection(
					true, _log.isDebugEnabled());
			}

			/*if (name.equals(
						PACLConstants.
							PORTAL_RUNTIME_PERMISSION_SET_BEAN_PROPERTY)) {

				paclPolicy = PACLClassUtil.getPACLPolicyByReflection(
					false, true);

				System.out.println("PACL policy " + paclPolicy);

				return paclPolicy;
			}*/
		}

		return PACLClassUtil.getPACLPolicyByReflection(
			false, _log.isDebugEnabled());
	}

	protected void initClasses() {

		// Load dependent classes to prevent ClassCircularityError

		_log.debug("Loading " + FileAvailabilityUtil.class.getName());
		_log.debug("Loading " + PortalHookPermission.class.getName());

		// Touch dependent classes to prevent NoClassDefError

		CheckerUtil.isAccessControllerDoPrivileged(0);
		PACLClassUtil.getPACLPolicyByReflection(false, false);
	}

	protected void initInitialContextFactoryBuilder() throws Exception {
		if (!NamingManager.hasInitialContextFactoryBuilder()) {
			PACLInitialContextFactoryBuilder paclInitialContextFactoryBuilder =
				new PACLInitialContextFactoryBuilder();

			if (_log.isInfoEnabled()) {
				_log.info("Overriding the initial context factory builder");
			}

			NamingManager.setInitialContextFactoryBuilder(
				paclInitialContextFactoryBuilder);
		}

		Class<?> clazz = NamingManager.class;

		String fieldName = "initctx_factory_builder";

		if (JavaDetector.isIBM()) {
			fieldName = "icfb";
		}

		Field field = clazz.getDeclaredField(fieldName);

		field.setAccessible(true);

		InitialContextFactoryBuilder initialContextFactoryBuilder =
			(InitialContextFactoryBuilder)field.get(null);

		if (initialContextFactoryBuilder
				instanceof PACLInitialContextFactoryBuilder) {

			return;
		}

		PACLInitialContextFactoryBuilder paclInitialContextFactoryBuilder =
			new PACLInitialContextFactoryBuilder();

		paclInitialContextFactoryBuilder.setInitialContextFactoryBuilder(
			initialContextFactoryBuilder);

		field.set(null, paclInitialContextFactoryBuilder);

		if (_log.isInfoEnabled()) {
			_log.info(
				"Overriding the initial context factory builder using " +
					"reflection");
		}
	}

	protected void parentCheckPermission(
		Permission permission, Object context) {

		if (_parentSecurityManager != null) {
			if (context == null) {
				context = getSecurityContext();
			}

			_parentSecurityManager.checkPermission(permission, context);
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		PortalSecurityManager.class.getName());

	private SecurityManager _parentSecurityManager;

}