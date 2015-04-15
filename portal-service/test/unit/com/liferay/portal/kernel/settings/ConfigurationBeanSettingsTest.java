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

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.powermock.api.mockito.PowerMockito;

/**
 * @author Iván Zaera
 */
public class ConfigurationBeanSettingsTest extends PowerMockito {

	@Before
	public void setUp() {
		_configurationBean = new ConfigurationBean();

		_mockLocationVariableResolver = mock(LocationVariableResolver.class);

		_configurationBeanSettings = new ConfigurationBeanSettings(
			_mockLocationVariableResolver, _configurationBean, null);
	}

	@Test
	public void testGetValuesWithExistingKey() {
		Assert.assertArrayEquals(
			_configurationBean.stringArrayValue(),
			_configurationBeanSettings.getValues(
				"stringArrayValue", new String[] {"defaultValue"}));
	}

	@Test
	public void testGetValuesWithMissingKey() {
		String[] defaultValue = {"defaultValue"};

		Assert.assertArrayEquals(
			defaultValue,
			_configurationBeanSettings.getValues("missingKey", defaultValue));
	}

	@Test
	public void testGetValueWithExistingBooleanValue() {
		Assert.assertEquals(
			String.valueOf(_configurationBean.booleanValue()),
			_configurationBeanSettings.getValue(
				"booleanValue", "defaultValue"));
	}

	@Test
	public void testGetValueWithExistingLocalizedValuesMapValue() {
		LocalizedValuesMap localizedValuesMap =
			_configurationBean.localizedValuesMap();

		Assert.assertEquals(
			localizedValuesMap.getDefaultValue(),
			_configurationBeanSettings.getValue("localizedValuesMap", null));
	}

	@Test
	public void testGetValueWithExistingStringValue() {
		Assert.assertEquals(
			_configurationBean.stringValue(),
			_configurationBeanSettings.getValue("stringValue", "defaultValue"));
	}

	@Test
	public void testGetValueWithLocationVariable() {
		when(
			_mockLocationVariableResolver.isLocationVariable(
				_configurationBean.locationVariableValue())
		).thenReturn(
			true
		);

		String expectedValue = "Once upon a time...";

		when(
			_mockLocationVariableResolver.resolve(
				_configurationBean.locationVariableValue())
		).thenReturn(
			expectedValue
		);

		Assert.assertEquals(
			expectedValue,
			_configurationBeanSettings.getValue(
				"locationVariableValue", "defaultValue"));
	}

	@Test
	public void testGetValueWithMissingKey() {
		Assert.assertEquals(
			"defaultValue",
			_configurationBeanSettings.getValue("missingKey", "defaultValue"));
	}

	@Test
	public void testGetValueWithNullConfigurationBean() {
		_configurationBeanSettings = new ConfigurationBeanSettings(
			null, null, null);

		Assert.assertEquals(
			"defaultValue",
			_configurationBeanSettings.getValue("anyKey", "defaultValue"));
	}

	private ConfigurationBean _configurationBean;
	private ConfigurationBeanSettings _configurationBeanSettings;
	private LocationVariableResolver _mockLocationVariableResolver;

	private static class ConfigurationBean {

		public boolean booleanValue() {
			return true;
		}

		public LocalizedValuesMap localizedValuesMap() {
			return new LocalizedValuesMap("default localized value");
		}

		public String locationVariableValue() {
			return "${resource:template.ftl}";
		}

		public String[] stringArrayValue() {
			return new String[] {
				"string value 0", "string value 1", "string value 2"
			};
		}

		public String stringValue() {
			return "a string value";
		}

	}

}