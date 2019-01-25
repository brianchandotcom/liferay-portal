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

import com.liferay.data.engine.constants.DEDataRecordCollectionConstants;
import com.liferay.data.engine.service.DEDataRecordCollectionSaveModelPermissionsRequest;
import com.liferay.data.engine.service.DEDataRecordCollectionSaveModelPermissionsResponse;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.permission.ModelPermissions;
import com.liferay.portal.kernel.util.ArrayUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Leonardo Barros
 */
public class DEDataRecordCollectionSaveModelPermissionsRequestExecutor {

	public DEDataRecordCollectionSaveModelPermissionsRequestExecutor(
		ResourcePermissionLocalService resourcePermissionLocalService) {

		_resourcePermissionLocalService = resourcePermissionLocalService;
	}

	public DEDataRecordCollectionSaveModelPermissionsResponse execute(
			DEDataRecordCollectionSaveModelPermissionsRequest
				deDataRecordCollectionSaveModelPermissionsRequest)
		throws Exception {

		long scopedUserId =
			deDataRecordCollectionSaveModelPermissionsRequest.getScopedUserId();

		long groupId =
			deDataRecordCollectionSaveModelPermissionsRequest.getGroupId();

		List<String> actionIds = new ArrayList<>();

		if (deDataRecordCollectionSaveModelPermissionsRequest.isDelete()) {
			actionIds.add(ActionKeys.DELETE);
		}

		if (deDataRecordCollectionSaveModelPermissionsRequest.isUpdate()) {
			actionIds.add(ActionKeys.UPDATE);
		}

		if (deDataRecordCollectionSaveModelPermissionsRequest.isView()) {
			actionIds.add(ActionKeys.VIEW);
		}

		long deDataRecordCollectionId =
			deDataRecordCollectionSaveModelPermissionsRequest.
				getDEDataRecordCollectionId();

		if (actionIds.isEmpty()) {
			return DEDataRecordCollectionSaveModelPermissionsResponse.Builder.
				of(deDataRecordCollectionId);
		}

		ModelPermissions modelPermissions = new ModelPermissions();

		List<String> roleNames =
			deDataRecordCollectionSaveModelPermissionsRequest.getRoleNames();

		for (String roleName : roleNames) {
			modelPermissions.addRolePermissions(
				roleName, ArrayUtil.toStringArray(actionIds));
		}

		_resourcePermissionLocalService.addModelResourcePermissions(
			deDataRecordCollectionSaveModelPermissionsRequest.getCompanyId(),
			groupId, scopedUserId,
			DEDataRecordCollectionConstants.MODEL_RESOURCE_NAME,
			String.valueOf(deDataRecordCollectionId), modelPermissions);

		return DEDataRecordCollectionSaveModelPermissionsResponse.Builder.of(
			deDataRecordCollectionId);
	}

	private final ResourcePermissionLocalService
		_resourcePermissionLocalService;

}