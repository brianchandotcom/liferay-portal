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
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecord;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceRecordLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;

/**
 * @author Leonardo Barros
 * @deprecated As of 3.0.0, replaced by {@link DDMFormInstancePermission}
 */
@Deprecated
public class DDMFormInstanceRecordPermission {

	public static void check(
			PermissionChecker permissionChecker,
			DDMFormInstanceRecord ddmFormInstanceRecord, String actionId)
		throws PortalException {

		if (!contains(permissionChecker, ddmFormInstanceRecord, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, DDMFormInstanceRecord.class.getName(),
				ddmFormInstanceRecord.getFormInstanceRecordId(), actionId);
		}
	}

	public static void check(
			PermissionChecker permissionChecker, long ddmFormInstanceRecord,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, ddmFormInstanceRecord, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, DDMFormInstanceRecord.class.getName(),
				ddmFormInstanceRecord, actionId);
		}
	}

	public static boolean contains(
			PermissionChecker permissionChecker,
			DDMFormInstanceRecord ddmFormInstanceRecord, String actionId)
		throws PortalException {

		DDMFormInstance ddmFormInstance =
			ddmFormInstanceRecord.getFormInstance();

		return DDMFormInstancePermission.contains(
			permissionChecker, ddmFormInstance, actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long formInstanceRecordId,
			String actionId)
		throws PortalException {

		DDMFormInstanceRecord ddmFormInstanceRecord =
			DDMFormInstanceRecordLocalServiceUtil.getFormInstanceRecord(
				formInstanceRecordId);

		return contains(permissionChecker, ddmFormInstanceRecord, actionId);
	}

}