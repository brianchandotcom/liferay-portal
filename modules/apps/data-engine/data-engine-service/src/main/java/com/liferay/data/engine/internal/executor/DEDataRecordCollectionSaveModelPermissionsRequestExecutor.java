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

		long userId =
			deDataRecordCollectionSaveModelPermissionsRequest.getUserId();

		long groupId =
			deDataRecordCollectionSaveModelPermissionsRequest.getGroupId();

		List<String> guestPermissions = new ArrayList<>();
		List<String> groupPermissions = new ArrayList<>();

		if (deDataRecordCollectionSaveModelPermissionsRequest.isDelete()) {
			if ((userId == 0) && (groupId == 0)) {
				guestPermissions.add(ActionKeys.DELETE);
			}
			else if (groupId != 0) {
				groupPermissions.add(ActionKeys.DELETE);
			}
		}

		if (deDataRecordCollectionSaveModelPermissionsRequest.isUpdate()) {
			if ((userId == 0) && (groupId == 0)) {
				guestPermissions.add(ActionKeys.UPDATE);
			}
			else if (groupId != 0) {
				groupPermissions.add(ActionKeys.UPDATE);
			}
		}

		if (deDataRecordCollectionSaveModelPermissionsRequest.isView()) {
			if ((userId == 0) && (groupId == 0)) {
				guestPermissions.add(ActionKeys.VIEW);
			}
			else if (groupId != 0) {
				groupPermissions.add(ActionKeys.VIEW);
			}
		}

		long deDataRecordCollectionId =
			deDataRecordCollectionSaveModelPermissionsRequest.
				getDEDataRecordCollectionId();

		_resourcePermissionLocalService.addModelResourcePermissions(
			deDataRecordCollectionSaveModelPermissionsRequest.getCompanyId(),
			groupId, userId,
			DEDataRecordCollectionConstants.MODEL_RESOURCE_NAME,
			String.valueOf(deDataRecordCollectionId),
			ArrayUtil.toStringArray(groupPermissions),
			ArrayUtil.toStringArray(guestPermissions));

		return DEDataRecordCollectionSaveModelPermissionsResponse.Builder.of(
			deDataRecordCollectionId);
	}

	private final ResourcePermissionLocalService
		_resourcePermissionLocalService;

}