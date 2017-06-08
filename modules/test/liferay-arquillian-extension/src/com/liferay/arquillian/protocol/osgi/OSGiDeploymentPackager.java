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

package com.liferay.arquillian.protocol.osgi;

import com.liferay.arquillian.container.activator.ArquillianBundleActivator;
import com.liferay.arquillian.junit.runner.JUnitBundleTestRunner;
import com.liferay.arquillian.util.PropsUtil;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.jar.Attributes;
import java.util.jar.Attributes.Name;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

import org.jboss.arquillian.container.test.spi.RemoteLoadableExtension;
import org.jboss.arquillian.container.test.spi.TestDeployment;
import org.jboss.arquillian.container.test.spi.TestRunner;
import org.jboss.arquillian.container.test.spi.client.deployment.DeploymentPackager;
import org.jboss.arquillian.container.test.spi.client.deployment.ProtocolArchiveProcessor;
import org.jboss.osgi.metadata.OSGiManifestBuilder;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.Node;
import org.jboss.shrinkwrap.api.asset.ArchiveAsset;
import org.jboss.shrinkwrap.api.asset.Asset;
import org.jboss.shrinkwrap.api.asset.ByteArrayAsset;
import org.jboss.shrinkwrap.api.asset.FileAsset;
import org.jboss.shrinkwrap.api.exporter.ZipExporter;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.jboss.shrinkwrap.resolver.api.maven.MavenFormatStage;
import org.jboss.shrinkwrap.resolver.api.maven.MavenResolverSystem;
import org.jboss.shrinkwrap.resolver.api.maven.MavenStrategyStage;

import org.osgi.framework.BundleActivator;

/**
 * @author Shuyang Zhou
 */
public class OSGiDeploymentPackager implements DeploymentPackager {

	@Override
	public Archive<?> generateDeployment(
		TestDeployment testDeployment,
		Collection<ProtocolArchiveProcessor> protocolArchiveProcessors) {

		JavaArchive javaArchive =
			(JavaArchive)testDeployment.getApplicationArchive();

		Node manifestNode = javaArchive.delete(JarFile.MANIFEST_NAME);

		if (manifestNode == null) {
			throw new IllegalArgumentException(
				"Missing " + JarFile.MANIFEST_NAME + " in " + javaArchive);
		}

		Asset manifestAsset = manifestNode.getAsset();

		try {
			Manifest manifest = new Manifest(manifestAsset.openStream());

			OSGiManifestBuilder.validateBundleManifest(manifest);

			ManifestAssetBuilder manifestAssetBuilder =
				new ManifestAssetBuilder(manifest);

			_registerRemoteLoadableExtensions(
				javaArchive, manifestAssetBuilder,
				testDeployment.getAuxiliaryArchives());

			manifestAssetBuilder.addImports(
				Arrays.asList(
					StringUtil.split(
						PropsUtil.get("bundle.import.packages"),
						CharPool.SEMICOLON)));

			_addBundleImportFiles(manifestAssetBuilder);
			_addBundleClassPathFiles(manifestAssetBuilder, javaArchive);

			javaArchive.addClass(ArquillianBundleActivator.class);

			javaArchive.add(
				manifestAssetBuilder.build(), JarFile.MANIFEST_NAME);
			javaArchive.addAsServiceProvider(
				BundleActivator.class.getName(),
				manifestAssetBuilder.getBundleActivators());
			javaArchive.addAsServiceProviderAndClasses(
				TestRunner.class, JUnitBundleTestRunner.class);

			if (GetterUtil.getBoolean(
					PropsUtil.get("dump.generated.archives"))) {

				ZipExporter zipExporter = javaArchive.as(ZipExporter.class);

				File file = new File(
					OSGiDeploymentPackager.class.getName() + "-" +
						javaArchive.getName());

				zipExporter.exportTo(file);

				System.out.println("Dumpped file " + file.getAbsolutePath());
			}

			return javaArchive;
		}
		catch (Exception e) {
			throw new IllegalArgumentException(
				"Unable to repackage OSGi bundle: " + javaArchive, e);
		}
	}

	public class ManifestAssetBuilder {

		public ManifestAssetBuilder(Manifest manifest) {
			Attributes mainAttributes = manifest.getMainAttributes();

			mainAttributes.remove(Name.MANIFEST_VERSION);

			String bundleActivator = (String)mainAttributes.remove(
				new Name("Bundle-Activator"));

			if (bundleActivator != null) {
				_bundleActivators.add(bundleActivator);
			}

			String importPackage = (String)mainAttributes.remove(
				new Name("Import-Package"));

			if (importPackage != null) {
				_imports.addAll(Arrays.asList(StringUtil.split(importPackage)));
			}

			String exportPackage = (String)mainAttributes.remove(
				new Name("Export-Package"));

			if (exportPackage != null) {
				_exports.addAll(Arrays.asList(StringUtil.split(exportPackage)));
			}

			for (Map.Entry<Object, Object> entry : mainAttributes.entrySet()) {
				_osgiManifestBuilder.addManifestHeader(
					String.valueOf(entry.getKey()),
					String.valueOf(entry.getValue()));
			}
		}

		public void addBundleActivator(String bundleActivator) {
			_bundleActivators.add(bundleActivator);
		}

		public void addBundleClassPathFile(String classPathFile) {
			_classPathFiles.add(classPathFile);
		}

		public void addImports(Collection<String> imports) {
			_imports.addAll(imports);
		}

		public Asset build() {
			_imports.removeAll(
				Arrays.asList(
					StringUtil.split(
						PropsUtil.get("bundle.import.exclude.packages"),
						CharPool.SEMICOLON)));

			_osgiManifestBuilder.addImportPackages(
				_imports.toArray(new String[_imports.size()]));
			_osgiManifestBuilder.addExportPackages(
				_exports.toArray(new String[_exports.size()]));
			_osgiManifestBuilder.addBundleClasspath(
				StringUtil.merge(_classPathFiles));
			_osgiManifestBuilder.addBundleActivator(
				ArquillianBundleActivator.class);

			return new ByteArrayAsset(_osgiManifestBuilder.openStream());
		}

		public String[] getBundleActivators() {
			return _bundleActivators.toArray(
				new String[_bundleActivators.size()]);
		}

		private final List<String> _bundleActivators = new ArrayList<>();
		private final List<String> _classPathFiles = new ArrayList<>(
			Arrays.asList(StringPool.PERIOD));
		private final List<String> _exports = new ArrayList<>();
		private final List<String> _imports = new ArrayList<>();
		private final OSGiManifestBuilder _osgiManifestBuilder =
			OSGiManifestBuilder.newInstance();

	}

	private void _addBundleClassPathFiles(
		ManifestAssetBuilder manifestAssetBuilder, JavaArchive javaArchive) {

		for (String bundleClassPathFile :
				StringUtil.split(
					PropsUtil.get("bundle.classpath.files"),
					CharPool.SEMICOLON)) {

			String[] coordinates = StringUtil.split(
				bundleClassPathFile, CharPool.COMMA);

			if (coordinates.length != 3) {
				throw new IllegalArgumentException(
					"Malformed maven coordinates " + bundleClassPathFile);
			}

			File file = _fetchMavenFile(
				coordinates[0], coordinates[1], coordinates[2]);

			String path = "lib/" + file.getName();

			javaArchive.addAsResource(new FileAsset(file), path);

			manifestAssetBuilder.addBundleClassPathFile(path);
		}
	}

	private void _addBundleImportFiles(
			ManifestAssetBuilder manifestAssetBuilder)
		throws IOException {

		for (String bundleClassPathFile :
				StringUtil.split(
					PropsUtil.get("bundle.import.files"), CharPool.SEMICOLON)) {

			String[] coordinates = StringUtil.split(
				bundleClassPathFile, CharPool.COMMA);

			if (coordinates.length != 3) {
				throw new IllegalArgumentException(
					"Malformed maven coordinates " + bundleClassPathFile);
			}

			manifestAssetBuilder.addImports(
				_listPackagesInJarFile(
					coordinates[0], coordinates[1], coordinates[2]));
		}
	}

	private File _fetchMavenFile(
		String groupId, String artifactId, String version) {

		MavenResolverSystem mavenResolverSystem = Maven.resolver();

		MavenStrategyStage mavenStrategyStage = mavenResolverSystem.resolve(
			groupId + ":" + artifactId + ":jar:" + version);

		MavenFormatStage mavenFormatStage =
			mavenStrategyStage.withoutTransitivity();

		return mavenFormatStage.asSingleFile();
	}

	private Set<String> _listPackagesInJarFile(
			String groupId, String artifactId, String version)
		throws IOException {

		Set<String> packageNames = new HashSet<>();

		File file = _fetchMavenFile(groupId, artifactId, version);

		try (JarFile jarFile = new JarFile(file)) {
			Enumeration<JarEntry> enumeration = jarFile.entries();

			while (enumeration.hasMoreElements()) {
				JarEntry jarEntry = enumeration.nextElement();

				if (!jarEntry.isDirectory()) {
					String name = jarEntry.getName();

					if (name.endsWith(".class")) {
						int index = name.lastIndexOf('/');

						if (index >= 0) {
							name = name.substring(0, index);

							if (!name.isEmpty()) {
								packageNames.add(name.replace('/', '.'));
							}
						}
					}
				}
			}
		}

		return packageNames;
	}

	private void _registerRemoteLoadableExtensions(
			JavaArchive javaArchive, ManifestAssetBuilder manifestAssetBuilder,
			Collection<Archive<?>> auxiliaryArchives)
		throws IOException {

		for (Archive<?> auxiliaryArchive : auxiliaryArchives) {
			Node remoteLoadableExtensionNode = auxiliaryArchive.get(
				_REMOTE_LOADABLE_EXTENSION_FILE);

			if (remoteLoadableExtensionNode == null) {
				continue;
			}

			javaArchive.add(
				remoteLoadableExtensionNode.getAsset(),
				_REMOTE_LOADABLE_EXTENSION_FILE);

			String path = "extension/" + auxiliaryArchive.getName();

			javaArchive.addAsResource(
				new ArchiveAsset(auxiliaryArchive, ZipExporter.class), path);

			manifestAssetBuilder.addBundleClassPathFile(path);

			Node manifestNode = auxiliaryArchive.get(JarFile.MANIFEST_NAME);

			if (manifestNode == null) {
				continue;
			}

			Asset manifestAsset = manifestNode.getAsset();

			Manifest manifest = new Manifest(manifestAsset.openStream());

			if (OSGiManifestBuilder.isValidBundleManifest(manifest)) {
				Attributes attributes = manifest.getMainAttributes();

				String value = attributes.getValue("Import-package");

				if (value != null) {
					manifestAssetBuilder.addImports(
						Arrays.asList(StringUtil.split(value)));
				}

				String bundleActivator = attributes.getValue(
					"Bundle-Activator");

				if (bundleActivator != null) {
					manifestAssetBuilder.addBundleActivator(bundleActivator);
				}
			}
		}
	}

	private static final String _REMOTE_LOADABLE_EXTENSION_FILE =
		"/META-INF/services/" +
			RemoteLoadableExtension.class.getCanonicalName();

}