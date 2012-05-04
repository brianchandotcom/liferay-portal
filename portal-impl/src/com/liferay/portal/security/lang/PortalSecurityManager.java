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
import com.liferay.portal.kernel.security.pacl.permission.PortalHookPermission;
import com.liferay.portal.security.pacl.PACLClassUtil;
import com.liferay.portal.security.pacl.PACLPolicy;
import com.liferay.portal.security.pacl.PACLPolicyManager;

import java.security.Permission;

import javax.naming.spi.NamingManager;

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

		initClasses();

		try {
			initInitialContextFactoryBuilder();
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	@Override
	public void checkPermission(Permission permission) {
		checkPermission(permission, getSecurityContext());
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

		return PACLClassUtil.getPACLPolicyByReflection(_log.isDebugEnabled());
	}

	protected void initClasses() {

		// Load dependent classes to prevent ClassCircularityError

		_log.debug("Loading " + PortalHookPermission.class.getName());

		// Touch dependent classes to prevent NoClassDefError

		PACLClassUtil.getPACLPolicyByReflection(false);
	}

	protected void initInitialContextFactoryBuilder() throws Exception {
		if (!_initInitialContextFactoryBuilder) {
			return;
		}

		_initInitialContextFactoryBuilder = false;

		if (NamingManager.hasInitialContextFactoryBuilder()) {
			if (_log.isInfoEnabled()) {
				_log.info(
					"Unable to override the initial context factory builder " +
						"because one already exists. JNDI security is not " +
							"enabled.");
			}

			return;
		}

		NamingManager.setInitialContextFactoryBuilder(
			new PACLInitialContextFactoryBuilder());
	}

	protected void parentCheckPermission(
		Permission permission, Object context) {

		if (_parentSecurityManager != null) {
			_parentSecurityManager.checkPermission(permission, context);
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		PortalSecurityManager.class.getName());

	private static boolean _initInitialContextFactoryBuilder = true;

	private SecurityManager _parentSecurityManager;

}