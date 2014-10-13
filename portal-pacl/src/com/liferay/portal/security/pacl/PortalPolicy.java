/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.concurrent.ConcurrentReferenceValueHashMap;
import com.liferay.portal.kernel.memory.FinalizeManager;
import com.liferay.portal.kernel.security.pacl.permission.PortalHookPermission;
import com.liferay.portal.kernel.security.pacl.permission.PortalMessageBusPermission;
import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;
import com.liferay.portal.kernel.security.pacl.permission.PortalServicePermission;
import com.liferay.portal.kernel.util.JavaDetector;

import java.lang.reflect.Field;

import java.security.AccessControlException;
import java.security.AccessController;
import java.security.AllPermission;
import java.security.CodeSource;
import java.security.Permission;
import java.security.PermissionCollection;
import java.security.Policy;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.security.ProtectionDomain;

import java.util.Enumeration;
import java.util.concurrent.ConcurrentMap;

/**
 * @author Raymond Augé
 */
public class PortalPolicy extends Policy {

	public PortalPolicy(Policy policy) throws PrivilegedActionException {
		if (policy instanceof PortalPolicy) {
			throw new IllegalArgumentException(
				"Liferay's PortalPolicy class should not wrap itself");
		}

		_policy = policy;

		_field = AccessController.doPrivileged(
			new FieldPrivilegedExceptionAction());
	}

	public Policy getOriginalPolicy() {
		return _policy;
	}

	@Override
	public PermissionCollection getPermissions(CodeSource codeSource) {
		if ((codeSource == null) || (codeSource.getLocation() == null)) {
			return new LenientPermissionCollection();
		}

		URLWrapper urlWrapper = new URLWrapper(codeSource.getLocation());

		PermissionCollection permissionCollection =
			_urlPermissionCollections.get(urlWrapper);

		if (permissionCollection != null) {
			return permissionCollection;
		}

		PACLPolicy paclPolicy = PACLPolicyManager.getPACLPolicy(
			codeSource.getLocation());

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
			if (JavaDetector.isIBM()) {
				permissionCollection = _policy.getPermissions(protectionDomain);
			}
			else {
				permissionCollection = new LenientPermissionCollection();
			}
		}

		if (key != null) {
			_permissionCollections.put(key, permissionCollection);
		}

		return permissionCollection;
	}

	@Override
	public boolean implies(
		ProtectionDomain protectionDomain, Permission permission) {

		if (_started.get()) {
			return true;
		}

		try {
			_started.set(true);

			PermissionCollection permissionCollection = null;

			if (JavaDetector.isIBM()) {
				permissionCollection = getPermissions(protectionDomain);

				if ((permissionCollection != null) &&
					!(permissionCollection instanceof
						PortalPermissionCollection)) {

					Enumeration<Permission> enumeration =
						permissionCollection.elements();

					while (enumeration.hasMoreElements()) {
						Permission curPermission = enumeration.nextElement();

						if (curPermission instanceof AllPermission) {
							return true;
						}
					}
				}
			}

			if (!(permission instanceof PACLUtil.Permission) &&
				!_paclPolicy.isCheckablePermission(permission)) {

				return _checkWithParentPolicy(protectionDomain, permission);
			}

			if (!JavaDetector.isIBM()) {
				permissionCollection = getPermissions(protectionDomain);
			}

			if (permissionCollection instanceof
					PortalPermissionCollection) {

				if (permissionCollection.implies(permission) ||
					_checkWithPACLPolicyPolicy(
						protectionDomain, permission, permissionCollection)) {

					return true;
				}

				throw new AccessControlException(
					"Access denied " + permission, permission);
			}

			return _checkWithParentPolicy(protectionDomain, permission);
		}
		finally {
			_started.remove();
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

	@SuppressWarnings("deprecation")
	private boolean _checkWithParentPolicy(
		ProtectionDomain protectionDomain, Permission permission) {

		if ((_policy != null) &&
			!(permission instanceof
				com.liferay.portal.kernel.security.pacl.permission.
					CheckMemberAccessPermission) &&
			!(permission instanceof PortalHookPermission) &&
			!(permission instanceof PortalMessageBusPermission) &&
			!(permission instanceof PortalRuntimePermission) &&
			!(permission instanceof PortalServicePermission) &&
			!(permission instanceof PACLUtil.Permission)) {

			return _policy.implies(protectionDomain, permission);
		}

		return true;
	}

	private Object _getKey(ProtectionDomain protectionDomain) {
		try {
			return _field.get(protectionDomain);
		}
		catch (Exception e) {
			return null;
		}
	}

	private static final ThreadLocal<Boolean> _started =
		new ThreadLocal<Boolean>() {

			@Override
			protected Boolean initialValue() {
				return Boolean.FALSE;
			}

		};

	private final Field _field;
	private final PACLPolicy _paclPolicy =
		PACLPolicyManager.getDefaultPACLPolicy();
	private final ConcurrentMap<Object, PermissionCollection>
		_permissionCollections =
			new ConcurrentReferenceValueHashMap<Object, PermissionCollection>(
				FinalizeManager.WEAK_REFERENCE_FACTORY);
	private final Policy _policy;
	private final ConcurrentMap<URLWrapper, PermissionCollection>
		_urlPermissionCollections = new ConcurrentReferenceValueHashMap
			<URLWrapper, PermissionCollection>(
				FinalizeManager.WEAK_REFERENCE_FACTORY);

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