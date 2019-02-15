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
import com.liferay.arquillian.extension.junit.bridge.deployment.BndDeploymentDescriptionUtil;

import javax.management.MBeanServerConnection;

import org.jboss.arquillian.container.spi.Container;
import org.jboss.arquillian.container.spi.client.container.DeployableContainer;
import org.jboss.arquillian.container.spi.client.container.DeploymentException;
import org.jboss.arquillian.container.spi.client.deployment.Deployment;
import org.jboss.arquillian.container.spi.client.deployment.DeploymentDescription;
import org.jboss.arquillian.core.api.InstanceProducer;
import org.jboss.arquillian.core.api.annotation.ApplicationScoped;
import org.jboss.arquillian.core.api.annotation.Inject;
import org.jboss.arquillian.core.api.annotation.Observes;
import org.jboss.arquillian.test.spi.event.suite.AfterClass;
import org.jboss.arquillian.test.spi.event.suite.BeforeClass;

/**
 * @author Matthew Tambara
 */
public class ContainerEventController {

	public void execute(@Observes AfterClass afterClass)
		throws DeploymentException {

		DeployableContainer<?> deployableContainer =
			_container.getDeployableContainer();

		DeploymentDescription deploymentDescription =
			_deployment.getDescription();

		try {
			deployableContainer.undeploy(
				deploymentDescription.getTestableArchive());
		}
		catch (DeploymentException de) {
			if (!_deployment.hasDeploymentError()) {
				throw de;
			}
		}
		finally {
			_deployment.undeployed();
		}
	}

	public void execute(@Observes BeforeClass beforeClass) throws Exception {
		_container = new ContainerImpl(
			"default",
			new LiferayRemoteDeployableContainer(
				_mBeanServerConnectionInstanceProducer));

		_container.start();

		DeploymentDescription deploymentDescription =
			BndDeploymentDescriptionUtil.create(beforeClass.getTestClass());

		_deployment = new Deployment(deploymentDescription);

		DeployableContainer<?> deployableContainer =
			_container.getDeployableContainer();

		deployableContainer.deploy(deploymentDescription.getTestableArchive());

		_deployment.deployed();
	}

	private Container _container;
	private Deployment _deployment;

	@ApplicationScoped
	@Inject
	private InstanceProducer<MBeanServerConnection>
		_mBeanServerConnectionInstanceProducer;

}