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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.jboss.arquillian.container.spi.client.protocol.ProtocolDescription;
import org.jboss.arquillian.container.spi.client.protocol.metadata.JMXContext;
import org.jboss.arquillian.container.spi.client.protocol.metadata.ProtocolMetaData;
import org.jboss.arquillian.container.test.spi.ContainerMethodExecutor;
import org.jboss.arquillian.container.test.spi.TestDeployment;
import org.jboss.arquillian.container.test.spi.client.deployment.DeploymentPackager;
import org.jboss.arquillian.container.test.spi.client.deployment.ProtocolArchiveProcessor;
import org.jboss.arquillian.container.test.spi.client.protocol.Protocol;
import org.jboss.arquillian.container.test.spi.command.CommandCallback;
import org.jboss.arquillian.protocol.jmx.JMXMethodExecutor;

/**
 * @author Matthew Tambara
 */
public class JMXOSGiProtocol implements Protocol<JMXProtocolConfiguration> {

	@Override
	public ProtocolDescription getDescription() {
		return new ProtocolDescription(_PROTOCOL_NAME);
	}

	@Override
	public ContainerMethodExecutor getExecutor(
		JMXProtocolConfiguration jmxProtocolConfiguration,
		ProtocolMetaData protocolMetaData, CommandCallback commandCallback) {

		if (protocolMetaData.hasContext(JMXContext.class)) {
			List<JMXContext> jmxContexts = new ArrayList<>(
				protocolMetaData.getContexts(JMXContext.class));

			JMXContext jmxContext = jmxContexts.get(0);

			return new JMXMethodExecutor(
				jmxContext.getConnection(), commandCallback);
		}
		else {
			throw new IllegalStateException(
				"No " + JMXContext.class.getName() + " was found in " +
					ProtocolMetaData.class.getName());
		}
	}

	@Override
	public DeploymentPackager getPackager() {
		return (TestDeployment testDeployment,
				Collection<ProtocolArchiveProcessor> processors) ->
				testDeployment.getApplicationArchive();
	}

	@Override
	public Class<JMXProtocolConfiguration> getProtocolConfigurationClass() {
		return JMXProtocolConfiguration.class;
	}

	private static final String _PROTOCOL_NAME = "jmx-osgi";

}