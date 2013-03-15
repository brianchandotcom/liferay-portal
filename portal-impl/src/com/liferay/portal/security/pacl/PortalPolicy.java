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

import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.WeakValueConcurrentHashMap;
import com.liferay.portal.util.PropsValues;

import java.lang.reflect.Field;

import java.net.URL;

import java.security.AccessController;
import java.security.AllPermission;
import java.security.CodeSource;
import java.security.Permission;
import java.security.PermissionCollection;
import java.security.Permissions;
import java.security.Policy;
import java.security.PrivilegedAction;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.security.ProtectionDomain;
import java.security.Provider;

import java.util.Enumeration;
import java.util.concurrent.ConcurrentMap;

/**
 * @author Raymond Augé
 */
public class PortalPolicy extends Policy {

	public PortalPolicy(Policy policy) {
		_policy = policy;

		_init();
	}

	@Override
	public Parameters getParameters() {
		Parameters parameters = null;

		if (_policy != null) {
			parameters = _policy.getParameters();
		}

		return parameters;
	}

	@Override
	public PermissionCollection getPermissions(CodeSource codeSource) {
		PermissionCollection permissionCollection = null;

		if (_policy != null) {
			permissionCollection = _policy.getPermissions(codeSource);
		}

		String location = StringPool.BLANK;

		URL url = codeSource.getLocation();

		if ((codeSource != null) && (url != null)) {
			location = url.toString();
		}

		if (location.equals(StringPool.BLANK) ||
			location.contains(PropsValues.LIFERAY_LIB_GLOBAL_DIR) ||
			location.contains(PropsValues.LIFERAY_LIB_GLOBAL_SHARED_DIR) ||
			location.contains(PropsValues.LIFERAY_LIB_PORTAL_DIR) ||
			location.contains(PropsValues.LIFERAY_WEB_PORTAL_DIR) ||
			location.contains(PropsValues.MODULE_FRAMEWORK_CORE_DIR) ||
			location.contains(PropsValues.MODULE_FRAMEWORK_PORTAL_DIR)) {

			if (permissionCollection == null) {
				permissionCollection = new Permissions();
			}

			permissionCollection.add(_allPermission);
		}

		if (permissionCollection == null) {
			permissionCollection = new Permissions();
		}

		return permissionCollection;
	}

	@Override
	public PermissionCollection getPermissions(
		ProtectionDomain protectionDomain) {

		PermissionCollection permissionCollection = null;

		if (protectionDomain == null) {
			return new Permissions();
		}

		Object key = _getKey(protectionDomain);

		permissionCollection = _permissionCollections.get(key);

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

		if (paclPolicy == null) {
			paclPolicy = _paclPolicy;

			permissionCollection.add(_allPermission);
		}

		return new PortalPermissionCollection(paclPolicy, permissionCollection);
	}

	@Override
	public Provider getProvider() {
		Provider provider = null;

		if (_policy != null) {
			provider = _policy.getProvider();
		}

		return provider;
	}

	@Override
	public String getType() {
		String type = null;

		if (_policy != null) {
			type= _policy.getType();
		}

		return type;
	}

	@Override
	public boolean implies(
		ProtectionDomain protectionDomain, Permission permission) {

		if ((!(permission instanceof PACLUtil.Permission)) &&
			((protectionDomain.getClassLoader() == null) ||
			 !PACLPolicyManager.isActive() ||
			 !_paclPolicy.isCheckablePermission(permission))) {

			return _checkWithParentPolicy(protectionDomain, permission);
		}

		Object key = _getKey(protectionDomain);

		PermissionCollection permissionCollection = _permissionCollections.get(
			key);

		if (permissionCollection != null) {
			if (permissionCollection.implies(permission)) {
				return _checkWithParentPolicy(protectionDomain, permission);
			}
			else if (_checkWithJavaSecurityPolicy(
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
		else if (_checkWithJavaSecurityPolicy(
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

		_permissionCollections.clear();
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

	private boolean _checkWithJavaSecurityPolicy(
		ProtectionDomain protectionDomain, Permission permission,
		PermissionCollection permissionCollection) {

		if (!(permissionCollection instanceof PortalPermissionCollection)) {
			return false;
		}

		PortalPermissionCollection portalPermissionCollection =
			(PortalPermissionCollection)permissionCollection;

		Policy javaSecurityPolicy =
			portalPermissionCollection.getJavaSecurityPolicy();

		if (javaSecurityPolicy != null) {
			return javaSecurityPolicy.implies(protectionDomain, permission);
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

	private void _init() {
		try {
			_field = AccessController.doPrivileged(
				new PrivilegedExceptionAction<Field>() {

					public Field run() throws Exception {
						Field field = ProtectionDomain.class.getDeclaredField(
							"key");

						field.setAccessible(true);

						return field;
					}

				}
			);
		}
		catch (PrivilegedActionException pae) {
			throw new IllegalStateException(
				"Lifray needs to be able to change the accessibility of the " +
					"key field in ProtectionDomain",
				pae);
		}

		_protectionDomain = AccessController.doPrivileged(
			new PrivilegedAction<ProtectionDomain>() {

				public ProtectionDomain run() {
					Class<?> clazz = getClass();

					return clazz.getProtectionDomain();
				}

			}
		);

		PermissionCollection permissionCollection = new Permissions();

		permissionCollection.add(_allPermission);

		_permissionCollections.put(
			_getKey(_protectionDomain), permissionCollection);
	}

	private static AllPermission _allPermission = new AllPermission();

	private Field _field;
	private PACLPolicy _paclPolicy = PACLPolicyManager.getDefaultPACLPolicy();
	private ConcurrentMap<Object, PermissionCollection> _permissionCollections =
		new WeakValueConcurrentHashMap<Object, PermissionCollection>();
	private Policy _policy;
	private ProtectionDomain _protectionDomain;

}