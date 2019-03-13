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
import com.liferay.data.engine.rest.dto.v1_0.DataRecordCollection;
import com.liferay.data.engine.rest.internal.dto.v1_0.util.LocalizedValueUtil;
import com.liferay.data.engine.rest.internal.storage.DEDataStorageTracker;
import com.liferay.data.engine.rest.resource.v1_0.DataRecordCollectionResource;
import com.liferay.data.engine.storage.DEDataStorage;
import com.liferay.dynamic.data.lists.model.DDLRecord;
import com.liferay.dynamic.data.lists.model.DDLRecordSet;
import com.liferay.dynamic.data.lists.model.DDLRecordSetConstants;
import com.liferay.dynamic.data.lists.model.DDLRecordSetVersion;
import com.liferay.dynamic.data.lists.service.DDLRecordLocalService;
import com.liferay.dynamic.data.lists.service.DDLRecordSetLocalService;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
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
	properties = "OSGI-INF/liferay/rest/v1_0/data-record-collection.properties",
	scope = ServiceScope.PROTOTYPE, service = DataRecordCollectionResource.class
)
public class DataRecordCollectionResourceImpl
	extends BaseDataRecordCollectionResourceImpl {

	@Override
	public boolean deleteDataRecordCollectionRecordDataRecord(
			Long dataRecordCollectionId, Long dataRecordId)
		throws Exception {

		DDLRecord ddlRecord = _ddlRecordLocalService.getDDLRecord(dataRecordId);

		DDLRecordSet ddlRecordSet = ddlRecord.getRecordSet();

		DDMStructure ddmStructure = ddlRecordSet.getDDMStructure();

		DEDataStorage deDataStorage = _deDataStorageTracker.getDEDataStorage(
			ddmStructure.getStorageType());

		deDataStorage.delete(ddlRecord.getDDMStorageId());

		_ddlRecordLocalService.deleteDDLRecord(ddlRecord);

		return true;
	}

	@Override
	public DataRecordCollection getDataRecordCollection(
			Long dataRecordCollectionId)
		throws Exception {

		return _toDataRecordCollection(
			_ddlRecordSetLocalService.getRecordSet(dataRecordCollectionId));
	}

	@Override
	public DataRecord getDataRecordCollectionRecordDataRecord(
			Long dataRecordCollectionId, Long dataRecordId)
		throws Exception {

		return _toDataRecord(_ddlRecordLocalService.getDDLRecord(dataRecordId));
	}

	@Override
	public Page<DataRecord> getDataRecordCollectionRecordsPage(
			Long dataRecordCollectionId, Pagination pagination)
		throws Exception {

		return Page.of(
			transform(
				_ddlRecordLocalService.getRecords(
					dataRecordCollectionId, pagination.getStartPosition(),
					pagination.getEndPosition(), null),
				this::_toDataRecord),
			pagination,
			_ddlRecordLocalService.getRecordsCount(dataRecordCollectionId));
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
	public DataRecordCollection postDataRecordCollection(
			Long contentSpaceId, DataRecordCollection dataRecordCollection)
		throws Exception {

		return _toDataRecordCollection(
			_ddlRecordSetLocalService.addRecordSet(
				PrincipalThreadLocal.getUserId(), contentSpaceId,
				dataRecordCollection.getDataDefinitionId(), null,
				LocalizedValueUtil.toLocalizationMap(
					dataRecordCollection.getName()),
				LocalizedValueUtil.toLocalizationMap(
					dataRecordCollection.getDescription()),
				0, DDLRecordSetConstants.SCOPE_DATA_ENGINE,
				new ServiceContext()));
	}

	@Override
	public DataRecord postDataRecordCollectionRecord(
			Long dataRecordCollectionId, Long contentSpaceId,
			DataRecord dataRecord)
		throws Exception {

		long storageId = _saveDataRecordOnStorage(
			dataRecordCollectionId, contentSpaceId, dataRecord);

		DDLRecord ddlRecord = _ddlRecordLocalService.addRecord(
			PrincipalThreadLocal.getUserId(), contentSpaceId, storageId,
			dataRecordCollectionId, new ServiceContext());

		dataRecord.setId(ddlRecord.getRecordId());

		_addStorageLink(storageId, ddlRecord);

		return dataRecord;
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
				LocalizedValueUtil.toLocalizationMap(
					dataRecordCollection.getName()),
				LocalizedValueUtil.toLocalizationMap(
					dataRecordCollection.getDescription()),
				0, new ServiceContext()));
	}

	@Override
	public DataRecord putDataRecordCollectionRecordDataRecord(
			Long dataRecordCollectionId, Long dataRecordId, Long contentSpaceId,
			DataRecord dataRecord)
		throws Exception {

		long storageId = _saveDataRecordOnStorage(
			dataRecordCollectionId, contentSpaceId, dataRecord);

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

	private long _saveDataRecordOnStorage(
			Long dataRecordCollectionId, Long contentSpaceId,
			DataRecord dataRecord)
		throws Exception {

		DDLRecordSet ddlRecordSet = _ddlRecordSetLocalService.getDDLRecordSet(
			dataRecordCollectionId);

		DDMStructure ddmStructure = ddlRecordSet.getDDMStructure();

		DEDataStorage deDataStorage = _deDataStorageTracker.getDEDataStorage(
			ddmStructure.getStorageType());

		return deDataStorage.save(contentSpaceId, dataRecord);
	}

	private DataRecord _toDataRecord(DDLRecord ddlRecord) throws Exception {
		DDLRecordSet ddlRecordSet = ddlRecord.getRecordSet();

		DDMStructure ddmStructure = ddlRecordSet.getDDMStructure();

		DEDataStorage deDataStorage = _deDataStorageTracker.getDEDataStorage(
			ddmStructure.getStorageType());

		deDataStorage.get(
			ddmStructure.getStructureId(), ddlRecord.getDDMStorageId());

		return new DataRecord() {
			{
				dataRecordCollectionId = ddlRecordSet.getRecordSetId();
				id = ddlRecord.getRecordId();
				dataRecordValues = deDataStorage.get(
					ddmStructure.getStructureId(), ddlRecord.getDDMStorageId());
			}
		};
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
	private DDLRecordLocalService _ddlRecordLocalService;

	@Reference
	private DDLRecordSetLocalService _ddlRecordSetLocalService;

	@Reference
	private DDMStorageLinkLocalService _ddmStorageLinkLocalService;

	@Reference
	private DEDataStorageTracker _deDataStorageTracker;

	@Reference
	private Portal _portal;

}