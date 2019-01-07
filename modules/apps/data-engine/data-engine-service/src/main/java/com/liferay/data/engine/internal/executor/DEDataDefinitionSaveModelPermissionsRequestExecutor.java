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
import com.liferay.portal.kernel.util.ArrayUtil;

import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Leonardo Barros
 */
@Component(
	immediate = true,
	service = DEDataDefinitionSaveModelPermissionsRequestExecutor.class
)
public class DEDataDefinitionSaveModelPermissionsRequestExecutor {

	public DEDataDefinitionSaveModelPermissionsResponse execute(
			DEDataDefinitionSaveModelPermissionsRequest
				deDataDefinitionSaveModelPermissionsRequest)
		throws Exception {

		long userId = deDataDefinitionSaveModelPermissionsRequest.getUserId();

		long groupId = deDataDefinitionSaveModelPermissionsRequest.getGroupId();

		List<String> guestPermissions = new ArrayList<>();
		List<String> groupPermissions = new ArrayList<>();

		if (deDataDefinitionSaveModelPermissionsRequest.isDelete()) {
			if ((userId == 0) && (groupId == 0)) {
				guestPermissions.add(ActionKeys.DELETE);
			}
			else if (groupId != 0) {
				groupPermissions.add(ActionKeys.DELETE);
			}
		}

		if (deDataDefinitionSaveModelPermissionsRequest.isUpdate()) {
			if ((userId == 0) && (groupId == 0)) {
				guestPermissions.add(ActionKeys.UPDATE);
			}
			else if (groupId != 0) {
				groupPermissions.add(ActionKeys.UPDATE);
			}
		}

		if (deDataDefinitionSaveModelPermissionsRequest.isView()) {
			if ((userId == 0) && (groupId == 0)) {
				guestPermissions.add(ActionKeys.VIEW);
			}
			else if (groupId != 0) {
				groupPermissions.add(ActionKeys.VIEW);
			}
		}

		long deDataDefinitionId =
			deDataDefinitionSaveModelPermissionsRequest.getDEDataDefinitionId();

		resourcePermissionLocalService.addModelResourcePermissions(
			deDataDefinitionSaveModelPermissionsRequest.getCompanyId(), groupId,
			userId, DEDataDefinitionConstants.MODEL_RESOURCE_NAME,
			String.valueOf(deDataDefinitionId),
			ArrayUtil.toStringArray(groupPermissions),
			ArrayUtil.toStringArray(guestPermissions));

		return DEDataDefinitionSaveModelPermissionsResponse.Builder.of(
			deDataDefinitionId);
	}

	@Reference
	protected ResourcePermissionLocalService resourcePermissionLocalService;

}