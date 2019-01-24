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

package com.liferay.arquillian.extension.junit.bridge.observer;

import com.liferay.arquillian.extension.junit.bridge.container.remote.LiferayRemoteDeployableContainer;

import java.util.jar.Attributes;
import java.util.jar.Manifest;

import org.jboss.arquillian.container.spi.client.deployment.DeploymentDescription;
import org.jboss.arquillian.container.spi.event.container.AfterDeploy;
import org.jboss.arquillian.core.api.annotation.Observes;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.Node;
import org.jboss.shrinkwrap.api.asset.Asset;

import org.osgi.framework.Constants;

/**
 * @author Matthew Tambara
 */
public class DeploymentObserver {

	public void autostartBundle(@Observes AfterDeploy event) throws Exception {
		LiferayRemoteDeployableContainer liferayRemoteDeployableContainer =
			(LiferayRemoteDeployableContainer)event.getDeployableContainer();

		DeploymentDescription deploymentDescription = event.getDeployment();

		Archive archive = deploymentDescription.getArchive();

		Node node = archive.get("/META-INF/MANIFEST.MF");

		Asset asset = node.getAsset();

		Manifest manifest = new Manifest(asset.openStream());

		Attributes attributes = manifest.getMainAttributes();

		liferayRemoteDeployableContainer.startBundle(
			attributes.getValue(Constants.BUNDLE_SYMBOLICNAME),
			attributes.getValue(Constants.BUNDLE_VERSION));
	}

}