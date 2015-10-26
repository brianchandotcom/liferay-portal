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

/**
 * @author Andrea Di Giorgi
 */
public class DeploymentHelperArgs {

	public String getDeploymentFileNames() {
		return _deploymentFileNames;
	}

	public String getDeploymentPath() {
		return _deploymentPath;
	}

	public String getOutputFileName() {
		return _outputFileName;
	}

	public void setDeploymentFileNames(String deploymentFileNames) {
		_deploymentFileNames = deploymentFileNames;
	}

	public void setDeploymentPath(String deploymentPath) {
		_deploymentPath = deploymentPath;
	}

	public void setOutputFileName(String outputFileName) {
		_outputFileName = outputFileName;
	}

	private String _deploymentFileNames;
	private String _deploymentPath;
	private String _outputFileName;

}