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
import com.liferay.dynamic.data.mapping.io.DDMFormValuesJSONDeserializer;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesJSONSerializer;
import com.liferay.dynamic.data.mapping.model.DDMContent;
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

			DDMFormValues ddmFormValues =
				ddmFormValuesJSONDeserializer.deserialize(
					ddmStorageAdapterGetRequest.getDDMForm(),
					ddmContent.getData());

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

	protected DDMStorageAdapterSaveResponse insert(
			DDMStorageAdapterSaveRequest ddmStorageAdapterSaveRequest)
		throws StorageException {

		DDMFormValues ddmFormValues =
			ddmStorageAdapterSaveRequest.getDDMFormValues();

		try {
			long userId = ddmStorageAdapterSaveRequest.getUserId();
			long scopeGroupId = ddmStorageAdapterSaveRequest.getScopeGroupId();

			ServiceContext serviceContext = new ServiceContext();

			serviceContext.setUserId(userId);
			serviceContext.setScopeGroupId(scopeGroupId);
			serviceContext.setUuid(ddmStorageAdapterSaveRequest.getUuid());

			DDMContent ddmContent = ddmContentLocalService.addContent(
				userId, scopeGroupId,
				ddmStorageAdapterSaveRequest.getClassName(), null,
				ddmFormValuesJSONSerializer.serialize(ddmFormValues),
				serviceContext);

			DDMStorageAdapterSaveResponse.Builder builder =
				DDMStorageAdapterSaveResponse.Builder.newBuilder(
					ddmContent.getPrimaryKey());

			return builder.build();
		}
		catch (Exception e) {
			throw new StorageException(e);
		}
	}

	protected DDMStorageAdapterSaveResponse update(
			DDMStorageAdapterSaveRequest ddmStorageAdapterSaveRequest)
		throws StorageException {

		try {
			DDMContent ddmContent = ddmContentLocalService.getContent(
				ddmStorageAdapterSaveRequest.getPrimaryKey());

			ddmContent.setModifiedDate(new Date());

			ddmContent.setData(
				ddmFormValuesJSONSerializer.serialize(
					ddmStorageAdapterSaveRequest.getDDMFormValues()));

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
	protected DDMFormValuesJSONDeserializer ddmFormValuesJSONDeserializer;

	@Reference
	protected DDMFormValuesJSONSerializer ddmFormValuesJSONSerializer;

}