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

package com.liferay.portal.test.rule;

import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.NewEnvTestRule;

import java.util.ArrayList;
import java.util.List;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

/**
 * @author Matthew Tambara
 */
public class LiferayUnitTestRule extends AggregateTestRule {

	public static final LiferayUnitTestRule INSTANCE =
		new LiferayUnitTestRule();

	public LiferayUnitTestRule() {
		super(false, _getTestRules());
	}

	private static TestRule[] _getTestRules() {
		List<TestRule> testRules = new ArrayList<>();

		testRules.add(
			new LazyInstanceTestRule(
				InitializeKernelUtilTestRule.class.getName()));
		testRules.add(
			new LazyInstanceTestRule(AspectJNewEnvTestRule.class.getName()));
		testRules.add(new LazyInstanceTestRule(NewEnvTestRule.class.getName()));

		return testRules.toArray(new TestRule[0]);
	}

	private static class LazyInstanceTestRule implements TestRule {

		@Override
		public Statement apply(Statement statement, Description description) {
			ClassLoader classLoader =
				LazyInstanceTestRule.class.getClassLoader();

			try {
				return ReflectionTestUtil.invoke(
					ReflectionTestUtil.<Object>getFieldValue(
						classLoader.loadClass(_testRuleName), "INSTANCE"),
					"apply",
					new Class<?>[] {Statement.class, Description.class},
					statement, description);
			}
			catch (ClassNotFoundException classNotFoundException) {
				ReflectionUtil.throwException(classNotFoundException);
			}

			return null;
		}

		private LazyInstanceTestRule(String testRuleName) {
			_testRuleName = testRuleName;
		}

		private final String _testRuleName;

	}

}