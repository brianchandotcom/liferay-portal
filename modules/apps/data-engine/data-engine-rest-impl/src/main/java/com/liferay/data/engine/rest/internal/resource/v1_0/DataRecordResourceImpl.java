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
import com.liferay.data.engine.rest.dto.v1_0.DataRecordValue;
import com.liferay.data.engine.rest.resource.v1_0.DataRecordResource;
import com.liferay.dynamic.data.lists.model.DDLRecord;
import com.liferay.dynamic.data.lists.model.DDLRecordSet;
import com.liferay.dynamic.data.lists.model.DDLRecordSetVersion;
import com.liferay.dynamic.data.lists.service.DDLRecordLocalService;
import com.liferay.dynamic.data.mapping.model.DDMStructureVersion;
import com.liferay.dynamic.data.mapping.service.DDMStorageLinkLocalService;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

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

	@Override
	public boolean deleteDataRecord(Long dataRecordId) throws Exception {
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
			Long dataRecordCollectionId, Long contentSpaceId,
			DataRecord dataRecord)
		throws Exception {

		return _toDataRecord(
			_ddlRecordLocalService.addRecord(
				PrincipalThreadLocal.getUserId(), contentSpaceId,
				_saveDataRecordOnStorage(),
				dataRecord.getDataRecordCollectionId(), new ServiceContext()));
	}

	@Override
	public DataRecord putDataRecord(Long dataRecordId, DataRecord dataRecord)
		throws Exception {

		long storageId = _saveDataRecordOnStorage();

		DDLRecord ddlRecord = _ddlRecordLocalService.updateRecord(
			PrincipalThreadLocal.getUserId(), dataRecordId, storageId,
			new ServiceContext());

		dataRecord.setId(ddlRecord.getRecordId());

		_addStorageLink(storageId, ddlRecord);

		return dataRecord;
	}

	private void _addStorageLink(long storageId, DDLRecord ddlRecord)
		throws Exception {

		DDLRecordSet ddlRecordSet = ddlRecord.getRecordSet();

		DDLRecordSetVersion ddlRecordSetVersion =
			ddlRecordSet.getRecordSetVersion();

		DDMStructureVersion ddmStructureVersion =
			ddlRecordSetVersion.getDDMStructureVersion();

		_ddmStorageLinkLocalService.addStorageLink(
			_portal.getClassNameId(DataRecord.class.getName()), storageId,
			ddmStructureVersion.getStructureVersionId(), new ServiceContext());
	}

	private long _saveDataRecordOnStorage() {

		// TODO DataStorage

		return 0;
	}

	private DataRecord _toDataRecord(DDLRecord ddlRecord) throws Exception {
		DDLRecordSet ddlRecordSet = ddlRecord.getRecordSet();

		//TODO DataStorage

		return new DataRecord() {
			{
				dataRecordCollectionId = ddlRecordSet.getRecordSetId();
				id = ddlRecord.getRecordId();
				dataRecordValues = new DataRecordValue[0];
			}
		};
	}

	@Reference
	private DDLRecordLocalService _ddlRecordLocalService;

	@Reference
	private DDMStorageLinkLocalService _ddmStorageLinkLocalService;

	private Portal _portal;

}