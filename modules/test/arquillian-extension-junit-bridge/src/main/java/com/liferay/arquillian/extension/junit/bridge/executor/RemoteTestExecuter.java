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

package com.liferay.arquillian.extension.junit.bridge.executor;

import org.jboss.arquillian.config.descriptor.api.ProtocolDef;
import org.jboss.arquillian.container.spi.Container;
import org.jboss.arquillian.container.spi.client.deployment.DeploymentDescription;
import org.jboss.arquillian.container.spi.client.protocol.metadata.ProtocolMetaData;
import org.jboss.arquillian.container.test.impl.domain.ProtocolDefinition;
import org.jboss.arquillian.container.test.impl.domain.ProtocolRegistry;
import org.jboss.arquillian.container.test.impl.execution.event.RemoteExecutionEvent;
import org.jboss.arquillian.container.test.spi.ContainerMethodExecutor;
import org.jboss.arquillian.container.test.spi.client.protocol.Protocol;
import org.jboss.arquillian.container.test.spi.client.protocol.ProtocolConfiguration;
import org.jboss.arquillian.container.test.spi.command.Command;
import org.jboss.arquillian.container.test.spi.command.CommandCallback;
import org.jboss.arquillian.core.api.Event;
import org.jboss.arquillian.core.api.Instance;
import org.jboss.arquillian.core.api.InstanceProducer;
import org.jboss.arquillian.core.api.annotation.Inject;
import org.jboss.arquillian.core.api.annotation.Observes;
import org.jboss.arquillian.core.api.threading.ContextSnapshot;
import org.jboss.arquillian.core.api.threading.ExecutorService;
import org.jboss.arquillian.test.spi.TestResult;
import org.jboss.arquillian.test.spi.annotation.TestScoped;

/**
 * @author Matthew Tambara
 */
public class RemoteTestExecuter {

	public void execute(@Observes RemoteExecutionEvent event) throws Exception {
		Container container = _containerInstance.get();

		DeploymentDescription deployment = _deploymentDescriptionInstance.get();

		ProtocolRegistry protocolRegistry = _protocolRegistryInstance.get();

		ProtocolDefinition protocol = protocolRegistry.getProtocol(
			deployment.getProtocol());

		if (protocol == null) {
			protocol = protocolRegistry.getProtocol(
				container.getDeployableContainer().getDefaultProtocol());
		}

		ProtocolConfiguration protocolConfiguration = null;

		if (container.hasProtocolConfiguration(
				protocol.getProtocolDescription())) {

			ProtocolDef protocolDef = container.getProtocolConfiguration(
				protocol.getProtocolDescription());

			protocolConfiguration = protocol.createProtocolConfiguration(
				protocolDef.getProtocolProperties());
		}
		else {
			protocolConfiguration = protocol.createProtocolConfiguration();
		}

		ContainerMethodExecutor executor = getContainerMethodExecutor(
			protocol, protocolConfiguration);

		_testResultInstanceProducer.set(executor.invoke(event.getExecutor()));
	}

	public ContainerMethodExecutor getContainerMethodExecutor(
		ProtocolDefinition protocol,
		ProtocolConfiguration protocolConfiguration) {

		ExecutorService executorService = _executorServiceInstance.get();

		ContextSnapshot contextSnapshot =
			executorService.createSnapshotContext();

		ContainerMethodExecutor executor =
			((Protocol)protocol.getProtocol()).getExecutor(
				protocolConfiguration, _protocolMetadataInstance.get(),
				new CommandCallback() {

					@Override
					public void fired(Command<?> command) {
						contextSnapshot.activate();

						try {
							_remoteEvent.fire(command);
						}
						finally {
							contextSnapshot.deactivate();
						}
					}

				});

		return executor;
	}

	@Inject
	private Instance<Container> _containerInstance;

	@Inject
	private Instance<DeploymentDescription> _deploymentDescriptionInstance;

	@Inject
	private Instance<ExecutorService> _executorServiceInstance;

	@Inject
	private Instance<ProtocolMetaData> _protocolMetadataInstance;

	@Inject
	private Instance<ProtocolRegistry> _protocolRegistryInstance;

	@Inject
	private Event<Object> _remoteEvent;

	@Inject
	@TestScoped
	private InstanceProducer<TestResult> _testResultInstanceProducer;

}