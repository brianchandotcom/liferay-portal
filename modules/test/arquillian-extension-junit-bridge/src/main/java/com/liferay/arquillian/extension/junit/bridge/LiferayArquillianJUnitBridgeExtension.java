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

import com.liferay.arquillian.extension.junit.bridge.container.remote.LiferayRemoteDeployableContainer;
import com.liferay.arquillian.extension.junit.bridge.deployment.BndDeploymentScenarioGenerator;
import com.liferay.arquillian.extension.junit.bridge.deployment.JUnitBridgeAuxiliaryArchiveAppender;
import com.liferay.arquillian.extension.junit.bridge.observer.ConfigurationRegistrar;
import com.liferay.arquillian.extension.junit.bridge.observer.JUnitBridgeObserver;
import com.liferay.arquillian.extension.junit.bridge.protocol.osgi.JMXOSGiProtocol;
import com.liferay.arquillian.extension.junit.bridge.remote.processor.OSGiAllInProcessor;

import java.net.URL;

import org.jboss.arquillian.container.spi.client.container.DeployableContainer;
import org.jboss.arquillian.container.test.spi.RemoteLoadableExtension;
import org.jboss.arquillian.container.test.spi.client.deployment.ApplicationArchiveProcessor;
import org.jboss.arquillian.container.test.spi.client.deployment.AuxiliaryArchiveAppender;
import org.jboss.arquillian.container.test.spi.client.deployment.DeploymentScenarioGenerator;
import org.jboss.arquillian.container.test.spi.client.protocol.Protocol;

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
			extensionBuilder.observer(ConfigurationRegistrar.class);
			extensionBuilder.service(
				AuxiliaryArchiveAppender.class,
				JUnitBridgeAuxiliaryArchiveAppender.class);
			extensionBuilder.service(
				ApplicationArchiveProcessor.class, OSGiAllInProcessor.class);
			extensionBuilder.service(
				DeployableContainer.class,
				LiferayRemoteDeployableContainer.class);
			extensionBuilder.service(
				DeploymentScenarioGenerator.class,
				BndDeploymentScenarioGenerator.class);
			extensionBuilder.service(Protocol.class, JMXOSGiProtocol.class);
		}
		else {
			extensionBuilder.observer(JUnitBridgeObserver.class);
		}
	}

}