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
import com.liferay.data.engine.service.DEDataRecordCollectionDeleteRecordRequest;
import com.liferay.data.engine.service.DEDataRecordCollectionDeleteRecordResponse;
import com.liferay.data.engine.storage.DEDataStorage;
import com.liferay.data.engine.storage.DEDataStorageDeleteRequest;
import com.liferay.data.engine.storage.DEDataStorageRequestBuilder;
import com.liferay.dynamic.data.lists.model.DDLRecord;
import com.liferay.dynamic.data.lists.model.DDLRecordSet;
import com.liferay.dynamic.data.lists.service.DDLRecordLocalService;
import com.liferay.dynamic.data.mapping.model.DDMStructure;

/**
 * @author Leonardo Barros
 */
public class DEDataRecordCollectionDeleteRecordRequestExecutor {

	public DEDataRecordCollectionDeleteRecordRequestExecutor(
		DEDataStorageTracker deDataStorageTracker,
		DDLRecordLocalService ddlRecordLocalService) {

		_deDataStorageTracker = deDataStorageTracker;
		_ddlRecordLocalService = ddlRecordLocalService;
	}

	public DEDataRecordCollectionDeleteRecordResponse execute(
			DEDataRecordCollectionDeleteRecordRequest
				deDataRecordCollectionDeleteRecordRequest)
		throws Exception {

		long deDataRecordId =
			deDataRecordCollectionDeleteRecordRequest.getDEDataRecordId();

		DDLRecord ddlRecord = _ddlRecordLocalService.getRecord(deDataRecordId);

		DDLRecordSet ddlRecordRecordSet = ddlRecord.getRecordSet();

		DDMStructure ddmStructure = ddlRecordRecordSet.getDDMStructure();

		String storageType = ddmStructure.getStorageType();

		DEDataStorage deDataStorage = _deDataStorageTracker.getDEDataStorage(
			storageType);

		if (deDataStorage == null) {
			throw new DEDataRecordCollectionException.NoSuchDataStorage(
				storageType);
		}

		DEDataStorageDeleteRequest deDataStorageDeleteRequest =
			DEDataStorageRequestBuilder.deleteBuilder(
				ddlRecord.getDDMStorageId()
			).build();

		_ddlRecordLocalService.deleteRecord(ddlRecord);

		deDataStorage.delete(deDataStorageDeleteRequest);

		return DEDataRecordCollectionDeleteRecordResponse.Builder.of(
			deDataRecordId);
	}

	private final DDLRecordLocalService _ddlRecordLocalService;
	private final DEDataStorageTracker _deDataStorageTracker;

}