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

package com.liferay.portal.security.pacl;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.pacl.permission.PortalHookPermission;
import com.liferay.portal.kernel.servlet.taglib.FileAvailabilityUtil;
import com.liferay.portal.kernel.util.AutoResetThreadLocal;
import com.liferay.portal.kernel.util.JavaDetector;
import com.liferay.portal.security.lang.PortalSecurityManager;
import com.liferay.portal.security.pacl.checker.CheckerUtil;
import com.liferay.portal.security.pacl.jndi.PACLInitialContextFactoryBuilder;

import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.ReflectPermission;

import java.security.AccessController;
import java.security.Permission;
import java.security.Policy;

import javax.naming.spi.InitialContextFactoryBuilder;
import javax.naming.spi.NamingManager;

import sun.security.util.SecurityConstants;

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
public class PortalSecurityManagerImpl extends SecurityManager
	implements PortalSecurityManager {

	public PortalSecurityManagerImpl() {
		SecurityManager securityManager = System.getSecurityManager();

		initClasses();

		try {
			Policy policy = null;

			if (securityManager != null) {
				policy = Policy.getPolicy();
			}

			_policy = new PortalPolicy(policy);

			Policy.setPolicy(_policy);

			_policy.refresh();
		}
		catch (Exception e) {
			if (_log.isInfoEnabled()) {
				_log.info(
					"Unable to override the original Java security policy " +
						"because sufficient privileges are not granted to " +
							"Liferay. PACL is not enabled.");
			}

			if (_log.isWarnEnabled()) {
				_log.warn(e, e);
			}

			return;
		}

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
	public void checkPermission(Permission permission) {
		boolean loopSupport = false;

		try {
			if ((permission instanceof ReflectPermission) &&
				(permission.getName().equals("suppressAccessChecks")) &&
				(_checkMembersAccessClassLoader.get() != null)) {

				// The suppressAccessChecks is particularly difficult to
				// handle because the java API does not have a mechanism to
				// get the class on which the accessibility is being suppressed.
				// This makes it difficult to differentiate between code
				// changing it's own accessibility (OK) from accessibility
				// changes on foreign code (Not OK). However there is a common
				// programming pattern we can take advantage of to short circuit
				// the problem.
				//
				// The common programming pattern is:
				//
				//     T t = clazz.getDeclared*(..);
				//     t.setAccessible(true);
				//
				// i.e. a call to getDeclared* followed immediately by a call
				//      to change accessibility of t.
				//
				// The getDeclared* calls on the Class result in a call to
				// SecurityManager.checkMemberAccess(clazz, accessType) in which
				// case if the target class and the caller class are from the
				// same classLoader the checking is short circuited with a
				// successful result. If this short circuit happens in our
				// implementation we will store the classLoader of the target
				// class, and on the very next permission check, if the check is
				// for "suppressAccessChecks" AND the classLoader of the caller
				// is the same as the stored classLoader from the previous check
				// we will also allow the check to succeed. In any case, the
				// thread local is purged to avoid later erroneous successes.

				Class<?> stack[] = getClassContext();

				// someCaller                         [2]
				// java.lang.reflect.AccessibleObject [1] Constructor, Field, or
				//                                        Method
				// SecurityManager.checkMemberAccess  [0]

				if (_checkMembersAccessClassLoader.get() ==
						stack[2].getClassLoader()) {

					// The loopSupport variable is set to true so that if the
					// initial call was to one of the array result methods:
					//
					//     T[] t = clazz.getDeclared*s(..);
					//
					// followed by a loop over the array the code will still
					// work. We'll hang onto the classLoader as long as
					// subsequent checks are for "suppressAccessChecks" and the
					// class loader still matches.

					loopSupport = true;

					return;
				}
			}

			java.security.AccessController.checkPermission(permission);
		}
		finally {
			if (!loopSupport) {
				_checkMembersAccessClassLoader.set(null);
			}
		}
	}

	@Override
	public void checkMemberAccess(Class<?> clazz, int accessibility) {
		if (clazz == null) {
			throw new NullPointerException("class can't be null");
		}

		if (accessibility == Member.PUBLIC) {
			return;
		}

		Class<?> stack[] = getClassContext();

		// stack depth of 4 should be the caller of one of the
		// methods in java.lang.Class that invoke checkMember
		// access. The stack should look like:
		//
		// someCaller                        [3]
		// java.lang.Class.someReflectionAPI [2]
		// java.lang.Class.checkMemberAccess [1]
		// SecurityManager.checkMemberAccess [0]

		ClassLoader clazzClassLoader = clazz.getClassLoader();

		if ((stack.length < 4) ||
			(stack[3].getClassLoader() != clazzClassLoader)) {

			_checkMembersAccessClassLoader.set(null);

			checkPermission(SecurityConstants.CHECK_MEMBER_ACCESS_PERMISSION);
		}
		else {
			_checkMembersAccessClassLoader.set(clazzClassLoader);
		}
	}

	public Policy getPolicy() {
		return _policy;
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

	private static ThreadLocal<ClassLoader> _checkMembersAccessClassLoader =
		new AutoResetThreadLocal<ClassLoader>(
			PortalSecurityManagerImpl.class +
				"._checkMembersAccessClassLoader", null);

	private static Log _log = LogFactoryUtil.getLog(
		PortalSecurityManagerImpl.class.getName());

	private Policy _policy;

}