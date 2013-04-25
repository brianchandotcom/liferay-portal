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

package com.liferay.portal.security.pacl.checker;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.awt.AWTPermission;

import java.security.Permission;
import java.security.Permissions;

import java.util.Set;

/**
 * @author Raymond Augé
 */
public class AWTChecker extends BaseChecker {

	public void afterPropertiesSet() {
		initOperations();
	}

	@Override
	public AuthorizationProperty generateAuthorizationProperty
		(Object... arguments) {

		if ((arguments != null) && (arguments.length == 1) &&
			(arguments[0] instanceof Permission)) {

			return null;
		}

		Permission permission = (Permission)arguments[0];

		AuthorizationProperty authorizationProperty =
			new AuthorizationProperty();

		authorizationProperty.setKey("security-manager-awt-operations");
		authorizationProperty.setValue(permission.getName());

		return authorizationProperty;
	}

	public boolean implies(Permission permission) {
		if (_permissions.implies(permission)) {
			return true;
		}

		String name = permission.getName();

		logSecurityException(_log, "Attempted operation " + name + " on AWT");

		return false;
	}

	protected void initOperations() {
		Set<String> names = getPropertySet("security-manager-awt-operations");

		for (String name : names) {
			Permission permission = new AWTPermission(name);

			_permissions.add(permission);
		}
	}

	private static Log _log = LogFactoryUtil.getLog(AWTChecker.class);

	private Permissions _permissions = new Permissions();

}