/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.security.pacl.permission.CheckMemberAccessPermission;
import com.liferay.portal.kernel.security.pacl.permission.PortalHookPermission;
import com.liferay.portal.kernel.security.pacl.permission.PortalMessageBusPermission;
import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;
import com.liferay.portal.kernel.security.pacl.permission.PortalServicePermission;
import com.liferay.portal.kernel.util.WeakValueConcurrentHashMap;

import java.net.URL;

import java.security.AllPermission;
import java.security.CodeSource;
import java.security.Permission;
import java.security.PermissionCollection;
import java.security.Permissions;
import java.security.Policy;
import java.security.ProtectionDomain;

import java.util.concurrent.ConcurrentMap;

/**
 * @author Raymond Augé
 */
public class PortalPolicy extends Policy {

	public PortalPolicy(Policy policy) {
		if (policy instanceof PortalPolicy) {
			throw new IllegalArgumentException(
				"Liferay's PortalPolicy class can not wrap itself");
		}

		_policy = policy;
	}

	@Override
	public PermissionCollection getPermissions(CodeSource codeSource) {
		PermissionCollection permissionCollection =
			new ForgetfulPermissionCollection();

		if ((codeSource == null) || (codeSource.getLocation() == null)) {
			return permissionCollection;
		}

		URL location = codeSource.getLocation();

		PermissionCollection cachedPermissionCollection =
			_permissionCollections.get(location);

		if (cachedPermissionCollection != null) {
			return cachedPermissionCollection;
		}

		PACLPolicy paclPolicy = PACLPolicyManager.getPACLPolicy(location);

		if (paclPolicy != null) {
			permissionCollection = new PortalPermissionCollection(paclPolicy);
		}

		_permissionCollections.put(location, permissionCollection);

		return permissionCollection;
	}

	@Override
	public PermissionCollection getPermissions(
		ProtectionDomain protectionDomain) {

		PermissionCollection permissionCollection =
			new ForgetfulPermissionCollection();

		if (protectionDomain == null) {
			return permissionCollection;
		}

		PermissionCollection cachedPermissionCollection =
			_permissionCollections.get(protectionDomain);

		if (cachedPermissionCollection != null) {
			return cachedPermissionCollection;
		}

		PACLPolicy paclPolicy = PACLPolicyManager.getPACLPolicy(
			protectionDomain);

		if (paclPolicy != null) {
			permissionCollection = new PortalPermissionCollection(paclPolicy);
		}
		else {
			permissionCollection = new Permissions();

			permissionCollection.add(_allPermission);
		}

		_permissionCollections.put(protectionDomain, permissionCollection);

		return permissionCollection;
	}

	@Override
	public boolean implies(
		ProtectionDomain protectionDomain, Permission permission) {

		if (_started.get().booleanValue()) {
			return true;
		}

		try {
			_started.set(true);

			if (!(permission instanceof PACLUtil.Permission) &&
				!_paclPolicy.isCheckablePermission(permission)) {

				return _checkWithParentPolicy(protectionDomain, permission);
			}

			PermissionCollection permissionCollection = getPermissions(
				protectionDomain);

			if (permissionCollection instanceof PortalPermissionCollection) {
				if (permissionCollection.implies(permission) ||
					_checkWithPACLPolicyPolicy(
						protectionDomain, permission, permissionCollection)) {

					return true;
				}
			}
			else {
				return _checkWithParentPolicy(protectionDomain, permission);
			}

			if (_log.isDebugEnabled()) {
				_log.debug(
					"FAILED for {Permission " + permission +
						"} on { " + protectionDomain + "}");
			}

			return false;
		}
		finally {
			_started.set(false);
		}
	}

	@Override
	public void refresh() {
		if (_policy != null) {
			_policy.refresh();
		}

		_permissionCollections.clear();
	}

	private boolean _checkWithPACLPolicyPolicy(
		ProtectionDomain protectionDomain, Permission permission,
		PermissionCollection permissionCollection) {

		PortalPermissionCollection portalPermissionCollection =
			(PortalPermissionCollection)permissionCollection;

		Policy policy = portalPermissionCollection.getPolicy();

		ClassLoader classLoader = portalPermissionCollection.getClassLoader();

		if ((policy != null) &&
			(classLoader == protectionDomain.getClassLoader())) {

			return policy.implies(protectionDomain, permission);
		}

		return false;
	}

	private boolean _checkWithParentPolicy(
		ProtectionDomain protectionDomain, Permission permission) {

		if ((_policy != null) &&
			!(permission instanceof CheckMemberAccessPermission) &&
			!(permission instanceof PortalHookPermission) &&
			!(permission instanceof PortalMessageBusPermission) &&
			!(permission instanceof PortalRuntimePermission) &&
			!(permission instanceof PortalServicePermission) &&
			!(permission instanceof PACLUtil.Permission)) {

			boolean result = _policy.implies(protectionDomain, permission);

			if (_log.isDebugEnabled() && !result) {
				_log.debug(
					"FAILED for {Permission " + permission +
						"} on { " + protectionDomain + "}");
			}

			return result;
		}

		return true;
	}

	private static AllPermission _allPermission = new AllPermission();
	private static Log _log = LogFactoryUtil.getLog(PortalPolicy.class);
	private static ThreadLocal<Boolean> _started = new ThreadLocal<Boolean>() {

		@Override
		protected Boolean initialValue() {
			return Boolean.FALSE;
		}

	};

	private PACLPolicy _paclPolicy = PACLPolicyManager.getDefaultPACLPolicy();
	private ConcurrentMap<Object, PermissionCollection> _permissionCollections =
		new WeakValueConcurrentHashMap<Object, PermissionCollection>();
	private Policy _policy;

}