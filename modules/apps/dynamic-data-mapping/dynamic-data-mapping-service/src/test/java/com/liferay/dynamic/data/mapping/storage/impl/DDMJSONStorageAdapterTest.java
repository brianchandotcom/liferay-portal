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
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.service.DDMContentLocalService;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.storage.DDMStorageAdapterDeleteRequest;
import com.liferay.dynamic.data.mapping.storage.DDMStorageAdapterDeleteResponse;
import com.liferay.dynamic.data.mapping.storage.DDMStorageAdapterGetRequest;
import com.liferay.dynamic.data.mapping.storage.DDMStorageAdapterGetResponse;
import com.liferay.dynamic.data.mapping.storage.DDMStorageAdapterSaveRequest;
import com.liferay.dynamic.data.mapping.storage.DDMStorageAdapterSaveResponse;
import com.liferay.portal.kernel.service.ServiceContext;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import org.powermock.api.mockito.PowerMockito;

/**
 * @author Leonardo Barros
 */
@RunWith(MockitoJUnitRunner.class)
public class DDMJSONStorageAdapterTest extends PowerMockito {

	@Test
	public void testDelete() throws Exception {
		DDMJSONStorageAdapter ddmJSONStorageAdapter =
			new DDMJSONStorageAdapter();

		ddmJSONStorageAdapter.ddmContentLocalService = _ddmContentLocalService;

		DDMStorageAdapterDeleteRequest.Builder builder =
			DDMStorageAdapterDeleteRequest.Builder.newBuilder(1);

		DDMStorageAdapterDeleteResponse expectedResponse =
			ddmJSONStorageAdapter.delete(builder.build());

		Assert.assertTrue(expectedResponse.isDeleted());

		Mockito.verify(
			_ddmContentLocalService, Mockito.times(1)
		).deleteDDMContent(
			1
		);
	}

	@Test(expected = StorageException.class)
	public void testDeleteException() throws Exception {
		DDMJSONStorageAdapter ddmJSONStorageAdapter =
			new DDMJSONStorageAdapter();

		ddmJSONStorageAdapter.ddmContentLocalService = _ddmContentLocalService;

		when(
			_ddmContentLocalService.deleteDDMContent(2)
		).thenThrow(
			Exception.class
		);

		DDMStorageAdapterDeleteRequest.Builder builder =
			DDMStorageAdapterDeleteRequest.Builder.newBuilder(2);

		ddmJSONStorageAdapter.delete(builder.build());
	}

	@Test
	public void testGet() throws Exception {
		DDMJSONStorageAdapter ddmJSONStorageAdapter =
			new DDMJSONStorageAdapter();

		ddmJSONStorageAdapter.ddmContentLocalService = _ddmContentLocalService;
		ddmJSONStorageAdapter.ddmFormValuesJSONDeserializer =
			_ddmFormValuesJSONDeserializer;

		DDMContent ddmContent = mock(DDMContent.class);

		when(
			_ddmContentLocalService.getContent(1)
		).thenReturn(
			ddmContent
		);

		String data = "{}";

		when(
			ddmContent.getData()
		).thenReturn(
			data
		);

		DDMForm ddmForm = mock(DDMForm.class);

		DDMFormValues ddmFormValues = mock(DDMFormValues.class);

		when(
			_ddmFormValuesJSONDeserializer.deserialize(ddmForm, data)
		).thenReturn(
			ddmFormValues
		);

		DDMStorageAdapterGetRequest.Builder builder =
			DDMStorageAdapterGetRequest.Builder.newBuilder(1, ddmForm);

		DDMStorageAdapterGetResponse ddmStorageAdapterGetResponse =
			ddmJSONStorageAdapter.get(builder.build());

		Assert.assertNotNull(ddmStorageAdapterGetResponse.getDDMFormValues());

		Mockito.verify(
			_ddmContentLocalService, Mockito.times(1)
		).getContent(
			1
		);

		Mockito.verify(
			_ddmFormValuesJSONDeserializer, Mockito.times(1)
		).deserialize(
			ddmForm, data
		);
	}

	@Test(expected = StorageException.class)
	public void testGetException() throws Exception {
		DDMJSONStorageAdapter ddmJSONStorageAdapter =
			new DDMJSONStorageAdapter();

		ddmJSONStorageAdapter.ddmContentLocalService = _ddmContentLocalService;

		when(
			_ddmContentLocalService.getContent(1)
		).thenThrow(
			Exception.class
		);

		DDMForm ddmForm = mock(DDMForm.class);

		DDMStorageAdapterGetRequest.Builder builder =
			DDMStorageAdapterGetRequest.Builder.newBuilder(1, ddmForm);

		ddmJSONStorageAdapter.get(builder.build());
	}

	@Test
	public void testInsert() throws Exception {
		DDMJSONStorageAdapter ddmJSONStorageAdapter =
			new DDMJSONStorageAdapter();

		ddmJSONStorageAdapter.ddmContentLocalService = _ddmContentLocalService;
		ddmJSONStorageAdapter.ddmFormValuesJSONSerializer =
			_ddmFormValuesJSONSerializer;

		DDMContent ddmContent = mock(DDMContent.class);

		when(
			_ddmContentLocalService.addContent(
				Matchers.anyLong(), Matchers.anyLong(), Matchers.anyString(),
				Matchers.anyString(), Matchers.anyString(),
				Matchers.any(ServiceContext.class))
		).thenReturn(
			ddmContent
		);

		when(
			ddmContent.getPrimaryKey()
		).thenReturn(
			1L
		);

		DDMFormValues ddmFormValues = mock(DDMFormValues.class);

		when(
			_ddmFormValuesJSONSerializer.serialize(ddmFormValues)
		).thenReturn(
			"{}"
		);

		DDMStorageAdapterSaveRequest.Builder builder =
			DDMStorageAdapterSaveRequest.Builder.newBuilder(
				1, 1, ddmFormValues);

		DDMStorageAdapterSaveRequest ddmStorageAdapterSaveRequest =
			builder.build();

		DDMStorageAdapterSaveResponse ddmStorageAdapterSaveResponse =
			ddmJSONStorageAdapter.save(ddmStorageAdapterSaveRequest);

		Assert.assertEquals(1L, ddmStorageAdapterSaveResponse.getPrimaryKey());

		Mockito.verify(
			_ddmFormValuesJSONSerializer, Mockito.times(1)
		).serialize(
			ddmFormValues
		);

		Mockito.verify(
			_ddmContentLocalService, Mockito.times(1)
		).addContent(
			Matchers.anyLong(), Matchers.anyLong(), Matchers.anyString(),
			Matchers.anyString(), Matchers.anyString(),
			Matchers.any(ServiceContext.class)
		);
	}

	@Test(expected = StorageException.class)
	public void testInsertException() throws Exception {
		DDMJSONStorageAdapter ddmJSONStorageAdapter =
			new DDMJSONStorageAdapter();

		ddmJSONStorageAdapter.ddmContentLocalService = _ddmContentLocalService;
		ddmJSONStorageAdapter.ddmFormValuesJSONSerializer =
			_ddmFormValuesJSONSerializer;

		when(
			_ddmFormValuesJSONSerializer.serialize(
				Matchers.any(DDMFormValues.class))
		).thenThrow(
			Exception.class
		);

		DDMStorageAdapterSaveRequest.Builder builder =
			DDMStorageAdapterSaveRequest.Builder.newBuilder(
				1, 1, mock(DDMFormValues.class));

		DDMStorageAdapterSaveRequest ddmStorageAdapterSaveRequest =
			builder.build();

		ddmJSONStorageAdapter.save(ddmStorageAdapterSaveRequest);
	}

	@Test
	public void testUpdate() throws Exception {
		DDMJSONStorageAdapter ddmJSONStorageAdapter =
			new DDMJSONStorageAdapter();

		ddmJSONStorageAdapter.ddmContentLocalService = _ddmContentLocalService;
		ddmJSONStorageAdapter.ddmFormValuesJSONSerializer =
			_ddmFormValuesJSONSerializer;

		DDMContent ddmContent = mock(DDMContent.class);

		when(
			_ddmContentLocalService.getContent(1)
		).thenReturn(
			ddmContent
		);

		when(
			ddmContent.getPrimaryKey()
		).thenReturn(
			1L
		);

		DDMFormValues ddmFormValues = mock(DDMFormValues.class);

		when(
			_ddmFormValuesJSONSerializer.serialize(ddmFormValues)
		).thenReturn(
			"{}"
		);

		DDMStorageAdapterSaveRequest.Builder builder =
			DDMStorageAdapterSaveRequest.Builder.newBuilder(
				1, 1, ddmFormValues);

		DDMStorageAdapterSaveRequest ddmStorageAdapterSaveRequest =
			builder.withPrimaryKey(
				1
			).build();

		DDMStorageAdapterSaveResponse ddmStorageAdapterSaveResponse =
			ddmJSONStorageAdapter.save(ddmStorageAdapterSaveRequest);

		Assert.assertEquals(1L, ddmStorageAdapterSaveResponse.getPrimaryKey());

		Mockito.verify(
			ddmContent, Mockito.times(1)
		).setModifiedDate(
			Matchers.any(Date.class)
		);

		Mockito.verify(
			ddmContent, Mockito.times(1)
		).setData(
			Matchers.anyString()
		);

		Mockito.verify(
			_ddmFormValuesJSONSerializer, Mockito.times(1)
		).serialize(
			ddmFormValues
		);

		Mockito.verify(
			_ddmContentLocalService, Mockito.times(1)
		).updateContent(
			Matchers.anyLong(), Matchers.anyString(), Matchers.anyString(),
			Matchers.anyString(), Matchers.any(ServiceContext.class)
		);
	}

	@Test(expected = StorageException.class)
	public void testUpdateException() throws Exception {
		DDMJSONStorageAdapter ddmJSONStorageAdapter =
			new DDMJSONStorageAdapter();

		ddmJSONStorageAdapter.ddmContentLocalService = _ddmContentLocalService;
		ddmJSONStorageAdapter.ddmFormValuesJSONSerializer =
			_ddmFormValuesJSONSerializer;

		when(
			_ddmFormValuesJSONSerializer.serialize(
				Matchers.any(DDMFormValues.class))
		).thenThrow(
			Exception.class
		);

		DDMStorageAdapterSaveRequest.Builder builder =
			DDMStorageAdapterSaveRequest.Builder.newBuilder(
				1, 1, mock(DDMFormValues.class));

		DDMStorageAdapterSaveRequest ddmStorageAdapterSaveRequest =
			builder.withPrimaryKey(
				1
			).build();

		ddmJSONStorageAdapter.save(ddmStorageAdapterSaveRequest);
	}

	@Mock
	private DDMContentLocalService _ddmContentLocalService;

	@Mock
	private DDMFormValuesJSONDeserializer _ddmFormValuesJSONDeserializer;

	@Mock
	private DDMFormValuesJSONSerializer _ddmFormValuesJSONSerializer;

}