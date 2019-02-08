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
import com.liferay.arquillian.extension.junit.bridge.deployment.DeploymentGenerator;
import com.liferay.arquillian.extension.junit.bridge.event.controller.ContainerEventController;
import com.liferay.arquillian.extension.junit.bridge.executor.RemoteTestExecuter;
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
import org.jboss.arquillian.container.test.impl.client.protocol.ProtocolRegistryCreator;
import org.jboss.arquillian.container.test.spi.RemoteLoadableExtension;
import org.jboss.arquillian.container.test.spi.client.protocol.Protocol;
import org.jboss.arquillian.core.spi.LoadableExtension;

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
			extensionBuilder.context(ClassContextImpl.class);
			extensionBuilder.context(ContainerContextImpl.class);
			extensionBuilder.context(DeploymentContextImpl.class);
			extensionBuilder.context(SuiteContextImpl.class);
			extensionBuilder.context(TestContextImpl.class);
			extensionBuilder.observer(ConfigurationRegistrar.class);
			extensionBuilder.observer(ContainerDeployController.class);
			extensionBuilder.observer(ContainerDeploymentContextHandler.class);
			extensionBuilder.observer(ContainerEventController.class);
			extensionBuilder.observer(ContainerLifecycleController.class);
			extensionBuilder.observer(DeploymentGenerator.class);
			extensionBuilder.observer(ProtocolRegistryCreator.class);
			extensionBuilder.observer(RemoteTestExecuter.class);
			extensionBuilder.observer(TestContextHandler.class);
			extensionBuilder.service(
				DeployableContainer.class,
				LiferayRemoteDeployableContainer.class);
			extensionBuilder.service(Protocol.class, JMXOSGiProtocol.class);
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