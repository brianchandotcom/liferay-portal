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

package com.liferay.portal.target.platform.indexer.main;

import com.liferay.portal.target.platform.indexer.internal.PathUtil;
import com.liferay.portal.target.platform.indexer.internal.TargetPlatformIndexer;

import java.io.ByteArrayOutputStream;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.HashMap;
import java.util.Map;
import java.util.ServiceLoader;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.launch.Framework;
import org.osgi.framework.launch.FrameworkFactory;

/**
 * @author Raymond Augé
 */
public class TargetPlatformIndexerMain {

	public static void main(String[] args) throws Exception {
		String moduleFrameworkBaseDirName = System.getProperty(
			"module.framework.base.dir");

		if (moduleFrameworkBaseDirName == null) {
			System.err.println(
				"== -Dmodule.framework.base.dir must point to a valid " +
					"directory");

			return;
		}

		String moduleFrameworkModulesDirName = System.getProperty(
			"module.framework.modules.dir");

		if (moduleFrameworkModulesDirName == null) {
			moduleFrameworkModulesDirName =
				moduleFrameworkBaseDirName + "/modules/";
		}

		String moduleFrameworkPortalDirName = System.getProperty(
			"module.framework.portal.dir");

		if (moduleFrameworkPortalDirName == null) {
			moduleFrameworkPortalDirName =
				moduleFrameworkBaseDirName + "/portal/";
		}

		Path targetPlatformPath = Paths.get(
			moduleFrameworkBaseDirName,
			TargetPlatformIndexer.DIR_NAME_TARGET_PLATFORM);

		Files.createDirectories(targetPlatformPath);

		Framework framework = null;

		Path tempPath = Files.createTempDirectory(null);

		try {
			ServiceLoader<FrameworkFactory> serviceLoader = ServiceLoader.load(
				FrameworkFactory.class);

			FrameworkFactory frameworkFactory = serviceLoader.iterator().next();

			Map<String, String> properties = new HashMap<>();

			properties.put(
				org.osgi.framework.Constants.FRAMEWORK_STORAGE,
				tempPath.toString());

			framework = frameworkFactory.newFramework(properties);

			framework.init();

			BundleContext bundleContext = framework.getBundleContext();

			Bundle systemBundle = bundleContext.getBundle(0);

			TargetPlatformIndexer targetPlatformIndexer =
				new TargetPlatformIndexer(
					systemBundle, moduleFrameworkBaseDirName + "/static",
					moduleFrameworkModulesDirName,
					moduleFrameworkPortalDirName);

			ByteArrayOutputStream byteArrayOutputStream =
				new ByteArrayOutputStream();

			targetPlatformIndexer.index(byteArrayOutputStream);

			Path indexFilePath = targetPlatformPath.resolve(
				"target.platform.index.xml");

			Files.write(indexFilePath, byteArrayOutputStream.toByteArray());

			System.out.println("== Wrote index file " + indexFilePath);
		}
		finally {
			framework.stop();

			PathUtil.deltree(tempPath);
		}
	}

}