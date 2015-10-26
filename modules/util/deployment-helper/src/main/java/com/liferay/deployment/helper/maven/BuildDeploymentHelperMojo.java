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

package com.liferay.deployment.helper.maven;

import com.liferay.deployment.helper.DeploymentHelperArgs;
import com.liferay.deployment.helper.DeploymentHelperInvoker;

import java.io.File;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;

/**
 * @author Andrea Di Giorgi
 * @goal build-deployment-helper
 */
public class BuildDeploymentHelperMojo extends AbstractMojo {

	@Override
	public void execute() throws MojoExecutionException {
		try {
			DeploymentHelperInvoker.invoke(baseDir, _deploymentHelperArgs);
		}
		catch (Exception e) {
			throw new MojoExecutionException(e.getMessage(), e);
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

	/**
	 * @parameter
	 */
	public void setDeploymentFileNames(String deploymentFileNames) {
		_deploymentHelperArgs.setDeploymentFileNames(deploymentFileNames);
	}

	/**
	 * @parameter
	 */
	public void setDeploymentPath(String deploymentPath) {
		_deploymentHelperArgs.setDeploymentPath(deploymentPath);
	}

	/**
	 * @parameter
	 */
	public void setOutputFileName(String outputFileName) {
		_deploymentHelperArgs.setOutputFileName(outputFileName);
	}

	/**
	 * @parameter default-value="${project.basedir}
	 * @readonly
	 */
	protected File baseDir;

	private final DeploymentHelperArgs _deploymentHelperArgs =
		new DeploymentHelperArgs();

}