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

package com.liferay.dynamic.data.mapping.data.provider.instance;

import com.liferay.dynamic.data.mapping.data.provider.DDMDataProvider;
import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderRequest;
import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderResponse;
import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderTracker;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesDeserializer;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesDeserializerDeserializeRequest;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesDeserializerDeserializeResponse;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesDeserializerTracker;
import com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.service.DDMDataProviderInstanceService;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.test.util.DDMFormValuesTestUtil;
import com.liferay.dynamic.data.mapping.util.DDMFormFactory;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.KeyValuePair;
import com.liferay.portal.kernel.util.ResourceBundleUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Matchers;
import org.mockito.Mock;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author Leonardo Barros
 */
@PrepareForTest(ResourceBundleUtil.class)
@RunWith(PowerMockRunner.class)
public class DDMDataProviderInstanceOutputParametersDataProviderTest
	extends PowerMockito {

	@Before
	public void setUp() throws Exception {
		_setUpLanguageUtil();
		_setUpResourceBundleUtil();

		_ddmDataProviderInstanceOutputParametersDataProvider =
			new DDMDataProviderInstanceOutputParametersDataProvider();

		_ddmDataProviderInstanceOutputParametersDataProvider.
			ddmDataProviderInstanceService = _ddmDataProviderInstanceService;
		_ddmDataProviderInstanceOutputParametersDataProvider.
			ddmDataProviderTracker = _ddmDataProviderTracker;
		_ddmDataProviderInstanceOutputParametersDataProvider.
			ddmFormValuesDeserializerTracker =
				_ddmFormValuesDeserializerTracker;

		when(
			_ddmFormValuesDeserializerTracker.getDDMFormValuesDeserializer(
				Matchers.anyString())
		).thenReturn(
			_ddmFormValuesDeserializer
		);
	}

	@Test
	public void testGetData() throws Exception {
		DDMDataProviderRequest.Builder builder =
			DDMDataProviderRequest.Builder.newBuilder();

		DDMDataProviderRequest ddmDataProviderRequest = builder.withParameter(
			"dataProviderInstanceId", "1"
		).build();

		when(
			_ddmDataProviderInstanceService.getDataProviderInstance(1)
		).thenReturn(
			_ddmDataProviderInstance
		);

		when(
			_ddmDataProviderInstance.getType()
		).thenReturn(
			"rest"
		);

		when(
			_ddmDataProviderTracker.getDDMDataProvider("rest")
		).thenReturn(
			_ddmDataProvider
		);

		when(
			_ddmDataProvider.getSettings()
		).thenReturn(
			(Class)TestDDMDataProviderParameterSettings.class
		);

		DDMForm ddmForm = DDMFormFactory.create(_ddmDataProvider.getSettings());

		DDMFormValues ddmFormValues = DDMFormValuesTestUtil.createDDMFormValues(
			ddmForm);

		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"url", "http://someservice.com/countries/api/"));

		DDMFormFieldValue outputParamaters =
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"outputParameters", StringPool.BLANK);

		ddmFormValues.addDDMFormFieldValue(outputParamaters);

		outputParamaters.addNestedDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"outputParameterName", "Country Id"));

		outputParamaters.addNestedDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"outputParameterPath", "countryId"));

		outputParamaters.addNestedDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"outputParameterType", "[\"number\"]"));

		outputParamaters =
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"outputParameters", StringPool.BLANK);

		ddmFormValues.addDDMFormFieldValue(outputParamaters);

		outputParamaters.addNestedDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"outputParameterName", "Country Name"));

		outputParamaters.addNestedDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"outputParameterPath", "countryName"));

		outputParamaters.addNestedDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"outputParameterType", "[\"string\"]"));

		DDMFormValuesDeserializerDeserializeResponse
			ddmFormValuesDeserializerDeserializeResponse =
				DDMFormValuesDeserializerDeserializeResponse.Builder.newBuilder(
					ddmFormValues
				).build();

		when(
			_ddmFormValuesDeserializer.deserialize(
				Matchers.any(DDMFormValuesDeserializerDeserializeRequest.class))
		).thenReturn(
			ddmFormValuesDeserializerDeserializeResponse
		);

		DDMDataProviderResponse ddmDataProviderResponse =
			_ddmDataProviderInstanceOutputParametersDataProvider.getData(
				ddmDataProviderRequest);

		Optional<List<KeyValuePair>> outputParameterNames =
			ddmDataProviderResponse.getOutput(
				"outputParameterNames", List.class);

		Assert.assertTrue(outputParameterNames.isPresent());

		List<KeyValuePair> keyValuePairs = new ArrayList() {
			{
				add(new KeyValuePair("Country Id", "Country Id"));
				add(new KeyValuePair("Country Name", "Country Name"));
			}
		};

		Assert.assertEquals(
			keyValuePairs.toString(), keyValuePairs,
			outputParameterNames.get());
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testGetSettings() {
		_ddmDataProviderInstanceOutputParametersDataProvider.getSettings();
	}

	@Test
	public void testThrowException() throws Exception {
		DDMDataProviderRequest.Builder builder =
			DDMDataProviderRequest.Builder.newBuilder();

		DDMDataProviderRequest ddmDataProviderRequest = builder.withParameter(
			"dataProviderInstanceId", "1"
		).build();

		when(
			_ddmDataProviderInstanceService.getDataProviderInstance(1)
		).thenThrow(
			Exception.class
		);

		DDMDataProviderResponse ddmDataProviderResponse =
			_ddmDataProviderInstanceOutputParametersDataProvider.getData(
				ddmDataProviderRequest);

		Optional<List<KeyValuePair>> optional =
			ddmDataProviderResponse.getOutput(
				"outputParameterNames", List.class);

		Assert.assertTrue(optional.isPresent());

		List<KeyValuePair> keyValuePairs = optional.get();

		Assert.assertEquals(keyValuePairs.toString(), 0, keyValuePairs.size());
	}

	@Test
	public void testWithInvalidSettingsClass() throws Exception {
		DDMDataProviderRequest.Builder builder =
			DDMDataProviderRequest.Builder.newBuilder();

		DDMDataProviderRequest ddmDataProviderRequest = builder.withParameter(
			"dataProviderInstanceId", "1"
		).build();

		when(
			_ddmDataProviderInstanceService.getDataProviderInstance(1)
		).thenReturn(
			_ddmDataProviderInstance
		);

		when(
			_ddmDataProviderInstance.getType()
		).thenReturn(
			"rest"
		);

		when(
			_ddmDataProviderTracker.getDDMDataProvider("rest")
		).thenReturn(
			_ddmDataProvider
		);

		when(
			_ddmDataProvider.getSettings()
		).thenReturn(
			(Class)Object.class
		);

		DDMDataProviderResponse ddmDataProviderResponse =
			_ddmDataProviderInstanceOutputParametersDataProvider.getData(
				ddmDataProviderRequest);

		Optional<List<KeyValuePair>> outputParameterNamesOptional =
			ddmDataProviderResponse.getOutput(
				"outputParameterNames", List.class);

		Assert.assertTrue(outputParameterNamesOptional.isPresent());

		List<KeyValuePair> outputParameterNames =
			outputParameterNamesOptional.get();

		Assert.assertEquals(
			outputParameterNames.toString(), 0, outputParameterNames.size());
	}

	@Test
	public void testWithoutDataProviderInstanceIdParameter() throws Exception {
		DDMDataProviderRequest.Builder builder =
			DDMDataProviderRequest.Builder.newBuilder();

		DDMDataProviderRequest ddmDataProviderRequest = builder.build();

		DDMDataProviderResponse ddmDataProviderResponse =
			_ddmDataProviderInstanceOutputParametersDataProvider.getData(
				ddmDataProviderRequest);

		Assert.assertFalse(
			ddmDataProviderResponse.hasOutput("outputParameterNames"));
	}

	private void _setUpLanguageUtil() {
		LanguageUtil languageUtil = new LanguageUtil();

		Language language = PowerMockito.mock(Language.class);

		languageUtil.setLanguage(language);
	}

	private void _setUpResourceBundleUtil() {
		PowerMockito.mockStatic(ResourceBundleUtil.class);

		PowerMockito.when(
			ResourceBundleUtil.getBundle(
				Matchers.anyString(), Matchers.any(Locale.class),
				Matchers.any(ClassLoader.class))
		).thenReturn(
			ResourceBundleUtil.EMPTY_RESOURCE_BUNDLE
		);
	}

	@Mock
	private DDMDataProvider _ddmDataProvider;

	@Mock
	private DDMDataProviderInstance _ddmDataProviderInstance;

	private DDMDataProviderInstanceOutputParametersDataProvider
		_ddmDataProviderInstanceOutputParametersDataProvider;

	@Mock
	private DDMDataProviderInstanceService _ddmDataProviderInstanceService;

	@Mock
	private DDMDataProviderTracker _ddmDataProviderTracker;

	@Mock
	private DDMFormValuesDeserializer _ddmFormValuesDeserializer;

	@Mock
	private DDMFormValuesDeserializerTracker _ddmFormValuesDeserializerTracker;

}