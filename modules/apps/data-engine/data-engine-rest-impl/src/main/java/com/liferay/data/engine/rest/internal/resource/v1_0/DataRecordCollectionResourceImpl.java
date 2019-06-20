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
import com.liferay.data.engine.rest.internal.constants.DataRecordCollectionConstants;
import com.liferay.data.engine.rest.internal.dto.v1_0.util.DataRecordCollectionUtil;
import com.liferay.data.engine.rest.internal.model.InternalDataRecordCollection;
import com.liferay.data.engine.rest.internal.resource.v1_0.util.DataEnginePermissionUtil;
import com.liferay.data.engine.rest.resource.v1_0.DataRecordCollectionResource;
import com.liferay.data.engine.spi.field.type.util.LocalizedValueUtil;
import com.liferay.dynamic.data.lists.model.DDLRecordSet;
import com.liferay.dynamic.data.lists.model.DDLRecordSetConstants;
import com.liferay.dynamic.data.lists.service.DDLRecordSetLocalService;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ResourceLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.util.ArrayList;
import java.util.List;

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
	public DataRecordCollection getSiteDataRecordCollection(
			Long siteId, String dataRecordCollectionKey)
		throws Exception {

		return DataRecordCollectionUtil.toDataRecordCollection(
			_ddlRecordSetLocalService.getRecordSet(
				siteId, dataRecordCollectionKey));
	}

	@Override
	public Page<DataRecordCollection> getSiteDataRecordCollectionsPage(
			Long siteId, String keywords, Pagination pagination)
		throws Exception {

		if (Validator.isNull(keywords)) {
			return Page.of(
				transform(
					_ddlRecordSetLocalService.getRecordSets(
						siteId, pagination.getStartPosition(),
						pagination.getEndPosition()),
					DataRecordCollectionUtil::toDataRecordCollection),
				pagination,
				_ddlRecordSetLocalService.getRecordSetsCount(siteId));
		}

		Group group = _groupLocalService.getGroup(siteId);

		return Page.of(
			transform(
				_ddlRecordSetLocalService.search(
					group.getCompanyId(), siteId, keywords,
					DDLRecordSetConstants.SCOPE_DATA_ENGINE,
					pagination.getStartPosition(), pagination.getEndPosition(),
					null),
				DataRecordCollectionUtil::toDataRecordCollection),
			pagination,
			_ddlRecordSetLocalService.searchCount(
				group.getCompanyId(), siteId, keywords,
				DDLRecordSetConstants.SCOPE_DATA_ENGINE));
	}

	@Override
	public DataRecordCollection postDataDefinitionDataRecordCollection(
			Long dataDefinitionId, DataRecordCollection dataRecordCollection)
		throws Exception {

		DDMStructure ddmStructure = _ddmStructureLocalService.getStructure(
			dataDefinitionId);

		DataEnginePermissionUtil.checkPermission(
			DataActionKeys.ADD_DATA_RECORD_COLLECTION, _groupLocalService,
			ddmStructure.getGroupId());

		ServiceContext serviceContext = new ServiceContext();

		dataRecordCollection = DataRecordCollectionUtil.toDataRecordCollection(
			_ddlRecordSetLocalService.addRecordSet(
				PrincipalThreadLocal.getUserId(), ddmStructure.getGroupId(),
				dataDefinitionId,
				dataRecordCollection.getDataRecordCollectionKey(),
				LocalizedValueUtil.toLocaleStringMap(
					dataRecordCollection.getName()),
				LocalizedValueUtil.toLocaleStringMap(
					dataRecordCollection.getDescription()),
				0, DDLRecordSetConstants.SCOPE_DATA_ENGINE, serviceContext));

		_resourceLocalService.addModelResources(
			contextCompany.getCompanyId(), ddmStructure.getGroupId(),
			PrincipalThreadLocal.getUserId(),
			InternalDataRecordCollection.class.getName(),
			dataRecordCollection.getId(), serviceContext.getModelPermissions());

		return dataRecordCollection;
	}

	@Override
	public void postDataRecordCollectionDataRecordCollectionPermission(
			Long dataRecordCollectionId, String operation,
			DataRecordCollectionPermission dataRecordCollectionPermission)
		throws Exception {

		DDLRecordSet ddlRecordSet = _ddlRecordSetLocalService.getRecordSet(
			dataRecordCollectionId);

		DataEnginePermissionUtil.checkOperationPermission(
			_groupLocalService, operation, ddlRecordSet.getGroupId());

		List<String> actionIds = new ArrayList<>();

		if (dataRecordCollectionPermission.getAddDataRecord()) {
			actionIds.add(DataActionKeys.ADD_DATA_RECORD);
		}

		if (dataRecordCollectionPermission.getDelete()) {
			actionIds.add(ActionKeys.DELETE);
		}

		if (dataRecordCollectionPermission.getDeleteDataRecord()) {
			actionIds.add(DataActionKeys.DELETE_DATA_RECORD);
		}

		if (dataRecordCollectionPermission.getExportDataRecord()) {
			actionIds.add(DataActionKeys.EXPORT_DATA_RECORDS);
		}

		if (dataRecordCollectionPermission.getUpdate()) {
			actionIds.add(ActionKeys.UPDATE);
		}

		if (dataRecordCollectionPermission.getUpdateDataRecord()) {
			actionIds.add(DataActionKeys.UPDATE_DATA_RECORD);
		}

		if (dataRecordCollectionPermission.getView()) {
			actionIds.add(ActionKeys.VIEW);
		}

		if (dataRecordCollectionPermission.getViewDataRecord()) {
			actionIds.add(DataActionKeys.VIEW_DATA_RECORD);
		}

		if (actionIds.isEmpty()) {
			return;
		}

		DataEnginePermissionUtil.persistModelPermission(
			actionIds, contextCompany, dataRecordCollectionId, operation,
			DataRecordCollectionConstants.RESOURCE_NAME,
			_resourcePermissionLocalService, _roleLocalService,
			dataRecordCollectionPermission.getRoleNames(),
			ddlRecordSet.getGroupId());
	}

	@Override
	public void postSiteDataRecordCollectionPermission(
			Long siteId, String operation,
			DataRecordCollectionPermission dataRecordCollectionPermission)
		throws Exception {

		DataEnginePermissionUtil.checkOperationPermission(
			_groupLocalService, operation, siteId);

		List<String> actionIds = new ArrayList<>();

		if (dataRecordCollectionPermission.getAddDataRecordCollection()) {
			actionIds.add(DataActionKeys.ADD_DATA_RECORD_COLLECTION);
		}

		if (dataRecordCollectionPermission.getDefinePermissions()) {
			actionIds.add(DataActionKeys.DEFINE_PERMISSIONS);
		}

		if (actionIds.isEmpty()) {
			return;
		}

		DataEnginePermissionUtil.persistPermission(
			actionIds, contextCompany, operation,
			_resourcePermissionLocalService, _roleLocalService,
			dataRecordCollectionPermission.getRoleNames());
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

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setUserId(PrincipalThreadLocal.getUserId());

		return DataRecordCollectionUtil.toDataRecordCollection(
			_ddlRecordSetLocalService.updateRecordSet(
				dataRecordCollectionId, ddlRecordSet.getDDMStructureId(),
				LocalizedValueUtil.toLocaleStringMap(
					dataRecordCollection.getName()),
				LocalizedValueUtil.toLocaleStringMap(
					dataRecordCollection.getDescription()),
				0, serviceContext));
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
	private ResourceLocalService _resourceLocalService;

	@Reference
	private ResourcePermissionLocalService _resourcePermissionLocalService;

	@Reference
	private RoleLocalService _roleLocalService;

}