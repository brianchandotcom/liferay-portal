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
import com.liferay.data.engine.rest.internal.dto.v1_0.util.DataRecordCollectionUtil;
import com.liferay.data.engine.rest.internal.model.InternalDataRecordCollection;
import com.liferay.data.engine.rest.internal.resource.common.CommonDataRecordCollectionResource;
import com.liferay.data.engine.rest.resource.v1_0.DataRecordCollectionResource;
import com.liferay.dynamic.data.lists.service.DDLRecordSetLocalService;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ResourceLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

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

		CommonDataRecordCollectionResource<DataRecordCollection>
			commonDataRecordCollectionResource =
				_getCommonDataRecordCollectionResource();

		commonDataRecordCollectionResource.deleteDataRecordCollection(
			dataRecordCollectionId);
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
	public DataRecordCollection getSiteDataRecordCollection(
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

		CommonDataRecordCollectionResource<DataRecordCollection>
			commonDataRecordCollectionResource =
				_getCommonDataRecordCollectionResource();

		return commonDataRecordCollectionResource.
			postDataDefinitionDataRecordCollection(
				contextCompany, dataDefinitionId,
				dataRecordCollection.getDataRecordCollectionKey(),
				dataRecordCollection.getDescription(),
				dataRecordCollection.getName());
	}

	@Override
	public void postDataRecordCollectionDataRecordCollectionPermission(
			Long dataRecordCollectionId, String operation,
			DataRecordCollectionPermission dataRecordCollectionPermission)
		throws Exception {

		CommonDataRecordCollectionResource<DataRecordCollection>
			commonDataRecordCollectionResource =
				_getCommonDataRecordCollectionResource();

		commonDataRecordCollectionResource.
			postDataRecordCollectionDataRecordCollectionPermissions(
				contextCompany, dataRecordCollectionId,
				GetterUtil.getBoolean(
					dataRecordCollectionPermission.getAddDataRecord()),
				GetterUtil.getBoolean(
					dataRecordCollectionPermission.getDelete()),
				GetterUtil.getBoolean(
					dataRecordCollectionPermission.getDeleteDataRecord()),
				GetterUtil.getBoolean(
					dataRecordCollectionPermission.getExportDataRecord()),
				GetterUtil.getBoolean(
					dataRecordCollectionPermission.getUpdate()),
				GetterUtil.getBoolean(
					dataRecordCollectionPermission.getUpdateDataRecord()),
				GetterUtil.getBoolean(dataRecordCollectionPermission.getView()),
				GetterUtil.getBoolean(
					dataRecordCollectionPermission.getViewDataRecord()),
				operation, dataRecordCollectionPermission.getRoleNames());
	}

	@Override
	public void postSiteDataRecordCollectionPermission(
			Long siteId, String operation,
			DataRecordCollectionPermission dataRecordCollectionPermission)
		throws Exception {

		CommonDataRecordCollectionResource<DataRecordCollection>
			commonDataRecordCollectionResource =
				_getCommonDataRecordCollectionResource();

		commonDataRecordCollectionResource.
			postSiteDataRecordCollectionPermissions(
				contextCompany,
				GetterUtil.getBoolean(
					dataRecordCollectionPermission.
						getAddDataRecordCollection()),
				GetterUtil.getBoolean(
					dataRecordCollectionPermission.getDefinePermissions()),
				operation, dataRecordCollectionPermission.getRoleNames(),
				siteId);
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