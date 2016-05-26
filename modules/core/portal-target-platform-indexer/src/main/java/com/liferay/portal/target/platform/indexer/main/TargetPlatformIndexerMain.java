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

import com.liferay.portal.target.platform.indexer.internal.TargetPlatformIndexer;

import java.io.File;

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

		File targetPlatformDir = new File(
			moduleFrameworkBaseDirName,
			TargetPlatformIndexer.DIR_NAME_TARGET_PLATFORM);

		if (!targetPlatformDir.exists() && !targetPlatformDir.mkdirs()) {
			System.err.printf(
				"== Unable to create directory %s\n", targetPlatformDir);

			return;
		}

		TargetPlatformIndexer targetPlatformIndexer = new TargetPlatformIndexer(
			moduleFrameworkBaseDirName, moduleFrameworkModulesDirName,
			moduleFrameworkPortalDirName);

		File indexFile = targetPlatformIndexer.index(targetPlatformDir);

		System.out.println("== Wrote index file " + indexFile);
	}

}