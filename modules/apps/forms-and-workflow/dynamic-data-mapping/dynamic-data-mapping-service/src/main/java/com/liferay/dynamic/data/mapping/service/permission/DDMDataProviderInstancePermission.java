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

package com.liferay.dynamic.data.mapping.service.permission;

import com.liferay.dynamic.data.mapping.constants.DDMPortletKeys;
import com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance;
import com.liferay.dynamic.data.mapping.service.DDMDataProviderInstanceLocalServiceUtil;
import com.liferay.exportimport.kernel.staging.permission.StagingPermissionUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;

/**
 * @author Leonardo Barros
 */
public class DDMDataProviderInstancePermission {

	public static void check(
			PermissionChecker permissionChecker, long dataProviderInstanceId,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, dataProviderInstanceId, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, DDMDataProviderInstance.class.getName(),
				dataProviderInstanceId, actionId);
		}
	}

	public static boolean contains(
		PermissionChecker permissionChecker,
		DDMDataProviderInstance dataProviderInstance, String actionId) {

		Boolean hasPermission = StagingPermissionUtil.hasPermission(
			permissionChecker, dataProviderInstance.getGroupId(),
			DDMDataProviderInstance.class.getName(),
			dataProviderInstance.getDataProviderInstanceId(),
			DDMPortletKeys.DYNAMIC_DATA_MAPPING, actionId);

		if (hasPermission != null) {
			return hasPermission.booleanValue();
		}

		if (permissionChecker.hasOwnerPermission(
				dataProviderInstance.getCompanyId(),
				DDMDataProviderInstance.class.getName(),
				dataProviderInstance.getDataProviderInstanceId(),
				dataProviderInstance.getUserId(), actionId)) {

			return true;
		}

		return permissionChecker.hasPermission(
			dataProviderInstance.getGroupId(),
			DDMDataProviderInstance.class.getName(),
			dataProviderInstance.getDataProviderInstanceId(), actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long dataProviderInstanceId,
			String actionId)
		throws PortalException {

		DDMDataProviderInstance dataProviderInstance =
			DDMDataProviderInstanceLocalServiceUtil.getDDMDataProviderInstance(
				dataProviderInstanceId);

		return contains(permissionChecker, dataProviderInstance, actionId);
	}

}