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

package com.liferay.arquillian.extension.junit.bridge.deployment;

import java.util.List;

import org.jboss.arquillian.container.spi.client.deployment.DeploymentDescription;
import org.jboss.arquillian.container.spi.client.deployment.DeploymentScenario;
import org.jboss.arquillian.container.test.impl.client.deployment.event.GenerateDeployment;
import org.jboss.arquillian.container.test.spi.client.deployment.DeploymentScenarioGenerator;
import org.jboss.arquillian.core.api.InstanceProducer;
import org.jboss.arquillian.core.api.annotation.Inject;
import org.jboss.arquillian.core.api.annotation.Observes;
import org.jboss.arquillian.test.spi.TestClass;
import org.jboss.arquillian.test.spi.annotation.ClassScoped;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.container.ClassContainer;

/**
 * @author Matthew Tambara
 */
public class DeploymentGenerator {

	public void generateDeployment(@Observes GenerateDeployment event) {
		DeploymentScenarioGenerator deploymentScenarioGenerator =
			new BndDeploymentScenarioGenerator();

		DeploymentScenario deploymentScenario = new DeploymentScenario();

		TestClass testClass = event.getTestClass();

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

	@ClassScoped
	@Inject
	private InstanceProducer<DeploymentScenario>
		_deploymentScenarioInstanceProducer;

}