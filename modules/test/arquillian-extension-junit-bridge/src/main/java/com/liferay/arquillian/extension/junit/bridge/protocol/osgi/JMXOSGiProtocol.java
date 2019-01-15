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

package com.liferay.arquillian.extension.junit.bridge.protocol.osgi;

import java.util.Collection;
import java.util.Iterator;

import org.jboss.arquillian.container.spi.client.protocol.ProtocolDescription;
import org.jboss.arquillian.container.spi.client.protocol.metadata.JMXContext;
import org.jboss.arquillian.container.spi.client.protocol.metadata.ProtocolMetaData;
import org.jboss.arquillian.container.test.spi.ContainerMethodExecutor;
import org.jboss.arquillian.container.test.spi.client.deployment.DeploymentPackager;
import org.jboss.arquillian.container.test.spi.client.protocol.Protocol;
import org.jboss.arquillian.container.test.spi.command.CommandCallback;
import org.jboss.arquillian.protocol.jmx.JMXMethodExecutor;

/**
 * @author Matthew Tambara
 */
public class JMXOSGiProtocol implements Protocol<JMXProtocolConfiguration> {

	@Override
	public ProtocolDescription getDescription() {
		return _protocolDescription;
	}

	@Override
	public ContainerMethodExecutor getExecutor(
		JMXProtocolConfiguration jmxProtocolConfiguration,
		ProtocolMetaData protocolMetaData, CommandCallback commandCallback) {

		Collection<JMXContext> jmxContexts = protocolMetaData.getContexts(
			JMXContext.class);

		if (jmxContexts.isEmpty()) {
			throw new IllegalStateException(
				"No " + JMXContext.class.getName() + " was found in " +
					ProtocolMetaData.class.getName());
		}

		Iterator<JMXContext> iterator = jmxContexts.iterator();

		JMXContext jmxContext = iterator.next();

		return new JMXMethodExecutor(
			jmxContext.getConnection(), commandCallback);
	}

	@Override
	public DeploymentPackager getPackager() {
		return (testDeployment, processors) ->
			testDeployment.getApplicationArchive();
	}

	@Override
	public Class<JMXProtocolConfiguration> getProtocolConfigurationClass() {
		return JMXProtocolConfiguration.class;
	}

	private static final ProtocolDescription _protocolDescription =
		new ProtocolDescription("jmx-osgi");

}