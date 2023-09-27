/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.form.web.internal.display.context.helper;

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