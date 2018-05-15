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
import com.liferay.portal.kernel.test.rule.NewEnv;
import com.liferay.portal.kernel.test.rule.NewEnvTestRule;
import com.liferay.portal.kernel.util.File;
import com.liferay.portal.util.FileImpl;

import java.io.IOException;
import java.io.InputStream;

import java.net.URL;

import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;
import java.util.zip.ZipEntry;

import org.junit.Rule;
import org.junit.Test;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.framework.launch.Framework;
import org.osgi.framework.launch.FrameworkFactory;
import org.osgi.framework.wiring.FrameworkWiring;

/**
 * @author Matthew Tambara
 */
public class FrameworkRestartTest {

	@NewEnv(type = NewEnv.Type.JVM)
	@Test
	public void testFrameworkRestart() throws Exception {
		URL url = FrameworkRestartTest.class.getResource("security.policy");

		System.setProperty("java.security.policy", url.getFile());

		System.setSecurityManager(new SecurityManager());

		Map<String, String> properties = new HashMap<>();

		Path tempDirPath = Files.createTempDirectory(null);

		String tempDirPathString = tempDirPath.toString();

		properties.put(Constants.FRAMEWORK_STORAGE, tempDirPathString);

		ServiceLoader<FrameworkFactory> serviceLoader = ServiceLoader.load(
			FrameworkFactory.class);

		Iterator<FrameworkFactory> iterator = serviceLoader.iterator();

		FrameworkFactory frameworkFactory = iterator.next();

		Framework framework = frameworkFactory.newFramework(properties);

		File file = new FileImpl();

		Files.createDirectory(Paths.get(tempDirPathString, _TEMP_DIR));

		String frameworkInfoStoragePath =
			tempDirPathString + _STORAGE_DIR + "framework.info.1";
		String frameworkInfoTempPath =
			tempDirPathString + _TEMP_DIR + "framework.info.1";
		String managerStoragePath =
			tempDirPathString + _STORAGE_DIR + ".manager";
		String managerTempPath = tempDirPathString + _TEMP_DIR + ".manager";

		try {
			framework.start();

			BundleContext bundleContext = framework.getBundleContext();

			List<Bundle> bundles = new ArrayList<>();

			Bundle bundle1 = null;

			try (InputStream inputStream = _createJAR(
					"com.liferay.test.1", "1.0.0", null, _TEST_EXPORT)) {

				bundle1 = bundleContext.installBundle(
					"testLocation1", inputStream);

				bundles.add(bundle1);
			}

			try (InputStream inputStream = _createJAR(
					"com.liferay.test.2", "1.0.0", _TEST_EXPORT, null)) {

				Bundle bundle2 = bundleContext.installBundle(
					"testLocation2", inputStream);

				bundles.add(bundle2);
			}

			FrameworkWiring frameworkWiring = framework.adapt(
				FrameworkWiring.class);

			frameworkWiring.resolveBundles(bundles);

			framework.stop();

			framework.waitForStop(0);

			file.move(frameworkInfoStoragePath, frameworkInfoTempPath);
			file.move(managerStoragePath, managerTempPath);

			framework.start();

			try (InputStream inputStream = _createJAR(
					"com.liferay.test.2", "2.0.0", _TEST_EXPORT, null)) {

				bundle1.update(inputStream);
			}

			framework.stop();

			framework.waitForStop(0);

			file.move(frameworkInfoTempPath, frameworkInfoStoragePath);
			file.move(managerTempPath, managerStoragePath);

			Files.walkFileTree(
				Paths.get(tempDirPathString, _STORAGE_DIR),
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
			framework.stop();

			Files.walkFileTree(
				tempDirPath,
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
							Path filePath,
							BasicFileAttributes basicFileAttributes)
						throws IOException {

						Files.delete(filePath);

						return FileVisitResult.CONTINUE;
					}

				});
		}
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

	private static final String _STORAGE_DIR = "/org.eclipse.osgi/";

	private static final String _TEMP_DIR = "/temp/";

	private static final String _TEST_EXPORT = "com.test.liferay.export";

}