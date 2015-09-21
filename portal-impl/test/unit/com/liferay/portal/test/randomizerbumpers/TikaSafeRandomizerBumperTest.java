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

package com.liferay.portal.test.randomizerbumpers;

import com.liferay.portal.kernel.test.rule.CodeCoverageAssertor;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.ContentTypes;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * @author Matthew Tambara
 */
public class TikaSafeRandomizerBumperTest {

	@ClassRule
	public static final CodeCoverageAssertor codeCoverageAssertor =
		CodeCoverageAssertor.INSTANCE;

	@Test
	public void testAcceptAny() {
		TikaSafeRandomizerBumper tikaSafeRandomizerBumper =
			TikaSafeRandomizerBumper.INSTANCE;

		Assert.assertTrue(tikaSafeRandomizerBumper.accept(_EXE_BYTE_ARRAY));

		String randomString = getRegularRandomString();

		Assert.assertTrue(
			tikaSafeRandomizerBumper.accept(randomString.getBytes()));
		Assert.assertFalse(tikaSafeRandomizerBumper.accept(_BROKEN_EXE_BYTES));
	}

	@Test
	public void testAcceptExe() {
		TikaSafeRandomizerBumper tikaSafeRandomizerBumper =
			new TikaSafeRandomizerBumper("application/x-msdownload");

		Assert.assertTrue(tikaSafeRandomizerBumper.accept(_EXE_BYTE_ARRAY));

		String randomString = getRegularRandomString();

		Assert.assertFalse(
			tikaSafeRandomizerBumper.accept(randomString.getBytes()));
	}

	@Test
	public void testAcceptText() {
		String randomString = getRegularRandomString();

		TikaSafeRandomizerBumper tikaSafeRandomizerBumper =
			new TikaSafeRandomizerBumper(ContentTypes.TEXT_PLAIN);

		Assert.assertTrue(
			tikaSafeRandomizerBumper.accept(randomString.getBytes()));

		randomString = _EXE_MAGIC_HEADER.concat(RandomTestUtil.randomString(6));

		Assert.assertFalse(
			tikaSafeRandomizerBumper.accept(randomString.getBytes()));
	}

	protected static String getRegularRandomString() {
		String randomString = RandomTestUtil.randomString();

		while (randomString.startsWith(_EXE_MAGIC_HEADER)) {
			randomString = RandomTestUtil.randomString();
		}

		return randomString;
	}

	private static final byte[] _BROKEN_EXE_BYTES = "MZ5gFGQt".getBytes();

	// http://www.phreedom.org/research/tinype

	private static final byte[] _EXE_BYTE_ARRAY = new byte[] {
		77, 90, 0, 0, 80, 69, 0, 0, 76, 1, 1, 0, 106, 42, 88, -61, 0, 0, 0, 0,
		0, 0, 0, 0, 4, 0, 3, 1, 11, 1, 8, 0, 4, 0, 0, 0, 0, 0, 0, 0, 4, 0, 0, 0,
		12, 0, 0, 0, 4, 0, 0, 0, 12, 0, 0, 0, 0, 0, 64, 0, 4, 0, 0, 0, 4, 0, 0,
		0, 4, 0, 0, 0, 0, 0, 0, 0, 4, 0, 0, 0, 0, 0, 0, 0, 104, 0, 0, 0, 100, 0,
		0, 0, 0, 0, 0, 0, 2
	};

	private static final String _EXE_MAGIC_HEADER = "MZ";

}