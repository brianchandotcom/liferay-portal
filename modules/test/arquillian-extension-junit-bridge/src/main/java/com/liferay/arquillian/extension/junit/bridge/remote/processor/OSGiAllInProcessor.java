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

import com.liferay.arquillian.container.osgi.remote.activator.ArquillianBundleActivator;
import com.liferay.arquillian.container.osgi.remote.processor.service.BundleActivatorsManager;
import com.liferay.petra.io.unsync.UnsyncByteArrayOutputStream;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.petra.string.StringUtil;

import java.io.IOException;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.jar.Attributes;
import java.util.jar.Attributes.Name;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

import org.jboss.arquillian.container.test.spi.client.deployment.ApplicationArchiveProcessor;
import org.jboss.arquillian.container.test.spi.client.deployment.AuxiliaryArchiveAppender;
import org.jboss.arquillian.core.api.Instance;
import org.jboss.arquillian.core.api.annotation.Inject;
import org.jboss.arquillian.core.spi.ServiceLoader;
import org.jboss.arquillian.protocol.jmx.JMXTestRunner;
import org.jboss.arquillian.test.spi.TestClass;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ArchivePath;
import org.jboss.shrinkwrap.api.ArchivePaths;
import org.jboss.shrinkwrap.api.Node;
import org.jboss.shrinkwrap.api.asset.ArchiveAsset;
import org.jboss.shrinkwrap.api.asset.Asset;
import org.jboss.shrinkwrap.api.asset.ByteArrayAsset;
import org.jboss.shrinkwrap.api.exporter.ZipExporter;
import org.jboss.shrinkwrap.api.spec.JavaArchive;

import org.osgi.framework.BundleActivator;

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

			Manifest manifest = _getManifest(javaArchive);

			List<String> importPackages = new ArrayList<>();

			List<Archive<?>> auxiliaryArchives = _loadAuxiliaryArchives(
				javaArchive, manifest, importPackages);

			_cleanRepeatedImports(auxiliaryArchives, manifest);

			_addOSGiImports(manifest, importPackages);

			Attributes mainAttributes = manifest.getMainAttributes();

			String bundleActivator = mainAttributes.getValue(
				_bundleActivatorName);

			mainAttributes.put(
				_bundleActivatorName,
				ArquillianBundleActivator.class.getCanonicalName());

			_setManifest(javaArchive, manifest);

			javaArchive.addClass(ArquillianBundleActivator.class);

			if (bundleActivator != null) {
				_addBundleActivator(javaArchive, bundleActivator);
			}
		}
		catch (IOException ioe) {
			throw new IllegalArgumentException(
				"Not a valid OSGi bundle: " + javaArchive, ioe);
		}
	}

	private void _addArquillianDependencies(JavaArchive javaArchive) {
		javaArchive.addPackage(JMXTestRunner.class.getPackage());
	}

	private void _addBundleActivator(
			JavaArchive javaArchive, String bundleActivatorValue)
		throws IOException {

		BundleActivatorsManager bundleActivatorsManager =
			_bundleActivatorsManagerInstance.get();

		List<String> bundleActivators =
			bundleActivatorsManager.getBundleActivators(
				javaArchive, _ACTIVATORS_FILE);

		bundleActivators.add(bundleActivatorValue);

		bundleActivatorsManager.replaceBundleActivatorsFile(
			javaArchive, _ACTIVATORS_FILE, bundleActivators);
	}

	private void _addManifestValues(
		Manifest manifest, Name attributeName,
		List<String> newAttributeValues) {

		Attributes mainAttributes = manifest.getMainAttributes();

		Set<String> attributeValues = new HashSet<>();

		String attributeValuesString = mainAttributes.getValue(attributeName);

		if (attributeValuesString != null) {
			attributeValues.addAll(StringUtil.split(attributeValuesString));
		}

		if (attributeValues.addAll(newAttributeValues)) {
			StringBundler sb = new StringBundler(attributeValues.size() * 2);

			for (String attributeValue : attributeValues) {
				sb.append(attributeValue);
				sb.append(StringPool.COMMA);
			}

			sb.setIndex(sb.index() - 1);

			mainAttributes.put(attributeName, sb.toString());
		}
	}

	private void _addOSGiImports(
		Manifest manifest, List<String> importPackages) {

		Collections.addAll(
			importPackages, "org.osgi.framework", "javax.management",
			"javax.management.*", "javax.naming", "javax.naming.*",
			"org.osgi.service.packageadmin", "org.osgi.service.startlevel",
			"org.osgi.util.tracker");

		_addManifestValues(manifest, _importPackageName, importPackages);
	}

	private void _addTestClass(JavaArchive javaArchive, TestClass testClass) {
		Class<?> javaClass = testClass.getJavaClass();

		while (javaClass != Object.class) {
			javaArchive.addClass(javaClass);

			javaClass = javaClass.getSuperclass();
		}
	}

	private void _cleanRepeatedImports(
			Collection<Archive<?>> auxiliaryArchives, Manifest manifest)
		throws IOException {

		Attributes mainAttributes = manifest.getMainAttributes();

		List<String> importPackages = StringUtil.split(
			mainAttributes.getValue(_importPackageName));

		boolean changed = false;

		Iterator<String> iterator = importPackages.iterator();

		packages:
		while (iterator.hasNext()) {
			String importPackage = iterator.next();

			int index = importPackage.indexOf(CharPool.SEMICOLON);

			if (index != -1) {
				importPackage = importPackage.substring(0, index);
			}

			ArchivePath archivePath = ArchivePaths.create(
				importPackage.replace(CharPool.PERIOD, CharPool.SLASH));

			for (Archive<?> archive : auxiliaryArchives) {
				if (archive.contains(archivePath)) {
					iterator.remove();

					changed = true;

					continue packages;
				}
			}
		}

		if (changed) {
			StringBundler sb = new StringBundler(importPackages.size());

			for (String importPackage : importPackages) {
				sb.append(importPackage);
				sb.append(StringPool.COMMA);
			}

			sb.setIndex(sb.index() - 1);

			mainAttributes.put(_importPackageName, sb.toString());
		}
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

	private List<Archive<?>> _loadAuxiliaryArchives(
			JavaArchive javaArchive, Manifest manifest,
			List<String> importPackages)
		throws IOException {

		List<Archive<?>> archives = new ArrayList<>();

		ServiceLoader serviceLoader = _serviceLoaderInstance.get();

		Collection<AuxiliaryArchiveAppender> archiveAppenders =
			serviceLoader.all(AuxiliaryArchiveAppender.class);

		List<String> bundleClassPaths = new ArrayList<>();

		bundleClassPaths.add(StringPool.PERIOD);

		for (AuxiliaryArchiveAppender archiveAppender : archiveAppenders) {
			Archive<?> auxiliaryArchive =
				archiveAppender.createAuxiliaryArchive();

			if (auxiliaryArchive == null) {
				continue;
			}

			archives.add(auxiliaryArchive);

			String path = "extension/" + auxiliaryArchive.getName();

			javaArchive.addAsResource(
				new ArchiveAsset(auxiliaryArchive, ZipExporter.class), path);

			bundleClassPaths.add(path);

			Manifest auxiliaryArchiveManifest = _getManifest(auxiliaryArchive);

			if (auxiliaryArchiveManifest == null) {
				continue;
			}

			Attributes mainAttributes =
				auxiliaryArchiveManifest.getMainAttributes();

			String value = mainAttributes.getValue(_importPackageName);

			if (value != null) {
				importPackages.addAll(StringUtil.split(value));
			}

			String bundleActivatorValue = mainAttributes.getValue(
				_bundleActivatorName);

			if ((bundleActivatorValue != null) &&
				!bundleActivatorValue.isEmpty()) {

				_addBundleActivator(javaArchive, bundleActivatorValue);
			}
		}

		_addManifestValues(manifest, _bundleClassPathName, bundleClassPaths);

		return archives;
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

	private static final String _ACTIVATORS_FILE =
		"/META-INF/services/" + BundleActivator.class.getCanonicalName();

	private static final Name _bundleActivatorName = new Name(
		"Bundle-Activator");
	private static final Name _bundleClassPathName = new Name(
		"Bundle-ClassPath");
	private static final Name _importPackageName = new Name("Import-Package");

	@Inject
	private Instance<BundleActivatorsManager> _bundleActivatorsManagerInstance;

	@Inject
	private Instance<ServiceLoader> _serviceLoaderInstance;

}