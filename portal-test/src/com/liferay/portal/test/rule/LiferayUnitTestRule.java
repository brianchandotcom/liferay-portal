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

import java.lang.reflect.Field;

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
				"com.liferay.portal.test.rule.InitializeKernelUtilTestRule"));
		testRules.add(
			new LazyInstanceTestRule(
				"com.liferay.portal.test.rule.AspectJNewEnvTestRule"));
		testRules.add(
			new LazyInstanceTestRule(
				"com.liferay.portal.kernel.test.rule.NewEnvTestRule"));

		return testRules.toArray(new TestRule[0]);
	}

	private static class LazyInstanceTestRule implements TestRule {

		public LazyInstanceTestRule(String testRuleName) {
			_testRuleName = testRuleName;
		}

		@Override
		public Statement apply(Statement statement, Description description) {
			ClassLoader classLoader =
				LazyInstanceTestRule.class.getClassLoader();

			try {
				Class<?> clazz = classLoader.loadClass(_testRuleName);

				Field field = clazz.getField("INSTANCE");

				Object testRule = field.get(null);

				return ReflectionTestUtil.invoke(
					testRule, "apply",
					new Class<?>[] {Statement.class, Description.class},
					statement, description);
			}
			catch (Exception exception) {
				ReflectionUtil.throwException(exception);
			}

			return null;
		}

		private final String _testRuleName;

	}

}