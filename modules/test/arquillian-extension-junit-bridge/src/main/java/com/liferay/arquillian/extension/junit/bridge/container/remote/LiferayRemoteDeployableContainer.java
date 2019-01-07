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
import javax.management.ObjectName;

import org.jboss.arquillian.container.osgi.jmx.JMXDeployableContainer;
import org.jboss.arquillian.container.spi.client.container.DeploymentException;
import org.jboss.arquillian.container.spi.client.container.LifecycleException;
import org.jboss.arquillian.container.spi.client.protocol.metadata.HTTPContext;
import org.jboss.arquillian.container.spi.client.protocol.metadata.ProtocolMetaData;
import org.jboss.arquillian.core.api.Instance;
import org.jboss.arquillian.core.api.InstanceProducer;
import org.jboss.arquillian.core.api.annotation.ApplicationScoped;
import org.jboss.arquillian.core.api.annotation.Inject;
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

		LiferayRemoteContainerConfiguration
			liferayRemoteContainerConfiguration = configurationInstance.get();

		ProtocolMetaData protocolMetaData = super.deploy(archive);

		protocolMetaData.addContext(
			new HTTPContext(
				liferayRemoteContainerConfiguration.getHttpHost(),
				liferayRemoteContainerConfiguration.getHttpPort()));

		return protocolMetaData;
	}

	@Override
	@SuppressWarnings("unchecked")
	public Class<T> getConfigurationClass() {
		return (Class<T>)LiferayRemoteContainerConfiguration.class;
	}

	@Override
	public void setup(T config) {
		configurationInstanceProducer.set(config);

		super.setup(config);
	}

	@Override
	public void start() throws LifecycleException {
		MBeanServerConnection mBeanServer = null;

		try {
			mBeanServer = getMBeanServerConnection(30, TimeUnit.SECONDS);

			mbeanServerInstance.set(mBeanServer);
		}
		catch (TimeoutException te) {
			throw new LifecycleException(
				"Error connecting to Karaf MBeanServer: ", te);
		}

		try {
			ObjectName objectName = new ObjectName(
				"osgi.core:type=framework,*");

			frameworkMBean = getMBeanProxy(
				mBeanServer, objectName, FrameworkMBean.class, 30,
				TimeUnit.SECONDS);

			objectName = new ObjectName("osgi.core:type=bundleState,*");

			bundleStateMBean = getMBeanProxy(
				mBeanServer, objectName, BundleStateMBean.class, 30,
				TimeUnit.SECONDS);

			objectName = new ObjectName("osgi.core:type=serviceState,*");

			serviceStateMBean = getMBeanProxy(
				mBeanServer, objectName, ServiceStateMBean.class, 30,
				TimeUnit.SECONDS);

			installArquillianBundle();

			awaitArquillianBundleActive(30, TimeUnit.SECONDS);

			awaitBootstrapCompleteServices();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new LifecycleException("Cannot start Karaf container", e);
		}
	}

	@Override
	protected void awaitArquillianBundleActive(long timeout, TimeUnit unit) {
	}

	@Override
	protected void installArquillianBundle() {
	}

	@ApplicationScoped
	@Inject
	protected Instance<LiferayRemoteContainerConfiguration>
		configurationInstance;

	@ApplicationScoped
	@Inject
	protected InstanceProducer<LiferayRemoteContainerConfiguration>
		configurationInstanceProducer;

}