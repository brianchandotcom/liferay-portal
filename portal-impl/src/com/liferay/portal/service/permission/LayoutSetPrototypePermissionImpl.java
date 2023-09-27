/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.service.permission;

import com.liferay.portal.kernel.model.LayoutSetPrototype;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.permission.LayoutSetPrototypePermission;

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
			throw new PrincipalException.MustHavePermission(
				permissionChecker, LayoutSetPrototype.class.getName(),
				layoutSetPrototypeId, actionId);
		}
	}

	@Override
	public boolean contains(
		PermissionChecker permissionChecker, long layoutSetPrototypeId,
		String actionId) {

		if (permissionChecker.hasPermission(
				null, LayoutSetPrototype.class.getName(), layoutSetPrototypeId,
				actionId)) {

			return true;
		}

		return false;
	}

}