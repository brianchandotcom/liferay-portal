/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.options.web.internal.security.permission.resource;

import com.liferay.commerce.product.model.CPOption;
import com.liferay.osgi.util.service.Snapshot;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;

/**
 * @author Marco Leo
 */
public class CPOptionPermission {

	public static boolean contains(
			PermissionChecker permissionChecker, CPOption cpOption,
			String actionId)
		throws PortalException {

		ModelResourcePermission<CPOption> modelResourcePermission =
			_cpOptionModelResourcePermissionSnapshot.get();

		return modelResourcePermission.contains(
			permissionChecker, cpOption, actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long cpOptionId,
			String actionId)
		throws PortalException {

		ModelResourcePermission<CPOption> modelResourcePermission =
			_cpOptionModelResourcePermissionSnapshot.get();

		return modelResourcePermission.contains(
			permissionChecker, cpOptionId, actionId);
	}

	private static final Snapshot<ModelResourcePermission<CPOption>>
		_cpOptionModelResourcePermissionSnapshot = new Snapshot<>(
			CPOptionPermission.class,
			Snapshot.cast(ModelResourcePermission.class),
			"(model.class.name=com.liferay.commerce.product.model.CPOption)");

}