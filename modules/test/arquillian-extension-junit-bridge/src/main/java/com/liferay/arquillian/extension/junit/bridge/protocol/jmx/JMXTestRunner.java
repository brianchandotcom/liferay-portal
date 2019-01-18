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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import javax.management.JMException;
import javax.management.MBeanServer;
import javax.management.NotificationBroadcasterSupport;
import javax.management.ObjectName;

import org.jboss.arquillian.container.test.spi.TestRunner;
import org.jboss.arquillian.container.test.spi.util.TestRunners;
import org.jboss.arquillian.test.spi.TestResult;

/**
 * @author Matthew Tambara
 */
public class JMXTestRunner
	extends NotificationBroadcasterSupport implements JMXTestRunnerMBean {

	public JMXTestRunner(ClassLoader classLoader) {
		_classLoader = classLoader;
	}

	public ObjectName registerMBean(MBeanServer mBeanServer)
		throws JMException {

		ObjectName objectName = new ObjectName(JMXTestRunnerMBean.OBJECT_NAME);

		mBeanServer.registerMBean(this, objectName);

		return objectName;
	}

	@Override
	public byte[] runTestMethod(String className, String methodName) {
		TestResult result = _runTestMethodInternal(className, methodName);

		try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(baos)) {

			oos.writeObject(result);
			oos.flush();

			return baos.toByteArray();
		}
		catch (IOException ioe) {
			throw new RuntimeException(
				"Could not serialize object: " + result, ioe);
		}
	}

	public void unregisterMBean(MBeanServer mBeanServer) throws JMException {
		ObjectName objectName = new ObjectName(JMXTestRunnerMBean.OBJECT_NAME);

		if (mBeanServer.isRegistered(objectName)) {
			mBeanServer.unregisterMBean(objectName);
		}
	}

	private TestResult _runTestMethodInternal(
		String className, String methodName) {

		_currentCall.set(className + methodName);

		try {
			TestRunner runner = TestRunners.getTestRunner(
				JMXTestRunner.class.getClassLoader());

			Class<?> testClass = _classLoader.loadClass(className);

			return runner.execute(testClass, methodName);
		}
		catch (ClassNotFoundException cnfe) {
			TestResult testResult = TestResult.failed(cnfe);

			testResult.setEnd(System.currentTimeMillis());

			return testResult;
		}
	}

	private final ClassLoader _classLoader;
	private final ThreadLocal<String> _currentCall = new ThreadLocal<>();

}