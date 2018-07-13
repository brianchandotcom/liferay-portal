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

import com.liferay.dynamic.data.mapping.constants.DDMActionKeys;
import com.liferay.dynamic.data.mapping.form.web.internal.security.permission.resource.DDMFormInstancePermission;
import com.liferay.dynamic.data.mapping.form.web.internal.security.permission.resource.DDMFormPermission;
import com.liferay.dynamic.data.mapping.model.DDMFormInstance;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;

/**
 * @author Rafael Praxedes
 */
public class FormInstancePermissionCheckerHelper {

	public FormInstancePermissionCheckerHelper(
		DDMFormAdminRequestHelper formAdminRequestHelper) {

		_formAdminRequestHelper = formAdminRequestHelper;
	}

	public boolean isShowAddButton() {
		return DDMFormPermission.contains(
			_formAdminRequestHelper.getPermissionChecker(),
			_formAdminRequestHelper.getScopeGroupId(),
			DDMActionKeys.ADD_FORM_INSTANCE);
	}

	public boolean isShowCopyButton() {
		return isShowAddButton();
	}

	public boolean isShowCopyURLIcon(DDMFormInstance formInstance)
		throws PortalException {

		return DDMFormInstancePermission.contains(
			_formAdminRequestHelper.getPermissionChecker(), formInstance,
			ActionKeys.VIEW);
	}

	public boolean isShowDeleteIcon(DDMFormInstance formInstance)
		throws PortalException {

		return DDMFormInstancePermission.contains(
			_formAdminRequestHelper.getPermissionChecker(), formInstance,
			ActionKeys.DELETE);
	}

	public boolean isShowEditIcon(DDMFormInstance formInstance)
		throws PortalException {

		return DDMFormInstancePermission.contains(
			_formAdminRequestHelper.getPermissionChecker(), formInstance,
			ActionKeys.UPDATE);
	}

	public boolean isShowExportIcon(DDMFormInstance formInstance)
		throws PortalException {

		return DDMFormInstancePermission.contains(
			_formAdminRequestHelper.getPermissionChecker(), formInstance,
			ActionKeys.VIEW);
	}

	public boolean isShowPermissionsIcon(DDMFormInstance formInstance)
		throws PortalException {

		return DDMFormInstancePermission.contains(
			_formAdminRequestHelper.getPermissionChecker(), formInstance,
			ActionKeys.PERMISSIONS);
	}

	public boolean isShowViewEntriesIcon(DDMFormInstance formInstance)
		throws PortalException {

		return DDMFormInstancePermission.contains(
			_formAdminRequestHelper.getPermissionChecker(), formInstance,
			ActionKeys.VIEW);
	}

	private final DDMFormAdminRequestHelper _formAdminRequestHelper;

}