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

package com.liferay.data.engine.rest.internal.resource.v2_0;

import com.liferay.data.engine.rest.dto.v2_0.DataModelPermission;
import com.liferay.data.engine.rest.internal.resource.util.DataEnginePermissionUtil;
import com.liferay.data.engine.rest.resource.v2_0.DataModelPermissionResource;
import com.liferay.dynamic.data.lists.model.DDLRecordSet;
import com.liferay.dynamic.data.lists.service.DDLRecordSetLocalService;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.model.ResourceAction;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.ResourcePermission;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ResourceActionLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.permission.ModelPermissions;
import com.liferay.portal.kernel.service.permission.ModelPermissionsFactory;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.vulcan.pagination.Page;

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
	properties = "OSGI-INF/liferay/rest/v2_0/data-model-permission.properties",
	scope = ServiceScope.PROTOTYPE, service = DataModelPermissionResource.class
)
public class DataModelPermissionResourceImpl
	extends BaseDataModelPermissionResourceImpl {

	public Page getDataDefinitionDataModelPermissionsPage(
			Long dataDefinitionId, String roleNames)
		throws Exception {

		DDMStructure ddmStructure = _ddmStructureLocalService.getDDMStructure(
			dataDefinitionId);

		DataEnginePermissionUtil.checkPermission(
			ActionKeys.PERMISSIONS, _groupLocalService,
			ddmStructure.getGroupId());

		return Page.of(
			transform(
				DataEnginePermissionUtil.getRoles(
					contextCompany, _roleLocalService,
					StringUtil.split(roleNames)),
				role -> _toDataModelPermission(
					ddmStructure.getCompanyId(), dataDefinitionId,
					_resourceActionLocalService.getResourceActions(
						_getResourceName(ddmStructure)),
					_portal.getClassName(ddmStructure.getClassNameId()),
					role)));
	}

	@Override
	public String getDataRecordCollectionDataModelPermissionByCurrentUser(
			Long dataRecordCollectionId)
		throws Exception {

		JSONArray actionIdsJSONArray = JSONFactoryUtil.createJSONArray();

		DDLRecordSet ddlRecordSet = _ddlRecordSetLocalService.getRecordSet(
			dataRecordCollectionId);

		String resourceName = _getResourceName(ddlRecordSet);

		List<ResourceAction> resourceActions =
			_resourceActionLocalService.getResourceActions(resourceName);

		for (ResourceAction resourceAction : resourceActions) {
			PermissionChecker permissionChecker =
				PermissionThreadLocal.getPermissionChecker();

			if (permissionChecker.hasPermission(
					ddlRecordSet.getGroupId(), resourceName,
					dataRecordCollectionId, resourceAction.getActionId())) {

				actionIdsJSONArray.put(resourceAction.getActionId());
			}
		}

		return actionIdsJSONArray.toString();
	}

	@Override
	public Page getDataRecordCollectionDataModelPermissionsPage(
			Long dataRecordCollectionId, String roleNames)
		throws Exception {

		DDLRecordSet ddlRecordSet = _ddlRecordSetLocalService.getRecordSet(
			dataRecordCollectionId);

		DataEnginePermissionUtil.checkPermission(
			ActionKeys.PERMISSIONS, _groupLocalService,
			ddlRecordSet.getGroupId());

		String resourceName = _getResourceName(ddlRecordSet);

		return Page.of(
			transform(
				DataEnginePermissionUtil.getRoles(
					contextCompany, _roleLocalService,
					StringUtil.split(roleNames)),
				role -> _toDataModelPermission(
					ddlRecordSet.getCompanyId(), dataRecordCollectionId,
					_resourceActionLocalService.getResourceActions(
						resourceName),
					resourceName, role)));
	}

	@Override
	public void putDataDefinitionDataModelPermission(
			Long dataDefinitionId, DataModelPermission[] dataModelPermissions)
		throws Exception {

		DDMStructure ddmStructure = _ddmStructureLocalService.getDDMStructure(
			dataDefinitionId);

		DataEnginePermissionUtil.checkPermission(
			ActionKeys.PERMISSIONS, _groupLocalService,
			ddmStructure.getGroupId());

		String resourceName = _getResourceName(ddmStructure);

		_resourcePermissionLocalService.updateResourcePermissions(
			ddmStructure.getCompanyId(), ddmStructure.getGroupId(),
			resourceName, String.valueOf(dataDefinitionId),
			_getModelPermissions(
				ddmStructure.getCompanyId(), dataModelPermissions,
				dataDefinitionId, resourceName));
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

		String resourceName = _getResourceName(ddlRecordSet);

		_resourcePermissionLocalService.updateResourcePermissions(
			ddlRecordSet.getCompanyId(), ddlRecordSet.getGroupId(),
			resourceName, String.valueOf(dataRecordCollectionId),
			_getModelPermissions(
				ddlRecordSet.getCompanyId(), dataModelPermissions,
				dataRecordCollectionId, resourceName));
	}

	private ModelPermissions _getModelPermissions(
			long companyId, DataModelPermission[] dataModelPermissions,
			long primKey, String resourceName)
		throws PortalException {

		ModelPermissions modelPermissions = ModelPermissionsFactory.create(
			new String[0], new String[0], resourceName);

		for (DataModelPermission dataModelPermission : dataModelPermissions) {
			String[] actionIds = dataModelPermission.getActionIds();

			if (actionIds.length == 0) {
				List<ResourceAction> resourceActions =
					_resourceActionLocalService.getResourceActions(
						resourceName);

				Role role = _roleLocalService.getRole(
					companyId, dataModelPermission.getRoleName());

				for (ResourceAction resourceAction : resourceActions) {
					_resourcePermissionLocalService.removeResourcePermission(
						companyId, resourceName,
						ResourceConstants.SCOPE_INDIVIDUAL,
						String.valueOf(primKey), role.getRoleId(),
						resourceAction.getActionId());
				}

				continue;
			}

			modelPermissions.addRolePermissions(
				dataModelPermission.getRoleName(),
				dataModelPermission.getActionIds());
		}

		return modelPermissions;
	}

	private String _getResourceName(DDLRecordSet ddlRecordSet)
		throws PortalException {

		DDMStructure ddmStructure = ddlRecordSet.getDDMStructure();

		return ResourceActionsUtil.getCompositeModelName(
			_portal.getClassName(ddmStructure.getClassNameId()),
			DDLRecordSet.class.getName());
	}

	private String _getResourceName(DDMStructure ddmStructure) {
		return ResourceActionsUtil.getCompositeModelName(
			_portal.getClassName(ddmStructure.getClassNameId()),
			DDMStructure.class.getName());
	}

	private DataModelPermission _toDataModelPermission(
		Long companyId, Long id, List<ResourceAction> resourceActions,
		String resourceName, Role role) {

		ResourcePermission resourcePermission =
			_resourcePermissionLocalService.fetchResourcePermission(
				companyId, resourceName, ResourceConstants.SCOPE_INDIVIDUAL,
				String.valueOf(id), role.getRoleId());

		if (resourcePermission == null) {
			return null;
		}

		Set<String> actionsIdsSet = new HashSet<>();

		long actionIds = resourcePermission.getActionIds();

		for (ResourceAction resourceAction : resourceActions) {
			long bitwiseValue = resourceAction.getBitwiseValue();

			if ((actionIds & bitwiseValue) == bitwiseValue) {
				actionsIdsSet.add(resourceAction.getActionId());
			}
		}

		return new DataModelPermission() {
			{
				actionIds = actionsIdsSet.toArray(new String[0]);
				roleName = role.getName();
			}
		};
	}

	@Reference
	private DDLRecordSetLocalService _ddlRecordSetLocalService;

	@Reference
	private DDMStructureLocalService _ddmStructureLocalService;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private Portal _portal;

	@Reference
	private ResourceActionLocalService _resourceActionLocalService;

	@Reference
	private ResourcePermissionLocalService _resourcePermissionLocalService;

	@Reference
	private RoleLocalService _roleLocalService;

}