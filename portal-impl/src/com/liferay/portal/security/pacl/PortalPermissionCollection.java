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

import com.liferay.portal.kernel.util.StringPool;

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

	@Override
	public Enumeration<Permission> elements() {
		return Collections.enumeration(Collections.<Permission>emptyList());
	}

	public Policy getJavaSecurityPolicy() {
		return null;
	}

	@Override
	public boolean implies(Permission permission) {
		if (!_paclPolicy.isActive()) {
			return true;
		}

		if (_permissionCollection.implies(permission)) {
			return true;
		}

		if (!_paclPolicy.implies(permission)) {
			return false;
		}

		return true;
	}

	@Override
	public String toString() {
		Class<?> clazz = getClass();

		String className = clazz.getSimpleName();

		return className.concat(StringPool.POUND).concat(
			_paclPolicy.toString());
	}

	private PACLPolicy _paclPolicy;
	private PermissionCollection _permissionCollection;

}