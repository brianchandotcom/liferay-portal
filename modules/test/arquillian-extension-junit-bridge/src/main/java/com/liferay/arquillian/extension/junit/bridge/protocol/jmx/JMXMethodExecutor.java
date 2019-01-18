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

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;

import java.lang.reflect.Method;

import javax.management.MBeanServerConnection;
import javax.management.MBeanServerInvocationHandler;
import javax.management.ObjectName;

import org.jboss.arquillian.container.test.spi.ContainerMethodExecutor;
import org.jboss.arquillian.test.spi.TestMethodExecutor;
import org.jboss.arquillian.test.spi.TestResult;

/**
 * @author Matthew Tambara
 */
public class JMXMethodExecutor implements ContainerMethodExecutor {

	public JMXMethodExecutor(MBeanServerConnection mBeanServerConnection) {
		_mBeanServerConnection = mBeanServerConnection;
	}

	@Override
	public TestResult invoke(TestMethodExecutor testMethodExecutor) {
		if (testMethodExecutor == null) {
			throw new IllegalArgumentException("TestMethodExecutor null");
		}

		Object instance = testMethodExecutor.getInstance();

		Class<?> testClass = instance.getClass();

		Method testMethod = testMethodExecutor.getMethod();

		TestResult result = null;

		try {
			ObjectName objectName = new ObjectName(JMXTestRunner.OBJECT_NAME);

			JMXTestRunnerMBean testRunner = _getMBeanProxy(
				objectName, JMXTestRunnerMBean.class);

			byte[] data = testRunner.runTestMethod(
				testClass.getName(), testMethod.getName());

			try (InputStream is = new ByteArrayInputStream(data);
				ObjectInputStream oos = new ObjectInputStream(is)) {

				return (TestResult)oos.readObject();
			}
		}
		catch (Exception e) {
			result = TestResult.failed(e);
		}
		finally {
			result.setEnd(System.currentTimeMillis());
		}

		return result;
	}

	private <T> T _getMBeanProxy(ObjectName objectName, Class<T> clazz) {
		return MBeanServerInvocationHandler.newProxyInstance(
			_mBeanServerConnection, objectName, clazz, false);
	}

	private final MBeanServerConnection _mBeanServerConnection;

}