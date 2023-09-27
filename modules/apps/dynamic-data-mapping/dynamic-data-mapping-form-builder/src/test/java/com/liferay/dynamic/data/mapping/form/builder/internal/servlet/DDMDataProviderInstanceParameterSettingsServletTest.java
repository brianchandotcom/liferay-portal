/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
import com.liferay.portal.kernel.resource.bundle.ResourceBundleLoader;
import com.liferay.portal.kernel.resource.bundle.ResourceBundleLoaderUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.ProxyFactory;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.io.InputStream;

import java.util.Locale;
import java.util.ResourceBundle;

import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;
import org.mockito.stubbing.Answer;

import org.skyscreamer.jsonassert.JSONAssert;

/**
 * @author Rafael Praxedes
 */
public class DDMDataProviderInstanceParameterSettingsServletTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@BeforeClass
	public static void setUpClass() {
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

	private static void _setUpDDMDataProvider() {
		_ddmDataProvider = Mockito.mock(DDMDataProvider.class);

		Mockito.when(
			_ddmDataProvider.getSettings()
		).then(
			(Answer<Class<?>>)invocationOnMock -> DDMDataProviderSettings.class
		);
	}

	private static void _setUpDDMFormValuesJSONDeserializer() {
		ReflectionTestUtil.setFieldValue(
			_ddmFormValuesJSONDeserializer, "_jsonFactory", _jsonFactory);
		ReflectionTestUtil.setFieldValue(
			_ddmFormValuesJSONDeserializer, "_serviceTrackerMap",
			ProxyFactory.newDummyInstance(ServiceTrackerMap.class));
	}

	private static void _setUpGetDataProviderParametersSettingsMVCResourceCommand() {
		_ddmDataProviderInstanceParameterSettingsServlet =
			new DDMDataProviderInstanceParameterSettingsServlet();

		ReflectionTestUtil.setFieldValue(
			_ddmDataProviderInstanceParameterSettingsServlet, "_jsonFactory",
			_jsonFactory);
		ReflectionTestUtil.setFieldValue(
			_ddmDataProviderInstanceParameterSettingsServlet,
			"_jsonDDMFormValuesDeserializer", _ddmFormValuesJSONDeserializer);
	}

	private static void _setUpJSONFactoryUtil() {
		JSONFactoryUtil jsonFactoryUtil = new JSONFactoryUtil();

		jsonFactoryUtil.setJSONFactory(_jsonFactory);
	}

	private static void _setUpLanguageUtil() {
		LanguageUtil languageUtil = new LanguageUtil();

		languageUtil.setLanguage(Mockito.mock(Language.class));
	}

	private static void _setUpPortalUtil() {
		PortalUtil portalUtil = new PortalUtil();

		Portal portal = Mockito.mock(Portal.class);

		ResourceBundle resourceBundle = Mockito.mock(ResourceBundle.class);

		Mockito.when(
			portal.getResourceBundle(Mockito.any(Locale.class))
		).thenReturn(
			resourceBundle
		);

		portalUtil.setPortal(portal);
	}

	private static void _setUpResourceBundleUtil() {
		ResourceBundleLoader resourceBundleLoader = Mockito.mock(
			ResourceBundleLoader.class);

		ResourceBundleLoaderUtil.setPortalResourceBundleLoader(
			resourceBundleLoader);

		Mockito.when(
			resourceBundleLoader.loadResourceBundle(Mockito.any(Locale.class))
		).thenReturn(
			ResourceBundleUtil.EMPTY_RESOURCE_BUNDLE
		);
	}

	private String _read(String fileName) throws Exception {
		Class<?> clazz = getClass();

		InputStream inputStream = clazz.getResourceAsStream(
			"dependencies/" + fileName);

		return StringUtil.read(inputStream);
	}

	private static DDMDataProvider _ddmDataProvider;
	private static DDMDataProviderInstanceParameterSettingsServlet
		_ddmDataProviderInstanceParameterSettingsServlet;
	private static final DDMFormValuesDeserializer
		_ddmFormValuesJSONDeserializer = new DDMFormValuesJSONDeserializer();
	private static final JSONFactory _jsonFactory = new JSONFactoryImpl();

	@DDMForm
	private interface DDMDataProviderSettings
		extends DDMDataProviderParameterSettings {
	}

}