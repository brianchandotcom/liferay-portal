/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.notification.internal.type.users.provider;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactory;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionRegistryUtil;

import org.osgi.service.component.annotations.Reference;

/**
 * @author Rafael Praxedes
 */
public abstract class BaseUsersProvider implements UsersProvider {

	protected boolean hasViewPermission(
		String className, long classPK, User user) {

		ModelResourcePermission<?> modelResourcePermission =
			ModelResourcePermissionRegistryUtil.getModelResourcePermission(
				className);

		if (modelResourcePermission != null) {
			try {
				return modelResourcePermission.contains(
					permissionCheckerFactory.create(user), classPK,
					ActionKeys.VIEW);
			}
			catch (PortalException portalException) {
				throw new RuntimeException(portalException);
			}
		}

		return false;
	}

	@Reference
	protected PermissionCheckerFactory permissionCheckerFactory;

}