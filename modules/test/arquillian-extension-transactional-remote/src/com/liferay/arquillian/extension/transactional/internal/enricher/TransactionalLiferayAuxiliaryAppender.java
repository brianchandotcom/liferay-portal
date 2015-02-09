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

package com.liferay.arquillian.extension.transactional.internal.enricher;

import com.liferay.arquillian.extension.transactional.internal.container.TransactionalRemoteExtension;
import com.liferay.arquillian.extension.transactional.internal.observer.TransactionalObserver;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import java.util.jar.Manifest;

import org.jboss.arquillian.container.test.spi.RemoteLoadableExtension;
import org.jboss.arquillian.container.test.spi.client.deployment.AuxiliaryArchiveAppender;
import org.jboss.osgi.metadata.OSGiManifestBuilder;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.ByteArrayAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;

/**
 * @author Carlos Sierra Andrés
 */
public class TransactionalLiferayAuxiliaryAppender
	implements AuxiliaryArchiveAppender {

	@Override
	public Archive<?> createAuxiliaryArchive() {
		JavaArchive archive = ShrinkWrap.create(
			JavaArchive.class, "arquillian-liferay-transactional.jar");

		final OSGiManifestBuilder builder = OSGiManifestBuilder.newInstance();

		builder.addImportPackages(
			"com.liferay.portal.kernel.util", "com.liferay.portal.util",
			"org.jboss.arquillian.core.api",
			"org.jboss.arquillian.core.api.annotation",
			"org.jboss.arquillian.core.spi",
			"org.jboss.arquillian.core.spi.event",
			"org.jboss.arquillian.test.spi",
			"org.jboss.arquillian.test.spi.event.suite");

		builder.addBundleManifestVersion(1);

		Manifest manifest = builder.getManifest();

		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		try {
			manifest.write(baos);
		}
		catch (IOException e) {
			e.printStackTrace();
		}

		ByteArrayAsset byteArrayAsset = new ByteArrayAsset(baos.toByteArray());

		archive.add(byteArrayAsset, "/META-INF/MANIFEST.MF");

		archive.addAsServiceProvider(
			RemoteLoadableExtension.class, TransactionalRemoteExtension.class);

		archive.addClass(TransactionalRemoteExtension.class);

		archive.addPackages(
			true, TransactionalRemoteExtension.class.getPackage(),
			TransactionalObserver.class.getPackage());

		return archive;
	}

}