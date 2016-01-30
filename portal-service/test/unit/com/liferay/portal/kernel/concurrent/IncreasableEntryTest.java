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

package com.liferay.portal.kernel.concurrent;

import com.liferay.portal.kernel.test.rule.CodeCoverageAssertor;
import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.StringBundler;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
public class IncreasableEntryTest {

	@ClassRule
	public static final CodeCoverageAssertor codeCoverageAssertor =
		CodeCoverageAssertor.INSTANCE;

	@Test
	public void testEqualsAndHashCode() {
		IncreasableEntry<String, Integer> increasableEntry =
			new IntegerIncreasableEntry("test", 0);

		Assert.assertTrue(increasableEntry.equals(increasableEntry));
		Assert.assertFalse(increasableEntry.equals(new Object()));
		Assert.assertFalse(
			increasableEntry.equals(new IntegerIncreasableEntry("test1", 0)));
		Assert.assertFalse(
			increasableEntry.equals(new IntegerIncreasableEntry("test", 1)));
		Assert.assertTrue(
			increasableEntry.equals(new IntegerIncreasableEntry("test", 0)));

		int hash = HashUtil.hash(0, increasableEntry.getKey());

		Assert.assertEquals(
			HashUtil.hash(hash, increasableEntry.getValue()),
			increasableEntry.hashCode());
	}

	@Test
	public void testGetters() {
		IncreasableEntry<String, Integer> increasableEntry =
			new IntegerIncreasableEntry("test", 0);

		Assert.assertEquals("test", increasableEntry.getKey());
		Assert.assertEquals(0, (int)increasableEntry.getValue());
	}

	@Test
	public void testIncrease() {
		IncreasableEntry<String, Integer> increasableEntry1 =
			new IntegerIncreasableEntry("test", 0);

		IncreasableEntry<String, Integer> increasableEntry2 =
			increasableEntry1.increase(2);

		Assert.assertNotSame(increasableEntry1, increasableEntry2);
		Assert.assertEquals(
			increasableEntry1.getKey(), increasableEntry2.getKey());
		Assert.assertEquals(2, (int)increasableEntry2.getValue());
	}

	@Test
	public void testToString() {
		IncreasableEntry<String, Integer> increasableEntry =
			new IntegerIncreasableEntry("test", 0);

		StringBundler sb = new StringBundler(5);

		sb.append("{key=");
		sb.append(increasableEntry.getKey());
		sb.append(", value=");
		sb.append(increasableEntry.getValue());
		sb.append("}");

		Assert.assertEquals(sb.toString(), increasableEntry.toString());
	}

	private static class IntegerIncreasableEntry
		extends IncreasableEntry<String, Integer> {

		public IntegerIncreasableEntry(String key, Integer value) {
			super(key, value);
		}

		@Override
		public IncreasableEntry<String, Integer> increase(Integer deltaValue) {
			return new IntegerIncreasableEntry(key, value + deltaValue);
		}

	}

}