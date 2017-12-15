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

import com.liferay.dynamic.data.mapping.model.DDMFormInstance;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceLocalServiceUtil;
import com.liferay.exportimport.kernel.staging.permission.StagingPermissionUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;

/**
 * @author Leonardo Barros
 */
public class DDMFormInstancePermission {

	public static void check(
			PermissionChecker permissionChecker, long ddmFormInstanceId,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, ddmFormInstanceId, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, DDMFormInstance.class.getName(),
				ddmFormInstanceId, actionId);
		}
	}

	public static boolean contains(
		PermissionChecker permissionChecker, DDMFormInstance ddmFormInstance,
		String actionId) {

		String portletId = PortletProviderUtil.getPortletId(
			DDMFormInstance.class.getName(), PortletProvider.Action.EDIT);

		Boolean hasPermission = StagingPermissionUtil.hasPermission(
			permissionChecker, ddmFormInstance.getGroupId(),
			DDMFormInstance.class.getName(),
			ddmFormInstance.getFormInstanceId(), portletId, actionId);

		if (hasPermission != null) {
			return hasPermission.booleanValue();
		}

		if (permissionChecker.hasOwnerPermission(
				ddmFormInstance.getCompanyId(), DDMFormInstance.class.getName(),
				ddmFormInstance.getFormInstanceId(),
				ddmFormInstance.getUserId(), actionId)) {

			return true;
		}

		return permissionChecker.hasPermission(
			ddmFormInstance.getGroupId(), DDMFormInstance.class.getName(),
			ddmFormInstance.getFormInstanceId(), actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long ddmFormInstanceId,
			String actionId)
		throws PortalException {

		DDMFormInstance ddmFormInstance =
			DDMFormInstanceLocalServiceUtil.getDDMFormInstance(
				ddmFormInstanceId);

		return contains(permissionChecker, ddmFormInstance, actionId);
	}

}