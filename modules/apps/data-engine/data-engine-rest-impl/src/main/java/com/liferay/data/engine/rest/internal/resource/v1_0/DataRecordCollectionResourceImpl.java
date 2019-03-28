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

import com.liferay.data.engine.rest.dto.v1_0.DataRecordCollection;
import com.liferay.data.engine.rest.dto.v1_0.DataRecordCollectionPermission;
import com.liferay.data.engine.rest.internal.constants.DataActionKeys;
import com.liferay.data.engine.rest.internal.constants.DataEngineConstants;
import com.liferay.data.engine.rest.internal.constants.DataRecordCollectionConstants;
import com.liferay.data.engine.rest.internal.dto.v1_0.util.DataRecordCollectionUtil;
import com.liferay.data.engine.rest.internal.dto.v1_0.util.LocalizedValueUtil;
import com.liferay.data.engine.rest.internal.model.InternalDataRecordCollection;
import com.liferay.data.engine.rest.internal.resource.v1_0.util.DataEnginePermissionUtil;
import com.liferay.data.engine.rest.resource.v1_0.DataRecordCollectionResource;
import com.liferay.dynamic.data.lists.model.DDLRecordSet;
import com.liferay.dynamic.data.lists.model.DDLRecordSetConstants;
import com.liferay.dynamic.data.lists.service.DDLRecordSetLocalService;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.ResourcePermission;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.permission.ModelPermissions;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
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
	properties = "OSGI-INF/liferay/rest/v1_0/data-record-collection.properties",
	scope = ServiceScope.PROTOTYPE, service = DataRecordCollectionResource.class
)
public class DataRecordCollectionResourceImpl
	extends BaseDataRecordCollectionResourceImpl {

	@Override
	public void deleteDataRecordCollection(Long dataRecordCollectionId)
		throws Exception {

		_modelResourcePermission.check(
			PermissionThreadLocal.getPermissionChecker(),
			dataRecordCollectionId, ActionKeys.DELETE);

		_ddlRecordSetLocalService.deleteRecordSet(dataRecordCollectionId);
	}

	@Override
	public Page<DataRecordCollection> getContentSpaceDataRecordCollectionsPage(
			Long contentSpaceId, String keywords, Pagination pagination)
		throws Exception {

		if (Validator.isNull(keywords)) {
			return Page.of(
				transform(
					_ddlRecordSetLocalService.getRecordSets(
						contentSpaceId, pagination.getStartPosition(),
						pagination.getEndPosition()),
					DataRecordCollectionUtil::toDataRecordCollection),
				pagination,
				_ddlRecordSetLocalService.getRecordSetsCount(contentSpaceId));
		}

		Group group = _groupLocalService.getGroup(contentSpaceId);

		return Page.of(
			transform(
				_ddlRecordSetLocalService.search(
					group.getCompanyId(), contentSpaceId, keywords,
					DDLRecordSetConstants.SCOPE_DATA_ENGINE,
					pagination.getStartPosition(), pagination.getEndPosition(),
					null),
				DataRecordCollectionUtil::toDataRecordCollection),
			pagination,
			_ddlRecordSetLocalService.searchCount(
				group.getCompanyId(), contentSpaceId, keywords,
				DDLRecordSetConstants.SCOPE_DATA_ENGINE));
	}

	@Override
	public Page<DataRecordCollection>
			getDataDefinitionDataRecordCollectionsPage(
				Long dataDefinitionId, String keywords, Pagination pagination)
		throws Exception {

		DDMStructure ddmStructure = _ddmStructureLocalService.getStructure(
			dataDefinitionId);

		if (Validator.isNull(keywords)) {
			return Page.of(
				transform(
					_ddlRecordSetLocalService.getRecordSets(
						ddmStructure.getGroupId(),
						pagination.getStartPosition(),
						pagination.getEndPosition()),
					DataRecordCollectionUtil::toDataRecordCollection),
				pagination,
				_ddlRecordSetLocalService.getRecordSetsCount(
					ddmStructure.getGroupId()));
		}

		return Page.of(
			transform(
				_ddlRecordSetLocalService.search(
					ddmStructure.getCompanyId(), ddmStructure.getGroupId(),
					keywords, DDLRecordSetConstants.SCOPE_DATA_ENGINE,
					pagination.getStartPosition(), pagination.getEndPosition(),
					null),
				DataRecordCollectionUtil::toDataRecordCollection),
			pagination,
			_ddlRecordSetLocalService.searchCount(
				ddmStructure.getCompanyId(), ddmStructure.getGroupId(),
				keywords, DDLRecordSetConstants.SCOPE_DATA_ENGINE));
	}

	@Override
	public DataRecordCollection getDataRecordCollection(
			Long dataRecordCollectionId)
		throws Exception {

		_modelResourcePermission.check(
			PermissionThreadLocal.getPermissionChecker(),
			dataRecordCollectionId, ActionKeys.VIEW);

		return DataRecordCollectionUtil.toDataRecordCollection(
			_ddlRecordSetLocalService.getRecordSet(dataRecordCollectionId));
	}

	@Override
	public void postContentSpaceDataRecordCollectionPermission(
			Long contentSpaceId, String operation,
			DataRecordCollectionPermission dataRecordCollectionPermission)
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

		if (dataRecordCollectionPermission.getAddDataRecordCollection()) {
			actionIds.add(DataActionKeys.ADD_DATA_DEFINITION);
		}

		if (dataRecordCollectionPermission.getDefinePermissions()) {
			actionIds.add(DataActionKeys.DEFINE_PERMISSIONS);
		}

		if (actionIds.isEmpty()) {
			return;
		}

		if (StringUtil.equalsIgnoreCase(
				DataEngineConstants.OPERATION_SAVE_PERMISSION, operation)) {

			List<Role> roles = DataEnginePermissionUtil.getRoles(
				contextCompany, _roleLocalService,
				dataRecordCollectionPermission.getRoleNames());

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
				dataRecordCollectionPermission.getRoleNames());

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
	public DataRecordCollection postDataDefinitionDataRecordCollection(
			Long dataDefinitionId, DataRecordCollection dataRecordCollection)
		throws Exception {

		DDMStructure ddmStructure = _ddmStructureLocalService.getStructure(
			dataDefinitionId);

		DataEnginePermissionUtil.checkPermission(
			DataActionKeys.ADD_DATA_RECORD_COLLECTION,
			ddmStructure.getGroupId(), _groupLocalService);

		return DataRecordCollectionUtil.toDataRecordCollection(
			_ddlRecordSetLocalService.addRecordSet(
				PrincipalThreadLocal.getUserId(), ddmStructure.getGroupId(),
				dataDefinitionId, null,
				LocalizedValueUtil.toLocalizationMap(
					dataRecordCollection.getName()),
				LocalizedValueUtil.toLocalizationMap(
					dataRecordCollection.getDescription()),
				0, DDLRecordSetConstants.SCOPE_DATA_ENGINE,
				new ServiceContext()));
	}

	@Override
	public void postDataRecordCollectionDataRecordCollectionPermission(
			Long dataRecordCollectionId, String operation,
			DataRecordCollectionPermission dataRecordCollectionPermission)
		throws Exception {

		if (!StringUtil.equalsIgnoreCase(
				DataEngineConstants.OPERATION_DELETE_PERMISSION, operation) &&
			!StringUtil.equalsIgnoreCase(
				DataEngineConstants.OPERATION_SAVE_PERMISSION, operation)) {

			throw new BadRequestException(
				"Operation must be 'delete' or 'save'");
		}

		DDLRecordSet ddlRecordSet = _ddlRecordSetLocalService.getRecordSet(
			dataRecordCollectionId);

		DataEnginePermissionUtil.checkPermission(
			DataActionKeys.DEFINE_PERMISSIONS, ddlRecordSet.getGroupId(),
			_groupLocalService);

		List<String> actionIds = new ArrayList<>();

		if (dataRecordCollectionPermission.getDelete()) {
			actionIds.add(ActionKeys.DELETE);
		}

		if (dataRecordCollectionPermission.getUpdate()) {
			actionIds.add(ActionKeys.UPDATE);
		}

		if (dataRecordCollectionPermission.getView()) {
			actionIds.add(ActionKeys.VIEW);
		}

		if (actionIds.isEmpty()) {
			return;
		}

		if (StringUtil.equalsIgnoreCase(
				DataEngineConstants.OPERATION_SAVE_PERMISSION, operation)) {

			ModelPermissions modelPermissions = new ModelPermissions();

			for (String roleName :
					dataRecordCollectionPermission.getRoleNames()) {

				modelPermissions.addRolePermissions(
					roleName, ArrayUtil.toStringArray(actionIds));
			}

			_resourcePermissionLocalService.addModelResourcePermissions(
				contextCompany.getCompanyId(), ddlRecordSet.getGroupId(),
				PrincipalThreadLocal.getUserId(),
				DataRecordCollectionConstants.RESOURCE_NAME,
				String.valueOf(dataRecordCollectionId), modelPermissions);
		}
		else {
			List<Role> roles = DataEnginePermissionUtil.getRoles(
				contextCompany, _roleLocalService,
				dataRecordCollectionPermission.getRoleNames());

			for (Role role : roles) {
				for (String actionId : actionIds) {
					_resourcePermissionLocalService.removeResourcePermission(
						contextCompany.getCompanyId(),
						DataRecordCollectionConstants.RESOURCE_NAME,
						ResourceConstants.SCOPE_INDIVIDUAL,
						String.valueOf(dataRecordCollectionId),
						role.getRoleId(), actionId);
				}
			}
		}
	}

	@Override
	public DataRecordCollection putDataRecordCollection(
			Long dataRecordCollectionId,
			DataRecordCollection dataRecordCollection)
		throws Exception {

		DDLRecordSet ddlRecordSet = _ddlRecordSetLocalService.getRecordSet(
			dataRecordCollectionId);

		_modelResourcePermission.check(
			PermissionThreadLocal.getPermissionChecker(),
			dataRecordCollectionId, ActionKeys.UPDATE);

		return DataRecordCollectionUtil.toDataRecordCollection(
			_ddlRecordSetLocalService.updateRecordSet(
				dataRecordCollectionId, ddlRecordSet.getDDMStructureId(),
				LocalizedValueUtil.toLocalizationMap(
					dataRecordCollection.getName()),
				LocalizedValueUtil.toLocalizationMap(
					dataRecordCollection.getDescription()),
				0, new ServiceContext()));
	}

	@Reference(
		target = "(model.class.name=com.liferay.data.engine.rest.internal.model.InternalDataRecordCollection)",
		unbind = "-"
	)
	protected void setModelResourcePermission(
		ModelResourcePermission<InternalDataRecordCollection>
			modelResourcePermission) {

		_modelResourcePermission = modelResourcePermission;
	}

	@Reference
	private DDLRecordSetLocalService _ddlRecordSetLocalService;

	@Reference
	private DDMStructureLocalService _ddmStructureLocalService;

	@Reference
	private GroupLocalService _groupLocalService;

	private ModelResourcePermission<InternalDataRecordCollection>
		_modelResourcePermission;

	@Reference
	private ResourcePermissionLocalService _resourcePermissionLocalService;

	@Reference
	private RoleLocalService _roleLocalService;

}