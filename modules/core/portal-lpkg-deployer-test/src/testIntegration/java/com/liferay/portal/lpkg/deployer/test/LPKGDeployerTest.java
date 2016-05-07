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

package com.liferay.portal.lpkg.deployer.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.lpkg.deployer.LPKGDeployer;

import java.io.File;
import java.io.IOException;

import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * @author Matthew Tambara
 */
@RunWith(Arquillian.class)
public class LPKGDeployerTest {

	@Test
	public void testDeployedLPKGs() throws Exception {
		Bundle testBundle = FrameworkUtil.getBundle(LPKGDeployerTest.class);

		BundleContext bundleContext = testBundle.getBundleContext();

		final String deploymentDir = bundleContext.getProperty(
			"lpkg.deployer.dir");

		Assert.assertNotNull(
			"Missing configuration for \"lpkg.deployer.dir\"", deploymentDir);

		Path deploymentDirPath = Paths.get(deploymentDir);

		Assert.assertTrue(
			deploymentDir + " does not exist", Files.exists(deploymentDirPath));

		final List<File> lpkgFiles = new ArrayList<>();

		Files.walkFileTree(
			deploymentDirPath,
			new SimpleFileVisitor<Path>() {

				@Override
				public FileVisitResult visitFile(
						Path filePath, BasicFileAttributes basicFileAttributes)
					throws IOException {

					Path fileNamePath = filePath.getFileName();

					String fileName = StringUtil.toLowerCase(
						fileNamePath.toString());

					if (!fileName.endsWith(".lpkg")) {
						Assert.fail(
							"Unexpected file " + filePath + " in " +
								deploymentDir);
					}

					lpkgFiles.add(filePath.toFile());

					return FileVisitResult.CONTINUE;
				}

			});

		Assert.assertFalse(
			"No lpkg file in " + deploymentDir, lpkgFiles.isEmpty());

		ServiceTracker<LPKGDeployer, LPKGDeployer> serviceTracker =
			new ServiceTracker<>(bundleContext, LPKGDeployer.class, null);

		serviceTracker.open();

		LPKGDeployer lpkgDeployer = serviceTracker.getService();

		serviceTracker.close();

		Map<Bundle, List<Bundle>> bundleMap =
			lpkgDeployer.getDeployedLPKGBundles();

		for (File file : lpkgFiles) {
			Bundle lpkgBundle = bundleContext.getBundle(
				file.getCanonicalPath());

			Assert.assertNotNull(
				"No matching lpkg bundle for " + file.getCanonicalPath(),
				lpkgBundle);

			List<Bundle> expectedAppBundles = new ArrayList<>(
				bundleMap.get(lpkgBundle));

			Assert.assertNotNull(
				"Registered lpkg bundles " + bundleMap.keySet() +
					" do not contain " + lpkgBundle,
				expectedAppBundles);

			Collections.sort(expectedAppBundles);

			List<Bundle> actualAppBundles = new ArrayList<>();

			ZipFile zipFile = new ZipFile(file);

			Enumeration<? extends ZipEntry> enumeration = zipFile.entries();

			while (enumeration.hasMoreElements()) {
				ZipEntry zipEntry = enumeration.nextElement();

				String name = zipEntry.getName();

				if (name.endsWith(".jar")) {
					Bundle bundle = bundleContext.getBundle(
						StringPool.SLASH + name);

					Assert.assertNotNull(
						"No matching app bundle for /" + name, bundle);

					actualAppBundles.add(bundle);
				}

				if (name.endsWith(".war")) {
					Bundle bundle = bundleContext.getBundle(
						StringPool.SLASH + name);

					Assert.assertNotNull(
						"No matching app bundle for /" + name, bundle);

					actualAppBundles.add(bundle);

					String contextName = name.substring(
						0, name.lastIndexOf(".war"));

					int index = contextName.lastIndexOf('-');

					if (index >= 0) {
						contextName = contextName.substring(0, index);
					}

					StringBundler sb = new StringBundler(8);

					sb.append("webbundle:lpkg://");
					sb.append(lpkgBundle.getSymbolicName());
					sb.append(StringPool.DASH);
					sb.append(lpkgBundle.getVersion());
					sb.append(StringPool.SLASH);
					sb.append(contextName);
					sb.append(".war?Web-ContextPath=/");
					sb.append(contextName);

					String location = sb.toString();

					Assert.assertNotNull(
						"Missing war bundle for wrapper bundle " + bundle +
							" with expected location " + location,
						bundleContext.getBundle(location));
				}
			}

			Collections.sort(actualAppBundles);

			Assert.assertEquals(
				"For lpkg bundle " + lpkgBundle + ", expected app bundles " +
					expectedAppBundles + ", actual app bundles " +
						actualAppBundles,
				expectedAppBundles, actualAppBundles);
		}
	}

}