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

	public JMXTestRunner(ClassLoader testClassLoader) {
		_testClassLoader = testClassLoader;
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

		return Serializer.toByteArray(result);
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

		TestResult result = null;

		try {
			TestRunner runner = TestRunners.getTestRunner(
				JMXTestRunner.class.getClassLoader());

			Class<?> testClass = _testClassLoader.loadClass(className);

			result = runner.execute(testClass, methodName);
		}
		catch (ClassNotFoundException cnfe) {
			result = TestResult.failed(cnfe);

			result.setEnd(System.currentTimeMillis());
		}

		return result;
	}

	private final ThreadLocal<String> _currentCall = new ThreadLocal<>();
	private final ClassLoader _testClassLoader;

}