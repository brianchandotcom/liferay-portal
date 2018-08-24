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

package com.liferay.dynamic.data.mapping.storage.impl;

import com.liferay.dynamic.data.mapping.exception.StorageException;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesDeserializer;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesDeserializerDeserializeRequest;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesDeserializerDeserializeResponse;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesDeserializerTracker;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesSerializer;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesSerializerSerializeRequest;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesSerializerSerializeResponse;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesSerializerTracker;
import com.liferay.dynamic.data.mapping.model.DDMContent;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.service.DDMContentLocalService;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.storage.DDMStorageAdapter;
import com.liferay.dynamic.data.mapping.storage.DDMStorageAdapterDeleteRequest;
import com.liferay.dynamic.data.mapping.storage.DDMStorageAdapterDeleteResponse;
import com.liferay.dynamic.data.mapping.storage.DDMStorageAdapterGetRequest;
import com.liferay.dynamic.data.mapping.storage.DDMStorageAdapterGetResponse;
import com.liferay.dynamic.data.mapping.storage.DDMStorageAdapterSaveRequest;
import com.liferay.dynamic.data.mapping.storage.DDMStorageAdapterSaveResponse;
import com.liferay.portal.kernel.service.ServiceContext;

import java.util.Date;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Leonardo Barros
 */
@Component(immediate = true, property = "ddm.storage.adapter.type=json")
public class DDMJSONStorageAdapter implements DDMStorageAdapter {

	@Override
	public DDMStorageAdapterDeleteResponse delete(
			DDMStorageAdapterDeleteRequest ddmStorageAdapterDeleteRequest)
		throws StorageException {

		try {
			ddmContentLocalService.deleteDDMContent(
				ddmStorageAdapterDeleteRequest.getPrimaryKey());

			DDMStorageAdapterDeleteResponse.Builder builder =
				DDMStorageAdapterDeleteResponse.Builder.newBuilder();

			return builder.build();
		}
		catch (Exception e) {
			throw new StorageException(e);
		}
	}

	@Override
	public DDMStorageAdapterGetResponse get(
			DDMStorageAdapterGetRequest ddmStorageAdapterGetRequest)
		throws StorageException {

		try {
			DDMContent ddmContent = ddmContentLocalService.getContent(
				ddmStorageAdapterGetRequest.getPrimaryKey());

			DDMFormValues ddmFormValues = deserialize(
				ddmContent.getData(), ddmStorageAdapterGetRequest.getDDMForm());

			DDMStorageAdapterGetResponse.Builder builder =
				DDMStorageAdapterGetResponse.Builder.newBuilder(ddmFormValues);

			return builder.build();
		}
		catch (Exception e) {
			throw new StorageException(e);
		}
	}

	@Override
	public DDMStorageAdapterSaveResponse save(
			DDMStorageAdapterSaveRequest ddmStorageAdapterSaveRequest)
		throws StorageException {

		if (ddmStorageAdapterSaveRequest.isInsert()) {
			return insert(ddmStorageAdapterSaveRequest);
		}

		return update(ddmStorageAdapterSaveRequest);
	}

	protected DDMFormValues deserialize(String content, DDMForm ddmForm) {
		DDMFormValuesDeserializer ddmFormValuesDeserializer =
			ddmFormValuesDeserializerTracker.getDDMFormValuesDeserializer(
				"json");

		DDMFormValuesDeserializerDeserializeRequest.Builder builder =
			DDMFormValuesDeserializerDeserializeRequest.Builder.newBuilder(
				content, ddmForm);

		DDMFormValuesDeserializerDeserializeResponse
			ddmFormValuesDeserializerDeserializeResponse =
				ddmFormValuesDeserializer.deserialize(builder.build());

		return ddmFormValuesDeserializerDeserializeResponse.getDDMFormValues();
	}

	protected DDMStorageAdapterSaveResponse insert(
			DDMStorageAdapterSaveRequest ddmStorageAdapterSaveRequest)
		throws StorageException {

		DDMFormValues ddmFormValues =
			ddmStorageAdapterSaveRequest.getDDMFormValues();

		try {
			ServiceContext serviceContext = new ServiceContext();

			serviceContext.setScopeGroupId(
				ddmStorageAdapterSaveRequest.getScopeGroupId());
			serviceContext.setUserId(ddmStorageAdapterSaveRequest.getUserId());
			serviceContext.setUuid(ddmStorageAdapterSaveRequest.getUuid());

			DDMContent ddmContent = ddmContentLocalService.addContent(
				ddmStorageAdapterSaveRequest.getUserId(),
				ddmStorageAdapterSaveRequest.getScopeGroupId(),
				ddmStorageAdapterSaveRequest.getClassName(), null,
				serialize(ddmFormValues), serviceContext);

			DDMStorageAdapterSaveResponse.Builder builder =
				DDMStorageAdapterSaveResponse.Builder.newBuilder(
					ddmContent.getPrimaryKey());

			return builder.build();
		}
		catch (Exception e) {
			throw new StorageException(e);
		}
	}

	protected String serialize(DDMFormValues ddmFormValues) {
		DDMFormValuesSerializer ddmFormValuesSerializer =
			ddmFormValuesSerializerTracker.getDDMFormValuesSerializer("json");

		DDMFormValuesSerializerSerializeRequest.Builder builder =
			DDMFormValuesSerializerSerializeRequest.Builder.newBuilder(
				ddmFormValues);

		DDMFormValuesSerializerSerializeResponse
			ddmFormValuesSerializerSerializeResponse =
				ddmFormValuesSerializer.serialize(builder.build());

		return ddmFormValuesSerializerSerializeResponse.getContent();
	}

	protected DDMStorageAdapterSaveResponse update(
			DDMStorageAdapterSaveRequest ddmStorageAdapterSaveRequest)
		throws StorageException {

		try {
			DDMContent ddmContent = ddmContentLocalService.getContent(
				ddmStorageAdapterSaveRequest.getPrimaryKey());

			ddmContent.setModifiedDate(new Date());

			ddmContent.setData(
				serialize(ddmStorageAdapterSaveRequest.getDDMFormValues()));

			ddmContentLocalService.updateContent(
				ddmContent.getPrimaryKey(), ddmContent.getName(),
				ddmContent.getDescription(), ddmContent.getData(),
				new ServiceContext());

			DDMStorageAdapterSaveResponse.Builder builder =
				DDMStorageAdapterSaveResponse.Builder.newBuilder(
					ddmContent.getPrimaryKey());

			return builder.build();
		}
		catch (Exception e) {
			throw new StorageException(e);
		}
	}

	@Reference
	protected DDMContentLocalService ddmContentLocalService;

	@Reference
	protected DDMFormValuesDeserializerTracker ddmFormValuesDeserializerTracker;

	@Reference
	protected DDMFormValuesSerializerTracker ddmFormValuesSerializerTracker;

}