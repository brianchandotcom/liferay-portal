/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.form.taglib.internal.security.permission;

import com.liferay.dynamic.data.mapping.model.DDMFormInstance;
import com.liferay.osgi.util.service.Snapshot;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;

/**
 * @author Pedro Queiroz
 */
public class DDMFormInstancePermission {

	public static boolean contains(
			PermissionChecker permissionChecker,
			DDMFormInstance ddmFormInstance, String actionId)
		throws PortalException {

		ModelResourcePermission<DDMFormInstance> modelResourcePermission =
			_ddmFormInstanceModelResourcePermissionSnapshot.get();

		return modelResourcePermission.contains(
			permissionChecker, ddmFormInstance, actionId);
	}

	private static final Snapshot<ModelResourcePermission<DDMFormInstance>>
		_ddmFormInstanceModelResourcePermissionSnapshot = new Snapshot<>(
			DDMFormInstancePermission.class,
			Snapshot.cast(ModelResourcePermission.class),
			"(model.class.name=com.liferay.dynamic.data.mapping.model." +
				"DDMFormInstance)");

}