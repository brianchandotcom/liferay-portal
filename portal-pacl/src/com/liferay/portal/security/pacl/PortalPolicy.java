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

import java.security.AllPermission;
import java.security.CodeSource;
import java.security.Permission;
import java.security.PermissionCollection;
import java.security.Permissions;
import java.security.Policy;
import java.security.ProtectionDomain;

import java.util.Enumeration;

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
		PermissionCollection permissionCollection = null;

		if (_policy != null) {
			permissionCollection = _policy.getPermissions(codeSource);
		}

		if (permissionCollection == null) {
			permissionCollection = new Permissions();
		}

		return permissionCollection;
	}

	@Override
	public PermissionCollection getPermissions(
		ProtectionDomain protectionDomain) {

		if (protectionDomain == null) {
			return new Permissions();
		}

		PermissionCollection permissionCollection = getPermissions(
			protectionDomain.getCodeSource());

		if (permissionCollection != null) {
			return permissionCollection;
		}

		permissionCollection = new Permissions();

		if (_policy != null) {
			_addExtraPermissions(
				permissionCollection, _policy.getPermissions(protectionDomain));
		}

		_addExtraPermissions(
			permissionCollection, protectionDomain.getPermissions());

		PACLPolicy paclPolicy = PACLPolicyManager.getPACLPolicy(
			protectionDomain.getClassLoader());

		if (paclPolicy != null) {
			return new PortalPermissionCollection(
				paclPolicy, permissionCollection);
		}

		permissionCollection.add(_allPermission);

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
			((protectionDomain.getClassLoader() == null) ||
			 !_paclPolicy.isCheckablePermission(permission))) {

			return _checkWithParentPolicy(protectionDomain, permission);
		}

		PermissionCollection permissionCollection = getPermissions(
			protectionDomain);

		if (permissionCollection.implies(permission)) {
			return _checkWithParentPolicy(protectionDomain, permission);
		}
		else if (_checkWithPACLPolicyPolicy(
					protectionDomain, permission, permissionCollection)) {

			return _checkWithParentPolicy(protectionDomain, permission);
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
	}

	private void _addExtraPermissions(
		PermissionCollection permissionCollection,
		PermissionCollection staticPermissionCollection) {

		if (staticPermissionCollection == null) {
			return;
		}

		synchronized (staticPermissionCollection) {
			Enumeration<Permission> enumeration =
				staticPermissionCollection.elements();

			while (enumeration.hasMoreElements()) {
				permissionCollection.add(enumeration.nextElement());
			}
		}
	}

	private boolean _checkWithPACLPolicyPolicy(
		ProtectionDomain protectionDomain, Permission permission,
		PermissionCollection permissionCollection) {

		if (!(permissionCollection instanceof PortalPermissionCollection)) {
			return false;
		}

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

		if (_policy != null) {
			return _policy.implies(protectionDomain, permission);
		}

		return true;
	}

	private static AllPermission _allPermission = new AllPermission();
	private static ThreadLocal<Boolean> _started = new ThreadLocal<Boolean>() {

		@Override
		protected Boolean initialValue() {
			return Boolean.FALSE;
		}

	};

	private PACLPolicy _paclPolicy = PACLPolicyManager.getDefaultPACLPolicy();
	private Policy _policy;

}