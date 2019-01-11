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

package com.liferay.arquillian.extension.junit.bridge.protocol.osgi;

import java.util.Collection;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

import org.jboss.arquillian.container.test.spi.TestDeployment;
import org.jboss.arquillian.container.test.spi.client.deployment.DeploymentPackager;
import org.jboss.arquillian.container.test.spi.client.deployment.ProtocolArchiveProcessor;
import org.jboss.osgi.metadata.OSGiManifestBuilder;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.Node;

/**
 * @author Matthew Tambara
 */
public class OSGiDeploymentPackager implements DeploymentPackager {

	@Override
	public Archive<?> generateDeployment(
		TestDeployment testDeployment,
		Collection<ProtocolArchiveProcessor> protocolArchiveProcessors) {

		return _handleArchive(testDeployment.getApplicationArchive());
	}

	private Archive<?> _handleArchive(Archive<?> archive) {
		try {
			_validateBundleArchive(archive);

			return archive;
		}
		catch (Exception e) {
			throw new IllegalArgumentException(
				"Not a valid OSGi bundle: " + archive, e);
		}
	}

	private void _validateBundleArchive(Archive<?> archive) throws Exception {
		Manifest manifest = null;

		Node node = archive.get(JarFile.MANIFEST_NAME);

		if (node != null) {
			manifest = new Manifest(node.getAsset().openStream());
		}

		OSGiManifestBuilder.validateBundleManifest(manifest);
	}

}