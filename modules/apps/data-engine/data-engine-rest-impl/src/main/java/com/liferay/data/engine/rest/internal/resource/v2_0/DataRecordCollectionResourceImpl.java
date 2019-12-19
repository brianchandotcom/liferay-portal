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

import com.liferay.data.engine.rest.dto.v2_0.DataRecordCollection;
import com.liferay.data.engine.rest.internal.dto.v2_0.util.DataRecordCollectionUtil;
import com.liferay.data.engine.rest.internal.model.InternalDataRecordCollection;
import com.liferay.data.engine.rest.internal.resource.common.CommonDataRecordCollectionResource;
import com.liferay.data.engine.rest.resource.v2_0.DataRecordCollectionResource;
import com.liferay.dynamic.data.lists.service.DDLRecordSetLocalService;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ResourceLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Jeyvison Nascimento
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v2_0/data-record-collection.properties",
	scope = ServiceScope.PROTOTYPE, service = DataRecordCollectionResource.class
)
public class DataRecordCollectionResourceImpl
	extends BaseDataRecordCollectionResourceImpl {

	@Override
	public void deleteDataRecordCollection(Long dataRecordCollectionId)
		throws Exception {

		CommonDataRecordCollectionResource<DataRecordCollection>
			commonDataRecordCollectionResource =
				_getCommonDataRecordCollectionResource();

		commonDataRecordCollectionResource.deleteDataRecordCollection(
			dataRecordCollectionId);
	}

	@Override
	public DataRecordCollection getDataDefinitionDataRecordCollection(
			Long dataDefinitionId)
		throws Exception {

		DDMStructure ddmStructure = _ddmStructureLocalService.getStructure(
			dataDefinitionId);

		return DataRecordCollectionUtil.toDataRecordCollection(
			_ddlRecordSetLocalService.getRecordSet(
				ddmStructure.getGroupId(), ddmStructure.getStructureKey()));
	}

	@Override
	public Page<DataRecordCollection>
			getDataDefinitionDataRecordCollectionsPage(
				Long dataDefinitionId, String keywords, Pagination pagination)
		throws Exception {

		CommonDataRecordCollectionResource<DataRecordCollection>
			commonDataRecordCollectionResource =
				_getCommonDataRecordCollectionResource();

		return commonDataRecordCollectionResource.
			getDataDefinitionDataRecordCollectionsPage(
				contextAcceptLanguage, contextCompany, dataDefinitionId,
				keywords, pagination);
	}

	@Override
	public DataRecordCollection getDataRecordCollection(
			Long dataRecordCollectionId)
		throws Exception {

		CommonDataRecordCollectionResource<DataRecordCollection>
			commonDataRecordCollectionResource =
				_getCommonDataRecordCollectionResource();

		return commonDataRecordCollectionResource.getDataRecordCollection(
			dataRecordCollectionId);
	}

	@Override
	public DataRecordCollection
			getSiteDataRecordCollectionByDataRecordCollectionKey(
				Long siteId, String dataRecordCollectionKey)
		throws Exception {

		CommonDataRecordCollectionResource<DataRecordCollection>
			commonDataRecordCollectionResource =
				_getCommonDataRecordCollectionResource();

		return commonDataRecordCollectionResource.getSiteDataRecordCollection(
			dataRecordCollectionKey, siteId);
	}

	@Override
	public Page<DataRecordCollection> getSiteDataRecordCollectionsPage(
			Long siteId, String keywords, Pagination pagination)
		throws Exception {

		CommonDataRecordCollectionResource<DataRecordCollection>
			commonDataRecordCollectionResource =
				_getCommonDataRecordCollectionResource();

		return commonDataRecordCollectionResource.
			getSiteDataRecordCollectionsPage(
				contextAcceptLanguage, contextCompany, keywords, pagination,
				siteId);
	}

	@Override
	public DataRecordCollection postDataDefinitionDataRecordCollection(
			Long dataDefinitionId, DataRecordCollection dataRecordCollection)
		throws Exception {

		DDMStructure ddmStructure = _ddmStructureLocalService.getDDMStructure(
			dataDefinitionId);

		String dataRecordCollectionKey =
			dataRecordCollection.getDataRecordCollectionKey();

		if (Validator.isNull(dataRecordCollectionKey)) {
			dataRecordCollectionKey = ddmStructure.getStructureKey();
		}

		CommonDataRecordCollectionResource<DataRecordCollection>
			commonDataRecordCollectionResource =
				_getCommonDataRecordCollectionResource();

		return commonDataRecordCollectionResource.
			postDataDefinitionDataRecordCollection(
				contextCompany, dataDefinitionId, dataRecordCollectionKey,
				dataRecordCollection.getDescription(),
				dataRecordCollection.getName());
	}

	@Override
	public DataRecordCollection putDataRecordCollection(
			Long dataRecordCollectionId,
			DataRecordCollection dataRecordCollection)
		throws Exception {

		CommonDataRecordCollectionResource<DataRecordCollection>
			commonDataRecordCollectionResource =
				_getCommonDataRecordCollectionResource();

		return commonDataRecordCollectionResource.putDataRecordCollection(
			dataRecordCollectionId, dataRecordCollection.getDescription(),
			dataRecordCollection.getName());
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

	private CommonDataRecordCollectionResource<DataRecordCollection>
		_getCommonDataRecordCollectionResource() {

		return new CommonDataRecordCollectionResource<>(
			_ddlRecordSetLocalService, _ddmStructureLocalService,
			_groupLocalService, _modelResourcePermission, _resourceLocalService,
			_resourcePermissionLocalService, _roleLocalService,
			DataRecordCollectionUtil::toDataRecordCollection);
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