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
		TestRule testRule1 = new TestRule();
		TestRule testRule2 = new TestRule();

		AggregateTestRule aggregateTestRule = new AggregateTestRule(
			testRule1, testRule2);

		aggregateTestRule.disableTestRule(testRule1);

		Statement statement = aggregateTestRule.apply(
			new Statement() {

				@Override
				public void evaluate() {
				}

			},
			Description.createTestDescription(getClass(), "testDisableRule"));

		statement.evaluate();

		Assert.assertFalse(testRule1.isApplied());
		Assert.assertTrue(testRule2.isApplied());
	}

	private static class TestRule extends LiferayUnitTestRule {

		@Override
		public Statement apply(Statement statement, Description description) {
			_applied = true;

			return statement;
		}

		public boolean isApplied() {
			return _applied;
		}

		private boolean _applied;

	}

}