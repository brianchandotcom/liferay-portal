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

import java.util.Arrays;
import java.util.Collection;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Iván Zaera
 */
public class MemorySettingsTest {

	@Test
	public void testSetAndGetValue() {
		_memorySettings.setValue("key", "value");

		Collection<String> keys = _memorySettings.getModifiedKeys();

		Assert.assertEquals(keys.toString(), 1, keys.size());

		Assert.assertEquals("value", _memorySettings.getValue("key", null));
	}

	@Test
	public void testSetAndGetValues() {
		_memorySettings.setValues("key", new String[] {"value1", "value2"});

		Collection<String> keys = _memorySettings.getModifiedKeys();

		Assert.assertEquals(keys.toString(), 1, keys.size());

		String[] values = _memorySettings.getValues("key", null);

		Assert.assertEquals(Arrays.toString(values), 2, values.length);
		Assert.assertEquals("value1", values[0]);
		Assert.assertEquals("value2", values[1]);

		Assert.assertEquals("value1", _memorySettings.getValue("key", null));
	}

	private final MemorySettings _memorySettings = new MemorySettings();

}