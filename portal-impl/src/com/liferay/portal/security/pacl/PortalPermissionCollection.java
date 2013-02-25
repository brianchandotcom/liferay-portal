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

import java.security.Permission;
import java.security.PermissionCollection;
import java.security.Policy;

import java.util.Collections;
import java.util.Enumeration;

/**
 * @author Raymond Augé
 */
public class PortalPermissionCollection extends PermissionCollection {

	public PortalPermissionCollection(
		PACLPolicy paclPolicy, PermissionCollection permissionCollection) {

		_paclPolicy = paclPolicy;
		_permissionCollection = permissionCollection;
	}

	@Override
	public void add(Permission permission) {
		throw new SecurityException();
	}

	public Policy getJavaSecurityPolicy() {
		return null; // TODO Future API : _paclPolicy.getJavaSecurityPolicy();
	}

	@Override
	public boolean implies(Permission permission) {
		if (!_paclPolicy.isActive()) {
			return true;
		}

		if (_permissionCollection.implies(permission)) {
			return true;
		}

		try {
			_paclPolicy.checkPermission(permission);
		}
		catch (SecurityException se) {
			return false;
		}

		return true;
	}

	@Override
	public Enumeration<Permission> elements() {
		return Collections.enumeration(Collections.EMPTY_LIST);
	}

	@Override
	public String toString() {
		return "PortalPermissionCollection".concat(_paclPolicy.toString());
	}

	private PACLPolicy _paclPolicy;
	private PermissionCollection _permissionCollection;

}