/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.audience.internal.security.permission.resource;

import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermissionLogic;

/**
 * @author Eudaldo Alonso
 */
public class AudiencePortletResourcePermissionLogic
	implements PortletResourcePermissionLogic {

	@Override
	public Boolean contains(
		PermissionChecker permissionChecker, String name, Group group,
		String actionId) {

		return permissionChecker.hasPermission(group, name, 0, actionId);
	}

}