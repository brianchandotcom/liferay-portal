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

package com.liferay.module.dependency.closure.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.util.PropsValues;

import java.io.File;
import java.io.IOException;

import java.net.URI;

import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.wiring.FrameworkWiring;

/**
 * @author Shuyang Zhou
 */
@RunWith(Arquillian.class)
public class ModuleSelfContainedDependencyClosureTest {

	@Test
	public void testTestModuleSelfContainedDependencyClosure()
		throws IOException {

		Bundle bundle = FrameworkUtil.getBundle(
			ModuleSelfContainedDependencyClosureTest.class);

		BundleContext bundleContext = bundle.getBundleContext();

		Set<Bundle> testBundles = _getTestBundles(bundleContext);

		Bundle systemBundle = bundleContext.getBundle(0);

		FrameworkWiring frameworkWiring = systemBundle.adapt(
			FrameworkWiring.class);

		for (Bundle testBundle : testBundles) {
			Collection<Bundle> dependencyClosure =
				frameworkWiring.getDependencyClosure(Arrays.asList(testBundle));

			Assert.assertTrue(
				"Test bundle " + testBundle + " has a dependency closure " +
					dependencyClosure + " that is larger than self contained " +
						"scope " + testBundles,
				testBundles.containsAll(dependencyClosure));
		}
	}

	private Set<Bundle> _getTestBundles(BundleContext bundleContext)
		throws IOException {

		Set<Bundle> testBundles = new HashSet<>();

		testBundles.add(bundleContext.getBundle());

		Path modulesPath = Paths.get(PropsValues.MODULE_FRAMEWORK_MODULES_DIR);

		try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(
				Paths.get(PropsValues.MODULE_FRAMEWORK_BASE_DIR, "test"),
				"*.jar")) {

			for (Path jarPath : directoryStream) {
				Path deployedJarPath = modulesPath.resolve(
					jarPath.getFileName());

				File deployedJarFile = deployedJarPath.toFile();

				URI uri = deployedJarFile.toURI();

				Bundle deployedBundle = bundleContext.getBundle(uri.toString());

				if (deployedBundle == null) {
					throw new IllegalStateException(
						"Unable to find module " + deployedJarPath);
				}

				testBundles.add(deployedBundle);
			}
		}

		return testBundles;
	}

}