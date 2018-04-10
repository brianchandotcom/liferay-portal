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

import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecord;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Leonardo Barros
 * @deprecated As of 3.0.0, with no direct replacement
 */
@Component(immediate = true, service = DDMFormInstanceRecordPermission.class)
@Deprecated
public class DDMFormInstanceRecordPermission {

	public static void check(
			PermissionChecker permissionChecker,
			DDMFormInstanceRecord ddmFormInstanceRecord, String actionId)
		throws PortalException {

		_ddmFormInstanceRecordModelResourcePermission.check(
			permissionChecker, ddmFormInstanceRecord, actionId);
	}

	public static void check(
			PermissionChecker permissionChecker, long ddmFormInstanceRecord,
			String actionId)
		throws PortalException {

		_ddmFormInstanceRecordModelResourcePermission.check(
			permissionChecker, ddmFormInstanceRecord, actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker,
			DDMFormInstanceRecord ddmFormInstanceRecord, String actionId)
		throws PortalException {

		return _ddmFormInstanceRecordModelResourcePermission.contains(
			permissionChecker, ddmFormInstanceRecord, actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long formInstanceRecordId,
			String actionId)
		throws PortalException {

		return _ddmFormInstanceRecordModelResourcePermission.contains(
			permissionChecker, formInstanceRecordId, actionId);
	}

	@Reference(
		target = "(model.class.name=com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecord)",
		unbind = "-"
	)
	protected void setModelResourcePermission(
		ModelResourcePermission<DDMFormInstanceRecord>
			modelResourcePermission) {

		_ddmFormInstanceRecordModelResourcePermission = modelResourcePermission;
	}

	private static ModelResourcePermission<DDMFormInstanceRecord>
		_ddmFormInstanceRecordModelResourcePermission;

}