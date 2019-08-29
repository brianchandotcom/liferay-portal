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
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

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
		_setUpPortalUtil();
		_setUpResourceBundleUtil();

		_ddmDataProviderInstanceOutputParametersDataProvider =
			new DDMDataProviderInstanceOutputParametersDataProvider();

		_ddmDataProviderInstanceOutputParametersDataProvider.
			ddmDataProviderInstanceService = _ddmDataProviderInstanceService;
		_ddmDataProviderInstanceOutputParametersDataProvider.
			ddmDataProviderTracker = _ddmDataProviderTracker;
		_ddmDataProviderInstanceOutputParametersDataProvider.
			jsonDDMFormValuesDeserializer = _ddmFormValuesDeserializer;
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

		String countryIdOutputParameterId = StringUtil.randomString();

		outputParamaters.addNestedDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"outputParameterId", countryIdOutputParameterId));

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

		String countryNameOutputParameterId = StringUtil.randomString();

		outputParamaters.addNestedDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"outputParameterId", countryNameOutputParameterId));

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

		Optional<List<KeyValuePair>> outputParameterNamesOptional =
			ddmDataProviderResponse.getOutputOptional(
				"outputParameterNames", List.class);

		Assert.assertTrue(outputParameterNamesOptional.isPresent());

		List<KeyValuePair> keyValuePairs = new ArrayList<KeyValuePair>() {
			{
				add(new KeyValuePair(countryIdOutputParameterId, "Country Id"));
				add(
					new KeyValuePair(
						countryNameOutputParameterId, "Country Name"));
			}
		};

		Assert.assertEquals(
			keyValuePairs.toString(), keyValuePairs,
			outputParameterNamesOptional.get());
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
			ddmDataProviderResponse.getOutputOptional(
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
			ddmDataProviderResponse.getOutputOptional(
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

		Assert.assertTrue(
			ddmDataProviderResponse.hasOutput("outputParameterNames"));

		Optional<List<KeyValuePair>> outputParameterNamesOptional =
			ddmDataProviderResponse.getOutputOptional(
				"outputParameterNames", List.class);

		Assert.assertTrue(outputParameterNamesOptional.isPresent());

		List<KeyValuePair> keyValuePairs = new ArrayList<>();

		Assert.assertEquals(
			keyValuePairs.toString(), keyValuePairs,
			outputParameterNamesOptional.get());
	}

	private void _setUpLanguageUtil() {
		LanguageUtil languageUtil = new LanguageUtil();

		Language language = PowerMockito.mock(Language.class);

		languageUtil.setLanguage(language);
	}

	private void _setUpPortalUtil() {
		PortalUtil portalUtil = new PortalUtil();

		Portal portal = mock(Portal.class);

		ResourceBundle resourceBundle = mock(ResourceBundle.class);

		when(
			portal.getResourceBundle(Matchers.any(Locale.class))
		).thenReturn(
			resourceBundle
		);

		portalUtil.setPortal(portal);
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

}