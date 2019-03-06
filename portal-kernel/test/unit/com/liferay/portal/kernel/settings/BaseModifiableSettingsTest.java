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

import java.util.Collection;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Iván Zaera
 */
public class BaseModifiableSettingsTest {

	@Test
	public void testReset() {
		_baseModifiableSettings.setValue("key1", "value2");
		_baseModifiableSettings.setValue("key2", "value2");

		Collection<String> modifiedKeys =
			_baseModifiableSettings.getModifiedKeys();

		Assert.assertEquals(modifiedKeys.toString(), 2, modifiedKeys.size());

		_baseModifiableSettings.reset();

		modifiedKeys = _baseModifiableSettings.getModifiedKeys();

		Assert.assertEquals(modifiedKeys.toString(), 0, modifiedKeys.size());
	}

	@Test
	public void testSetValues() {
		_baseModifiableSettings.setValue("key1", "value1");
		_baseModifiableSettings.setValue("key2", "value2");

		ModifiableSettings sourceModifiableSettings = new MemorySettings();

		sourceModifiableSettings.setValue("otherKey", "otherValue");

		_baseModifiableSettings.setValues(sourceModifiableSettings);

		Collection<String> keys = _baseModifiableSettings.getModifiedKeys();

		Assert.assertEquals(keys.toString(), 3, keys.size());

		Assert.assertEquals(
			"otherValue", _baseModifiableSettings.getValue("otherKey", null));
		Assert.assertEquals(
			"value1", _baseModifiableSettings.getValue("key1", null));
		Assert.assertEquals(
			"value2", _baseModifiableSettings.getValue("key2", null));
	}

	private final BaseModifiableSettings _baseModifiableSettings =
		new MemorySettings();

}