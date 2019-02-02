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

package com.liferay.arquillian.extension.junit.bridge.remote.processor;

import com.liferay.arquillian.extension.junit.bridge.LiferayArquillianJUnitBridgeExtension;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.arquillian.extension.junit.bridge.observer.JUnitBridgeObserver;
import com.liferay.arquillian.extension.junit.bridge.protocol.jmx.JMXTestRunner;
import com.liferay.arquillian.extension.junit.bridge.remote.activator.ArquillianBundleActivator;
import com.liferay.petra.io.unsync.UnsyncByteArrayOutputStream;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.petra.string.StringUtil;

import java.io.IOException;
import java.io.InputStream;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

import org.jboss.arquillian.container.test.impl.ContainerTestRemoteExtension;
import org.jboss.arquillian.container.test.impl.RemoteExtensionLoader;
import org.jboss.arquillian.container.test.spi.RemoteLoadableExtension;
import org.jboss.arquillian.container.test.spi.client.deployment.ApplicationArchiveProcessor;
import org.jboss.arquillian.core.spi.ExtensionLoader;
import org.jboss.arquillian.test.spi.TestClass;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.Node;
import org.jboss.shrinkwrap.api.asset.Asset;
import org.jboss.shrinkwrap.api.asset.ByteArrayAsset;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;

/**
 * @author Matthew Tambara
 */
public class OSGiAllInProcessor implements ApplicationArchiveProcessor {

	@Override
	public void process(Archive<?> archive, TestClass testClass) {
		JavaArchive javaArchive = (JavaArchive)archive;

		try {
			_addTestClass(javaArchive, testClass);

			_addArquillianDependencies(javaArchive);

			javaArchive.add(EmptyAsset.INSTANCE, "/arquillian.remote.marker");
			javaArchive.addClasses(
				LiferayArquillianJUnitBridgeExtension.class,
				JUnitBridgeObserver.class);
			javaArchive.addAsServiceProvider(
				RemoteLoadableExtension.class,
				ContainerTestRemoteExtension.class,
				LiferayArquillianJUnitBridgeExtension.class);
			javaArchive.addAsServiceProvider(
				ExtensionLoader.class, RemoteExtensionLoader.class);

			Package pkg = Arquillian.class.getPackage();

			javaArchive.addPackages(false, pkg);

			Manifest manifest = _getManifest(javaArchive);

			Map<String, String> importPackages = _createImportPackages(
				manifest);

			importPackages.remove(pkg.getName());

			_setManifestValues(
				manifest, _importPackageName, importPackages.values());

			Attributes mainAttributes = manifest.getMainAttributes();

			mainAttributes.put(
				_bundleActivatorName,
				ArquillianBundleActivator.class.getCanonicalName());

			_setManifest(javaArchive, manifest);

			javaArchive.addClass(ArquillianBundleActivator.class);
		}
		catch (IOException ioe) {
			throw new IllegalArgumentException(
				"Invalid OSGi bundle: " + javaArchive, ioe);
		}
	}

	private void _addArquillianDependencies(JavaArchive javaArchive) {
		javaArchive.addPackage(JMXTestRunner.class.getPackage());
		javaArchive.addPackages(
			true, "org.jboss.arquillian.core",
			"org.jboss.arquillian.container.spi",
			"org.jboss.arquillian.container.impl",
			"org.jboss.arquillian.container.test.api",
			"org.jboss.arquillian.container.test.spi",
			"org.jboss.arquillian.container.test.impl",
			"org.jboss.arquillian.config", "org.jboss.arquillian.test",
			"org.jboss.shrinkwrap.api", "org.jboss.shrinkwrap.descriptor.api");
	}

	private void _addTestClass(JavaArchive javaArchive, TestClass testClass) {
		Class<?> javaClass = testClass.getJavaClass();

		while (javaClass != Object.class) {
			javaArchive.addClass(javaClass);

			javaClass = javaClass.getSuperclass();
		}
	}

	private Map<String, String> _createImportPackages(Manifest manifest) {
		Map<String, String> importPackages = new LinkedHashMap<>();

		for (String importPackage : _IMPORTS_PACKAGES) {
			importPackages.put(importPackage, importPackage);
		}

		Attributes mainAttributes = manifest.getMainAttributes();

		List<String> originalImportPackages = StringUtil.split(
			mainAttributes.getValue(_importPackageName));

		Iterator<String> iterator = originalImportPackages.iterator();

		packages:
		while (iterator.hasNext()) {
			String originalImportPackage = iterator.next();

			String importPackage = originalImportPackage;

			int index = originalImportPackage.indexOf(CharPool.SEMICOLON);

			if (index != -1) {
				importPackage = originalImportPackage.substring(0, index);
			}

			importPackages.put(importPackage, originalImportPackage);
		}

		return importPackages;
	}

	private Manifest _getManifest(Archive<?> archive) throws IOException {
		Node manifestNode = archive.get(JarFile.MANIFEST_NAME);

		if (manifestNode == null) {
			return null;
		}

		Asset manifestAsset = manifestNode.getAsset();

		try (InputStream inputStream = manifestAsset.openStream()) {
			return new Manifest(inputStream);
		}
	}

	private void _setManifest(Archive<?> archive, Manifest manifest)
		throws IOException {

		archive.delete(JarFile.MANIFEST_NAME);

		UnsyncByteArrayOutputStream unsyncByteArrayOutputStream =
			new UnsyncByteArrayOutputStream();

		manifest.write(unsyncByteArrayOutputStream);

		archive.add(
			new ByteArrayAsset(unsyncByteArrayOutputStream.toByteArray()),
			JarFile.MANIFEST_NAME);
	}

	private void _setManifestValues(
		Manifest manifest, Attributes.Name attributeName,
		Collection<String> attributeValues) {

		Attributes mainAttributes = manifest.getMainAttributes();

		StringBundler sb = new StringBundler(attributeValues.size() * 2);

		for (String attributeValue : attributeValues) {
			sb.append(attributeValue);
			sb.append(StringPool.COMMA);
		}

		sb.setIndex(sb.index() - 1);

		mainAttributes.put(attributeName, sb.toString());
	}

	private static final String[] _IMPORTS_PACKAGES = {
		"org.osgi.framework", "org.osgi.framework.wiring", "javax.management",
		"javax.management.*", "javax.naming", "javax.naming.*",
		"org.osgi.service.packageadmin", "org.osgi.service.startlevel",
		"org.osgi.util.tracker", "org.junit.internal",
		"org.junit.internal.runners", "org.junit.internal.runners.statements",
		"org.junit.internal.runners.model", "org.junit.rules",
		"org.junit.runners", "org.junit.runners.model",
		"org.junit.runner.manipulation", "org.junit.runner.notification"
	};

	private static final Attributes.Name _bundleActivatorName =
		new Attributes.Name("Bundle-Activator");
	private static final Attributes.Name _importPackageName =
		new Attributes.Name("Import-Package");

}