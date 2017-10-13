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

package com.liferay.petra.lang;

import com.liferay.portal.kernel.test.rule.CodeCoverageAssertor;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * @author Preston Crary
 */
public class HashUtilTest {

	@ClassRule
	public static final CodeCoverageAssertor codeCoverageAssertor =
		CodeCoverageAssertor.INSTANCE;

	@Test
	public void testConstructor() {
		new HashUtil();
	}

	@Test
	public void testHashBoolean() {
		_assertHashValue(0, false);
		_assertHashValue(1, true);
	}

	@Test
	public void testHashInt() {
		_assertHashValue(Integer.MIN_VALUE, Integer.MIN_VALUE);
		_assertHashValue(-1, -1);
		_assertHashValue(0, 0);
		_assertHashValue(1, 1);
		_assertHashValue(Integer.MAX_VALUE, Integer.MAX_VALUE);
	}

	@Test
	public void testHashLong() {
		_assertHashValue(0, Long.MIN_VALUE);
		_assertHashValue(-1, -1L);
		_assertHashValue(0, 0L);
		_assertHashValue(1, 1L);
		_assertHashValue(-1, Long.MAX_VALUE);
	}

	@Test
	public void testHashObject() {
		_assertHashValue(Boolean.FALSE);
		_assertHashValue(Boolean.TRUE);

		_assertHashValue(Integer.MIN_VALUE);
		_assertHashValue(Integer.MIN_VALUE);

		_assertHashValue(Long.MIN_VALUE);
		_assertHashValue(Long.MAX_VALUE);

		_assertHashValue(null);

		_assertHashValue(new Object());

		_assertHashValue("hash");
	}

	private void _assertHashValue(int expected, boolean value) {
		Assert.assertEquals(expected, HashUtil.hash(0, value));
		Assert.assertEquals(11 + expected, HashUtil.hash(1, value));
		Assert.assertEquals(-11 + expected, HashUtil.hash(-1, value));
	}

	private void _assertHashValue(int expected, int value) {
		Assert.assertEquals(expected, HashUtil.hash(0, value));
		Assert.assertEquals(11 + expected, HashUtil.hash(1, value));
		Assert.assertEquals(-11 + expected, HashUtil.hash(-1, value));
	}

	private void _assertHashValue(int expected, long value) {
		Assert.assertEquals(expected, HashUtil.hash(0, value));
		Assert.assertEquals(11 + expected, HashUtil.hash(1, value));
		Assert.assertEquals(-11 + expected, HashUtil.hash(-1, value));
	}

	private void _assertHashValue(Object value) {
		if (value == null) {
			Assert.assertEquals(0, HashUtil.hash(0, null));
			Assert.assertEquals(11, HashUtil.hash(1, null));
			Assert.assertEquals(-11, HashUtil.hash(-1, null));
		}
		else {
			Assert.assertEquals(value.hashCode(), HashUtil.hash(0, value));
			Assert.assertEquals(11 + value.hashCode(), HashUtil.hash(1, value));
			Assert.assertEquals(
				-11 + value.hashCode(), HashUtil.hash(-1, value));
		}
	}

}