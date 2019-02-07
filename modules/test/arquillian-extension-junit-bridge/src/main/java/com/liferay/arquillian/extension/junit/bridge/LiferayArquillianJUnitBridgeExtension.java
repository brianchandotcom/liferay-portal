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

package com.liferay.arquillian.extension.junit.bridge;

import com.liferay.arquillian.extension.junit.bridge.container.ContainerDeployController;
import com.liferay.arquillian.extension.junit.bridge.container.ContainerDeploymentContextHandler;
import com.liferay.arquillian.extension.junit.bridge.container.ContainerLifecycleController;
import com.liferay.arquillian.extension.junit.bridge.container.LiferayRemoteDeployableContainer;
import com.liferay.arquillian.extension.junit.bridge.context.ContainerContextImpl;
import com.liferay.arquillian.extension.junit.bridge.context.DeploymentContextImpl;
import com.liferay.arquillian.extension.junit.bridge.deployment.BndDeploymentScenarioGenerator;
import com.liferay.arquillian.extension.junit.bridge.observer.ConfigurationRegistrar;
import com.liferay.arquillian.extension.junit.bridge.protocol.osgi.JMXOSGiProtocol;
import com.liferay.arquillian.extension.junit.bridge.remote.context.ClassContextImpl;
import com.liferay.arquillian.extension.junit.bridge.remote.context.SuiteContextImpl;
import com.liferay.arquillian.extension.junit.bridge.remote.context.TestContextImpl;
import com.liferay.arquillian.extension.junit.bridge.remote.context.handler.TestContextHandler;
import com.liferay.arquillian.extension.junit.bridge.remote.executor.LocalTestExecutor;
import com.liferay.arquillian.extension.junit.bridge.remote.observer.JUnitBridgeObserver;

import java.net.URL;

import org.jboss.arquillian.container.spi.client.container.DeployableContainer;
import org.jboss.arquillian.container.test.impl.ClientTestInstanceEnricher;
import org.jboss.arquillian.container.test.impl.client.ContainerEventController;
import org.jboss.arquillian.container.test.impl.client.LocalCommandService;
import org.jboss.arquillian.container.test.impl.client.container.ClientContainerControllerCreator;
import org.jboss.arquillian.container.test.impl.client.container.ContainerRestarter;
import org.jboss.arquillian.container.test.impl.client.container.command.ContainerCommandObserver;
import org.jboss.arquillian.container.test.impl.client.deployment.ClientDeployerCreator;
import org.jboss.arquillian.container.test.impl.client.deployment.DeploymentGenerator;
import org.jboss.arquillian.container.test.impl.client.deployment.command.DeploymentCommandObserver;
import org.jboss.arquillian.container.test.impl.client.deployment.tool.ArchiveDeploymentToolingExporter;
import org.jboss.arquillian.container.test.impl.client.protocol.ProtocolRegistryCreator;
import org.jboss.arquillian.container.test.impl.client.protocol.local.LocalProtocol;
import org.jboss.arquillian.container.test.impl.deployment.ArquillianDeploymentAppender;
import org.jboss.arquillian.container.test.impl.enricher.resource.ContainerControllerProvider;
import org.jboss.arquillian.container.test.impl.enricher.resource.DeployerProvider;
import org.jboss.arquillian.container.test.impl.enricher.resource.InitialContextProvider;
import org.jboss.arquillian.container.test.impl.enricher.resource.RemoteResourceCommandObserver;
import org.jboss.arquillian.container.test.impl.enricher.resource.URIResourceProvider;
import org.jboss.arquillian.container.test.impl.enricher.resource.URLResourceProvider;
import org.jboss.arquillian.container.test.impl.execution.ClientBeforeAfterLifecycleEventExecuter;
import org.jboss.arquillian.container.test.impl.execution.ClientTestExecuter;
import org.jboss.arquillian.container.test.impl.execution.LocalTestExecuter;
import org.jboss.arquillian.container.test.impl.execution.RemoteTestExecuter;
import org.jboss.arquillian.container.test.spi.RemoteLoadableExtension;
import org.jboss.arquillian.container.test.spi.client.deployment.AuxiliaryArchiveAppender;
import org.jboss.arquillian.container.test.spi.client.deployment.DeploymentScenarioGenerator;
import org.jboss.arquillian.container.test.spi.client.protocol.Protocol;
import org.jboss.arquillian.container.test.spi.command.CommandService;
import org.jboss.arquillian.core.spi.LoadableExtension;
import org.jboss.arquillian.test.impl.enricher.resource.ArquillianResourceTestEnricher;
import org.jboss.arquillian.test.spi.TestEnricher;
import org.jboss.arquillian.test.spi.enricher.resource.ResourceProvider;

/**
 * @author Shuyang Zhou
 */
public class LiferayArquillianJUnitBridgeExtension
	implements RemoteLoadableExtension {

	@Override
	public void register(LoadableExtension.ExtensionBuilder extensionBuilder) {
		URL url = LiferayArquillianJUnitBridgeExtension.class.getResource(
			"/arquillian.remote.marker");

		if (url == null) {
			extensionBuilder.context(ContainerContextImpl.class);
			extensionBuilder.context(DeploymentContextImpl.class);
			extensionBuilder.observer(ConfigurationRegistrar.class);
			extensionBuilder.observer(ContainerDeployController.class);
			extensionBuilder.observer(ContainerDeploymentContextHandler.class);
			extensionBuilder.observer(ContainerLifecycleController.class);
			extensionBuilder.service(
				DeployableContainer.class,
				LiferayRemoteDeployableContainer.class);
			extensionBuilder.service(
				DeploymentScenarioGenerator.class,
				BndDeploymentScenarioGenerator.class);
			extensionBuilder.service(Protocol.class, JMXOSGiProtocol.class);

			extensionBuilder.context(
				org.jboss.arquillian.test.impl.context.SuiteContextImpl.class);
			extensionBuilder.context(
				org.jboss.arquillian.test.impl.context.ClassContextImpl.class);
			extensionBuilder.context(
				org.jboss.arquillian.test.impl.context.TestContextImpl.class);

			extensionBuilder.observer(
				org.jboss.arquillian.test.impl.TestContextHandler.class);
			extensionBuilder.observer(ClientTestInstanceEnricher.class);

			extensionBuilder.service(
				AuxiliaryArchiveAppender.class,
				ArquillianDeploymentAppender.class);
			extensionBuilder.service(
				TestEnricher.class, ArquillianResourceTestEnricher.class);
			extensionBuilder.service(Protocol.class, LocalProtocol.class);
			extensionBuilder.service(
				CommandService.class, LocalCommandService.class);
			extensionBuilder.service(
				ResourceProvider.class, URLResourceProvider.class);
			extensionBuilder.service(
				ResourceProvider.class, URIResourceProvider.class);
			extensionBuilder.service(
				ResourceProvider.class, DeployerProvider.class);
			extensionBuilder.service(
				ResourceProvider.class, InitialContextProvider.class);
			extensionBuilder.service(
				ResourceProvider.class, ContainerControllerProvider.class);

			extensionBuilder.observer(ContainerEventController.class);
			extensionBuilder.observer(ContainerRestarter.class);
			extensionBuilder.observer(DeploymentGenerator.class);
			extensionBuilder.observer(ArchiveDeploymentToolingExporter.class);
			extensionBuilder.observer(ProtocolRegistryCreator.class);
			extensionBuilder.observer(ClientContainerControllerCreator.class);
			extensionBuilder.observer(ClientDeployerCreator.class);
			extensionBuilder.observer(
				ClientBeforeAfterLifecycleEventExecuter.class);
			extensionBuilder.observer(ClientTestExecuter.class);
			extensionBuilder.observer(LocalTestExecuter.class);
			extensionBuilder.observer(RemoteTestExecuter.class);
			extensionBuilder.observer(DeploymentCommandObserver.class);
			extensionBuilder.observer(ContainerCommandObserver.class);
			extensionBuilder.observer(RemoteResourceCommandObserver.class);
		}
		else {
			extensionBuilder.context(ClassContextImpl.class);
			extensionBuilder.context(SuiteContextImpl.class);
			extensionBuilder.context(TestContextImpl.class);
			extensionBuilder.observer(JUnitBridgeObserver.class);
			extensionBuilder.observer(LocalTestExecutor.class);
			extensionBuilder.observer(TestContextHandler.class);
		}
	}

}