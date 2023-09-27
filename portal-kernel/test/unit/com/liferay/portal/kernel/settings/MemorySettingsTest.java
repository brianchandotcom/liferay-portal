/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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