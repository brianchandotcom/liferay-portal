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

import com.liferay.arquillian.container.osgi.remote.bundleclasspath.BundleClassPathAuxiliaryAppender;
import com.liferay.arquillian.container.osgi.remote.commandservice.CommandServiceAuxiliaryAppender;
import com.liferay.arquillian.container.osgi.remote.processor.service.BundleActivatorsManager;
import com.liferay.arquillian.container.osgi.remote.processor.service.BundleActivatorsManagerImpl;
import com.liferay.arquillian.extension.junit.bridge.container.remote.LiferayRemoteDeployableContainer;
import com.liferay.arquillian.extension.junit.bridge.deployment.BndDeploymentScenarioGenerator;
import com.liferay.arquillian.extension.junit.bridge.deployment.JUnitBridgeAuxiliaryArchiveAppender;
import com.liferay.arquillian.extension.junit.bridge.deployment.NoOpArchiveApplicationProcessor;
import com.liferay.arquillian.extension.junit.bridge.observer.JUnitBridgeObserver;
import com.liferay.arquillian.extension.junit.bridge.remote.processor.OSGiAllInProcessor;

import java.net.URL;

import org.jboss.arquillian.container.osgi.DeploymentObserver;
import org.jboss.arquillian.container.osgi.OSGiApplicationArchiveProcessor;
import org.jboss.arquillian.container.osgi.karaf.remote.KarafRemoteDeployableContainer;
import org.jboss.arquillian.container.spi.client.container.DeployableContainer;
import org.jboss.arquillian.container.test.spi.RemoteLoadableExtension;
import org.jboss.arquillian.container.test.spi.client.deployment.ApplicationArchiveProcessor;
import org.jboss.arquillian.container.test.spi.client.deployment.AuxiliaryArchiveAppender;
import org.jboss.arquillian.container.test.spi.client.deployment.DeploymentScenarioGenerator;
import org.jboss.arquillian.container.test.spi.command.CommandService;
import org.jboss.arquillian.junit.container.JUnitDeploymentAppender;
import org.jboss.arquillian.protocol.jmx.JMXCommandService;

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
			extensionBuilder.override(
				ApplicationArchiveProcessor.class,
				com.liferay.arquillian.container.osgi.remote.processor.
					OSGiAllInProcessor.class,
				OSGiAllInProcessor.class);
			extensionBuilder.override(
				ApplicationArchiveProcessor.class,
				OSGiApplicationArchiveProcessor.class,
				NoOpArchiveApplicationProcessor.class);
			extensionBuilder.service(
				AuxiliaryArchiveAppender.class,
				BundleClassPathAuxiliaryAppender.class);
			extensionBuilder.service(
				AuxiliaryArchiveAppender.class,
				CommandServiceAuxiliaryAppender.class);
			extensionBuilder.observer(DeploymentObserver.class);
			extensionBuilder.service(
				ApplicationArchiveProcessor.class,
				OSGiApplicationArchiveProcessor.class);
			extensionBuilder.override(
				AuxiliaryArchiveAppender.class, JUnitDeploymentAppender.class,
				JUnitBridgeAuxiliaryArchiveAppender.class);
			extensionBuilder.service(
				BundleActivatorsManager.class,
				BundleActivatorsManagerImpl.class);
			extensionBuilder.service(
				CommandService.class, JMXCommandService.class);
			extensionBuilder.override(
				DeployableContainer.class, KarafRemoteDeployableContainer.class,
				LiferayRemoteDeployableContainer.class);
			extensionBuilder.service(
				DeploymentScenarioGenerator.class,
				BndDeploymentScenarioGenerator.class);
		}
		else {
			extensionBuilder.observer(JUnitBridgeObserver.class);
		}
	}

}