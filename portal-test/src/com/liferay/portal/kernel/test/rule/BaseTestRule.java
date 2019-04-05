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

package com.liferay.portal.kernel.test.rule;

import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.callback.TestCallback;

import org.junit.internal.runners.statements.ExpectException;
import org.junit.internal.runners.statements.FailOnTimeout;
import org.junit.internal.runners.statements.InvokeMethod;
import org.junit.internal.runners.statements.RunAfters;
import org.junit.internal.runners.statements.RunBefores;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

/**
 * @author Shuyang Zhou
 */
public class BaseTestRule<C, M> implements TestRule {

	public BaseTestRule(TestCallback<C, M> testCallback) {
		_testCallback = testCallback;
	}

	@Override
	public final Statement apply(
		Statement statement, final Description description) {

		String methodName = description.getMethodName();

		if (methodName != null) {
			return new StatementWrapper(statement) {

				@Override
				public void evaluate() throws Throwable {
					Object target = inspectTarget(statement);

					M m = _testCallback.beforeMethod(description, target);

					try {
						statement.evaluate();
					}
					finally {
						_testCallback.afterMethod(description, m, target);
					}
				}

			};
		}

		return new StatementWrapper(statement) {

			@Override
			public void evaluate() throws Throwable {
				C c = _testCallback.beforeClass(description);

				try {
					statement.evaluate();
				}
				finally {
					_testCallback.afterClass(description, c);
				}
			}

		};
	}

	protected Object inspectTarget(Statement statement) {
		while (statement instanceof StatementWrapper) {
			StatementWrapper statementWrapper = (StatementWrapper)statement;

			statement = statementWrapper.getStatement();
		}

		if (statement instanceof InvokeMethod ||
			statement instanceof RunAfters || statement instanceof RunBefores) {

			return ReflectionTestUtil.getFieldValue(statement, "target");
		}
		else if (statement instanceof ExpectException) {
			return inspectTarget(
				ReflectionTestUtil.<Statement>getFieldValue(statement, "next"));
		}
		else if (statement instanceof FailOnTimeout) {
			return inspectTarget(
				ReflectionTestUtil.<Statement>getFieldValue(
					statement, "originalStatement"));
		}

		throw new IllegalStateException("Unknow statement " + statement);
	}

	private final TestCallback<C, M> _testCallback;

}