/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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