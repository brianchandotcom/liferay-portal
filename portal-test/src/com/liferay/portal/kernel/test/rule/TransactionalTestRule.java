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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.BaseTestRule.StatementWrapper;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.TransactionConfig;
import com.liferay.portal.kernel.transaction.TransactionInvokerUtil;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.ReflectionUtil;

import java.lang.reflect.Method;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import org.junit.internal.runners.statements.RunAfters;
import org.junit.internal.runners.statements.RunBefores;
import org.junit.rules.RunRules;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;

/**
 * @author     Shuyang Zhou
 * @deprecated As of 3.0.0, see {@link
 *             com.liferay.portal.test.rule.TransactionalTestRule}
 */
@Deprecated
public class TransactionalTestRule implements TestRule {

	public static final TransactionalTestRule INSTANCE =
		new TransactionalTestRule();

	public TransactionalTestRule() {
		this(Propagation.SUPPORTS);
	}

	public TransactionalTestRule(Propagation propagation) {
		_transactionConfig = TransactionConfig.Factory.create(
			propagation,
			new Class<?>[] {PortalException.class, SystemException.class});
	}

	@Override
	public Statement apply(Statement statement, final Description description) {
		Statement currentStatement = statement;

		while (true) {
			if (currentStatement instanceof StatementWrapper) {
				StatementWrapper statementWrapper =
					(StatementWrapper)currentStatement;

				currentStatement = statementWrapper.getStatement();

				continue;
			}

			if (currentStatement instanceof RunRules) {
				currentStatement = ReflectionTestUtil.getFieldValue(
					currentStatement, "statement");

				continue;
			}

			if (currentStatement instanceof RunBefores) {
				replaceFrameworkMethods(currentStatement, "befores");

				currentStatement = ReflectionTestUtil.getFieldValue(
					currentStatement, "next");

				continue;
			}

			if (currentStatement instanceof RunAfters) {
				replaceFrameworkMethods(currentStatement, "afters");

				currentStatement = ReflectionTestUtil.getFieldValue(
					currentStatement, "next");

				continue;
			}

			return new StatementWrapper(statement) {

				@Override
				public void evaluate() throws Throwable {
					TransactionInvokerUtil.invoke(
						getTransactionConfig(
							description.getAnnotation(Transactional.class)),
						new Callable<Void>() {

							@Override
							public Void call() throws Exception {
								try {
									statement.evaluate();
								}
								catch (Throwable t) {
									ReflectionUtil.throwException(t);
								}

								return null;
							}

						});
				}

			};
		}
	}

	public TransactionConfig getTransactionConfig(Transactional transactional) {
		if (transactional != null) {
			return TransactionConfig.Factory.create(
				transactional.isolation(), transactional.propagation(),
				transactional.readOnly(), transactional.timeout(),
				transactional.rollbackFor(),
				transactional.rollbackForClassName(),
				transactional.noRollbackFor(),
				transactional.noRollbackForClassName());
		}

		return _transactionConfig;
	}

	protected void replaceFrameworkMethods(Statement statement, String name) {
		List<FrameworkMethod> newFrameworkMethods = new ArrayList<>();

		List<FrameworkMethod> frameworkMethods =
			ReflectionTestUtil.<List<FrameworkMethod>>getFieldValue(
				statement, name);

		for (FrameworkMethod frameworkMethod : frameworkMethods) {
			if (frameworkMethod instanceof TransactionalFrameworkMethod) {
				newFrameworkMethods.add(frameworkMethod);

				continue;
			}

			newFrameworkMethods.add(
				new TransactionalFrameworkMethod(
					frameworkMethod.getMethod(),
					getTransactionConfig(
						frameworkMethod.getAnnotation(Transactional.class))));
		}

		ReflectionTestUtil.setFieldValue(statement, name, newFrameworkMethods);
	}

	protected static class TransactionalFrameworkMethod
		extends FrameworkMethod {

		@Override
		public Object invokeExplosively(
				final Object target, final Object... params)
			throws Throwable {

			return TransactionInvokerUtil.invoke(
				_transactionConfig,
				new Callable<Object>() {

					@Override
					public Object call() throws Exception {
						try {
							return TransactionalFrameworkMethod.super.
								invokeExplosively(target, params);
						}
						catch (Throwable t) {
							ReflectionUtil.throwException(t);
						}

						return null;
					}

				});
		}

		protected TransactionalFrameworkMethod(
			Method method, TransactionConfig transactionConfig) {

			super(method);

			_transactionConfig = transactionConfig;
		}

		private final TransactionConfig _transactionConfig;

	}

	private final TransactionConfig _transactionConfig;

}