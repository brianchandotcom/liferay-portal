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

package com.liferay.data.engine.internal.storage;

import com.liferay.data.engine.internal.io.DEDataRecordValuesDeserializerTracker;
import com.liferay.data.engine.internal.io.DEDataRecordValuesSerializerTracker;
import com.liferay.data.engine.io.DEDataRecordValuesDeserializer;
import com.liferay.data.engine.io.DEDataRecordValuesDeserializerApplyRequest;
import com.liferay.data.engine.io.DEDataRecordValuesDeserializerApplyResponse;
import com.liferay.data.engine.io.DEDataRecordValuesRequestBuilder;
import com.liferay.data.engine.io.DEDataRecordValuesSerializer;
import com.liferay.data.engine.io.DEDataRecordValuesSerializerApplyRequest;
import com.liferay.data.engine.io.DEDataRecordValuesSerializerApplyResponse;
import com.liferay.data.engine.model.DEDataDefinition;
import com.liferay.data.engine.model.DEDataRecord;
import com.liferay.data.engine.storage.DEDataStorage;
import com.liferay.data.engine.storage.DEDataStorageDeleteRequest;
import com.liferay.data.engine.storage.DEDataStorageDeleteResponse;
import com.liferay.data.engine.storage.DEDataStorageGetRequest;
import com.liferay.data.engine.storage.DEDataStorageGetResponse;
import com.liferay.data.engine.storage.DEDataStorageResponseBuilder;
import com.liferay.data.engine.storage.DEDataStorageSaveRequest;
import com.liferay.data.engine.storage.DEDataStorageSaveResponse;
import com.liferay.dynamic.data.mapping.model.DDMContent;
import com.liferay.dynamic.data.mapping.service.DDMContentLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;

import java.util.Date;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Leonardo Barros
 */
@Component(
	immediate = true, property = "de.data.storage.type=json",
	service = DEDataStorage.class
)
public class DEDataJSONStorage implements DEDataStorage {

	@Override
	public DEDataStorageDeleteResponse delete(
			DEDataStorageDeleteRequest deDataStorageDeleteRequest)
		throws PortalException {

		ddmContentLocalService.deleteDDMContent(
			deDataStorageDeleteRequest.getDEDataStorageId());

		return DEDataStorageResponseBuilder.deleteBuilder(
			deDataStorageDeleteRequest.getDEDataStorageId()
		).build();
	}

	@Override
	public DEDataStorageGetResponse get(
			DEDataStorageGetRequest deDataStorageGetRequest)
		throws PortalException {

		DEDataRecordValuesDeserializer deDataRecordValuesDeserializer =
			deDataRecordValuesDeserializerTracker.getDEDataRecordDeserializer(
				"json");

		if (deDataRecordValuesDeserializer == null) {
			return DEDataStorageResponseBuilder.getBuilder(
				null
			).build();
		}

		DDMContent ddmContent = ddmContentLocalService.getContent(
			deDataStorageGetRequest.getDEDataStorageId());

		DEDataRecordValuesDeserializerApplyRequest.Builder builder =
			DEDataRecordValuesRequestBuilder.deserializeBuilder(
				ddmContent.getData(),
				deDataStorageGetRequest.getDEDataDefinition()
			);

		DEDataRecordValuesDeserializerApplyResponse
			deDataRecordValuesDeserializerApplyResponse =
				deDataRecordValuesDeserializer.apply(builder.build());

		return DEDataStorageResponseBuilder.getBuilder(
			deDataRecordValuesDeserializerApplyResponse.getValues()
		).build();
	}

	@Override
	public DEDataStorageSaveResponse save(
			DEDataStorageSaveRequest deDataStorageSaveRequest)
		throws PortalException {

		DEDataRecordValuesSerializer deDataRecordValuesSerializer =
			deDataRecordValuesSerializerTracker.getDEDataRecordSerializer(
				"json");

		if (deDataRecordValuesSerializer == null) {
			return DEDataStorageResponseBuilder.saveBuilder(
				0
			).build();
		}

		DEDataRecord deDataRecord = deDataStorageSaveRequest.getDEDataRecord();

		Map<String, Object> values = deDataRecord.getValues();

		DEDataDefinition deDataDefinition = deDataRecord.getDEDataDefinition();

		if ((values == null) || (deDataDefinition == null)) {
			return DEDataStorageResponseBuilder.saveBuilder(
				0
			).build();
		}

		DEDataRecordValuesSerializerApplyRequest.Builder builder =
			DEDataRecordValuesRequestBuilder.serializeBuilder(
				values, deDataDefinition
			);

		DEDataRecordValuesSerializerApplyResponse
			deDataRecordValuesSerializerApplyResponse =
				deDataRecordValuesSerializer.apply(builder.build());

		long deDataStorageId = deDataStorageSaveRequest.getDEDataStorageId();

		if (deDataRecord.getDEDataRecordId() == 0) {
			deDataStorageId = insert(
				deDataStorageSaveRequest.getUserId(),
				deDataStorageSaveRequest.getGroupId(),
				deDataRecordValuesSerializerApplyResponse.getContent());
		}
		else {
			update(
				deDataStorageId,
				deDataRecordValuesSerializerApplyResponse.getContent());
		}

		return DEDataStorageResponseBuilder.saveBuilder(
			deDataStorageId
		).build();
	}

	protected long insert(long userId, long groupId, String content)
		throws PortalException {

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setScopeGroupId(groupId);
		serviceContext.setUserId(userId);

		DDMContent ddmContent = ddmContentLocalService.addContent(
			userId, groupId, DEDataRecord.class.getName(), null, content,
			serviceContext);

		return ddmContent.getPrimaryKey();
	}

	protected void update(long deDataStorageId, String content)
		throws PortalException {

		DDMContent ddmContent = ddmContentLocalService.getContent(
			deDataStorageId);

		ddmContent.setModifiedDate(new Date());
		ddmContent.setData(content);

		ddmContentLocalService.updateContent(
			ddmContent.getPrimaryKey(), ddmContent.getName(),
			ddmContent.getDescription(), ddmContent.getData(),
			new ServiceContext());
	}

	@Reference
	protected DDMContentLocalService ddmContentLocalService;

	@Reference
	protected DEDataRecordValuesDeserializerTracker
		deDataRecordValuesDeserializerTracker;

	@Reference
	protected DEDataRecordValuesSerializerTracker
		deDataRecordValuesSerializerTracker;

}