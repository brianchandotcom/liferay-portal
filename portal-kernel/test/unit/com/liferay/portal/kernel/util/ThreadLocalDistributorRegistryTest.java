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

package com.liferay.portal.kernel.util;

import com.liferay.portal.kernel.test.rule.CodeCoverageAssertor;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
public class ThreadLocalDistributorRegistryTest {

	@ClassRule
	public static final CodeCoverageAssertor codeCoverageAssertor =
		CodeCoverageAssertor.INSTANCE;

	@Test
	public void testConstructor() {
		new ThreadLocalDistributorRegistry();
	}

	@Test
	public void testThreadLocalDistributorRegistry() {
		ThreadLocalDistributor[] threadLocalDistributors =
			ThreadLocalDistributorRegistry.getThreadLocalDistributors();

		Assert.assertEquals(
			Arrays.toString(threadLocalDistributors), 0,
			threadLocalDistributors.length);

		ThreadLocalDistributor threadLocalDistributor1 =
			new ThreadLocalDistributor();
		ThreadLocalDistributor threadLocalDistributor2 =
			new ThreadLocalDistributor();

		Assert.assertEquals(
			0,
			ThreadLocalDistributorRegistry.addThreadLocalDistributor(
				threadLocalDistributor1));
		Assert.assertEquals(
			1,
			ThreadLocalDistributorRegistry.addThreadLocalDistributor(
				threadLocalDistributor2));
		Assert.assertSame(
			threadLocalDistributor1,
			ThreadLocalDistributorRegistry.getThreadLocalDistributor(0));
		Assert.assertSame(
			threadLocalDistributor2,
			ThreadLocalDistributorRegistry.getThreadLocalDistributor(1));
	}

}