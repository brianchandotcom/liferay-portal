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

package com.liferay.arquillian.extension.junit.bridge.container;

import org.jboss.arquillian.container.spi.Container;
import org.jboss.arquillian.container.spi.client.container.DeployableContainer;
import org.jboss.arquillian.container.spi.client.container.DeploymentException;
import org.jboss.arquillian.container.spi.client.deployment.Deployment;
import org.jboss.arquillian.container.spi.client.deployment.DeploymentDescription;
import org.jboss.arquillian.container.spi.client.deployment.DeploymentScenario;
import org.jboss.arquillian.container.spi.client.deployment.DeploymentTargetDescription;
import org.jboss.arquillian.container.spi.client.protocol.metadata.ProtocolMetaData;
import org.jboss.arquillian.container.spi.context.DeploymentContext;
import org.jboss.arquillian.container.spi.context.annotation.DeploymentScoped;
import org.jboss.arquillian.container.spi.event.DeployManagedDeployments;
import org.jboss.arquillian.container.spi.event.UnDeployManagedDeployments;
import org.jboss.arquillian.core.api.Instance;
import org.jboss.arquillian.core.api.InstanceProducer;
import org.jboss.arquillian.core.api.annotation.Inject;
import org.jboss.arquillian.core.api.annotation.Observes;

/**
 * @author Matthew Tambara
 */
public class ContainerDeployController {

	public void deployManaged(
			@Observes DeployManagedDeployments deployManagedDeployments)
		throws DeploymentException {

		Container container = _containerInstance.get();

		if (container.getState() != Container.State.STARTED) {
			throw new IllegalStateException(
				"Container " + container.getName() + " is not started");
		}

		DeploymentScenario deploymentScenario =
			_deploymentScenarioInstance.get();

		Deployment deployment = deploymentScenario.deployment(
			DeploymentTargetDescription.DEFAULT);

		DeploymentContext deploymentContext = _deploymentContextInstance.get();

		deploymentContext.activate(deployment);

		_deploymentInstanceProducer.set(deployment);

		DeployableContainer<?> deployableContainer =
			container.getDeployableContainer();

		DeploymentDescription deploymentDescription =
			deployment.getDescription();

		_deploymentDescriptionInstanceProducer.set(deploymentDescription);

		try {
			ProtocolMetaData protocolMetaData = deployableContainer.deploy(
				deploymentDescription.getTestableArchive());

			_protocolMetadataInstanceProducer.set(protocolMetaData);

			deployment.deployed();
		}
		finally {
			deploymentContext.deactivate();
		}
	}

	public void undeployManaged(
			@Observes UnDeployManagedDeployments unDeployManagedDeployments)
		throws DeploymentException {

		Container container = _containerInstance.get();

		if (!Container.State.STARTED.equals(container.getState())) {
			return;
		}

		DeploymentScenario deploymentScenario =
			_deploymentScenarioInstance.get();

		Deployment deployment = deploymentScenario.deployment(
			DeploymentTargetDescription.DEFAULT);

		DeploymentContext deploymentContext = _deploymentContextInstance.get();

		deploymentContext.activate(deployment);

		DeployableContainer<?> deployableContainer =
			container.getDeployableContainer();

		DeploymentDescription deploymentDescription =
			deployment.getDescription();

		try {
			deployableContainer.undeploy(
				deploymentDescription.getTestableArchive());
		}
		catch (DeploymentException de) {
			if (!deployment.hasDeploymentError()) {
				throw de;
			}
		}
		finally {
			deployment.undeployed();

			deploymentContext.deactivate();
		}
	}

	@Inject
	private Instance<Container> _containerInstance;

	@Inject
	private Instance<DeploymentContext> _deploymentContextInstance;

	@DeploymentScoped
	@Inject
	private InstanceProducer<DeploymentDescription>
		_deploymentDescriptionInstanceProducer;

	@DeploymentScoped
	@Inject
	private InstanceProducer<Deployment> _deploymentInstanceProducer;

	@Inject
	private Instance<DeploymentScenario> _deploymentScenarioInstance;

	@DeploymentScoped
	@Inject
	private InstanceProducer<ProtocolMetaData>
		_protocolMetadataInstanceProducer;

}