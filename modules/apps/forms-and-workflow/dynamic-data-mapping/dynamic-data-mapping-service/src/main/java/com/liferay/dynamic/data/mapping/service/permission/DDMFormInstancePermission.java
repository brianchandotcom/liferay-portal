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
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Leonardo Barros
 */
@Component(immediate = true)
public class DDMFormInstancePermission {

	public static void check(
			PermissionChecker permissionChecker,
			DDMFormInstance ddmFormInstance, String actionId)
		throws PortalException {

		if (!contains(permissionChecker, ddmFormInstance, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, DDMFormInstance.class.getName(),
				ddmFormInstance.getFormInstanceId(), actionId);
		}
	}

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
			PermissionChecker permissionChecker,
			DDMFormInstance ddmFormInstance, String actionId)
		throws PortalException {

		return _ddmFormInstanceModelResourcePermission.contains(
			permissionChecker, ddmFormInstance, actionId);
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

	@Reference(
		target = "(model.class.name=com.liferay.dynamic.data.mapping.model.DDMFormInstance)",
		unbind = "-"
	)
	protected void setModelResourcePermission(
		ModelResourcePermission<DDMFormInstance> modelResourcePermission) {

		_ddmFormInstanceModelResourcePermission = modelResourcePermission;
	}

	private static ModelResourcePermission<DDMFormInstance>
		_ddmFormInstanceModelResourcePermission;

}