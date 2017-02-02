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

package com.liferay.dynamic.data.mapping.data.provider.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.dynamic.data.mapping.data.provider.DDMDataProvider;
import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderContext;
import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderRequest;
import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderResponse;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.test.util.DDMFormValuesTestUtil;
import com.liferay.dynamic.data.mapping.util.DDMFormFactory;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Marcellus Tavares
 */
@RunWith(Arquillian.class)
public class DDMRESTDataProviderTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		Registry registry = RegistryUtil.getRegistry();

		DDMDataProvider[] ddmDataProviders = registry.getServices(
			"com.liferay.dynamic.data.mapping.data.provider.DDMDataProvider",
			"(ddm.data.provider.type=rest)");

		_ddmDataProvider = ddmDataProviders[0];
	}

	@Test
	public void testGetCountries() throws Exception {
		Class<?> ddmDataProviderSettings = _ddmDataProvider.getSettings();

		com.liferay.dynamic.data.mapping.model.DDMForm ddmForm =
			DDMFormFactory.create(ddmDataProviderSettings);

		DDMFormValues ddmFormValues = DDMFormValuesTestUtil.createDDMFormValues(
			ddmForm);

		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"cacheable", Boolean.FALSE.toString()));
		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"filterable", Boolean.FALSE.toString()));
		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"filterParameterName", StringPool.BLANK));
		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"password", "test"));
		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"url",
				"http://localhost:8080/api/jsonws/country/get-countries"));
		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"username", "test@liferay.com"));

		DDMFormFieldValue outputParameters =
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"outputParameters", StringPool.BLANK);

		ddmFormValues.addDDMFormFieldValue(outputParameters);

		outputParameters.addNestedDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"outputParameterName", "output"));

		outputParameters.addNestedDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"outputParameterPath", "nameCurrentValue;countryId"));

		outputParameters.addNestedDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"outputParameterType", "[\"list\"]"));

		DDMDataProviderContext ddmDataProviderContext =
			new DDMDataProviderContext(ddmFormValues);

		DDMDataProviderRequest ddmDataProviderRequest =
			new DDMDataProviderRequest(ddmDataProviderContext);

		DDMDataProviderResponse ddmDtaProviderResponse =
			_ddmDataProvider.getData(ddmDataProviderRequest);

		List<Map<Object, Object>> data = ddmDtaProviderResponse.getData();

		Assert.assertFalse(data.isEmpty());

		List<Map<Object, Object>> expectedData = createExpectedData();

		Assert.assertTrue(data.containsAll(expectedData));
	}

	@Test
	public void testGetCountryByName() throws Exception {
		Class<?> ddmDataProviderSettings = _ddmDataProvider.getSettings();

		com.liferay.dynamic.data.mapping.model.DDMForm ddmForm =
			DDMFormFactory.create(ddmDataProviderSettings);

		String url =
			"http://localhost:8080/api/jsonws/country/get-country-by-name";

		DDMFormValues ddmFormValues = DDMFormValuesTestUtil.createDDMFormValues(
			ddmForm);

		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"cacheable", Boolean.FALSE.toString()));
		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"filterable", Boolean.TRUE.toString()));
		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"filterParameterName", "name"));
		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"password", "test"));
		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"url", url));
		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"username", "test@liferay.com"));

		DDMFormFieldValue outputParameters =
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"outputParameters", StringPool.BLANK);

		ddmFormValues.addDDMFormFieldValue(outputParameters);

		outputParameters.addNestedDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"outputParameterName", "output"));

		outputParameters.addNestedDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"outputParameterPath", "nameCurrentValue;countryId"));

		outputParameters.addNestedDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"outputParameterType", "[\"list\"]"));

		DDMDataProviderContext ddmDataProviderContext =
			new DDMDataProviderContext(ddmFormValues);

		ddmDataProviderContext.addParameter("filterParameterValue", "brazil");

		DDMDataProviderRequest ddmDataProviderRequest =
			new DDMDataProviderRequest(ddmDataProviderContext);

		DDMDataProviderResponse ddmDataProviderResponse =
			_ddmDataProvider.getData(ddmDataProviderRequest);

		Assert.assertNotNull(ddmDataProviderResponse);

		List<Map<Object, Object>> data = ddmDataProviderResponse.getData();

		Assert.assertEquals(data.toString(), 1, data.size());

		Map<Object, Object> map = new HashMap<>();

		map.put("countryId", "48");
		map.put("nameCurrentValue", "Brazil");

		Assert.assertTrue(data.contains(map));
	}

	protected List<Map<Object, Object>> createExpectedData() {
		List<Map<Object, Object>> expectedData = new ArrayList<>();

		Map<Object, Object> map = new HashMap<>();

		map.put("countryId", "3");
		map.put("nameCurrentValue", "France");

		expectedData.add(map);

		map = new HashMap<>();

		map.put("countryId", "15");
		map.put("nameCurrentValue", "Spain");

		expectedData.add(map);

		map = new HashMap<>();

		map.put("countryId", "19");
		map.put("nameCurrentValue", "United States");

		expectedData.add(map);

		map = new HashMap<>();

		map.put("countryId", "48");
		map.put("nameCurrentValue", "Brazil");

		expectedData.add(map);

		return expectedData;
	}

	private DDMDataProvider _ddmDataProvider;

}