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

package com.liferay.portal.security.pacl.checker;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.security.Permission;
import java.security.Permissions;

import java.util.PropertyPermission;
import java.util.Set;

/**
 * @author Raymond Augé
 */
public class PropertyChecker extends BaseChecker {

	@Override
	public void afterPropertiesSet() {
		initProperties();
	}

	@Override
	public AuthorizationProperty generateAuthorizationProperty(
		Object... arguments) {

		if ((arguments != null) && (arguments.length == 1) &&
			!(arguments[0] instanceof Permission)) {

			return null;
		}

		Permission permission = (Permission)arguments[0];

		String actions = permission.getActions();

		AuthorizationProperty authorizationProperty =
			new AuthorizationProperty();

		if (actions.equals(PROPERTY_PERMISSION_WRITE)) {
			authorizationProperty.setKey("security-manager-properties-write");
		}
		else {
			authorizationProperty.setKey("security-manager-properties-read");
		}

		authorizationProperty.setValue(permission.getName());

		return authorizationProperty;
	}

	@Override
	public boolean implies(Permission permission) {
		if (_permissions.implies(permission)) {
			return true;
		}

		String name = permission.getName();
		String action = permission.getActions();

		logSecurityException(
			_log, "Attempted to " + action + " system property " + name);

		return false;
	}

	protected void initProperties() {
		Set<String> names = getPropertySet("security-manager-properties-read");

		for (String name : names) {
			Permission permission = new PropertyPermission(
				name, PROPERTY_PERMISSION_READ);

			_permissions.add(permission);
		}

		names = getPropertySet("security-manager-properties-write");

		for (String name : names) {
			Permission permission = new PropertyPermission(
				name, PROPERTY_PERMISSION_WRITE);

			_permissions.add(permission);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PropertyChecker.class);

	private final Permissions _permissions = new Permissions();

}