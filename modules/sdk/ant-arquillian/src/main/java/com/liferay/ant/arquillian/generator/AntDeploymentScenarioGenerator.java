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

package com.liferay.ant.arquillian.generator;

import com.liferay.ant.arquillian.WebArchiveBuilder;

import java.util.ArrayList;
import java.util.List;

import org.jboss.arquillian.container.spi.client.deployment.DeploymentDescription;
import org.jboss.arquillian.container.test.impl.client.deployment.AnnotationDeploymentScenarioGenerator;
import org.jboss.arquillian.test.spi.TestClass;
import org.jboss.shrinkwrap.api.spec.WebArchive;

/**
 * @author Carlos Sierra Andrés
 */
public class AntDeploymentScenarioGenerator
	extends AnnotationDeploymentScenarioGenerator {

	@Override
	public List<DeploymentDescription> generate(TestClass testClass) {
		List<DeploymentDescription> defaultDeploymentDescriptions =
			super.generate(testClass);

		if ((defaultDeploymentDescriptions != null) &&
			(defaultDeploymentDescriptions.size() > 0)) {

			return defaultDeploymentDescriptions;
		}

		List<DeploymentDescription> deploymentDescriptions = new ArrayList<>();

		WebArchive webArchive = WebArchiveBuilder.build();

		DeploymentDescription deploymentDescription = new DeploymentDescription(
			webArchive.getName(), webArchive);

		deploymentDescription.shouldBeTestable(true);

		deploymentDescriptions.add(deploymentDescription);

		return deploymentDescriptions;
	}

}