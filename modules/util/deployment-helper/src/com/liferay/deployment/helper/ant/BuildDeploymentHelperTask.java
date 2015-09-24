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
		catch (Exception e) {
			throw new BuildException(e);
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