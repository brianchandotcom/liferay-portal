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

package com.liferay.portal.tools;

import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Renato Rego
 */
public class MachineInfoUtilTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testConvertMaxValueToGigaBytes() {
		Assert.assertEquals(
			Long.MAX_VALUE / MachineInfoUtil.ONE_GIGA_BYTE_IN_BYTES,
			MachineInfoUtil.convertToGigaBytes(Long.MAX_VALUE), 0.0);
	}

	@Test
	public void testConvertZeroToGigaBytes() {
		Assert.assertEquals(
			0L / MachineInfoUtil.ONE_GIGA_BYTE_IN_BYTES,
			MachineInfoUtil.convertToGigaBytes(0L), 0.0);
	}

	@Test
	public void testFormatMemorySizeWithOneDecimalDigit() {
		Assert.assertEquals("32.1 GB", MachineInfoUtil.formatMemorySize(32.1));
	}

	@Test
	public void testFormatMemorySizeWithoutDecimalDigit() {
		Assert.assertEquals("32.0 GB", MachineInfoUtil.formatMemorySize(32));

		Assert.assertEquals("32.0 GB", MachineInfoUtil.formatMemorySize(32.));
	}

	@Test
	public void testFormatMemorySizeWithThreeDecimalDigits() {
		Assert.assertEquals(
			"32.01 GB", MachineInfoUtil.formatMemorySize(32.012));

		Assert.assertEquals(
			"32.01 GB", MachineInfoUtil.formatMemorySize(32.0120));
	}

	@Test
	public void testFormatMemorySizeWithTwoDecimalDigits() {
		Assert.assertEquals(
			"32.01 GB", MachineInfoUtil.formatMemorySize(32.01));

		Assert.assertEquals("32.1 GB", MachineInfoUtil.formatMemorySize(32.10));
	}

}