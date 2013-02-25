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

import com.liferay.portal.jndi.pacl.PACLInitialContextFactoryBuilder;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.pacl.permission.PortalHookPermission;
import com.liferay.portal.kernel.servlet.taglib.FileAvailabilityUtil;
import com.liferay.portal.kernel.util.JavaDetector;
import com.liferay.portal.security.lang.PortalSecurityManager;
import com.liferay.portal.security.pacl.checker.CheckerUtil;

import java.lang.reflect.Field;

import java.security.Policy;

import javax.naming.spi.InitialContextFactoryBuilder;
import javax.naming.spi.NamingManager;

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
					"Unable to override the original java security policy " +
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

	private static Log _log = LogFactoryUtil.getLog(
		PortalSecurityManagerImpl.class.getName());

	private Policy _policy;

}