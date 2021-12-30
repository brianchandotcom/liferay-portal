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

package com.liferay.dynamic.data.mapping.form.builder.internal.servlet;

import com.liferay.dynamic.data.mapping.annotations.DDMForm;
import com.liferay.dynamic.data.mapping.data.provider.DDMDataProvider;
import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderParameterSettings;
import com.liferay.dynamic.data.mapping.internal.io.DDMFormValuesJSONDeserializer;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesDeserializer;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesDeserializerDeserializeRequest;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesDeserializerDeserializeResponse;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.util.DDMFormFactory;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.ProxyFactory;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.util.PortalImpl;

import java.io.InputStream;

import java.util.Locale;
import java.util.ResourceBundle;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Matchers;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import org.skyscreamer.jsonassert.JSONAssert;

/**
 * @author Rafael Praxedes
 */
@PrepareForTest(ResourceBundleUtil.class)
@RunWith(PowerMockRunner.class)
public class DDMDataProviderInstanceParameterSettingsServletTest
	extends PowerMockito {

	@Before
	public void setUp() throws Exception {
		_setUpDDMDataProvider();
		_setUpDDMFormValuesJSONDeserializer();
		_setUpGetDataProviderParametersSettingsMVCResourceCommand();
		_setUpJSONFactoryUtil();
		_setUpLanguageUtil();
		_setUpPortalUtil();
		_setUpResourceBundleUtil();
	}

	@Test
	public void testCreateParametersJSONObject() throws Exception {
		JSONObject parametersJSONObject =
			_ddmDataProviderInstanceParameterSettingsServlet.
				createParametersJSONObject(
					_ddmDataProvider,
					getDataProviderFormValues(
						"form-values-data-provider-settings-1.json"));

		String expectedValue = _read(
			"data-provider-input-output-parameters-1.json");

		JSONAssert.assertEquals(
			expectedValue, parametersJSONObject.toString(), false);
	}

	@Test
	public void testCreateParametersJSONObjectWithoutLabels() throws Exception {
		JSONObject parametersJSONObject =
			_ddmDataProviderInstanceParameterSettingsServlet.
				createParametersJSONObject(
					_ddmDataProvider,
					getDataProviderFormValues(
						"form-values-data-provider-settings-2.json"));

		String expectedValue = _read(
			"data-provider-input-output-parameters-2.json");

		JSONAssert.assertEquals(
			expectedValue, parametersJSONObject.toString(), false);
	}

	protected DDMFormValues deserialize(
		String content,
		com.liferay.dynamic.data.mapping.model.DDMForm ddmForm) {

		DDMFormValuesDeserializerDeserializeRequest.Builder builder =
			DDMFormValuesDeserializerDeserializeRequest.Builder.newBuilder(
				content, ddmForm);

		DDMFormValuesDeserializerDeserializeResponse
			ddmFormValuesDeserializerDeserializeResponse =
				_ddmFormValuesJSONDeserializer.deserialize(builder.build());

		return ddmFormValuesDeserializerDeserializeResponse.getDDMFormValues();
	}

	protected DDMFormValues getDataProviderFormValues(String file)
		throws Exception {

		com.liferay.dynamic.data.mapping.model.DDMForm ddmForm =
			DDMFormFactory.create(DDMDataProviderSettings.class);

		String serializedDDMFormValues = _read(file);

		return deserialize(serializedDDMFormValues, ddmForm);
	}

	private String _read(String fileName) throws Exception {
		Class<?> clazz = getClass();

		InputStream inputStream = clazz.getResourceAsStream(
			"dependencies/" + fileName);

		return StringUtil.read(inputStream);
	}

	private void _setUpDDMDataProvider() {
		_ddmDataProvider = PowerMockito.mock(DDMDataProvider.class);

		PowerMockito.when(
			_ddmDataProvider.getSettings()
		).then(
			new Answer<Class<?>>() {

				@Override
				public Class<?> answer(InvocationOnMock invocationOnMock)
					throws Throwable {

					return DDMDataProviderSettings.class;
				}

			}
		);
	}

	private void _setUpDDMFormValuesJSONDeserializer() throws Exception {
		PowerMockito.field(
			DDMFormValuesJSONDeserializer.class, "_jsonFactory"
		).set(
			_ddmFormValuesJSONDeserializer, _jsonFactory
		);

		PowerMockito.field(
			DDMFormValuesJSONDeserializer.class, "_serviceTrackerMap"
		).set(
			_ddmFormValuesJSONDeserializer,
			ProxyFactory.newDummyInstance(ServiceTrackerMap.class)
		);
	}

	private void _setUpGetDataProviderParametersSettingsMVCResourceCommand()
		throws Exception {

		_ddmDataProviderInstanceParameterSettingsServlet =
			new DDMDataProviderInstanceParameterSettingsServlet();

		PowerMockito.field(
			_ddmDataProviderInstanceParameterSettingsServlet.getClass(),
			"_jsonFactory"
		).set(
			_ddmDataProviderInstanceParameterSettingsServlet, _jsonFactory
		);

		PowerMockito.field(
			_ddmDataProviderInstanceParameterSettingsServlet.getClass(),
			"_jsonDDMFormValuesDeserializer"
		).set(
			_ddmDataProviderInstanceParameterSettingsServlet,
			_ddmFormValuesJSONDeserializer
		);
	}

	private void _setUpJSONFactoryUtil() {
		JSONFactoryUtil jsonFactoryUtil = new JSONFactoryUtil();

		jsonFactoryUtil.setJSONFactory(_jsonFactory);
	}

	private void _setUpLanguageUtil() {
		LanguageUtil languageUtil = new LanguageUtil();

		languageUtil.setLanguage(PowerMockito.mock(Language.class));
	}

	private void _setUpPortalClassLoaderUtil() {
		PortalClassLoaderUtil.setClassLoader(PortalImpl.class.getClassLoader());
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

	private DDMDataProvider _ddmDataProvider;
	private DDMDataProviderInstanceParameterSettingsServlet
		_ddmDataProviderInstanceParameterSettingsServlet;
	private final DDMFormValuesDeserializer _ddmFormValuesJSONDeserializer =
		new DDMFormValuesJSONDeserializer();
	private final JSONFactory _jsonFactory = new JSONFactoryImpl();

	@DDMForm
	private interface DDMDataProviderSettings
		extends DDMDataProviderParameterSettings {
	}

}