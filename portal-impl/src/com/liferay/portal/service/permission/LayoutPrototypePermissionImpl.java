/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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