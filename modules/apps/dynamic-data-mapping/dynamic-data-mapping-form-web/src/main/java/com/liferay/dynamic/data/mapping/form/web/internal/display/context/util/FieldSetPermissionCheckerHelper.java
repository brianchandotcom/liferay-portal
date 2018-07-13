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

package com.liferay.dynamic.data.mapping.form.web.internal.display.context.util;

import com.liferay.dynamic.data.mapping.form.web.internal.security.permission.resource.DDMFormPermission;
import com.liferay.dynamic.data.mapping.form.web.internal.security.permission.resource.DDMStructurePermission;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;

/**
 * @author Rafael Praxedes
 */
public class FieldSetPermissionCheckerHelper {

	public FieldSetPermissionCheckerHelper(
		DDMFormAdminRequestHelper formAdminRequestHelper) {

		_formAdminRequestHelper = formAdminRequestHelper;
	}

	public boolean isShowAddButton() {
		return DDMFormPermission.contains(
			_formAdminRequestHelper.getPermissionChecker(),
			_formAdminRequestHelper.getScopeGroupId(), "ADD_STRUCTURE");
	}

	public boolean isShowDeleteIcon(DDMStructure ddmStructure)
		throws PortalException {

		return DDMStructurePermission.contains(
			_formAdminRequestHelper.getPermissionChecker(), ddmStructure,
			ActionKeys.DELETE);
	}

	public boolean isShowEditIcon(DDMStructure ddmStructure)
		throws PortalException {

		return DDMStructurePermission.contains(
			_formAdminRequestHelper.getPermissionChecker(), ddmStructure,
			ActionKeys.UPDATE);
	}

	public boolean isShowPermissionsIcon(DDMStructure ddmStructure)
		throws PortalException {

		return DDMStructurePermission.contains(
			_formAdminRequestHelper.getPermissionChecker(), ddmStructure,
			ActionKeys.PERMISSIONS);
	}

	private final DDMFormAdminRequestHelper _formAdminRequestHelper;

}