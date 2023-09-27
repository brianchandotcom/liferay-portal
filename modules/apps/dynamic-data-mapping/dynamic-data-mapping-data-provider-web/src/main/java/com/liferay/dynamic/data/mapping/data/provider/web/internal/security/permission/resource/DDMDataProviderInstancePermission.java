/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.data.provider.web.internal.security.permission.resource;

import com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance;
import com.liferay.osgi.util.service.Snapshot;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;

/**
 * @author Rafael Praxedes
 */
public class DDMDataProviderInstancePermission {

	public static boolean contains(
			PermissionChecker permissionChecker,
			DDMDataProviderInstance ddmDataProviderInstance, String actionId)
		throws PortalException {

		ModelResourcePermission<DDMDataProviderInstance>
			modelResourcePermission =
				_ddmDataProviderInstanceModelResourcePermissionSnapshot.get();

		return modelResourcePermission.contains(
			permissionChecker, ddmDataProviderInstance, actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long dataProviderInstanceId,
			String actionId)
		throws PortalException {

		ModelResourcePermission<DDMDataProviderInstance>
			modelResourcePermission =
				_ddmDataProviderInstanceModelResourcePermissionSnapshot.get();

		return modelResourcePermission.contains(
			permissionChecker, dataProviderInstanceId, actionId);
	}

	private static final Snapshot
		<ModelResourcePermission<DDMDataProviderInstance>>
			_ddmDataProviderInstanceModelResourcePermissionSnapshot =
				new Snapshot<>(
					DDMDataProviderInstancePermission.class,
					Snapshot.cast(ModelResourcePermission.class),
					"(model.class.name=com.liferay.dynamic.data.mapping." +
						"model.DDMDataProviderInstance)");

}