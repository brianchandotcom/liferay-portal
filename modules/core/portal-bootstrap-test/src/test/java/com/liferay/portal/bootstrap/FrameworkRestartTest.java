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

package com.liferay.portal.bootstrap;

import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayOutputStream;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.NewEnv;
import com.liferay.portal.kernel.test.rule.NewEnvTestRule;
import com.liferay.portal.kernel.util.ServiceLoader;

import java.io.IOException;
import java.io.InputStream;

import java.net.URL;
import java.net.URLClassLoader;

import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import java.security.CodeSource;
import java.security.ProtectionDomain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;
import java.util.zip.ZipEntry;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.framework.FrameworkEvent;
import org.osgi.framework.launch.Framework;
import org.osgi.framework.launch.FrameworkFactory;
import org.osgi.framework.wiring.FrameworkWiring;

/**
 * @author Matthew Tambara
 */
public class FrameworkRestartTest {

	public static void doTestFrameworkRestart() throws Exception {
		URL url = FrameworkRestartTest.class.getResource("security.policy");

		System.setProperty("java.security.policy", url.getFile());

		System.setSecurityManager(new SecurityManager());

		Map<String, String> properties = new HashMap<>();

		Path frameworkStoragePath = Files.createTempDirectory(null);

		properties.put(
			Constants.FRAMEWORK_STORAGE, frameworkStoragePath.toString());

		List<FrameworkFactory> frameworkFactories = ServiceLoader.load(
			FrameworkRestartTest.class.getClassLoader(),
			FrameworkFactory.class);

		FrameworkFactory frameworkFactory = frameworkFactories.get(0);

		Framework framework = frameworkFactory.newFramework(properties);

		Path storagePath = frameworkStoragePath.resolve("org.eclipse.osgi");

		Path frameworkInfoStoragePath = storagePath.resolve("framework.info.1");
		Path managerStoragePath = storagePath.resolve(".manager");

		Path backupPath = frameworkStoragePath.resolve("backup");

		Files.createDirectory(backupPath);

		Path frameworkInfoBackupPath = backupPath.resolve("framework.info.1");
		Path managerBackupPath = backupPath.resolve(".manager");

		try {
			framework.start();

			BundleContext bundleContext = framework.getBundleContext();

			List<Bundle> bundles = new ArrayList<>();

			try (InputStream inputStream = _createJAR(
					"com.liferay.test.1", "1.0.0", null, _TEST_EXPORT)) {

				bundles.add(
					bundleContext.installBundle("testLocation1", inputStream));
			}

			try (InputStream inputStream = _createJAR(
					"com.liferay.test.2", "1.0.0", _TEST_EXPORT, null)) {

				bundles.add(
					bundleContext.installBundle("testLocation2", inputStream));
			}

			FrameworkWiring frameworkWiring = framework.adapt(
				FrameworkWiring.class);

			frameworkWiring.resolveBundles(bundles);

			_stopFramework(framework);

			Files.move(frameworkInfoStoragePath, frameworkInfoBackupPath);
			Files.move(managerStoragePath, managerBackupPath);

			framework.start();

			try (InputStream inputStream = _createJAR(
					"com.liferay.test.2", "2.0.0", _TEST_EXPORT, null)) {

				Bundle bundle = bundles.get(0);

				bundle.update(inputStream);
			}

			_stopFramework(framework);

			_delete(frameworkInfoStoragePath);
			_delete(managerStoragePath);

			Files.move(frameworkInfoBackupPath, frameworkInfoStoragePath);
			Files.move(managerBackupPath, managerStoragePath);

			Files.walkFileTree(
				storagePath,
				new SimpleFileVisitor<Path>() {

					@Override
					public FileVisitResult visitFile(
							Path filePath,
							BasicFileAttributes basicFileAttributes)
						throws IOException {

						Path fileNamePath = filePath.getFileName();

						String fileNameString = fileNamePath.toString();

						if (fileNameString.equals("bundleFile")) {
							Files.delete(filePath);
						}

						return FileVisitResult.CONTINUE;
					}

				});

			frameworkFactory.newFramework(properties);
		}
		finally {
			_stopFramework(framework);

			_delete(frameworkStoragePath);
		}
	}

	@NewEnv(type = NewEnv.Type.JVM)
	@Test
	public void testFrameworkRestart() throws Exception {
		ClassLoader classLoader = new URLClassLoader(
			_getURLS(
				Assert.class, FrameworkFactory.class,
				FrameworkRestartTest.class, UnsyncByteArrayOutputStream.class),
			null);

		Class<?> clazz = classLoader.loadClass(
			FrameworkRestartTest.class.getName());

		ReflectionTestUtil.invoke(
			clazz, "doTestFrameworkRestart", new Class<?>[0]);
	}

	@Rule
	public final NewEnvTestRule newEnvTestRule = NewEnvTestRule.INSTANCE;

	private static InputStream _createJAR(
			String symbolicName, String version, String exportPackage,
			String importPackage)
		throws IOException {

		try (UnsyncByteArrayOutputStream unsyncByteArrayOutputStream =
				new UnsyncByteArrayOutputStream()) {

			try (JarOutputStream jarOutputStream = new JarOutputStream(
					unsyncByteArrayOutputStream)) {

				Manifest manifest = new Manifest();

				Attributes attributes = manifest.getMainAttributes();

				attributes.putValue(Constants.BUNDLE_MANIFESTVERSION, "2");
				attributes.putValue(
					Constants.BUNDLE_SYMBOLICNAME, symbolicName);
				attributes.putValue(Constants.BUNDLE_VERSION, version);
				attributes.putValue("Manifest-Version", "2");

				if (exportPackage != null) {
					attributes.putValue(
						Constants.EXPORT_PACKAGE,
						exportPackage.concat(";version=\"1.0.0\""));
				}

				if (importPackage != null) {
					attributes.putValue(
						Constants.IMPORT_PACKAGE,
						importPackage.concat(";version=\"1.0.0\""));
				}

				jarOutputStream.putNextEntry(
					new ZipEntry(JarFile.MANIFEST_NAME));

				manifest.write(jarOutputStream);

				jarOutputStream.closeEntry();
			}

			return new UnsyncByteArrayInputStream(
				unsyncByteArrayOutputStream.unsafeGetByteArray(), 0,
				unsyncByteArrayOutputStream.size());
		}
	}

	private static void _delete(Path path) throws IOException {
		Files.walkFileTree(
			path,
			new SimpleFileVisitor<Path>() {

				@Override
				public FileVisitResult postVisitDirectory(
						Path dirPath, IOException ioe)
					throws IOException {

					Files.delete(dirPath);

					return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult visitFile(
						Path filePath, BasicFileAttributes basicFileAttributes)
					throws IOException {

					Files.delete(filePath);

					return FileVisitResult.CONTINUE;
				}

			});
	}

	private static void _stopFramework(Framework framework) throws Exception {
		framework.stop();

		FrameworkEvent frameworkEvent = framework.waitForStop(0);

		Assert.assertEquals(FrameworkEvent.STOPPED, frameworkEvent.getType());
	}

	private URL[] _getURLS(Class<?>... classes) {
		URL[] urls = new URL[classes.length];

		for (int i = 0; i < classes.length; i++) {
			ProtectionDomain protectionDomain =
				classes[i].getProtectionDomain();

			CodeSource codeSource = protectionDomain.getCodeSource();

			urls[i] = codeSource.getLocation();
		}

		return urls;
	}

	private static final String _TEST_EXPORT = "com.test.liferay.export";

}