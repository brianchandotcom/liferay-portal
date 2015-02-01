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

package com.liferay.portal.kernel.resiliency.spi;

import com.liferay.portal.kernel.process.local.LocalProcessLauncher;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.CodeCoverageAssertor;
import com.liferay.portal.kernel.test.rule.NewEnv;
import com.liferay.portal.kernel.test.rule.NewEnvTestRule;

import java.util.concurrent.ConcurrentMap;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
public class SPIUtilTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			CodeCoverageAssertor.INSTANCE, NewEnvTestRule.INSTANCE);

	@Test
	public void testConstructor() {
		new SPIUtil();
	}

	@Test
	public void testIsNotSPI() {
		Assert.assertFalse(SPIUtil.isSPI());

		try {
			SPIUtil.getSPI();

			Assert.fail();
		}
		catch (IllegalStateException ise) {
			Assert.assertEquals(
				"Current process is not an SPI instance", ise.getMessage());
		}
	}

	@NewEnv(type = NewEnv.Type.CLASSLOADER)
	@Test
	public void testIsSPI() {
		MockSPI mockSPI = new MockSPI();

		ConcurrentMap<String, Object> attributes =
			LocalProcessLauncher.ProcessContext.getAttributes();

		attributes.put(SPI.SPI_INSTANCE_PUBLICATION_KEY, mockSPI);

		Assert.assertTrue(SPIUtil.isSPI());
		Assert.assertSame(mockSPI, SPIUtil.getSPI());
	}

}