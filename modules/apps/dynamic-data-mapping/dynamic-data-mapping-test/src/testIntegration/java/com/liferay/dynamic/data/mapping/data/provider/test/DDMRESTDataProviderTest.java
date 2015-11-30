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
import com.liferay.dynamic.data.mapping.model.DDMFormField;
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
		DDMFormField ddmFormField = new DDMFormField("test", "text");

		ddmFormField.setProperty("key", "countryId");
		ddmFormField.setProperty("password", "test");
		ddmFormField.setProperty(
			"url", "http://localhost:8080/api/jsonws/country/get-countries");
		ddmFormField.setProperty("username", "test@liferay.com");
		ddmFormField.setProperty("value", "nameCurrentValue");

		DDMDataProviderContext ddmDataProviderContext =
			new DDMDataProviderContext(ddmFormField.getProperties());

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

}