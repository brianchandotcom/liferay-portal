/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.util;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Michael C. Han
 */
public class PrimitiveLongListTest {

	@Test
	public void testAdd() {
		long[] expected = {10L, 11L, 12L};

		PrimitiveLongList primitiveLongList = new PrimitiveLongList();

		for (long l : expected) {
			primitiveLongList.add(l);
		}

		Assert.assertEquals(expected.length, primitiveLongList.size());

		long[] actual = primitiveLongList.getArray();

		Assert.assertEquals(
			Arrays.toString(actual), expected.length, actual.length);

		for (int i = 0; i < actual.length; i++) {
			Assert.assertEquals(expected[i], actual[i]);
		}
	}

	@Test
	public void testAddAll() {
		long[] expected = {10L, 11L, 12L};

		PrimitiveLongList primitiveLongList = new PrimitiveLongList();

		primitiveLongList.addAll(expected);

		Assert.assertEquals(expected.length, primitiveLongList.size());

		long[] actual = primitiveLongList.getArray();

		Assert.assertEquals(
			Arrays.toString(actual), expected.length, actual.length);

		for (int i = 0; i < actual.length; i++) {
			Assert.assertEquals(expected[i], actual[i]);
		}
	}

}