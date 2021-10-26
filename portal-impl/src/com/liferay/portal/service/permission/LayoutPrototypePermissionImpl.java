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

package com.liferay.portal.service.permission;

import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerList;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerListFactory;
import com.liferay.portal.kernel.model.LayoutPrototype;
import com.liferay.portal.kernel.module.util.SystemBundleUtil;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.permission.LayoutPrototypePermission;

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
			throw new PrincipalException.MustHavePermission(
				permissionChecker, LayoutPrototype.class.getName(),
				layoutPrototypeId, actionId);
		}
	}

	@Override
	public boolean contains(
		PermissionChecker permissionChecker, long layoutPrototypeId,
		String actionId) {

		if (permissionChecker.hasPermission(
				null, LayoutPrototype.class.getName(), layoutPrototypeId,
				actionId)) {

			return true;
		}

		for (LayoutPrototypePermission layoutPrototypePermission :
				_layoutPrototypePermissions) {

			if (layoutPrototypePermission.contains(
					permissionChecker, layoutPrototypeId, actionId)) {

				return true;
			}
		}

		return false;
	}

	private final ServiceTrackerList<LayoutPrototypePermission>
		_layoutPrototypePermissions = ServiceTrackerListFactory.open(
			SystemBundleUtil.getBundleContext(),
			LayoutPrototypePermission.class, "(extended=true)");

}