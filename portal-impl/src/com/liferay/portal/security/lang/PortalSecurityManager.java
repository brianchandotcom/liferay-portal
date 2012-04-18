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
import com.liferay.portal.security.pacl.PACLPolicyManager;
import com.liferay.portal.security.pacl.permission.PortalPermission;
import com.liferay.portal.spring.util.FilterClassLoader;

import java.security.Permission;

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
		doCheckPermission(permission, getSecurityContext());
	}

	@Override
	public void checkPermission(Permission permission, Object context) {
		doCheckPermission(permission, context);
	}

	protected void doCheckPermission(Permission permission, Object context) {
		boolean enabled = PortalSecurityManagerThreadLocal.isEnabled();

		if (!enabled) {
			if (_parentSecurityManager != null) {
				_parentSecurityManager.checkPermission(permission, context);
			}

			return;
		}

		PACLPolicy paclPolicy = getPACLPolicy(permission, _log.isDebugEnabled());

		if ((paclPolicy == null) || !paclPolicy.isActive()) {
			if (_parentSecurityManager != null) {
				_parentSecurityManager.checkPermission(permission, context);
			}

			return;
		}

		paclPolicy.checkPermission(permission);

		if (_parentSecurityManager != null) {
			_parentSecurityManager.checkPermission(permission, context);
		}
	}

	protected PACLPolicy getPACLPolicy(Permission permission, boolean debug) {
		boolean enabled = PortalSecurityManagerThreadLocal.isEnabled();

		try {
			PortalSecurityManagerThreadLocal.setEnabled(false);

			if (permission instanceof PortalPermission) {
				PortalPermission portalPermission =
					(PortalPermission)permission;

				ClassLoader policyClassLoader =
					portalPermission.getPolicyClassLoader();

				if (policyClassLoader != null) {
					return PACLPolicyManager.getPACLPolicy(policyClassLoader);
				}
			}

			return PACLClassUtil.getPACLPolicyBySecurityManagerClassContext(
				getClassContext(), debug);
		}
		finally {
			PortalSecurityManagerThreadLocal.setEnabled(enabled);
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		PortalSecurityManager.class.getName());

	private SecurityManager _parentSecurityManager;

}