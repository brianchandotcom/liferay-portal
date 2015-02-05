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

package com.liferay.portal.settings;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Ivan Zaera
 */
public class ServiceConfigurationBeanSettingsTest {

	public static final String TEMPLATE_CONTENTS = "template contents";

	@Before
	public void setUp() {
		_serviceConfigurationBean = new ServiceConfigurationBean();

		_mockResourceManager = new MockResourceManager(TEMPLATE_CONTENTS);

		_serviceConfigurationBeanSettings =
			new ServiceConfigurationBeanSettings(
				_serviceConfigurationBean, _mockResourceManager, null);
	}

	@Test
	public void testGetValuesWithExistingKey() {
		Assert.assertArrayEquals(
			_serviceConfigurationBean.stringArrayValue(),
			_serviceConfigurationBeanSettings.getValues(
				"stringArrayValue", new String[]{"defaultValue"}));
	}

	@Test
	public void testGetValuesWithMissingKey() {
		String[] defaultValue = {"defaultValue"};

		Assert.assertArrayEquals(
			defaultValue,
			_serviceConfigurationBeanSettings.getValues(
				"missingKey", defaultValue));
	}

	@Test
	public void testGetValueWithExistingBooleanValue() {
		Assert.assertEquals(
			String.valueOf(_serviceConfigurationBean.booleanValue()),
			_serviceConfigurationBeanSettings.getValue(
				"booleanValue", "defaultValue"));
	}

	@Test
	public void testGetValueWithExistingStringValue() {
		Assert.assertEquals(
			_serviceConfigurationBean.stringValue(),
			_serviceConfigurationBeanSettings.getValue(
				"stringValue", "defaultValue"));
	}

	@Test
	public void testGetValueWithLocationVariable() {
		Assert.assertEquals(
			TEMPLATE_CONTENTS,
			_serviceConfigurationBeanSettings.getValue(
				"locationVariableValue", "defaultValue"));

		List<String> requestedLocations =
			_mockResourceManager.getRequestedLocations();

		Assert.assertEquals(1, requestedLocations.size());
		Assert.assertEquals("template.ftl", requestedLocations.get(0));
	}

	@Test
	public void testGetValueWithMissingKey() {
		Assert.assertEquals(
			"defaultValue",
			_serviceConfigurationBeanSettings.getValue(
				"missingKey", "defaultValue"));
	}

	@Test
	public void testGetValueWithNullServiceConfigurationBean() {
		_serviceConfigurationBeanSettings =
			new ServiceConfigurationBeanSettings(null, null, null);

		Assert.assertEquals(
			"defaultValue",
			_serviceConfigurationBeanSettings.getValue(
				"anyKey", "defaultValue"));
	}

	private MockResourceManager _mockResourceManager;
	private ServiceConfigurationBean _serviceConfigurationBean;
	private ServiceConfigurationBeanSettings _serviceConfigurationBeanSettings;

	private static class ServiceConfigurationBean {

		public boolean booleanValue() {
			return true;
		}

		public String locationVariableValue() {
			return "${resource:template.ftl}";
		}

		public String[] stringArrayValue() {
			return new String[] {
				"string value 0", "string value 1", "string value 2" };
		}

		public String stringValue() {
			return "a string value";
		}

	}

}