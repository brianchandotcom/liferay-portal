/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.vulcan.internal.graphql.instrumentation;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import graphql.execution.AbortExecutionException;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Carlos Correa
 */
public class QueryDepthLimitInstrumentationTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testMkAbortException() {
		int queryDepthLimit = RandomTestUtil.randomInt();

		QueryDepthLimitInstrumentation queryDepthLimitInstrumentation =
			new QueryDepthLimitInstrumentation(queryDepthLimit);

		int depth = RandomTestUtil.randomInt();

		AbortExecutionException abortExecutionException =
			queryDepthLimitInstrumentation.mkAbortException(
				depth, queryDepthLimit);

		Assert.assertTrue(
			abortExecutionException instanceof AbortExecutionException);
		Assert.assertEquals(
			StringBundler.concat(
				"Depth ", depth, " is greater than the query depth limit of ",
				queryDepthLimit),
			abortExecutionException.getMessage());
	}

}