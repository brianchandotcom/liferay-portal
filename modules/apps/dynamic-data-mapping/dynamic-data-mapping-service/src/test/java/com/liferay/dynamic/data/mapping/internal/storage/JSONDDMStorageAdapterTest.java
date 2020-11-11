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

package com.liferay.dynamic.data.mapping.internal.storage;

import com.liferay.dynamic.data.mapping.exception.StorageException;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesDeserializer;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesDeserializerDeserializeRequest;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesDeserializerDeserializeResponse;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesSerializer;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesSerializerSerializeRequest;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesSerializerSerializeResponse;
import com.liferay.dynamic.data.mapping.model.DDMContent;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.impl.DDMContentImpl;
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
import org.junit.Before;
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
public class JSONDDMStorageAdapterTest extends PowerMockito {

	@Before
	public void setUp() {
		_jsonDDMStorageAdapter = new JSONDDMStorageAdapter();

		_jsonDDMStorageAdapter.ddmContentLocalService = _ddmContentLocalService;
		_jsonDDMStorageAdapter.jsonDDMFormValuesDeserializer =
			_ddmFormValuesDeserializer;
		_jsonDDMStorageAdapter.jsonDDMFormValuesSerializer =
			_ddmFormValuesSerializer;
	}

	@Test
	public void testDelete() throws Exception {
		when(
			_ddmContentLocalService.fetchDDMContent(1)
		).thenReturn(
			new DDMContentImpl() {
				{
					setPrimaryKey(1);
				}
			}
		);

		DDMStorageAdapterDeleteResponse expectedResponse =
			_jsonDDMStorageAdapter.delete(
				DDMStorageAdapterDeleteRequest.Builder.newBuilder(
					1
				).build());

		Assert.assertTrue(expectedResponse.isDeleted());

		Mockito.verify(
			_ddmContentLocalService, Mockito.times(1)
		).deleteDDMContent(
			1
		);
	}

	@Test
	public void testGet() throws Exception {
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

		DDMFormValuesDeserializerDeserializeResponse
			ddmFormValuesDeserializerDeserializeResponse =
				DDMFormValuesDeserializerDeserializeResponse.Builder.newBuilder(
					ddmFormValues
				).build();

		when(
			_ddmFormValuesDeserializer.deserialize(
				Mockito.any(DDMFormValuesDeserializerDeserializeRequest.class))
		).thenReturn(
			ddmFormValuesDeserializerDeserializeResponse
		);

		DDMStorageAdapterGetResponse ddmStorageAdapterGetResponse =
			_jsonDDMStorageAdapter.get(
				DDMStorageAdapterGetRequest.Builder.newBuilder(
					1, ddmForm
				).build());

		Assert.assertNotNull(ddmStorageAdapterGetResponse.getDDMFormValues());

		Mockito.verify(
			_ddmContentLocalService, Mockito.times(1)
		).getContent(
			1
		);

		Mockito.verify(
			_ddmFormValuesDeserializer, Mockito.times(1)
		).deserialize(
			Mockito.any(DDMFormValuesDeserializerDeserializeRequest.class)
		);
	}

	@Test(expected = StorageException.class)
	public void testGetException() throws Exception {
		when(
			_ddmContentLocalService.getContent(1)
		).thenThrow(
			Exception.class
		);

		DDMForm ddmForm = mock(DDMForm.class);

		_jsonDDMStorageAdapter.get(
			DDMStorageAdapterGetRequest.Builder.newBuilder(
				1, ddmForm
			).build());
	}

	@Test
	public void testInsert() throws Exception {
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

		DDMFormValuesSerializerSerializeResponse
			ddmFormValuesSerializerSerializeResponse =
				DDMFormValuesSerializerSerializeResponse.Builder.newBuilder(
					"{}"
				).build();

		when(
			_ddmFormValuesSerializer.serialize(
				Mockito.any(DDMFormValuesSerializerSerializeRequest.class))
		).thenReturn(
			ddmFormValuesSerializerSerializeResponse
		);

		DDMStorageAdapterSaveResponse ddmStorageAdapterSaveResponse =
			_jsonDDMStorageAdapter.save(
				DDMStorageAdapterSaveRequest.Builder.newBuilder(
					1, 1, ddmFormValues
				).build());

		Assert.assertEquals(1L, ddmStorageAdapterSaveResponse.getPrimaryKey());

		Mockito.verify(
			_ddmFormValuesSerializer, Mockito.times(1)
		).serialize(
			Mockito.any(DDMFormValuesSerializerSerializeRequest.class)
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
		when(
			_ddmFormValuesSerializer.serialize(
				Matchers.any(DDMFormValuesSerializerSerializeRequest.class))
		).thenThrow(
			Exception.class
		);

		_jsonDDMStorageAdapter.save(
			DDMStorageAdapterSaveRequest.Builder.newBuilder(
				1, 1, mock(DDMFormValues.class)
			).build());
	}

	@Test
	public void testUpdate() throws Exception {
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

		DDMFormValuesSerializerSerializeResponse
			ddmFormValuesSerializerSerializeResponse =
				DDMFormValuesSerializerSerializeResponse.Builder.newBuilder(
					"{}"
				).build();

		when(
			_ddmFormValuesSerializer.serialize(
				Mockito.any(DDMFormValuesSerializerSerializeRequest.class))
		).thenReturn(
			ddmFormValuesSerializerSerializeResponse
		);

		DDMStorageAdapterSaveResponse ddmStorageAdapterSaveResponse =
			_jsonDDMStorageAdapter.save(
				DDMStorageAdapterSaveRequest.Builder.newBuilder(
					1, 1, ddmFormValues
				).withPrimaryKey(
					1
				).build());

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
			_ddmFormValuesSerializer, Mockito.times(1)
		).serialize(
			Mockito.any(DDMFormValuesSerializerSerializeRequest.class)
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
		when(
			_ddmFormValuesSerializer.serialize(
				Matchers.any(DDMFormValuesSerializerSerializeRequest.class))
		).thenThrow(
			Exception.class
		);

		_jsonDDMStorageAdapter.save(
			DDMStorageAdapterSaveRequest.Builder.newBuilder(
				1, 1, mock(DDMFormValues.class)
			).withPrimaryKey(
				1
			).build());
	}

	@Mock
	private DDMContentLocalService _ddmContentLocalService;

	@Mock
	private DDMFormValuesDeserializer _ddmFormValuesDeserializer;

	@Mock
	private DDMFormValuesSerializer _ddmFormValuesSerializer;

	private JSONDDMStorageAdapter _jsonDDMStorageAdapter;

}