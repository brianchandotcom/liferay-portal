/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.test.rule;

import com.liferay.portal.kernel.test.rule.AggregateTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

/**
 * @author István András Dézsi
 */
public class AggregateTestRuleTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testDisableTestRule() throws Throwable {
		TestRule1 testRule1 = new TestRule1();
		TestRule2 testRule2 = new TestRule2();

		AggregateTestRule aggregateTestRule = new AggregateTestRule(
			testRule1, testRule2);

		aggregateTestRule.disableTestRule(TestRule1.class);

		Statement statement = aggregateTestRule.apply(
			new Statement() {

				@Override
				public void evaluate() {
				}

			},
			Description.createTestDescription(getClass(), "testDisableRule"));

		statement.evaluate();

		Assert.assertFalse(_testRule1Applied);
		Assert.assertTrue(_testRule2Applied);
	}

	private static boolean _testRule1Applied;
	private static boolean _testRule2Applied;

	private static class TestRule1 extends LiferayUnitTestRule {

		@Override
		public Statement apply(Statement statement, Description description) {
			_testRule1Applied = true;

			return statement;
		}

	}

	private static class TestRule2 extends LiferayUnitTestRule {

		@Override
		public Statement apply(Statement statement, Description description) {
			_testRule2Applied = true;

			return statement;
		}

	}

}