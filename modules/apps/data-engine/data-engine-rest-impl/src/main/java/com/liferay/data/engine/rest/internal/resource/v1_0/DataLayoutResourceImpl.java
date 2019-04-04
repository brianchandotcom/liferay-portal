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

import com.liferay.data.engine.rest.dto.v1_0.DataLayout;
import com.liferay.data.engine.rest.dto.v1_0.DataLayoutPermission;
import com.liferay.data.engine.rest.internal.constants.DataActionKeys;
import com.liferay.data.engine.rest.internal.constants.DataEngineConstants;
import com.liferay.data.engine.rest.internal.constants.DataLayoutConstants;
import com.liferay.data.engine.rest.internal.dto.v1_0.util.DataLayoutUtil;
import com.liferay.data.engine.rest.internal.dto.v1_0.util.LocalizedValueUtil;
import com.liferay.data.engine.rest.internal.model.InternalDataLayout;
import com.liferay.data.engine.rest.internal.model.InternalDataRecordCollection;
import com.liferay.data.engine.rest.internal.resource.v1_0.util.DataEnginePermissionUtil;
import com.liferay.data.engine.rest.resource.v1_0.DataLayoutResource;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMStructureLayout;
import com.liferay.dynamic.data.mapping.model.DDMStructureVersion;
import com.liferay.dynamic.data.mapping.service.DDMStructureLayoutLocalService;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.service.DDMStructureVersionLocalService;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.ResourcePermission;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ResourceLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.permission.ModelPermissions;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.BadRequestException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Jeyvison Nascimento
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/data-layout.properties",
	scope = ServiceScope.PROTOTYPE, service = DataLayoutResource.class
)
public class DataLayoutResourceImpl extends BaseDataLayoutResourceImpl {

	@Override
	public void deleteDataLayout(Long dataLayoutId) throws Exception {
		_modelResourcePermission.check(
			PermissionThreadLocal.getPermissionChecker(), dataLayoutId,
			ActionKeys.DELETE);

		_ddmStructureLayoutLocalService.deleteDDMStructureLayout(dataLayoutId);
	}

	@Override
	public Page<DataLayout> getContentSpaceDataLayoutPage(
			Long contentSpaceId, Pagination pagination)
		throws Exception {

		return Page.of(
			transform(
				_ddmStructureLayoutLocalService.getStructureLayouts(
					contentSpaceId, pagination.getStartPosition(),
					pagination.getEndPosition()),
				this::_toDataLayout),
			pagination,
			_ddmStructureLayoutLocalService.getStructureLayoutsCount(
				contentSpaceId));
	}

	@Override
	public DataLayout getDataLayout(Long dataLayoutId) throws Exception {
		_modelResourcePermission.check(
			PermissionThreadLocal.getPermissionChecker(), dataLayoutId,
			ActionKeys.VIEW);

		return _toDataLayout(
			_ddmStructureLayoutLocalService.getDDMStructureLayout(
				dataLayoutId));
	}

	@Override
	public void postContentSpaceDataLayoutPermission(
			Long contentSpaceId, String operation,
			DataLayoutPermission dataLayoutPermission)
		throws Exception {

		if (!StringUtil.equalsIgnoreCase(
				DataEngineConstants.OPERATION_DELETE_PERMISSION, operation) &&
			!StringUtil.equalsIgnoreCase(
				DataEngineConstants.OPERATION_SAVE_PERMISSION, operation)) {

			throw new BadRequestException(
				"Operation must be 'delete' or 'save'");
		}

		DataEnginePermissionUtil.checkPermission(
			DataActionKeys.DEFINE_PERMISSIONS, contentSpaceId,
			_groupLocalService);

		List<String> actionIds = new ArrayList<>();

		if (dataLayoutPermission.getAddDataLayout()) {
			actionIds.add(DataActionKeys.ADD_DATA_LAYOUT);
		}

		if (dataLayoutPermission.getDefinePermissions()) {
			actionIds.add(DataActionKeys.DEFINE_PERMISSIONS);
		}

		if (actionIds.isEmpty()) {
			return;
		}

		if (StringUtil.equalsIgnoreCase(
				DataEngineConstants.OPERATION_SAVE_PERMISSION, operation)) {

			List<Role> roles = DataEnginePermissionUtil.getRoles(
				contextCompany, _roleLocalService,
				dataLayoutPermission.getRoleNames());

			for (Role role : roles) {
				_resourcePermissionLocalService.setResourcePermissions(
					contextCompany.getCompanyId(),
					DataEngineConstants.RESOURCE_NAME,
					ResourceConstants.SCOPE_COMPANY,
					String.valueOf(contextCompany.getCompanyId()),
					role.getRoleId(), ArrayUtil.toStringArray(actionIds));
			}
		}
		else {
			List<Role> roles = DataEnginePermissionUtil.getRoles(
				contextCompany, _roleLocalService,
				dataLayoutPermission.getRoleNames());

			for (Role role : roles) {
				ResourcePermission resourcePermission =
					_resourcePermissionLocalService.fetchResourcePermission(
						contextCompany.getCompanyId(),
						DataEngineConstants.RESOURCE_NAME,
						ResourceConstants.SCOPE_COMPANY,
						String.valueOf(contextCompany.getCompanyId()),
						role.getRoleId());

				if (resourcePermission != null) {
					_resourcePermissionLocalService.deleteResourcePermission(
						resourcePermission);
				}
			}
		}
	}

	@Override
	public DataLayout postDataDefinitionDataLayout(
			Long dataDefinitionId, DataLayout dataLayout)
		throws Exception {

		if (ArrayUtil.isEmpty(dataLayout.getName())) {
			throw new Exception("Name is required");
		}

		DDMStructure ddmStructure = _ddmStructureLocalService.getStructure(
			dataDefinitionId);

		DataEnginePermissionUtil.checkPermission(
			DataActionKeys.ADD_DATA_LAYOUT, ddmStructure.getGroupId(),
			_groupLocalService);

		ServiceContext serviceContext = new ServiceContext();

		DDMStructureLayout ddmStructureLayout =
			_ddmStructureLayoutLocalService.addStructureLayout(
				PrincipalThreadLocal.getUserId(), ddmStructure.getGroupId(),
				_getDDMStructureVersionId(dataDefinitionId),
				LocalizedValueUtil.toLocalizationMap(dataLayout.getName()),
				LocalizedValueUtil.toLocalizationMap(
					dataLayout.getDescription()),
				DataLayoutUtil.toJSON(dataLayout), serviceContext);

		dataLayout.setId(ddmStructureLayout.getStructureLayoutId());

		_resourceLocalService.addModelResources(
			contextCompany.getCompanyId(), ddmStructure.getGroupId(),
			PrincipalThreadLocal.getUserId(),
			InternalDataLayout.class.getName(), dataLayout.getId(),
			serviceContext.getModelPermissions());

		return dataLayout;
	}

	public void postDataLayoutDataLayoutPermission(
			Long dataLayoutId, String operation,
			DataLayoutPermission dataLayoutPermission)
		throws Exception {

		if (!StringUtil.equalsIgnoreCase(
				DataEngineConstants.OPERATION_DELETE_PERMISSION, operation) &&
			!StringUtil.equalsIgnoreCase(
				DataEngineConstants.OPERATION_SAVE_PERMISSION, operation)) {

			throw new BadRequestException(
				"Operation must be 'delete' or 'save'");
		}

		DDMStructureLayout ddmStructureLayout =
			_ddmStructureLayoutLocalService.getStructureLayout(dataLayoutId);

		DataEnginePermissionUtil.checkPermission(
			DataActionKeys.DEFINE_PERMISSIONS, ddmStructureLayout.getGroupId(),
			_groupLocalService);

		List<String> actionIds = new ArrayList<>();

		if (dataLayoutPermission.getDelete()) {
			actionIds.add(ActionKeys.DELETE);
		}

		if (dataLayoutPermission.getUpdate()) {
			actionIds.add(ActionKeys.UPDATE);
		}

		if (dataLayoutPermission.getView()) {
			actionIds.add(ActionKeys.VIEW);
		}

		if (actionIds.isEmpty()) {
			return;
		}

		if (StringUtil.equalsIgnoreCase(
				DataEngineConstants.OPERATION_SAVE_PERMISSION, operation)) {

			ModelPermissions modelPermissions = new ModelPermissions();

			for (String roleName : dataLayoutPermission.getRoleNames()) {
				modelPermissions.addRolePermissions(
					roleName, ArrayUtil.toStringArray(actionIds));
			}

			_resourcePermissionLocalService.addModelResourcePermissions(
				contextCompany.getCompanyId(), ddmStructureLayout.getGroupId(),
				PrincipalThreadLocal.getUserId(),
				DataLayoutConstants.RESOURCE_NAME, String.valueOf(dataLayoutId),
				modelPermissions);
		}
		else {
			List<Role> roles = DataEnginePermissionUtil.getRoles(
				contextCompany, _roleLocalService,
				dataLayoutPermission.getRoleNames());

			for (Role role : roles) {
				for (String actionId : actionIds) {
					_resourcePermissionLocalService.removeResourcePermission(
						contextCompany.getCompanyId(),
						DataLayoutConstants.RESOURCE_NAME,
						ResourceConstants.SCOPE_INDIVIDUAL,
						String.valueOf(dataLayoutId), role.getRoleId(),
						actionId);
				}
			}
		}
	}

	@Override
	public DataLayout putDataLayout(Long dataLayoutId, DataLayout dataLayout)
		throws Exception {

		if (ArrayUtil.isEmpty(dataLayout.getName())) {
			throw new Exception("Name is required");
		}

		_modelResourcePermission.check(
			PermissionThreadLocal.getPermissionChecker(), dataLayoutId,
			ActionKeys.UPDATE);

		return _toDataLayout(
			_ddmStructureLayoutLocalService.updateStructureLayout(
				dataLayoutId,
				_getDDMStructureVersionId(dataLayout.getDataDefinitionId()),
				LocalizedValueUtil.toLocalizationMap(dataLayout.getName()),
				LocalizedValueUtil.toLocalizationMap(
					dataLayout.getDescription()),
				DataLayoutUtil.toJSON(dataLayout), new ServiceContext()));
	}

	@Reference(
		target = "(model.class.name=com.liferay.data.engine.rest.internal.model.InternalDataLayout)",
		unbind = "-"
	)
	protected void setModelResourcePermission(
		ModelResourcePermission<InternalDataRecordCollection>
			modelResourcePermission) {

		_modelResourcePermission = modelResourcePermission;
	}

	private long _getDDMStructureId(DDMStructureLayout ddmStructureLayout)
		throws Exception {

		DDMStructureVersion ddmStructureVersion =
			_ddmStructureVersionLocalService.getDDMStructureVersion(
				ddmStructureLayout.getStructureVersionId());

		DDMStructure ddmStructure = ddmStructureVersion.getStructure();

		return ddmStructure.getStructureId();
	}

	private long _getDDMStructureVersionId(Long deDataDefinitionId)
		throws Exception {

		DDMStructureVersion ddmStructureVersion =
			_ddmStructureVersionLocalService.getLatestStructureVersion(
				deDataDefinitionId);

		return ddmStructureVersion.getStructureVersionId();
	}

	private DataLayout _toDataLayout(DDMStructureLayout ddmStructureLayout)
		throws Exception {

		DataLayout dataLayout = DataLayoutUtil.toDataLayout(
			ddmStructureLayout.getDefinition());

		dataLayout.setDateCreated(ddmStructureLayout.getCreateDate());
		dataLayout.setDataDefinitionId(_getDDMStructureId(ddmStructureLayout));
		dataLayout.setId(ddmStructureLayout.getStructureLayoutId());
		dataLayout.setDescription(
			LocalizedValueUtil.toLocalizedValues(
				ddmStructureLayout.getDescriptionMap()));
		dataLayout.setDateModified(ddmStructureLayout.getModifiedDate());
		dataLayout.setName(
			LocalizedValueUtil.toLocalizedValues(
				ddmStructureLayout.getNameMap()));
		dataLayout.setUserId(ddmStructureLayout.getUserId());

		return dataLayout;
	}

	@Reference
	private DDMStructureLayoutLocalService _ddmStructureLayoutLocalService;

	@Reference
	private DDMStructureLocalService _ddmStructureLocalService;

	@Reference
	private DDMStructureVersionLocalService _ddmStructureVersionLocalService;

	@Reference
	private GroupLocalService _groupLocalService;

	private ModelResourcePermission<InternalDataRecordCollection>
		_modelResourcePermission;

	@Reference
	private ResourceLocalService _resourceLocalService;

	@Reference
	private ResourcePermissionLocalService _resourcePermissionLocalService;

	@Reference
	private RoleLocalService _roleLocalService;

}