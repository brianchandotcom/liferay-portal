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

package com.liferay.portal.kernel.settings;

import com.liferay.portal.kernel.test.ReflectionTestUtil;

import java.util.Map;
import java.util.Properties;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.powermock.api.mockito.PowerMockito;

/**
 * @author Iván Zaera
 */
public class PropertiesSettingsTest extends PowerMockito {

	@Before
	public void setUp() {
		Properties properties = new Properties();

		properties.put(_SINGLE_KEY, _SINGLE_VALUE);
		properties.put(_MULTIPLE_KEY, _MULTIPLE_VALUES);

		_mockLocationVariableResolver = mock(LocationVariableResolver.class);

		_propertiesSettings = new PropertiesSettings(
			_mockLocationVariableResolver, properties);

		_properties = ReflectionTestUtil.getFieldValue(
			_propertiesSettings, "_properties");
	}

	@Test
	public void testGetValuesWithDefaultValue() {
		String[] defaultValue = {"default0", "default1"};

		Assert.assertArrayEquals(
			defaultValue,
			_propertiesSettings.getValues("missingKey", defaultValue));
	}

	@Test
	public void testGetValuesWithExistingKey() {
		Assert.assertArrayEquals(
			new String[] {"value0", "value1", "value2"},
			_propertiesSettings.getValues(_MULTIPLE_KEY, null));
	}

	@Test
	public void testGetValuesWithMissingKey() {
		Assert.assertArrayEquals(
			null, _propertiesSettings.getValues("missingKey", null));
	}

	@Test
	public void testGetValuesWithResourceValue() {
		_properties.put(_MULTIPLE_KEY, _RESOURCE_MULTIPLE_VALUES);

		when(
			_mockLocationVariableResolver.isLocationVariable(
				_RESOURCE_MULTIPLE_VALUES)
		).thenReturn(
			true
		);

		final String expectedValue =
			"resourceValue0,resourceValue1,resourceValue2";

		when(
			_mockLocationVariableResolver.resolve(_RESOURCE_MULTIPLE_VALUES)
		).thenReturn(
			expectedValue
		);

		Assert.assertArrayEquals(
			expectedValue.split(","),
			_propertiesSettings.getValues(_MULTIPLE_KEY, null));
	}

	@Test
	public void testGetValueWithDefaultValue() {
		Assert.assertEquals(
			"default", _propertiesSettings.getValue("missingKey", "default"));
	}

	@Test
	public void testGetValueWithExistingKey() {
		Assert.assertEquals(
			_SINGLE_VALUE, _propertiesSettings.getValue(_SINGLE_KEY, null));
	}

	@Test
	public void testGetValueWithMissingKey() {
		Assert.assertEquals(
			null, _propertiesSettings.getValue("missingKey", null));
	}

	@Test
	public void testGetValueWithResourceValue() {
		_properties.put(_SINGLE_KEY, _RESOURCE_SINGLE_VALUE);

		when(
			_mockLocationVariableResolver.isLocationVariable(
				_RESOURCE_SINGLE_VALUE)
		).thenReturn(
			true
		);

		final String expectedValue = "resourceValue";

		when(
			_mockLocationVariableResolver.resolve(_RESOURCE_SINGLE_VALUE)
		).thenReturn(
			expectedValue
		);

		Assert.assertEquals(
			expectedValue, _propertiesSettings.getValue(_SINGLE_KEY, null));
	}

	private static final String _MULTIPLE_KEY = "multipleKey";

	private static final String _MULTIPLE_VALUES = "value0,value1,value2";

	private static final String _RESOURCE_MULTIPLE_VALUES =
		"${resource:multiple.txt}";

	private static final String _RESOURCE_SINGLE_VALUE =
		"${resource:single.txt}";

	private static final String _SINGLE_KEY = "key";

	private static final String _SINGLE_VALUE = "value";

	private LocationVariableResolver _mockLocationVariableResolver;
	private Map<String, String> _properties;
	private PropertiesSettings _propertiesSettings;

}