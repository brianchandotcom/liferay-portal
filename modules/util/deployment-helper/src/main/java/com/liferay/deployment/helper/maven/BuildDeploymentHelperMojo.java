/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.deployment.helper.maven;

import com.liferay.deployment.helper.DeploymentHelperArgs;
import com.liferay.deployment.helper.DeploymentHelperInvoker;

import java.io.File;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;

/**
 * @author Andrea Di Giorgi
 * @goal build
 */
public class BuildDeploymentHelperMojo extends AbstractMojo {

	@Override
	public void execute() throws MojoExecutionException {
		try {
			DeploymentHelperInvoker.invoke(baseDir, _deploymentHelperArgs);
		}
		catch (Exception exception) {
			throw new MojoExecutionException(exception.getMessage(), exception);
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