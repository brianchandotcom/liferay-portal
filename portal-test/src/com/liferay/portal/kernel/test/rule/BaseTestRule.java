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

	@Override
	public final Statement apply(
		Statement statement, final Description description) {

		return new StatementWrapper(statement) {

			@Override
			public void evaluate() throws Throwable {
				String methodName = description.getMethodName();

				C c = null;
				M m = null;

				if (methodName == null) {
					c = beforeClass(description);
				}
				else {
					setInstance(inspectTarget(statement));

					m = beforeMethod(description);
				}

				try {
					invokeStatement(statement, description);
				}
				finally {
					if (methodName == null) {
						afterClass(description, c);
					}
					else {
						afterMethod(description, m);
					}
				}
			}

		};
	}

	public static abstract class StatementWrapper extends Statement {

		public StatementWrapper(Statement statement) {
			this.statement = statement;
		}

		public Statement getStatement() {
			return statement;
		}

		protected final Statement statement;

	}

	protected void afterClass(Description description, C c) throws Throwable {
	}

	protected void afterMethod(Description description, M m) throws Throwable {
	}

	protected C beforeClass(Description description) throws Throwable {
		return null;
	}

	protected M beforeMethod(Description description) throws Throwable {
		return null;
	}

	protected Object inspectTarget(Statement statement) {
		while (statement instanceof StatementWrapper) {
			StatementWrapper statementWrapper = (StatementWrapper)statement;

			statement = statementWrapper.getStatement();
		}

		if ((statement instanceof InvokeMethod) ||
			(statement instanceof RunAfters) ||
			(statement instanceof RunBefores)) {

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

	protected void invokeStatement(Statement statement, Description description)
		throws Throwable {

		statement.evaluate();
	}

	protected void setInstance(Object instance) {
	}

}