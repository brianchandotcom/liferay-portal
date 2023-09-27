/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.internal.increment;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.CodeCoverageAssertor;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
public class IncreasableEntryTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			CodeCoverageAssertor.INSTANCE, LiferayUnitTestRule.INSTANCE);

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

		Assert.assertEquals(
			StringBundler.concat(
				"{key=", increasableEntry.getKey(), ", value=",
				increasableEntry.getValue(), "}"),
			increasableEntry.toString());
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