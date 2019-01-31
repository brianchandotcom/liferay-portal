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

import java.util.List;
import java.util.concurrent.Callable;

import org.jboss.arquillian.config.descriptor.api.ContainerDef;
import org.jboss.arquillian.container.spi.Container;
import org.jboss.arquillian.container.spi.ContainerRegistry;
import org.jboss.arquillian.container.spi.client.container.DeployableContainer;
import org.jboss.arquillian.container.spi.client.container.DeploymentException;
import org.jboss.arquillian.container.spi.client.deployment.Deployment;
import org.jboss.arquillian.container.spi.client.deployment.DeploymentDescription;
import org.jboss.arquillian.container.spi.client.deployment.DeploymentScenario;
import org.jboss.arquillian.container.spi.client.protocol.metadata.ProtocolMetaData;
import org.jboss.arquillian.container.spi.context.annotation.DeploymentScoped;
import org.jboss.arquillian.container.spi.event.DeployDeployment;
import org.jboss.arquillian.container.spi.event.DeployManagedDeployments;
import org.jboss.arquillian.container.spi.event.DeploymentEvent;
import org.jboss.arquillian.container.spi.event.UnDeployDeployment;
import org.jboss.arquillian.container.spi.event.UnDeployManagedDeployments;
import org.jboss.arquillian.container.spi.event.container.AfterDeploy;
import org.jboss.arquillian.container.spi.event.container.AfterUnDeploy;
import org.jboss.arquillian.container.spi.event.container.BeforeDeploy;
import org.jboss.arquillian.container.spi.event.container.BeforeUnDeploy;
import org.jboss.arquillian.container.spi.event.container.DeployerEvent;
import org.jboss.arquillian.core.api.Event;
import org.jboss.arquillian.core.api.Injector;
import org.jboss.arquillian.core.api.Instance;
import org.jboss.arquillian.core.api.InstanceProducer;
import org.jboss.arquillian.core.api.annotation.Inject;
import org.jboss.arquillian.core.api.annotation.Observes;

/**
 * @author Matthew Tambara
 */
public class ContainerDeployController {

	public void deploy(@Observes DeployDeployment deployDeployment)
		throws Exception {

		_executeOperation(
			new Callable<Void>() {

				@Override
				public Void call() throws DeploymentException {
					DeployableContainer<?> deployableContainer =
						deployDeployment.getDeployableContainer();

					Deployment deployment = deployDeployment.getDeployment();

					DeploymentDescription deploymentDescription =
						deployment.getDescription();

					_deploymentDescriptionInstanceProducer.set(
						deploymentDescription);

					_deploymentInstanceProducer.set(deployment);

					_deployerEvent.fire(
						new BeforeDeploy(
							deployableContainer, deploymentDescription));

					try {
						if (deploymentDescription.isArchiveDeployment()) {
							ProtocolMetaData protocolMetaData =
								deployableContainer.deploy(
									deploymentDescription.getTestableArchive());

							_protocolMetadataInstanceProducer.set(
								protocolMetaData);
						}
						else {
							deployableContainer.deploy(
								deploymentDescription.getDescriptor());
						}

						deployment.deployed();
					}
					catch (DeploymentException de) {
						deployment.deployedWithError(de);

						throw de;
					}

					_deployerEvent.fire(
						new AfterDeploy(
							deployableContainer, deploymentDescription));

					return null;
				}

				@Inject
				private Event<DeployerEvent> _deployerEvent;

				@DeploymentScoped
				@Inject
				private InstanceProducer<DeploymentDescription>
					_deploymentDescriptionInstanceProducer;

				@DeploymentScoped
				@Inject
				private InstanceProducer<Deployment>
					_deploymentInstanceProducer;

				@DeploymentScoped
				@Inject
				private InstanceProducer<ProtocolMetaData>
					_protocolMetadataInstanceProducer;

			});
	}

	public void deployManaged(
		@Observes DeployManagedDeployments deployManagedDeployments) {

		_forEachManagedDeployment(
			new Operation<Container, Deployment>() {

				@Override
				public void perform(
					Container container, Deployment deployment) {

					ContainerDef containerDef =
						container.getContainerConfiguration();

					if (!"manual".equals(containerDef.getMode())) {
						if (container.getState() != Container.State.STARTED) {
							DeploymentDescription deploymentDescription =
								deployment.getDescription();

							throw new IllegalStateException(
								"Trying to deploy a managed deployment " +
									deploymentDescription.getName() +
										" to a non started managed contianer " +
											container.getName());
						}

						_deploymentEvent.fire(
							new DeployDeployment(container, deployment));
					}
				}

				@Inject
				private Event<DeploymentEvent> _deploymentEvent;

			});
	}

	public void undeploy(@Observes UnDeployDeployment unDeployDeployment)
		throws Exception {

		_executeOperation(
			new Callable<Void>() {

				@Override
				public Void call() throws DeploymentException {
					DeployableContainer<?> deployableContainer =
						unDeployDeployment.getDeployableContainer();

					Deployment deployment = unDeployDeployment.getDeployment();

					DeploymentDescription deploymentDescription =
						deployment.getDescription();

					_deployerEvent.fire(
						new BeforeUnDeploy(
							deployableContainer, deploymentDescription));

					try {
						if (deployment.getDescription().isArchiveDeployment()) {
							try {
								deployableContainer.undeploy(
									deploymentDescription.getTestableArchive());
							}
							catch (DeploymentException de) {
								if (!deployment.hasDeploymentError()) {
									throw de;
								}
							}
						}
						else {
							deployableContainer.undeploy(
								deploymentDescription.getDescriptor());
						}
					}
					finally {
						deployment.undeployed();
					}

					_deployerEvent.fire(
						new AfterUnDeploy(
							deployableContainer, deploymentDescription));

					return null;
				}

				@Inject
				private Event<DeployerEvent> _deployerEvent;

			});
	}

	public void undeployManaged(
		@Observes UnDeployManagedDeployments unDeployManagedDeployments) {

		_forEachDeployedDeployment(
			new Operation<Container, Deployment>() {

				@Override
				public void perform(
					Container container, Deployment deployment) {

					if (Container.State.STARTED.equals(container.getState()) &&
						deployment.isDeployed()) {

						_deploymentEvent.fire(
							new UnDeployDeployment(container, deployment));
					}
				}

				@Inject
				private Event<DeploymentEvent> _deploymentEvent;

			});
	}

	public interface Operation<T, X> {

		public void perform(T container, X deployment);

	}

	private void _executeOperation(Callable<Void> callable) throws Exception {
		Injector injector = _injectorInstance.get();

		injector.inject(callable);

		callable.call();
	}

	private void _forEachDeployedDeployment(
		Operation<Container, Deployment> operation) {

		DeploymentScenario scenario = _deploymentScenarioInstance.get();

		if (scenario == null) {
			return;
		}

		_forEachDeployment(
			scenario.deployedDeploymentsInUnDeployOrder(), operation);
	}

	private void _forEachDeployment(
		List<Deployment> deployments,
		Operation<Container, Deployment> operation) {

		Injector injector = _injectorInstance.get();

		injector.inject(operation);

		ContainerRegistry containerRegistry = _containerRegistryInstance.get();

		if (containerRegistry == null) {
			return;
		}

		for (Deployment deployment : deployments) {
			DeploymentDescription deploymentDescription =
				deployment.getDescription();

			Container container = containerRegistry.getContainer(
				deploymentDescription.getTarget());

			operation.perform(container, deployment);
		}
	}

	private void _forEachManagedDeployment(
		Operation<Container, Deployment> operation) {

		DeploymentScenario deploymentScenario =
			_deploymentScenarioInstance.get();

		if (deploymentScenario == null) {
			return;
		}

		_forEachDeployment(
			deploymentScenario.managedDeploymentsInDeployOrder(), operation);
	}

	@Inject
	private Instance<ContainerRegistry> _containerRegistryInstance;

	@Inject
	private Instance<DeploymentScenario> _deploymentScenarioInstance;

	@Inject
	private Instance<Injector> _injectorInstance;

}