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

package com.liferay.portal.test.runners;

import com.liferay.portal.kernel.util.CentralizedThreadLocal;
import com.liferay.portal.test.log.LogAssertionTestRule;
import com.liferay.portal.test.randomizerbumpers.UniqueStringRandomizerBumper;
import com.liferay.portal.test.rule.DeleteAfterTestRunTestRule;

import java.util.Collections;
import java.util.List;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.Statement;

/**
 * @author Miguel Pastor
 * @author Carlos Sierra
 * @author Shuyang Zhou
 */
public class LiferayIntegrationJUnitTestRunner
	extends CustomizableSpringContextJUnitTestRunner {

	public LiferayIntegrationJUnitTestRunner(Class<?> clazz)
		throws InitializationError {

		super(clazz);
	}

	@Override
	public void afterApplicationContextInit() {
	}

	@Override
	public List<String> getExtraConfigLocations() {
		return Collections.emptyList();
	}

	@Override
	protected List<TestRule> classRules() {
		List<TestRule> testRules = super.classRules();

		testRules.add(_uniqueStringRandomizerBumperTestRule);

		testRules.add(_clearThreadLocalTestRule);

		testRules.add(LogAssertionTestRule.INSTANCE);

		return testRules;
	}

	@Override
	protected List<TestRule> getTestRules(Object object) {
		List<TestRule> testRules = super.getTestRules(object);

		testRules.add(new DeleteAfterTestRunTestRule(object));

		testRules.add(LogAssertionTestRule.INSTANCE);

		return testRules;
	}

	private final TestRule _clearThreadLocalTestRule = new TestRule() {

		@Override
		public Statement apply(
			final Statement statement, Description description) {

			return new Statement() {

				@Override
				public void evaluate() throws Throwable {
					try {
						statement.evaluate();
					}
					finally {
						CentralizedThreadLocal.clearShortLivedThreadLocals();
					}
				}

			};
		}

	};

	private final TestRule _uniqueStringRandomizerBumperTestRule =
		new TestRule() {

			@Override
			public Statement apply(
				final Statement statement, Description description) {

				return new Statement() {

					@Override
					public void evaluate() throws Throwable {
						UniqueStringRandomizerBumper.reset();

						statement.evaluate();
					}

				};
			}

		};

}