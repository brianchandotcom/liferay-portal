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

package com.liferay.arquillian.extension.junit.bridge.listener;

import com.liferay.arquillian.extension.junit.bridge.event.Event;
import com.liferay.arquillian.extension.junit.bridge.event.TestEvent;
import com.liferay.arquillian.extension.junit.bridge.protocol.jmx.JMXTestRunnerMBean;
import com.liferay.arquillian.extension.junit.bridge.remote.manager.Registry;
import com.liferay.petra.io.unsync.UnsyncByteArrayInputStream;

import java.io.InputStream;
import java.io.ObjectInputStream;

import java.lang.reflect.Method;

import javax.management.MBeanServerConnection;
import javax.management.MBeanServerInvocationHandler;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;

import org.jboss.arquillian.test.spi.TestResult;

/**
 * @author Matthew Tambara
 */
public class ClientExecutorEventListener implements EventListener {

	public ClientExecutorEventListener(Registry registry) {
		_registry = registry;
	}

	@Override
	public void handleEvent(Event event) {
		if (event instanceof TestEvent) {
			_handleTestEvent((TestEvent)event);
		}
	}

	private void _handleTestEvent(TestEvent testEvent) {
		Object target = testEvent.getTarget();

		Class<?> testClass = target.getClass();

		Method method = testEvent.getMethod();

		JMXTestRunnerMBean jmxTestRunnerMBean =
			MBeanServerInvocationHandler.newProxyInstance(
				_registry.get(MBeanServerConnection.class), _objectName,
				JMXTestRunnerMBean.class, false);

		try {
			byte[] data = jmxTestRunnerMBean.runTestMethod(
				testClass.getName(), method.getName());

			try (InputStream inputStream = new UnsyncByteArrayInputStream(data);
				ObjectInputStream oos = new ObjectInputStream(inputStream)) {

				TestResult testResult = (TestResult)oos.readObject();

				testResult.setEnd(System.currentTimeMillis());

				_registry.set(TestResult.class, testResult);
			}
		}
		catch (Throwable t) {
			TestResult testResult = TestResult.failed(t);

			testResult.setEnd(System.currentTimeMillis());

			_registry.set(TestResult.class, testResult);
		}
	}

	private static final ObjectName _objectName;

	static {
		try {
			_objectName = new ObjectName(JMXTestRunnerMBean.OBJECT_NAME);
		}
		catch (MalformedObjectNameException mone) {
			throw new ExceptionInInitializerError(mone);
		}
	}

	private final Registry _registry;

}