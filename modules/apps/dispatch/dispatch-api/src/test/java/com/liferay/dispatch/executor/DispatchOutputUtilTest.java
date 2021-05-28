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

package com.liferay.dispatch.executor;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Igor Beslic
 */
public class DispatchOutputUtilTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testTruncate() {
		String output = _getRandomOutput(40, 100);

		Assert.assertFalse(output.contains("Truncation message"));

		output = DispatchOutputUtil.truncate(
			5, 3, "Truncation message", output);

		Assert.assertTrue(output.contains("Truncation message"));

		output = _getOutput(20);

		output = DispatchOutputUtil.truncate(
			5, 5, "Truncation message", output);

		Assert.assertTrue(output.contains("Truncation message"));

		output = _getRandomOutput(5, 19);

		Assert.assertFalse(output.contains("Truncation message"));

		output = DispatchOutputUtil.truncate(
			5, 5, "Truncation message", output);

		Assert.assertFalse(output.contains("Truncation message"));

		Assert.assertNull(DispatchOutputUtil.truncate(100, 100, null, null));

		output = _getRandomOutput(20, 100);

		output = output + "\nLast line in output";

		Assert.assertEquals(
			DispatchOutputUtil.truncate(5, 7, null, output),
			DispatchOutputUtil.truncate(5, 7, StringPool.BLANK, output));
		Assert.assertEquals(
			"Last line in output",
			DispatchOutputUtil.truncate(-20, 1, null, output));
		Assert.assertEquals(
			StringPool.BLANK,
			DispatchOutputUtil.truncate(-20, -10, null, output));
	}

	private String _getOutput(int lineCount) {
		StringBundler sb = new StringBundler(lineCount);

		for (int i = 0; i <= lineCount; i++) {
			sb.append(i);
			sb.append(StringPool.SPACE);
			sb.append(RandomTestUtil.randomString());

			if ((i + 1) < lineCount) {
				sb.append(System.lineSeparator());
			}
		}

		return sb.toString();
	}

	private String _getRandomOutput(int lineCountMin, int lineCountMax) {
		return _getOutput(RandomTestUtil.randomInt(lineCountMin, lineCountMax));
	}

}