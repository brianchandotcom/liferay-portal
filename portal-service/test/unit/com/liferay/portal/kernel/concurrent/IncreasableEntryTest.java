/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.concurrent;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
public class IncreasableEntryTest {

	@Test
	public void testGettingKey() {
		IncreasableEntry<String, Integer> increasableEntry =
			new IntegerIncreasableEntry("test", 0);

		Assert.assertEquals("test", increasableEntry.getKey());
		Assert.assertEquals("test", increasableEntry.getKey());
		Assert.assertEquals("test", increasableEntry.getKey());
	}

	@Test
	public void testIncreaseAndGet() {
		IncreasableEntry<String, Integer> increasableEntry =
			new IntegerIncreasableEntry("test", 0);

		// Simple increase

		Assert.assertTrue(increasableEntry.increase(1));

		// Simple get

		Assert.assertEquals(1, (int)increasableEntry.getValue());

		increasableEntry = new IntegerIncreasableEntry("test", 0);

		// Continue get

		Assert.assertEquals(0, (int)increasableEntry.getValue());
		Assert.assertEquals(0, (int)increasableEntry.getValue());
		Assert.assertEquals(0, (int)increasableEntry.getValue());

		increasableEntry = new IntegerIncreasableEntry("test", 0);

		// Continue increase

		Assert.assertTrue(increasableEntry.increase(1));
		Assert.assertTrue(increasableEntry.increase(2));
		Assert.assertTrue(increasableEntry.increase(3));

		// Check value

		Assert.assertEquals(6, (int)increasableEntry.getValue());

		// Increase after get

		increasableEntry = new IntegerIncreasableEntry("test", 0);

		Assert.assertEquals(0, (int)increasableEntry.getValue());
		Assert.assertFalse(increasableEntry.increase(1));
		Assert.assertEquals(0, (int)increasableEntry.getValue());
	}

	private class IntegerIncreasableEntry
		extends IncreasableEntry<String, Integer> {

		public IntegerIncreasableEntry(String key, Integer value) {
			super(key, value);
		}

		@Override
		public Integer doIncrease(Integer originalValue, Integer deltaValue) {
			return originalValue + deltaValue;
		}

	}

}