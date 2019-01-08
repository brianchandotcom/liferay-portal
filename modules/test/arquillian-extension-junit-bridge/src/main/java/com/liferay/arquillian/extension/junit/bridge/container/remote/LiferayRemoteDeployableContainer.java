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

package com.liferay.arquillian.extension.junit.bridge.container.remote;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import javax.management.MBeanServerConnection;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;

import org.jboss.arquillian.container.osgi.jmx.JMXDeployableContainer;
import org.jboss.arquillian.container.spi.client.container.DeploymentException;
import org.jboss.arquillian.container.spi.client.container.LifecycleException;
import org.jboss.arquillian.container.spi.client.protocol.metadata.HTTPContext;
import org.jboss.arquillian.container.spi.client.protocol.metadata.ProtocolMetaData;
import org.jboss.shrinkwrap.api.Archive;

import org.osgi.jmx.framework.BundleStateMBean;
import org.osgi.jmx.framework.FrameworkMBean;
import org.osgi.jmx.framework.ServiceStateMBean;

/**
 * @author Preston Crary
 */
public class LiferayRemoteDeployableContainer
	<T extends LiferayRemoteContainerConfiguration>
		extends JMXDeployableContainer<T> {

	@Override
	public ProtocolMetaData deploy(Archive<?> archive)
		throws DeploymentException {

		ProtocolMetaData protocolMetaData = super.deploy(archive);

		protocolMetaData.addContext(
			new HTTPContext(
				_liferayRemoteContainerConfiguration.getHttpHost(),
				_liferayRemoteContainerConfiguration.getHttpPort()));

		return protocolMetaData;
	}

	@Override
	@SuppressWarnings("unchecked")
	public Class<T> getConfigurationClass() {
		return (Class<T>)LiferayRemoteContainerConfiguration.class;
	}

	@Override
	public void setup(T configuration) {
		_liferayRemoteContainerConfiguration = configuration;

		super.setup(configuration);
	}

	@Override
	public void start() throws LifecycleException {
		try {
			MBeanServerConnection mBeanServer = getMBeanServerConnection(
				30, TimeUnit.SECONDS);

			mbeanServerInstance.set(mBeanServer);

			frameworkMBean = getMBeanProxy(
				mBeanServer, _frameworkObjectName, FrameworkMBean.class, 30,
				TimeUnit.SECONDS);

			bundleStateMBean = getMBeanProxy(
				mBeanServer, _bundleStateObjectName, BundleStateMBean.class, 30,
				TimeUnit.SECONDS);

			serviceStateMBean = getMBeanProxy(
				mBeanServer, _serviceStateObjectName, ServiceStateMBean.class,
				30, TimeUnit.SECONDS);

			awaitBootstrapCompleteServices();
		}
		catch (TimeoutException te) {
			throw new LifecycleException("JMX timeout", te);
		}
	}

	private static final ObjectName _bundleStateObjectName;
	private static final ObjectName _frameworkObjectName;
	private static final ObjectName _serviceStateObjectName;

	static {
		try {
			_bundleStateObjectName = new ObjectName(
				"osgi.core:type=bundleState,*");

			_frameworkObjectName = new ObjectName("osgi.core:type=framework,*");

			_serviceStateObjectName = new ObjectName(
				"osgi.core:type=serviceState,*");
		}
		catch (MalformedObjectNameException mone) {
			throw new ExceptionInInitializerError(mone);
		}
	}

	private LiferayRemoteContainerConfiguration
		_liferayRemoteContainerConfiguration;

}