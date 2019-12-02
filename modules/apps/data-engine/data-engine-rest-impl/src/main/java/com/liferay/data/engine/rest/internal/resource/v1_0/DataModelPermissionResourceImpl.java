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

package com.liferay.data.engine.rest.internal.resource.v1_0;

import com.liferay.data.engine.rest.dto.v1_0.DataModelPermission;
import com.liferay.data.engine.rest.internal.constants.DataRecordCollectionConstants;
import com.liferay.data.engine.rest.internal.resource.v1_0.util.DataEnginePermissionUtil;
import com.liferay.data.engine.rest.resource.v1_0.DataModelPermissionResource;
import com.liferay.dynamic.data.lists.model.DDLRecordSet;
import com.liferay.dynamic.data.lists.service.DDLRecordSetLocalService;
import com.liferay.portal.kernel.model.ResourceAction;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.ResourcePermission;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ResourceActionLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.permission.ModelPermissions;
import com.liferay.portal.kernel.service.permission.ModelPermissionsFactory;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Jeyvison Nascimento
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/data-model-permission.properties",
	scope = ServiceScope.PROTOTYPE, service = DataModelPermissionResource.class
)
public class DataModelPermissionResourceImpl
	extends BaseDataModelPermissionResourceImpl {

	@Override
	public Page<DataModelPermission>
			getDataRecordCollectionDataModelPermissionsPage(
				Long dataRecordCollectionId, String roleNames,
				Pagination pagination)
		throws Exception {

		DDLRecordSet ddlRecordSet = _ddlRecordSetLocalService.getRecordSet(
			dataRecordCollectionId);

		DataEnginePermissionUtil.checkPermission(
			ActionKeys.PERMISSIONS, _groupLocalService,
			ddlRecordSet.getGroupId());

		List<Role> roles = DataEnginePermissionUtil.getRoles(
			contextCompany, _roleLocalService, StringUtil.split(roleNames));

		List<DataModelPermission> dataModelPermissions = new ArrayList<>();

		Set<String> actionsIdsSet = new HashSet<>();

		List<ResourceAction> resourceActions =
			_resourceActionLocalService.getResourceActions(
				DataRecordCollectionConstants.RESOURCE_NAME);

		for (Role role : roles) {
			ResourcePermission resourcePermission =
				_resourcePermissionLocalService.fetchResourcePermission(
					ddlRecordSet.getCompanyId(),
					DataRecordCollectionConstants.RESOURCE_NAME,
					ResourceConstants.SCOPE_INDIVIDUAL,
					String.valueOf(dataRecordCollectionId), role.getRoleId());

			if (resourcePermission == null) {
				continue;
			}

			long actionIds = resourcePermission.getActionIds();

			for (ResourceAction resourceAction : resourceActions) {
				long bitwiseValue = resourceAction.getBitwiseValue();

				if ((actionIds & bitwiseValue) == bitwiseValue) {
					actionsIdsSet.add(resourceAction.getActionId());
				}
			}

			DataModelPermission dataModelPermission = new DataModelPermission();

			dataModelPermission.setRoleName(role.getName());
			dataModelPermission.setActionIds(
				actionsIdsSet.toArray(new String[0]));

			dataModelPermissions.add(dataModelPermission);
		}

		return Page.of(
			dataModelPermissions, pagination, dataModelPermissions.size());
	}

	@Override
	public void putDataRecordCollectionDataModelPermission(
			Long dataRecordCollectionId,
			DataModelPermission[] dataModelPermissions)
		throws Exception {

		DDLRecordSet ddlRecordSet = _ddlRecordSetLocalService.getRecordSet(
			dataRecordCollectionId);

		DataEnginePermissionUtil.checkPermission(
			ActionKeys.PERMISSIONS, _groupLocalService,
			ddlRecordSet.getGroupId());

		ModelPermissions modelPermissions =
			ModelPermissionsFactory.createWithDefaultPermissions(
				DataRecordCollectionConstants.RESOURCE_NAME);

		for (DataModelPermission dataModelPermission : dataModelPermissions) {
			modelPermissions.addRolePermissions(
				dataModelPermission.getRoleName(),
				dataModelPermission.getActionIds());
		}

		_resourcePermissionLocalService.updateResourcePermissions(
			ddlRecordSet.getCompanyId(), ddlRecordSet.getGroupId(),
			DataRecordCollectionConstants.RESOURCE_NAME,
			String.valueOf(dataRecordCollectionId), modelPermissions);
	}

	@Reference
	private DDLRecordSetLocalService _ddlRecordSetLocalService;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private ResourceActionLocalService _resourceActionLocalService;

	@Reference
	private ResourcePermissionLocalService _resourcePermissionLocalService;

	@Reference
	private RoleLocalService _roleLocalService;

}