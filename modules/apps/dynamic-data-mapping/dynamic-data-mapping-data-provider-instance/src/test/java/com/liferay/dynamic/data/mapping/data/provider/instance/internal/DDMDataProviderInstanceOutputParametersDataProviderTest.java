/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.data.provider.instance.internal;

import com.liferay.dynamic.data.mapping.data.provider.DDMDataProvider;
import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderRegistry;
import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderRequest;
import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderResponse;
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
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.resource.bundle.ResourceBundleLoader;
import com.liferay.portal.kernel.resource.bundle.ResourceBundleLoaderUtil;
import com.liferay.portal.kernel.util.KeyValuePair;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Leonardo Barros
 */
public class DDMDataProviderInstanceOutputParametersDataProviderTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@BeforeClass
	public static void setUpClass() {
		_setUpLanguageUtil();
		_setUpPortalUtil();
		_setUpResourceBundleUtil();
	}

	@Before
	public void setUp() {
		_ddmDataProviderInstanceOutputParametersDataProvider =
			new DDMDataProviderInstanceOutputParametersDataProvider();

		_ddmDataProviderInstanceOutputParametersDataProvider.
			ddmDataProviderInstanceService = _ddmDataProviderInstanceService;
		_ddmDataProviderInstanceOutputParametersDataProvider.
			ddmDataProviderRegistry = _ddmDataProviderRegistry;
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

		Mockito.when(
			_ddmDataProviderInstanceService.getDataProviderInstance(1)
		).thenReturn(
			_ddmDataProviderInstance
		);

		Mockito.when(
			_ddmDataProviderInstance.getType()
		).thenReturn(
			"rest"
		);

		Mockito.when(
			_ddmDataProviderRegistry.getDDMDataProvider("rest")
		).thenReturn(
			_ddmDataProvider
		);

		Mockito.when(
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

		Mockito.when(
			_ddmFormValuesDeserializer.deserialize(
				Mockito.any(DDMFormValuesDeserializerDeserializeRequest.class))
		).thenReturn(
			ddmFormValuesDeserializerDeserializeResponse
		);

		DDMDataProviderResponse ddmDataProviderResponse =
			_ddmDataProviderInstanceOutputParametersDataProvider.getData(
				ddmDataProviderRequest);

		List<KeyValuePair> keyValuePairs = ddmDataProviderResponse.getOutput(
			"outputParameterNames", List.class);

		Assert.assertNotNull(keyValuePairs);

		List<KeyValuePair> expectedKeyValuePairs =
			new ArrayList<KeyValuePair>() {
				{
					add(
						new KeyValuePair(
							countryIdOutputParameterId, "Country Id"));
					add(
						new KeyValuePair(
							countryNameOutputParameterId, "Country Name"));
				}
			};

		Assert.assertEquals(
			keyValuePairs.toString(), expectedKeyValuePairs, keyValuePairs);
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

		Mockito.when(
			_ddmDataProviderInstanceService.getDataProviderInstance(1)
		).thenThrow(
			PortalException.class
		);

		DDMDataProviderResponse ddmDataProviderResponse =
			_ddmDataProviderInstanceOutputParametersDataProvider.getData(
				ddmDataProviderRequest);

		List<KeyValuePair> keyValuePairs = ddmDataProviderResponse.getOutput(
			"outputParameterNames", List.class);

		Assert.assertNotNull(keyValuePairs);

		Assert.assertEquals(keyValuePairs.toString(), 0, keyValuePairs.size());
	}

	@Test
	public void testWithInvalidSettingsClass() throws Exception {
		DDMDataProviderRequest.Builder builder =
			DDMDataProviderRequest.Builder.newBuilder();

		DDMDataProviderRequest ddmDataProviderRequest = builder.withParameter(
			"dataProviderInstanceId", "1"
		).build();

		Mockito.when(
			_ddmDataProviderInstanceService.getDataProviderInstance(1)
		).thenReturn(
			_ddmDataProviderInstance
		);

		Mockito.when(
			_ddmDataProviderInstance.getType()
		).thenReturn(
			"rest"
		);

		Mockito.when(
			_ddmDataProviderRegistry.getDDMDataProvider("rest")
		).thenReturn(
			_ddmDataProvider
		);

		Mockito.when(
			_ddmDataProvider.getSettings()
		).thenReturn(
			(Class)Object.class
		);

		DDMDataProviderResponse ddmDataProviderResponse =
			_ddmDataProviderInstanceOutputParametersDataProvider.getData(
				ddmDataProviderRequest);

		List<KeyValuePair> keyValuePairs = ddmDataProviderResponse.getOutput(
			"outputParameterNames", List.class);

		Assert.assertNotNull(keyValuePairs);

		Assert.assertEquals(keyValuePairs.toString(), 0, keyValuePairs.size());
	}

	@Test
	public void testWithoutDataProviderInstanceIdParameter() {
		DDMDataProviderRequest.Builder builder =
			DDMDataProviderRequest.Builder.newBuilder();

		DDMDataProviderRequest ddmDataProviderRequest = builder.build();

		DDMDataProviderResponse ddmDataProviderResponse =
			_ddmDataProviderInstanceOutputParametersDataProvider.getData(
				ddmDataProviderRequest);

		Assert.assertTrue(
			ddmDataProviderResponse.hasOutput("outputParameterNames"));

		List<KeyValuePair> keyValuePairs = ddmDataProviderResponse.getOutput(
			"outputParameterNames", List.class);

		Assert.assertNotNull(keyValuePairs);

		Assert.assertEquals(
			keyValuePairs.toString(), Collections.emptyList(), keyValuePairs);
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

	private final DDMDataProvider _ddmDataProvider = Mockito.mock(
		DDMDataProvider.class);
	private final DDMDataProviderInstance _ddmDataProviderInstance =
		Mockito.mock(DDMDataProviderInstance.class);
	private DDMDataProviderInstanceOutputParametersDataProvider
		_ddmDataProviderInstanceOutputParametersDataProvider;
	private final DDMDataProviderInstanceService
		_ddmDataProviderInstanceService = Mockito.mock(
			DDMDataProviderInstanceService.class);
	private final DDMDataProviderRegistry _ddmDataProviderRegistry =
		Mockito.mock(DDMDataProviderRegistry.class);
	private final DDMFormValuesDeserializer _ddmFormValuesDeserializer =
		Mockito.mock(DDMFormValuesDeserializer.class);

}