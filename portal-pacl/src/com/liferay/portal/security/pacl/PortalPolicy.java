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

import com.liferay.portal.kernel.util.WeakValueConcurrentHashMap;

import java.lang.reflect.Field;

import java.security.AccessController;
import java.security.AllPermission;
import java.security.CodeSource;
import java.security.Permission;
import java.security.PermissionCollection;
import java.security.Permissions;
import java.security.Policy;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.security.ProtectionDomain;

import java.util.Enumeration;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;

/**
 * @author Raymond Augé
 */
public class PortalPolicy extends Policy {

	public PortalPolicy(Policy policy) throws PrivilegedActionException {
		if (policy instanceof PortalPolicy) {
			throw new IllegalArgumentException(
				"Liferay's PortalPolicy class can not wrap itself");
		}

		_policy = policy;

		_field = AccessController.doPrivileged(
			new FieldPrivilegedExceptionAction());
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

		Object key = _getKey(protectionDomain);

		PermissionCollection permissionCollection = _getPermissionCollection(
			key);

		if (permissionCollection != null) {
			return permissionCollection;
		}

		permissionCollection = getPermissions(protectionDomain.getCodeSource());

		if (permissionCollection == null) {
			permissionCollection = new Permissions();
		}

		if (_policy != null) {
			_addExtraPermissions(
				permissionCollection, _policy.getPermissions(protectionDomain));
		}

		_addExtraPermissions(
			permissionCollection, protectionDomain.getPermissions());

		PACLPolicy paclPolicy = PACLPolicyManager.getPACLPolicy(
			protectionDomain.getClassLoader());

		if (paclPolicy != null) {
			return new PortalPermissionCollection(paclPolicy);
		}

		permissionCollection.add(_allPermission);

		return permissionCollection;
	}

	@Override
	public boolean implies(
		ProtectionDomain protectionDomain, Permission permission) {

		if (!(permission instanceof PACLUtil.Permission) &&
			((protectionDomain.getClassLoader() == null) ||
			 !_paclPolicy.isCheckablePermission(permission))) {

			return _checkWithParentPolicy(protectionDomain, permission);
		}

		Object key = _getKey(protectionDomain);

		PermissionCollection permissionCollection = _getPermissionCollection(
			key);

		if (permissionCollection != null) {
			if (permissionCollection.implies(permission)) {
				return _checkWithParentPolicy(protectionDomain, permission);
			}
			else if (_checkWithPACLPolicyPolicy(
						protectionDomain, permission, permissionCollection)) {

				return _checkWithParentPolicy(protectionDomain, permission);
			}

			return false;
		}

		permissionCollection = getPermissions(protectionDomain);

		_permissionCollections.putIfAbsent(key, permissionCollection);

		if (permissionCollection.implies(permission)) {
			return _checkWithParentPolicy(protectionDomain, permission);
		}
		else if (_checkWithPACLPolicyPolicy(
					protectionDomain, permission, permissionCollection)) {

			return _checkWithParentPolicy(protectionDomain, permission);
		}

		return false;
	}

	@Override
	public void refresh() {
		if (_policy != null) {
			_policy.refresh();
		}

		synchronized (_permissionCollections) {
			_permissionCollections.clear();

			_permissionCollections.putAll(_rootPermissionCollections);
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

	private Object _getKey(ProtectionDomain protectionDomain) {
		try {
			return _field.get(protectionDomain);
		}
		catch (Exception e) {
			String string = protectionDomain.toString();

			return string.hashCode();
		}
	}

	private PermissionCollection _getPermissionCollection(Object key) {
		PermissionCollection permissionCollection = _permissionCollections.get(
			key);

		if (permissionCollection == null) {
			permissionCollection = _rootPermissionCollections.get(key);

			if (permissionCollection != null) {
				_permissionCollections.putIfAbsent(key, permissionCollection);
			}
		}

		return permissionCollection;
	}

	private static AllPermission _allPermission = new AllPermission();

	private Field _field;
	private PACLPolicy _paclPolicy = PACLPolicyManager.getDefaultPACLPolicy();
	private ConcurrentMap<Object, PermissionCollection> _permissionCollections =
		new WeakValueConcurrentHashMap<Object, PermissionCollection>();
	private Policy _policy;
	private Map<Object, PermissionCollection> _rootPermissionCollections;

	private class FieldPrivilegedExceptionAction
		implements PrivilegedExceptionAction<Field> {

		@Override
		public Field run() throws Exception {
			Field field = ProtectionDomain.class.getDeclaredField("key");

			field.setAccessible(true);

			return field;
		}

	}

}