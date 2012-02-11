/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.test;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ReflectionUtil;

import java.lang.reflect.Method;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Miguel Pastor
 */
public class TestContextHandler {
	public TestContextHandler(Class<?> clazz) {
		_testContext = new TestContext(clazz);

		registerExecutionListeners(_findExecutionTestListeners(clazz));
	}

	public void registerExecutionListeners(
		ExecutionTestListener ... executionTestListeners) {

		for (ExecutionTestListener execTestListener:executionTestListeners) {
			_executionTestListeners.add(execTestListener);
		}
	}

	public void runAfterTestMethod(Object testInstance, Method method) {
		for (ExecutionTestListener execTestListener:_executionTestListeners) {
			execTestListener.runAfterTest(_testContext);
		}
	}

	public void runBeforeTestMethod(Object testInstance, Method method) {

		_testContext.updateState(testInstance, method);

		for (ExecutionTestListener execTestListener:_executionTestListeners) {
			execTestListener.runBeforeTest(_testContext);
		}
	}

	private ExecutionTestListener[] _findExecutionTestListeners(
		Class<?> clazz) {

		Class<ExecutionTestListeners> annotationType =
			ExecutionTestListeners.class;

		Class<?> declaringClass =
			ReflectionUtil.findDeclaringClassAnnotation(annotationType, clazz);

		List<Class<? extends ExecutionTestListener>> allListenerClasses =
			new ArrayList<Class<? extends ExecutionTestListener>>();

		while (declaringClass != null) {

			ExecutionTestListeners executionTestListeners =
				declaringClass.getAnnotation(annotationType);

			Class<? extends ExecutionTestListener>[] listenerClasses =
				executionTestListeners.listeners();

			if (!ArrayUtil.isEmpty(listenerClasses)) {
				allListenerClasses.addAll(Arrays.asList(listenerClasses));
			}

			declaringClass = ReflectionUtil.findDeclaringClassAnnotation(
				annotationType, declaringClass.getSuperclass());
		}

		ExecutionTestListener[] executionTestListeners =
			new ExecutionTestListener[allListenerClasses.size()];

		for (int i = 0; i < executionTestListeners.length; i++) {
			Class<? extends ExecutionTestListener> listenerClass = null;

			try {
				listenerClass = allListenerClasses.get(i);

				executionTestListeners[i] =
					(ExecutionTestListener) ReflectionUtil.instantiateClass(
						listenerClass);

			} catch (Exception e) {
				_log.error(
					"Class " + listenerClass +
						" cannot be instancited. No registered listener");
			}
		}

		return executionTestListeners;
	}

	private static Log _log = LogFactoryUtil.getLog(TestContextHandler.class);

	private List<ExecutionTestListener> _executionTestListeners =
		new ArrayList<ExecutionTestListener>();
	private TestContext _testContext;

}