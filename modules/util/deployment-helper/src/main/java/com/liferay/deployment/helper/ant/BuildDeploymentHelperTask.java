/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.deployment.helper.ant;

import com.liferay.deployment.helper.DeploymentHelperArgs;
import com.liferay.deployment.helper.DeploymentHelperInvoker;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Task;

/**
 * @author Andrea Di Giorgi
 */
public class BuildDeploymentHelperTask extends Task {

	@Override
	public void execute() throws BuildException {
		try {
			Project project = getProject();

			DeploymentHelperInvoker.invoke(
				project.getBaseDir(), _deploymentHelperArgs);
		}
		catch (Exception exception) {
			throw new BuildException(exception);
		}
	}

	public String getDeploymentFileNames() {
		return _deploymentHelperArgs.getDeploymentFileNames();
	}

	public String getDeploymentPath() {
		return _deploymentHelperArgs.getDeploymentPath();
	}

	public String getOutputFileName() {
		return _deploymentHelperArgs.getOutputFileName();
	}

	public void setDeploymentFileNames(String deploymentFileNames) {
		_deploymentHelperArgs.setDeploymentFileNames(deploymentFileNames);
	}

	public void setDeploymentPath(String deploymentPath) {
		_deploymentHelperArgs.setDeploymentPath(deploymentPath);
	}

	public void setOutputFileName(String outputFileName) {
		_deploymentHelperArgs.setOutputFileName(outputFileName);
	}

	private final DeploymentHelperArgs _deploymentHelperArgs =
		new DeploymentHelperArgs();

}