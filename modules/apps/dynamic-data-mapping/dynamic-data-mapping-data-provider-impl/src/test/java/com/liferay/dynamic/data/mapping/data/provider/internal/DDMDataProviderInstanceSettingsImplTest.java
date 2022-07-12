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
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Leonardo Barros
 */
public class DDMDataProviderInstanceSettingsImplTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		_ddmDataProviderInstanceSettingsImpl =
			new DDMDataProviderInstanceSettingsImpl();

		_ddmDataProviderInstanceSettingsImpl.ddmDataProviderTracker =
			_ddmDataProviderTracker;
		_ddmDataProviderInstanceSettingsImpl.jsonDDMFormValuesDeserializer =
			_ddmFormValuesDeserializer;
	}

	@Test
	public void testGetSettings() {
		Mockito.when(
			_ddmDataProviderTracker.getDDMDataProvider(
				Mockito.nullable(String.class))
		).thenReturn(
			_ddmDataProvider
		);

		Mockito.when(
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

		Mockito.when(
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
		Assert.assertTrue(testDataProviderInstanceSettings.prop3());
	}

	@Test(expected = IllegalStateException.class)
	public void testGetSettingsCatchException() {
		Mockito.when(
			_ddmDataProviderTracker.getDDMDataProvider(
				Mockito.nullable(String.class))
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

	private final DDMDataProvider _ddmDataProvider = Mockito.mock(
		DDMDataProvider.class);
	private final DDMDataProviderInstance _ddmDataProviderInstance =
		Mockito.mock(DDMDataProviderInstance.class);
	private DDMDataProviderInstanceSettingsImpl
		_ddmDataProviderInstanceSettingsImpl;
	private final DDMDataProviderTracker _ddmDataProviderTracker = Mockito.mock(
		DDMDataProviderTracker.class);
	private final DDMFormValuesDeserializer _ddmFormValuesDeserializer =
		Mockito.mock(DDMFormValuesDeserializer.class);

}