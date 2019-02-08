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

import org.jboss.arquillian.container.spi.Container;
import org.jboss.arquillian.container.spi.client.deployment.Deployment;
import org.jboss.arquillian.container.spi.client.deployment.DeploymentScenario;
import org.jboss.arquillian.container.spi.client.deployment.DeploymentTargetDescription;
import org.jboss.arquillian.container.spi.context.ContainerContext;
import org.jboss.arquillian.container.spi.context.DeploymentContext;
import org.jboss.arquillian.container.spi.event.ContainerMultiControlEvent;
import org.jboss.arquillian.container.spi.event.DeployManagedDeployments;
import org.jboss.arquillian.container.spi.event.SetupContainers;
import org.jboss.arquillian.container.spi.event.StartClassContainers;
import org.jboss.arquillian.container.spi.event.StartSuiteContainers;
import org.jboss.arquillian.container.spi.event.StopClassContainers;
import org.jboss.arquillian.container.spi.event.StopManualContainers;
import org.jboss.arquillian.container.spi.event.StopSuiteContainers;
import org.jboss.arquillian.container.spi.event.UnDeployManagedDeployments;
import org.jboss.arquillian.container.test.impl.client.deployment.event.GenerateDeployment;
import org.jboss.arquillian.core.api.Event;
import org.jboss.arquillian.core.api.Instance;
import org.jboss.arquillian.core.api.annotation.Inject;
import org.jboss.arquillian.core.api.annotation.Observes;
import org.jboss.arquillian.core.spi.EventContext;
import org.jboss.arquillian.test.spi.event.suite.AfterClass;
import org.jboss.arquillian.test.spi.event.suite.AfterSuite;
import org.jboss.arquillian.test.spi.event.suite.AfterTestLifecycleEvent;
import org.jboss.arquillian.test.spi.event.suite.BeforeClass;
import org.jboss.arquillian.test.spi.event.suite.BeforeSuite;
import org.jboss.arquillian.test.spi.event.suite.BeforeTestLifecycleEvent;
import org.jboss.arquillian.test.spi.event.suite.Test;
import org.jboss.arquillian.test.spi.event.suite.TestEvent;

/**
 * @author Matthew Tambara
 */
public class ContainerEventController {

	public void createAfterContext(
		@Observes EventContext<AfterTestLifecycleEvent> eventContext) {

		_createContext(eventContext);
	}

	public void createBeforeContext(
		@Observes EventContext<BeforeTestLifecycleEvent> eventContext) {

		_createContext(eventContext);
	}

	public void createTestContext(@Observes EventContext<Test> eventContext) {
		_createContext(eventContext);
	}

	public void execute(@Observes AfterClass afterClass) {
		try {
			_containerMultiControlEvent.fire(new UnDeployManagedDeployments());
		}
		finally {
			_containerMultiControlEvent.fire(new StopManualContainers());
			_containerMultiControlEvent.fire(new StopClassContainers());
		}
	}

	public void execute(@Observes AfterSuite afterSuite) {
		_containerMultiControlEvent.fire(new StopSuiteContainers());
	}

	public void execute(@Observes BeforeClass beforeClass) {
		_containerMultiControlEvent.fire(new StartClassContainers());

		_generateDeploymentEvent.fire(
			new GenerateDeployment(beforeClass.getTestClass()));

		_containerMultiControlEvent.fire(new DeployManagedDeployments());
	}

	public void execute(@Observes BeforeSuite beforeSuite) {
		_containerMultiControlEvent.fire(new SetupContainers());
		_containerMultiControlEvent.fire(new StartSuiteContainers());
	}

	private void _createContext(
		EventContext<? extends TestEvent> eventContext) {

		try {
			_lookup(new Activate());

			eventContext.proceed();
		}
		finally {
			_lookup(new DeActivate());
		}
	}

	private void _lookup(ResultCallback resultCallback) {
		DeploymentScenario deploymentScenario =
			_deploymentScenarioInstance.get();

		Deployment deployment = deploymentScenario.deployment(
			DeploymentTargetDescription.DEFAULT);

		Container container = _containerInstance.get();

		resultCallback.call(container, deployment);
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

	@Inject
	private Event<GenerateDeployment> _generateDeploymentEvent;

	private class Activate extends ResultCallback {

		@Override
		public void call(Container container, Deployment deployment) {
			_containerContextInstance.get().activate(container.getName());

			_deploymentContextInstance.get().activate(deployment);
		}

	}

	private class DeActivate extends ResultCallback {

		@Override
		public void call(Container container, Deployment deployment) {
			_containerContextInstance.get().deactivate();

			_deploymentContextInstance.get().deactivate();
		}

	}

	private abstract class ResultCallback {

		public abstract void call(Container container, Deployment deployment);

	}

}