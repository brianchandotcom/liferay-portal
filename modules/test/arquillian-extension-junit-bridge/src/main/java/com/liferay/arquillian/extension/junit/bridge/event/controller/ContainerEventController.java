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

package com.liferay.arquillian.extension.junit.bridge.event.controller;

import com.liferay.arquillian.extension.junit.bridge.container.LiferayRemoteDeployableContainer;
import com.liferay.arquillian.extension.junit.bridge.container.impl.ContainerImpl;
import com.liferay.arquillian.extension.junit.bridge.deployment.BndDeploymentScenarioGenerator;

import java.util.List;

import javax.management.MBeanServerConnection;

import org.jboss.arquillian.config.descriptor.api.ContainerDef;
import org.jboss.arquillian.config.descriptor.impl.ContainerDefImpl;
import org.jboss.arquillian.container.spi.Container;
import org.jboss.arquillian.container.spi.client.container.DeployableContainer;
import org.jboss.arquillian.container.spi.client.container.DeploymentException;
import org.jboss.arquillian.container.spi.client.container.LifecycleException;
import org.jboss.arquillian.container.spi.client.deployment.Deployment;
import org.jboss.arquillian.container.spi.client.deployment.DeploymentDescription;
import org.jboss.arquillian.container.spi.context.ContainerContext;
import org.jboss.arquillian.container.spi.context.DeploymentContext;
import org.jboss.arquillian.container.spi.context.annotation.DeploymentScoped;
import org.jboss.arquillian.container.test.spi.client.deployment.DeploymentScenarioGenerator;
import org.jboss.arquillian.core.api.Injector;
import org.jboss.arquillian.core.api.Instance;
import org.jboss.arquillian.core.api.InstanceProducer;
import org.jboss.arquillian.core.api.annotation.Inject;
import org.jboss.arquillian.core.api.annotation.Observes;
import org.jboss.arquillian.core.spi.EventContext;
import org.jboss.arquillian.test.spi.TestClass;
import org.jboss.arquillian.test.spi.event.suite.AfterClass;
import org.jboss.arquillian.test.spi.event.suite.AfterSuite;
import org.jboss.arquillian.test.spi.event.suite.BeforeClass;
import org.jboss.arquillian.test.spi.event.suite.BeforeSuite;
import org.jboss.arquillian.test.spi.event.suite.TestEvent;
import org.jboss.shrinkwrap.api.Archive;

/**
 * @author Matthew Tambara
 */
public class ContainerEventController {

	public void createAfterContext(
		@Observes EventContext<TestEvent> eventContext) {

		_createContext(eventContext);
	}

	public void execute(@Observes AfterClass afterClass)
		throws DeploymentException {

		_undeployManaged();
	}

	public void execute(@Observes AfterSuite afterSuite)
		throws LifecycleException {

		Container container = _getContainer();

		container.stop();
	}

	public void execute(@Observes BeforeClass beforeClass)
		throws DeploymentException {

		_generateDeployment(beforeClass.getTestClass());

		_deployManaged();
	}

	public void execute(@Observes BeforeSuite beforeSuite)
		throws LifecycleException {

		Container container = _getContainer();

		container.start();
	}

	private void _createContext(
		EventContext<? extends TestEvent> eventContext) {

		Container container = _getContainer();

		ContainerContext containerContext = _containerContextInstance.get();

		DeploymentContext deploymentContext = _deploymentContextInstance.get();

		try {
			containerContext.activate(container.getName());

			deploymentContext.activate(_deployment);

			eventContext.proceed();
		}
		finally {
			deploymentContext.deactivate();

			containerContext.deactivate();
		}
	}

	private void _deployManaged() throws DeploymentException {
		Container container = _getContainer();

		if (container.getState() != Container.State.STARTED) {
			throw new IllegalStateException(
				"Container " + container.getName() + " is not started");
		}

		Deployment deployment = _deployment;

		DeploymentContext deploymentContext = _deploymentContextInstance.get();

		deploymentContext.activate(deployment);

		DeployableContainer<?> deployableContainer =
			container.getDeployableContainer();

		DeploymentDescription deploymentDescription =
			deployment.getDescription();

		try {
			deployableContainer.deploy(
				deploymentDescription.getTestableArchive());

			deployment.deployed();
		}
		finally {
			deploymentContext.deactivate();
		}
	}

	private void _generateDeployment(TestClass testClass) {
		DeploymentScenarioGenerator deploymentScenarioGenerator =
			new BndDeploymentScenarioGenerator();

		List<DeploymentDescription> deploymentDescriptions =
			deploymentScenarioGenerator.generate(testClass);

		DeploymentDescription deploymentDescription =
			deploymentDescriptions.get(0);

		Archive<?> archive = deploymentDescription.getArchive();

		deploymentDescription.setTestableArchive(archive);

		_deployment = new Deployment(deploymentDescription);
	}

	private Container _getContainer() {
		if (_container == null) {
			ContainerDef containerDef = new ContainerDefImpl("arquillian.xml");

			containerDef.setContainerName("default");

			Container container = new ContainerImpl(
				containerDef.getContainerName(),
				new LiferayRemoteDeployableContainer(
					_mBeanServerConnectionInstanceProducer),
				containerDef);

			Injector injector = _injectorInstance.get();

			injector.inject(container);

			_container = container;
		}

		return _container;
	}

	private void _undeployManaged() throws DeploymentException {
		Container container = _getContainer();

		if (!Container.State.STARTED.equals(container.getState())) {
			return;
		}

		Deployment deployment = _deployment;

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

	private Container _container;

	@Inject
	private Instance<ContainerContext> _containerContextInstance;

	private Deployment _deployment;

	@Inject
	private Instance<DeploymentContext> _deploymentContextInstance;

	@Inject
	private Instance<Injector> _injectorInstance;

	@DeploymentScoped
	@Inject
	private InstanceProducer<MBeanServerConnection>
		_mBeanServerConnectionInstanceProducer;

}