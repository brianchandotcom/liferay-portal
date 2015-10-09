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

package com.liferay.deployment.helper;

import java.io.File;

/**
 * @author Andrea Di Giorgi
 */
public class DeploymentHelperInvoker {

	public static DeploymentHelper invoke(
			File baseDir, DeploymentHelperArgs deploymentHelperArgs)
		throws Exception {

		return new DeploymentHelper(
			_getAbsolutePaths(
				baseDir, deploymentHelperArgs.getDeploymentFileNames()),
			_getAbsolutePath(baseDir, deploymentHelperArgs.getDeploymentPath()),
			_getAbsolutePath(
				baseDir, deploymentHelperArgs.getOutputFileName()));
	}

	private static String _getAbsolutePath(File baseDir, String fileName) {
		File file = new File(baseDir, fileName);

		fileName = file.getAbsolutePath();

		return fileName.replace('\\', '/');
	}

	private static String _getAbsolutePaths(File baseDir, String fileNames) {
		StringBuilder sb = new StringBuilder();

		for (String fileName : fileNames.split(",")) {
			sb.append(_getAbsolutePath(baseDir, fileName));
			sb.append(',');
		}

		sb.setLength(sb.length() - 1);

		return sb.toString();
	}

}