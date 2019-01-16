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

import java.lang.reflect.Method;

import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import javax.management.JMException;
import javax.management.MBeanServer;
import javax.management.Notification;
import javax.management.NotificationBroadcasterSupport;
import javax.management.ObjectName;

import org.jboss.arquillian.container.test.spi.TestRunner;
import org.jboss.arquillian.container.test.spi.command.Command;
import org.jboss.arquillian.container.test.spi.util.TestRunners;
import org.jboss.arquillian.test.spi.TestResult;

/**
 * @author Matthew Tambara
 */
public class JMXTestRunner
	extends NotificationBroadcasterSupport implements JMXTestRunnerMBean {

	public JMXTestRunner(ClassLoader classLoader) {
		this(classLoader, JMXTestRunnerMBean.OBJECT_NAME);
	}

	public JMXTestRunner(ClassLoader testClassLoader, String objectName) {
		_testClassLoader = testClassLoader;

		_objectName = objectName;
	}

	@Override
	public void push(String eventId, byte[] command) {
		_events.put(eventId, Serializer.toObject(Command.class, command));
	}

	@Override
	public Command<?> receive() {
		return _events.remove(_currentCall.get());
	}

	public ObjectName registerMBean(MBeanServer mBeanServer)
		throws JMException {

		ObjectName objectName = new ObjectName(_objectName);

		mBeanServer.registerMBean(this, objectName);

		localMBeanServer = mBeanServer;

		return objectName;
	}

	@Override
	public byte[] runTestMethod(String className, String methodName) {
		TestResult result = _runTestMethodInternal(className, methodName);

		return Serializer.toByteArray(result);
	}

	@Override
	public byte[] runTestMethod(
		String className, String methodName,
		Map<String, String> protocolProps) {

		try {
			Class<?> clazz = JMXTestRunner.class;

			Method method = AccessController.doPrivileged(
				new PrivilegedExceptionAction<Method>() {

					@Override
					public Method run() throws NoSuchMethodException {
						return clazz.getMethod(
							"runTestMethod", String.class, String.class);
					}

				});

			if (method.getDeclaringClass() != JMXTestRunner.class) {
				return runTestMethod(className, methodName);
			}
		}
		catch (PrivilegedActionException pae) {
		}

		TestResult result = _runTestMethodInternal(className, methodName);

		return Serializer.toByteArray(result);
	}

	@Override
	public void send(Command<?> command) {
		Notification notification = new Notification(
			"arquillian-command", this, _integer.incrementAndGet(),
			_currentCall.get());

		notification.setUserData(Serializer.toByteArray(command));

		sendNotification(notification);
	}

	public void unregisterMBean(MBeanServer mBeanServer) throws JMException {
		ObjectName objectName = new ObjectName(_objectName);

		if (mBeanServer.isRegistered(objectName)) {
			mBeanServer.unregisterMBean(objectName);
		}

		localMBeanServer = null;
	}

	protected String getCurrentCall() {
		return _currentCall.get();
	}

	protected void setCurrentCall(String current) {
		_currentCall.set(current);
	}

	protected void setExposedTestRunnerForTest(TestRunner mockTestRunner) {
		_mockTestRunner = mockTestRunner;
	}

	protected static MBeanServer localMBeanServer;

	private TestResult _runTestMethodInternal(
		String className, String methodName) {

		_currentCall.set(className + methodName);
		TestResult result = new TestResult();

		try {
			TestRunner runner = _mockTestRunner;

			if (runner == null) {
				runner = TestRunners.getTestRunner(getClass().getClassLoader());
			}

			Class<?> testClass = _testClassLoader.loadClass(className);

			result = runner.execute(testClass, methodName);
		}
		catch (Throwable th) {
			result.setStatus(TestResult.Status.FAILED);
			result.setEnd(System.currentTimeMillis());
			result.setThrowable(th);
		}

		return result;
	}

	private final ThreadLocal<String> _currentCall = new ThreadLocal<>();
	private final ConcurrentHashMap<String, Command<?>> _events =
		new ConcurrentHashMap<>();
	private final AtomicInteger _integer = new AtomicInteger();
	private TestRunner _mockTestRunner;
	private final String _objectName;
	private ClassLoader _testClassLoader;

}