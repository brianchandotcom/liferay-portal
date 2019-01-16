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

import java.util.Map;

import javax.management.MBeanServerConnection;
import javax.management.MBeanServerInvocationHandler;
import javax.management.Notification;
import javax.management.NotificationListener;
import javax.management.ObjectName;

import org.jboss.arquillian.container.test.spi.ContainerMethodExecutor;
import org.jboss.arquillian.container.test.spi.command.Command;
import org.jboss.arquillian.container.test.spi.command.CommandCallback;
import org.jboss.arquillian.test.spi.TestMethodExecutor;
import org.jboss.arquillian.test.spi.TestResult;

/**
 * @author Matthew Tambara
 */
public class JMXMethodExecutor implements ContainerMethodExecutor {

	public JMXMethodExecutor(
		MBeanServerConnection mBeanServerConnection,
		CommandCallback commandCallback) {

		this(
			mBeanServerConnection, commandCallback, JMXTestRunner.OBJECT_NAME,
			null);
	}

	public JMXMethodExecutor(
		MBeanServerConnection mBeanServerConnection,
		CommandCallback commandCallback, String objectName,
		Map<String, String> protocolProperties) {

		_mBeanServerConnection = mBeanServerConnection;
		_commandCallback = commandCallback;
		_objectName = objectName;
		_protocolProperties = protocolProperties;
	}

	@Override
	public TestResult invoke(TestMethodExecutor testMethodExecutor) {
		if (testMethodExecutor == null) {
			throw new IllegalArgumentException("TestMethodExecutor null");
		}

		Object instance = testMethodExecutor.getInstance();

		Class<?> testClass = instance.getClass();

		Method testMethod = testMethodExecutor.getMethod();

		NotificationListener commandListener = null;

		ObjectName objectName = null;

		TestResult result = null;

		try {
			objectName = new ObjectName(_objectName);

			commandListener = new CallbackNotificationListener(objectName);

			_mBeanServerConnection.addNotificationListener(
				objectName, commandListener, null, null);

			JMXTestRunnerMBean testRunner = _getMBeanProxy(
				objectName, JMXTestRunnerMBean.class);

			result = Serializer.toObject(
				TestResult.class,
				testRunner.runTestMethod(
					testClass.getName(), testMethod.getName(),
					_protocolProperties));
		}
		catch (Throwable t) {
			result = new TestResult(TestResult.Status.FAILED);

			result.setThrowable(t);
		}
		finally {
			result.setEnd(System.currentTimeMillis());

			if ((objectName != null) && (commandListener != null)) {
				try {
					_mBeanServerConnection.removeNotificationListener(
						objectName, commandListener);
				}
				catch (Exception e) {
				}
			}
		}

		return result;
	}

	private <T> T _getMBeanProxy(ObjectName objectName, Class<T> clazz) {
		return MBeanServerInvocationHandler.newProxyInstance(
			_mBeanServerConnection, objectName, clazz, false);
	}

	private final CommandCallback _commandCallback;
	private final MBeanServerConnection _mBeanServerConnection;
	private final String _objectName;
	private final Map<String, String> _protocolProperties;

	private class CallbackNotificationListener implements NotificationListener {

		public CallbackNotificationListener(ObjectName serviceName) {
			_serviceName = serviceName;
		}

		@Override
		public void handleNotification(
			Notification notification, Object handback) {

			String message = notification.getMessage();

			Command<?> command = Serializer.toObject(
				Command.class, (byte[])notification.getUserData());

			_commandCallback.fired(command);

			try {
				_mBeanServerConnection.invoke(
					_serviceName, "push",
					new Object[] {message, Serializer.toByteArray(command)},
					new String[] {
						String.class.getName(), byte[].class.getName()
					});
			}
			catch (Exception e) {
				throw new RuntimeException(
					"Could not return command result for command " + command,
					e);
			}
		}

		private final ObjectName _serviceName;

	}

}