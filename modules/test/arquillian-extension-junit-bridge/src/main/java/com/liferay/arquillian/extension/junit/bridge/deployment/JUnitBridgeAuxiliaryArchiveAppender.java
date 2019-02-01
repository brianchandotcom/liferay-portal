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
import com.liferay.petra.io.unsync.UnsyncByteArrayOutputStream;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;

import java.io.IOException;

import java.util.jar.Attributes;
import java.util.jar.Manifest;

import org.jboss.arquillian.container.test.spi.RemoteLoadableExtension;
import org.jboss.arquillian.container.test.spi.client.deployment.AuxiliaryArchiveAppender;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.Asset;
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
		javaArchive.add(_createManifestAsset(), "/META-INF/MANIFEST.MF");
		javaArchive.addAsServiceProviderAndClasses(
			RemoteLoadableExtension.class,
			LiferayArquillianJUnitBridgeExtension.class);
		javaArchive.addClasses(Arquillian.class, JUnitBridgeObserver.class);
		javaArchive.addPackages(false, Arquillian.class.getPackage());

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

	private Asset _createManifestAsset() {
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
				"org.junit.runner.manipulation",
				"org.junit.runner.notification"));
		attributes.putValue("Manifest-Version", "2");

		UnsyncByteArrayOutputStream unsyncByteArrayOutputStream =
			new UnsyncByteArrayOutputStream();

		try {
			manifest.write(unsyncByteArrayOutputStream);
		}
		catch (IOException ioe) {
			throw new RuntimeException(ioe);
		}

		return new ByteArrayAsset(unsyncByteArrayOutputStream.toByteArray());
	}

}