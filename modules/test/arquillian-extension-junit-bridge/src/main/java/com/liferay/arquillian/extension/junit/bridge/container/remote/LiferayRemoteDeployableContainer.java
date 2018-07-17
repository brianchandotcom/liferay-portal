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

import org.jboss.arquillian.container.osgi.karaf.remote.KarafRemoteDeployableContainer;
import org.jboss.arquillian.container.spi.client.container.DeploymentException;
import org.jboss.arquillian.container.spi.client.protocol.metadata.HTTPContext;
import org.jboss.arquillian.container.spi.client.protocol.metadata.ProtocolMetaData;
import org.jboss.arquillian.core.api.Instance;
import org.jboss.arquillian.core.api.InstanceProducer;
import org.jboss.arquillian.core.api.annotation.ApplicationScoped;
import org.jboss.arquillian.core.api.annotation.Inject;
import org.jboss.shrinkwrap.api.Archive;

/**
 * @author Preston Crary
 */
public class LiferayRemoteDeployableContainer
	<T extends LiferayRemoteContainerConfiguration>
		extends KarafRemoteDeployableContainer<T> {

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