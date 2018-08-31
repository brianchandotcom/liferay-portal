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

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Iván Zaera
 */
public class ParameterMapSettingsTest {

	@Before
	public void setUp() {
		_parameterMapSettings = new ParameterMapSettings(
			_parameterMap, _parentSettings);
	}

	@Test
	public void testGetValuesWhenFoundInParameterMap() {
		String[] values = {"requestValue1", "requestValue2"};

		_parameterMap.put("preferences--key--", values);

		Assert.assertArrayEquals(
			values,
			_parameterMapSettings.getValues(
				"key", new String[] {"defaultValue"}));
	}

	@Test
	public void testGetValuesWhenFoundInParameterMapWithParameterNamePrefix() {
		String[] values = {"requestValue1", "requestValue2"};

		_parameterMap.put("prefix--key", values);

		_parameterMapSettings.setParameterNamePrefix("prefix--");

		Assert.assertArrayEquals(
			values,
			_parameterMapSettings.getValues(
				"key", new String[] {"defaultValue"}));
	}

	@Test
	public void testGetValuesWhenFoundInSettings() {
		String[] values = {"settingsValue1", "settingsValue2"};

		_parentSettings.setValues("key", values);

		Assert.assertArrayEquals(
			values,
			_parameterMapSettings.getValues(
				"key", new String[] {"defaultValue"}));
	}

	@Test
	public void testGetValueWhenFoundInParameterMap() {
		_parameterMap.put("preferences--key--", new String[] {"requestValue"});

		_parentSettings.setValue("key", "settingsValue");

		Assert.assertEquals(
			"requestValue",
			_parameterMapSettings.getValue("key", "defaultValue"));
	}

	@Test
	public void testGetValueWhenFoundInParameterMapWithParameterNamePrefix() {
		_parameterMap.put("prefix--key", new String[] {"requestValue"});

		_parameterMapSettings.setParameterNamePrefix("prefix--");

		Assert.assertEquals(
			"requestValue",
			_parameterMapSettings.getValue("key", "defaultValue"));
	}

	@Test
	public void testGetValueWhenFoundInSettings() {
		_parentSettings.setValue("key", "settingsValue");

		Assert.assertEquals(
			"settingsValue",
			_parameterMapSettings.getValue("key", "defaultValue"));
	}

	private final Map<String, String[]> _parameterMap = new HashMap<>();
	private ParameterMapSettings _parameterMapSettings;
	private final MemorySettings _parentSettings = new MemorySettings();

}