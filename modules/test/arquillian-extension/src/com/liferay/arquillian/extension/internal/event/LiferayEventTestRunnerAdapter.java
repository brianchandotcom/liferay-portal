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

package com.liferay.arquillian.extension.internal.event;

import com.liferay.portal.kernel.test.rule.executor.ClearThreadLocalExecutor;
import com.liferay.portal.kernel.test.rule.executor.DeleteAfterTestRunExecutor;
import com.liferay.portal.kernel.test.rule.executor.InitTestLiferayContextExecutor;
import com.liferay.portal.kernel.test.rule.executor.UniqueStringRandomizerBumperExecutor;
import com.liferay.portal.test.log.CaptureAppender;
import com.liferay.portal.test.rule.ExpectedLogs;
import com.liferay.portal.test.rule.executor.LogAssertionExecutor;

import java.lang.reflect.Method;

import org.jboss.arquillian.core.api.Instance;
import org.jboss.arquillian.core.api.annotation.Inject;
import org.jboss.arquillian.core.api.annotation.Observes;
import org.jboss.arquillian.core.spi.EventContext;
import org.jboss.arquillian.test.spi.TestClass;
import org.jboss.arquillian.test.spi.event.suite.After;
import org.jboss.arquillian.test.spi.event.suite.AfterClass;
import org.jboss.arquillian.test.spi.event.suite.Before;
import org.jboss.arquillian.test.spi.event.suite.BeforeClass;
import org.jboss.arquillian.test.spi.event.suite.ClassEvent;
import org.jboss.arquillian.test.spi.event.suite.Test;
import org.jboss.arquillian.test.spi.event.suite.TestEvent;

/**
 * @author Cristina González
 */
public class LiferayEventTestRunnerAdapter {

	public void after(@Observes EventContext<After> eventContext)
		throws Throwable {

		LogAssertionExecutor logAssertionExecutor =
			_logAssertionExecutorInstance.get();

		After afterEvent = eventContext.getEvent();

		ExpectedLogs expectedLogs = getAnnotation(afterEvent);

		CaptureAppender captureAppender = logAssertionExecutor.startAssert(
			expectedLogs);

		eventContext.proceed();

		DeleteAfterTestRunExecutor deleteAfterTestExecutor =
			_deleteAfterTestExecutorInstance.get();

		Method testMethod = afterEvent.getTestMethod();

		deleteAfterTestExecutor.deleteFieldsAfterTest(
			afterEvent.getTestInstance(), testMethod.getDeclaringClass());

		logAssertionExecutor.endAssert(expectedLogs, captureAppender);
	}

	public void afterClass(@Observes EventContext<AfterClass> eventContext)
		throws Throwable {

		LogAssertionExecutor logAssertionExecutor =
			_logAssertionExecutorInstance.get();

		ExpectedLogs expectedLogs = getAnnotation(eventContext.getEvent());

		CaptureAppender captureAppender = logAssertionExecutor.startAssert(
			expectedLogs);

		eventContext.proceed();

		ClearThreadLocalExecutor clearThreadLocalExecutor =
			_clearThreadLocalExecutorInstance.get();

		clearThreadLocalExecutor.clearThreadLocal();

		logAssertionExecutor.endAssert(expectedLogs, captureAppender);
	}

	public void before(@Observes EventContext<Before> eventContext)
		throws Throwable {

		LogAssertionExecutor logAssertionExecutor =
			_logAssertionExecutorInstance.get();

		ExpectedLogs expectedLogs = getAnnotation(eventContext.getEvent());

		CaptureAppender captureAppender = logAssertionExecutor.startAssert(
			expectedLogs);

		eventContext.proceed();

		logAssertionExecutor.endAssert(expectedLogs, captureAppender);
	}

	public void beforeClass(@Observes EventContext<BeforeClass> eventContext)
		throws Throwable {

		LogAssertionExecutor logAssertionExecutor =
			_logAssertionExecutorInstance.get();

		ExpectedLogs expectedLogs = getAnnotation(eventContext.getEvent());

		CaptureAppender captureAppender = logAssertionExecutor.startAssert(
			expectedLogs);

		InitTestLiferayContextExecutor initTestLiferayContextExecutor =
			_initTestLiferayContextExecutorInstance.get();

		initTestLiferayContextExecutor.init();

		UniqueStringRandomizerBumperExecutor
			uniqueStringRandomizerBumperExecutor =
				_uniqueStringRandomizerBumperExecutorInstance.get();

		uniqueStringRandomizerBumperExecutor.reset();

		eventContext.proceed();

		logAssertionExecutor.endAssert(expectedLogs, captureAppender);
	}

	public void test(@Observes EventContext<Test> eventContext)
		throws Throwable {

		LogAssertionExecutor logAssertionExecutor =
			_logAssertionExecutorInstance.get();

		ExpectedLogs expectedLogs = getAnnotation(eventContext.getEvent());

		CaptureAppender captureAppender = logAssertionExecutor.startAssert(
			expectedLogs);

		eventContext.proceed();

		logAssertionExecutor.endAssert(expectedLogs, captureAppender);
	}

	protected ExpectedLogs getAnnotation(ClassEvent classEvent) {
		TestClass testClass = classEvent.getTestClass();

		return testClass.getAnnotation(ExpectedLogs.class);
	}

	protected ExpectedLogs getAnnotation(Object object) {
		if (object instanceof ClassEvent) {
			return getAnnotation((ClassEvent)object);
		}
		else if (object instanceof TestEvent) {
			return getAnnotation((TestEvent)object);
		}

		throw new RuntimeException(
			"Object " + object + " is not a class event or test event");
	}

	protected ExpectedLogs getAnnotation(TestEvent testEvent) {
		Method method = testEvent.getTestMethod();

		ExpectedLogs expectedLogs = method.getAnnotation(ExpectedLogs.class);

		if (expectedLogs != null) {
			return expectedLogs;
		}

		return getAnnotation((ClassEvent)testEvent);
	}

	@Inject
	private Instance<ClearThreadLocalExecutor>
		_clearThreadLocalExecutorInstance;

	@Inject
	private Instance<DeleteAfterTestRunExecutor>
		_deleteAfterTestExecutorInstance;

	@Inject
	private Instance<InitTestLiferayContextExecutor>
		_initTestLiferayContextExecutorInstance;

	@Inject
	private Instance<LogAssertionExecutor> _logAssertionExecutorInstance;

	@Inject
	private Instance<UniqueStringRandomizerBumperExecutor>
		_uniqueStringRandomizerBumperExecutorInstance;

}