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

package com.liferay.arquillian.extension.junit.bridge.protocol.jmx;

import com.liferay.arquillian.extension.junit.bridge.junit.State;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import javax.management.NotificationBroadcasterSupport;

import org.jboss.arquillian.test.spi.TestResult;

import org.junit.AssumptionViolatedException;
import org.junit.Test;
import org.junit.runner.Description;
import org.junit.runner.JUnitCore;
import org.junit.runner.Request;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;

/**
 * @author Matthew Tambara
 */
public class JMXTestRunner
	extends NotificationBroadcasterSupport implements JMXTestRunnerMBean {

	public JMXTestRunner(ClassLoader classLoader) {
		_classLoader = classLoader;
	}

	@Override
	public byte[] runTestMethod(String className, String methodName) {
		try {
			Class<?> testClass = _classLoader.loadClass(className);

			return _toByteArray(_execute(testClass, methodName));
		}
		catch (ClassNotFoundException cnfe) {
			TestResult testResult = TestResult.failed(cnfe);

			testResult.setEnd(System.currentTimeMillis());

			return _toByteArray(testResult);
		}
	}

	private static byte[] _toByteArray(TestResult testResult) {
		try (ByteArrayOutputStream byteArrayOutputStream =
				new ByteArrayOutputStream();
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(
				byteArrayOutputStream)) {

			objectOutputStream.writeObject(testResult);

			objectOutputStream.flush();

			return byteArrayOutputStream.toByteArray();
		}
		catch (IOException ioe) {
			throw new RuntimeException(
				"Unable to serialize object: " + testResult, ioe);
		}
	}

	private TestResult _execute(Class<?> testClass, String methodName) {
		TestResult testResult = null;

		ExceptionRunListener exceptionRunListener = new ExceptionRunListener();

		try {
			JUnitCore jUnitCore = new JUnitCore();

			jUnitCore.addListener(exceptionRunListener);

			Result result = jUnitCore.run(
				Request.method(testClass, methodName));

			if (result.getFailureCount() > 0) {
				testResult = TestResult.failed(
					exceptionRunListener.getException());
			}
			else if (result.getIgnoreCount() > 0) {
				testResult = TestResult.skipped(null);
			}
			else {
				testResult = TestResult.passed();
			}

			if (testResult.getThrowable() == null) {
				testResult.setThrowable(exceptionRunListener.getException());
			}
		}
		catch (Throwable t) {
			testResult = TestResult.failed(t);
		}

		Throwable throwable = testResult.getThrowable();

		if (throwable instanceof AssumptionViolatedException) {
			testResult = TestResult.skipped(throwable);
		}

		testResult.setEnd(System.currentTimeMillis());

		return testResult;
	}

	private final ClassLoader _classLoader;

	private class ExceptionRunListener extends RunListener {

		public Throwable getException() {
			return _throwable;
		}

		@Override
		public void testAssumptionFailure(Failure failure) {
			Throwable throwable = failure.getException();

			_throwable = new AssumptionViolatedException(
				throwable.getMessage());

			_throwable.setStackTrace(throwable.getStackTrace());
		}

		@Override
		public void testFailure(Failure failure) {
			_throwable = State.getTestException();

			Description description = failure.getDescription();

			Test test = description.getAnnotation(Test.class);

			if ((_throwable == null) &&
				((test == null) || Test.None.class.equals(test.expected()))) {

				_throwable = failure.getException();
			}
		}

		@Override
		public void testFinished(Description description) {
			Test test = description.getAnnotation(Test.class);

			if ((_throwable == null) && (test != null) &&
				!Test.None.class.equals(test.expected())) {

				_throwable = State.getTestException();
			}

			State.caughtTestException(null);
		}

		private Throwable _throwable;

	}

}