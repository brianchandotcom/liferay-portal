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

import com.liferay.portal.kernel.util.WeakValueConcurrentHashMap;
import com.liferay.portal.util.PropsValues;

import java.lang.reflect.Field;

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

/**
 * @author Raymond Augé
 */
public class PortalPolicy extends Policy {

	public PortalPolicy(Policy policy) {
		_parent = policy;

		init();
	}

	@Override
	public Provider getProvider() {
		Provider provider = null;

		if (_parent != null) {
			provider = _parent.getProvider();
		}

		return provider;
	}

	@Override
	public String getType() {
		String type = null;

		if (_parent != null) {
			type= _parent.getType();
		}

		return type;
	}

	@Override
	public Parameters getParameters() {
		Parameters parameters = null;

		if (_parent != null) {
			parameters = _parent.getParameters();
		}

		return parameters;
	}

	@Override
	public PermissionCollection getPermissions(CodeSource codesource) {
		PermissionCollection permissionCollection = null;

		if (_parent != null) {
			permissionCollection = _parent.getPermissions(codesource);
		}

		String string = _BLANK;

		if ((codesource != null) && (codesource.getLocation() != null)) {
			string = codesource.getLocation().toString();
		}

		if (string.equals(_BLANK) ||
			string.contains(PropsValues.LIFERAY_LIB_GLOBAL_DIR) ||
			string.contains(PropsValues.LIFERAY_LIB_GLOBAL_SHARED_DIR) ||
			string.contains(PropsValues.LIFERAY_LIB_PORTAL_DIR) ||
			string.contains(PropsValues.LIFERAY_WEB_PORTAL_DIR) ||
			string.contains(PropsValues.MODULE_FRAMEWORK_CORE_DIR) ||
			string.contains(PropsValues.MODULE_FRAMEWORK_PORTAL_DIR)) {

			if (permissionCollection == null) {
				permissionCollection = new Permissions();
			}

			permissionCollection.add(_ALL_PERMISSION);
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

		Object key = getKey(protectionDomain);

		permissionCollection = _protectionDomainMapping.get(key);

		if (permissionCollection != null) {
			return permissionCollection;
		}

		permissionCollection = getPermissions(protectionDomain.getCodeSource());

		if (permissionCollection == null) {
			permissionCollection = new Permissions();
		}

		if (_parent != null) {
			addExtraPermissions(
				permissionCollection, _parent.getPermissions(protectionDomain));
		}

		addExtraPermissions(
			permissionCollection, protectionDomain.getPermissions());

		PACLPolicy paclPolicy = PACLPolicyManager.getPACLPolicy(
			protectionDomain.getClassLoader());

		if (paclPolicy == null) {
			paclPolicy = _defaultPaclPolicy;

			permissionCollection.add(_ALL_PERMISSION);
		}

		return paclPolicyToPermissionCollection(
			paclPolicy, permissionCollection);
	}

	@Override
	public boolean implies(
		ProtectionDomain protectionDomain, Permission permission) {

		if ((protectionDomain.getClassLoader() == null) ||
			!PACLPolicyManager.isActive()||
			!_defaultPaclPolicy.isCheckablePermission(permission)) {

			return checkWithParent(protectionDomain, permission);
		}

		Object key = getKey(protectionDomain);

		PermissionCollection permissionCollection =
			_protectionDomainMapping.get(key);

		if (permissionCollection != null) {
			if (permissionCollection.implies(permission)) {
				return checkWithParent(protectionDomain, permission);
			}
			else if (checkWithJavaSecurityPolicy(
						protectionDomain, permission, permissionCollection)) {

				return checkWithParent(protectionDomain, permission);
			}

			return false;
		}

		permissionCollection = getPermissions(protectionDomain);

		_protectionDomainMapping.putIfAbsent(key, permissionCollection);

		if (permissionCollection.implies(permission)) {
			return checkWithParent(protectionDomain, permission);
		}
		else if (checkWithJavaSecurityPolicy(
					protectionDomain, permission, permissionCollection)) {

			return checkWithParent(protectionDomain, permission);
		}

		return false;
	}

	@Override
	public void refresh() {
		if (_parent != null) {
			_parent.refresh();
		}

		_protectionDomainMapping.clear();
	}

	private void addExtraPermissions(
		PermissionCollection permissionCollection,
		PermissionCollection staticPermissionCollection) {

		if (staticPermissionCollection == null) {
			return;
		}

		synchronized (staticPermissionCollection) {
			Enumeration<Permission> e = staticPermissionCollection.elements();

			while (e.hasMoreElements()) {
				permissionCollection.add(e.nextElement());
			}
		}
	}

	private boolean checkWithJavaSecurityPolicy(
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

	private boolean checkWithParent(
		ProtectionDomain protectionDomain, Permission permission) {

		if (_parent != null) {
			return _parent.implies(protectionDomain, permission);
		}

		return true;
	}

	private void init() {
		try {
			_keyField = AccessController.doPrivileged(
				new PrivilegedExceptionAction<Field>() {
					public Field run() throws Exception {
						Field keyField =
							ProtectionDomain.class.getDeclaredField("key");

						keyField.setAccessible(true);

						return keyField;
					}
				}
			);
		}
		catch (PrivilegedActionException e) {
			throw new IllegalStateException(
				"Lifray needs to be able to change the accessibility of " +
					"the ProtectionDomain.key field. Try granting ", e);
		}

		_policyProtectionDomain = AccessController.doPrivileged(
			new PrivilegedAction<ProtectionDomain>() {
				public ProtectionDomain run() {
					return this.getClass().getProtectionDomain();
				}
			}
		);

		PermissionCollection policyPerms = new Permissions();

		policyPerms.add(_ALL_PERMISSION);

		_protectionDomainMapping.put(getKey(
			_policyProtectionDomain), policyPerms);

		_defaultPaclPolicy = PACLPolicyManager.getDefaultPACLPolicy();
	}

	private PermissionCollection paclPolicyToPermissionCollection(
		PACLPolicy paclPolicy, PermissionCollection permissionCollection) {

		return new PortalPermissionCollection(paclPolicy, permissionCollection);
	}

	protected Object getKey(ProtectionDomain protectionDomain) {
		try {
			return _keyField.get(protectionDomain);
		}
		catch (Exception e) {
			return protectionDomain.toString().hashCode();
		}
	}

	private static final AllPermission _ALL_PERMISSION = new AllPermission();

	private static final String _BLANK = "";

	private PACLPolicy _defaultPaclPolicy;
	private Field _keyField;
	private Policy _parent;
	private ProtectionDomain _policyProtectionDomain;
	private WeakValueConcurrentHashMap<Object, PermissionCollection>
		_protectionDomainMapping =
			new WeakValueConcurrentHashMap<Object, PermissionCollection>();

}