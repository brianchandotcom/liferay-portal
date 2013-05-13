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

package com.liferay.portal.service.permission;

import com.liferay.portal.model.LayoutPrototype;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.PermissionChecker;

/**
 * @author Jorge Ferrer
 */
public class LayoutPrototypePermissionImpl
	implements LayoutPrototypePermission {

	@Override
	public void check(
			PermissionChecker permissionChecker, long layoutPrototypeId,
			String actionId)
		throws PrincipalException {

		if (!contains(permissionChecker, layoutPrototypeId, actionId)) {
			throw new PrincipalException();
		}
	}

	@Override
	public boolean contains(
		PermissionChecker permissionChecker, long layoutPrototypeId,
		String actionId) {

		if (permissionChecker.hasPermission(
				0, LayoutPrototype.class.getName(), layoutPrototypeId,
				actionId)) {

			return true;
		}

		return false;
	}

}