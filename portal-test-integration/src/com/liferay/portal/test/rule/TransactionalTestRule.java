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
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.StatementWrapper;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.TransactionConfig;
import com.liferay.portal.kernel.transaction.TransactionInvokerUtil;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceReference;

import java.io.Closeable;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;
import java.util.concurrent.Callable;

import org.junit.Assert;
import org.junit.internal.runners.statements.RunAfters;
import org.junit.internal.runners.statements.RunBefores;
import org.junit.rules.RunRules;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;

/**
 * @author Shuyang Zhou
 */
public class TransactionalTestRule implements TestRule {

	public static final TransactionalTestRule INSTANCE =
		new TransactionalTestRule(Propagation.SUPPORTS, null);

	public TransactionalTestRule(Propagation propagation) {
		this(propagation, null);
	}

	public TransactionalTestRule(
		Propagation propagation, String originBundleSymbolicName) {

		_transactionConfig = TransactionConfig.Factory.create(
			propagation,
			new Class<?>[] {PortalException.class, SystemException.class});
		_originBundleSymbolicName = originBundleSymbolicName;
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
					try (Closeable closeable = _installTransactionExecutor(
							_originBundleSymbolicName)) {

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
						frameworkMethod.getAnnotation(Transactional.class)),
					_originBundleSymbolicName));
		}

		ReflectionTestUtil.setFieldValue(statement, name, newFrameworkMethods);
	}

	protected static class TransactionalFrameworkMethod
		extends FrameworkMethod {

		@Override
		public Object invokeExplosively(
				final Object target, final Object... params)
			throws Throwable {

			try (Closeable closeable = _installTransactionExecutor(
					_originBundleSymbolicName)) {

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
		}

		protected TransactionalFrameworkMethod(
			Method method, TransactionConfig transactionConfig,
			String originBundleSymbolicName) {

			super(method);

			_transactionConfig = transactionConfig;
			_originBundleSymbolicName = originBundleSymbolicName;
		}

		private final String _originBundleSymbolicName;
		private final TransactionConfig _transactionConfig;

	}

	private static Closeable _installTransactionExecutor(
			String originBundleSymbolicName)
		throws Exception {

		if (originBundleSymbolicName == null) {
			return () -> {
			};
		}

		ClassLoader classLoader = PortalClassLoaderUtil.getClassLoader();

		Class<?> clazz = classLoader.loadClass(
			"com.liferay.portal.spring.transaction." +
				"TransactionExecutorThreadLocal");

		Field field = clazz.getDeclaredField("_transactionExecutorThreadLocal");

		field.setAccessible(true);

		ThreadLocal<Deque<Object>> transactionExecutorsThreadLocal =
			(ThreadLocal<Deque<Object>>)field.get(null);

		Deque<Object> transactionExecutors =
			transactionExecutorsThreadLocal.get();

		Registry registry = RegistryUtil.getRegistry();

		ServiceReference<?>[] serviceReferences =
			registry.getAllServiceReferences(
				"com.liferay.portal.spring.transaction.TransactionExecutor",
				"(origin.bundle.symbolic.name=" + originBundleSymbolicName +
					")");

		Assert.assertEquals(
			StringBundler.concat(
				"Expected 1 TransactionExecutor for ", originBundleSymbolicName,
				", actually have ", Arrays.toString(serviceReferences)),
			1, serviceReferences.length);

		ServiceReference<?> serviceReference = serviceReferences[0];

		Object portletTransactionExecutor = registry.getService(
			serviceReference);

		if (portletTransactionExecutor == transactionExecutors.peek()) {
			return () -> {
			};
		}

		transactionExecutors.push(portletTransactionExecutor);

		return () -> {
			transactionExecutors.pop();

			registry.ungetService(serviceReference);
		};
	}

	private final String _originBundleSymbolicName;
	private final TransactionConfig _transactionConfig;

}