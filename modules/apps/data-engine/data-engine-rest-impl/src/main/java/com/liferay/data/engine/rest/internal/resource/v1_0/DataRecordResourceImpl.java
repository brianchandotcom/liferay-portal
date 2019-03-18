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

import com.liferay.data.engine.rest.dto.v1_0.DataRecord;
import com.liferay.data.engine.rest.internal.storage.DataStorage;
import com.liferay.data.engine.rest.resource.v1_0.DataDefinitionResource;
import com.liferay.data.engine.rest.resource.v1_0.DataRecordCollectionResource;
import com.liferay.data.engine.rest.resource.v1_0.DataRecordResource;
import com.liferay.dynamic.data.lists.model.DDLRecord;
import com.liferay.dynamic.data.lists.model.DDLRecordSet;
import com.liferay.dynamic.data.lists.model.DDLRecordSetVersion;
import com.liferay.dynamic.data.lists.service.DDLRecordLocalService;
import com.liferay.dynamic.data.lists.service.DDLRecordService;
import com.liferay.dynamic.data.lists.service.DDLRecordSetLocalService;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMStructureVersion;
import com.liferay.dynamic.data.mapping.service.DDMContentLocalService;
import com.liferay.dynamic.data.mapping.service.DDMStorageLinkLocalService;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Jeyvison Nascimento
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/data-record.properties",
	scope = ServiceScope.PROTOTYPE, service = DataRecordResource.class
)
public class DataRecordResourceImpl extends BaseDataRecordResourceImpl {

	@Activate
	public void activate() {
		_dataStorage = new DataStorage(
			_dataRecordCollectionResource, _dataDefinitionResource,
			_ddmContentLocalService);
	}

	@Override
	public boolean deleteDataRecord(Long dataRecordId) throws Exception {
		_dataStorage.delete(dataRecordId);
		_ddlRecordLocalService.deleteDDLRecord(dataRecordId);

		return true;
	}

	@Override
	public DataRecord getDataRecord(Long dataRecordId) throws Exception {
		return _toDataRecord(_ddlRecordLocalService.getDDLRecord(dataRecordId));
	}

	@Override
	public Page<DataRecord> getDataRecordCollectionDataRecordsPage(
			Long dataRecordCollectionId, Pagination pagination)
		throws Exception {

		return Page.of(
			transform(
				_ddlRecordLocalService.getRecords(
					dataRecordCollectionId, pagination.getStartPosition(),
					pagination.getEndPosition(), null),
				this::_toDataRecord),
			pagination,
			_ddlRecordLocalService.getRecordsCount(
				dataRecordCollectionId, PrincipalThreadLocal.getUserId()));
	}

	@Override
	public DataRecord postDataRecordCollectionDataRecord(
			Long dataRecordCollectionId, DataRecord dataRecord)
		throws Exception {

		DDLRecordSet ddlRecordSet = _ddlRecordSetLocalService.getRecordSet(
			dataRecordCollectionId);

		return _toDataRecord(
			_ddlRecordLocalService.addRecord(
				PrincipalThreadLocal.getUserId(), ddlRecordSet.getGroupId(),
				_store(ddlRecordSet.getGroupId(), dataRecord),
				dataRecord.getDataRecordCollectionId(), new ServiceContext()));
	}

	@Override
	public DataRecord putDataRecord(Long dataRecordId, DataRecord dataRecord)
		throws Exception {

		DDLRecord ddlRecord = _ddlRecordService.getRecord(dataRecordId);

		long ddmStorageId = _store(ddlRecord.getGroupId(), dataRecord);

		_ddlRecordLocalService.updateRecord(
			PrincipalThreadLocal.getUserId(), dataRecordId, ddmStorageId,
			new ServiceContext());

		DDLRecordSet ddlRecordSet = ddlRecord.getRecordSet();

		DDLRecordSetVersion ddlRecordSetVersion =
			ddlRecordSet.getRecordSetVersion();

		DDMStructureVersion ddmStructureVersion =
			ddlRecordSetVersion.getDDMStructureVersion();

		_ddmStorageLinkLocalService.addStorageLink(
			_portal.getClassNameId(DataRecord.class.getName()), ddmStorageId,
			ddmStructureVersion.getStructureVersionId(), new ServiceContext());

		return dataRecord;
	}

	private long _store(long contentSpaceId, DataRecord dataRecord)
		throws Exception {

		return _dataStorage.save(contentSpaceId, dataRecord);
	}

	private DataRecord _toDataRecord(DDLRecord ddlRecord) throws Exception {
		DDLRecordSet ddlRecordSet = ddlRecord.getRecordSet();

		DDMStructure ddmStructure = ddlRecordSet.getDDMStructure();

		return new DataRecord() {
			{
				dataRecordCollectionId = ddlRecordSet.getRecordSetId();
				dataRecordValues = _dataStorage.get(
					ddmStructure.getStructureId(), ddlRecord.getDDMStorageId());
				id = ddlRecord.getRecordId();
			}
		};
	}

	@Reference
	private DataDefinitionResource _dataDefinitionResource;

	@Reference
	private DataRecordCollectionResource _dataRecordCollectionResource;

	private DataStorage _dataStorage;

	@Reference
	private DDLRecordLocalService _ddlRecordLocalService;

	@Reference
	private DDLRecordService _ddlRecordService;

	@Reference
	private DDLRecordSetLocalService _ddlRecordSetLocalService;

	@Reference
	private DDMContentLocalService _ddmContentLocalService;

	@Reference
	private DDMStorageLinkLocalService _ddmStorageLinkLocalService;

	@Reference
	private Portal _portal;

}