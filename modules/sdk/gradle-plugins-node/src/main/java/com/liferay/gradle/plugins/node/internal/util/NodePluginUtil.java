/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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