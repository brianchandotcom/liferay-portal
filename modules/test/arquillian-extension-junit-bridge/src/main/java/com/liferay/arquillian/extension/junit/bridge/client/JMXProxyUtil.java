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

import java.io.IOException;

import java.util.Collections;
import java.util.Iterator;
import java.util.Set;

import javax.management.MBeanServerConnection;
import javax.management.MBeanServerInvocationHandler;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

/**
 * @author Shuyang Zhou
 */
public class JMXProxyUtil {

	public static <T> T newProxy(ObjectName objectName, Class<T> interfaceClass)
		throws IOException {

		if (objectName.isPattern()) {
			Set<ObjectName> objectNames = _mBeanServerConnection.queryNames(
				objectName, null);

			Iterator<ObjectName> iterator = objectNames.iterator();

			objectName = iterator.next();
		}

		return MBeanServerInvocationHandler.newProxyInstance(
			_mBeanServerConnection, objectName, interfaceClass, false);
	}

	private static final MBeanServerConnection _mBeanServerConnection;

	static {
		try {
			JMXServiceURL liferayJMXServiceURL = new JMXServiceURL(
				"service:jmx:rmi:///jndi/rmi://localhost:8099/jmxrmi");

			JMXConnector jmxConnector = JMXConnectorFactory.connect(
				liferayJMXServiceURL,
				Collections.singletonMap(
					JMXConnector.CREDENTIALS, new String[] {"", ""}));

			_mBeanServerConnection = jmxConnector.getMBeanServerConnection();
		}
		catch (Exception e) {
			throw new ExceptionInInitializerError(e);
		}
	}

}