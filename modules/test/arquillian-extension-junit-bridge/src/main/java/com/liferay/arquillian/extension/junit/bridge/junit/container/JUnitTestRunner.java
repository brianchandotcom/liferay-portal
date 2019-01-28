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

package com.liferay.arquillian.extension.junit.bridge.junit.container;

import com.liferay.arquillian.extension.junit.bridge.junit.State;

import org.jboss.arquillian.container.test.spi.TestRunner;
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
public class JUnitTestRunner implements TestRunner {

	@Override
	public TestResult execute(Class<?> testClass, String methodName) {
		TestResult testResult = null;

		ExpectedExceptionHolder exceptionHolder = new ExpectedExceptionHolder();

		try {
			JUnitCore jUnitCore = new JUnitCore();

			jUnitCore.addListener(exceptionHolder);

			Result result = jUnitCore.run(
				Request.method(testClass, methodName));

			if (result.getFailureCount() > 0)
			{
				testResult = TestResult.failed(exceptionHolder.getException());
			}
			else if (result.getIgnoreCount() > 0)
			{
				testResult = TestResult.skipped(null);
			}
			else {
				testResult = TestResult.passed();
			}

			if (testResult.getThrowable() == null) {
				testResult.setThrowable(exceptionHolder.getException());
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

	private class ExpectedExceptionHolder extends RunListener {

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
		public void testFailure(Failure failure) throws Exception {
			_throwable = State.getTestException();

			Description description = failure.getDescription();

			Test test = description.getAnnotation(Test.class);

			if ((_throwable == null) &&
				((test == null) || Test.None.class.equals(test.expected()))) {

				_throwable = failure.getException();
			}
		}

		@Override
		public void testFinished(Description description) throws Exception {
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