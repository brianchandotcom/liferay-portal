/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.vulcan.internal.graphql.servlet.instrumentation;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.portal.vulcan.internal.graphql.exception.MaxQueryDepthExceededException;

import graphql.analysis.QueryDepthInfo;

import graphql.execution.AbortExecutionException;

import java.util.function.Function;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Carlos Correa
 */
public class MaxQueryDepthInstrumentationTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testMaxQueryDepthExceededFunction() {
		int maxDepthLimit = RandomTestUtil.randomInt();

		MaxQueryDepthInstrumentation maxQueryDepthInstrumentation =
			new MaxQueryDepthInstrumentation(maxDepthLimit);

		Function<QueryDepthInfo, Boolean> maxQueryDepthExceededFunction =
			ReflectionTestUtil.getFieldValue(
				maxQueryDepthInstrumentation, "maxQueryDepthExceededFunction");

		Assert.assertFalse(
			maxQueryDepthExceededFunction.apply(
				_createQueryDepthInfo(maxDepthLimit - 1)));
		Assert.assertFalse(
			maxQueryDepthExceededFunction.apply(
				_createQueryDepthInfo(maxDepthLimit)));
		Assert.assertTrue(
			maxQueryDepthExceededFunction.apply(
				_createQueryDepthInfo(maxDepthLimit + 1)));
	}

	@Test
	public void testMaxQueryDepthExceededFunctionWithNoLimit() {
		MaxQueryDepthInstrumentation maxQueryDepthInstrumentation =
			new MaxQueryDepthInstrumentation(-1);

		Function<QueryDepthInfo, Boolean> maxQueryDepthExceededFunction =
			ReflectionTestUtil.getFieldValue(
				maxQueryDepthInstrumentation, "maxQueryDepthExceededFunction");

		Assert.assertFalse(
			maxQueryDepthExceededFunction.apply(
				_createQueryDepthInfo(Integer.MAX_VALUE)));
	}

	@Test
	public void testMkAbortException() {
		int maxDepthLimit = RandomTestUtil.randomInt();

		MaxQueryDepthInstrumentation maxQueryDepthInstrumentation =
			new MaxQueryDepthInstrumentation(maxDepthLimit);

		int limit = RandomTestUtil.randomInt();

		try {
			maxQueryDepthInstrumentation.mkAbortException(
				limit + maxDepthLimit, maxDepthLimit);

			Assert.fail();
		}
		catch (MaxQueryDepthExceededException maxQueryDepthExceededException) {
			Assert.assertTrue(
				maxQueryDepthExceededException instanceof
					AbortExecutionException);
			Assert.assertEquals(
				StringBundler.concat(
					"Maximum query depth exceeded ", limit + maxDepthLimit,
					" > ", maxDepthLimit),
				maxQueryDepthExceededException.getMessage());
		}
	}

	private QueryDepthInfo _createQueryDepthInfo(int depth) {
		return QueryDepthInfo.newQueryDepthInfo(
		).depth(
			depth
		).build();
	}

}