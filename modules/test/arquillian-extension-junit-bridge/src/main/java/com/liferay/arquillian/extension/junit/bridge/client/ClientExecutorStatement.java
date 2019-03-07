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

package com.liferay.arquillian.extension.junit.bridge.client;

import com.liferay.arquillian.extension.junit.bridge.jmx.JMXTestRunnerMBean;
import com.liferay.petra.io.unsync.UnsyncByteArrayInputStream;

import java.io.InputStream;
import java.io.ObjectInputStream;

import java.lang.reflect.Method;

import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;

import org.junit.runners.model.Statement;

/**
 * @author Shuyang Zhou
 */
public class ClientExecutorStatement extends Statement {

	public ClientExecutorStatement(Object target, Method method) {
		Class<?> clazz = target.getClass();

		_className = clazz.getName();

		_methodName = method.getName();
	}

	@Override
	public void evaluate() throws Throwable {
		JMXTestRunnerMBean jmxTestRunnerMBean = JMXProxyUtil.newProxy(
			_objectName, JMXTestRunnerMBean.class);

		byte[] data = jmxTestRunnerMBean.runTestMethod(_className, _methodName);

		try (InputStream inputStream = new UnsyncByteArrayInputStream(data);
			ObjectInputStream oos = new ObjectInputStream(inputStream)) {

			Throwable throwable = (Throwable)oos.readObject();

			if (throwable != null) {
				throw throwable;
			}
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

	private final String _className;
	private final String _methodName;

}