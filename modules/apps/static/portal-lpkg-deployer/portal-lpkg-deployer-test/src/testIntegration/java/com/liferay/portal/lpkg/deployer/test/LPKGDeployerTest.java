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
import com.liferay.portal.kernel.lpkg.StaticLPKGResolver;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.URLCodec;
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
	public void testPostUpgradeDeployedLPKGS() throws Exception {
		if (!Boolean.getBoolean("before.lpkg.upgrade")) {
			testDeployedLPKGs();
		}
	}

	@Test
	public void testPreUpgradeDeployedLPKGS() throws Exception {
		if (Boolean.getBoolean("before.lpkg.upgrade")) {
			testDeployedLPKGs();
		}
	}

	protected void testDeployedLPKGs() throws Exception {
		Bundle testBundle = FrameworkUtil.getBundle(LPKGDeployerTest.class);

		BundleContext bundleContext = testBundle.getBundleContext();

		final String lpkgDeployerDirString = bundleContext.getProperty(
			"lpkg.deployer.dir");

		Assert.assertNotNull(
			"The property \"lpkg.deployer.dir\" is null",
			lpkgDeployerDirString);

		Path lpkgDeployerDirPath = Paths.get(lpkgDeployerDirString);

		Assert.assertTrue(
			"The path " + lpkgDeployerDirString + " does not exist",
			Files.exists(lpkgDeployerDirPath));

		final List<File> lpkgFiles = new ArrayList<>();

		Files.walkFileTree(
			lpkgDeployerDirPath,
			new SimpleFileVisitor<Path>() {

				@Override
				public FileVisitResult visitFile(
						Path filePath, BasicFileAttributes basicFileAttributes)
					throws IOException {

					Path fileNamePath = filePath.getFileName();

					String fileName = StringUtil.toLowerCase(
						fileNamePath.toString());

					if (fileName.endsWith(".markdown")) {
						return FileVisitResult.CONTINUE;
					}

					if (!fileName.endsWith(".lpkg")) {
						Assert.fail(
							"Unexpected file " + filePath + " in " +
								lpkgDeployerDirString);
					}

					lpkgFiles.add(filePath.toFile());

					return FileVisitResult.CONTINUE;
				}

			});

		Assert.assertFalse(
			"There are no LPKG files in " + lpkgDeployerDirString,
			lpkgFiles.isEmpty());

		ServiceTracker<LPKGDeployer, LPKGDeployer> serviceTracker =
			new ServiceTracker<>(bundleContext, LPKGDeployer.class, null);

		serviceTracker.open();

		LPKGDeployer lpkgDeployer = serviceTracker.getService();

		serviceTracker.close();

		Map<Bundle, List<Bundle>> deployedLPKGBundles =
			lpkgDeployer.getDeployedLPKGBundles();

		for (File lpkgFile : lpkgFiles) {
			Bundle lpkgBundle = bundleContext.getBundle(
				lpkgFile.getCanonicalPath());

			Assert.assertNotNull(
				"No matching LPKG bundle for " + lpkgFile.getCanonicalPath(),
				lpkgBundle);

			List<Bundle> expectedAppBundles = new ArrayList<>(
				deployedLPKGBundles.get(lpkgBundle));

			Assert.assertNotNull(
				"Registered LPKG bundles " + deployedLPKGBundles.keySet() +
					" do not contain " + lpkgBundle,
				expectedAppBundles);

			Collections.sort(expectedAppBundles);

			List<Bundle> actualAppBundles = new ArrayList<>();

			ZipFile zipFile = new ZipFile(lpkgFile);

			Enumeration<? extends ZipEntry> enumeration = zipFile.entries();

			String symbolicName = lpkgBundle.getSymbolicName();

			while (enumeration.hasMoreElements()) {
				ZipEntry zipEntry = enumeration.nextElement();

				String name = zipEntry.getName();

				if (name.endsWith(".jar")) {
					if (symbolicName.equals(
							StaticLPKGResolver.
								getStaticLPKGBundleSymbolicName())) {

						Bundle bundle = bundleContext.getBundle(
							"reference:" + StringPool.SLASH + name);

						Assert.assertNotNull(
							"No matching static bundle for reference:/" + name,
							bundle);
					}
					else {
						Bundle bundle = bundleContext.getBundle(
							StringPool.SLASH + name);

						Assert.assertNotNull(
							"No matching app bundle for /" + name, bundle);

						actualAppBundles.add(bundle);
					}
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

					StringBundler sb = new StringBundler(10);

					sb.append("webbundle:lpkg://");
					sb.append(URLCodec.encodeURL(lpkgBundle.getSymbolicName()));
					sb.append(StringPool.DASH);
					sb.append(lpkgBundle.getVersion());
					sb.append(StringPool.SLASH);
					sb.append(contextName);
					sb.append(".war?Bundle-Version=");
					sb.append(bundle.getVersion());
					sb.append("&Web-ContextPath=/");
					sb.append(contextName);

					String location = sb.toString();

					Assert.assertNotNull(
						"Missing WAR bundle for wrapper bundle " + bundle +
							" with expected location " + location,
						bundleContext.getBundle(location));
				}
			}

			if (!symbolicName.equals("static")) {
				Collections.sort(actualAppBundles);

				Assert.assertEquals(
					"LPKG bundle " + lpkgBundle + " expects app bundles " +
						expectedAppBundles + " but has actual app bundles " +
							actualAppBundles,
					expectedAppBundles, actualAppBundles);
			}
		}
	}

}