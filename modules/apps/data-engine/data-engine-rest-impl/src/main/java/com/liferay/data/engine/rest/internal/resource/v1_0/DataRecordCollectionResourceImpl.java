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
import com.liferay.data.engine.rest.internal.dto.v1_0.util.LocalizedValueUtil;
import com.liferay.data.engine.rest.resource.v1_0.DataRecordCollectionResource;
import com.liferay.dynamic.data.lists.model.DDLRecordSet;
import com.liferay.dynamic.data.lists.model.DDLRecordSetConstants;
import com.liferay.dynamic.data.lists.service.DDLRecordSetLocalService;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMStructureService;
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
	properties = "OSGI-INF/liferay/rest/v1_0/data-record-collection.properties",
	scope = ServiceScope.PROTOTYPE, service = DataRecordCollectionResource.class
)
public class DataRecordCollectionResourceImpl
	extends BaseDataRecordCollectionResourceImpl {

	@Override
	public boolean deleteDataRecordCollection(Long dataRecordCollectionId)
		throws Exception {

		_ddlRecordSetLocalService.deleteRecordSet(dataRecordCollectionId);

		return true;
	}

	@Override
	public Page<DataRecordCollection>
			getDataDefinitionDataRecordCollectionsPage(
				Long dataDefinitionId, String keywords, Pagination pagination)
		throws Exception {

		DDMStructure ddmStructure = _ddmStructureService.getStructure(
			dataDefinitionId);

		if (keywords == null) {
			return Page.of(
				transform(
					_ddlRecordSetLocalService.getRecordSets(
						ddmStructure.getGroupId(),
						pagination.getStartPosition(),
						pagination.getEndPosition()),
					this::_toDataRecordCollection),
				pagination,
				_ddlRecordSetLocalService.getRecordSetsCount(
					ddmStructure.getGroupId()));
		}

		return Page.of(
			transform(
				_ddlRecordSetLocalService.search(
					contextCompany.getCompanyId(), ddmStructure.getGroupId(),
					keywords, DDLRecordSetConstants.SCOPE_DATA_ENGINE,
					pagination.getStartPosition(), pagination.getEndPosition(),
					null),
				this::_toDataRecordCollection),
			pagination,
			_ddlRecordSetLocalService.searchCount(
				contextCompany.getCompanyId(), ddmStructure.getGroupId(),
				keywords, DDLRecordSetConstants.SCOPE_DATA_ENGINE));
	}

	@Override
	public DataRecordCollection getDataRecordCollection(
			Long dataRecordCollectionId)
		throws Exception {

		return _toDataRecordCollection(
			_ddlRecordSetLocalService.getRecordSet(dataRecordCollectionId));
	}

	@Override
	public Page<DataRecordCollection> getDataRecordCollectionsPage(
			Long contentSpaceId, String keywords, Pagination pagination)
		throws Exception {

		if (keywords == null) {
			return Page.of(
				transform(
					_ddlRecordSetLocalService.getRecordSets(
						contentSpaceId, pagination.getStartPosition(),
						pagination.getEndPosition()),
					this::_toDataRecordCollection),
				pagination,
				_ddlRecordSetLocalService.getRecordSetsCount(contentSpaceId));
		}

		return Page.of(
			transform(
				_ddlRecordSetLocalService.search(
					contextCompany.getCompanyId(), contentSpaceId, keywords,
					DDLRecordSetConstants.SCOPE_DATA_ENGINE,
					pagination.getStartPosition(), pagination.getEndPosition(),
					null),
				this::_toDataRecordCollection),
			pagination,
			_ddlRecordSetLocalService.searchCount(
				contextCompany.getCompanyId(), contentSpaceId, keywords,
				DDLRecordSetConstants.SCOPE_DATA_ENGINE));
	}

	@Override
	public DataRecordCollection postDataDefinitionDataRecordCollection(
			Long dataDefinitionId, DataRecordCollection dataRecordCollection)
		throws Exception {

		DDMStructure ddmStructure = _ddmStructureService.getStructure(
			dataDefinitionId);

		return _toDataRecordCollection(
			_ddlRecordSetLocalService.addRecordSet(
				PrincipalThreadLocal.getUserId(), ddmStructure.getGroupId(),
				dataRecordCollection.getDataDefinitionId(), null,
				LocalizedValueUtil.toLocalizationMap(
					dataRecordCollection.getName()),
				LocalizedValueUtil.toLocalizationMap(
					dataRecordCollection.getDescription()),
				0, DDLRecordSetConstants.SCOPE_DATA_ENGINE,
				new ServiceContext()));
	}

	@Override
	public DataRecordCollection putDataRecordCollection(
			Long dataRecordCollectionId,
			DataRecordCollection dataRecordCollection)
		throws Exception {

		DDLRecordSet ddlRecordSet = _ddlRecordSetLocalService.getRecordSet(
			dataRecordCollectionId);

		return _toDataRecordCollection(
			_ddlRecordSetLocalService.updateRecordSet(
				dataRecordCollectionId, ddlRecordSet.getDDMStructureId(),
				LocalizedValueUtil.toLocalizationMap(
					dataRecordCollection.getName()),
				LocalizedValueUtil.toLocalizationMap(
					dataRecordCollection.getDescription()),
				0, new ServiceContext()));
	}

	private DataRecordCollection _toDataRecordCollection(
		DDLRecordSet ddlRecordSet) {

		return new DataRecordCollection() {
			{
				dataDefinitionId = ddlRecordSet.getDDMStructureId();
				description = LocalizedValueUtil.toLocalizedValues(
					ddlRecordSet.getDescriptionMap());
				id = ddlRecordSet.getRecordSetId();
				name = LocalizedValueUtil.toLocalizedValues(
					ddlRecordSet.getNameMap());
			}
		};
	}

	@Reference
	private DDLRecordSetLocalService _ddlRecordSetLocalService;

	@Reference
	private DDMStructureService _ddmStructureService;

}