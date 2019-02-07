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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.jboss.arquillian.container.spi.Container;
import org.jboss.arquillian.container.spi.client.container.DeployableContainer;
import org.jboss.arquillian.container.spi.client.deployment.Deployment;
import org.jboss.arquillian.container.spi.client.deployment.DeploymentDescription;
import org.jboss.arquillian.container.spi.client.deployment.DeploymentScenario;
import org.jboss.arquillian.container.test.impl.client.deployment.event.GenerateDeployment;
import org.jboss.arquillian.container.test.impl.domain.ProtocolDefinition;
import org.jboss.arquillian.container.test.impl.domain.ProtocolRegistry;
import org.jboss.arquillian.container.test.spi.TestDeployment;
import org.jboss.arquillian.container.test.spi.client.deployment.ApplicationArchiveProcessor;
import org.jboss.arquillian.container.test.spi.client.deployment.AuxiliaryArchiveAppender;
import org.jboss.arquillian.container.test.spi.client.deployment.AuxiliaryArchiveProcessor;
import org.jboss.arquillian.container.test.spi.client.deployment.DeploymentPackager;
import org.jboss.arquillian.container.test.spi.client.deployment.DeploymentScenarioGenerator;
import org.jboss.arquillian.container.test.spi.client.deployment.ProtocolArchiveProcessor;
import org.jboss.arquillian.container.test.spi.client.protocol.Protocol;
import org.jboss.arquillian.core.api.Instance;
import org.jboss.arquillian.core.api.InstanceProducer;
import org.jboss.arquillian.core.api.annotation.Inject;
import org.jboss.arquillian.core.api.annotation.Observes;
import org.jboss.arquillian.core.spi.ServiceLoader;
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

		for (DeploymentDescription deploymentDescription :
				deploymentScenarioGenerator.generate(event.getTestClass())) {

			deploymentScenario.addDeployment(deploymentDescription);
		}

		_createTestableDeployments(deploymentScenario, event.getTestClass());

		_deploymentInstanceProducer.set(deploymentScenario);
	}

	private void _applyApplicationProcessors(
		Archive<?> archive, TestClass testClass) {

		ServiceLoader serviceLoader = _serviceLoaderInstance.get();

		Collection<ApplicationArchiveProcessor> applicationArchiveProcessors =
			serviceLoader.all(ApplicationArchiveProcessor.class);

		for (ApplicationArchiveProcessor applicationArchiveProcessor :
				applicationArchiveProcessors) {

			applicationArchiveProcessor.process(archive, testClass);
		}
	}

	private void _applyAuxiliaryProcessors(List<Archive<?>> archives) {
		ServiceLoader serviceLoader = _serviceLoaderInstance.get();

		Collection<AuxiliaryArchiveProcessor> archiveProcessors =
			serviceLoader.all(AuxiliaryArchiveProcessor.class);

		for (AuxiliaryArchiveProcessor auxiliaryArchiveProcessor :
				archiveProcessors) {

			for (Archive<?> archive : archives) {
				auxiliaryArchiveProcessor.process(archive);
			}
		}
	}

	private void _buildTestableDeployments(
		DeploymentScenario deploymentScenario, TestClass testClass,
		ProtocolRegistry protocolRegistry) {

		List<Deployment> deployments = deploymentScenario.deployments();

		Deployment deployment = deployments.get(0);

		DeploymentDescription deploymentDescription =
			deployment.getDescription();

		List<Archive<?>> auxiliaryArchives = _loadAuxiliaryArchives();

		ProtocolDefinition protocolDefinition = protocolRegistry.getProtocol(
			deploymentDescription.getProtocol());

		Container container = _containerInstance.get();

		DeployableContainer deployableContainer =
			container.getDeployableContainer();

		if (protocolDefinition == null) {
			protocolDefinition = protocolRegistry.getProtocol(
				deployableContainer.getDefaultProtocol());
		}

		Protocol<?> protocol = protocolDefinition.getProtocol();

		DeploymentPackager deploymentPackager = protocol.getPackager();

		Archive<?> archive = deploymentDescription.getArchive();

		_applyApplicationProcessors(
			deploymentDescription.getArchive(), testClass);

		_applyAuxiliaryProcessors(auxiliaryArchives);

		try {
			if (ClassContainer.class.isInstance(archive)) {
				ClassContainer<?> classContainer = ClassContainer.class.cast(
					archive);

				classContainer.addClass(testClass.getJavaClass());
			}
		}
		catch (UnsupportedOperationException uoe) {
		}

		ServiceLoader serviceLoader = _serviceLoaderInstance.get();

		deploymentDescription.setTestableArchive(
			deploymentPackager.generateDeployment(
				new TestDeployment(
					deployment.getDescription(), archive, auxiliaryArchives),
				serviceLoader.all(ProtocolArchiveProcessor.class)));
	}

	private void _createTestableDeployments(
		DeploymentScenario deploymentScenario, TestClass testClass) {

		ProtocolRegistry protocolRegistry = _protocolRegistryInstance.get();

		_buildTestableDeployments(
			deploymentScenario, testClass, protocolRegistry);
	}

	private List<Archive<?>> _loadAuxiliaryArchives() {
		List<Archive<?>> archives = new ArrayList<>();

		ServiceLoader serviceLoader = _serviceLoaderInstance.get();

		Collection<AuxiliaryArchiveAppender> auxiliaryArchiveAppenders =
			serviceLoader.all(AuxiliaryArchiveAppender.class);

		for (AuxiliaryArchiveAppender auxiliaryArchiveAppender :
				auxiliaryArchiveAppenders) {

			Archive<?> auxiliaryArchive =
				auxiliaryArchiveAppender.createAuxiliaryArchive();

			if (auxiliaryArchive != null) {
				archives.add(auxiliaryArchive);
			}
		}

		return archives;
	}

	@Inject
	private Instance<Container> _containerInstance;

	@ClassScoped
	@Inject
	private InstanceProducer<DeploymentScenario> _deploymentInstanceProducer;

	@Inject
	private Instance<ProtocolRegistry> _protocolRegistryInstance;

	@Inject
	private Instance<ServiceLoader> _serviceLoaderInstance;

}