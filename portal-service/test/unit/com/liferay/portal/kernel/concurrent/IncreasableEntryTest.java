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

import com.liferay.portal.kernel.test.TestCase;

/**
 * @author Shuyang Zhou
 */
public class IncreasableEntryTest extends TestCase {

	public void testGettingKey() {
		IncreasableEntry<String, Integer> increasableEntry =
			new IntegerIncreasableEntry("test", 0);

		assertEquals("test", increasableEntry.getKey());
		assertEquals("test", increasableEntry.getKey());
		assertEquals("test", increasableEntry.getKey());
	}

	public void testIncreaseAndGet() {
		IncreasableEntry<String, Integer> increasableEntry =
			new IntegerIncreasableEntry("test", 0);

		// Simple increase

		assertTrue(increasableEntry.increase(1));

		// Simple get

		assertEquals(1, (int)increasableEntry.getValue());

		increasableEntry = new IntegerIncreasableEntry("test", 0);

		// Continue get

		assertEquals(0, (int)increasableEntry.getValue());
		assertEquals(0, (int)increasableEntry.getValue());
		assertEquals(0, (int)increasableEntry.getValue());

		increasableEntry = new IntegerIncreasableEntry("test", 0);

		// Continue increase

		assertTrue(increasableEntry.increase(1));
		assertTrue(increasableEntry.increase(2));
		assertTrue(increasableEntry.increase(3));

		// Check value

		assertEquals(6, (int)increasableEntry.getValue());

		// Increase after get

		increasableEntry = new IntegerIncreasableEntry("test", 0);

		assertEquals(0, (int)increasableEntry.getValue());
		assertFalse(increasableEntry.increase(1));
		assertEquals(0, (int)increasableEntry.getValue());
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