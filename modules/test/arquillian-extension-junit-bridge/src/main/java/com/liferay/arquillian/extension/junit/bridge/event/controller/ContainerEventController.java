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
import com.liferay.arquillian.extension.junit.bridge.deployment.BndDeploymentDescriptionUtil;

import javax.management.MBeanServerConnection;

import org.jboss.arquillian.container.spi.client.container.DeploymentException;
import org.jboss.arquillian.core.api.InstanceProducer;
import org.jboss.arquillian.core.api.annotation.ApplicationScoped;
import org.jboss.arquillian.core.api.annotation.Inject;
import org.jboss.arquillian.core.api.annotation.Observes;
import org.jboss.arquillian.test.spi.event.suite.AfterClass;
import org.jboss.arquillian.test.spi.event.suite.BeforeClass;
import org.jboss.shrinkwrap.api.Archive;

/**
 * @author Matthew Tambara
 */
public class ContainerEventController {

	public void execute(@Observes AfterClass afterClass)
		throws DeploymentException {

		_lifeRemoteDeployableContainer.undeploy(_archive);
	}

	public void execute(@Observes BeforeClass beforeClass) throws Exception {
		_lifeRemoteDeployableContainer = new LiferayRemoteDeployableContainer(
			_mBeanServerConnectionInstanceProducer);

		_lifeRemoteDeployableContainer.start();

		_archive = BndDeploymentDescriptionUtil.create(
			beforeClass.getTestClass());

		_lifeRemoteDeployableContainer.deploy(_archive);
	}

	private Archive<?> _archive;
	private LiferayRemoteDeployableContainer _lifeRemoteDeployableContainer;

	@ApplicationScoped
	@Inject
	private InstanceProducer<MBeanServerConnection>
		_mBeanServerConnectionInstanceProducer;

}