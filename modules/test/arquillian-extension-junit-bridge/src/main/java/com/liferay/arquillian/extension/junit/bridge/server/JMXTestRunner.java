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

package com.liferay.arquillian.extension.junit.bridge.server;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import org.junit.AssumptionViolatedException;
import org.junit.Test;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.TestClass;

/**
 * @author Matthew Tambara
 */
public class JMXTestRunner implements JMXTestRunnerMBean {

	public JMXTestRunner(ClassLoader classLoader) {
		_classLoader = classLoader;
	}

	@Override
	public byte[] runTestMethod(String className, String methodName) {
		try {
			TestClass testClass = new TestClass(
				_classLoader.loadClass(className));

			for (FrameworkMethod frameworkMethod :
					testClass.getAnnotatedMethods(Test.class)) {

				if (!methodName.equals(frameworkMethod.getName())) {
					continue;
				}

				TestExecutorUtil.execute(
					testClass, frameworkMethod.getMethod());
			}
		}
		catch (Throwable t) {
			return _toByteArray(t);
		}

		return _PASSED;
	}

	private static byte[] _toByteArray(Throwable throwable) {
		if (throwable instanceof AssumptionViolatedException) {

			// To neutralize the non-serializable Matcher field
			// inside AssumptionViolatedException

			AssumptionViolatedException ave = new AssumptionViolatedException(
				throwable.getMessage());

			ave.setStackTrace(throwable.getStackTrace());

			throwable = ave;
		}

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

	private static final byte[] _PASSED = _toByteArray(null);

	private final ClassLoader _classLoader;

}