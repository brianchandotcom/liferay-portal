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

package com.liferay.dynamic.data.mapping.data.provider.internal;

import com.liferay.dynamic.data.mapping.data.provider.DDMDataProvider;
import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderTracker;
import com.liferay.dynamic.data.mapping.data.provider.internal.rest.DDMRESTDataProviderSettings;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesDeserializer;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesDeserializerDeserializeResponse;
import com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.test.util.DDMFormValuesTestUtil;
import com.liferay.dynamic.data.mapping.util.DDMFormFactory;

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
public class DDMDataProviderInstanceSettingsImplTest extends PowerMockito {

	@Before
	public void setUp() throws Exception {
		_ddmDataProviderInstanceSettingsImpl =
			new DDMDataProviderInstanceSettingsImpl();

		_ddmDataProviderInstanceSettingsImpl.ddmDataProviderTracker =
			_ddmDataProviderTracker;
		_ddmDataProviderInstanceSettingsImpl.jsonDDMFormValuesDeserializer =
			_ddmFormValuesDeserializer;
	}

	@Test
	public void testGetSettings() throws Exception {
		when(
			_ddmDataProviderTracker.getDDMDataProvider(Matchers.anyString())
		).thenReturn(
			_ddmDataProvider
		);

		when(
			_ddmDataProvider.getSettings()
		).thenReturn(
			(Class)TestDataProviderInstanceSettings.class
		);

		DDMFormValues ddmFormValues = _createDDMFormValues();

		DDMFormValuesDeserializerDeserializeResponse
			ddmFormValuesDeserializerDeserializeResponse =
				DDMFormValuesDeserializerDeserializeResponse.Builder.newBuilder(
					ddmFormValues
				).build();

		when(
			_ddmFormValuesDeserializer.deserialize(Mockito.any())
		).thenReturn(
			ddmFormValuesDeserializerDeserializeResponse
		);

		TestDataProviderInstanceSettings testDataProviderInstanceSettings =
			_ddmDataProviderInstanceSettingsImpl.getSettings(
				_ddmDataProviderInstance,
				TestDataProviderInstanceSettings.class);

		Assert.assertEquals(
			"string value", testDataProviderInstanceSettings.prop1());
		Assert.assertEquals(
			Integer.valueOf(1), testDataProviderInstanceSettings.prop2());
		Assert.assertEquals(true, testDataProviderInstanceSettings.prop3());
	}

	@Test(expected = IllegalStateException.class)
	public void testGetSettingsCatchException() throws Exception {
		when(
			_ddmDataProviderTracker.getDDMDataProvider(Matchers.anyString())
		).thenThrow(
			IllegalStateException.class
		);

		_ddmDataProviderInstanceSettingsImpl.getSettings(
			_ddmDataProviderInstance, DDMRESTDataProviderSettings.class);
	}

	private DDMFormValues _createDDMFormValues() {
		DDMForm ddmForm = DDMFormFactory.create(
			TestDataProviderInstanceSettings.class);

		DDMFormValues ddmFormValues = DDMFormValuesTestUtil.createDDMFormValues(
			ddmForm);

		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"prop1", "string value"));

		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"prop2", "1"));

		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"prop3", Boolean.TRUE.toString()));

		return ddmFormValues;
	}

	@Mock
	private DDMDataProvider _ddmDataProvider;

	@Mock
	private DDMDataProviderInstance _ddmDataProviderInstance;

	private DDMDataProviderInstanceSettingsImpl
		_ddmDataProviderInstanceSettingsImpl;

	@Mock
	private DDMDataProviderTracker _ddmDataProviderTracker;

	@Mock
	private DDMFormValuesDeserializer _ddmFormValuesDeserializer;

}