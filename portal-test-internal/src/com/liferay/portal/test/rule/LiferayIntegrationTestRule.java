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

import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.BaseTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRunTestRule;
import com.liferay.portal.kernel.test.rule.executor.ClearThreadLocalExecutor;
import com.liferay.portal.kernel.test.rule.executor.InitTestLiferayContextExecutor;
import com.liferay.portal.kernel.test.rule.executor.UniqueStringRandomizerBumperExecutor;
import com.liferay.portal.test.rule.executor.ClearThreadLocalExecutorImpl;
import com.liferay.portal.test.rule.executor.InitTestLiferayContextExecutorImpl;
import com.liferay.portal.test.rule.executor.UniqueStringRandomizerBumperExecutorImpl;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

/**
 * @author Shuyang Zhou
 */
public class LiferayIntegrationTestRule extends AggregateTestRule {

	public LiferayIntegrationTestRule() {
		super(
			false, LogAssertionTestRule.INSTANCE, _clearThreadLocalTestRule,
			_uniqueStringRandomizerBumperTestRule,
			new DeleteAfterTestRunTestRule());
	}

	@Override
	public Statement apply(Statement statement, Description description) {
		_initTestLiferayContextExecutor.init();

		return super.apply(statement, description);
	}

	private static final ClearThreadLocalExecutor _clearThreadLocalExecutor =
		new ClearThreadLocalExecutorImpl();

	private static final TestRule _clearThreadLocalTestRule =
		new BaseTestRule<Object, Object>() {

			@Override
			protected void afterClass(Description description, Object object) {
				_clearThreadLocalExecutor.clearThreadLocal();
			}

		};

	private static final InitTestLiferayContextExecutor
		_initTestLiferayContextExecutor =
			new InitTestLiferayContextExecutorImpl();

	private static final TestRule _uniqueStringRandomizerBumperTestRule =
		new BaseTestRule<Object, Object>() {

			@Override
			protected Object beforeClass(Description description) {
				_uniqueStringRandomizerBumperExecutor.reset();

				return null;
			}

			private final UniqueStringRandomizerBumperExecutor
				_uniqueStringRandomizerBumperExecutor =
					new UniqueStringRandomizerBumperExecutorImpl();

		};

}