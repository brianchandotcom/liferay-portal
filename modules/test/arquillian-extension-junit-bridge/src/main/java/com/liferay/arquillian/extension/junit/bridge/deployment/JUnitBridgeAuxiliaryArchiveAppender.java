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
import com.liferay.arquillian.extension.junit.bridge.junit.container.JUnitTestRunner;
import com.liferay.arquillian.extension.junit.bridge.observer.JUnitBridgeObserver;
import com.liferay.petra.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.petra.io.unsync.UnsyncByteArrayOutputStream;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;

import java.io.IOException;
import java.io.InputStream;

import java.util.jar.Attributes;
import java.util.jar.Manifest;

import org.jboss.arquillian.container.test.spi.RemoteLoadableExtension;
import org.jboss.arquillian.container.test.spi.TestRunner;
import org.jboss.arquillian.container.test.spi.client.deployment.AuxiliaryArchiveAppender;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.ByteArrayAsset;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;

import org.osgi.framework.Constants;

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
		javaArchive.addAsServiceProviderAndClasses(
			TestRunner.class, JUnitTestRunner.class);
		javaArchive.addClasses(Arquillian.class, JUnitBridgeObserver.class);
		javaArchive.addPackages(false, Arquillian.class.getPackage());

		try (InputStream inputStream = _writeManifest()) {
			javaArchive.add(
				new ByteArrayAsset(inputStream), "/META-INF/MANIFEST.MF");
		}
		catch (IOException ioe) {
			throw new IllegalStateException("Unable to add manifest", ioe);
		}

		return javaArchive;
	}

	private String _buildImportPackageString(String... strings) {
		StringBundler sb = new StringBundler(strings.length * 2);

		for (String string : strings) {
			sb.append(string);
			sb.append(StringPool.COMMA);
		}

		sb.setIndex(sb.index() - 1);

		return sb.toString();
	}

	private InputStream _writeManifest() throws IOException {
		Manifest manifest = new Manifest();

		Attributes attributes = manifest.getMainAttributes();

		attributes.putValue(Constants.BUNDLE_MANIFESTVERSION, "2");
		attributes.putValue(
			Constants.BUNDLE_SYMBOLICNAME, "arquillian.junit.bridge");
		attributes.putValue(Constants.BUNDLE_VERSION, "1.0.0");
		attributes.putValue(
			Constants.IMPORT_PACKAGE,
			_buildImportPackageString(
				"org.junit.internal", "org.junit.internal.runners",
				"org.junit.internal.runners.statements",
				"org.junit.internal.runners.model", "org.junit.rules",
				"org.junit.runners", "org.junit.runners.model",
				"org.junit.runner.notification"));
		attributes.putValue("Manifest-Version", "2");

		UnsyncByteArrayOutputStream unsyncByteArrayOutputStream =
			new UnsyncByteArrayOutputStream();

		manifest.write(unsyncByteArrayOutputStream);

		return new UnsyncByteArrayInputStream(
			unsyncByteArrayOutputStream.toByteArray());
	}

}