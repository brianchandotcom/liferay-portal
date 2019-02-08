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

import com.liferay.arquillian.extension.junit.bridge.deployment.BndDeploymentScenarioGenerator;

import java.util.List;

import org.jboss.arquillian.container.spi.Container;
import org.jboss.arquillian.container.spi.client.container.LifecycleException;
import org.jboss.arquillian.container.spi.client.deployment.Deployment;
import org.jboss.arquillian.container.spi.client.deployment.DeploymentDescription;
import org.jboss.arquillian.container.spi.client.deployment.DeploymentScenario;
import org.jboss.arquillian.container.spi.client.deployment.DeploymentTargetDescription;
import org.jboss.arquillian.container.spi.context.ContainerContext;
import org.jboss.arquillian.container.spi.context.DeploymentContext;
import org.jboss.arquillian.container.spi.event.ContainerMultiControlEvent;
import org.jboss.arquillian.container.spi.event.DeployManagedDeployments;
import org.jboss.arquillian.container.spi.event.UnDeployManagedDeployments;
import org.jboss.arquillian.container.test.spi.client.deployment.DeploymentScenarioGenerator;
import org.jboss.arquillian.core.api.Event;
import org.jboss.arquillian.core.api.Instance;
import org.jboss.arquillian.core.api.InstanceProducer;
import org.jboss.arquillian.core.api.annotation.Inject;
import org.jboss.arquillian.core.api.annotation.Observes;
import org.jboss.arquillian.core.spi.EventContext;
import org.jboss.arquillian.test.spi.TestClass;
import org.jboss.arquillian.test.spi.annotation.ClassScoped;
import org.jboss.arquillian.test.spi.event.suite.AfterClass;
import org.jboss.arquillian.test.spi.event.suite.AfterSuite;
import org.jboss.arquillian.test.spi.event.suite.BeforeClass;
import org.jboss.arquillian.test.spi.event.suite.BeforeSuite;
import org.jboss.arquillian.test.spi.event.suite.TestEvent;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.container.ClassContainer;

/**
 * @author Matthew Tambara
 */
public class ContainerEventController {

	public void createAfterContext(
		@Observes EventContext<TestEvent> eventContext) {

		_createContext(eventContext);
	}

	public void execute(@Observes AfterClass afterClass) {
		_containerMultiControlEvent.fire(new UnDeployManagedDeployments());
	}

	public void execute(@Observes AfterSuite afterSuite)
		throws LifecycleException {

		Container container = _containerInstance.get();

		container.stop();
	}

	public void execute(@Observes BeforeClass beforeClass) {
		_generateDeployment(beforeClass.getTestClass());

		_containerMultiControlEvent.fire(new DeployManagedDeployments());
	}

	public void execute(@Observes BeforeSuite beforeSuite)
		throws LifecycleException {

		Container container = _containerInstance.get();

		container.start();
	}

	private void _createContext(
		EventContext<? extends TestEvent> eventContext) {

		DeploymentScenario deploymentScenario =
			_deploymentScenarioInstance.get();

		Deployment deployment = deploymentScenario.deployment(
			DeploymentTargetDescription.DEFAULT);

		Container container = _containerInstance.get();

		ContainerContext containerContext = _containerContextInstance.get();

		DeploymentContext deploymentContext = _deploymentContextInstance.get();

		try {
			containerContext.activate(container.getName());

			deploymentContext.activate(deployment);

			eventContext.proceed();
		}
		finally {
			deploymentContext.deactivate();

			containerContext.deactivate();
		}
	}

	private void _generateDeployment(TestClass testClass) {
		DeploymentScenarioGenerator deploymentScenarioGenerator =
			new BndDeploymentScenarioGenerator();

		DeploymentScenario deploymentScenario = new DeploymentScenario();

		List<DeploymentDescription> deploymentDescriptions =
			deploymentScenarioGenerator.generate(testClass);

		DeploymentDescription deploymentDescription =
			deploymentDescriptions.get(0);

		deploymentScenario.addDeployment(deploymentDescription);

		Archive<?> archive = deploymentDescription.getArchive();

		((ClassContainer<?>)archive).addClass(testClass.getJavaClass());

		deploymentDescription.setTestableArchive(archive);

		_deploymentScenarioInstanceProducer.set(deploymentScenario);
	}

	@Inject
	private Instance<ContainerContext> _containerContextInstance;

	@Inject
	private Instance<Container> _containerInstance;

	@Inject
	private Event<ContainerMultiControlEvent> _containerMultiControlEvent;

	@Inject
	private Instance<DeploymentContext> _deploymentContextInstance;

	@Inject
	private Instance<DeploymentScenario> _deploymentScenarioInstance;

	@ClassScoped
	@Inject
	private InstanceProducer<DeploymentScenario>
		_deploymentScenarioInstanceProducer;

}