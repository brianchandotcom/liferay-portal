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

package com.liferay.data.engine.internal.executor;

import com.liferay.data.engine.exception.DEDataRecordCollectionException;
import com.liferay.data.engine.internal.storage.DEDataStorageTracker;
import com.liferay.data.engine.model.DEDataDefinition;
import com.liferay.data.engine.model.DEDataRecord;
import com.liferay.data.engine.service.DEDataRecordCollectionSaveRecordRequest;
import com.liferay.data.engine.service.DEDataRecordCollectionSaveRecordResponse;
import com.liferay.data.engine.storage.DEDataStorage;
import com.liferay.data.engine.storage.DEDataStorageRequestBuilder;
import com.liferay.data.engine.storage.DEDataStorageSaveRequest;
import com.liferay.data.engine.storage.DEDataStorageSaveResponse;
import com.liferay.dynamic.data.lists.model.DDLRecord;
import com.liferay.dynamic.data.lists.model.DDLRecordSet;
import com.liferay.dynamic.data.lists.model.DDLRecordSetVersion;
import com.liferay.dynamic.data.lists.service.DDLRecordLocalService;
import com.liferay.dynamic.data.mapping.model.DDMStructureVersion;
import com.liferay.dynamic.data.mapping.service.DDMStorageLinkLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.util.Portal;

/**
 * @author Leonardo Barros
 */
public class DEDataRecordCollectionSaveRecordRequestExecutor {

	public DEDataRecordCollectionSaveRecordRequestExecutor(
		DEDataStorageTracker deDataStorageTracker,
		DDLRecordLocalService ddlRecordLocalService,
		DDMStorageLinkLocalService ddmStorageLinkLocalService, Portal portal) {

		_deDataStorageTracker = deDataStorageTracker;
		_ddlRecordLocalService = ddlRecordLocalService;
		_ddmStorageLinkLocalService = ddmStorageLinkLocalService;
		_portal = portal;
	}

	public DEDataRecordCollectionSaveRecordResponse execute(
			DEDataRecordCollectionSaveRecordRequest
				deDataRecordCollectionSaveRecordRequest)
		throws Exception {

		DEDataRecord deDataRecord =
			deDataRecordCollectionSaveRecordRequest.getDEDataRecord();

		DEDataDefinition deDataDefinition = deDataRecord.getDEDataDefinition();

		if (deDataDefinition == null) {
			return DEDataRecordCollectionSaveRecordResponse.Builder.of(
				null
			);
		}

		String storageType = deDataDefinition.getStorageType();

		DEDataStorage deDataStorage = _deDataStorageTracker.getDEDataStorage(
			storageType);

		if (deDataStorage == null) {
			throw new DEDataRecordCollectionException.NoSuchDataStorage(
				storageType);
		}

		DEDataStorageSaveRequest deDataStorageSaveRequest =
			DEDataStorageRequestBuilder.saveBuilder(
				deDataRecord
			).inGroup(
				deDataRecordCollectionSaveRecordRequest.getGroupId()
			).onBehalfOf(
				deDataRecordCollectionSaveRecordRequest.getUserId()
			).build();

		DEDataStorageSaveResponse deDataStorageSaveResponse =
			deDataStorage.save(deDataStorageSaveRequest);

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		long deDataRecordId = deDataRecord.getDEDataRecordId();

		DDLRecord ddlRecord = null;

		if (deDataRecordId == 0) {
			ddlRecord = _ddlRecordLocalService.addRecord(
				deDataRecordCollectionSaveRecordRequest.getUserId(),
				deDataRecordCollectionSaveRecordRequest.getGroupId(),
				deDataStorageSaveResponse.getDEDataStorageId(),
				deDataRecord.getDEDataRecordCollectionId(), serviceContext);
		}
		else {
			ddlRecord = _ddlRecordLocalService.updateRecord(
				deDataRecordCollectionSaveRecordRequest.getUserId(),
				deDataRecordCollectionSaveRecordRequest.getGroupId(),
				deDataStorageSaveResponse.getDEDataStorageId(), serviceContext);
		}

		addStorageLink(
			deDataStorageSaveResponse.getDEDataStorageId(), ddlRecord,
			serviceContext);

		deDataRecord.setDEDataRecordId(ddlRecord.getRecordId());

		return DEDataRecordCollectionSaveRecordResponse.Builder.of(
			deDataRecord
		);
	}

	protected void addStorageLink(
			long deDataStorageId, DDLRecord ddlRecord,
			ServiceContext serviceContext)
		throws Exception {

		DDLRecordSet ddlRecordSet = ddlRecord.getRecordSet();

		DDLRecordSetVersion ddlRecordSetVersion =
			ddlRecordSet.getRecordSetVersion();

		DDMStructureVersion ddmStructureVersion =
			ddlRecordSetVersion.getDDMStructureVersion();

		_ddmStorageLinkLocalService.addStorageLink(
			_portal.getClassNameId(DEDataRecord.class.getName()),
			deDataStorageId, ddmStructureVersion.getStructureVersionId(),
			serviceContext);
	}

	private final DDLRecordLocalService _ddlRecordLocalService;
	private final DDMStorageLinkLocalService _ddmStorageLinkLocalService;
	private final DEDataStorageTracker _deDataStorageTracker;
	private final Portal _portal;

}