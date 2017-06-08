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

package com.liferay.arquillian.extension;

import com.liferay.arquillian.container.osgi.jmx.LiferayJMXDeployableContainer;
import com.liferay.arquillian.deployment.JUnitBridgeAuxiliaryArchiveAppender;
import com.liferay.arquillian.deployment.ModuleDeploymentScenarioGenerator;
import com.liferay.arquillian.junit.observer.JUnitBridgeObserver;
import com.liferay.arquillian.protocol.osgi.JMXOSGiProtocol;

import java.net.URL;

import org.jboss.arquillian.container.osgi.DeploymentObserver;
import org.jboss.arquillian.container.spi.client.container.DeployableContainer;
import org.jboss.arquillian.container.test.spi.RemoteLoadableExtension;
import org.jboss.arquillian.container.test.spi.client.deployment.AuxiliaryArchiveAppender;
import org.jboss.arquillian.container.test.spi.client.deployment.DeploymentScenarioGenerator;
import org.jboss.arquillian.container.test.spi.client.protocol.Protocol;

/**
 * @author Shuyang Zhou
 */
public class LiferayArquillianExtension implements RemoteLoadableExtension {

	@Override
	public void register(ExtensionBuilder extensionBuilder) {
		URL url = LiferayArquillianExtension.class.getResource(
			"/arquillian.remote.marker");

		if (url == null) {
			extensionBuilder.observer(DeploymentObserver.class);

			extensionBuilder.service(
				AuxiliaryArchiveAppender.class,
				JUnitBridgeAuxiliaryArchiveAppender.class);
			extensionBuilder.service(
				DeployableContainer.class, LiferayJMXDeployableContainer.class);
			extensionBuilder.service(
				DeploymentScenarioGenerator.class,
				ModuleDeploymentScenarioGenerator.class);
			extensionBuilder.service(Protocol.class, JMXOSGiProtocol.class);
		}
		else {
			extensionBuilder.observer(JUnitBridgeObserver.class);
		}
	}

}