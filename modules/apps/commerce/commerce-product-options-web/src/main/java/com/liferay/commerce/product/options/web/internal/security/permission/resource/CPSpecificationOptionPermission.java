/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.options.web.internal.security.permission.resource;

import com.liferay.commerce.product.model.CPSpecificationOption;
import com.liferay.osgi.util.service.Snapshot;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;

/**
 * @author Marco Leo
 */
public class CPSpecificationOptionPermission {

	public static boolean contains(
			PermissionChecker permissionChecker,
			CPSpecificationOption cpSpecificationOption, String actionId)
		throws PortalException {

		ModelResourcePermission<CPSpecificationOption> modelResourcePermission =
			_cpSpecificationOptionModelResourcePermissionSnapshot.get();

		return modelResourcePermission.contains(
			permissionChecker, cpSpecificationOption, actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long cpSpecificationOptionId,
			String actionId)
		throws PortalException {

		ModelResourcePermission<CPSpecificationOption> modelResourcePermission =
			_cpSpecificationOptionModelResourcePermissionSnapshot.get();

		return modelResourcePermission.contains(
			permissionChecker, cpSpecificationOptionId, actionId);
	}

	private static final Snapshot
		<ModelResourcePermission<CPSpecificationOption>>
			_cpSpecificationOptionModelResourcePermissionSnapshot =
				new Snapshot<>(
					CPSpecificationOptionPermission.class,
					Snapshot.cast(ModelResourcePermission.class),
					"(model.class.name=com.liferay.commerce.product.model." +
						"CPSpecificationOption)");

}