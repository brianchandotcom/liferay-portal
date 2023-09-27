/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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