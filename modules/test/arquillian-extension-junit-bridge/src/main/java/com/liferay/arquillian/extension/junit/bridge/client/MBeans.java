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

import java.util.Collections;
import java.util.Iterator;
import java.util.Set;

import javax.management.MBeanServerConnection;
import javax.management.MBeanServerInvocationHandler;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

import org.osgi.jmx.framework.FrameworkMBean;

/**
 * @author Shuyang Zhou
 */
public class MBeans {

	public static FrameworkMBean getFrameworkMBean() {
		return _frameworkMBean;
	}

	private static final FrameworkMBean _frameworkMBean;

	static {
		try {
			JMXServiceURL liferayJMXServiceURL = new JMXServiceURL(
				"service:jmx:rmi:///jndi/rmi://localhost:8099/jmxrmi");

			JMXConnector jmxConnector = JMXConnectorFactory.connect(
				liferayJMXServiceURL,
				Collections.singletonMap(
					JMXConnector.CREDENTIALS, new String[] {"", ""}));

			MBeanServerConnection mBeanServerConnection =
				jmxConnector.getMBeanServerConnection();

			Set<ObjectName> objectNames = mBeanServerConnection.queryNames(
				new ObjectName("osgi.core:type=framework,*"), null);

			Iterator<ObjectName> iterator = objectNames.iterator();

			if (!iterator.hasNext()) {
				throw new IllegalStateException(
					"FrameworkMBean is unavailable");
			}

			_frameworkMBean = MBeanServerInvocationHandler.newProxyInstance(
				mBeanServerConnection, iterator.next(), FrameworkMBean.class,
				false);
		}
		catch (Exception e) {
			throw new ExceptionInInitializerError(e);
		}
	}

}