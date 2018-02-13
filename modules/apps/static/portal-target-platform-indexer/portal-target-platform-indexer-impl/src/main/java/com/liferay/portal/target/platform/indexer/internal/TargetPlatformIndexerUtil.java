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

package com.liferay.portal.target.platform.indexer.internal;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;

import java.nio.file.Files;
import java.nio.file.Path;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.framework.FrameworkEvent;
import org.osgi.framework.launch.Framework;
import org.osgi.framework.launch.FrameworkFactory;

/**
 * @author Shuyang Zhou
 */
public class TargetPlatformIndexerUtil {

	public static void indexTargetPlatform(
			OutputStream outputStream, List<File> additionalJarFiles,
			long stopWaitTimeout, String... dirNames)
		throws Exception {

		Framework framework = null;

		Path tempPath = Files.createTempDirectory(null);

		ClassLoader classLoader =
			TargetPlatformIndexerUtil.class.getClassLoader();

		try (InputStream inputStream = classLoader.getResourceAsStream(
				"META-INF/system.packages.extra.mf")) {

			Map<String, String> properties = new HashMap<>();

			properties.put(Constants.FRAMEWORK_STORAGE, tempPath.toString());

			Manifest extraPackagesManifest = new Manifest(inputStream);

			Attributes attributes = extraPackagesManifest.getMainAttributes();

			properties.put(
				Constants.FRAMEWORK_SYSTEMPACKAGES_EXTRA,
				attributes.getValue("Export-Package"));

			ServiceLoader<FrameworkFactory> serviceLoader = ServiceLoader.load(
				FrameworkFactory.class);

			Iterator<FrameworkFactory> iterator = serviceLoader.iterator();

			FrameworkFactory frameworkFactory = iterator.next();

			framework = frameworkFactory.newFramework(properties);

			framework.init();

			BundleContext bundleContext = framework.getBundleContext();

			Bundle systemBundle = bundleContext.getBundle(0);

			TargetPlatformIndexer targetPlatformIndexer =
				new TargetPlatformIndexer(
					systemBundle, additionalJarFiles, dirNames);

			targetPlatformIndexer.index(outputStream);
		}
		finally {
			framework.stop();

			FrameworkEvent frameworkEvent = framework.waitForStop(
				stopWaitTimeout);

			if (frameworkEvent.getType() == FrameworkEvent.WAIT_TIMEDOUT) {
				throw new Exception(
					"OSGi framework event " + frameworkEvent +
						" triggered after a " + stopWaitTimeout + "ms timeout");
			}

			PathUtil.deltree(tempPath);
		}
	}

}