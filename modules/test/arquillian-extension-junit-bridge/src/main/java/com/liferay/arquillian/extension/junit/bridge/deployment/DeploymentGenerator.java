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
import org.jboss.arquillian.container.spi.ContainerRegistry;
import org.jboss.arquillian.container.spi.client.deployment.Deployment;
import org.jboss.arquillian.container.spi.client.deployment.DeploymentDescription;
import org.jboss.arquillian.container.spi.client.deployment.DeploymentScenario;
import org.jboss.arquillian.container.spi.client.deployment.TargetDescription;
import org.jboss.arquillian.container.spi.client.protocol.ProtocolDescription;
import org.jboss.arquillian.container.test.api.TargetsContainer;
import org.jboss.arquillian.container.test.impl.client.deployment.ValidationException;
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

		_validate(deploymentScenario);

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

		ServiceLoader serviceLoader = _serviceLoaderInstance.get();

		for (Deployment deployment : deploymentScenario.deployments()) {
			DeploymentDescription deploymentDescription =
				deployment.getDescription();

			if (!deploymentDescription.testable() ||
				!deploymentDescription.isArchiveDeployment()) {

				continue;
			}

			List<Archive<?>> auxiliaryArchives = _loadAuxiliaryArchives();

			ProtocolDefinition protocolDefinition =
				protocolRegistry.getProtocol(
					deploymentDescription.getProtocol());

			if (protocolDefinition == null) {
				protocolDefinition = protocolRegistry.getProtocol(
					_containerRegistryInstance.get().getContainer(deploymentDescription.getTarget()).getDeployableContainer().getDefaultProtocol());
			}

			Protocol<?> protocol = protocolDefinition.getProtocol();

			DeploymentPackager deploymentPackager = protocol.getPackager();

			Archive<?> archive = deploymentDescription.getArchive();

			_applyApplicationProcessors(
				deploymentDescription.getArchive(), testClass);

			_applyAuxiliaryProcessors(auxiliaryArchives);

			try {
				if (ClassContainer.class.isInstance(archive)) {
					ClassContainer<?> classContainer =
						ClassContainer.class.cast(archive);

					classContainer.addClass(testClass.getJavaClass());
				}
			}
			catch (UnsupportedOperationException uoe) {
			}

			deploymentDescription.setTestableArchive(
				deploymentPackager.generateDeployment(
					new TestDeployment(
						deployment.getDescription(), archive,
						auxiliaryArchives),
					serviceLoader.all(ProtocolArchiveProcessor.class)));
		}
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

	private void _throwNoContainerFound(TargetDescription targetDescription) {
		throw new ValidationException(
			"DeploymentScenario contains a target (" +
				targetDescription.getName() + ") not matching any defined " +
					"Container in the registry.\nPlease include at least 1 " +
						"Deployable Container on your Classpath.");
	}

	private void _throwNoMatchFound(
		ContainerRegistry containerRegistry,
		TargetDescription targetDescription) {

		throw new ValidationException("DeploymentScenario contains a target (" + targetDescription.getName()
			+ ") not matching any defined Container in the registry.\n" + "Possible causes are: None of the "
			+ containerRegistry.getContainers().size() + " Containers are marked as default or you have defined a " + "@"
			+ org.jboss.arquillian.container.test.api.Deployment.class.getSimpleName() + " with a @"
			+ TargetsContainer.class.getSimpleName() + " of value (" + targetDescription.getName() + ") that "
			+ "does not match any found/configured Containers (" + _toString(containerRegistry) +
			"), see arquillian.xml container@qualifier");
	}

	private void _throwTargetNotFoundValidationException(
		ContainerRegistry containerRegistry,
		TargetDescription targetDescription) {

		List<Container> containers = containerRegistry.getContainers();

		if (containers.isEmpty()) {
			_throwNoContainerFound(targetDescription);
		}

		_throwNoMatchFound(containerRegistry, targetDescription);
	}

	private String _toString(ContainerRegistry containerRegistry) {
		StringBuilder sb = new StringBuilder();

		for (Container container : containerRegistry.getContainers()) {
			sb.append(container.getName());
			sb.append(".");
		}

		if (sb.length() > 0) {
			sb.deleteCharAt(sb.length() - 1);
		}

		return sb.toString();
	}

	private void _validate(DeploymentScenario deploymentScenario) {
		ContainerRegistry containerRegistry = _containerRegistryInstance.get();

		for (TargetDescription target : deploymentScenario.targets()) {
			Container container = containerRegistry.getContainer(target);

			if (container == null) {
				_throwTargetNotFoundValidationException(
					containerRegistry, target);
			}
		}

		for (Deployment deployment : deploymentScenario.deployments()) {
			Container container = containerRegistry.getContainer(
				deployment.getDescription().getTarget());

			if ("custom".equalsIgnoreCase(
					container.getContainerConfiguration().getMode())) {

				if (deployment.getDescription().managed()) {
					throw new ValidationException(
							"Deployment " + deployment.getDescription().getName() + " is targeted against container " +
							container.getName() + ". This container is set to mode custom which can not handle managed deployments. " +
							"Please verify the @" + TargetsContainer.class.getName() + " annotation or container@mode in arquillian.xml");
				}
			}
		}

		ProtocolRegistry protocolRegistry = _protocolRegistryInstance.get();

		for (ProtocolDescription protocolDescription :
				deploymentScenario.protocols()) {

			if (ProtocolDescription.DEFAULT.equals(protocolDescription)) {
				continue;
			}

			ProtocolDefinition protocol = protocolRegistry.getProtocol(
				protocolDescription);

			if (protocol == null) {
				throw new ValidationException(
					DeploymentScenario.class.getSimpleName() + " contains " +
						"protocols not maching any defined Protocol in the " +
							"registry. " + protocolDescription.getName());
			}
		}
	}

	@Inject
	private Instance<ContainerRegistry> _containerRegistryInstance;

	@Inject @ClassScoped
	private InstanceProducer<DeploymentScenario> _deploymentInstanceProducer;

	@Inject
	private Instance<ProtocolRegistry> _protocolRegistryInstance;

	@Inject
	private Instance<ServiceLoader> _serviceLoaderInstance;

}