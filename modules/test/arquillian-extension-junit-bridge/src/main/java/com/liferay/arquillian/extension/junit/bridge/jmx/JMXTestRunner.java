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

package com.liferay.arquillian.extension.junit.bridge.jmx;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import org.junit.AssumptionViolatedException;
import org.junit.runner.JUnitCore;
import org.junit.runner.Request;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;

/**
 * @author Matthew Tambara
 */
public class JMXTestRunner implements JMXTestRunnerMBean {

	public JMXTestRunner(ClassLoader classLoader) {
		_classLoader = classLoader;
	}

	@Override
	public byte[] runTestMethod(String className, String methodName) {
		Throwable throwable = null;

		ExceptionRunListener exceptionRunListener = new ExceptionRunListener();

		try {
			JUnitCore jUnitCore = new JUnitCore();

			jUnitCore.addListener(exceptionRunListener);

			jUnitCore.run(
				Request.method(_classLoader.loadClass(className), methodName));

			throwable = exceptionRunListener.getException();
		}
		catch (Throwable t) {
			throwable = t;
		}

		return _toByteArray(throwable);
	}

	private static byte[] _toByteArray(Throwable throwable) {
		try (ByteArrayOutputStream byteArrayOutputStream =
				new ByteArrayOutputStream();
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(
				byteArrayOutputStream)) {

			objectOutputStream.writeObject(throwable);

			objectOutputStream.flush();

			return byteArrayOutputStream.toByteArray();
		}
		catch (IOException ioe) {
			throw new RuntimeException(
				"Unable to serialize object: " + throwable, ioe);
		}
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
			_throwable = failure.getException();
		}

		private Throwable _throwable;

	}

}