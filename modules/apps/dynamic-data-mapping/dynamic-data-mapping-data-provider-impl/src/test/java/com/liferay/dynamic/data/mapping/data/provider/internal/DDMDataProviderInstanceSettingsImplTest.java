/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.data.provider.internal;

import com.liferay.dynamic.data.mapping.data.provider.DDMDataProvider;
import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderRegistry;
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

		_ddmDataProviderInstanceSettingsImpl.ddmDataProviderRegistry =
			_ddmDataProviderRegistry;
		_ddmDataProviderInstanceSettingsImpl.jsonDDMFormValuesDeserializer =
			_ddmFormValuesDeserializer;
	}

	@Test
	public void testGetSettings() {
		Mockito.when(
			_ddmDataProviderRegistry.getDDMDataProvider(
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
			_ddmDataProviderRegistry.getDDMDataProvider(
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
	private final DDMDataProviderRegistry _ddmDataProviderRegistry =
		Mockito.mock(DDMDataProviderRegistry.class);
	private final DDMFormValuesDeserializer _ddmFormValuesDeserializer =
		Mockito.mock(DDMFormValuesDeserializer.class);

}