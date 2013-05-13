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

import com.liferay.portal.model.LayoutSetPrototype;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.PermissionChecker;

/**
 * @author Brian Wing Shun Chan
 */
public class LayoutSetPrototypePermissionImpl
	implements LayoutSetPrototypePermission {

	@Override
	public void check(
			PermissionChecker permissionChecker, long layoutSetPrototypeId,
			String actionId)
		throws PrincipalException {

		if (!contains(permissionChecker, layoutSetPrototypeId, actionId)) {
			throw new PrincipalException();
		}
	}

	@Override
	public boolean contains(
		PermissionChecker permissionChecker, long layoutSetPrototypeId,
		String actionId) {

		if (permissionChecker.hasPermission(
				0, LayoutSetPrototype.class.getName(), layoutSetPrototypeId,
				actionId)) {

			return true;
		}

		return false;
	}

}