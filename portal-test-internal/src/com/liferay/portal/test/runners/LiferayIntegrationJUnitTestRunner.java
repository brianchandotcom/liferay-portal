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

import com.liferay.portal.kernel.util.ReflectionUtil;
import com.liferay.portal.log.CaptureAppender;
import com.liferay.portal.test.log.ConcurrentAssertUtil;
import com.liferay.portal.test.log.ExpectedLogsUtil;
import com.liferay.portal.test.log.LogAssertionUtil;
import com.liferay.portal.test.randomizerbumpers.UniqueStringRandomizerBumper;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import java.util.Collections;
import java.util.List;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.model.FrameworkMethod;
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

	protected static Statement logAssertStatement(
		final Statement statement, final Method method) {

		return new Statement() {

			@Override
			public void evaluate() throws Throwable {
				ConcurrentAssertUtil.startAssert();

				CaptureAppender captureAppender = ExpectedLogsUtil.startAssert(
					method);

				try {
					LogAssertionUtil.enableLogAssertion();

					statement.evaluate();
				}
				finally {
					ExpectedLogsUtil.endAssert(method, captureAppender);

					ConcurrentAssertUtil.endAssert();
				}
			}

		};
	}

	@Override
	protected Statement classBlock(RunNotifier notifier) {
		final Statement statement = super.classBlock(notifier);

		return new Statement() {

			@Override
			public void evaluate() throws Throwable {
				Thread currentThread = Thread.currentThread();

				Object inheritableThreadLocals =
					_INHERITABLE_THREAD_LOCALS_FIELD.get(currentThread);

				if (inheritableThreadLocals != null) {
					_INHERITABLE_THREAD_LOCALS_FIELD.set(
						currentThread,
						_CREATE_INHERITED_MAP_METHOD.invoke(
							null, inheritableThreadLocals));
				}

				Object threadLocals = _THREAD_LOCALS_FIELD.get(currentThread);

				_THREAD_LOCALS_FIELD.set(currentThread, null);

				try {
					statement.evaluate();
				}
				finally {
					_INHERITABLE_THREAD_LOCALS_FIELD.set(
						currentThread, inheritableThreadLocals);
					_THREAD_LOCALS_FIELD.set(currentThread, threadLocals);
				}
			}

		};
	}

	@Override
	protected List<TestRule> classRules() {
		List<TestRule> testRules = super.classRules();

		testRules.add(_logAssertTestRule);
		testRules.add(_uniqueStringRandomizerBumperTestRule);

		return testRules;
	}

	@Override
	protected Statement methodBlock(FrameworkMethod frameworkMethod) {
		return logAssertStatement(
			super.methodBlock(frameworkMethod), frameworkMethod.getMethod());
	}

	private static final Method _CREATE_INHERITED_MAP_METHOD;

	private static final Field _INHERITABLE_THREAD_LOCALS_FIELD;

	private static final Class<?> _THREAD_LOCAL_MAP_CLASS;

	private static final Field _THREAD_LOCALS_FIELD;

	static {
		try {
			_THREAD_LOCAL_MAP_CLASS = Class.forName(
				ThreadLocal.class.getName().concat("$ThreadLocalMap"));

			_CREATE_INHERITED_MAP_METHOD = ReflectionUtil.getDeclaredMethod(
				ThreadLocal.class, "createInheritedMap",
				_THREAD_LOCAL_MAP_CLASS);

			_INHERITABLE_THREAD_LOCALS_FIELD = ReflectionUtil.getDeclaredField(
				Thread.class, "inheritableThreadLocals");
			_THREAD_LOCALS_FIELD = ReflectionUtil.getDeclaredField(
				Thread.class, "threadLocals");
		}
		catch (Exception e) {
			throw new ExceptionInInitializerError(e);
		}
	}

	private final TestRule _logAssertTestRule = new TestRule() {

		@Override
		public Statement apply(Statement statement, Description description) {
			return logAssertStatement(statement, null);
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