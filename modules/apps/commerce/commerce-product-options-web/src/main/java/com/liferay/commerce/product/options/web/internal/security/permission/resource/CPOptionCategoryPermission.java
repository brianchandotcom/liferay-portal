/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.options.web.internal.security.permission.resource;

import com.liferay.commerce.product.model.CPOptionCategory;
import com.liferay.osgi.util.service.Snapshot;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;

/**
 * @author Marco Leo
 */
public class CPOptionCategoryPermission {

	public static boolean contains(
			PermissionChecker permissionChecker,
			CPOptionCategory cpOptionCategory, String actionId)
		throws PortalException {

		ModelResourcePermission<CPOptionCategory> modelResourcePermission =
			_cpOptionCategoryModelResourcePermissionSnapshot.get();

		return modelResourcePermission.contains(
			permissionChecker, cpOptionCategory, actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long cpOptionCategoryId,
			String actionId)
		throws PortalException {

		ModelResourcePermission<CPOptionCategory> modelResourcePermission =
			_cpOptionCategoryModelResourcePermissionSnapshot.get();

		return modelResourcePermission.contains(
			permissionChecker, cpOptionCategoryId, actionId);
	}

	private static final Snapshot<ModelResourcePermission<CPOptionCategory>>
		_cpOptionCategoryModelResourcePermissionSnapshot = new Snapshot<>(
			CPOptionCategoryPermission.class,
			Snapshot.cast(ModelResourcePermission.class),
			"(model.class.name=com.liferay.commerce.product.model." +
				"CPOptionCategory)");

}