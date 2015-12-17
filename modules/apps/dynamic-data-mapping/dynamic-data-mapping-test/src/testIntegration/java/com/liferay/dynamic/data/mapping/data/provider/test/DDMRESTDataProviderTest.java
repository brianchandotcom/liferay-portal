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
import com.liferay.dynamic.data.mapping.annotations.DDMForm;
import com.liferay.dynamic.data.mapping.annotations.DDMFormField;
import com.liferay.dynamic.data.mapping.data.provider.DDMDataProvider;
import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderContext;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.test.util.DDMFormValuesTestUtil;
import com.liferay.dynamic.data.mapping.util.DDMFormFactory;
import com.liferay.portal.kernel.util.KeyValuePair;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;

import java.util.ArrayList;
import java.util.List;

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
		com.liferay.dynamic.data.mapping.model.DDMForm ddmForm =
			DDMFormFactory.create(DDMRESTDataProviderSettings.class);

		DDMFormValues ddmFormValues = DDMFormValuesTestUtil.createDDMFormValues(
			ddmForm);

		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"cacheable", Boolean.FALSE.toString()));
		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"key", "countryId"));
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
		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"value", "nameCurrentValue"));

		DDMDataProviderContext ddmDataProviderContext =
			new DDMDataProviderContext(ddmFormValues);

		List<KeyValuePair> actualKeyValuePairs = _ddmDataProvider.getData(
			ddmDataProviderContext);

		Assert.assertNotNull(actualKeyValuePairs);

		List<KeyValuePair> expectedKeyValuePairs =
			createExpectedKeyValuePairs();

		for (KeyValuePair expectedKeyValuePair : expectedKeyValuePairs) {
			Assert.assertTrue(
				actualKeyValuePairs.contains(expectedKeyValuePair));
		}
	}

	protected List<KeyValuePair> createExpectedKeyValuePairs() {
		List<KeyValuePair> expectedKeyValuePairs = new ArrayList<>();

		expectedKeyValuePairs.add(new KeyValuePair("3", "France"));
		expectedKeyValuePairs.add(new KeyValuePair("15", "Spain"));
		expectedKeyValuePairs.add(new KeyValuePair("19", "United States"));
		expectedKeyValuePairs.add(new KeyValuePair("48", "Brazil"));

		return expectedKeyValuePairs;
	}

	private DDMDataProvider _ddmDataProvider;

	@DDMForm
	private interface DDMRESTDataProviderSettings {

		@DDMFormField
		public boolean cacheable();

		@DDMFormField
		public String key();

		@DDMFormField
		public String password();

		@DDMFormField
		public String url();

		@DDMFormField
		public String username();

		@DDMFormField
		public String value();

	}

}