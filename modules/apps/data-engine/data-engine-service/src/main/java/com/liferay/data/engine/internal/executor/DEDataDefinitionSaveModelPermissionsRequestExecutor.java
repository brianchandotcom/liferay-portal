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

import com.liferay.data.engine.constants.DEDataDefinitionConstants;
import com.liferay.data.engine.service.DEDataDefinitionSaveModelPermissionsRequest;
import com.liferay.data.engine.service.DEDataDefinitionSaveModelPermissionsResponse;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.permission.ModelPermissions;
import com.liferay.portal.kernel.util.ArrayUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Leonardo Barros
 */
public class DEDataDefinitionSaveModelPermissionsRequestExecutor {

	public DEDataDefinitionSaveModelPermissionsRequestExecutor(
		ResourcePermissionLocalService resourcePermissionLocalService) {

		_resourcePermissionLocalService = resourcePermissionLocalService;
	}

	public DEDataDefinitionSaveModelPermissionsResponse execute(
			DEDataDefinitionSaveModelPermissionsRequest
				deDataDefinitionSaveModelPermissionsRequest)
		throws Exception {

		long scopedUserId =
			deDataDefinitionSaveModelPermissionsRequest.getScopedUserId();

		long groupId = deDataDefinitionSaveModelPermissionsRequest.getGroupId();

		List<String> actionIds = new ArrayList<>();

		if (deDataDefinitionSaveModelPermissionsRequest.isDelete()) {
			actionIds.add(ActionKeys.DELETE);
		}

		if (deDataDefinitionSaveModelPermissionsRequest.isUpdate()) {
			actionIds.add(ActionKeys.UPDATE);
		}

		if (deDataDefinitionSaveModelPermissionsRequest.isView()) {
			actionIds.add(ActionKeys.VIEW);
		}

		long deDataDefinitionId =
			deDataDefinitionSaveModelPermissionsRequest.getDEDataDefinitionId();

		if (actionIds.isEmpty()) {
			return DEDataDefinitionSaveModelPermissionsResponse.Builder.of(
				deDataDefinitionId);
		}

		ModelPermissions modelPermissions = new ModelPermissions();

		List<String> roleNames =
			deDataDefinitionSaveModelPermissionsRequest.getRoleNames();

		for (String roleName : roleNames) {
			modelPermissions.addRolePermissions(
				roleName, ArrayUtil.toStringArray(actionIds));
		}

		_resourcePermissionLocalService.addModelResourcePermissions(
			deDataDefinitionSaveModelPermissionsRequest.getCompanyId(), groupId,
			scopedUserId, DEDataDefinitionConstants.MODEL_RESOURCE_NAME,
			String.valueOf(deDataDefinitionId), modelPermissions);

		return DEDataDefinitionSaveModelPermissionsResponse.Builder.of(
			deDataDefinitionId);
	}

	private final ResourcePermissionLocalService
		_resourcePermissionLocalService;

}