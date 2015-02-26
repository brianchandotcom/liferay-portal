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

import com.liferay.arquillian.extension.junit.bridge.LiferayArquillianJUnitBridgeExtension;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.arquillian.extension.junit.bridge.observer.JUnitBridgeObserver;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import java.util.jar.Manifest;

import org.jboss.arquillian.container.test.spi.RemoteLoadableExtension;
import org.jboss.arquillian.container.test.spi.TestRunner;
import org.jboss.arquillian.container.test.spi.client.deployment.AuxiliaryArchiveAppender;
import org.jboss.arquillian.junit.container.JUnitTestRunner;
import org.jboss.osgi.metadata.OSGiManifestBuilder;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.ByteArrayAsset;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;

/**
 * @author Shuyang Zhou
 */
public class JUnitBridgeAuxiliaryArchiveAppender
	implements AuxiliaryArchiveAppender {

	@Override
	public Archive<?> createAuxiliaryArchive() {
		JavaArchive javaArchive = ShrinkWrap.create(
			JavaArchive.class, "arquillian-junit-bridge.jar");

		javaArchive.add(EmptyAsset.INSTANCE, "/arquillian.remote.marker");
		javaArchive.addAsServiceProviderAndClasses(
			RemoteLoadableExtension.class,
			LiferayArquillianJUnitBridgeExtension.class);
		javaArchive.addClass(JUnitBridgeObserver.class);
		javaArchive.addClass(Arquillian.class);

		javaArchive.addPackage("org.jboss.arquillian.junit.event");

		javaArchive.addPackage(
			org.jboss.arquillian.junit.Arquillian.class.getPackage().getName());

		javaArchive.addClass(TestRunner.class);
		javaArchive.addClass(JUnitTestRunner.class);

		javaArchive.addAsServiceProvider(
			TestRunner.class, JUnitTestRunner.class);

		final OSGiManifestBuilder builder = OSGiManifestBuilder.newInstance();

		builder.addImportPackages(
			"org.junit.internal", "org.junit.internal.runners",
			"org.junit.internal.runners.statements",
			"org.junit.internal.runners.model", "org.junit.runners",
			"org.junit.runners.model", "org.junit.runner.notification"
		);

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

		javaArchive.add(byteArrayAsset, "/META-INF/MANIFEST.MF");

		return javaArchive;
	}

}