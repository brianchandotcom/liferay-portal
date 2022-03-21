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

package com.liferay.gradle.plugins.node.internal.util;

import java.io.File;

/**
 * @author Andrea Di Giorgi
 */
public class NodePluginUtil {

	public static File getBinDir(File nodeDir) {
		File binDir = new File(nodeDir, "bin");

		if (!binDir.exists()) {
			binDir = nodeDir;
		}

		return binDir;
	}

	public static int getNodeMajorVersion(String version) {
		int index = version.indexOf('.');

		if (index > 0) {
			return Integer.parseInt(version.substring(0, index));
		}

		return Integer.parseInt(version);
	}

	public static File getNpmDir(File nodeDir) {
		File nodeModulesDir = new File(nodeDir, "node_modules");

		if (!nodeModulesDir.exists()) {
			nodeModulesDir = new File(nodeDir, "lib/node_modules");
		}

		return new File(nodeModulesDir, "npm");
	}

	public static File getYarnDir(File nodeDir) {
		File nodeModulesDir = new File(nodeDir, "node_modules");

		if (!nodeModulesDir.exists()) {
			nodeModulesDir = new File(nodeDir, "lib/node_modules");
		}

		return new File(nodeModulesDir, "yarn");
	}

}