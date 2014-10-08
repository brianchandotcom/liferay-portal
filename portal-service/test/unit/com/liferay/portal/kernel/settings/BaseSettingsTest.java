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

import java.util.Collections;

import org.junit.Assert;
import org.junit.Test;

import org.powermock.api.mockito.PowerMockito;

/**
 * @author Iván Zaera
 */
public class BaseSettingsTest extends PowerMockito {

	public BaseSettingsTest() {
		_parentSettings = new MemorySettings();

		_baseSettings = new MemorySettings(_parentSettings);
	}

	@Test
	public void testGetModifiableSettingsForModifiableBaseSettings() {
		BaseSettings baseSettings = new MemorySettings();

		Assert.assertTrue(baseSettings instanceof ModifiableSettings);
		Assert.assertSame(baseSettings, baseSettings.getModifiableSettings());
	}

	@Test
	public void testGetModifiableSettingsForUnmodifiableBaseSettings() {
		ModifiableSettings modifiableSettings = new MemorySettings();
		BaseSettings baseSettings = new ParameterMapSettings(
			Collections.<String, String[]> emptyMap(), modifiableSettings);

		Assert.assertFalse(baseSettings instanceof ModifiableSettings);
		Assert.assertSame(
			modifiableSettings, baseSettings.getModifiableSettings());
	}

	@Test
	public void testGetParentSettings() {
		Assert.assertSame(_parentSettings, _baseSettings.getParentSettings());
	}

	@Test
	public void testGetValueReturnsDefaultWhenValueAndParentNotSet() {
		Assert.assertEquals(
			_DEFAULT_VALUE, _baseSettings.getValue(_KEY, _DEFAULT_VALUE));
		Assert.assertArrayEquals(
			_DEFAULT_VALUES, _baseSettings.getValues(_KEY, _DEFAULT_VALUES));

		_parentSettings.setValue(_KEY, _VALUE);

		Assert.assertEquals(
			_VALUE, _baseSettings.getValue(_KEY, _DEFAULT_VALUE));

		_parentSettings.setValues(_KEY, _VALUES);

		Assert.assertArrayEquals(
			_VALUES, _baseSettings.getValues(_KEY, _DEFAULT_VALUES));
	}

	private static final String _DEFAULT_VALUE = "defaultValue";

	private static final String[] _DEFAULT_VALUES = {
		"defaultValue0", "defaultValue1"};

	private static final String _KEY = "key";

	private static final String _VALUE = "value";

	private static final String[] _VALUES = {"value0", "value1"};

	private final BaseSettings _baseSettings;
	private final MemorySettings _parentSettings;

}