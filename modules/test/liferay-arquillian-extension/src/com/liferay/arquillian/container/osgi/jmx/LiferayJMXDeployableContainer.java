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

package com.liferay.arquillian.container.osgi.jmx;

import com.liferay.arquillian.util.PropsUtil;
import com.liferay.portal.kernel.util.GetterUtil;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import javax.management.MBeanServerConnection;
import javax.management.ObjectName;

import org.jboss.arquillian.container.osgi.jmx.JMXContainerConfiguration;
import org.jboss.arquillian.container.osgi.jmx.JMXDeployableContainer;
import org.jboss.arquillian.container.spi.client.container.LifecycleException;

import org.osgi.jmx.framework.BundleStateMBean;
import org.osgi.jmx.framework.FrameworkMBean;
import org.osgi.jmx.framework.ServiceStateMBean;

/**
 * @author Shuyang Zhou
 */
public class LiferayJMXDeployableContainer
	extends JMXDeployableContainer<JMXContainerConfiguration> {

	public static final String DEFAULT_JMX_SERVICE_URL =
		"service:jmx:rmi:///jndi/rmi://localhost:8099/jmxrmi";

	public static final long DEFAULT_JMX_TIMEOUT = 30;

	@Override
	public Class<JMXContainerConfiguration> getConfigurationClass() {
		return JMXContainerConfiguration.class;
	}

	@Override
	public void setup(JMXContainerConfiguration config) {
		config.setAutostartBundle(true);
		config.setJmxPassword(
			GetterUtil.getString(PropsUtil.get("jmx.password")));
		config.setJmxServiceURL(
			GetterUtil.getString(
				PropsUtil.get("jmx.url"), DEFAULT_JMX_SERVICE_URL));
		config.setJmxUsername(
			GetterUtil.getString(PropsUtil.get("jmx.username")));

		super.setup(config);
	}

	@Override
	public void start() throws LifecycleException {
		long timeout = GetterUtil.getLong(
			PropsUtil.get("jmx.timeout"), DEFAULT_JMX_TIMEOUT);

		MBeanServerConnection mbeanServer = null;

		try {
			mbeanServer = getMBeanServerConnection(timeout, TimeUnit.SECONDS);

			mbeanServerInstance.set(mbeanServer);
		}
		catch (TimeoutException te) {
			throw new LifecycleException(
				"Unable to connect to MBeanServer", te);
		}

		try {
			bundleStateMBean = getMBeanProxy(
				mbeanServer, new ObjectName("osgi.core:type=bundleState,*"),
				BundleStateMBean.class, timeout, TimeUnit.SECONDS);
			frameworkMBean = getMBeanProxy(
				mbeanServer, new ObjectName("osgi.core:type=framework,*"),
				FrameworkMBean.class, timeout, TimeUnit.SECONDS);
			serviceStateMBean = getMBeanProxy(
				mbeanServer, new ObjectName("osgi.core:type=serviceState,*"),
				ServiceStateMBean.class, timeout, TimeUnit.SECONDS);
		}
		catch (Exception e) {
			throw new LifecycleException("Unable to start container", e);
		}
	}

	@Override
	public void stop() {
	}

}