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
import com.liferay.arquillian.extension.junit.bridge.container.remote.LiferayRemoteDeployableContainer;
import com.liferay.arquillian.extension.junit.bridge.context.ContainerContextImpl;
import com.liferay.arquillian.extension.junit.bridge.context.DeploymentContextImpl;
import com.liferay.arquillian.extension.junit.bridge.deployment.BndDeploymentScenarioGenerator;
import com.liferay.arquillian.extension.junit.bridge.observer.ConfigurationRegistrar;
import com.liferay.arquillian.extension.junit.bridge.observer.JUnitBridgeObserver;
import com.liferay.arquillian.extension.junit.bridge.protocol.osgi.JMXOSGiProtocol;

import java.net.URL;

import org.jboss.arquillian.container.spi.client.container.DeployableContainer;
import org.jboss.arquillian.container.test.impl.client.container.ContainerContainerControllerCreator;
import org.jboss.arquillian.container.test.impl.client.deployment.ContainerDeployerCreator;
import org.jboss.arquillian.container.test.impl.enricher.resource.ContainerControllerProvider;
import org.jboss.arquillian.container.test.impl.enricher.resource.ContainerURIResourceProvider;
import org.jboss.arquillian.container.test.impl.enricher.resource.ContainerURLResourceProvider;
import org.jboss.arquillian.container.test.impl.enricher.resource.DeployerProvider;
import org.jboss.arquillian.container.test.impl.enricher.resource.InitialContextProvider;
import org.jboss.arquillian.container.test.impl.execution.AfterLifecycleEventExecuter;
import org.jboss.arquillian.container.test.impl.execution.BeforeLifecycleEventExecuter;
import org.jboss.arquillian.container.test.impl.execution.ContainerTestExecuter;
import org.jboss.arquillian.container.test.impl.execution.LocalTestExecuter;
import org.jboss.arquillian.container.test.spi.RemoteLoadableExtension;
import org.jboss.arquillian.container.test.spi.client.deployment.DeploymentScenarioGenerator;
import org.jboss.arquillian.container.test.spi.client.protocol.Protocol;
import org.jboss.arquillian.test.impl.TestContextHandler;
import org.jboss.arquillian.test.impl.TestInstanceEnricher;
import org.jboss.arquillian.test.impl.context.ClassContextImpl;
import org.jboss.arquillian.test.impl.context.SuiteContextImpl;
import org.jboss.arquillian.test.impl.context.TestContextImpl;
import org.jboss.arquillian.test.impl.enricher.resource.ArquillianResourceTestEnricher;
import org.jboss.arquillian.test.spi.TestEnricher;
import org.jboss.arquillian.test.spi.enricher.resource.ResourceProvider;

/**
 * @author Shuyang Zhou
 */
public class LiferayArquillianJUnitBridgeExtension
	implements RemoteLoadableExtension {

	@Override
	public void register(ExtensionBuilder extensionBuilder) {
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
		}
		else {
			extensionBuilder.context(ClassContextImpl.class);
			extensionBuilder.context(SuiteContextImpl.class);
			extensionBuilder.context(TestContextImpl.class);
			extensionBuilder.observer(AfterLifecycleEventExecuter.class);
			extensionBuilder.observer(BeforeLifecycleEventExecuter.class);
			extensionBuilder.observer(ContainerTestExecuter.class);
			extensionBuilder.observer(ContainerDeployerCreator.class);
			extensionBuilder.observer(
				ContainerContainerControllerCreator.class);
			extensionBuilder.observer(JUnitBridgeObserver.class);
			extensionBuilder.observer(LocalTestExecuter.class);
			extensionBuilder.observer(TestContextHandler.class);
			extensionBuilder.observer(TestInstanceEnricher.class);
			extensionBuilder.service(
				ResourceProvider.class, ContainerControllerProvider.class);
			extensionBuilder.service(
				ResourceProvider.class, ContainerURIResourceProvider.class);
			extensionBuilder.service(
				ResourceProvider.class, ContainerURLResourceProvider.class);
			extensionBuilder.service(
				ResourceProvider.class, DeployerProvider.class);
			extensionBuilder.service(
				ResourceProvider.class, InitialContextProvider.class);
			extensionBuilder.service(
				TestEnricher.class, ArquillianResourceTestEnricher.class);
		}
	}

}