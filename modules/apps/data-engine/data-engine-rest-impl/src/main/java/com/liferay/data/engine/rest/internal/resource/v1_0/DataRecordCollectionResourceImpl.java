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
import com.liferay.data.engine.rest.internal.dto.v1_0.util.DataEngineUtil;
import com.liferay.data.engine.rest.resource.v1_0.DataRecordCollectionResource;
import com.liferay.dynamic.data.lists.model.DDLRecordSet;
import com.liferay.dynamic.data.lists.model.DDLRecordSetConstants;
import com.liferay.dynamic.data.lists.service.DDLRecordSetLocalService;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Jeyvison Nascimento
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/Data-record-collection.properties",
	scope = ServiceScope.PROTOTYPE, service = DataRecordCollectionResource.class
)
public class DataRecordCollectionResourceImpl
	extends BaseDataRecordCollectionResourceImpl {

	@Override
	public DataRecordCollection getDataRecordCollection(
			Long dataRecordCollectionId)
		throws Exception {

		return _toDataRecordCollection(
			_ddlRecordSetLocalService.getRecordSet(dataRecordCollectionId));
	}

	@Override
	public Page<DataRecordCollection> getDataRecordCollectionSearchPage(
			Long groupId, String keywords, Pagination pagination)
		throws Exception {

		return Page.of(
			transform(
				_ddlRecordSetLocalService.search(
					contextCompany.getCompanyId(), groupId, keywords,
					DDLRecordSetConstants.SCOPE_DATA_ENGINE,
					pagination.getStartPosition(), pagination.getEndPosition(),
					null),
				this::_toDataRecordCollection),
			pagination,
			_ddlRecordSetLocalService.searchCount(
				contextCompany.getCompanyId(), groupId, keywords,
				DDLRecordSetConstants.SCOPE_DATA_ENGINE));
	}

	@Override
	public Page<DataRecordCollection> getDataRecordCollectionsPage(
			Long groupId, Pagination pagination)
		throws Exception {

		return Page.of(
			transform(
				_ddlRecordSetLocalService.getRecordSets(
					groupId, pagination.getStartPosition(),
					pagination.getEndPosition()),
				this::_toDataRecordCollection),
			pagination, _ddlRecordSetLocalService.getRecordSetsCount(groupId));
	}

	@Override
	public DataRecordCollection postDataRecordCollection(
			Long groupId, DataRecordCollection dataRecordCollection)
		throws Exception {

		return _toDataRecordCollection(
			_ddlRecordSetLocalService.addRecordSet(
				PrincipalThreadLocal.getUserId(), groupId,
				dataRecordCollection.getDataDefinitionId(), null,
				DataEngineUtil.toLocalizationMap(
					dataRecordCollection.getName()),
				DataEngineUtil.toLocalizationMap(
					dataRecordCollection.getDescription()),
				0, DDLRecordSetConstants.SCOPE_DATA_ENGINE,
				new ServiceContext()));
	}

	@Override
	public DataRecordCollection putDataRecordCollection(
			Long dataRecordCollectionId,
			DataRecordCollection dataRecordCollection)
		throws Exception {

		return _toDataRecordCollection(
			_ddlRecordSetLocalService.updateRecordSet(
				dataRecordCollection.getId(),
				dataRecordCollection.getDataDefinitionId(),
				DataEngineUtil.toLocalizationMap(
					dataRecordCollection.getName()),
				DataEngineUtil.toLocalizationMap(
					dataRecordCollection.getDescription()),
				0, new ServiceContext()));
	}

	private DataRecordCollection _toDataRecordCollection(
		DDLRecordSet ddlRecordSet) {

		DataRecordCollection dataRecordCollection = new DataRecordCollection();

		dataRecordCollection.setDataDefinitionId(
			ddlRecordSet.getDDMStructureId());
		dataRecordCollection.setId(ddlRecordSet.getRecordSetId());
		dataRecordCollection.setDescription(
			DataEngineUtil.toLocalizedValues(ddlRecordSet.getDescriptionMap()));
		dataRecordCollection.setName(
			DataEngineUtil.toLocalizedValues(ddlRecordSet.getNameMap()));

		return dataRecordCollection;
	}

	@Reference
	private DDLRecordSetLocalService _ddlRecordSetLocalService;

}