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

package com.liferay.data.engine.internal.executor;

import com.liferay.data.engine.constants.DEDataLayoutConstants;
import com.liferay.data.engine.service.DEDataLayoutSaveModelPermissionsRequest;
import com.liferay.data.engine.service.DEDataLayoutSaveModelPermissionsResponse;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.permission.ModelPermissions;
import com.liferay.portal.kernel.util.ArrayUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Marcela Cunha
 */
public class DEDataLayoutSaveModelPermissionsRequestExecutor {

	public DEDataLayoutSaveModelPermissionsRequestExecutor(
		ResourcePermissionLocalService resourcePermissionLocalService) {

		_resourcePermissionLocalService = resourcePermissionLocalService;
	}

	public DEDataLayoutSaveModelPermissionsResponse execute(
			DEDataLayoutSaveModelPermissionsRequest
				deDataLayoutSaveModelPermissionsRequest)
		throws Exception {

		long scopedUserId =
			deDataLayoutSaveModelPermissionsRequest.getScopedUserId();

		long groupId = deDataLayoutSaveModelPermissionsRequest.getGroupId();

		List<String> actionIds = new ArrayList<>();

		if (deDataLayoutSaveModelPermissionsRequest.isDelete()) {
			actionIds.add(ActionKeys.DELETE);
		}

		if (deDataLayoutSaveModelPermissionsRequest.isUpdate()) {
			actionIds.add(ActionKeys.UPDATE);
		}

		if (deDataLayoutSaveModelPermissionsRequest.isView()) {
			actionIds.add(ActionKeys.VIEW);
		}

		long deDataLayoutId =
			deDataLayoutSaveModelPermissionsRequest.getDEDataLayoutId();

		if (actionIds.isEmpty()) {
			return DEDataLayoutSaveModelPermissionsResponse.Builder.of(
				deDataLayoutId);
		}

		ModelPermissions modelPermissions = new ModelPermissions();

		List<String> roleNames =
			deDataLayoutSaveModelPermissionsRequest.getRoleNames();

		for (String roleName : roleNames) {
			modelPermissions.addRolePermissions(
				roleName, ArrayUtil.toStringArray(actionIds));
		}

		_resourcePermissionLocalService.addModelResourcePermissions(
			deDataLayoutSaveModelPermissionsRequest.getCompanyId(), groupId,
			scopedUserId, DEDataLayoutConstants.MODEL_RESOURCE_NAME,
			String.valueOf(deDataLayoutId), modelPermissions);

		return DEDataLayoutSaveModelPermissionsResponse.Builder.of(
			deDataLayoutId);
	}

	private final ResourcePermissionLocalService
		_resourcePermissionLocalService;

}