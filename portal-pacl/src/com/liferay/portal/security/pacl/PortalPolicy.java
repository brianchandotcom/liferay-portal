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

import java.net.URL;

import java.security.AccessControlException;
import java.security.AccessController;
import java.security.CodeSource;
import java.security.Permission;
import java.security.PermissionCollection;
import java.security.Policy;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.security.ProtectionDomain;

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
		if ((codeSource == null) || (codeSource.getLocation() == null)) {
			return new LenientPermissionCollection();
		}

		URL location = codeSource.getLocation();

		URLWrapper urlWrapper = new URLWrapper(location);

		PermissionCollection permissionCollection =
			_urlPermissionCollections.get(urlWrapper);

		if (permissionCollection != null) {
			return permissionCollection;
		}

		PACLPolicy paclPolicy = PACLPolicyManager.getPACLPolicy(location);

		if (paclPolicy != null) {
			permissionCollection = new PortalPermissionCollection(paclPolicy);
		}
		else {
			permissionCollection = new LenientPermissionCollection();
		}

		_urlPermissionCollections.put(urlWrapper, permissionCollection);

		return permissionCollection;
	}

	@Override
	public PermissionCollection getPermissions(
		ProtectionDomain protectionDomain) {

		if (protectionDomain == null) {
			return new LenientPermissionCollection();
		}

		Object key = _getKey(protectionDomain);

		PermissionCollection permissionCollection = null;

		if (key != null) {
			permissionCollection = _permissionCollections.get(key);
		}

		if (permissionCollection == null) {
			CodeSource codeSource = protectionDomain.getCodeSource();

			if ((codeSource != null) && (codeSource.getLocation() != null)) {
				permissionCollection = _urlPermissionCollections.get(
					new URLWrapper(codeSource.getLocation()));
			}
		}

		if (permissionCollection != null) {
			return permissionCollection;
		}

		PACLPolicy paclPolicy = PACLPolicyManager.getPACLPolicy(
			protectionDomain);

		if (paclPolicy != null) {
			permissionCollection = new PortalPermissionCollection(paclPolicy);
		}
		else {
			permissionCollection = new LenientPermissionCollection();
		}

		if (key != null) {
			_permissionCollections.put(key, permissionCollection);
		}

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

			PermissionCollection permissionCollection = null;

			if (!(permission instanceof PACLUtil.Permission) &&
				!_paclPolicy.isCheckablePermission(permission)) {

				return _checkWithParentPolicy(protectionDomain, permission);
			}

			if (permissionCollection instanceof
					PortalPermissionCollection) {

				if (permissionCollection.implies(permission) ||
					_checkWithPACLPolicyPolicy(
						protectionDomain, permission, permissionCollection)) {

					return true;
				}

				throw new AccessControlException(
					"access denied " + permission, permission);
			}

			return _checkWithParentPolicy(protectionDomain, permission);
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

		synchronized (_permissionCollections) {
			_permissionCollections.clear();
			_urlPermissionCollections.clear();
		}
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

	private static ThreadLocal<Boolean> _started = new ThreadLocal<Boolean>() {

		@Override
		protected Boolean initialValue() {
			return Boolean.FALSE;
		}

	};

	private Field _field;
	private PACLPolicy _paclPolicy = PACLPolicyManager.getDefaultPACLPolicy();
	private ConcurrentMap<Object, PermissionCollection> _permissionCollections =
		new WeakValueConcurrentHashMap<Object, PermissionCollection>();
	private Policy _policy;
	private ConcurrentMap<URLWrapper, PermissionCollection>
		_urlPermissionCollections =
			new WeakValueConcurrentHashMap<URLWrapper, PermissionCollection>();

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