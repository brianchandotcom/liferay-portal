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
import com.liferay.data.engine.rest.internal.resource.spi.SPIDataRecordCollectionResource;
import com.liferay.data.engine.rest.resource.v1_0.DataRecordCollectionResource;
import com.liferay.dynamic.data.lists.service.DDLRecordSetLocalService;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ResourceLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
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

		_spiDataRecordCollectionResource.deleteDataRecordCollection(
			dataRecordCollectionId);
	}

	@Override
	public Page<DataRecordCollection>
			getDataDefinitionDataRecordCollectionsPage(
				Long dataDefinitionId, String keywords, Pagination pagination)
		throws Exception {

		return _spiDataRecordCollectionResource.
			getDataDefinitionDataRecordCollectionsPage(
				contextAcceptLanguage, dataDefinitionId, keywords, pagination);
	}

	@Override
	public DataRecordCollection getDataRecordCollection(
			Long dataRecordCollectionId)
		throws Exception {

		return _spiDataRecordCollectionResource.getDataRecordCollection(
			dataRecordCollectionId);
	}

	@Override
	public DataRecordCollection getSiteDataRecordCollection(
			Long siteId, String dataRecordCollectionKey)
		throws Exception {

		return _spiDataRecordCollectionResource.getSiteDataRecordCollection(
			dataRecordCollectionKey, siteId);
	}

	@Override
	public Page<DataRecordCollection> getSiteDataRecordCollectionsPage(
			Long siteId, String keywords, Pagination pagination)
		throws Exception {

		return _spiDataRecordCollectionResource.
			getSiteDataRecordCollectionsPage(
				contextAcceptLanguage, keywords, pagination, siteId);
	}

	@Override
	public DataRecordCollection postDataDefinitionDataRecordCollection(
			Long dataDefinitionId, DataRecordCollection dataRecordCollection)
		throws Exception {

		return _spiDataRecordCollectionResource.
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

		_spiDataRecordCollectionResource.
			postDataRecordCollectionDataRecordCollectionPermissions(
				dataRecordCollectionPermission.getAddDataRecord(),
				contextCompany, dataRecordCollectionId,
				dataRecordCollectionPermission.getDelete(),
				dataRecordCollectionPermission.getDeleteDataRecord(),
				dataRecordCollectionPermission.getExportDataRecord(), operation,
				dataRecordCollectionPermission.getRoleNames(),
				dataRecordCollectionPermission.getUpdate(),
				dataRecordCollectionPermission.getUpdateDataRecord(),
				dataRecordCollectionPermission.getView(),
				dataRecordCollectionPermission.getViewDataRecord());
	}

	@Override
	public void postSiteDataRecordCollectionPermission(
			Long siteId, String operation,
			DataRecordCollectionPermission dataRecordCollectionPermission)
		throws Exception {

		_spiDataRecordCollectionResource.
			postSiteDataRecordCollectionPermissions(
				dataRecordCollectionPermission.getAddDataRecordCollection(),
				contextCompany,
				dataRecordCollectionPermission.getDefinePermissions(),
				operation, dataRecordCollectionPermission.getRoleNames(),
				siteId);
	}

	@Override
	public DataRecordCollection putDataRecordCollection(
			Long dataRecordCollectionId,
			DataRecordCollection dataRecordCollection)
		throws Exception {

		return _spiDataRecordCollectionResource.putDataRecordCollection(
			dataRecordCollectionId, dataRecordCollection.getDescription(),
			dataRecordCollection.getName());
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_spiDataRecordCollectionResource =
			new SPIDataRecordCollectionResource<>(
				_ddlRecordSetLocalService, _ddmStructureLocalService,
				_groupLocalService, _modelResourcePermission,
				_resourceLocalService, _resourcePermissionLocalService,
				_roleLocalService,
				DataRecordCollectionUtil::toDataRecordCollection);
	}

	@Deactivate
	protected void deactivate() {
		_spiDataRecordCollectionResource = null;
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

	private SPIDataRecordCollectionResource<DataRecordCollection>
		_spiDataRecordCollectionResource;

}